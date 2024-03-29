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

	all_mice: LINKED_LIST [MOUSE]

feature -- initialization

	make (x: INTEGER; y: INTEGER; subways: LINKED_LIST [SUBWAY]; mouses: LINKED_LIST [MOUSE])
		local
			initPosition: POINT
		do
			create initPosition.make (x, y)
			init
			position := initPosition
			all_subways := subways
			all_mice := mouses
		end

feature -- overridden inherited features

	move (tick: INTEGER)
		local
			lowestDist: INTEGER
			nextPos: POINT
			dist: INTEGER
			mousePos: POINT
		do
			if (tick \\ get_speed = 0) then --only move if speed allows
				lowestDist := 999999
				nextPos := position.deep_twin --use current pos as fallback
				across
					all_mice as mouse
				loop
					if mouse.item.current_subway = Void and mouse.item.alive then --cats can onlu see mice alive + outside

						mousePos := mouse.item.position
						dist := position.calculuate_distance_to (mousePos)
						if (dist < lowestDist) then -- is it a close mouse?
							lowestDist := dist
							nextPos := position.calculate_next_position (mousePos)
						end
					end
				end
				if (nextPos /= Void and nextPos.is_valid) then
					position := nextPos
				end
			end
		end

	get_speed: INTEGER
		do
			RESULT := speed
		end

	set_visible_mice (visible: LINKED_LIST [MOUSE])
		do
			all_mice := visible
		end

end
