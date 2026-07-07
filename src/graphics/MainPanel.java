package graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import java.awt.geom.Point2D;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import entities.Bus;
import entities.Bike;
import entities.Car;
import entities.Snowplower;
import entities.Vehicle;
import graphics.ModelViews.BusView;
import graphics.ModelViews.CarView;
import graphics.ModelViews.BikeView;
import graphics.ModelViews.CrossingView;
import graphics.ModelViews.LaneView;
import graphics.ModelViews.RoadView;
import graphics.ModelViews.SnowplowerView;
import graphics.Panels.BusPanel;
import graphics.Panels.MapPanel;
import graphics.Panels.SnowplowerPanel;
import main.World;
import playground.City;
import playground.Crossing;
import playground.Lane;
import playground.Path;
import playground.Road;
import playground.Tunnel;
import user.BusDriver;
import user.Cleaner;
import user.Player;
import user.setupPlayerData;

/**
 * A játék fő grafikus ablaka, amely a térképet, az aktív játékos adatait és a
 * járműspecifikus vezérlőpaneleket fogja össze.
 */
public class MainPanel extends JPanel {
	public static final int CROSSING_SIZE = 80;
	public static final int LANE_WIDTH = 20;
	public static final float CROSSING_STROKE = 6f;
	private static final int CAR_NUM = 30;

	private List<Cleaner> cleaners = new ArrayList<>();
	private List<BusDriver> busDrivers = new ArrayList<>();
	private Map<Cleaner, Color> cleanerColors = new HashMap<>();
	private boolean isExtendingPath;

	private Cleaner selectedCleaner;
	private BusDriver selectedBusDriver;
	private Snowplower selectedSnowplower;
	private Crossing selectedCrossing;

	// Kellett máshol
	public static List<CrossingView> crossingViews = new ArrayList<>();
	public static List<RoadView> roadViews = new ArrayList<>();
	public static List<LaneView> laneViews = new ArrayList<>();
	public static List<SnowplowerView> snowplowerViews = new ArrayList<>();
	public static List<BusView> busViews = new ArrayList<>();
	public static List<CarView> carViews = new ArrayList<>();
	public static List<BikeView> bikeViews = new ArrayList<>();

	private JPanel activePlayerPanel;
	private JComboBox<Player> playerSelectorComboBox;
	private JTextField playerData;
	private boolean activePlayerComboBoxLoaded;

	private SnowplowerPanel snowplowerPanel;
	private BusPanel busPanel;
	private MapPanel mapPanel;

	/**
	 * Létrehozza a fő játékablakot, inicializálja a tesztpályát, a játékosokat,
	 * a járműnézeteket és a jobb oldali vezérlőpaneleket.
	 */
	public MainPanel(List<setupPlayerData> playerDataList) {
		setFocusable(true);

		this.addKeyListener(new KeyAdapter() {
			/**
			 * Út hosszabbítása KeyListener.
			 * Kezeli az útvonalhosszabbítás billentyűparancsait, és a lenyomott
			 * szám alapján hozzáadja a kiválasztott sávot az aktív jármű útvonalához.
			 *
			 * @param e a billentyűesemény
			 */
			@Override
			public void keyPressed(KeyEvent e) {

				if (!isExtendingPath || selectedCrossing == null)
					return;

				Vehicle selectedVehicle = getSelectedBus() == null ? getSelectedSnowplower() : getSelectedBus();
				if (selectedVehicle == null)
					return;

				Path path = selectedVehicle.getPath();
				Crossing lastCrossing = path.getLastCrossing();

				Road roadToExtendWith = null;

				for (Road road : lastCrossing.getOutRoads()) {
					if (road.getToCrossing() == selectedCrossing) {
						roadToExtendWith = road;
						break;
					}
				}

				if (roadToExtendWith == null)
					return;

				int pressed = Character.getNumericValue(e.getKeyChar());

				List<Lane> lanes = roadToExtendWith.getLanes();
				if (lanes.size() < pressed || pressed <= 0)
					return;

				selectedVehicle.extendPath(lanes.get(pressed - 1));
				mapPanel.repaint();
			}
		});

		buildTestMap();

		initCars(CAR_NUM);

		initPlayerViews(playerDataList);
		activePlayerPanel = createActivePlayerPanel();
		snowplowerPanel = new SnowplowerPanel(this);
		busPanel = new BusPanel(this);
		mapPanel = new MapPanel(this);

		setLayout(new BorderLayout());
		// --- JOBB OLDAL: Vezérlő Panel ---
		JPanel sidePanel = new JPanel(new BorderLayout());
		sidePanel.setBackground(Color.WHITE);
		sidePanel.setPreferredSize(new Dimension(320, 1000));
		// --- JOBB OLDAL, FELSŐ: Active Player Panel ---
		sidePanel.add(activePlayerPanel, BorderLayout.NORTH);
		// --- JOBB OLDAL, ALSÓ: Snowplower / Bus Panel ---
		JPanel vehicleHolder = new JPanel(new GridBagLayout());
		vehicleHolder.setBackground(Color.WHITE);
		GridBagConstraints panelGbc = new GridBagConstraints();
		panelGbc.gridx = 0;
		panelGbc.gridy = 0;
		panelGbc.weightx = 1.0;
		panelGbc.weighty = 1.0;
		panelGbc.fill = GridBagConstraints.BOTH;
		panelGbc.anchor = GridBagConstraints.NORTH;

		busPanel.setVisible(false);
		snowplowerPanel.setVisible(false);

		vehicleHolder.add(snowplowerPanel, panelGbc);
		vehicleHolder.add(busPanel, panelGbc);

		sidePanel.add(vehicleHolder, BorderLayout.CENTER);

		add(mapPanel, BorderLayout.CENTER);
		add(sidePanel, BorderLayout.EAST);

		startGameLoop();
	}

