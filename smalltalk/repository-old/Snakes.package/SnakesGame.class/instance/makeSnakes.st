private
makeSnakes
	| snake1 snake2 list1 list2 |
	list1 := OrderedCollection new.
	list1
		add: 4@1;
		add: 3@1;
		add: 2@1.
	snake1 := Snake
		newWithPlayingField: self
		id: 1
		direction: 1@0
		positions: list1.
			
	list2 := OrderedCollection new.
	list2
		add: 4@10;
		add: 3@10;
		add: 2@10.
	snake2 := Snake
		newWithPlayingField: self
		id: 2
		direction: 1@0
		positions: list2.
			
	snakeList add: snake1; add: snake2.
			
		