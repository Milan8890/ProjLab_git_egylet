package graphics;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
import user.BusDriver;
import user.Cleaner;

public class MainPanel extends JFrame {
	private List<Cleaner> cleaners;
	private List<BusDriver> busDrivers;
	private boolean isExtendingPath;

	private Cleaner selectedCleaner;
	private BusDriver selectedBusDriver;
	private Snowplower selectedSnowplower;

	private Crossing selectedCrossing;

	private List<CrossingView> crossingViews = new ArrayList<>();
	private List<RoadView> roadViews = new ArrayList<>();
	private List<LaneView> laneViews = new ArrayList<>();
	private List<SnowplowerView> snowplowerViews = new ArrayList<>();
	private List<BusView> busViews = new ArrayList<>();
	private List<CarView> carViews = new ArrayList<>();

	private JComboBox playerSelectorComboBox;
	private JTextField playerData;

	private SnowplowerPanel snowplowerPanel;
	private BusPanel busPanel;

	private MapPanel mapPanel;

	public MainPanel() {
		NewMain.notdone("MainPanel konstruktor");
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

	public List<CrossingView> getCrossingViews() {
		NewMain.notdone(
				"MainPanel getter/setter at (raw function header):\npublic List<CrossingView> getCrossingViews() {");
		return crossingViews;
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

	// Szerintem nem kell az update, mert csak megoldja a repaint. És ezeket kéne
	// újradefiniálni az egyes panelekben.

	// public void update() {
	// // Ahhoz meg kéne írni a játékosválasztást
	// // Ha igen, akkor nincs nullozás
	// if (snowplowerPanel != null)
	// snowplowerPanel.update();

	// NewMain.notdone("MainPanel update");
	// }
}
