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

	user: USER

	players: LINKED_LIST [PLAYER]

	subways: LINKED_LIST [SUBWAY]

	target_subway: SUBWAY

	random_sequence: RANDOM

feature -- initialization

	make (cat_players: INTEGER; mouse_players: INTEGER; user_char: CHARACTER; s: LINKED_LIST [SUBWAY]; target: SUBWAY)
		require
			positive_cat_players: cat_players >= 0
			positive_mouse_players: mouse_players >= 0
			existing_subways: s.count > 0
		local
			user_cat: USER_CAT
			user_mouse: USER_MOUSE
		do
			create random_sequence.set_seed (1337)
			create players.make
			create subways.make
			subways := s
			target_subway := target
			if user_char = 'c' then
				create user_cat.make (10, 10)
				user := user_cat
			else
				create user_mouse.make (10, 10, target_subway)
				user := user_mouse
			end
			players.extend (user)
			create_bots (mouse_players, cat_players)
		end

	create_bots (mice_cnt: INTEGER; cats_cnt: INTEGER)
		require
			valid_mice_cnt: mice_cnt >= 0
			valid_cats_cnt: cats_cnt >= 0
		local
			cnt: INTEGER
			pc_cat: COMPUTER_CAT
			pc_mouse: COMPUTER_MOUSE
			mice: LINKED_LIST [MOUSE]
		do
			create mice.make
			if attached {MOUSE} user as mu then
				mice.extend (mu)
			end
			from
				cnt := 0
			until
				cnt >= mice_cnt
			loop
				create pc_mouse.make (new_random_x, new_random_y, subways, target_subway)
				mice.extend (pc_mouse)
				players.extend (pc_mouse)
				cnt := cnt + 1
			end
			from
				cnt := 0
			until
				cnt >= cats_cnt
			loop
				create pc_cat.make (new_random_x, new_random_y, subways, mice)
				players.extend (pc_cat)
				cnt := cnt + 1
			end
		end

feature -- helper functions

	add_subway (s: SUBWAY)
		require
			subway_not_void: s /= Void
		do
			subways.extend (s)
		ensure
			subway_added: subways.count = old subways.count + 1
		end

	new_random_x: INTEGER
		do
			random_sequence.forth
			RESULT := random_sequence.item \\ MAX_X_POS + 1
		end

	new_random_y: INTEGER
		do
			random_sequence.forth
			RESULT := random_sequence.item \\ MAX_Y_POS + 1
		end

feature -- game logic

	execute_game_step (tick: INTEGER)
		local
			cats: LINKED_LIST [POINT]
		do
			cats := create_cat_list
			across
				players as player
			loop
				if attached {CAT} player.item as cat then
					player.item.move (tick)
				elseif attached {MOUSE} player.item as mouse then
					move_mice (mouse, tick, cats)
				end
			end
			kill_mice
		end

	move_mice (mouse: MOUSE; tick: INTEGER; cats: LINKED_LIST [POINT])
		require
			tick >= 0
			mouse.position.is_valid
		do
			if (not mouse.finished) and mouse.is_alive then
				mouse.move (tick)

					-- handle subways
				if attached mouse.current_subway as curr_sub then --mouse is in subway
					if not curr_sub.position_in_subway (mouse.position) then --mouse exists subway
						mouse.set_current_subway (Void)
					end
				else
					across
						subways as subway
					loop
							-- every mouse on entry/exit field without
						if subway.item.on_entry_or_exit (mouse) then
							mouse.set_current_subway (subway.item)
							mouse.inform_about_cats (cats)
						end
					end
				end
			end
		end

	game_finished: BOOLEAN
		local
			done_cnt: INTEGER
			total_cnt: INTEGER
		do
			done_cnt := 0
			total_cnt := 0
			across
				players as player
			loop
				if attached {MOUSE} player.item as mouse then
					if mouse.finished or not mouse.is_alive then
						done_cnt := done_cnt + 1
					end
					total_cnt := total_cnt + 1
				end
			end
			RESULT := done_cnt = total_cnt
		end

	kill_mice
			-- check if mouse gets killed
		do
			across
				players as outer_player
			loop
				if attached {CAT} outer_player.item as cat then
					across
						players as inner_player
					loop
						if attached {MOUSE} inner_player.item as mouse then
							if not attached mouse.current_subway and mouse.is_alive and outer_player.item.position.is_equal (inner_player.item.position) then
								cat.increment_eaten_mice
								mouse.kill_mouse
							end
						end
					end
				end
			end
		end

feature --game helper

	is_user_alive: BOOLEAN
		do
			if attached {MOUSE} user as mu then
				RESULT := mu.is_alive
			else
				RESULT := TRUE
			end
		end

	create_cat_list: LINKED_LIST [POINT]
		local
			cats: LINKED_LIST [POINT]
		do
			create cats.make
			across
				players as player
			loop
				if attached {CAT} player.item as cat then
					cats.extend (cat.position)
				end
			end
			RESULT := cats
		end

