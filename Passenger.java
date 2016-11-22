//keeps track of the passenger, currently just its destination
//later also time spent and such.
public class Passenger {
	private int destination;
	public Passenger(int dest){
		destination = dest;
	}
	
	public int getDestination(){
		return destination;
	}
}
