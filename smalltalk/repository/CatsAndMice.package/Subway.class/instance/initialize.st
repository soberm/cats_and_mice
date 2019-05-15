initialize-release
initialize
	"comment stating purpose of message"
	mice := OrderedCollection new.
	knownCatLocations := OrderedCollection new.
	
	entry1 := CircleMorph new.
	entry1 bounds: (position1 corner: position1 + (60@60)).
	entry1 position: position1.
	entry1 color: Color black.
	
	entry2 := CircleMorph new.
	entry2 bounds: (position2 corner: position2 + (60@60)).
	entry2 position: position2.
	entry2 color: Color black.
	
	path := RectangleMorph new.
	path position: pathPosition.
	path color: Color blue.
	
	position1 x = position2 x
		ifTrue: [
				path width: 60.		
				path height: position2 y - position1 y. 
			]
		ifFalse: [
				path width: position2 x - position1 x.		
				path height: 60.
		].
	
	
	
	