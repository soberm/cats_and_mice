as yet unclassified
createFrom: position1 to: position2
	"comment stating purpose of message"
	|subway|
	subway := Subway basicNew.
	subway position1: position1.
	subway position2: position2.
	position1 x = position2 x
		ifTrue: [
				subway pathPosition: position1 x @ (position1 y + 30).
			]
		ifFalse: [
				subway pathPosition: (position1 x + 30) @ position1 y.
			].
	subway initialize.
	^subway.