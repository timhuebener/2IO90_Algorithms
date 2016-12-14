import java.util.ArrayList;

//occupants and location
public class Taxi {
	private int node, endSpot, startSpot, end;
	private int capacity;
	private ArrayList<Integer> path;
	private ArrayList<Passenger> occupants, task;

	public Taxi(int startingNode, int capacity) {
		node = startingNode;
		this.capacity = capacity;
		occupants = new ArrayList<Passenger>();
		path = new ArrayList<Integer>();
		task = new ArrayList<Passenger>();
	}

	private int checkFull(int i) {
		int c = occupants.size();
		int max = Integer.MIN_VALUE;
		for (int j = 0; j <= i; j++) {
			if (task.get(i) != null)
				c += 1;
			else
				c += -1;
			if (c > max)
				max = c;
		}
		return c;
	}

	/**
	 * 
	 * @param start
	 *            pick up node for new passenger
	 * @param end
	 *            drop off node for new passenger
	 * @return
	 */

	private int findDistance(int start, int end, int i, int j){
		int distance = 0;
		if(path.size()==0){
			return (int) Math.pow((Algorithm.Network[node].getDist(start)+Algorithm.Network[start].getDist(end) + getPathLength(-1, startSpot - 1) + endSpot + 1)
					/ Math.pow(distance + 2, Algorithm.alpha), 2);
		}if(i == 0 && j == 0){
			return (Algorithm.Network[node].getDist(start) + Algorithm.Network[start]
					.getDist(end)) + Algorithm.Network[end]
							.getDist(path.get(0)) - Algorithm.Network[node].getDist(path.get(0));
		}if(i == path.size()){
			return (Algorithm.Network[path.get(i-1)].getDist(start) + Algorithm.Network[start]
					.getDist(end));
		}if(i == j){
			return (Algorithm.Network[path.get(i-1)].getDist(start) + Algorithm.Network[start]
					.getDist(end)) + Algorithm.Network[end]
							.getDist(path.get(i)) - Algorithm.Network[path.get(i-1)].getDist(path.get(i));
		}if (i == 0){
			distance+=(Algorithm.Network[node].getDist(start) + Algorithm.Network[start]
					.getDist(path.get(0))) - Algorithm.Network[node].getDist(path.get(0));
		}else{
			distance+=(Algorithm.Network[path.get(i-1)].getDist(start) + Algorithm.Network[start]
					.getDist(path.get(i))) - Algorithm.Network[path.get(i-1)].getDist(path.get(i));
		}if(j == path.size()){
			distance+=(Algorithm.Network[path.get(j-1)].getDist(end));
		
		}else{
			distance+=(Algorithm.Network[path.get(j-1)].getDist(end) + Algorithm.Network[end]
					.getDist(path.get(j))) - Algorithm.Network[path.get(j-1)].getDist(path.get(j));
		}return distance;
			
	}
	public int testEfChange(int start, int end, int distance){
		this.end = end;
		startSpot = path.size();
		endSpot = path.size();
		int change = changedEf(start, end, findDistance(start, end, startSpot, endSpot), distance);
		for(int i = 0; i <= path.size();i++){
			if(checkFull(i-1)<=capacity){
				for(int j = i; j <= path.size();j++){
					if(checkFull(j-1)+1 <= capacity){
						int tempChange = changedEf(start, end, findDistance(start, end, i, j), distance);
						if(tempChange < change ){
							change = tempChange;
							startSpot = i;
							endSpot = j;
						}
					}else{
						break;
					}
				}
			}
		}
		return change;
	}

