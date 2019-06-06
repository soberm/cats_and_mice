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
	-- TODO change type to subway
	target_subway : INTEGER
	current_subway : INTEGER


feature -- overriden inherited methods
	init
		do
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

	set_current_subway (cs : INTEGER)
		do
			current_subway := cs
		end

	set_target_subway (ts : INTEGER)
		do
			target_subway := ts
		end

end
