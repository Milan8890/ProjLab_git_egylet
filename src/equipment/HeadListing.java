package equipment;

import java.util.Arrays;

import main.Skeleton;

/**
 * Eltárolja a vehető fejet és árát.
 */
public class HeadListing {
    
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
        Skeleton.logFunctionStart(this, "getHead", null);
        int answer = Skeleton.questionMultiple("Milyen fej van felszerelve?", Arrays.asList("Jégtörő", "Hányó", "Seprő", "Sószóró", "Sárkány"));
        switch (answer) {
            case 1:
                Skeleton.logFunctionEnd();
                return Skeleton.Market.breaker;
            case 2:
                Skeleton.logFunctionEnd();
                return Skeleton.Market.ejector;
            case 3:
                Skeleton.logFunctionEnd();
                return Skeleton.Market.sweeper;
            case 4:
                Skeleton.logFunctionEnd();
                return Skeleton.Market.saltSpreader;
            default:
                Skeleton.logFunctionEnd();
                return Skeleton.Market.dragon;
        }
    }
    
    /**
     * visszaadja az árucikkben szereplő fej árát
     * 
     * @return a fej ára
     */
    public int getPrice(){
        Skeleton.logFunctionStart(this, "getPrice", null);
        int ret = Skeleton.questionValue("Mennyibe kerül a headListing?");
        Skeleton.logFunctionEnd();
        return ret;
    }
}