package equipment;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Eltárolja a vehető fejet és árát.
 */
public class HeadListing {
	/**
	 * A megvásárolható fej.
	 */
	Head head;
	/**
	 * A fej ára.
	 */
	int price;

	/**
	 * Konstruktor
	 * 
	 * @param h     a fej amit meg lehet venni
	 * @param price az ár amit ki kell fizetni érte
	 */
	public HeadListing(Head h, int price) {
		head = h;
		this.price = price;
		Logger.getGlobal().log(Level.INFO, "[Obj] created", this);
	}

    /**
     * visszaadja az árucikkben szereplő fejet
     * 
     * @return a megvehető fej
     */
    public Head getHead() {
        Logger.getGlobal().log(Level.INFO, "[Obj]'s head is [Obj]" , new Object[] {this, head});

        return head;       
    }
    
    /**
     * visszaadja az árucikkben szereplő fej árát
     * 
     * @return a fej ára
     */
    public int getPrice(){
        Logger.getGlobal().log(Level.INFO, "[Obj]'s price is " + price + "$" , new Object[] {this});
        return price;
    }
}