import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Branch{
    public static void main(String args[]) throws IOException{
        BufferedReader buffered_Reader = new BufferedReader(new FileReader("file.txt"));
        List<String> listOfStrings = new ArrayList<String>();
        String bufferedReaderLine = buffered_Reader.readLine();
        while (bufferedReaderLine != null) {
            listOfStrings.add(bufferedReaderLine);
            bufferedReaderLine = buffered_Reader.readLine();
        }
        buffered_Reader.close();


        //Turn list into array
        String[] arrayOfLines = listOfStrings.toArray(new String[0]);

        
        //print contents of code
        for (int i=0; i<arrayOfLines.length; i++){
            System.out.println(arrayOfLines[i]);
        }

        
        /*
        First, we will go through and resolve the symbolic names based on a variable-sized instruction set. We look at each
        element of the arrayOfLines to determine whether a symbolic name is included. If it is, we note the memory location.
        If not, we incrememnt the memory location based on the instruction.
        */

        

        HashMap<String, Integer> symbolic_names = new HashMap<String, Integer>();
        
        
        int memoryAddress = 0;
        String output = "";

        for (int i=0; i<arrayOfLines.length; i++){
            String line = arrayOfLines[i];

            Integer lineWithSymbolicName = 0;
            //If this is a line with a symbolic name
            if (line.contains(":")){
                String symbolicName = (line.substring(0, line.indexOf(":")));
                symbolic_names.put(symbolicName, memoryAddress);
                lineWithSymbolicName = 1;
            }
            
            //Seperate out the actual instruction and do a switch case
            String[] temp = line.split(" |, ");
            String instruction = temp[0+lineWithSymbolicName]; //The instruction shifts over 1 to the right if theres a symbolic name

            switch (icode_ifun.get(instruction)) {
                case :
                    
                    break;
                default:
                    throw new AssertionError();
            }
        }

        System.out.println(output);
    }

    //Make a hashmap for icode_ifun and the name
    static HashMap<String, String> icode_ifun = new HashMap<String, String>(){{
        put("halt"  , "00"); 
        put("nop"   , "10");
        put("rrmovq", "20");
        put("cmovle", "21");
        put("cmovl" , "22");
        put("cmove" , "23");
        put("cmovne", "24");
        put("cmovge", "25");
        put("cmovg" , "26");
        put("irmovq", "30");
        put("rmmovq", "40");
        put("mrmovq", "50");
        put("addq"  , "60");
        put("subq"  , "61");
        put("andq"  , "62");
        put("xorq"  , "63");
        put("jmp"   , "70");
        put("jle"   , "71");
        put("jl"    , "72");
        put("je"    , "73");
        put("jne"   , "74");
        put("jge"   , "75");
        put("jg"    , "76");
        put("call"  , "80");
        put("ret"   , "90");
        put("pushq" , "A0");
        put("popq"  , "B0");
    }};


    //Make a hasmap for the registers and the number
    static HashMap<String, String> registers = new HashMap<String, String>(){{
        put("%rax", "0");
        put("%rcx", "1");
        put("%rdx", "2");
        put("%rbx", "3");
        put("%rsp", "4");
        put("%rbp", "5");
        put("%rsi", "6");
        put("%rdi", "7");
        put("%r8" , "8");
        put("%r9" , "9");
        put("%r10", "A");
        put("%r11", "B");
        put("%r12", "C");
        put("%r13", "D");
        put("%r14", "E");
    }};

    static HashMap<String, Integer> icode_iLength = new HashMap<String, Integer>(){{
        put("0",1);
        put("1",1);
        put("2",2);
        put("3",6);
        put("4",6);
        put("5",6);
        put("6",2);
        put("7",5);
        put("8",2);
        put("9",5);
        put("A",2);
        put("B",2);
    }};

}