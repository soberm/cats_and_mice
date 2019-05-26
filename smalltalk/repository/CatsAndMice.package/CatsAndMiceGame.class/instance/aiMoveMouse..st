as yet unclassified
aiMoveMouse: mouse 
	| x1 y1 enter |
	x1 := mouse position x.
	y1 := mouse position y.
	mouse nextGoalX isNil
		ifTrue: [| distance visitedSubways inSubway selectedSubway |
			distance := 2 raisedTo: 31.
			mouse nextGoalX: 0.
			mouse nextGoalY: 0.
			visitedSubways := mouse visitedSubways.
			inSubway := mouse parent isMemberOf: Subway.
			inSubway
				ifTrue: [| exit |
					exit := (1 to: 2) atRandom.
					exit = 1
						ifTrue: [mouse nextGoalX: mouse parent position1 x.
							mouse nextGoalY: mouse parent position1 y]
						ifFalse: [mouse nextGoalX: mouse parent position2 x.
							mouse nextGoalY: mouse parent position2 y]]
				ifFalse: ["we are not in a subway, look for the closest one we
					have not been at"
					1
						to: subways size
						do: [:subwayIndex | 
							| x2 y2 tmp subway contains |
							subway := subways at: subwayIndex.
							contains := visitedSubways indexOf: subway.
							contains := contains > 0.
							contains
								ifFalse: [x2 := subway position1 x.
									y2 := subway position1 y.
									tmp := ((x1 - x2 raisedTo: 2)
												+ (y1 - y2 raisedTo: 2)) sqrt.
									"if it is closer than our current goal
									select this one"
									distance > tmp
										ifTrue: [distance := tmp.
											mouse nextGoalX: x2.
											mouse nextGoalY: y2.
											selectedSubway := subway].
									"check if other entry is closer"
									x2 := subway position2 x.
									y2 := subway position2 y.
									tmp := ((x1 - x2 raisedTo: 2)
												+ (y1 - y2 raisedTo: 2)) sqrt.
									"if it is closer than our current goal
									select this one"
									distance > tmp
										ifTrue: [distance := tmp.
											mouse nextGoalX: x2.
											mouse nextGoalY: y2.
											selectedSubway := subway]]].
					mouse visitedSubways add: selectedSubway]].
	"only move if smaller than step size"
	y1 < (mouse nextGoalY - 4)
		ifTrue: [mouse down]
		ifFalse: [y1 > (mouse nextGoalY + 4)
				ifTrue: [mouse up]
				ifFalse: [mouse upStop.
					mouse downStop]].
	x1 < (mouse nextGoalX - 4)
		ifTrue: [mouse right]
		ifFalse: [x1 > (mouse nextGoalX + 4)
				ifTrue: [mouse left]
				ifFalse: [mouse leftStop.
					mouse rightStop]].
	"check wheter we are at the goal"
	enter := mouse direction x = 0.
	enter := enter
				and: [mouse direction y = 0].
	enter
		ifTrue: [mouse enterOrExit: true]