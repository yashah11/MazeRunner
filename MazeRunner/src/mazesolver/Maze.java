package mazesolver;

import java.util.*;

/**
*
* @author Aaron Argueta
* @author Yash Shah
*/

public class Maze {
	
	public int pathlength;
	public int sizeOfStack;
	public int nodesExpanded;
	public int[][] maze;
	public List<Integer> path = new ArrayList<>();
	public List<Integer> listOfBlocks = new ArrayList<>();
	public List<Coordinate> listOfBlocksOnFire = new ArrayList<>();
	
	
}
