"./scgazer.sc".loadRelative;

(
  ~sample = thisProcess.nowExecutingPath.dirname +/+ "akwf.wav";
  f = SoundFile.openRead(~sample);
  f.numFrames;

  a = FloatArray.newClear(f.numFrames);
  f.readData(a);
  f.close; // close the file

  // Verify the file was loaded
  a.size;
  a = a.resamp1(1024);

  // Conver it to a Signal
  a = a.as(Signal);
  a.size; // 1024

  // Convert it to a Wavetable
  a = a.asWavetable;
  a.size; // 2048: wavetable format is signal.size * 2

  ~buf = Buffer.alloc(s, 2048);
  ~buf.loadCollection(a);
)

(
  Synth(\scgazer, [
    \buf, ~buf,
    \freq, 100,
    \dur, 1
  ]);
)
