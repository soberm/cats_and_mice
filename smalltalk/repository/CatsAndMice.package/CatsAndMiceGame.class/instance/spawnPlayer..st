as yet unclassified
spawnPlayer: isCat
	| character x y |

	isCat 
		ifTrue: [
			x := rand nextInt: window width.
			y := rand nextInt: window height.
	
			character := Cat new.
			character position: x@y.

			player := character.				
			cats add: character.
			self placeMorph: character morph at: x@y.
			]
		ifFalse: [
				x := rand nextInt: window width.
				y := rand nextInt: window height.
	
				character := Mouse new.
				character position: x@y.
				mice add: character.
				
				player := character.				
				self placeMorph: character morph at: x@y.
			].
	
			