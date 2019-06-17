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
		local
			initPosition: POINT
		do
			create initPosition.make (x, y)
			init
			position := initPosition
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

	validate_move (tmp : POINT) : BOOLEAN
		do
			-- check for subway restriction
			RESULT := TRUE
		end

end
