package pl.rogalik.environ1.game_map.map_providers.generators;

public class CaveMapGenerator extends MapGenerator {

	private boolean correct;
	private int[][] matrix;

	public CaveMapGenerator(int width, int height) {
		super(width, height);
	}

	@Override
	protected void generateMap(){
        map = new int[width][height];
        while (true) {
			try {
				fillRandomly();
				for (int i = 0; i < 5; i++)
					smooth();
				if (checkIfCorrect())
					break;
			}
			catch (StackOverflowError e){
				width--;
				height--;
				map = new int[width][height];
			}
		}


	}


	private void fillRandomly(){

		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (i == 0 || i == this.width - 1 || j == 0 || j == this.height - 1) {
					map[i][j] = 1;
				}
				else {
					if (random.nextDouble() >= wallsPercent)
						map[i][j] = 1;
					else
						map[i][j] = 0;
				}
			}
		}
	}

	private void smooth(){

		int wallTiles = 0;
		int wallTiles2 = 0;

		for(int i = 1; i < width-1; i++){
			for(int j = 1; j < height-1; j++){
	        	/* brzegi nie powinny byc w zaden sposob zmieniane */
	         	
	        	/* kwadrat wokol - 3x3 */
				for(int k = -1; k <= 1; k++){
					for(int l = -1; l <= 1; l++){
						if((k*k + l*l != 0) && map[k+i][l+j] == 0)
							wallTiles++;
					}
				}

				if(i > 2 && i < width-2 && j > 2 && j < height-2)
				{
					for(int k = -2; k <= 2; k++)
					{
						for(int l = -2; l <= 2; l++)
						{
							if((k*k + l*l != 0) && map[k+i][l+j] == 1)
								wallTiles2++;
						}
					}
				}


				if (wallTiles >= 5)
					map[i][j] = 0;

				if (wallTiles2 >= 23)
					map[i][j] = 1;


				wallTiles = 0;
				wallTiles2 = 0;
			}
		}
	}


	public boolean ifPathExists (int sourceX, int sourceY, int destinationX, int destinationY){
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



}