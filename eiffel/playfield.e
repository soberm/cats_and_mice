note
	description: "Summary description for {PLAYFIELD}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	PLAYFIELD

create
	make

feature {NONE} -- constants -> TODO: check how to use constants correctly
	MIN_X_POS: INTEGER = 1
	MAX_X_POS: INTEGER = 80
	MIN_Y_POS: INTEGER = 1
	MAX_Y_POS: INTEGER = 25

feature {NONE} -- fields
	user : USER

feature -- initialization
	make (cat_players : INTEGER; mouse_players : INTEGER; u : USER)
		require
			positive_cat_players: cat_players > 0
			positive_mouse_players: mouse_players > 0
		do
			user := u
			-- TODO: initialize cat and mouse players
		end

feature -- game logic
	execute_game_step
		do
			user.move
		end

feature -- display logic
	display_playfield
		local
			i: INTEGER
			j: INTEGER
			field : ARRAY2[CHARACTER]
		do
			create field.make_filled(' ', MAX_Y_POS, MAX_X_POS)

			-- TODO iterate over all players and draw symbol
			field.put (user.get_symbol, user.y_pos, user.x_pos)

			-- TODO add subways to field

			from
				i := 1
			until
				i > (MAX_Y_POS+2)
			loop
				io.new_line
				from
					j := 1
				until
					j > (MAX_X_POS+2)
				loop
					if ((i = 1) or (i = (MAX_Y_POS+2)) or (j = 1) or (j = (MAX_X_POS+2))) then
						io.put_character ('#')
					else
						io.put_character (field.item ((i-1), (j-1)))
					end
					j := j+1
				end
				i := i+1
			end
		end

end
