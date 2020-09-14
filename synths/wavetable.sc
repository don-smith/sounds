/*
 * This file creates 2 global variables:
 *   - w: An array of 10 signals used for making the wavetables
 *   - b: An array of 10 wavetable buffers
 * The w variable is indended to only be used inside this file, while the
 * b variable is used to pass wavetable buffers indexes into the wavetable
 * synth. See ../playground/synths/wavetable_examples.sc for more info.
 */

// Clean up
// b.do(_.free);

//10 wavetables with increasing complexity
w = 10.collect({
  arg i;

  //random number of envelope segments
  var numSegs = i.linexp(0,9,4,40).round;

  Env(
    //env always begins and ends with zero
    //inner points are random from -1.0 to 1.0
    [0]++({1.0.rand}.dup(numSegs-1) * [1,-1]).scramble++[0],

    //greater segment duration variety in higher-index wavetables
    {exprand(1,i.linexp(0,9,1,50))}.dup(numSegs),

    //low-index wavetables tend to be sinusoidal
    //high index wavetables tend to have sharp angles and corners
    {[\sine,0,exprand(1,20) * [1,-1].choose].wchoose([9-i,3,i].normalizeSum)}.dup(numSegs);
  ).asSignal(1024);
});

// w.reverseDo(_.plot);

//load into 10 buffers in wavetable format
b = Buffer.allocConsecutive(10, s, 2048, 1, {
  arg buf, index;
  buf.setnMsg(0, w[index].asWavetable);
});

SynthDef(\wavetable, {
  arg buf=0, freq=200, detune=0.2,
  amp=0.2, pan=0, out=0, rout=0, rsend=(-20),
  atk=0.01, sus=1, rel=0.01, c0=1, c1=(-1);
  var sig, env, detuneCtrl;
  env = EnvGen.ar(
    Env([0,1,1,0],[atk,sus,rel],[c0,0,c1]),
    doneAction:2
  );

  //array of eight Oscs with uniquely detune frequencies
  //and unique initial phase offsets
  detuneCtrl = LFNoise1.kr(0.1!8).bipolar(detune).midiratio;
  sig = Osc.ar(buf, freq * detuneCtrl, {rrand(0,2pi)}!8);

  sig = LeakDC.ar(sig); //remove DC bias
  sig = PanAz.ar(4, sig, LFSaw.kr(0.05), 0.01, width:2);
  sig = sig * env * amp;
  Out.ar(out, sig);
  Out.ar(rout, sig * rsend.dbamp); //"post-fader" send to reverb
}).add;
