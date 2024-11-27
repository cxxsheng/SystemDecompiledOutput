package android.filterfw.core;

/* loaded from: classes.dex */
public class NativeFrame extends android.filterfw.core.Frame {
    private int nativeFrameId;

    private native boolean getNativeBitmap(android.graphics.Bitmap bitmap, int i, int i2);

    private native boolean getNativeBuffer(android.filterfw.core.NativeBuffer nativeBuffer);

    private native int getNativeCapacity();

    private native byte[] getNativeData(int i);

    private native float[] getNativeFloats(int i);

    private native int[] getNativeInts(int i);

    private native boolean nativeAllocate(int i);

    private native boolean nativeCopyFromGL(android.filterfw.core.GLFrame gLFrame);

    private native boolean nativeCopyFromNative(android.filterfw.core.NativeFrame nativeFrame);

    private native boolean nativeDeallocate();

    private static native int nativeFloatSize();

    private static native int nativeIntSize();

    private native boolean setNativeBitmap(android.graphics.Bitmap bitmap, int i, int i2);

    private native boolean setNativeData(byte[] bArr, int i, int i2);

    private native boolean setNativeFloats(float[] fArr);

    private native boolean setNativeInts(int[] iArr);

    NativeFrame(android.filterfw.core.FrameFormat frameFormat, android.filterfw.core.FrameManager frameManager) {
        super(frameFormat, frameManager);
        this.nativeFrameId = -1;
        int size = frameFormat.getSize();
        nativeAllocate(size);
        setReusable(size != 0);
    }

    @Override // android.filterfw.core.Frame
    protected synchronized void releaseNativeAllocation() {
        nativeDeallocate();
        this.nativeFrameId = -1;
    }

    @Override // android.filterfw.core.Frame
    protected synchronized boolean hasNativeAllocation() {
        return this.nativeFrameId != -1;
    }

    @Override // android.filterfw.core.Frame
    public int getCapacity() {
        return getNativeCapacity();
    }

    @Override // android.filterfw.core.Frame
    public java.lang.Object getObjectValue() {
        if (getFormat().getBaseType() != 8) {
            return getData();
        }
        java.lang.Class objectClass = getFormat().getObjectClass();
        if (objectClass == null) {
            throw new java.lang.RuntimeException("Attempting to get object data from frame that does not specify a structure object class!");
        }
        if (!android.filterfw.core.NativeBuffer.class.isAssignableFrom(objectClass)) {
            throw new java.lang.RuntimeException("NativeFrame object class must be a subclass of NativeBuffer!");
        }
        try {
            android.filterfw.core.NativeBuffer nativeBuffer = (android.filterfw.core.NativeBuffer) objectClass.newInstance();
            if (!getNativeBuffer(nativeBuffer)) {
                throw new java.lang.RuntimeException("Could not get the native structured data for frame!");
            }
            nativeBuffer.attachToFrame(this);
            return nativeBuffer;
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("Could not instantiate new structure instance of type '" + objectClass + "'!");
        }
    }

    @Override // android.filterfw.core.Frame
    public void setInts(int[] iArr) {
        assertFrameMutable();
        if (iArr.length * nativeIntSize() > getFormat().getSize()) {
            throw new java.lang.RuntimeException("NativeFrame cannot hold " + iArr.length + " integers. (Can only hold " + (getFormat().getSize() / nativeIntSize()) + " integers).");
        }
        if (!setNativeInts(iArr)) {
            throw new java.lang.RuntimeException("Could not set int values for native frame!");
        }
    }

    @Override // android.filterfw.core.Frame
    public int[] getInts() {
        return getNativeInts(getFormat().getSize());
    }

    @Override // android.filterfw.core.Frame
    public void setFloats(float[] fArr) {
        assertFrameMutable();
        if (fArr.length * nativeFloatSize() > getFormat().getSize()) {
            throw new java.lang.RuntimeException("NativeFrame cannot hold " + fArr.length + " floats. (Can only hold " + (getFormat().getSize() / nativeFloatSize()) + " floats).");
        }
        if (!setNativeFloats(fArr)) {
            throw new java.lang.RuntimeException("Could not set int values for native frame!");
        }
    }

