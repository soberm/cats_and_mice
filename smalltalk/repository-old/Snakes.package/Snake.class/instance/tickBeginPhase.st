Snakes
tickBeginPhase
	"comment stating purpose of message"

	ticksToNextStep := ticksToNextStep -1.
	(ticksToNextStep <= 0)
		ifTrue: [
			self moveHead.
		]
		ifFalse: [
			(touchedBoost > 0)
				ifTrue: [
					self moveHead.
				]
		]