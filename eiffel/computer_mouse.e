note
	description: "Summary description for {COMPUTER_MOUSE}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	COMPUTER_MOUSE

inherit
	PLAYER
	MOUSE

create
	make

feature --initialization
	make (x: INTEGER; y: INTEGER)
		do
			init
			x_pos := x
			y_pos := y
		end

feature -- overridden inherited features
	move
		do
			-- TODO: implement "AI" algorithm
		end

	get_speed : INTEGER
		do
			RESULT := speed
		end

	get_symbol : CHARACTER
		do
			RESULT := identity_symbol
		end


end
