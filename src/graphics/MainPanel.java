package graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
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
import entities.Car;
import entities.Snowplower;
import entities.Vehicle;
import graphics.ModelViews.BusView;
import graphics.ModelViews.CarView;
import graphics.ModelViews.CrossingView;
import graphics.ModelViews.LaneView;
import graphics.ModelViews.RoadView;
import graphics.ModelViews.SnowplowerView;
import graphics.Panels.BusPanel;
import graphics.Panels.MapPanel;
import graphics.Panels.SnowplowerPanel;
import main.App;
import main.World;
import playground.City;
import playground.Crossing;
import playground.Lane;
import playground.Path;
import playground.Road;
import user.BusDriver;
import user.Cleaner;
import user.Player;
import user.setupPlayerData;

/**
 * A játék fő grafikus ablaka, amely a térképet, az aktív játékos adatait és a
 * járműspecifikus vezérlőpaneleket fogja össze.
 */
public class MainPanel extends JFrame {
	public static final int CROSSING_SIZE = 80;
	public static final int LANE_WIDTH = 20;
	public static final float CROSSING_STROKE = 6f;

	private BufferedImage backgroundImage;
	private List<Cleaner> cleaners = new ArrayList<>();
	private List<BusDriver> busDrivers = new ArrayList<>();
	private Map<Cleaner, Color> cleanerColors = new HashMap<>();
	private boolean isExtendingPath;

	private Cleaner selectedCleaner;
	private BusDriver selectedBusDriver;
	private Snowplower selectedSnowplower;
	private Crossing selectedCrossing;

	private int carNumber = 5;		//Csak, hogy ne hardCodeolva legyen.

