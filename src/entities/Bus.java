package entities;

import java.util.Arrays;

import main.Skeleton;
import playground.City;
import playground.Crossing;
import playground.City;
import playground.Path;
import user.BusDriver;
import playground.Lane;

/**
 * A BusDriver játékosok által irányított járművek, a stationA, és a stationB
 * között közlekednek
 * <p>
 * 
 * Felelősség <br>
 * Két megálló közti fordulók számolása. Elakadás, ha túl magas a hó. Csúszás
 * kezdeményezése. Ütközés kezelése, ütközés utáni újraindulás. Útvonal
 * követése.
 * Nyilvántartja, hogy a jelenlegi úton hol helyezkedik el. Letaposás végzése.
 * 
 * Ősosztályok <br>
 * Vechicle
 */
public class Bus extends Vehicle {
	Crossing stationA;
	Crossing stationB;

	BusDriver owner;

	/**
	 * Konstruktor
	 * 
	 * @param stationA  az egyik végállomás
	 * @param stationB: az egyik végállomás
	 * @param owner:    a busz vezetője
	 */
	public Bus(Crossing stationA, Crossing stationB, BusDriver owner) {
		Skeleton.initSettingUpObjectStart(this);
		this.stationA = stationA;
		this.stationB = stationB;
		this.owner = owner;
		Skeleton.initSettingUpObjectEnd();
	}

	public void onTick() {
		Skeleton.logFunctionStart(this, "onTick", null);

		// először döntsük el kereszteződésből vagy útról induljon az eset
		int whereAnswer = Skeleton.questionMultiple("Hol tartózkodik az autó?", Arrays.asList("kereszteződés", "út"));
		if (whereAnswer == 1) {

			// Ha kereszteződésről indul, akkor tovább szeretne hajtani a busz, ehhez az
			// útvonaltervéből elkéri a következő sávot, ahová a kereszteződésből behajthat
			System.out.println("A busz lekéri a következő sávot az útvonalából.");
			Lane nextLane = path.pop();

			// Kérdezzük meg a sávtól túl magas -e ott a hó
			double snowCm = nextLane.getSnow();
			int snowAnswer = Skeleton.questionMultiple("Elakad a busz " + snowCm + " cm hóban?",
					Arrays.asList("Igen", "Nem"));
			if (snowAnswer == 1) {
				System.out.println("A hó túl magas a sávba lépéshez, a busz a kereszteződésben marad.");
			} else {
				// Küldjük el a sávnak a buszt, hogy tegye fel magára.
				nextLane.addVehicle(this);
			}
		}

		else {
			// Ha az úton tartózkodik előszőr nézzük végig a Vechicle ősosztálybeli
			// járművekre vonatkozó általános léptetést
			super.onTick();

			// Nézzük meg a saját sávunkban most túl magas -e a hó réteg
			int snowLevelAnswer = Skeleton.questionMultiple("Túl magas -e a hóréteg a közlekedéshez?",
					Arrays.asList("igen", "Nem"));

			// Ha túl magas lenne a mi sávunkban a hó, nézzük meg a szomszédos sávokat, át
			// tudunk -e oda hajtani
			if (snowLevelAnswer == 1
					&& Skeleton.questionMultiple("Tud -e sávot váltani?", Arrays.asList("igen", "Nem")) == 1) {

				// Ha nem lehetséges a sávváltás sem, akkor beragadt a buszunk a hóba
				System.out.println("A busz beragadva ott marad");
			} else {
				// Ha vagy a saját sávunk, vagy a szomszédos sávok le lettek tisztítva, akkor
				// korábbi elakadást már fel szabadíthatunk
				int busStuckAnswer = Skeleton.questionMultiple("El volt -e akadva a busz?",
						Arrays.asList("igen", "Nem"));
				if (busStuckAnswer == 1) {
					System.out.println("A busz sikeresen felszabadul");
				}

				// Ha éppen jeges úton tartózkodna a busz döntsük el, hogy elcsússzon -e
				int skidAnswer = Skeleton.questionMultiple("Megcsússzon -e a busz a jeges úton?",
						Arrays.asList("igen", "Nem"));
				if (skidAnswer == 1) {
					System.out.println("A busz megcsúszott");

					// Ha elcsúszott, döntsük el tartózkodott -e másik jármű az úton, akivel ütközik
					int moreVechicleAnswer = Skeleton.questionMultiple("Van -e másik jármű az úton?",
							Arrays.asList("igen", "Nem"));
					if (moreVechicleAnswer == 1) {

						// Ebben az esetben ütköztek, és lerobbannak
						System.out.println("A busz ütközött, emiatt lerobbant");
						this.crashed(5);
					}
				} else {
					// Ha a buszunk megúszta a magas havat, és az elcsúszást is, tovább haladhatna,
					// döntsünk kereszteződésbe ért -e
					int intoCrossingAnswer = Skeleton.questionMultiple("Kereszteződésbe ért -e?",
							Arrays.asList("igen", "Nem"));
					if (intoCrossingAnswer == 1) {

						// Kereszteződésbe éréskor végezzük el erre a sávra a hó letaposását
						System.out.println("A busz elhagyja a sávot, ezért letapossa a havat.");
						currentLane.trampleSnow();

						// Döntsük el a kereszteződés amibe értünk végállomása volt -e a busznak
						int stationAnswer = Skeleton.questionMultiple("Végállomásra érkeztél -e?",
								Arrays.asList("igen", "Nem"));
						if (stationAnswer == 1) {

							// Ha elérte a végállomását emiatt pontot adunk a játékosnak
							System.out.println(
									"A busz megérkezett a végállomásába, ezért kap pontot. Új célja ezután a másik végállomás");
						}
					} else {
						// Ha nem történik semmi a busszal ezen a léptetésen
						System.out.println("A busz haladt előre a sávjában.");
					}
				}
			}

		}
		Skeleton.logFunctionEnd();
	}
}