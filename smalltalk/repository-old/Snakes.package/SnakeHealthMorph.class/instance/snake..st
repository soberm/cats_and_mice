private
snake: aSnake
	snake := aSnake.
	healthBarMorph color: (snake id = 1
		ifTrue: [Color blue]
		ifFalse: [Color yellow]).
	headMorph load: (snake id = 1
		ifTrue: ['blue_snake_head_left.png']
		ifFalse: ['yellow_snake_head_left.png']).