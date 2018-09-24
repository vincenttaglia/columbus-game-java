import java.awt.Point;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Random;

import javafx.scene.image.ImageView;

public class MonsterGroup{
	private LinkedList<Monster> monsters = new LinkedList<Monster>();
	private Point groupCenter;
	private int groupSize = 5;
	private int numMonsters = 4;

	/**********************************
		* Constructor of Monster Group *
	***********************************/
	public MonsterGroup() {
		OceanMap oceanMap = OceanMap.getInstance();
		Random rand = new Random();
		
		/* groupSize must be odd */
		if(groupSize%2 != 1)
			groupSize--;
		
		/* groupSize can't be smaller than 3 */
		if(groupSize < 5)
			groupSize = 3;
		
		/* Monsters must be able to move freely */
		if(numMonsters >= groupSize*groupSize-groupSize*2)
			numMonsters = groupSize;
		
		/* Set how many spaces from center are in MonsterGroup boundary */
		int oneSide = groupSize/2;
		int x = rand.nextInt(oceanMap.getDimension());
		int y = rand.nextInt(oceanMap.getDimension());
		
		/* Must be enough room on grid to hold MonsterGroup */
		while(x <= oneSide || y <= oneSide 
				|| x >= oceanMap.getDimension()-oneSide-1 
				|| y >= oceanMap.getDimension()-oneSide-1) 
		{
			x = rand.nextInt(oceanMap.getDimension());
			y = rand.nextInt(oceanMap.getDimension());
		}
		groupCenter = new Point(x,y);
		
		/* Create Monsters */
		for(int i = 0; i < numMonsters; i++) {
			
			/* Get random position until position is valid */
			int mx = rand.nextInt(groupSize)+(int)groupCenter.getX()-oneSide;
			int my = rand.nextInt(groupSize)+(int)groupCenter.getY()-oneSide;
			Point newPoint = new Point(mx,my);
			while(hasMonster(newPoint)) {
				mx = rand.nextInt(groupSize)+(int)groupCenter.getX()-oneSide;
				my = rand.nextInt(groupSize)+(int)groupCenter.getY()-oneSide;
				newPoint = new Point(mx,my);
			}
			monsters.add(new Monster(this, newPoint));

		}

	}

	/******************************************
		*Moves the Monster Group by its center*
		*point. Center point is within a 3 by*
	  *3 grid. Moves all members of the group*
	*******************************************/
	public void move() {
		OceanMap oceanMap = OceanMap.getInstance();
		Random rand = new Random();
		Point oldPoint = groupCenter;
		int x = (int) oldPoint.getX();
		int y = (int) oldPoint.getY();
		int oneSide = groupSize/2;
		
		/* Move center random direction until direction is valid */
		while(groupCenter.equals(oldPoint)) {
			switch(rand.nextInt(4)) {
			case 0: //South
				if(y+1 <= oceanMap.getDimension()-oneSide) {
					groupCenter = new Point(x,y+1);
				}
				break;
			case 1: //East
				if(x+1 <= oceanMap.getDimension()-oneSide) {
					groupCenter = new Point(x+1,y);
				}
				break;
			case 2: //North
				if(y-1 >= oneSide) {
					groupCenter = new Point(x,y-1);
				}
				break;
			case 3: //West
				if(x-1 >= oneSide) {
					groupCenter = new Point(x-1,y);
				}
				break;
			}
		}
		
		
		/* Set new position */
		x = (int)groupCenter.getX();
		y = (int)groupCenter.getY();
		
		/* Move monsters */
		for(Monster monster : monsters) {
			monster.move();
		}
	}

	/************************************
		*Gets the Center Point Location*
	*************************************/
	public Point getLocation() {
		return groupCenter;
	}
	
	/*****************************
	 * Returns group size as int *
	 *****************************/
	public int getGroupSize() {
		return groupSize;
	}

	/*************************************
		* Checks point passed has Monster *
						  * On It *
	**************************************/
	public Boolean hasMonster(Point point) {
		for(Monster monster : monsters) {
			if(monster.getLocation().equals(point)) {
				return true;
			}
		}
		return false;
	}

	/***********************************************
		* Gets the Image View of the Monster Group *
		* Adds the view to a LinkedList and return *
						* That Linked List *
	************************************************/
	public LinkedList<ImageView> getViews(){
		LinkedList<ImageView> list = new LinkedList<ImageView>();
		for(Monster monster : monsters) {
			list.add(monster.getView());
		}
		return list;
	}
}
