import java.util.ArrayList;

//keeps track of adjacent nodes and passengers in this node
public class Node {
	private int[] neighbors, dist;
	private int index;
	public ArrayList<Passenger> passengers;
	
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
	public int randomNeighbor(){
		int temp = (int)(Math.random()*neighbors.length);
		//System.out.print(neighbors.length);
		return neighbors[temp];
	}
	
	//removes the passenger who has waited the longest and returns them
	public Passenger remove(){
		return passengers.remove(0);
	}
	
	public boolean empty(){
		if(passengers.size() == 0)
			return true;
		return false;
	}

}
