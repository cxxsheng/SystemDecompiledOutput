package android.filterfw.core;

/* loaded from: classes.dex */
public class FinalPort extends android.filterfw.core.FieldPort {
    public FinalPort(android.filterfw.core.Filter filter, java.lang.String str, java.lang.reflect.Field field, boolean z) {
        super(filter, str, field, z);
    }

    @Override // android.filterfw.core.FieldPort
    protected synchronized void setFieldFrame(android.filterfw.core.Frame frame, boolean z) {
        assertPortIsOpen();
        checkFrameType(frame, z);
        if (this.mFilter.getStatus() != 0) {
            throw new java.lang.RuntimeException("Attempting to modify " + this + "!");
        }
        super.setFieldFrame(frame, z);
        super.transfer(null);
    }

    @Override // android.filterfw.core.FieldPort, android.filterfw.core.FilterPort
    public java.lang.String toString() {
        return "final " + super.toString();
    }
}
