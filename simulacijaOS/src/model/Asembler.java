package model;

//https://www.youtube.com/watch?v=qM3-OVfVbLs
//https://en.wikipedia.org/wiki/Stack_machine

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class Asembler {
    private List<Integer> machineCode;
    private Map<String, Integer> memory;

    public Asembler() {
        machineCode = new ArrayList<>();
        memory = new HashMap<>();
    }

    public void loadAssemblyCode(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processAssemblyInstruction(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processAssemblyInstruction(String instruction) {
        // Parse the instruction and perform corresponding actions
        // Example: LOAD 10
        String[] parts = instruction.split("\\s+");
        String opcode = parts[0];
        String operand = parts[1];

        switch (opcode) {
            case "LOAD":
                load(Integer.parseInt(operand));
                break;
            case "STORE":
                store(Integer.parseInt(operand));
                break;
            case "ADD":
                add();
                break;
            case "SUB":
                subtract();
                break;
            case "MULT":
                multiply();
                break;
            case "DIV":
                divide();
                break;
            case "JUMP":
                jump(Integer.parseInt(operand));
                break;
            case "HALT":
                halt();
                break;
        }
    }

    private void load(int value) {
        machineCode.add(value);
    }

    private void store(int address) {
        memory.put(Integer.toString(address), machineCode.remove(machineCode.size() - 1));
    }

    private void add() {
        int operand1 = machineCode.remove(machineCode.size() - 1);
        int operand2 = machineCode.remove(machineCode.size() - 1);
        machineCode.add(operand1 + operand2);
    }

    private void subtract() {
        int operand1 = machineCode.remove(machineCode.size() - 1);
        int operand2 = machineCode.remove(machineCode.size() - 1);
        machineCode.add(operand1 - operand2);
    }

    private void multiply() {
        int operand1 = machineCode.remove(machineCode.size() - 1);
        int operand2 = machineCode.remove(machineCode.size() - 1);
        machineCode.add(operand1 * operand2);
    }

    private void divide() {
        int operand1 = machineCode.remove(machineCode.size() - 1);
        int operand2 = machineCode.remove(machineCode.size() - 1);
        machineCode.add(operand1 / operand2);
    }

    private void jump(int address) {
        int instructionIndex = address - 1;
        if (instructionIndex >= 0 && instructionIndex < machineCode.size()) {
            // Jump to the specified instruction
            // Adjust the instruction index accordingly
            machineCode.remove(machineCode.size() - 1);
            machineCode.add(instructionIndex);
        }
    }

    private void halt() {
        // Terminate the execution
    }

    public void execute() {
        int instructionPointer = 0;
        while (instructionPointer < machineCode.size()) {
            int instruction = machineCode.get(instructionPointer);
            instructionPointer++;
            
            // Perform the necessary operations based on the instruction
            // You may need additional logic here depending on the instruction set
            
            // Example: Print the instruction
            System.out.println(instruction);
        }
    }

    public void displayMemory() {
        System.out.println("Memory:");
        for (Map.Entry<String, Integer> entry : memory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        Asembler assembler = new Asembler();
        assembler.loadAssemblyCode("assembly_code.asm");
        assembler.execute();
        assembler.displayMemory();
    }
}
