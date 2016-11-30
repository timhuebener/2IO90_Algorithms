//keeps track of the passenger, currently just its destination
//later also time spent and such.
public class Passenger {
	private int destination;
	private int position;
	private int pickUpTaxi;

	public Passenger(int dest, int pos, int taxi){
		destination = dest;
		position = pos;
		pickUpTaxi = taxi;
	}
	
	public int getDestination(){
		return destination;
	}

	public int getPosition(){ return position;}

	public void  choosePickUpTaxi(int p){
		pickUpTaxi = p;
	}

	public int getPickUpTaxi(){
		return pickUpTaxi;
	}
}
