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
			tmp : POINT
			test: BOOLEAN
		do
			read_char := get_char
			tmp := position.deep_twin

			if (read_char = 'w') and position.y > MIN_Y_POS then
				tmp.y := position.y - 1
			elseif (read_char = 's') and position.y < MAX_Y_POS then
				tmp.y := position.y + 1
			elseif (read_char = 'a') and position.x > MIN_X_POS then
				tmp.x := position.x - 1
			elseif (read_char = 'd') and position.x < MAX_X_POS then
				tmp.x := position.x + 1
			end

			if validate_move (tmp) then
				position := tmp
			end

		end

feature -- user specific features
	validate_move (tmp : POINT) : BOOLEAN
		deferred
		end

feature {NONE}
	get_char : CHARACTER
		external "C inline use <stdio.h>"
			alias "return getchar()"
		end
end