	/**
	 * Felépíti az alap tesztpályát kereszteződésekkel, utakkal, sávokkal és
	 * beállítja a hókotró bázisát.
	 */
	private void buildTestMap() {
		Crossing c1 = new Crossing();
		Crossing c2 = new Crossing();
		Crossing c3 = new Crossing();
		Crossing c4 = new Crossing();
		Crossing c5 = new Crossing();
		Crossing c6 = new Crossing();
		Crossing c7 = new Crossing();
		Crossing c8 = new Crossing();
		Crossing c9 = new Crossing();
		Crossing c10 = new Crossing();
		Crossing c11 = new Crossing();
		Crossing c12 = new Crossing();
		Crossing c13 = new Crossing();
		Crossing c14 = new Crossing();
		Crossing c15 = new Crossing();
		Crossing c16 = new Crossing();
		Crossing c17 = new Crossing();
		Crossing c18 = new Crossing();
		Crossing c19 = new Crossing();
		Crossing c20 = new Crossing();
		Crossing c21 = new Crossing();
		Crossing c22 = new Crossing();
		Crossing c23 = new Crossing();
		Point2D.Double pos1 = new Point2D.Double(55, 60);
		Point2D.Double pos2 = new Point2D.Double(245, 12);
		Point2D.Double pos3 = new Point2D.Double(515, 105);
		Point2D.Double pos4 = new Point2D.Double(750, 45);
		Point2D.Double pos5 = new Point2D.Double(1050, 55);
		Point2D.Double pos6 = new Point2D.Double(1340, 80);
		Point2D.Double pos7 = new Point2D.Double(40, 260);
		Point2D.Double pos8 = new Point2D.Double(350, 280);
		Point2D.Double pos9 = new Point2D.Double(550, 390);
		Point2D.Double pos10 = new Point2D.Double(780, 330);
		Point2D.Double pos11 = new Point2D.Double(1080, 285);
		Point2D.Double pos12 = new Point2D.Double(1335, 315);
		Point2D.Double pos13 = new Point2D.Double(90, 510);
		Point2D.Double pos14 = new Point2D.Double(360, 560);
		Point2D.Double pos15 = new Point2D.Double(510, 710);
		Point2D.Double pos16 = new Point2D.Double(840, 560);
		Point2D.Double pos17 = new Point2D.Double(1230, 560);
		Point2D.Double pos18 = new Point2D.Double(50, 780);
		Point2D.Double pos19 = new Point2D.Double(270, 890);
		Point2D.Double pos20 = new Point2D.Double(610, 910);
		Point2D.Double pos21 = new Point2D.Double(860, 890);
		Point2D.Double pos22 = new Point2D.Double(1180, 897);
		Point2D.Double pos23 = new Point2D.Double(1390, 765);
		crossingViews.add(new CrossingView(c1, new Point2D.Double(pos1.x + 3, pos1.y + 3), false, this));
		crossingViews.add(new CrossingView(c2, new Point2D.Double(pos2.x + 3, pos2.y + 3), false, this));
		crossingViews.add(new CrossingView(c3, new Point2D.Double(pos3.x + 3, pos3.y + 3), false, this));
		crossingViews.add(new CrossingView(c4, new Point2D.Double(pos4.x + 3, pos4.y + 3), false, this));
		crossingViews.add(new CrossingView(c5, new Point2D.Double(pos5.x + 3, pos5.y + 3), false, this));
		crossingViews.add(new CrossingView(c6, new Point2D.Double(pos6.x + 3, pos6.y + 3), false, this));
		crossingViews.add(new CrossingView(c7, new Point2D.Double(pos7.x + 3, pos7.y + 3), false, this));
		crossingViews.add(new CrossingView(c8, new Point2D.Double(pos8.x + 3, pos8.y + 3), false, this));
		crossingViews.add(new CrossingView(c9, new Point2D.Double(pos9.x + 3, pos9.y + 3), false, this));
		crossingViews.add(new CrossingView(c10, new Point2D.Double(pos10.x + 3, pos10.y + 3), false, this));
		crossingViews.add(new CrossingView(c11, new Point2D.Double(pos11.x + 3, pos11.y + 3), true, this));
		crossingViews.add(new CrossingView(c12, new Point2D.Double(pos12.x + 3, pos12.y + 3), false, this));
		crossingViews.add(new CrossingView(c13, new Point2D.Double(pos13.x + 3, pos13.y + 3), false, this));
		crossingViews.add(new CrossingView(c14, new Point2D.Double(pos14.x + 3, pos14.y + 3), false, this));
		crossingViews.add(new CrossingView(c15, new Point2D.Double(pos15.x + 3, pos15.y + 3), false, this));
		crossingViews.add(new CrossingView(c16, new Point2D.Double(pos16.x + 3, pos16.y + 3), false, this));
		crossingViews.add(new CrossingView(c17, new Point2D.Double(pos17.x + 3, pos17.y + 3), false, this));
		crossingViews.add(new CrossingView(c18, new Point2D.Double(pos18.x + 3, pos18.y + 3), false, this));
		crossingViews.add(new CrossingView(c19, new Point2D.Double(pos19.x + 3, pos19.y + 3), false, this));
		crossingViews.add(new CrossingView(c20, new Point2D.Double(pos20.x + 3, pos20.y + 3), false, this));
		crossingViews.add(new CrossingView(c21, new Point2D.Double(pos21.x + 3, pos21.y + 3), false, this));
		crossingViews.add(new CrossingView(c22, new Point2D.Double(pos22.x + 3, pos22.y + 3), false, this));
		crossingViews.add(new CrossingView(c23, new Point2D.Double(pos23.x + 3, pos23.y + 3), false, this));

		// 1-es kereszteződésből induló utak
		addRoadWithLanes(c1, c2, 2, false);
		addRoadWithLanes(c1, c7, 2, false);
		// 2-es kereszteződésből induló utak
		addRoadWithLanes(c2, c3, 1, false);
		addRoadWithLanes(c2, c1, 2, false);
		// 3-as kereszteződésből induló utak
		addRoadWithLanes(c3, c2, 2, false);
		addRoadWithLanes(c3, c4, 2, false);
		addRoadWithLanes(c3, c8, 1, false);
		// 4-es kereszteződésből induló utak
		addRoadWithLanes(c4, c3, 2, false);
		addRoadWithLanes(c4, c5, 1, false);
		// 5-ös kereszteződésből induló utak
		addRoadWithLanes(c5, c4, 3, false);
		addRoadWithLanes(c5, c11, 1, false);
		// 6-os kereszteződésből induló utak
		addRoadWithLanes(c6, c5, 3, false);
		addRoadWithLanes(c6, c12, 1, false);
		// 7-es kereszteződésből induló utak
		addRoadWithLanes(c7, c1, 2, false);
		addRoadWithLanes(c7, c13, 2, false);
		// 8-0s kereszteződésből induló utak
		addRoadWithLanes(c8, c3, 1, false);
		addRoadWithLanes(c8, c9, 1, false);
		addRoadWithLanes(c8, c13, 1, false);
		// 9-es kereszteződésből induló utak
		addRoadWithLanes(c9, c8, 2, false);
		addRoadWithLanes(c9, c10, 2, false);
		// 10-es kereszteződésből induló utak
		addRoadWithLanes(c10, c9, 3, false);
		addRoadWithLanes(c10, c11, 1, false);
		addRoadWithLanes(c10, c16, 2, false);
		// 11-es kereszteződésből induló utak
		addRoadWithLanes(c11, c5, 2, false);
		addRoadWithLanes(c11, c10, 2, false);
		addRoadWithLanes(c11, c12, 1, false);
		addRoadWithLanes(c11, c17, 1, false);
		// 12-es kereszteződésből induló utak
		addRoadWithLanes(c12, c6, 3, false);
		addRoadWithLanes(c12, c11, 2, false);
		addRoadWithLanes(c12, c17, 1, false);
		// 13-as kereszteződésből induló utak
		addRoadWithLanes(c13, c7, 1, false);
		addRoadWithLanes(c13, c8, 2, false);
		addRoadWithLanes(c13, c14, 2, false);
		addRoadWithLanes(c13, c18, 2, false);
		// 14-es kereszteződésből induló utak
		addRoadWithLanes(c14, c8, 1, false);
		addRoadWithLanes(c14, c15, 1, false);
		addRoadWithLanes(c14, c19, 1, false);
		// 15-ös kereszteződésből induló utak
		addRoadWithLanes(c15, c14, 2, false);
		addRoadWithLanes(c15, c9, 2, false);
		addRoadWithLanes(c15, c20, 1, false);
		// 16-os kereszteződésből induló utak
		addRoadWithLanes(c16, c10, 2, false);
		addRoadWithLanes(c16, c17, 2, false);
		addRoadWithLanes(c16, c21, 2, true);
		// 17-es kereszteződésből induló utak
		addRoadWithLanes(c17, c16, 1, false);
		addRoadWithLanes(c17, c12, 2, false);
		addRoadWithLanes(c17, c22, 1, false);
		// 18-as kereszteződésből induló utak
		addRoadWithLanes(c18, c13, 1, false);
		addRoadWithLanes(c18, c19, 2, false);
		// 19-es kereszteződésből induló utak
		addRoadWithLanes(c19, c18, 1, false);
		addRoadWithLanes(c19, c14, 1, false);
		addRoadWithLanes(c19, c20, 2, false);
		// 20-as kereszteződésből induló utak
		addRoadWithLanes(c20, c19, 1, false);
		addRoadWithLanes(c20, c15, 2, false);
		addRoadWithLanes(c20, c21, 1, false);
		// 21-es kereszteződésből induló utak
		addRoadWithLanes(c21, c20, 1, false);
		addRoadWithLanes(c21, c16, 1, true);
		addRoadWithLanes(c21, c22, 3, false);
		// 22-es kereszteződésből induló utak
		addRoadWithLanes(c22, c21, 1, false);
		addRoadWithLanes(c22, c17, 1, false);
		addRoadWithLanes(c22, c23, 2, false);
		// 23-as kereszteződésből induló utak
		addRoadWithLanes(c23, c17, 2, false);

		City.getCrossings().clear();

		City.getCrossings().addAll(List.of(
				c1, c2, c3, c4, c5, c6, c7, c8, c9, c10,
				c11, c12, c13, c14, c15, c16, c17, c18,
				c19, c20, c21, c22, c23));
		City.setSnowplowBase(c11);
	}

