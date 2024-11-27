package android.filterfw.core;

/* loaded from: classes.dex */
public class GLFrame extends android.filterfw.core.Frame {
    public static final int EXISTING_FBO_BINDING = 101;
    public static final int EXISTING_TEXTURE_BINDING = 100;
    public static final int EXTERNAL_TEXTURE = 104;
    public static final int NEW_FBO_BINDING = 103;
    public static final int NEW_TEXTURE_BINDING = 102;
    private int glFrameId;
    private android.filterfw.core.GLEnvironment mGLEnvironment;
    private boolean mOwnsTexture;

    private native boolean generateNativeMipMap();

    private native boolean getNativeBitmap(android.graphics.Bitmap bitmap);

    private native byte[] getNativeData();

    private native int getNativeFboId();

    private native float[] getNativeFloats();

    private native int[] getNativeInts();

    private native int getNativeTextureId();

    private native boolean nativeAllocate(android.filterfw.core.GLEnvironment gLEnvironment, int i, int i2);

    private native boolean nativeAllocateExternal(android.filterfw.core.GLEnvironment gLEnvironment);

    private native boolean nativeAllocateWithFbo(android.filterfw.core.GLEnvironment gLEnvironment, int i, int i2, int i3);

    private native boolean nativeAllocateWithTexture(android.filterfw.core.GLEnvironment gLEnvironment, int i, int i2, int i3);

    private native boolean nativeCopyFromGL(android.filterfw.core.GLFrame gLFrame);

    private native boolean nativeCopyFromNative(android.filterfw.core.NativeFrame nativeFrame);

    private native boolean nativeDeallocate();

    private native boolean nativeDetachTexFromFbo();

    private native boolean nativeFocus();

    private native boolean nativeReattachTexToFbo();

    private native boolean nativeResetParams();

    private native boolean setNativeBitmap(android.graphics.Bitmap bitmap, int i);

    private native boolean setNativeData(byte[] bArr, int i, int i2);

    private native boolean setNativeFloats(float[] fArr);

    private native boolean setNativeInts(int[] iArr);

    private native boolean setNativeTextureParam(int i, int i2);

    private native boolean setNativeViewport(int i, int i2, int i3, int i4);

    GLFrame(android.filterfw.core.FrameFormat frameFormat, android.filterfw.core.FrameManager frameManager) {
        super(frameFormat, frameManager);
        this.glFrameId = -1;
        this.mOwnsTexture = true;
    }

    GLFrame(android.filterfw.core.FrameFormat frameFormat, android.filterfw.core.FrameManager frameManager, int i, long j) {
        super(frameFormat, frameManager, i, j);
        this.glFrameId = -1;
        this.mOwnsTexture = true;
    }

    void init(android.filterfw.core.GLEnvironment gLEnvironment) {
        android.filterfw.core.FrameFormat format = getFormat();
        this.mGLEnvironment = gLEnvironment;
        if (format.getBytesPerSample() != 4) {
            throw new java.lang.IllegalArgumentException("GL frames must have 4 bytes per sample!");
        }
        if (format.getDimensionCount() != 2) {
            throw new java.lang.IllegalArgumentException("GL frames must be 2-dimensional!");
        }
        if (getFormat().getSize() < 0) {
            throw new java.lang.IllegalArgumentException("Initializing GL frame with zero size!");
        }
        int bindingType = getBindingType();
        boolean z = false;
        if (bindingType == 0) {
            initNew(false);
        } else if (bindingType == 104) {
            initNew(true);
            setReusable(z);
        } else if (bindingType == 100) {
            initWithTexture((int) getBindingId());
        } else if (bindingType == 101) {
            initWithFbo((int) getBindingId());
        } else if (bindingType == 102) {
            initWithTexture((int) getBindingId());
        } else if (bindingType == 103) {
            initWithFbo((int) getBindingId());
        } else {
            throw new java.lang.RuntimeException("Attempting to create GL frame with unknown binding type " + bindingType + "!");
        }
        z = true;
        setReusable(z);
    }

    private void initNew(boolean z) {
        if (z) {
            if (!nativeAllocateExternal(this.mGLEnvironment)) {
                throw new java.lang.RuntimeException("Could not allocate external GL frame!");
            }
        } else if (!nativeAllocate(this.mGLEnvironment, getFormat().getWidth(), getFormat().getHeight())) {
            throw new java.lang.RuntimeException("Could not allocate GL frame!");
        }
    }

    private void initWithTexture(int i) {
        if (!nativeAllocateWithTexture(this.mGLEnvironment, i, getFormat().getWidth(), getFormat().getHeight())) {
            throw new java.lang.RuntimeException("Could not allocate texture backed GL frame!");
        }
        this.mOwnsTexture = false;
        markReadOnly();
    }

    private void initWithFbo(int i) {
        if (!nativeAllocateWithFbo(this.mGLEnvironment, i, getFormat().getWidth(), getFormat().getHeight())) {
            throw new java.lang.RuntimeException("Could not allocate FBO backed GL frame!");
        }
    }

    void flushGPU(java.lang.String str) {
        android.filterfw.core.StopWatchMap stopWatchMap = android.filterfw.core.GLFrameTimer.get();
        if (stopWatchMap.LOG_MFF_RUNNING_TIMES) {
            stopWatchMap.start("glFinish " + str);
            android.opengl.GLES20.glFinish();
            stopWatchMap.stop("glFinish " + str);
        }
    }

    @Override // android.filterfw.core.Frame
    protected synchronized boolean hasNativeAllocation() {
        return this.glFrameId != -1;
    }

    @Override // android.filterfw.core.Frame
    protected synchronized void releaseNativeAllocation() {
        nativeDeallocate();
        this.glFrameId = -1;
    }

