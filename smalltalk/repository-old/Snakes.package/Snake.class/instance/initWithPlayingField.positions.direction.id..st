private
initWithPlayingField: pf positions: p direction: d id: i
	"comment stating purpose of message"

	playingField := pf.
	direction := d.
	id := i.
	positions := p collect: [:point |
		{point. self makeBodyMorph} 
	].

	positions first at:2 put: self makeHeadMorph.

	positions do: [:pair |
		playingField placeMorph: (pair at: 2) at: (pair at: 1). 
	].
	