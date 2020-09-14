("../../synths/kick.sc").loadRelative;
("../../synths/hat.sc").loadRelative;

~k1 = Pbind(\instrument,\kick,\len,1,\dur,4,\amp,1,\freq,40);
~k1.quant = 4
~k1.play;
~k1.clear;

~h1 = Pbind(\instrument,\hat,\len,0.15,\dur,Pwrand([0.5,Pseq([0.25],2)],[6,1].normalizeSum,inf),\amp,Pwhite(0.2, 0.4),\freq,6000);
~h1.quant = 4
~h1.play;
~h1.clear;

~k2 = NodeProxy(s, \audio, 2);
~k2[0] = Pbind(\instrument,\kick,\len,1,\dur,4,\amp,1,\freq,80);

~k2.quant = 1
~k2.play;

// add some reverb
~k2[1] = \filterIn -> {|in| FreeVerb.ar(in)}
~k2.set(\wet1, 0.8);
// re-evaluate on the fly to change the
// values of an effect UGen or to add modulation
~k2[1] = \filterIn -> {|in| FreeVerb.ar(in,0.6,0.6,0.01)}

~k2.end;
