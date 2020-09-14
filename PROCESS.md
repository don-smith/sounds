# My Process

This document attempts to describe how I reason about making music with Supercollider. Why create such a document? Well, Supercollider is an incredibly flexible tool/platform that doesn't dictate how you must use it in strict terms. Rather, it gives you a LOT of capability in the form of language constructs and libraries and let's you use them in any way you like. This can be really daunting when you're trying to figure out to accomplish certain things, such as creating musical pieces or live coding a performance. I've spent some time going through [Eli's amazing tutorial videos](https://www.youtube.com/watch?v=yRzsOOiJ_p4&list=PLPYzvS8A_rTaNDweXe6PX4CXSGq4iEWYC) and [Sean's generous live coding setup](https://theseanco.github.io/howto_co34pt_liveCode) in an attempt to learn Supercollider and figure out how I might use it to make music. This is my current thinking.

## ProxySpace

At the moment, based on a recommendation from [Sean](https://seancotterill.xyz/), I'm using [ProxySpace](https://doc.sccode.org/Classes/ProxySpace.html) as the environment for my sessions. Surprisingly, [Eli](https://www.elifieldsteel.com/) hasn't talked about ProxySpace yet. I think it's because ProxySpace is doing a lot for you, such as managing buses, fading between changes and creating/destroying groups, which Eli is obviously quite comfortable controlling himself. I'm still trying to decide which will be better for me. I don't know if the fact that Sean seems to focus on [Live Coding](https://toplap.org/about/) and Eli seems to focus on [well-thought-out pieces](https://www.youtube.com/watch?v=HjsQ8E1DNt0) is a determining factor or not. I may change my mind at some point, but at the moment I'm having a go with ProxySpace.

## Types of sounds

For the music I'd like to create, I need 3 basic types of sounds:

- **Continuous** signals, such as a drone. They my fluctuate, but the tone doesn't have an _end_ per se.
- **Rhythmic** tones, such as percussion and melody. Typically, these are played according to a pattern with optionally controlled randomness.
- **Stabs** are only played once and then stop. They may be short or long in length.


## Controlling the sound

### Tone and frequency

There is also the matter of tone aka the frequency of the signals. Supercollider offers a number of ways to define this. I am leaning towards doing this 2 different ways: a fundamental and its harmonic series and using musical descriptors such as root, octave, degree and scale. Ideally I'd like to be able to use both interchangably and easily. However, it looks like using musical descriptors is only an option when using Pbinds (rhythmic tones) so I might have to use a fundamental frequency and its harmonics for continuous signals and stabs. Although I am using [ChordSymbol](https://github.com/triss/ChordSymbol), which might bridge that gap ... needs more experimenting.

### Filtering

For each of these types of sounds, I need to be able to run them through a filter (something that alters the sound in some way), such as a reverb, chorus, delay effect for example.

### Panning

I will also be playing through either 2 or 4 speakers and will use these multiple channels to create movement. So for each individual sound I also need to control its panning behaviour. For example, I may want to send it initially to a specific speaker and have it move at a certain speed and direction to other speakers.

Eventually I'd like to have enough control to have different panning behaviour for the the dry and filtered/wet signals. In other words, I might like to send the main signal to speaker 1 of 4 and its reverb to speaker 3 of 4.


## How to do it

Okay, back to the types of sounds ... specifically, how to create them in Supercollider.

### Continuous signals

Using functions is the easiest way to create these sounds.

```
~b = NodeProxy(s, \audio, 2);
(
  ~b[0] = { |amp=0.8, freq=70|
    var sig = Blip.ar([freq,freq*1.05], LFNoise1.kr(3).range(15,40));
    sig = LPF.ar(sig, 500);
    LPF.ar(sig * amp, 800);
  };
)
~b.play;
~b.end;
```

I use `play` and `stop` to control their output/monitors, `release` and `send` to control the synth and `end` and `clear` to free resources. I can also use indexes to add more nodes to control them in groups.

When it comes to filtering, I can inject filters as new indexes and control the wet/dry for each filter independently.

```
(
  ~d = NodeProxy(s, \audio, 2);
  ~d[0] = { |amp=5|
    var sig = Dust.ar(4);
    sig!2 * amp;
  };
  ~d[10] = \filter -> { |in| CombC.ar(in, 5) };
  ~d[20] = \filter -> { |in| FreeVerb.ar(in, 0.6, 0.4) };
  ~d[30] = \filter -> { |in| LPF.ar(in, 800) };
)
~d.xset(\wet1, 0.6);
~d.xset(\wet2, 0.7);
~d.xset(\wet3, 0.8);
```

Panning can be controlled by simply placing a panning UGen as the last filter.

```
~d[40] = \filter -> { |in| Balance2.ar(in[0], in[1], LFSaw.kr(0.1).bipolar) };
```

### Rhythmic tones

For tones that repeat based on a pattern, I use a `Pbind` that plays a `SynthDef`.

### Stabs


