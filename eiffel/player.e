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
	x_pos: INTEGER
	y_pos: INTEGER

feature --methods implemented by inherited classes
	move
		deferred
		end

	get_speed : INTEGER
		deferred
		end

	get_symbol : CHARACTER
		deferred
		end

feature -- setter methods
	set_x_pos (newx: INTEGER)
		require
			newx >= MIN_X_POS and newx <= MAX_X_POS
		do
			x_pos := newx

		ensure -- maybe unnecessary because of invariant?
			x_pos >= MIN_X_POS and x_pos <= MAX_X_POS

		end

	set_y_pos (newy: INTEGER)
		require
			newy >= MIN_Y_POS and newy <= MAX_Y_POS
		do
			y_pos := newy

		ensure -- maybe unnecessary because of invariant?
			y_pos >= MIN_Y_POS and y_pos <= MAX_Y_POS

		end

invariant
	valid_x_pos: x_pos >= MIN_X_POS and x_pos <= MAX_X_POS
	valid_y_pos: y_pos >= MIN_Y_POS and y_pos <= MAX_Y_POS

end
