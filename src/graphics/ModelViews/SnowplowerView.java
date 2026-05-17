package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.io.File;
import javax.imageio.ImageIO;

import playground.Crossing;
import playground.Lane;
import graphics.MainPanel;
import graphics.NewMain;
import entities.Snowplower;
import equipment.Head;

public class SnowplowerView {
    private Snowplower modelSnowplower;
    private Point2D pos;
    private MainPanel mainPanel;

    private static BufferedImage imgHanyo;
    private static BufferedImage imgJegtoro;
    private static BufferedImage imgKoszoro; 
    private static BufferedImage imgSarkany;
    private static BufferedImage imgSopro;
    private static BufferedImage imgSoszoro;

    static {
        try {
            imgHanyo    = ImageIO.read(new File("Asset/hanyo.png"));
            imgJegtoro  = ImageIO.read(new File("Asset/jegtoro.png"));
            imgKoszoro  = ImageIO.read(new File("Asset/koszoro.png"));
            imgSarkany  = ImageIO.read(new File("Asset/sarkany.png"));
            imgSopro    = ImageIO.read(new File("Asset/sopro.png"));
            imgSoszoro  = ImageIO.read(new File("Asset/soszoro.png"));
        } catch (Exception e) {
            // Hiba eseten uresen marad, nem szall el a program
        }
    }

    public SnowplowerView(Snowplower snowplower, MainPanel mainPanel) {
        this.modelSnowplower = snowplower;
        this.mainPanel = mainPanel;
        this.pos = new Point2D.Double(0, 0);
    }

    public void paint(Graphics2D g) {
        Lane currentLane = modelSnowplower.getCurrentLane();
        double plowX, plowY, szog;

        if (currentLane != null) {
            double absoluteProgress = modelSnowplower.getLaneProgress();

            LaneView currentLaneView = null;
            for (LaneView lv : mainPanel.getLaneViews()) {
                if (lv.getLane() == currentLane) {
                    currentLaneView = lv;
                    break;
                }
            }
            if (currentLaneView == null) return;

            double startX = currentLaneView.startPos.getX();
            double startY = currentLaneView.startPos.getY();
            double endX = currentLaneView.endPos.getX();
            double endY = currentLaneView.endPos.getY();

            double utHossz = currentLane.getRoad().getLength();
            double progressRatio = (utHossz > 0) ? (absoluteProgress / utHossz) : 0.0;

            plowX = startX + (endX - startX) * progressRatio;
            plowY = startY + (endY - startY) * progressRatio;

            double dx = endX - startX;
            double dy = endY - startY;
            szog = Math.atan2(dy, dx);

        } else {
            Crossing currentCrossing = modelSnowplower.getLastCrossing();
            if (currentCrossing == null) return;

            CrossingView currentCrossingView = mainPanel.getCrossingView(currentCrossing);
            if (currentCrossingView == null) return;

            Point2D.Double center = MainPanel.calculateCenter(currentCrossingView);
            plowX = center.x;
            plowY = center.y;
            szog = 0.0; 
        }
        
        this.pos.setLocation(plowX, plowY);

        if (modelSnowplower.getHeadInventory() == null) return;
        Head activeHead = modelSnowplower.getHeadInventory().getActiveHead();
        if (activeHead == null) return;
        
        String fejTipus = activeHead.getClass().getSimpleName().toLowerCase();
        
        BufferedImage kivalasztottKep = null;

        if (fejTipus.contains("ejector")) {
            kivalasztottKep = imgHanyo;
        } else if (fejTipus.contains("breaker")) {
            kivalasztottKep = imgJegtoro;
        } else if (fejTipus.contains("gravel")) {
            kivalasztottKep = imgKoszoro;
        } else if (fejTipus.contains("dragon")) {
            kivalasztottKep = imgSarkany;
        } else if (fejTipus.contains("sweeper")) {
            kivalasztottKep = imgSopro;
        } else if (fejTipus.contains("salt")) {
            kivalasztottKep = imgSoszoro;
        }

        if (kivalasztottKep == null) return;

        double eredetiSzelesseg = kivalasztottKep.getWidth();
        double eredetiMagassag = kivalasztottKep.getHeight();

        int jarmuMagassag = MainPanel.LANE_WIDTH;
        int jarmuHossz = (int) Math.round(jarmuMagassag * (eredetiSzelesseg / eredetiMagassag));

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.translate(plowX, plowY);
        g2.rotate(szog);

        g2.drawImage(kivalasztottKep, 
            -jarmuHossz / 2, -jarmuMagassag / 2, 
            jarmuHossz, jarmuMagassag, 
            null
        );

        g2.dispose();
    }

    public boolean isClicked(int x, int y) {
        NewMain.notdone("SnowplowerView isClicked");
        return false;
    }
}