(
  //initialization
  s.newBusAllocators;
  ~circpanbus = Bus.audio(s,1);
  ~randpanbus = Bus.audio(s,1);
)

(
SynthDef(\bpfsaw, {
  arg atk=2, sus=0, rel=3, c1=1, c2=(-1), freq=500, detune=0.2,
    cfmin=500, cfmax=2000, rqmin=0.1, rqmax=0.2, amp=1, out=0;
  var sig, env;
  env = EnvGen.kr(Env([0,1,1,0],[atk,sus,rel],[c1,0,c2]),doneAction:2);
  sig = Saw.ar(freq * LFNoise1.kr(0.5,detune).midiratio);
  sig = BPF.ar(
    sig,
    LFNoise1.kr(0.2).exprange(cfmin, cfmax),
    LFNoise1.kr(0.1).exprange(rqmin, rqmax)
  );
  sig = sig * env * amp;
  Out.ar(out, sig);
}).add;

SynthDef(\circpanner, {
  arg in=0, out=0, panspeed=0.5, panstart=0, pandir=1;
  var sig;
  sig = In.ar(in, 1);
  sig = PanAz.ar(4, sig, LFSaw.kr(panspeed, panstart, pandir));
  Out.ar(out, sig);
}).add;

SynthDef(\randpanner, {
  arg in=0, out=0, panspeed=0.5;
  var sig;
  sig = In.ar(in, 1);
  sig = PanAz.ar(4, sig, LFNoise1.kr(panspeed));
  Out.ar(out, sig);
}).add;
)

(
~fxGrp.free;
~fxGrp = Group.new;
~circpanner = Synth(\circpanner, [ in: ~circpanbus ], ~fxGrp);
~randpanner = Synth(\randpanner, [ in: ~randpanbus ], ~fxGrp);
)

(
~circpanner.set(\panspeed, 0.1, \pandir, [-1, 1].choose);
~pads = Pbind(
    \instrument, \bpfsaw,
    \dur, Pwhite(4.5, 7.0, inf),
    \midinote, Pxrand([
      [23,35,54,63,64],
      [45,52,54,59,61,64],
      [28,40,47,56,59,63],
      [42,52,57,61,63]
    ],inf),
    \detune, Pexprand(0.05,0.2,inf),
    \cfmin, 100,
    \cfmax, 1500,
    \atk, Pexprand(2.0,2.5,inf),
    \rel, Pexprand(6.5,10.0,inf),
    \out, ~circpanbus,
    \amp, 0.2
).play;
)

(
~randpanner.set(\panspeed, 0.4);
~marimba = Pbind(
  \instrument, \bpfsaw,
  \dur, Prand([1,0.5], inf),
  \freq, Prand([1/2, 2/3, 1, 4/3, 2, 5/2, 3, 4, 6, 8], inf),
  \detune, 0,
  \rqmin, 0.005,
  \rqmax, 0.008,
  \cfmin, Prand((Scale.major.degrees+64).midicps, inf) * Prand([0.5,1,2,4], inf),
  \cfmax, Pkey(\cfmin) * Pwhite(1.008,1.025, inf),
  \atk, 3,
  \sus, 1,
  \rel, 5,
  \amp, 1,
  \out, ~randpanbus
).play;
)

~pads.stop;
~marimba.stop;

// The rest of the file is just experimenting

(
  ~randpanner.set(\panspeed, 0.4);
  ~circpanner.set(\panspeed, 0.1, \pandir, [-1, 1].choose);
  ~other = Pbind(
    \instrument, \bpfsaw,
    \freq, 30,
    \detune, 4,
    // \rqmin, 0.005,
    // \rqmax, 0.008,
    \cfmin, 50*2,
    \cfmax, 50*10,
    \atk, 1,
    \sus, 7,
    \rel, 5,
    \amp, 1,
    \out, ~circpanbus,
  ).play;
)

(
10.do {
  Synth(\bpfsaw,
    [
      \freq, 20,
      \amp, 0.5,
      \detune, 1,
      \cfmin, 20*40,
      \cfmax, 20*50,
      \pan, 0
    ]
  );
};
)

// First set of settings
(
10.do {
  Synth(\bpfsaw,
    [
      \freq, 50,
      \amp, 0.4,
      \cfmin, 50*2,
      \cfmax, 50*50,
      \rqmin, 0.005,
      \rqmax, 0.03,
      \pan, 0
    ]
  );
};
)
