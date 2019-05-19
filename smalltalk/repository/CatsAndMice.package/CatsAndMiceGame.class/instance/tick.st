ticks
tick
	"Main logic of the game"
	self moveCharacters.

	"Check if cat eats mouse"
	self subwayTick.
	self miceTick.
	
	self renderTick.
	"AI"
	"make sure to filter out the player in mice/cats, by using e.g. cats at: catindex !== player, adding the player to these lists makes rendering and collision detection a lot easier"