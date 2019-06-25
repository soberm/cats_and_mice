as yet unclassified
checkForEmptySubways
	"check if there are subways (except the goal) without any mice"
	1
		to: subways size
		do: [:subwayIndex | 
			| subway subwayMice |
			subway := subways at: subwayIndex.
			subwayMice := subway mice.
			subway isGoal
				ifFalse: [subwayMice size = 0
						ifFalse: [^ false]]].
	^ true