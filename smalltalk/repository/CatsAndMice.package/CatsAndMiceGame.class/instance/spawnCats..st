as yet unclassified
spawnCats: count
	| cat x y |

	count timesRepeat: [
		x := rand nextInt: fieldWidth.
		y := rand nextInt: fieldHeight.
		
		cat := Cat new.
		cat position: x@y.
		cat playingField: self.
		cat morph: cat makeMorph.
				
		cats add: cat.
		cat placeOnGameField: self at: x@y
	]. 
			