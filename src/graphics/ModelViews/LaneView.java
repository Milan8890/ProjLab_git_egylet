package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.BasicStroke;

import graphics.NewMain;
import playground.Lane;
import graphics.MainPanel;

public class LaneView {
	private static final float SZEGELY_VASTAGSAG = 1f;

	private static final BufferedImage[] ASZFALT_TEXTURAK = new BufferedImage[5];
    private static final int[] TEXTURA_HOSSZAK = new int[] { 100, 200, 300, 400, 500 };

	private static final int[] FELEZETT_HOSSZAK = new int[] { 50, 100, 150, 200, 250 };
    
    private static final BufferedImage[] SO_TEXTURAK = new BufferedImage[5];
    private static final BufferedImage[] KO_TEXTURAK = new BufferedImage[5];
    
    // 5 kulonbozo hossz (0-4 index) x 10 kulonbozo intenzitasi szint (0-9 index)
    private static final BufferedImage[][] HO_TEXTURAK = new BufferedImage[5][10];
    private static final BufferedImage[][] JEG_TEXTURAK = new BufferedImage[5][10];

    static {
        try {
            // 1. Alap beton betoltese
            for (int i = 0; i < 5; i++) {
                ASZFALT_TEXTURAK[i] = ImageIO.read(new File("Asset/beton" + TEXTURA_HOSSZAK[i] + ".png"));
            }

            // 2. So es Zuzottko (gravel/ko) betoltese (so1..so5, ko1..ko5)
            for (int i = 0; i < 5; i++) {
                int sorszam = i + 1;
                SO_TEXTURAK[i] = ImageIO.read(new File("Asset/savra/so" + sorszam + ".png"));
                KO_TEXTURAK[i] = ImageIO.read(new File("Asset/savra/ko" + sorszam + ".png"));
            }

            // 3. Ho es Jeg betoltese matricaba (pl: ho10..ho59)
            for (int h = 0; h < 5; h++) {
                int hosszKod = h + 1;
                for (int sz = 0; sz < 10; sz++) {
                    HO_TEXTURAK[h][sz] = ImageIO.read(new File("Asset/savra/ho" + hosszKod + sz + ".png"));
                    JEG_TEXTURAK[h][sz] = ImageIO.read(new File("Asset/savra/jeg" + hosszKod + sz + ".png"));
                }
            }
        } catch (Exception e) {
            // Hiba eseten az erintett kep null marad, a program fut tovabb
        }
    }

	public Point2D startPos;
	public Point2D endPos;

	private Lane modelLane;

	public LaneView(Lane lane, Point2D startPos, Point2D endPos) {
		this.modelLane = lane;
		this.startPos = startPos;
		this.endPos = endPos;
	}

	public Lane getLane() {
		return modelLane;
	}

