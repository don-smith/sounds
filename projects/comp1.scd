(
SynthDef(\pad, {
	arg fund=440, offset=1.0, amp=0.2, maxtempo=2, out=0;
	var sig;
	sig = Saw.ar([fund, fund*offset]);
	sig = sig * {LFNoise0.kr(maxtempo).range(0.3.rand, rrand(0.3,0.7))};
	sig = sig * amp;
	Out.ar(out, sig);
}).add;

SynthDef(\verb, {
	arg in, out=0, mix=0.33, room=0.5, damp=0.5;
	var sig;
	sig = In.ar(in, 2);
	sig = FreeVerb2.ar(sig[0], sig[1], mix, room, damp);
	Out.ar(out, sig);
}).add;
)

~pad1 = Synth(\pad, [\fund, 80]);
~pad2 = Synth(\pad, [\fund, 94]);
~pad3 = Synth(\pad, [\fund, 110]);
~pad4 = Synth(\pad, [\fund, 121]);

~pad1.set(\fund, 80);
~pad2.set(\fund, 94);
~pad3.set(\fund, 110);
~pad4.set(\fund, 121);

~padbus = Bus.audio(s, 2);
~verb = Synth(\verb, [\in, ~padbus], addAction: \addToTail);

~pad1.set(\out, ~padbus);
~pad2.set(\out, ~padbus);
~pad3.set(\out, ~padbus);
~pad4.set(\out, ~padbus);

~pad1.set(\maxtempo, 0.4);
~pad2.set(\maxtempo, 0.24);
~pad3.set(\maxtempo, 0.32);
~pad4.set(\maxtempo, 0.17);

~pad1.set(\offset, 1.08);
~pad2.set(\offset, 1.06);
~pad3.set(\offset, 1.04);
~pad4.set(\offset, 1.02);

~pad1.free;
~pad2.free;
~pad3.free;
~pad4.free;

~verb.set(\room, 1);
~verb.set(\mix, 0.9);
~verb.set(\damp, 0.7);

~pad.set(\fund, 220);




