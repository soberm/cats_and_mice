note
	description: "Summary description for {POINT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	POINT

create
	make

feature -- constants
	MIN_X: INTEGER = 1
	MAX_X: INTEGER = 80
	MIN_Y: INTEGER = 1
	MAX_Y: INTEGER = 25

feature -- fields
	x: INTEGER assign setX
	y: INTEGER assign setY

feature -- initialization
	make (initX: INTEGER; initY: INTEGER)
		do
			x := initX
			y := initY
		end

feature --setter
	setX (newX: INTEGER)
		do
			x := newX
		end

	setY (newY: INTEGER)
		do
			y := newY
		end

end
