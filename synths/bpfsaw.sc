(
  // The building of Eli Fieldsteel's bpfsaw synth:
  // https://www.youtube.com/watch?v=lGs7JOOVjag
  SynthDef(\bpfsaw, {
    arg atk=2, sus=0, rel=3, c1=1, pan=0, c2=(-1),
      freq=500, detune=0.2, cfhzmin=0.1, cfhzmax=0.3,
      cfmin=1500, cfmax=2000, rqmin=0.1, rqmax=0.2,
      lsf=200, ldb=0, amp=1, out=0;
    var sig, env;
    env = EnvGen.kr(Env([0,1,1,0],[atk,sus,rel],[c1,0,c2]),doneAction:2);
    sig = Saw.ar(freq * {LFNoise1.kr(0.5,detune).midiratio}!4);
    sig = BPF.ar(
      sig,
      {LFNoise1.kr(
        LFNoise1.kr(4).exprange(cfhzmin, cfhzmax)
      ).exprange(cfmin, cfmax)}!4,
      {LFNoise1.kr(0.1).exprange(rqmin, rqmax)}!4
    );
    sig = BLowShelf.ar(sig, lsf, 0.5, ldb);
    sig = PanAz.ar(4, sig);
    sig = sig * env * amp;
    Out.ar(out, sig);
  }).add;
)

/*
* -= Arguments =-
*
* atk,sus,rel,c1,c2: times and curves of the amplitude envelope
* cfmin,cfmax: range of the center frequency
* rqmin,rqmax: range of the reciprocal of Q/(bandwidth / cutoffFreq)
* detune: +/- semitone values to detune (0 disables detuning)
* pan: self explanatory, but I don't tend to use it (I have other solutions)
* cfhzmin,cfhzmax: range of the rate change the cf can vary
* lsh: target frequency of the low shelf filter to boost or attenuate
* ldb: decible value of the low shelf filter - 0 (default) disables it
*
* -= Tips =-
*
* Playing multiple instances of this synth at the same time (a chord),
* depending on the arg values of course, for example with 10.do{}, sounds
* amazing. If using a Pbind, this can be done with an array of arrays for the
* value of the freq/midinote/
*
* The detune argument shifts the frequency of each instance of the synth just
* slightly enough that when played simultaneously with other instances, it
* creates a sort of chorus effect.
*
* As the rq approaches 1, it lowers the resonance quality so the effect
* is weakened, which more closely resembles the original sawtooth wave. Values
* closer to 0, increase resonance/whisling. Higher values are also louder. Low
* values with a wide cf range of harmonics, sounds pretty cool.
*
* By increasing the cfhz range (e.g. 1-10), the center frequency changes more
* quickly causing the resonant sweeping effect to become more prominent, to the
* point of a bubbling texture.
*
* -= How it works =-
*
* The LFNoise1 used in Saw is detuning the frequency once every 2 seconds
* by taking a value between -1/1 (detune used as the mul argument) and converting
* that value to the semitone equivalent with midiratio
*
* The LFNoise1 nested in the freq arg (the outter LFNoise1) of the BPF controls
* how often the bandwidth, defined by cfmin and cfmax, of the BPF is evaluated.
*
* BLowShelf creates the ability to boost or attenuate specific low frequencies.
* The default rs (reciprocal of S) of 0.5 can be considered the middle setting.
*
*/
