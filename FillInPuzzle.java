import java.io.*;
import java.util.ArrayList;

public class FillInPuzzle {
    static int  integer_array[][];
    static int row,column,n;
    static ArrayList<String> al=new ArrayList<>();
    static int ways;
    static char puzzle_matrix[][];
    // function to create the puzzle box
    public static Boolean loadPuzzle(  BufferedReader br)
    {

        try
        {
            String text;al.clear();
         // reads the input file line by line
            while((text=br.readLine())!= null )
            {
                al.add(text);
            }
            String input=al.get(0);// first line of the input to get the total no of column and row
            String line1[]=input.split(" ");
            column=Integer.parseInt(line1[0]);
            row=Integer.parseInt(line1[1]);
            n=Integer.parseInt(line1[2]);
            integer_array=new int[row][column];
            // input file is read from the second line and is split with spaces and is stored in input_array
            for(int j=1;j<=n;j++)
            {

                String inputlines=al.get(j);
                String input_array[]=inputlines.split(" ");
                int given_inputcol=Integer.parseInt(input_array[0]);
                int given_inputrow=Integer.parseInt((input_array[1]));
                int given_word_length=Integer.parseInt(input_array[2]);
                String direction=input_array[3];
 // if the direction is horizontal colum is fixed and rows are filled
                if(direction.equals("h"))
                {
                    int temp=given_inputcol+given_word_length;

                    for(int end=given_inputcol;end<temp;end++)
                    {
                        integer_array[row-1-given_inputrow][end]=1;
                    }

                }
                // else part deals with the vertical direction and row is fixed and columns varies
                else{

                    int temp=row-1-given_inputrow+given_word_length;
                    for(int end=row-1-given_inputrow;end<temp;end++)
                    {
                        integer_array[end][given_inputcol]=1;
                    }
                }

            }
        }
        catch(Exception e)
        {
            System.out.println("Input file format is wrong");
            return false;
        }
        return true;
    }

    public static Boolean solve( ) {
        int maxsize;//maxsize is used to set the total no of boxes
        if(row==0)
            return false;
        if(row>column)
            maxsize=row;
        else
            maxsize=column;
/* character array is created to store "#" and "*" in the place of 1 and 0 respectively with the maxsize
and store 0 in the extra row or extra column created
 */

        puzzle_matrix=new char[maxsize][maxsize];
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<column;j++)
            {
                //the index value of the integer array is checked for 0 or 1 and # or * is stored in the char array
                if(integer_array[i][j]==1)
                    puzzle_matrix[i][j]='#';
                else
                    puzzle_matrix[i][j]='*';
            }
        }

        for(int i=0;i<maxsize;i++)
        {
            for(int j=0;j<maxsize;j++)
            {
                // extra row or column created is filled with * or #
                if(puzzle_matrix[i][j]!='*' && puzzle_matrix[i][j]!='#')
                    puzzle_matrix[i][j]='*';
            }
        }

// words arrayList is created to store the words that needs to be filled in puzzle box
        ArrayList<String> words=new ArrayList<>();

        for(int i=n+1;i<=n+n;i++)
            words.add(al.get(i));
ways=0;
        solvePuzzle(words, puzzle_matrix, 0, maxsize);
        if(ways>0)
            return true;
        else
            return false;
    }
// function to solve puzzle
    public static void solvePuzzle(ArrayList<String> words,
                                   char matrix[][],
                                   int index, int n)
    {
        if (index < words.size()) {

            String currentWord = words.get(index);
            int maxLen = n - currentWord.length();

            // loop to check the words that can align vertically.
            for (int i = 0; i < n; i++) {

                for (int j = 0; j <= maxLen; j++) {
                    char temp[][]=checkVertical(j, i,
                            matrix, currentWord);
                    if (temp[0][0] != '@') {
                        //System.out.print(temp[0][0]);
                        solvePuzzle(words, temp, index + 1, n);
                    }
                }
            }

            // loop to check the words that can align horizontally.
            for (int i = 0; i < n; i++) {

                for (int j = 0; j <= maxLen; j++) {
                    char temp[][] = checkHorizontal(i, j,
                            matrix, currentWord);

                    if (temp[0][0] != '@') {
                        solvePuzzle(words, temp, index + 1, n);
                    }
                }
            }

        }
        else {
            ways++;
            puzzle_matrix=matrix;
            return;
        }
    }

    public static char[][] checkVertical(int x, int y,
                                         char[][] matrix2,
                                         String currentWord)
    {
        // x represents index of row
        // y represents index of column and currentWord represents the current word in the list
        int n = currentWord.length();
        // matrix3 is created and matrix2 values is stored in it and modified
        char matrix3[][]=new char[matrix2.length][matrix2.length];
        for(int i=0;i<matrix2.length;i++)
        {
            for(int j=0;j<matrix2.length;j++) {
                matrix3[i][j] = matrix2[i][j];
            }
        }
        // checking if the word can fit vertically otherwise next available possiblities are checked with the remaining words
        for (int i = 0; i < n; i++) {
            if (matrix3[x + i][y] == '#' ||
                    matrix3[x + i][y] == currentWord.charAt(i) ||
                    (int)matrix3[x + i][y] == ((int)currentWord.charAt(i))+32 ||
                    (int)matrix3[x + i][y] == ((int)currentWord.charAt(i))-32) {
                matrix3[x + i][y] = currentWord.charAt(i);
            }
            else {

                // this shows that word
                // cannot be placed vertically
                matrix3[0][0] = '@';
                return matrix3;
            }
        }
        return matrix3;
    }


    public static char[][] checkHorizontal(int x, int y,
                                           char[][] matrix2,
                                           String currentWord)
    {
        int n = currentWord.length();
        // matrix2 values are copied to matrix3 and the operations are peformed with the matrix3
        char matrix3[][]=new char[matrix2.length][matrix2.length];
        for(int i=0;i<matrix2.length;i++)
        {
            for(int j=0;j<matrix2.length;j++) {
                matrix3[i][j] = matrix2[i][j];
            }
        }
        // checking if the word can match horizontally
        for (int i = 0; i < n; i++) {
            if (matrix3[x][y + i] == '#' ||
                    matrix3[x][y + i] == currentWord.charAt(i) ||
                    (int)matrix3[x][y + i] == ((int)currentWord.charAt(i))+32 ||
                    (int)matrix3[x][y + i] == ((int)currentWord.charAt(i))-32) {
                matrix3[x][y + i] = currentWord.charAt(i);
            }
            // if the word does not match then the next available possiblities are checked
            else {

                // this shows that word cannot
                // be placed horizontally
                matrix3[0][0] = '@';
                return matrix3;
            }
        }

        return matrix3;
    }
// function to print the words to the outputstream or file
    public static void print(BufferedWriter br1)
    {
        try {

            for (int i = 0; i < puzzle_matrix.length; i++) {
                for (int j = 0; j < puzzle_matrix.length; j++) {
                    if(puzzle_matrix[i][j]!='*') {
                        br1.write(puzzle_matrix[i][j]);
                    }
                    else {
                        br1.write(' ');
                    }
                }
                br1.newLine();
            }
        }
        catch (IOException e) {
            System.out.println("Exception occured");
        }
    }
    // function to print the no of available ways to solve the puzzle
    public static int choices()
    {
        return ways;
    }

}
