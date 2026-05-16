package graphics.Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import entities.Snowplower;
import equipment.Head;
import equipment.HeadListing;
import equipment.heads.Breaker;
import equipment.heads.Dragon;
import equipment.heads.Ejector;
import equipment.heads.GravelSpreader;
import equipment.heads.SaltSpreader;
import equipment.heads.Sweeper;
import graphics.MainPanel;
import user.Cleaner;

/**
 * A hókotró vezérlőpanelje, amely a fejvásárlást, alapanyag vásárlást és
 * útvonal vezérlést jeleníti meg.
 */
public class SnowplowerPanel extends JPanel {
	private MainPanel mainPanel;

	private JTextField activeHeadText;
	private JButton changeHeadButton;

	private JLabel sweeperPriceLabel;
	private JLabel ejectorPriceLabel;
	private JLabel saltSpreaderPriceLabel;
	private JLabel gravelSpreaderPriceLabel;
	private JLabel breakerPriceLabel;
	private JLabel dragonPriceLabel;

	private JButton buySweeperListingButton;
	private JButton buyEjectorListingButton;
	private JButton buyBreakerListingButton;
	private JButton buyGravelSpreaderListingButton;
	private JButton buySaltSpreaderListingButton;
	private JButton buyDragonListingButton;

	private JTextField saltAmountText;
	private JButton buySaltButton;
	private JTextField bioAmountText;
	private JButton buybioButton;
	private JTextField gravelAmountText;
	private JButton buyGravelButton;

	private JButton extPathButton;
	private JButton clearPathButton;

	/**
	 * Létrehoz egy hókotrópanelt a megadott főpanelhez kapcsolva.
	 *
	 * @param mainPanel az a főpanel, amelyből a kiválasztott hókotró lekérdezhető
	 */
	public SnowplowerPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;

