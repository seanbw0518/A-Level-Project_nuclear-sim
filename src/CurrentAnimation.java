public class CurrentAnimation {

	// universal
	static byte lod = 0;

	static int targetPNum = 0;
	static int targetNNum = 0;
	static int targetMass = 0;

	static boolean willBounceBack = false;
	static boolean onReturn = false;
	static boolean willPassThrough = false;
	static boolean started = false;
	static boolean selected = false;

	static int minSpeed = 0;
	static int maxSpeed = 4;
	static int defSpeed = 2;
	static int speed = 2;

	// decay
	static char decayMode = ' ';
	static boolean hasDecayed = false;
	static boolean hasChanged = false;
	static boolean drawn = false;

	// fission

	static boolean willFission = false;
	static boolean hasFissioned = false;
	static int product1Protons = 0;
	static int product2Protons = 0;
	static int product1Neutrons = 0;
	static int product2Neutrons = 0;
	static int energyOut = 0;

	static float neutronVel = 0;

	// fusion

	static int targetLeftPNum = 0;
	static int targetLeftNNum = 0;
	static int targetLeftMass = 0;

	static int targetRightPNum = 0;
	static int targetRightNNum = 0;
	static int targetRightMass = 0;

	static boolean hasFused = false;
	static boolean selected1 = false;
	static boolean selected2 = false;

	// temp in C
	static int temperature = 0;
	static int pressure = 0;

}