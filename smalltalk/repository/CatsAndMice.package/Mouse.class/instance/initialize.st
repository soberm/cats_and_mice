initialize-release
initialize
	stepLength := 3.
	direction := 0 @ 0.
	visitedSubways := OrderedCollection new.
	morph := MouseMorph new.
	nextGoalX := nil.
	nextGoalY := nil.
	alive := true.
	enterOrExit := false