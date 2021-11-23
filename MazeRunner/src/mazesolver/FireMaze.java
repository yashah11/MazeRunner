package mazesolver;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
*
* @author Aaron Argueta
* @author Yash Shah
*/

public class FireMaze {
	
	public static Maze fireMaze = new Maze();
	public static Coordinate src = FireMazeView.src;
	public static Coordinate goal = FireMazeView.goal;
	public static double p = FireMazeView.probOfF;
	
	
	public static Maze makeFireMaze(Maze maze) { // 2 mazes are being made one that the UI sees and the other where the fire will spread etc. 

		Coordinate firstfirecell = new Coordinate(1,1);
		int pathlengthoffire, mazepathlen;
		fireMaze.maze = new int[View.dim][View.dim];
		
		do {					
			maze.maze = RandomMazeGenerator.maze();							//check if the maze is solvable
			fireMaze.maze = new int [maze.maze.length][]; 	//clone the solvable generated maze to Fire maze
				
				for (int r = 0; r<maze.maze.length; r++) {
					fireMaze.maze[r] =maze.maze[r].clone();}
				/* So the FireMaze has new rules
				 * 		blocks == -10
				 * 		free spaces = 0;
				 * 		cells on fire = -666;
				 * 		the reasoning is that we are going to keep track of the neighbors on fire of each cell by adding 1 to the number on the cell
				 * 
				 * 		since no cell can have more than 4 neighbors on fire 10 is a safe number to use
				 *  */
			cleanupMaze(fireMaze);				// we add the new rules to fireMaze
			firstfirecell =startFire(maze);
			pathlengthoffire=BFS.runBFS(maze, firstfirecell);
			mazepathlen=BFS.runBFS(maze, src);
			
			
			
		}while ( mazepathlen < 0 || pathlengthoffire < 0);
		
	
		return maze;

	}
	
	private static void cleanupMaze(Maze maze) {
		  
		for(int i= 0; i < View.dim; i++ ) {
			for(int j=0; j< View.dim; j ++) {
				if(maze.maze[i][j] == 0) {
				maze.maze[i][j]= -10;
				}
				if(maze.maze[i][j] == 1) {
					maze.maze[i][j]=0;
			 }
				if(maze.maze[i][j] == 9) {
					maze.maze[i][j]=-10;
			 }
		 }
		} 
		
	}
	
	

	
	public static void pickCellstoBurn(Maze maze,  double p) {
		//System.out.println("hello");
		Random random = new Random();
		boolean isgonnaburn;
		Coordinate current = new Coordinate(0,0);
		int [][]map = new int[View.dim] [View.dim];
		
			//clone the solvable generated maze to Fire maze
		for (int r = 0; r< fireMaze.maze.length; r++) {
			map[r] =fireMaze.maze[r].clone();	
		}
		
		for(int i= 0; i < View.dim; i++ ) {
			for(int j=0; j< View.dim; j ++) {
				
				if(map[i][j] > 0) {
					double k = fireMaze.maze[i][j];												//k is the integer in firemaze[][]
					double pofire = (1 - Math.pow(1-p, k));										//Probability that it will catch on fire
					isgonnaburn = ((random.nextDouble() <= pofire)) ? true : false;				// will it catch on fire?
					
					if(isgonnaburn) {															//if it catches on fire we need to do this stuff
						fireMaze.maze[i][j] = -666;												//set that cell on fire in frieMaze
						maze.maze[i][j] = -666;													// and maze in UI;
						current.x =i;																	// save the coordinate of this new fire
						current.y =j;																	
						fireMaze.listOfBlocksOnFire.add(current);										// add to the list of blocksOnfire
						getFireNeghibors(current);											//then get the neighbors that are near this fire and add 1 to there k values
					}
				}
			}
		};
		
	}
		
		
		
		
	
	
	public static void getFireNeghibors(Coordinate current) {
		int rowNum[] = {-1, 1, 0, 0}; 
		int colNum[] = {0, 0, 1, -1}; 
		
		int row = 0;
        int col = 0;
        
        for (int i = 0; i < 4; i++) { 
            
            
            row = current.x + rowNum[i]; 
            col = current.y + colNum[i]; 
            
        
          

          if (cellValid(row, col) && fireMaze.maze[row][col] != -10 && fireMaze.maze[row][col] != -666){  // if( inside the boundries, not a block, and not on fire already)

             fireMaze.maze[row][col]= fireMaze.maze[row][col] + 1;  //add one to the value of that cell because the value of that cell is the K value
              														// so we are basically saying k = k +1; 
          }
        }
		
		
	}
        
	
	public static boolean cellValid(int r, int c){ //checks to see if current Coordinate is valid and within the boundaries of the matrix
        return (r<View.dim && r>=0 && c<View.dim && c>=0);
    }
	
	public static Coordinate startFire(Maze maze) {
		
		int x, y;
		Random rand=new Random();
		
		Coordinate coordinate = new Coordinate(1,1);
		do {
			
		coordinate.x = x = rand.nextInt(View.dim-1); //wont pick the last two on path because those two are the start state
		coordinate.y = y = rand.nextInt(View.dim-1);
		
		
		} while( maze.maze[x][y] ==0 || (x == 0 && y == 0)||(x== View.dim-1 && y==View.dim-1)); //while not a block, not the start state, not goal state
	
		maze.maze[x][y] = -665; //-666 is a fire now!
		fireMaze.maze[x][y] = -666;
		fireMaze.listOfBlocksOnFire.add(coordinate);
		getFireNeghibors(coordinate);
		
		return coordinate;
	}
	
	public static boolean amIBurning(Maze maze, int x , int y) {
		
		
		if(maze.maze[x][y] == -666 && fireMaze.maze[x][y]==-666) {
			
			return true;
			
		}
		return false;
		
		
	}
}
