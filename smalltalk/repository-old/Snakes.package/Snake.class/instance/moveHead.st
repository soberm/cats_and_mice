Snakes
moveHead
	"comment stating purpose of message"

	| newHeadPosition oldHeadPair |
	oldHeadPair := positions first.
	newHeadPosition := Point x: (oldHeadPair first) x + direction x y: (oldHeadPair first) y + direction y.
	(playingField isPointInside: newHeadPosition)
		ifTrue: [
			| headMorph newPair |
			headMorph := oldHeadPair second.
			playingField removeMorph: headMorph.
			headMorph := self makeHeadMorph.
			oldHeadPair at: 2 put: (self makeBodyMorph).
			newPair := {newHeadPosition. headMorph}.
			positions addFirst: newPair.
			"update positions on visual field"
			playingField
				placeMorph: (oldHeadPair second) at: (oldHeadPair first);
				placeMorph: (newPair second) at: (newPair first).
		]
		ifFalse: [
			health := -1.
			self die.
		]