as yet unclassified
moveCharacters
	1 to: mice size do: [
			 :mouseIndex | 
			|mouse|
		
			mouse := mice at: mouseIndex.
			mouse move.
	].
	1 to: cats size do: [
		:catIndex |
			|cat|
			cat := cats at: catIndex.
			cat move.
		].
	1 to: subways size do: [
		:subwayIndex |
			|subway mice|
			subway := subways at: subwayIndex.
			mice := subway mice.
			1 to: mice size do: [
				 :mouseIndex | 
				|mouse|
			
				mouse := mice at: mouseIndex.
				mouse move.
			].
		].
	"AI"
	"make sure to filter out the player in mice/cats, by using e.g. cats at: catindex !== player, adding the player to these lists makes rendering and collision detection a lot easier"