b = Buffer.alloc(s, 2048);

// (
// ~amps = [1] ++ ({[0, exprand(0.05,0.5)].choose}!31);
// ~sig = Signal.sineFill(
//     1024,
//     ~amps,
//     0!31
// );
// ~wt = ~sig.asWavetable;
// b.loadCollection(~wt);
// )

(
var env, sig, wt;
// env = Env([0,0.6,-0.9,0.3,0],[4,3,2,1],\lin);
// env = Env([0,0.6,-0.9,0.3,0],[4,3,2,1],\sine);
env = Env([0,0.6,-0.9,0.3,0],[4,3,2,1],{rrand(-20,20)}!4);
sig = env.asSignal(1024);
wt = sig.asWavetable;
b.loadCollection(wt);
// wt.plot('sc:env');
)

b.plot('sc:buffer');
// sig.plot('sc:signal');

{Osc.ar(b, MouseX.kr(10, 10000, 1)*[1,2 .01], mul:0.2!2)}.play;