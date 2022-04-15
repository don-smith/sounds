"./bpfsaw2.sc".loadRelative;

(
  10.do{
    Synth(\bpfsaw, [
      \freq, 50,
      \detune, 0, // 0 to disable detuning
      \cfmin, 50*2,
      \cfmax, 50*50,
      \rqmin, 0.005,
      \rqmax, 0.03,
      \amp, 0.6,
    ])
  }
)

(
  10.do{
    Synth(\bpfsaw, [
      \midinote, [23,35,54,63,64],
      \detune, 0.7, // 0 to disable detuning
      \cfmin, ([23,35,54,63,64]).midicps*2,
      \cfmax, ([23,35,54,63,64]).midicps*40,
      \cfhzmin, 3,
      \cfhzmax, 6,
      \rqmin, 0.08,
      \rqmax, 0.5,
      \amp, 0.2,
      \atk, 4,
      \rel, 12,
    ])
  }
)

(
  ~chrds=(
    \c1: [23,35,54,63,64],
    \c2: [45,52,54,59,61,64],
    \c3: [28,40,47,56,59,63]
  );
  Pbindef(\ex1,
    \instrument, \bpfsaw,
    \midinote, Pseq([~chrds[\c1], ~chrds[\c2], ~chrds[\c3]], inf),
    \detune, 0.3, // 0 to disable detuning
    \cfmin, Pseq([
      Prand(~chrds[\c1]).midicps*Pwhite(2,5),
      Prand(~chrds[\c2]).midicps*Pwhite(2,5),
      Prand(~chrds[\c3]).midicps*Pwhite(2,5)
    ], inf),
    \cfmax, Pseq([
      Prand(~chrds[\c1]).midicps*Pwhite(30,80),
      Prand(~chrds[\c2]).midicps*Pwhite(30,80),
      Prand(~chrds[\c3]).midicps*Pwhite(30,80)
    ], inf),
    \rqmin, 0.05,
    \rqmax, 0.3,
    \amp, 0.5,
    \atk, 2,
    \rel, Pwhite(5,8),
    \dur, Pwhite(4,6),
  )
)

Pbindef(\ex1).play
Pbindef(\ex1).stop

(
  Pbindef(\ex2,
    \instrument, \bpfsaw,
    \dur, Pwhite(4.5,7.0,inf),
    \midinote, Pxrand([
      [23,35,54,63,64],
      [45,52,54,59,61,64],
      [28,40,47,56,59,63],
      [42,52,57,61,63],
    ], inf),
    \detune, Pexprand(0.05,0.2,inf),
    \cfmin, 100,
    \cfmax, 1500,
    \rqmin, Pexprand(0.01,0.15,inf),
    \atk, Pwhite(2.0,2.5,inf),
    \rel, Pwhite(6.5,10.0,inf),
    \ldb, 6,
    \amp, 0.2
  )
)

Pbindef(\ex2).play
Pbindef(\ex2).stop

(
  // Marimba
  Pbindef(\ex3,
    \instrument, \bpfsaw,
    \dur, Pexprand(0.1,1,inf),
    \freq, Pexprand(0.25,9,inf),
    \detune, 0,
    \rqmin, 0.005,
    \rqmax, 0.008,
    \cfmin, 150,
    \cfmax, 1500,
    \amp, 0.7
  )
)

Pbindef(\ex3).play
Pbindef(\ex3).stop

(
  // Marimba
  Pbindef(\ex4,
    \instrument, \bpfsaw,
    \dur, Prand([1,0.5], inf),
    \freq, Prand([1/2, 2/3, 1, 4/3, 2, 5/2, 3, 4, 6, 8], inf),
    \detune, Pwhite(0,0.1,inf),
    \rqmin, 0.005,
    \rqmax, 0.008,
    \cfmin, Prand((Scale.major.degrees+64).midicps, inf) * Prand([0.5,1,2,4], inf),
    \cfmax, Pkey(\cfmin) * Pwhite(1.008,1.025, inf),
    \atk, 3,
    \sus, 1,
    \rel, 5,
    \amp, 0.7
  )
)

Pbindef(\ex4).play
Pbindef(\ex4).stop

(
  // still a work in progress
  Pbindef(\ex5,
    \instrument, \bpfsaw,
    // \midinote, Pxrand([23,35,54,45,52,54,28,40,47], inf),
    \scale, Scale.major,
    \octave, 4,
    \degree, Pxrand(Scale.major.degrees, inf),
    \detune, 0.02, // 0 to disable detuning
    \cfmin, 250,
    \cfmax, 2500,
    // \cfhzmin, 2,
    // \cfhzmax, 4,
    \rqmin, 0.05,
    \rqmax, 0.6,
    \atk, 0.2,
    \rel, 0.9,
    \amp, 0.3,
    \dur, Prand([1, 1/2, 1, 2/3], inf)
  )
)

Pbindef(\ex5).play
Pbindef(\ex5).stop

(
  Pbindef(\chords,
    \instrument,\bpfsaw,
    \dur,Pwhite(4.5,7.0,inf),
    \scale,[Scale.minor,Scale.major].choose,
    \degree,Pwrand([[0,2,4],[3,5,7],[4,6,8]],[0.5,0.25,0.25],inf),
    \cfmin,100,
    \cfmax,1500,
    \rqmin,Pexprand(0.02,0.15,inf),
    \atk,Pwhite(2.0,4.5,inf),
    \rel,Pwhite(6.5,10.0,inf),
    // \ldb,6,
    // \lsf,1000,
    \octave,Pwrand([4,3,5],[0.6,0.3,0.1],inf),
    \amp,Pwhite(0.5,1.0),
    \out,0
  )
)

Pbindef(\chords).play
Pbindef(\chords).stop
