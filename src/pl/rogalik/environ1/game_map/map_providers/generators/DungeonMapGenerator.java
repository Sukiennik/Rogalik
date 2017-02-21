package pl.rogalik.environ1.game_map.map_providers.generators;

import pl.rogalik.environ1.game_map.map_objects.entities.Entity;
import pl.rogalik.environ1.game_map.map_objects.entities.EntityType;

import java.util.*;

public class DungeonMapGenerator extends MapGenerator {
    List<int[]> roomList ;
    private int rooms;
    private int maxRoomSize ;
    private int minRoomSize ;
    private List<Integer> touncarve;
    private int[][] matrix;

    public DungeonMapGenerator(int width, int height){
        super(width,height);
    }

    @Override
    protected void generateMap(){
        rooms = 30;
        maxRoomSize = 15;
        minRoomSize = 3;
        map = new int[width][height];
        while(true) {
                roomList = new ArrayList();
                touncarve = new ArrayList();

                for (int a = 0; a < width; a++) {
                    Arrays.fill(map[a], 1);
                }

                width = width - 1;
                height = height - 1;

                for (int k = 0; k < this.rooms; k++) {
                    int x = random.nextInt(width - 1) + 1;
                    int y = random.nextInt(height - 1) + 1;
                    int h = minRoomSize + random.nextInt(maxRoomSize - minRoomSize + 1);
                    int w = minRoomSize + random.nextInt(maxRoomSize - minRoomSize + 1);

                    int room[] = {x, y, h, w};
                    Boolean failed = false;
                    for (int b[] : roomList) {
                        if (intersects(room, b)) {
                            failed = true;
                            break;
                        }

                    }
                    if (!failed) {
                        for (int i = room[0]; i < room[0] + room[3]; i++) {
                            for (int j = room[1]; j < room[1] + room[2]; j++) {
                                if (i < width && j < height)
                                    map[i][j] = 0;
                            }
                        }
                        roomList.add(room);
                    }
                }
            try {
                MazePaths();

                width = width + 1;
                height = height + 1;
                if (checkIfCorrect())
                    break;
            }
            catch (StackOverflowError e){
                width--;
                height--;
                map = new int[width][height];
            }
            }


        uncarve();


    }
    private void MazePaths(){
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (map[i][j] == 1) {
                    if (map[i][j - 1] + map[i][j + 1] + map[i - 1][j] + map[i + 1][j] == 4) {
                        carve(i, j);

                    }
                }
            }
        }

        for (int room[] : roomList) {
            boolean connected = false;
            int count = 1;
            ArrayList<int[]> possibleexits = new ArrayList<>();
            while (!connected || count <3) {
                for (int i = room[0]; i < room[0] + room[3]; i++) {
                    if (i > 1 && i < width) {
                        if (room[1] + room[2] + count < height) {
                            if (map[i][room[1] + room[2] + count - 1] == 1 && map[i][room[1] + room[2] + count] == 0) {
                                int a[] = {i, room[1] + room[2] - 1, i, room[1] + room[2] + count - 1};
                                possibleexits.add(a);
                            }
                        }
                        if (room[1] - count - 1 > 0) {
                            if (map[i][room[1] - count] == 1 && map[i][room[1] - count - 1] == 0) {
                                int a[] = {i, room[1] - count, i, room[1]};
                                possibleexits.add(a);
                            }
                        }
                    }
                }
                for (int j = room[1]; j < room[1] + room[2]; j++) {
                    if (j > 1 && j < height) {
                        if (room[0] + room[3] + count < width) {
                            if (map[room[0] + room[3] + count - 1][j] == 1 && map[room[0] + room[3] + count][j] == 0) {
                                int a[] = {room[0] + room[3] - 1, j, room[0] + room[3] + count - 1, j};
                                possibleexits.add(a);
                            }
                        }
                        if (room[0] - count - 1 > 0) {
                            if (map[room[0] - count][j] == 1 && map[room[0] - count - 1][j] == 0) {
                                int a[] = {room[0] - count, j, room[0], j};
                                possibleexits.add(a);
                            }
                        }
                    }
                }
                if (!possibleexits.isEmpty()) {
                    connected = true;
                }
                count++;
            }
            while (!possibleexits.isEmpty()) {
                int x[] = possibleexits.remove(random.nextInt(possibleexits.size()));
                if (x[0] != x[2]) {
                    for (int k = x[0]; k <= x[2]; k++) {
                        map[k][x[1]] = 0;
                    }
                } else {
                    for (int k = x[1]; k <= x[3]; k++) {
                        map[x[0]][k] = 0;
                    }
                }
                if(random.nextInt(100)>30)
                    break;
            }
        }
    }

    private boolean ifPathExists (int sourceX, int sourceY, int destinationX, int destinationY){
        int width = map.length;
        int height = map[0].length;


        this.matrix = new int[width][height];

        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                matrix[i][j] = map[i][j];
            }
        }

        if (matrix[destinationX-1][destinationY] == 0)
            destinationX = destinationX - 1;

        else if (matrix[destinationX][destinationY-1] == 0)
            destinationY = destinationY - 1;


        floodFill(sourceX, sourceY);

        if (matrix[destinationX][destinationY] == 2)
            return true;
        else
            return false;
    }

    private void floodFill(int x, int y){

        int current = matrix[x][y];
        if(current == 0){
            this.matrix[x][y] = 2;
            floodFill(x+1, y);
            floodFill(x-1, y);
            floodFill(x, y+1);
            floodFill(x, y-1);

        }
    }


    private boolean checkIfCorrect(){
        for (int destx = this.width-1; destx >= 1; destx--){
            for (int desty = this.height-1; desty >= 1; desty--){
                if ((map[destx][desty] == 1 && map[destx][desty-1] == 0) || (map[destx][desty] == 1 && map[destx-1][desty] == 0)){
                    if (map[destx-1][desty]+map[destx][desty-1]+map[destx][desty+1] < 3) {
                        for (int i = 1; i <= this.width-1; i++){
                            for (int j = 1; j <= this.height-1; j++){
                                if (map[i][j] == 0){
                                    if (map[i-1][j]+map[i][j-1]+map[i][j+1] < 3)
                                        return ifPathExists(i,j,destx,desty);

                                }
                            }
                        }

                    }
                }
            }
        }
        return true;
    }



    private Boolean intersects(int[] room1 , int [] room2){
        return (room1[0] <= room2[0]+room2[3] && room2[0] <= room1[0]+room1[3] &&
                room1[1] <= room2[1]+room2[2] && room2[1] <= room1[1]+room1[2]);

    }

    private Boolean canbeplaced(int x1 , int y1, int x2, int y2){
        return  (y2>=1)&&(y1>=1)&&(x2>=1)&&(x1>=1)&&(y2<height)&&(y1<height)&&(x2<width)&&(x1<width)&&
                (map[x1+1][y1]+map[x1-1][y1]+map[x1][y1+1]+map[x1][y1-1]+map[x1-1][y1-1]+map[x1+1][y1-1]+map[x1-1][y1+1]+map[x1+1][y1+1]>5)&&
                (map[x2+1][y2]+map[x2-1][y2]+map[x2][y2+1]+map[x2][y2-1]+map[x2-1][y2-1]+map[x2+1][y2-1]+map[x2-1][y2+1]+map[x2+1][y2+1]>7);
    }
    private void uncarve(){
        int[] upx = { 1, -1, 0, 0 };
        int[] upy = { 0, 0, 1, -1 };
        while (!touncarve.isEmpty()){
            int x = touncarve.remove(0);
            int y = touncarve.remove(0);
            while (map[x+1][y]+map[x-1][y]+map[x][y-1]+map[x][y+1]>2 ){
                if(map[x][y]==1)
                    break;
                map[x][y]=1;
                for(int i =0; i<4; i++){
                    if(map[x+upx[i]][y+upy[i]]==0){
                        x += upx[i];
                        y += upy[i];
                        break;
                    }

                }
            }
        }

    }

    private void carve(int x, int y) {

        final int[] upx = { 1, -1, 0, 0 };
        final int[] upy = { 0, 0, 1, -1 };

        int dir = random.nextInt(4);
        int count = 0;
        while(count < 4) {
            int x1 = x + upx[dir];
            int y1 = y + upy[dir];
            int c = 0;
            while(c<4) {
                int x2 = x1 + upx[(dir+c)%4];
                int y2 = y1 + upy[(dir+c)%4];

                if (canbeplaced(x1, y1, x2, y2) && map[x1][y1] == 1 && map[x2][y2] == 1) {
                    map[x1][y1] = 0;
                    map[x2][y2] = 0;
                    carve(x2, y2);
                }
                else
                    c++;
            }
            dir = (dir + 1) % 4;
            count += 1;

        }
        if(map[x+1][y]+map[x-1][y]+map[x][y-1]+map[x][y+1]==3) {
            touncarve.add(x);
            touncarve.add(y);
        }
    }

    private void corridor(int [] room1, int[] room2){
        int center1x = ((2*room1[0] + room1[3]) / 2);
        int center1y = ((2*room1[1] + room1[2]) / 2 );
        int center2x = ((2*room2[0] + room2[3]) / 2);
        int center2y = ((2*room2[1] + room2[2]) / 2 );
        if (center1x >= width)
            center1x = width-1;
        if (center1y >= height)
            center1y = height-1;
        if (center2x >= width)
            center2x = width-1;
        if (center2y >= height)
            center2y = height-1;

        if (center1x<center2x)
            for(int x = center1x; x<=center2x; x ++)
                map[x][center1y] = 0;
        else
            for(int x = center1x; x>=center2x; x --)
                map[x][center1y] = 0;
        if(center1y<center2y)
            for(int y = center1y; y<=center2y; y ++)
                map[center2x][y] = 0;
        else
            for(int y = center1y; y>=center2y; y --)
                map[center2x][y] = 0;
    }

    @Override
    protected void generateEntities(){

        entityList = new ArrayList<Entity>();

        int heightStep =  (int)(this.height * this.entityPercent);
        int widthStep =  (int)(this.width * this.entityPercent);
        int ID = 0;

        /** Umiejscowienie bohatera **/

        entityList.add(generateHero(ID++));
        entityList.add(setDoor(ID++));


        for (int[] room : this.roomList){
            int i = room[0] + random.nextInt(room[3] - 2);
            int j = room[1] + random.nextInt(room[2] - 2);
            if (j < this.height && i < this.width) {
                if (random.nextDouble() < 0.5) {
                    if (map[i][j] == 0) {
                        Entity character;
                        if (3 * i < this.width)
                            character = new Entity(i, j, EntityType.SMALL_MONSTER, ID++);
                        else if (3 * i < 2 * this.width)
                            character = new Entity(i, j, EntityType.MEDIUM_MONSTER, ID++);
                        else
                            character = new Entity(i, j, EntityType.BIG_MONSTER, ID++);
                        entityList.add(character);
                        map[i][j] = 3;
                    }
                }
                else{
                    if (map[i][j] == 0) {
                        Entity item;
                        if (3 * i < this.width)
                            item = new Entity(i, j, EntityType.SMALL_ITEM, ID++);
                        else if (3 * i < 2 * this.width)
                            item = new Entity(i, j, EntityType.MEDIUM_ITEM, ID++);
                        else
                            item = new Entity(i, j, EntityType.BIG_ITEM, ID++);
                        entityList.add(item);
                        map[i][j] = 3;
                    }
                }
            }
        }


        /** Dodatkowy potwór przy drzwiach **/
        setFinalMonster(ID++);
    }


}
