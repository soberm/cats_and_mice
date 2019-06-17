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
	target_subway: SUBWAY

feature -- initialization
	make (cat_players : INTEGER; mouse_players : INTEGER; u : USER; s: LINKED_LIST [SUBWAY]; target: SUBWAY)
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
			target_subway := target
			create_bots
		end

	create_bots
		local
			c1: COMPUTER_CAT
			c2: COMPUTER_CAT
			m1: COMPUTER_MOUSE
			m2: COMPUTER_MOUSE
		do --TODO set proper random positions, generify number of bots

			create m1.make (75, 24, subways, target_subway)
			create m2.make (75, 1, subways, target_subway)
			mouse_bots.extend (m1)
			mouse_bots.extend (m2)

			create c1.make (1, 24, subways, mouse_bots)
			create c2.make (1, 2, subways, mouse_bots)
			cat_bots.extend (c1)
			cat_bots.extend (c2)
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
			across cat_bots as cat loop
				cat.item.move
			end

			across mouse_bots as mouse loop
				mouse.item.move
			end
		end

feature -- display logic
	display_playfield
		local
			field : ARRAY2[CHARACTER]
		do
			create field.make_filled(' ', MAX_Y_POS, MAX_X_POS)

			set_player(field)
			set_bots(field)

			set_subways(field)
			print_field(field)

		end

	set_bots (field : ARRAY2[CHARACTER])
		local
		do
			across cat_bots as cat loop
				field.put (cat.item.get_symbol, cat.item.position.y, cat.item.position.x)
			end

			across mouse_bots as mouse loop
				field.put (mouse.item.get_symbol, mouse.item.position.y, mouse.item.position.x)
			end
		end

	set_player (field : ARRAY2[CHARACTER])
		do
			field.put (user.get_symbol, user.position.y, user.position.x)
		end

	set_subways(field : ARRAY2[CHARACTER])
		local
			s_cnt: INTEGER
			s_min: INTEGER
			s_max: INTEGER
		do
			across subways as subway loop
				if (subway.item.is_vertical) then
					s_min := subway.item.entrance1.y
					s_max := subway.item.entrance2.y
					field.put ('-', s_min-1, subway.item.entrance1.x)
					field.put ('-', s_max+1, subway.item.entrance1.x)
				else
					s_min := subway.item.entrance1.x
					s_max := subway.item.entrance2.x
					field.put ('|', subway.item.entrance1.y, s_min-1)
					field.put ('|', subway.item.entrance1.y, s_max+1)
				end

				from
					s_cnt := s_min
				until
					s_cnt > s_max
				loop
					if (subway.item.is_vertical) then
						field.put ('|', s_cnt, subway.item.entrance1.x+1)
						field.put ('|', s_cnt, subway.item.entrance1.x-1)
					else
						field.put ('-', subway.item.entrance1.y+1, s_cnt)
						field.put ('-', subway.item.entrance1.y-1, s_cnt)
					end
					s_cnt := s_cnt + 1
				end
			end
		end

	print_field (field : ARRAY2[CHARACTER])
		local
			i: INTEGER
			j: INTEGER
		do
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
