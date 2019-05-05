Snakes
bittenBy: snake
	"comment stating purpose of message"

	| superBittenBy |
	superBittenBy := super bittenBy: snake.
	^ [ | snakePositions lastSnakePosition |
		superBittenBy value.
		snakePositions := snake positions.
		lastSnakePosition := snakePositions last.
		snakePositions add: lastSnakePosition.
	]