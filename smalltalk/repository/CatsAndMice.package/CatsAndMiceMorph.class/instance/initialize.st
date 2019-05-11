initialize-release
initialize
	"initializes this instance"
	super initialize.
	self image: 	(PNGReadWriter formFromFileNamed: filename).