// This works in scide and scnvim, but not scvim
// "/home/don/music/sc/playground/loadtest/synth.sc".load;

(
  // These do not work in SCVim,
  // but they do work in scide and scnvim
  // "./synth.sc".resolveRelative.load;
  ("./synth.sc").loadRelative;
  Synth(\testsynth);
)
