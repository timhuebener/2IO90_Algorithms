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
	ArrayList<Integer> customerstype = new ArrayList<Integer>();
	ArrayList<Integer> customersstart = new ArrayList<Integer>();
	ArrayList<Integer> customersend = new ArrayList<Integer>();
	ArrayList<Integer> clustersarray = new ArrayList<Integer>();
	ArrayList<Double> customersdensity = new ArrayList<Double>();



	public static void main(String[] args){
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
		while(temp.type != 4){
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
		int[] clusters = new int[amclusters];
		int[] counter = new int[amNodes];
		String[] node = new String[amNodes];
		for(int i = 0; i < amNodes;i++){
			node[i]="";
			counter[i]=0;
		}
		for(int i = 0; i < amclusters;i++){
			clusters[i]=2;
		}

		for(int i=2*amclusters; i<amNodes;i++){
			clusters[rn.nextInt(amclusters)]++;
		}


		int temp = 0;

		for(int k=0; k<amclusters;k++){//make all clusters

			int temph=temp+1;
			node[temph] += " " + temp;
			node[temp] += " "+temph;
			counter[temph]++;
			counter[temp]++;
			for(int i = 2; i < clusters[k]; i++){//connect graph
				int tempr = rn.nextInt(i-1);
				int store1 = i+ temp;
				int store2 = temp+tempr;
				node[store1] += " " + store2;
				node[store2] += " "+store1;
				counter[store1]++;
				counter[store2]++;
				for(int z = temp; z < store1; z++){//randomly add extra nodes
					double tempr2 = Math.random();
					if(tempr2<conNodes && store2 != z ){
						node[store1] += " " + z;
						node[z] += " "+store1;
						counter[store1]++;
						counter[z]++;
					}
				}
			}

			temp+= clusters[k];
		}
		if(amclusters>1){
			int conclusters[][]=new int[amclusters][amclusters];
			for(int i = 0; i < amclusters; i++){//finalize links
				for(int z = 0; z < amclusters; z++){//randomly add extra nodes
					conclusters[i][z]=0;	
			}
		}
			conclusters[0][1]=1;
			conclusters[1][0]=1;
			for(int i = 2; i < amclusters; i++){//connect clusters
				int randin = rn.nextInt(i-1);
				conclusters[i][randin]++;
				conclusters[randin][i]++;
				for(int z = 0; z < i; z++){//randomly add extra nodes
					double tempr2 = Math.random();
					if(tempr2<conClusters && conclusters[i][z] == 0 ){
						conclusters[i][z]++;
						conclusters[z][i]++;
					}
					if(clusters[z]<clusters[i]&& conclusters[i][z] != 0 ){//make links stronger
						conclusters[z][i]=Math.round(clusters[z]*link);
						conclusters[i][z]=conclusters[z][i];
					}
					if(clusters[z]>=clusters[i] && conclusters[i][z] != 0 ){
						conclusters[z][i]=Math.round(clusters[i]*link);
						conclusters[i][z]=conclusters[z][i];
					}
				}
			}
			
			int tempi = 0;
			int tempz = 0;
			for(int i = 0; i < amclusters; i++){//finalize links
				for(int z = 0; z < i; z++){//randomly add extra nodes
					ArrayList<Integer> listz = new ArrayList<Integer>();
					for (int y=tempz; y<tempz+clusters[z]; y++) {
						listz.add(new Integer(y));
					}
					ArrayList<Integer> listi = new ArrayList<Integer>();
					for (int y=tempi; y<tempi+clusters[i]; y++) {
						listi.add(new Integer(y));
					}
					Collections.shuffle(listz);
					Collections.shuffle(listi);
					for (int y=0; y<conclusters[z][i]; y++) {
						node[listi.get(y)] += " " + listz.get(y);
						node[listz.get(y)] += " "+listi.get(y);
						counter[listi.get(y)]++;
						counter[listz.get(y)]++;
					}
					tempz+=clusters[z];
				}
				tempz = 0;
				tempi+=clusters[i];
			}
		}

		for(int i = 0; i < amNodes;i++){
			node[i]=counter[i]+node[i];
			System.out.println(node[i]);
		}
		for(int i = 0; i < amclusters;i++){//make clusters public
			clustersarray.add(clusters[i]);
		}

	}
	
	public static int getPoisson(double lambda) {
		  double L = Math.exp(-lambda);
		  double p = 1.0;
		  int k = 0;

		  do {
		    k++;
		    p *= Math.random();
		  } while (p > L);

		  return k - 1;
		}


	void simPeople(){
		int realtime[] = new int[endday+1];
		String realorders[] = new String[endday+1];
		for(int i=0;i<endday+1;i++){
			realtime[i]=0;
			realorders[i]="";
		}
		int time;
		for(int i = 0; i < customerstype.size(); i++){
			time = customersstart.get(i);
			while( time < customersend.get(i)){
				time +=getPoisson(customersdensity.get(i));
				if(time<endday){
				realtime[Math.round(time)]++;
				if(customerstype.get(i)==1){
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
		for(int i=0;i<endday+1;i++){
			System.out.println(realtime[i]+realorders[i]);
		}
	}
	
	String customer1(){
		int nodestart=0;
		int nodeend;
		String result;
		int cluster1 = 0;
		if(amclusters!=1){
			cluster1 = rn.nextInt(amclusters-1);
		}
		for(int i=0; i < cluster1; i++){
			nodestart+=clustersarray.get(i);
		}
		nodeend=nodestart;
		nodeend+=rn.nextInt(clustersarray.get(cluster1));
		nodestart+=rn.nextInt(clustersarray.get(cluster1));
		result= " "+nodestart+" "+nodeend;
		return result;
	}
	String customer2(){
		int nodestart=0;
		int nodeend=0;
		int cluster2;
		String result;
		int cluster1 = rn.nextInt(amclusters-1);
		cluster2=cluster1;
		while(cluster2!=cluster1){
		cluster2 = rn.nextInt(amclusters-1);
		}
		for(int i=0; i < cluster1; i++){
			nodestart+=clustersarray.get(i);
		}
		for(int i=0; i < cluster2; i++){
			nodeend+=clustersarray.get(i);
		}
		nodeend+=rn.nextInt(clustersarray.get(cluster2));
		nodestart+=rn.nextInt(clustersarray.get(cluster1));
		result= " "+nodestart+" "+nodeend;
		return result;
	}
	String customer3(){
		String result = " "+ rn.nextInt(amclusters-1) +" "+ rn.nextInt(amclusters-1);
		return result;
	}




}
