package android.filterfw.core;

/* loaded from: classes.dex */
public class ProgramPort extends android.filterfw.core.FieldPort {
    protected java.lang.String mVarName;

    public ProgramPort(android.filterfw.core.Filter filter, java.lang.String str, java.lang.String str2, java.lang.reflect.Field field, boolean z) {
        super(filter, str, field, z);
        this.mVarName = str2;
    }

    @Override // android.filterfw.core.FieldPort, android.filterfw.core.FilterPort
    public java.lang.String toString() {
        return "Program " + super.toString();
    }

    @Override // android.filterfw.core.FieldPort, android.filterfw.core.InputPort
    public synchronized void transfer(android.filterfw.core.FilterContext filterContext) {
        if (this.mValueWaiting) {
            try {
                java.lang.Object obj = this.mField.get(this.mFilter);
                if (obj != null) {
                    ((android.filterfw.core.Program) obj).setHostValue(this.mVarName, this.mValue);
                    this.mValueWaiting = false;
                }
            } catch (java.lang.ClassCastException e) {
                throw new java.lang.RuntimeException("Non Program field '" + this.mField.getName() + "' annotated with ProgramParameter!");
            } catch (java.lang.IllegalAccessException e2) {
                throw new java.lang.RuntimeException("Access to program field '" + this.mField.getName() + "' was denied!");
            }
        }
    }
}
