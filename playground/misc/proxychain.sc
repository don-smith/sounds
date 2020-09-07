p.pop;
currentEnvironment;
// Be sure you are NOT in a ProxySpace

(
  // First add all the possible things that can be chained
  ProxyChain.add(
    \sin, \mix -> { |freq=440, mul=0.02| SinOsc.ar(freq, 0, mul) },
    \pink, \mix -> { |mul=0.018| PinkNoise.ar(mul) },
    \brown, \mix -> { |mul=0.01| BrownNoise.ar(mul) },
    \white, \mix -> { |mul=0.005| WhiteNoise.ar(mul) },
    \gray, \mix -> { |mul=0.01| GrayNoise.ar(mul) },
    \panner, \filter -> {
      arg in, panspeed=0.15, panstart=0, pandir=1;
      PanAz.ar(4, in, LFSaw.kr(panspeed, panstart, pandir), width:2);
    }
  );

  ProxyChain.all;
  // Next, create a specific chain of things
  Ndef(\sin1).ar(4);
  ~sin1 = ProxyChain.from(Ndef(\sin1).ar(4), [\sin, \panner]);
  ~sin2 = ProxyChain(\sin2, [\sin, \panner], 4);
  ~sin3 = ProxyChain(\sin3, [\sin, \panner], 4);
  ~sin4 = ProxyChain(\sin4, [\sin, \panner], 4);
  ~pn = ProxyChain(\pn, [\pink, \panner], 4);
  ~bn = ProxyChain(\bn, [\brown, \panner], 4);
  ~wn = ProxyChain(\wn, [\white, \panner], 4);
  ~gn = ProxyChain(\gn, [\gray, \panner], 4);

  // Then add the things to the chain with mix values
  ~sin1.setSlots([\sin, \panner], [1,1]);
  ~sin2.setSlots([\sin, \panner], [1,1]);
  ~sin3.setSlots([\sin, \panner], [1,1]);
  ~sin4.setSlots([\sin, \panner], [1,1]);
  ~pn.setSlots([\pink, \panner], [1,1]);
  ~bn.setSlots([\brown, \panner], [1,1]);
  ~wn.setSlots([\white, \panner], [1,1]);
  ~gn.setSlots([\gray, \panner], [1,1]);

  // Finally play the chain
  ~sin1.stop;
  [~sin1, ~sin2, ~sin3, ~sin4, ~pn, ~bn, ~wn, ~gn].do(_.play);

  // And if you have any energy left, make some parameter changes
  ~sin1.set(\freq, 128, \panstart, 0);
  ~sin2.set(\freq, 129, \panstart, 0.5);
  ~sin3.set(\freq, 256, \panspeed, 0.25, \panstart, 1, \pandir, (-1));
  ~sin4.set(\freq, 258, \panspeed, 0.25, \panstart, 1.5, \pandir, (-1));
  ~pn.set(\panspeed, 0.1, \panstart, 0.5);
  ~bn.set(\panspeed, 0.1, \panstart, 0);
  ~wn.set(\panspeed, 0.13, \panstart, 2, \pandir, (-1));
  ~gn.set(\panspeed, 0.13, \panstart, 1.5, \pandir, (-1));

  // THIS IS TOO MUCH OF A PAIN IN THE ASS!

  // And I still can't figure out how to play it out of more than the 1st channel
  [~sin1, ~sin2, ~sin3, ~sin4, ~pn, ~bn, ~wn, ~gn].do(_.stop);
);
