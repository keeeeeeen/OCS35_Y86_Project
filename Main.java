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
        //HashMap<String, Integer> rA = new HashMap<String, Integer>();
        //HashMap<String, Integer> rB = new HashMap<String, Integer>();

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
        icode_ifun.put("andq", "62");
        icode_ifun.put("xorq", "63");
        icode_ifun.put("jmp", "70");
        icode_ifun.put("jle", "71");
        icode_ifun.put("jl", "72");
        icode_ifun.put("je", "73");
        icode_ifun.put("jne", "74");
        icode_ifun.put("jge", "75");
        icode_ifun.put("jg", "76");
        icode_ifun.put("call", "80");
        icode_ifun.put("ret", "90");
        icode_ifun.put("pushq", "A0");
        icode_ifun.put("popq", "B0");

        HashMap<String, String> registers = new HashMap<String, String>();
        registers.put("%rax", "0");
        registers.put("%rcx", "1");
        registers.put("%rdx", "2");
        registers.put("%rbx", "3");
        registers.put("%rsp", "4");
        registers.put("%rbp", "5");
        //finish hash map later

        HashMap<String, String> symbolic_names = new HashMap<String, String>();
        int length, line_num = 0;

        for (i=0; code[i] != null; i++){
            System.out.println("");
            length = code[i].length();
            //setting up symbolic names to a hashmap
            //delete the colon and add an element to the hashmap e.g. main --> 0x0
            //i'm assuming that the symbolic name declarations are done before they are used i.e. loop: is done before call loop - fix!
            if(code[i].charAt(length-1) == ':'){
                symbolic_names.put(code[i].replace(":", ""), ""+line_num);
                continue;
            }

            line_num += 10;

            //split each line by a comma or a blank space
            String[] temp = code[i].split(", | ");
            temp[0] = icode_ifun.get(temp[0]);
            System.out.println(temp[0]);

            char icode = temp[0].charAt(0);
            //set rA and rB for cmov and op
            if (icode == '2' || icode == '6'){
                temp[1] = registers.get(temp[1]);
                temp[2] = registers.get(temp[2]);
                System.out.println(temp[1]);
                System.out.println(temp[2]);
            }

            //set rA and rB for push and pop
            else if (icode == 'A' || icode == 'B'){
                temp[1] = registers.get(temp[1]) + 'F';
            }

            //set rA and rB for irmovq
            else if (icode == '3'){
                temp[1] = 'F' + registers.get(temp[1]);
            }

            //set rA, rB, and D for rmmovq
            else if (icode == '4'){
                temp[1] = registers.get(temp[1]);
                //take away the displacement and parentheses e.g. 8(%rax) --> %rax
                String[] register_displacement = temp[2].split("\\(");
                temp[2] = register_displacement[1].replace(")", "");
                temp[2] = registers.get(temp[2]);
                //add displacement to the end of the string
                temp[2] += register_displacement[0];
                System.out.println(temp[1]);
                System.out.println(temp[2]);
            }

            //set rA, rB, and D for mrmovq
            else if (icode == '5'){
                //take away the displacement and parentheses e.g. 8(%rax) --> %rax
                String[] register_displacement = temp[1].split("\\(");
                temp[1] = register_displacement[1].replace(")", "");
                temp[1] = registers.get(temp[1]);

                temp[2] = registers.get(temp[2]);
                //add displacement to the end of the string
                temp[2] += register_displacement[0];
                System.out.println(temp[1]);
                System.out.println(temp[2]);
            }
            
            else if (icode == '7' || icode == '8'){
                temp[1] = symbolic_names.get(temp[1]);
                System.out.println(temp[1]);
            }
        }
    }
}