( // With FreeVerb
  Ndef(\rev, {
    arg in=0, out=0, amp=1, mix=0.5, room=0.5, damp=0.5;
    var sig = FreeVerb.ar(In.ar(in,2), mix:mix, room:room, damp:damp);
    Out.ar(out, sig * amp);
  });
)

s.options.memSize_(65536 * 4);
s.reboot;
( // With JPverb
  Ndef(\rev, {
    arg t60=1, damp=0, size=1, earlyDiff=0.707, modDepth=0.1, modFreq=2;
    Out.ar(0, JPverb.ar(
      In.ar([0,1], 2), t60, damp, size, earlyDiff, modDepth, modFreq
    ));
  });
)

Ndef(\rev).set(\t60, 1.0);
Ndef(\rev).set(\damp, 0.0);
Ndef(\rev).set(\size, 1.0);
Ndef(\rev).set(\modDepth, 0.9);
Ndef(\rev).set(\modFreq, 5.0);
Ndef(\rev).set(\low, 1);
Ndef(\rev).set(\mid, 0.4);
Ndef(\rev).set(\high, 0.6);
Ndef(\rev).free;

Env([0,1,1,0], [0.01,0,0.01], [1,0,-1]).plot;

( // tone
  x = {
    arg freq, amp;
    var env, sig;
    sig = SinOsc.ar([freq,freq*1.01]);
    env = EnvGen.kr(Env(
      [0,1,1,0],    // segments
      [0.01,0,0.8], // durations
      [1,0,-1]      // curves
    ), doneAction:2);
    sig * env * amp;
  };
)

x.play(args: [\freq, 200, \amp, 0.2]);

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
    \amp, 0.5
  );
)

t = TempoClock(80/60);

Pbindef(\tonedef).play(t, quant:4);
Pbindef(\tonedef, \amp, 0.15);
Pbindef(\tonedef, \dur, 4.0);
Pbindef(\tonedef, \freq, Pseq(([60, 65, 67]+2).midicps, inf));
Pbindef(\tonedef).stop;
