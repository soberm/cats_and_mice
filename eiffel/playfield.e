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
	players: LINKED_LIST [PLAYER]
	subways: LINKED_LIST [SUBWAY]
	target_subway: SUBWAY

feature -- initialization
	make (cat_players : INTEGER; mouse_players : INTEGER; user_char : CHARACTER; s: LINKED_LIST [SUBWAY]; target: SUBWAY)
		require
			positive_cat_players: cat_players > 0
			positive_mouse_players: mouse_players > 0
			existing_subways: s.count > 0
		local
			user_cat: USER_CAT
			user_mouse: USER_MOUSE
		do
			create players.make
			create subways.make

			subways := s
			target_subway := target

			if user_char = 'c' then
				create user_cat.make (10, 10)
				players.extend(user_cat)
			else
				create user_mouse.make (10, 10, target_subway)
				players.extend(user_mouse)
			end


			create_bots
		end

	create_bots
		local
			c1: COMPUTER_CAT
			c2: COMPUTER_CAT
			m1: COMPUTER_MOUSE
			m2: COMPUTER_MOUSE
			mouses: LINKED_LIST [COMPUTER_MOUSE]
		do --TODO set proper random positions, generify number of bots

			create mouses.make

			create m1.make (75, 24, subways, target_subway)
			create m2.make (75, 1, subways, target_subway)
			players.extend (m1)
			players.extend (m2)
			mouses.extend (m1)
			mouses.extend (m2)

			create c1.make (1, 24, subways, mouses)
			create c2.make (1, 2, subways, mouses)
			players.extend (c1)
			players.extend (c2)
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

			across players as player loop
				if attached {CAT} player.item as cat then
					player.item.move
				elseif attached {MOUSE} player.item as mouse then
					if (not mouse.finished) and mouse.is_alive then
						player.item.move

						-- handle subways
						if attached mouse.current_subway as curr_sub then
							if not curr_sub.position_in_subway(player.item.position) then
								mouse.set_current_subway (Void)
							end
						else
							across subways as subway loop
								-- every mouse on entry/exit field without
								if subway.item.on_entry_or_exit(player.item) then
									mouse.set_current_subway (subway.item)
								end
							end
						end
					end
				end
			end

			-- check if mouse gets killed
			across players as outer_player loop
				if attached {CAT} outer_player.item as cat then
					across players as inner_player loop
						if attached {MOUSE} inner_player.item as mouse then
							if outer_player.item.position.is_equal(inner_player.item.position) then
							cat.increment_eaten_mice
							mouse.kill_mouse
							end
						end
					end
				end
			end


--			user.move
--			across cats as cat loop
--				cat.item.move
--			end

			-- handle mouse moves

			-- TODO:
					--  - implement valid_move feature for mouse: check subway borders DONE
					--  - remove user as playfield parameter +1
					--  - mouse communication within subway
--			across mice as mouse loop
--				if (not mouse.item.finished) and mouse.item.is_alive then
--					mouse.item.move

--					-- handle subways
--					if attached mouse.item.current_subway as curr_sub then
--						if not curr_sub.position_in_subway(mouse.item.position) then
--							mouse.item.set_current_subway (Void)
--						end
--					else
--						across subways as subway loop
--							-- every mouse on entry/exit field without
--							if subway.item.on_entry_or_exit(mouse.item) then
--								mouse.item.set_current_subway (subway.item)
--							end
--						end
--					end

--					-- check if mouse gets killed
--					across cats as cat loop
--						if cat.item.position.is_equal(mouse.item.position) then
--							cat.item.increment_eaten_mice
--							mouse.item.kill_mouse
--						end
--					end

--				end
--			end
		end

	game_finished: BOOLEAN
		local
			done_cnt: INTEGER
			total_cnt: INTEGER
		do
			done_cnt := 0
			total_cnt := 0

			across players as player loop
				if attached {MOUSE} player.item as mouse then
					if mouse.finished or not mouse.is_alive then
						done_cnt := done_cnt + 1
					end
					total_cnt := total_cnt + 1
				end
			end

--			across mice as mouse loop
--				if mouse.item.finished or not mouse.item.is_alive then
--					done_cnt := done_cnt + 1
--				end
--			end

			RESULT := done_cnt = total_cnt

		end

feature -- display logic
	display_playfield
		local
			field : ARRAY2[CHARACTER]
		do
			create field.make_filled(' ', MAX_Y_POS, MAX_X_POS)

			-- set_player(field)
			set_players(field)

			set_subways(field)
			print_field(field)

		end

	set_players (field : ARRAY2[CHARACTER])
		local
		do
			across players as player loop
				if attached {PLAYER_TYPE} player.item as pl then
					field.put (pl.identity_symbol, player.item.position.y, player.item.position.x)
				end
			end

--			across cats as cat loop
--				if attached {PLAYER} cat.item as pl then
--				field.put (cat.item.identity_symbol, pl.position.y, pl.position.x)
--				end

--			end

--			across mice as mouse loop
--				if mouse.item.is_alive then
--					field.put (mouse.item.identity_symbol, mouse.item.position.y, mouse.item.position.x)
--				end
--			end
		end

--	set_player (field : ARRAY2[CHARACTER])
--		do
--			field.put (user.get_symbol, user.position.y, user.position.x)
--		end

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
