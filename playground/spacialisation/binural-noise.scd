p.pop;
currentEnvironment;
p.push(s);
p.clear;

~x.fadeTime = 3;
~x.play(numChannels: 4);
(
  ~x[0] = {
    var sig = SinOsc.ar(128, mul: 0.02);
    PanAz.ar(4, sig, LFSaw.kr(0.15, 0), width:2);
  };
  ~x[1] = {
    var sig = SinOsc.ar(129, mul: 0.02);
    PanAz.ar(4, sig, LFSaw.kr(0.15, 0.5), width:2);
  };
  ~x[2] = {
    var sig = SinOsc.ar(256, mul: 0.02);
    PanAz.ar(4, sig, LFSaw.kr(0.25, 1, -1), width:2);
  };
  ~x[3] = {
    var sig = SinOsc.ar(258, mul: 0.02);
    PanAz.ar(4, sig, LFSaw.kr(0.25, 1.5, -1), width:2);
  };
  ~x[4] = {
    var sig = PinkNoise.ar(mul: 0.018);
    PanAz.ar(4, sig, LFSaw.kr(0.1, 0.5), width:2);
  };
  ~x[5] = {
    var sig = BrownNoise.ar(mul: 0.01);
    PanAz.ar(4, sig, LFSaw.kr(0.1, 0), width:2);
  };
  ~x[6] = {
    var sig = WhiteNoise.ar(mul: 0.005);
    PanAz.ar(4, sig, LFSaw.kr(0.13, 2, -1), width:2);
  };
  ~x[7] = {
    var sig = GrayNoise.ar(mul: 0.01);
    PanAz.ar(4, sig, LFSaw.kr(0.13, 1.5, -1), width:2);
  };
)
~x.release;
~x.end;

(
  ~y = {
    var sig = PinkNoise.ar(mul: 0.01);
    Pan4.ar(sig);
  };
)
~y.play(numChannels: 4);
~y.end(3);
~y.clear;



// This doesn't work

~x.fadeTime = 3;
~x.play(numChannels: 4);
(
  ~x[0] = { SinOsc.ar(128, mul: 0.02) };
  ~x[1] = \circpanner;
  // ~x[1].map(\panspeed, 0.2);
    // PanAz.ar(4, sig, LFSaw.kr(0.15, 0), width:2);
)
(
  SynthDef(\circpanner, {
    arg in=0, panspeed=0.5, panstart=0, pandir=1;
    PanAz.ar(4, in, LFSaw.kr(panspeed, panstart, pandir));
  }).add;
)
~x[1] = \circpanner;
~x.release;
~x.clear;
