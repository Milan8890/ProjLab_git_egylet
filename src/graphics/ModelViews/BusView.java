package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

import javax.imageio.ImageIO;

import entities.Bus;
import graphics.NewMain;

public class BusView {
	private Bus modelBus;
	private Point2D pos;

	static private BufferedImage img = null;
	private static java.awt.image.BufferedImage jegtoroImg = null;

	static void readImage() {
		try {
			img = ImageIO.read(new File("assets/testing/the thing.png"));
		} catch (Exception e) {
			System.err.println("Lol");
		}
	}

	public BusView(Bus bus) {
		if (img == null) {
			readImage();
		}

		if (jegtoroImg == null) {
            try {
                jegtoroImg = javax.imageio.ImageIO.read(new java.io.File("assets/testing/Jegtoro.png"));
            } catch (Exception e) {
                System.err.println("Nem sikerült beolvasni a Jegtoro.png-t!");
            }
        }

		this.modelBus = bus;

		// nincs meg a kép
		NewMain.notdone("BusView konstruktor");
	}

	private static final java.util.List<Double> utHosszak = new java.util.ArrayList<>();
    private final java.util.List<java.awt.geom.Point2D.Double> crossingsList = new java.util.ArrayList<>();

    public static double autoProgress = -1.0; 
    private static final int AUTO_SZELESSEG = 19;

	public void paint(Graphics2D g) {
		crossingsList.clear();
		utHosszak.clear();
		
		/*
		g.translate(300, 300);
		g.rotate(Math.toRadians(60));
		g.scale(1.5, 2);

		g.drawRect(-100, -100, 100, 100);

		// Normál rajzolás
		g.drawImage(img, 200, 200, null);

		// A tömb meghatározza a szorzókat a Piros, Zöld, Kék csatornákhoz
		float r = (float) Math.random();
		float green = (float) Math.random();
		float b = (float) Math.random();

		float[] scales = { r, green, b }; // Az utolsó az Alpha (átlátszóság)
		float[] offsets = new float[4]; // Eltolás (itt 0)

		// BESZÍNEZÉS CSAK AZ RGB KOORDINÁTÁKAT SZOROZZA SZÁMOKKAL
		RescaleOp tintFilter = new RescaleOp(scales, offsets, null);

		// A szűrt képet rajzoljuk ki
		g.drawImage(tintFilter.filter(img, null), 0, 0, null);

		NewMain.notdone("Bus paint");
		*/

		//Négyzetrács
    	Graphics2D gridG = (Graphics2D) g.create();
        gridG.setColor(java.awt.Color.LIGHT_GRAY);
        for (int x = 100; x < 1500; x += 100) { gridG.drawLine(x, 0, x, 1000); }
        for (int y = 100; y < 1000; y += 100) { gridG.drawLine(0, y, 1500, y); }
        gridG.dispose();

		//Kereszteződések
		KeresztezodesLetrehoz(g, 2, 2); // 0. index
    	KeresztezodesLetrehoz(g, 4, 5); // 1. index
    	KeresztezodesLetrehoz(g, 7, 3); // 2. index
		KeresztezodesLetrehoz(g, 4, 1); // 3. index
		KeresztezodesLetrehoz(g, 4, 9); // 4. index

   		//Gráfélek
    	VonallalOsszekot(g, 1, 2); // Összeköti a (2,2) és (6,2) pontokat
    	VonallalOsszekot(g, 2, 3);
		VonallalOsszekot(g, 3, 4);
		VonallalOsszekot(g, 2, 4);
		VonallalOsszekot(g, 2, 5); 

		//Sávok
		savoklerak(g, 1, 2, 3); 
    	savoklerak(g, 2, 3, 2);
		savoklerak(g, 4, 3, 2);
		savoklerak(g, 3, 4, 2);
		savoklerak(g, 2, 5, 1);
		savoklerak(g, 5, 2, 3);

		//auto
		autolerak(g, 1, 2, 2, 0);

    	NewMain.notdone("Bus paint");
	}

	private void KeresztezodesLetrehoz(Graphics2D g, int oszlop, int sor) {
        int cellaX = (oszlop - 1) * 100;
        int cellaY = (sor - 1) * 100;
        float szegelyVastagsag = 6f;
        crossingsList.add(new java.awt.geom.Point2D.Double(cellaX + 50.0, cellaY + 50.0));
        Graphics2D circleG = (Graphics2D) g.create();
        circleG.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        circleG.setColor(new java.awt.Color(30, 144, 255));
        circleG.fillOval(cellaX + 3, cellaY + 3, 94, 94);
        circleG.setColor(new java.awt.Color(0, 0, 139));
        circleG.setStroke(new java.awt.BasicStroke(szegelyVastagsag)); 
        circleG.drawOval(cellaX + 3, cellaY + 3, 94, 94);
        circleG.dispose();
    }

	private void VonallalOsszekot(Graphics2D g, int index1, int index2) {
        java.awt.geom.Point2D.Double[] pts = kapLeroviditettPontok(index1, index2);
        if (pts == null) return;
        Graphics2D lineG = (Graphics2D) g.create();
        lineG.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        lineG.setColor(java.awt.Color.RED);
        lineG.setStroke(new java.awt.BasicStroke(5f));
        lineG.drawLine((int)pts[0].x, (int)pts[0].y, (int)pts[1].x, (int)pts[1].y);
        lineG.dispose();
    }

