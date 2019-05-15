access
enter: aMouse at: position
	"comment stating purpose of message"
	mice add: aMouse.
	aMouse position: position + (0 @ (aMouse morph height/2)).
	aMouse enterOrExit: false.