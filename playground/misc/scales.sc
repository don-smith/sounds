Scale.directory;
Scale.minorPentatonic.degrees;

(
  SynthDef(\tone, {
    arg freq=300, amp=0.3, numSegs=4;
    var sig, env;
    amp = SinOsc.kr({ExpRand(2,4)}!4).range(0,1);
    sig = SinOsc.ar(freq, 1);
    env = EnvGen.kr(Env.new(
      [0]++({exprand(0.1,0.6)}!7)++[0],
      {exprand(0.1,0.5)}!8,
      \lin), doneAction:2
    );
    sig = sig * env;
    sig = sig * amp;
    sig = Splay.ar(sig) * 0.5;
    sig = FreeVerb.ar(sig, 0.1);
    Out.ar(0, sig);
  }).add;
)

(
  a = Scale.minorPentatonic;
  Pbindef(\a,
    \instrument, \tone,
    \scale, a,
    \degree, Pseq((0..7) ++ (6..0) ++ [\rest], 1), \dur, 0.25
  ).play;
)

(
  ~mp = Scale.minorPentatonic;
  Synth(\kick, [
    \freq, ~mp.degreeToFreq(~mp.degrees.choose, 33.midicps, 0),
    \hitHZ, exprand(800, 1600),
    \hitDur, exprand(0.01, 0.3)
  ]);
)
