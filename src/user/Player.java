package user;
import java.awt.Color;
/**
 * A játékosok ősosztálya. Nincs saját függvénye csak belső állapotot tárolna
 */
public abstract class Player{
    /**
     * Konstruktor, beállítja a játékos nevét és színét
     * @param name
     * @param color
     */
    Player(String name, Color color) {}
}