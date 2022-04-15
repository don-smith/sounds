// I don't remember where I found this,
// but I'll add a link here when I do.
SynthDef(\kick, {
  arg freq=52, hitHz=1000, hitDur=0.02,
    len=1, out=0, amp=1;
  var env, sig, hit;
  sig = {SinOsc.ar(freq)};
  sig = sig * {Line.ar(1, 0, len, doneAction:2)};
  hit = {LPF.ar(WhiteNoise.ar(1), hitHz)};
  hit = hit * {Line.ar(1, 0, hitDur)};
  sig = sig + hit;
  sig = LeakDC.ar(sig);
  sig = Splay.ar(sig) * amp;
  Out.ar(out, sig);
}).add;
