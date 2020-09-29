# bpfsaw

> [The building of Eli Fieldsteel's bpfsaw synth](https://www.youtube.com/watch?v=lGs7JOOVjag)

## Arguments

* atk,sus,rel,c1,c2: times and curves of the amplitude envelope
* cfmin,cfmax: range of the center frequency
* rqmin,rqmax: range of the reciprocal of Q/(bandwidth / cutoffFreq)
* detune: +/- semitone values to detune (0 disables detuning)
* pan: self explanatory, but I don't tend to use it (I have other solutions)
* cfhzmin,cfhzmax: range of the rate change the cf can vary
* lsh: target frequency of the low shelf filter to boost or attenuate
* ldb: decible value of the low shelf filter - 0 (default) disables it

## Tips

Playing multiple instances of this synth at the same time (a chord),
depending on the arg values of course, for example with 10.do{}, sounds
amazing. If using a Pbind, this can be done with an array of arrays for the
value of the freq/midinote/

The detune argument shifts the frequency of each instance of the synth just
slightly enough that when played simultaneously with other instances, it
creates a sort of chorus effect.

As the rq approaches 1, it lowers the resonance quality so the effect
is weakened, which more closely resembles the original sawtooth wave. Values
closer to 0, increase resonance/whisling. Higher values are also louder. Low
values with a wide cf range of harmonics, sounds pretty cool.

By increasing the cfhz range (e.g. 1-10), the center frequency changes more
quickly causing the resonant sweeping effect to become more prominent, to the
point of a bubbling texture.

## How it works

The LFNoise1 used in Saw is detuning the frequency once every 2 seconds
by taking a value between -1/1 (detune used as the mul argument) and converting
that value to the semitone equivalent with midiratio

The LFNoise1 nested in the freq arg (the outter LFNoise1) of the BPF controls
how often the bandwidth, defined by cfmin and cfmax, of the BPF is evaluated.

BLowShelf creates the ability to boost or attenuate specific low frequencies.
The default rs (reciprocal of S) of 0.5 can be considered the middle setting.
