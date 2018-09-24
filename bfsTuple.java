import java.awt.Point;

/***************************************
 * A tuple class created for BFS queue *
 ***************************************/
public class bfsTuple {
	private int distance;
	private Point point;
	
	/************************
	 * bfsTuple Constructor *
	 ************************/
	bfsTuple(Point point, int distance){
		this.distance = distance;
		this.point = point;
	}
	
	/****************************
	 * Returns point from tuple *
	 ****************************/
	public Point getPoint() {
		return point;
	}
	
	/*******************************
	 * Returns distance from tuple *
	 *******************************/
	public int getDistance() {
		return distance;
	}
	
	/******************************************
	 * Returns point.getX() from tuple as int *
	 ******************************************/
	public int getX() {
		return (int)point.getX();
	}
	
	/******************************************
	 * Returns point.getY() from tuple as int *
	 ******************************************/
	public int getY() {
		return (int)point.getY();
	}
	
}
