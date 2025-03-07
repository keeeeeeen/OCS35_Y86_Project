import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Branch2{
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

        
        //trim (remove whitespaces before and after) + print contents of code
        for (int i=0; i<arrayOfLines.length; i++){
            arrayOfLines[i] = arrayOfLines[i].trim();
            //System.out.println(arrayOfLines[i]);
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
                //convert the memory address to hex before saving it in the hash map
                symbolic_names.put(symbolicName, Integer.toHexString(memoryAddress));
                lineWithSymbolicName = 1;
            }
            
            //Seperate out the actual instruction and do a switch case
            String[] temp = line.split(" |, ");

            System.out.println(returnMachineCode(temp, lineWithSymbolicName));
        }

        System.out.println(symbolic_names);

        //Resolve the symbolic names using the symbolic_names hasmap

    }

    public static String returnMachineCode(String temp[], int lineWithSymbolicName){
        //if there is no instruction after the symbolic name, return nothing
        //to prevent accessing temp[1] when there is no temp[1]
        if (lineWithSymbolicName == 1 && temp.length == 1){
            return "";
        }
        String instruction = temp[0+lineWithSymbolicName]; //The instruction shifts over 1 to the right if theres a symbolic name
        String output = "";

        switch (instruction) {
            case ".pos":
                //if the number is in hex format - change to decimal
                if(temp[1+lineWithSymbolicName].length() > 1 && temp[1+lineWithSymbolicName].substring(0,2).equals("0x")){
                    memoryAddress = Integer.parseInt((temp[1+lineWithSymbolicName].substring(2)), 16);
                }
                else{
                    memoryAddress = Integer.parseInt(temp[1+lineWithSymbolicName]);
                }
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
                memoryAddress += 2;
                break;
            case "rrmovq":
                output = "20";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "cmovle":
                output = "21";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "cmovl":
                output = "22";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "cmove":
                output = "23";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "cmovne":
                output = "24";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "cmovge":
                output = "25";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "cmovg":
                output = "26";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "irmovq":
                output = "30F";
                output += registers.get(temp[2+lineWithSymbolicName]);
                //output += littleEndian(temp[1+lineWithSymbolicName], 4);

                //starts with $: convert to a 8-byte hex number, pad with zeros in the left
                if(temp[1+lineWithSymbolicName].charAt(0) == '$'){
                    int int_convert = Integer.parseInt(temp[1+lineWithSymbolicName].substring(1));
                    output += String.format("%08x", int_convert);
                }
                //else - the number is already in hex, just pad the left with more zeros
                else{
                    output += String.format("%8s", temp[1+lineWithSymbolicName].substring(2)).replace(' ', '0');
                }
                memoryAddress += 10;
                break;
            case "addq":
                output = "60";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "subq":
                output = "61";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "andq":
                output = "62";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "xorq":
                output = "63";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += registers.get(temp[2+lineWithSymbolicName]);
                memoryAddress += 2;
                break;
            case "jmp":
                output = "70";
                //get the 8 byte address from hash map
                output += symbolic_names.get(temp[1+lineWithSymbolicName]);
                memoryAddress += 9;
                break;
            case "jle":
                output = "71";
                output += symbolic_names.get(temp[1+lineWithSymbolicName]);
                memoryAddress += 9;
                break;
            case "jl":
                output = "72";
                output += symbolic_names.get(temp[1+lineWithSymbolicName]);
                memoryAddress += 9;
                break;
            case "je":
                output = "73";
                output += symbolic_names.get(temp[1+lineWithSymbolicName]);
                memoryAddress += 9;
                break;
            case "jne":
                output = "74";
                output += symbolic_names.get(temp[1+lineWithSymbolicName]);
                memoryAddress += 9;
                break;
            case "jge":
                output = "75";
                output += symbolic_names.get(temp[1+lineWithSymbolicName]);
                memoryAddress += 9;
                break;
            case "jg":
                output = "76";
                output += symbolic_names.get(temp[1+lineWithSymbolicName]);
                memoryAddress += 9;
                break;
            case "call":
                output = "80";
                output += symbolic_names.get(temp[1+lineWithSymbolicName]);
                System.out.println(temp[1+lineWithSymbolicName]);
                memoryAddress += 9;
                break;
            case "ret":
                output = "90";
                memoryAddress += 1;
                break;
            case "pushq":
                output = "A0";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += "F";
                memoryAddress += 2;
                break;
            case "popq":
                output = "B0";
                output += registers.get(temp[1+lineWithSymbolicName]);
                output += "F";
                memoryAddress += 2;
                break;
            default:
                break;
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
        put("3",10);
        put("4",10);
        put("5",10);
        put("6",2);
        put("7",9);
        put("8",9);
        put("9",1);
        put("A",2);
        put("B",2);
    }};

    //Make a hasmap for the symbolic names
    static HashMap<String, String> symbolic_names = new HashMap<String, String>();

}