private
makeHeadMorph
	| colourString |
	(id = 1)
		ifTrue: [colourString := 'blue']
		ifFalse: [colourString := 'yellow'].
		
	(direction = (1@0))
		ifTrue: [^GameImageMorph new load: colourString, '_snake_head_right.png'].
	(direction = (0@1))
		ifTrue: [^GameImageMorph new load: colourString, '_snake_head_down.png'].
	(direction = (-1@0))
		ifTrue: [^GameImageMorph new load: colourString, '_snake_head_left.png'].
	(direction = (0@(-1)))
		ifTrue: [^GameImageMorph new load: colourString, '_snake_head_up.png'].