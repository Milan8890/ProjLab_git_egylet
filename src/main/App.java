package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import graphics.NewMain;
import graphics.Panels.BusPanel;
import graphics.Panels.SnowplowerPanel;
import user.setupPlayerData;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.AbstractBorder;

/**
 * Az alkalmazás belépési pontját és a játékosok kezdőadatainak bekérésére
 * szolgáló grafikus menüt tartalmazza.
 */
public class App {
	/**
	 * Az alkalmazás belépési pontja.
	 *
	 * @param args parancssori argumentumok
	 * @throws Exception ha az indítás közben nem kezelt hiba történik
	 */
	public static void main(String[] args) throws Exception {
		// Handler beállítása
		Proto proto = new Proto(World.players);
		Logger.getGlobal().setUseParentHandlers(false);
		OwnHandler ownHandler = new OwnHandler(proto.objectMap);
		ownHandler.isLogging = false;
		Logger.getGlobal().addHandler(ownHandler);

		// Innen lehet tesztelni
		SnowplowerPanel.main(args);

		// Heti setupMenu teszthez
		// if (args.length > 0 && args[0].equals("setup")) {
		// List<setupPlayerData> players = setupPlayer();

		// for (setupPlayerData player : players) {
		// System.out.println(player.getName() + " " + player.getColor() + " " +
		// player.getVehicle());
		// }
		// return;
		// }

		// Új main hívás, persze nem így lesz, csak könnyebben tudok prototipizálni
		// Meg valszeg majd itt csak létre lesz hozva a Graphics, vagy tudja a rosseb.
		// NewMain.main(args);

		// // Parancsok beolvasása
		// proto.readCommandsFromCommandLine();
	}

	/**
	 * Megjeleníti a játékos hozzáadó menüt, amelyben a felhasználó játékosokat
	 * vehet fel névvel, színnel és járművel.
	 *
	 * @return a felvett játékosok adatait tartalmazó lista, a képernyőn látható
	 *         sorok sorrendjében
	 */
	public static List<setupPlayerData> setupPlayer() {
		final String setupMenuTitle = "Játékos hozzáadó menü";
		final String[] playerColors = { "Zöld", "Sárga", "Kék", "Piros", "Lila", "Narancs" };
		final String[] playerVehicles = { "Busz", "Hókotró jégtörőfejjel", "Hókotró hányófejjel" };
		final Color titleBackground = new Color(25, 101, 135);
		final Color menuBorder = new Color(0, 145, 215);
		final Color controlBorder = new Color(166, 157, 143);
		final Color controlBackground = new Color(250, 249, 246);
		final Font titleFont = new Font("Serif", Font.BOLD, 30);
		final Font menuFont = new Font("Serif", Font.PLAIN, 28);
		final Font buttonFont = new Font("Serif", Font.PLAIN, 27);
		final int menuWidth = 1180;
		final int menuHeight = 730;
		final int controlHeight = 52;
		final int defaultControlWidth = 235;
		final int vehicleControlWidth = 335;

		final List<setupPlayerData> selectedPlayers = new ArrayList<>();

		final JDialog dialog = new JDialog();
		dialog.setTitle(setupMenuTitle);
		dialog.setModal(true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JPanel menuPanel = new JPanel(new BorderLayout(0, 0));
		menuPanel.setBackground(Color.WHITE);
		menuPanel.setBorder(BorderFactory.createLineBorder(menuBorder, 4));

		JLabel title = new JLabel(setupMenuTitle, SwingConstants.CENTER);
		title.setOpaque(true);
		title.setBackground(titleBackground);
		title.setForeground(Color.WHITE);
		title.setFont(titleFont);
		title.setPreferredSize(new Dimension(menuWidth, 70));
		title.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, menuBorder));
		menuPanel.add(title, BorderLayout.NORTH);

		JPanel rowsPanel = new JPanel(new GridBagLayout());
		rowsPanel.setBackground(Color.WHITE);
		rowsPanel.setBorder(BorderFactory.createEmptyBorder(34, 30, 0, 30));

		// ADATOK
		List<JTextField> names = new ArrayList<>();
		List<JComboBox<String>> colors = new ArrayList<>();
		List<JComboBox<String>> vehicles = new ArrayList<>();

