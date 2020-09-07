// Requires a ProxySpace
// Dependent synths: bpfsaw and sinfb

("../../synths/sinfb.sc").loadRelative;
("../../synths/bpfsaw.sc").loadRelative;

p.fadeTime = 5;
p.clock.tempo = 110/60;

~durs.end;
~durs=(0.25,0.5..2).scramble;
(
~sinfb[0] = Pbind(
  \instrument, \sinfb,
  \scale, Scale.minor(),
  \root, Pwhite(2,3).stutter(8),
  \octave, Pwhite(4,5).stutter(8),
  \degree, Pseq([0,3,4,5,7],inf),
  \dur, Pseq((0.25,0.5..1).scramble, inf),
  \amp, Pbjorklund(7,16,inf) * 0.3,
  \fb, 0.8,
  \rel, 0.5
);
)

~sinfb[1] = \filterIn -> {|in| CombC.ar(in, 1.5, 0.75, 2.0)};
~sinfb[2] = \filterIn -> {|in| FreeVerb.ar(in, 0.5, 0.7)};
~sinfb.xset(\fb, 0.6);
~sinfb.play(numChannels:4, quant:4);
~sinfb.release;
~sinfb.end;

(
  var chrds=(
    \c1: [23,35,54,63,64],
    \c2: [45,52,54,59,61,64],
    \c3: [28,40,47,56,59,63]
  );
  ~pads = Pbind(
    \instrument, \bpfsaw,
    \midinote, Pseq([ chrds[\c1], chrds[\c2], chrds[\c3] ], inf),
    \detune, 0.3, // 0 to disable detuning
    \cfmin, Pseq([
      Prand(chrds[\c1]).midicps*Pwhite(2,5),
      Prand(chrds[\c2]).midicps*Pwhite(2,5),
      Prand(chrds[\c3]).midicps*Pwhite(2,5)
    ], inf),
    \cfmax, Pseq([
      Prand(chrds[\c1]).midicps*Pwhite(30,80),
      Prand(chrds[\c2]).midicps*Pwhite(30,80),
      Prand(chrds[\c3]).midicps*Pwhite(30,80)
    ], inf),
    \rqmin, 0.05,
    \rqmax, 0.3,
    \amp, 0.6,
    \atk, Pwhite(2,4),
    \rel, Pwhite(5,10),
    \dur, Pwhite(4,6),
  )
)
// not sure how to do this yet
~pads[1] = \filterIn -> {Synth(\circpanner)};
~pads.xset(\amp, 0.05);
~pads.play(numChannels: 4);
~pads.release;
~pads.end;
