as yet unclassified
spawnCats: count
	| cat x y |

	count timesRepeat: [
		x := rand nextInt: window width.
		y := rand nextInt: window height.
		
		cat := Cat new.
		cat position: x@y.
		cat playingField: self.
		cat morph: cat makeMorph.
				
		cats add: cat.
		cat placeOnGameField: self at: x@y
	]. 
			