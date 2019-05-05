Snakes
tickEndPhase
	"comment stating purpose of message"

	ticksToLive := ticksToLive - 1.
	(ticksToLive <= 0)
		ifTrue: [playingField remove: self]