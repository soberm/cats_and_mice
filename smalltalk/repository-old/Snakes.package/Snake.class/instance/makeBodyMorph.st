private
makeBodyMorph
	(id = 1)
		ifTrue: [^GameImageMorph new load: 'blue_snake_body.png']
		ifFalse: [^GameImageMorph new load: 'yellow_snake_body.png'].