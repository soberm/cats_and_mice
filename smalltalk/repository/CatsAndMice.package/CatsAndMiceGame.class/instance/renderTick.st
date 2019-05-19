ticks
renderTick
	"Main logic of the game"
	player isCat
		ifTrue: [
			1 to: mice size do: [
					:mouseIndex |
							|mouse|
							mouse := mice at: mouseIndex.
							mouse alive
								ifTrue: [
										self placeMorph: mouse morph at: mouse position.
									].						
				].
			1 to: cats size do: [
					:catIndex |
							|cat|
							cat := cats at: catIndex.
							self placeMorph: cat morph at: cat position.
							
				].
			]
		ifFalse: [
				player parent == window
					ifTrue: [
							1 to: mice size do: [
								:mouseIndex |
										|mouse|
										mouse := mice at: mouseIndex.
										mouse alive
											ifTrue: [
													self placeMorph: mouse morph at: mouse position.
												].						
							].
							1 to: cats size do: [
									:catIndex |
											|cat|
											cat := cats at: catIndex.
											self placeMorph: cat morph at: cat position.
											
								].
							1 to: subways size do: [
									:subwayIndex |
											|subway|
											subway := subways at: subwayIndex.

											1 to: (subway mice) size do: [
													:mouseIndex |
															|mouse|
															mouse := subway mice at: mouseIndex.
															mouse alive
																ifTrue: [
																		self removeMorph: mouse morph.
																	].						
												].
								].
						]
					ifFalse: [
							1 to: cats size do: [
									:catIndex |
											|cat|
											cat := cats at: catIndex.
											self removeMorph: cat morph.
								].
							1 to: mice size do: [
									:mouseIndex |
											|mouse|
											mouse := mice at: mouseIndex.
											self removeMorph: mouse morph.
								].
							1 to: (player parent) mice size do: [
									:mouseIndex |
											|mouse|
											mouse := player parent mice at: mouseIndex.
											self placeMorph: mouse morph at: mouse position.
								].
							1 to: (player parent) knownCatLocations size do: [
									:catIndex |
											|cat|
											cat := (player parent knownCatLocations) at: catIndex.
											self placeMorph: cat morph at: cat position.
								].
						].
				].
			
	"AI"
	"make sure to filter out the player in mice/cats, by using e.g. cats at: catindex !== player, adding the player to these lists makes rendering and collision detection a lot easier"