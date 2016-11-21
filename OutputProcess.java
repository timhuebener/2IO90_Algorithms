import java.util.Scanner;

/**
 * Created by Nikolay on 21-Nov-16.
 */
public class OutputProcess {



    void readingInput() {


        Scanner scanner = new Scanner(System.in);

        int l = scanner.nextInt();

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
        int m = scanner.nextInt();
        int taxis = scanner.nextInt(); // parse taxis and passengers
        int passengers = scanner.nextInt();
        int destinations = scanner.nextInt();
        int stops[][]=new int[destinations][];
        int length;

        //s
        for (int i = 0; i < destinations; i++) {
            length = scanner.nextInt();
            for (int j=0; j < length; j++ )
            stops[i][j] = scanner.nextInt();// parse
        }

        int t = scanner.nextInt();
        int timeCalls = scanner.nextInt();// parse


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
