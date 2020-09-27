// An explanation of Eli Fieldsteel building this synth:
// https://youtu.be/WBqAM_94TW4?t=2776

SynthDef(\gs, {
  arg sync=1, dens=20, len=0.1, lenRnd=1, buf=0,
    rate=1, pos=0, posSpd=1, posRnd=0,
    grainEnv=(-1), pan=0, panHz=0.1, panRnd=0,
    atk=1, sus=2, rel=1, c0=1, c1=(-1), amp=1, out=0;
  var sig, env, densCtrl, lenCtrl, posCtrl, panCtrl;

  env = EnvGen.ar(Env([0,1,1,0], [atk,sus,rel], [c0,0,c1]), doneAction:2);

  densCtrl = Select.ar(sync, [Dust.ar(dens), Impulse.ar(dens)]);

  lenCtrl = len * LFNoise1.kr(100).exprange(1/lenRnd, lenRnd);

  posCtrl = Phasor.ar(0, posSpd * BufRateScale.ir(buf), 0, BufSamples.ir(buf)-1);
  posCtrl = posCtrl + LFNoise1.kr(100).bipolar(posRnd * SampleRate.ir);
  posCtrl = posCtrl / BufSamples.ir(buf);
  posCtrl = posCtrl + pos;

  panCtrl = pan + LFNoise1.kr(panHz).bipolar(panRnd);

  sig = GrainBuf.ar(
    2,        // numChannels
    densCtrl, // trigger start of new grain
    lenCtrl,  // dur: size of grain in seconds
    buf,      // buffer holding a mono signal
    rate,     // playback rate
    posCtrl,  // start position (0-1)
    2,        // interpolation: 1=none 2=linear 4=cubic
    panCtrl,  // panning: 1=none 2=like Pan2 >2=like PanAz
    grainEnv  // bufnum of sig to use for env -1=built in Hann env
  );

  sig = sig * env * amp;

  Out.ar(out, sig);
}).add;
