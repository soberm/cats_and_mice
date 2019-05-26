as yet unclassified
createSubways: count withMinLength: minLength isCat: isCat
	| subway i x1 x2 y1 y2 z width height |
	
	height := window height - 120.
	width := window width - 120.
	
	i := 0.
	[i < count] whileTrue: [
		
		z := rand nextInt: 2.
		z = 1 
		ifTrue: [
			"sideways"
			x1 := (0 to: width - minLength) atRandom.
			x2 := (x1 to: width) atRandom.
			
			(x2 - x1) < minLength
			ifTrue: [
				x2 := x1 + minLength.	
			].
			
			y1 := (0 to: height) atRandom.
			y2 := y1.
		]
		ifFalse: [
			y1 := (0 to: height - minLength) atRandom.
			y2 := (y1 to: height) atRandom.
			
			(y2 - y1) < minLength
			ifTrue: [
				y2 := y1 + minLength.	
			].
			
			x1 := (0 to:  width) atRandom.
			x2 := x1.
		].
		
		"TODO: Check if subway intersects with others"
		
		subway := Subway createFrom: x1@y1 to: x2@y2.
	
		isCat
			ifFalse: [
				window placeMorph: subway path at: subway path position.
			].
		window placeMorph: subway entry1 at: subway entry1 position.
		window placeMorph: subway entry2 at: subway entry2 position.
		
		i := i + 1.
		subways add: subway.
	].
	goal := subways at: (rand nextInt: subways size).
	goal isGoal: true.
	

	


			