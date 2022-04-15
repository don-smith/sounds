"./scgazer.sc".loadRelative;

(
  x = Synth(\scgazer, [
    \freq, 62.midicps, //Pitch
    \wave, 9, //waveform selector 0 to 89 waveform
    \detune, 1.midiratio, //detune parameter of the second oscillator
    \sub, 1, // 1 takes detune one octave lower, 0 for using detune as it is
    \mix, 0, // Mix for 2 oscillator. -1 is 1st oscillator and 1 for the 2nd oscillator only 0 is the middle
    \freq1, 800, // Cutoff frequency for the 1st filter
    \lfo1type1, 0, // LFO of 1st filter choose between 3 waveforms 0 for Triangle, 1 for Saw, 2 for Pulse
    \rate1, 10, // Rate of 1st LFO in Hz
    \depth1, 1, // Depth of 1st LFO in Hz, 1 means no modulation, 0 is max
    \alias, 44100, // Sample rate reduction in Hz
    \redux, 24, // Bit rate reduction between 0-24 bits
    \freq2, 800, // Cutoff frequency for the 2nd filter
    \lfo1type2, 0, // LFO of 2nd filter choose between 3 waveforms 0 for Triangle, 1 for Saw, 2 for Pulse
    \rate2, 10, // Rate of 2nd LFO in Hz
    \depth2, 1, // Depth of 2nd LFO in Hz, 1 means no modulation, 0 is max
    \gain, -1, // Gain stage for distortion kinda effect -1 is clean, 1 is dirty
    \lfo1type3, 0, // LFO of amplitude choose between 3 waveforms 0 for Triangle, 1 for Saw, 2 for Pulse
    \rate3, 10, // Rate of 3rd LFO in Hz
    \depth3, 1, // Depth of 3rd LFO in Hz, 1 means no modulation, 0 is max
  ]);
)

(
  x.set(
    \freq, 55.midicps, //Pitch
    \wave, 69, //waveform selector 0 to 89 waveform
    \detune, 1.midiratio, //detune parameter of the second oscillator
    \sub, 1, // 1 takes detune one octave lower, 0 for using detune as it is
    \mix, 0, // Mix for 2 oscillator. -1 is 1st oscillator and 1 for the 2nd oscillator only 0 is the middle
    \freq1, 1000, // Cutoff frequency for the 1st filter
    \lfo1type1, 0, // LFO of 1st filter choose between 3 waveforms 0 for Triangle, 1 for Saw, 2 for Pulse
    \rate1, 1, // Rate of 1st LFO in Hz
    \depth1, 1, // Depth of 1st LFO in Hz, 1 means no modulation, 0 is max
    \alias, 44100/8, // Sample rate reduction in Hz
    \redux, 8, // Bit rate reduction between 0-24 bits
    \freq2, 600, // Cutoff frequency for the 2nd filter
    \lfo1type2, 0, // LFO of 2nd filter choose between 3 waveforms 0 for Triangle, 1 for Saw, 2 for Pulse
    \rate2, 60, // Rate of 2nd LFO in Hz
    \depth2, 0.3, // Depth of 2nd LFO in Hz, 1 means no modulation, 0 is max
    \gain, -1, // Gain stage for distortion kinda effect -1 is clean, 1 is dirty
    \lfo1type3, 2, // LFO of amplitude choose between 3 waveforms 0 for Triangle, 1 for Saw, 2 for Pulse
    \rate3, 20, // Rate of 3rd LFO in Hz
    \depth3, 1, // Depth of 3rd LFO in Hz, 1 means no modulation, 0 is max)
  )
)

x.free;
