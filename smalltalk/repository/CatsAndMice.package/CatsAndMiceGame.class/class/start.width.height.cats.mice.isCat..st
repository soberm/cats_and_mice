as yet unclassified
start: player width: width height: height cats: cats mice: mice isCat: isCat
	""
	
	| window game subway|
	
	window := CatsAndMiceWindow new.
	window openInWorld.
	window height: height.
	window width: width.
	
	game := CatsAndMiceGame new.
	game window: window.
	game spawnMice: mice.
	game spawnCats: cats.
	game spawnPlayer: isCat.
	
	subway := Subway createFrom: 100@100 to: 100@200.
	game subways add: subway.
	
	window placeMorph: subway path at: subway path position.
	window placeMorph: subway entry1 at: subway entry1 position.
	window placeMorph: subway entry2 at: subway entry2 position.
	
	
	window game: game.