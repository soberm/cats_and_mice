as yet unclassified
spawnPlayer
	| cat x y |

	x := rand nextInt: window width.
	y := rand nextInt: window height.
		
	cat := Cat new.
	cat position: x@y.

	player := cat.				
	self placeMorph: cat morph at: x@y.
			