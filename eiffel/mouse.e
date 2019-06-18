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
	alive : BOOLEAN
	target_subway : SUBWAY
	current_subway : detachable SUBWAY
	-- maybe put that field into the subway -> mouse updates it after entry
	known_cat_positions : HASH_TABLE[POINT, INTEGER]

feature -- overriden inherited methods
	init
		do
			create known_cat_positions.make (2)
			speed := 1
			alive := TRUE
		end

	identity_symbol : CHARACTER
		do
			RESULT := 'M'
		end

feature -- mouse specific methods
	is_alive : BOOLEAN
		do
			RESULT := alive
		end

	kill_mouse
		do
			alive := FALSE
		end

	set_current_subway (cs : detachable SUBWAY)
		do
			current_subway := cs
		end

	set_target_subway (ts : SUBWAY)
		do
			target_subway := ts
		end

	inform_about_cats(info: HASH_TABLE[POINT, INTEGER])
		do
			known_cat_positions := info
		end

	finished : BOOLEAN
		do
			RESULT := (current_subway /= Void) and (current_subway = target_subway)
		end
end
