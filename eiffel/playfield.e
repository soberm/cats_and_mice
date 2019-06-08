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
	cat_bots: LINKED_LIST [COMPUTER_CAT]
	mouse_bots: LINKED_LIST [COMPUTER_MOUSE]
	subways: LINKED_LIST [SUBWAY]

feature -- initialization
	make (cat_players : INTEGER; mouse_players : INTEGER; u : USER; s: LINKED_LIST [SUBWAY])
		require
			positive_cat_players: cat_players > 0
			positive_mouse_players: mouse_players > 0
			existing_subways: s.count > 0
		do
			user := u
			create cat_bots.make
			create mouse_bots.make
			create subways.make
			subways := s
			-- TODO: initialize cat and mouse players
		end

feature -- helper functions
	add_subway (s: SUBWAY)
		require
			subway_not_void: s /= Void
		do
			subways.extend(s)
		ensure
			subway_added: subways.count = old subways.count + 1
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
			s_cnt: INTEGER
			s_min: INTEGER
			s_max: INTEGER
			field : ARRAY2[CHARACTER]
		do
			create field.make_filled(' ', MAX_Y_POS, MAX_X_POS)

			-- TODO iterate over all players and draw symbol
			field.put (user.get_symbol, user.y_pos, user.x_pos)

			-- TODO add subways to field
			across subways as subway loop
				if (subway.item.x1 = subway.item.x2) then
					s_min := subway.item.y1
					s_max := subway.item.y2
					field.put ('-', s_min-1, subway.item.x1)
					field.put ('-', s_max+1, subway.item.x1)
				else
					s_min := subway.item.x1
					s_max := subway.item.x2
					field.put ('|', subway.item.y1, s_min-1)
					field.put ('|', subway.item.y1, s_max+1)
				end

				from
					s_cnt := s_min
				until
					s_cnt > s_max
				loop
					if (subway.item.x1 = subway.item.x2) then
						field.put ('|', s_cnt, subway.item.x1+1)
						field.put ('|', s_cnt, subway.item.x1-1)
					else
						field.put ('-', subway.item.y1+1, s_cnt)
						field.put ('-', subway.item.y1-1, s_cnt)
					end
					s_cnt := s_cnt + 1
				end
			end

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
