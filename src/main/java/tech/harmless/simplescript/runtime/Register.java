package tech.harmless.simplescript.runtime;

//TODO Register should only hold data for one loop. Add this check?
//TODO Can registers be null??
public class Register {

    private final Object[] register;

    public Register() {
        register = new Object[5];
    }

    public void setReg(int reg, Object data) {
        assert data != null;
        assert register[reg] == null;

        register[reg] = data;
    }

    public Object getRegObj(int reg) {
        Object obj = register[reg];
        register[reg] = null; // Once used the data in the register expires.

        assert obj != null; // When a register is accessed it should never be null.
        return obj;
    }

    public <T> T getReg(int reg) {
        return getReg(reg);
    }
}
