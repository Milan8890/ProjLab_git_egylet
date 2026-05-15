package graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import entities.Bus;
import entities.Snowplower;
import graphics.ModelViews.BusView;
import graphics.ModelViews.CarView;
import graphics.ModelViews.CrossingView;
import graphics.ModelViews.LaneView;
import graphics.ModelViews.RoadView;
import graphics.ModelViews.SnowplowerView;
import graphics.Panels.BusPanel;
import graphics.Panels.MapPanel;
import graphics.Panels.SnowplowerPanel;
import playground.Crossing;
import user.BusDriver;
import user.Cleaner;
import user.Player;

/**
 * A játék fő grafikus ablaka, amely a térképet, az aktív játékos adatait és a
 * járműspecifikus vezérlőpaneleket fogja össze.
 */
public class MainPanel extends JFrame {
	private List<Cleaner> cleaners;
	private List<BusDriver> busDrivers;
	private boolean isExtendingPath;

	private Cleaner selectedCleaner;
	private BusDriver selectedBusDriver;
	private Snowplower selectedSnowplower;

	private Crossing selectedCrossing;

	private List<CrossingView> crossingViews;
	private List<RoadView> roadViews;
	private List<LaneView> laneViews;
	private List<SnowplowerView> snowplowerViews;
	private List<BusView> busViews;
	private List<CarView> carViews;

	private JPanel activePlayerPanel;
	private JComboBox<Player> playerSelectorComboBox;
	private JTextField playerData;
	private boolean activePlayerComboBoxLoaded;

	private SnowplowerPanel snowplowerPanel;
	private BusPanel busPanel;

	private MapPanel mapPanel;

	/**
	 * Létrehozza a főpanelt és annak jelenlegi alpaneljeit.
	 */
	public MainPanel() {
		NewMain.notdone("MainPanel konstruktor");
		activePlayerPanel = createActivePlayerPanel();
		snowplowerPanel = new SnowplowerPanel(this);
		busPanel = new BusPanel(this);
	}

