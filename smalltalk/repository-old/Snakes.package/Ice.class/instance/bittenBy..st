Snakes
bittenBy: snake
	"comment stating purpose of message"

	| superBittenBy |
	superBittenBy := super bittenBy: snake.
	^ [ | ticksPerStep oldSlowDownCounter |
		superBittenBy value.
		ticksPerStep := snake ticksPerStep.
		(ticksPerStep < 2)
			ifTrue: [
				snake ticksPerStep: 2.
				snake boostSlowdownCounter: 0
			]
			ifFalse: [
				(ticksPerStep < 4)
					ifTrue: [
						ticksPerStep := ticksPerStep * 2.
						snake ticksPerStep: ticksPerStep.
						snake boostSlowdownCounter: 20.
					]
					ifFalse: [
						oldSlowDownCounter := snake boostSlowdownCounter.
						oldSlowDownCounter = oldSlowDownCounter + 20.
						snake boostSlowdownCounter: oldSlowDownCounter.
					]
			]
		
	]