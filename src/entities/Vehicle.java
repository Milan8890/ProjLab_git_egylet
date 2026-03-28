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
 * Automatikusan követi az utat. Ha túl nagy lenne a hóréteg, elakad. Jeges úton egy 
 * adott eséllyel megcsúszik, ezt jelzi az útnak. <br>
 * lastCrossing: Az utolsó kereszteződés, amiben tartózkodott. Azért tárolja, ha nincs 
 * megadva sáv, akkor tudjuk, hogy itt tartózkodik. <br>
 * currentLane: Az a sáv, amin éppen van. Ezen keresztül tud a sávval kommunikálni, 
 * az úton megcsúszni. Ha értéke null, kereszteződésben van.
 */
public abstract class Vehicle {
    Crossing lastCrossing;
    Lane currentLane;
    double LaneProgress;
    Path path;
    boolean isCrashed;
    boolean isStuck;
    int revTimer;


    public void onTick() {
        Skeleton.logFunctionStart(this, "onTick", null);

        int answer1 = Skeleton.questionMultiple("Ütközés után várakozik -e?", Arrays.asList("igen", "Nem"));
        if(answer1 == 1) {
            System.out.println("revTimer csökkentésre kerül");

            int answer2 = Skeleton.questionMultiple("Lejárt -e a revTimer ideje?", Arrays.asList("igen", "Nem"));
            if(answer2 == 1) {
                System.out.println("Felszabadítjuk a balesetből");
            }
        }

        Skeleton.logFunctionEnd();
    }

    /**
    * Meghívódik, ha a jármű beleütközik valamibe.
    * @param r Mennyi ideig marad mozgásképtelen (revTimer)
    */
    public void crashed(int r) {
        //Az r itt a revTimer értéke
        Skeleton.logFunctionStart(this, "crashed", Arrays.asList(String.valueOf(r)));
    
        System.out.println("Sikeres ütközés regisztrálva. Újraéledési idő: " + r);

        Skeleton.logFunctionEnd();
    }
}