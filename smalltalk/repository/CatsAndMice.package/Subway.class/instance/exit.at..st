access
exit: aMouse at: position
	"comment stating purpose of message"
	aMouse position: position + (0 @ (aMouse morph height/2)).
	aMouse enterOrExit: false.