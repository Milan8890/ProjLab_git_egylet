package user;
import java.awt.Color;
import java.util.HashSet;

import main.Skeleton;

/**
 * A játékosok ősosztálya. Nincs saját függvénye csak belső állapotot tárolna
 */
public abstract class Player{
    /**
     * Konstruktor, beállítja a játékos nevét és színét
     * @param name
     * @param color
     */
    Player(String name, Color color) {
        Skeleton.initSettingUpObjectStart(this);
    }
}