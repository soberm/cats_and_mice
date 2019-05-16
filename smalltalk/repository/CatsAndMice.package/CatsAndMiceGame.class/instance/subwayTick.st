as yet unclassified
subwayTick
	1 to: subways size do: [
		:subwayIndex |
		|removeableMice mice subway|

		subway := subways at: subwayIndex.
		mice := subway mice.
		removeableMice := OrderedCollection new.
		1 to: mice size do: [
			 :mouseIndex | 
			
				|mouse canExit|
				mouse := mice at: mouseIndex.
				canExit := false.
				mouse enterOrExit
					ifTrue: [
						subway := subways at: subwayIndex.
						canExit :=  subway entry1 intersects: ((mouse morph) bounds).
						canExit
							ifTrue: [
									subway exit: mouse at: subway position1.
								]
							ifFalse: [
								canExit :=  subway entry2 intersects: ((mouse morph) bounds).
								canExit
									ifTrue: [
											subway exit: mouse at: subway position2.
										].
							].
						].

				canExit
					ifTrue: [
							removeableMice add: mouse.
							self mice add: mouse.
						]
					ifFalse: [
							self placeMorph: mouse morph at: mouse position.
						].
						
			].
		1 to:  removeableMice size do: [
			:rmMouseIndex |
				|mouse|
				mouse := removeableMice at: rmMouseIndex.
				mice remove: mouse.
			].
	].