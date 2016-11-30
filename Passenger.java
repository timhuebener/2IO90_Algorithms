//keeps track of the passenger, currently just its destination
//later also time spent and such.
public class Passenger {
	private int destination;
	private int position;

	public Passenger(int dest, int pos){
		destination = dest;
		position = pos;
	}
	
	public int getDestination(){
		return destination;
	}

	public int getPosition(){ return position;}
}
