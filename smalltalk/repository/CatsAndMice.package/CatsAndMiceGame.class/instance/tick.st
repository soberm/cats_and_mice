ticks
tick
	"Main logic of the game"
	self moveCharacters.

	"Check if cat eats mouse"
	self subwayTick.
	self miceTick.
	
	self renderTick.