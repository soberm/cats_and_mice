Snakes
bittenBy: snake
	"comment stating purpose of message"

	| otherHealth |
	^ [
		otherHealth := snake health.
		health := health - (otherHealth / 2).
	]