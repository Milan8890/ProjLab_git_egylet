package equipment;

import java.util.Arrays;

import entities.Snowplower;
import equipment.heads.*;
import main.Skeleton;
import user.Cleaner;

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
                return new Breaker(null); //TODO null helyére kell a Snowplower
            case 2:
                Skeleton.logFunctionEnd();
                return new Ejector(null);//TODO null helyére kell a Snowplower
            case 3:
                Skeleton.logFunctionEnd();
                return new Sweeper(null);//TODO null helyére kell a Snowplower
            case 4:
                Skeleton.logFunctionEnd();
                return new SaltSpreader(null);//TODO null helyére kell a Snowplower
            default:
                Skeleton.logFunctionEnd();
                return new Dragon(null);//TODO null helyére kell a Snowplower
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