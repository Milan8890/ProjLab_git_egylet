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
	 * 
	 * @return 1, ha túl magas, 2, ha nem (járható).
	 */
	public int getSnow() {
		Skeleton.logFunctionStart(this, "getSnow", null);

		int snowCm = Skeleton.questionValue("Hány centiméter hó van a sávban?");
		Skeleton.logFunctionEnd();
		return snowCm;
	}

	public void trampleSnow() {
        Skeleton.logFunctionStart(this, "trampleSnow", null);
        
        int amount = Skeleton.questionValue("Hány centiméterrel csökkenjen a hó a letaposás miatt?");
        
        System.out.println("A sávban a hóréteg " + amount + " cm-rel csökkent, a jégréteg harmad ennyivel nőtt.");
        
        Skeleton.logFunctionEnd();
    }
}