note
	description: "Summary description for {MOUSE}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

deferred class
	MOUSE

inherit

	PLAYER_TYPE

feature --fields

	alive: BOOLEAN

	target_subway: SUBWAY

	current_subway: detachable SUBWAY
			-- maybe put that field into the subway -> mouse updates it after entry

	known_cat_positions: LINKED_LIST [POINT]

feature -- overriden inherited methods

	init
		do
			create known_cat_positions.make
			speed := 1
			alive := TRUE
		end

	identity_symbol: CHARACTER
		do
			RESULT := 'M'
		end

feature -- mouse specific methods

	is_alive: BOOLEAN
		do
			RESULT := alive
		end

	kill_mouse
		do
			alive := FALSE
		end

	set_current_subway (cs: detachable SUBWAY)
		do
			current_subway := cs
		end

	set_target_subway (ts: SUBWAY)
		do
			target_subway := ts
		end

	inform_about_cats (info: LINKED_LIST [POINT])
		do
			known_cat_positions := info
		end

	finished: BOOLEAN
		do
			if attached current_subway as curr_sub then
				RESULT := curr_sub.is_equal (target_subway)
			else
				RESULT := FALSE
			end
		end

end
