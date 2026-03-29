package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.*;
import user.*;
import playground.*;
import equipment.*;

/**
 * main osztály, itt lehet tesztelni a kódot, illetve itt van egy egyszerű menü is, amivel a különböző use case-eket lehet elindítani.
 */
public class App {

	/**
	 * függvényhez rendelünk egy nevet, hogy a logolásnál használni tudjuk
	 */
	static class NamedFunction {
		String name;
		Runnable function;

		/**
		 * Konstruktor
		 * @param name a függvény neve, amit a logolásnál használunk
		 * @param function a függvény, amit a névvel meghívunk
		 */
		public NamedFunction(String name, Runnable function) {
			this.name = name;
			this.function = function;
		}
	}

	/**
	 * Kategóriákba rendezve tároljuk a különböző use case-eket, hogy a menüben megjeleníthessük őket.
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

	
	public static void hello() {
		System.out.println("hello");
	}

	public static void hello2() {
		System.out.println("hello2");
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
		mainMenu.useCases.add(new NamedFunction("Vásárlás", () -> currentCategory = categories.get(1)));
		mainMenu.useCases.add(new NamedFunction("Járművek", () -> currentCategory = categories.get(2)));
		mainMenu.useCases.add(new NamedFunction("Játékindítás", () -> currentCategory = categories.get(2)));
		// mainMenu.useCases.add(new NamedFunction("Harmadik kategória", () ->
		// currentCategory = categories.get(3)));

		categories.add(mainMenu);

		NamedCategory cat1 = new NamedCategory();
		cat1.name = "Vásárlás";

		cat1.useCases.add(new NamedFunction("Vissza", () -> currentCategory = categories.get(0)));
		cat1.useCases.add(new NamedFunction("Hello1", App::hello));

		categories.add(cat1);

		NamedCategory cat2 = new NamedCategory();
		cat2.name = "Második kategória";

		cat2.useCases.add(new NamedFunction("Vissza", () -> currentCategory = categories.get(0)));
		cat2.useCases.add(new NamedFunction("Hello2", App::hello2));

		categories.add(cat2);
		currentCategory = categories.get(0);
	}

	/**
	 * A menü kirajzolása, a jelenlegi kategória neve és a hozzá tartozó use case-ek megjelenítése.
	 */
	public static void draw() {
		System.out.println(currentCategory.name);
		for (int i = 0; i < currentCategory.useCases.size(); i++) {
			System.out.println(i + ". " + currentCategory.useCases.get(i).name);
		}
	}

	/**
	 * A menü kezelése, a felhasználó bemenetétől függően a megfelelő use case-ek meghívása.
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
			currentCategory.useCases.get(choice).function.run();
		}
	}

	/**
	 * A main függvény, innen lehet elindítani a programot.
	 * @param args a parancssori argumentumok, amiket a program indításakor meg lehet adni
	 * @throws Exception ha valami hiba történik a program futása közben
	 */
	public static void main(String[] args) throws Exception {
		// Itt lehet tesztelni
		catinit();
		// menu();

		UseCases.switchHead_14();
	}
}