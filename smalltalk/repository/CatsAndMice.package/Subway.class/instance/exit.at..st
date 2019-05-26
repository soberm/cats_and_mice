access
exit: aMouse at: position 
	aMouse position: position + (0 @ (aMouse morph height / 2)).
	aMouse enterOrExit: false.
	aMouse nextGoalX: nil.
	aMouse nextGoalY: nil