import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Pass2Assembler {
    public static void main(String[] args) throws IOException {
        // Open input files and create an output file
        BufferedReader intermediateFile = new BufferedReader(new FileReader("intermediate.txt"));
        BufferedReader symbolTableFile = new BufferedReader(new FileReader("symbol_table.txt"));
        BufferedReader literalTableFile = new BufferedReader(new FileReader("literal_table.txt"));
        FileWriter outputfile = new FileWriter("pass2_output.txt");

        // Initialize symbol and literal tables
        HashMap<Integer, String> symbolTable = new HashMap<>();
        HashMap<Integer, String> literalTable = new HashMap<>();
        HashMap<Integer, String> literalAddressTable = new HashMap<>();

        // Populate symbol table from the symbol_table.txt file
        int symbolTablePointer = 1;
        String line;
        while ((line = symbolTableFile.readLine()) != null) {
            String[] words = line.split("\t");
            symbolTable.put(symbolTablePointer++, words[1]);
        }

        // Populate literal and literal address tables from the literal_table.txt file
        int literalTablePointer = 1;
        while ((line = literalTableFile.readLine()) != null) {
            String[] words = line.split("\t");
            literalTable.put(literalTablePointer, words[0]);
            literalAddressTable.put(literalTablePointer++, words[1]);
        }

        // Process the intermediate file and generate the output
        while ((line = intermediateFile.readLine()) != null) {
            if (line.substring(1, 6).equalsIgnoreCase("IS,00")) {
                // Handle instruction code 'IS,00'
                outputfile.write("+ 00 0 000\n");
            } else if (line.substring(1, 3).equalsIgnoreCase("IS")) {
                // Handle other instruction codes
                outputfile.write("+ " + line.substring(4, 6) + " ");
                int offset = 0;
                if (line.charAt(9) == ')') {
                    outputfile.write(line.charAt(8) + " ");
                    offset = 3;
                } else {
                    outputfile.write("0 ");
                }
                if (line.charAt(8 + offset) == 'S') {
                    int symbolIndex = Integer.parseInt(line.substring(10 + offset, line.length() - 1));
                    outputfile.write(symbolTable.get(symbolIndex) + "\n");
                } else {
                    int literalIndex = Integer.parseInt(line.substring(10 + offset, line.length() - 1));
                    outputfile.write(literalAddressTable.get(literalIndex) + "\n");
                }
            } else if (line.substring(1, 6).equalsIgnoreCase("DL,01")) {
                // Handle data statement 'DL,01'
                String dataValue = line.substring(10, line.length() - 1);
                String formattedDataValue = "0" + String.format("%03d", Integer.parseInt(dataValue));
                outputfile.write("+ 00 0 " + formattedDataValue + "\n");
            } else {
                // Handle other cases
                outputfile.write("\n");
            }
        }

        // Close all the open files
        outputfile.close();
        intermediateFile.close();
        symbolTableFile.close();
        literalTableFile.close();
    }
}