		JScrollPane scrollPane = new JScrollPane(rowsPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setPreferredSize(new Dimension(menuWidth, menuHeight));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		menuPanel.add(scrollPane, BorderLayout.CENTER);

		JButton addPlayerButton = createMenuButton("Játékos hozzáadása", buttonFont, controlBackground,
				controlBorder);
		JButton removePlayerButton = createMenuButton("Játékos törlése", buttonFont, controlBackground,
				controlBorder);
		JButton startGameButton = createMenuButton("Játék kezdése", buttonFont, controlBackground, controlBorder);

		addPlayerRow(names, colors, vehicles, playerColors, playerVehicles, menuFont, controlBackground,
				controlBorder);
		rebuildRowsPanel(rowsPanel, names, colors, vehicles, addPlayerButton, removePlayerButton, startGameButton,
				menuFont, controlHeight, defaultControlWidth, vehicleControlWidth);

		// GOMBOK ACTION LISTENERJE
		addPlayerButton.addActionListener(e -> {
			addPlayerRow(names, colors, vehicles, playerColors, playerVehicles, menuFont, controlBackground,
					controlBorder);
			rebuildRowsPanel(rowsPanel, names, colors, vehicles, addPlayerButton, removePlayerButton,
					startGameButton, menuFont, controlHeight, defaultControlWidth, vehicleControlWidth);
			SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar()
					.setValue(scrollPane.getVerticalScrollBar().getMaximum()));
		});

		removePlayerButton.addActionListener(e -> {
			removeLastPlayerRow(names, colors, vehicles);
			rebuildRowsPanel(rowsPanel, names, colors, vehicles, addPlayerButton, removePlayerButton,
					startGameButton, menuFont, controlHeight, defaultControlWidth, vehicleControlWidth);
		});

		startGameButton.addActionListener(e -> {
			selectedPlayers.clear();
			for (int i = 0; i < names.size(); i++) {
				setupPlayerData playerData = new setupPlayerData();
				playerData.setName(names.get(i).getText());
				playerData.setColor((String) colors.get(i).getSelectedItem());
				playerData.setVehicle((String) vehicles.get(i).getSelectedItem());
				selectedPlayers.add(playerData);
			}
			dialog.dispose();
		});

		dialog.setContentPane(menuPanel);
		dialog.pack();
		dialog.setMinimumSize(dialog.getSize());
		positionDialogAtTop(dialog);
		SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
		dialog.setVisible(true);

