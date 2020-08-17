s.meter;

x = {PinkNoise.ar(0.5)!2}.play;
x = {PinkNoise.ar(0.5!2)}.play;
x.free;

x = {VarSaw.ar(40!2, 0, 0.05)}.play;
x.free;