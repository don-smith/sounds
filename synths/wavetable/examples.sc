("./wavetable.sc").loadRelative;

s.options.sampleRate_(44100);
s.options.memSize_(2.pow(20));
s.reboot;

(
  Pbindef(\test,
    \instrument, \wavetable,
    \buf, b[0],
    \freq, 80,
    \dur, 1
  );
)
Pbindef(\test).play;
Pbindef(\test, \amp, 9.8);
Pbindef(\test).stop;

(
  Pbindef(\bass,
    \instrument, \wavetable,
    \dur, 1/2,
    // \freq, 70,
    \atk, 0,
    \sus, 0,
    \rel, Pexprand(1.5,2.7),
    \c0, Pexprand(1,2),
    \c1, Pexprand(1,2).neg,
    \detune, Pseq(0!3, inf),
    \buf, Prand(b[0..2], inf),
    \scale, Scale.minorPentatonic,
    \root, 1,
    \octave, 3,
    \degree, Pfunc({ (-12,-10..12).scramble[0..rrand(1,3)] }),
    \amp, Pexprand(0.5,1.07),
    \pan, 0, //Pwhite(-0.4,0.4),
    \rsend, -10
  );
)

Pbindef(\bass).play;
Pbindef(\bass, \amp, 0.6);
Pbindef(\bass, \scale, Scale.phrygian);
Pbindef(\bass, \root, -3);
Pbindef(\bass, \octave, 4);
Pbindef(\bass).stop;
Scale.directory;

(
  Pbindef(\pads,
    \instrument, \wavetable,
    \dur, Pwrand([1,4,6,9,12],[0.35,0.25,0.2,0.15,0.05],inf),
    \atk, Pexprand(3,6),
    \sus, 0,
    \rel, Pexprand(5,10),
    \c0, Pexprand(1,2),
    \c1, Pexprand(1,2).neg,
    \root, 1,
    \detune, Pfunc({rrand(0.15,0.4)}!3),
    \buf, Prand(b[0..3], inf),
    \scale, Scale.minorPentatonic,
    \degree, Pfunc({ (-12,-10..12).scramble[0..rrand(1,3)] }),
    \amp, Pexprand(0.15,0.17),
    \pan, Pwhite(-0.4,0.4),
    \rsend, -10
  );
)

Pbindef(\pads).play;
Pbindef(\pads, \amp, 1.3);
Pbindef(\pads).stop;

(
  Pbindef(\pulse,
    \instrument, \wavetable,
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
    \buf, Prand(b[4..9], inf),
    \scale, Scale.minorPentatonic,
    \degree, Pseq([Prand([-15,-10,-5],24), Pseq([\],1)],inf)
      + Pstutter(25,Pwrand([0,2,-1],[0.78,0.1,0.12],inf)),
    \amp, Pseq([Pgeom(0.45,-1.dbamp,25)],inf),
    \pan, Pwhite(0.01,0.3) * Pseq([1,-1],inf)
  );
)

Pbindef(\pulse).play;
Pbindef(\pulse, \amp, 1.3);
Pbindef(\pulse).stop;

(
  Pbindef(\melody,
    \instrument, \wavetable,
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
      Pseq([b[0]],4),
      Pseq([b[1]],4),
      Pseq([b[2]],4),
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
Pbindef(\melody).play;
Pbindef(\melody, \amp, 1.4);
Pbindef(\melody).stop;
