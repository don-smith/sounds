s.boot;
s.quit;


// This works in SCVim
"/home/don/Music/sounds/sketches/loadtest/synth.sc".load;

(
  // These do not work in SCVim, but they do
  // work in the official SuperCollider IDE
  "./synth.sc".resolveRelative.load;
  // ("./synth.sc").loadRelative;
  Synth(\testsynth);
)
