/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazesolver;

/**
 *
 * @author Malabooyah
 */
public class Coordinate {
    
    int x,y;
    
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString(){
        return ("(" + x + "," + y + ")");
    }
    
}
