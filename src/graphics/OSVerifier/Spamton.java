package graphics.OSVerifier;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Spamton extends JFrame {
	public Spamton(Point pos) {

		JLabel text = new JLabel();
		text.setText("Want to be a BIG SHOT?");
		JButton yesButton = new JButton();
		yesButton.setText("Yes");

		JButton noButton = new JButton();
		noButton.setText("No");

		Spamton spam = this;
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spam.dispose();
			}
		});

		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Too bad!");
			}
		});

		this.setTitle("EXQUISITE OFFER");

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
}
