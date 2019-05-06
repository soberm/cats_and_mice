as yet unclassified
handleKeystroke: anEvent
	""
	| keyValue |
	keyValue := anEvent keyValue.
	((self isStepping not) and: keyValue >= 28 and: keyValue <= 30)
		ifTrue: [self startStepping].

	keyValue = 30 "up arrow"
		ifTrue: [playerimagemorph direction: (0 @ -1)].

	keyValue = 31 "down arrow"
		ifTrue: [playerimagemorph direction: (0 @ 1)].

	keyValue = 29 "right arrow"
		ifTrue: [playerimagemorph direction: (1 @ 0)].

	keyValue = 28 "left arrow"
		ifTrue: [playerimagemorph direction: (-1 @ 0)].
		
	keyValue = 113 "Q"
		ifTrue: [self delete].