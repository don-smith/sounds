(
{
    PanAz.ar(
        numChans: 4,
        in: ClipNoise.ar(0.1),
        pos: LFSaw.kr(0.2, 1, -1.0),
        //pos: 0,
        level: 0.2,
        width: 2,
        orientation: 0.2
    );
}.play
)

// phase value: 0=0, 0.5=0.5, 1=-1, 1.5=-0.5, 2=0
{ LFSaw.ar(20, [0, 0.5, 1, 1.5, 2]) }.plot(0.1)