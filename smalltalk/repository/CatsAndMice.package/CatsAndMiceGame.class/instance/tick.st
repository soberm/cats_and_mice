as yet unclassified
tick
	|intersects removableMice|
	"Main logic of the game"
	removableMice := OrderedCollection new.

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
				].
			Transcript show: String cr, 'intersects: ', intersects printString, ' ', mouseIndex, String cr.
			mouse alive: intersects.
			intersects ifTrue: [
				mouse alive: false.
				removableMice add: mouse.
				self removeMorph: (mouse morph).
				]
				ifFalse: [
						self placeMorph: mouse morph at: mouse position.
					].
			].
		1 to: cats size do: [
				:catIndex |
						|cat|
						cat := cats at: catIndex.
						self placeMorph: cat morph at: cat position.
			].
		1 to: removableMice size do: [
				:rmMouseIndex |
					|mouse|
					mouse := removableMice at: rmMouseIndex.
					mice remove: mouse.
			]
	"AI"
	"make sure to filter out the player in mice/cats, by using e.g. cats at: catindex !== player, adding the player to these lists makes rendering and collision detection a lot easier"