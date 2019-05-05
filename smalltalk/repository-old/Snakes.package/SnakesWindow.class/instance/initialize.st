initialize-release
initialize
	"arrange the layout of my submorphs"
	
	| rightPaneFrame sidePaneWidth |
	super initialize.

	healthMorphs := OrderedCollection new.	
	
	sidePaneWidth := self sidePaneWidth.
	
	bgImageMorph := GameBackgroundImageMorph new.
	bgImageMorph load.
	
	backgroundMorph := BackgroundMorph new.
	backgroundMorph
		addMorph: bgImageMorph;
		delta: 0.
		
	sidePaneMorph := BorderedMorph new
		color: Color lightGray.
	
"	gridPaneMorph := BorderedMorph new
		layoutPolicy: ProportionalLayout new;
		color: Color transparent."
	
	playingFieldMorph := BorderedMorph new
		layoutPolicy: ProportionalLayout new;
		color: Color transparent.
	
	rightPaneFrame := LayoutFrame
		fractions: (0@0 corner: 1@1)
		offsets: (sidePaneWidth@0 corner: 0@0).
		
	self
		addMorph: sidePaneMorph fullFrame: (LayoutFrame
			fractions: (0@0 corner: 0@1)
			offsets: (0@0 corner: sidePaneWidth@0 negated));
		addMorph: backgroundMorph fullFrame: rightPaneFrame;
		"addMorph: gridPaneMorph fullFrame: rightPaneFrame;"
		addMorph: playingFieldMorph fullFrame: rightPaneFrame.
