(
// 0. clean up
Pdef.all.do(_.clear);
Pbindef.all.do(_.clear);
~wtBuffers.do(_.free);
t.stop;
ServerTree.remove(~add_reverb);
)

(
//1. server config
s = Server.local;
t = TempoClock.new(90/60).permanent_(true);
s.options.numOutputBusChannels_(2);
s.options.sampleRate_(44100);
s.options.memSize_(2.pow(20));
s.newBusAllocators;
~rbus = Bus.audio(s,2);

//2. initialize global variables
~out = 0;

s.waitForBoot({
  //10 wavetables with increasing complexity
  ~wavetables = 10.collect({
    arg i;

    //random number of envelope segments
    var numSegs = i.linexp(0,9,4,40).round;

    Env(
      //env always begins and ends with zero
      //inner points are random from -1.0 to 1.0
      [0]++({1.0.rand}.dup(numSegs-1) * [1,-1]).scramble++[0],

      //greater segment duration variety in higher-index wavetables
      {exprand(1,i.linexp(0,9,1,50))}.dup(numSegs),

      //low-index wavetables tend to be sinusoidal
      //high index wavetables tend to have sharp angles and corners
      {[\sine,0,exprand(1,20) * [1,-1].choose].wchoose([9-i,3,i].normalizeSum)}.dup(numSegs);
    ).asSignal(1024);
  });

  //load into 10 buffers in wavetable format
  ~wtBuffers = Buffer.allocConsecutive(10, s, 2048, 1, {
    arg buf, index;
    buf.setnMsg(0, ~wavetables[index].asWavetable);
  });

  SynthDef(\reverb, {
    arg in, predelay=0.1, revtime=1.8,
    lpf=4500, mix=0.15, amp=1, out=~out;
    var dry, wet, temp, sig;
    dry = In.ar(in,2);
    temp = In.ar(in,2);
    wet = 0;
    temp = DelayN.ar(temp, 0,2, predelay);
    16.do{
      temp = AllpassN.ar(temp, 0.05, {Rand(0.001,0.05)}!2, revtime);
      temp = LPF.ar(temp, lpf);
      wet = wet + temp;
    };
    sig = XFade2.ar(dry, wet, mix*2-1, amp);
    Out.ar(out, sig);
  }).add;

  s.sync;

  //instantiate reverb and re-instantiate when cmd-period is pressed
  ~add_reverb = {Synth(\reverb, [\in, ~rbus])};
  ServerTree.add(~add_reverb);
  s.freeAll;

  s.sync;

  SynthDef(\osc, {
    arg buf=0, freq=200, detune=0.2,
    amp=0.2, pan=0, out=~out, rout=0, rsend=(-20),
    atk=0.01, sus=1, rel=0.01, c0=1, c1=(-1);
    var sig, env, detuneCtrl;
    env = EnvGen.ar(
      Env([0,1,1,0],[atk,sus,rel],[c0,0,c1]),
      doneAction:2
    );

    //array of eight Oscs with uniquely detune frequencies
    //and unique initial phase offsets
    detuneCtrl = LFNoise1.kr(0.1!8).bipolar(detune).midiratio;
    sig = Osc.ar(buf, freq * detuneCtrl, {rrand(0,2pi)}!8);

    sig = Splay.ar(sig); //spread 8 signals over stereo field
    sig = LeakDC.ar(sig); //remove DC bias
    sig = Balance2.ar(sig[0], sig[1], pan, amp); //L/R balance (pan)
    sig = sig * env;
    Out.ar(out, sig);
    Out.ar(rout, sig * rsend.dbamp); //"post-fader" send to reverb
  }).add;

  Pdef(\pads,
    Pbind(
      \instrument, \osc,
      \dur, Pwrand([1,4,6,9,12],[0.35,0.25,0.2,0.15,0.05],inf),
      \atk, Pexprand(3,6),
      \sus, 0,
      \rel, Pexprand(5,10),
      \c0, Pexprand(1,2),
      \c1, Pexprand(1,2).neg,
      \root, 1,
      \detune, Pfunc({rrand(0.15,0.4)}!3),
      \buf, Prand(~wtBuffers[0..3], inf),
      \scale, Scale.minorPentatonic,
      \degree, Pfunc({ (-12,-10..12).scramble[0..rrand(1,3)] }),
      \amp, Pexprand(0.05,0.07),
      \pan, Pwhite(-0.4,0.4),
      \out, ~out,
      \rout, ~rbus,
      \rsend, -10,
    )
  );
})
)

~wavetables.reverseDo(_.plot);

Pdef(\pads).play(t, quant:1);
Pdef(\pads).set(\root, 2);
Pdef(\pads).stop;
