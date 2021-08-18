import javax.imageio.ImageIO;
import javax.swing.UIManager.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class main {
	static Color topColor = new Color(109, 196, 237);
	static Color leftColor = new Color(143, 216, 130);

	// screen styling
	static Dimension mainButtonsSize = new Dimension(200, 120);
	static Dimension lodButtonsSize = new Dimension(150, 30);
	static Font titleFont = new Font("Trebuchet MS", Font.BOLD, 20);
	static Font optionFont = new Font("Trebuchet MS", Font.BOLD, 16);
	static Font secondaryFont = new Font("Trebuchet MS", Font.PLAIN, 14);

	// get screen dimensions
	public static int getScreenSizeX() {
		// getting local screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return (int) screenSize.getWidth();
	}

	public static int getScreenSizeY() {
		// getting local screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return (int) screenSize.getHeight();
	}

	// method that draws the components onto the decay screen
	private static Component drawDecayContent(Container screen, final CardLayout cl, final Container master) {
		// use border layout for screen
		screen.setLayout(new BorderLayout());

		// top bar
		// creation-----------------------------------------------------------------------------
		JPanel topBar = new JPanel();
		topBar.setLayout(new BorderLayout());

		// panel that holds everything to do with the level of detail (lod) options
		JPanel lodHolder = new JPanel();
		lodHolder.setLayout(new BoxLayout(lodHolder, BoxLayout.Y_AXIS));
		lodHolder.setBackground(topColor);

		// heading of the level of detail section
		JLabel lodHeading = new JLabel("Level of Detail");
		lodHeading.setFont(titleFont);
		lodHeading.setBorder(new EmptyBorder(10, 10, 10, 10));
		lodHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
		lodHolder.add(lodHeading);

		// panel that contains the a level and gcse buttons
		JPanel lodButtHolder = new JPanel();
		lodButtHolder.setBorder(new EmptyBorder(15, 10, 10, 10));
		lodButtHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		lodButtHolder.setBackground(topColor);

		// the level of detail buttons
		final JButton aLevel = new JButton("A Level");
		aLevel.setFont(secondaryFont);
		aLevel.setPreferredSize(lodButtonsSize);
		lodButtHolder.add(aLevel);
		final JButton gcse = new JButton("GCSE");
		gcse.setFont(secondaryFont);
		gcse.setPreferredSize(lodButtonsSize);
		lodButtHolder.add(gcse);
		lodHolder.add(lodButtHolder);

		// panel that holds everything to do with the simulation options
		JPanel optionsHolder = new JPanel();
		optionsHolder.setBackground(topColor);
		optionsHolder.setLayout(new BoxLayout(optionsHolder, BoxLayout.Y_AXIS));

		// holds the prompt and buttons
		JPanel buttonHolder = new JPanel();
		buttonHolder.setBorder(new EmptyBorder(15, 10, 10, 10));
		buttonHolder.setBackground(topColor);

		// heading of the simulation options section
		JLabel optionsHeading = new JLabel("Simulation Options");
		optionsHeading.setFont(titleFont);
		optionsHolder.add(optionsHeading);

		// prompt
		JLabel prompt = new JLabel("Select Decay Method:");
		prompt.setFont(secondaryFont);
		buttonHolder.add(prompt);

		// all four buttons
		final JRadioButton alpha = new JRadioButton("α");
		final JRadioButton betaMinus = new JRadioButton("β-");
		final JRadioButton betaPlus = new JRadioButton("β+");
		final JRadioButton gamma = new JRadioButton("γ");

		final ButtonGroup group = new ButtonGroup();
		group.add(alpha);
		group.add(betaMinus);
		group.add(betaPlus);
		group.add(gamma);

		alpha.setFont(optionFont);
		betaMinus.setFont(optionFont);
		betaPlus.setFont(optionFont);
		gamma.setFont(optionFont);

		alpha.setBackground(topColor);
		betaMinus.setBackground(topColor);
		betaPlus.setBackground(topColor);
		gamma.setBackground(topColor);

		buttonHolder.add(alpha);
		buttonHolder.add(betaMinus);
		buttonHolder.add(betaPlus);
		buttonHolder.add(gamma);

		JLabel sliderLabel = new JLabel("Speed Control:");
		sliderLabel.setFont(secondaryFont);
		JSlider speedSlide = new JSlider(JSlider.HORIZONTAL, CurrentAnimation.minSpeed, CurrentAnimation.maxSpeed,
				CurrentAnimation.defSpeed);
		speedSlide.setMajorTickSpacing(1);
		speedSlide.setPreferredSize(new Dimension(100, 50));
		speedSlide.setPaintTicks(true);
		speedSlide.setPaintLabels(true);

		optionsHolder.add(buttonHolder);

		optionsHolder.add(sliderLabel);
		optionsHolder.add(speedSlide);

		// return to menu button creation (within its own panel in order to have correct
		// formatting)
		JButton goToMenu = new JButton("Main Menu");
		goToMenu.setFont(secondaryFont);
		goToMenu.setPreferredSize(mainButtonsSize);
		JPanel menuButtHolder = new JPanel();
		menuButtHolder.setBackground(topColor);
		menuButtHolder.add(goToMenu);

		// add all that to the top bar
		topBar.add(menuButtHolder, BorderLayout.WEST);
		topBar.add(optionsHolder, BorderLayout.CENTER);
		topBar.add(lodHolder, BorderLayout.EAST);

		// reset button creation
		JButton resetButt = new JButton("Reset");
		resetButt.setFont(secondaryFont);
		resetButt.setPreferredSize(new Dimension(120, 120));

		// panels that holds the reset button in correct position on screen
		JPanel resetHolder = new JPanel();
		resetHolder.setLayout(new BorderLayout());
		resetHolder.setBackground(leftColor);
		JPanel resetHolderSec = new JPanel();
		resetHolderSec.setBackground(leftColor);
		resetHolderSec.add(resetButt);
		resetHolder.add(resetHolderSec, BorderLayout.SOUTH);

		// left bar
		// creation-----------------------------------------------------------------------------
		JPanel leftBar = new JPanel();
		leftBar.setBackground(leftColor);
		leftBar.setLayout(new BorderLayout());

		// panel that holds the nucleus selection content
		JPanel nucleusSelectionArea = new JPanel();
		nucleusSelectionArea.setLayout(new BoxLayout(nucleusSelectionArea, BoxLayout.Y_AXIS));

		// heading content
		JLabel selectionPrompt = new JLabel("Select An Isotope:");
		selectionPrompt.setFont(secondaryFont);
		JPanel nucHeadingHolder = new JPanel();
		nucHeadingHolder.setBackground(leftColor);
		nucHeadingHolder.add(selectionPrompt);
		nucleusSelectionArea.add(nucHeadingHolder);

		// drop down contents
		CSVContent csvContent = new CSVContent();
		// drop down options are brought in from CSVContent class
		final JComboBox nucleusDropdown = new JComboBox(
				csvContent.getOptionNames(csvContent.getFileContent("decay.csv")));
		nucleusDropdown.setPreferredSize(lodButtonsSize);
		JPanel dropdownHolder = new JPanel();
		dropdownHolder.setBackground(leftColor);
		dropdownHolder.add(nucleusDropdown);
		nucleusSelectionArea.add(dropdownHolder);

		// region that will contain the live simulation data
		JPanel dataHolder = new JPanel();
		dataHolder.setLayout(new BoxLayout(dataHolder, BoxLayout.Y_AXIS));
		dataHolder.setBackground(leftColor);

		JLabel dataHeading = new JLabel("Simulation Info");
		dataHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
		dataHeading.setFont(new Font("Courier New", Font.BOLD, 14));

		// target name
		final JLabel targetName = new JLabel("Target: null");
		targetName.setAlignmentX(Component.CENTER_ALIGNMENT);
		targetName.setFont(new Font("Courier New", Font.ITALIC, 12));

		// number of protons in target
		final JLabel targetPNum = new JLabel("Proton #: null");
		targetPNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		targetPNum.setFont(new Font("Courier New", Font.ITALIC, 12));

		// number of neutrons in target
		final JLabel targetNNum = new JLabel("Neutron #: null");
		targetNNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		targetNNum.setFont(new Font("Courier New", Font.ITALIC, 12));

		// target mass
		final JLabel mass = new JLabel("Mass (u): null");
		mass.setAlignmentX(Component.CENTER_ALIGNMENT);
		mass.setFont(new Font("Courier New", Font.ITALIC, 12));

		// energy out
		final JLabel outEnergy = new JLabel("Energy Out (MeV): null");
		outEnergy.setAlignmentX(Component.CENTER_ALIGNMENT);
		outEnergy.setFont(new Font("Courier New", Font.ITALIC, 12));

		// half life
		final JLabel halfLife = new JLabel("Half Life: null");
		halfLife.setAlignmentX(Component.CENTER_ALIGNMENT);
		halfLife.setFont(new Font("Courier New", Font.ITALIC, 12));

		dataHolder.add(dataHeading);
		dataHolder.add(targetName);
		dataHolder.add(targetPNum);
		dataHolder.add(targetNNum);
		dataHolder.add(mass);
		dataHolder.add(outEnergy);
		dataHolder.add(halfLife);

		// start button creation
		JButton startButt = new JButton("Start!");
		startButt.setFont(secondaryFont);
		startButt.setPreferredSize(mainButtonsSize);
		JPanel startButtHolder = new JPanel();
		startButtHolder.setBackground(leftColor);
		startButtHolder.add(startButt);

		// add everything to the bars appropriately
		leftBar.add(nucleusSelectionArea);
		leftBar.add(dataHolder, BorderLayout.SOUTH);
		leftBar.add(startButtHolder, BorderLayout.NORTH);

		// component that holds the visuals
		final DecaySim draw = new DecaySim();

		// add the bars to the screen
		screen.add(topBar, BorderLayout.NORTH);
		screen.add(resetHolder, BorderLayout.EAST);
		screen.add(leftBar, BorderLayout.WEST);
		screen.add(draw, BorderLayout.CENTER);

		// action listener for slider
		speedSlide.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider src = (JSlider) e.getSource();
				CurrentAnimation.speed = src.getValue();
			}
		});

		// action listener to send back to menu screen on button press
		goToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CurrentAnimation.started = false;
				CurrentAnimation.selected = false;
				CurrentAnimation.hasDecayed = false;
				CurrentAnimation.hasChanged = false;
				CurrentAnimation.targetMass = 0;
				CurrentAnimation.targetNNum = 0;
				CurrentAnimation.targetPNum = 0;
				cl.show(master, "0");
			}
		});

		// action listeners for lod buttons
		aLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// toggle pressability
				gcse.setEnabled(true);
				aLevel.setEnabled(false);

				CurrentAnimation.lod = 1;
			}
		});

		gcse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// toggle pressability
				gcse.setEnabled(false);
				aLevel.setEnabled(true);

				CurrentAnimation.lod = 0;
			}
		});

		// nucleus selection
		nucleusDropdown.addActionListener(new ActionListener() {
			CSVContent csv = new CSVContent();

			public void actionPerformed(ActionEvent e) {
				CurrentAnimation.started = false;
				CurrentAnimation.hasChanged = false;
				// if the selected item is "Select", then keep as null.
				// Otherwise, set data display to relevant csv content

				if (nucleusDropdown.getSelectedItem().toString().equals("Select")) {
					targetName.setText("Start Nucleus: " + "null");
					targetPNum.setText("Proton #: " + "null");
					targetNNum.setText("Neutron #: " + "null");
					mass.setText("Mass (u): " + "null");
					halfLife.setText("Half Life: " + "null");
					outEnergy.setText("Energy Out (MeV): " + "null");

					// set the properties to 0 so it won't be drawn at all
					CurrentAnimation.targetMass = 0;
					CurrentAnimation.targetPNum = 0;
					CurrentAnimation.targetNNum = 0;

				} else {

					// set the display labels
					targetName.setText("Target: " + csv.getOptionFullNames(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString()));
					targetPNum.setText("Proton #: " + csv.getOptionProtons(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString()));
					targetNNum.setText("Neutron #: " + csv.getOptionNeutrons(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString()));
					mass.setText("Mass (u): " + csv.getOptionMass(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString()));
					halfLife.setText("Half Life: " + csv.getDecayOptionHalfLife(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString()));

					// set the selection values

					CurrentAnimation.targetMass = Math
							.round(Float.parseFloat(csv.getOptionMass(csv.getFileContent("decay.csv"),
									nucleusDropdown.getSelectedItem().toString())));
					CurrentAnimation.targetPNum = Math
							.round(Float.parseFloat(csv.getOptionProtons(csv.getFileContent("decay.csv"),
									nucleusDropdown.getSelectedItem().toString())));
					CurrentAnimation.targetNNum = Math
							.round(Float.parseFloat(csv.getOptionNeutrons(csv.getFileContent("decay.csv"),
									nucleusDropdown.getSelectedItem().toString())));
				}
				CurrentAnimation.selected = true;
				// redraw
				draw.repaint();

				// clear any selected buttons
				group.clearSelection();

				// check the available decays from CSVContent and deactivate the unusable decay
				// mode radio buttons
				// if it is available, set the radio button to enabled

				if (csv.getDecayModes(csv.getFileContent("decay.csv"),
						nucleusDropdown.getSelectedItem().toString())[0] == false) {
					alpha.setEnabled(false);

				} else {
					alpha.setEnabled(true);
				}

				if (csv.getDecayModes(csv.getFileContent("decay.csv"),
						nucleusDropdown.getSelectedItem().toString())[1] == false) {
					betaMinus.setEnabled(false);
				} else {
					betaMinus.setEnabled(true);

				}
				if (csv.getDecayModes(csv.getFileContent("decay.csv"),
						nucleusDropdown.getSelectedItem().toString())[2] == false) {
					betaPlus.setEnabled(false);
				} else {
					betaPlus.setEnabled(true);

				}
				if (csv.getDecayModes(csv.getFileContent("decay.csv"),
						nucleusDropdown.getSelectedItem().toString())[3] == false) {
					gamma.setEnabled(false);
				} else {
					gamma.setEnabled(true);
				}
			}
		});

		// action listener for reset button press
		resetButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CurrentAnimation.started = false;
				nucleusDropdown.setEnabled(true);
				draw.repaint();
			}
		});

		// action listener for start button press
		startButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// check if decay mode is chosen
				if (!(alpha.isSelected() || betaMinus.isSelected() || betaPlus.isSelected() || gamma.isSelected())) {
					JOptionPane.showMessageDialog(null, "You haven't selected a decay mode yet!", "Wait!",
							JOptionPane.INFORMATION_MESSAGE);
					CurrentAnimation.started = false;
				}
				// check if isotope is chosen
				else if (nucleusDropdown.getSelectedItem().equals("Select")) {
					JOptionPane.showMessageDialog(null, "You haven't selected an isotope yet!", "Wait!",
							JOptionPane.INFORMATION_MESSAGE);
					CurrentAnimation.started = false;
				}
				// check if level of detail is chosen
				else if ((aLevel.isEnabled() && gcse.isEnabled())) {
					JOptionPane.showMessageDialog(null, "You haven't selected a level of detail yet!", "Wait!",
							JOptionPane.INFORMATION_MESSAGE);
					CurrentAnimation.started = false;

					// begin!
				} else {
					CurrentAnimation.started = true;
					CurrentAnimation.hasChanged = false;
					nucleusDropdown.setEnabled(false);

					// set decay mode
					if (alpha.isSelected()) {
						CurrentAnimation.decayMode = 'a';
					} else if (betaMinus.isSelected()) {
						CurrentAnimation.decayMode = 'b';
					} else if (betaPlus.isSelected()) {
						CurrentAnimation.decayMode = 'B';
					} else {
						CurrentAnimation.decayMode = 'c';
					}

					draw.repaint();
				}
			}
		});

		// action listeners for radio buttons - set energy out label
		alpha.addActionListener(new ActionListener() {
			CSVContent csv = new CSVContent();

			public void actionPerformed(ActionEvent e) {
				if (nucleusDropdown.getSelectedItem().toString().equals("Select")) {
					outEnergy.setText("Energy Out (MeV): " + "null");
				} else {
					outEnergy.setText("Energy Out (MeV): " + csv.getDecayOptionEnergy(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString(), 1));
					targetPNum.setText("Proton #: " + csv.getOptionProtons(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString()));
					targetNNum.setText("Neutron #: " + csv.getOptionNeutrons(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString()));
					mass.setText("Mass (u): " + csv.getOptionMass(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString()));
				}

			}
		});

		betaMinus.addActionListener(new ActionListener() {
			CSVContent csv = new CSVContent();

			public void actionPerformed(ActionEvent e) {
				if (nucleusDropdown.getSelectedItem().toString().equals("Select")) {
					outEnergy.setText("Energy Out (MeV): " + "null");
				} else {
					outEnergy.setText("Energy Out (MeV): " + csv.getDecayOptionEnergy(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString(), 2));
				}
			}
		});

		betaPlus.addActionListener(new ActionListener() {
			CSVContent csv = new CSVContent();

			public void actionPerformed(ActionEvent e) {
				if (nucleusDropdown.getSelectedItem().toString().equals("Select")) {
					outEnergy.setText("Energy Out (MeV): " + "null");
				} else {
					outEnergy.setText("Energy Out (MeV): " + csv.getDecayOptionEnergy(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString(), 3));
				}
			}
		});

		gamma.addActionListener(new ActionListener() {
			CSVContent csv = new CSVContent();

			public void actionPerformed(ActionEvent e) {
				if (nucleusDropdown.getSelectedItem().toString().equals("Select")) {
					outEnergy.setText("Energy Out (MeV): " + "null");
				} else {
					outEnergy.setText("Energy Out (MeV): " + csv.getDecayOptionEnergy(csv.getFileContent("decay.csv"),
							nucleusDropdown.getSelectedItem().toString(), 4));
				}
			}
		});

		/*
		 * return the completed screen back to the drawContent method to be added to the
		 * master JPanel using CardLayout
		 */

		return screen;
	}

	// method that draws the components onto the fusion screen
	public static Component drawFusionContent(Container screen, final CardLayout cl, final Container master) {
		// use border layout for screen
		screen.setLayout(new BorderLayout());

		// top bar
		// creation-----------------------------------------------------------------------------
		JPanel topBar = new JPanel();
		topBar.setLayout(new BorderLayout());

		// panel that holds everything to do with the level of detail (lod) options
		JPanel lodHolder = new JPanel();
		lodHolder.setLayout(new BoxLayout(lodHolder, BoxLayout.Y_AXIS));
		lodHolder.setBackground(topColor);

		// heading of the level of detail section
		JLabel lodHeading = new JLabel("Level of Detail");
		lodHeading.setFont(titleFont);
		lodHeading.setBorder(new EmptyBorder(10, 10, 10, 10));
		lodHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
		lodHolder.add(lodHeading);

		// panel that contains the a level and gcse buttons
		JPanel lodButtHolder = new JPanel();
		lodButtHolder.setBorder(new EmptyBorder(15, 10, 10, 10));
		lodButtHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		lodButtHolder.setBackground(topColor);

		// the level of detail buttons
		final JButton aLevel = new JButton("A Level");
		aLevel.setFont(secondaryFont);
		aLevel.setPreferredSize(lodButtonsSize);
		aLevel.setEnabled(false);
		lodButtHolder.add(aLevel);
		final JButton gcse = new JButton("GCSE");
		gcse.setFont(secondaryFont);
		gcse.setPreferredSize(lodButtonsSize);
		gcse.setEnabled(false);
		lodButtHolder.add(gcse);
		lodHolder.add(lodButtHolder);

		// panel that holds everything to do with the simulation options
		JPanel optionsHolder = new JPanel();
		optionsHolder.setLayout(new BoxLayout(optionsHolder, BoxLayout.Y_AXIS));
		optionsHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionsHolder.setBackground(topColor);

		// heading of the simulation options section
		JLabel optionsHeading = new JLabel("Simulation Options");
		optionsHeading.setBorder(new EmptyBorder(10, 10, 10, 10));
		optionsHeading.setFont(titleFont);
		optionsHolder.add(optionsHeading);

		// input for temperature & pressure
		final JTextField temperatureIn = new JTextField();
		temperatureIn.setPreferredSize(new Dimension(100, 25));
		final JTextField pressureIn = new JTextField();
		pressureIn.setPreferredSize(new Dimension(100, 25));

		// holds the prompt and text fields
		JPanel fieldHolder = new JPanel();
		fieldHolder.setBorder(new EmptyBorder(15, 10, 10, 10));
		fieldHolder.setBackground(topColor);
		fieldHolder.setLayout(new BoxLayout(fieldHolder, BoxLayout.X_AXIS));

		// prompts
		JLabel tempPrompt = new JLabel("Enter Temperature (°C):");
		JLabel pressPrompt = new JLabel("Enter Pressure (GPa):");
		tempPrompt.setFont(secondaryFont);
		pressPrompt.setFont(secondaryFont);
		fieldHolder.add(tempPrompt);
		fieldHolder.add(temperatureIn);
		fieldHolder.add(pressPrompt);
		fieldHolder.add(pressureIn);

		JLabel sliderLabel = new JLabel("Speed Control:");
		sliderLabel.setFont(secondaryFont);
		JSlider speedSlide = new JSlider(JSlider.HORIZONTAL, CurrentAnimation.minSpeed, CurrentAnimation.maxSpeed,
				CurrentAnimation.defSpeed);
		speedSlide.setMajorTickSpacing(1);
		speedSlide.setPaintTicks(true);
		speedSlide.setPaintLabels(true);

		optionsHolder.add(fieldHolder);
		optionsHolder.add(sliderLabel);
		optionsHolder.add(speedSlide);

		// return to menu button creation (within its own panel in order to have correct
		// formatting)
		JButton goToMenu = new JButton("Main Menu");
		goToMenu.setFont(secondaryFont);
		goToMenu.setPreferredSize(mainButtonsSize);
		JPanel menuButtHolder = new JPanel();
		menuButtHolder.setBackground(topColor);
		menuButtHolder.add(goToMenu);

		// add all that to the top bar
		topBar.add(menuButtHolder, BorderLayout.WEST);
		topBar.add(optionsHolder, BorderLayout.CENTER);
		topBar.add(lodHolder, BorderLayout.EAST);

		// reset button creation
		JButton resetButt = new JButton("Reset");
		resetButt.setFont(secondaryFont);
		resetButt.setPreferredSize(new Dimension(120, 120));

		// panels that holds the reset button in correct position on screen
		JPanel resetHolder = new JPanel();
		resetHolder.setLayout(new BorderLayout());
		resetHolder.setBackground(leftColor);
		JPanel resetHolderSec = new JPanel();
		resetHolderSec.setBackground(leftColor);
		resetHolderSec.add(resetButt);
		resetHolder.add(resetHolderSec, BorderLayout.SOUTH);

		// left bar
		// creation-----------------------------------------------------------------------------
		JPanel leftBar = new JPanel();
		leftBar.setBackground(leftColor);
		leftBar.setLayout(new BorderLayout());

		// panel that holds the nucleus selection content
		JPanel nucleusSelectionArea = new JPanel();
		nucleusSelectionArea.setLayout(new BoxLayout(nucleusSelectionArea, BoxLayout.Y_AXIS));

		// heading content
		JLabel selectionPrompt = new JLabel("Select Two Isotopes:");
		selectionPrompt.setFont(secondaryFont);
		JPanel nucHeadingHolder = new JPanel();
		nucHeadingHolder.setBackground(leftColor);
		nucHeadingHolder.add(selectionPrompt);
		nucleusSelectionArea.add(nucHeadingHolder);

		// drop down contents
		CSVContent csvContent = new CSVContent();
		// drop down options are brought in from CSVContent class
		final JComboBox nucleusDropdown1 = new JComboBox(
				csvContent.getOptionNames(csvContent.getFileContent("fusion.csv")));
		final JComboBox nucleusDropdown2 = new JComboBox(
				csvContent.getOptionNames(csvContent.getFileContent("fusion.csv")));
		nucleusDropdown1.setPreferredSize(lodButtonsSize);
		nucleusDropdown2.setPreferredSize(lodButtonsSize);

		// panels to hold the drop down menus
		JPanel dropdownHolder1 = new JPanel();
		dropdownHolder1.setBackground(leftColor);
		dropdownHolder1.add(nucleusDropdown1);
		nucleusSelectionArea.add(dropdownHolder1);
		JPanel dropdownHolder2 = new JPanel();
		dropdownHolder2.setBackground(leftColor);
		dropdownHolder2.add(nucleusDropdown2);
		nucleusSelectionArea.add(dropdownHolder2);

		// region that will contain the live simulation data
		// heading
		JPanel dataHolder = new JPanel();
		dataHolder.setLayout(new BoxLayout(dataHolder, BoxLayout.Y_AXIS));
		dataHolder.setBackground(leftColor);
		JLabel dataHeading = new JLabel("Simulation Info");
		dataHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
		dataHeading.setFont(new Font("Courier New", Font.BOLD, 14));

		// temperature
		final JLabel temperatureInfo = new JLabel("Temperature (°C): null");
		temperatureInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		temperatureInfo.setFont(new Font("Courier New", Font.ITALIC, 12));

		// pressure
		final JLabel pressureInfo = new JLabel("Pressure (GPa): null");
		pressureInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		pressureInfo.setFont(new Font("Courier New", Font.ITALIC, 12));

		// nucleus name 1
		final JLabel name1 = new JLabel("Nucleus 1: null");
		name1.setAlignmentX(Component.CENTER_ALIGNMENT);
		name1.setFont(new Font("Courier New", Font.ITALIC, 12));

		// nucleus name 2
		final JLabel name2 = new JLabel("Nucleus 2: null");
		name2.setAlignmentX(Component.CENTER_ALIGNMENT);
		name2.setFont(new Font("Courier New", Font.ITALIC, 12));

		// number of protons in nucleus 1
		final JLabel pNum1 = new JLabel("Proton # (1): null");
		pNum1.setAlignmentX(Component.CENTER_ALIGNMENT);
		pNum1.setFont(new Font("Courier New", Font.ITALIC, 12));

		// number of protons in nucleus 2
		final JLabel pNum2 = new JLabel("Proton # (2): null");
		pNum2.setAlignmentX(Component.CENTER_ALIGNMENT);
		pNum2.setFont(new Font("Courier New", Font.ITALIC, 12));

		// number of neutrons in nucleus 1
		final JLabel nNum1 = new JLabel("Neutron # (1): null");
		nNum1.setAlignmentX(Component.CENTER_ALIGNMENT);
		nNum1.setFont(new Font("Courier New", Font.ITALIC, 12));

		// number of neutrons in nucleus 2
		final JLabel nNum2 = new JLabel("Neutron # (2): null");
		nNum2.setAlignmentX(Component.CENTER_ALIGNMENT);
		nNum2.setFont(new Font("Courier New", Font.ITALIC, 12));

		// mass 1
		final JLabel mass1 = new JLabel("Mass 1 (u): null");
		mass1.setAlignmentX(Component.CENTER_ALIGNMENT);
		mass1.setFont(new Font("Courier New", Font.ITALIC, 12));

		// mass 2
		final JLabel mass2 = new JLabel("Mass 2 (u): null");
		mass2.setAlignmentX(Component.CENTER_ALIGNMENT);
		mass2.setFont(new Font("Courier New", Font.ITALIC, 12));

		// energy out
		final JLabel outEnergy = new JLabel("Energy Out (MeV): null");
		outEnergy.setAlignmentX(Component.CENTER_ALIGNMENT);
		outEnergy.setFont(new Font("Courier New", Font.ITALIC, 12));

		dataHolder.add(dataHeading);
		dataHolder.add(temperatureInfo);
		dataHolder.add(pressureInfo);
		dataHolder.add(name1);
		dataHolder.add(name2);
		dataHolder.add(pNum1);
		dataHolder.add(pNum2);
		dataHolder.add(nNum1);
		dataHolder.add(nNum2);
		dataHolder.add(mass1);
		dataHolder.add(mass2);
		dataHolder.add(outEnergy);

		// start button creation
		JButton startButt = new JButton("Start!");
		startButt.setFont(secondaryFont);
		startButt.setPreferredSize(mainButtonsSize);
		JPanel startButtHolder = new JPanel();
		startButtHolder.setBackground(leftColor);
		startButtHolder.add(startButt);

		// add everything to the bars appropriately
		leftBar.add(nucleusSelectionArea);
		leftBar.add(dataHolder, BorderLayout.SOUTH);
		leftBar.add(startButtHolder, BorderLayout.NORTH);

		// panel that holds the visuals
		final FusionSim draw = new FusionSim();

		// add the bars to the screen
		screen.add(topBar, BorderLayout.NORTH);
		screen.add(resetHolder, BorderLayout.EAST);
		screen.add(leftBar, BorderLayout.WEST);
		screen.add(draw, BorderLayout.CENTER);

		// action listener for slider
		speedSlide.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider src = (JSlider) e.getSource();
				CurrentAnimation.speed = src.getValue();
			}
		});

		// action listener to send back to menu screen on button press
		goToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CurrentAnimation.started = false;
				CurrentAnimation.targetLeftMass = 0;
				CurrentAnimation.targetRightMass = 0;
				CurrentAnimation.targetLeftPNum = 0;
				CurrentAnimation.targetRightPNum = 0;
				CurrentAnimation.targetLeftNNum = 0;
				CurrentAnimation.targetRightNNum = 0;

				cl.show(master, "0");
			}
		});

		final Validation validate = new Validation();

		// action listener to get temperature input
		temperatureIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if input is out of range, then show relevant error message
				if (validate.validateTemperature(temperatureIn.getText()) == -300.0) {
					JOptionPane.showMessageDialog(null,
							"That input is out of range!  \n Acceptable Values: \n -273 - 100000000000",
							"Invalid Input", JOptionPane.INFORMATION_MESSAGE);
					temperatureInfo.setText("Temperature: " + "OoR" + "°C");
					// if input is NaN, then show relevant error message
				} else if (validate.validateTemperature(temperatureIn.getText()) == -400.0) {
					JOptionPane.showMessageDialog(null, "That input is not a number", "Invalid Input",
							JOptionPane.INFORMATION_MESSAGE);
					temperatureInfo.setText("Temperature: " + "NaN" + "°C");
					// else, show temperature in data area
				} else {
					temperatureInfo
							.setText("Temperature: " + validate.validateTemperature(temperatureIn.getText()) + "°C");
					CurrentAnimation.temperature = (int) validate.validateTemperature(temperatureIn.getText());
				}
			}
		});

		// action listener to get pressure input
		pressureIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if input is out of range, then show relevant error message
				if (validate.validatePressure(pressureIn.getText()) == -300.0) {
					JOptionPane.showMessageDialog(null,
							"That input is out of range!  \n Acceptable Values: \n 1 - 1000000000", "Invalid Input",
							JOptionPane.INFORMATION_MESSAGE);
					pressureInfo.setText("Pressure: " + "OoR" + "GPa");
					// if input is NaN, then show relevant error message
				} else if (validate.validatePressure(pressureIn.getText()) == -400.0) {
					JOptionPane.showMessageDialog(null, "That input is not a number", "Invalid Input",
							JOptionPane.INFORMATION_MESSAGE);
					pressureInfo.setText("Pressure: " + "NaN" + "GPa");
					// else, show pressure in data area
				} else {
					pressureInfo.setText("Pressure: " + validate.validatePressure(pressureIn.getText()) + "GPa");
					CurrentAnimation.pressure = (int) validate.validatePressure(pressureIn.getText());
				}
			}
		});

		// NOT IN USE
		// action listeners for lod buttons
		aLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// toggle pressability
				gcse.setEnabled(true);
				aLevel.setEnabled(false);

				CurrentAnimation.lod = 1;
			}
		});

		gcse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// toggle pressability
				gcse.setEnabled(false);
				aLevel.setEnabled(true);

				CurrentAnimation.lod = 0;
			}
		});

		// nucleus selection 1
		nucleusDropdown1.addActionListener(new ActionListener() {
			CSVContent csv = new CSVContent();

			public void actionPerformed(ActionEvent e) {
				CurrentAnimation.started = false;

				// if the selected item is "Select", then keep as null.
				// Otherwise, set data display to relevant csv content

				if (nucleusDropdown1.getSelectedItem().toString().equals("Select")) {
					name1.setText("Nucleus 1: " + "null");
					pNum1.setText("Proton # (1): " + "null");
					nNum1.setText("Neutron # (1): " + "null");
					mass1.setText("Mass 1 (u): " + "null");

					// set everything to 0 so it wont be drawn
					CurrentAnimation.targetLeftNNum = 0;
					CurrentAnimation.targetLeftPNum = 0;

				} else {
					name1.setText("Nucleus 1: " + csv.getOptionFullNames(csv.getFileContent("fusion.csv"),
							nucleusDropdown1.getSelectedItem().toString()));
					pNum1.setText("Proton # (1): " + csv.getOptionProtons(csv.getFileContent("fusion.csv"),
							nucleusDropdown1.getSelectedItem().toString()));
					nNum1.setText("Neutron # (1): " + csv.getOptionNeutrons(csv.getFileContent("fusion.csv"),
							nucleusDropdown1.getSelectedItem().toString()));
					mass1.setText("Mass 1 (u): " + csv.getOptionMass(csv.getFileContent("fusion.csv"),
							nucleusDropdown1.getSelectedItem().toString()));

					// set the selection values
					CurrentAnimation.targetLeftMass = Math
							.round(Float.parseFloat(csv.getOptionMass(csv.getFileContent("fusion.csv"),
									nucleusDropdown1.getSelectedItem().toString())));
					CurrentAnimation.targetLeftPNum = Math
							.round(Float.parseFloat(csv.getOptionProtons(csv.getFileContent("fusion.csv"),
									nucleusDropdown1.getSelectedItem().toString())));
					CurrentAnimation.targetLeftNNum = Math
							.round(Float.parseFloat(csv.getOptionNeutrons(csv.getFileContent("fusion.csv"),
									nucleusDropdown1.getSelectedItem().toString())));

				}
				// redraw
				draw.repaint();
			}
		});

		// nucleus selection 2
		nucleusDropdown2.addActionListener(new ActionListener() {
			CSVContent csv = new CSVContent();

			public void actionPerformed(ActionEvent e) {
				CurrentAnimation.started = false;

				// if the selected item is "Select", then keep as null.
				// Otherwise, set data display to relevant csv content

				if (nucleusDropdown2.getSelectedItem().toString().equals("Select")) {
					name2.setText("Nucleus 2: " + "null");
					pNum2.setText("Proton # (2): " + "null");
					nNum2.setText("Neutron # (2): " + "null");
					mass2.setText("Mass 2 (u): " + "null");
					outEnergy.setText("Energy Out (MeV): " + "null");

					// set everything to 0 so it won't be drawn
					CurrentAnimation.targetRightNNum = 0;
					CurrentAnimation.targetRightPNum = 0;

				} else {
					name2.setText("Nucleus 2: " + csv.getOptionFullNames(csv.getFileContent("fusion.csv"),
							nucleusDropdown2.getSelectedItem().toString()));
					pNum2.setText("Proton # (2): " + csv.getOptionProtons(csv.getFileContent("fusion.csv"),
							nucleusDropdown2.getSelectedItem().toString()));
					nNum2.setText("Neutron # (2): " + csv.getOptionNeutrons(csv.getFileContent("fusion.csv"),
							nucleusDropdown2.getSelectedItem().toString()));
					mass2.setText("Mass 2 (u): " + csv.getOptionMass(csv.getFileContent("fusion.csv"),
							nucleusDropdown2.getSelectedItem().toString()));

					// set the selection values
					CurrentAnimation.targetRightMass = Math
							.round(Float.parseFloat(csv.getOptionMass(csv.getFileContent("fusion.csv"),
									nucleusDropdown2.getSelectedItem().toString())));
					CurrentAnimation.targetRightPNum = Math
							.round(Float.parseFloat(csv.getOptionProtons(csv.getFileContent("fusion.csv"),
									nucleusDropdown2.getSelectedItem().toString())));
					CurrentAnimation.targetRightNNum = Math
							.round(Float.parseFloat(csv.getOptionNeutrons(csv.getFileContent("fusion.csv"),
									nucleusDropdown2.getSelectedItem().toString())));
				}
				// redraw
				CurrentAnimation.selected1 = true;
				CurrentAnimation.selected2 = true;
				draw.repaint();
			}
		});

		// action listener for reset button press
		resetButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CurrentAnimation.started = false;
				temperatureIn.setEnabled(true);
				pressureIn.setEnabled(true);
				nucleusDropdown1.setEnabled(true);
				nucleusDropdown2.setEnabled(true);

				draw.repaint();
			}
		});

		// action listener for start press
		startButt.addActionListener(new ActionListener() {
			CSVContent csv = new CSVContent();

			public void actionPerformed(ActionEvent e) {

				// check if temperature is chosen
				if (temperatureIn.getText().isEmpty() || validate.validateTemperature(temperatureIn.getText()) == -300.0
						|| validate.validateTemperature(temperatureIn.getText()) == -300.0) {
					JOptionPane.showMessageDialog(null, "You haven't selected a temperature yet!", "Wait!",
							JOptionPane.INFORMATION_MESSAGE);
					CurrentAnimation.started = false;
				}

				// check if pressure is chosen
				if (pressureIn.getText().isEmpty() || validate.validatePressure(pressureIn.getText()) == -300.0
						|| validate.validatePressure(pressureIn.getText()) == -300.0) {
					JOptionPane.showMessageDialog(null, "You haven't selected a pressure yet!", "Wait!",
							JOptionPane.INFORMATION_MESSAGE);
					CurrentAnimation.started = false;
				}

				// check if isotope 1 is chosen
				else if (nucleusDropdown1.getSelectedItem().equals("Select")) {
					JOptionPane.showMessageDialog(null, "You haven't selected an isotope yet!", "Wait!",
							JOptionPane.INFORMATION_MESSAGE);
					CurrentAnimation.started = false;
				}

				// check if isotope 2 is chosen
				else if (nucleusDropdown2.getSelectedItem().equals("Select")) {
					JOptionPane.showMessageDialog(null, "You haven't selected an isotope yet!", "Wait!",
							JOptionPane.INFORMATION_MESSAGE);
					CurrentAnimation.started = false;
				} /*
					 * 
					 * // check if level of detail is chosen
					 * 
					 * else if ((aLevel.isEnabled() && gcse.isEnabled())) {
					 * JOptionPane.showMessageDialog(null,
					 * "You haven't selected a level of detail yet!", "Wait!",
					 * JOptionPane.INFORMATION_MESSAGE); CurrentAnimation.started = false;
					 * 
					 * 
					 * // begin! }
					 */else {
					// try to convert the text input to a double and then start up the simulation
					try {
						// set temperature
						CurrentAnimation.temperature = (int) Float.parseFloat(temperatureIn.getText());

						CurrentAnimation.started = true;
						temperatureIn.setEnabled(false);
						nucleusDropdown1.setEnabled(false);
						nucleusDropdown2.setEnabled(false);

						// to calculate energy released
						float mass1 = Float.parseFloat(csv.getOptionMass(csv.getFileContent("fusion.csv"),
								nucleusDropdown1.getSelectedItem().toString()));

						float mass2 = Float.parseFloat(csv.getOptionMass(csv.getFileContent("fusion.csv"),
								nucleusDropdown2.getSelectedItem().toString()));

						int pNum1 = Integer.parseInt(csv.getOptionProtons(csv.getFileContent("fusion.csv"),
								nucleusDropdown1.getSelectedItem().toString()));
						int nNum1 = Integer.parseInt(csv.getOptionNeutrons(csv.getFileContent("fusion.csv"),
								nucleusDropdown1.getSelectedItem().toString()));
						int pNum2 = Integer.parseInt(csv.getOptionProtons(csv.getFileContent("fusion.csv"),
								nucleusDropdown2.getSelectedItem().toString()));
						int nNum2 = Integer.parseInt(csv.getOptionNeutrons(csv.getFileContent("fusion.csv"),
								nucleusDropdown2.getSelectedItem().toString()));

						// 931.5 * difference in mass of product to reactants (MeV)
						double energyOut = 931.5 * (Math
								.abs((1.00728 * (pNum1 + pNum2)) + (1.00867 * (nNum1 + nNum2)) - (mass1 + mass2)));

						outEnergy.setText("Energy Out (MeV): " + Math.round(energyOut));

						draw.repaint();

						// if that's not possible, give the user an error message
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "You haven't selected a valid temperature yet!", "Wait!",
								JOptionPane.INFORMATION_MESSAGE);
					}

					// try to convert the text input to a double and then start up the simulation
					try {
						// set pressure
						CurrentAnimation.pressure = (int) Float.parseFloat(pressureIn.getText());

						CurrentAnimation.started = true;
						pressureIn.setEnabled(false);
						nucleusDropdown1.setEnabled(false);
						nucleusDropdown2.setEnabled(false);

						draw.repaint();

						// if that's not possible, give the user an error message
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "You haven't selected a valid pressure yet!", "Wait!",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		/*
		 * return the completed screen back to the drawContent method to be added to the
		 * master JPanel using CardLayout
		 */

		return screen;
	}

	// method that draws the components onto the fission screen
	public static Component drawFissionContent(Container screen, final CardLayout cl, final Container master) {
		CSVContent csvContent = new CSVContent();

		// use border layout for screen
		screen.setLayout(new BorderLayout());

		// top bar
		// creation-----------------------------------------------------------------------------
		JPanel topBar = new JPanel();
		topBar.setLayout(new BorderLayout());

		// panel that holds everything to do with the level of detail (lod) options
		JPanel lodHolder = new JPanel();
		lodHolder.setLayout(new BoxLayout(lodHolder, BoxLayout.Y_AXIS));
		lodHolder.setBackground(topColor);

		// heading of the level of detail section
		JLabel lodHeading = new JLabel("Level of Detail");
		lodHeading.setFont(titleFont);
		lodHeading.setBorder(new EmptyBorder(10, 10, 10, 10));
		lodHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
		lodHolder.add(lodHeading);

		// panel that contains the a level and gcse buttons
		JPanel lodButtHolder = new JPanel();
		lodButtHolder.setBorder(new EmptyBorder(15, 10, 10, 10));
		lodButtHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		lodButtHolder.setBackground(topColor);

		// the level of detail buttons
		final JButton aLevel = new JButton("A Level");
		aLevel.setPreferredSize(lodButtonsSize);
		aLevel.setFont(secondaryFont);
		lodButtHolder.add(aLevel);
		final JButton gcse = new JButton("GCSE");
		gcse.setPreferredSize(lodButtonsSize);
		gcse.setFont(secondaryFont);
		lodButtHolder.add(gcse);
		lodHolder.add(lodButtHolder);

		// panel that holds everything to do with the simulation options
		JPanel optionsHolder = new JPanel();
		optionsHolder.setLayout(new BoxLayout(optionsHolder, BoxLayout.Y_AXIS));
		optionsHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionsHolder.setBackground(topColor);

		// heading of the simulation options section
		JLabel optionsHeading = new JLabel("Simulation Options");
		optionsHeading.setBorder(new EmptyBorder(10, 10, 10, 10));
		optionsHeading.setFont(titleFont);
		optionsHolder.add(optionsHeading);

		// input for neutron velocity
		final JTextField neutronVIn = new JTextField();
		neutronVIn.setPreferredSize(new Dimension(75, 25));

		// holds the prompt and text field
		JPanel fieldHolder = new JPanel();
		fieldHolder.setBorder(new EmptyBorder(15, 10, 10, 10));
		fieldHolder.setBackground(topColor);
		JLabel prompt = new JLabel("Enter Neutron Velocity (km/s):");
		prompt.setFont(secondaryFont);
		fieldHolder.add(prompt);
		fieldHolder.add(neutronVIn);

		JLabel sliderLabel = new JLabel("Speed Control:");
		sliderLabel.setFont(secondaryFont);
		JSlider speedSlide = new JSlider(JSlider.HORIZONTAL, CurrentAnimation.minSpeed, CurrentAnimation.maxSpeed,
				CurrentAnimation.defSpeed);
		speedSlide.setMajorTickSpacing(1);
		speedSlide.setPaintTicks(true);
		speedSlide.setPaintLabels(true);

		optionsHolder.add(fieldHolder);
		optionsHolder.add(sliderLabel);
		optionsHolder.add(speedSlide);

		// return to menu button creation (within its own panel in order to have correct
		// formatting)
		JButton goToMenu = new JButton("Main Menu");
		goToMenu.setFont(secondaryFont);
		goToMenu.setPreferredSize(mainButtonsSize);
		JPanel menuButtHolder = new JPanel();
		menuButtHolder.setBackground(topColor);
		menuButtHolder.add(goToMenu);

		// add all that to the top bar
		topBar.add(menuButtHolder, BorderLayout.WEST);
		topBar.add(optionsHolder, BorderLayout.CENTER);
		topBar.add(lodHolder, BorderLayout.EAST);

		// reset button creation
		JButton resetButt = new JButton("Reset");
		resetButt.setFont(secondaryFont);
		resetButt.setPreferredSize(new Dimension(120, 120));

		// panels that holds the reset button in correct position on screen
		JPanel resetHolder = new JPanel();
		resetHolder.setLayout(new BorderLayout());
		resetHolder.setBackground(leftColor);
		JPanel resetHolderSec = new JPanel();
		resetHolderSec.setBackground(leftColor);
		resetHolderSec.add(resetButt);
		resetHolder.add(resetHolderSec, BorderLayout.SOUTH);

		// left bar
		// creation-----------------------------------------------------------------------------
		JPanel leftBar = new JPanel();
		leftBar.setBackground(leftColor);
		leftBar.setLayout(new BorderLayout());

		// panel that holds the nucleus selection content
		JPanel nucleusSelectionArea = new JPanel();
		nucleusSelectionArea.setLayout(new BoxLayout(nucleusSelectionArea, BoxLayout.Y_AXIS));

		// heading content
		JLabel selectionPrompt = new JLabel("Select An Isotope:");
		selectionPrompt.setFont(secondaryFont);
		JPanel nucHeadingHolder = new JPanel();
		nucHeadingHolder.setBackground(leftColor);
		nucHeadingHolder.add(selectionPrompt);
		nucleusSelectionArea.add(nucHeadingHolder);

		// drop down contents

		// drop down options are brought in from CSVContent class
		final JComboBox nucleusDropdown = new JComboBox(
				csvContent.getOptionNames(csvContent.getFileContent("fission.csv")));
		nucleusDropdown.setPreferredSize(lodButtonsSize);
		JPanel dropdownHolder = new JPanel();
		dropdownHolder.setBackground(leftColor);
		dropdownHolder.add(nucleusDropdown);
		nucleusSelectionArea.add(dropdownHolder);

		// region that will contain the live simulation data
		// heading
		JPanel dataHolder = new JPanel();
		dataHolder.setLayout(new BoxLayout(dataHolder, BoxLayout.Y_AXIS));
		dataHolder.setBackground(leftColor);
		JLabel dataHeading = new JLabel("Simulation Info");
		dataHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
		dataHeading.setFont(new Font("Courier New", Font.BOLD, 14));

		// velocity of neutron
		final JLabel neutronVInfo = new JLabel("Neutron Vel.: null");
		neutronVInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		neutronVInfo.setFont(new Font("Courier New", Font.ITALIC, 12));

		// target name
		final JLabel targetName = new JLabel("Target: null");
		targetName.setAlignmentX(Component.CENTER_ALIGNMENT);
		targetName.setFont(new Font("Courier New", Font.ITALIC, 12));

		// number of protons in target
		final JLabel targetPNum = new JLabel("Proton #: null");
		targetPNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		targetPNum.setFont(new Font("Courier New", Font.ITALIC, 12));

		// number of neutrons in target
		final JLabel targetNNum = new JLabel("Neutron #: null");
		targetNNum.setAlignmentX(Component.CENTER_ALIGNMENT);
		targetNNum.setFont(new Font("Courier New", Font.ITALIC, 12));

		// target mass
		final JLabel mass = new JLabel("Mass (u): null");
		mass.setAlignmentX(Component.CENTER_ALIGNMENT);
		mass.setFont(new Font("Courier New", Font.ITALIC, 12));

		// energy out
		final JLabel outEnergy = new JLabel("Energy Out (MeV): null");
		outEnergy.setAlignmentX(Component.CENTER_ALIGNMENT);
		outEnergy.setFont(new Font("Courier New", Font.ITALIC, 12));

		// products
		final JLabel products = new JLabel("Products: null, null");
		products.setAlignmentX(Component.CENTER_ALIGNMENT);
		products.setFont(new Font("Courier New", Font.ITALIC, 12));

		dataHolder.add(dataHeading);
		dataHolder.add(neutronVInfo);
		dataHolder.add(targetName);
		dataHolder.add(targetPNum);
		dataHolder.add(targetNNum);
		dataHolder.add(mass);
		dataHolder.add(outEnergy);
		dataHolder.add(products);

		// start button creation
		JButton startButt = new JButton("Start!");
		startButt.setFont(secondaryFont);
		startButt.setPreferredSize(mainButtonsSize);
		JPanel startButtHolder = new JPanel();
		startButtHolder.setBackground(leftColor);
		startButtHolder.add(startButt);

		// add everything to the bars appropriately
		leftBar.add(nucleusSelectionArea);
		leftBar.add(dataHolder, BorderLayout.SOUTH);
		leftBar.add(startButtHolder, BorderLayout.NORTH);

		// panel that holds the visuals
		final FissionSim draw = new FissionSim();

		// add the bars to the screen
		screen.add(topBar, BorderLayout.NORTH);
		screen.add(resetHolder, BorderLayout.EAST);
		screen.add(leftBar, BorderLayout.WEST);
		screen.add(draw, BorderLayout.CENTER);

		// action listener for slider
		speedSlide.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider src = (JSlider) e.getSource();
				CurrentAnimation.speed = src.getValue();
			}
		});

		// action listener to send back to menu screen on button press
		goToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CurrentAnimation.started = false;
				CurrentAnimation.hasFissioned = false;
				CurrentAnimation.selected = false;
				CurrentAnimation.targetMass = 0;
				CurrentAnimation.targetNNum = 0;
				CurrentAnimation.targetPNum = 0;
				cl.show(master, "0");
			}
		});

		final Validation validate = new Validation();

		// action listener to get neutron velocity input
		neutronVIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if input is out of range, then show relevant error message
				if (validate.validateVelocity(neutronVIn.getText()) == -300.0) {
					JOptionPane.showMessageDialog(null, "That input is out of range! \n Acceptable Values: \n 0-10",
							"Invalid Input", JOptionPane.INFORMATION_MESSAGE);
					neutronVInfo.setText("Neutron Vel.: " + "OoR" + "km/s");
					// if input is NaN, then show relevant error message
				} else if (validate.validateVelocity(neutronVIn.getText()) == -400.0) {
					JOptionPane.showMessageDialog(null, "That input is not a number", "Invalid Input",
							JOptionPane.INFORMATION_MESSAGE);
					neutronVInfo.setText("Neutron Vel.: " + "NaN" + "km/s");
					// else, show neutron velocity in data area
				} else {
					neutronVInfo.setText("Neutron Vel.: " + validate.validateVelocity(neutronVIn.getText()) + "km/s");
					CurrentAnimation.neutronVel = validate.validateVelocity(neutronVIn.getText());
				}
			}
		});

		// action listeners for lod buttons
		aLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// set what can be clicked
				gcse.setEnabled(true);
				aLevel.setEnabled(false);

				CurrentAnimation.lod = 1;
			}
		});

		gcse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// set what can be clicked
				gcse.setEnabled(false);
				aLevel.setEnabled(true);

				CurrentAnimation.lod = 0;
			}
		});

		// nucleus selection
		nucleusDropdown.addActionListener(new ActionListener() {
			CSVContent csv = new CSVContent();

			public void actionPerformed(ActionEvent e) {
				CurrentAnimation.started = false;

				// if the selected item is "Select", then keep as null.
				// Otherwise, set data display to relevant csv content

				if (nucleusDropdown.getSelectedItem().toString().equals("Select")) {
					targetName.setText("Target: " + "null");
					targetPNum.setText("Proton #: " + "null");
					targetNNum.setText("Neutron #: " + "null");
					mass.setText("Mass (u): " + "null");
					products.setText("Products: null, null");
					outEnergy.setText("Energy Out (MeV): " + "null");

					// set the properties to 0 so it won't be drawn at all
					CurrentAnimation.targetPNum = 0;
					CurrentAnimation.targetNNum = 0;

				} else {
					targetName.setText("Target: " + csv.getOptionFullNames(csv.getFileContent("fission.csv"),
							nucleusDropdown.getSelectedItem().toString()));
					targetPNum.setText("Proton #: " + csv.getOptionProtons(csv.getFileContent("fission.csv"),
							nucleusDropdown.getSelectedItem().toString()));
					targetNNum.setText("Neutron #: " + csv.getOptionNeutrons(csv.getFileContent("fission.csv"),
							nucleusDropdown.getSelectedItem().toString()));
					mass.setText("Mass (u): " + csv.getOptionMass(csv.getFileContent("fission.csv"),
							nucleusDropdown.getSelectedItem().toString()));
					products.setText("Products: "
							+ csv.getFissionOptionProduct1(csv.getFileContent("fission.csv"),
									nucleusDropdown.getSelectedItem().toString())
							+ "," + csv.getFissionOptionProduct2(csv.getFileContent("fission.csv"),
									nucleusDropdown.getSelectedItem().toString()));
					outEnergy.setText("Energy Out (MeV): " + csv.getOptionEnergy(csv.getFileContent("fission.csv"),
							nucleusDropdown.getSelectedItem().toString()));

					// set the selection values
					CurrentAnimation.targetMass = Math
							.round(Float.parseFloat(csv.getOptionMass(csv.getFileContent("fission.csv"),
									nucleusDropdown.getSelectedItem().toString())));
					CurrentAnimation.targetPNum = Math
							.round(Float.parseFloat(csv.getOptionProtons(csv.getFileContent("fission.csv"),
									nucleusDropdown.getSelectedItem().toString())));
					CurrentAnimation.targetNNum = Math
							.round(Float.parseFloat(csv.getOptionNeutrons(csv.getFileContent("fission.csv"),
									nucleusDropdown.getSelectedItem().toString())));

					CurrentAnimation.product1Protons = Math.round(Float.parseFloat(csv.getFissonOptionProduct1Protons(
							csv.getFileContent("fission.csv"), nucleusDropdown.getSelectedItem().toString())));
					CurrentAnimation.product2Protons = Math.round(Float.parseFloat(csv.getFissonOptionProduct2Protons(
							csv.getFileContent("fission.csv"), nucleusDropdown.getSelectedItem().toString())));
					CurrentAnimation.product1Neutrons = Math.round(Float.parseFloat(csv.getFissonOptionProduct1Neutrons(
							csv.getFileContent("fission.csv"), nucleusDropdown.getSelectedItem().toString())));
					CurrentAnimation.product2Neutrons = Math.round(Float.parseFloat(csv.getFissonOptionProduct2Neutrons(
							csv.getFileContent("fission.csv"), nucleusDropdown.getSelectedItem().toString())));
					CurrentAnimation.energyOut = Math
							.round(Float.parseFloat(csv.getOptionEnergy(csv.getFileContent("fission.csv"),
									nucleusDropdown.getSelectedItem().toString())));
				}

				CurrentAnimation.selected = true;
				// redraw
				draw.repaint();
			}
		});

		// action listener for reset button press
		resetButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CurrentAnimation.started = false;
				CurrentAnimation.hasChanged = false;
				CurrentAnimation.hasDecayed = false;
				neutronVIn.setEnabled(true);
				nucleusDropdown.setEnabled(true);
			}
		});

		// action listener for start press
		startButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check if neutron velocity is chosen
				if (neutronVIn.getText().isEmpty() || validate.validateVelocity(neutronVIn.getText()) == -300.0
						|| validate.validateVelocity(neutronVIn.getText()) == -300.0) {
					JOptionPane.showMessageDialog(null, "You haven't selected a neutron velocity yet!", "Wait!",
							JOptionPane.INFORMATION_MESSAGE);
					CurrentAnimation.started = false;
				}
				// check if isotope is chosen
				else if (nucleusDropdown.getSelectedItem().equals("Select")) {
					JOptionPane.showMessageDialog(null, "You haven't selected an isotope yet!", "Wait!",
							JOptionPane.INFORMATION_MESSAGE);
					CurrentAnimation.started = false;
				}
				// check if level of detail is chosen
				else if ((aLevel.isEnabled() && gcse.isEnabled())) {
					JOptionPane.showMessageDialog(null, "You haven't selected a level of detail yet!", "Wait!",
							JOptionPane.INFORMATION_MESSAGE);
					CurrentAnimation.started = false;

					// begin!
				} else {
					// try to convert the text input to a double and then start up the simulation
					try {
						// set neutron velocity
						CurrentAnimation.neutronVel = Float.parseFloat(neutronVIn.getText());

						CurrentAnimation.started = true;
						neutronVIn.setEnabled(false);
						nucleusDropdown.setEnabled(false);

						draw.repaint();

						// if that's not possible, give the user an error message
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "You haven't selected a valid neutron velocity yet!",
								"Wait!", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		/*
		 * return the completed screen back to the drawContent method to be added to the
		 * master JPanel using CardLayout
		 */

		return screen;
	}

	// method that draws the components onto the help screen
	public static Component drawHelpContent(Container screen, final CardLayout cl, final Container master) {
		HelpContent helpContent = new HelpContent();

		// body panel for main text
		JPanel body = new JPanel();
		body.setBackground(topColor);

		JTextArea text = new JTextArea(helpContent.getFileContent(), getScreenSizeY() / 24, getScreenSizeX() / 20);
		text.setBackground(new Color(200, 214, 229));
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		text.setEditable(false);

		// scrollable
		JScrollPane scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// Add Textarea in to middle panel
		body.add(scroll);

		// appearance stuff
		Font titleFont = new Font("Trebuchet MS", Font.BOLD, 28);
		screen.setLayout(new BorderLayout());

		// panels that contain the title and button separately
		JPanel buttonHolder = new JPanel();
		buttonHolder.setBackground(topColor);
		JPanel titleHolder = new JPanel();
		titleHolder.setBackground(topColor);
		titleHolder.setLayout(new BoxLayout(titleHolder, BoxLayout.Y_AXIS));

		// create return button
		JButton goToMenu = new JButton("Main Menu");
		goToMenu.setPreferredSize(new Dimension(100, 100));
		buttonHolder.add(goToMenu);
		screen.add(buttonHolder, BorderLayout.WEST);

		// create title
		JLabel title = new JLabel("Help Screen");
		title.setFont(titleFont);
		title.setBorder(new EmptyBorder(37, 0, 37, 50));
		titleHolder.add(title);
		titleHolder.add(body);
		screen.add(titleHolder);

		// action listener to send back to menu screen on button press
		goToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(master, "0");
			}
		});

		/*
		 * return the completed screen back to the drawContent method to be added to the
		 * master JPanel using CardLayout
		 */

		return screen;
	}

	// method that draws the components onto the menu screen
	public static Component drawMenuContent(Container screen, final CardLayout cl, final Container master) {

		// creating fonts for buttons and title
		Font titleFont = new Font("Trebuchet MS", Font.BOLD, 28);
		Font buttFont = new Font("Trebuchet MS", Font.PLAIN, 20);

		// JPanel that contains the 3 primary buttons and title and image
		JPanel menuMainColumn = new JPanel();
		menuMainColumn.setPreferredSize(new Dimension((int) (getScreenSizeX() / 1.6), (int) (getScreenSizeY() / 1.6)));
		menuMainColumn.setBackground(new Color(87, 101, 116));

		// creating layout and constraints
		GridBagConstraints layout = new GridBagConstraints();
		GridBagConstraints titLayout = new GridBagConstraints();

		layout.fill = GridBagConstraints.CENTER;
		layout.insets = new Insets(0, 10, 10, 0);

		layout.gridx = 0;
		layout.gridy = 0;

		// main title
		JLabel title = new JLabel("Nuclear Physics Simulator");
		title.setFont(titleFont);

		// button constructions
		JButton fisButt = new JButton("FISSION");
		fisButt.setPreferredSize(new Dimension(500, 100));
		fisButt.setFont(buttFont);

		JButton fusButt = new JButton("FUSION");
		fusButt.setPreferredSize(new Dimension(500, 100));
		fusButt.setFont(buttFont);

		JButton decButt = new JButton("DECAYS");
		decButt.setPreferredSize(new Dimension(500, 100));
		decButt.setFont(buttFont);

		JButton helpButt = new JButton("?");
		helpButt.setPreferredSize(new Dimension(50, 50));
		helpButt.setFont(buttFont);

		// putting the GB layout onto the screen
		screen.setLayout(new GridBagLayout());
		menuMainColumn.setLayout(new GridBagLayout());

		// adding everything on appropriately
		menuMainColumn.add(title, titLayout);
		layout.gridy++;

		// adding image file
		BufferedImage menuImg;
		try {
			menuImg = ImageIO.read(new File("D:\\OneDrive\\Documents\\PROJECTS\\PROGRAMMING\\Java\\A-Level Coursework\\image.jpg"));
			JLabel imgLabel = new JLabel(new ImageIcon(menuImg));
			menuMainColumn.add(imgLabel, layout);
		} catch (IOException e) {
			e.printStackTrace();
		}

		layout.gridy++;
		menuMainColumn.add(fisButt, layout);
		layout.gridy++;
		menuMainColumn.add(fusButt, layout);
		layout.gridy++;
		menuMainColumn.add(decButt, layout);
		screen.add(menuMainColumn);
		layout.gridx++;
		screen.add(helpButt, layout);

		// action listeners for button presses
		helpButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// go to help screen
				cl.show(master, "4");

			}
		});

		// action listener to send back to menu screen on button press
		fisButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(master, "1");

			}
		});

		// action listener to send back to menu screen on button press
		fusButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(master, "2");
			}
		});

		// action listener to send back to menu screen on button press
		decButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(master, "3");
			}
		});
		/*
		 * return the completed screen back to the drawContent method to be added to the
		 * master JPanel using CardLayout
		 */

		return screen;
	}

	// method that handles the drawing of the different screens using a master
	// container
	public static void drawContent(Container frame) {
		// creating the CardLayout to manage all of the screens
		final CardLayout cl = new CardLayout();

		// creating the individual screens
		JPanel menuScreen = new JPanel();
		menuScreen.setBackground(topColor);
		JPanel fisScreen = new JPanel();
		JPanel fusScreen = new JPanel();
		JPanel decScreen = new JPanel();
		JPanel helpScreen = new JPanel();

		// the master JPanel that holds all the screens
		final JPanel master = new JPanel();
		master.setLayout(cl);

		// adding all of the screens to the master
		master.add(drawMenuContent(menuScreen, cl, master), "0");
		master.add(drawFissionContent(fisScreen, cl, master), "1");
		master.add(drawFusionContent(fusScreen, cl, master), "2");
		master.add(drawDecayContent(decScreen, cl, master), "3");
		master.add(drawHelpContent(helpScreen, cl, master), "4");

		// by default, show the menu screen
		cl.show(master, "0");
		// add the master JPanel to the JFrame
		frame.add(master);
	}

	// main method creates JFrame
	public static void main(String[] args) {
		// using 'Nimbus' look and feel
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}

		// create the JFrame
		JFrame win = new JFrame("Nuclear Process Simulator");
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setSize(getScreenSizeX(), getScreenSizeY());
		win.setMinimumSize(new Dimension(getScreenSizeX() / 4 * 3, getScreenSizeY() - 50));
		win.pack();

		// add the stuff from drawContent to the frame
		drawContent(win.getContentPane());
		// display it!
		win.setVisible(true);
	}
}