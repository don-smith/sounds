// Adding and multiplying SinOsc UGens
(
  SynthDef(\sound, {
    arg freq=300, amp=0.3, numSegs=4;
    var sig, sig1, sig2, sig3, env, noise;
    sig1 = SinOsc.ar(300);
    sig2 = SinOsc.ar(800);
    sig3 = SinOsc.ar(1100);
    // sig = sig2 * amp;
    // sig = (sig1 * sig2) * amp;
    sig = (sig1 + sig2) * amp;
    // sig = sig3 * amp;
    sig = Splay.ar(sig) * 0.5;
    // sig = FreeVerb.ar(sig, 0.3);
    Out.ar(0, sig);
  }).add;
)

x = Synth(\sound, [\freq, 32.midicps]);
x.free;
