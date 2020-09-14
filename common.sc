// :SCNvimStart
// :SCNvimStop

s.options.numOutputBusChannels = 4;
s.options.memSize_(2.pow(20));

s.boot;
s.reboot;
s.quit;

currentEnvironment;
p = ProxySpace.push(s);
p.reshaping = \elastic;
p.makeTempoClock;
p.clock.tempo = 40/60;
p.fadeTime = 3;
p.quant = 1;

FreqScope();
s.plotTree;
s.meter;
s.scope;

s.record;
s.stopRecording;

s.freeAll;
