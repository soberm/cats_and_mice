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
			allEntrances: LINKED_LIST [POINT]
			lowestDist: INTEGER
			nearestSub: SUBWAY
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
			if(nextPos /= Void) then
				position.x := nextPos.x
				position.y := nextPos.y
			end
		end

	get_speed : INTEGER
		do
			RESULT := speed
		end

	get_symbol : CHARACTER
		do
			RESULT := identity_symbol
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
