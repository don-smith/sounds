// Here's the idea going into this instrument:
// Atmospheric pads that can go from a lushy, sinusiodal swirl
// with a subtle hush of hissing noise and occasional cracking and popping
// to an angry, annoyed, embarrassed, caccophany of layered noise and
// scratching. The angry part is a bit of a stretch, but some level of an
// adjustment interface is expected.

// I'll start with lushy sinusiodals. I'll start them with a couple of SinOscs
// using multichannel expansion and hopefully migrate to wavetable oscillation
// fairly quickly.

// I'd also like to document this process. I'll likely do this in comments.
// I'm interested in the process. For example: 1. Vauge idea of a sound or a
// sound type (click, hush, bass, whoop, tap, zip, sample, etc). 2. Work out
// sound with expected defaults and a sense of the ranges of the instrument.
// 3. Create a Pbindef that plays the instruments using the provided args in
// expected ways ... and perhaps some unexpected ways.

s.boot;
s.quit;
s.plotTree;

(
  Env.new(
    [0]++({exprand(0.1,0.9)}!7)++[0],
    {exprand(1,2.5)}!8,
    \sin
  ).plot;
)

(
  SynthDef(\pads, {
    arg freq=300, amp=0.3, numSegs=4;
    var sig, env;
    // amp = SinOsc.kr({ExpRand(2,4)}!4).range(0,1);
    sig = SinOsc.ar({Scale.minorPentatonic.degreeToFreq(Scale.minorPentatonic.degrees.choose, freq, 1)}!4);
    env = EnvGen.kr(Env.new(
      [0]++({exprand(0.1,1.6)}!7)++[0],
      {exprand(1,2.5)}!8,
      \sin), doneAction:2
    );
    sig = sig * env;
    sig = sig * amp;
    sig = Splay.ar(sig) * 0.5;
    sig = FreeVerb.ar(sig, 0.3);
    Out.ar(0, sig);
  }).add;
)

x = Synth(\pads);
x.set(\freq, 52.midicps);
x = Synth(\pads, [\freq, 32.midicps]);

// These aren't relevant with a doneAction of 2
x.play;
x.stop;
x.free;
