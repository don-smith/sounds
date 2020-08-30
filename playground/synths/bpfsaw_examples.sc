// Using /synths/bpfsaw.sc

(
  Pbindef(\ex1,
    \instrument, \bpfsaw,
    \atk, 0.5,
    \rel, 2,
    \dur, Pseq([1, 0.5, 2], inf).trace,
    \midinote, Pseq([60, 65, 67], inf).trace
  )
)

Pbindef(\ex1).play;
Pbindef(\ex1).stop;

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

Pbindef(\ex2).play;
Pbindef(\ex2).stop;

(
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

Pbindef(\ex3).play;
Pbindef(\ex3).stop;

(
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
  );
)

Pbindef(\ex4).play;
Pbindef(\ex4).stop;