    public android.filterfw.core.GLEnvironment getGLEnvironment() {
        return this.mGLEnvironment;
    }

    @Override // android.filterfw.core.Frame
    public java.lang.Object getObjectValue() {
        assertGLEnvValid();
        return java.nio.ByteBuffer.wrap(getNativeData());
    }

    @Override // android.filterfw.core.Frame
    public void setInts(int[] iArr) {
        assertFrameMutable();
        assertGLEnvValid();
        if (!setNativeInts(iArr)) {
            throw new java.lang.RuntimeException("Could not set int values for GL frame!");
        }
    }

    @Override // android.filterfw.core.Frame
    public int[] getInts() {
        assertGLEnvValid();
        flushGPU("getInts");
        return getNativeInts();
    }

    @Override // android.filterfw.core.Frame
    public void setFloats(float[] fArr) {
        assertFrameMutable();
        assertGLEnvValid();
        if (!setNativeFloats(fArr)) {
            throw new java.lang.RuntimeException("Could not set int values for GL frame!");
        }
    }

    @Override // android.filterfw.core.Frame
    public float[] getFloats() {
        assertGLEnvValid();
        flushGPU("getFloats");
        return getNativeFloats();
    }

    @Override // android.filterfw.core.Frame
    public void setData(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        assertFrameMutable();
        assertGLEnvValid();
        byte[] array = byteBuffer.array();
        if (getFormat().getSize() != array.length) {
            throw new java.lang.RuntimeException("Data size in setData does not match GL frame size!");
        }
        if (!setNativeData(array, i, i2)) {
            throw new java.lang.RuntimeException("Could not set GL frame data!");
        }
    }

    @Override // android.filterfw.core.Frame
    public java.nio.ByteBuffer getData() {
        assertGLEnvValid();
        flushGPU("getData");
        return java.nio.ByteBuffer.wrap(getNativeData());
    }

    @Override // android.filterfw.core.Frame
    public void setBitmap(android.graphics.Bitmap bitmap) {
        assertFrameMutable();
        assertGLEnvValid();
        if (getFormat().getWidth() != bitmap.getWidth() || getFormat().getHeight() != bitmap.getHeight()) {
            throw new java.lang.RuntimeException("Bitmap dimensions do not match GL frame dimensions!");
        }
        android.graphics.Bitmap convertBitmapToRGBA = convertBitmapToRGBA(bitmap);
        if (!setNativeBitmap(convertBitmapToRGBA, convertBitmapToRGBA.getByteCount())) {
            throw new java.lang.RuntimeException("Could not set GL frame bitmap data!");
        }
    }

    @Override // android.filterfw.core.Frame
    public android.graphics.Bitmap getBitmap() {
        assertGLEnvValid();
        flushGPU("getBitmap");
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(getFormat().getWidth(), getFormat().getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        if (!getNativeBitmap(createBitmap)) {
            throw new java.lang.RuntimeException("Could not get bitmap data from GL frame!");
        }
        return createBitmap;
    }

    @Override // android.filterfw.core.Frame
    public void setDataFromFrame(android.filterfw.core.Frame frame) {
        assertGLEnvValid();
        if (getFormat().getSize() < frame.getFormat().getSize()) {
            throw new java.lang.RuntimeException("Attempting to assign frame of size " + frame.getFormat().getSize() + " to smaller GL frame of size " + getFormat().getSize() + "!");
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

    public void setViewport(int i, int i2, int i3, int i4) {
        assertFrameMutable();
        setNativeViewport(i, i2, i3, i4);
    }

    public void setViewport(android.graphics.Rect rect) {
        assertFrameMutable();
        setNativeViewport(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
    }

    public void generateMipMap() {
        assertFrameMutable();
        assertGLEnvValid();
        if (!generateNativeMipMap()) {
            throw new java.lang.RuntimeException("Could not generate mip-map for GL frame!");
        }
    }

    public void setTextureParameter(int i, int i2) {
        assertFrameMutable();
        assertGLEnvValid();
        if (!setNativeTextureParam(i, i2)) {
            throw new java.lang.RuntimeException("Could not set texture value " + i + " = " + i2 + " for GLFrame!");
        }
    }

    public int getTextureId() {
        return getNativeTextureId();
    }

    public int getFboId() {
        return getNativeFboId();
    }

    public void focus() {
        if (!nativeFocus()) {
            throw new java.lang.RuntimeException("Could not focus on GLFrame for drawing!");
        }
    }

    public java.lang.String toString() {
        return "GLFrame id: " + this.glFrameId + " (" + getFormat() + ") with texture ID " + getTextureId() + ", FBO ID " + getFboId();
    }

    @Override // android.filterfw.core.Frame
    protected void reset(android.filterfw.core.FrameFormat frameFormat) {
        if (!nativeResetParams()) {
            throw new java.lang.RuntimeException("Could not reset GLFrame texture parameters!");
        }
        super.reset(frameFormat);
    }

    @Override // android.filterfw.core.Frame
    protected void onFrameStore() {
        if (!this.mOwnsTexture) {
            nativeDetachTexFromFbo();
        }
    }

    @Override // android.filterfw.core.Frame
    protected void onFrameFetch() {
        if (!this.mOwnsTexture) {
            nativeReattachTexToFbo();
        }
    }

    private void assertGLEnvValid() {
        if (!this.mGLEnvironment.isContextActive()) {
            if (android.filterfw.core.GLEnvironment.isAnyContextActive()) {
                throw new java.lang.RuntimeException("Attempting to access " + this + " with foreign GL context active!");
            }
            throw new java.lang.RuntimeException("Attempting to access " + this + " with no GL context  active!");
        }
    }

    static {
        java.lang.System.loadLibrary("filterfw");
    }
}
