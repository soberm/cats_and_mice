as yet unclassified
handleKeyDown: anEvent
	""
	
	| keyValue  |
	keyValue := anEvent keyValue.
	(self isStepping not)
		ifTrue: [self startStepping].

	keyValue = 30 "up arrow"
		ifTrue: [
			self game player up.
		].

	keyValue = 31 "down arrow"
		ifTrue: [
			self game player down.	
		].

	keyValue = 29 "right arrow"
		ifTrue: [
			self game player right.
		].

	keyValue = 28 "left arrow"
		ifTrue: [
			self game player left.	
		].
	keyValue = 69 "e"
		ifTrue: [
			self game player enterOrExit: true.	
		].

	keyValue = 81 "q"
		ifTrue: [self delete].
		
	"Transcript show: keyValue; cr."

