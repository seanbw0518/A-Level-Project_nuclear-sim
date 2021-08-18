import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DecaySim extends JPanel {
	static Random random = new Random();
	static int y = main.getScreenSizeY();
	static int x = main.getScreenSizeX();

	Photon p = new Photon();
	Electron el = new Electron();
	Positron antiEl = new Positron();
	AlphaParticle a = new AlphaParticle();
	Neutrino v = new Neutrino();
	AntiNeutrino antiV = new AntiNeutrino();

	Color protonCol = new Color(205, 11, 42);
	Color neutronCol = new Color(13, 116, 195);

	int[] XArray = null;
	int[] YArray = null;

	char[] currentDrawPosition = null;

	// method that does the primary drawing of graphics objects
	public void paintComponent(Graphics g) {
		// draw stuff on
		super.paintComponent(g);
		// set background to white
		this.setBackground(Color.WHITE);
		drawStartingPositions(g);

	}

	public static int[] getX(int num) {
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
	public static int[] getY(int num) {
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
	public static char[] getOrder(int pNum, int nNum) {

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
		// antialiasing
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// if the nucleus has decayed then change the nucleons accordingly
		if (CurrentAnimation.decayMode == 'b' && CurrentAnimation.hasDecayed && !CurrentAnimation.hasChanged) {
			CurrentAnimation.targetNNum--;
			CurrentAnimation.targetPNum++;
			CurrentAnimation.hasChanged = true;
			CurrentAnimation.drawn = false;
		} else if (CurrentAnimation.decayMode == 'B' && CurrentAnimation.hasDecayed && !CurrentAnimation.hasChanged) {
			CurrentAnimation.targetNNum++;
			CurrentAnimation.targetPNum--;
			CurrentAnimation.hasChanged = true;
			CurrentAnimation.drawn = false;
		} else if (CurrentAnimation.decayMode == 'a' && CurrentAnimation.hasDecayed && !CurrentAnimation.hasChanged) {
			CurrentAnimation.targetNNum -= 2;
			CurrentAnimation.targetPNum -= 2;
			CurrentAnimation.hasChanged = true;
			CurrentAnimation.drawn = false;
		}

		// if the nucleus has been chosen, but the start button has not yet been
		// pressed...
		if ((!CurrentAnimation.started && CurrentAnimation.selected) || (CurrentAnimation.started
				&& CurrentAnimation.hasDecayed && !CurrentAnimation.drawn && CurrentAnimation.hasChanged)) {
			XArray = null;
			YArray = null;
			// fill up the x and y arrays by using the respective methods
			XArray = getX(CurrentAnimation.targetPNum + CurrentAnimation.targetNNum);
			YArray = getY(CurrentAnimation.targetPNum + CurrentAnimation.targetNNum);

			currentDrawPosition = getOrder(CurrentAnimation.targetPNum, CurrentAnimation.targetNNum);

			CurrentAnimation.drawn = true;
		}
		if (CurrentAnimation.selected) {
			for (int i = 0; i < CurrentAnimation.targetNNum + CurrentAnimation.targetPNum; i++) {

				int rand = random.nextInt(10);
				// if the orderedArray index is a 'p' then draw a proton
				if (currentDrawPosition[i] == 'p') {
					g2d.setColor(protonCol);
					g2d.fillOval(XArray[i] + x / 3 - rand, YArray[i] + y / 3 - rand, 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.setFont(new Font("Verdana", Font.BOLD, 20));
					g2d.drawString("+", XArray[i] + x / 3 + 15 - rand, YArray[i] + y / 3 - rand + 29);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(XArray[i] + x / 3 - rand, YArray[i] + y / 3 - rand, 46, 46);
					// if the orderedArray index is a 'n' then draw a neutron
				} else if (currentDrawPosition[i] == 'n') {
					g2d.setColor(neutronCol);
					g2d.fillOval(XArray[i] + x / 3 - rand, YArray[i] + y / 3 - rand, 46, 46);

					g2d.setColor(Color.BLACK);
					g2d.drawOval(XArray[i] + x / 3 - rand, YArray[i] + y / 3 - rand, 46, 46);
				}
			}
			g2d.setFont(new Font("Verdana", Font.BOLD, 26));

			// if particle is ready to be launched, animate it
			if (p.started) {
				// make a photon
				g2d.setColor(Color.ORANGE);
				g2d.drawString("γ", p.x, p.y);
				p.x += 6 * CurrentAnimation.speed;
				p.y -= 6 * CurrentAnimation.speed;
			} else if (a.started) {
				// make an alpha particle
				g2d.setColor(protonCol);
				g2d.fillOval(a.x - 23, a.y - 40, 46, 46);
				g2d.fillOval(a.x - 23, a.y + 40, 46, 46);
				g2d.setColor(neutronCol);
				g2d.fillOval(a.x - 46, a.y, 46, 46);
				g2d.fillOval(a.x, a.y, 46, 46);
				g2d.setColor(Color.BLACK);
				g2d.drawOval(a.x - 23, a.y - 40, 46, 46);
				g2d.drawOval(a.x - 23, a.y + 40, 46, 46);
				g2d.drawOval(a.x - 46, a.y, 46, 46);
				g2d.drawOval(a.x, a.y, 46, 46);
				g2d.drawString("+", a.x - 23 + 15, a.y - 40 + 29);
				g2d.drawString("+", a.x - 23 + 15, a.y + 40 + 29);
				a.x += 4 * CurrentAnimation.speed;
				a.y -= 4 * CurrentAnimation.speed;
			} else if (el.started) {
				// make an electron and antineutrino
				g2d.setColor(Color.CYAN);
				g2d.drawString("e-", el.x, el.y);
				el.x += CurrentAnimation.speed;
				el.y -= CurrentAnimation.speed;
				// if a level than draw neutrinos
				if (CurrentAnimation.lod == 1) {
					g2d.setColor(Color.MAGENTA);
					g2d.drawString("ν'", antiV.x, antiV.y);
					antiV.x += CurrentAnimation.speed;
					antiV.y += CurrentAnimation.speed;
				}
			} else if (antiEl.started) {
				// make a positron and neutrino
				g2d.setColor(Color.MAGENTA);
				g2d.drawString("e+", antiEl.x, antiEl.y);
				antiEl.x += CurrentAnimation.speed;
				antiEl.y += CurrentAnimation.speed;
				// if a level than draw neutrinos
				if (CurrentAnimation.lod == 1) {
					g2d.setColor(Color.CYAN);
					g2d.drawString("ν", v.x, v.y);
					v.x += CurrentAnimation.speed;
					v.y -= CurrentAnimation.speed;
				}

			}

			// if ready to start simulation, then start updating
			if (CurrentAnimation.started) {
				update(g);
			}
		}
	}

	public void update(Graphics g) {

		// timer moves simulation forward
		Timer timer = new Timer(20, new ActionListener() {
			Random random = new Random();

			public void actionPerformed(ActionEvent e) {
				// if the reset button is pressed (to stop the simulation) then stop the timer
				if (!CurrentAnimation.started) {
					// if the nucleus has decayed then change the nucleons accordingly
					if (CurrentAnimation.decayMode == 'b' && CurrentAnimation.hasDecayed
							&& CurrentAnimation.hasChanged) {
						CurrentAnimation.targetNNum++;
						CurrentAnimation.targetPNum--;
						CurrentAnimation.hasDecayed = false;
					} else if (CurrentAnimation.decayMode == 'B' && CurrentAnimation.hasDecayed
							&& CurrentAnimation.hasChanged) {
						CurrentAnimation.targetNNum--;
						CurrentAnimation.targetPNum++;
						CurrentAnimation.hasDecayed = false;
					} else if (CurrentAnimation.decayMode == 'a' && CurrentAnimation.hasDecayed
							&& CurrentAnimation.hasChanged) {
						CurrentAnimation.targetNNum += 2;
						CurrentAnimation.targetPNum += 2;
						CurrentAnimation.hasDecayed = false;
					}

					if (CurrentAnimation.decayMode == 'a') {
						a.moving = false;
						a.started = false;
						a.x = x / 3;
						a.y = y / 3;
					} else if (CurrentAnimation.decayMode == 'B') {
						antiEl.moving = false;
						antiEl.started = false;
						antiEl.x = x / 3;
						antiEl.y = y / 3;
						v.moving = false;
						v.started = false;
						v.x = x / 3;
						v.y = y / 3;
					} else if (CurrentAnimation.decayMode == 'b') {
						el.moving = false;
						el.started = false;
						el.x = x / 3;
						el.y = y / 3;
						antiV.moving = false;
						antiV.started = false;
						antiV.x = x / 3;
						antiV.y = y / 3;
					} else {
						p.moving = false;
						p.started = false;
						p.x = x / 3;
						p.y = y / 3;
					}
					((Timer) e.getSource()).stop();
				}

				// at a random time and when the particles are not moving
				if (random.nextInt(5000) == 1 && !p.moving && !a.moving && !el.moving && !antiEl.moving && !v.moving
						&& !antiV.moving) {
					if (CurrentAnimation.decayMode == 'b') {
						el.started = true;
						el.moving = true;
						antiV.started = true;
						antiV.moving = true;
					} else if (CurrentAnimation.decayMode == 'B') {
						antiEl.started = true;
						antiEl.moving = true;
						v.started = true;
						v.moving = true;
					} else if (CurrentAnimation.decayMode == 'a') {
						a.started = true;
						a.moving = true;
					} else {
						p.started = true;
						p.moving = true;
					}
					CurrentAnimation.hasDecayed = true;
				}
				repaint();
			}
		});
		timer.start();
	}
}