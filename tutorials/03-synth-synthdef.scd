// Start interpreter with :SClangStart
s.boot;

// The original from the previous tutorial
(
z = {
    arg noiseHz=8;
    var freq, amp;
    freq = LFNoise0.kr(noiseHz).exprange(200,1000);
    amp = LFNoise0.kr(12).exprange(0.21,0.3);
    SinOsc.ar(freq, mul:amp);
}.play;
)

// Define the SynthDef version
(
SynthDef.new(\sineTest, {
    arg noiseHz=8;
    var freq, amp, sig;
    freq = LFNoise0.kr(noiseHz).exprange(200,1000);
    amp = LFNoise0.kr(12).exprange(0.21,0.3);
    sig = SinOsc.ar(freq, mul:amp);
    Out.ar(0, sig);
}).add;
)

// Use the SynthDef
x = Synth.new(\sineTest);

x.free;
