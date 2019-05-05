private
buildGrid
	"i don't know if we really need this later on"
	| spacing cellWidth cellHeight |
	spacing := 2.
	
	cellWidth := 1 / gridWidth.
	cellHeight := 1 / gridHeight.
	
	0 to: gridHeight-1 do: [ :y |
		| topFraction bottomFraction |
		topFraction := cellHeight * y.
		bottomFraction := topFraction + cellHeight.
		0 to: gridWidth-1 do: [ :x |
			| nMorph leftFraction rightFraction |
			leftFraction := cellWidth * x.
			rightFraction := leftFraction + cellWidth.
			nMorph := Morph new
				color: (Color red alpha: 0.5).
			gridPaneMorph addMorph: nMorph fullFrame: (LayoutFrame
				fractions: (leftFraction@topFraction corner: rightFraction@bottomFraction)
				offsets: (spacing@spacing corner: (spacing@spacing) negated)).
		].
	].
		
	
	
	