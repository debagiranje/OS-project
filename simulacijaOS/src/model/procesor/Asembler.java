package model.procesor;

public class Asembler {
    private Registri registri;
    private StringBuilder instrukcija; // instrukcija = opcode + operand

    public Asembler() {
        this.registri = new Registri();
        this.instrukcija = new StringBuilder();
    }

    public void assemble(String assemblyCode) {
        String[] lines = assemblyCode.split("\n");

        for (String line : lines) {
            processLine(line.trim());
        }
    }

    private void processLine(String line) {
        if (line.isEmpty()) {
            return;
        }

        String[] parts = line.split("\\s+");
        String opcode = parts[0].toUpperCase();

        switch (opcode) {
            case "PUSH": // op 1
                push(parts);
                break;
            case "POP"://op 2
                pop(parts);
                break;
            case "ADD": // op3
                add();
                break;
            case "SUB"://op4
            	sub();
            case "MUL": //op5
            	mul();
            default:
                break;
        }

        registri.toString();
    }

    private void push(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Format instrukcije je <PUSH> <VRIJEDNOST>");
        }

        int val = Integer.parseInt(parts[1]);
        registri.pushToStack(Registri.intToBinaryString(val));
        instrukcija.append(Registri.intToBinaryString(1) + Registri.intToBinaryString(val));
    }

    private void pop(String[] parts) {
        if (parts.length != 1) {
            throw new IllegalArgumentException("Format instrukcije je <POP>");
        }
        registri.popFromStack();
        instrukcija.append(Registri.intToBinaryString(2));
    }

    private void add() {
    	 int br = Registri.binaryStringToInt(registri.popFromStack());
         int br2 = Registri.binaryStringToInt(registri.popFromStack());
         int rez = br + br2;
         registri.pushToStack(Registri.intToBinaryString(rez));
         instrukcija.append(Registri.intToBinaryString(3) + Registri.intToBinaryString(rez));
    }
    
    private void sub() {
    	 int br = Registri.binaryStringToInt(registri.popFromStack());
         int br2 = Registri.binaryStringToInt(registri.popFromStack());
         int rez = br - br2;
         registri.pushToStack(Registri.intToBinaryString(rez));
         instrukcija.append(Registri.intToBinaryString(4) + Registri.intToBinaryString(rez));
    }
    
    private void mul() {
        int br = Registri.binaryStringToInt(registri.popFromStack());
        int br2 = Registri.binaryStringToInt(registri.popFromStack());
        int rez = br * br2;
        registri.pushToStack(Registri.intToBinaryString(rez));
        instrukcija.append(Registri.intToBinaryString(5) + Registri.intToBinaryString(rez));
    }
    
    // dodaj div i handluj dijeljenje sa nulom 
    
    public String getInstrukcija() {
        return instrukcija.toString();
    }

    public Registri getRegisterManager() {
        return registri;
    }
}