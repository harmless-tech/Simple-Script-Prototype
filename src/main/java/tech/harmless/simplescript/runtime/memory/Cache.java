package tech.harmless.simplescript.runtime.memory;

import tech.harmless.simplescript.shared.data.TypedData;

// Register can hold data for multiple loops.
public class Cache {

    // Be aware that multiple register types might share the same register location.
    private final TypedData[] register;

    public Cache() {
        register = new TypedData[3];
    }

    public void setReg(int reg, TypedData data) {
        assert data != null;
        assert register[reg] == null;

        register[reg] = data;
    }

    public TypedData getReg(int reg) {
        TypedData regVar = register[reg];
        register[reg] = null; // Once used the data in the register expires.

        assert regVar != null; // When a register is accessed it should never be null.
        assert register[reg] == null;
        return regVar;
    }
}
