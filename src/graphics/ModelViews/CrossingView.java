package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.BasicStroke;

import playground.Crossing;
import graphics.MainPanel;

public class CrossingView {
    public Point2D pos;

    private Crossing modelCrossing;
    private boolean isSnowplowerBase;
    private MainPanel mainPanel;

    public CrossingView(Crossing modelCrossing, Point2D pos, boolean isSnowplowerBase) {
        this(modelCrossing, pos, isSnowplowerBase, null);
    }

    public CrossingView(Crossing modelCrossing, Point2D pos, boolean isSnowplowerBase, MainPanel mainPanel) {
        this.modelCrossing = modelCrossing;
        this.pos = pos;
        this.isSnowplowerBase = isSnowplowerBase;
        this.mainPanel = mainPanel;
    }

    public Crossing getCrossing() {
        return modelCrossing;
    }

    public void paint(Graphics2D g) { 
        if (!updatePos())
            return;

        int x = (int) pos.getX();
        int y = (int) pos.getY();
        
        int atmero = MainPanel.CROSSING_SIZE - (int)MainPanel.CROSSING_STROKE; 
        float vastagsag = MainPanel.CROSSING_STROKE;

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(new Color(30, 144, 255));
        g2.fillOval(x, y, atmero, atmero);

        g2.setColor(new Color(0, 0, 139));
        g2.setStroke(new BasicStroke(vastagsag));
        g2.drawOval(x, y, atmero, atmero);

        g2.dispose();
    }

    private boolean updatePos() {
        return pos != null;
    }

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
        return true;
    }
}