	/**
	 * Véletlenszerű kezdő- és célkereszteződéssel létrehozza az autókat, majd
	 * hozzáadja őket a városhoz és a kirajzolható autó nézetekhez.
	 *
	 * @param carNumber a létrehozandó autók száma
	 */
	private void initCars(int carNumber) {
		List<Crossing> crossings = City.getCrossings();
		Color[] colors = { new Color(220, 20, 60), new Color(0, 120, 215), new Color(0, 160, 120),
				new Color(255, 140, 0), new Color(170, 80, 220), new Color(255, 80, 120),
				new Color(40, 180, 220), new Color(120, 200, 40), new Color(230, 90, 40),
				new Color(255, 200, 0) };
		if (crossings.size() < 2) {
			Logger.getGlobal().severe("Nincs elég kereszteződés autók létrehozásához.");
			return;
		}

		Random random = new Random();
		for (int i = 0; i < carNumber; i++) {
			Crossing home = crossings.get(random.nextInt(crossings.size()));
			Crossing work = crossings.get(random.nextInt(crossings.size()));
			while (work == home) {
				work = crossings.get(random.nextInt(crossings.size()));
			}

			Car car = new Car(home, work);
			City.getCars().add(car);
			carViews.add(new CarView(car, this, colors[random.nextInt(colors.length)]));

			if (i % 2 == 0) {
				Bike bike = new Bike();
				City.getBikes().add(bike);
				bikeViews.add(new BikeView(bike, this, colors[random.nextInt(colors.length)]));
			}
		}
	}

