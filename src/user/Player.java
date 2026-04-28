package user;
import java.awt.Color;
import java.util.HashSet;


/**
 * A játékosok ősosztálya. Nincs saját függvénye csak belső állapotot tárolna.
 * <p>
 * 
 * Felelősség <br>
 * Tárolja a játékos nevét.
 */
public abstract class Player{
    /**
     * A játékos neve.
     */
    String name;
    
    /**
     * Konstruktor, beállítja a játékos nevét.
     * @param name
     */
    Player(String name) {
        this.name = name;
    }
}