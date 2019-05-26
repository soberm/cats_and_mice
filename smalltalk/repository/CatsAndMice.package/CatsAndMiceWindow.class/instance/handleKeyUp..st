as yet unclassified
handleKeyUp: anEvent 
	| keyValue |
	keyValue := anEvent keyValue.
	keyValue = 30
		ifTrue: ["up arrow"
			self game player upStop].
	keyValue = 31
		ifTrue: ["down arrow"
			self game player downStop].
	keyValue = 29
		ifTrue: ["right arrow"
			self game player rightStop].
	keyValue = 28
		ifTrue: ["left arrow"
			self game player leftStop]