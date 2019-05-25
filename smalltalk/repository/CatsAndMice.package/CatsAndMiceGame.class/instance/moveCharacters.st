as yet unclassified
moveCharacters
	
	self moveMice.
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