note
	description: "Summary description for {CHILDREN_SWIMMING_POOL}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	CHILDREN_SWIMMING_POOL

inherit
	SWIMMING_POOL
		redefine
			do_swim,
			do_swim_v2
		end

create
	child_pool_make

feature
	child_pool_make
	do
		length := 5
	end

feature
	do_swim (person: CHILD; depth: DOUBLE) : INTEGER
--	require else
--		valid_child_depth: depth > 0 and depth < 1.5
	do
		RESULT := person.enter_child_pool_and_swim(depth)
--	ensure then
--		max_child_distance: RESULT < 25 -- the number of times a person was swimming the whole pool distance
	end

	do_swim_v2 (person: CHILD; depth: DOUBLE) : INTEGER
	require else
		max_child_depth: depth > 0 and depth < person.max_depth
	do
		RESULT := person.enter_pool_and_swim_v2(depth)
	ensure then
		max_child_distance: RESULT < person.max_lengths -- the number of times a person was swimming the whole pool distance
	end

end
