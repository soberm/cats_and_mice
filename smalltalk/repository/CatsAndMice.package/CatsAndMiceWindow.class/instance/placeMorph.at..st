as yet unclassified
placeMorph: aMorph at: position

	| left top bottom right |
	left := (position x) / self width.
	top := (position y) / self height.
	right := (position x + 1) / self width.
	bottom := (position y + 1) / self height.
	
	playingFieldMorph addMorph: aMorph fullFrame: (LayoutFrame
		fractions: (left@top corner: right@bottom)
		offsets: (0@0 corner: 0@0)).
	
	"playingFieldMorph addMorph: aMorph."