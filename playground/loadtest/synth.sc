SynthDef(\testsynth, {
  arg freq=300, amp=0.3, numSegs=4;
  var sig, env;
  sig = SinOsc.ar({Scale.minorPentatonic.degreeToFreq(Scale.minorPentatonic.degrees.choose, freq, 1)}!4);
  env = EnvGen.kr(Env.new(
    [0]++({ExpRand(0.1,0.9)}!7)++[0],
    {exprand(0.1,0.5)}!4,
    \sin), doneAction:2
  );
  sig = sig * env;
  sig = sig * amp;
  sig = Splay.ar(sig) * 0.5;
  sig = FreeVerb.ar(sig, 0.2);
  Out.ar(0, sig);
}).add;
