as yet unclassified
aiMoveCat: cat 
	| x1 y1 targetX targetY distance |
	x1 := cat position x.
	y1 := cat position y.
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
			y1 < (targetY - 4)
				ifTrue: [cat down]
				ifFalse: [y1 > (targetY + 4)
						ifTrue: [cat up]
						ifFalse: [cat upStop.
							cat downStop]].
			x1 < (targetX - 4)
				ifTrue: [cat right]
				ifFalse: [x1 > (targetX + 4)
						ifTrue: [cat left]
						ifFalse: [cat leftStop.
							cat rightStop]].
			(targetX = 0) & (targetY = 0)
				ifTrue: [cat direction: 0 @ 0]]