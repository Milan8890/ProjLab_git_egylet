package entities;

import java.lang.runtime.SwitchBootstraps;
import java.util.Arrays;
import java.util.List;

import playground.City;
import playground.Crossing;
import playground.City;
import playground.Path;
import playground.Lane;

/**
 * A gépi irányítású járművek, home és work közt közlekednek
 * <p>
 * 
 * Felelősség <br>
 * A legrövidebb út újraterveztetése, ha blokkolva lenne egy sáv. Elakad, ha túl
 * magas a
 * hó. Kezdeményezi a csúszást. Ütközés kezelése. Nyilvántartja, hogy a
 * jelenlegi úton
 * hol helyezkedik el. Letaposás végzése. <br>
 * 
 * Ősosztályok <br>
 * Vechicle
 */
public class Car extends Vehicle {
	/**
	 * A gépjármű otthona.
	 */
	Crossing home;
	/**
	 * A gépjármű munkahelye.
	 */
	Crossing work;
	/**
	 * Jelzi, hogy a gépjármű hazafelé tart-e.
	 */
	boolean isGoingHome;

	/**
	 * Konstruktor, beállítja az autó otthonát és munkahelyét.
	 * 
	 * @param home
	 * @param work
	 */
	public Car(Crossing home, Crossing work) {
		super(home);
		this.home = home;
		this.work = work;
		this.isGoingHome = false;
	}

	/**
	 * Ha elérte célpontját, megváltoztatja a célpontját a másik kereszteződésre
	 * (ha hazaért a munkahelyre, ha munkába ért akkor haza), és kér egy új
	 * útvonalat a Citytől a pillanatnyi helyétől az új cél felé.
	 */
	@Override
	public void reachedCrossing() {
		super.reachedCrossing();

		if (isGoingHome && this.lastCrossing.equals(home)) {

			isGoingHome = false;
			this.path = City.shortestPathFrom(this.lastCrossing, work);

		} else if (!isGoingHome && this.lastCrossing.equals(work)) {

			isGoingHome = true;
			this.path = City.shortestPathFrom(this.lastCrossing, home);
		}
	}

	/**
	 * Meghívja az őse stepFollowPath()-jét, és ha ezután is
	 * kereszteződésben van (azaz nem tudott ráhajtani a sávra ami meg volt adva),
	 * akkor a
	 * City-től kér egy új útvonalat, abból a kereszteződésből amiben van, abba ami
	 * a
	 * célpontja.
	 * 
	 * @return Igaz, ha az ősosztály hívása sikeres volt, egyébként hamis.
	 */
	@Override
	protected boolean stepFollowPath() {
		boolean success = super.stepFollowPath();

		if (!success && isInCrossing()) {
			Crossing target = isGoingHome ? home : work;
			this.path = City.shortestPathFrom(this.lastCrossing, target);
		}
		return success;
	}

	/**
     * Kezeli az ütközés utáni állapotot az ősosztály hívásával.
     * 
     * @return Igaz, ha az autó már nincs mozgásképtelen állapotban, egyébként hamis.
     */
	@Override
	protected boolean stepWaitAfterCrash() {
        return super.stepWaitAfterCrash();
    }

	/**
     * Beállítja a helyzetét az otthonára, és elindítja a munkahelye felé.
     */
    @Override
    protected void revive() {
        this.lastCrossing = home;
        this.isGoingHome = false;
        this.currentLane = null;
        this.path.clear();
    }

	/**
     * Ellenőrzi a hóhelyzetet az aktuális sávon.
     * Ha a saját sávjában túl magas a hó, megpróbál egy másik, járható sávot keresni 
     * ugyanazon az úton. Ha talál ilyet, sávot vált. Ha az út összes sávja járhatatlan, 
     * a jármű elakad.
     * 
     * @return Igaz, ha a jármű tud tovább haladni, egyébként hamis.
     */
    @Override
    protected boolean stepStuckInSnow() {
        if (currentLane.getSnow() <= 10.0) {
            this.isStuck = false;
            return true;
        }

        List<Lane> allLanes = currentLane.getRoad().getLanes();

        for (int i = 0; i < allLanes.size(); i++) {
            Lane l = allLanes.get(i);
            
            if (l.getSnow() <= 10.0) {
                currentLane.removeVehicle(this);
                this.currentLane = l;
                this.currentLane.addVehicle(this);
                
                this.isStuck = false;
                return true;
            }
        }

        this.isStuck = true;
        return false;
    }

	/**
     * Kezeli a jeges úton való megcsúszást.
     * Ha túl sok a jég és nem védi vagy hófedte a zúzalék, egy megadott eséllyel 
     * az autó megcsúszik, amit jelez az útnak.
     * 
     * @return Hamis, ha a megcsúszás miatt ütközés történt, egyébként igaz.
     */
    @Override
    protected boolean stepSlipOnIce() {
        if (currentLane.getIce() > 5.0) {
            
            if (currentLane.hasGravel() && currentLane.getSnow() <= 0.0) {
                    return true;
            }

            if (Math.random() < 0.8) {
                currentLane.getRoad().crashVehicle(this);

                if (this.isCrashed) {
                    return false;
                }
            }
        }

        return true;
    }
	
	/**
     * Megvizsgálja, hogy a jelenlegi sávon található-e elakadt jármű.
     * <p>
     * Ha a sávon bármelyik jármű isStuck állapotban van, az az egész sávot 
     * blokkolja. Ekkor az autó újratervezi az útvonalát és várakozik.
     * 
     * @return Hamis, ha van elakadt jármű a sávon, egyébként igaz.
     */
    @Override
    protected boolean stepWaitBecauseOfStuck() {
        if (currentLane.hasStuckVehicle()) {
            Crossing target = isGoingHome ? home : work;
            this.path = City.shortestPathFrom(this.lastCrossing, target);
            
            return false;
        }

        return true;
    }
}