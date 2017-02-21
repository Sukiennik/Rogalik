package pl.rogalik.environ1.game_map.map_objects.direction;

import pl.rogalik.objects.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bblaszkow on 08.12.16.
 */
public enum Direction {
    /*N(0,1),
    NE(1,1),
    E(1,0),
    SE(1,-1),
    S(0,-1),
    SW(-1,-1),
    W(-1,0),
    NW(-1,1);*/
    NE(-1,1),
    E(0,1),
    W(0,-1),
    S(1,0),
    N(-1,0),
    SW(1,-1),
    SE(1,1),
    NW(-1,-1);

    private static Map<Tuple<Integer, Integer>, Direction> directions;
    static {
        directions = new HashMap<>();
        directions.put(new Tuple<>(0, 1), Direction.N);
        directions.put(new Tuple<>(1, 1), Direction.NE);
        directions.put(new Tuple<>(1, 0), Direction.E);
        directions.put(new Tuple<>(1, -1), Direction.SE);
        directions.put(new Tuple<>(0, -1), Direction.S);
        directions.put(new Tuple<>(-1, -1), Direction.SW);
        directions.put(new Tuple<>(-1, 0), Direction.W);
        directions.put(new Tuple<>(-1, 1), Direction.NW);
    }

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }

    public static Direction fromCoordinates(int dx, int dy){
        if(dx != 0)
            dx /= Math.abs(dx);
        if(dy != 0)
            dy /= Math.abs(dy);
        return directions.get(new Tuple<>(dx, dy));
    }
}
