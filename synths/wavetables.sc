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

    sig = LeakDC.ar(sig); //remove DC bias
    sig = PanAz.ar(4, sig, LFSaw.kr(0.05), 0.01, width:2);
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
  Pbindef(\bass,
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
  );
)

Pbindef(\bass).play(t, quant:1);
Pbindef(\bass).stop();

(
  Pbindef(\pads,
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
  );
)

Pbindef(\pads).play(t, quant:1);
Pbindef(\pads).stop();

(
	Pbindef(\pulse,
		\instrument, \osc,
		\dur, Pseq([
			Pstutter(24,Pseq([1/4],1)),
			Prand([1,2,4,6,12],1)
		],inf),
		\atk, 0.001,
		\sus, 0,
		\rel, Pexprand(0.4,1),
		\c0, 0,
		\c1, Pwhite(5,10).neg,
		\detune, 0.3,
		\buf, Prand(~wtBuffers[4..9], inf),
		\scale, Scale.minorPentatonic,
		\degree, Pseq([Prand([-15,-10,-5],24), Pseq([\],1)],inf)
		+ Pstutter(25,Pwrand([0,2,-1],[0.78,0.1,0.12],inf)),
		\amp, Pseq([Pgeom(0.45,-1.dbamp,25)],inf),
		\pan, Pwhite(0.01,0.3) * Pseq([1,-1],inf)
	);
)

Pbindef(\pulse).play(t, quant:1);
Pbindef(\pulse).stop;

(
	Pbindef(\melody,
		\instrument, \osc,
		\dur, Prand([
			Pseq([Prand([3,4,5]),2,1.5,0.5],1),
			Pseq([Prand([3,4,5]),1.5,1,1.5],1),
		],inf),
		\atk, 0.01,
		\sus, 0.3,
		\rel, 1.5,
		\c0, -2,
		\c1, -2,
		\detune, Pexprand(0.18,0.25),
		\buf, Pwrand([
			Pseq([~wtBuffers[0]],4),
			Pseq([~wtBuffers[1]],4),
			Pseq([~wtBuffers[2]],4),
		],[9,3,1].normalizeSum,inf),
		\midinote, Pxrand([
			Pseq([\,67,60,Prand([58,70,\])],1),
			Pseq([\,67,58,Prand([57,63,\])],1),
			Pseq([\,70,72,Prand([65,79,\])],1)
		],inf),
		\amp, Pseq([0,0.18,0.24,0.28],inf)
	);
)

// might take a while to start
Pbindef(\melody).play(t, quant:1);
Pbindef(\melody).stop;