		return selectedPlayers;
	}

	/**
	 * Hozzáadja a három oszlop fejlécét a játékos adatokat tartalmazó panelhez.
	 *
	 * @param rowsPanel a sorokat és fejléceket tartalmazó panel
	 * @param menuFont  a fejléc szövegéhez használt betűtípus
	 */
	private static void addHeader(JPanel rowsPanel, Font menuFont) {
		addHeaderLabel(rowsPanel, "Játékos neve:", 0, menuFont);
		addHeaderLabel(rowsPanel, "Játékos színe:", 1, menuFont);
		addHeaderLabel(rowsPanel, "Játékos járműve:", 2, menuFont);
	}

	/**
	 * Létrehoz és elhelyez egy fejlécfeliratot a megadott oszlopban.
	 *
	 * @param rowsPanel a fejlécet fogadó panel
	 * @param text      a fejlécben megjelenő szöveg
	 * @param column    az oszlop indexe, ahová a fejléc kerül
	 * @param menuFont  a fejléc szövegéhez használt betűtípus
	 */
	private static void addHeaderLabel(JPanel rowsPanel, String text, int column, Font menuFont) {
		JLabel label = new JLabel(text, SwingConstants.LEFT);
		label.setFont(menuFont);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = column;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 6, 18, 14);
		rowsPanel.add(label, gbc);
	}

	/**
	 * Hozzáad egy új játékossort a háttérben tárolt komponenslistákhoz.
	 *
	 * @param names             a játékosneveket tartalmazó szövegmezők listája
	 * @param colors            a játékosszín választó mezők listája
	 * @param vehicles          a jármű választó mezők listája
	 * @param playerColors      a választható játékosszínek
	 * @param playerVehicles    a választható járművek
	 * @param menuFont          a sor komponenseinek betűtípusa
	 * @param controlBackground a választómezők háttérszíne
	 * @param controlBorder     a komponensek keretszíne
	 */
	private static void addPlayerRow(List<JTextField> names, List<JComboBox<String>> colors,
			List<JComboBox<String>> vehicles, String[] playerColors, String[] playerVehicles, Font menuFont,
			Color controlBackground, Color controlBorder) {
		names.add(createNameField(menuFont, controlBorder));
		colors.add(createComboBox(playerColors, menuFont, controlBackground, controlBorder));
		vehicles.add(createComboBox(playerVehicles, menuFont, controlBackground, controlBorder));
	}

	/**
	 * Eltávolítja az utolsó játékossor komponenseit a háttérben tárolt listákból.
	 *
	 * @param names    a játékosneveket tartalmazó szövegmezők listája
	 * @param colors   a játékosszín választó mezők listája
	 * @param vehicles a jármű választó mezők listája
	 */
	private static void removeLastPlayerRow(List<JTextField> names, List<JComboBox<String>> colors,
			List<JComboBox<String>> vehicles) {
		if (names.isEmpty()) {
			return;
		}
		int lastIndex = names.size() - 1;
		names.remove(lastIndex);
		colors.remove(lastIndex);
		vehicles.remove(lastIndex);
	}

	/**
	 * Újraépíti a játékossorokat megjelenítő panel teljes tartalmát.
	 *
	 * @param rowsPanel           az újraépítendő panel
	 * @param names               a játékosneveket tartalmazó szövegmezők listája
	 * @param colors              a játékosszín választó mezők listája
	 * @param vehicles            a jármű választó mezők listája
	 * @param addPlayerButton     a játékos hozzáadására szolgáló gomb
	 * @param removePlayerButton  a játékos törlésére szolgáló gomb
	 * @param startGameButton     a játék indítására szolgáló gomb
	 * @param menuFont            a fejléc betűtípusa
	 * @param controlHeight       a sor komponenseinek magassága
	 * @param defaultControlWidth az alap mezőszélesség
	 * @param vehicleControlWidth a járműválasztó mező szélessége
	 */
	private static void rebuildRowsPanel(JPanel rowsPanel, List<JTextField> names, List<JComboBox<String>> colors,
			List<JComboBox<String>> vehicles, JButton addPlayerButton, JButton removePlayerButton,
			JButton startGameButton, Font menuFont, int controlHeight, int defaultControlWidth,
			int vehicleControlWidth) {
		rowsPanel.removeAll();
		addHeader(rowsPanel, menuFont);

		for (int i = 0; i < names.size(); i++) {
			int row = i + 1;
			addRowComponent(rowsPanel, names.get(i), row, 0, controlHeight, defaultControlWidth,
					vehicleControlWidth);
			addRowComponent(rowsPanel, colors.get(i), row, 1, controlHeight, defaultControlWidth,
					vehicleControlWidth);
			addRowComponent(rowsPanel, vehicles.get(i), row, 2, controlHeight, defaultControlWidth,
					vehicleControlWidth);
		}

		int buttonRow = names.size() + 1;
		addButton(rowsPanel, addPlayerButton, buttonRow, 0);
		addButton(rowsPanel, removePlayerButton, buttonRow, 1);
		addButton(rowsPanel, startGameButton, buttonRow, 2);
		addVerticalFiller(rowsPanel, buttonRow + 1);

		rowsPanel.revalidate();
		rowsPanel.repaint();
	}

	/**
	 * Elhelyez egy játékossorhoz tartozó komponenst a megadott cellában.
	 *
	 * @param rowsPanel           a komponenst fogadó panel
	 * @param component           az elhelyezendő komponens
	 * @param row                 a sor indexe
	 * @param column              az oszlop indexe
	 * @param controlHeight       a komponens magassága
	 * @param defaultControlWidth az alap mezőszélesség
	 * @param vehicleControlWidth a járműválasztó mező szélessége
	 */
	private static void addRowComponent(JPanel rowsPanel, Component component, int row, int column, int controlHeight,
			int defaultControlWidth, int vehicleControlWidth) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = column;
		gbc.gridy = row;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 6, 20, 26);

		int width = column == 2 ? vehicleControlWidth : defaultControlWidth;
		component.setPreferredSize(new Dimension(width, controlHeight));
		rowsPanel.add(component, gbc);
	}

	/**
	 * Elhelyez egy gombot a játékos sorok alatti gombsorban.
	 *
	 * @param rowsPanel a gombot fogadó panel
	 * @param button    az elhelyezendő gomb
	 * @param row       a sor indexe
	 * @param column    az oszlop indexe
	 */
	private static void addButton(JPanel rowsPanel, JButton button, int row, int column) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = column;
		gbc.gridy = row;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(2, 6, 0, 40);

		button.setPreferredSize(new Dimension(260, 58));
		rowsPanel.add(button, gbc);
	}

	/**
	 * Hozzáad egy kitöltő panelt, amely a tartalmat a görgethető terület tetején
	 * tartja.
	 *
	 * @param rowsPanel a kitöltő panelt fogadó panel
	 * @param row       a kitöltő panel sorindexe
	 */
	private static void addVerticalFiller(JPanel rowsPanel, int row) {
		JPanel filler = new JPanel();
		filler.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.gridwidth = 3;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		rowsPanel.add(filler, gbc);
	}

	/**
	 * Létrehoz egy játékosnév megadására szolgáló szövegmezőt.
	 *
	 * @param menuFont      a szövegmező betűtípusa
	 * @param controlBorder a szövegmező keretszíne
	 * @return az elkészített szövegmező
	 */
	private static JTextField createNameField(Font menuFont, Color controlBorder) {
		JTextField nameField = new JTextField();
		nameField.setFont(menuFont);
		nameField.setBackground(Color.WHITE);
		nameField.setBorder(new RoundedBorder(controlBorder, 8));
		return nameField;
	}

	/**
	 * Létrehoz egy választómezőt a megadott választható értékekkel.
	 *
	 * @param values            a választómezőben megjelenő értékek
	 * @param menuFont          a választómező betűtípusa
	 * @param controlBackground a választómező háttérszíne
	 * @param controlBorder     a választómező keretszíne
	 * @return az elkészített választómező
	 */
	private static JComboBox<String> createComboBox(String[] values, Font menuFont, Color controlBackground,
			Color controlBorder) {
		JComboBox<String> comboBox = new JComboBox<>(values);
		comboBox.setFont(menuFont);
		comboBox.setBackground(controlBackground);
		comboBox.setBorder(new RoundedBorder(controlBorder, 8));
		comboBox.setFocusable(false);
		comboBox.setPrototypeDisplayValue("Hókotró hányófejjel");
		comboBox.setRenderer(new PaddedComboBoxRenderer());
		return comboBox;
	}

	/**
	 * Létrehoz egy menügombot egységes megjelenéssel.
	 *
	 * @param text              a gomb felirata
	 * @param buttonFont        a gomb betűtípusa
	 * @param controlBackground a gomb háttérszíne
	 * @param controlBorder     a gomb keretszíne
	 * @return az elkészített gomb
	 */
	private static JButton createMenuButton(String text, Font buttonFont, Color controlBackground,
			Color controlBorder) {
		JButton button = new JButton(text);
		button.setFont(buttonFont);
		button.setBackground(controlBackground);
		button.setBorder(new RoundedBorder(controlBorder, 8));
		button.setFocusPainted(false);
		return button;
	}

	/**
	 * Az ablakot a képernyő felső részére, vízszintesen középre helyezi.
	 *
	 * @param dialog az elhelyezendő párbeszédablak
	 */
	private static void positionDialogAtTop(JDialog dialog) {
		Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int x = screenBounds.x + Math.max(0, (screenBounds.width - dialog.getWidth()) / 2);
		int y = screenBounds.y + 20;
		dialog.setLocation(x, y);
	}

	/**
	 * Egyszerű, lekerekített sarkú keret Swing komponensekhez.
	 */
	private static class RoundedBorder extends AbstractBorder {
		private final Color color;
		private final int arc;

		/**
		 * Létrehoz egy lekerekített keretet.
		 *
		 * @param color a keret színe
		 * @param arc   a sarkok lekerekítésének mértéke
		 */
		RoundedBorder(Color color, int arc) {
			this.color = color;
			this.arc = arc;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Insets getBorderInsets(Component component) {
			return new Insets(4, 10, 4, 10);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Insets getBorderInsets(Component component, Insets insets) {
			insets.top = 4;
			insets.left = 10;
			insets.bottom = 4;
			insets.right = 10;
			return insets;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
			Graphics2D graphics2D = (Graphics2D) graphics.create();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics2D.setColor(color);
			graphics2D.drawRoundRect(x, y, width - 1, height - 1, arc, arc);
			graphics2D.dispose();
		}
	}

	/**
	 * Belső margóval rendelkező listaelem-megjelenítő a választómezők elemeihez.
	 */
	private static class PaddedComboBoxRenderer extends DefaultListCellRenderer {
		/**
		 * Létrehoz egy belső margót használó választómező-renderert.
		 */
		PaddedComboBoxRenderer() {
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
			return label;
		}
	}

}
