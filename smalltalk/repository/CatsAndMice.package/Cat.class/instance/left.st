move
left
	"comment stating purpose of message"

	direction := -1 @ 0.
	self position: (self position + (stepLength * direction)).
	