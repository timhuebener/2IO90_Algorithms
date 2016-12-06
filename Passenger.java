//keeps track of the passenger, currently just its destination
//later also time spent and such.
public class Passenger {
	private int destination,taxi;

	public Passenger(int dest, int taxi) {
		destination = dest;
		this.taxi = taxi;
	}

	public int getDestination() {
		return destination;
	}
	
	public int getTaxi(){
		return taxi;
	}
}
