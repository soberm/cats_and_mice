as yet unclassified
moveMice
	"move all mice an on the overworld an in the subways"
	1
		to: mice size
		do: [:mouseIndex | 
			| mouse |
			mouse := mice at: mouseIndex.
			mouse == player
				ifFalse: [self aiMoveMouse: mouse].
			mouse move: self window].
	1
		to: subways size
		do: [:subwayIndex | 
			| subway subwayMice |
			subway := subways at: subwayIndex.
			subwayMice := subway mice.
			1
				to: subwayMice size
				do: [:mouseIndex | 
					| mouse |
					mouse := subwayMice at: mouseIndex.
					mouse == player
						ifFalse: [self aiMoveMouse: mouse].
					mouse move: subway]]