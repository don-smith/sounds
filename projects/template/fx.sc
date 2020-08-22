ServerTree.remove(~add_reverb1);
ServerTree.remove(~add_reverb2);

(
  SynthDef(\reverb1, {
    arg in, predelay=0.1, revtime=1.8,
    lpf=4500, mix=0.15, amp=1, out=~out;
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

(
s.newBusAllocators;
~rbus1 = Bus.audio(s,4);
~rbus2 = Bus.audio(s,4);

//instantiate reverb and re-instantiate when cmd-period is pressed
~add_reverb1 = {Synth(\reverb1, [\in, ~rbus1])};
~add_reverb2 = {Synth(\reverb2, [\in, ~rbus2])};
ServerTree.add(~add_reverb1);
ServerTree.add(~add_reverb2);
s.freeAll;
)
