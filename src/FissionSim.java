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

public class FissionSim extends JPanel {
	static Random random = new Random();
	static int y = main.getScreenSizeY();
	static int x = main.getScreenSizeX();
	FissionNeutron n = new FissionNeutron();
	Photon p1 = new Photon();
	Photon p2 = new Photon();
	double nucY1 = 0;
	double nucY2 = 0;
	int outN1X = 400;
	int outN2X = 400;
	int outN3X = 400;

	int outN2Y = 250;
	int outN3Y = 250;

	int fade = 0;

	Color protonCol = new Color(205, 11, 42);
	Color neutronCol = new Color(13, 116, 195);

	int[] StartXArray = null;
	int[] StartYArray = null;
	int[] XArray1 = null;
	int[] YArray1 = null;
	int[] XArray2 = null;
	int[] YArray2 = null;

	char[] currentDrawPosition = null;
	char[] currentDrawPosition1 = null;
	char[] currentDrawPosition2 = null;

	// method that does the primary drawing of graphics objects
	public void paintComponent(Graphics g) {
		// draw stuff on
		super.paintComponent(g);

		// if fission has happened, set background to yellow and then fade out
		if (CurrentAnimation.hasFissioned && fade < 256) {
			this.setBackground(new Color(255, 255, fade));
			fade += 5 * CurrentAnimation.speed;
		} else {
			// set background to white
			this.setBackground(Color.WHITE);
		}
		drawStartingPositions(g);
	}

