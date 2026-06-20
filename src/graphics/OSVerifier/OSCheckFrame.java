package graphics.OSVerifier;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OSCheckFrame extends JFrame {
	private boolean isOsCorrect = false;

	public OSCheckFrame(String CheckedOS, Point pos) {
		isOsCorrect = realOS.toLowerCase().contains(CheckedOS.toLowerCase());

		JLabel text = new JLabel();
		text.setText(CheckedOS + "-t használsz?");
		JButton yesButton = new JButton();
		yesButton.setText("Igen");

		JButton noButton = new JButton();
		noButton.setText("Nem");

		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isOsCorrect) {
					OSCheckFrame.NOT_OK();
				} else {
					OSCheckFrame.OK();
				}
			}
		});

		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isOsCorrect) {
					OSCheckFrame.OK();
				} else {
					OSCheckFrame.NOT_OK();
				}
			}
		});

		this.setTitle(CheckedOS + " system check");

		// Set default close operation
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set the layout for the entire frame (using BorderLayout is good for stacking)
		this.setLayout(new BorderLayout(10, 10)); // Add some padding

		// 2. Create the central container panel
		JPanel contentPanel = new JPanel();
		// Use FlowLayout for the main content panel to arrange components
		// horizontally/vertically
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the content

		// Add the text label to the panel
		contentPanel.add(text);

		// Add some vertical space between the text and the buttons
		contentPanel.add(Box.createVerticalStrut(20));

		// 4. Create the panel for the side-by-side buttons
		JPanel buttonPanel = new JPanel();
		// Use FlowLayout for the buttons, which naturally places them side-by-side
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Add buttons to the button panel
		buttonPanel.add(yesButton);
		buttonPanel.add(noButton);

		// 5. Add the button panel to the main content panel
		contentPanel.add(buttonPanel);

		// 6. Add the content panel to the main frame
		this.add(contentPanel, BorderLayout.CENTER);

		// 7. Finalize and display the window
		this.setSize(400, 200); // Set a reasonable initial size
		this.setLocation(pos);
		this.setVisible(true);
	}

	// Nem akartam singleton patternt, inkább járjanak
	// És így van is értelme, különben kétszer kéne ugyan azt a kódot leírni, de
	// mégis statikus objektumom van
	private static OSCheckFrame boyton = null;
	private static OSCheckFrame girlton = null;
	private static boolean done = false;
	private static List<Spamton> spamtonlings = new ArrayList<>();
	private static String realOS;

	/**
	 * Létrehozza az ablakot ami ellenőrzi az operációs rendszert.
	 * Válasz után automatikusan visszatér a függvény, és kilövi az ablakokat.
	 */
	public static void CheckOS() {
		realOS = System.getProperty("os.name");
		girlton = new OSCheckFrame("Windows", new Point(500, 500));
		boyton = new OSCheckFrame("Linux", new Point(1000, 500));
		Random r = new Random();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double chance = 0.1;
		while (!done) {
			try {
				if (r.nextDouble() < chance) {
					int x = r.nextInt(100, (int) screenSize.getWidth() - 500);
					int y = r.nextInt(100, (int) screenSize.getHeight() - 500);
					Spamton spamton = new Spamton(new Point(x, y));
					spamtonlings.add(spamton);
					chance += 0.05;
				}
				Thread.sleep(1000);
			} catch (Exception e) {

			}
		}
		girlton.dispose();
		boyton.dispose();
		for (var spamtonling : spamtonlings) {
			spamtonling.dispose();
		}
	}

	private static void OK() {
		done = true;
	}

	private static void NOT_OK() {
		System.exit(1);
	}
}
