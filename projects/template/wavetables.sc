(
  // Clean up
  ~wtBuffers.do(_.free);
)

(
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
)

(
  SynthDef(\osc, {
    arg buf=0, freq=200, detune=0.2,
    amp=0.2, pan=0, out=0, rout=0, rsend=(-20),
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
)

// Examples

~wavetables.reverseDo(_.plot);
t = TempoClock.new(90/60).permanent_(true);
s.options.sampleRate_(44100);
s.options.memSize_(2.pow(20));

(
  Pdef(\bass,
    Pbind(
      \instrument, \osc,
      \dur, 1/2,
      \freq, 70,
      \atk, 0,
      \sus, 0,
      \rel, Pexprand(1.5,2.7),
      \c0, Pexprand(1,2),
      \c1, Pexprand(1,2).neg,
      \detune, Pseq(0!3, inf),
      \buf, Prand(~wtBuffers[0..2], inf),
      \scale, Scale.minorPentatonic,
      // \degree, Pfunc({ (-12,-10..12).scramble[0..rrand(1,3)] }),
      \amp, Pexprand(0.05,0.07),
      \pan, 0, //Pwhite(-0.4,0.4),
      \rsend, -10
    )
  );

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
      \rsend, -10
    )
  );
)

Pdef(\pads).play(t, quant:1);
Pdef(\bass).play(t, quant:1);

Pdef(\pads).stop();
Pdef(\bass).stop();
