move
move: bounds
	"comment stating purpose of message"
	| tmpPosition|
	tmpPosition := position + (stepLength * direction).

	(((direction x >= 0) or: (tmpPosition x >= bounds minX)) and:
		((direction x <= 0) or: (tmpPosition x + morph width <= bounds maxX)) and:
		((direction y >= 0) or: (tmpPosition y >= bounds minY)) and:
		((direction y <= 0) or: (tmpPosition y + morph height) <= bounds maxY))
			ifTrue: [
					self position: tmpPosition.
				].
