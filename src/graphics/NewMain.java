package graphics;

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

	public static void main(String[] args) {
		// Csak egy új main, hogy innen lehessen dolgozni (nincs sok értelme)
	}
}
