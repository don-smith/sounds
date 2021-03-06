// Start interpreter with :SClangStart
s.boot;
s.quit;
s.options.numOutputBusChannels = 4;

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
SynthDef(\sineTest, {
    arg noiseHz=8, out=0, amp=0.01;
    var freq, sig;
    freq = LFNoise0.kr(noiseHz).exprange(200,1000);
    amp = LFNoise0.kr(12).exprange(0.21,0.3) * amp;
    sig = SinOsc.ar(freq, mul:amp);
    Out.ar(out, sig);
}).add;
)

(
  SynthDef(\test, {
    arg out=0.1;
    var sig;
    sig = SinOsc.ar(1000, mul:0.1);
    Out.ar(out, sig);
  }).add;
)

// Use the SynthDef
x = Synth(\test, [\out, 0, \amp, 0.5]);
y = Synth(\test, [\out, 1, \amp, 0.5]);
z = Synth(\test, [\out, 2, \amp, 0.5]);
w = Synth(\test, [\out, 3, \amp, 0.5]);
x.set(\noiseHz, 5)
x.set(\amp, 0.50)
x.free;

(
  SynthDef(\pulseTest, {
    arg ampHz=4, fund=40, maxPartial=4, width=0.5;
    var amp1, amp2, freq1, freq2, sig1, sig2;
    amp1 = LFPulse.kr(ampHz, 0, 0.12) * 0.05;
    amp2 = LFPulse.kr(ampHz, 0.5, 0.12) * 0.05;
    freq1 = LFNoise0.kr(4).exprange(fund, fund*maxPartial).round(fund);
    freq2 = LFNoise0.kr(4).exprange(fund, fund*maxPartial).round(fund);
    freq1 = freq1 * LFPulse.kr(8, add:1);
    freq2 = freq2 * LFPulse.kr(6, add:1);
    sig1 = Pulse.ar(freq1, width, amp1);
    sig2 = Pulse.ar(freq2, width, amp2);
    sig1 = FreeVerb.ar(sig1, 0.7, 0.8, 0.25);
    sig2 = FreeVerb.ar(sig2, 0.7, 0.8, 0.25);
    Out.ar(0, sig1);
    Out.ar(1, sig2);
  }).add;
)

p = Synth(\pulseTest);
p.set(\fund, 80);
p.set(\maxPartial, 8);
p.free;

s.freeAll;
