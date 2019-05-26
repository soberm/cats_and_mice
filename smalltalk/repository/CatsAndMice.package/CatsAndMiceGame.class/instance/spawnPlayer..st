as yet unclassified
spawnPlayer: isCat 
	| character x y height width |
	isCat
		ifTrue: [character := Cat new.
			height := window height - character morph height.
			width := window width - character morph width.
			x := rand nextInt: width.
			y := rand nextInt: height.
			character position: x @ y.
			cats add: character.
			player := character.
			self placeMorph: character morph at: x @ y]
		ifFalse: [character := Mouse new.
			height := window height - character morph height.
			width := window width - character morph width.
			x := rand nextInt: width.
			y := rand nextInt: height.
			character position: x @ y.
			mice add: character.
			character parent: window.
			player := character.
			self placeMorph: character morph at: x @ y]