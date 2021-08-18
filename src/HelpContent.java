import java.io.*;
import java.util.Scanner;

public class HelpContent {
	public String getFileContent() {
		// get the file
		File helpFile = new File("manual.txt");
		String content = "";

		// try-catch to scan the file
		try {
			Scanner fileScan = new Scanner(helpFile);

			while (fileScan.hasNext()) {
				// append the scanned line to the string (replace \n with line break)
				content += fileScan.nextLine().replaceAll("\\\\n", System.getProperty("line.separator"));
			}
			// close the file when done and return the string
			fileScan.close();
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
}