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
import entities.Car;

public class CarView {
    private Car modelCar;
    private Point2D pos;
    private MainPanel mainPanel;
    private static BufferedImage carImage;
    
    static {
        try {
            carImage = ImageIO.read(new File("Asset/auto.png"));
        } catch (Exception e) {
            carImage = null;
        }
    }

    public CarView(Car car, MainPanel mainPanel) {
        this.modelCar = car;
        this.mainPanel = mainPanel;
        this.pos = new Point2D.Double(0, 0);
    }

    public void paint(Graphics2D g) {
        if (carImage == null) return;

        Lane currentLane = modelCar.getCurrentLane();
        double carX, carY, szog;

        if (currentLane != null) {
            double absoluteProgress = modelCar.getLaneProgress();

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

            carX = startX + (endX - startX) * progressRatio;
            carY = startY + (endY - startY) * progressRatio;

            double dx = endX - startX;
            double dy = endY - startY;
            szog = Math.atan2(dy, dx);

        } else {
            Crossing currentCrossing = modelCar.getLastCrossing();
            if (currentCrossing == null) return;

            CrossingView currentCrossingView = mainPanel.getCrossingView(currentCrossing);
            if (currentCrossingView == null) return;

            Point2D.Double center = MainPanel.calculateCenter(currentCrossingView);
            carX = center.x;
            carY = center.y;
            
            szog = 0.0; 
        }
        
        this.pos.setLocation(carX, carY);

        double eredetiSzelesseg = carImage.getWidth();
        double eredetiMagassag = carImage.getHeight();

        int autoMagassag = MainPanel.LANE_WIDTH;
        int autoHossz = (int) Math.round(autoMagassag * (eredetiSzelesseg / eredetiMagassag));

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.translate(carX, carY);
        g2.rotate(szog);

        g2.drawImage(carImage, 
            -autoHossz / 2, -autoMagassag / 2, 
            autoHossz, autoMagassag, 
            null
        );

        g2.dispose();
    }
}