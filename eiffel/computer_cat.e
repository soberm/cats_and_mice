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
			position.x := position.x +1
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
