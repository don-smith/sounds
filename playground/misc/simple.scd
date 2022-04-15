// Playing around with what Eli considers the essential UGens
// can find list in /tutorials/mus499cFall2020/essential UGens.sc

(
  ~sintone = {
    arg freq=300, amp=0.15, atk=0.01, sus=0, rel=2, c1=1, c2=(-1);
    var sig, env;
    sig = [SinOsc.ar(freq * {Rand(0.4,0.5)}!2)];
    env = EnvGen.kr(Env([0,1,1,0], [atk,sus,rel], [c1,0,c2]), doneAction:2);
    sig = CombC.ar(sig, 0.2, 0.2, 1);
    sig * amp * env;
  }.play;
)
~sintone.play;
~sintone.send;

(
  ~d = NodeProxy(s, \audio, 2);
  ~d[0] = { |amp=5|
    var sig = Dust.ar(4);
    sig!2 * amp;
  };
  ~d[1] = \filter -> { |in| CombC.ar(in, 5) };
  ~d[2] = \filter -> { |in| FreeVerb.ar(in, 0.6, 0.4) };
  ~d[3] = \filter -> { |in| LPF.ar(in, 800) };
  ~d[4] = \filter -> { |in| Balance2.ar(in[0], in[1], LFSaw.kr(0.1).bipolar) };
)
~d.xset(\wet1, 0.7);
~d.xset(\wet2, 0.7);
~d.xset(\wet3, 0.8);
~d.xset(\amp, 8);
~d.play;
~d.release;
~d.end;

(
  ~b = NodeProxy(s, \audio, 2);
  ~b[0] = { |amp=0.8, freq=70|
    var sig = Blip.ar([freq,freq*1.05], LFNoise1.kr(3).range(15,40));
    sig = LPF.ar(sig, 500);
    LPF.ar(sig * amp, 800);
  };
  ~b[10] = \filter -> { |in| CombC.ar(in, 5) };
  ~b[20] = \filter -> { |in| FreeVerb.ar(in, 0.8, 0.8) };
  ~b[30] = \filter -> { |in| Pan2.ar(in, LFNoise1.kr(1).bipolar) };
)
~b.xset(\wet1, 1);
~b.xset(\wet2, 1);
~b.xset(\freq, 74);
~b.play;
~b.end;

(
  ~z = { // toy around with boost/cut
    BLowShelf.ar(
      \in.ar(0!2),
      MouseX.kr(40, 6000, \exponential),
      1.0, // rs
      MouseY.kr(24.0, -24.0, \linear),
      0.5 // mul
    );
  };
)
~z.play;
~z.release;
~z.end;

(
  ~p = {
    arg freq=300, amp=0.05, mov=4;
    var sig = 8.collect({
      LFTri.ar({LFNoise0.kr(mov).exprange(freq, freq*6).round(freq)});
    });
    Splay.ar(sig * amp, 0.3);
  }
)
~p.xset(\mov, 4.5);
~p.xset(\amp, 0.2);
~p.xset(\freq, 70);
~p.end;

~z <<>.in ~p;

~r = NodeProxy(s, \audio, 2);
(
  ~r[0] = {|freq, amp=0.5| 5.collect({ |i|
    var sig = VarSaw.ar(freq!2, width:0.9);
    sig = Blip.ar(sig);
    // sig = Saw.ar(sig);
    // sig = LFTri.ar(sig);
    // sig = SinOsc.ar(sig);
    sig * amp;
  })};
  ~r[4] = \filter -> {|in| CombC.ar(in, 8)};
  ~r[5] = \filter -> {|in| FreeVerb.ar(in, 0.4, 0.6)};
)
~r.stop;
~r.fadeTime = 15;
~r.xset(\wet4, 1);
~r.xset(\freq, 6000);
~r.xset(\amp, 0.5);
