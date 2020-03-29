p = Pshuf([1,2,3], 2);
p = Pseries(1, 0.1, 5);
p = Pgeom(1, 1.1, 5);

~ps = p.asStream;
~ps.next;

(
p = Pbind(
    \degree, Pseq(#[0, 0, 4, 4, 5, 5, 4, 3, 3, 2, 2, 1, 1, 0], 1),
    \dur, Pseq(#[0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 1, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 1], 1)
).play;
)

Pslide(#[1, 2, 3, 4, 5], 5, 3, 1, 0, true).clump(3).asStream.all;

(
p = Pbind(
    \degree, Pslide((-6, -4 .. 12), 8, 3, 1, 0),
    \dur, Pseq(#[0.1, 0.1, 0.2], inf),
    \sustain, 0.15
).play;
)

// Prand: given scale degrees (pentatonic) with equal probability of each
(
p = Pbind(
    \degree, Pxrand([0, 1, 2, 4, 5], inf),
    \dur, 0.25
).play;
)

// Pshuf: randomly ordered once and repeated
(
p = Pbind(
    \degree, Pshuf([0, 1, 2, 4, 5], inf),
    \dur, 0.25
).play;
)

// Pwrand: these probabilities favor triadic notes from scale degrees
(
p = Pbind(
    \degree, Pwrand((0..7), [4, 1, 3, 1, 3, 2, 1].normalizeSum, inf),
    \dur, 0.25
).play;
)

(
p = Pbind(
    \degree, Ppatlace([
        Pseries(0, 1, 8),    // first, third etc. notes
        Pseries(2, 1, 7)    // second, fourth etc. notes
    ], inf),
    \dur, 0.25
).play;
)

('degree': 0, 'dur': 0.5).play;
Pseries(0,1,8).asStream.all;
Ppatlace([Pseries(0,1,8), Pseries(2,1,7)], inf).asStream.all;

// attacks every note (by defining them each time)
p = Pbind(\degree, Pwhite(0, 7, inf), \dur, 0.25, \legato, 1).play;
p.stop;

// slides between notes (by updating them)
p = Pmono(\default, \degree, Pwhite(0, 7, inf), \dur, 0.25).play;
p.stop;

// Scale segments, in the sequence: up, up, down (repeat)
(
TempoClock.default.tempo = 1;
p = Pbind(
    \degree, Pseq([
        Pseries({ rrand(0, 7) }, 1, { rrand(4, 8) }),    // up (step = 1)
        Pseries({ rrand(0, 7) }, 1, { rrand(4, 8) }),    // up (step = 1)
        Pseries({ rrand(7, 14) }, -1, { rrand(4, 8) })    // down (step = -1)
    ], inf),
    \dur, 0.125
).play;
)
p.stop;

p.stop;
s.freeAll;
