accessors
getSurroundingVectors: point
	"comment stating purpose of message"

	| x y re |
	x := point x.
	y := point y.
	
	re := OrderedCollection new.
	
	re add: (Point x: x-1 y: y ) .
	re add: (Point x: x+1 y: y ) .
	re add: (Point x: x y: y-1 ) .
	re add: (Point x: x y: y+1 ) .
	
	^ re.