access
maxY
	| tmp |
	tmp := 0.
	position1 x = position2 x
		ifTrue: [tmp := 30].
	^ pathPosition y + path height + tmp