package playground;

import main.Skeleton;

public class Path {
    public Lane pop(){
        Skeleton.logFunctionStart(this, "pop", null);
        
        Lane nextLane = new Lane(); 
        
        Skeleton.logFunctionEnd();
        return nextLane;
    }
}