package equipment;

import java.util.Arrays;


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
     * @param h a fej amit meg lehet venni
     * @param price az ár amit ki kell fizetni érte
     */
    public HeadListing(Head h, int price){
        Skeleton.initSettingUpObjectStart(this);
        Skeleton.initSettingUpObjectEnd();
    }

    /**
     * visszaadja az árucikkben szereplő fejet
     * 
     * @return a megvehető fej
     */
    public Head getHead() {
        return head;       
    }
    
    /**
     * visszaadja az árucikkben szereplő fej árát
     * 
     * @return a fej ára
     */
    public int getPrice(){
        return price;
    }
}