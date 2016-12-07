import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


//Class for generating different inputs


public class InputClass {
	private int line5;
	private int minutes;
	Random rn = new Random();
	static Scanner scanner = new Scanner(System.in);
	float alpha;
	int maxTime;
	int amTaxi;
	int sizeTaxi;
	int amNodes;
	int amclusters;
	float conNodes;
	float conClusters;
	float link;
	int training;
	int endday = 0;
	int type;
	int start;
	int end;
	double density;
	//arrays for incoming calls
	ArrayList<Integer> customerstype = new ArrayList<Integer>();
	ArrayList<Integer> customersstart = new ArrayList<Integer>();
	ArrayList<Integer> customersend = new ArrayList<Integer>();
	ArrayList<Double> customersdensity = new ArrayList<Double>();
	
	//real dumb array didnt know how else to do it
	ArrayList<Integer> clustersarray = new ArrayList<Integer>();


	public static void main(String[] args){//take input
		InputClass temp = new InputClass();
	    System.out.println("Alpha, double between 0 and 1"); 
		temp.alpha = scanner.nextFloat();
		System.out.println("Maximum time allowed for one person, int larger than 0"); 
		temp.maxTime = scanner.nextInt();
		System.out.println("Amount of taxis, int larger than 0"); 
		temp.amTaxi = scanner.nextInt();
		System.out.println("Maximum amount of people transported by a taxi, int larger than 0"); 
		temp.sizeTaxi = scanner.nextInt();
		System.out.println("Amount of nodes, int larger than 0"); 
		temp.amNodes = scanner.nextInt();
		System.out.println("amount of clusters, int larger than 0 smaller than half of the amount of nodes");
		temp.amclusters = scanner.nextInt();
		System.out.println("percentage possible edges in clusters, double between 0 and 1"); 
		temp.conNodes = scanner.nextFloat();
		System.out.println("percentage connected clusters , double between 0 and 1");
		temp.conClusters = scanner.nextFloat();
		System.out.println("strength link connected clusters , double between 0 and 1"); 
		temp.link = scanner.nextFloat();
		System.out.println("generate customers:type, start time(int>=0), end time(int>0), average(bouble>0)");
		System.out.println("1 = close customers");
		System.out.println("2 = far customers");
		System.out.println("3 = random customers");
		System.out.println("4 = end day");
		while(temp.type != 4){//loop for customers
			temp.type = scanner.nextInt();
			if(temp.type!=4){
			temp.start = scanner.nextInt();
			temp.end = scanner.nextInt();
			temp.density = 1/(scanner.nextDouble());
			if(temp.end>temp.endday){
				temp.endday = temp.end;
			}
			temp.customerstype.add(temp.type);
			temp.customersstart.add(temp.start);
			temp.customersend.add(temp.end);
			temp.customersdensity.add(temp.density);
			}
		}
		
		System.out.println("End training periode, int larger than 0");
		temp.training = scanner.nextInt();

		//print output
		try {
			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("C:\\Users\\s157937\\Documents\\Jaar_2_Sofware_sience\\q2\\algorithms\\input.txt")), true));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(temp.lineOne());
		System.out.println(temp.lineTwo());
		System.out.println(temp.lineThree());
		System.out.println(temp.lineFour());
		System.out.println(temp.lineFive());
		temp.simGraph();
		System.out.println(temp.linelast());
		temp.simPeople();
		
	}

