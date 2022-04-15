p = ProxySpace.push(s);

(
SynthDef(\insect, {
	arg out=0, amp=0.5, rate=20, lpCenter=1000, lpSpread=500;
	var sig;
	sig = Impulse.ar(55, 0.35);
	sig = sig + Impulse.ar(100, mul:amp*0.5);
	sig = sig + Impulse.ar(210, mul:amp*0.25);
	sig = sig * Pulse.ar(rate, mul:amp*0.9);
	sig = RLPF.ar(sig!2, LFNoise0.kr(1).range(lpCenter-lpSpread, lpCenter+lpSpread));
	Out.ar(out, sig * amp);
}).add;

SynthDef(\pluck, {
	arg out=0, amp=0.8, freq=440;
	var sig, env;
	sig = Resonz.ar(Impulse.ar(freq), freq*[0.01,1.01].choose, Rand(0.4,0.7));
	env = EnvGen.ar(Env([0,1,0], [0.05,Rand(0.4,0.7)], \sin), doneAction:2);
	sig = sig * env * amp;
	Out.ar(out, sig!2);
}).add;

SynthDef(\verb, {
	arg in, out=0, mix=0.33, room=0.5, damp=0.5;
	var sig;
	sig = In.ar(in, 2);
	sig = FreeVerb2.ar(sig[0], sig[1], mix, room, damp);
	Out.ar(out, sig);
}).add;
)

~insect = Synth(\insect, [\rate, 26]);
~insect.set(\amp, 0.69);
~insect.set(\rate, 28);
~insect.set(\lpCenter, 2200);
~insect.set(\lpSpread, 400);
~insect.free

Synth(\pluck, [\freq, rrand(500, 800), \amp, 0.4, \out, ~verbus]);

~verbus = Bus.audio(s, 2);
~verb = Synth(\verb, [\in, ~verbus], addAction: \addToTail);

~insect.set(\out, ~verbus);

~verb.set(\room, 0.8);
~verb.set(\mix, 0.6);
~verb.set(\damp, 0.2);

p = Pseq([1,2,3,4,5], 4);
~p = p.asStream;
~p.next;