	/**
	 * Létrehoz egy modellszintű utat vagy alagutat a két kereszteződés között,
	 * majd hozzáadja az út- és sávnézeteket.
	 *
	 * @param from     az út kezdő kereszteződése
	 * @param to       az út cél kereszteződése
	 * @param savSzam  az út sávjainak száma
	 * @param isTunnel jelzi, hogy az utszakasz egy alagut-e
	 */
	// JAVITAS: A metodus fuszignaturaja kibovitve az 'isTunnel' flaggel
	private void addRoadWithLanes(Crossing from, Crossing to, int savSzam, boolean isTunnel) {
		CrossingView fromView = getCrossingView(from);
		CrossingView toView = getCrossingView(to);

		if (fromView == null || toView == null)
			return;

		Point2D.Double kp1 = calculateCenter(fromView);
		Point2D.Double kp2 = calculateCenter(toView);
		Point2D.Double[] roadEnds = calculateRoadEndPoints(kp1, kp2);
		if (roadEnds == null)
			return;

		Point2D.Double utEleje = roadEnds[0];
		Point2D.Double utVege = roadEnds[1];
		double valosUtHossz = utEleje.distance(utVege);

		Road road;
		if (isTunnel) {
			road = new Tunnel(from, to, savSzam, valosUtHossz);
		} else {
			road = new Road(from, to, savSzam, valosUtHossz);
		}

		roadViews.add(new RoadView(road, utEleje, utVege));
		generateAndAddLaneViews(road, utEleje, utVege);
	}

