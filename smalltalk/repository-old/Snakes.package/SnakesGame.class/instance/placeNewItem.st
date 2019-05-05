Snakes
placeNewItem
	
	| itemClasses val newItem x y |	
	itemClasses := Item allSubclasses.
	val := rand nextInt: itemClasses capacity.
	newItem := ((itemClasses at: val) new).
	
	x := rand nextValue \\ self fieldWidth.
	y := rand nextValue \\ self fieldHeight.
	
	self addItem: newItem at: x@y.