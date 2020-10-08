import java.io.*;
import java.util.Scanner;

public class FillInPuzzleUI {
    public static void main(String[] args) {
        // Define variables to manage user input

        String userCommand = "";
        Scanner userInput = new Scanner( System.in );

        // Define variables to catch the return values of the transformation methods

        boolean booleanOutcome;
        Integer integerOutcome;

        // Define a single data transformation object.

        FillInPuzzle object=new FillInPuzzle();

        System.out.println("Type any one of the following operations: loadpuzzle,solve,print,choices. Type 'quit' to exit the program");

        // Process the user input until they provide the command "quit"

        do {
            // Find out what the user wants to do
            userCommand = userInput.nextLine();

            /* Do what the user asked for. */

            if (userCommand.equalsIgnoreCase("loadpuzzle")) {
                System.out.println("Enter the input file path : ");
                String inputfilepath=userInput.nextLine();
                FileReader fr= null;
                try {
                    fr = new FileReader(inputfilepath);
                    BufferedReader br = new BufferedReader(fr);
                    booleanOutcome = object.loadPuzzle(br);
                    System.out.println("loadPuzzle outcome '" + booleanOutcome+"'" );
                } catch (FileNotFoundException e) {
                    System.out.println("File Not Found");
                }

            } else if (userCommand.equalsIgnoreCase("solve")) {
                booleanOutcome = object.solve();
                System.out.println("solve outcome '" + booleanOutcome+"'" );
            } else if (userCommand.equalsIgnoreCase("print")) {
                if(object.ways==0) {
                    System.out.println("No solution");
                }
                else {
                    System.out.println("Solved crossword puzzle :");
                    for (int i = 0; i < object.puzzle_matrix.length; i++) {
                        for (int j = 0; j < object.puzzle_matrix.length; j++) {
                            if (object.puzzle_matrix[i][j] != '*')
                                System.out.print(object.puzzle_matrix[i][j] + " ");
                            else
                                System.out.print(' ' + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("Enter the output file path : ");
                    String outputfilepath = userInput.nextLine();
                    FileWriter fr1 = null;
                    try {
                        fr1 = new FileWriter(outputfilepath);
                        BufferedWriter br1 = new BufferedWriter(fr1);
                        object.print(br1);
                        System.out.println("Output written to file successfully");
                        br1.close();
                    } catch (IOException e) {
                        System.out.println("Exception occured");
                    }

                }
            } else if (userCommand.equalsIgnoreCase("choices")) {
                integerOutcome = object.choices();
                System.out.println("choices outcome " + integerOutcome );
            } else if (userCommand.equalsIgnoreCase("quit")) {
                System.out.println ("Quit");
            } else {
                System.out.println ("Bad command: " + userCommand);
            }
        } while (!userCommand.equalsIgnoreCase("quit"));

        // The user is done so close the stream of user input before ending.

        userInput.close();
    }

}
