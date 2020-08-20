(
SynthDef(\fm, {
  | freq=500, mRatio=1, cRatio=1, index=1, iScale=5,
    atk=0.01, rel=3, amp=0.2, cAtk=4, cRel=(-4), pan=0 |
  var car, mod, env, iEnv;
  iEnv = EnvGen.kr(
    Env.new(
      [index, index * iScale, index],
      [atk, rel],
      [cAtk, cRel]
    )
  );
  env = EnvGen.kr(
    Env.perc(atk, rel, curve:[cAtk, cRel]),
    doneAction: 2
  );
  mod = SinOsc.ar(freq * mRatio, mul:freq * mRatio * iEnv);
  car = SinOsc.ar(freq * cRatio + mod) * env * amp;
  car = Pan2.ar(car, pan);
  Out.ar(0, car);
}).add;
)

// Example

(
Synth(\fm, [
  \freq, 36.midicps,
  \atk, 0.3,
  \rel, 2,
  \index, 11,
  \iScale, 0.05,
  \mRatio, 0.4
])
)
