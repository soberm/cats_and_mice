initialize-release
initialize
	"initialize the morph by reading the right image from a file"
	super initialize.
	self
		image: (PNGReadWriter formFromFileNamed: filename)