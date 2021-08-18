import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class FusionSim extends JPanel {
	static Random random = new Random();
	static int y = main.getScreenSizeY();
	static int x = main.getScreenSizeX();
	float leftNucX = 0;
	float rightNucX = 0;

	float fade = 0;

	Color protonCol = new Color(205, 11, 42);
	Color neutronCol = new Color(13, 116, 195);

	int[] EndXArray = null;
	int[] EndYArray = null;
	int[] XArray1 = null;
	int[] YArray1 = null;
	int[] XArray2 = null;
	int[] YArray2 = null;

	char[] currentDrawPosition = null;
	char[] currentDrawPosition1 = null;
	char[] currentDrawPosition2 = null;

	// speed of light and boltzmann's constant
	final int C = 299792458;
	final double BOLTZMANN = 1.38064852E-23;

	public void paintComponent(Graphics g) {

		// draw stuff on
		super.paintComponent(g);
		// if fission has happened, set background to yellow and then fade out
		if (CurrentAnimation.hasFused && fade < 256) {
			this.setBackground(new Color(255, 255, (int) fade));
			fade += 0.7 * CurrentAnimation.speed;
		} else {
			// set background to white
			this.setBackground(Color.WHITE);
		}
		drawStartingPositions(g);
	}

	public static int[] getX1(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetLeftNNum + CurrentAnimation.targetLeftPNum) * 18);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.cos(random.nextInt()));
		}
		return arr;
	}

	// this method returns an array of y-coords from the number of nucleons in the
	// selection
	public static int[] getX2(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetRightNNum + CurrentAnimation.targetRightPNum) * 18);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.sin(random.nextInt()));
		}
		return arr;
	}

	public static int[] getY1(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetLeftNNum + CurrentAnimation.targetLeftPNum) * 18);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.cos(random.nextInt()));
		}
		return arr;
	}

	// this method returns an array of y-coords from the number of nucleons in the
	// selection
	public static int[] getY2(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetRightNNum + CurrentAnimation.targetRightPNum) * 18);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.sin(random.nextInt()));
		}
		return arr;
	}

	public static int[] getOutX(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetNNum + CurrentAnimation.targetPNum) * 16);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.cos(random.nextInt()));
		}
		return arr;
	}

	// this method returns an array of y-coords from the number of nucleons in the
	// selection
	public static int[] getEndY(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(Math.sqrt(CurrentAnimation.targetLeftPNum + CurrentAnimation.targetRightPNum
				+ CurrentAnimation.targetLeftNNum + CurrentAnimation.targetRightNNum) * 16));
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.sin(random.nextInt()));
		}
		return arr;
	}

	public static int[] getEndX(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetLeftPNum + CurrentAnimation.targetRightPNum
				+ CurrentAnimation.targetLeftNNum + CurrentAnimation.targetRightNNum) * 16);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.cos(random.nextInt()));
		}
		return arr;
	}

	// this method returns an array that determines the drawing order of the protons
	// and neutrons
	public static char[] getEndOrder(int pNum, int nNum) {

		// this array will hold p's and n's to say which one to draw at that instant
		char[] orderedArray = new char[pNum + nNum];

		// pCount is used to correctly add the right number of protons and neutrons to
		// the array
		int pCount = pNum;
		for (int i = 0; i < pNum + nNum; i++) {
			// if the protons haven't all been added yet...
			if (pCount > 0) {
				// then make it a proton: 'p'
				orderedArray[i] = 'p';
				// one proton has been used up
				pCount--;
				// else add a neutron
			} else {
				orderedArray[i] = 'n';
			}
		}
		for (int i = 0; i < pNum + nNum; i++) {
			// Fisher-Yates shuffle
			// this randomly orders the p's and n's in the orderedArray
			int rand = random.nextInt(pNum + nNum);
			char x = orderedArray[rand];
			orderedArray[rand] = orderedArray[i];
			orderedArray[i] = x;
		}
		return orderedArray;
	}

	public static char[] get1Order(int pNum, int nNum) {

		// this array will hold p's and n's to say which one to draw at that instant
		char[] orderedArray = new char[pNum + nNum];

		// pCount is used to correctly add the right number of protons and neutrons to
		// the array
		int pCount = pNum;
		for (int i = 0; i < pNum + nNum; i++) {
			// if the protons haven't all been added yet...
			if (pCount > 0) {
				// then make it a proton: 'p'
				orderedArray[i] = 'p';
				// one proton has been used up
				pCount--;
				// else add a neutron
			} else {
				orderedArray[i] = 'n';
			}
		}
		for (int i = 0; i < pNum + nNum; i++) {
			// Fisher-Yates shuffle
			// this randomly orders the p's and n's in the orderedArray
			int rand = random.nextInt(pNum + nNum);
			char x = orderedArray[rand];
			orderedArray[rand] = orderedArray[i];
			orderedArray[i] = x;
		}
		return orderedArray;
	}

	public static char[] get2Order(int pNum, int nNum) {

		// this array will hold p's and n's to say which one to draw at that instant
		char[] orderedArray = new char[pNum + nNum];

		// pCount is used to correctly add the right number of protons and neutrons to
		// the array
		int pCount = pNum;
		for (int i = 0; i < pNum + nNum; i++) {
			// if the protons haven't all been added yet...
			if (pCount > 0) {
				// then make it a proton: 'p'
				orderedArray[i] = 'p';
				// one proton has been used up
				pCount--;
				// else add a neutron
			} else {
				orderedArray[i] = 'n';
			}
		}
		for (int i = 0; i < pNum + nNum; i++) {
			// Fisher-Yates shuffle
			// this randomly orders the p's and n's in the orderedArray
			int rand = random.nextInt(pNum + nNum);
			char x = orderedArray[rand];
			orderedArray[rand] = orderedArray[i];
			orderedArray[i] = x;
		}
		return orderedArray;
	}

	public void drawStartingPositions(Graphics g) {
		// modified equations to fit simulation constraints
		double coulombBarrier = (8.99E9 * CurrentAnimation.targetLeftPNum * CurrentAnimation.targetRightPNum
				* Math.pow(Math.E, 2));

		// energy in MeV
		double energyIn = 2 * BOLTZMANN * (CurrentAnimation.temperature + 273.15) / 1E-26;

		double leftVel = 2 * energyIn / (CurrentAnimation.targetLeftMass);

		double rightVel = 2 * energyIn / (CurrentAnimation.targetRightMass);

		Graphics2D g2d = (Graphics2D) g;
		Random random = new Random();
		// antialiasing
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// if the nucleus has been chosen, but the start button has not yet been
		// pressed...
		if (!CurrentAnimation.started && CurrentAnimation.selected1 && CurrentAnimation.selected2) {
			EndXArray = null;
			EndYArray = null;
			XArray1 = null;
			YArray1 = null;
			XArray2 = null;
			YArray2 = null;

			// fill up the x and y arrays by using the respective methods
			EndXArray = getEndX(CurrentAnimation.targetLeftPNum + CurrentAnimation.targetRightPNum
					+ CurrentAnimation.targetLeftNNum + CurrentAnimation.targetRightNNum);
			EndYArray = getEndY(CurrentAnimation.targetLeftPNum + CurrentAnimation.targetRightPNum
					+ CurrentAnimation.targetLeftNNum + CurrentAnimation.targetRightNNum);
			XArray1 = getX1(CurrentAnimation.targetLeftPNum + CurrentAnimation.targetLeftNNum);
			XArray2 = getX2(CurrentAnimation.targetRightPNum + CurrentAnimation.targetRightNNum);
			YArray1 = getY1(CurrentAnimation.targetLeftPNum + CurrentAnimation.targetLeftNNum);
			YArray2 = getY2(CurrentAnimation.targetRightPNum + CurrentAnimation.targetRightNNum);

			currentDrawPosition = getEndOrder(CurrentAnimation.targetLeftPNum + CurrentAnimation.targetRightPNum,
					CurrentAnimation.targetLeftNNum + CurrentAnimation.targetRightNNum);
			currentDrawPosition1 = get1Order(CurrentAnimation.targetLeftPNum, CurrentAnimation.targetLeftNNum);
			currentDrawPosition2 = get2Order(CurrentAnimation.targetRightPNum, CurrentAnimation.targetRightNNum);
		}

		// if the nucleus has been chosen and the nucleus is pre-fusion
		// then draw two nuclei...
		if (CurrentAnimation.selected1 && CurrentAnimation.selected2 && !CurrentAnimation.hasFused) {
			for (int i = 0; i < CurrentAnimation.targetLeftNNum + CurrentAnimation.targetLeftPNum; i++) {
				int rand = random.nextInt(10);
				// if the orderedArray index is a 'p' then draw a proton
				if (currentDrawPosition1[i] == 'p') {
					g2d.setColor(protonCol);
					g2d.fillOval(XArray1[i] + x / 4 - rand + (int) leftNucX, YArray1[i] + y / 3 - rand, 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.setFont(new Font("Verdana", Font.BOLD, 20));
					g2d.drawString("+", XArray1[i] + x / 4 + 15 - rand + (int) leftNucX,
							YArray1[i] + y / 3 - rand + 29);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(XArray1[i] + x / 4 - rand + (int) leftNucX, YArray1[i] + y / 3 - rand, 46, 46);
					// if the orderedArray index is a 'n' then draw a neutron
				} else if (currentDrawPosition1[i] == 'n') {
					g2d.setColor(neutronCol);
					g2d.fillOval(XArray1[i] + x / 4 - rand + (int) leftNucX, YArray1[i] + y / 3 - rand, 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(XArray1[i] + x / 4 - rand + (int) leftNucX, YArray1[i] + y / 3 - rand, 46, 46);
				}
			}
			for (int i = 0; i < CurrentAnimation.targetRightNNum + CurrentAnimation.targetRightPNum; i++) {

				int rand = random.nextInt(10);
				// if the orderedArray index is a 'p' then draw a proton
				if (currentDrawPosition2[i] == 'p') {
					g2d.setColor(protonCol);
					g2d.fillOval(XArray2[i] + x / 2 - rand + (int) rightNucX, YArray2[i] + y / 3 - rand, 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.setFont(new Font("Verdana", Font.BOLD, 20));
					g2d.drawString("+", XArray2[i] + x / 2 + 15 - rand + (int) rightNucX,
							YArray2[i] + y / 3 - rand + 29);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(XArray2[i] + x / 2 - rand + (int) rightNucX, YArray2[i] + y / 3 - rand, 46, 46);
					// if the orderedArray index is a 'n' then draw a neutron
				} else if (currentDrawPosition2[i] == 'n') {
					g2d.setColor(neutronCol);
					g2d.fillOval(XArray2[i] + x / 2 - rand + (int) rightNucX, YArray2[i] + y / 3 - rand, 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(XArray2[i] + x / 2 - rand + (int) rightNucX, YArray2[i] + y / 3 - rand, 46, 46);
				}
			}

		} else if (CurrentAnimation.selected1 && CurrentAnimation.selected2 && CurrentAnimation.hasFused) {
			for (int i = 0; i < CurrentAnimation.targetLeftNNum + CurrentAnimation.targetLeftPNum
					+ CurrentAnimation.targetRightNNum + CurrentAnimation.targetRightPNum; i++) {
				int rand = random.nextInt(10);
				// if the orderedArray index is a 'p' then draw a proton
				if (currentDrawPosition[i] == 'p') {
					g2d.setColor(protonCol);
					g2d.fillOval(EndXArray[i] + x / 3 - rand, EndYArray[i] + y / 3 - rand, 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.setFont(new Font("Verdana", Font.BOLD, 20));
					g2d.drawString("+", EndXArray[i] + x / 3 + 15 - rand, EndYArray[i] + y / 3 - rand + 29);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(EndXArray[i] + x / 3 - rand, EndYArray[i] + y / 3 - rand, 46, 46);
					// if the orderedArray index is a 'n' then draw a neutron
				} else if (currentDrawPosition[i] == 'n') {
					g2d.setColor(neutronCol);
					g2d.fillOval(EndXArray[i] + x / 3 - rand, EndYArray[i] + y / 3 - rand, 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(EndXArray[i] + x / 3 - rand, EndYArray[i] + y / 3 - rand, 46, 46);
				}
			}
		}

		// if pressure and energy aren't high enough...
		if (CurrentAnimation.pressure < 25330000 || coulombBarrier > energyIn) {
			CurrentAnimation.willBounceBack = true;

		} else {
			CurrentAnimation.willBounceBack = false;
		}

		// if animation has started then...
		if (CurrentAnimation.started) {
			if (CurrentAnimation.willBounceBack) {
				// if going to bounce and not on return and in range...
				if (!CurrentAnimation.onReturn && (x / 4) + leftNucX <= (x / 2) + rightNucX
						&& (x / 4) + leftNucX >= (x / 2) + rightNucX - 20) {
					CurrentAnimation.onReturn = true;

					// if going to bounce and not in range and not on return...
				} else if (!CurrentAnimation.onReturn && !((x / 4) + leftNucX <= (x / 2) + rightNucX
						&& (x / 4) + leftNucX >= (x / 2) + rightNucX - 20)) {
					// move normally
					leftNucX += Math.sqrt(Math.sqrt(leftVel)) * 0.001 * CurrentAnimation.speed;
					rightNucX -= Math.sqrt(Math.sqrt(rightVel)) * 0.001 * CurrentAnimation.speed;

					// if on return
				} else if (CurrentAnimation.onReturn) {
					// move backwards
					leftNucX -= Math.sqrt(Math.sqrt(leftVel)) * CurrentAnimation.speed * 0.001;
					rightNucX += Math.sqrt(Math.sqrt(rightVel)) * CurrentAnimation.speed * 0.001;
				}
				// if will fuse rather than bounce...
			} else if (!CurrentAnimation.willBounceBack) {
				// if not fused and in range...
				if (!CurrentAnimation.hasFused && (x / 4) + leftNucX + 20 >= (x / 2) + rightNucX - 20) {
					CurrentAnimation.hasFused = true;
					
					// if not in range
				} else if (!CurrentAnimation.hasFused && !((x / 4) + leftNucX + 20 >= (x / 2) - rightNucX - 20)) {
					// move normally
					leftNucX += Math.sqrt(Math.sqrt(leftVel)) * 0.001 * CurrentAnimation.speed;
					rightNucX -= Math.sqrt(Math.sqrt(rightVel)) * 0.001 * CurrentAnimation.speed;

				} // else move nothing
			}
			update(g);
		}
	}

	public void update(Graphics g) {

		// timer moves simulation forward
		Timer timer = new Timer(20, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// if the reset button is pressed (to stop the simulation) then stop the timer
				if (CurrentAnimation.started == false) {

					leftNucX = 0;
					rightNucX = 0;

					CurrentAnimation.onReturn = false;
					CurrentAnimation.willBounceBack = false;
					CurrentAnimation.hasFused = false;

					fade = 0;

					((Timer) e.getSource()).stop();
				}
				repaint();
			}
		});
		timer.start();
	}
}