feature -- display logic

	display_playfield
		local
			field: ARRAY2 [CHARACTER]
		do
			create field.make_filled (' ', MAX_Y_POS, MAX_X_POS)
			set_subways (field)
			if attached {MOUSE} user as mu then
				set_players_for_mouse_player (field)
			else
				set_players_for_cat_player (field)
			end
			print_field (field)
		end

	set_players_for_mouse_player (field: ARRAY2 [CHARACTER])
		do
			across
				players as player
			loop
				if attached {PLAYER_TYPE} player.item as pl then
					if attached {MOUSE} player.item as mp then
						if mp.is_alive then
							field.put (pl.identity_symbol, player.item.position.y, player.item.position.x)
						end
					else
						field.put (pl.identity_symbol, player.item.position.y, player.item.position.x)
					end
				end
			end
		end

	set_players_for_cat_player (field: ARRAY2 [CHARACTER])
		do
			across
				players as player
			loop
				if attached {MOUSE} player.item as mouse then
					if not attached mouse.current_subway and mouse.is_alive then
						field.put (mouse.identity_symbol, player.item.position.y, player.item.position.x)
					end
				elseif attached {CAT} player.item as cat then
					field.put (cat.identity_symbol, player.item.position.y, player.item.position.x)
				end
			end
		end

	set_subways (field: ARRAY2 [CHARACTER])
		local
			s_cnt: INTEGER
			s_min: INTEGER
			s_max: INTEGER
		do
			across
				subways as subway
			loop
				if (subway.item.is_vertical) then
					s_min := subway.item.entrance1.y
					s_max := subway.item.entrance2.y
					field.put ('-', s_min - 1, subway.item.entrance1.x)
					field.put ('-', s_max + 1, subway.item.entrance1.x)
				else
					s_min := subway.item.entrance1.x
					s_max := subway.item.entrance2.x
					field.put ('|', subway.item.entrance1.y, s_min - 1)
					field.put ('|', subway.item.entrance1.y, s_max + 1)
				end
				if attached {MOUSE} user as mu then
					from
						s_cnt := s_min
					until
						s_cnt > s_max
					loop
						if (subway.item.is_vertical) then
							field.put ('|', s_cnt, subway.item.entrance1.x + 1)
							field.put ('|', s_cnt, subway.item.entrance1.x - 1)
						else
							field.put ('-', subway.item.entrance1.y + 1, s_cnt)
							field.put ('-', subway.item.entrance1.y - 1, s_cnt)
						end
						s_cnt := s_cnt + 1
					end
				else
					if (subway.item.is_vertical) then
						s_min := subway.item.entrance1.y
						s_max := subway.item.entrance2.y
						field.put ('-', s_min + 1, subway.item.entrance1.x)
						field.put ('-', s_max - 1, subway.item.entrance1.x)
						field.put ('|', s_min, subway.item.entrance1.x - 1)
						field.put ('|', s_min, subway.item.entrance1.x + 1)
						field.put ('|', s_max, subway.item.entrance1.x - 1)
						field.put ('|', s_max, subway.item.entrance1.x + 1)
					else
						s_min := subway.item.entrance1.x
						s_max := subway.item.entrance2.x
						field.put ('|', subway.item.entrance1.y, s_min + 1)
						field.put ('|', subway.item.entrance1.y, s_max - 1)
						field.put ('-', subway.item.entrance1.y - 1, s_min)
						field.put ('-', subway.item.entrance1.y + 1, s_min)
						field.put ('-', subway.item.entrance1.y - 1, s_max)
						field.put ('-', subway.item.entrance1.y + 1, s_max)
					end
				end
			end
		end

	print_field (field: ARRAY2 [CHARACTER])
		local
			i: INTEGER
			j: INTEGER
		do
			from
				i := 1
			until
				i > (MAX_Y_POS + 2)
			loop
				io.new_line
				from
					j := 1
				until
					j > (MAX_X_POS + 2)
				loop
					if ((i = 1) or (i = (MAX_Y_POS + 2)) or (j = 1) or (j = (MAX_X_POS + 2))) then
						io.put_character ('#')
					else
						io.put_character (field.item ((i - 1), (j - 1)))
					end
					j := j + 1
				end
				i := i + 1
			end
		end

	print_game_finished
		local
			alive_cnt: INTEGER
			dead_cnt: INTEGER
			max_kills: INTEGER
		do
			io.new_line
			io.put_string ("  $$$$$$  $$  $$    $  $$    $$   $$  $$  $$$$$  $$$   $$")
			io.new_line
			io.put_string ("  $$      $$  $$$   $  $$   $     $$  $$  $$     $$ $  $$")
			io.new_line
			io.put_string ("  $$$$    $$  $$ $  $  $$    $    $$$$$$  $$$$   $$ $  $$")
			io.new_line
			io.put_string ("  $$      $$  $$  $ $  $$      $  $$  $$  $$     $$ $    ")
			io.new_line
			io.put_string ("  $$      $$  $$   $$  $$    $$   $$  $$  $$$$$  $$$   $$")
			io.new_line
			io.new_line
			across
				players as player
			loop
				if attached {MOUSE} player.item as mouse then
					if mouse.is_alive then
						alive_cnt := alive_cnt + 1
					else
						dead_cnt := dead_cnt + 1
					end
				end
			end
			io.put_string ("Statistics:")
			io.new_line
			io.put_string ("Mice still alive: ")
			io.put_integer (alive_cnt)
			io.new_line
			io.put_string ("Killed mice: ")
			io.put_integer (dead_cnt)
			io.new_line
			io.put_string ("The user played ")
			if attached {MOUSE} user as mu then
				io.put_string ("mouse and ")
				if mu.is_alive then
					io.put_string ("survived! Congratulation!")
				else
					io.put_string ("sadly died... Maybe next time!")
				end
			end
			if attached {CAT} user as cu then
				io.put_string ("cat and killed ")
				io.put_integer (cu.eaten_mice)
				io.put_string (" mice!")
			end
			io.new_line
			io.put_string ("Most killed mice by a single cat: ")
			across
				players as player
			loop
				if attached {CAT} player.item as cat then
					if max_kills < cat.eaten_mice then
						max_kills := cat.eaten_mice
					end
				end
			end
			io.put_integer (max_kills)
		end

end
