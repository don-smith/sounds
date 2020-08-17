s.boot;
s.scope;

(
z = {
    arg noiseHz=8;
    var freq, amp;
    freq = LFNoise0.kr(noiseHz).exprange(200,1000);
    amp = LFNoise0.kr([12, 40]).exprange(0.21,0.3);
    SinOsc.ar(freq, mul:amp);
}.play;
)

z.set(\noiseHz, 6);
z.set(\noiseHz, exprand(1,10));

z.free;
