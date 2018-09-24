import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class DefaultSail implements SailStrategy{
	
	/***********************************************
	 * Algorithm for default pirate ship movements *
	 ***********************************************/
	@Override
	public void sail(Ship ship, PirateShip pirateShip) {
		Point location = ship.getLocation();
		Point pirateLocation = pirateShip.getLocation();
		Random rand = new Random();
		
		/* Only move if you need to */
		if(!location.equals(pirateLocation)) {
			
			Point newPoint = null;
			
			/* Grab possible adjacent locations */
			LinkedList<Point> adj = BFS.getAdj(pirateShip.getLocation());
			
			/* Grab the 2D array of distances */
			int[][] bfsMap = BFS.getPath(pirateShip.getLocation(), ship.getLocation());
			int minDistance = Integer.MAX_VALUE;
			
			/* Look for smallest distance and set newPoint */ 
			for(Point point : adj) {
				int newDist = bfsMap[(int)point.getX()][(int)point.getY()];
				if(newDist < minDistance) {
					minDistance = newDist;
					newPoint = point;
				}
			}
			
			/* If a newPoint is set, call updateEverything() */
			if(newPoint != null) {
				pirateShip.updateEverything(newPoint);
			}
		}
	}
}
