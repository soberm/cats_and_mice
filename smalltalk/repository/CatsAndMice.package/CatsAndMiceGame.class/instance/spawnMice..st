as yet unclassified
spawnMice: count
	| mouse x y |

	count timesRepeat: [
		x := rand nextInt: window width.
		y := rand nextInt: window height.
		
		mouse := Mouse new.
		mouse position: x@y.
				
		mice add: mouse.
		self placeMorph: mouse morph at: x@y
	]. 
			
		