note
	description: "Summary description for {SUBWAY}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	SUBWAY

create
	make

feature -- constants
	MIN_X_POS: INTEGER = 1
	MAX_X_POS: INTEGER = 80
	MIN_Y_POS: INTEGER = 1
	MAX_Y_POS: INTEGER = 25

feature -- fields
	x1: INTEGER
	y1: INTEGER
	x2: INTEGER
	y2: INTEGER
	is_target: BOOLEAN


feature --initialization
	make (x1_init: INTEGER; y1_init: INTEGER; x2_init: INTEGER; y2_init: INTEGER; t: BOOLEAN)
		require
			valid_x1_init: x1_init > MIN_X_POS + 1 and x1_init < MAX_X_POS - 1
			valid_x2_init: x2_init > MIN_X_POS + 1 and x2_init < MAX_X_POS - 1
			valid_y1_init: y1_init > MIN_Y_POS + 1 and y1_init < MAX_Y_POS - 1
			valid_y2_init: y2_init > MIN_Y_POS + 1 and y2_init < MAX_Y_POS - 1
			valid_x_init: x2_init >= x1_init
			valid_y_init: y2_init >= y1_init
		do
			x1 := x1_init
			y1 := y1_init
			x2 := x2_init
			y2 := y2_init
			is_target := t
		end

feature
	on_entry_or_exit (p: PLAYER): BOOLEAN
		do
			RESULT := (x1 = p.x_pos and y1 = p.y_pos) or (x2 = p.x_pos and y2 = p.y_pos)
		end


invariant
	valid_x1: x1 > MIN_X_POS + 1 and x1 < MAX_X_POS - 1
	valid_x2: x2 > MIN_X_POS + 1 and x2 < MAX_X_POS - 1
	valid_y1: y1 > MIN_Y_POS + 1 and y1 < MAX_Y_POS - 1
	valid_y2: y2 > MIN_Y_POS + 1 and y2 < MAX_Y_POS - 1
	vaild_x_coordinate: x2 >= x1
	valid_y_coordinate: y2 >= y1

end
