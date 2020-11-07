"./scgazer.sc".loadRelative;

(
  x = Synth(\scgazer, [\wave, 5]);
)

(
  x.set(
    \freq, 52.midicps, //Pitch expectation: 1-500Hz
    \wave, 30, //waveform selector 0 to 87 waveform
    \detune, 3.midiratio, //detune parameter of the second oscillator
    \sub, 1, // 1 takes detune one octave lower, 0 for using detune as it is
    \mix, (-0.1), // Mix for 2 oscillator. -1 is 1st oscillator and 1 for the 2nd oscillator only 0 is the middle

    \freq1, 1800, // Cutoff frequency for the 1st filter
    \res1, 0.2, // Resonance for filter1 (0..1)
    \lfo1type1, 4, // LFO of 1st filter choose between 6 waveforms: 0=Sine, 1=Triangle, 2=Up Saw, 3=Down Saw, 4=Pulse, 5=Stepped Random
    \rate1, 0.1, // Rate of 1st LFO in Hz
    \depth1, 0.3, // Depth of 1st LFO in Hz, 1 means no modulation, 0 is max
    \alias, 44100/110, // Sample rate reduction in Hz
    \redux, 3, // Bit rate reduction between 0-24 bits

    \freq2, 800, // Cutoff frequency for the 2nd filter
    \res2, 0.7, // Resonance for filter2 (0..1)
    \lfo1type2, 0, // LFO of 2nd filter choose between 6 waveforms: 0=Sine, 1=Triangle, 2=Up Saw, 3=Down Saw, 4=Pulse, 5=Stepped Random
    \rate2, 0.1, // Rate of 2nd LFO in Hz
    \depth2, 0.3, // Depth of 2nd LFO in Hz, 1 means no modulation, 0 is max

    \lfo1type3, 5, // LFO of 3rd filter choose between 6 waveforms: 0=Sine, 1=Triangle, 2=Up Saw, 3=Down Saw, 4=Pulse, 5=Stepped Random
    \rate3, 10, // Rate of 3rd LFO in Hz
    \depth3, 0.5, // Depth of 3rd LFO in Hz, 1 means no modulation, 0 is max)

    \gain, (-0.1), // Gain stage for distortion kinda effect -1 is clean, 1 is dirty
    \amp, 1.5
  )
)

x.free;
