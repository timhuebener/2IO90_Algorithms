//main algorythm class for dumbAlgorythm
public class Algorithm {

	// all those variables in the beginning
	TaxiScanner scanner;
	int linesLeft;
	float alpha;
	int maxTime;
	Taxi[] taxis;// array containing all taxis
	int capacity;
	Node[] network;// array of nodes representing the network
	int training;
	int totalCalls;
	String line = "";

	public Algorithm() {
		// -----------------------reads input using TaxiScanner
		// class------------------------
		scanner = TaxiScanner.getInstance();
		linesLeft = Integer.parseInt(scanner.nextLine());
		alpha = Float.parseFloat(scanner.nextLine());
		maxTime = Integer.parseInt(scanner.nextLine());
		String temp = scanner.nextLine();
		// substring includes character at index 0 but not at index 1 so this
		// would be the first character in the string
		taxis = new Taxi[Integer.parseInt(temp.substring(0, temp.indexOf(" ")))];
		capacity = Integer.parseInt(temp.substring(temp.indexOf(" ") + 1));
		network = new Node[Integer.parseInt(scanner.nextLine())];
		for (int i = 0; i < network.length; i++) {// creates each node with its
													// correct neighbors
			temp = scanner.nextLine() + " ";
			int[] dist = new int[network.length];
			for (int k = 0; k < dist.length; k++) {
				dist[k] = Integer.MAX_VALUE/10;
			}
			dist[i] = 0;
			int[] neighbors = new int[Integer.parseInt(temp.substring(0,
					temp.indexOf(" ")))];
			temp = temp.substring(temp.indexOf(" ") + 1);
			for (int j = 0; temp.length() > 0; j++) {
				int node = Integer
						.parseInt(temp.substring(0, temp.indexOf(" ")));// ----------
				dist[node] = 1;
				neighbors[j] = node;

				temp = temp.substring(temp.indexOf(" ") + 1);
			}
			network[i] = new Node(neighbors, dist);
		}
		floydWarshall();
		//printFloyd();
		temp = scanner.nextLine();
		training = Integer.parseInt(temp.substring(0, temp.indexOf(" ")));
		totalCalls = Integer.parseInt(temp.substring(temp.indexOf(" ") + 1));
		// set starting points for taxis in this case, all node 0
		for (int i = 0; i < taxis.length; i++) {
			taxis[i] = new Taxi(0, capacity);
			line = line + "m " + i + " " + 0 + " ";
		}
		scanner.println(line + "c");
		line = "";


		// ------------------------------------------------------------------------------
		// Main loop that moves the Taxis in the graph minute by minute
		while (!done()) {

			if (totalCalls > 0) {// add passengers to nodes
				totalCalls--;
				temp = scanner.nextLine() + " ";
				temp = temp.substring(temp.indexOf(" ") + 1);
				while (temp.length() > 0) {// skip # of new people since can be
											// derived from temp.length and 4 at
											// a time due to 2 numbers and 2
											// spaces
					// this is a rough one, add a passenger to the node equal to
					// the fist number with a destination equal to the second
					// number
					int node = Integer.parseInt(temp.substring(0,
							temp.indexOf(" ")));
					temp = temp.substring(temp.indexOf(" ") + 1);
					int dest = Integer.parseInt(temp.substring(0,
							temp.indexOf(" ")));
					network[node].addPassenger(new Passenger(dest, node/*node = current position(not sure)*/));
					temp = temp.substring(temp.indexOf(" ") + 1);
				}
			}
			// dumb algorithms behavior
			// check if it can drop someone at their destination
			// else check if it can pick someone up
			// else move randomly
			for (int i = 0; i < taxis.length; i++) {
				if (taxis[i].drop()) {
					line = line + "d " + i + " " + taxis[i].getNode() + " ";
				} else if (!taxis[i].full()
						&& !network[taxis[i].getNode()].empty()) {
					Passenger p = network[taxis[i].getNode()].remove();
					taxis[i].pickUp(p);
					line = line + "p " + i + " " + p.getDestination() + " ";
				} else {
					int m = network[taxis[i].getNode()].randomNeighbor();
					taxis[i].moveTo(m);
					line = line + "m " + i + " " + m + " ";
				}
			}
			scanner.println(line + "c");// end minute
			line = "";
		}
	}

	private boolean done() {// done when no more lines in input, no more
							// passengers in nodes or taxies
		return (!scanner.hasNextLine() && nodesEmpty() && taxisEmpty());
	}

	// checks is all taxis are empty
	private boolean taxisEmpty() {
		for (int i = 0; i < taxis.length; i++) {
			if (!taxis[i].empty()) {
				return false;
			}
		}
		return true;
	}

