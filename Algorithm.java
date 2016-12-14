import java.util.ArrayList;

//main algorithm class for dumbAlgorythm
public class Algorithm {

	// all those variables in the beginning
	TaxiScanner scanner;
	int linesLeft;
	public static float alpha;
	int maxTime;
	Taxi[] taxis;// array containing all taxis
	int capacity;
	public static Node[] network;// array of nodes representing the network
	public static ArrayList<Integer> times;
	public static ArrayList<Integer> distances;
    public double[] trainingNodes;
	int training;
	int totalCalls;
	String line = "";

	public Algorithm() {
		// -----------------------reads input using TaxiScanner
		// class------------------------
		times = new ArrayList<Integer>();
		distances = new ArrayList<Integer>();
		long time = System.nanoTime();
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
				dist[k] = Integer.MAX_VALUE / 10;
			}
			dist[i] = 0;
			int[] neighbors = new int[Integer.parseInt(temp.substring(0,
					temp.indexOf(" ")))];
			temp = temp.substring(temp.indexOf(" ") + 1);
			for (int j = 0; temp.length() > 0; j++) {
				int node = Integer
						.parseInt(temp.substring(0, temp.indexOf(" ")));// ----------
				dist[node] = Integer.MAX_VALUE / 10;
				neighbors[j] = node;

				temp = temp.substring(temp.indexOf(" ") + 1);
			}
			network[i] = new Node(neighbors, dist);
		}
		for(int i =0;i<network.length;i++){
			bfs(i,i);
		}
		//floydWarshall();
		// printFloyd();
		temp = scanner.nextLine();
		training = Integer.parseInt(temp.substring(0, temp.indexOf(" ")));
		totalCalls = Integer.parseInt(temp.substring(temp.indexOf(" ") + 1));
		// set starting points for taxis in this case, all node 0
		int[] placeTaxis = new int[taxis.length];
		for (int i = 0; i < placeTaxis.length; i++) {
			placeTaxis[i] = -1;
		}
		// place taxis here
		for (int i = 0; i < network.length; i++) {
			for (int j = 0; j < taxis.length; j++) {
				if (placeTaxis[j] == -1
						|| network[i].getNeighbors().length > network[placeTaxis[j]]
								.getNeighbors().length) {
					placeTaxis = bubbleDown(placeTaxis, j, i);
					break;
				}
			}
		}
		/*
		 * for(int i = 0; i < placeTaxis.length;i++){
		 * System.out.print(placeTaxis[i]); }
		 */

		// set starting points for taxis in this case, all node 0
		for (int i = 0; i < taxis.length; i++) {
			if (placeTaxis[i] == -1) {
				taxis[i] = new Taxi(placeTaxis[0], capacity);
				line = line + "m " + (i + 1) + " " + placeTaxis[0] + " ";
			} else {
				taxis[i] = new Taxi(placeTaxis[i], capacity);
				line = line + "m " + (i + 1) + " " + placeTaxis[i] + " ";
			}
		}
		scanner.println(line + "c");
		line = "";
        trainingNodes = new double[network.length];
        
        while(training > 0){
            training--;
            totalCalls--;
            temp = scanner.nextLine() + " ";
            temp = temp.substring(temp.indexOf(" ") + 1);
            while (temp.length() > 0) {
                int node = Integer.parseInt(temp.substring(0,
                                                           temp.indexOf(" ")));
                trainingNodes[node]+=0.75;
                temp = temp.substring(temp.indexOf(" ") + 1);
                int dest = Integer.parseInt(temp.substring(0,
                                                           temp.indexOf(" ")));
                temp = temp.substring(temp.indexOf(" ") + 1);
                
                //Passenger p = new Passenger(node,dest);
                //p.setTaxi(addToBestTaxi(p,node, dest, network[node].getDist(dest)));
                //network[node].addPassenger(p);
                
                
            }
            scanner.println("c");
        }
        for(int i = 0; i < trainingNodes.length; i++){
            trainingNodes[i]+=0.25*network[i].getNeighbors().length;
        }
        
        int[] placeTaxis2 = new int[taxis.length];
        for (int i = 0; i < placeTaxis2.length; i++) {
            placeTaxis2[i] = -1;
        }
        // place taxis here
        for (int i = 0; i < trainingNodes.length; i++) {
            for (int j = 0; j < taxis.length; j++) {
                if (placeTaxis2[j] == -1
                    || trainingNodes[i] >= trainingNodes[placeTaxis2[j]]) {
                    placeTaxis2 = bubbleDown(placeTaxis2, j, i);
                    break;
                }
            }
        }
        /*
         * for(int i = 0; i < placeTaxis.length;i++){
         * System.out.print(placeTaxis[i]); }
         */
        
        // set starting points for taxis in this case, all node 0
        for (int i = 0; i < taxis.length; i++) {
            if (placeTaxis2[i] == -1) {
                taxis[i] = new Taxi(placeTaxis2[0], capacity);
                line = line + "m " + (i + 1) + " " + placeTaxis2[0] + " ";
            } else {
                taxis[i] = new Taxi(placeTaxis2[i], capacity);
                line = line + "m " + (i + 1) + " " + placeTaxis2[i] + " ";
            }
        }
        
        // ------------------------------------------------------------------------------
		// main loop, every loop represents a minute
		while (!done()) {

			// increment total time waited for all passangers
			incrementTime();

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
					temp = temp.substring(temp.indexOf(" ") + 1);
					// find best taxi
					Passenger p = new Passenger(node,dest);
					p.setTaxi(addToBestTaxi(p,node, dest, network[node].getDist(dest)));
					network[node].addPassenger(p);// add
														// passanger
														// to
														// the
														// node
														// with
														// its
														// destination
														// and
														// which
														// taxi
														// will
														// pick
														// it
														// up

				}
			}

			// taxi behavior
			for (int i = 0; i < taxis.length; i++) {
				if (taxis[i].atDest() != -2) {
					while (taxis[i].atDest() != -2) {
						if (taxis[i].atDest() == -1) {// drop off
							taxis[i].drop(i);
							line = line + "d " + (i + 1) + " "
									+ taxis[i].getNode() + " ";
						} else {// pick up
							Passenger p = network[taxis[i].getNode()].remove(i,
									taxis[i].atDest());
							taxis[i].pickUp(p, i);
							line = line + "p " + (i + 1) + " "
									+ p.getDestination() + " ";
						}
					}
				} else {// not at a destination
					int m = taxis[i].moveAlong();
					line = line + "m " + (i + 1) + " " + m + " ";
				}
			}
			/*
			 * // dumb algorithms behavior // check if it can drop someone at
			 * their destination // else check if it can pick someone up // else
			 * move randomly for (int i = 0; i < taxis.length; i++) { if
			 * (taxis[i].drop()) { line = line + "d " + i + " " +
			 * taxis[i].getNode() + " "; } else if (!taxis[i].full() &&
			 * !network[taxis[i].getNode()].empty()) { Passenger p =
			 * network[taxis[i].getNode()].remove(); taxis[i].pickUp(p); line =
			 * line + "p " + i + " " + p.getDestination() + " "; } else { int m
			 * = network[taxis[i].getNode()].randomNeighbor();
			 * taxis[i].moveTo(m); line = line + "m " + i + " " + m + " "; } }
			 */
			scanner.println(line + "c");// end minute
			line = "";
		}
		// System.out.println(((double)(System.nanoTime()-time)/1000000000.0) +
		// " ");
		efficiency();
	}

	private void efficiency(){
		double efficiency = 0;
		for(int i = 0; i < times.size();i++){
			efficiency += Math.pow((times.get(i)/Math.pow((distances.get(i) + 2),alpha)),2);
		}
		System.out.println("The efficiency is : " + efficiency);
	}
	private void incrementTime(){
		for(int i = 0; i < taxis.length;i++){
			taxis[i].incrementTime();
		}
		for(int i = 0; i < network.length;i++){
			network[i].incrementTime();
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

	
	private void bfs(int root,int node){
		//root is first node 
		for(int i=0; i<network[node].getNeighbors().length;i++){
			//for all neighbours
			if( network[root].getDist(network[node].getNeighbors()[i]) == Integer.MAX_VALUE / 10){
				//if we have no distance from the root to the neighbour(if distance is infinity)
				network[root].setDist(network[root].getDist(node)+1, network[node].getNeighbors()[i]);
				//distance root to neigbour is distance current node + 1
			}
		}
		for(int i=0; i<network[node].getNeighbors().length;i++){
			//for all neighbours
			if( network[root].getDist(network[node].getNeighbors()[i]) == network[root].getDist(node)+1){
				//if we have found a distance to the node fromnode(if distance is infinity)
				bfs(root,network[node].getNeighbors()[i]);
				//call bfs again from current node
			}
		}
	}


	// check each taxi to find which taxi has the shortest weighted change in
	// path if the new nodes where to be added
	private int addToBestTaxi(Passenger p,int start, int end, int dist) {
		int bestTaxi = -1;
		int bestTime = Integer.MAX_VALUE;
		for (int i = 0; i < taxis.length; i++) {
			int temp = taxis[i].testPathChange(start, end, dist);
			if (temp < bestTime) {// check taxi
				bestTaxi = i;
				bestTime = temp;
			}
		}
		taxis[bestTaxi].addToPath(start, p, bestTaxi + 1);// add pick up point
															// and drop off
															// point to theb est
															// taxi's path
		// System.out.print(bestTaxi + " ");
		return bestTaxi;
	}

	private int[] bubbleDown(int[] topnodes, int index, int node) {
		int temp = topnodes[index];
		topnodes[index] = node;
		for (int i = index + 1; i < topnodes.length; i++) {
			int save = topnodes[i];
			topnodes[i] = temp;
			temp = save;
		}
		return topnodes;

	}
}