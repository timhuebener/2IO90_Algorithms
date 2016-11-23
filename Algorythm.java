
//main algorythm class for dumbAlgorythm
public class Algorythm {

	//all those variables in the beggining
	TaxiScanner scanner;
	int linesLeft;
	float alpha;
	int maxTime;
	Taxi[] taxis;//array containing all taxis
	int capacity;
	Node[] network;//array of nodes representing the network
	int training;
	int totalCalls;
	
	public Algorythm(){
		//-----------------------reads input using TaxiScanner class------------------------
		scanner = TaxiScanner.getInstance();
		linesLeft = Integer.parseInt(scanner.nextLine());
		alpha = Float.parseFloat(scanner.nextLine());
		maxTime = Integer.parseInt(scanner.nextLine());
		String temp = scanner.nextLine();
		//substring includes character at index 0 but not at index 1 so this would be the first character in the string
		taxis= new Taxi[Integer.parseInt(temp.substring(0,1))];
		capacity = Integer.parseInt(temp.substring(2,3));
		network = new Node[Integer.parseInt(scanner.nextLine())];
		for(int i = 0;i < network.length;i++){//creates each node with its correct neighbors
			temp = scanner.nextLine();
			int[] neighbors = new int[Integer.parseInt(temp.substring(0,1))];
			for(int j = 2; j < temp.length()-2; j+=2){//+= 2 to skip the next space
				neighbors[j/2] = Integer.parseInt(temp.substring(j,j+1));
			}
			network[i] = new Node(neighbors);
		}
		temp = scanner.nextLine();
		training = Integer.parseInt(temp.substring(0,1));
		totalCalls = Integer.parseInt(temp.substring(2,3));
		//set starting points for taxis in this case, all node 0
		for(int i = 0; i < taxis.length;i++){
			taxis[i] = new Taxi(0,capacity);
			System.out.println("m " + i + " " + 0 + " ");
		}
        System.out.println("c");
		//------------------------------------------------------------------------------
		//main loop, every loop represents a minute
		while(!done()){
			if(scanner.hasNextLine()){//add passengers to nodes
				temp = scanner.nextLine();
				for(int i = 2; i < temp.length()-2; i+=4){//skip # of new people since can be derived from temp.length and 4 at a time due to 2 numbers and 2 spaces
					//this is a rough one, add a passenger to the node equal to the fist number with a destination equal to the second number
					network[Integer.parseInt(temp.substring(i,i+1))].addPassenger(new Passenger(Integer.parseInt(temp.substring(i+2,i+3))));
				}
			}
			//dumb algorythms behavior
			//check if it can drop someone at their destination
			//else check if it can pick someone up
			//else move randomly
			for(int i = 0; i < taxis.length; i++){
				if(taxis[i].drop()){
					System.out.print("d " + i + " " + taxis[i].getNode() + " ");
				}else if (!taxis[i].full() && !network[taxis[i].getNode()].empty()){
					Passenger p = network[taxis[i].getNode()].remove();
					taxis[i].pickUp(p);
					System.out.print("p "+ i + " " + p.getDestination() + " ");
				}else{
					int m = network[taxis[i].getNode()].randomNeighbor();
					taxis[i].moveTo(m);
					System.out.print("m " + i + " " + m + " ");
				}
			}
			System.out.println("c");//end minute
			
		}
	}
	
	private boolean done(){//done when no more lines in input, no more passengers in nodes or taxies
		return !scanner.hasNextLine() && nodesEmpty() && taxisEmpty();
	}
	
	//checks is all taxies are empty
	private boolean taxisEmpty(){
		for(int i = 0; i < taxis.length; i++){
			if(!taxis[i].empty()){
				return false;
			}
		}
		return true;
	}
	
	//checks if all nodes are empty of passengers
	private boolean nodesEmpty(){
		for(int i = 0; i < network.length; i++){
			if(!network[i].empty()){
				return false;
			}
		}
		return true;
	}
	
}
