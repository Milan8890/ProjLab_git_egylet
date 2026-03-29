package main;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import entities.*;
import playground.*;
import user.*;
import equipment.*;

public class UseCases {

    
    
     /**
     * Use case 1: Fej vásárlása
     * A játékos kiválaszt egy fejet a lehetőségek közül, és megvásárolja azt a hókotrójához. A fej ára levonódik a játékos pénzéből, és a fej hozzáadódik a hókotrónak a fejtárolójához.
     */
	public static void purchaseHead_1() {
		Snowplower plower = Skeleton.Market.snowplower;

		int listing = Skeleton.questionMultiple("Milyen fejet szeretne vásárolni?",
				Arrays.asList("Jégtörő", "Hányó", "Seprő", "Sószóró", "Sárkány"));
		HeadListing headListing;
		switch (listing) {
			case 1:
				headListing = Skeleton.Market.breakerHeadListing;
				break;
			case 2:
				headListing = Skeleton.Market.sweeperHeadListing;
				break;
			case 3:
				headListing = Skeleton.Market.ejectorHeadListing;
				break;
			case 4:
				headListing = Skeleton.Market.saltSpreaderHeadListing;
				break;
			default:
				headListing = Skeleton.Market.dragonHeadListing;
				break;
		}
		plower.getHeadInventory().buyListing(headListing);
	}
	public static void vehicleCrash_2() {
		Skeleton.startInit();

		Car car = Skeleton.Market.car;
		Road road = Skeleton.Market.road;

		Skeleton.startUseCase("2. Vehicle crash");

		boolean otherVehicle = 1 == Skeleton.questionMultiple("Van másik jármű az úton?", Arrays.asList("Igen", "Nem"));

		if (otherVehicle) {
			// ez kell, hogy rajta legyen a vehicle a lane-en
			Skeleton.Market.lane2.addVehicle(Skeleton.Market.car2);
		}

		Skeleton.setAnswerStack(Arrays.asList(2, 2, -1, 1, 2));

		car.onTick(); // Car.java 90-tol 103-ig

		// A bus-on hogy lesz meghívva a crashedInto, a road crashVehicle fv-nek kene
		// csinalnia, de az ures.

		// ez nem is kell, mert a crashet majd meghívja az elcsúszott jármű
		// vagy külön use case, amikor ennek van értelme?
		// road.crashVehicle(car);
	}

	public static void vehicleWaitingDueToCrash_3() {
		Skeleton.startUseCase("3. Vehicle waiting due to crash");
		Car car = Skeleton.Market.car;

		// Az Ontick-ben kellene lennie az út megkérdezésének a torlódással
		// kapcsolatban, de nincs benne.
		car.onTick();
	}

	public static void vehicleTrampeSnow_4() {
		Skeleton.startUseCase("4. Vehicle trampe snow");
		Car car = Skeleton.Market.car;

		car.onTick(); // Car.java 105-tol 112-ig
	}

	public static void BusDriverGetsPointForATurn_5() {
		Skeleton.startUseCase("5. Bus driver gets point for a turn");
		Bus bus = Skeleton.Market.bus;

		bus.onTick(); // Bus.java 112-tol 117-ig
	}

	public static void BiokerozinPurchase_6() {
		Skeleton.startUseCase("6. Biokerozin purchase");
		Snowplower plower = Skeleton.Market.snowplower;

		// Itt a "felhasználó kerozin vásárlást kezdeményez a hókotróval"-t hogy kéne?

		boolean purchased = plower.buyBio(); // Snowplower.java 130-tol 142-ig
		if (purchased) {
			Skeleton.logString("Biokerozin vásárlás sikeres.");
		} else {
			Skeleton.logString("Biokerozin vásárlás sikertelen.");
		}
	}

	public static void SaltPurchase_7() {
		Skeleton.startUseCase("7. Salt purchase");
		Snowplower plower = Skeleton.Market.snowplower;

		// Itt a "felhasználó só vásárlást kezdeményez a hókotróval"-t hogy kéne?

		boolean purchased = plower.buySalt(); // Snowplower.java 111-tol 123-ig
		if (purchased) {
			Skeleton.logString("Só vásárlás sikeres.");
		} else {
			Skeleton.logString("Só vásárlás sikertelen.");
		}
	}

