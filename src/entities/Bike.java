package entities;

import java.lang.runtime.SwitchBootstraps;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.World;
import playground.City;
import playground.Crossing;
import playground.City;
import playground.Path;
import playground.Lane;

public class Bike extends Vehicle{

    /*
        Random kezdő crossing
     */
	public Bike(){
		super(City.getCrossings().get((int) (Math.random() * (City.getCrossings().size()-1))));

		Logger.getGlobal().log(Level.INFO, "Created [Obj]", this);
		this.path = new Path(this);
	}

	@Override
	public void reachedCrossing() {
		Logger.getGlobal().log(Level.INFO, "[Obj] trampled snow on [Obj]", new Object[] { this, currentLane });

		super.reachedCrossing();
	}

	/**
	 * Full random crossing-ba indul ami nem a mostani
	 * 
	 * @return Igaz, ha az ősosztály hívása sikeres volt, egyébként hamis.
	 */
	@Override
	protected boolean stepFollowPath() {
		boolean success = super.stepFollowPath();

		if (!success && isInCrossing()) {
            Crossing c;
            while(true)
            {
                c = City.getCrossings().get((int) (Math.random() * (City.getCrossings().size()-1)));
                if(c != lastCrossing)
                    break;
            }

            Logger.getGlobal().log(Level.INFO, "[Obj] has no path, requesting new path between [Obj] and [Obj]",
					new Object[] { this, lastCrossing, c });
			this.path = City.shortestPathFrom(this.lastCrossing, c, this);
		}
		return success;
	}

	@Override
	protected void revive() {
	}


    @Override
    protected boolean stepWaitAfterCrash() {
        if (isCrashed){
            return false;
        }
        return true;
    }
	/**
	 * Megvizsgálja, hogy a jelenlegi sávon található-e elakadt jármű.
	 * <p>
	 * Ha a sávon bármelyik jármű isStuck állapotban van, az az egész sávot
	 * blokkolja. Ekkor az autó újratervezi az útvonalát és várakozik.
	 * 
	 * @return Hamis, ha van elakadt jármű a sávon, egyébként igaz.
	 */
	@Override
	protected boolean stepWaitBecauseOfStuck() {
		return true;
	}
    
    @Override
    protected boolean canEnterLane(Lane l) {
        return true;
    }

    @Override
    public void crashed(int r) {
		super.crashed(r);
        this.isStuck = true;
	}

	@Override
	protected boolean stepSlipOnIce() {
		if (currentLane.getIce() > ICE_DANGER_LIMIT) {
			Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: ice is thick enough",
					new Object[] { this, currentLane });

			if (currentLane.hasGravel() && currentLane.getSnow() <= SNOW_COVER_LEVEL) {
				Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: has gravel",
						new Object[] { this, currentLane });
				Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: snow is thin enough, not slipping",
						new Object[] { this, currentLane });
				return true;
			} else if (currentLane.hasGravel() && currentLane.getSnow() > SNOW_COVER_LEVEL) {
				Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: has gravel",
						new Object[] { this, currentLane });
				Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: snow is too thick for gravel, slipping",
						new Object[] { this, currentLane });
			} else {
				Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: doesn't have gravel, slipping",
						new Object[] { this, currentLane });
			}

			if (World.getRandom(SLIP_CHANCE*10)) {
				Logger.getGlobal().log(Level.INFO, "[Obj] slipping on [Obj]",
						new Object[] { this, currentLane.getRoad() });
				currentLane.getRoad().crashVehicle(this);

				if (this.isCrashed) {
					return false;
				}
			}
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: ice is too thin, not slipping",
					new Object[] { this, currentLane });
		}

		return true;
	}

}