	private void autolerak(Graphics2D g, int index1, int index2, int sávIndex, int utTombIndex) {
    	java.awt.geom.Point2D.Double[] pts = kapLeroviditettPontok(index1, index2);
    	if (pts == null || utTombIndex >= utHosszak.size()) return;

    	double maxHossz = utHosszak.get(utTombIndex);

    	if (autoProgress < 0) {
    	    autoProgress = 0;
    	}

    	int autoDinamikusHossz = 40;
    	if (jegtoroImg != null) {
    	    double kepEredetiSzelesseg = jegtoroImg.getWidth();
    	    double kepEredetiHossz = jegtoroImg.getHeight();
    	    double kepArany = kepEredetiSzelesseg / kepEredetiHossz;
    	    autoDinamikusHossz = (int) Math.round(AUTO_SZELESSEG * kepArany);
    	}

    	if (autoProgress + autoDinamikusHossz > maxHossz) {
    	    autoProgress = maxHossz - autoDinamikusHossz;
    	}

    	double dx = pts[1].x - pts[0].x;
    	double dy = pts[1].y - pts[0].y;
    	double uX = dx / maxHossz;
    	double uY = dy / maxHossz;
    	double nX = -uY;
    	double nY = uX;

    	int savSzelesseg = 20;
    	double szog = Math.atan2(dy, dx);

    	// Kiszámítjuk a sáv merőleges eltolását a piros vonaltól (a sárga szegély belső peremére: +3 pixel)
    	double eltolasMeroleges = 3.0 + ((sávIndex - 1) * savSzelesseg);

    	// Az autó aktuális koordinátája a sávon belül a megtett progress (haladás) alapján
    	double autoX = pts[0].x + (nX * eltolasMeroleges) + (uX * autoProgress);
    	double autoY = pts[0].y + (nY * eltolasMeroleges) + (uY * autoProgress);

    	Graphics2D carG = (Graphics2D) g.create();
    	carG.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
    
    	// Áttoljuk az origót a kocsi elejére és beforgatjuk az út szögébe
    	carG.translate(autoX, autoY);
    	carG.rotate(szog);

    	if (jegtoroImg != null) {
    	    carG.drawImage(jegtoroImg, 0, 0, autoDinamikusHossz, AUTO_SZELESSEG, null);
    	} else {
    	    carG.setColor(java.awt.Color.GREEN);
    	    carG.fillRect(0, 0, autoDinamikusHossz, AUTO_SZELESSEG);
    	}

    	carG.dispose();
	}

	private void savoklerak(Graphics2D g, int index1, int index2, int savSzam) {
        java.awt.geom.Point2D.Double[] pts = kapLeroviditettPontok(index1, index2);
        if (pts == null) return;
        double dx = pts[1].x - pts[0].x;
        double dy = pts[1].y - pts[0].y;
        double hossza = Math.sqrt(dx * dx + dy * dy);
        if (hossza == 0) return;
        double uX = dx / hossza; double uY = dy / hossza;
        double nX = -uY; double nY = uX;  
        int savSzelesseg = 20; float szegelyVastagsag = 1f;
        double szog = Math.atan2(dy, dx);

        for (int i = 0; i < savSzam; i++) {
            double eltolasMeroleges = 2.5 + (i * savSzelesseg);
            double eltolasHaladasi = 2.5;
            double sávKezdetX = pts[0].x + (nX * eltolasMeroleges) - (uX * eltolasHaladasi);
            double sávKezdetY = pts[0].y + (nY * eltolasMeroleges) - (uY * eltolasHaladasi);
            Graphics2D laneG = (Graphics2D) g.create();
            laneG.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            laneG.translate(sávKezdetX, sávKezdetY);
            laneG.rotate(szog);
            int vizualisHossz = (int) Math.round(hossza + 5.0);
            laneG.setColor(java.awt.Color.BLACK);
            laneG.fillRect(1, 1, vizualisHossz - 1, savSzelesseg - 1);
            laneG.setColor(java.awt.Color.YELLOW);
            laneG.setStroke(new java.awt.BasicStroke(szegelyVastagsag));
            laneG.drawRect(0, 0, vizualisHossz, savSzelesseg);
            laneG.dispose();
        }
    }

	private java.awt.geom.Point2D.Double[] kapLeroviditettPontok(int index1, int index2) {
        if (index1 < 1 || index2 < 1 || index1 > crossingsList.size() || index2 > crossingsList.size()) return null;
        java.awt.geom.Point2D.Double p1 = crossingsList.get(index1 - 1);
        java.awt.geom.Point2D.Double p2 = crossingsList.get(index2 - 1);
        double dx = p2.x - p1.x; double dy = p2.y - p1.y;
        double tavolsag = Math.sqrt(dx * dx + dy * dy);
        if (tavolsag == 0) return null;
        double uX = dx / tavolsag; double uY = dy / tavolsag;
        double lerovidites = 53.0;
        java.awt.geom.Point2D.Double start = new java.awt.geom.Point2D.Double(p1.x + uX * lerovidites, p1.y + uY * lerovidites);
        java.awt.geom.Point2D.Double end = new java.awt.geom.Point2D.Double(p2.x - uX * lerovidites, p2.y - uY * lerovidites);
        
        if (utHosszak.size() < crossingsList.size()) {
            utHosszak.add(tavolsag - (2 * lerovidites));
        }
        return new java.awt.geom.Point2D.Double[]{start, end};
    }
}
