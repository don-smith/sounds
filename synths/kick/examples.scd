"./kick.sc".loadRelative;

(
  Synth(\kick, [
    \freq: 32.midicps
  ]);
)

(
  Pbindef(\ex1,
    \instrument, \kick,
    \freq, 34.midicps, // A: 21, 33, 45=110, 57, 69, 81, 93
    \hitHz, 600,
    \hitDur, 0.025,
    \amp, 0.5,
    \dur, 0.5
  );
)

t = TempoClock(106/60)
Pbindef(\ex1).play(t, quant:4);
Pbindef(\ex1).stop;
