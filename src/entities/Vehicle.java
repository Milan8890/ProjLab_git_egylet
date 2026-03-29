package entities;

import main.Skeleton;
import java.util.Arrays;

import playground.City;
import playground.Crossing;
import playground.Lane;
import playground.Path;

/**
 * A járművek absztrakt ősosztálya.
 * <p>
 * 
 * Felelősség <br>
 * Automatikusan követi az utat. Ha túl nagy lenne a hóréteg, elakad. Jeges úton
 * egy adott eséllyel megcsúszik, ezt jelzi az útnak.
 */
public abstract class Vehicle {
	Crossing lastCrossing;
	Lane currentLane;
	Path path;

	//Ez nem kell
	int revTimer;

	public void onTick() {
		Skeleton.logFunctionStart(this, "onTick", null);

		//Döntsük el, hogy korábbi baleset miatt várakozik -e
		int crashAnswer = Skeleton.questionMultiple("Ütközés után várakozik -e?", Arrays.asList("igen", "Nem"));
		if (crashAnswer == 1) {

			//Ha igen csökkentsük a várakozásának hátralevő idejét
			System.out.println("revTimer csökkentésre kerül");

			//Nézzük meg a csökkentéssel lejárt -e a várakozási ideje
			int timerAnswer = Skeleton.questionMultiple("Lejárt -e a revTimer ideje?", Arrays.asList("igen", "Nem"));
			if (timerAnswer == 1) {

				//Ha lejárt a várakozási ideje szabadítsuk fel a baleset alól
				System.out.println("Felszabadítjuk a balesetből");
			}
		}
		else {
			//Ha nem volt ütközése tovább haladhat
			System.out.println("Lane progresst nő");
		}

		Skeleton.logFunctionEnd();
	}

	/**
	 * Meghívódik, ha a jármű beleütközik valamibe.
	 * 
	 * @param r Mennyi ideig marad mozgásképtelen (revTimer)
	 */
	public void crashed(int r) {
		// Az r itt a revTimer értéke
		Skeleton.logFunctionStart(this, "crashed", Arrays.asList(String.valueOf(r)));

		System.out.println("Sikeres ütközés regisztrálva. Újraéledési idő: " + r);

		Skeleton.logFunctionEnd();
	}


	/**
     * Meghívódik, ha egy másik jármű megy neki ennek a járműnek.
     * @param r A várakozási idő (revTimer)
     */
    public void crashedInto(int r) {
        Skeleton.logFunctionStart(this, "crashedInto", Arrays.asList(String.valueOf(r)));
        
        System.out.println("A járműbe ütközött egy másik itt fog ragadni ennyi Időre: " + r);
        
        Skeleton.logFunctionEnd();
    }

	/**
     * Visszaadja, hogy a jármű jelenleg kereszteződésben várakozik-e.
     * <p>
     * Ha a járműnek nincs kijelölt sávja (currentLane értéke null), 
     * akkor egy kereszteződésben tartózkodik.
     * * @return true, ha kereszteződésben van, egyébként false.
     */
    public boolean isInCrossing() {
        Skeleton.logFunctionStart(this, "isInCrossing", null);
        
        int crossingAnswer = Skeleton.questionMultiple("Kereszteződésben van -e?", Arrays.asList("igen", "nem"));
        boolean result = (crossingAnswer == 1);
        if (crossingAnswer == 1) {
            System.out.println("A jármű jelenleg kereszteződésben tartózkodik");
        } else {
            System.out.println("A jármű egy sávon halad");
        }

        Skeleton.logFunctionEnd();
        return result;
    }

	/**
     * Meghosszabbítja a jármű útvonalát egy új sávval.
     * <p>
     * A jármű a feladatot továbbítja a saját Path objektumának, 
     * amely elvégzi a sáv tényleges hozzáadását.
     * * @param l Az új sáv (Lane), amivel bővíteni szeretnénk az útvonalat.
     */
	public void extendPath(Lane l) {
    	Skeleton.logFunctionStart(this, "extendPath", Arrays.asList("l"));
    
    	// Itt a jármű meghívja a Path-et ezzel a lane-el
    	path.extendPath(l); 
    
    	Skeleton.logFunctionEnd();
	}
}