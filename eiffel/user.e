note
	description: "Summary description for {USER}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

deferred class
	USER

inherit
	PLAYER

feature -- implement deferred methods
	move
		local
			read_char : CHARACTER
			y_tmp : INTEGER
			x_tmp : INTEGER
		do
			read_char := get_char
			y_tmp := y_pos
			x_tmp := x_pos

			if (read_char = 'w') and y_pos > MIN_Y_POS then
				y_tmp := y_pos - 1
			elseif (read_char = 's') and y_pos < MAX_Y_POS then
				y_tmp := y_pos + 1
			elseif (read_char = 'a') and x_pos > MIN_X_POS then
				x_tmp := x_pos - 1
			elseif (read_char = 'd') and x_pos < MAX_X_POS then
				x_tmp := x_pos + 1
			end

			if validate_move (x_tmp, y_tmp) then
				y_pos := y_tmp
				x_pos := x_tmp
			end

		end

feature -- user specific features
	validate_move (x_tmp : INTEGER; y_tmp : INTEGER) : BOOLEAN
		deferred
		end

feature {NONE}
	get_char : CHARACTER
		external "C inline use <stdio.h>"
			alias "return getchar()"
		end
end
