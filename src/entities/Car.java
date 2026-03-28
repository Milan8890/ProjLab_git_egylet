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
  * home: Az egyik kereszteződés, tudja az autó, hogy hova kell hazamennie <br>
  * work: A másik kereszteződés, tudja az autó, hova kell dolgozni mennie 
  * 
  * Ősosztályok <br>
  * Vechicle
  */
public class Car extends Vehicle {
    Crossing home;
    Crossing work;
    boolean isGoingHome;

    /**
     * Konstruktor
     * @param home egy kereszteződés ami az otthona
     * @param work egy kereszteződés ami a munkahelye
     * @param isGoingHome hazafelé tart-e
     */
    public Car(Crossing home, Crossing work) {
        this.home = home;
        this.work = work;
    }

    public void onTick() {
        Skeleton.logFunctionStart(this, "onTick", null);

        int answer = Skeleton.questionMultiple("Hol tartózkodik az autó?", Arrays.asList("kereszteződés", "út"));
        if(answer == 1) {
            System.out.println("Az autó lekéri a következő sávot az útvonalából.");
            Lane nextLane = path.pop();

            //Kérdezzük meg a sávtól túl magas -e ott a hó
            int snowLevel = nextLane.getSnow();
            if(snowLevel == 1) {
                //Küldjük el a sávnak az autót, hogy tegye fel magára.
                nextLane.addVehicle(this);
            } 
            else {
                System.out.println("A hó túl magas a sávba lépéshez, az autó a kereszteződésben marad.");
            }
        }

        else {
            super.onTick();

            int answer1 = Skeleton.questionMultiple("Túl magas -e a hóréteg a közlekedéshez?", Arrays.asList("igen", "Nem"));
            int answer2 = 2; //Csak akkor kérdezze az autót a sávváltásról, ha túl magas a hó a saját sávjában
            if (answer1 == 1) answer2 = Skeleton.questionMultiple("Tud -e sávot váltani?", Arrays.asList("igen", "Nem"));
            if(answer1 == 1 && answer2 == 1) {
                System.out.println("Az autó beragadva ott marad");
            }
            else {
                answer = Skeleton.questionMultiple("El volt -e akadva az autó?", Arrays.asList("igen", "Nem"));
                if(answer == 1) {
                    System.out.println("Az autó sikeresen felszabadul");
                }

                int answer4 = Skeleton.questionMultiple("Megcsússzon -e az autó?", Arrays.asList("igen", "Nem"));
                if(answer4 == 1) {
                    System.out.println("Az autó megcsúszott");

                    int answer5 = Skeleton.questionMultiple("Van -e másik jármű az úton?", Arrays.asList("igen", "Nem"));
                    if(answer5 == 1) {
                        System.out.println("Az autó ütközött, emiatt lerobbant");
                        this.crashed(5);
                    }        
                }

                answer = Skeleton.questionMultiple("Kereszteződésbe ért -e?", Arrays.asList("igen", "Nem"));
                if(answer == 1) {
                    int answer10 = Skeleton.questionMultiple("Munkahelyedre érkeztél -e?", Arrays.asList("igen", "Nem"));
                    if(answer10 == 1) {
                        System.out.println("Az autó megérkezett a munkahelyére. Irányváltás: mostantól hazafelé tart.");
                        Path path = City.shortestPathFrom(work, home);
                    }
                }
            }

        }

        Skeleton.logFunctionEnd();
    }
}