    @Override // android.filterfw.core.Frame
    public float[] getFloats() {
        return getNativeFloats(getFormat().getSize());
    }

    @Override // android.filterfw.core.Frame
    public void setData(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        assertFrameMutable();
        byte[] array = byteBuffer.array();
        int i3 = i2 + i;
        if (i3 > byteBuffer.limit()) {
            throw new java.lang.RuntimeException("Offset and length exceed buffer size in native setData: " + i3 + " bytes given, but only " + byteBuffer.limit() + " bytes available!");
        }
        if (getFormat().getSize() != i2) {
            throw new java.lang.RuntimeException("Data size in setData does not match native frame size: Frame size is " + getFormat().getSize() + " bytes, but " + i2 + " bytes given!");
        }
        if (!setNativeData(array, i, i2)) {
            throw new java.lang.RuntimeException("Could not set native frame data!");
        }
    }

    @Override // android.filterfw.core.Frame
    public java.nio.ByteBuffer getData() {
        byte[] nativeData = getNativeData(getFormat().getSize());
        if (nativeData == null) {
            return null;
        }
        return java.nio.ByteBuffer.wrap(nativeData);
    }

    @Override // android.filterfw.core.Frame
    public void setBitmap(android.graphics.Bitmap bitmap) {
        assertFrameMutable();
        if (getFormat().getNumberOfDimensions() != 2) {
            throw new java.lang.RuntimeException("Attempting to set Bitmap for non 2-dimensional native frame!");
        }
        if (getFormat().getWidth() != bitmap.getWidth() || getFormat().getHeight() != bitmap.getHeight()) {
            throw new java.lang.RuntimeException("Bitmap dimensions do not match native frame dimensions!");
        }
        android.graphics.Bitmap convertBitmapToRGBA = convertBitmapToRGBA(bitmap);
        if (!setNativeBitmap(convertBitmapToRGBA, convertBitmapToRGBA.getByteCount(), getFormat().getBytesPerSample())) {
            throw new java.lang.RuntimeException("Could not set native frame bitmap data!");
        }
    }

    @Override // android.filterfw.core.Frame
    public android.graphics.Bitmap getBitmap() {
        if (getFormat().getNumberOfDimensions() != 2) {
            throw new java.lang.RuntimeException("Attempting to get Bitmap for non 2-dimensional native frame!");
        }
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(getFormat().getWidth(), getFormat().getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        if (!getNativeBitmap(createBitmap, createBitmap.getByteCount(), getFormat().getBytesPerSample())) {
            throw new java.lang.RuntimeException("Could not get bitmap data from native frame!");
        }
        return createBitmap;
    }

    @Override // android.filterfw.core.Frame
    public void setDataFromFrame(android.filterfw.core.Frame frame) {
        if (getFormat().getSize() < frame.getFormat().getSize()) {
            throw new java.lang.RuntimeException("Attempting to assign frame of size " + frame.getFormat().getSize() + " to smaller native frame of size " + getFormat().getSize() + "!");
        }
        if (frame instanceof android.filterfw.core.NativeFrame) {
            nativeCopyFromNative((android.filterfw.core.NativeFrame) frame);
            return;
        }
        if (frame instanceof android.filterfw.core.GLFrame) {
            nativeCopyFromGL((android.filterfw.core.GLFrame) frame);
        } else if (frame instanceof android.filterfw.core.SimpleFrame) {
            setObjectValue(frame.getObjectValue());
        } else {
            super.setDataFromFrame(frame);
        }
    }

    public java.lang.String toString() {
        return "NativeFrame id: " + this.nativeFrameId + " (" + getFormat() + ") of size " + getCapacity();
    }

    static {
        java.lang.System.loadLibrary("filterfw");
    }
}
