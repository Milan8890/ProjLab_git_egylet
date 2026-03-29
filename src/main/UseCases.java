package main;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import entities.*;
import playground.*;
import user.*;
import equipment.*;

public class UseCases {

      /**
     * Use case 1: Fej vásárlása
     * A játékos kiválaszt egy fejet a lehetőségek közül, és megvásárolja azt a hókotrójához. 
     * A fej ára levonódik a játékos pénzéből, és a fej hozzáadódik a hókotrónak a fejtárolójához.
     */
     //Kézi futtatású.
    public static void purchaseHead_1(){
        Skeleton.startInit();
        Snowplower plower = Skeleton.Market.snowplower;

        Skeleton.startUseCase("1. Purchase head");
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

    /**
     * Use case 2: Vehicle crash
     * A jármű megcsúszik, és amennyiben van más jármű az úton, akkor ütközik azzal.
     */
    //Kérdés után autómatán fut.
    // Opcionálisan átírható úgy, hogy különböző fajta járművek ütközzenek (busz).
	public static void vehicleCrash_2() {
		Skeleton.startInit();

		Car car = Skeleton.Market.car;
        Bus bus = Skeleton.Market.bus;
		Road road = Skeleton.Market.road;

		Skeleton.startUseCase("2. Vehicle crash");

        int vehicleType = Skeleton.questionMultiple("Milyen járműről van szó?", Arrays.asList("Autó", "Busz"));

        boolean otherVehicle = 1 == Skeleton.questionMultiple("Van másik jármű az úton?", Arrays.asList("Igen", "Nem"));

        if(vehicleType == 1){ //Autó
            if (otherVehicle){ //Van másik jármű

            Skeleton.Market.lane2.addVehicle(Skeleton.Market.car2);
            Skeleton.setAnswerStack(Arrays.asList(2, 2, 100, 1, 2, 60)); //Ha kell, akkor utolsó max az utolsó (idő) egyet kell kivenni.
            car.onTick();
		        }
            else { //Nincs másik jármű
                Skeleton.setAnswerStack(Arrays.asList(2, 2, 100, 1, 2));
                car.onTick();
            }
        } //Busz
        else if(vehicleType == 2){ //Busz
            if (otherVehicle){ //Van másik jármű

                Skeleton.Market.lane2.addVehicle(Skeleton.Market.bus2);
                Skeleton.setAnswerStack(Arrays.asList(2, 2, 100, 1, 2, 60)); //Ha kell, akkor utolsó max az utolsó (idő) egyet kell kivenni.
                bus.onTick();
		    }
            else { //Nincs másik jármű
                Skeleton.setAnswerStack(Arrays.asList(2, 2, 100, 1, 2));
                bus.onTick();
            }
        }
	}

    /**
     * Use case 3: Vehicle waiting due to crash
     * A jármű várakozik, amíg a baleset miatt az út szabad nem lesz.
     */
    //Autómatán fut.
    public static void vehicleWaitingDueToCrash_3() { 
        Skeleton.startInit();
        Car car = Skeleton.Market.car;
        Bus bus = Skeleton.Market.bus;

		Skeleton.startUseCase("3. Vehicle waiting due to crash");

        int vehicleType = Skeleton.questionMultiple("Milyen járműről van szó?", Arrays.asList("Autó", "Busz"));

        if(vehicleType == 1){ //Autó
            Skeleton.setAnswerStack(Arrays.asList(2, 1)); //Ha kell, akkor utolsó kettőt kell kivenni.
            car.onTick();
        }
        else if(vehicleType == 2){ //Busz
            Skeleton.setAnswerStack(Arrays.asList(2,1)); //Ha kell,akkor utolsó kettőt kell kivenni.
            bus.onTick();
        }
	}

    /**
     * Use case 4: Vehicle trampe snow
     * A jármű elakad a hóban, és amíg el nem akad, addig halad.
     */
    //Autómatán fut.
	public static void vehicleTrampeSnow_4(){
        Skeleton.startInit();
        Car car = Skeleton.Market.car;
        Bus bus = Skeleton.Market.bus;

		Skeleton.startUseCase("4. Vehicle trampe snow");

        int vehicleType = Skeleton.questionMultiple("Milyen járműről van szó?", Arrays.asList("Autó", "Busz"));

        if(vehicleType == 1){ //Autó
            Skeleton.setAnswerStack(Arrays.asList(2,2,5,2,10,2,1,2)); //Ha kell,akkor utolsó kettőt kell kivenni.
		    car.onTick();
        }
        else if(vehicleType == 2){ //Busz
            Skeleton.setAnswerStack(Arrays.asList(2,2,5,2,10,2,1,2)); //Ha kell,akkor utolsó kettőt kell kivenni.
            bus.onTick();
        }

    }

    /**
     * Use case 5: Bus driver gets point for a turn
     * A buszsofőr pontot kap egy fordulóért, amikor a busz eléri az egyik irányú végállomást.
     */
    //Autómatán fut.     
	public static void BusDriverGetsPointForATurn_5() {
        Skeleton.startInit();
        Bus bus = Skeleton.Market.bus;

		Skeleton.startUseCase("5. Bus driver gets point for a turn");

        Skeleton.setAnswerStack(Arrays.asList(2,2,5,2,10,2,1,1)); //Ha kell,akkor utolsó egyet kell kivenni.
		bus.onTick();
	}

    /**
     * Use case 6: Biokerozin purchase
     * A játékos (felhasználó) biokerozint vásárol a hókotrójához.
     * A biokerozin ára levonódik a játékos pénzéből, és a biokerozin hozzáadódik a hókotrónak az üzemanyagtartályához.
     */
    //Kézi futtatású.
	public static void BiokerozinPurchase_6() {
        Skeleton.startInit();
        Snowplower plower = Skeleton.Market.snowplower;

		Skeleton.startUseCase("6. Biokerozin purchase");
        Skeleton.setAnswerStack(Arrays.asList(1));

		boolean purchased = plower.buyBio();
		if (purchased) {
			Skeleton.logString("Biokerozin vásárlás sikeres.");
		} else {
			Skeleton.logString("Biokerozin vásárlás sikertelen.");
		}
	}

    /**
     * Use case 7: Salt purchase
     * A játékos (felhasználó) sót vásárol a hókotrójához.
     * A só ára levonódik a játékos pénzéből, és a só hozzáadódik a hókotrónak a sótartályához.
     */
    //Kézi futtatású.
	public static void SaltPurchase_7() {
        Skeleton.startInit();
        Snowplower plower = Skeleton.Market.snowplower;

		Skeleton.startUseCase("7. Salt purchase");
        Skeleton.setAnswerStack(Arrays.asList(1));

		boolean purchased = plower.buySalt();
		if (purchased) {
			Skeleton.logString("Só vásárlás sikeres.");
		} else {
			Skeleton.logString("Só vásárlás sikertelen.");
		}
	}

    /**
     * Use case 8: Snowplower purchase with the chosen head
     * A játékos kiválaszt egy fejet a lehetőségek közül, és megvásárolja vele rendelkező hókotrót. 
     * Az új hókotró ára levonódik a játékos pénzéből.
     */
     //Kézi futtatású.
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

    /**
     * Use case 15: Hóesés
     * A rendszer bizonyos időközönként növelheti a sávokon található hó mennyiségét.
     */
    public static void snowing_15() {
        Skeleton.startInit();
        
        Road road = Skeleton.Market.road;

        Skeleton.startUseCase("Snowing");

        Skeleton.setAnswerStack(Arrays.asList(-1, 1));

        road.onTick();
    }

    /**
     * Use case 16: Vehicle unblocking itself during onTick
     * A hókotró takarítása után egy korábban elakadt jármű a hó eltakarítása miatt már kiszabadulhat.
     */
    public static void vehicleUnblocking_16() {
        Skeleton.startInit();

        Car car = Skeleton.Market.car;

        Skeleton.startUseCase("Vehicle unblocking itself during onTick");

        Skeleton.setAnswerStack(Arrays.asList(2, 2, 0, 2, -1, -1, 2));

        car.onTick();
    }

    /**
     * Use case 17: Vehicle enters next lane from crossing
     * A kereszteződésben tartózkodó jármű (autó vagy busz) megkísérel behajtani a következő sávba.
     */
    public static void vehicleEntersNextLane_17() {
        Skeleton.startInit();

        Car car = Skeleton.Market.car;

        Skeleton.startUseCase("Vehicle enters next lane from crossing");

        Skeleton.setAnswerStack(Arrays.asList(1, -1, 2, 0, 2, -1, 2, 2));

        car.onTick();
    }

    /**
     * Use case 18: Vehicle switches lanes due to deep snow
     * A jármű (autó vagy busz) haladás közben túl mély havat észlel az aktuális sávjában, 
     * ezért megpróbál átváltani az út egy másik sávjába, hogy tovább haladhasson.
     */
    public static void vehicleSwitchesLane_18() {
        Skeleton.startInit();
                
        Car car = Skeleton.Market.car;

        Skeleton.startUseCase("Vehicle switches lanes due to deep snow");

        Skeleton.setAnswerStack(Arrays.asList(2, 2, 0, 2));

        car.onTick();
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
