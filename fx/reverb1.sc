ServerTree.remove(~add_reverb1);

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
)

(
s.newBusAllocators;
~rbus1 = Bus.audio(s,4);

//instantiate reverb and re-instantiate when cmd-period is pressed
~add_reverb1 = {Synth(\reverb1, [\in, ~rbus1])};
ServerTree.add(~add_reverb1);
s.freeAll;
)
