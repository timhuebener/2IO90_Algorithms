import java.util.Scanner;

/**
 * Created by Nikolay on 21-Nov-16.
 */
public class OutputProcess {



    void readingInput() {


        Scanner scanner = new Scanner(System.in);

        int l = scanner.nextInt();
        System.out.println("L is:" + l);

        /*String line = scanner.nextLine();
        String[] inValues = line.split(" ");
        int[] values = new int[inValues.length];

        int l = Integer.parseInt(inValues[0]); //number of lines
        System.out.println("L  is:" + l);

        //line = scanner.nextLine();
        //inValues = line.split(" ");
        //int m = Integer.parseInt(inValues[0]);


        String line2 = scanner.nextLine();



        for(int i = 0; i < inValues.length; i++){

            values[i] = Integer.parseInt(inValues[i]);
            System.out.println(values[i]);
        }*/



        float alpha = scanner.nextFloat();
        System.out.println("alpha is:" + alpha);
        int m = scanner.nextInt();
        System.out.println("m is:" + m);
        int taxis = scanner.nextInt(); // parse taxis and passengers
        System.out.println("taxis is:" + taxis);
        int passengers = scanner.nextInt();
        System.out.println("passenger is:" + passengers);
        int destinations = scanner.nextInt();
        System.out.println("destination is:" + destinations);
        int stops[][]=new int[destinations][99];
        int length;

        //s
        for (int i = 0; i <= destinations; i++) {
            length = scanner.nextInt();
            System.out.println("length is:" + length);
            for (int j=0; j <= length; j++ ){
            stops[i][j] = scanner.nextInt();// parse
            System.out.println("stops is:" + stops[i][j]);
            }
        }

        int t = scanner.nextInt();
        System.out.println("t is:" + t);
        int timeCalls = scanner.nextInt();// parse
        System.out.println("timeCalls is:" + timeCalls);


        int calls[][]=new int[999999][2];
        int counter=0;


        for (int i=0; i< timeCalls; i++);
        {
            calls[counter][0] = scanner.nextInt(); //parse
            calls[counter][1] = scanner.nextInt();
            counter ++;
        }


    }
    public static void main(String[] args){

        OutputProcess test = new OutputProcess();
        test.readingInput();
        //ss
    }
}
