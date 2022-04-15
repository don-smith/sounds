// https://sccode.org/1-5dv
// More fun with arpeggios in F Major

~prog = [\Fmajor7, \Dm7, \Bbmajor7, \Cdom7]

~chords = ~prog.collect({ arg item, i; ChordSymbol.asDegrees(item, Scale.major)}); // collect the chords into a 2D array

p = [0, 2, 1, 3]// new pattern

o = [0, 7, 7, 0]

(
~bass = Pbind(
	\instrument, \default,
	\octave, 4,
	\scale, Scale.major,
	\dur, Pbjorklund2(6, 16) * 0.125,
	\degree, Pseq(
		[
			Pseq([~chords[0][0]],6),
			Pseq([~chords[1][0]],6),
			Pseq([~chords[2][0]]-7,6),
			Pseq([~chords[3][0]],6),
		],
		inf),
	\legato, 0.3
).play;

~chordtest = Pbind(
	\instrument, \default,
	\scale, Scale.major,
	\dur, 0.5,
	\degree, Pseq(
		[
			Pseq(~chords[0][p] + o, 1),
			Pseq(~chords[1][p] + o, 1),
			Pseq(~chords[2][p]-7 + o, 1),
			Pseq(~chords[3][p] + o, 1),
		],
		inf)
).play;
)
(
~chordtest.stop;
~bass.stop;
)
