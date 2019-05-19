access
knownCatLocations: aList 
	| tmpList |
	tmpList := OrderedCollection new.
	1 to: aList size do: [
		:catIndex |
			| cat copy |
			cat := aList at: catIndex.
			copy := Cat new.
			copy position: cat position x @ cat position y.
			tmpList add: copy.
		].
	knownCatLocations := tmpList.