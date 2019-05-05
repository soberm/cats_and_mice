window events
setWindowSizeToGridSize: anInt
	self
		width: (anInt * gridWidth + 150);
		height: (anInt * gridHeight).