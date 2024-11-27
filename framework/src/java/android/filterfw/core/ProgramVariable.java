package android.filterfw.core;

/* loaded from: classes.dex */
public class ProgramVariable {
    private android.filterfw.core.Program mProgram;
    private java.lang.String mVarName;

    public ProgramVariable(android.filterfw.core.Program program, java.lang.String str) {
        this.mProgram = program;
        this.mVarName = str;
    }

    public android.filterfw.core.Program getProgram() {
        return this.mProgram;
    }

    public java.lang.String getVariableName() {
        return this.mVarName;
    }

    public void setValue(java.lang.Object obj) {
        if (this.mProgram == null) {
            throw new java.lang.RuntimeException("Attempting to set program variable '" + this.mVarName + "' but the program is null!");
        }
        this.mProgram.setHostValue(this.mVarName, obj);
    }

    public java.lang.Object getValue() {
        if (this.mProgram == null) {
            throw new java.lang.RuntimeException("Attempting to get program variable '" + this.mVarName + "' but the program is null!");
        }
        return this.mProgram.getHostValue(this.mVarName);
    }
}
