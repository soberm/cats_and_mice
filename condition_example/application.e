note
	description: "condition_example application root class"
	date: "$Date$"
	revision: "$Revision$"

class
	APPLICATION

inherit
	ARGUMENTS_32

create
	make

feature {NONE} -- Initialization

	make
			-- Run application.
		local
			adult: ADULT
			child: CHILD
			adult_pool: SWIMMING_POOL
			child_pool: CHILDREN_SWIMMING_POOL
			res: INTEGER
		do
			--| Add your code here
			create adult.make
			create child.child_make
			create adult_pool.make
			create child_pool.child_pool_make

			io.put_string ("-----------------")
			io.new_line

			res := adult_pool.do_swim(adult, 2)
			io.put_integer(res)
			io.new_line

			res := child_pool.do_swim(child, 1)
			io.put_integer(res)
			io.new_line

			io.put_string ("v2---------------")
			io.new_line

			res := adult_pool.do_swim_v2(adult, 2)
			io.put_integer(res)
			io.new_line

			res := child_pool.do_swim_v2(child, 1)
			io.put_integer(res)
			io.new_line

		end

end
