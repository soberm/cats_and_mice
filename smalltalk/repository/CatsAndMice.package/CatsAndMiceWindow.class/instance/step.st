as yet unclassified
step
	"execute the logic of the game in each step"
	| message miceScore catScore |
	game tick.
	"Check for a winner and stop"
	game checkGameEnd
		ifTrue: [self stopStepping.
			miceScore := game goal mice size.
			catScore := game eatenMice.
			catScore > miceScore
				ifTrue: [message := 'Cats won! ' , catScore , ' Mice eaten.']
				ifFalse: [message := 'Mice won! ' , miceScore , ' Mice in goal.'].
			UIManager default inform: message.
			self delete]