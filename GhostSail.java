import java.awt.Point;
import java.util.Random;

public class GhostSail implements SailStrategy{
	
	/***************************************
	 * Algorithm for ghost pirate movement *
	 ***************************************/
	@Override
	public void sail(Ship ship, PirateShip pirateShip) {
	    Point location = ship.getLocation();
	    Random rand = new Random();
	    
	    /* Find difference between x's and y's for ship and pirateShip */
	    int x = (int) pirateShip.getLocation().getX();
	    int y = (int) pirateShip.getLocation().getY();
	    int yDiff = (int) location.getY() - y;
	    int xDiff = (int) location.getX() - x;
	    Point newPoint;
	    boolean xWorseDiff = Math.abs(xDiff) > Math.abs(yDiff);
	    
	    /* If pirateShip is further from ship on the X axis */
	    if(xWorseDiff){
	    	
	      /* Set newPoint based on direction of ship */
	      if(xDiff > 0){
	        newPoint = new Point(x+1, y);
	      }
	      else{
	        newPoint = new Point(x-1, y);
	      }
	    }
	    else {
	      /* If pirateShip is further from ship on the Y axis */
	    	
	      /* Set newPoint based on direction of ship */
		  if(yDiff > 0){
		    newPoint = new Point(x, y+1);
		  }
		  else{
		    newPoint = new Point(x, y-1);
		  }
	    }
	    
	    /* If the ship is in the same location or 1/4 of the time, don't move */
	    if(pirateShip.getLocation().equals(ship.getLocation()) || rand.nextInt(4) == 0){
	      newPoint = pirateShip.getLocation();
	    }
	    pirateShip.updateEverything(newPoint);
	  }
	}
