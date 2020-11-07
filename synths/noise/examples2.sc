"./noise2.sc".loadRelative

// Play with default arguments

Synth(\noise)

// Tweaking various arguments with constants

(
Synth(\noise, [
  \len, 0.1 // closer to a closed hat
])
)

// With some randomality
(
  Synth(\noise, [
    \len, exprand(0.05, 0.5),
    \freq, exprand(4000, 10000),
    \lpCut, exprand(1500, 4000)
  ])
)

// On a tempo
(
  Pbindef(\ex1,
    \instrument, \noise,
    \freq, 5000,
    \amp, 0.5,
    \dur, 0.5,
  )
)

Pbindef(\ex1).play
Pbindef(\ex1).stop
