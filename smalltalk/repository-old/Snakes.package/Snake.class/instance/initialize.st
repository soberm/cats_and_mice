initialize-release
initialize
	"comment stating purpose of message"

	health := 100.
	ticksPerStep := 2.
	ticksToNextStep := ticksPerStep.
	boostSlowdownCounter := 0.
	touchedBoost := 0.