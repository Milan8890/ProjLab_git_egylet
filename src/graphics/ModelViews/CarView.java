package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import playground.Lane;
import graphics.MainPanel;

import entities.Bus;
import entities.Car;

import graphics.NewMain;
import graphics.MainPanel;

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

	public CarView(Car car) {
		this.modelCar = modelCar;
        this.mainPanel = mainPanel;
        this.pos = new Point2D.Double(0, 0);
	}

	public void paint(Graphics2D g) {
        if (modelCar.getPath() == null) return;

        Lane currentLane = modelCar.getPath().getCurrentLane();
        double progress = modelCar.getPath().getLaneProgress();

        if (currentLane == null) return;

        LaneView currentLaneView = null;
        for (LaneView lv : mainPanel.getLaneViews()) {
            if (lv.getLane() == currentLane) {
                currentLaneView = lv;
                break;
            }
        }

        if (currentLaneView == null) return;

        // Kiolvassuk a sáv már előre tökéletesen kiszámított pixel végpontjait
        double startX = currentLaneView.startPos.getX();
        double startY = currentLaneView.startPos.getY();
        double endX = currentLaneView.endPos.getX();
        double endY = currentLaneView.endPos.getY();


        double carX = startX + (endX - startX) * progress;
        double carY = startY + (endY - startY) * progress;
        
        this.pos.setLocation(carX, carY);

        double dx = endX - startX;
        double dy = endY - startY;
        double szog = Math.atan2(dy, dx);

        double eredetiSzelesseg = carImage.getWidth();
        double eredetiMagassag = carImage.getHeight();

        int autoMagassag = MainPanel.LANE_WIDTH;
        
        int autoHossz = (int) Math.round(autoMagassag * (eredetiSzelesseg / eredetiMagassag));

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

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
