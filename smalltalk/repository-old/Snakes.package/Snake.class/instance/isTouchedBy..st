Snakes
isTouchedBy: snake
	"comment stating purpose of message"

	snake positions do: [ :otherSnakePosition | 
		(self getSurroundingVectors: otherSnakePosition) do: [ :neighborhoodPoint |
			(self positions contains: [:p | neighborhoodPoint = p])
				ifTrue: [
					^ true.
				].
		].
	].
	^ false.