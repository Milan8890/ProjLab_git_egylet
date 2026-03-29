package main;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.*;
import user.*;
import playground.*;
import equipment.*;

/**
 * main osztály, itt lehet tesztelni a kódot, illetve itt van egy egyszerű menü
 * is, amivel a különböző use case-eket lehet elindítani.
 */
public class App {

	/**
	 * függvényhez rendelünk egy nevet, hogy a logolásnál használni tudjuk
	 */
	static class NamedFunction {
		String name;
		Runnable function;
		boolean isMenuNavigation;

		/**
		 * Konstruktor
		 * 
		 * @param name     a függvény neve, amit a logolásnál használunk
		 * @param function a függvény, amit a névvel meghívunk
		 */
		public NamedFunction(String name, Runnable function, boolean isMenuNavigation) {
			this.name = name;
			this.function = function;
			this.isMenuNavigation = isMenuNavigation;
		}
	}

	/**
	 * Kategóriákba rendezve tároljuk a különböző use case-eket, hogy a menüben
	 * megjeleníthessük őket.
	 */
	static class NamedCategory {
		/**
		 * A kategória neve, amit a menüben megjelenítünk
		 */
		public String name;

		/**
		 * A kategóriába tartozó use case-ek, amiket a menüben megjelenítünk
		 */
		public List<NamedFunction> useCases = new ArrayList<>();
	}

	static List<NamedCategory> categories = new ArrayList<>();

	static NamedCategory currentCategory = null;

	/**
	 * A menü kategóriáinak és use case-einek inicializálása.
	 */
	public static void catinit() {
		NamedCategory mainMenu = new NamedCategory();
		mainMenu.name = "Főmenü";
		// mainMenu.useCases.add(new NamedFunction("Vissza", () -> currentCategory =
		// categories.get(0)));
		mainMenu.useCases.add(new NamedFunction("Vásárlás", () -> currentCategory = categories.get(1), true));
		mainMenu.useCases.add(new NamedFunction("Járművek", () -> currentCategory = categories.get(2), true));
		mainMenu.useCases
				.add(new NamedFunction("Játékindítás és környezet", () -> currentCategory = categories.get(7), true));

		// 0
		categories.add(mainMenu);

		NamedCategory purchuseCat = new NamedCategory();
		purchuseCat.name = "Vásárlás";

		purchuseCat.useCases.add(new NamedFunction("Vissza", () -> currentCategory = categories.get(0), true));
		purchuseCat.useCases.add(new NamedFunction("Biokerozin vásárlás", UseCases::BiokerozinPurchase_6, false));
		purchuseCat.useCases.add(new NamedFunction("Só vásárlás", UseCases::SaltPurchase_7, false));
		purchuseCat.useCases.add(new NamedFunction("Hókotró vásárlás kezdő fej vásárlással",
				UseCases::SnowplowerPurchaseWithTheChosenHead_8, false));
		purchuseCat.useCases.add(new NamedFunction("Hókotró fej vásárlása", UseCases::purchaseHead_1, false));

		// 1
		categories.add(purchuseCat);

		NamedCategory Vechicles = new NamedCategory();
		Vechicles.name = "Járművek";

		Vechicles.useCases.add(new NamedFunction("Vissza", () -> currentCategory = categories.get(0), true));
		Vechicles.useCases.add(new NamedFunction("Jármű (busz/autó)", () -> currentCategory = categories.get(3), true));
		Vechicles.useCases.add(new NamedFunction("Autó", () -> currentCategory = categories.get(4), true));
		Vechicles.useCases.add(new NamedFunction("Busz", () -> currentCategory = categories.get(5), true));
		Vechicles.useCases.add(new NamedFunction("Hókotró", () -> currentCategory = categories.get(6), true));

		// 2
		categories.add(Vechicles);

		NamedCategory VehicleBA = new NamedCategory();
		VehicleBA.name = "Jármű (busz/autó)";

		VehicleBA.useCases.add(new NamedFunction("Vissza", () -> currentCategory = categories.get(2), true));
		VehicleBA.useCases.add(new NamedFunction("Jármű ütközése", UseCases::vehicleCrash_2, false));
		VehicleBA.useCases
				.add(new NamedFunction("Jármű várakozik ütközés miatt", UseCases::vehicleWaitingDueToCrash_3, false));
		VehicleBA.useCases.add(new NamedFunction("Jármű letapossa a havat", UseCases::vehicleTrampeSnow_4, false));
		// IDE KELL _11, _9 _16 _17 _18

		// 3
		categories.add(VehicleBA);

		NamedCategory Car = new NamedCategory();
		Car.name = "Autó";

		Car.useCases.add(new NamedFunction("Vissza", () -> currentCategory = categories.get(2), true));
		Car.useCases.add(new NamedFunction("Jármű ütközése", UseCases::vehicleCrash_2, false)); // IDE KELL _13

		// 4
		categories.add(Car);

		NamedCategory Bus = new NamedCategory();
		Bus.name = "Busz";

		Bus.useCases.add(new NamedFunction("Vissza", () -> currentCategory = categories.get(2), true));
		Bus.useCases.add(new NamedFunction("Busz vezetője pontot kap", UseCases::BusDriverGetsPointForATurn_5, false));

		// 5
		categories.add(Bus);

		NamedCategory Snowplower = new NamedCategory();
		Snowplower.name = "Hókotró";

		Snowplower.useCases.add(new NamedFunction("Vissza", () -> currentCategory = categories.get(2), true));
		Snowplower.useCases.add(new NamedFunction("Hókotró vásárlása", UseCases::BusDriverGetsPointForATurn_5, false)); // IDE
																														// KELL
																														// _10
																														// _12
																														// _14

		// 6
		categories.add(Snowplower);

		NamedCategory GameStart = new NamedCategory();
		GameStart.name = "Játékindítás és környezet";

		GameStart.useCases.add(new NamedFunction("Vissza", () -> currentCategory = categories.get(0), true));
		GameStart.useCases.add(new NamedFunction("Játékindítás", UseCases::startingGame_19, false)); // IDE
		GameStart.useCases.add(new NamedFunction("Havazás", () -> currentCategory = categories.get(7), true)); // IDE
																												// KELL
																												// _15

		// 7
		categories.add(GameStart);

		currentCategory = categories.get(0);
	}

