as yet unclassified
moveCats
	1
		to: cats size
		do: [:catIndex | 
			| cat |
			cat := cats at: catIndex.
			cat == player
				ifFalse: [self aiMoveCat: cat].
			cat move: self window]