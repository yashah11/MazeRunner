package mazesolver;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
*
* @author Aaron Argueta
* @author Yash Shah
*/

public class FireMazeView extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final boolean Astar = false;
	public static double probOfF = .30;    
    private Maze maze = new Maze();
    public static Coordinate src = new Coordinate(0,0);
    public static Coordinate current = new Coordinate(0,0);
    public static Coordinate goal = new Coordinate(View.dim-1,View.dim-1);
    public static boolean amidead = false;
    private int pathIndex;
    
    public FireMazeView() {
        setTitle("Fire Maze Runner");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        maze = FireMaze.makeFireMaze(maze);
        FireMazeSolver.runFiresolver(maze, FireMaze.fireMaze, src);
        pathIndex = maze.path.size()-2;
    }

    

	@Override
    public void paint(Graphics g) {
        super.paint(g);
        
        g.translate(100, 50);
        
        // draw the maze
        for (int row = 0; row < maze.maze.length; row++) {
            for (int col = 0; col < maze.maze[0].length; col++) {
                Color color;
                switch (maze.maze[row][col]) {
                    case 0 : color = Color.BLACK; break;
                    case 9 : color = Color.RED; break;
                    case -666: color = Color.ORANGE; break;
                    case -665: color = Color.YELLOW; break;
                    default : color = Color.WHITE;
                }
                g.setColor(color);
                g.fillRect(30 * col, 30 * row, 30, 30);
                g.setColor(Color.BLACK);
                g.drawRect(30 * col, 30 * row, 30, 30);
            }
        }
        

        //draw the ball on path
        if(!amidead) {
        int pathY = maze.path.get(1);
        int pathX = maze.path.get(0);
        g.setColor(Color.BLUE);
        g.fillOval(pathX * 30, pathY * 30, 30, 30);
        }
        else {
        	int pathY = maze.path.get(2);
            int pathX = maze.path.get(3);
            g.setColor(Color.RED);
            g.fillOval(pathX * 30, pathY * 30, 30, 30);
        }
    }
    
    @Override
    protected void processKeyEvent(KeyEvent ke) {
    	try {
        if (ke.getID() != KeyEvent.KEY_PRESSED) {
            return;
        }
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
        	pathIndex += 2;
            
            if (pathIndex > maze.path.size() - 2) {
                pathIndex = maze.path.size() - 2;
                
            }
            
        }
        
        else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            pathIndex -= 2;
            int y = maze.path.get(pathIndex);
            int x = maze.path.get(pathIndex+1);
            current.x =x;
            current.y = y;
            if(!amidead) {
	        	  FireMazeSolver.runFiresolver(maze, FireMaze.fireMaze,current );
	        	  FireMaze.pickCellstoBurn(maze,probOfF);
	              pathIndex = maze.path.size()-2;
	              y = maze.path.get(pathIndex);
	              x = maze.path.get(pathIndex+1);
	              if(FireMaze.amIBurning(maze, y , x)) {
	                	FireMazeView.amidead=true;
	                }
            }  
        }
	          if (pathIndex < 0) {
	                pathIndex = 0;

	            }
    	}catch(Exception e) {
    		if(amidead) {
    			System.out.println("Im ON FIREEE!!!!!!!!!!");
    		}
    		else {
        	System.out.println("NO WAY OUT NOOOOOOO!!!");
    		}
        }
    	repaint(); 
    }
}