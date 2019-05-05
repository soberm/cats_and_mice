private
addItem: anItem at: point
	fieldList add: anItem.
	anItem placeOnGameField: self at: point. 