s.boot;
s.quit;

// This does not seem to work in SCVim, but it
// does work in the official SuperCollider IDE

(
  // "/home/don/Music/sounds/loadtest/synth.scd".load;
  "./synth.scd".resolveRelative.load;
  Synth(\testsynth);
)
