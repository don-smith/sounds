s.options.sampleRate = 48000.0;
s.reboot;

(
  "../../synths/gs.sc".loadRelative;
  "../../synths/bpfbuf.sc".loadRelative;
  d = thisProcess.nowExecutingPath.dirname +/+ "samples";
  b = PathName(d.asAbsolutePath).entries.collect({ |sample|
    Buffer.read(s, sample.fullPath);
  });
  t = TempoClock(0.5);
)

(
  // sounds like a horror movie, ouch!
  ~horrorpads = Pbind(
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
~horrorpads.play();

(
r = ([0,2,3,7,9,10].choose + 50).midicps * [2,1,0.5,0.25].choose;
Synth(\gs, [
  \buf, b[[0,1,2,3,4,5,6,7,8,9].choose],
  \posSpd, 0.02,
  \rate, r,
  \dens, r * rrand(-0.15,0.15).midicps,
  \len, 0.09,
  \atk, rrand(3,5),
  \sus, 1,
  \rel, rrand(4,6),
  \pos, 0,//rrand(0, 0.3),
  \amp, 0.1
]);
)

~pads = NodeProxy(s, \audio, 2);
(
~pads[0] = Pbind(
  \instrument, \gs,
  \buf, Prand([b[0],b[1],b[2],b[3],b[4],b[5],b[6],b[7],b[8],b[9]],inf),
  \prerate, Prand(([0,2,3,7,9,10] + 57).midicps,inf),
  \rate, Pkey(\prerate) * Prand([2,1,0.5,0.25],inf),
  \dens, Pkey(\rate) * Pwhite(-0.15,0.15).midiratio,
  \posSpd, 0,
  \len, Pexprand(0.01, 0.08),
  \dur, Pexprand(1,3),
  \atk, Pexprand(1,4),
  \sus, Pexprand(2,4),
  \rel, Pexprand(3,6),
  \pos, Pexprand(0.01,0.8),
  \amp, 0.2
);
)
~pads[1] = \filterIn -> {|in| CombN.ar(in,2,8)}
~pads[2] = \filterIn -> {|in| FreeVerb.ar(in,0.7,0.9,0.2)}
~pads.set(\amp, 0.6);
~pads.fadeTime = 4;
~pads.play;

// ([0,2,3,7,9,10] + 70).midicps
// rrand(-0.15,0.15)

