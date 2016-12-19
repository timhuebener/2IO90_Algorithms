import java.util.ArrayList;

//main algorithm class for dumbAlgorythm
public class Algorithm {
    
    // all those variables in the beginning
    TaxiScanner scanner;
    int linesLeft;
    public static float alpha;
    public static int MaxTime;
    Taxi[] taxis;// array containing all taxis
    int capacity;
    int[] trainingNodes;
    int[] nodesWithCallsNeighbors;
    public static Node[] Network;// array of nodes representing the network
    public static ArrayList<Integer> Times;
    public static ArrayList<Integer> Distances;
    int training;
    int totalCalls;
    String line = "";
    
    public Algorithm() {
        // -----------------------reads input using TaxiScanner
        // class------------------------
        Times = new ArrayList<Integer>();
        Distances = new ArrayList<Integer>();
        long time = System.nanoTime();
        scanner = TaxiScanner.getInstance();
        linesLeft = Integer.parseInt(scanner.nextLine());
        alpha = Float.parseFloat(scanner.nextLine());
        MaxTime = Integer.parseInt(scanner.nextLine());
        String temp = scanner.nextLine();
        // substring includes character at index 0 but not at index 1 so this
        // would be the first character in the string
        taxis = new Taxi[Integer.parseInt(temp.substring(0, temp.indexOf(" ")))];
        capacity = Integer.parseInt(temp.substring(temp.indexOf(" ") + 1));
        Network = new Node[Integer.parseInt(scanner.nextLine())];
        trainingNodes = new int[Network.length];
        nodesWithCallsNeighbors = new int[Network.length];
        for (int i = 0; i < Network.length; i++) {// creates each node with its
            // correct neighbors
            temp = scanner.nextLine() + " ";
            int[] dist = new int[Network.length];
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
                dist[node] = 1;
                neighbors[j] = node;
                
                temp = temp.substring(temp.indexOf(" ") + 1);
            }
            Network[i] = new Node(neighbors, dist);
        }
        floydWarshall();
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
        for (int i = 0; i < Network.length; i++) {
            for (int j = 0; j < taxis.length; j++) {
                if (placeTaxis[j] == -1
                    || Network[i].getNeighbors().length > Network[placeTaxis[j]]
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
        // ------------------------------------------------------------------------------
        // main loop, every loop represents a minute
        while(training > 0){
            totalCalls--;
            training--;
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
                trainingNodes[node]+=1;
                temp = temp.substring(temp.indexOf(" ") + 1);
                int dest = Integer.parseInt(temp.substring(0,
                                                           temp.indexOf(" ")));
                temp = temp.substring(temp.indexOf(" ") + 1);
                
            }
        }
        
        for(int i = 0;i<nodesWithCallsNeighbors.length;i++){
            for(int j = 0;j<Network[i].getNeighbors().length;j++){
                nodesWithCallsNeighbors[i]=trainingNodes[Network[i].getNeighbors()[j]];
            }
        }
        
        int[] placeTaxis2 = new int[taxis.length];
        for (int i = 0; i < placeTaxis2.length; i++) {
            placeTaxis2[i] = -1;
        }
        // place taxis here
        for (int i = 0; i < Network.length; i++) {
            for (int j = 0; j < taxis.length; j++) {
                if (placeTaxis2[j] == -1
                    || Network[i].getNeighbors().length > Network[placeTaxis[j]]
                    .getNeighbors().length) {
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
        scanner.println(line + "c");
        line = "";
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
                    p.setTaxi(addToBestTaxi(p,node, dest, Network[node].getDist(dest)));
                    Network[node].addPassenger(p);// add
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
                            Passenger p = Network[taxis[i].getNode()].remove(i,
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
            scanner.println(line + "c");// end minute
            line = "";
        }
        System.out.println(((double)(System.nanoTime()-time)/1000000000.0) + " ");
        efficiency();
    }
    
    
    private void efficiency(){
        double efficiency = 0;
        for(int i = 0; i < Times.size();i++){
            efficiency += Math.pow((Times.get(i)/Math.pow((Distances.get(i) + 2),alpha)),2);
        }
        System.out.println("The efficiency is : " + (int)efficiency);
    }
    private void incrementTime(){
        for(int i = 0; i < taxis.length;i++){
            taxis[i].incrementTime();
        }
        for(int i = 0; i < Network.length;i++){
            Network[i].incrementTime();
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
        for (int i = 0; i < Network.length; i++) {
            if (!Network[i].empty()) {
                return false;
            }
        }
        return true;
    }
    
    private void floydWarshall() {
        
        for (int k = 0; k < Network.length; k++) {
            // Pick all vertices as source one by one
            for (int i = 0; i < Network.length; i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (int j = i; j < Network.length; j++) {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (Network[i].getDist(k) + Network[k].getDist(j) < Network[i]
                        .getDist(j)) {
                        Network[i].setDist(
                                           Network[i].getDist(k) + Network[k].getDist(j),
                                           j);
                        Network[j].setDist(
                                           Network[i].getDist(k) + Network[k].getDist(j),
                                           i);
                    }
                }
            }
        }
    }
    
    private void printFloyd() {
        for (int i = 0; i < Network.length; i++) {
            for (int j = 0; j < Network.length; j++) {
                System.out.print(Network[i].getDist(j) + " ");
            }
            System.out.println();
        }
    }
    
    // check each taxi to find which taxi has the shortest weighted change in
    // path if the new nodes where to be added
    private int addToBestTaxi(Passenger p,int start, int end, int dist) {
        int bestTaxi = -1;
        int bestTime = Integer.MAX_VALUE;
        for (int i = 0; i < taxis.length; i++) {
            int temp = taxis[i].testEfChange(start, end, dist);
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