import java.io.BufferedReader;
import java.io.FileReader;
//handles exception - i'm not sure why this is necessary but apparently it is
import java.io.IOException;

class Main{
    public static void main(String args[]) throws IOException{
        FileReader fr = new FileReader("file.txt");
        BufferedReader br = new BufferedReader(fr);
        //if we have more than 100 lines then we can just make the number bigger
        //maybe use an ArrayList if it needs to be resizable? idk whether this is strictly necessary though
        String[] code = new String [100];
        //to store one line at a time
        String line = br.readLine();

        int i=0;
        while (line != null){
            code[i] = line;
            line = br.readLine();
            i++;
        }
        br.close();

        //print contents of code
        for (i=0; code[i] != null; i++){
            System.out.println(code[i]);
        }
    }
}