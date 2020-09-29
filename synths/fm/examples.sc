"./fm.sc".loadRelative;

(
  Pbindef(\ex1,
    \instrument, \fm,
    \freq, 36.midicps,
    \atk, 0.3,
    \rel, 2,
    \index, 11,
    \iScale, 0.05,
    \mRatio, 0.4
  );
)

Pbindef(\ex1).quant = 1;
Pbindef(\ex1).play;
Pbindef(\ex1, \rel, 1);
Pbindef(\ex1, \dur, 0.85);
Pbindef(\ex1, \atk, 0.01);
Pbindef(\ex1, \amp, 0.3);
Pbindef(\ex1, \freq, 44.midicps);
Pbindef(\ex1).stop;
