package android.filterfw.core;

/* loaded from: classes.dex */
public class SimpleFrame extends android.filterfw.core.Frame {
    private java.lang.Object mObject;

    SimpleFrame(android.filterfw.core.FrameFormat frameFormat, android.filterfw.core.FrameManager frameManager) {
        super(frameFormat, frameManager);
        initWithFormat(frameFormat);
        setReusable(false);
    }

    static android.filterfw.core.SimpleFrame wrapObject(java.lang.Object obj, android.filterfw.core.FrameManager frameManager) {
        android.filterfw.core.SimpleFrame simpleFrame = new android.filterfw.core.SimpleFrame(android.filterfw.format.ObjectFormat.fromObject(obj, 1), frameManager);
        simpleFrame.setObjectValue(obj);
        return simpleFrame;
    }

    private void initWithFormat(android.filterfw.core.FrameFormat frameFormat) {
        int length = frameFormat.getLength();
        switch (frameFormat.getBaseType()) {
            case 2:
                this.mObject = new byte[length];
                break;
            case 3:
                this.mObject = new short[length];
                break;
            case 4:
                this.mObject = new int[length];
                break;
            case 5:
                this.mObject = new float[length];
                break;
            case 6:
                this.mObject = new double[length];
                break;
            default:
                this.mObject = null;
                break;
        }
    }

    @Override // android.filterfw.core.Frame
    protected boolean hasNativeAllocation() {
        return false;
    }

    @Override // android.filterfw.core.Frame
    protected void releaseNativeAllocation() {
    }

    @Override // android.filterfw.core.Frame
    public java.lang.Object getObjectValue() {
        return this.mObject;
    }

    @Override // android.filterfw.core.Frame
    public void setInts(int[] iArr) {
        assertFrameMutable();
        setGenericObjectValue(iArr);
    }

    @Override // android.filterfw.core.Frame
    public int[] getInts() {
        if (this.mObject instanceof int[]) {
            return (int[]) this.mObject;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setFloats(float[] fArr) {
        assertFrameMutable();
        setGenericObjectValue(fArr);
    }

    @Override // android.filterfw.core.Frame
    public float[] getFloats() {
        if (this.mObject instanceof float[]) {
            return (float[]) this.mObject;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setData(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        assertFrameMutable();
        setGenericObjectValue(java.nio.ByteBuffer.wrap(byteBuffer.array(), i, i2));
    }

    @Override // android.filterfw.core.Frame
    public java.nio.ByteBuffer getData() {
        if (this.mObject instanceof java.nio.ByteBuffer) {
            return (java.nio.ByteBuffer) this.mObject;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setBitmap(android.graphics.Bitmap bitmap) {
        assertFrameMutable();
        setGenericObjectValue(bitmap);
    }

    @Override // android.filterfw.core.Frame
    public android.graphics.Bitmap getBitmap() {
        if (this.mObject instanceof android.graphics.Bitmap) {
            return (android.graphics.Bitmap) this.mObject;
        }
        return null;
    }

    private void setFormatObjectClass(java.lang.Class cls) {
        android.filterfw.core.MutableFrameFormat mutableCopy = getFormat().mutableCopy();
        mutableCopy.setObjectClass(cls);
        setFormat(mutableCopy);
    }

    @Override // android.filterfw.core.Frame
    protected void setGenericObjectValue(java.lang.Object obj) {
        android.filterfw.core.FrameFormat format = getFormat();
        if (format.getObjectClass() == null) {
            setFormatObjectClass(obj.getClass());
        } else if (!format.getObjectClass().isAssignableFrom(obj.getClass())) {
            throw new java.lang.RuntimeException("Attempting to set object value of type '" + obj.getClass() + "' on SimpleFrame of type '" + format.getObjectClass() + "'!");
        }
        this.mObject = obj;
    }

    public java.lang.String toString() {
        return "SimpleFrame (" + getFormat() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
