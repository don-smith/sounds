s.boot;

// Steps for creating a new instrument

// 1. Start with a SynthDef

(
SynthDef(\hat, {
  // Define its arguments (| is the same as `arg`)
  | freq=6000, lpCut=2000, dur=0.3 | // 0.3 is closer to an open hat
  // Define variables
  var sig, noise;
  // Develop the signal
  noise = {LPF.ar(WhiteNoise.ar(1),freq)};
  sig = {HPF.ar(noise, lpCut)};
  sig = sig * {Line.ar(1, 0, dur)};

  // Prepare the signal
  sig = Splay.ar(sig) * 0.6;
  // Output the signal
  Out.ar(0, sig);
}).add; // add the SynthDef to the server
)

// 2. Play the SynthDef with default arguments

Synth(\hat);

// 3. Tweak various arguments

(
Synth(\hat, [
  \dur, 0.1 // closer to a closed hat
]).play;
)

// 4. Play it with random starting arguments
(
  Synth(\hat, [
    \dur, exprand(0.05, 0.5),
    \freq, exprand(4000, 10000),
    \lpCut, exprand(1500, 4000)
  ]);
)

// 5. Play it on a tempo
t = TempoClock(90/60).permanent_(true);


s.freeAll;
