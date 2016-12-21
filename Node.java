import java.util.ArrayList;

//keeps track of adjacent nodes and passengers in this node
public class Node {
	private int[] neighbors, dist;
	private int self;
	private ArrayList<Passenger> passengers;
	
	
	public Node(int[] neighbors, int[] dist, int self){
		this.neighbors = neighbors;
		passengers = new ArrayList<Passenger>();
		this.dist = dist;
		this.self = self;
	}
	
	public void addPassenger(Passenger noobie){
		passengers.add(noobie);
	}
	
	public void setDist(int dist, int index){
		this.dist[index] = dist;
	}
	private void bfs(){
        //root is first node 
		while(Algorithm.bfs.size() !=0){
		int node = Algorithm.bfs.get(0);
		Algorithm.bfs.remove(0);
		for(int i=0; i<Algorithm.Network[node].getNeighbors().length;i++){
			//for all neighbours
            if( dist[Algorithm.Network[node].getNeighbors()[i]] == Integer.MAX_VALUE / 10){
                //if we have no distance from the root to the neighbour(if distance is infinity)
            	dist[Algorithm.Network[node].getNeighbors()[i]] = dist[node]+1;
                //distance root to neigbour is distance current node + 1
                Algorithm.bfs.add(Algorithm.Network[node].getNeighbors()[i]);
            }
		}
        }
    }
	
	public int getDist(int index){
		if(dist[index]==Integer.MAX_VALUE/10){
			Algorithm.bfs.add(self);
			bfs();
		}
		return dist[index];
	}
	public int getNext(int dest){
		for(int i = 0; i < neighbors.length; i++){
			if(Algorithm.Network[neighbors[i]].getDist(dest) == getDist(dest)-1){
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
	
	public void incrementTime(){
		for(int i = 0; i < passengers.size();i++){
			passengers.get(i).incrementTime();
		}
	}

}