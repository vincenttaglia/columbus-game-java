import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

/*************************************
 * We created the static BFS class   *
 * to be able to access BFS anywhere *
 *************************************/
public final class BFS {
	Point beginning;
	Point end;
	int[][] bfsMap; 
	LinkedList<Point> visited;
	Queue<bfsTuple> queue;
	
	/*********************************************
	 * Get BFS grid from beginning and end point *
	 *********************************************/
	public static int[][] getPath(Point beginning, Point end) {
		
		OceanMap oceanMap = OceanMap.getInstance();
		int[][] bfsMap = new int[oceanMap.getDimension()][oceanMap.getDimension()];
		
		/* Create a map of MAX_VALUE for finding min distance */
		for(int x=0;x<oceanMap.getDimension();x++) {
			for(int y=0;y<oceanMap.getDimension();y++) {
				bfsMap[x][y] = Integer.MAX_VALUE;
			}
		}
		
		LinkedList<Point> visited = new LinkedList<Point>();
		Queue<bfsTuple> queue = new LinkedList<bfsTuple>();
		
		/* Add end point to visited and queue, with beginning distance of 0 */
		visited.add(end);
		queue.add(new bfsTuple(end, 0));
		
		/* Find adjacent points for every point in queue and add them to queue if not visited */
		while(!queue.isEmpty()) {
			bfsTuple cur = queue.poll();
			bfsMap[cur.getX()][cur.getY()] = cur.getDistance();
			LinkedList<Point> adj = getAdj(cur.getPoint());
			for(Point pt : adj) {
				if(!visited.contains(pt)) {
					visited.add(pt);
					queue.add(new bfsTuple(pt,cur.getDistance()+1));
				}
				
			}
		}
		
		
		return bfsMap;
	}
	
	/************************************************
	 * Get valid adjacent points from specific point*
	 ************************************************/
	public static LinkedList<Point> getAdj(Point point) {
		LinkedList<Point> validAdj = new LinkedList<Point>();
		OceanMap oceanMap = OceanMap.getInstance();
		int x = (int)point.getX();
		int y = (int)point.getY();
		
		/* Check south */
		if(oceanMap.getState(x,y+1) == 0) {
			validAdj.add(new Point(x,y+1));
		}
		
		/* Check north */
		if(oceanMap.getState(x,y-1) == 0) {
			validAdj.add(new Point(x,y-1));
		}
		
		/* Check east */
		if(oceanMap.getState(x+1,y) == 0) {
			validAdj.add(new Point(x+1,y));
		}
		
		/* Check north */
		if(oceanMap.getState(x-1,y) == 0) {
			validAdj.add(new Point(x-1,y));
		}
		
		return validAdj;
	}
}
