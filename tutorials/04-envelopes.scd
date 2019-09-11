// To start server run :SClangStart
s.boot;

(
// x = {PinkNoise.ar * 0.5}.play;

// x = {
//   var sig, env;
//   env = Line.kr(1,0,1);
//   sig = Pulse.ar(exprand(20,100)) * env;
// }.play;

x = {
  var sig, env;
  env = EnvGen.kr(Env.new(
    [0, 0.4, 0.2, 0],
    [0.5, 0.5, 1],
    [3, -3, 1]),
    doneAction:2);
  sig = Pulse.ar(exprand(30,500)) * env;
}.play;
)

x.free;
