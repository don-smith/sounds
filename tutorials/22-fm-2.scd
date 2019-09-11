s.boot;
s.quit;
s.scope;
FreqScope.new;

// FM creates sideband pairs on each side of the carrier freq
// The distance of these sidebands from the carrier freq depend on the modulator freq
// The number of sideband pairs depend on the "index of modulation" which is directly
//   proportional to the amplitude of the modulation frequency

// Here, because the amplitude of modulation is zero, so is the index of modulation
// so the modulator frequency is taken out of the picture and we're left with the carrier freq
{SinOsc.ar(800 + SinOsc.ar(200, mul:0)) * 0.2!2}.play; // just an 800Hz tone

{SinOsc.ar(800 + SinOsc.ar(200, mul:MouseY.kr(0, 400))) * 0.2!2}.play;

// Rule #1 of FM: As the amplitude of the modulator increases,
// the number of sidebands increases, which creates a more complex sound.

// Rule #2 of FM: The interval at which sidebands appear is equal to the modulator freq.

// Example: This creates sidebands at 200, 400, 600, 1000, 1200, 1400, etc.
{SinOsc.ar(800 + SinOsc.ar(200, mul:400)) * 0.2!2}.play; // just an 800Hz tone

// Rule #3 (more like a guideline): We get a sense of pitch when the frequencies of the
// carrier and the modulator form a simple ratio (e.g. 2:1, 3:1, 6:4, etc)

// Example: a 5:1 ratio creates a tone with a fundamental of 100Hz
{SinOsc.ar(500 + SinOsc.ar(100, mul:400)) * 0.2!2}.play; // just an 800Hz tone


// What we'd like in a Synth is the ability to have a single argument represent the pitch
// This is a modified SynthDef from the previous tutorial to do just that
(
SynthDef(\fm, {
  | freq=500, mRatio=1, cRatio=1, modAmp=200
    atk=0.01, rel=1, amp=0.2, pan=0 |
  var car, mod, env;
  env = EnvGen.kr(Env.perc(atk, rel), doneAction: 2);
  mod = SinOsc.ar(freq * mRatio, mul:modAmp);
  car = SinOsc.ar(freq * cRatio + mod) * env * amp;
  car = Pan2.ar(car, pan);
  Out.ar(0, car);
}).add;
)

(
Synth(\fm, [
  \freq, 66.midicps,
  \cRatio, 5,
  \mRatio, 2
])
)

// index of modulation is expressed as: index = modAmp/modHz
// the number of "audible" sideband pairs is index+1

(
SynthDef(\fm, {
  | freq=500, mRatio=1, cRatio=1, index=1,
    atk=0.01, rel=1, amp=0.2, pan=0 |
  var car, mod, env;
  env = EnvGen.kr(Env.perc(atk, rel), doneAction: 2);
  // modAmp has been replaced with freq * mRatio, which by itself
  // will cause the index to always be 1
  mod = SinOsc.ar(freq * mRatio, mul:freq * mRatio * index);
  car = SinOsc.ar(freq * cRatio + mod) * env * amp;
  car = Pan2.ar(car, pan);
  Out.ar(0, car);
}).add;
)

(
Synth(\fm, [
  \freq, 66.midicps,
  // the index now allows us to directly define the number of sidebands
  \index, 4 // aka complexity of the sound
])
)

// Adding an envelope to the index allows us to dynamically
// shape the sound spectrum over the course of a single note
// more interesting (and model the sound of real instruments)
(
SynthDef(\fm, {
  | freq=500, mRatio=1, cRatio=1, index=1, iScale=5,
    atk=0.01, rel=3, amp=0.2, cAtk=4, cRel=(-4), pan=0 |
  var car, mod, env, iEnv;
  iEnv = EnvGen.kr(
    Env.new(
      [index, index * iScale, index],
      [atk, rel],
      [cAtk, cRel]
    )
  );
  env = EnvGen.kr(
    Env.perc(atk, rel, curve:[cAtk, cRel]),
    doneAction: 2
  );
  mod = SinOsc.ar(freq * mRatio, mul:freq * mRatio * iEnv);
  car = SinOsc.ar(freq * cRatio + mod) * env * amp;
  car = Pan2.ar(car, pan);
  Out.ar(0, car);
}).add;
)

// You should totally have a play with these values!
(
Synth(\fm, [
  \freq, 36.midicps,
  \rel, 4,
  \index, 20,
  \iScale, 0.05,
  \mRatio, 0.5
])
)
