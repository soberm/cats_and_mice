note
	description: "Summary description for {USER_CAT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	USER_CAT

inherit
	USER
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
			RESULT := true
		end

end
