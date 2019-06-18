note
	description: "Summary description for {POINT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	POINT

inherit
    ANY
        redefine
            is_equal
        end

create
	make

feature -- constants
	MIN_X: INTEGER = 1
	MAX_X: INTEGER = 80
	MIN_Y: INTEGER = 1
	MAX_Y: INTEGER = 25

feature -- fields
	x: INTEGER assign setX
	y: INTEGER assign setY
	math: DOUBLE_MATH

feature -- initialization
	make (initX: INTEGER; initY: INTEGER)
		do
			x := initX
			y := initY
			create math
		end

feature --setter
	setX (newX: INTEGER)
		do
			x := newX
		end

	setY (newY: INTEGER)
		do
			y := newY
		end

feature
	calculuate_distance_to (point: POINT): INTEGER
		do
			RESULT := math.sqrt((point.x -x)^2 + (point.y -y)^2).truncated_to_integer
		end

	calculuate_distance_to_intern (pX: INTEGER; pY: INTEGER): INTEGER
		do
			RESULT := math.sqrt((pX -x)^2 + (pY -y)^2).truncated_to_integer
		end

	calculate_next_position(point: POINT): POINT
		local
			distTop: INTEGER
			distDown: INTEGER
			distLeft: INTEGER
			distRight: INTEGER
			tmp: POINT
		do
			distRight := point.calculuate_distance_to_intern((x+1), y)
			distLeft := point.calculuate_distance_to_intern((x-1), y)
			distTop := point.calculuate_distance_to_intern(x, (y+1))
			distDown := point.calculuate_distance_to_intern(x, (y-1))

			if is_minimum(distRight, distLeft, distTop, distDown) then
				create tmp.make ((x+1), y)
				RESULT := tmp

			elseif is_minimum(distLeft, distTop, distDown, distRight) then
				create tmp.make ((x-1), y)
				RESULT := tmp

			elseif is_minimum(distTop, distDown, distRight, distLeft) then
				create tmp.make (x, (y+1))
				RESULT := tmp

			else --if is_minimum(distDown, distRight, distLeft, distTop) then
				create tmp.make (x, (y-1))
				RESULT := tmp
			end
		end

	is_minimum(min: INTEGER; a: INTEGER; b: INTEGER; c: INTEGER): BOOLEAN
		do
			RESULT := min < a and min < b and min < c
		end

	is_equal(o: POINT): BOOLEAN
		do
			RESULT := x = o.x and y = o.y
		end
end
