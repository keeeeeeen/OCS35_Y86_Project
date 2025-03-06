import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Branch{
    static int memoryAddress = 0;

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

            System.out.print(returnMachineCode(temp, lineWithSymbolicName));
        }

        //Resolve the symbolic names using the symbolic_names hasmap

    }

    public static String returnMachineCode(String temp[], int lineWithSymbolicName){
        String instruction = temp[0+lineWithSymbolicName]; //The instruction shifts over 1 to the right if theres a symbolic name
        String output = "";

        switch (instruction) {
            case ".pos":
                memoryAddress = Integer.parseInt(temp[1+lineWithSymbolicName]);
                return "";
            case ".align":
                int align_boundary = Integer.parseInt(temp[1+lineWithSymbolicName]);
                int number_of_zeros = 0;
                if (memoryAddress / align_boundary != 0){
                    number_of_zeros = align_boundary - memoryAddress % align_boundary;
                }
                for (int k=0; k < number_of_zeros; k++){
                    output += "0";
                    memoryAddress++;
                }
                break;
            case ".long":
                String value = temp[1+lineWithSymbolicName];
                output = littleEndian(value, 4);
                memoryAddress += 4;
                break;
            case ".quad":
                value = temp[1+lineWithSymbolicName];
                output = littleEndian(value, 8);
                memoryAddress += 8;
                break;
            case "halt":
                output = "00";
                memoryAddress += 1;
                break;
            case "nop":
                break;
            case "rrmovl":
                output = "20";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "irmovl":
                output = "30F";
                output += registers.get(temp[2+lineWithSymbolicName]);
                output += littleEndian(temp[1+lineWithSymbolicName], 4);
                memoryAddress += 4;
                break;
            case "addq":
                
                break;
            case "subq":
                
                break;
            case "andq":
                
                break;
            case "xorq":
                
                break;
            case "jmp":
                
                break;
            case "jle":
                
                break;
            case "jl":
                
                break;
            case "je":
                
                break;
            case "jne":
                
                break;
            case "jge":
                
                break;
            case "jg":
                
                break;
            case "call":
                
                break;
            case "ret":
                
                break;
            case "pushq":
                
                break;
            case "popq":
                
                break;
            default:
                throw new AssertionError();
        }
        return output;
    }

    static String littleEndian(String value, Integer length){
        value = value.split("0x")[1];
        
        Integer currentLength = value.length();
        for (int i=0; i<length*2-currentLength; i++){
            value = "0" + value;
        }

        String output="";
        for (int i=0; i<length; i++){
            output += value.substring(2*length-2*i-2, 2*length-2*i);
        }
        
        return output;
    }

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

    //Make a hashmap for the length of the instruction
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

    //Make a hasmap for the symbolic names
    static HashMap<String, Integer> symbolic_names = new HashMap<String, Integer>();

}