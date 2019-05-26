initialize-release
initialize
	super initialize.
	self
		image: (PNGReadWriter formFromFileNamed: filename)