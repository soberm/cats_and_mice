as yet unclassified
tick
	|intersects removableMice|
	"Main logic of the game"
	removableMice := OrderedCollection new.

	"Check if cat eats mouse"
	
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
					removableMice add: mouse.
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
													
												].	
										].
								].
							mouse enterOrExit: false.
							self placeMorph: mouse morph at: mouse position.
						].
					].
					
			].
			
		1 to: cats size do: [
				:catIndex |
						|cat|
						cat := cats at: catIndex.
						self placeMorph: cat morph at: cat position.
			].
		1 to: removableMice size do: [
				:rmMouseIndex |
					|mouse|
					mouse := removableMice at: rmMouseIndex.
					mice remove: mouse.
			]
	"AI"
	"make sure to filter out the player in mice/cats, by using e.g. cats at: catindex !== player, adding the player to these lists makes rendering and collision detection a lot easier"