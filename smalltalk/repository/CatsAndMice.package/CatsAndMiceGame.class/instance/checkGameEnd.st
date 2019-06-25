as yet unclassified
checkGameEnd
	"determine if the game has ended by checking for empty subways and if mice are left on the overworld"
	^ self checkForEmptySubways & (mice size = 0)