	private List<CrossingView> crossingViews = new ArrayList<>();
	private List<RoadView> roadViews = new ArrayList<>();
	private List<LaneView> laneViews = new ArrayList<>();
	private List<SnowplowerView> snowplowerViews = new ArrayList<>();
	private List<BusView> busViews = new ArrayList<>();
	private List<CarView> carViews = new ArrayList<>();

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
	public MainPanel() {
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

		initCars(carNumber);

		initPlayerViews(App.setupPlayer());
		activePlayerPanel = createActivePlayerPanel();
		snowplowerPanel = new SnowplowerPanel(this);
		busPanel = new BusPanel(this);
		initMapPanel();

		//TESZT
		setLayout(new BorderLayout());

		JPanel sidePanel = new JPanel(new BorderLayout());
		sidePanel.setBackground(Color.WHITE);
		sidePanel.setPreferredSize(new Dimension(320, 1000));

		sidePanel.add(activePlayerPanel, BorderLayout.NORTH);

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
		//TESZT VÉGE
		/*
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// --- BAL OLDAL: Map Panel ---
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(mapPanel, gbc);

		// --- JOBB OLDAL, FELSŐ: Active Player Panel ---
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.insets = new Insets(0, 0, 10, 0);
		add(activePlayerPanel, gbc);

		// --- JOBB OLDAL, ALSÓ: Snowplower Panel ---
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.NONE;		//BOTH volt
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.insets = new Insets(0, 0, 0, 0);

		busPanel.setVisible(false);
		snowplowerPanel.setVisible(false);
		add(snowplowerPanel, gbc);
		add(busPanel, gbc);
		*/

		try {
			backgroundImage = ImageIO.read(new File("Asset/zuzmaravaros.png"));
		} catch (Exception e) {
			backgroundImage = null;
		}

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

		//1-es kereszteződésből induló utak
		addRoadWithLanes(c1, c2, 2);
		addRoadWithLanes(c1, c7, 2);
		//2-es kereszteződésből induló utak
		addRoadWithLanes(c2, c3, 1);
		addRoadWithLanes(c2, c1, 2);
		//3-as kereszteződésből induló utak
		addRoadWithLanes(c3, c2, 2);
		addRoadWithLanes(c3, c4, 2);
		addRoadWithLanes(c3, c8, 1);
		//4-es kereszteződésből induló utak
		addRoadWithLanes(c4, c3, 2);
		addRoadWithLanes(c4, c5, 1);
		//5-ös kereszteződésből induló utak
		addRoadWithLanes(c5, c4, 3);
		addRoadWithLanes(c5, c11, 1);
		//6-os kereszteződésből induló utak
		addRoadWithLanes(c6, c5, 3);
		addRoadWithLanes(c6, c12, 1);
		//7-es kereszteződésből induló utak
		addRoadWithLanes(c7, c1, 2);
		addRoadWithLanes(c7, c13, 2);
		//8-0s kereszteződésből induló utak
		addRoadWithLanes(c8, c3, 1);
		addRoadWithLanes(c8, c9, 1);
		addRoadWithLanes(c8, c13, 1);
		//9-es kereszteződésből induló utak
		addRoadWithLanes(c9, c8, 2);
		addRoadWithLanes(c9, c10, 2);
		//10-es kereszteződésből induló utak
		addRoadWithLanes(c10, c9, 3);
		addRoadWithLanes(c10, c11, 1);
		addRoadWithLanes(c10, c16, 2);
		//11-es kereszteződésből induló utak
		addRoadWithLanes(c11, c5, 2);
		addRoadWithLanes(c11, c10, 2);
		addRoadWithLanes(c11, c12, 1);
		addRoadWithLanes(c11, c17, 1);
		//12-es kereszteződésből induló utak
		addRoadWithLanes(c12, c6, 3);
		addRoadWithLanes(c12, c11, 2);
		addRoadWithLanes(c12, c17, 1);
		//13-as kereszteződésből induló utak
		addRoadWithLanes(c13, c7, 1);
		addRoadWithLanes(c13, c8, 2);
		addRoadWithLanes(c13, c14, 2);
		addRoadWithLanes(c13, c18, 2);
		//14-es kereszteződésből induló utak
		addRoadWithLanes(c14, c8, 1);
		addRoadWithLanes(c14, c15, 1);
		addRoadWithLanes(c14, c19, 1);
		//15-ös kereszteződésből induló utak
		addRoadWithLanes(c15, c14, 2);
		addRoadWithLanes(c15, c9, 2);
		addRoadWithLanes(c15, c20, 1);
		//16-os kereszteződésből induló utak
		addRoadWithLanes(c16, c10, 2);
		addRoadWithLanes(c16, c17, 2);
		addRoadWithLanes(c16, c21, 2);
		//17-es kereszteződésből induló utak
		addRoadWithLanes(c17, c16, 1);
		addRoadWithLanes(c17, c12, 2);
		addRoadWithLanes(c17, c22, 1);
		//18-as kereszteződésből induló utak
		addRoadWithLanes(c18, c13, 1);
		addRoadWithLanes(c18, c19, 2);
		//19-es kereszteződésből induló utak
		addRoadWithLanes(c19, c18, 1);
		addRoadWithLanes(c19, c14, 1);
		addRoadWithLanes(c19, c20, 2);
		//20-as kereszteződésből induló utak
		addRoadWithLanes(c20, c19, 1);
		addRoadWithLanes(c20, c15, 2);
		addRoadWithLanes(c20, c21, 1);
		//21-es kereszteződésből induló utak
		addRoadWithLanes(c21, c20, 1);
		addRoadWithLanes(c21, c16, 1);
		addRoadWithLanes(c21, c22, 3);
		//21-es kereszteződésből induló utak
		addRoadWithLanes(c22, c21, 1);
		addRoadWithLanes(c22, c17, 1);
		addRoadWithLanes(c22, c23, 2);
		//23-as kereszteződésből induló utak
		addRoadWithLanes(c23, c17, 2);

		City.getCrossings().clear();

		City.getCrossings().addAll(List.of(
			c1, c2, c3, c4, c5, c6, c7, c8, c9, c10,
			c11, c12, c13, c14, c15, c16, c17, c18,
			c19, c20, c21, c22, c23
		));

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
		Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE,
						Color.MAGENTA, Color.CYAN, Color.PINK };

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
			carViews.add(new CarView(car, this, colors[random.nextInt(colors.length)] ));
		}
	}

	/**
	 * Létrehozza a térképet kirajzoló panelt, és beállítja a pályaelemek,
	 * járművek és háttérkép rajzolási sorrendjét.
	 */
	private void initMapPanel() {
		MapPanel rajzPanel = new MapPanel(this) {
			/**
			 * Kirajzolja a térkép hátterét, a kereszteződéseket, utakat, sávokat és
			 * a járműveket.
			 *
			 * @param g a rajzoláshoz kapott grafikus kontextus
			 */
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				if (backgroundImage != null) {
					g2.drawImage(backgroundImage, 0, 0, 1500, 1000, this);
				}

				/*
				// racs
				g2.setColor(Color.LIGHT_GRAY);
				for (int x = 100; x < 1500; x += 100) {
					g2.drawLine(x, 0, x, 1000);
				}
				for (int y = 100; y < 1000; y += 100) {
					g2.drawLine(0, y, 1500, y);
				}
				*/

				for (CrossingView cv : crossingViews) {
					cv.paint(g2);
				}

				for (RoadView rv : roadViews) {
					rv.paint(g2);
				}

				for (LaneView lv : laneViews) {
					lv.paint(g2);
				}

				for (CarView cv : carViews) {
					cv.paint(g2);
				}

				for (SnowplowerView spv : snowplowerViews) {
					spv.paint(g2);
				}

				for (BusView bv : busViews) {
					bv.paint(g2);
				}

				g2.dispose();
			}
		};

		// rajzPanel.setBackground(Color.WHITE);
		mapPanel = rajzPanel;
	}

	/**
	 * Létrehoz egy modellszintű utat a két kereszteződés között, 
	 * majd hozzáadja az út- és sávnézeteket.
	 *
	 * @param from   az út kezdő kereszteződése
	 * @param to     az út cél kereszteződése
	 * @param savSzam az út sávjainak száma
	 */
	private void addRoadWithLanes(Crossing from, Crossing to, int savSzam) {
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
		Road road = new Road(from, to, savSzam, valosUtHossz);

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
	 * @return a kiszámolt útvégpontok tömbje, vagy {@code null}, ha a két pont azonos
	 */
	private Point2D.Double[] calculateRoadEndPoints(Point2D.Double kp1, Point2D.Double kp2) {
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
						busViews.add(new BusView(bd.getBus(), this, playerData.getColor()));;
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
		World.setIsSnowing(true);	//Bekapcsolja a havazást.
		int delay = 50; 			//1000 ms / 50 ms = 20 tick/sec (fps)

		javax.swing.Timer timer = new javax.swing.Timer(delay, e -> {
			World.tick();
    		playerData.setText(getActivePlayerDataText());
			mapPanel.repaint();
		});

		timer.start();
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
	public CrossingView getCrossingView(Crossing c) {
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
	 * Visszaadja a kiválasztott buszvezető játékost. Ha nem buszvezető van
	 * kiválasztva, null-t.
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
	 * 
	 * @return A kiválasztott jármű
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
	 * Frissíti a főpanel megjelenítését.
	 */
	/*
	 * ez itt elv nem kell de itthagyom
	 * public void update() {
	 * NewMain.notdone("MainPanel update");
	 * loadActivePlayerComboBox();
	 * handleActivePlayerSelection();
	 * }
	 */

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

		nullPlayer = new BusDriver("-");
		comboBox.addItem(nullPlayer);

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

					if (selectedPlayer == nullPlayer) {
						selectedBusDriver = null;
						selectedCleaner = null;
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

	/**
	 * Újrarajzolja a fő ablakot, és közben frissíti az aktív játékoshoz tartozó
	 * információs mezőt.
	 */
	@Override
	public void repaint() {
		// Többi repaint meghívása
		super.repaint();
		// TODO Elméletben ez kell, mert ez az egyetlen UI elem, ami változhat
		// újrarajzolások közt.
		// Ez felel azért, hogy a pontszám és pénz valós időben változzon.
		// Ha nem menne, akkor szerintem a loopban repaintot meg kell hívni rá. (Ha loop
		// alapján futtat a main függvény)
		playerData.setText(getActivePlayerDataText());
	}

}
