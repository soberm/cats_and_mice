as yet unclassified
runWithPlayers: numberOfPlayers gridWidth: gridWidth gridHeight: gridHeight
	"runs a game of snakes"

	| window game |
	
	game := SnakesGame withPlayers: numberOfPlayers.
	window := SnakesWindow withWidth: gridWidth andHeight: gridHeight.
	game 
		fieldWidth: gridWidth;
		fieldHeight: gridHeight;
		window: window;
		makeSnakes.
	window
		game: game;
		openInWorld;
		setWindowSizeToGridSize: 30.
	^game
	