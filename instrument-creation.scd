s.boot;

// Steps for creating a new instrument

// 1. Start with a SynthDef

(
SynthDef(\sineTest, {
  // Define its arguments
  arg fund=110, maxPartials=6, freqHz=4, ampHz=12, atk=0, sus=2, rel=3;
  // Define variables
  var freq, sig, amp, env;
  // Develop the signal
  freq = LFNoise0.kr(freqHz).exprange(fund, fund*maxPartials).round(fund);
  env = EnvGen.kr(Env([0,1,1,0], [atk, sus, rel], [0,0,1]), doneAction:2);
  amp = LFNoise0.kr(ampHz).exprange(0.2, 0.3);
  sig = SinOsc.ar(freq, mul:amp);
  sig = sig * env;
  // Prepare the signal
  sig = Splay.ar(sig) * 0.6;
  sig = FreeVerb.ar(sig, 0.7, 0.8, 0.25);
  // Output the signal
  Out.ar(0, sig);
}).add; // add the SynthDef to the server
)

// 2. Play the SynthDef with default arguments

x = Synth(\sineTest);

// 3. Tweak various arguments

x.set(\fund, 33.midicps); // A: 21, 33, 45=110, 57, 69, 81, 93
x.set(\maxPartials, 10);
x.set(\freqHz, 6);
x.set(\ampHz, 8);
x.free;

// 4. Play it with random starting arguments
(
  ~mp = Scale.minorPentatonic;
  x = Synth(\sineTest, [
    \fund, ~mp.degreeToFreq(~mp.degrees.choose, 33.midicps, [0,1,2,3].choose),
    \maxPartials, exprand(2, 10),
    \freqHz, exprand(2, 6),
    \ampHz, exprand(2, 10)
  ]);
)

// 5. Play it on a tempo
(
  t = TempoClock(20/60);
  p = Pbind(
    \instrument, \sineTest,
  ).play(t, quant:4);
)
p.stop;



s.freeAll;
