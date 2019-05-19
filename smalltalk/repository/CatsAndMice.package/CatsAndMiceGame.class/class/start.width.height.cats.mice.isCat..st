as yet unclassified
start: player width: width height: height cats: cats mice: mice isCat: isCat
	""
	
	| window game|
	
	window := CatsAndMiceWindow new.
	window openInWorld.
	window height: height.
	window width: width.
	
	game := CatsAndMiceGame new.
	game window: window.
	game createSubways: 8 withMinLength: 200 isCat: isCat.
	game spawnMice: mice.
	game spawnCats: cats.
	game spawnPlayer: isCat.
	
	
	window game: game.