package main;

import playground.Lane;
import playground.Road;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import entities.*;
import playground.*;
import user.*;
import equipment.*;

public class UseCases {
    //TODO

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

		Skeleton.setAnswerStack(Arrays.asList(2, 2, 100, 1, 2));

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

	public static void cleaningALane_12() {
		Snowplower plower = Skeleton.Market.snowplower;
		plower.onTick();
	}

	public static void switchHead_14() {
		Snowplower plower = Skeleton.Market.snowplower;
		plower.getHeadInventory().cycleActiveHead();
	}

    /*
    Skeleton.startInit();

		Car car = Skeleton.Market.car;
		Road road = Skeleton.Market.road;

		Skeleton.startUseCase("2. Vehicle crash");

		boolean otherVehicle = 1 == Skeleton.questionMultiple("Van másik jármű az úton?", Arrays.asList("Igen", "Nem"));

		if (otherVehicle) {
			// ez kell, hogy rajta legyen a vehicle a lane-en
			Skeleton.Market.lane2.addVehicle(Skeleton.Market.car2);
		}

		Skeleton.setAnswerStack(Arrays.asList(2, 2, 100, 1, 2));

		car.onTick(); // Car.java 90-tol 103-ig

		// A bus-on hogy lesz meghívva a crashedInto, a road crashVehicle fv-nek kene
		// csinalnia, de az ures.

		// ez nem is kell, mert a crashet majd meghívja az elcsúszott jármű
		// vagy külön use case, amikor ennek van értelme?
		// road.crashVehicle(car);
    */

    public static void snowing_15() {
        Skeleton.startUseCase("Snowing");

        Road road = Skeleton.Market.road;
        road.onTick();

        Skeleton.logFunctionEnd();
    }

    public static void vehicleUnblocking_16() {
        Skeleton.startUseCase("Vehicle unblocking itself during onTick");
        Car car = Skeleton.Market.car;
        car.onTick();

        Skeleton.logFunctionEnd();
    }

    public static void vehicleEntersNextLane_17() {
        Skeleton.startUseCase("Vehicle enters next lane from crossing");
        Car car = Skeleton.Market.car;
        car.onTick();

        Skeleton.logFunctionEnd();
    }

    public static void vehicleSwitchesLane_18() {
        Skeleton.startUseCase("Vehicle switches lanes due to deep snow");

        Car car = Skeleton.Market.car;
        car.onTick();
    }
}
