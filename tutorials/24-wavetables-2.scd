(
~wt0 = Signal.sineFill(
    1024,
    1/(1..8),
    0!8
).asWavetable;

~wt1 = Signal.sineFill(
    1024,
    [1,0,1/2,1,1/4],
    [0,0,pi,0,pi]
).asWavetable;
)

~wt0.plot;
~wt1.plot;

// (
// ~b0 = Buffer.loadCollection(s, ~wt0);
// ~b1 = Buffer.loadCollection(s, ~wt1);
// )

// (
// {
//     var sig;
//     sig = VOsc.ar(MouseX.kr(0,1), 300, mul:0.2!2);
// }.play;
// )

(
~wt = Array.fill(4, {
    var numSegs = rrand(4,20);
    Env(
        [0]++(({rrand(0.0,1.0)}!(numSegs-1)) * [1,-1]).scramble++[0],
        {exprand(1,20)}!numSegs,
        {rrand(-20,20)}!numSegs
    ).asSignal(1024).asWavetable;
});
)

~buf = Buffer.allocConsecutive(4, s, 2048);

(
~buf.do({
    arg buf, i;
    buf.loadCollection(~wt[i]);
});
)

~wt.do({arg n, i; n.plot('sc:'++~buf[i].bufnum.asString)});

(
SynthDef.new(\vosc, {
    arg out=0, buf=0, numBufs=2, freq=300, amp=0.2;
    var sig, bufpos, detuneSig;
    detuneSig = LFNoise1.kr(0.2!8).bipolar(0.2).midiratio;
    bufpos = buf + LFNoise1.kr(0.5).range(0, numBufs-1);
    sig = VOsc.ar(bufpos, freq*detuneSig);
    sig = Splay.ar(sig);
    sig = LeakDC.ar(sig) * amp;
    Out.ar(out, sig);
}).add;
)

Synth(\vosc, [\freq, 43.midicps, \buf, ~buf[0].bufnum, \numBufs, 4]);
