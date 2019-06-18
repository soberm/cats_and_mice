note
	description: "Summary description for {COMPUTER_CAT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	COMPUTER_CAT

inherit
	PLAYER
	CAT

create
	make

feature --fields	
	all_subways: LINKED_LIST [SUBWAY]
	all_mouse: LINKED_LIST [COMPUTER_MOUSE]

feature -- initialization
	make (x: INTEGER; y: INTEGER; subways: LINKED_LIST [SUBWAY]; mouses: LINKED_LIST [COMPUTER_MOUSE])
		local
			initPosition: POINT
		do
			create initPosition.make (x, y)
			init
			position := initPosition
			all_subways := subways
			all_mouse := mouses
		end

feature -- overridden inherited features
	move
		local
			lowestDist: INTEGER
			nextPos: POINT
			dist: INTEGER
			mousePos: POINT
		do
			lowestDist := 999999

			across all_mouse as mouse loop
				mousePos := mouse.item.position
				dist := position.calculuate_distance_to (mousePos)

				if(dist < lowestDist) then -- is it a close mouse?
					lowestDist := dist
					nextPos := position.calculate_next_position (mousePos)
				end

			end

			if(nextPos /= Void) then
				position.x := nextPos.x
				position.y := nextPos.y
			end
		end

	get_speed : INTEGER
		do
			RESULT := speed
		end

end
