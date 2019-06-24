note
	description: "Summary description for {PLAYER}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

deferred class
	PLAYER

feature -- fields

	position: POINT

feature -- constants

	MIN_X_POS: INTEGER = 1

	MAX_X_POS: INTEGER = 80

	MIN_Y_POS: INTEGER = 1

	MAX_Y_POS: INTEGER = 25

feature --methods implemented by inherited classes

	move (tick: INTEGER)
		require
			tick >= 0
		deferred
		end

	get_speed: INTEGER
		deferred
		end

feature -- setter methods

	set_new_pos (newPos: Point)
		require
			newPos.is_valid
		do
			position := newPos
		end

invariant
	position.is_valid

end
