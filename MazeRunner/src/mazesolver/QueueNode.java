package mazesolver;

public class QueueNode implements Comparable<Object>{
    
	public QueueNode parent;
    public Coordinate point;
    public double g,h,f;
    Coordinate prev;
    public int pathTotal;
   
    public QueueNode(Coordinate point, int pathTotal, Coordinate prev){
        this.point = point;
        this.pathTotal = pathTotal;
        this.prev = prev;
    }
    
    public QueueNode(QueueNode parent, Coordinate point, double g, double h) {
    	this.parent = parent;
    	this.point = point;
    	this.g = g;
    	this.h = h;
   
    }

	@Override
    public int compareTo(Object o) {
    	QueueNode that = (QueueNode) o;
    	this.f = (int)((this.g+this.h)-(that.g+that.h));
    	System.out.println(f);
    	return (int)((this.g+this.h)-(that.g+that.h));
    }
}