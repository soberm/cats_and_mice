ticks
miceTick
	|intersects removeableMice|
	removeableMice := OrderedCollection new.
	1 to: mice size do: [
		 :mouseIndex | 
			|mouse|
			intersects := false.
			mouse := mice at: mouseIndex.
			1 to: cats size do: [
					:catIndex |
						|cat|
						cat := cats at: catIndex.
						intersects := intersects or: (cat morph intersects: ((mouse morph) bounds)).
				].
			
			intersects 
				ifTrue: [
					mouse alive: false.
					removeableMice add: mouse.
					eatenMice := eatenMice + 1.
					self removeMorph: (mouse morph).
				]
				ifFalse: [
					mouse alive
						ifTrue: [
							mouse enterOrExit
								ifTrue: [
									|canEnter |
									1 to: subways size do: [
										:subwayIndex |
											|subway|
											mouse enterOrExit
												ifTrue: [
													subway := subways at: subwayIndex.
													canEnter :=  subway entry1 intersects: ((mouse morph) bounds).
													canEnter
														ifTrue: [
																self removeMorph: (mouse morph).
																mouse parent: subway.
																subway enter: mouse at: subway position1.
																1 to:  subway knownCatLocations size do: [
																			:catIndex |
																				|cat|
																				cat := subway knownCatLocations at: catIndex.
																				self removeMorph: cat morph.
																			].
																subway knownCatLocations: cats.
															]
														ifFalse: [
															canEnter :=  subway entry2 intersects: ((mouse morph) bounds).
															canEnter
																ifTrue: [
																	self removeMorph: (mouse morph).
																	mouse parent: subway.

																	subway enter: mouse at: subway position2.
																	1 to:  subway knownCatLocations size do: [
																		:catIndex |
																			|cat|
																			cat := subway knownCatLocations at: catIndex.
																			self removeMorph: cat morph.
																		].
																	subway knownCatLocations: cats.
																	].
														].
													
													canEnter
														ifTrue: [
																removeableMice add: mouse.
															].
													
												].	
										].
								].
							mouse enterOrExit: false.
						].
					].
					
			].
	1 to: removeableMice size do: [
		:rmMouseIndex |
			|mouse|
			mouse := removeableMice at: rmMouseIndex.
			mice remove: mouse.
	]
		