	/**
	 * A menü kirajzolása, a jelenlegi kategória neve és a hozzá tartozó use case-ek
	 * megjelenítése.
	 */
	public static void draw() {
		System.out.println(currentCategory.name);
		for (int i = 0; i < currentCategory.useCases.size(); i++) {
			System.out.println(i + ". " + currentCategory.useCases.get(i).name);
		}
	}

	/**
	 * A menü kezelése, a felhasználó bemenetétől függően a megfelelő use case-ek
	 * meghívása.
	 */
	public static void menu() {
		catinit();

		Scanner sc = new Scanner(System.in);

		while (true) {
			for (int i = 0; i < 100; i++) {
				System.out.println();
			}
			draw();
			int choice = sc.nextInt();
			if (choice == 100)
				break;
			if (currentCategory.useCases.size() <= choice) {
				System.out.println("Wrong input.");
				continue;
			}

			boolean isNotMenuNavigation = !currentCategory.useCases.get(choice).isMenuNavigation;
			currentCategory.useCases.get(choice).function.run();
			if (isNotMenuNavigation) {
				sc.nextLine();
				sc.nextLine();
			}
		}
	}

	/**
	 * A main függvény, innen lehet elindítani a programot.
	 * 
	 * @param args a parancssori argumentumok, amiket a program indításakor meg
	 *             lehet adni
	 * @throws Exception ha valami hiba történik a program futása közben
	 */
	public static void main(String[] args) throws Exception {
		// Itt lehet tesztelni
		// catinit();
		// menu();

		UseCases.vehicleSwitchesLane_18();
	}
}