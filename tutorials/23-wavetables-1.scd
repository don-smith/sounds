s.boot;
s.quit;
s.scope;
FreqScope.new;

// Osc is the primary UGen for oscillating over wavetables and it is a linear interpolation that, unline SinOsc, can operate on a custom wavetable stored in a buffer. There is also COsc (chorusing) and VOsc (variable).
// Shaper uses a wavetable as a transfer function in order to alter the shape of an input signal. This will be covered more in part 2.
// Signal is a language-side ordered collection (like an array that can only contain floats) used for representing a sampled function in an offline context (away from the audio server). The size should be a power of 2. 1024 is a good place to start. We can either use sineFill on Signal or an Env with its asSignal message.

b  = Buffer.alloc(s, 2048);

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

{Osc.ar(b, MouseX.kr(10, 10000, 1)*[1,2.01], mul:0.2!2)}.play;

