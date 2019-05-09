initialize-release
initialize
	""
	
	super initialize.
	bgimagemorph := GameBackgroundImageMorph new.
	bgimagemorph load.
	
	backgroundMorph := BackgroundMorph new.
	backgroundMorph
		addMorph: bgimagemorph;
		delta: 0.
		
	playingFieldMorph := BorderedMorph new
		layoutPolicy: ProportionalLayout new;
		color: Color transparent.
		
	self 
		addMorph: backgroundMorph
		fullFrame: (LayoutFrame
			fractions: (0@0 corner: 1@1)
			offsets: (0@0 corner: 0@0));
		addMorph: playingFieldMorph
		fullFrame: (LayoutFrame
			fractions: (0@0 corner: 1@1)
			offsets: (0@0 corner: 0@0)).