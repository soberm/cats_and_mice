access
maxX
	| tmp |
	tmp := 0.
	position1 x = position2 x
		ifFalse: [tmp := 30].
	^ pathPosition x + path width + tmp