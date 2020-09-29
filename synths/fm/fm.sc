SynthDef(\fm, {
  arg freq=500, mRatio=1, cRatio=1, index=1, iScale=5,
    atk=0.01, rel=3, amp=0.2, cAtk=4, cRel=(-4), pan=0, out=0;
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
  car = PanAz.ar(4, car, pan);
  Out.ar(out, car);
}).add;
