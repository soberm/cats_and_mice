accessors
game: aGame
	| healthMorph1 healthMorph2 |
	game := aGame.
	
	healthMorph1 := SnakeHealthMorph withSnake: game snakes first.
	sidePaneMorph
		layoutPolicy: ProportionalLayout new;
		addMorph: (healthMorph1) fullFrame: (LayoutFrame
			fractions: (0@0 corner: 1@0)
			offsets: (0@0 corner: 0@34)).
	
	healthMorph2 := SnakeHealthMorph withSnake: game snakes second.
	sidePaneMorph
		layoutPolicy: ProportionalLayout new;
		addMorph: (healthMorph2) fullFrame: (LayoutFrame
			fractions: (0@1 corner: 1@1)
			offsets: (0@34 negated corner: 0@0)).
			
	healthMorphs add: healthMorph1; add: healthMorph2.