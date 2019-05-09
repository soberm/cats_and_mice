as yet unclassified
start
	""
	
	| window game |
	
	window := CatsAndMiceWindow new.
	window openInWorld.
	window gridHeight: fieldHeight.
	window gridWidth: fieldWidth.
	
	game := CatsAndMiceGame new.
	game window: window.
	game fieldWidth: fieldWidth.
	game fieldHeight: fieldHeight.
	game spawnMice: 5.
	game spawnCats: 2.