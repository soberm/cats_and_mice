Snakes
bittenBy: snake
	"comment stating purpose of message"

	| snakeLength superAction |
	superAction := super bittenBy: snake.
	^ [
		superAction value.
		snakeLength := snake positions size.
		(snakeLength > 2)
			ifTrue: [
				snake moveTail.
			]
			ifFalse: [
				snake health: -10000
			]
	]
	
	