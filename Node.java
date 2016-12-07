import java.util.ArrayList;

//keeps track of adjacent nodes and passengers in this node
public class Node {
	private int[] neighbors, dist;
	private ArrayList<Passenger> passengers;
	
	public Node(int[] neighbors, int[] dist){
		this.neighbors = neighbors;
		passengers = new ArrayList<Passenger>();
		this.dist = dist;

	}
	
	public void addPassenger(Passenger noobie){
		passengers.add(noobie);
	}
	
	public void setDist(int dist, int index){
		this.dist[index] = dist;
	}
	
	public int getDist(int index){
		return dist[index];
	}
	public int getNext(int dest){
		for(int i = 0; i < neighbors.length; i++){
			if(Algorithm.network[neighbors[i]].getDist(dest) == getDist(dest)-1){
				return neighbors[i];
			}
		}
		return -1;
	}
	
	public int[] getNeighbors(){
		return neighbors;
	}
	public int randomNeighbor(){
		int temp = (int)(Math.random()*neighbors.length);
		//System.out.print(neighbors.length);
		return neighbors[temp];
	}
	
	//removes the passenger who has waited the longest and returns them
	public Passenger remove(int taxi, int dest){
		for(int i = 0; i < passengers.size();i++){
			if(passengers.get(i).getTaxi() == taxi && passengers.get(i).getDestination() == dest)
				return passengers.remove(i);
		}
		return null;
	}
	
	public boolean empty(){
		if(passengers.size() == 0)
			return true;
		return false;
	}

}