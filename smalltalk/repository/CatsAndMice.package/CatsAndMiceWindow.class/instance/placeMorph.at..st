as yet unclassified
placeMorph: aMorph at: position 
	"place a morph on the playing field on the given position within the playfield"
	| left top bottom right |
	left := position x / self width.
	top := position y / self height.
	right := position x + aMorph width / self width.
	bottom := position y + aMorph height / self height.
	playingFieldMorph
		addMorph: aMorph
		fullFrame: (LayoutFrame
				fractions: (left @ top corner: right @ bottom)
				offsets: (0 @ 0 corner: 0 @ 0))