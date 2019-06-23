note
	description: "Summary description for {CHILD}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	CHILD

inherit
	ADULT
		redefine
			enter_pool_and_swim_v2
		end

create
	child_make

feature
	child_make
	do
		identity := 'C'
		max_lengths := 25
		max_depth := 1.5
	end

feature
	enter_child_pool_and_swim(depth: DOUBLE): INTEGER
	require else
		child_valid_pool_depth: depth > 0 and depth < 1.5
	do
		RESULT := 20
	ensure then
		child_max_lengths: RESULT < 25
	end

	enter_pool_and_swim_v2(depth: DOUBLE): INTEGER
	require else
		child_valid_pool_depth: depth > 0 and depth < 1.5
	do
		RESULT := 20
	end
end
