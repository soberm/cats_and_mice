as yet unclassified
handleKeystroke: anEvent
	""
	
	| keyValue  |
	keyValue := anEvent keyValue.

	keyValue = 30 "up arrow"
		ifTrue: [].

	keyValue = 31 "down arrow"
		ifTrue: [].

	keyValue = 29 "right arrow"
		ifTrue: [].

	keyValue = 28 "left arrow"
		ifTrue: [].

	keyValue = ($q asInteger)
		ifTrue: [self delete].
		
	Transcript show: keyValue; cr.