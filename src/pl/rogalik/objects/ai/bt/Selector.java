package pl.rogalik.objects.ai.bt;


import pl.rogalik.mechanics.GameEngine;
import pl.rogalik.objects.Monster;

import java.util.List;


public class Selector implements Routine {

    private List<Routine> routines;

    public Selector(List<Routine> routines) {
        this.routines = routines;
    }

    @Override
    public boolean execute(Monster mob, GameEngine engine) {
        for(Routine currentRoutine : routines){
            if(currentRoutine.execute(mob, engine))
                return true;
        }
        return false;
    }
}
