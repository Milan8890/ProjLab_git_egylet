package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.Color;

import graphics.NewMain;
import playground.Crossing;
import graphics.MainPanel;

public class CrossingView {
    public Point2D pos;

    private Crossing modelCrossing;
    private boolean isSnowplowerBase;

    public CrossingView(Crossing modelCrossing, Point2D pos, boolean isSnowplowerBase) {
        this.modelCrossing = modelCrossing;
        this.pos = pos;
        this.isSnowplowerBase = isSnowplowerBase;
    }

    public Crossing getCrossing() {
        return modelCrossing;
    }

    public void paint(Graphics2D g) { 
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        
        int atmero = MainPanel.CROSSING_SIZE - (int)MainPanel.CROSSING_STROKE; 
        float vastagsag = MainPanel.CROSSING_STROKE;

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(new Color(30, 144, 255));
        g2.fillOval(x, y, atmero, atmero);

        g2.setColor(new Color(0, 0, 139));
        g2.setStroke(new java.awt.BasicStroke(vastagsag));
        g2.drawOval(x, y, atmero, atmero);

        g2.dispose();
    }

    public boolean isClicked(int x, int y) {
        NewMain.notdone("CrossingView isClicked");
        return false;
    }
}