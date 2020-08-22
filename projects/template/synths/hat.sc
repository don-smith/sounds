(
  SynthDef(\hat, {
    arg freq=6000, lpCut=2000, dur=0.3, out=0;
    var sig, noise;
    noise = {LPF.ar(WhiteNoise.ar(1),freq)};
    sig = {HPF.ar(noise, lpCut)};
    sig = sig * {Line.ar(1, 0, dur)};
    sig = Splay.ar(sig) * 0.6;
    Out.ar(0, sig);
  }).add;
)

// Example

(
  Pbindef(\hats,
    \instrument, \hat,
    \dur, 1 // closer to a closed hat
  );
)

t = TempoClock.new(180/60).permanent_(true);

Pbindef(\hats).play(t, quant:1);
Pbindef(\hats).stop;
