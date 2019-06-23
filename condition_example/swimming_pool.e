note
	description: "Summary description for {SWIMMING_POOL}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	SWIMMING_POOL

create
	make

feature
	length: INTEGER

feature
	make
		do
			length := 50
		end

feature
	do_swim (person: ADULT; depth: DOUBLE) : INTEGER
--	require
--		valid_adult_depth: depth > 0 and depth < 3
	do
		RESULT := person.enter_pool_and_swim(depth)
--	ensure
--		max_distance: RESULT < 15 -- the number of times a person was swimming the whole pool distance
	end

	do_swim_v2 (person: ADULT; depth: DOUBLE) : INTEGER
	require
		max_adult_depth: depth > 0 and depth < person.max_depth
	do
		RESULT := person.enter_pool_and_swim_v2(depth)
	ensure then
		max_adult_distance: RESULT < person.max_lengths -- the number of times a person was swimming the whole pool distance
	end

end
