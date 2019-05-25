as yet unclassified
moveCats
	1
		to: cats size
		do: [:catIndex | 
			| cat x1 y1 targetX targetY distance |
			cat := cats at: catIndex.
			x1 := cat morph position x.
			y1 := cat morph position y.
			targetX := 0.
			targetY := 0.
			distance := 2 raisedTo: 31.
			cat == player
				ifFalse: [1
						to: mice size
						do: [:mouseIndex | 
							| x2 y2 tmp mouse |
							mouse := mice at: mouseIndex.
							x2 := mouse position x.
							y2 := mouse position y.
							tmp := ((x1 - x2 raisedTo: 2)
										+ (y1 - y2 raisedTo: 2)) sqrt.
							distance > tmp
								ifTrue: [distance := tmp.
									targetX := x2.
									targetY := y2]].
					y1 <= targetY
						ifTrue: [cat down]
						ifFalse: [cat up].
					x1 <= targetX
						ifTrue: [cat right]
						ifFalse: [cat left]].
			cat move: self window].