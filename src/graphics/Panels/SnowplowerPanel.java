package graphics.Panels;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import graphics.NewMain;

public class SnowplowerPanel extends JPanel{
	private JTextField activeHeadText;
	private JButton changeHeadButton;

	private JButton buySweeperListingButton;
	private JButton buyEjectorListingButton;
	private JButton buyBreakerListingButton;
	private JButton buyGravelSpreaderListingButton;
	private JButton buySaltSpreaderListingButton;

	private JTextField saltAmountText;
	private JButton buySaltButton;
	private JTextField bioAmountText;
	private JButton buybioButton;
	private JTextField gravelAmountText;
	private JButton buyGravelButton;

	private JButton extPathButton;
	private JButton clearPathButton;

	public SnowplowerPanel() {
		NewMain.notdone("Snowplower Panel contructor");
	}

	public void update() {
		NewMain.notdone("Snowplower Panel update");
	}
}
