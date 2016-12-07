import java.util.ArrayList;

//occupants and location
public class Taxi {
	private int node, endSpot, startSpot, end;
	private int capacity;
	private ArrayList<Integer> path, task;
	private ArrayList<Passenger> occupants;

	public Taxi(int startingNode, int capacity) {
		node = startingNode;
		this.capacity = capacity;
		occupants = new ArrayList<Passenger>();
		path = new ArrayList<Integer>();
		task = new ArrayList<Integer>();
	}

	private int checkFull(int i){
		int c = occupants.size();
		int max = Integer.MIN_VALUE;
		for(int j = 0; j <= i; j++){
			if(task.get(i) > -1)
				c+=1;
			else
				c+=-1;
			if(c>max)
				max = c;
		}
		return c;
	}
	/**
	 * 
	 * @param start pick up node for new passenger
	 * @param end drop off node for new passenger
	 * @return
	 */
	public int testPathChange(int start, int end) {
		int dist;
		this.end = end;
		if (occupants.size() == capacity) {//to simplify, if taxi is currently full add to end of path
			startSpot = 0;
			endSpot = 0;
			if (path.size() == 0)
				dist = (Algorithm.network[node].getDist(start)
						+ Algorithm.network[start].getDist(end))/2;//the div 2 is a weighting factor since no path is good
			else{
				dist = Algorithm.network[path.get(path.size() - 1)]
						.getDist(start) + Algorithm.network[start].getDist(end);
				startSpot = path.size();
				endSpot = path.size();
			}
		} else {// find best spot to put it
			startSpot = 0;
			endSpot = 0;
			if (path.size() == 0)
				dist = (Algorithm.network[node].getDist(start)
						+ Algorithm.network[start].getDist(end))/2;
			else {
				
				int startChange = Algorithm.network[node].getDist(start)
						+ Algorithm.network[start].getDist(path.get(0)) - Algorithm.network[node].getDist(0);
				startSpot = 0;
				for (int i = 1; i < path.size() - 1; i++) {//try putting start in between any of the destinations, save the shortest one 
					if (Algorithm.network[path.get(i - 1)].getDist(start)
							+ Algorithm.network[start].getDist(path.get(i)) - Algorithm.network[path.get(i - 1)].getDist(path.get(i)) <= startChange && !(checkFull(i-1)>=capacity)) {
						startChange = Algorithm.network[path.get(i - 1)].getDist(start)
								+ Algorithm.network[start].getDist(path.get(i)) - Algorithm.network[path.get(i - 1)].getDist(path.get(i));
						startSpot = i;
					}
				}
				// if(2*Algorithm.network[path.get(path.size()-1)].getDist(start)
				// < startChange){
				// startChange =
				// 2*Algorithm.network[path.get(path.size()-1)].getDist(start);
				// startSpot = path.size();
				// }
				int endChange = Algorithm.network[start].getDist(end)
						+ Algorithm.network[end].getDist(path
								.get(startSpot)) - Algorithm.network[start].getDist(path
										.get(startSpot));
				endSpot = startSpot;
				boolean temp = true;
				for (int i = startSpot + 1; i < path.size() - 1; i++) {//put end destination after start destination being careful not to let the taxi go over capacity in between
					if(checkFull(i-1)>capacity-1){
						temp = false;
						break;
					}else if (Algorithm.network[start].getDist(end)
							+ Algorithm.network[end].getDist(path.get(i))- Algorithm.network[start].getDist(path.get(i)) <= endChange && !(checkFull(i-1)+1>capacity)) {
						endChange = Algorithm.network[path.get(i - 1)]
								.getDist(end)
								+ Algorithm.network[end].getDist(path.get(i)) - - Algorithm.network[path.get(i - 1)].getDist(path.get(i));
						endSpot = i;
					}
				}
				if (temp && Algorithm.network[start].getDist(end) < endChange && !(checkFull(path.size()-1)>=capacity-1)) {
					endChange = Algorithm.network[path.get(path.size() - 1)]
							.getDist(end);
					endSpot = path.size();
				}
				dist = startChange + endChange;
			}
		}
		return dist + weightedPathLength();//return length or change + weighting factor
	}
	
	private int weightedPathLength(){
		return path.size()*4; //temporary
	}
	public void addToPath(int start, int end, int taxi) {
		if(endSpot<startSpot)
			System.out.print("path error");
		path.add(endSpot, end);
		task.add(endSpot, -1);
		path.add(startSpot, start);
		task.add(startSpot, end);
//		System.out.print("taxi "+ taxi + ": ");
//		for(int i = 0; i < path.size();i++){
//			System.out.print(path.get(i) + ", ");
//		}
//		System.out.println();
//		System.out.print("taxi "+ taxi + ": ");
//		for(int i = 0; i < task.size();i++){
//			System.out.print(task.get(i) + ", ");
//		}
//		System.out.println();

	}

	public void moveTo(int newNode) {
		node = newNode;
	}

	public int moveAlong() {
		if (path.size() > 0)
			moveTo(Algorithm.network[node].getNext(path.get(0)));
		return node;
	}

	public int atDest() {
		if (path.size()>0 && node == path.get(0)) {
			return task.get(0);
		}
		return -2;
	}

	public int location() {
		return node;
	}

	public int getNode() {
		return node;
	}

	public boolean full() {
		if (occupants.size() == capacity) {
			return true;
		}
		return false;
	}

	// picks up a new passenger
	public void pickUp(Passenger p, int i) {
		if (occupants.size() < capacity) {
			task.remove(0);
			path.remove(0);
			occupants.add(p);
		} else
			System.out.println("error: taxi is over capacity :" + (i +1) );
	}

	// removes the first passenger whose destination has been reached then
	// returns true
	public boolean drop(int taxi) {
		for (int i = 0; i < occupants.size(); i++) {
			if (occupants.get(i).getDestination() == node) {
				occupants.remove(i);
				task.remove(0);
				path.remove(0);
//				System.out.print("taxi "+ (taxi+1) + ": ");
//				for(int j = 0; j < path.size();j++){
//					System.out.print(path.get(j) + ", ");
//				}
//				System.out.println();
//				System.out.print("taxi "+ (taxi+1) + ": ");
//				for(int j = 0; j < task.size();j++){
//					System.out.print(task.get(j) + ", ");
//				}
//				System.out.println();
				return true;
			}
		}
		System.out.println("error, passenger not found" + (taxi+1));
		return false;
	}

	public boolean empty() {
		if (occupants.size() == 0) {
			return true;
		}
		return false;
	}

}