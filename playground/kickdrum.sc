// Steps for creating a new instrument

// 1. Start with a SynthDef

(
SynthDef('kickdrum', {
  // Define its arguments (| is the same as `arg`)
  | hz=60, hitHz=1500, hitDur=0.02 |
  // Define variables
  var env, sig, hit;
  // Develop the signal
  sig = {SinOsc.ar(hz)};
  sig = sig * {Line.ar(1, 0, 1, doneAction: 2)};
  hit = {LPF.ar(WhiteNoise.ar(1), hitHz)};
  hit = hit * {Line.ar(1, 0, hitDur)};
  sig = sig + hit;

  // Prepare the signal
  sig = Splay.ar(sig) * 0.4;
  // Output the signal
  Out.ar(0, sig);
}).add;
)

// 2. Play the SynthDef with default arguments

Synth(\kickdrum);

// 3. Tweak various arguments

(
Synth(\kickdrum, [
  \hz, 30.midicps, // A: 21, 33, 45=110, 57, 69, 81, 93
  \hitHZ, 900,
  \hitDur, 0.025
]);
)

// 4. Play it with random starting arguments
(
  ~mp = Scale.minorPentatonic;
  Synth(\kickdrum, [
    \hz, ~mp.degreeToFreq(~mp.degrees.choose, 33.midicps, 0),
    \hitHZ, exprand(800, 1600),
    \hitDur, exprand(0.01, 0.3)
  ]);
)

// 5. Play it on a tempo
(
t = TempoClock(120/60);
p = Pbindef(\kickdef,
    \instrument, \kickdrum
  ).play(t, quant:4);
)
p.stop;


s.freeAll;
