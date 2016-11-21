import java.lang.Math;
import java.util.Random;


//Class for generating different inputs


public class InputClass {
    private int line5;
    private int minutes;
    Random rn = new Random();
    
    
    private int lineOne(){
        line5 = (int)(Math.random()*999+1);
        return 5 + line5;
    }
    private float lineTwo(){
        return (float) Math.random();
    }
    
    
    private int lineThree(){
        return lineFive() * 2;
    }
    
    
    private String lineFour(){
        return (int)(Math.random()*100+1) + " "+ (int)(Math.random()*15)+1;
    }
    
    
    private int lineFive(){
        return line5;
    }
    
    
    private String lineSix(){
        String temp = "" + (line5-1);
        for(int i = 1; i < line5; i++){
            temp += " " + i;
        }
        return temp;
        
    }
    private void lineSixPlus(){
        for(int i = 1; i <= line5; i++)
            System.out.println("1 0");
    }
    
    
    private String lineSeven(){
        int temp = (int)(Math.random()*50);
        minutes =  (int)(Math.random()*100+temp);
        
        
        return temp + " "+ minutes;
    }
    
    
    private void lineSevenPlus(){
        for(int i = 0; i < minutes; i++){
            int temp = (int)(Math.random()*100);
            System.out.print(temp+" ");
            for(int j = 0; j < temp; j++){
                int start = (int)(Math.random() * (line5+1));
                int end = (int)(Math.random() * (line5+1));
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
        System.out.println(temp.lineOne());
        System.out.println(temp.lineTwo());
        System.out.println(temp.lineThree());
        System.out.println(temp.lineFour());
        System.out.println(temp.lineFive());
        System.out.println(temp.lineSix());
        temp.lineSixPlus();
        System.out.println(temp.lineSeven());
        temp.lineSevenPlus();
    }
}
