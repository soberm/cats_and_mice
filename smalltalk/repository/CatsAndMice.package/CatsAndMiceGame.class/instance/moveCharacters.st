as yet unclassified
moveCharacters
	1
		to: mice size
		do: [:mouseIndex | 
			| mouse |
			mouse := mice at: mouseIndex.
			mouse move: self window].
	1
		to: cats size
		do: [:catIndex | 
			| cat x1 y1 targetX targetY distance xDiff yDiff |
			cat := cats at: catIndex.
			x1 := cat position x.
			y1 := cat position y.
			targetX := 0.
			targetY := 0.
			xDiff := 0.
			yDiff := 0.
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
					xDiff := (x1 - targetX) abs.
					yDiff := (y1 - targetY) abs.
					
						x1 <= targetX
								ifTrue: [cat right]
								ifFalse: [cat left].
						 y1 <= targetY
							ifTrue: [cat down]
							ifFalse: [cat up].
					cat move: self window]].
	1
		to: subways size
		do: [:subwayIndex | 
			| subway mice |
			subway := subways at: subwayIndex.
			mice := subway mice.
			1
				to: mice size
				do: [:mouseIndex | 
					| mouse |
					mouse := mice at: mouseIndex.
					mouse move: subway]]