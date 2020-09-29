~verBus = Bus.audio(s, 2);

(
  r = {
    arg in=0, out=0, amp=1, mix=0.5, room=0.5, damp=0.5;
    var sig;
    sig = FreeVerb.ar(In.ar(in,2), mix:mix, room:room, damp:damp);
    Out.ar(out, sig * amp);
  }.play;
)

r.set(\in, ~verBus);
r.set(\damp, 0.1);
r.free;

(
  // Env([0,1,1,0], [0.01,0,0.01], [1,0,-1]).plot;
  // still a work in progress ... for learning
  SynthDef(\pad, {
    arg freq=200, out=0, amp=1, atk=1, sus=0, rel=1;
    var env, sig, freqs;
    freqs = Array.exprand(5, freq, freq*2);
    sig = SinOsc.ar(freqs);
    env = EnvGen.kr(Env(
      [0,1,1,0],     // segments
      [atk,sus,rel], // durations
      [1,0,-1]       // curves
    ), doneAction:2);
    Out.ar(out, sig * env * amp);
  }).add;

  Pbindef(\padef,
    \instrument, \pad,
    \out, ~verBus,
    // \atk, Pexprand(4,6),
    // \rel, Pexprand(8,12),
    \atk, Pexprand(2,4),
    \rel, Pexprand(6,8),
    \amp, 0.3,
  );
)

t = TempoClock(80/60);

Pbindef(\padef).play(t, quant:4);
Pbindef(\padef, \dur, Pexprand(1.6, 4));
Pbindef(\padef, \amp, 0.2);
Pbindef(\padef, \freq, Pseq(([60, 65, 67]+2).midicps, inf));
Pbindef(\padef).stop;
