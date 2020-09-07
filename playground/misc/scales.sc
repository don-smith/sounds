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

// To choose a random note within a scale (say, minor),
// based on a root note/fundamental frequency (say, middle C, midi note 60),
// it's frequency can be calculated by doing this:
(Scale.minor.degrees+60).midicps.choose;
// To avoid duplicates:

(
// expects to be in a ProxySpace
d=();
p.clock.tempo = 1;
d[\scale] = Scale.choose.postln;
~sinfb.xset(\scale,d[\scale]);
~sinfb = Pbind(\instrument,\sinfb,\scale,d[\scale],\octave,4,\degree,Pseq((0..d[\scale].degrees.size-1),inf),\dur,0.25,\amp,0.3,\fb,0.6,\rel,0.3);
~sinfb.play;
)

(
// expects to be in a ProxySpace
~sinfb = Pbind(
  \instrument,\sinfb,
  \scale,Scale.chromatic,
  \root,0,
  \octave,4,
  \degree,Pseq([\Em7,\G,\Dsus4,\A7sus4].chordProg,inf).stutter(6),
  \dur,1,
  \atk,0.8,\sus,0,\rel,1,\amp,0.3,\fb,0.1
);
)
