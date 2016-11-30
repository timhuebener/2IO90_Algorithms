import java.util.ArrayList;

//occupants and location
public class Taxi {
	private int node;
	private int capacity;
	private int passengersIn=0;
	private ArrayList<Passenger> occupants;
	public ArrayList<Integer> Path = new ArrayList<>();
	
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
		if(occupants.size() == passengersIn){
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
		passengersIn++;
	}
	
	//removes the first passenger whose destination has been reached then returns true
	public boolean drop(){
		for(int i = 0; i < occupants.size(); i++){
			if(occupants.get(i).getDestination() == node){
				occupants.remove(i);
				passengersIn--;
				return true;
			}
		}
		return false;

	}

	public int getPassengersIn(){
		return passengersIn;
	}
	
	public boolean empty(){
		if(occupants.size() == 0){
			return true;
		}
		return false;
	}
	public void addPath(int i){Path.add(i);}

    public void printPath(){
        for(int i = 0; i < Path.size(); i++) {
            System.out.print(Path.get(i));
        }
    }
}
