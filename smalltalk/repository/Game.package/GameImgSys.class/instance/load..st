as yet unclassified
load: aFilename
	""

	| png |
	png := PNGReadWriter formFromFileNamed: aFilename.
	"png := png nextImage."

	^ png

"	self addMorph: mo".