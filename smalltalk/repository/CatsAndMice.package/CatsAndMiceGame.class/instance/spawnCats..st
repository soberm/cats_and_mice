as yet unclassified
spawnCats: count 
	"spawn the specified amount of cats on the playing field"
	| cat x y height width |
	count
		timesRepeat: [cat := Cat new.
			height := window height - cat morph height.
			width := window width - cat morph width.
			x := rand nextInt: width.
			y := rand nextInt: height.
			cat position: x @ y.
			cats add: cat.
			self placeMorph: cat morph at: x @ y]