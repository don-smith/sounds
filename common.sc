// :SCNvimStart
// :SCNvimStop

s.options.numOutputBusChannels = 4
s.options.memSize_(2.pow(20))

s.boot
s.reboot
s.quit

FreqScope()
s.plotTree
s.meter
s.scope

s.record
s.stopRecording
s.freeAll
