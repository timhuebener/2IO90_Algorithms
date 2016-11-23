import java.lang.Math;
import java.util.Random;


//Class for generating different inputs


public class InputClass {
    private int line5;
    private int minutes;
    Random rn = new Random();
    
    //number of following lines
    private int lineOne(){
        line5 = rn.nextInt(98)+2;
        return 5 + line5;
    }

    //alpha number
    private float lineTwo(){
        return (float) Math.random();
    }
    
    //max time that may pass between ordering a taxi and dropping off
    private int lineThree(){
        return line5 * 2;
    }
    
    //number of taxis available and capacity per taxi respectively
    private String lineFour(){
        return rn.nextInt(line5) + " " + rn.nextInt(15);}
    
    //number of nodes in graph
    private int lineFive(){
        return line5;
    }
    
    //connections of node 0
    private String lineSix(){
        String temp = "" + (line5-1);
        for(int i = 1; i < line5; i++){
            temp += " " + i;
        }
        return temp;
    }

    //connections of rest of nodes
    private void lineSixPlus() {
        for (int i = 2; i <= line5; i++) {
            System.out.println("1 0");
        }
    }

    //Training time and length of caller list
    private String lineSeven(){
        int temp = rn.nextInt(10);
        minutes =  rn.nextInt(100)+temp;
        
        
        return temp + " "+ minutes;
    }
    
    
    private void lineSevenPlus(){
        for(int i = 0; i < minutes; i++){
            int temp = rn.nextInt(40);
            System.out.print(temp+" ");
            for(int j = 0; j < temp; j++){
                int start = rn.nextInt(line5);
                int end = rn.nextInt(line5);
                if(start == end)
                    j--;
                else{
                    System.out.print(start + " " + end + " ");
                }
            }
            System.out.println();
        }
    }
    
    
    public static void main(String[] args){
        
        InputClass temp = new InputClass();
        //System.out.println("Number of lines");
        System.out.println(temp.lineOne());
        //System.out.println("Alpha number");
        System.out.println(temp.lineTwo());
        //System.out.println("Max time collect drop off");
        System.out.println(temp.lineThree());
        //System.out.println("Number of Taxis and Capacity");
        System.out.println(temp.lineFour());
        //System.out.println("Number of nodes");
        System.out.println(temp.lineFive());
        //System.out.println("Node 0 connections");
        System.out.println(temp.lineSix());
        //System.out.println("Rest of connections");
        temp.lineSixPlus();
        //System.out.println("Training time and length of caller list");
        System.out.println(temp.lineSeven());
        //System.out.println("Input");
        temp.lineSevenPlus();
    }
}
