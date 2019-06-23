note
	description: "Summary description for {COMPUTER_MOUSE}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	COMPUTER_MOUSE

inherit
	PLAYER
	MOUSE

create
	make

feature --fields	
	all_subways: LINKED_LIST [SUBWAY]

feature --initialization
	make (x: INTEGER; y: INTEGER; subways: LINKED_LIST [SUBWAY]; target: SUBWAY)
		local
			initPosition: POINT
		do
			create initPosition.make (x, y)
			init
			position := initPosition
			target_subway := target
			all_subways := subways
		end

feature -- overridden inherited features
	move
		local
			nextPos: POINT
		do
			if attached current_subway as subway then
				nextPos := move_in_subway(subway)
			else
				nextPos := move_outside
			end

			if(nextPos /= Void) then
				position.x := nextPos.x
				position.y := nextPos.y
			end
		end

	move_in_subway ( subway: SUBWAY) :POINT
		require
			in_subway: subway /= Void
		local
			danger1: INTEGER
			danger2: INTEGER
		do
			danger1 := calculate_danger_level(subway.entrance1)
			danger2 := calculate_danger_level(subway.entrance2)

			if(danger1 > danger2) then
				RESULT := position.calculate_next_position (subway.entrance1)
			else
				RESULT := position.calculate_next_position (subway.entrance2)
			end

		end

	calculate_danger_level ( entrance: POINT) : INTEGER
		local
			dist: INTEGER
			targetDist: INTEGER
			tmp: POINT
			tmpDist: INTEGER
			danger: INTEGER
		do
			dist := position.calculuate_distance_to (entrance)

			tmp := position.calculate_next_position (entrance)
			targetDist := target_subway.calculuate_distance_to (tmp)

			across known_cat_positions as cat_position loop
				tmpDist := cat_position.item.calculuate_distance_to (entrance)
				danger := danger + tmpDist
			end

			RESULT := danger

		end


	move_outside : POINT
		local
			allEntrances: LINKED_LIST [POINT]
			lowestDist: INTEGER
			tmp: POINT
			currentDist: INTEGER
			nextDist: INTEGER
			lowestNextDist: INTEGER
			nextPos: POINT
			dist: INTEGER
		do
			create allEntrances.make
			across all_subways as sub loop
				allEntrances.extend (sub.item.entrance1)
				allEntrances.extend (sub.item.entrance2)
			end

			currentDist := current_distance
			lowestDist := 999999
			lowestNextDist := 999999
			nextPos := position.deep_twin

			across allEntrances as entrance loop
				dist := position.calculuate_distance_to (entrance.item)

				if(dist < lowestDist) then -- is it a close entrance?
					lowestDist := dist

					tmp := position.calculate_next_position (entrance.item)
					nextDist := target_subway.calculuate_distance_to (tmp)

					if(nextDist < lowestNextDist) then -- does this entrance brings us closer to target?
						lowestNextDist := nextDist
						nextPos := tmp
					end
				end
			end
			RESULT := nextPos
		end


	get_speed : INTEGER
		do
			RESULT := speed
		end

	current_distance: INTEGER
		do
			if(position.calculuate_distance_to (target_subway.entrance1) < position.calculuate_distance_to (target_subway.entrance2)) then
				RESULT := position.calculuate_distance_to (target_subway.entrance1)
			else
				RESULT := position.calculuate_distance_to (target_subway.entrance2)
		end
	end
end
