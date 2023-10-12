package model.procesor;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

// imacemo osnovne interne registre -- IR, MAR, MBR, neki dict GP registara pa sta god oni bili, i "stack" registar
// posto mi imamo 0 adresni asembler koji priti mac radi kao stek masina, pa cemo na tom steku operisati
// mozda nam gp ni ne trebaju -- nama je stack kinda gp

public class Registri {

    private String instructionRegister;
    private String memoryBufferRegister;
    private String memoryAddressRegister;

    private Map<String, String> generalPurposeRegisters;
    private Stack<String> stackRegister;

    public Registri() {
        this.generalPurposeRegisters = new HashMap<>();
        this.stackRegister = new Stack<>();
        initGeneralPurposeRegisters();
        initInternalRegisters();
    }

    private void initGeneralPurposeRegisters() {
        generalPurposeRegisters.put("A", "0011"); 
        generalPurposeRegisters.put("B", "0100");
        generalPurposeRegisters.put("C", "0101");
        generalPurposeRegisters.put("D", "0110");
    }

    private void initInternalRegisters() {
        setInstructionRegister("0000"); 
        setMemoryBufferRegister("0001");
        setMemoryAddressRegister("0010");
    }

    public String getRegisterValue(String registerName) {
        if (generalPurposeRegisters.containsKey(registerName)) {
            return generalPurposeRegisters.get(registerName);
        } else {
            throw new IllegalArgumentException("Nepostojeci reg " + registerName);
        }
    }

    public void setRegisterValue(String registerName, String value) {
        if (generalPurposeRegisters.containsKey(registerName)) {
            generalPurposeRegisters.put(registerName, value);
        } else {
            throw new IllegalArgumentException("Nepostojeci reg: " + registerName);
        }
    }

    public void pushToStack(String value) {
        stackRegister.push(value);
        System.out.println("Na stack dodata vrijednost: " + value);
    }

    public String popFromStack() {
        if (!stackRegister.isEmpty()) {
            return stackRegister.pop();
        } else {
        	 return "prazan";
        }
    }
    
    public static int binaryStringToInt(String binaryString) {
        try {
            return Integer.parseInt(binaryString, 2);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("err str " + binaryString);
        }
    }
    
    public static String intToBinaryString(int intValue) {
    	String binaryString = Integer.toBinaryString(intValue);

        while (binaryString.length() < 4) {
            binaryString = "0" + binaryString;
        }

    
        if (binaryString.length() > 4) {
            binaryString = binaryString.substring(binaryString.length() - 4);
        }

        return binaryString;
    }

    @Override
    public String toString() {
        return "Trenutne vrijednosti na registrima: [instructionRegister=" + instructionRegister + ", memoryBufferRegister=" + memoryBufferRegister
                + ", memoryAddressRegister=" + memoryAddressRegister + ", stackRegister=" + stackRegister + "]";
    }

    public String getInstructionRegister() {
        return instructionRegister;
    }

    public void setInstructionRegister(String instructionRegister) {
        this.instructionRegister = instructionRegister;
    }

    public String getMemoryBufferRegister() {
        return memoryBufferRegister;
    }

    public void setMemoryBufferRegister(String memoryBufferRegister) {
        this.memoryBufferRegister = memoryBufferRegister;
    }

    public String getMemoryAddressRegister() {
        return memoryAddressRegister;
    }

    public void setMemoryAddressRegister(String memoryAddressRegister) {
        this.memoryAddressRegister = memoryAddressRegister;
    }
}
