move
move: bounds 
	| tmpPosition |
	tmpPosition := position x @ position y.
	((direction x >= 0 or: tmpPosition x >= bounds minX)
			and: (direction x <= 0 or: tmpPosition x + morph width <= bounds maxX))
		ifTrue: [tmpPosition := tmpPosition x + (stepLength * direction x) @ tmpPosition y].
	((direction y >= 0 or: tmpPosition y >= bounds minY)
			and: (direction y <= 0 or: tmpPosition y + morph height <= bounds maxY))
		ifTrue: [tmpPosition := tmpPosition x @ (tmpPosition y + (stepLength * direction y))].
	self position: tmpPosition