package android.filterfw.core;

/* loaded from: classes.dex */
public class FieldPort extends android.filterfw.core.InputPort {
    protected java.lang.reflect.Field mField;
    protected boolean mHasFrame;
    protected java.lang.Object mValue;
    protected boolean mValueWaiting;

    public FieldPort(android.filterfw.core.Filter filter, java.lang.String str, java.lang.reflect.Field field, boolean z) {
        super(filter, str);
        this.mValueWaiting = false;
        this.mField = field;
        this.mHasFrame = z;
    }

    @Override // android.filterfw.core.FilterPort
    public void clear() {
    }

    @Override // android.filterfw.core.FilterPort
    public void pushFrame(android.filterfw.core.Frame frame) {
        setFieldFrame(frame, false);
    }

    @Override // android.filterfw.core.FilterPort
    public void setFrame(android.filterfw.core.Frame frame) {
        setFieldFrame(frame, true);
    }

    @Override // android.filterfw.core.InputPort
    public java.lang.Object getTarget() {
        try {
            return this.mField.get(this.mFilter);
        } catch (java.lang.IllegalAccessException e) {
            return null;
        }
    }

    @Override // android.filterfw.core.InputPort
    public synchronized void transfer(android.filterfw.core.FilterContext filterContext) {
        if (this.mValueWaiting) {
            try {
                this.mField.set(this.mFilter, this.mValue);
                this.mValueWaiting = false;
                if (filterContext != null) {
                    this.mFilter.notifyFieldPortValueUpdated(this.mName, filterContext);
                }
            } catch (java.lang.IllegalAccessException e) {
                throw new java.lang.RuntimeException("Access to field '" + this.mField.getName() + "' was denied!");
            }
        }
    }

    @Override // android.filterfw.core.FilterPort
    public synchronized android.filterfw.core.Frame pullFrame() {
        throw new java.lang.RuntimeException("Cannot pull frame on " + this + "!");
    }

    @Override // android.filterfw.core.FilterPort
    public synchronized boolean hasFrame() {
        return this.mHasFrame;
    }

    @Override // android.filterfw.core.InputPort
    public synchronized boolean acceptsFrame() {
        return !this.mValueWaiting;
    }

    @Override // android.filterfw.core.FilterPort
    public java.lang.String toString() {
        return "field " + super.toString();
    }

    protected synchronized void setFieldFrame(android.filterfw.core.Frame frame, boolean z) {
        assertPortIsOpen();
        checkFrameType(frame, z);
        java.lang.Object objectValue = frame.getObjectValue();
        if ((objectValue == null && this.mValue != null) || !objectValue.equals(this.mValue)) {
            this.mValue = objectValue;
            this.mValueWaiting = true;
        }
        this.mHasFrame = true;
    }
}
