//keeps track of the passenger, currently just its destination
//later also time spent and such.
public class Passenger {
	private int destination,taxi, time, distance;

	public Passenger(int node, int dest, int taxi) {
		destination = dest;
		this.taxi = taxi;
		time = 0;
		distance = Algorithm.network[node].getDist(dest);
	}

	public int getDestination() {
		return destination;
	}
	
	public int getTaxi(){
		return taxi;
	}
	
	public void incrementTime(){
		time++;
	}
	
	public int getTime(){
		return time;
	}
	
	public int getDistance(){
		return distance;
	}
}
