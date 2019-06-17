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
	entrance1: POINT
	entrance2: POINT
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
		local
			p1: POINT
			p2: POINT
		do
			create p1.make (x1_init, y1_init)
			create p2.make (x2_init, y2_init)

			entrance1 := p1
			entrance2 := p2
			is_target := t
		end

feature
	is_vertical: BOOLEAN
		do
			RESULT := (entrance1.x = entrance2.x)
		end

	is_horizontal: BOOLEAN
		do
			RESULT := (entrance1.y = entrance2.y)
		end

	on_entry_or_exit (p: PLAYER): BOOLEAN
		do
			RESULT := (entrance1 = p.position) or (entrance2 = p.position)
		end

	calculuate_distance_to (point: POINT): INTEGER
		local
			dist1: INTEGER
			dist2: INTEGER
		do
			dist1 := entrance1.calculuate_distance_to (point)
			dist2 := entrance2.calculuate_distance_to (point)

			if(dist1 < dist2) then
				RESULT := dist1
			else
				RESULT := dist2

		end
end

invariant
	valid_x1: entrance1.x > MIN_X_POS + 1 and entrance1.x < MAX_X_POS - 1
	valid_x2: entrance2.x > MIN_X_POS + 1 and entrance2.x < MAX_X_POS - 1
	valid_y1: entrance1.y > MIN_Y_POS + 1 and entrance1.y < MAX_Y_POS - 1
	valid_y2: entrance2.y > MIN_Y_POS + 1 and entrance2.y < MAX_Y_POS - 1
	vaild_x_coordinate: entrance2.x >= entrance1.x
	valid_y_coordinate: entrance2.y >= entrance1.y

end
