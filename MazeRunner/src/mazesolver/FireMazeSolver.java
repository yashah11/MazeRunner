package mazesolver;


import java.nio.file.Path;
//import java.io.*; 
import java.util.*;

/**
*
* @author Aaron Argueta
* @author Yash Shah
*/

public class FireMazeSolver {
  
 static int dim = View.dim;
 //row and column locations of neighboring cells (up down left right)
 static int rowNum[] = {-1, 0, 0, 1}; 
 static int colNum[] = {0, -1, 1, 0};  
 public static int kvalues;
 private static List<Coordinate> neighbors = new ArrayList<>();
 @SuppressWarnings("rawtypes")
private static Stack<List> stackofPaths = new Stack<>();
private static List<Integer> pathA = new ArrayList<>();
private static List<Integer> pathB = new ArrayList<>();
private static Stack<Integer> stackofKvalues = new Stack<>();
private static List<Integer> finalpath = new ArrayList<>();
  
  
 

  //-----------------------------------------Search Algorithms------------------------------------------------
  
  public static boolean cellValid(int r, int c){ //checks to see if current Coordinate is valid and within the boundaries of the matrix
      return (r<dim && r>=0 && c<dim && c>=0);
  }
  
  public static void runFiresolver(Maze maze, Maze fireMaze, Coordinate src) {
	  Coordinate nextstep = new Coordinate(0,0);
		  nextstep = runSmartBFS(maze, fireMaze, src);
		 
		  maze.path.clear();
		  maze.path.add(nextstep.x);
		  maze.path.add(nextstep.y);
		  maze.path.add(src.x);
		  maze.path.add(src.y);
		  
	  
	  
  }
  
  public static Coordinate runSmartBFS(Maze maze, Maze fireMaze, Coordinate src){ //takes in parameters of: generated map and our source cell in the matrix

	  if(src.x == dim-1 && src.y == dim-1) {
		  return src;
	  }
	  
	  int row = 0;
      int col = 0;
      
      for (int i = 0; i < 4; i++) { 
          
          row = src.x + rowNum[i]; 
          col = src.y + colNum[i]; 

        if (cellValid(row, col) && maze.maze[row][col] == 1 && maze.maze[row][col] != -666){ 
        	Coordinate c = new Coordinate(col,row);
        	
        	neighbors.add(c);

            // mark cell as visited and enqueue it 
           

            
        }
      }
      
      while(!neighbors.isEmpty()) {
    	  Coordinate c = new Coordinate(0,0);
    	  
    	  c.x = neighbors.get(0).y;
    	  c.y =neighbors.get(0).x;
    	  neighbors.remove(0);
    	  BFS.runBFS(maze, c);
    	  
    	  int i =-1;
    	  int x = 0;
    	  int y = 0;
   
    	  if(!maze.path.isEmpty()) {
    		  List<Integer> path = new ArrayList<>();
	    	  while(!maze.path.isEmpty()) {

	    		  i++;
	    		  path.add(maze.path.remove(0));
	    		  		x = path.get(i);
	    		  		
	    		  		
	    		  i++;
	    		  path.add(maze.path.remove(0));
	    		  
	    		  		y = path.get(i);
	    		  		kvalues = kvalues + fireMaze.maze[y][x];
	    		  
	    	  }								
	    	  stackofPaths.push(path);
	    	  stackofKvalues.push(kvalues);
	    	  kvalues = 0;
	    	  }
	    	  
	    	  
	    	  
	    	  
    	  }
    	  
      
      int kvalueA = 0;
	  int kvalueB = 0;
	  
	  while(!stackofPaths.isEmpty()) {
		  
		  pathB.clear();
		  kvalueB = 45678754;
		  
		  pathB.addAll(stackofPaths.pop());
		  kvalueB = stackofKvalues.pop();
		  
		  if( pathA.isEmpty() && !stackofPaths.isEmpty()) {

			  pathA.addAll(stackofPaths.pop());
			  											
			  kvalueA = stackofKvalues.pop();
			  System.out.println("test2 x: "+pathA.get(0)+"y: "+pathA.get(1));
		  }
		  
		  if(!pathB.isEmpty() && pathA.isEmpty()) {
			  kvalueA = kvalueB;
			  pathA.clear();
			  pathA.addAll(pathB);
			  System.out.println("test3 x: "+pathA.get(0)+"y: "+pathA.get(1));
			  //pathB.clear();
		  }
		  	
			  										//if there is only one thing on stack we only want the first pop
		 
		  //if(pathB.size() < pathA.size()) {
		  if(pathB.size() < pathA.size() && !pathB.isEmpty() ) {
			  kvalueA = kvalueB;
			  pathA.clear();
			  pathA.addAll(pathB);
			  //pathB.clear();
		  }
		  
		  else if(pathA.size() == pathB.size() && !pathB.isEmpty()) {
			  if(kvalueB > kvalueA ) {											
			  }
			  else{																		
				  pathA.clear();
				  pathA.addAll(pathB);
				  kvalueA =kvalueB;
			  }
			 
		  }
		  }
		  
	  
	  int x1 = pathA.get(0);
	  int y = pathA.get(1);
	  Coordinate c = new Coordinate(x1,y);
	  //System.out.println("x: "+x1+"y: "+y);
	  pathA.clear();
	  return c;
	  
	  
  }
     
          	       
      
     
  
  
  @SuppressWarnings("unchecked")
public static Coordinate comparePaths(Stack<List> paths, Stack<Integer> kvalues) {
	  List<Integer> pathA = null;
	  List<Integer> pathB = null;
	  int kvalueA = 0;
	  int kvalueB =0;
	  System.out.println(paths.pop().size());
	  
	  while(!paths.isEmpty()) {
		  if( pathA == null) {
			  System.out.println(paths.pop().size());
			  //pathA = sysoutpaths.pop();
			  System.out.println(pathA.size());
			  kvalueA = kvalues.pop();
		  }
		  kvalueB = kvalues.pop();
		  pathB = paths.pop();
		  
		  if(kvalueB > kvalueA) {
			  kvalueA = kvalueB;
			  pathA.clear();
			  pathA.addAll(pathB);
		  }
		  
		  else if(kvalueB == kvalueA) {
			  if(pathB.size() > pathA.size()) {
				  
			  }
			  else{
				  pathA.clear();
				  pathA.addAll(pathB);
			  }
			 
		  }
		  
	  }
	  int x = pathA.get(pathA.size()-1);
	  int y = pathA.get(pathA.size()-2);
	  Coordinate c = new Coordinate(x,y);
	  return c;
	  
  }   
  }
 

