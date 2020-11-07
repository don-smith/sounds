"./bpfbuf.sc".loadRelative;

(
  ~sample = thisProcess.nowExecutingPath.dirname +/+ "s16.wav";
  b = Buffer.read(s, ~sample);
)
(
  ~horrorpads = Pbind(
    \instrument, \bpfbuf,
    \buf, b,
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
    \amp, 25
  )
)

~horrorpads.play();
