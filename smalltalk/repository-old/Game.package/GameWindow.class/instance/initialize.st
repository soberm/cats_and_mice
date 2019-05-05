as yet unclassified
initialize
	""

	| backgroundMorph |
	super initialize.
	
	bgimagemorph := GameBackgroundImageMorph new.
	bgimagemorph load.
	
	backgroundMorph := BackgroundMorph new.
	backgroundMorph
		addMorph: bgimagemorph;
		delta: 0.
		
	self 
		addMorph: backgroundMorph
		fullFrame: (LayoutFrame
			fractions: (0@0 corner: 1@1)
			offsets: (0@0 corner: 0@0)).
	
	playerimagemorph := GamePlayerImageMorph new.
	playerimagemorph 
		load;
		position: 120@100;
		addMorphTo: self.