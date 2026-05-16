package graphics.Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import entities.Bus;
import graphics.MainPanel;

/**
 * A busz vezérlőpanelje, amely az útvonal vezérlésére szolgáló gombokat
 * jeleníti meg.
 */
public class BusPanel extends JPanel {
	private MainPanel mainPanel;

	private JButton extPathButton;
	private JButton clearPathButton;

	/**
	 * Létrehoz egy buszpanelt a megadott főpanelhez kapcsolva.
	 *
	 * @param mainPanel az a főpanel, amelyből a kiválasztott hókotró lekérdezhető
	 */
	public BusPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		final Color separatorColor = new Color(48, 78, 157);
		final Font normalFont = new Font("Serif", Font.PLAIN, 14);

		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(separatorColor, 2));
		setPreferredSize(new Dimension(300, 520));

		addFillerSection();
		addRouteControlSection(createRouteControlSection(normalFont));
	}

	/**
	 * Hozzáadja a panel felső, üres kitöltő részét.
	 */
	private void addFillerSection() {
		JPanel filler = new JPanel();
		filler.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.80;
		gbc.fill = GridBagConstraints.BOTH;
		add(filler, gbc);
	}

	/**
	 * Hozzáadja az útvonal vezérlő részt a panel aljához.
	 *
	 * @param routeControlSection az útvonal vezérlő gombokat tartalmazó panel
	 */
	private void addRouteControlSection(JPanel routeControlSection) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.20;
		gbc.fill = GridBagConstraints.BOTH;
		add(routeControlSection, gbc);
	}

	/**
	 * Létrehozza az útvonal tervezésére és törlésére szolgáló gombokat tartalmazó
	 * panelrészt.
	 *
	 * @param normalFont a gombok betűtípusa
	 * @return az elkészített útvonal vezérlő panelrész
	 */
	private JPanel createRouteControlSection(Font normalFont) {
		JPanel section = new JPanel(new GridBagLayout());
		section.setBackground(Color.WHITE);
		section.setBorder(BorderFactory.createEmptyBorder(14, 21, 15, 21));

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

		clearPathButton.addActionListener(e -> clearSelectedBusPath());
		extPathButton.addActionListener(e -> pressExtPathButton());

		return section;
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
	 * Lekéri a főpanelen jelenleg kiválasztott hókotrót.
	 *
	 * @return a kiválasztott hókotró, vagy {@code null}, ha nincs elérhető főpanel
	 */
	private Bus getSelectedBus() {
		if (mainPanel == null) {
			return null;
		}

		return mainPanel.getSelectedBus();
	}

	/**
	 * Törli a kiválasztott hókotró aktuálisan tervezett útvonalát.
	 */
	private void clearSelectedBusPath() {
		Bus selectedBus = getSelectedBus();
		if (selectedBus == null) {
			return;
		}

		selectedBus.getPath().clear();
	}

	/**
	 * Az útvonal tervezésének állítása.
	 */
	private void pressExtPathButton() {
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
			javax.swing.JFrame frame = new javax.swing.JFrame("BusPanel teszt");
			frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
			frame.add(new BusPanel(null));
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
	// TESZT
	// VÉGE!!!##############################################################################################################

}
