// I most likely got this from one of Eli
// Fieldsteel's musical pieces or tutorial videos

(
  SynthDef(\twoverb, {
    arg in=0, out=0, dec=4, lpf=1500;
    var sig;
    sig = In.ar(in, 2).sum;
    sig = DelayN.ar(sig, 0.03, 0.03);
    sig = CombN.ar(sig, 0.1, {Rand(0.01,0.099)}!32, dec);
    sig = Splay.ar(sig);
    sig = LPF.ar(sig, lpf);
    5.do{sig = AllpassN.ar(sig, 0.1, {Rand(0.01,0.099)}!2, 3)};
    sig = LPF.ar(sig, lpf);
    sig = LeakDC.ar(sig);
    Out.ar(out, sig);
  }).add;
)

// ( // setup
//   "../path/to/fx/twoverb2.sc".loadRelative;
//   ~add_twoverbus = {Synth(\twoverb, [\in, ~twoverbus])};
// )

// ( // auto-initialisation
//   s.newBusAllocators;
//   ~twoverbus = Bus.audio(s,2);
//
//   //instantiate reverb fx and re-instantiate when cmd-period is pressed
//   ServerTree.add(~add_twoverbus);
//
//   s.freeAll;
// )

// ( // cleanup
//   ServerBoot.removeAll;
//   ServerTree.removeAll;
//   ServerQuit.removeAll;
//   s.freeAll;
// )