		final Color separatorColor = new Color(48, 78, 157);
		final Color priceCanBuyColor = new Color(20, 150, 55);
		final Color priceCannotBuyColor = new Color(205, 50, 50);
		final Font normalFont = new Font("Serif", Font.PLAIN, 14);
		final Font buttonFont = new Font("Serif", Font.PLAIN, 13);

		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(separatorColor, 2));
		setPreferredSize(new Dimension(300, 520));

		addSection(createHeadShopSection(separatorColor, normalFont, buttonFont, priceCanBuyColor, priceCannotBuyColor),
				0, 0.53);
		addSection(createMaterialShopSection(separatorColor, normalFont, buttonFont), 1, 0.27);
		addSection(createRouteControlSection(normalFont), 2, 0.20);

		update();
	}

	/**
	 * Frissíti a panelen megjelenő adatokat.
	 */
	public void update() {
		updateActiveHeadText();
		updateHeadShopSection();
		updateMaterialShopSection();
	}

	/**
	 * Hozzáad egy panelrészt a megadott sorba.
	 *
	 * @param section a hozzáadandó panelrész
	 * @param row     a cél sor indexe
	 * @param weightY a panelrész függőleges súlya
	 */
	private void addSection(JPanel section, int row, double weightY) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.weightx = 1.0;
		gbc.weighty = weightY;
		gbc.fill = GridBagConstraints.BOTH;
		add(section, gbc);
	}

	/**
	 * Létrehozza a fejvásárló panelrészt.
	 *
	 * @param separatorColor      a szakaszelválasztó vonal színe
	 * @param normalFont          a szövegek betűtípusa
	 * @param buttonFont          a gombok betűtípusa
	 * @param priceCanBuyColor    a megvásárolható fej árának színe
	 * @param priceCannotBuyColor a nem megvásárolható fej árának színe
	 * @return az elkészített fejvásárló panelrész
	 */
	private JPanel createHeadShopSection(Color separatorColor, Font normalFont, Font buttonFont, Color priceCanBuyColor,
			Color priceCannotBuyColor) {
		JPanel section = createSectionPanel(separatorColor, true, 22, 27, 22, 27);
		section.setLayout(new GridBagLayout());

		JLabel activeHeadLabel = createLabel("Aktív fej:", normalFont, SwingConstants.LEFT);
		activeHeadText = createInfoTextField("Söprő fej", normalFont);
		changeHeadButton = createButton("Fej váltás", buttonFont);
		changeHeadButton.addActionListener(e -> changeActiveHead());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 2, 18);
		section.add(activeHeadLabel, gbc);

		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 24, 18);
		section.add(activeHeadText, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 18, 24, 0);
		section.add(changeHeadButton, gbc);

		sweeperPriceLabel = createPriceLabel("1000 $", false, priceCanBuyColor, priceCannotBuyColor, normalFont);
		ejectorPriceLabel = createPriceLabel("2000 $", false, priceCanBuyColor, priceCannotBuyColor, normalFont);
		breakerPriceLabel = createPriceLabel("3000 $", false, priceCanBuyColor, priceCannotBuyColor, normalFont);
		gravelSpreaderPriceLabel = createPriceLabel("4000 $", false, priceCanBuyColor, priceCannotBuyColor,
				normalFont);
		saltSpreaderPriceLabel = createPriceLabel("5000 $", false, priceCanBuyColor, priceCannotBuyColor,
				normalFont);
		dragonPriceLabel = createPriceLabel("6000 $", false, priceCanBuyColor, priceCannotBuyColor, normalFont);

		buySweeperListingButton = createButton("Söprő fej", buttonFont);
		buyEjectorListingButton = createButton("Hányó fej", buttonFont);
		buySaltSpreaderListingButton = createButton("Sószóró fej", buttonFont);
		buyGravelSpreaderListingButton = createButton("Kőszóró fej", buttonFont);
		buyBreakerListingButton = createButton("Jégtörő fej", buttonFont);
		buyDragonListingButton = createButton("Sárkány fej", buttonFont);

		buySweeperListingButton.addActionListener(e -> buySweeperHead());
		buyEjectorListingButton.addActionListener(e -> buyEjectorHead());
		buyBreakerListingButton.addActionListener(e -> buyBreakerHead());
		buyGravelSpreaderListingButton.addActionListener(e -> buyGravelSpreaderHead());
		buySaltSpreaderListingButton.addActionListener(e -> buySaltSpreaderHead());
		buyDragonListingButton.addActionListener(e -> buyDragonHead());

		addHeadShopItem(section, sweeperPriceLabel, buySweeperListingButton, 0, 2);
		addHeadShopItem(section, ejectorPriceLabel, buyEjectorListingButton, 1, 2);
		addHeadShopItem(section, breakerPriceLabel, buyBreakerListingButton, 0, 3);
		addHeadShopItem(section, gravelSpreaderPriceLabel, buyGravelSpreaderListingButton, 1, 3);
		addHeadShopItem(section, saltSpreaderPriceLabel, buySaltSpreaderListingButton, 0, 4);
		addHeadShopItem(section, dragonPriceLabel, buyDragonListingButton, 1, 4);

		return section;
	}

	/**
	 * Létrehozza az alapanyag vásárló panelrészt.
	 *
	 * @param separatorColor a szakaszelválasztó vonal színe
	 * @param normalFont     a szövegek betűtípusa
	 * @param buttonFont     a gombok betűtípusa
	 * @return az elkészített alapanyag vásárló panelrész
	 */
	private JPanel createMaterialShopSection(Color separatorColor, Font normalFont, Font buttonFont) {
		JPanel section = createSectionPanel(separatorColor, true, 22, 31, 18, 31);
		section.setLayout(new GridBagLayout());

		saltAmountText = createPlainTextField("Só: TEMP kg", normalFont);
		bioAmountText = createPlainTextField("Biokerozin: TEMP l", normalFont);
		gravelAmountText = createPlainTextField("Zúzottkő: TEMP kg", normalFont);

		buySaltButton = createButton("Vásárlás", buttonFont);
		buybioButton = createButton("Vásárlás", buttonFont);
		buyGravelButton = createButton("Vásárlás", buttonFont);

		buySaltButton.addActionListener(e -> buySalt());
		buybioButton.addActionListener(e -> buyBio());
		buyGravelButton.addActionListener(e -> buyGravel());

		addMaterialRow(section, saltAmountText, buySaltButton, 0);
		addMaterialRow(section, bioAmountText, buybioButton, 1);
		addMaterialRow(section, gravelAmountText, buyGravelButton, 2);

		return section;
	}

	/**
	 * Létrehozza az útvonal vezérlő panelrészt.
	 *
	 * @param normalFont a gombok betűtípusa
	 * @return az elkészített útvonal vezérlő panelrész
	 */
	private JPanel createRouteControlSection(Font normalFont) {
		JPanel section = createSectionPanel(null, false, 14, 21, 15, 21);
		section.setLayout(new GridBagLayout());

		extPathButton = createButton("Útvonal tervezés", normalFont);
		clearPathButton = createButton("Útvonal törlése", normalFont);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 14, 0);
		section.add(extPathButton, gbc);

		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		section.add(clearPathButton, gbc);

		clearPathButton.addActionListener(e -> clearSelectedSnowplowerPath());
		extPathButton.addActionListener(e -> pressExtPathButton());

		return section;
	}

	/**
	 * Létrehoz egy általános, opcionálisan alsó elválasztóvonallal rendelkező
	 * panelrészt.
	 *
	 * @param separatorColor     az elválasztó vonal színe
	 * @param hasBottomSeparator igaz, ha kell alsó elválasztó vonal
	 * @param top                felső belső margó
	 * @param left               bal oldali belső margó
	 * @param bottom             alsó belső margó
	 * @param right              jobb oldali belső margó
	 * @return az elkészített panelrész
	 */
	private JPanel createSectionPanel(Color separatorColor, boolean hasBottomSeparator, int top, int left, int bottom,
			int right) {
		JPanel section = new JPanel();
		section.setBackground(Color.WHITE);

		Border padding = BorderFactory.createEmptyBorder(top, left, bottom, right);
		if (hasBottomSeparator) {
			Border separator = BorderFactory.createMatteBorder(0, 0, 2, 0, separatorColor);
			section.setBorder(BorderFactory.createCompoundBorder(separator, padding));
		} else {
			section.setBorder(padding);
		}

		return section;
	}

	/**
	 * Létrehoz egy egységesen formázott feliratot.
	 *
	 * @param text                a felirat szövege
	 * @param font                a felirat betűtípusa
	 * @param horizontalAlignment a felirat vízszintes igazítása
	 * @return az elkészített felirat
	 */
	private JLabel createLabel(String text, Font font, int horizontalAlignment) {
		JLabel label = new JLabel(text, horizontalAlignment);
		label.setFont(font);
		return label;
	}

	/**
	 * Létrehoz egy nem szerkeszthető, keretezett információs szövegmezőt.
	 *
	 * @param text a mezőben megjelenő szöveg
	 * @param font a mező betűtípusa
	 * @return az elkészített szövegmező
	 */
	private JTextField createInfoTextField(String text, Font font) {
		JTextField textField = new JTextField(text);
		textField.setEditable(false);
		textField.setFocusable(false);
		textField.setFont(font);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setBackground(Color.WHITE);
		textField.setBorder(BorderFactory.createLineBorder(new Color(48, 78, 157), 2, true));
		return textField;
	}

	/**
	 * Létrehoz egy nem szerkeszthető, keret nélküli szövegmezőt.
	 *
	 * @param text a mezőben megjelenő szöveg
	 * @param font a mező betűtípusa
	 * @return az elkészített szövegmező
	 */
	private JTextField createPlainTextField(String text, Font font) {
		JTextField textField = new JTextField(text);
		textField.setEditable(false);
		textField.setFocusable(false);
		textField.setFont(font);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setBackground(Color.WHITE);
		textField.setBorder(BorderFactory.createEmptyBorder());
		return textField;
	}

	/**
	 * Létrehoz egy fejárat megjelenítő feliratot.
	 *
	 * @param text                az ár szövege
	 * @param canBuy              igaz, ha a fej jelenleg megvásárolható
	 * @param priceCanBuyColor    a megvásárolható ár színe
	 * @param priceCannotBuyColor a nem megvásárolható ár színe
	 * @param font                a felirat betűtípusa
	 * @return az elkészített ár felirat
	 */
	private JLabel createPriceLabel(String text, boolean canBuy, Color priceCanBuyColor, Color priceCannotBuyColor,
			Font font) {
		JLabel label = createLabel(text, font, SwingConstants.LEFT);
		label.setForeground(canBuy ? priceCanBuyColor : priceCannotBuyColor);
		return label;
	}

	/**
	 * Létrehoz egy egységesen formázott gombot.
	 *
	 * @param text a gomb felirata
	 * @param font a gomb betűtípusa
	 * @return az elkészített gomb
	 */
	private JButton createButton(String text, Font font) {
		JButton button = new JButton(text);
		button.setFont(font);
		button.setFocusPainted(false);
		return button;
	}

	/**
	 * Hozzáad egy fejvásárló elemet az ár feliratával és a vásárló gombbal.
	 *
	 * @param section    a fejvásárló panelrész
	 * @param priceLabel az árat megjelenítő felirat
	 * @param buyButton  a vásárlást indító gomb
	 * @param column     a cél oszlop indexe
	 * @param row        a cél sor indexe
	 */
	private void addHeadShopItem(JPanel section, JLabel priceLabel, JButton buyButton, int column, int row) {
		JPanel itemPanel = new JPanel(new BorderLayout(0, 3));
		itemPanel.setBackground(Color.WHITE);
		itemPanel.add(priceLabel, BorderLayout.NORTH);
		itemPanel.add(buyButton, BorderLayout.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = column;
		gbc.gridy = row;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, column == 0 ? 0 : 18, 12, column == 0 ? 18 : 0);
		section.add(itemPanel, gbc);
	}

	/**
	 * Hozzáad egy alapanyag vásárló sort a panelrészhez.
	 *
	 * @param section      az alapanyag vásárló panelrész
	 * @param materialText az alapanyag nevét és mennyiségét megjelenítő mező
	 * @param buyButton    a vásárlást indító gomb
	 * @param row          a cél sor indexe
	 */
	private void addMaterialRow(JPanel section, JTextField materialText, JButton buyButton, int row) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 12, 18);
		materialText.setPreferredSize(new Dimension(130, materialText.getPreferredSize().height));
		section.add(materialText, gbc);

		gbc.gridx = 1;
		gbc.weightx = 0.0;
		gbc.insets = new Insets(0, 12, 12, 0);
		section.add(buyButton, gbc);
	}

	/**
	 * Frissíti az aktív fej nevét megjelenítő mezőt.
	 */
	private void updateActiveHeadText() {
		Snowplower selectedSnowplower = getSelectedSnowplower();

		if (selectedSnowplower == null) {
			activeHeadText.setText("Nincs aktív fej");
			return;
		}

		Head activeHead = selectedSnowplower.getHeadInventory().getActiveHead();
		activeHeadText.setText(activeHead.getDescription());
	}

	/**
	 * Fejbolt frissítése
	 */
	private void updateHeadShopSection() {
		// TODO Itt lehetne mókolni, mert jelenleg összemegy a panel. De lehet csak az
		// egészet kéne állítani (Ez középen van, a másik kettőt alulra / felülre
		// rendezni, és akkor nem baj, ha összemegy, vagy ilyesmi)
		// setVisible(false) HELYETT ezt a 3 sort használd:
		// gomb.setEnabled(false); // Ne lehessen rákattintani
		// gomb.setContentAreaFilled(false); // Eltünteti a gomb hátterét
		// gomb.setBorderPainted(false); // Eltünteti a gomb keretét
		// gomb.setText(""); // Letörli a szöveget (ha van)
		Snowplower selectedSnowplower = getSelectedSnowplower();

		buySweeperListingButton.setVisible(false);
		sweeperPriceLabel.setVisible(false);

		buyEjectorListingButton.setVisible(false);
		ejectorPriceLabel.setVisible(false);

		buyBreakerListingButton.setVisible(false);
		breakerPriceLabel.setVisible(false);

		buyGravelSpreaderListingButton.setVisible(false);
		gravelSpreaderPriceLabel.setVisible(false);

		buySaltSpreaderListingButton.setVisible(false);
		saltSpreaderPriceLabel.setVisible(false);

		buyDragonListingButton.setVisible(false);
		dragonPriceLabel.setVisible(false);

		if (selectedSnowplower == null) {
			return;
		}

		for (HeadListing hl : selectedSnowplower.getHeadInventory().getShop()) {
			// Nem switch, mert const expr-nek kell lennie
			if (hl.getHead().getDescription().equals(new Sweeper(null).getDescription())) {
				buySweeperListingButton.setVisible(true);
				sweeperPriceLabel.setVisible(true);
			} else if (hl.getHead().getDescription().equals(new Ejector(null).getDescription())) {
				buyEjectorListingButton.setVisible(true);
				ejectorPriceLabel.setVisible(true);
			} else if (hl.getHead().getDescription().equals(new Breaker(null).getDescription())) {
				buyBreakerListingButton.setVisible(true);
				breakerPriceLabel.setVisible(true);
			} else if (hl.getHead().getDescription().equals(new GravelSpreader(null).getDescription())) {
				buyGravelSpreaderListingButton.setVisible(true);
				gravelSpreaderPriceLabel.setVisible(true);
			} else if (hl.getHead().getDescription().equals(new SaltSpreader(null).getDescription())) {
				buySaltSpreaderListingButton.setVisible(true);
				saltSpreaderPriceLabel.setVisible(true);
			} else if (hl.getHead().getDescription().equals(new Dragon(null).getDescription())) {
				buyDragonListingButton.setVisible(true);
				dragonPriceLabel.setVisible(true);
			}
		}
	}

	/**
	 * Alapanyagbolt frissítése
	 */
	private void updateMaterialShopSection() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		saltAmountText.setText("Só: " + (selectedSnowplower == null ? 0 : selectedSnowplower.getSalt()) + " kg");
		bioAmountText.setText("Biokerozin: " + (selectedSnowplower == null ? 0 : selectedSnowplower.getBio()) + " l");
		gravelAmountText
				.setText("Zúzottkő: " + (selectedSnowplower == null ? 0 : selectedSnowplower.getGravel()) + " kg");
	}

	// TODO TESZT csak
	static Cleaner c = new Cleaner("Andros");
	static Snowplower sp = Snowplower.createWithBreaker(c);

	/**
	 * Lekéri a főpanelen jelenleg kiválasztott hókotrót.
	 *
	 * @return a kiválasztott hókotró, vagy {@code null}, ha nincs elérhető főpanel
	 */
	private Snowplower getSelectedSnowplower() {
		c.addMoney(10000);
		if (sp != null)
			return sp;

		if (mainPanel == null) {
			return null;
		}
		return mainPanel.getSelectedSnowplower();
	}

	/**
	 * A kiválasztott hókotró aktív fejét a következő megvett fejre váltja.
	 */
	private void changeActiveHead() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		if (selectedSnowplower == null) {
			return;
		}
		selectedSnowplower.getHeadInventory().cycleActiveHead();
	}

	/**
	 * Söprő fejet vásárol a kiválasztott hókotróhoz.
	 */
	private void buySweeperHead() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		if (selectedSnowplower == null) {
			return;
		}

		HeadListing foundHeadListing = null;
		for (HeadListing hl : selectedSnowplower.getHeadInventory().getShop()) {
			if (hl.getHead().getDescription().equals(new Sweeper(null).getDescription())) {
				foundHeadListing = hl;
				break;
			}
		}
		if (foundHeadListing != null) {
			selectedSnowplower.getHeadInventory().buyListing(foundHeadListing);
		}
	}

	/**
	 * Hányó fejet vásárol a kiválasztott hókotróhoz.
	 */
	private void buyEjectorHead() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		if (selectedSnowplower == null) {
			return;
		}
		HeadListing foundHeadListing = null;
		for (HeadListing hl : selectedSnowplower.getHeadInventory().getShop()) {
			if (hl.getHead().getDescription().equals(new Ejector(null).getDescription())) {
				foundHeadListing = hl;
				break;
			}
		}
		if (foundHeadListing != null) {
			selectedSnowplower.getHeadInventory().buyListing(foundHeadListing);
		}
	}

	/**
	 * Jégtörő fejet vásárol a kiválasztott hókotróhoz.
	 */
	private void buyBreakerHead() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		if (selectedSnowplower == null) {
			return;
		}
		HeadListing foundHeadListing = null;
		for (HeadListing hl : selectedSnowplower.getHeadInventory().getShop()) {
			if (hl.getHead().getDescription().equals(new Ejector(null).getDescription())) {
				foundHeadListing = hl;
				break;
			}
		}
		if (foundHeadListing != null) {
			selectedSnowplower.getHeadInventory().buyListing(foundHeadListing);
		}
	}

	/**
	 * Kőszóró fejet vásárol a kiválasztott hókotróhoz.
	 */
	private void buyGravelSpreaderHead() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		if (selectedSnowplower == null) {
			return;
		}
		HeadListing foundHeadListing = null;
		for (HeadListing hl : selectedSnowplower.getHeadInventory().getShop()) {
			if (hl.getHead().getDescription().equals(new GravelSpreader(null).getDescription())) {
				foundHeadListing = hl;
				break;
			}
		}
		if (foundHeadListing != null) {
			selectedSnowplower.getHeadInventory().buyListing(foundHeadListing);
		}
	}

	/**
	 * Sószóró fejet vásárol a kiválasztott hókotróhoz.
	 */
	private void buySaltSpreaderHead() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		if (selectedSnowplower == null) {
			return;
		}
		HeadListing foundHeadListing = null;
		for (HeadListing hl : selectedSnowplower.getHeadInventory().getShop()) {
			if (hl.getHead().getDescription().equals(new SaltSpreader(null).getDescription())) {
				foundHeadListing = hl;
				break;
			}
		}
		if (foundHeadListing != null) {
			selectedSnowplower.getHeadInventory().buyListing(foundHeadListing);
		}
	}

	/**
	 * Sárkány fejet vásárol a kiválasztott hókotróhoz.
	 */
	private void buyDragonHead() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		if (selectedSnowplower == null) {
			return;
		}
		HeadListing foundHeadListing = null;
		for (HeadListing hl : selectedSnowplower.getHeadInventory().getShop()) {
			if (hl.getHead().getDescription().equals(new Dragon(null).getDescription())) {
				foundHeadListing = hl;
				break;
			}
		}
		if (foundHeadListing != null) {
			selectedSnowplower.getHeadInventory().buyListing(foundHeadListing);
		}
	}

	/**
	 * Sót vásárol a kiválasztott hókotróhoz.
	 */
	private void buySalt() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		if (selectedSnowplower == null) {
			return;
		}
		selectedSnowplower.buySalt();
	}

	/**
	 * Biokerozint vásárol a kiválasztott hókotróhoz.
	 */
	private void buyBio() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		if (selectedSnowplower == null) {
			return;
		}
		selectedSnowplower.buyBio();
	}

	/**
	 * Zúzottkövet vásárol a kiválasztott hókotróhoz.
	 */
	private void buyGravel() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		if (selectedSnowplower == null) {
			return;
		}
		selectedSnowplower.buyGravel();
	}

	/**
	 * Törli a kiválasztott hókotró aktuálisan tervezett útvonalát.
	 */
	private void clearSelectedSnowplowerPath() {
		Snowplower selectedSnowplower = getSelectedSnowplower();
		if (selectedSnowplower == null) {
			return;
		}
		selectedSnowplower.getPath().clear();
	}

	/**
	 * Az útvonal tervezésének állítása.
	 */
	private void pressExtPathButton() {
		if (mainPanel == null) {
			return;
		}

		mainPanel.setIsExtendingPath(!mainPanel.getIsExtendingPath());
	}

	// TESZT!!!##############################################################################################################
	/**
	 * Egyszerű, önálló tesztablakot indít a panel megjelenítéséhez.
	 *
	 * @param args parancssori argumentumok
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			javax.swing.JFrame frame = new javax.swing.JFrame("SnowplowerPanel teszt");
			frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
			SnowplowerPanel spPanel = new SnowplowerPanel(null);
			frame.add(spPanel);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

			Thread gameThread = new Thread(() -> {
				while (true) {
					try {
						// Blocks this thread until Swing finishes updating
						SwingUtilities.invokeAndWait(() -> {
							spPanel.update(); // model update + repaint, all on EDT
						});

						Thread.sleep(16); // ~60fps, runs on game thread (not EDT)
					} catch (InterruptedException | InvocationTargetException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}
			});
			gameThread.setDaemon(true);
			gameThread.start();
		});

	}
	// TESZT
	// VÉGE!!!##############################################################################################################

}
