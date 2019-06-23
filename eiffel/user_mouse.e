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
	make (x: INTEGER; y: INTEGER; target: SUBWAY)
		local
			initPosition: POINT
		do
			create initPosition.make (x, y)
			init
			position := initPosition
			target_subway := target
		end

feature -- overriden inherited features
	get_speed : INTEGER
		do
			RESULT := speed
		end

	validate_move (tmp : POINT) : BOOLEAN
		do
			-- check for subway restriction
			if attached current_subway as cr then
				RESULT := cr.move_in_subway(tmp)
			else
				RESULT := TRUE
			end
		end
end
