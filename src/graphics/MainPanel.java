package graphics;

import java.util.ArrayList;
import java.util.List;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
import playground.Lane;
import playground.Road;
import user.BusDriver;
import user.Cleaner;

public class MainPanel extends JFrame {
    public static final int CROSSING_SIZE = 150;
    public static final int LANE_WIDTH = 20;
    public static final float CROSSING_STROKE = 6f;

    private BufferedImage backgroundImage;

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

    private JComboBox playerSelectorComboBox;
    private JTextField playerData;

    private SnowplowerPanel snowplowerPanel;
    private BusPanel busPanel;

    private MapPanel mapPanel;

    public MainPanel() {
        crossingViews = new ArrayList<>();
        roadViews = new ArrayList<>();
        laneViews = new ArrayList<>();
        snowplowerViews = new ArrayList<>();
        busViews = new ArrayList<>();
        carViews = new ArrayList<>();

        try {
            backgroundImage = ImageIO.read(new File("Asset/fu.jpg"));
        } catch (Exception e) {
            backgroundImage = null;
        }

        buildTestMap();
        initMapPanel();
    }

    private void buildTestMap() {
        Crossing c1 = new Crossing();
        Crossing c2 = new Crossing();
        Crossing c3 = new Crossing();
        
        Point2D.Double pos1 = new Point2D.Double(400, 500);
        Point2D.Double pos2 = new Point2D.Double(0, 0);
        Point2D.Double pos3 = new Point2D.Double(800, 100);
        
        crossingViews.add(new CrossingView(c1, new Point2D.Double(pos1.x + 3, pos1.y + 3), false));
        crossingViews.add(new CrossingView(c2, new Point2D.Double(pos2.x + 3, pos2.y + 3), false));
        crossingViews.add(new CrossingView(c3, new Point2D.Double(pos3.x + 3, pos3.y + 3), false));

        addRoadWithLanes(c2, c1, 3); 
        addRoadWithLanes(c2, c3, 2); 
        addRoadWithLanes(c3, c2, 2); 
    }

    private void initMapPanel() {
        JPanel rajzPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); 
                
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (backgroundImage != null) {
                    g2.drawImage(backgroundImage, 0, 0, 1500, 1000, this);
                }
                //racs
                g2.setColor(Color.LIGHT_GRAY);
                for (int x = 100; x < 1500; x += 100) { g2.drawLine(x, 0, x, 1000); }
                for (int y = 100; y < 1000; y += 100) { g2.drawLine(0, y, 1500, y); }
                
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

