package mazesolver;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import java.util.List;

public class DFS {
	   static int dim = View.dim;
	   //row and column locations of neighboring cells (up down left right)
	   static int rowNum[] = {-1, 1, 0, 0}; 
	   static int colNum[] = {0, 0, 1, -1}; 
	   static int sizeofStack;
	   
	    //-----------------------------------------Search Algorithms------------------------------------------------
	    
	    public static boolean cellValid(int r, int c){ //checks to see if current Coordinate is valid and within the boundaries of the matrix
	        return (r<dim && r>=0 && c<dim && c>=0);
	    }
	    
	    public static int runDFS(int[][] map, Coordinate src, List<Integer> path){ //takes in parameters of: generated map and our source cell in the matrix
	        //System.out.println(src.x + " " + src.y);
	        
	        boolean visited[][] = new boolean[dim][dim]; //visited boolean 2d array that is the same size as 2d map
	        Coordinate visit[][]=new Coordinate[dim][dim];
	        visit[0][0]=new Coordinate(0,0);
	        
	        Coordinate goal = new Coordinate(dim-1,dim-1); //our goal state
	        Stack<QueueNode> stack = new Stack<>();
	        QueueNode sn = new QueueNode(src,0,src);
	        ArrayList<Coordinate> pathHold = new ArrayList<>(); 
	        
	        visited[src.x][src.y] = true; //mark the start node as visited inside of the visited boolean 2d array
	        
	        stack.push(sn); 
	        
	        while(!stack.isEmpty()){ 
	            QueueNode current = stack.peek(); 
	            Coordinate c = current.point;
	            if(c.x == goal.x && c.y == goal.y){ //goal has been reached
	                
	               Stack<Coordinate> stacky = new Stack<Coordinate>();
	               int diana=dim-1;
	               int kyle = dim-1;
	               Coordinate end = new Coordinate(dim-1, dim-1);
	               stacky.push(end);
	               for(int hey = 0; hey< current.pathTotal; hey++){
	            	   //if error, add + 1 to current.pathTotal
	            	   stacky.push(visit[diana][kyle]);
	            	   int tempx = visit[diana][kyle].x;
	            	   int tempy = visit[diana][kyle].y;
	            	   diana=tempx;
	            	   kyle=tempy;
	               }
	            	   
	            	   path.clear();
	               	while(!stacky.empty()) {
	               		Coordinate hold = stacky.pop();
	               		int tempx = hold.x;
	               		int tempy = hold.y;
	               		path.add(tempy);
	               		path.add(tempx);
	               		
	               	}
	               	sizeofStack = stack.size();
	                return current.pathTotal;
	            }
	            
	            stack.pop(); 
	            
	            int row = 0;
	            int col = 0;
	            
	            for (int i = 0; i < 4; i++) { 
	                
	                row = c.x + rowNum[i]; 
	                col = c.y + colNum[i]; 
	               
	              if (cellValid(row, col) && map[row][col] == 1 && !visited[row][col]){ 

	                  // mark cell as visited and enqueue it 
	                  visited[row][col] = true; 
	                  visit[row][col] = new Coordinate(c.x, c.y);

	                  QueueNode Adjcell = new QueueNode(new Coordinate(row, col), current.pathTotal + 1, new Coordinate(row-rowNum[i], col-colNum[i])); 
	                  stack.push(Adjcell);
	              }
	            
	        } 
	            
	              pathHold.add(c);

	    } 
	     
	        return -1;
	        
	        }
	        
	    
}

