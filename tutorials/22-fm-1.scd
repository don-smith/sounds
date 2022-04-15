s.boot;
s.quit;
s.scope;
FreqScope.new;

// 500 is the carrier freq
// the sine wave we're adding is the modulator freq
// the 400 amplitude is the intensity of the modulation
// which causes the output to fluctuate between 100 Hz to 900 Hz
{SinOsc.ar(500 + SinOsc.ar(1, mul:400)) * 0.2!2}.play;

{SinOsc.ar(500 + SinOsc.ar(MouseX.kr(1,2000,1).poll, mul:400)) * 0.2!2}.play;

(
SynthDef(\fm, {
  | carHz=500, modHz=100, modAmp=200,
    atk=0.01, rel=1, amp=0.2, pan=0 |
  var car, mod, env;
  env = EnvGen.kr(Env.perc(atk, rel), doneAction: 2);
  mod = SinOsc.ar(modHz, mul:modAmp);
  car = SinOsc.ar(carHz + mod) * env * amp;
  car = Pan2.ar(car, pan);
  Out.ar(0, car);
}).add;
)

(
Synth(\fm, [
  \carHz, exprand(20, 10000),
  \modHz, exprand(20, 10000),
  \modAmp, rrand(0, 10000),
  \amp, exprand(0.1, 0.3),
  \atk, exprand(0.001, 0.05),
  \rel, exprand(0.05, 1.2),
  \pan, rrand(-1.0, 1.0),
]);
)

(
p = Pbind(
  \instrument, \fm,
  \dur, 1/8,
  \carHz, Pexprand(20, 10000),
  \modHz, Pexprand(20, 10000),
  \modAmp, Pwhite(0, 10000),
  \amp, Pexprand(0.1, 0.5),
  \atk, Pexprand(0.001, 0.05),
  \rel, Pexprand(0.05, 1.2),
  \pan, Pwhite(-1.0, 1.0),
).play;
)

p.stop;