	/**
	 * Kiszámolja egy CrossingView középpontját a kirajzolási pozíció és
	 * a kereszteződés mérete alapján.
	 *
	 * @param view a kereszteződés grafikus nézete
	 * @return a kereszteződés középpontja
	 */
	public static Point2D.Double calculateCenter(CrossingView view) {
		double sugar = CROSSING_SIZE / 2.0;
		double centerX = view.pos.getX() - 3.0 + sugar;
		double centerY = view.pos.getY() - 3.0 + sugar;
		return new Point2D.Double(centerX, centerY);
	}

	/**
	 * Meghatározza egy út kezdő- és végpontját úgy, hogy az út ne a
	 * kereszteződések középpontjából, hanem azok szélétől induljon.
	 *
	 * @param kp1 az első kereszteződés középpontja
	 * @param kp2 a második kereszteződés középpontja
	 * @return a kiszámolt útvégpontok tömbje, vagy {@code null}, ha a két pont
	 *         azonos
	 */
	public static Point2D.Double[] calculateRoadEndPoints(Point2D.Double kp1, Point2D.Double kp2) {
		double dx = kp2.x - kp1.x;
		double dy = kp2.y - kp1.y;
		double kozepTavolsag = Math.sqrt(dx * dx + dy * dy);
		if (kozepTavolsag == 0)
			return null;

		double uX = dx / kozepTavolsag;
		double uY = dy / kozepTavolsag;
		double kulsoPeremSugar = (CROSSING_SIZE / 2.0) + (CROSSING_STROKE / 2.0);
		Point2D.Double utEleje = new Point2D.Double(kp1.x + uX * kulsoPeremSugar, kp1.y + uY * kulsoPeremSugar);
		Point2D.Double utVege = new Point2D.Double(kp2.x - uX * kulsoPeremSugar, kp2.y - uY * kulsoPeremSugar);
		return new Point2D.Double[] { utEleje, utVege };
	}

