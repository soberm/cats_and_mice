Snakes
moveTail

	positions isEmpty
		ifFalse: [ | oldTail |
			oldTail := positions removeLast.
			oldTail second delete.
		]
	