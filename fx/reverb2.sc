// ServerTree.remove(~add_reverb2);

(
  SynthDef(\reverb2, {
    arg in=0, out=0, dec=4, lpf=1500;
    var sig;
    sig = In.ar(in, 2).sum;
    sig = DelayN.ar(sig, 0.03, 0.03);
    sig = CombN.ar(sig, 0.1, {Rand(0.01,0.099)}!32, dec);
    sig = SplayAz.ar(4, sig);
    sig = LPF.ar(sig, lpf);
    5.do{sig = AllpassN.ar(sig, 0.1, {Rand(0.01,0.099)}!2, 3)};
    sig = LPF.ar(sig, lpf);
    sig = LeakDC.ar(sig);
    Out.ar(out, sig);
  }).add;
)

// (
// s.newBusAllocators;
// ~rbus2 = Bus.audio(s,4);

// //instantiate reverb and re-instantiate when cmd-period is pressed
// ~add_reverb2 = {Synth(\reverb2, [\in, ~rbus2])};
// ServerTree.add(~add_reverb2);
// s.freeAll;
// )
