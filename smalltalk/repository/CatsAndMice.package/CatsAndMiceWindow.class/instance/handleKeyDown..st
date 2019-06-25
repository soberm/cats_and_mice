as yet unclassified
handleKeyDown: anEvent 
	"move a player by pressing specific key strokes"
	| keyValue |
	keyValue := anEvent keyValue.
	self isStepping not
		ifTrue: [self startStepping].
	keyValue = 30
		ifTrue: ["up arrow"
			self game player up].
	keyValue = 31
		ifTrue: ["down arrow"
			self game player down].
	keyValue = 29
		ifTrue: ["right arrow"
			self game player right].
	keyValue = 28
		ifTrue: ["left arrow"
			self game player left].
	keyValue = 101
		ifTrue: ["e"
			self game player enterOrExit: true].
	keyValue = 81
		ifTrue: ["q"
			self delete]