s.freeAll;
s.newBusAllocators

// This is intended to be a mostly chill track
t = TempoClock(126/60)

// We're going to ease into things with a synth that can create
// a wide variety of sounds, a band-pass filtered saw wave
"../../synths/bpfsaw/bpfsaw2.sc".loadRelative

// To start, looking for some pseudo random tones in the mid-range
(
  Pbindef(\tone,
      \instrument, \bpfsaw,
      // no particular key ... yet
      \midinote, Prand((55..74), inf),
      \harmonic, 0, // just the fundamental for now
      \detune, 0,   // no detuning for now
      // no movement of the center freq ... yet
      \cfmin, Pkey(\midinote).midicps,
      \cfmax, Pkey(\midinote).midicps,
      // no resonance movement ... yet
      \rqmin, 0.1,
      \rqmax, 0.1,
      \atk, 0.1,
      \rel, 2,
      \amp, 2,
      \out, 0,
      \dur, 2
  ).play(t, quant:4)
)

// Let's run this through a reverb synth bus
~oneverbus = Bus.audio(s, 2)
"../../fx/oneverb2.sc".loadRelative
~oneverb = Synth(\oneverb, [\in, ~oneverbus])
Pbindef(\tone, \out, ~oneverbus)
// Turn up the mix and volume of the reverb
~oneverb.set(\mix, 0.75, \amp, 3)
// ~oneverb.free

// Replace this one-synth-per-beat (default) pattern
// with some additional irregularity and space
Pbindef(\tone, \dur, Pexprand(0.5,8,inf))

// Now something for the background
(
  Pbindef(\pads,
    \instrument, \bpfsaw,
    \dur, Pwhite(4.5,7.0,inf),
    \midinote, Pxrand([
      [23,35,54,63,64],    // B0, B1, F#3/Gb3, D#4/Eb4, E4
      [45,52,54,59,61,64], // A2, E3, F#3/Gb3, B3, C#4/Db4, E4
      [28,40,47,56,59,63], // E1, E2, B2, G#3/Ab3, B3, D#4/Eb4
      [42,52,57,61,63],    // F#2/Gb2, E3, A3, C#4/Db4, D#4/Eb4
    ], inf),
    \detune, Pexprand(0.05,0.2,inf),
    \cfmin, 100,
    \cfmax, 1500,
    \rqmin, Pexprand(0.01,0.15,inf),
    \atk, Pwhite(2.0,2.5,inf),
    \rel, Pwhite(6.5,10.0,inf),
    \ldb, 6,
    \amp, 0.08
  ).play(t, quant:4)
)

// Alter the pad's texture
Pbindef(\pads, \harmonic, 5) // 3 is also good
// Make some bubbling sounds ... by adding small harmonics?!
Pbindef(\pads, \harmonic, 0.01) // is that rad or what?!
// I know, RIGHT?!
// Bring the normal pads back when you're ready
Pbindef(\pads, \harmonic, 1)
// And just in case you need it ;)
Pbindef(\pads).clear

(
  Pbindef(\drops,
    \instrument, \bpfsaw,
    \dur, Pwhite(4.5,7.0,inf),
    \midinote, Pxrand([
      23,35,54,63,64,
      45,52,54,59,61,64,
      28,40,47,56,59,63,
      42,52,57,61,63,
    ], inf),
    \ctranspose, 12,
    \detune, Pexprand(0.05,0.1,inf),
    \cfmin, Pkey(\midinote).midicps,
    \cfmax, Pkey(\midinote).midicps,
    \rqmin, Pexprand(0.01,0.15,inf),
    \atk, Pwhite(0.001,0.01,inf),
    \rel, Pwhite(0.5,2,inf),
    \ldb, 6,
    \amp, 0.3
  ).play(t, quant:4)
)

Pbindef(\drops, \ctranspose, 24)
Pbindef(\drops, \amp, 0.4, \out, ~oneverbus)

// Bring some rhythm back into this swirl of sounds
// by locking the tones back into at least an 1/8 note
Pbindef(\tone, \dur, Pwrand(4/[2, 1, 1/2, 1/4, 1/8], [5,10,40,20,25], inf))
Pbindef(\drops, \dur, Pwrand(4/[2, 1, 1/2, 1/4, 1/8], [5,10,30,30,25], inf))

// Let's add another rhythmic part
"../../synths/noise/noise2.sc".loadRelative

(
  Pbindef(\hat,
    \instrument, \noise,
    \freq, 4000,
    \amp, Pbjorklund(5,8,inf) * 0.01,
    \dur, Pseq((0.25,0.5..1), inf),
    \out, 0,
  ).play(t, quant:4)
)

Pbindef(\hat, \dur, Pxrand(2/(0.25,0.5..1),inf))
Pbindef(\hat, \dur, Pbjorklund(4,1,inf))
Pbindef(\hat, \out, ~oneverbus)
Pbindef(\hat, \out, 0)
Pbindef(\hat, \amp, Pbjorklund(5,8,inf) * 0.1)
Pbindef(\hat, \freq, 400)
Pbindef(\hat, \lpCut, 800)
Pbindef(\hat, \midinote, Pexprand(30, 127).round(60))
Pbindef(\hat, \amp, Pbjorklund(4,8,inf) * 0.3)

// This sounds like a good place to take a break
// and just appreciate how this is evolving :)

// On second listen, it would probably be a good
// idea to choose a hat sound that doesn't sound
// like a gunshot. Ugh! Higher pitches perhaps?
// Amplitude filtering?

Pbindef(\hat).stop
Pbindef(\drops).stop
Pbindef(\pads).stop
Pbindef(\tone).stop

(
  Pbindef(\bass,
      \instrument, \bpfsaw,
      \freq, Pseq([60, 80, 90, 180], inf),
      \detune, 0,
      \cfmin, 50,
      \cfmax, 50,
      \rqmin, 0.1,
      \rqmax, 0.1,
      \atk, 0.1,
      \rel, 0.7,
      \amp, 0.9,
      \dur, 2
  ).play(t, quant:4)
)
Pbindef(\bass).stop
