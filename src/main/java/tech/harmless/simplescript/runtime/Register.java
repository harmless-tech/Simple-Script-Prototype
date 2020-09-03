package tech.harmless.simplescript.runtime;

import tech.harmless.simplescript.shared.data.TypedData;

//TODO Register should only hold data for one loop. Add this check?
public class Register {

    // Be aware that multiple register types might share the same register location.
    private final TypedData[] register;

    public Register() {
        register = new TypedData[3];
    }

    public void setReg(int reg, TypedData data) {
        assert data != null;
        assert register[reg] == null;

        register[reg] = data;
    }

    //TODO Redo!
    public TypedData getReg(int reg) {
        TypedData regVar = register[reg];
        register[reg] = null; // Once used the data in the register expires.

        assert regVar != null; // When a register is accessed it should never be null.
        assert register[reg] == null; //TODO Remove!!!
        return regVar;
    }
}
