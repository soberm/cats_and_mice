as yet unclassified
load: aFilename
	""
	| png |
	
	png := imgsys load: aFilename.
	self new image: png.