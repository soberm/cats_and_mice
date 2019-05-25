as yet unclassified
moveMice
	1
		to: mice size
		do: [:mouseIndex | 
			| mouse x1 y1 targetX targetY distance |
			mouse := mice at: mouseIndex.
			x1 := mouse position x.
			y1 := mouse position y.
			targetX := 0.
			targetY := 0.
			distance := 2 raisedTo: 31.
			mouse == player
				ifFalse: [1
						to: subways size
						do: [:subwayIndex | 
							| subway x2 y2 x3 y3 entry1Distance entry2Distance |
							subway := subways at: subwayIndex.
							x2 := subway entry1 position x.
							y2 := subway entry1 position y.
							entry1Distance := ((x1 - x2 raisedTo: 2)
										+ (y1 - y2 raisedTo: 2)) sqrt.
							x3 := subway entry2 position x.
							y3 := subway entry2 position y.
							entry2Distance := ((x1 - x3 raisedTo: 2)
										+ (y1 - y3 raisedTo: 2)) sqrt.
							distance > entry1Distance
								ifTrue: [distance := entry1Distance.
									targetX := x2.
									targetY := y2].
							distance > entry2Distance
								ifTrue: [distance := entry2Distance.
									targetX := x3.
									targetY := y3]].
					y1 <= targetY
						ifTrue: [mouse down]
						ifFalse: [mouse up].
					x1 <= targetX
						ifTrue: [mouse right]
						ifFalse: [mouse left]].
			mouse move: self window]