	public void paint(Graphics2D g) {
		double startX = startPos.getX();
		double startY = startPos.getY();
		double endX = endPos.getX();
		double endY = endPos.getY();

		double dx = endX - startX;
		double dy = endY - startY;
		double hossza = Math.sqrt(dx * dx + dy * dy);
		if (hossza == 0) return;

		double szog = Math.atan2(dy, dx);

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

		g2.translate(startX, startY);
		g2.rotate(szog);

		int h = (int) Math.round(hossza);

		// --- 1. ALAP ASZFALT REGEG KIRAJZOLASA ---
        BufferedImage kivalasztottAszfalt = valasztLegkozelebbiTexturat(h);
        if (kivalasztottAszfalt != null) {
            g2.drawImage(kivalasztottAszfalt, 1, 1, h - 1, MainPanel.LANE_WIDTH - 1, null);
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect(1, 1, h - 1, MainPanel.LANE_WIDTH - 1);
        }

        // --- DINAMIKUS MERET- ES ALLAPOT-SZAMITASOK ---
        // Kiszamoljuk a felut teruletenek pontos pixelfelbontasat
        int felUtHossz = (int) Math.ceil(h / 2.0);
        int hosszIndex = valasztLegkozelebbiFelezettIndex(felUtHossz);

        // Lekerjuk a modell szintű adatokat a Lane objektumbol
        double snowLevel = modelLane.getSnow();
        double iceLevel = modelLane.getIce();
        boolean hasSalt = modelLane.hasSalt();
        boolean hasGravel = modelLane.hasGravel();

        // Szinszint meghatarozo index (0-9 koze szoritva)
        int snowIntexSzint = Math.min(9, (int) (snowLevel / 10.0));
        int iceIndexSzint = Math.min(9, (int) (iceLevel / 10.0));

        // --- 2. HO REGEG KIRAJZOLASA (INDULO OLDAL -> BAL FELT) ---
        if (snowLevel > 0) {
            BufferedImage imgHo = HO_TEXTURAK[hosszIndex][snowIntexSzint];
            if (imgHo != null) {
                g2.drawImage(imgHo, 1, 1, felUtHossz, MainPanel.LANE_WIDTH - 1, null);
            }
        }

        // --- 3. JEG REGEG KIRAJZOLASA (CEL OLDAL -> JOBB FELT) ---
        if (iceLevel > 0) {
            BufferedImage imgJeg = JEG_TEXTURAK[hosszIndex][iceIndexSzint];
            if (imgJeg != null) {
                // Eltoljuk az x koordinatat a sáv feletol, es kihuzzuk a sáv vegeig
                g2.drawImage(imgJeg, h - felUtHossz, 1, felUtHossz, MainPanel.LANE_WIDTH - 1, null);
            }
        }

        // --- 4. SO REGEG KIRAJZOLASA LEGUPULRE (INDULO OLDAL -> BAL FELT) ---
        if (hasSalt) {
            BufferedImage imgSo = SO_TEXTURAK[hosszIndex];
            if (imgSo != null) {
                g2.drawImage(imgSo, 1, 1, felUtHossz, MainPanel.LANE_WIDTH - 1, null);
            }
        }

        // --- 5. ZUZOTTKO REGEG KIRAJZOLASA LEGUPULRE (CEL OLDAL -> JOBB FELT) ---
        if (hasGravel) {
            BufferedImage imgKo = KO_TEXTURAK[hosszIndex];
            if (imgKo != null) {
                g2.drawImage(imgKo, h - felUtHossz, 1, felUtHossz, MainPanel.LANE_WIDTH - 1, null);
            }
        }

        // --- 6. SZEGELYKERET MEGRAJZOLASA (VÁLTOZATLAN) ---
        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(SZEGELY_VASTAGSAG));
        g2.drawRect(0, 0, h, MainPanel.LANE_WIDTH);

        g2.dispose();
    }

    private BufferedImage valasztLegkozelebbiTexturat(int aktualisHossz) {
        int legjobbIndex = 0;
        int legkisebbKulonbseg = Math.abs(aktualisHossz - TEXTURA_HOSSZAK[0]);

        for (int i = 1; i < TEXTURA_HOSSZAK.length; i++) {
            int aktualisKulonbseg = Math.abs(aktualisHossz - TEXTURA_HOSSZAK[i]);
            if (aktualisKulonbseg < legkisebbKulonbseg) {
                legkisebbKulonbseg = aktualisKulonbseg;
                legjobbIndex = i;
            }
        }
        return ASZFALT_TEXTURAK[legjobbIndex];
    }

    /**
     * Segedfuggveny, ami megkeresi a felut pixelhosszahoz legkozelebb allo 
     * felezett texturameret (50, 100, 150, 200, 250) matrix-indexet (0-4).
     */
    private int valasztLegkozelebbiFelezettIndex(int felUtHossz) {
        int legjobbIndex = 0;
        int legkisebbKulonbseg = Math.abs(felUtHossz - FELEZETT_HOSSZAK[0]);

        for (int i = 1; i < FELEZETT_HOSSZAK.length; i++) {
            int aktualisKulonbseg = Math.abs(felUtHossz - FELEZETT_HOSSZAK[i]);
            if (aktualisKulonbseg < legkisebbKulonbseg) {
                legkisebbKulonbseg = aktualisKulonbseg;
                legjobbIndex = i;
            }
        }
        return legjobbIndex;
    }
}
