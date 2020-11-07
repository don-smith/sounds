// I most likely got this from one of Eli
// Fieldsteel's musical pieces or tutorial videos

(
  SynthDef(\oneverb, {
    arg in, predelay=0.1, revtime=1.8,
      lpf=4500, mix=0.15, amp=0.1, out=0;
    var dry, wet, temp, sig;
    dry = In.ar(in,2);
    temp = In.ar(in,2);
    wet = 0;
    temp = DelayN.ar(temp, 0,2, predelay);
    16.do{
      temp = AllpassN.ar(temp, 0.05, {Rand(0.001,0.05)}!2, revtime);
      temp = LPF.ar(temp, lpf);
      wet = wet + temp;
    };
    sig = XFade2.ar(dry, wet, mix*2-1, amp);
    Out.ar(out, sig);
  }).add;
)

// ( // setup
//   "../path/to/fx/oneverb2.sc".loadRelative;
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
