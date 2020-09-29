"./sinfb.sc".loadRelative;

(
  Pbindef(\ex1,
    \instrument, \sinfb,
    \scale, Scale.minor(\just),
    \root, Pwhite(0,4).stutter(8),
    \octave, Pwhite(3,5).stutter(8),
    \degree, Pseq((0..7),inf),
    \dur, Pseq([0.25, 0.75, 1, 2, 0.5], inf),
    \amp, 0.3,
    \fb, 1.5,
    \rel, 0.5
  );
)
Pbindef(\ex1).play;
Pbindef(\ex1).stop;

// Chords can be used by specifying a 2-dimensional array
// in the \degree argument. The same can be done for the \octave argument
(
  Pbindef(\ex2,
    \instrument, \sinfb,
    \scale, Scale.major,
    \root, 0,
    \octave, Pwrand([4,[3,4],[2,3,4]],[0.9,0.08,0.02],inf),
    \degree, Prand([[0,2,4],[2,4,6],[7,2,4],[1,2,3],[0,-2,-4]],inf),
    \dur, Pwhite(5,10),
    \atk, 2,
    \sus, 1,
    \rel, 3,
    \amp, 0.3,
    \fb, 0.1
  );
)

Pbindef(\ex2).play;
Pbindef(\ex2, \fb, 0.1);
Pbindef(\ex2, \fb, 1.9);
Pbindef(\ex2, \amp, 0.04);
Pbindef(\ex2).stop;
