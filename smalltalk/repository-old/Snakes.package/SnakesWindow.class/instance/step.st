window events
step
	"makes one step during stepping"
	| tmp |
	tmp := game tick.
	game snakes size <= 1
		ifTrue: [
			self stopStepping.
			UIManager default inform: ('Player ', tmp, ' won!').
			self delete.
		].
	
	healthMorphs do: [:each | each update].