"./kick.sc".loadRelative;

(
  Synth(\kick, [
    \freq: 32.midicps
  ]);
)

(
  Pbindef(\ex1,
    \instrument, \kick,
    \freq, 30.midicps, // A: 21, 33, 45=110, 57, 69, 81, 93
    \hitHz, 600,
    \hitDur, 0.025,
    \amp, 0.5,
    \dur, 4
  );
)

Pbindef(\ex1).play;
Pbindef(\ex1).stop;
