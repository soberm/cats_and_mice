note
	description: "Summary description for {PLAYER_TYPE}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

deferred class
	PLAYER_TYPE

inherit

	PLAYER

feature --fields

	speed: INTEGER

feature

	init
		deferred
		end

	identity_symbol: CHARACTER
		deferred
		end

	set_speed (sp: INTEGER)
		require
			valid_speed: sp >= 0
		do
			speed := sp
		end

invariant
	valid_speed: speed >= 0

end
