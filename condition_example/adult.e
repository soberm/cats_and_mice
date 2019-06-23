note
	description: "Summary description for {ADULT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	ADULT

create
	make

feature
	identity: CHARACTER
	max_lengths: INTEGER
	max_depth: DOUBLE

feature
	make
	do
		identity := 'A'
		max_lengths := 15
		max_depth := 3.0
	end

feature
	enter_pool_and_swim(depth: DOUBLE): INTEGER
	require
		adult_valid_pool_depth: depth > 0 and depth < 3
	do
		RESULT := 12
	ensure
		adult_max_lengths: RESULT < 15
	end

	enter_pool_and_swim_v2(depth: DOUBLE): INTEGER
	require
		adult_valid_pool_depth_v2: depth > 0 and depth < 3
	do
		RESULT := 12
	end
end
