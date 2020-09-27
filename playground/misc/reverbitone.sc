~reverbBus = Bus.audio(s, 2);

(
  r = {
    arg in=0, out=0, amp=1, mix=0.5, room=0.5, damp=0.5;
    var sig;
    sig = FreeVerb.ar(In.ar(in,2), mix:mix, room:room, damp:damp);
    Out.ar(out, sig * amp);
  }.play;
)

Env([0,1,1,0], [0.01,0,0.01], [1,0,-1]).plot;

( // tone
  x = {
    arg freq;
    var env, sig;
    sig = SinOsc.ar([freq,freq*1.01]);
    env = EnvGen.kr(Env(
      [0,1,1,0],    // segments
      [0.01,0,0.8], // durations
      [1,0,-1]      // curves
    ), doneAction:2);
    sig * env * 0.5;
  };
)

r.set(\in, ~reverbBus);
r.set(\damp, 0.1);

x.play(args: [\freq, 200], outbus: 0);
x.play(args: [\freq, 200], outbus: ~reverbBus);

(
  SynthDef(\tone, {
    arg freq, out=0, amp=1;
    var env, sig;
    sig = SinOsc.ar([freq,freq*1.01]);
    env = EnvGen.kr(Env(
      [0,1,1,0],    // segments
      [0.01,0,0.8], // durations
      [1,0,-1]      // curves
    ), doneAction:2);
    Out.ar(out, sig * env * amp)
  }).add;

  Pbindef(\tonedef,
    \instrument, \tone,
    \amp, 0.5,
    \out, ~reverbBus
  );
)

t = TempoClock(80/60);

Pbindef(\tonedef).play(t, quant:4);
Pbindef(\tonedef, \dur, 1.20);
Pbindef(\tonedef, \freq, 220);
Pbindef(\tonedef, \freq, Pseq(([60, 65, 67]+2).midicps, inf));
Pbindef(\tonedef).stop;
