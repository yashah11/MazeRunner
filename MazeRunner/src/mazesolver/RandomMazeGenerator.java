package mazesolver;

import java.util.Random;

/**
*
* @author Aaron Argueta
* @author Yash Shah
*/
public class RandomMazeGenerator {
	private static int [][] maze;
	static int dim = View.dim;
	static double p;
	
	
	public static int[][] maze(int dim1, double p1){
		dim = dim1;
		p = p1;
		Random rand=new Random();
		  maze = new int [dim1][dim1];
		  
		  for(int i= 0; i < dim1; i++ ) {
				for(int j=0; j< dim1; j ++) {
						maze[i][j]= ((rand.nextDouble() <= p1)) ? 0 : 1;
					//}
				 }
			 } 
		   	maze[0][0] = 1;
			maze [dim1-1][dim1-1] = 1;
			return maze;
	}
	public static int[][] maze(){
		
		Random rand=new Random();
		  maze = new int [dim][dim];
		  
		  for(int i= 0; i < dim; i++ ) {
				for(int j=0; j< dim; j ++) {
						maze[i][j]= ((rand.nextDouble() <= p)) ? 0 : 1;
					//}
				 }
			 }
		   	maze[0][0] = 1;
			maze [dim-1][dim-1] = 1;
			return maze;
	}	
}
