package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.*;
import user.*;
import playground.*;
import equipment.*;

public class App {
	static class NamedFunction {
		String name;
		Runnable function;

		public NamedFunction(String name, Runnable function) {
			this.name = name;
			this.function = function;
		}
	}

	static class NamedCategory {
		public String name;
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

	// TODO menü szervezése
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

	public static void draw() {
		System.out.println(currentCategory.name);
		for (int i = 0; i < currentCategory.useCases.size(); i++) {
			System.out.println(i + ". " + currentCategory.useCases.get(i).name);
		}
	}

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

	public static void main(String[] args) throws Exception {
		// Itt lehet tesztelni
		catinit();
		// menu();
		
	}
}