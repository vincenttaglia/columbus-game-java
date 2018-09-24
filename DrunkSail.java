import java.awt.Point;
import java.util.Random;

public class DrunkSail implements SailStrategy{
	
	/****************************
	 * Algorithm for drunk ship *
	 ****************************/
	@Override
	public void sail(Ship ship, PirateShip pirateShip) {
		OceanMap oceanMap = OceanMap.getInstance();
		Random rand = new Random();
		SailStrategy defaultSail = new DefaultSail();
		int x = (int)pirateShip.getLocation().getX();
		int y = (int)pirateShip.getLocation().getY();
		
		/* 1 in 3 chance to do go a random direction instead of BFS */
		if(rand.nextInt(3) == 0) {
			
			Point newPoint = null;
			
			/* Choose random direction */
			switch(rand.nextInt(4)) {
			case 0: // East
				if(x+1 < oceanMap.getDimension() && oceanMap.getState(x+1, y) == 0) {
					newPoint = new Point(x+1,y);
				}
				break;
			case 1: // South
				if(y+1 < oceanMap.getDimension() && oceanMap.getState(x, y+1) == 0) {
					newPoint = new Point(x,y+1);
				}
				break;
			case 2: // West
				if(x-1 >= 0 && oceanMap.getState(x-1, y) == 0) {
					newPoint = new Point(x-1,y);
				}
				break;
			case 3: // North
				if(y-1 >= 0 && oceanMap.getState(x,y-1) == 0) {
					newPoint = new Point(x,y-1);
				}
				break;
			}
			if(newPoint != null) {
				
				/* If random direction chosen, update ship to new point */
				pirateShip.updateEverything(newPoint);
			}else {
				
				/* If random direction not possible, default to BFS */
				defaultSail.sail(ship, pirateShip);
			}
			
		}else {
			
			/* If 1/3 chance fails, default to BFS */
			defaultSail.sail(ship, pirateShip);
		}
	}

}