	/**
	 * Létrehozza és eltárolja az adott úthoz tartozó sávnézeteket a sávok
	 * egymáshoz képesti merőleges eltolásával.
	 *
	 * @param road    az út, amelynek sávjaihoz nézeteket kell létrehozni
	 * @param utEleje az út grafikus kezdőpontja
	 * @param utVege  az út grafikus végpontja
	 */
	private void generateAndAddLaneViews(Road road, Point2D.Double utEleje, Point2D.Double utVege) {
		double dx = utVege.x - utEleje.x;
		double dy = utVege.y - utEleje.y;
		double utHossz = Math.sqrt(dx * dx + dy * dy);
		if (utHossz == 0)
			return;
		double uX = dx / utHossz;
		double uY = dy / utHossz;

		double nX = -uY;
		double nY = uX;
		List<Lane> modelLanes = road.getLanes();
		for (int i = 0; i < modelLanes.size(); i++) {
			double merolegesEltolas = 2.5 + (i * LANE_WIDTH);
			double savStartX = utEleje.x + (nX * merolegesEltolas) - (uX * 2.5);
			double savStartY = utEleje.y + (nY * merolegesEltolas) - (uY * 2.5);
			double savEndX = utVege.x + (nX * merolegesEltolas) + (uX * 2.5);
			double savEndY = utVege.y + (nY * merolegesEltolas) + (uY * 2.5);

			Point2D.Double laneStart = new Point2D.Double(savStartX, savStartY);
			Point2D.Double laneEnd = new Point2D.Double(savEndX, savEndY);

			laneViews.add(new LaneView(modelLanes.get(i), laneStart, laneEnd, this));
		}
	}

	/**
	 * A játékosválasztóból kapott adatok alapján létrehozza a buszvezetőket,
	 * takarítókat, kezdő járműveiket és azok grafikus nézeteit.
	 *
	 * @param playerDataList a játékosválasztó által összeállított játékosadatok
	 */
	public void initPlayerViews(List<setupPlayerData> playerDataList) {
		for (setupPlayerData playerData : playerDataList) {

			switch (playerData.getVehicle()) {
				case "Busz":
					BusDriver bd = new BusDriver(playerData.getName());
					busDrivers.add(bd);

					if (bd.getBus() != null) {
						busViews.add(new BusView(bd.getBus(), this, playerData.getColor()));
					}
					break;

				case "Hókotró jégtörőfejjel":
					Cleaner cleaner = new Cleaner(playerData.getName());
					cleaner.createBreakerSnowplower();
					cleaners.add(cleaner);
					cleanerColors.put(cleaner, playerData.getColor());

					Snowplower snowplower = cleaner.getSnowplowers().get(0);
					snowplowerViews.add(new SnowplowerView(snowplower, this, playerData.getColor()));
					break;

				case "Hókotró hányófejjel":
					Cleaner cleaner2 = new Cleaner(playerData.getName());
					cleaner2.createEjectorSnowplower();
					cleaners.add(cleaner2);
					cleanerColors.put(cleaner2, playerData.getColor());

					Snowplower snowplower2 = cleaner2.getSnowplowers().get(0);
					snowplowerViews.add(new SnowplowerView(snowplower2, this, playerData.getColor()));
					break;

				default:
					Logger.getGlobal().severe("Ismeretlen járműtípus: " + playerData.getVehicle());
			}
		}
	}

	/**
	 * Elindítja a játék időzítőjét, amely minden lépésben frissíti a világot,
	 * az aktív játékos adatait és újrarajzolja a térképet.
	 */
	private void startGameLoop() {
		World.setIsSnowing(true); // Bekapcsolja a havazást.
		int delay = 50;

		javax.swing.Timer timer = new javax.swing.Timer(delay, e -> {
			World.tick();
			playerData.setText(getActivePlayerDataText());
			mapPanel.repaint();

			if (snowplowerPanel.isVisible()) {
				snowplowerPanel.update();
			}
		});

		timer.start();
	}

	/**
	 * Frissíti az aktív játékos adatait megjelenítő szöveges mezőt.
	 */
	public void refreshActivePlayerData() {
		playerData.setText(getActivePlayerDataText());
	}

	/**
	 * Hozzáad egy új hókotró nézetet a megadott hókotróról.
	 *
	 * @param snowplower a hozzáadni kívánt hókotró
	 */
	public void addSnowplowerView(Snowplower snowplower) {
		if (snowplower == null) {
			return;
		}

		Cleaner owner = snowplower.getCleaner();
		Color color = cleanerColors.getOrDefault(owner, Color.WHITE);

		snowplowerViews.add(new SnowplowerView(snowplower, this, color));
		repaintMap();
	}

