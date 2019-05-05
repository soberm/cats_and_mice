private
update
	| healthFraction |
	healthFraction := snake health / 100.
	healthBarMorph layoutFrame: (LayoutFrame
		fractions: (0@0 corner: healthFraction@1)).