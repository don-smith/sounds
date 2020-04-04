// Rumush
// Facebook: https://www.facebook.com/rumushproduction
// SoundCloud: https://soundcloud.com/rumushproduction
// YouTube: https://www.youtube.com/channel/UCs_Cn1R4iFrYOyc8liFucSQ
// Blog: https://mycelialcordsblog.wordpress.com/
// https://sccode.org/1-50x

(
fork{// I start by setting structure variables: rep(repetition), st(structure), timM(time Multiplier) and two buffers
    var rep = 0;
    var st = 0;
    var timM = 2;
    var buf1 = Buffer.alloc(s, 4e5, 2);
    var buf2 = Buffer.alloc(s, 2e5, 2);
    loop{// Each time the function runs new loop time gets picked(tim), as well as two frequencies. To progress the structure I use two switches. First one is here and it changes according to st variable. It sets value of rep that gets fed into the switch in play function, as well as timM to have better control on the length of each repetition.
        var tim = Array.series(4, 4, 2).choose;
        var freq1 = [1, 5, 9, 20, 10].choose * [0.5, 2, 6, 4].choose;
        var freq2 = [1, 5, 9, 20, 10].choose * [0.5, 2, 6, 4].choose;
        switch(st, // First switch
            3, {rep=rep+1; timM = 4},
            7, {rep=rep+1; timM = 4},
            11, {rep=rep+1; timM = 8}
        );
        play{// Second switch. If rep is 0, then play first the block of code, if 1 then the second block of code etc.
            switch(rep,
                0, {
                    var mainEnv = EnvGen.ar(Env.linen(tim*2, tim*2, tim*2), doneAction:2); // Used to control the volume and free the synth
                    var timA = Array.geom(3, tim, 0.5); // Array calculated based on tim that got chosen with this repetition, it provides values for a couple of sounds to keep it roughly in time

                    var src4 = Mix.fill(8, {// Texture source, panned sine wave with saw controlling the volume and another sine that controls panning, copied 8 times
                        Pan2.ar(SinOsc.ar(freq2*(10+25.rand), 0, Lag.ar(LFSaw.ar(Rand(0.025, 1))+1*0.5))*0.125, SinOsc.ar(1/timA[0]))
                    });
                    st = st+1; // rep and timM changes according to st, look at the first 'switch'
                    ((src4*0.5).tanh)*mainEnv
                },
                1, {
                    var mainEnv = EnvGen.ar(Env.linen(tim/2, tim*4, tim), doneAction:2);
                    var timA = Array.geom(3, tim, 0.5);
                    var trig = Impulse.ar(4/timA); // Used to progress pos and pos2 as well as trigger Decay and reset Phasor. If you're ciurious on how exactly buffers work in this work please go to: https://mycelialcordsblog.wordpress.com/2015/11/09/glitch-buffer-functions/

                    var pos = Demand.ar(trig[1], 0, Dxrand([0, 4e5*0.25, 4e5*0.4, 4e5*0.75], inf)); // Used to set values for Phasor to change reset position
                    var pos2 = Demand.ar(trig[2], 0, Dxrand([0, 2e5*0.25, 2e5*0.4, 2e5*0.75], inf));
                    var src1 = BufRd.ar(2, buf1, Phasor.ar(trig[0], 1, 0, 4e5, pos))*0.5;
                    var src2 = PinkNoise.ar(Decay.ar(trig[0]!2, 0.1, 250)).fold(-0.5, 0.5);// Some noise to give it a pulse
                    var src4 = Mix.fill(8, {
                        Pan2.ar(SinOsc.ar(freq2*(5+75.rand), 0, Lag.ar(LFSaw.ar(Rand(0.025, 1))+1*0.5))*0.125, SinOsc.ar(1/timA[0]))
                    });
                    BufWr.ar(Blip.ar([freq1, freq2], LFNoise1.ar(freq1)+1*50)+src1, buf1, Phasor.ar(0, 1, 0, 2e5)+(LFNoise1.ar(freq1*0.01)+1*2e5));
                    src2 = src2*0.125;
                    st = st+1;
                    MoogFF.ar((((src1+src2+src4)*0.25).tanh)*mainEnv, 18000, 0)// Filter with high frequency just to cut some top
                },
                2, {
                    var mainEnv = XLine.ar(0.001,1, tim*timM, doneAction:2);
                    var src4 = Mix.fill(8, {
                        Pan2.ar(SinOsc.ar(freq2*(5+100.rand), 0, Lag.ar(LFSaw.ar(Rand(0.025, 1))+1*0.5))*0.125, SinOsc.ar(1/tim))
                    });
                    st = st+1;
                    rep = rep+1;
                    SinOsc.ar(60!2+(src4*(10000*mainEnv)), 0, 0.5)// Now instead of playing src4 it is used as a frequency modulator for simple sine bass
                },
                3, {
                    var mainEnv = EnvGen.ar(Env.linen(0.0001, tim*4, tim), doneAction:2);
                    var timA = Array.geom(3, tim, 0.5);
                    var trig = Impulse.ar(8/timA);

                    var pos = Demand.ar(trig[1], 0, Dxrand([0, 4e5*0.25, 4e5*0.4, 4e5*0.75], inf));
                    var pos2 = Demand.ar(trig[2], 0, Dxrand([0, 2e5*0.25, 2e5*0.4, 2e5*0.75], inf));
                    var src1 = BufRd.ar(2, buf1, Phasor.ar(trig[0], 1, 0, 4e5, pos))*0.5;
                    var src2 = PinkNoise.ar(Decay.ar(trig[0]!2, 0.1, 250)).fold(-0.5, 0.5);
                    var src3 = BufRd.ar(2, buf2, Phasor.ar(trig[0], 1, 0, 2e5, pos2))*0.75;
                    var src4 = Mix.fill(8, {
                        Pan2.ar(SinOsc.ar(freq2*(5+50.rand), 0, Lag.ar(LFSaw.ar(Rand(0.025, 1))+1*0.5))*0.125, SinOsc.ar(1/timA[0]))
                    });
                    var src5 = SinOsc.ar(60!2+(src4*(1000*mainEnv)), 0, 0.5);
                    BufWr.ar(Blip.ar([freq1, freq2], LFNoise1.ar(freq1)+1*50)+src1, buf1, Phasor.ar(0, 1, 0, 2e5)+(LFNoise1.ar(freq1*0.1)+1*2e5));
                    BufWr.ar(Saw.ar([freq1, freq2],0.25)+src3, buf2, LFNoise1.ar(freq2*0.1)+1*2e4);
                    src2 = src2*0.125;
                    st = st+1;
                    MoogFF.ar((((src1+src2+src3+src4+src5)*0.25).tanh)*mainEnv, 18000, 0)
                },
                4, {
                    var mainEnv = EnvGen.ar(Env.linen(tim/2, tim*4, tim), doneAction:2);
                    var timA = Array.geom(3, tim, 0.5);
                    var trig = Impulse.ar(timA);

                    var pos = Demand.ar(trig[1], 0, Dxrand([0, 4e5*0.25, 4e5*0.4, 4e5*0.75], inf));
                    var pos2 = Demand.ar(trig[2], 0, Dxrand([0, 2e5*0.25, 2e5*0.4, 2e5*0.75], inf));
                    var src1 = BufRd.ar(2, buf1, Phasor.ar(trig[0], 1, 0, 4e5, pos))*0.5;
                    var src3 = BufRd.ar(2, buf2, Phasor.ar(trig[0], 1, 0, 2e5, pos2))*0.75;
                    BufWr.ar(Blip.ar([freq1, freq2], LFNoise1.ar(freq1)+1*50)+src1, buf1, Phasor.ar(0, 1, 0, 2e5)+(LFNoise1.ar(freq1*0.1)+1*2e5));
                    BufWr.ar(Saw.ar([freq1, freq2],0.25)+src3, buf2, LFNoise1.ar(freq2*0.01)+1*2e4);
                    st = st+1;
                    MoogFF.ar((((src1+src3)*0.25).tanh)*mainEnv, 18000, 0)
                }
            );
        };(tim*timM).wait // Here tim gets multiplied by timM to get the final length and the cycle repeats
    }
};
)
s.freeAll;
