note
	description: "Summary description for {USER_MOUSE}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	USER_MOUSE

inherit
	MOUSE
	USER

create
	make

feature --initialization
	make (x: INTEGER; y: INTEGER)
		do
			init
			x_pos := x
			y_pos := y
		end

feature -- overriden inherited features
	get_speed : INTEGER
		do
			RESULT := speed
		end

	get_symbol : CHARACTER
		do
			RESULT := identity_symbol
		end

	validate_move (x_tmp : INTEGER; y_tmp : INTEGER) : BOOLEAN
		do
			-- check for subway restriction
			RESULT := TRUE
		end

end
