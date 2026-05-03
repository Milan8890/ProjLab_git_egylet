package main;

import java.util.logging.Logger;

public class App {

	public static void main(String[] args) throws Exception {
		Proto proto = new Proto(World.players);

		// Handler beállítása
		Logger.getGlobal().setUseParentHandlers(false);
		OwnHandler ownHandler = new OwnHandler(proto.objectMap);
		Logger.getGlobal().addHandler(ownHandler);

		// Parancsok beolvasása
		proto.readCommandsFromCommandLine();
	}
}