// buffers are server-side objects

var file = "/home/don/Samples/Cymatics/Samples/Cymatics\ -\ Beatbox\ Samples\ and\ Loops/Loops/Cymatics\ -\ Beatbox\ Loop\ 10\ -\ 100\ BPM.wav";
~b0 = Buffer.read(s, file);

~b0.numFrames;
~b0.numChannels;
~b0.sampleRate;
~b0.query;

~b0.play;

~effects = Array.new;
~folder = PathName.new("/home/don/Samples/Cymatics/Samples/Cymatics - FX Toolkit Vol 1/Misc FX");
~folder.entries.do({ |path|
    ~effects = ~effects.add(Buffer.read(s, path.fullPath));
});
~effects[1].play;

s.plotTree;
s.freeAll;