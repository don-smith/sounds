// Playing with envelopes

s.boot;
s.quit;

s.scope;
FreqScope.new;

(
  Env.new(
    [0,1,0],
    [0.1,1.2],
    \sin
  ).plot;
)

(
  SynthDef(\tone, {
    arg freq=300, amp=0.3, numSegs=4;
    var sig, env, noise;
    sig = SinOsc.ar(300);
    env = EnvGen.kr(Env.new(
      [0,1,0],
      [0.1,1.2],
      \sin
    ), doneAction:2);
    sig = sig * env;
    sig = sig * amp;
    sig = Splay.ar(sig) * 0.5;
    Out.ar(0, sig);
  }).add;
)

x = Synth(\tone, [\freq, 32.midicps]);

