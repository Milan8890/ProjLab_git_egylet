package playground;

import java.util.Arrays;

import entities.Vehicle;
import main.Skeleton;

public class Lane {
    public void addVehicle(Vehicle v) {
        Skeleton.logFunctionStart(this, "addVehicle", Arrays.asList(v.toString()));
        
        System.out.println("A sávra felkerült a jármű.");
        
        Skeleton.logFunctionEnd();
    }

    /**
     * Megkérdezi a, hogy túl magas-e a hó.
     * @return 1, ha túl magas, 2, ha nem (járható).
     */
    public int getSnow() {
        Skeleton.logFunctionStart(this, "getSnow", null);
        
        int answer = Skeleton.questionMultiple("Túl magas -e a hó a sávban?", Arrays.asList("Igen", "Nem"));
        Skeleton.logFunctionEnd();
        return answer;
    }
}