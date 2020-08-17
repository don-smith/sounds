s.meter;

x = {PinkNoise.ar(0.5)!2}.play;
x = {PinkNoise.ar(0.5!2)}.play;
x.free;

x = {VarSaw.ar(40!2, 0, 0.05)}.play;

(
SynthDef.new(\iter, {
    arg freq=40;
    var temp, sum, env;
    sum = 0;
    env = EnvGen.kr(
        Env.perc(0.01, 5, 1, -2),
        doneAction: Done.freeSelf
    );
    10.do{
        temp = VarSaw.ar(
            freq * {Rand(0.99, 1.02)}!2,
            {Rand(0.0, 1.0)}!2,
            {ExpRand(0.005, 0.05)}!2
        );
        sum = sum + temp;
    };
    sum = sum * 0.05 * env;
    Out.ar(0, sum);
}).add;
)

(
SynthDef(\iter2, {|freq=200, dev=1.02|
    var temp, sum;
    sum = 0;
    10.do{ |count|
        temp = SinOsc.ar(
            freq *
            (count + 1) *
            LFNoise1.kr({Rand(0.05, 0.2)}!2).range(dev.reciprocal, dev)
        );
        temp = temp * LFNoise1.kr({Rand(0.5,8)}!2).exprange(0.01,1);
        sum = sum + temp;
    };
    sum = sum * 0.05;
    Out.ar(0, sum);
}).add;
)

x = Synth(\iter2, [\freq, 60]);
x.set(\freq, 88);
x.set(\dev, 1.04);
x.free;