SynthDef(\hat, {
  arg freq=6000, lpCut=2000, len=0.3, out=0, amp=1;
  var sig, noise;
  noise = {LPF.ar(WhiteNoise.ar(1),freq)};
  sig = {HPF.ar(noise, lpCut)};
  sig = sig * {Line.ar(1, 0, len, doneAction:2)};
  sig = LeakDC.ar(sig);
  sig = Splay.ar(sig * amp);
  Out.ar(out, sig);
}).add;

/*
* lpCut: low pass cutoff
* len: length duration of the sound
*/
