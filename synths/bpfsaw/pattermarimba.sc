
"./bpfsaw2.sc".loadRelative;
"../../fx/oneverb2.sc".loadRelative;

s.newBusAllocators;

(
  ~m1rvb = Bus.audio(s,2);
  ~m1rvbSynth = Synth(\oneverb, [\in, ~m1rvb]);

  // Marimba
  Pbindef(\m1,
    \instrument, \bpfsaw,
    \dur, Pexprand(0.1,1,inf),
    \freq, Pexprand(0.25,9,inf),
    \detune, 0,
    \rqmin, 0.005,
    \rqmax, 0.008,
    \cfmin, 150,
    \cfmax, 1500,
    \amp, 0.7
  )
)

(
  Pbindef(\m2,
    \instrument, \bpfsaw
  )
)

Pbindef(\m1, \out, 0, \amp, 0.7).play
Pbindef(\m1, \out, ~m1rvb, \amp, 7)
Pbindef(\m1, \freq, 0.25)
Pbindef(\m1, \dur, 0.5)
Pbindef(\m1, \cfmin, 150)
Pbindef(\m1, \cfmax, 2500)
Pbindef(\m1, \cfhzmin, 0.01)
Pbindef(\m1, \cfhzmax, 0.02)
Pbindef(\m1, \rqmax, 0.005)
Pbindef(\m1, \detune, 0)
Pbindef(\m1).stop

(
  Pbindef(\m2,
    \instrument, \bpfsaw
  )
)

Pbindef(\m2, \out, 0, \amp, 0.3).play
Pbindef(\m2, \out, ~m1rvb, \amp, 7)
Pbindef(\m2, \dur, 0.5)
Pbindef(\m2).stop
