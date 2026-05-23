package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import entities.Bus;

import java.awt.Color;
import java.awt.BasicStroke;

import playground.City;
import playground.Crossing;
import playground.Lane;
import graphics.MainPanel;

/**
 * Egy kereszteződés grafikus nézetét megvalósító osztály.
 * <p>
 * A kereszteződés állapotától és az aktuális játékosválasztástól függően
 * különböző színekkel jelöli a kijelölt pontot, a buszmegállókat, az útvonalban
 * szereplő kereszteződéseket és a hókotróbázist.
 */
public class CrossingView {
	public Point2D pos;

	private Crossing modelCrossing;
	private boolean isSnowplowerBase;
	private MainPanel mainPanel;

	//ez kell?
	/**
	 * Létrehozza a kereszteződés grafikus nézetét főpanel-hivatkozás nélkül.
	 *
	 * @param modelCrossing   a megjelenített kereszteződés modellobjektuma
	 * @param pos             a kereszteződés kirajzolási pozíciója
	 * @param isSnowplowerBase jelzi, hogy a kereszteződés hókotróbázis-e
	 */
	public CrossingView(Crossing modelCrossing, Point2D pos, boolean isSnowplowerBase) {
		this(modelCrossing, pos, isSnowplowerBase, null);
	}

	/**
	 * Létrehozza a kereszteződés grafikus nézetét a hozzá tartozó modellel,
	 * pozícióval, bázisjelöléssel és főpanel-hivatkozással.
	 *
	 * @param modelCrossing   a megjelenített kereszteződés modellobjektuma
	 * @param pos             a kereszteződés kirajzolási pozíciója
	 * @param isSnowplowerBase jelzi, hogy a kereszteződés hókotróbázis-e
	 * @param mainPanel       a főpanel, amelyből az aktuális kijelölések elérhetők
	 */
	public CrossingView(Crossing modelCrossing, Point2D pos, boolean isSnowplowerBase, MainPanel mainPanel) {
		this.modelCrossing = modelCrossing;
		this.pos = pos;
		this.isSnowplowerBase = isSnowplowerBase;
		this.mainPanel = mainPanel;
	}

	/**
	 * Visszaadja a nézethez tartozó kereszteződés modellobjektumot.
	 *
	 * @return a megjelenített kereszteződés
	 */
	public Crossing getCrossing() {
		return modelCrossing;
	}

	/**
	 * Megvizsgálja, hogy ez a kereszteződés az aktívan kiválasztott busz első végállomása-e.
	 *
	 * @return {@code true}, ha ez a kiválasztott busz első végállomása, egyébként
	 *         {@code false}
	 */
	private boolean isSelectedBusStationA() {
		if (mainPanel == null) {
			return false;
		}

		Bus selectedBus = mainPanel.getSelectedBus();
		return selectedBus != null && selectedBus.getStationA() == modelCrossing;
	}

	/**
	 * Megvizsgálja, hogy ez a kereszteződés az aktívan kiválasztott busz második
	 * végállomása-e.
	 *
	 * @return {@code true}, ha ez a kiválasztott busz második végállomása,
	 *         egyébként {@code false}
	 */
	private boolean isSelectedBusStationB() {
		if (mainPanel == null) {
			return false;
		}

		Bus selectedBus = mainPanel.getSelectedBus();
		return selectedBus != null && selectedBus.getStationB() == modelCrossing;
	}

	/**
	 * Kirajzolja a kereszteződést az aktuális állapotának megfelelő színnel,
	 * például kijelölt pontként, buszvégállomásként, útvonalrészként vagy
	 * hókotróbázisként.
	 *
	 * @param g a rajzoláshoz használt grafikus kontextus
	 */
	public void paint(Graphics2D g) {
		if (!updatePos())
			return;

		int x = (int) pos.getX();
		int y = (int) pos.getY();

		int atmero = MainPanel.CROSSING_SIZE - (int) MainPanel.CROSSING_STROKE;
		float vastagsag = MainPanel.CROSSING_STROKE;

		Graphics2D g2 = (Graphics2D) g.create();

		boolean isSelectedCrossing = false;

		if (mainPanel != null)
			isSelectedCrossing = mainPanel.getSelectedCrossing() == modelCrossing && mainPanel.getIsExtendingPath();

		boolean isInPathCrossing = false;
		if (mainPanel != null && mainPanel.getSelectedVehicle() != null) {
			for (Lane l : mainPanel.getSelectedVehicle().getPath().getLanes()) {
				Crossing endCrossing = l.getRoad().getToCrossing();
				if (modelCrossing == endCrossing) {
					isInPathCrossing = true;
				}
			}
		}
		boolean isSnowplowerBase = modelCrossing == City.getSnowplowBase();

		// TODO ha rondák a színek, változtatni!
		// Szín állítása az alapján, hogy
		if (isSelectedCrossing) {
			// Éppen ez van-e kiválasztva
			g2.setColor(Color.YELLOW);
		} else if (isSelectedBusStationA() ) {
			// Kiválasztott busz első megállója-e.
			g2.setColor(new Color(102, 255, 255));
		} else if (isSelectedBusStationB() ) {
			// Kiválasztott busz második megállója-e.
			g2.setColor(new Color(178, 102, 255));
		} else if (isInPathCrossing) {
			// Benne van-e az útvonalban
			g2.setColor(Color.GREEN);
		} else if (isSnowplowerBase) {
			// Hókotróbázis-e
			g2.setColor(new Color(0, 153, 153));
		} else {
			g2.setColor(new Color(30, 144, 255));
		}

		g2.fillOval(x, y, atmero, atmero);
		g2.setColor(new Color(0, 0, 139));
		g2.setStroke(new BasicStroke(vastagsag));
		g2.drawOval(x, y, atmero, atmero);

		g2.dispose();
	}

	/**
	 * Ellenőrzi, hogy a kereszteződésnek van-e érvényes kirajzolási pozíciója.
	 *
	 * @return {@code true}, ha a pozíció elérhető, egyébként {@code false}
	 */
	private boolean updatePos() {
		return pos != null;
	}

	/**
	 * Kezeli a kereszteződésre való kattintást útvonalbővítés módban, és
	 * sikeres találat esetén kijelöli ezt a kereszteződést a főpanelen.
	 *
	 * @param x a kattintás x koordinátája
	 * @param y a kattintás y koordinátája
	 * @return {@code true}, ha a kattintás elég közel van a kereszteződéshez, amúgy {@code false}
	 */
	public boolean isClicked(int x, int y) {
		if (!updatePos())
			return false;
		if (mainPanel == null || !mainPanel.getIsExtendingPath())
			return false;

		Point2D.Double center = MainPanel.calculateCenter(this);
		double radius = MainPanel.CROSSING_SIZE / 2.0;
		if (center.distance(x, y) > radius)
			return false;

		mainPanel.setSelectedCrossing(modelCrossing);
		mainPanel.repaint();
		return true;
	}
}
