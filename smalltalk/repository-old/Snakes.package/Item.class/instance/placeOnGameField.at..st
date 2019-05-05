initialize-release
placeOnGameField: aGame at: aPoint
	position := aPoint.
	playingField := aGame.
	aGame placeMorph: morph at: position.