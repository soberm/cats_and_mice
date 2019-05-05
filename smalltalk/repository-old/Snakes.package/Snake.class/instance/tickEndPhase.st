Snakes
tickEndPhase
	"comment stating purpose of message"

	(health <= 0)
		ifTrue: [
			self die.
		]
		ifFalse: [
			(health > 100)
				ifTrue: [
					health := 100
				].
				
				(boostSlowdownCounter > 0)
					ifTrue: [
						boostSlowdownCounter := boostSlowdownCounter - 1.
						(boostSlowdownCounter = 0)
							ifTrue: [
								ticksPerStep := 2.
							].
					].
				
				((ticksToNextStep = 0) or: (touchedBoost > 0))
					ifTrue: [
						ticksToNextStep := ticksPerStep.
						self moveTail.
						(touchedBoost > 0) ifTrue: [touchedBoost := touchedBoost - 1].
					]
		]