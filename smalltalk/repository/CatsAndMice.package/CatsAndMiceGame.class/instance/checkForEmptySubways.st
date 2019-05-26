as yet unclassified
checkForEmptySubways
	"comment stating purpose of message"

	1
		to: subways size
		do: [:subwayIndex |
			| subway subwayMice | 
			subway := subways at: subwayIndex.
			subwayMice := subway mice.
			
			subway isGoal
			ifFalse: [
				subwayMice size = 0 
				ifFalse: [
					 ^false.
				].
			].
		
	].
	^true.