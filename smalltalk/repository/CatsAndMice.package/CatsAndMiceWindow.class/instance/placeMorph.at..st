as yet unclassified
placeMorph: aMorph at: position

	| left top bottom right |
	left := (position x) / gridWidth.
	top := (position y) / gridHeight.
	right := (position x + 1) / gridWidth.
	bottom := (position y + 1) / gridHeight.
	
	playingFieldMorph addMorph: aMorph fullFrame: (LayoutFrame
		fractions: (left@top corner: right@bottom)
		offsets: (0@0 corner: 0@0)).