	public static int[] getStartX(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetNNum + CurrentAnimation.targetPNum) * 10);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.cos(random.nextInt()));
		}
		return arr;
	}

	// this method returns an array of y-coords from the number of nucleons in the
	// selection
	public static int[] getStartY(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetNNum + CurrentAnimation.targetPNum) * 10);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.sin(random.nextInt()));
		}
		return arr;
	}

	public static int[] getOut1X(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetNNum + CurrentAnimation.targetPNum) * 10);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.cos(random.nextInt()));
		}
		return arr;
	}

	// this method returns an array of y-coords from the number of nucleons in the
	// selection
	public static int[] getOut1Y(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetNNum + CurrentAnimation.targetPNum) * 10);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.sin(random.nextInt()));
		}
		return arr;
	}

	public static int[] getOut2X(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetNNum + CurrentAnimation.targetPNum) * 10);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.cos(random.nextInt()));
		}
		return arr;
	}

	// this method returns an array of y-coords from the number of nucleons in the
	// selection
	public static int[] getOut2Y(int num) {
		int[] arr = new int[num];
		// function that sets the magnitude of the spawn radius
		int sizeFunc = (int) (Math.sqrt(CurrentAnimation.targetNNum + CurrentAnimation.targetPNum) * 10);
		for (int i = 0; i < num; i++) {
			// put a random coordinate in each index of the array
			arr[i] = (int) (random.nextInt(sizeFunc) * Math.sin(random.nextInt()));
		}
		return arr;
	}

	// this method returns an array that determines the drawing order of the protons
	// and neutrons
	public static char[] getStartOrder(int pNum, int nNum) {

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

		Graphics2D g2d = (Graphics2D) g;
		Random random = new Random();
		// antialiasing
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// if the nucleus has been chosen, but the start button has not yet been
		// pressed...
		if (!CurrentAnimation.started && CurrentAnimation.selected) {
			StartXArray = null;
			StartYArray = null;
			XArray1 = null;
			YArray1 = null;
			XArray2 = null;
			YArray2 = null;

			// fill up the x and y arrays by using the respective methods
			StartXArray = getStartX(CurrentAnimation.targetPNum + CurrentAnimation.targetNNum);
			StartYArray = getStartY(CurrentAnimation.targetPNum + CurrentAnimation.targetNNum);
			XArray1 = getOut1X(CurrentAnimation.product1Protons + CurrentAnimation.product1Neutrons);
			XArray2 = getOut2X(CurrentAnimation.product2Protons + CurrentAnimation.product2Neutrons);
			YArray1 = getOut1Y(CurrentAnimation.product1Protons + CurrentAnimation.product1Neutrons);
			YArray2 = getOut2Y(CurrentAnimation.product2Protons + CurrentAnimation.product2Neutrons);

			currentDrawPosition = getStartOrder(CurrentAnimation.targetPNum, CurrentAnimation.targetNNum);
			currentDrawPosition1 = get1Order(CurrentAnimation.product1Protons, CurrentAnimation.product1Neutrons);
			currentDrawPosition2 = get2Order(CurrentAnimation.product2Protons, CurrentAnimation.product2Neutrons);
		}
		// if the nucleus has been chosen and the nucleus is at the point of fission
		// then draw two nuclei...
		if (CurrentAnimation.selected && !CurrentAnimation.hasFissioned) {
			for (int i = 0; i < CurrentAnimation.targetNNum + CurrentAnimation.targetPNum; i++) {

				int rand = random.nextInt(10);
				// if the orderedArray index is a 'p' then draw a proton
				if (currentDrawPosition[i] == 'p') {
					g2d.setColor(protonCol);
					g2d.fillOval(StartXArray[i] + x / 3 - rand, StartYArray[i] + y / 3 - rand, 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.setFont(new Font("Verdana", Font.BOLD, 20));
					g2d.drawString("+", StartXArray[i] + x / 3 + 15 - rand, StartYArray[i] + y / 3 - rand + 29);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(StartXArray[i] + x / 3 - rand, StartYArray[i] + y / 3 - rand, 46, 46);
					// if the orderedArray index is a 'n' then draw a neutron
				} else if (currentDrawPosition[i] == 'n') {
					g2d.setColor(neutronCol);
					g2d.fillOval(StartXArray[i] + x / 3 - rand, StartYArray[i] + y / 3 - rand, 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(StartXArray[i] + x / 3 - rand, StartYArray[i] + y / 3 - rand, 46, 46);
				}
			}

		} else if (CurrentAnimation.selected && CurrentAnimation.hasFissioned) {

			for (int i = 0; i < CurrentAnimation.product1Protons + CurrentAnimation.product1Neutrons; i++) {

				int rand = random.nextInt(10);
				// if the orderedArray index is a 'p' then draw a proton
				if (currentDrawPosition1[i] == 'p') {
					g2d.setColor(protonCol);
					g2d.fillOval(XArray1[i] + x / 3 - rand, (int) (YArray1[i] + y / 3 - rand + nucY1), 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.setFont(new Font("Verdana", Font.BOLD, 20));
					g2d.drawString("+", XArray1[i] + x / 3 + 15 - rand, (int) (YArray1[i] + y / 3 - rand + nucY1 + 29));

					g2d.setColor(Color.BLACK);
					g2d.drawOval(XArray1[i] + x / 3 - rand, (int) (YArray1[i] + y / 3 - rand + nucY1), 46, 46);
					// if the orderedArray index is a 'n' then draw a neutron
				} else if (currentDrawPosition1[i] == 'n') {
					g2d.setColor(neutronCol);
					g2d.fillOval(XArray1[i] + x / 3 - rand, (int) (YArray1[i] + y / 3 - rand + nucY1), 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(XArray1[i] + x / 3 - rand, (int) (YArray1[i] + y / 3 - rand + nucY1), 46, 46);
				}
			}

			for (int i = 0; i < CurrentAnimation.product2Protons + CurrentAnimation.product2Neutrons; i++) {

				int rand = random.nextInt(10);
				// if the orderedArray index is a 'p' then draw a proton
				if (currentDrawPosition2[i] == 'p') {
					g2d.setColor(protonCol);
					g2d.fillOval(XArray2[i] + x / 3 - rand, (int) (YArray2[i] + y / 3 - rand + nucY2), 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.setFont(new Font("Verdana", Font.BOLD, 20));
					g2d.drawString("+", XArray2[i] + x / 3 + 15 - rand, (int) (YArray2[i] + y / 3 - rand + nucY2 + 29));

					g2d.setColor(Color.BLACK);
					g2d.drawOval(XArray2[i] + x / 3 - rand, (int) (YArray2[i] + y / 3 - rand + nucY2), 46, 46);
					// if the orderedArray index is a 'n' then draw a neutron
				} else if (currentDrawPosition2[i] == 'n') {
					g2d.setColor(neutronCol);
					g2d.fillOval(XArray2[i] + x / 3 - rand, (int) (YArray2[i] + y / 3 - rand + nucY2), 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(XArray2[i] + x / 3 - rand, (int) (YArray2[i] + y / 3 - rand + nucY2), 46, 46);
				}
			}
			double nucVelocityOut = Math.sqrt(CurrentAnimation.energyOut / (0.5 * CurrentAnimation.targetMass * 931.5))
					* CurrentAnimation.speed;

			if (CurrentAnimation.lod == 1) {
				// move product nuclei away at accurate rate
				nucY1 -= nucVelocityOut * 20;
				nucY2 += nucVelocityOut * 20;
			} else {
				// move product nuclei away at default rate
				nucY1 -= 7 * CurrentAnimation.speed;
				nucY2 += 7 * CurrentAnimation.speed;
			}
		}

		// draw output neutrons

		double neuVelocityOut = Math.sqrt(CurrentAnimation.energyOut / (0.5 * 1.00867)) * CurrentAnimation.speed * 0.1;
		double photonVelocityOut = Math.sqrt(CurrentAnimation.energyOut * 15) * CurrentAnimation.speed * 0.1;

		if (CurrentAnimation.hasFissioned) {
			g2d.setColor(neutronCol);
			g2d.fillOval(outN1X, 250, 46, 46);
			g2d.fillOval(outN2X, outN2Y, 46, 46);
			g2d.fillOval(outN3X, outN3Y, 46, 46);

			g2d.setColor(Color.BLACK);
			g2d.drawOval(outN1X, 250, 46, 46);
			g2d.drawOval(outN2X, outN2Y, 46, 46);
			g2d.drawOval(outN3X, outN3Y, 46, 46);

			if (CurrentAnimation.lod == 1) {
				g2d.setFont(new Font("Verdana", Font.BOLD, 26));
				g2d.setColor(protonCol);
				outN1X += neuVelocityOut;
				outN2X += neuVelocityOut;
				outN3X += neuVelocityOut;

				outN2Y -= neuVelocityOut;
				outN3Y += neuVelocityOut;

				// draw gamma rays
				g2d.drawString("γ", p1.x + 100, p1.y);
				g2d.drawString("γ", p2.x + 100, p2.y);

				p1.x += photonVelocityOut;
				p1.y -= photonVelocityOut;
				p2.x += photonVelocityOut;
				p2.y += photonVelocityOut;
			} else {
				outN1X += 7 * CurrentAnimation.speed;
				outN2X += 7 * CurrentAnimation.speed;
				outN3X += 7 * CurrentAnimation.speed;
				outN2Y -= 7 * CurrentAnimation.speed;
				outN3Y += 7 * CurrentAnimation.speed;
			}
		}

		// how will the neutron behave
		// if too slow then bounce back
		if (CurrentAnimation.neutronVel <= 2) {
			CurrentAnimation.willPassThrough = false;
			CurrentAnimation.willBounceBack = true;
			CurrentAnimation.willFission = false;

			// if too fast then pass through
		} else if (CurrentAnimation.neutronVel >= 8) {
			CurrentAnimation.willPassThrough = true;
			CurrentAnimation.willBounceBack = false;
			CurrentAnimation.willFission = false;

			// if just right than fission
		} else {
			CurrentAnimation.willPassThrough = false;
			CurrentAnimation.willBounceBack = false;
			CurrentAnimation.willFission = true;
		}

		// if the animation has started and it will fuse
		if (CurrentAnimation.started == true && !CurrentAnimation.willBounceBack && !CurrentAnimation.willFission) {
			// draw projectile neutron
			g2d.setColor(neutronCol);
			g2d.fillOval((int) n.x, n.y, 46, 46);
			g2d.setColor(Color.BLACK);
			g2d.drawOval((int) n.x, n.y, 46, 46);

			// move it forward (by the chosen velocity)
			n.x += CurrentAnimation.neutronVel * CurrentAnimation.speed;

			// if the animation has started and it bounce back
		} else if (CurrentAnimation.started == true && CurrentAnimation.willBounceBack
				&& !CurrentAnimation.willFission) {
			// draw projectile neutron
			g2d.setColor(neutronCol);
			g2d.fillOval((int) n.x, n.y, 46, 46);
			g2d.setColor(Color.BLACK);
			g2d.drawOval((int) n.x, n.y, 46, 46);

			// if it has reached the nucleus, then go backwards
			if (n.x >= StartXArray[0] + x / 3 || CurrentAnimation.onReturn) {
				// move it backward (by the chosen velocity)
				n.x -= CurrentAnimation.neutronVel * CurrentAnimation.speed;
				CurrentAnimation.onReturn = true;
			} else {
				// move it forward (by the chosen velocity)
				n.x += CurrentAnimation.neutronVel * CurrentAnimation.speed;
			}
		} else if (CurrentAnimation.started == true && CurrentAnimation.willFission) {
			// if it has not reached the nucleus, then draw it in
			if (n.x <= StartXArray[0] + x / 3) {
				// draw projectile neutron
				g2d.setColor(neutronCol);
				g2d.fillOval((int) n.x, n.y, 46, 46);
				g2d.setColor(Color.BLACK);
				g2d.drawOval((int) n.x, n.y, 46, 46);

				// move it forward (by the chosen velocity)
				n.x += CurrentAnimation.neutronVel * CurrentAnimation.speed;
			}
		}

		// if ready to start simulation, then start updating
		if (CurrentAnimation.started) {
			update(g);
		}
	}

	public void update(Graphics g) {

		// timer moves simulation forward
		Timer timer = new Timer(20, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// if the reset button is pressed (to stop the simulation) then stop the timer
				if (CurrentAnimation.started == false) {
					n.x = 0;
					CurrentAnimation.onReturn = false;
					CurrentAnimation.willBounceBack = false;
					CurrentAnimation.hasFissioned = false;
					CurrentAnimation.willPassThrough = false;
					nucY1 = 0;
					nucY2 = 0;
					outN1X = StartXArray[0] + x / 3;
					outN2X = StartXArray[0] + x / 3;
					outN3X = StartXArray[0] + x / 3;

					outN2Y = StartYArray[0] + y / 3;
					outN3Y = StartYArray[0] + y / 3;

					p1.x = StartXArray[0] + x / 3;
					p2.x = StartXArray[0] + x / 3;
					p1.y = StartYArray[0] + y / 3;
					p2.y = StartYArray[0] + y / 3;
					fade = 0;

					((Timer) e.getSource()).stop();
				}

				if (CurrentAnimation.willFission && n.x >= StartXArray[0] + x / 3) {
					CurrentAnimation.hasFissioned = true;
				}

				repaint();

			}
		});
		timer.start();
	}
}