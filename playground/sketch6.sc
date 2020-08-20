(
  {
    var sig, sig1, sig2, pan;
    sig = SinOsc.ar(120!2, 0, 0.3);
    sig1 = sig + SinOsc.ar({Rand(185,197)}!2, 0, 0.3);
    sig2 = sig + SinOsc.ar({Rand(335,340)}!2, 0, 0.3);
    pan = FBSineC.ar(1, 2, 1.7); // panning movement - use wavetable instead
    sig = XFade2.ar(sig1, sig2, pan, {Rand(0.1,0.4)});
  }.play;
)

// With a 200hz carrier, these ranges
// sounded like good modulators:
// 185-197
// 209-219
// 262-280
// 335-340
