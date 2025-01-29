import java.io.BufferedReader;
import java.io.FileReader;
//handles exception - i'm not sure why this is necessary but apparently it is
import java.io.IOException;
import java.util.HashMap;

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

        HashMap<String, String> icode_ifun = new HashMap<String, String>();
        HashMap<String, Integer> rA = new HashMap<String, Integer>();
        HashMap<String, Integer> rB = new HashMap<String, Integer>();

        icode_ifun.put("halt", "00");
        icode_ifun.put("nop", "10");
        icode_ifun.put("rrmovq", "20");
        icode_ifun.put("cmovle", "21");
        icode_ifun.put("cmovl", "22");
        icode_ifun.put("cmove", "23");
        icode_ifun.put("cmovne", "24");
        icode_ifun.put("cmovge", "25");
        icode_ifun.put("cmovg", "26");
        icode_ifun.put("irmovq", "30");
        icode_ifun.put("rmmovq", "40");
        icode_ifun.put("mrmovq", "50");
        icode_ifun.put("addq", "60");
        icode_ifun.put("subq", "61");
        //finish hash map later

        for (i=0; code[i] != null; i++){
            String[] temp = code[i].split(" ");
            temp[0] = icode_ifun.get(temp[0]);
        }
        
    }
}