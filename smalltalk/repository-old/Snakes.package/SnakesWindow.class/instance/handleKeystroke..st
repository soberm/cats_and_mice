window events
handleKeystroke: anEvent
	""
	| keyValue snake1 snake2 |
	keyValue := anEvent keyValue.
	(self isStepping not)
		ifTrue: [self startStepping].
		
	snake1 := (game snakes select: [:e | e id = 1]) first.
	snake2 := (game snakes select: [:e | e id = 2]) first.

	keyValue = 30 "up arrow"
		ifTrue: [snake2 direction: (0 @ -1)].

	keyValue = 31 "down arrow"
		ifTrue: [snake2 direction: (0 @ 1)].

	keyValue = 29 "right arrow"
		ifTrue: [snake2 direction: (1 @ 0)].

	keyValue = 28 "left arrow"
		ifTrue: [snake2 direction: (-1 @ 0)].
		
	keyValue = ($q asInteger)
		ifTrue: [self delete].
		
	keyValue = ($w asInteger)
		ifTrue: [snake1 direction: 0@ -1].
		
	keyValue = ($a asInteger)
		ifTrue: [snake1 direction: -1@0].
		
	keyValue = ($s asInteger)
		ifTrue: [snake1 direction: 0@1].
		
	keyValue = ($d asInteger)
		ifTrue: [snake1 direction: 1@0].
		
	Transcript show: keyValue; cr.