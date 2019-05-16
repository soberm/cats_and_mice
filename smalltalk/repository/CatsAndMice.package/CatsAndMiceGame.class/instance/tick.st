as yet unclassified
tick
	"Main logic of the game"
	self moveCharacters.

	"Check if cat eats mouse"
	self subwayTick.
	self miceTick.
	
	1 to: cats size do: [
			:catIndex |
					|cat|
					cat := cats at: catIndex.
					self placeMorph: cat morph at: cat position.
		].
	"AI"
	"make sure to filter out the player in mice/cats, by using e.g. cats at: catindex !== player, adding the player to these lists makes rendering and collision detection a lot easier"