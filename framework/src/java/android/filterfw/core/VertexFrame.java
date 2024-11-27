package android.filterfw.core;

/* loaded from: classes.dex */
public class VertexFrame extends android.filterfw.core.Frame {
    private int vertexFrameId;

    private native int getNativeVboId();

    private native boolean nativeAllocate(int i);

    private native boolean nativeDeallocate();

    private native boolean setNativeData(byte[] bArr, int i, int i2);

    private native boolean setNativeFloats(float[] fArr);

    private native boolean setNativeInts(int[] iArr);

    VertexFrame(android.filterfw.core.FrameFormat frameFormat, android.filterfw.core.FrameManager frameManager) {
        super(frameFormat, frameManager);
        this.vertexFrameId = -1;
        if (getFormat().getSize() <= 0) {
            throw new java.lang.IllegalArgumentException("Initializing vertex frame with zero size!");
        }
        if (!nativeAllocate(getFormat().getSize())) {
            throw new java.lang.RuntimeException("Could not allocate vertex frame!");
        }
    }

    @Override // android.filterfw.core.Frame
    protected synchronized boolean hasNativeAllocation() {
        return this.vertexFrameId != -1;
    }

    @Override // android.filterfw.core.Frame
    protected synchronized void releaseNativeAllocation() {
        nativeDeallocate();
        this.vertexFrameId = -1;
    }

    @Override // android.filterfw.core.Frame
    public java.lang.Object getObjectValue() {
        throw new java.lang.RuntimeException("Vertex frames do not support reading data!");
    }

    @Override // android.filterfw.core.Frame
    public void setInts(int[] iArr) {
        assertFrameMutable();
        if (!setNativeInts(iArr)) {
            throw new java.lang.RuntimeException("Could not set int values for vertex frame!");
        }
    }

    @Override // android.filterfw.core.Frame
    public int[] getInts() {
        throw new java.lang.RuntimeException("Vertex frames do not support reading data!");
    }

    @Override // android.filterfw.core.Frame
    public void setFloats(float[] fArr) {
        assertFrameMutable();
        if (!setNativeFloats(fArr)) {
            throw new java.lang.RuntimeException("Could not set int values for vertex frame!");
        }
    }

    @Override // android.filterfw.core.Frame
    public float[] getFloats() {
        throw new java.lang.RuntimeException("Vertex frames do not support reading data!");
    }

    @Override // android.filterfw.core.Frame
    public void setData(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        assertFrameMutable();
        byte[] array = byteBuffer.array();
        if (getFormat().getSize() != array.length) {
            throw new java.lang.RuntimeException("Data size in setData does not match vertex frame size!");
        }
        if (!setNativeData(array, i, i2)) {
            throw new java.lang.RuntimeException("Could not set vertex frame data!");
        }
    }

    @Override // android.filterfw.core.Frame
    public java.nio.ByteBuffer getData() {
        throw new java.lang.RuntimeException("Vertex frames do not support reading data!");
    }

    @Override // android.filterfw.core.Frame
    public void setBitmap(android.graphics.Bitmap bitmap) {
        throw new java.lang.RuntimeException("Unsupported: Cannot set vertex frame bitmap value!");
    }

    @Override // android.filterfw.core.Frame
    public android.graphics.Bitmap getBitmap() {
        throw new java.lang.RuntimeException("Vertex frames do not support reading data!");
    }

    @Override // android.filterfw.core.Frame
    public void setDataFromFrame(android.filterfw.core.Frame frame) {
        super.setDataFromFrame(frame);
    }

    public int getVboId() {
        return getNativeVboId();
    }

    public java.lang.String toString() {
        return "VertexFrame (" + getFormat() + ") with VBO ID " + getVboId();
    }

    static {
        java.lang.System.loadLibrary("filterfw");
    }
}
