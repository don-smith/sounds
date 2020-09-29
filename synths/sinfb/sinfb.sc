(
// From Sean Cotterill
// https://github.com/theseanco/howto_co34pt_liveCode/blob/master/Setup/SynthDefs.scd
SynthDef(\sinfb, {
  arg freq=440, fb=0, atk=0.01, sus=0, rel=1,
    amp=0.3, out=0, pan=0;
  var sig, env;
  env = EnvGen.ar(Env.linen(atk,sus,rel),1,1,0,1,2);
  sig = SinOscFB.ar(freq, fb, 1);
  sig = Pan2.ar(sig, pan);
  sig = sig * env * amp;
  Out.ar(out, sig);
}).add;
);

/*
* General purpose SinOscFB Ugen designed to mutate between pure sine waves and
* noise using the feedback argument. It works well for bass, chords and melody.
*
* -= Arguments =-
*
* fb: feedback (generally useful values are from 0-2, with 0 being pure sine
*   waves and 2-20 being noise. More info on this in 'Between Pitch and Noise'
*   section of guides)
*
*/