	// checks if all nodes are empty of passengers
	private boolean nodesEmpty() {
		for (int i = 0; i < network.length; i++) {
			if (!network[i].empty()) {
				return false;
			}
		}
		return true;
	}

    // ------------------------------------------------------------------------------
	private void floydWarshall() {
		/*
		 * dist[][] will be the output matrix that will finally have the
		 * shortest distances between every pair of vertices
		 */

		/*
		 * Initialize the solution matrix same as input graph matrix. Or we can say
		 * the initial values of shortest distances are based on shortest paths
		 * considering no intermediate vertex.
		 */
		/*
		 * Add all vertices one by one to the set of intermediate vertices. --->
		 * Before start of a iteration, we have shortest distances between all
		 * pairs of vertices such that the shortest distances consider only the
		 * vertices in set {0, 1, 2, .. k-1} as intermediate vertices. ---->
		 * After the end of a iteration, vertex no. k is added to the set of
		 * intermediate vertices and the set becomes {0, 1, 2, .. k}
		 */
		for (int k = 0; k < network.length; k++) {
			// Pick all vertices as source one by one
			for (int i = 0; i < network.length; i++) {
				// Pick all vertices as destination for the
				// above picked source
				for (int j = 0; j < network.length; j++) {
					// If vertex k is on the shortest path from
					// i to j, then update the value of dist[i][j]
					if (network[i].getDist(k) + network[k].getDist(j) < network[i]
							.getDist(j))
						network[i].setDist(
								network[i].getDist(k) + network[k].getDist(j),
								j);
				}
			}
		}
	}

	private void printFloyd(){
		for(int i = 0; i < network.length;i++)
		{
			for(int j = 0; j < network.length;j++)
			{
				System.out.print(network[i].getDist(j) + " ");
			}
			System.out.println();
		}
	}

	//path finding method
    //has to be adapted
    void findPath(int taxi, int start, int end){
        for(int i=0; i<network.length; i++){
            if(network[i].getDist(start)==1 && (network[end].getDist(start)-1 == network[i].getDist(end))){
                taxis[taxi].addPath(i);
                System.out.println(i);
                findPath(taxi, i, end);
            }
        }
    }

    //find from all taxis the one that can implement new caller into its path with the least cost
    void findClosestTaxi(Passenger caller){
        int pickup=0, dropoff=0, pickIndex=0, costs=0, bestTaxi=0;

        for(int i=0; i<taxis.length; i++){
            for(int j=0; j<taxis[i].Path.size(); j++){
                if (j == 0) {
                    pickup = network[caller.getPosition()].getDist(taxis[i].Path.get(j));
                    pickIndex=j;
                }
                else if(pickup > network[caller.getPosition()].getDist(taxis[i].Path.get(j))){
                    pickup = network[caller.getPosition()].getDist(taxis[i].Path.get(j));
                    pickIndex=j;
                }
            }

            for(int j=pickIndex; j<taxis[i].Path.size(); j++){
                if (j == pickIndex) {
                    dropoff = network[caller.getDestination()].getDist(taxis[i].Path.get(j));
                }
                else if(dropoff > network[caller.getDestination()].getDist(taxis[i].Path.get(j))){
                    dropoff = network[caller.getDestination()].getDist(taxis[i].Path.get(j));
                }
            }

            if(i == 0){
                costs = pickup + dropoff;
                bestTaxi = i;
            }
            else if(costs > pickup+dropoff){
                costs = pickup + dropoff;
                bestTaxi = i;
            }
        }
        //found best taxi
        for(int j=0; j<taxis[bestTaxi].Path.size(); j++){
            if (j == 0) {
                pickup = network[caller.getPosition()].getDist(taxis[bestTaxi].Path.get(j));
                pickIndex=j;
            }
            else if(pickup > network[caller.getPosition()].getDist(taxis[bestTaxi].Path.get(j))){
                pickup = network[caller.getPosition()].getDist(taxis[bestTaxi].Path.get(j));
                pickIndex=j;
            }
        }
        taxis[bestTaxi].Path.add(pickIndex, caller.getPosition());

        int dropIndex=0;
        for(int j=pickIndex; j<taxis[bestTaxi].Path.size(); j++){
            if (j == pickIndex) {
                dropoff = network[caller.getDestination()].getDist(taxis[bestTaxi].Path.get(j));
                dropIndex = j;
            }
            else if(dropoff > network[caller.getDestination()].getDist(taxis[bestTaxi].Path.get(j))){
                dropoff = network[caller.getDestination()].getDist(taxis[bestTaxi].Path.get(j));
                dropIndex = j;
            }
        }
        taxis[bestTaxi].Path.add(dropIndex, caller.getPosition());
    }
}