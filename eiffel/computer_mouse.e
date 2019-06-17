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
		local
			initPosition: POINT
		do
			create initPosition.make (x, y)
			init
			position := initPosition
		end

feature -- overridden inherited features
	move
		do
			-- TODO: implement "AI" algorithm
			position.y := position.y +1
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
