ticks
tick
	"logic of the game which should get executed on every tick"
	self moveCharacters.
	self subwayTick.
	self miceTick.
	self renderTick