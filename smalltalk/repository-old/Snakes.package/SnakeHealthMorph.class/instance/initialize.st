initialization
initialize
	super initialize.

	headMorph := GameImageMorph new.
	healthBarContainerMorph := RectangleMorph new.
	healthBarMorph := RectangleMorph new.
	
	healthBarMorph borderWidth: 0.
	
	healthBarContainerMorph
		layoutPolicy: ProportionalLayout new;
		borderColor: Color black;
		borderWidth: 2;
		color: Color transparent;
		addMorph: healthBarMorph fullFrame: (LayoutFrame
			fractions: (0@0 corner: 1@1)).
	
	self
		color: Color transparent;
		layoutPolicy: ProportionalLayout new;
		layoutInset: 2;
		addMorph: headMorph fullFrame: (LayoutFrame
			fractions: (0@0 corner: 0@0)
			offsets: (0@0 corner: 30@30 negated));
		addMorph: healthBarContainerMorph fullFrame: (LayoutFrame
			fractions: (0@0 corner: 1@1)
			offsets: (30@0 corner: 0@0)).