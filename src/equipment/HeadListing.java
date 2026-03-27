package equipment;

import main.Skeleton;

/**
 * Eltárolja a vehető fejet és árát.
 */
public class HeadListing {
    Head head;
    
    /**
     * Konstruktor
     * 
     * @param h a fej amit meg lehet venni
     * @param price az ár amit ki kell fizetni érte
     */
    HeadListing(Head h, int price){
        head=h;
    }
    /**
     * visszaadja az árucikkben szereplő fejet
     * 
     * @return a megvehető fej
     */
    public Head getHead() {
        Skeleton.logFunctionStart(head, "getHead", null);
        Skeleton.logFunctionEnd();
        return head;
    }
    /**
     * visszaadja az árucikkben szereplő fej árát
     * 
     * @return a fej ára
     */
    public int getPrice(){
        Skeleton.logFunctionStart(head, "getPrice", null);
        int ret = Skeleton.questionValue("Mennyibe kerül a headListing?");
        Skeleton.logFunctionEnd();
        return ret;
    }
}