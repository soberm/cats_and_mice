as yet unclassified
handleKeyUp: anEvent
	""
	
	| keyValue  |
	keyValue := anEvent keyValue.

	keyValue = 30 "up arrow"
		ifTrue: [
			self game player upStop.
		].

	keyValue = 31 "down arrow"
		ifTrue: [
			self game player downStop.	
		].

	keyValue = 29 "right arrow"
		ifTrue: [
			self game player rightStop.
		].

	keyValue = 28 "left arrow"
		ifTrue: [
			self game player leftStop.	
		].

