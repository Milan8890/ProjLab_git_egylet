package equipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.Snowplower;
import equipment.heads.*;
import main.Skeleton;
/**
 * Fejtároló egy hókotróhoz
 * <p>
 * 
 * Felelősség <br>
 * A vehető fejek nyilvántartása, megvett fejek tárolása, aktív fej nyilvántartása. Új fej vételének lebonyolítása.
 */
public class HeadInventory {
    Snowplower owner;

    /**
     * Konstruktor
     * 
     * @param owner a Hókotró aminek tároljuk a fejeit
     * @param ownedHead a fej amit a Hókotró már birtokol, ez lesz az aktív fej is
     */
    private HeadInventory(Snowplower owner, Head ownedHead){
        Skeleton.initSettingUpObjectStart(this);
        this.owner = owner;

    }
    /**
     * Konstruál egy Hókotróhoz egy fejtárolót aminek jégtörő feje van
     * 
     * @param owner a Hókotró aminek tároljuk a fejeit
     * @return HeadInventory ami a helyes elemeket tartalmazza
     */
    public static HeadInventory createWithBreaker(Snowplower owner){
        HeadInventory ret = new HeadInventory(owner, new Breaker(owner));
        new ArrayList<>(Arrays.asList( 
            new HeadListing(new Sweeper(owner) , 0), 
            new HeadListing(new Ejector(owner), 0),
            new HeadListing(new SaltSpreader(owner), 0),
            new HeadListing(new Dragon(owner), 0)));
        Skeleton.initSettingUpObjectEnd();
        return ret;

    }
    /**
     * Konstruál egy Hókotróhoz egy fejtárolót aminek hányó feje van
     * 
     * @param owner a Hókotró aminek tároljuk a fejeit
     * @return HeadInventory ami a helyes elemeket tartalmazza
     */
    public static HeadInventory createWithEjector(Snowplower owner) {
        HeadInventory ret = new HeadInventory(owner, new Ejector(owner));
        new ArrayList<>(Arrays.asList( 
            new HeadListing(new Sweeper(owner) , 0), 
            new HeadListing(new Breaker(owner), 0),
            new HeadListing(new SaltSpreader(owner), 0),
            new HeadListing(new Dragon(owner), 0)));
        Skeleton.initSettingUpObjectEnd();
        return ret;
    }
    /**
     * visszaadja a fejtárolóban jelenleg aktív fejet
     * 
     * @return a jelenleg aktív fej
     */
    public Head getActiveHead() {
        Skeleton.logFunctionStart(this, "getActiveHead", null);
        int answer = Skeleton.questionMultiple("Milyen fej van felszerelve?", Arrays.asList("Jégtörő", "Hányó", "Seprő", "Sószóró", "Sárkány"));
        switch (answer) {
            case 1:
                Skeleton.logFunctionEnd();
                return new Breaker(owner);
            case 2:
                Skeleton.logFunctionEnd();
                return new Ejector(owner);
            case 3:
                Skeleton.logFunctionEnd();
                return new Sweeper(owner);
            case 4:
                Skeleton.logFunctionEnd();
                return new SaltSpreader(owner);
            case 5:
                Skeleton.logFunctionEnd();
                return new Dragon(owner);
        
            default:
                Skeleton.logFunctionEnd();
                return null;
        }
    }

    /**
     * visszadja a fejtárolóban jelenleg megvehető Listingeket
     * @return a jelenleg megvehető Listingek
     */
    public List<HeadListing> getShop() {
        Skeleton.logFunctionStart(this, "getShop", null);

        int listingCount = Skeleton.questionValue("Hány Listingje van?");
        List<HeadListing> ret = new ArrayList<>();
        for (int i = 0; i < listingCount; i++) {
            int headType = Skeleton.questionMultiple("Milyen fej Listingje?", Arrays.asList("Jégtörő", "Hányó", "Seprő", "Sószóró", "Sárkány"));
            int price = Skeleton.questionValue("Mennyibe kerül a fej?");
            switch (headType) {
                case 1:
                    ret.add(new HeadListing(new Breaker(owner), price));
                    break;
                case 2:
                    ret.add(new HeadListing(new Ejector(owner), price));
                    break;
                case 3:
                    ret.add(new HeadListing(new Sweeper(owner), price));
                    break;
                case 4:
                    ret.add(new HeadListing(new SaltSpreader(owner), price));
                    break;
                case 5:
                    ret.add(new HeadListing(new Dragon(owner), price));
                    break;
            
                default:
                    break;
            }
        }
        Skeleton.logFunctionEnd();
        return ret;
    }
    /**
     * Átváltja az aktív fejet a következőre a fejtárolóban
     */
    public void cycleActiveHead() {
        Skeleton.logFunctionStart(this, "cycleActiveHead", null);

        boolean answer = owner.isInCrossing();
        if(!answer){
            Skeleton.logFunctionEnd();
            return;
        }
        int headType = Skeleton.questionMultiple("Milyen fej a következő fej?", Arrays.asList("Jégtörő", "Hányó", "Seprő", "Sószóró", "Sárkány"));
        switch (headType) {
            case 1:
                Skeleton.logString("Jelenleg aktív fej: Jégtörő");
                break;
            case 2:
                Skeleton.logString("Jelenleg aktív fej: Hányó");
                break;
            case 3:
                Skeleton.logString("Jelenleg aktív fej: Seprő");
                break;
            case 4:
                Skeleton.logString("Jelenleg aktív fej: Sószóró");
                break;
            case 5:
                Skeleton.logString("Jelenleg aktív fej: Sárkány");
                break;
        
            default:
                break;
        }
        Skeleton.logFunctionEnd();
    }
    /**
     * Megvásárol egy fejet a listing alapján, ha van elég pénze a játékosnak
     * @param listing a megvásárolni kívánt fej listingje
     * @return sikeres volt-e a vásárlás
     */
    public boolean buyListing(HeadListing listing){
        Skeleton.logFunctionStart(this, "buyListing", Arrays.asList(Skeleton.createNameOfObject(listing)));
        Skeleton.logFunctionEnd();
        return owner.getCleaner().removeMoney(listing.getPrice());
    }

}