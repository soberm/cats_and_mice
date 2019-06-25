as yet unclassified
spawnMice: count 
	"spawn the specified amount of mice on the playing field"
	| mouse x y height width |
	count
		timesRepeat: [mouse := Mouse new.
			height := window height - mouse morph height.
			width := window width - mouse morph width.
			x := rand nextInt: width.
			y := rand nextInt: height.
			mouse position: x @ y.
			mouse parent: window.
			mice add: mouse.
			self placeMorph: mouse morph at: x @ y]