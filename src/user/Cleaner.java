package user;

import java.awt.Color;
import java.util.Arrays;
import java.util.Set;

import entities.Snowplower;
import main.Skeleton;
public class Cleaner extends Player {
    Set<Snowplower> snowplowers;

    Cleaner(String name, Color color)
    {
        super(name, color);
        Skeleton.initSettingUpObjectStart(this);
        Skeleton.initSettingUpObjectEnd();
    }


    public void addMoney(int m)
    {
        Skeleton.logFunctionStart(this, "addMoney", Arrays.asList(Integer.toString(m)));
        Skeleton.logFunctionEnd();
    }

    public boolean removeMoney(int m)
    {
        Skeleton.logFunctionStart(this, "removeMoney", Arrays.asList(Integer.toString(m)));
        int ans = Skeleton.questionMultiple("Van elég pénz?", Arrays.asList("Igen", "Nem"));
        Skeleton.logFunctionEnd();
        return ans == 1;
    }

    public boolean buyBreakerSnowplower()
    {
        Skeleton.logFunctionStart(this, "buyBreakerSnowplower", null);

        int val = Skeleton.questionValue("Mennyibe kerül?");
        boolean temp = removeMoney(val);

        //Kéne bázis lekérés?
        snowplowers.add(Snowplower.createWithBreaker(this, null));

        Skeleton.logFunctionEnd();
        return temp;
    }

    public boolean buyEjectorSnowplower()
    {
        Skeleton.logFunctionStart(this, "buyEjectorSnowplower", null);
        
        int val = Skeleton.questionValue("Mennyibe kerül?");
        boolean temp = removeMoney(val);

        //Kéne bázis lekérés?
        snowplowers.add(Snowplower.createWithEjector(this, null));

        Skeleton.logFunctionEnd();
        return temp;
    }

}