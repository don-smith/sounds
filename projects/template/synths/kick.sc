(
  SynthDef(\kickdrum, {
    arg hz=60, hitHz=1500, hitDur=0.02, out=0;
    var env, sig, hit;
    sig = {SinOsc.ar(hz)};
    sig = sig * {Line.ar(1, 0, 1, doneAction: 2)};
    hit = {LPF.ar(WhiteNoise.ar(1), hitHz)};
    hit = hit * {Line.ar(1, 0, hitDur)};
    sig = sig + hit;
    sig = LeakDC.ar(sig);
    sig = Splay.ar(sig) * 0.4;
    Out.ar(out, sig);
  }).add;
)

// Example

(
  Pbindef(\kick,
    \instrument, \kickdrum,
    \hz, 30.midicps, // A: 21, 33, 45=110, 57, 69, 81, 93
    \hitHZ, 900,
    \hitDur, 0.025
  );
)

Pbindef(\kick).play;
Pbindef(\kick).stop;