	/**
	 * Visszaadja, hogy a felhasználó éppen útvonalat bővít-e.
	 *
	 * @return {@code true}, ha útvonalbővítés folyamatban van, egyébként
	 *         {@code false}
	 */
	public boolean getIsExtendingPath() {
		NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic boolean getIsExtendingPath() {");
		return isExtendingPath;
	}

	/**
	 * Beállítja, hogy a felhasználó éppen útvonalat bővít-e.
	 *
	 * @param b az új útvonalbővítési állapot
	 */
	public void setIsExtendingPath(boolean b) {
		NewMain.notdone(
				"MainPanel getter/setter at (raw function header):\npublic void setIsExtendingPath(boolean b) {");
		isExtendingPath = b;
	}

	/**
	 * Visszaadja a kereszteződések grafikus nézeteit.
	 *
	 * @return a kereszteződés nézetek listája
	 */
	public List<CrossingView> getCrossingViews() {
		NewMain.notdone(
				"MainPanel getter/setter at (raw function header):\npublic List<CrossingView> getCrossingViews() {");
		return crossingViews;
	}

	/**
	 * Visszaadja az utak grafikus nézeteit.
	 *
	 * @return az út nézetek listája
	 */
	public List<RoadView> getRoadViews() {
		NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic List<RoadView> getRoadViews() {");
		return roadViews;
	}

	/**
	 * Visszaadja a sávok grafikus nézeteit.
	 *
	 * @return a sáv nézetek listája
	 */
	public List<LaneView> getLaneViews() {
		NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic List<LaneView> getLaneViews() {");
		return laneViews;
	}

	/**
	 * Visszaadja a hókotrók grafikus nézeteit.
	 *
	 * @return a hókotró nézetek listája
	 */
	public List<SnowplowerView> getSnowplowerViews() {
		NewMain.notdone(
				"MainPanel getter/setter at (raw function header):\npublic List<SnowplowerView> getSnowplowerViews() {");
		return snowplowerViews;
	}

	/**
	 * Visszaadja a buszok grafikus nézeteit.
	 *
	 * @return a busz nézetek listája
	 */
	public List<BusView> getBusViews() {
		NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic List<BusView> getBusViews() {");
		return busViews;
	}

	/**
	 * Visszaadja az autók grafikus nézeteit.
	 *
	 * @return az autó nézetek listája
	 */
	public List<CarView> getCarViews() {
		NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic List<CarView> getCarViews() {");
		return carViews;
	}

	/**
	 * Visszaadja a kiválasztott takarító játékost.
	 *
	 * @return a kiválasztott takarító játékos
	 */
	public Cleaner getSelectedCleaner() {
		NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic Cleaner getSelectedCleaner() {");
		return selectedCleaner;
	}

	/**
	 * Visszaadja a kiválasztott buszvezető játékost.
	 *
	 * @return a kiválasztott buszvezető játékos
	 */
	public BusDriver getSelectedBusDriver() {
		NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic BusDriver getSelectedBusDriver() {");
		return selectedBusDriver;
	}

	/**
	 * Visszaadja a kiválasztott hókotrót.
	 *
	 * @return a kiválasztott hókotró
	 */
	public Snowplower getSelectedSnowplower() {
		NewMain.notdone(
				"MainPanel getter/setter at (raw function header):\npublic Snowplower getSelectedSnowplower() {");
		return selectedSnowplower;
	}

	/**
	 * Beállítja a kiválasztott hókotrót.
	 *
	 * @param selectedSnowplower az újonnan kiválasztott hókotró
	 */
	public void setSelectedSnowplower(Snowplower selectedSnowplower) {
		NewMain.notdone(
				"MainPanel getter/setter at (raw function header):\npublic void setSelectedSnowplower(Snowplower selectedSnowplower) {");
		this.selectedSnowplower = selectedSnowplower;
	}

	/**
	 * Visszaadja a kiválasztott buszt.
	 *
	 * @return a kiválasztott busz
	 */
	public Bus getSelectedBus() {
		NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic Bus getSelectedBus() {");
		return null;
	}

	/**
	 * Visszaadja a kiválasztott kereszteződést.
	 *
	 * @return a kiválasztott kereszteződés
	 */
	public Crossing getSelectedCrossing() {
		NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic Crossing getSelectedCrossing() {");
		return selectedCrossing;
	}

	/**
	 * Beállítja a kiválasztott kereszteződést.
	 *
	 * @param selectedCrossing az újonnan kiválasztott kereszteződés
	 */
	public void setSelectedCrossing(Crossing selectedCrossing) {
		NewMain.notdone(
				"MainPanel getter/setter at (raw function header):\npublic void setSelectedCrossing(Crossing selectedCrossing) {");
		this.selectedCrossing = selectedCrossing;
	}

	/**
	 * Frissíti a főpanel megjelenítését.
	 */
	public void update() {
		NewMain.notdone("MainPanel update");
		loadActivePlayerComboBox();
		handleActivePlayerSelection();
	}

	/**
	 * Létrehozza az aktív játékost és pénzét megjelenítő panelrészt.
	 *
	 * @return az elkészített aktív játékos panel
	 */
	private JPanel createActivePlayerPanel() {
		final Color separatorColor = new Color(48, 78, 157);
		final Font normalFont = new Font("Serif", Font.PLAIN, 14);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(separatorColor, 2),
				BorderFactory.createEmptyBorder(22, 31, 18, 31)));
		panel.setPreferredSize(new Dimension(300, 140));

		JLabel activePlayerLabel = createActivePlayerLabel("Aktív játékos:", normalFont);
		playerSelectorComboBox = createActivePlayerComboBox(normalFont);
		playerData = createActivePlayerInfoText("Pénzed: 0000 $", normalFont, separatorColor);
		connectActivePlayerSelector();
		loadActivePlayerComboBox();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(activePlayerLabel, gbc);

		gbc.gridy = 1;
		gbc.insets = new Insets(2, 0, 12, 0);
		panel.add(playerSelectorComboBox, gbc);

		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 0, 0);
		panel.add(playerData, gbc);

		return panel;
	}

	/**
	 * Létrehoz egy feliratot az aktív játékos panelhez.
	 *
	 * @param text a felirat szövege
	 * @param font a felirat betűtípusa
	 * @return az elkészített felirat
	 */
	private JLabel createActivePlayerLabel(String text, Font font) {
		JLabel label = new JLabel(text, SwingConstants.LEFT);
		label.setFont(font);
		return label;
	}

	/**
	 * Létrehozza az aktív játékos kiválasztására szolgáló legördülő mezőt.
	 *
	 * @param font a legördülő mező betűtípusa
	 * @return az elkészített legördülő mező
	 */
	private JComboBox<Player> createActivePlayerComboBox(Font font) {
		JComboBox<Player> comboBox = new JComboBox<>();
		comboBox.setFont(font);
		comboBox.setRenderer(createActivePlayerComboBoxRenderer());
		return comboBox;
	}

	/**
	 * Létrehozza az aktív játékos legördülő mezőjének megjelenítőjét.
	 *
	 * @return a játékosneveket megjelenítő listaelem-renderer
	 */
	private DefaultListCellRenderer createActivePlayerComboBoxRenderer() {
		return new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);
				label.setText(getPlayerDisplayName((Player) value));
				return label;
			}
		};
	}

	/**
	 * Létrehoz egy nem szerkeszthető információs mezőt az aktív játékos panelhez.
	 *
	 * @param text a mezőben megjelenő szöveg
	 * @param font a mező betűtípusa
	 * @param borderColor a mező keretszíne
	 * @return az elkészített információs mező
	 */
	private JTextField createActivePlayerInfoText(String text, Font font, Color borderColor) {
		JTextField textField = new JTextField(text);
		textField.setEditable(false);
		textField.setFocusable(false);
		textField.setFont(font);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setBackground(Color.WHITE);
		textField.setBorder(BorderFactory.createLineBorder(borderColor, 2, true));
		return textField;
	}

	/**
	 * Ráköti az aktív játékos legördülő mező változását a kiválasztott játékos
	 * adatainak frissítésére.
	 */
	private void connectActivePlayerSelector() {
		playerSelectorComboBox.addActionListener(e -> handleActivePlayerSelection());
	}

	/**
	 * Betölti az aktív játékos legördülő mezőt a takarító és buszvezető játékosok
	 * listájából.
	 */
	private void loadActivePlayerComboBox() {
		if (playerSelectorComboBox == null || activePlayerComboBoxLoaded || !canLoadActivePlayerComboBox()) {
			return;
		}

		Player selectedPlayer = (Player) playerSelectorComboBox.getSelectedItem();
		DefaultComboBoxModel<Player> playerModel = new DefaultComboBoxModel<>();
		addCleanersToActivePlayerModel(playerModel);
		addBusDriversToActivePlayerModel(playerModel);

		playerSelectorComboBox.setModel(playerModel);
		activePlayerComboBoxLoaded = true;
		restoreActivePlayerSelection(selectedPlayer, playerModel);
		handleActivePlayerSelection();
	}

	/**
	 * Megadja, hogy a játékoslisták rendelkezésre állnak-e a legördülő mező
	 * feltöltéséhez.
	 *
	 * @return {@code true}, ha legalább az egyik játékoslista betölthető,
	 *         egyébként {@code false}
	 */
	private boolean canLoadActivePlayerComboBox() {
		return cleaners != null || busDrivers != null;
	}

	/**
	 * Hozzáadja a takarító játékosokat az aktív játékos választó modellhez.
	 *
	 * @param playerModel a feltöltendő legördülő mező modell
	 */
	private void addCleanersToActivePlayerModel(DefaultComboBoxModel<Player> playerModel) {
		if (cleaners == null) {
			return;
		}
		for (Cleaner cleaner : cleaners) {
			playerModel.addElement(cleaner);
		}
	}

	/**
	 * Hozzáadja a buszvezető játékosokat az aktív játékos választó modellhez.
	 *
	 * @param playerModel a feltöltendő legördülő mező modell
	 */
	private void addBusDriversToActivePlayerModel(DefaultComboBoxModel<Player> playerModel) {
		if (busDrivers == null) {
			return;
		}
		for (BusDriver busDriver : busDrivers) {
			playerModel.addElement(busDriver);
		}
	}

	/**
	 * Visszaállítja a korábban kiválasztott aktív játékost, ha az továbbra is
	 * szerepel a modellben.
	 *
	 * @param selectedPlayer a korábban kiválasztott játékos
	 * @param playerModel az aktív játékosokat tartalmazó modell
	 */
	private void restoreActivePlayerSelection(Player selectedPlayer, DefaultComboBoxModel<Player> playerModel) {
		if (selectedPlayer == null) {
			return;
		}

		for (int i = 0; i < playerModel.getSize(); i++) {
			if (playerModel.getElementAt(i) == selectedPlayer) {
				playerSelectorComboBox.setSelectedItem(selectedPlayer);
				return;
			}
		}
	}

	/**
	 * Kezeli az aktív játékos választásának változását.
	 */
	private void handleActivePlayerSelection() {
		Player selectedPlayer = (Player) playerSelectorComboBox.getSelectedItem();
		setSelectedPlayerFromComboBox(selectedPlayer);
		updateActivePlayerDataText();
	}

	/**
	 * A legördülő mező kiválasztott eleme alapján beállítja a kiválasztott játékos
	 * mezőket.
	 *
	 * @param selectedPlayer a legördülő mezőben kiválasztott játékos
	 */
	private void setSelectedPlayerFromComboBox(Player selectedPlayer) {
		Cleaner cleaner = findCleanerByReference(selectedPlayer);
		if (cleaner != null) {
			selectedCleaner = cleaner;
			selectedBusDriver = null;
			return;
		}

		BusDriver busDriver = findBusDriverByReference(selectedPlayer);
		if (busDriver != null) {
			selectedCleaner = null;
			selectedBusDriver = busDriver;
			return;
		}
		selectedCleaner = null;
		selectedBusDriver = null;
	}

	/**
	 * Megkeresi a kiválasztott játékost a takarító játékosok között
	 * objektumreferencia-egyezéssel.
	 *
	 * @param selectedPlayer a legördülő mezőben kiválasztott játékos
	 * @return a kiválasztott takarító, vagy {@code null}, ha nincs egyezés
	 */
	private Cleaner findCleanerByReference(Player selectedPlayer) {
		if (cleaners == null) {
			return null;
		}
		for (Cleaner cleaner : cleaners) {
			if (cleaner == selectedPlayer) {
				return cleaner;
			}
		}
		return null;
	}

	/**
	 * Megkeresi a kiválasztott játékost a buszvezető játékosok között
	 * objektumreferencia-egyezéssel.
	 *
	 * @param selectedPlayer a legördülő mezőben kiválasztott játékos
	 * @return a kiválasztott buszvezető, vagy {@code null}, ha nincs egyezés
	 */
	private BusDriver findBusDriverByReference(Player selectedPlayer) {
		if (busDrivers == null) {
			return null;
		}
		for (BusDriver busDriver : busDrivers) {
			if (busDriver == selectedPlayer) {
				return busDriver;
			}
		}
		return null;
	}

	/**
	 * Frissíti az aktív játékos pénzét vagy pontszámát megjelenítő mezőt.
	 */
	private void updateActivePlayerDataText() {
		if (playerData == null) {
			return;
		}
		playerData.setText(getActivePlayerDataText());
	}

	/**
	 * Összeállítja az aktív játékoshoz tartozó pénz vagy pontszám szövegét.
	 *
	 * @return a megjelenítendő pénz vagy pontszám szöveg
	 */
	private String getActivePlayerDataText() {
		if (selectedCleaner != null) {
			return "Pénzed: " + selectedCleaner.getMoney() + " $";
		}
		if (selectedBusDriver != null) {
			return "Pontjaid: " + selectedBusDriver.getPoint() + " db";
		}
		return "Pénzed: 0000 $";
	}

	/**
	 * Visszaadja a játékos legördülő mezőben megjelenő nevét.
	 *
	 * @param player a megjelenítendő játékos
	 * @return a játékos neve, vagy üres szöveg, ha nincs játékos
	 */
	private String getPlayerDisplayName(Player player) {
		if (player == null) {
			return "";
		}
		return player.getName();
	}

	//TESZT!!!##############################################################################################################
	/**
	 * Egyszerű, önálló tesztablakot indít a panel megjelenítéséhez.
	 *
	 * @param args parancssori argumentumok
	 */
	public static void main(String[] args) {
	javax.swing.SwingUtilities.invokeLater(() -> {
		MainPanel mainPanel = new MainPanel();

		javax.swing.JFrame frame = new javax.swing.JFrame("ActivePlayerPanel teszt");
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.add(mainPanel.activePlayerPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	});
}
	//TESZT VÉGE!!!##############################################################################################################

}
