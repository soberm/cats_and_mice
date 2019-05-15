as yet unclassified
placeMorph: aMorph at: position

	| left top bottom right |
	left := (position x) / self width.
	top := (position y) / self height.
	right := (position x + aMorph width) / self width.
	bottom := (position y + aMorph height) / self height.
	
	Transcript show: String cr, aMorph.
	Transcript show: String cr, left.
	Transcript show:  String cr, right.
	
	playingFieldMorph addMorph: aMorph fullFrame: (LayoutFrame
		fractions: (left@top corner: right@bottom)
		offsets: (0@0 corner: 0@0)).
	
	"playingFieldMorph addMorph: aMorph."