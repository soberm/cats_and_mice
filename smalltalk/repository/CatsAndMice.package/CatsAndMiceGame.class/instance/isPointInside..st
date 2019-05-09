as yet unclassified
isPointInside: aPoint
	^(aPoint x >= 0) and: (aPoint x < window width) and: (aPoint y >= 0) and: (aPoint y < window height).