	private int changedEf(int start, int end, int dist, int distance) {
		int nEfficiency = 0;
		for (int i = 0; i < path.size(); i++) {
			int cDist = 0;
			int nDist = 0;
			if (task.get(i) != null) {
				int temp = -1;
				for (int k = i + 1; k < path.size(); k++) {
					if (path.get(k) == task.get(i).getDestination()
							&& task.get(k) == null)
						temp = k;
				}
				cDist = task.get(i).getTime() + getPathLength(-1, temp) + temp
						- 1;
				nDist = cDist;
				if (i >= endSpot)
					cDist += 2 + dist;
				else if (i >= startSpot && startSpot > 0)
					cDist += Algorithm.Network[path.get(startSpot - 1)]
							.getDist(start)
							+ Algorithm.Network[start].getDist(path
									.get(startSpot))
							- Algorithm.Network[path.get(startSpot - 1)]
									.getDist(path.get(startSpot)) + 1;
				else if (i >= startSpot)
					cDist += Algorithm.Network[node].getDist(start)
							+ Algorithm.Network[start].getDist(path
									.get(startSpot))
							- Algorithm.Network[node].getDist(path
									.get(startSpot)) + 1;
				for (int j = i; j < path.size() && j <= endSpot; j++) {
					if (startSpot == 0 && j == 0) {
						if (j == startSpot && j == endSpot) {
							cDist += Algorithm.Network[node].getDist(start)
									+ Algorithm.Network[start].getDist(end)
									+ Algorithm.Network[end].getDist(path
											.get(j))
									- Algorithm.Network[node].getDist(path
											.get(j)) + 2;
							break;
						} else if (j == startSpot) {
							cDist += Algorithm.Network[node].getDist(start)
									+ Algorithm.Network[start].getDist(path
											.get(j))
									- Algorithm.Network[node].getDist(path
											.get(j)) + 1;
						}
					} else {
						if (j == temp && j < startSpot) {
							break;
						} else if (j == startSpot && j == endSpot) {
							cDist += Algorithm.Network[path.get(j - 1)]
									.getDist(start)
									+ Algorithm.Network[start].getDist(end)
									+ Algorithm.Network[end].getDist(path
											.get(j))
									- Algorithm.Network[path.get(j - 1)]
											.getDist(path.get(j)) + 2;
							break;
						} else if (j == startSpot) {
							cDist += Algorithm.Network[path.get(j - 1)]
									.getDist(start)
									+ Algorithm.Network[start].getDist(path
											.get(j))
									- Algorithm.Network[path.get(j - 1)]
											.getDist(path.get(j)) + 1;
						} else if (j == endSpot) {
							cDist += Algorithm.Network[path.get(j - 1)]
									.getDist(end)
									+ Algorithm.Network[end].getDist(path
											.get(j))
									- Algorithm.Network[path.get(j - 1)]
											.getDist(path.get(j)) + 1;
							break;
						} else if (j == temp && j < endSpot) {
							break;
						}
					}
				}
				nEfficiency += Math.pow(
						cDist
								/ Math.pow(
										Algorithm.Network[path.get(i)]
												.getDist(task.get(i)
														.getDestination()) + 2,
										Algorithm.alpha), 2)
						- Math.pow(
								nDist
										/ Math.pow(Algorithm.Network[path
												.get(i)].getDist(task.get(i)
												.getDestination()) + 2,
												Algorithm.alpha), 2);
				if(nEfficiency<0)
					System.out.println("efficiency calculation error");
			}
		}
		nEfficiency += (int) Math.pow((dist + getPathLength(-1, startSpot - 1) + endSpot + 1)
				/ Math.pow(distance + 2, Algorithm.alpha), 2);
		return nEfficiency;
	}

	private int getPathLength(int start, int end) {
		if (path.size() == 0)
			return 0;
		int length = 0;
		if (start == -1) {
			length = Algorithm.Network[node].getDist(path.get(0));
			start++;
		}
		for (int i = start; i < end; i++) {
			length += Algorithm.Network[path.get(i)].getDist(path.get(i + 1));
		}
		return length;
	}

	public void addToPath(int start, Passenger p, int taxi) {
		if (endSpot < startSpot)
			System.out.print("path error");
		path.add(endSpot, end);
		task.add(endSpot, null);
		path.add(startSpot, start);
		task.add(startSpot, p);
//		 System.out.print("taxi "+ taxi + ": ");
//		 for(int i = 0; i < path.size();i++){
//		 System.out.print(path.get(i) + ", ");
//		 }
//		 System.out.println();
//		 System.out.print("taxi "+ taxi + ": ");
//		 for(int i = 0; i < task.size();i++){
//		 System.out.print(task.get(i) + ", ");
//		 }
//		 System.out.println();

	}

	public void moveTo(int newNode) {
		node = newNode;
	}

	public int moveAlong() {
		if (path.size() > 0)
			moveTo(Algorithm.Network[node].getNext(path.get(0)));
		return node;
	}

	public int atDest() {
		if (path.size() > 0 && node == path.get(0)) {
			if (task.get(0) == null)
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
			System.out.println("error: taxi is over capacity :" + (i + 1));
	}

	// removes the first passenger whose destination has been reached then
	// returns true
	public boolean drop(int taxi) {
		for (int i = 0; i < occupants.size(); i++) {
			if (occupants.get(i).getDestination() == node) {
				Algorithm.Times.add(occupants.get(i).getTime());
				Algorithm.Distances.add(occupants.get(i).getDistance());
				occupants.remove(i);
				task.remove(0);
				path.remove(0);
//				 System.out.print("taxi "+ (taxi+1) + ": ");
//				 for(int j = 0; j < path.size();j++){
//				 System.out.print(path.get(j) + ", ");
//				 }
//				 System.out.println();
//				 System.out.print("taxi "+ (taxi+1) + ": ");
//				 for(int j = 0; j < task.size();j++){
//				 System.out.print(task.get(j) + ", ");
//				 }
//				 System.out.println();
				return true;
			}
		}
		System.out.println("error, passenger not found" + (taxi + 1));
		return false;
	}

	public boolean empty() {
		if (occupants.size() == 0) {
			return true;
		}
		return false;
	}

	public void incrementTime() {
		for (int i = 0; i < occupants.size(); i++) {
			occupants.get(i).incrementTime();
		}
	}

}