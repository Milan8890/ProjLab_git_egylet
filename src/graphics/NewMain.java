package graphics;



import java.util.logging.Logger;

import main.OwnHandler;
import main.Proto;
import main.World;

public class NewMain {
	static boolean PLSRUN = true;

	// Ezt a függvényt hívjuk amikor valami még nincs kész
	// Így ki lehet kapcsolni, hogy hibákat dobjon, de mégis le lehet ellenőrizni,
	// hogy minden kész van-e
	// Így is írja, ha valami nincs kész, csak nem dob hibát.
	public static void notdone(String s) {
		if (!PLSRUN) {
			throw new UnsupportedOperationException("Még nincs kész: " + s);
		} else {
			System.out.println("Soft nincs kész: " + s);
		}
	}

	public static void notdone() {
		notdone("");
	}


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
