"./voices.sc".loadRelative;

(
  // Sounds best through some reverb
  Ndef(\rev, {
    Out.ar(0,Limiter.ar(FreeVerb.ar(LPF.ar(In.ar([0,1]),10000),mix:0.33),0.7));
  });
)

// This example was provided by the author.
// See ./voices.sc for details on the synth.

(
  Tdef(\voices, {
    inf.do {
      10.do {
        if ((0.8).coin, {
          Synth(\voice, [
            \n: [24,28,29,48,36,40,41,52,53,60,64,65].choose,
            \p: {rrand(-0.5,0.5)},
            \d: {rrand(5,13)},
            \r: {rrand(8,14)}
          ]);
        });
        rrand(0.1,1).wait;
      };
      18.wait;
    };
  });
)

Tdef(\voices).play;
Tdef(\voices).stop;
Ndef(\rev).free;