	/**
	 * Visszaadja, hogy a felhasználó éppen útvonalat bővít-e.
	 *
	 * @return {@code true}, ha útvonalbővítés folyamatban van, egyébként
	 *         {@code false}
	 */
	public boolean getIsExtendingPath() {
		return isExtendingPath;
	}

	/**
	 * Beállítja, hogy a felhasználó éppen útvonalat bővít-e.
	 *
	 * @param b az új útvonalbővítési állapot
	 */
	public void setIsExtendingPath(boolean b) {
		isExtendingPath = b;
	}

	/**
	 * Visszaadja a kereszteződések grafikus nézeteit.
	 *
	 * @return a kereszteződés nézetek listája
	 */
	public static CrossingView getCrossingView(Crossing c) {
		for (CrossingView cv : crossingViews) {
			if (cv.getCrossing() == c) {
				return cv;
			}
		}
		return null;
	}

	/**
	 * Visszaadja a kereszteződések grafikus nézeteit.
	 *
	 * @return a kereszteződés nézetek listája
	 */
	public List<CrossingView> getCrossingViews() {
		return crossingViews;
	}

	/**
	 * Visszaadja az utak grafikus nézeteit.
	 *
	 * @return az út nézetek listája
	 */
	public List<RoadView> getRoadViews() {
		return roadViews;
	}

	/**
	 * Visszaadja a sávok grafikus nézeteit.
	 *
	 * @return a sáv nézetek listája
	 */
	public List<LaneView> getLaneViews() {
		return laneViews;
	}

	/**
	 * Visszaadja a hókotrók grafikus nézeteit.
	 *
	 * @return a hókotró nézetek listája
	 */
	public List<SnowplowerView> getSnowplowerViews() {
		return snowplowerViews;
	}

	/**
	 * Visszaadja a buszok grafikus nézeteit.
	 *
	 * @return a busz nézetek listája
	 */
	public List<BusView> getBusViews() {
		return busViews;
	}

	/**
	 * Visszaadja az autók grafikus nézeteit.
	 *
	 * @return az autó nézetek listája
	 */
	public List<CarView> getCarViews() {
		return carViews;
	}

	public List<BikeView> getBikeViews() {
		return bikeViews;
	}

	/**
	 * Visszaadja a kiválasztott takarító játékost. Ha nem takarító van kiválasztva,
	 * null-t.
	 *
	 * @return a kiválasztott takarító játékos
	 */
	public Cleaner getSelectedCleaner() {
		return selectedCleaner;
	}

	/**
	 * Visszaadja a kiválasztott buszvezető játékost.
	 * Ha nem buszvezető van kiválasztva, null-t.
	 *
	 * @return a kiválasztott buszvezető játékos
	 */
	public BusDriver getSelectedBusDriver() {
		return selectedBusDriver;
	}

	/**
	 * Visszaadja a kiválasztott hókotrót. Ha nincs ilyen, null-t.
	 *
	 * @return A kiválasztott hókotró
	 */
	public Snowplower getSelectedSnowplower() {
		return selectedSnowplower;
	}

	/**
	 * Beállítja a kiválasztott hókotrót.
	 *
	 * @param selectedSnowplower Az újonnan kiválasztott hókotró
	 */
	public void setSelectedSnowplower(Snowplower selectedSnowplower) {
		this.selectedSnowplower = selectedSnowplower;
	}

	/**
	 * Visszaadja a kiválasztott buszt. Ha nincs ilyen, null-t.
	 *
	 * @return a kiválasztott busz
	 */
	public Bus getSelectedBus() {
		if (selectedBusDriver != null)
			return selectedBusDriver.getBus();
		return null;
	}

	/**
	 * Visszaadja a kiválasztott járművet. Ha nincs ilyen, null-t.
	 * * @return A kiválasztott jármű
	 */
	public Vehicle getSelectedVehicle() {
		Vehicle v = getSelectedBus();
		if (v == null)
			v = getSelectedSnowplower();
		return v;
	}

