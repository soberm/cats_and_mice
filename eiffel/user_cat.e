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
		local
			initPosition: POINT
		do
			create initPosition.make (x, y)
			init
			position := initPosition
		end

feature -- overriden inherited features

	get_speed: INTEGER
		do
			RESULT := speed
		end

	validate_move (tmp: POINT): BOOLEAN
		do
			RESULT := true
		end

end
