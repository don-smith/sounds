//up-down riff
//minor scale version
//re-evaluate individual directions to create a different riff
(
p.clock.tempo = 1.5;
//up
~sinfb1 = Pbind(\instrument,\sinfb,\scale,Scale.minor,\octave,5,\degree,Pseq((0..7),inf),\dur,0.25,\amp,0.3,\fb,Pwhite(0.1,0.4),\rel,0.2);
~sinfb1.play;
)
(
//down
~sinfb2 = Pbind(\instrument,\sinfb,\scale,Scale.minor,\octave,5,\degree,Pseq((0..7).reverse,inf),\dur,0.25,\amp,0.3,\fb,Pwhite(0.1,0.4),\rel,0.2);
~sinfb2.play;
)
(
//random, an octave higher
~sinfb3 = Pbind(\instrument,\sinfb,\scale,Scale.minor,\octave,6,\degree,Pseq((0..7).scramble,inf),\dur,0.25,\amp,0.3,\fb,Pwhite(0.1,1.0),\rel,0.2);
~sinfb3.play;
)

//replacing duration of 0.25 with a Pwrand which will automatically shift the riffs
(
p.clock.tempo = 1.5;
~sinfb1 = Pbind(\instrument,\sinfb,\scale,Scale.minor,\octave,5,\degree,Pseq((0..7),inf),\dur,Pwrand([0.25,Pseq([0.125],2)],[0.9,0.1],inf),\amp,0.3,\fb,Pwhite(0.1,0.4),\rel,0.2);
~sinfb2 = Pbind(\instrument,\sinfb,\scale,Scale.minor,\octave,5,\degree,Pseq((0..7).reverse,inf),\dur,Pwrand([0.25,Pseq([0.125],2)],[0.9,0.1],inf),\amp,0.3,\fb,Pwhite(0.1,0.4),\rel,0.2);
~sinfb3 = Pbind(\instrument,\sinfb,\scale,Scale.minor,\octave,5,\degree,Pseq((0..7).scramble,inf),\dur,Pwrand([0.25,Pseq([0.125],2)],[0.9,0.1],inf),\amp,0.3,\fb,Pwhite(0.1,1.4),\rel,0.2);
)
~sinfb1.play;
~sinfb2.play;
~sinfb3.play;


(
SynthDef(\ring1,
	{
		arg f = 45, a = 9, d = 0.6, pan = 0, amp = 1, out = 0;
		var sig, env;
		env = Line.kr(0,1,d);
		env = FreeSelfWhenDone.kr(env);
		sig = Impulse.ar(0);
		sig = Ringz.ar(sig,f,d,a);
		Out.ar(out,Pan2.ar(sig.tanh,pan) * amp)
	};
).add;
)

//Place - riffs that contain riffs
(
//first riff
~ring1 = Pbind(\instrument,\ring1,\f,Pkey(\freq),\scale,Scale.minor,\degree,Place([0,7],inf),\octave,3,\dur,0.25,\d,0.6,\a,Pseq((1..40),inf),\pan,0,\amp,0.2);
)
~ring1.play;
~ring1.end(4);

(
//second riff
~ring1 = Pbind(\instrument,\ring1,\f,Pkey(\freq),\scale,Scale.minor,\degree,Place([2,4,3,5,4,6,8,11],inf),\octave,3,\dur,0.25,\d,0.6,\a,Pseq((1..40),inf),\pan,0,\amp,0.2);
)
~ring1.play;
~ring1.end(4);

(
//two riffs laced together with the longer one on the inner level, playing the first riff and then a note of the second
~ring1 = Pbind(\instrument,\ring1,\f,Pkey(\freq),\scale,Scale.minor,\degree,Place([0,7,[2,4,3,5,4,6,8,11]],inf),\octave,3,\dur,0.25,\d,0.6,\a,Pseq((1..40),inf),\pan,0,\amp,0.2);
)
~ring1.play;
~ring1.end(4);

(
~sinfb = Pbind(\instrument,\sinfb,\scale,Scale.minor,\octave,[3,4,5],\degree,Pseq([0,0,4,5],inf),\dur,Pbjorklund2(3,8)/4,\amp,0.3,\fb,0.1,\rel,0.3);
~feedback = {SinOsc.kr(0.1,-1,1).range(1,8.0).poll(5)};
~sinfb.set(\fb,~feedback);
~sinfb.play;
~feedback.end;
)
