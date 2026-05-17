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
import entities.Bus;

public class BusView {
    private Bus modelBus;
    private Point2D pos;
    private MainPanel mainPanel;
    private static BufferedImage img;

    static {
        try {
            img = ImageIO.read(new File("Asset/busz.png"));
        } catch (Exception e) {
            img = null;
        }
    }

    public BusView(Bus bus, MainPanel mainPanel) {
        this.modelBus = bus;
        this.mainPanel = mainPanel;
        this.pos = new Point2D.Double(0, 0);
    }

    public void paint(Graphics2D g) {
        if (img == null) return;

        Lane currentLane = modelBus.getCurrentLane();
        double busX, busY, szog;

        if (currentLane != null) {
            double absoluteProgress = modelBus.getLaneProgress();

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

            busX = startX + (endX - startX) * progressRatio;
            busY = startY + (endY - startY) * progressRatio;

            double dx = endX - startX;
            double dy = endY - startY;
            szog = Math.atan2(dy, dx);

        } else {
            Crossing currentCrossing = modelBus.getLastCrossing();
            if (currentCrossing == null) return;

            CrossingView currentCrossingView = mainPanel.getCrossingView(currentCrossing);
            if (currentCrossingView == null) return;

            Point2D.Double center = MainPanel.calculateCenter(currentCrossingView);
            busX = center.x;
            busY = center.y;
            szog = 0.0; 
        }
        
        this.pos.setLocation(busX, busY);

        double eredetiSzelesseg = img.getWidth();
        double eredetiMagassag = img.getHeight();

        int busMagassag = MainPanel.LANE_WIDTH;
        int busHossz = (int) Math.round(busMagassag * (eredetiSzelesseg / eredetiMagassag));

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.translate(busX, busY);
        g2.rotate(szog);

        g2.drawImage(img, 
            -busHossz / 2, -busMagassag / 2, 
            busHossz, busMagassag, 
            null
        );

        g2.dispose();
    }
}