note
	description: "Summary description for {CAT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

deferred class
	CAT

inherit
	PLAYER_TYPE

feature --fields
	eaten_mice : INTEGER

feature -- overriden inherited methods
	init
		do
			speed := 2
		end

	identity_symbol : CHARACTER
		do
			RESULT := 'C'
		end

feature -- cat specific methods
	increment_eaten_mice
		do
			eaten_mice := eaten_mice + 1
		end

invariant
	valid_eaten_mice: eaten_mice >= 0

end
