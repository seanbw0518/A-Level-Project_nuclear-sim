public class Validation {
	// method to validate temperature inputs
	public double validateTemperature(String input) {
		// try to convert to double type
		try {
			// convert to double
			double numInput = Double.parseDouble(input);

			// if number is out of range
			if (numInput < -273 || numInput > 100000000000L) {
				// this code will be used to display the right error message
				return -300;

			} else {
				return numInput;
			}

			// if that's not possible then it's invalid
		} catch (NumberFormatException e) {
			// this code will be used to display the right error message
			return -400;
		}
	}

	// method to validate pressure inputs
	public double validatePressure(String input) {
		// try to convert to double type
		try {
			// convert to double
			double numInput = Double.parseDouble(input);

			// if number is out of range
			if (numInput < 1 || numInput > 1000000000L) {
				// this code will be used to display the right error message
				return -300;

			} else {
				return numInput;
			}

			// if that's not possible then it's invalid
		} catch (NumberFormatException e) {
			// this code will be used to display the right error message
			return -400;
		}
	}

	// method to validate velocity inputs
	public float validateVelocity(String input) {
		// try to convert to float type
		try {
			// convert to float
			float numInput = Float.parseFloat(input);

			// if number is out of range
			if (numInput < 0 || numInput > 10) {
				// this code will be used to display the right error message
				return -300;

			} else {
				return numInput;
			}

			// if that's not possible then it's invalid
		} catch (NumberFormatException e) {
			// this code will be used to display the right error message
			return -400;
		}
	}
}