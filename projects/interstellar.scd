// I think it would be super cool (and I think I'l,d learn a lot) to
// remake https://www.youtube.com/watch?v=1ISgr-2qdK0 in Supercollider.
// Maybe this will be an attempt ¯\_(ツ)_/¯
//
// nicolaariutti, thank you for https://sccode.org/1-5ay
// It's a very nice sounding synth. Thank you for sharing it with us.

( // 1. define the synth
SynthDef(\bell, {
  |fs=1, t60=1, pitchy=1, amp=0.25, gate=1, out=0|
  var sig, exciter;
  exciter = Impulse.ar(0);
  // exciter = WhiteNoise.ar() * EnvGen.ar(Env.perc(0.001, 0.05), gate) * 0.25;
  sig = Klank.ar(
    `[
      [1, 2, 2.803, 3.871, 5.074, 7.81, 10.948, 14.421],   // freqs
      [1, 0.044, 0.891, 0.0891, 0.794, 0.1, 0.281, 0.079], // amplitudes
      [1, 0.205, 1, 0.196, 0.339, 0.047, 0.058, 0.047]*t60 // ring times
    ],
    exciter,
    freqscale:fs*pitchy);
  sig = FreeVerb.ar(sig) * amp;
  DetectSilence.ar(sig, 0.001, 0.5, doneAction:2);
  Out.ar(out, sig!2);
}).add
)


// 2. Test a single note
Synth(\bell, [\fs, 60.midicps, \t60, 9.177, \pitchy, 2]);

// 3. Add some reverb

s.newBusAllocators
~freeverbus = Bus.audio(s,2)
( // with FreeVerb
  Ndef(\freeverb, {
    arg in=0, out=0, amp=1, mix=0.5, room=0.5, damp=0.5;
    var sig = FreeVerb.ar(In.ar(in,2), mix:mix, room:room, damp:damp);
    Out.ar(out, sig * amp);
  });
)
Ndef(\freeverb).set(\in, ~freeverbus)
Ndef(\freeverb).set(\size, 10)
Ndef(\freeverb).set(\mix, 0.8, \amp, 1.0)
Ndef(\freeverb).set(\out, 0)
Ndef(\freeverb).free

~jpverbus = Bus.audio(s,2)
s.options.memSize_(65536 * 4);
s.reboot;
( // with JPverb
  Ndef(\jpverb, {
    arg in=0, t60=1, damp=0, size=1, earlyDiff=0.707, modDepth=0.1, modFreq=2;
    Out.ar(0, JPverb.ar(
      In.ar(in, 2), t60, damp, size, earlyDiff, modDepth, modFreq
    ));
  });
)
Ndef(\jpverb).set(\in, ~jpverbus)
Ndef(\jpverb).set(\t60, 0.05)
Ndef(\jpverb).set(\modFreq, 2)
Ndef(\jpverb).set(\modDepth, 0.1)
Ndef(\jpverb).set(\size, 2)
Ndef(\jpverb).set(\damp, 0.8)
Ndef(\jpverb).free

"../fx/oneverb2.sc".loadRelative;
~oneverbus = Bus.audio(s,2)
~oneverb = Synth(\oneverb, [\in, ~oneverbus])
~oneverb.set(\mix, 0.8, \amp, 0.3)

"../fx/twoverb2.sc".loadRelative;
~twoverbus = Bus.audio(s,2)
~twoverb = Synth(\twoverb, [\in, ~twoverbus])
~twoverb.set(\lpf, 500)
~twoverb.set(\dec, 0.1)

( // marimba m1
Pbindef(\m1,
  \instrument, \bell,
  \fs, Pseq([65, 61], inf).midicps,
  \t60, 1.1,
  \pitchy, 3,
  \dur, 0.5,
  \amp, 0.3
))

t = TempoClock(98/60)
Pbindef(\m1).play(t, quant:4)
Pbindef(\m1).stop

Pbindef(\m1, \out, ~freeverbus, \amp, 0.8)
Pbindef(\m1, \out, ~jpverbus, \amp, 0.5)
Pbindef(\m1, \out, ~oneverbus, \amp, 2)
Pbindef(\m1, \out, ~twoverbus, \amp, 2) // not so much
Pbindef(\m1, \out, 0, \amp, 0.5)
Pbindef(\m1).stop

Pbindef(\m1, \fs, Pseq([45, 41], 8).midicps)
Pbindef(\m1, \fs, Pseq([46, 43], inf).midicps)
