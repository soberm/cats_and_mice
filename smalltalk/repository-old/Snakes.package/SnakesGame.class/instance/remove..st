private
remove: itemOrSnake
	"remove the thing from me"
	
	itemOrSnake isSnake
		ifFalse: [ 
			fieldList removeAllSuchThat: [:i | i == itemOrSnake ].
			self removeMorph: (itemOrSnake morph).
		]
		ifTrue: [
			snakeList removeAllSuchThat: [:s | s == itemOrSnake ].
			"TODO remove intermediate morphs"
		]