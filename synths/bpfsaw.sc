(
  // The building of Eli Fieldsteel's bpfsaw synth:
  // https://www.youtube.com/watch?v=lGs7JOOVjag
  SynthDef(\bpfsaw, {
    arg atk=2, sus=0, rel=3, c1=1, pan=0, c2=(-1),
      freq=500, detune=0.2, cfhzmin=0.1, cfhzmax=0.3,
      cfmin=1500, cfmax=2000, rqmin=0.1, rqmax=0.2,
      lsf=200, ldb=0, amp=1, out=0;
    var sig, env;
    env = EnvGen.kr(Env([0,1,1,0],[atk,sus,rel],[c1,0,c2]),doneAction:2);
    sig = Saw.ar(freq * {LFNoise1.kr(0.5,detune).midiratio}!4);
    sig = BPF.ar(
      sig,
      {LFNoise1.kr(
        LFNoise1.kr(4).exprange(cfhzmin, cfhzmax)
      ).exprange(cfmin, cfmax)}!4,
      {LFNoise1.kr(0.1).exprange(rqmin, rqmax)}!4
    );
    sig = BLowShelf.ar(sig, lsf, 0.5, ldb);
    sig = PanAz.ar(4, sig);
    sig = sig * env * amp;
    Out.ar(out, sig);
  }).add;
)

/*
* cf: center frequency
* rq: reciprocal quality
*/