//triv lines
	private int lineOne(){
		return 5 + amNodes;
	}

	private double lineTwo(){
		return alpha;
	}


	private int lineThree(){
		return maxTime;
	}


	private String lineFour(){
		String temp = amTaxi + " " + sizeTaxi;
		return temp;
	}


	private int lineFive(){
		return amNodes;
	}


	private String linelast(){
		String temp = training+" "+endday;
		return temp;
	}

	
	void simGraph(){
		int[] clusters = new int[amclusters];//array amount of nodes per cluster
		int[] counter = new int[amNodes];//degree of every node
		String[] node = new String[amNodes];//adjacency list all nodes to be printed
		//pre fill arrays
		for(int i = 0; i < amNodes;i++){
			node[i]="";
			counter[i]=0;
		}
		for(int i = 0; i < amclusters;i++){//every cluster has at least 2 nodes
			clusters[i]=2;
		}

		for(int i=2*amclusters; i<amNodes;i++){//randomly divide remaining nodes over clusters
			clusters[rn.nextInt(amclusters)]++;
		}


		int temp = 0;//first node of the cluster we are working on

		for(int k=0; k<amclusters;k++){//make all clusters

			int temph=temp+1;//second node of the cluster we are working on
			node[temph] += " " + temp;//add to adjacency matrix
			node[temp] += " "+temph;
			counter[temph]++;
			counter[temp]++;
			for(int i = 2; i < clusters[k]; i++){//connect graph
				int tempr = rn.nextInt(i-1);//pick random already connected node
				int store1 = i+ temp;//connect the new node
				int store2 = temp+tempr;
				node[store1] += " " + store2;
				node[store2] += " "+store1;
				counter[store1]++;
				counter[store2]++;
				for(int z = temp; z < store1; z++){//randomly add extra nodes
					double tempr2 = Math.random();//add a node we dont have yet with chance conNodes
					if(tempr2<conNodes && store2 != z ){
						node[store1] += " " + z;
						node[z] += " "+store1;
						counter[store1]++;
						counter[z]++;
					}
				}
			}

			temp+= clusters[k];//temp is first node next cluster
		}
		if(amclusters>1){
			int conclusters[][]=new int[amclusters][amclusters];//contains amount of nodes between 2 clusters
			for(int i = 0; i < amclusters; i++){//finalize links
				for(int z = 0; z < amclusters; z++){//randomly add extra nodes
					conclusters[i][z]=0;	
			}
		}
			int randin = 0;//random cluster
			for(int i = 1; i < amclusters; i++){//connect clusters with 1 node
				conclusters[i][randin]++;
				conclusters[randin][i]++;
				randin = rn.nextInt(i);//cluster for next iteration 
				for(int z = 0; z < i; z++){//randomly add extra clusters
					double tempr2 = Math.random();
					if(tempr2<conClusters && conclusters[i][z] == 0 ){
						conclusters[i][z]++;
						conclusters[z][i]++;
					}
					if(conclusters[i][z] != 0 ){//make links stronger
						conclusters[z][i]=Math.round(clusters[z]*clusters[i]*link);
						if(conclusters[z][i]==0){//in case link is 0 keep connected
							conclusters[z][i]=1;
						}
						conclusters[i][z]=conclusters[z][i];//mirror it not needed because array
						//is reflexive  
					}
					
				}
			}
			
			int tempi = 0;
			int tempz = 0;
			for(int i = 0; i < amclusters; i++){//finalize links
				for(int z = 0; z < i; z++){//randomly add extra nodes
					ArrayList<Integer> listz = new ArrayList<Integer>();
					for (int y=tempz; y<tempz+clusters[z]; y++) {//make array list with all nodes 1 cluster
						listz.add(new Integer(y));
					}
					ArrayList<Integer> listi = new ArrayList<Integer>();//other cluster
					for (int y=tempi; y<tempi+clusters[i]; y++) {
						listi.add(new Integer(y));
					}
					Collections.shuffle(listz);//shuffle so we can pick unique nodes
					Collections.shuffle(listi);
					int kcheck=(int)Math.floor(conclusters[z][i]/clusters[i]);
					if(kcheck==0){ kcheck=1;}//kcheck is not useful but needed somehow
					int ycheck=(int)Math.floor(conclusters[z][i]/clusters[z]);
					if(ycheck==0){ ycheck=1;}//same as kcheck
					for(int k = 0; k<kcheck;k++){
					for (int y=0; y<ycheck; y++) {//make the cluster connections node connections
						node[listi.get(y)] += " " + listz.get((y+k)%clusters[z]);//pick unique number from array list
						node[listz.get((y+k)%(clusters[z]))] += " "+listi.get(y);
						counter[listi.get(y)]++;
						counter[listz.get((y+k)%(clusters[z]))]++;
						
					}
					}
					tempz+=clusters[z];//temp z is first node cluster z+1
				
					listi.clear();
					listz.clear();
				}
				tempz = 0;//reset tempz 
				tempi+=clusters[i];//tempi is first node cluster i+1
			}
		}

		for(int i = 0; i < amNodes;i++){//print adjacency all nodes
			node[i]=counter[i]+node[i];
			System.out.println(node[i]);
		}
		for(int i = 0; i < amclusters;i++){//make clusters public should be smarter way
			clustersarray.add(clusters[i]);
		}

	}
	
	public static int getPoisson(double lambda) {//gives random time till next call dependent on average amount of calls per minute
		  double L = Math.exp(-lambda);
		  double p = 1.0;
		  int k = 0;

		  do {
		    k++;
		    p *= Math.random();
		  } while (p > L);

		  return k - 1;
		}


	void simPeople(){//simulate calls
		int realtime[] = new int[endday+1];//array the representing the minutes
		String realorders[] = new String[endday+1];//representing the orders per minute
		for(int i=0;i<endday+1;i++){
			realtime[i]=0;
			realorders[i]="";
		}
		int time;//current time
		for(int i = 0; i < customerstype.size(); i++){
			time = customersstart.get(i);
			while( time < customersend.get(i)){
				time +=getPoisson(customersdensity.get(i));//move time to next call(time is double now)
				if(time<endday){//add a caller to the minute
				realtime[Math.round(time)]++;
				if(customerstype.get(i)==1){//generate the order based on the type of customer
					realorders[Math.round(time)]+=customer1();
				}
				if(customerstype.get(i)==2){
					realorders[Math.round(time)]+=customer2();
				}
				if(customerstype.get(i)==3){
					realorders[Math.round(time)]+=customer3();
				}
			}
			}
		}
		for(int i=0;i<endday;i++){//print calls
			System.out.println(realtime[i]+realorders[i]);
		}
	}
	
	String customer1(){//customer from random node in a cluster to a random node in the same cluster
		int nodestart=0;
		int nodeend;
		String result;
		int cluster1 = 0;
		if(amclusters!=1){//if there is more then 1 cluster
			cluster1 = rn.nextInt(amclusters-1);
		}
		for(int i=0; i < cluster1; i++){//node start is first node of the cluster
			nodestart+=clustersarray.get(i);
		}
		nodeend=nodestart;
		nodeend+=rn.nextInt(clustersarray.get(cluster1-1));//+random amount of nodes in cluster
		nodestart+=rn.nextInt(clustersarray.get(cluster1-1));
		result= " "+nodestart+" "+nodeend;
		return result;
	}
	String customer2(){//customer from one to another cluster
		int nodestart=0;
		int nodeend=0;
		int cluster1;
		int cluster2;
		String result;
		if(amclusters==1){// if only 1 cluster
			cluster1 = 1;
		}else{
		cluster1 = rn.nextInt(amclusters)+1;
		}
		cluster2=cluster1;
		while(cluster2==cluster1 && amclusters != 1){//pick unique second cluster if there is one
		cluster2 = rn.nextInt(amclusters)+1;
		}
		for(int i=0; i < cluster1-1; i++){//set to first node of the cluster
			nodestart+=clustersarray.get(i);
		}
		for(int i=0; i < cluster2-1; i++){
			nodeend+=clustersarray.get(i);
		}
		nodeend+=rn.nextInt(clustersarray.get(cluster2-1));
		nodestart+=rn.nextInt(clustersarray.get(cluster1-1));
		result= " "+nodestart+" "+nodeend;
		return result;
	}
	String customer3(){
		String result;
		result = " "+ rn.nextInt(amNodes-1) +" "+ rn.nextInt(amNodes-1);
		
		return result;
	}
}
