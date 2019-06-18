note
	description: "Summary description for {PLAYER}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

deferred class
	PLAYER

feature -- constants
	MIN_X_POS: INTEGER = 1
	MAX_X_POS: INTEGER = 80
	MIN_Y_POS: INTEGER = 1
	MAX_Y_POS: INTEGER = 25

feature -- fields
	position: POINT

feature --methods implemented by inherited classes
	move
		deferred
		end

	get_speed : INTEGER
		deferred
		end

feature -- setter methods
	set_new_pos(newPos: Point)
		require
			newPos.x >= MIN_X_POS and newPos.x <= MAX_X_POS
			newPos.y >= MIN_Y_POS and newPos.y <= MAX_Y_POS
		do
			position := newPos

		end

invariant
	valid_x_pos: position.x >= MIN_X_POS and position.x <= MAX_X_POS
	valid_y_pos: position.y >= MIN_Y_POS and position.y <= MAX_Y_POS

end