	/**
	 * Visszaadja a kiválasztott kereszteződést.
	 *
	 * @return a kiválasztott kereszteződés
	 */
	public Crossing getSelectedCrossing() {
		return selectedCrossing;
	}

	/**
	 * Beállítja a kiválasztott kereszteződést.
	 *
	 * @param selectedCrossing az újonnan kiválasztott kereszteződés
	 */
	public void setSelectedCrossing(Crossing selectedCrossing) {
		this.selectedCrossing = selectedCrossing;
		repaintMap();
	}

	/**
	 * Frissíti a térképet.
	 */
	public void repaintMap() {
		if (mapPanel != null) {
			mapPanel.repaint();
		}
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
	 * Összeállítja az aktív játékoshoz tartozó pénz- vagy pontszám szövegét.
	 *
	 * @return az aktív játékos adatait megjelenítő szöveg
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

	// Nem valós játokos, hogy lehessen üres érték a legördülő mezőben
	Player nullPlayer;

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

		comboBox.addItem(null); // nem lesz invisible busz.

		for (BusDriver busDriver : busDrivers) {
			comboBox.addItem(busDriver);
		}
		for (Cleaner cleaner : cleaners) {
			comboBox.addItem(cleaner);
		}
		comboBox.addActionListener(
				new ActionListener() {

					/**
					 * Kezeli az aktív játékos legördülő mezőjének változását, és beállítja
					 * a kiválasztott buszvezetőt vagy takarítót.
					 *
					 * @param e a kiválasztási esemény
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						Player selectedPlayer = (Player) comboBox.getSelectedItem();

						if (selectedPlayer == null) {
							selectedBusDriver = null;
							selectedCleaner = null;
							selectedSnowplower = null;
							playerData.setText(getActivePlayerDataText());
							updateVehiclePanel();
							return;
						}
						for (BusDriver busDriver : busDrivers) {
							if (busDriver.equals(selectedPlayer)) {
								selectedBusDriver = busDriver;
								selectedCleaner = null;
								selectedSnowplower = null;
								playerData.setText(getActivePlayerDataText());
								updateVehiclePanel();
								return;
							}
						}
						for (Cleaner cleaner : cleaners) {
							if (cleaner.equals(selectedPlayer)) {
								selectedCleaner = cleaner;
								selectedBusDriver = null;

								if (selectedCleaner.getSnowplowers().size() < 1) {
									selectedSnowplower = null;
									Logger.getGlobal().severe(
											"ComboBox Snowplower állításánál nincsen egy hókotrója sem az egyik játékosnak.");
								} else {
									selectedSnowplower = selectedCleaner.getSnowplowers().get(0);
								}
								playerData.setText(getActivePlayerDataText());
								updateVehiclePanel();
								return;
							}
						}
					}

				});
		return comboBox;
	}

	/**
	 * Az aktív játékos típusa alapján megjeleníti a megfelelő járművezérlő
	 * panelt, majd frissíti a térképet.
	 */
	public void updateVehiclePanel() {

		if (selectedBusDriver != null) {
			snowplowerPanel.setVisible(false);
			busPanel.setVisible(true);
		} else if (selectedCleaner != null) {
			busPanel.setVisible(false);
			snowplowerPanel.setVisible(true);
			snowplowerPanel.update();
		} else {
			busPanel.setVisible(false);
			snowplowerPanel.setVisible(false);
		}
		setIsExtendingPath(false);
		setSelectedCrossing(null);
		requestFocusInWindow();
	}

	/**
	 * Létrehozza az aktív játékos legördülő mezőjének megjelenítőjét.
	 *
	 * @return a játékosneveket megjelenítő listaelem-renderer
	 */
	private DefaultListCellRenderer createActivePlayerComboBoxRenderer() {
		return new DefaultListCellRenderer() {
			/**
			 * Beállítja, hogy a legördülő mező elemei a játékosok megjelenítendő
			 * nevét mutassák.
			 *
			 * @param list         a megjelenített lista
			 * @param value        az aktuális listaelem értéke
			 * @param index        az aktuális listaelem indexe
			 * @param isSelected   jelzi, hogy az elem ki van-e választva
			 * @param cellHasFocus jelzi, hogy az elem fókuszban van-e
			 * @return a listaelem megjelenítéséhez használt komponens
			 */
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
	 * @param text        a mezőben megjelenő szöveg
	 * @param font        a mező betűtípusa
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
}