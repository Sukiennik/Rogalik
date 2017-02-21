package pl.rogalik.objects.ai.bt;

import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.Monster;


public class Loop implements Routine {

    private final Routine routine;
    private final int timesPerTurn;
    private int times;

    public Loop(Routine routine) {
        this.routine = routine;
        this.times = -1;            // infinite
        this.timesPerTurn = -1;
    }

    public Loop(Routine routine, int times) {
        if (times < 1)
            throw new RuntimeException("Can't repeat negative times.");

        this.routine = routine;
        this.times = times;
        this.timesPerTurn = times;
    }

    @Override
    public boolean execute(Monster mob, GameEngine engine) {
        times = timesPerTurn;

        while (routine.execute(mob, engine)){
            if(times > 0)
                times -= 1;
            else if(times == 0)
                return true;
        }
        return false;
    }
}
