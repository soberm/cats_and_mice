Snakes
tick
	"Main logic of the game"
	| effects |
	snakeList size = 1
		ifTrue: [^snakeList first id].
		
	snakeList size <= 0
		ifTrue: [^-1].
	
	((rand nextInt: 20) = 1)
		ifTrue: [self placeNewItem].
	
	snakeList do: [:snake | snake tickBeginPhase].
	
	effects := OrderedCollection new.
	snakeList do:[:snake |
		"check if snakes bites item"
		fieldList do:[:passive |
			(snake == passive)
				ifFalse: [
					(passive isBittenBy: snake)
						ifTrue: [
							effects add: (passive bittenBy: snake).
						].
					(passive isTouchedBy: snake)
						ifTrue: [
							effects add: (passive touchedBy: snake).
						].						
				].
		].
	
		snakeList do:[:passive |
			(snake == passive)
				ifFalse: [
					(passive isBittenBy: snake)
						ifTrue: [
							effects add: (passive bittenBy: snake).
						].
					(passive isTouchedBy: snake)
						ifTrue: [
							effects add: (passive touchedBy: snake).
						].						
				].
		].
	].
	effects do:[:runnable |
		runnable value.
	].
	
	snakeList do: [:snake | snake tickEndPhase].
	
	
	snakeList size = 1
		ifTrue: [^snakeList first id].
	
