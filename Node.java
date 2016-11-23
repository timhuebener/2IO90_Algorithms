import java.util.ArrayList;

//keeps track of adjacent nodes and passengers in this node
public class Node {
	private int[] neighbors;
	private ArrayList<Passenger> passengers;
	
	public Node(int[] neighbors){
		this.neighbors = neighbors;
		passengers = new ArrayList<Passenger>();
	}
	
	public void addPassenger(Passenger noobie){
		passengers.add(noobie);
	}
	
	public int randomNeighbor(){
		return neighbors[(int)(Math.random()*neighbors.length)];
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
