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
			RESULT := calculuate_distance_to_intern(point.x, point.y)
		end

	calculuate_distance_to_intern (pX: INTEGER; pY: INTEGER): INTEGER
		do
			RESULT := math.sqrt ((pX - x) ^ 2 + (pY - y) ^ 2).truncated_to_integer
		end

	calculate_next_position (point: POINT): POINT
		local
			top: INTEGER
			down: INTEGER
			left: INTEGER
			right: INTEGER
			distTop: INTEGER
			distDown: INTEGER
			distLeft: INTEGER
			distRight: INTEGER
			pointTop: POINT
			pointDown: POINT
			pointLeft: POINT
			pointRight: POINT
		do
			top := y - 1
			down := y + 1
			left := x + 1
			right := x - 1

			distRight := point.calculuate_distance_to_intern (right, y)
			distLeft := point.calculuate_distance_to_intern (left, y)
			distDown := point.calculuate_distance_to_intern (x, down)
			distTop := point.calculuate_distance_to_intern (x, top)

			create pointTop.make (x, top)
			create pointDown.make (x, down)
			create pointLeft.make (left, y)
			create pointRight.make (right, y)

			if is_minimum (distRight, distLeft, distTop, distDown) and pointRight.is_valid then
				RESULT := pointRight

			elseif is_minimum (distLeft, distTop, distDown, distRight) and pointLeft.is_valid then
				RESULT := pointLeft

			elseif is_minimum (distTop, distDown, distRight, distLeft) and pointTop.is_valid then
				RESULT := pointTop

			elseif is_minimum(distDown, distRight, distLeft, distTop) and pointDown.is_valid then
				RESULT := pointDown

			else
				RESULT := point
			end
		end

	is_minimum (min: INTEGER; a: INTEGER; b: INTEGER; c: INTEGER): BOOLEAN
		do
			RESULT := min <= a and min <= b and min <= c
		end

	is_equal (o: POINT): BOOLEAN
		do
			RESULT := x = o.x and y = o.y
		end

	is_valid: BOOLEAN
		do
			RESULT := x >= MIN_X and x <= MAX_X and y >= MIN_Y and y <= MAX_Y
		end

end
