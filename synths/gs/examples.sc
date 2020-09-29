"./gs.sc".loadRelative;

(
  ~sample = thisProcess.nowExecutingPath.dirname +/+ "lunchtime_mall.wav";
  b = Buffer.readChannel(s, ~sample, channels:[0]);
)

b.play;
b.numFrames;
b.sampleRate;
b.numChannels;

(
  Synth(\gs, [\buf, b]);
)

(
  r = ([0,2,3,7,9,10].choose + 50).midicps * [2,1,0.5,0.25].choose;
  Synth(\gs, [
    \buf, b,
    \posSpd, 0.02,
    \rate, r,
    \dens, r * rrand(-0.15,0.15).midicps,
    \len, 0.09,
    \atk, rrand(3,5),
    \sus, 1,
    \rel, rrand(4,6),
    \pos, rrand(0, 0.8),
    \amp, 0.1
  ]);
)

(
  Synth(\gs, [
    \buf, b,
    \posSpd, 1.5,
    \rate, 18,
    \dens, 1.8,
    \len, 2.2,
    \atk, rrand(3,5),
    \sus, 1,
    \rel, rrand(4,6),
    \pos, rrand(0.5, 0.99),
    \amp, 0.1
  ]);
)
