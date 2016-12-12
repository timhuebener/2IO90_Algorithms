import java.util.ArrayList;

//occupants and location
public class Taxi {
	private int node, endSpot, startSpot, end;
	private int capacity;
	private ArrayList<Integer> path;
	private ArrayList<Passenger> occupants,task;

	public Taxi(int startingNode, int capacity) {
		node = startingNode;
		this.capacity = capacity;
		occupants = new ArrayList<Passenger>();
		path = new ArrayList<Integer>();
		task = new ArrayList<Passenger>();
	}

	private int checkFull(int i){
		int c = occupants.size();
		int max = Integer.MIN_VALUE;
		for(int j = 0; j <= i; j++){
			if(task.get(i) != null)
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
	public int testPathChange(int start, int end, int distance) {
		int dist;
		this.end = end;
		if (occupants.size()==capacity || capacity == 1) {//to simplify, if taxi is currently full add to end of path
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
			if (path.size() == 0)//to avoid null pointer issue in case of empty path
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
				/*
				 * currently doesn't work, checks if putting start at the end is at all efficient
				 */
				// if(2*Algorithm.network[path.get(path.size()-1)].getDist(start)
				// < startChange){
				// startChange =
				// 2*Algorithm.network[path.get(path.size()-1)].getDist(start);
				// startSpot = path.size();
				// }
				int endChange = Algorithm.network[start].getDist(end)// default position is right after the start
						+ Algorithm.network[end].getDist(path
						.get(startSpot)) - Algorithm.network[start].getDist(path
						.get(startSpot));
				endSpot = startSpot;
				boolean endLimit = true;
				for (int i = startSpot + 1; i < path.size() - 1; i++) {//put end destination after start destination being careful not to let the taxi go over capacity in between
					if(checkFull(i-1)>capacity-1){
						endLimit = false;
						break;
					}else if (Algorithm.network[start].getDist(end)
							+ Algorithm.network[end].getDist(path.get(i))- Algorithm.network[start].getDist(path.get(i)) <= endChange) {
						endChange = Algorithm.network[path.get(i - 1)]
								.getDist(end)
								+ Algorithm.network[end].getDist(path.get(i)) - - Algorithm.network[path.get(i - 1)].getDist(path.get(i));
						endSpot = i;
					}
				}
				if (endLimit && Algorithm.network[start].getDist(end) < endChange) {
					endChange = Algorithm.network[path.get(path.size() - 1)]
							.getDist(end);
					endSpot = path.size();
				}
				dist = startChange + endChange;//total added distance
			}
		}
		return (int)Math.pow((dist + getPathLength(-1,startSpot-1))/Math.pow(distance+2,Algorithm.alpha),2) + changedEf(start, end);// return change in distance + a weighting factor, the smaller it is, the better the taxi.
	}

	private int changedEf(int start, int end){
		int nEfficiency = 0;
		for(int i = 0; i < path.size();i++){
			int cDist = 0;
			int nDist = 0;
			if(task.get(i)!=null){
				int temp = -1;
				for(int k = i+1; k < path.size(); k++){
					if(path.get(k)==task.get(i).getDestination()&&task.get(k)==null)
						temp = k;
				}
				cDist = task.get(i).getTime()+getPathLength(-1,temp)+temp-1;
				nDist = cDist;
				if(i+1> endSpot)
					cDist+=2;
				for(int j = i+1; j < path.size() && j <= endSpot; j++){
					if(j==temp && j < startSpot){
						break;
					}else if(j == startSpot && j == endSpot){
						cDist += Algorithm.network[path.get(j-1)].getDist(start)+Algorithm.network[start].getDist(end)+Algorithm.network[end].getDist(path.get(j))-Algorithm.network[path.get(j-1)].getDist(path.get(j))+2;
						break;
					}else if(j==startSpot){
						cDist += Algorithm.network[path.get(j-1)].getDist(start)+Algorithm.network[start].getDist(path.get(j))-Algorithm.network[path.get(j-1)].getDist(path.get(j))+1;
					}else if(j==endSpot){
						cDist += Algorithm.network[path.get(j-1)].getDist(end)+Algorithm.network[end].getDist(path.get(j))-Algorithm.network[path.get(j-1)].getDist(path.get(j))+1;
						break;
					}else if(j==temp && j < endSpot){
						break;
					}
				}
				nEfficiency += Math.pow(cDist/Math.pow(Algorithm.network[path.get(i)].getDist(task.get(i).getDestination())+2,Algorithm.alpha), 2) - Math.pow(nDist/Math.pow(Algorithm.network[path.get(i)].getDist(task.get(i).getDestination())+2,Algorithm.alpha), 2);
			}
		}
		return nEfficiency;
	}

	private int getPathLength(int start, int end){
		if(path.size() == 0)
			return 0;
		int length = 0;
		if(start == -1){
			length = Algorithm.network[node].getDist(path.get(0));
			start++;
		}
		for (int i = start; i < end; i++){
			length += Algorithm.network[path.get(i)].getDist(path.get(i+1));
		}
		return length;
	}

	public void addToPath(int start, Passenger p, int taxi) {
		if(endSpot<startSpot)
			System.out.print("path error");
		path.add(endSpot, end);
		task.add(endSpot, null);
		path.add(startSpot, start);
		task.add(startSpot, p);
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
			if(task.get(0) == null)
				return -1;
			return task.get(0).getDestination();
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
				Algorithm.times.add(occupants.get(i).getTime());
				Algorithm.distances.add(occupants.get(i).getDistance());
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

	public void incrementTime(){
		for(int i = 0; i < occupants.size();i++){
			occupants.get(i).incrementTime();
		}
	}

}