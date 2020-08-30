~k1 = Pbind(\instrument,\kick,\len,1,\dur,4,\amp,1,\freq,40);
~k1.quant = 4
~k1.play;
~k1.clear;

~k2 = Pbind(\instrument,\kick,\len,1,\dur,4,\amp,1,\freq,80);
~k2.quant = 4
~k2.play;
~k2.clear;

~h1 = Pbind(\instrument,\hat,\len,0.15,\dur,Pwrand([0.5,Pseq([0.25],2)],[6,1].normalizeSum,inf),\amp,Pwhite(0.2, 0.4),\freq,6000);
~h1.quant = 4
~h1.play;
~h1.clear;

//add a [0] to the name of the proxy
~k1[0] = Pbind(\instrument,\kick,\len,1,\dur,4,\amp,1,\freq,40);
//add some reverb
~k1[1] = \filterIn -> {|in| FreeVerb.ar(in)}
//re-evaluate 'index 0' of the NodeProxy, and effect will be kept
~k1[0] = Pbind(\instrument,\kick,\len,1,\dur,4,\amp,1,\freq,40);
//note that indexes can be re-evaluated on the fly, e.g. to change the values of an effect UGen or to add modulation
~k1[1] = \filterIn -> {|in| FreeVerb.ar(in,0.6,0.6,0.01)}
