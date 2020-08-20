// Frequency Modulation

//initialization
t = TempoClock(90/60).permanent_(true);
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
SynthDef(\fm1, {
  | carHz=500, modHz=100, modAmp=200,
    atk=0.01, rel=1, amp=0.2, pan=0, rout=0 |
  var car, mod, env;
  env = EnvGen.kr(Env.perc(atk, rel), doneAction: 2);
  mod = SinOsc.ar(modHz, mul:modAmp);
  car = SinOsc.ar(carHz + mod) * env * amp;
  car = Pan2.ar(car, pan);
  Out.ar(0, car);
  Out.ar(rout, car);
}).add;
)

(
Pbindef(\fmp1,
  \instrument, \fm1,
  \dur, Pexprand(3, 7),
  \carHz, Pexprand(20, 800),
  \modHz, Pexprand(20, 800),
  \modAmp, Pwhite(0, 10000),
  \amp, Pexprand(0.1, 0.5),
  \atk, Pexprand(0.5, 2),
  \rel, Pexprand(5, 18),
  \pan, Pwhite(-1.0, 1.0),
  \rout, ~rbus
).play(t, quant:1);
)

(
Synth(\fm1, [
  \carHz, exprand(20, 10000),
  \modHz, exprand(20, 10000),
  \modAmp, rrand(1000, 5000),
  \amp, exprand(0.1, 0.5),
  \atk, exprand(0.1, 0.5),
  \rel, exprand(5, 8),
  \pan, rrand(-1.0, 1.0),
  \rout, ~rbus
]);
)

Pbindef(\fmp1).stop;
