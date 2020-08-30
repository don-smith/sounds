// :SClangStart

s.options.numOutputBusChannels = 4;
s.options.memSize_(2.pow(20));

s.boot;

p = ProxySpace.push(s);
p.makeTempoClock;
p.clock.tempo = 120/60;
p.quant = 1;
