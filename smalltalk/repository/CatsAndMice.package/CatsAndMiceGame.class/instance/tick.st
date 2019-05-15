as yet unclassified
tick
	|intersects |
	"Main logic of the game"
	

	"Check if cat eats mouse"
	
	1 to: mice size do: [
		 :mouseIndex | 
			|mouse|
			intersects := false.
			mouse := mice at: mouseIndex.
			1 to: cats size do: [
					:catIndex |
						|cat|
						cat := cats at: catIndex.
						intersects := intersects or: (cat morph intersects: ((mouse morph) bounds)).
						Transcript show: String cr, 'cat intersects: ', intersects printString, ' ', mouseIndex, String cr.
				].
			Transcript show: String cr, 'intersects: ', intersects printString, ' ', mouseIndex, String cr.
			mouse alive: intersects.
			intersects ifTrue: [
				mouse alive: false.
				mice remove: mouse.
				self removeMorph: (mouse morph).
				]
				ifFalse: [
						self placeMorph: mouse morph at: mouse position.
					].
			]
	"AI"
	"make sure to filter out the player in mice/cats, by using e.g. cats at: catindex !== player, adding the player to these lists makes rendering and collision detection a lot easier"