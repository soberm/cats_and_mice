ticks
subwayTick
	1 to: subways size do: [
		:subwayIndex |
		|removeableMice subwayMice subway|

		subway := subways at: subwayIndex.
		subwayMice := subway mice.
		removeableMice := OrderedCollection new.
		1 to: subwayMice size do: [
			 :mouseIndex | 
			
				|mouse canExit|
				mouse := subwayMice at: mouseIndex.
				canExit := false.
				mouse enterOrExit
					ifTrue: [
						subway := subways at: subwayIndex.
						canExit :=  subway entry1 intersects: ((mouse morph) bounds).
						canExit
							ifTrue: [
									self removeMorph: mouse morph.
									1 to:  subway knownCatLocations size do: [
										:catIndex |
											|cat|
											cat := subway knownCatLocations at: catIndex.
											self removeMorph: cat morph.
										].
									mouse parent: window.
									subway exit: mouse at: subway position1.
								]
							ifFalse: [
								canExit :=  subway entry2 intersects: ((mouse morph) bounds).
								Transcript show: String cr, canExit.
								canExit
									ifTrue: [
											self removeMorph: mouse morph.
											1 to:  subway knownCatLocations size do: [
												:catIndex |
													|cat|
													cat := subway knownCatLocations at: catIndex.
													self removeMorph: cat morph.
												].
											mouse parent: window.
											subway exit: mouse at: subway position2.

										].
							].
						].

				canExit
					ifTrue: [
							removeableMice add: mouse.
							mice add: mouse.
						]
						
			].
		1 to:  removeableMice size do: [
			:rmMouseIndex |
				|mouse|
				mouse := removeableMice at: rmMouseIndex.
				subwayMice remove: mouse.
			].
	].