	public static void SnowplowerPurchaseWithTheChosenHead_8() {
		Skeleton.startUseCase("8. Snowplower purchase with the chosen head");
		Cleaner cleaner = Skeleton.Market.cleaner;
		boolean purchaseConfirmed;

		int chosenHead = Skeleton.questionMultiple("Milyen fejjel szeretnél hókotrót vásárolni?",
				List.of("Breaker", "Ejector"));

		if (chosenHead == 0) { // Breaker fej
			purchaseConfirmed = cleaner.buyBreakerSnowplower();
			if (purchaseConfirmed) {
				Skeleton.logString("Breaker fejjel ellátott hókotró vásárlása sikeres.");
			} else {
				Skeleton.logString("Breaker fejjel ellátott hókotró vásárlása sikertelen.");
			}
		} else if (chosenHead == 1) { // Ejector fej
			purchaseConfirmed = cleaner.buyEjectorSnowplower();
			if (purchaseConfirmed) {
				Skeleton.logString("Ejector fejjel ellátott hókotró vásárlása sikeres.");
			} else {
				Skeleton.logString("Ejector fejjel ellátott hókotró vásárlása sikertelen.");
			}
		}
	}
    /**
     * Use case 9: User adding a lane to the path of a vehicle
     */
    public static void VehiclePathExtending_9(){
        Skeleton.startInit();
        Vehicle vehicle;
        Skeleton.startUseCase("9. User adding a lane to the path of a vehicle");
        int vehicleChoice = Skeleton.questionMultiple("Melyik járműhöz szeretnél új sávot hozzáadni?", Arrays.asList("Bus", "Snowplower"));
        if(vehicleChoice == 1){
            vehicle = Skeleton.Market.bus;
        }
        else{
            vehicle = Skeleton.Market.snowplower;
        }

        vehicle.extendPath(Skeleton.Market.lane);
    }
    /**
     * Use case 10: Snowplower leaving a Crossing on the set Path
     */
    public static void SnowplowerPathFolowing_10(){
        Skeleton.startInit();
        Skeleton.setAnswerStack(Arrays.asList(1/*, -1, 2, 2 */ ));
        Skeleton.startUseCase("10. Snowplower leaving a Crossing on the set Path");
        Snowplower plower = Skeleton.Market.snowplower;

        plower.onTick();

    }

    /**
     * Use case 11: Bus or Car leaving a Crossing on the set Path
     */
    public static void BusCarPathFolowing_11(){
        Skeleton.startInit();
        Skeleton.startUseCase("11. Bus and Car leaving a Crossing on the set Path");
        Vehicle vehicle;
        int vehicleChoice = Skeleton.questionMultiple("Melyik járművet szeretnéd tesztelni?", Arrays.asList("Bus", "Car"));
        if(vehicleChoice == 1){
            vehicle = Skeleton.Market.bus;
        }
        else{
            vehicle = Skeleton.Market.car;
        }
        
        Skeleton.setAnswerStack(Arrays.asList(/*-1,*/1/*, -1, 2, 0, 2, 0, 2, 2 */ )); //TODO ezt fel kell majd tenni a szokásos helyre, de most csak így mükszik.
        vehicle.onTick();
    }



	/**
	 * Use case 12: Sáv letakarítása
	 */
	public static void cleaningALane_12() {
        Skeleton.startInit();
		Snowplower plower = Skeleton.Market.snowplower;
        Skeleton.setAnswerStack(Arrays.asList(2, 2, 1));
        Skeleton.startUseCase("12. Cleaning a lane");
		plower.onTick();
	}

    /**
     * Use case 13: Car finding new path
     */
    public static void CarFindingNewPath_13() {
        Skeleton.startInit();
        Car car = Skeleton.Market.car;
        Skeleton.setAnswerStack(Arrays.asList(1, 3));
        Skeleton.startUseCase("13. Car finding new path");
        car.onTick();
    }

    /**
     * Use case 14: Fej váltása
     */
	public static void switchHead_14() {
        Skeleton.startInit();
		Snowplower plower = Skeleton.Market.snowplower;
        Skeleton.startUseCase("14. Snowplower Switches active head");
		plower.getHeadInventory().cycleActiveHead();
	}

	public static void startingGame_19() {
		Skeleton.startInit();

		int cleanerAmount = Skeleton.questionValue("Mennyi takarító játékos fog játszani?");
		int busDriverAmount = Skeleton.questionValue("Mennyi buszvezető játékos fog játszani?");
		City.initCity();

		for (int i = 0; i < cleanerAmount; i++) {
			Cleaner t = new Cleaner("Cleaner " + i, Color.RED);
			boolean breaker = 1 == Skeleton.questionMultiple("Milyen hókotrója legyen a játékosnak?",
					Arrays.asList("Breaker", "Ejector"));

			Skeleton.setAnswerStack(Arrays.asList(0, 1));

			if (breaker) {
				t.buyBreakerSnowplower();
			} else {
				t.buyEjectorSnowplower();
			}
		}
		for (int i = 0; i < busDriverAmount; i++) {
			BusDriver t = new BusDriver("BusDriver " + i, Color.RED);
		}
	}
}
