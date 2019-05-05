Snakes
bittenBy: snake
	"comment stating purpose of message"

	| superBittenBy |
	superBittenBy := super bittenBy: snake.
	^ [ | health |
		superBittenBy value.
		health := snake health.
		health := health + 20.
		snake health: health.
	]