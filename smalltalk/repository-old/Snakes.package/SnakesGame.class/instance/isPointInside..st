Snakes
isPointInside: aPoint
	^(aPoint x >= 0) and: (aPoint x < fieldWidth) and: (aPoint y >= 0) and: (aPoint y < fieldHeight).