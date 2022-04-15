// I most likely got this from one of Eli
// Fieldsteel's musical pieces or tutorial videos

// Full disclosure: I've modified this synthdef to operate on 4 channels, as
// you can see from the replacement of XFade2 to LinSelectX below, but I have
// not tested if this actually works yet. It's just an ititial idea. There is
// a chance I could be way off in my understanding/assumptions of crossfading
// the 4 input channels and outputting 4 channels. In a future revision or
// fork of this filter is one that allows for more control over pan position.

(
  SynthDef(\oneverb, {
    arg in, predelay=0.1, revtime=1.8,
      lpf=4500, mix=0.15, amp=0.1, out=0;
    var dry, wet, temp, sig;
    dry = In.ar(in,4);
    temp = In.ar(in,4);
    wet = 0;
    temp = DelayN.ar(temp, 0, 2, predelay);
    16.do{
      temp = AllpassN.ar(temp, 0.05, {Rand(0.001,0.05)}!4, revtime);
      temp = LPF.ar(temp, lpf);
      wet = wet + temp;
    };
    // sig = XFade2.ar(dry, wet, mix*2-1, amp;
    sig = LinSelectX.ar(mix, [dry,wet]);
    sig = SplayAz.ar(4, sig * amp);
    Out.ar(out, sig);
  }).add;
)

// ( // setup
//   "../path/to/fx/oneverb4.sc".loadRelative;
//   ~add_oneverbus = {Synth(\oneverb, [\in, ~oneverbus])};
// )

// ( // auto-initialisation
//   s.newBusAllocators;
//   ~oneverbus = Bus.audio(s,2);
//
//   //instantiate reverb fx and re-instantiate when cmd-period is pressed
//   ServerTree.add(~add_oneverbus);
//
//   s.freeAll;
// )

// ( // cleanup
//   ServerBoot.removeAll;
//   ServerTree.removeAll;
//   ServerQuit.removeAll;
//   s.freeAll;
// )
