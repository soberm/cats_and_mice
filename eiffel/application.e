note
	description: "cats_and_mice application root class"
	date: "$Date$"
	revision: "$Revision$"

class
	APPLICATION

inherit
    EXECUTION_ENVIRONMENT

create
	make

feature {NONE} -- Initialization

	make
		local
			playfield: PLAYFIELD
			subways: LINKED_LIST [SUBWAY]
			s1: SUBWAY
			s2: SUBWAY
			player_type: CHARACTER
			mouse_players: INTEGER
			cat_players: INTEGER

			--test
			user_mouse: USER_MOUSE

			-- Run application.
		do
--			reset_input
--			clear_screen
--			io.put_string ("Press Enter to start cats and mice!%N")

--			clear_stdin

--			-- ask user for game parameters
--			io.put_string ("Choose your player type (C = cat,M = mouse): ")
--			io.read_character
--			player_type := io.last_character.as_lower

--			io.put_string ("Choose number of computer cats: ")
--			io.read_integer
--			cat_players := io.last_integer

--			io.put_string ("Choose number of computer mice: ")
--			io.read_integer
--			mouse_players := io.last_integer

--			prepare_input
			create subways.make
			create s1.make (10, 10, 10, 16, False)
			create s2.make (30, 14, 50, 14, True)
			subways.extend (s1)
			subways.extend (s2)

			-- test temp
			create user_mouse.make (10, 10, s2)
			user_mouse.set_current_subway (s2)

			io.put_boolean (user_mouse.finished)
			io.put_boolean (s2.is_equal (s2))

			-- end test temp


--			create playfield.make(cat_players, mouse_players, player_type, subways, s1)

--			-- TODO
--			-- test, test, test...
--			-- input issue when restarting the game DONE
--			-- propagate playfield info when mouse enters subway

--			from

--			until
--				playfield.game_finished or not playfield.is_user_alive
--			loop
--				playfield.execute_game_step
--				clear_screen
--				playfield.display_playfield

--				sleep (1000 * 1000 * 100)
--			end

--			clear_screen
--			reset_input

--			playfield.print_game_finished
		end

feature {NONE}
	clear_screen
			-- Clears the console
	    external "C inline use <stdlib.h>"
	        alias "system(%"clear%");"
	    end

	clear_stdin
		external "C inline use <stdio.h>"
			alias "int c; while ((c = getchar()) != '\n' && c != EOF) { }"
		--	alias "fseek(stdin,0,SEEK_END);"
		end

	prepare_input
		do
			set_non_blocking
			make_term_raw
		end

	reset_input
		do
			reset_non_blocking
			make_term_canonical
		end

	reset_non_blocking
		external "C inline use <fcntl.h>"
			alias "fcntl(0, F_SETFL, 32770)"
		end

	set_non_blocking
		external "C inline use <fcntl.h>"
			alias "fcntl(0, F_SETFL, O_NONBLOCK);"
		end

	make_term_raw
		external "C inline use <termios.h>"
			alias "struct termios term;  tcgetattr(0, &term); term.c_lflag &= ~(ICANON | ECHO);  tcsetattr(0, 0, &term);" --term.c_cc[VTIME] = 0; term.c_cc[VMIN] = 1;
		end

	make_term_canonical
		external "C inline use <termios.h>"
			alias "struct termios term; tcgetattr(0, &term); term.c_lflag |= (ICANON | ECHO); tcsetattr(0, 0, &term);"
		end
end
