/*
  Voices
  https://sccode.org/1-4Ss
  http://www.kimri.org/blog/?p=64
*/

(
  SynthDef(\voice, {
    arg out=0, n=0, p=0, d=10, r=10;
    var sig=Array.fill(3,{|i|
      VarSaw.ar(n.midicps*(i+1.0001),mul:0.05/(i+1))
    }).sum;
    var sig2=Ringz.ar(
      WhiteNoise.ar(0.0003),
      TRand.ar(n.midicps,(n+1).midicps,Impulse.ar(10))
    );
    var env=EnvGen.kr(Env.linen(d,1,r), doneAction:2);
    Out.ar(out,Pan2.ar((sig+sig2)*env*(0.8+SinOsc.kr(0.1,0,0.2)),p));
  }).add;
)
