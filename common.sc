// :SCNvimStart
// :SCNvimStop

s.options.numOutputBusChannels = 4;
s.options.memSize_(2.pow(20));

s.boot;
s.reboot;
s.quit;

currentEnvironment;
p = ProxySpace.push(s);
p.makeTempoClock;
p.clock.tempo = 120/60;
p.quant = 4;

FreqScope();
s.plotTree;
s.meter;
s.scope;

s.record;
s.stopRecording;

s.freeAll;
