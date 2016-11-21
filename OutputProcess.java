import java.util.Scanner;

/**
 * Created by Nikolay on 21-Nov-16.
 */
public class OutputProcess {



    void readingInput() {
        //int l = scanner.nextLine();

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] inValues = line.split(" ");
        int[] values = new int[inValues.length];

        for(int i = 0; i < inValues.length; i++){

            values[i] = Integer.parseInt(inValues[i]);
            System.out.println(values[i]);
        }

        /*float alpha = scanner.nextLine();
        int m = scanner.nextLine();
        int taxis = scanner.nextLine(); // parse taxis and passengers
        int passengers = scanner.nextLine();
        int destinations = scanner.nextLine();
        int stops[][]=new int[destinations][];
        int length;


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
        }*/


    }
}
