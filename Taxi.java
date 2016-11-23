import java.util.ArrayList;

//occupants and location
public class Taxi {
	private int node;
	private int capacity;
	private ArrayList<Passenger> occupants;
	
	public Taxi(int startingNode, int capacity){
		node = startingNode;
		this.capacity = capacity;
		occupants = new ArrayList<Passenger>();
	}
	
	public void moveTo(int newNode){
		node = newNode;
	}
	
	public int location(){
		return node;
	}
	
	public int getNode(){
		return node;
	}
	
	public boolean full(){
		if(occupants.size() == capacity){
			return true;
		}
		return false;
	}
	//picks up a new passenger
	public void pickUp(Passenger p){
		if(occupants.size()< capacity)
			occupants.add(p);
		else
			System.out.println("error: taxi is over capacity");
	}
	
	//removes the first passenger whose destination has been reached then returns true
	public boolean drop(){
		for(int i = 0; i < occupants.size(); i++){
			if(occupants.get(i).getDestination() == node){
				occupants.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean empty(){
		if(occupants.size() == 0){
			return false;
		}
		return true;
	}

}