package entities;

import java.util.Arrays;

import main.Skeleton;
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
  * A legrövidebb út újraterveztetése, ha blokkolva lenne egy sáv. Elakad, ha túl magas a 
  * hó. Kezdeményezi a csúszást. Ütközés kezelése. Nyilvántartja, hogy a jelenlegi úton 
  * hol helyezkedik el. Letaposás végzése. <br>
  * 
  * Ősosztályok <br>
  * Vechicle
  */
public class Car extends Vehicle {
    Crossing home;
    Crossing work;

    /**
     * Konstruktor
     * @param home egy kereszteződés ami az otthona
     * @param work egy kereszteződés ami a munkahelye
     */
    public Car(Crossing home, Crossing work) {
        Skeleton.initSettingUpObjectStart(this);
        this.home = home;
        this.work = work;
        Skeleton.initSettingUpObjectEnd();
    }

    /**
     * Az autó léptetéskor történő lehetőségeit kezeli:
     * -Kereszteződésnél kezeli az új sávba hajtás lehetőségét az ottani hó magasságtól függően.
     * -Útnál pedig kezeli a túl magas hó miatti elakadást, a takarítás miatti felszabadítást.
     * -Kezeli még az elcsúszást jeges úton, az emiatti baleseteket
     * -Kezeli ha útról kereszteződésbe ér, és azt ha ez éppen a munkahelye, vagy otthona az autónak.
     */
    public void onTick() {
        Skeleton.logFunctionStart(this, "onTick", null);

        //először döntsük el kereszteződésből vagy útról induljon az eset
        int whereAnswer = Skeleton.questionMultiple("Hol tartózkodik az autó?", Arrays.asList("kereszteződés", "út"));
        if(whereAnswer == 1) {

            //Ha kereszteződésről indul, akkor tovább szeretne hajtani az autó, ehhez az útvonaltervéből elkéri a következő sávot, ahová a kereszteződésből behajthat
            System.out.println("Az autó lekéri a következő sávot az útvonalából.");
            Lane nextLane = path.pop();

            //Kérdezzük meg a sávtól túl magas -e ott a hó
            double snowCm = nextLane.getSnow();
            int snowAnswer = Skeleton.questionMultiple("Elakad az autó " + snowCm + " cm hóban?",Arrays.asList("Igen", "Nem"));
            if(snowAnswer == 1) {
                System.out.println("A hó túl magas a sávba lépéshez, az autó a kereszteződésben marad.");
            } 
            else {
                //Küldjük el a sávnak az autót, hogy tegye fel magára.
                nextLane.addVehicle(this);
            }
        }

        else {
            //Ha az úton tartózkodik előszőr nézzük végig a Vechicle ősosztálybeli járművekre vonatkozó általános léptetést
            super.onTick();

            //Nézzük meg a saját sávunkban most túl magas -e a hó réteg
            int snowLevelAnswer = Skeleton.questionMultiple("Túl magas -e a hóréteg a közlekedéshez?", Arrays.asList("igen", "Nem"));

            //Ha túl magas lenne a mi sávunkban a hó, nézzük meg a szomszédos sávokat, át tudunk -e oda hajtani
            if(snowLevelAnswer == 1 && Skeleton.questionMultiple("Tud -e sávot váltani?", Arrays.asList("igen", "Nem")) == 1) {

                //Ha nem lehetséges a sávváltás sem, akkor beragadt az autónk a hóba
                System.out.println("Az autó beragadva ott marad");
            }
            else {
                //Ha vagy a saját sávunk vagy a szomszédos sávok le lettek tisztítva, akkor korábbi elakadást már fel szabadíthatunk
                int carStuckAnswer = Skeleton.questionMultiple("El volt -e akadva az autó?", Arrays.asList("igen", "Nem"));
                if(carStuckAnswer == 1) {
                    System.out.println("Az autó sikeresen felszabadul");
                }
                
                //Ha éppen jeges úton tartózkodna az autó döntsük el, hogy elcsússzon -e
                int skidAnswer = Skeleton.questionMultiple("Megcsússzon -e az autó a jeges úton?", Arrays.asList("igen", "Nem"));
                if(skidAnswer == 1) {
                    System.out.println("Az autó megcsúszott");

                    //Ha elcsúszott, döntsük el tartózkodott -e másik jármű az úton, akivel ütközik
                    int moreVechicleAnswer = Skeleton.questionMultiple("Van -e másik jármű az úton?", Arrays.asList("igen", "Nem"));
                    if(moreVechicleAnswer == 1) {

                        //Ebben az esetben ütköztek, és lerobbannak
                        System.out.println("Az autó ütközött, emiatt lerobbant");
                        this.crashed(5);
                    }        
                }
                else {
                    //Ha az autónk megúszta a magas havat, és az elcsúszást is és tovább haladhatna, döntsünk kereszteződésbe ért -e
                    int intoCrossingAnswer = Skeleton.questionMultiple("Kereszteződésbe ért -e?", Arrays.asList("igen", "Nem"));
                    if(intoCrossingAnswer == 1) {

                        //Kereszteződésbe éréskor végezzük el erre a sávra a hó letaposását
                        System.out.println("Az autó elhagyja a sávot, ezért letapossa a havat.");
                        currentLane.trampleSnow();

                        //Döntsük el a kereszteződés amibe értünk a célja volt -e az autónak
                        int workAnswer = Skeleton.questionMultiple("Munkahelyedre érkeztél -e?", Arrays.asList("igen", "Nem"));
                        if(workAnswer == 1) {

                            //Ha elérte a célját új útvonalat kell kérni neki
                            System.out.println("Az autó megérkezett a munkahelyére. Irányváltás: mostantól hazafelé tart.");
                            Path path = City.shortestPathFrom(work, home);
                        }
                    }
                    else {
                        //Ha nem történik semmi az autóval ezen a léptetésen
                        System.out.println("Az autó haladt előre a sávjában.");
                    }
                }
            }

        }
        Skeleton.logFunctionEnd();
    }
}