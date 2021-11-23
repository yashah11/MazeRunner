package mazesolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
/**
*
* @author Aaron Argueta
* @author Yash Shah
*/
public class HardMazeGenerator {
	
	static Stack<int[][]> finalMazes = new Stack<>(); //this is where we are storing our 3 hardest mazes to compare later
	static int [][] mazeA;
	static int [][] mazeB;
	static List<Integer> pathA = new ArrayList<Integer>();
	static int max_iterations = 50;
	static int iterations=0;
	static int hill_counter =0;
	static Coordinate src = new Coordinate(0,0);
	static int dim = View.dim;
	
	
	
	public static int[][] makeHardmaze(int[][] maze, List<Integer> path){
		while (hill_counter < 1000) {
			maze = RandomMazeGenerator.maze();
			path.clear();
			DFS.runDFS(maze, src, path);
			while (path.size() == 0) {
				maze = RandomMazeGenerator.maze();
				DFS.runDFS(maze, src, path);
			}
			if(Arrays.deepEquals(mazeA, mazeB)) { //checks to see is MazeA is empty
				maze = cleanupMaze(maze);
				mazeA = new int [maze.length][];
				for (int r = 0; r<maze.length; r++) {
					mazeA[r] =maze[r].clone();	
				}
				System.out.println("-----------NEW HILL-----------");
				System.out.println(" ");
				pathA.addAll(path);

			}
			while(iterations < max_iterations) {
				for (int r = 0; r<mazeA.length; r++) {
					maze[r] =mazeA[r].clone();
				}
				maze = changeMaze(maze, path);
				mazeB = compareDFS_fringe(maze, mazeA, pathA);
				for (int r = 0; r<mazeB.length; r++) {
					mazeA[r] =mazeB[r].clone();
				}
				mazeB=null;
				++iterations;

			}
			iterations=0;
			++hill_counter;
			finalMazes.push(mazeA);
			mazeA = null;
			pathA.clear();
		}
		mazeA = compareDFS_fringe(finalMazes.pop(),finalMazes.pop());
		for (int i=0; i < hill_counter-2; i++) {
		mazeA = compareDFS_fringe(mazeA, finalMazes.pop());
		
		}
		return mazeA;
	}
	
	
	
	private static int[][] changeMaze(int[][] maze, List<Integer> path){
		
		
		int index;
		int x, y;
		Random rand=new Random();
		
		if(path.size()!= 0) {
		
		index = rand.nextInt(path.size()-3); //wont pick the last two on path because those two are the start state
		if(index % 2 != 0)
		{
			 index = index - 1;
		}
		
		y = path.get(index);
		x = path.get(index + 1);
		
		if (maze[x][y] != 9) { //that 9 means its goal state 
			
				maze[x][y] = 0;
			
		}
		}
		return maze;
		
	
	}
	private static int[][] compareDFS_length(int[][] mazeX, int[][] mazeY){
		List<Integer> pathX = new ArrayList<Integer>();
		List<Integer> pathY = new ArrayList<Integer>();
		DFS.runDFS(mazeX, src, pathX);
		DFS.runDFS(mazeY, src, pathY);
		int sizeX= pathX.size();
		int sizeY= pathY.size();
		if(sizeX > sizeY) {
			pathY.clear();
			pathA.clear();
			pathA.addAll(pathX);
			System.out.println("The final path size of the hardest maze is" + pathA.size());
			mazeX = cleanupMaze(mazeX);
			return mazeX;
		}
		else{
			pathA.clear(); 
			pathA.addAll(pathY);
			System.out.println("The final path size of the hardest maze is" + pathA.size());
			pathY.clear();
			mazeY = cleanupMaze(mazeY);
			return mazeY;
		}
	}
	
	private static int[][] compareDFS_fringe(int[][] mazeX, int[][] mazeY){
		List<Integer> pathX = new ArrayList<Integer>();
		List<Integer> pathY = new ArrayList<Integer>();
		DFS.runDFS(mazeX, src, pathX);
		int sizeX= DFS.sizeofStack;
		DFS.runDFS(mazeY, src, pathY);
		int sizeY= DFS.sizeofStack;
		
		
		if(sizeX > sizeY) {
			pathY.clear();
			pathA.clear();
			pathA.addAll(pathX);
			System.out.println("The final FRINGE size of the hardest maze is" + sizeX);
			mazeX = cleanupMaze(mazeX);
			return mazeX;
		}
		else{
			pathA.clear(); 
			pathA.addAll(pathY);
			System.out.println("The final FRINGE size of the hardest maze is" + sizeY);
			pathY.clear();
			mazeY = cleanupMaze(mazeY);
			return mazeY;
		}
	}
	private static int[][] compareDFS_length(int[][] mazeX, int[][] mazeA, List<Integer> path1){
			
			int sizeA, sizeX;
			List<Integer> pathX = new ArrayList<Integer>();
			
			sizeA= path1.size();
			System.out.println("size of path1 is: " +sizeA);
			mazeX = cleanupMaze(mazeX);
			DFS.runDFS(mazeX, src, pathX);
			//DepthFirst.searchPath(mazeX, 1, 1, pathX);
			sizeX = pathX.size();
			System.out.println("size of pathX is: " +sizeX);
			if(sizeA > sizeX) {
				pathX.clear();
				mazeA = cleanupMaze(mazeA);
				return mazeA;
			}
			else{
				pathA.clear(); 
				
				pathA.addAll(pathX);
				pathX.clear();
				mazeX = cleanupMaze(mazeX);
				return mazeX;
			}
			
	}
	private static int[][] compareDFS_fringe(int[][] mazeX, int[][] mazeA, List<Integer> path1){
		List<Integer> pathX = new ArrayList<Integer>();
		int sizeA, sizeX;
		DFS.runDFS(mazeX, src, pathX);
		sizeX= DFS.sizeofStack;
	
		DFS.runDFS(mazeA, src, path1);
		sizeA=DFS.sizeofStack;
		System.out.println("The fringe size of old maze is: " + sizeA);
		System.out.println("The fringe size of new maze is: " + sizeX);
		
		if(sizeX >= sizeA && pathX.size() > 0) {
			pathA.clear(); 
			pathA.addAll(pathX);
			pathX.clear();
			mazeX = cleanupMaze(mazeX);
			return mazeX;
			
		}
		else{
			pathX.clear();
			mazeA = cleanupMaze(mazeA);
			return mazeA;
		}
		
		
		
		
	}
	
	private static int[][] cleanupMaze(int[][] maze) {
		  
			for(int i= 0; i < dim; i++ ) {
				for(int j=0; j< dim; j ++) {
					if(maze[i][j] == 2) {
					//System.out.println("yes changed");
					maze[i][j]=0;
				 }
			 }
			} return maze;
		}
	
	


}
