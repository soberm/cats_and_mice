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
			ticks: INTEGER
			subways: LINKED_LIST [SUBWAY]
			s1: SUBWAY
			s2: SUBWAY
			-- Run application.
		do
			io.put_string ("Start cats and mice!%N")

			prepare_input
			create subways.make
			create s1.make (10, 10, 10, 16, False)
			create s2.make (30, 14, 50, 14, True)
			subways.extend (s1)
			subways.extend (s2)

			create playfield.make(1, 1, 'c', subways, s1)

			from
				ticks := 0
			invariant
				ticks >= 0
			until
				ticks >= 150 or playfield.game_finished or playfield.is_user_dead
			loop
				--read_char := get_char
				playfield.execute_game_step
				clear_screen
				playfield.display_playfield

				sleep (1000 * 1000 * 100)
				ticks := ticks + 1
			end

			reset_input

			-- TODO add "Finished" screen with stats

			io.new_line
			io.put_string ("Finished!%N")
		end

feature {NONE}
	clear_screen
			-- Clears the console
	    external "C inline use <stdlib.h>"
	        alias "system(%"clear%");"
	    end

	prepare_input
		do
			set_non_blocking
			make_term_raw
		end

	reset_input
		do
			make_term_canonical
		end

	set_non_blocking
		external "C inline use <fcntl.h>"
			alias "fcntl(0, F_SETFL, O_NONBLOCK);"
		end

	make_term_raw
		external "C inline use <termios.h>"
			alias "struct termios term; tcgetattr(0, &term); term.c_lflag &= ~(ICANON | ECHO); term.c_cc[VTIME] = 0; term.c_cc[VMIN] = 1; tcsetattr(0, 0, &term);"
		end

	make_term_canonical
		external "C inline use <termios.h>"
			alias "struct termios term; tcgetattr(0, &term); term.c_lflag |= (ICANON | ECHO); tcsetattr(0, 0, &term);"
		end
end
