s.boot;
s.plotTree;

// Patterns provide a means of sequencing data through algorythmic and
// computational processes. Usually patterns are used to generate a sequence
// synths allowing the user to dynamically create and manipulate textures,
// drones, rhythms, but patterns are not limited to sound synthesis. They can
// be used to sequence MIDI data, control GUIs and more.

// A simple SynthDef to start with
(
SynthDef(\sine, { | freq=440, atk=0.005, rel=0.3, amp=0.7, pan=0 |
    var sig, env;
    sig = SinOsc.ar(freq);
    env = EnvGen.kr(Env.new([0,1,0],[atk,rel],[1,-1]), doneAction:2);
    sig = Pan2.ar(sig, pan, amp);
    sig = sig * env;
    Out.ar(0, sig);
}).add;
)

(
Synth(\sine, [
    \atk, 0.5,
    \rel, 2
]);
)

(
p = Pbind(
    \instrument, \sine,
    \atk, 0.5,
    \rel, 2,
    \dur, Pseq([1, 0.5, 2], inf).trace,
    \midinote, Pseq([60, 65, 67], inf).trace
).play;
)

p.stop;

t = TempoClock(120/60);

(
Pbindef(
    \sinedef,
    \instrument, \sine,
    \atk, 1.5,
    \rel, 1.5,
    \dur, 1,
    \midinote, 60
).play(t).quant_(4);
)

Pdef(\sinedef).stop;