                g2.dispose();
            }
        };

        rajzPanel.setPreferredSize(new Dimension(1500, 1000));
        rajzPanel.setBackground(Color.WHITE); 
        this.setContentPane(rajzPanel);
    }

    private void addRoadWithLanes(Crossing from, Crossing to, int savSzam) {
        CrossingView fromView = getCrossingView(from);
        CrossingView toView = getCrossingView(to);

        if (fromView == null || toView == null) return;

        Point2D.Double kp1 = calculateCenter(fromView);
        Point2D.Double kp2 = calculateCenter(toView);

        Point2D.Double[] roadEnds = calculateRoadEndPoints(kp1, kp2);
        if (roadEnds == null) return;
        
        Point2D.Double utEleje = roadEnds[0];
        Point2D.Double utVege = roadEnds[1];

        double valosUtHossz = utEleje.distance(utVege);
        Road road = new Road(from, to, savSzam, valosUtHossz);
        
        roadViews.add(new RoadView(road, utEleje, utVege));
        generateAndAddLaneViews(road, utEleje, utVege);
    }

    public static Point2D.Double calculateCenter(CrossingView view) {
        double sugar = CROSSING_SIZE / 2.0;
        double centerX = view.pos.getX() - 3.0 + sugar;
        double centerY = view.pos.getY() - 3.0 + sugar;
        return new Point2D.Double(centerX, centerY);
    }

    private Point2D.Double[] calculateRoadEndPoints(Point2D.Double kp1, Point2D.Double kp2) {
        double dx = kp2.x - kp1.x;
        double dy = kp2.y - kp1.y;
        double kozepTavolsag = Math.sqrt(dx * dx + dy * dy);
        if (kozepTavolsag == 0) return null;

        double uX = dx / kozepTavolsag;
        double uY = dy / kozepTavolsag;

        double kulsoPeremSugar = (CROSSING_SIZE / 2.0) + (CROSSING_STROKE / 2.0);

        Point2D.Double utEleje = new Point2D.Double(kp1.x + uX * kulsoPeremSugar, kp1.y + uY * kulsoPeremSugar);
        Point2D.Double utVege = new Point2D.Double(kp2.x - uX * kulsoPeremSugar, kp2.y - uY * kulsoPeremSugar);

        return new Point2D.Double[]{ utEleje, utVege };
    }

    private void generateAndAddLaneViews(Road road, Point2D.Double utEleje, Point2D.Double utVege) {
        double dx = utVege.x - utEleje.x;
        double dy = utVege.y - utEleje.y;
        double utHossz = Math.sqrt(dx * dx + dy * dy);
        if (utHossz == 0) return;

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

            laneViews.add(new LaneView(modelLanes.get(i), laneStart, laneEnd));
        }
    }

    public boolean getIsExtendingPath() {
        NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic boolean getIsExtendingPath() {");
        return isExtendingPath;
    }

    public void setIsExtendingPath(boolean b) {
        NewMain.notdone(
                "MainPanel getter/setter at (raw function header):\npublic void setIsExtendingPath(boolean b) {");
        isExtendingPath = b;
    }

    public CrossingView getCrossingView(Crossing c) {
        for (CrossingView cv : crossingViews) {
            if (cv.getCrossing() == c) {
                return cv;
            }
        }
        return null;
    }

    public List<RoadView> getRoadViews() {
        NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic List<RoadView> getRoadViews() {");
        return roadViews;
    }

    public List<LaneView> getLaneViews() {
        NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic List<LaneView> getLaneViews() {");
        return laneViews;
    }

    public List<SnowplowerView> getSnowplowerViews() {
        NewMain.notdone(
                "MainPanel getter/setter at (raw function header):\npublic List<SnowplowerView> getSnowplowerViews() {");
        return snowplowerViews;
    }

    public List<BusView> getBusViews() {
        NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic List<BusView> getBusViews() {");
        return busViews;
    }

    public List<CarView> getCarViews() {
        NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic List<CarView> getCarViews() {");
        return carViews;
    }

    public Cleaner getSelectedCleaner() {
        NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic Cleaner getSelectedCleaner() {");
        return selectedCleaner;
    }

    public BusDriver getSelectedBusDriver() {
        NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic BusDriver getSelectedBusDriver() {");
        return selectedBusDriver;
    }

    public Snowplower getSelectedSnowplower() {
        NewMain.notdone(
                "MainPanel getter/setter at (raw function header):\npublic Snowplower getSelectedSnowplower() {");
        return selectedSnowplower;
    }

    public void setSelectedSnowplower(Snowplower selectedSnowplower) {
        NewMain.notdone(
                "MainPanel getter/setter at (raw function header):\npublic void setSelectedSnowplower(Snowplower selectedSnowplower) {");
        this.selectedSnowplower = selectedSnowplower;
    }

    public Bus getSelectedBus() {
        NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic Bus getSelectedBus() {");
        return null;
    }

    public Crossing getSelectedCrossing() {
        NewMain.notdone("MainPanel getter/setter at (raw function header):\npublic Crossing getSelectedCrossing() {");
        return selectedCrossing;
    }

    public void setSelectedCrossing(Crossing selectedCrossing) {
        NewMain.notdone(
                "MainPanel getter/setter at (raw function header):\npublic void setSelectedCrossing(Crossing selectedCrossing) {");
        this.selectedCrossing = selectedCrossing;
    }

    public void update() {
        NewMain.notdone("MainPanel update");
    }

}