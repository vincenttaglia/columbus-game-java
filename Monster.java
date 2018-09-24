import java.awt.Point;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Monster{
	private MonsterGroup monsterGroup;
	private Point monsterPoint;
	private ImageView monsterImageView;


	/***********************************************************
						Constructor for the Monster Class
	************************************************************/
	public Monster(MonsterGroup monsterGroup, Point monsterPoint) {
		this.monsterGroup = monsterGroup;
		this.monsterPoint = monsterPoint;
		OceanMap oceanMap = OceanMap.getInstance();
		Image monsterImage = new Image("/birds.png",oceanMap.getScale(), oceanMap.getScale(), true,true);
		monsterImageView = new ImageView(monsterImage);
		monsterImageView.setX(monsterPoint.getX()*oceanMap.getScale());
		monsterImageView.setY(monsterPoint.getY()*oceanMap.getScale());
	}


	/************************************************************
	 * Moves monster to new random location within monsterGroup *
	 ************************************************************/
	public void move() {
		Point groupCenter = monsterGroup.getLocation();
		Point oldMonsterPoint = monsterPoint;
		OceanMap oceanMap = OceanMap.getInstance();
		Random rand = new Random();
		int x = (int) oldMonsterPoint.getX();
		int y = (int) oldMonsterPoint.getY();
		
		/* Set number of spaces available from the center */
		int oneside = monsterGroup.getGroupSize()/2;
		int times = 0;
		
		/* Attempt to go a random direction until direction is valid or you've tried 10 times */
		while(monsterPoint.equals(oldMonsterPoint) && times < 10) {
			switch(rand.nextInt(4)) {
			case 0: //South
				times++;
				if(y+1 <= groupCenter.getY()+oneside 
						&& y+1 >= groupCenter.getY()-oneside
						&& x <= groupCenter.getX()+oneside
						&& x >= groupCenter.getX()-oneside
						&& !monsterGroup.hasMonster(new Point(x,y+1)))
				{
					monsterPoint = new Point(x,y+1);
				}
				break;
			case 1: //East
				times++;
				if(x+1 <= groupCenter.getX()+oneside
						&& x+1 >= groupCenter.getX()-oneside
						&& y <= groupCenter.getY()+oneside
						&& y >= groupCenter.getY()-oneside
						&& !monsterGroup.hasMonster(new Point(x+1,y)))
				{
					monsterPoint = new Point(x+1,y);
				}
				break;
			case 2: //North
				times++;
				if(y-1 <= groupCenter.getY()+oneside
						&& y-1 >= groupCenter.getY()-oneside
						&& x <= groupCenter.getX()+oneside
						&& x >= groupCenter.getX()-oneside
						&& !monsterGroup.hasMonster(new Point(x,y-1)))
				{
					monsterPoint = new Point(x,y-1);
				}
				break;
			case 3: //West
				times++;
				if(x-1 <= groupCenter.getX()+oneside
						&& x-1 >= groupCenter.getX()-oneside
						&& y <= groupCenter.getY()+oneside
						&& y >= groupCenter.getY()-oneside
						&& !monsterGroup.hasMonster(new Point(x-1,y)))
				{
					monsterPoint = new Point(x-1,y);
				}
				break;
			}
		}
		
		/* If can't find a possible movement, find location that is closest to monster and set as newPoint */
		if(times == 10) {
			int totaldistance = Integer.MAX_VALUE;
			Point newPoint = null;
			for(int i = (int)monsterGroup.getLocation().getX()-oneside;i<monsterGroup.getLocation().getX()+oneside;i++) {
				for(int n = (int)monsterGroup.getLocation().getY()-oneside;n<monsterGroup.getLocation().getY()+oneside;n++) {
					int xdist = (int)monsterPoint.getX() - i;
					int ydist = (int)monsterPoint.getY() - n;
					Point testPoint = new Point(i,n);
					int totaldifference = Math.abs(xdist)+Math.abs(ydist);
					if(totaldifference < totaldistance && !monsterGroup.hasMonster(testPoint)) {
						totaldistance = totaldifference;
						newPoint = testPoint;
					}
				}
			}
			monsterPoint = newPoint;
		}
		monsterImageView.setX(monsterPoint.getX()*oceanMap.getScale());
		monsterImageView.setY(monsterPoint.getY()*oceanMap.getScale());

	}
	
	/*******************************
	 * Returns location as a Point *
	 *******************************/
	public Point getLocation() {
		return monsterPoint;
	}
	
	/************************
	 * Returns monster view *
	 ************************/
	public ImageView getView() {
		return monsterImageView;
	}
}
