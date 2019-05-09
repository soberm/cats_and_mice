as yet unclassified
spawnMice: count
	| mouse x y |

	count timesRepeat: [
		x := rand nextInt: fieldWidth.
		y := rand nextInt: fieldHeight.
		
		mouse := Mouse new.
		mouse position: x@y.
		mouse playingField: self.
		mouse morph: mouse makeMorph.
				
		mice add: mouse.
		mouse placeOnGameField: self at: x@y
	]. 
			
		