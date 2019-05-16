as yet unclassified
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
					Transcript show: String cr, mouseIndex.
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
											Transcript show: mouse enterOrExit.
											mouse enterOrExit
												ifTrue: [
													subway := subways at: subwayIndex.
													canEnter :=  subway entry1 intersects: ((mouse morph) bounds).
													canEnter
														ifTrue: [
																subway enter: mouse at: subway position1.
															]
														ifFalse: [
															canEnter :=  subway entry2 intersects: ((mouse morph) bounds).
															canEnter
																ifTrue: [
																		subway enter: mouse at: subway position2.
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
							self placeMorph: mouse morph at: mouse position.
						].
					].
					
			].
	1 to: removeableMice size do: [
		:rmMouseIndex |
			|mouse|
			mouse := removeableMice at: rmMouseIndex.
			mice remove: mouse.
	]
		