s.options.sampleRate = 48000.0;
s.reboot;

(
  "../../synths/bpfbuf.sc".loadRelative;
  d = thisProcess.nowExecutingPath.dirname +/+ "samples";
  b = PathName(d.asAbsolutePath).entries.collect({ |sample|
    Buffer.read(s, sample.fullPath);
  });
  t = TempoClock(0.5);
)

(
  ~pads = Pbind(
    \instrument, \bpfbuf,
    \buf, b[15],
    \midinote, Prand(Scale.minor.degrees+92),
    \scale, Scale.minor,
    \octave, Prand([3,4,5]),
    \atk, Pexprand(4,6),
    \sus, 6,
    \rel, Pexprand(6,8),
    \bpfmix, 1,
    \dur, 4,
    \rate, Pexprand(0.1,0.4),
    \pan, Prand([-0.5,0.5]),
    \amp, 7
  )
)
~pads.play();

(
Synth(\bpfbuf, [
  \buf, b,
  \sus, 10,
  \spos, 400000
]);
)
