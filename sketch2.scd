s.boot;
s.quit;
s.scope;
FreqScope.new;

//initialization
t = TempoClock.new(90/60).permanent_(true);
s.newBusAllocators;
~rbus = Bus.audio(s,2);
Synth(\reverb, [\in, ~rbus]);

(
SynthDef(\reverb, {
  arg in=0, out=0, dec=4, lpf=1500;
  var sig;
  sig = In.ar(in, 2).sum;
  sig = DelayN.ar(sig, 0.03, 0.03);
  sig = CombN.ar(sig, 0.1, {Rand(0.01,0.099)}!32, dec);
  sig = SplayAz.ar(2, sig);
  sig = LPF.ar(sig, lpf);
  5.do{sig = AllpassN.ar(sig, 0.1, {Rand(0.01,0.099)}!2, 3)};
  sig = LPF.ar(sig, lpf);
  sig = LeakDC.ar(sig);
  Out.ar(out, sig);
}).add;
)

(
SynthDef(\fm2, {
  | freq=500, mRatio=1, cRatio=1, index=1, iScale=5, rout=0,
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
  Out.ar(rout, car);
}).add;
)

(
Synth(\fm2, [
  \freq, 36.midicps,
  \atk, 2,
  \rel, 2,
  \cAtk, 2,
  \cRel, (-8),
  \index, 8,
  \iScale, 0.5,
  \mRatio, 0.5,
  \cRatio, 0.5
])
)

(
Pbindef( \fmp2,
  \instrument, \fm2,
  \freq, 36.midicps,
  \rel, 4,
  \index, 20,
  \iScale, 0.05,
  \mRatio, 0.5,
  \amp, Pexprand(0.01, 0.05),
  \atk, Pexprand(0.5, 2),
  \rel, Pexprand(5, 18),
  \pan, Pwhite(-1.0, 1.0),
  \rout, ~rbus
).play(t, quant:1);
)

Pbindef(\fmp2).stop;
