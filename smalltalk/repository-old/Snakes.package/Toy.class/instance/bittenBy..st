Snakes
bittenBy: snake
	"comment stating purpose of message"

	| superBittenBy |
	superBittenBy := super bittenBy: snake.
	^ [ | ticksPerStep boostSlowdownCounter |
		superBittenBy value.
		ticksPerStep := snake ticksPerStep.
		(ticksPerStep = 2)
			ifTrue: [
				snake ticksPerStep: 1.
				snake boostSlowdownCounter: 20.
			]
			ifFalse: [
				(ticksPerStep > 2)
					ifTrue: [
						snake ticksPerStep: 2.
						snake boostSlowdownCounter: 0
					]
					ifFalse: [
						boostSlowdownCounter := snake boostSlowdownCounter.
						boostSlowdownCounter := boostSlowdownCounter + 20.
						snake boostSlowdownCounter: boostSlowdownCounter.
					]
			]
	]