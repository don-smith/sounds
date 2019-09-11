(
SynthDef(\bpfsaw, {| atk=2, sus=0, rel=3, c1=1, pan=0,
    c2=(-1), freq=500, cf=1500, rq=0.2, amp=1, out=0 |
    var sig, env;
    env = EnvGen.kr(Env([0,1,1,0],[atk,sus,rel],[c1,0,c2]),doneAction:Done.freeSelf);
    sig = Saw.ar(freq);
    sig = BPF.ar(sig, cf, rq);
    sig = Pan2.ar(sig, pan, amp);
    sig = sig * env;
    Out.ar(out, sig);
}).add;
)

(
Synth(\bpfsaw, [
    \atk, 0.5,
    \rel, 2
]);
)

(
p = Pbind(
    \instrument, \bpfsaw,
    \atk, 0.5,
    \rel, 2,
    \dur, Pseq([1, 0.5, 2], inf).trace,
    \midinote, Pseq([60, 65, 67], inf).trace
).play;
)

p.stop;