package main;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import entities.*;
import playground.*;
import user.*;
import equipment.*;

public class UseCases {

    public static void purchaseHead_1(){
        Snowplower plower = Skeleton.Market.snowplower;

        int listing = Skeleton.questionMultiple("Milyen fejet szeretne vásárolni?", Arrays.asList("Jégtörő", "Hányó", "Seprő", "Sószóró", "Sárkány"));
        HeadListing headListing;
        switch (listing) {
            case 1:
                headListing = Skeleton.Market.breakerHeadListing; 
                break;
            case 2:
                headListing =Skeleton.Market.sweeperHeadListing; 
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

		if (otherVehicle){ //Van másik jármű
			// ez kell, hogy rajta legyen a vehicle a lane-en
			Skeleton.Market.lane2.addVehicle(Skeleton.Market.car2);
            Skeleton.setAnswerStack(Arrays.asList(2, 2, 100, 1, 2, 60));
            car.onTick();
		}
        else { //Nincs másik jármű
            Skeleton.setAnswerStack(Arrays.asList(2, 2, 100, 1, 2));
            car.onTick();
        }
	}

    public static void vehicleWaitingDueToCrash_3() {
        Skeleton.startInit();
        Car car = Skeleton.Market.car;

		Skeleton.startUseCase("3. Vehicle waiting due to crash");
		
		car.onTick(); //Nincs kereszteződésben, majd várakozik ütközés után. 74-88 sorok.
	}

	public static void vehicleTrampeSnow_4(){
        Skeleton.startInit();
        Car car = Skeleton.Market.car;

		Skeleton.startUseCase("4. Vehicle trampe snow");

		car.onTick(); // Car.java 134 sor.
    }

	public static void BusDriverGetsPointForATurn_5() {
        Skeleton.startInit();
        Bus bus = Skeleton.Market.bus;

		Skeleton.startUseCase("5. Bus driver gets point for a turn");

		bus.onTick(); // Bus.java 137 sor.
	}

	public static void BiokerozinPurchase_6() {
        Skeleton.startInit();
        Snowplower plower = Skeleton.Market.snowplower;

		Skeleton.startUseCase("6. Biokerozin purchase");

		// Itt a "felhasználó kerozin vásárlást kezdeményez a hókotróval"-t hogy kéne?

		boolean purchased = plower.buyBio(); // Snowplower.java 178- 189 sorok.
		if (purchased) {
			Skeleton.logString("Biokerozin vásárlás sikeres.");
		} else {
			Skeleton.logString("Biokerozin vásárlás sikertelen.");
		}
	}

	public static void SaltPurchase_7() {
        Skeleton.startInit();
        Snowplower plower = Skeleton.Market.snowplower;

		Skeleton.startUseCase("7. Salt purchase");

		// Itt a "felhasználó só vásárlást kezdeményez a hókotróval"-t hogy kéne?

		boolean purchased = plower.buySalt(); // Snowplower.java 159-tol 170-ig
		if (purchased) {
			Skeleton.logString("Só vásárlás sikeres.");
		} else {
			Skeleton.logString("Só vásárlás sikertelen.");
		}
	}

	public static void SnowplowerPurchaseWithTheChosenHead_8() {
        Skeleton.startInit();
        Cleaner cleaner = Skeleton.Market.cleaner;
		boolean purchaseConfirmed;

		Skeleton.startUseCase("8. Snowplower purchase with the chosen head");

        int chosenHead = Skeleton.questionMultiple("Milyen fejjel szeretnél hókotrót vásárolni?",
				List.of("Breaker", "Ejector"));

		if (chosenHead == 1) { // Breaker fej
			purchaseConfirmed = cleaner.buyBreakerSnowplower();
			if (purchaseConfirmed) {
				Skeleton.logString("Breaker fejjel ellátott hókotró vásárlása sikeres.");
			} else {
				Skeleton.logString("Breaker fejjel ellátott hókotró vásárlása sikertelen.");
			}
		}
        else if (chosenHead == 2) { // Ejector fej
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
}
