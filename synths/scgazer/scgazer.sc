// SCgazer versiøn 1.0.
//
// Møffenzeef Mødular Stargazer Drone Synth Emulation
//
// Description:
//
// STARGAZER is øne beast øf a drøne machine: dual wavetable øscillatør
// with ninety arbitrary waveførms,
// twø resønant løwpass filters, three wavetable LFØ's,
// sample rate reductiøn, bit rate reductiøn,
// amplitude mødulatiøn, and CMØS distørtiøn.
// STARGAZER can handle the abuse it will inevitably take at gigs
// and is røad ready før whatever horrible treatment lies ahead.
// Stare intø the sky møuth agape while shredding
// parallel dimensiøns with this hypnøtic vømitrøn.
//
// Website: https://www.moffenzeefmodular.com/stargazer
//
// This is an emulatiøn from what the website is describing.
// I bet the hardware versiøn is much weirder and sø much more interesting.
// If you like it gø buy it.
//
// This is definetly nøt a spønsøred emulatiøn.
//
// 25/04/2020
// Bangkøk, Thailand
// K.E.

// Initialize 90 randomly created waveforms with increasing complexity

~wt = 90.collect({
  arg i;

  //random number of envelope segments
  var numSegs = i.linexp(0,89,4,64).round;

  Env(
    //env always begins and ends with zero
    //inner points are random from -1.0 to 1.0
    [0]++({1.0.rand}.dup(numSegs-1) * [1,-1]).scramble++[0],

    //greater segment duration variety in higher-index wavetables
    {exprand(1,i.linexp(0,9,1,50))}.dup(numSegs),

    //low-index wavetables tend to be sinusoidal
    //high index wavetables tend to have sharp angles and corners
    {[\sine,0,exprand(1,20) * [1,-1].choose].wchoose([9-i,3,i].normalizeSum)}.dup(numSegs);
  ).asSignal(1024).asWavetable;
});

~vbuf = Buffer.allocConsecutive(90, s, 2048);
~vbuf.do({
  arg buf, i;
  buf.loadCollection(~wt[i]);
});

// Initialize the synth

SynthDef.new(\scgazer, {
  arg amp=1, out=0, pan=0.0, freq=440, gain=(-1.0), wave=0, sub=0, detune=1,
  mix=(-1.0), freq1=880, freq2=880, res1=0.0, res2=0.0, alias=44100, redux=24,
  rate1=10, rate2=10, rate3=10, depth1=1, depth2=1, depth3=1,
  lfo1type1=0, 	lfo1type2=0, 	lfo1type3=0;

  var sig, detuned, pitch, lfo1, lfo2, lfo3, filter1, filter2;

  lfo1 = Select.kr(lfo1type1, [
    SinOsc.kr(rate1), LFTri.kr(rate1), LFSaw.kr(rate1, 1),
    LFSaw.kr(rate1*(-1)), LFPulse.kr(rate1), LFNoise0.kr(rate1)
  ]);
  lfo2 = Select.kr(lfo1type2, [
    SinOsc.kr(rate2), LFTri.kr(rate2), LFSaw.kr(rate2, 1),
    LFSaw.kr(rate2*(-1)), LFPulse.kr(rate2), LFNoise0.kr(rate2)
  ]);
  lfo3 = Select.kr(lfo1type3, [
    SinOsc.kr(rate3), LFTri.kr(rate3), LFSaw.kr(rate3, 1),
    LFSaw.kr(rate3*(-1)), LFPulse.kr(rate3), LFNoise0.kr(rate3)
  ]);
  detuned = Select.ar(sub, [VOsc.ar(wave, freq*detune), VOsc.ar(wave, (freq*0.5)*detune)]);

  wave = ~vbuf[0].bufnum + wave;
  sig = VOsc.ar(wave, freq);
  sig = XFade2.ar(sig, detuned, mix);
  filter1 = MoogLadder.ar(sig, freq1*lfo1.range(1, depth1), res1);
  sig = Decimator.ar(filter1, alias, redux);
  filter2 = MoogLadder.ar(sig, freq2*lfo2.range(1, depth2), res2);
  sig = Splay.ar(filter2);
  sig = LeakDC.ar(sig);
  sig = XFade2.ar(sig, sig.clip, gain);
  sig = sig * amp * lfo3.range(1, depth3);
  sig = Limiter.ar(sig, 0.8);

  Out.ar(out, sig)
}).add;
