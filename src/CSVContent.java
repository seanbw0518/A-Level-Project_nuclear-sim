import java.io.*;
import java.util.Scanner;

public class CSVContent {
	// method to get the csv file and convert it into a 2d array for later use
	public String[][] getFileContent(String filename) {
		// get the file
		File csvFile = new File(filename);

		// try-catch to scan the file
		try {
			// file reader and line number reader
			FileReader reader = new FileReader(csvFile);
			LineNumberReader lineNum = new LineNumberReader(reader);

			// number of lines in the file
			int lines = 0;

			// while each line is not not there, number of lines goes up by 1
			while (lineNum.readLine() != null) {
				lines++;
			}

			// stuff for scanning file content (not line number)
			Scanner fileScan = new Scanner(csvFile);
			String content = "";

			// array that is the length required to hold each line of the csv file
			String[] lineArray = new String[lines];

			// go through each line and add the line to the array at the correct position
			while (fileScan.hasNext()) {
				for (int i = 0; i < lines; i++) {
					// append the scanned line to the string
					content = fileScan.nextLine();
					lineArray[i] = content;
				}
			}
			// length of each line
			int lineLength = content.split(",").length;

			// array to hold the data from each individual line in the below for loop
			String[] tempArray = new String[lineLength];
			// array to hold all the data from the csv in a 2d array format
			String[][] finalArray = new String[lines][lineLength];

			// for every element in lineArray (every line), set tempArray to every
			// comma-separated value in that line
			for (int i = 0; i < lines; i++) {
				tempArray = lineArray[i].split(",");
				// for every element in tempArray (every comma-separated value), put it in the
				// finalArray.
				for (int j = 0; j < tempArray.length; j++) {
					finalArray[i][j] = tempArray[j];
				}
			}

			// close everything
			lineNum.close();
			fileScan.close();
			return finalArray;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// method to make an array of all the names of each nucleus
	public String[] getOptionNames(String[][] masterArray) {
		// array to store the names
		String[] nucleusNamesArray = new String[masterArray.length];

		// for every 'row' in the 2d array, add the first element to the final array
		for (int i = 0; i < masterArray.length; i++) {
			nucleusNamesArray[i] = masterArray[i][0];
		}
		return nucleusNamesArray;
	}

	public String getOptionFullNames(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 1 value (name)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][1];
			}
		}
		return null;
	}

	public String getOptionProtons(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 2 value (proton #)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][2];
			}
		}
		return null;
	}

	public String getOptionNeutrons(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 3 value (neutron #)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][3];
			}
		}
		return null;
	}

	public String getOptionMass(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 4 value (mass)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][4];
			}
		}
		return null;
	}

	public String getFissionOptionProduct1(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 6 value (product 1 name)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][6];
			}
		}
		return null;
	}

	public String getFissionOptionProduct2(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 9 value (product 2 name)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][9];
			}
		}
		return null;
	}

	public String getDecayOptionHalfLife(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 5 value (half life)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][5];
			}
		}
		return null;
	}

	public String getDecayOptionEnergy(String[][] masterArray, String name, int mode) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 5 value (half life)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][5 + mode];
			}
		}
		return null;
	}

	public boolean[] getDecayModes(String[][] masterArray, String name) {
		// array that holds valid decay modes as true or false
		boolean[] modeArray = { false, false, false, false };

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if the matching row has a decay mode available, then set it to true in the
		// modeArray

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				if (!masterArray[i][6].toString().equals("0.00")) {
					modeArray[0] = true;
				}
				if (!masterArray[i][7].toString().equals("0.00")) {
					modeArray[1] = true;
				}
				if (!masterArray[i][8].toString().equals("0.00")) {
					modeArray[2] = true;
				}
				if (!masterArray[i][9].toString().equals("0.00")) {
					modeArray[3] = true;
				}
				return modeArray;
			}
		}
		return null;
	}

	public String getFissonOptionProduct1Protons(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 7 value (proton #)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][7];
			}
		}
		return null;
	}

	public String getFissonOptionProduct2Protons(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 10 value (proton #)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][10];
			}
		}
		return null;
	}

	public String getFissonOptionProduct1Neutrons(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 8 value (neutron #)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][8];
			}
		}
		return null;
	}

	public String getFissonOptionProduct2Neutrons(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 11 value (neutron #)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][11];
			}
		}
		return null;
	}

	public String getOptionEnergy(String[][] masterArray, String name) {

		// for every 'row' in the 2d array, search for the inputted name eg U-235
		// if it is found, then return that row's index: 5 value (binding energy)

		for (int i = 0; i < masterArray.length; i++) {
			if (masterArray[i][0].equals(name)) {
				return masterArray[i][5];
			}
		}
		return null;
	}
}