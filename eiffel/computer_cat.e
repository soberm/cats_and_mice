note
	description: "Summary description for {COMPUTER_CAT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	COMPUTER_CAT

inherit
	PLAYER
	CAT

create
	make

feature -- initialization
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
			x_pos := x_pos +1
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
