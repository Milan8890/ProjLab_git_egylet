package playground;

import java.util.Arrays;

import main.Skeleton;

public class Path {
    public Lane pop(){
        Skeleton.logFunctionStart(this, "pop", null);
        
        Lane nextLane = new Lane(Skeleton.Market.road); 
        
        Skeleton.logFunctionEnd();
        return nextLane;
    }

    public void extendPath(Lane l) {
        Skeleton.logFunctionStart(this, "extendPath", Arrays.asList("l"));

        //Megkérdezzük a sávot, melyik úthoz tartozik
        Road r = l.getRoad(); 

        //Megkérdezzük az utat, honnan indul
        Crossing c = r.getFromCrossing();

        System.out.println("Sáv sikeresen hozzáadva az útvonalhoz.");

        Skeleton.logFunctionEnd();
    }
}