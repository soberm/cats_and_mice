Snakes
isBittenBy: snake
	"comment stating purpose of message"

	| otherSnakeHead |
	otherSnakeHead := snake positions first.
	^ self positions contains: [:p | p = otherSnakeHead].