as yet unclassified
spawnCats: count
	| cat x y |

	count timesRepeat: [
		x := rand nextInt: window width.
		y := rand nextInt: window height.
		
		cat := Cat new.
		cat position: x@y.
				
		cats add: cat.
		self placeMorph: cat morph at: x@y
	]. 
			