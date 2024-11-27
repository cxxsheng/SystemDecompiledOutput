package android.filterfw.core;

/* loaded from: classes.dex */
public abstract class Frame {
    public static final int NO_BINDING = 0;
    public static final long TIMESTAMP_NOT_SET = -2;
    public static final long TIMESTAMP_UNKNOWN = -1;
    private long mBindingId;
    private int mBindingType;
    private android.filterfw.core.FrameFormat mFormat;
    private android.filterfw.core.FrameManager mFrameManager;
    private boolean mReadOnly;
    private int mRefCount;
    private boolean mReusable;
    private long mTimestamp;

    public abstract android.graphics.Bitmap getBitmap();

    public abstract java.nio.ByteBuffer getData();

    public abstract float[] getFloats();

    public abstract int[] getInts();

    public abstract java.lang.Object getObjectValue();

    protected abstract boolean hasNativeAllocation();

    protected abstract void releaseNativeAllocation();

    public abstract void setBitmap(android.graphics.Bitmap bitmap);

    public abstract void setData(java.nio.ByteBuffer byteBuffer, int i, int i2);

    public abstract void setFloats(float[] fArr);

    public abstract void setInts(int[] iArr);

    Frame(android.filterfw.core.FrameFormat frameFormat, android.filterfw.core.FrameManager frameManager) {
        this.mReadOnly = false;
        this.mReusable = false;
        this.mRefCount = 1;
        this.mBindingType = 0;
        this.mBindingId = 0L;
        this.mTimestamp = -2L;
        this.mFormat = frameFormat.mutableCopy();
        this.mFrameManager = frameManager;
    }

    Frame(android.filterfw.core.FrameFormat frameFormat, android.filterfw.core.FrameManager frameManager, int i, long j) {
        this.mReadOnly = false;
        this.mReusable = false;
        this.mRefCount = 1;
        this.mBindingType = 0;
        this.mBindingId = 0L;
        this.mTimestamp = -2L;
        this.mFormat = frameFormat.mutableCopy();
        this.mFrameManager = frameManager;
        this.mBindingType = i;
        this.mBindingId = j;
    }

    public android.filterfw.core.FrameFormat getFormat() {
        return this.mFormat;
    }

    public int getCapacity() {
        return getFormat().getSize();
    }

    public boolean isReadOnly() {
        return this.mReadOnly;
    }

    public int getBindingType() {
        return this.mBindingType;
    }

    public long getBindingId() {
        return this.mBindingId;
    }

    public void setObjectValue(java.lang.Object obj) {
        assertFrameMutable();
        if (obj instanceof int[]) {
            setInts((int[]) obj);
            return;
        }
        if (obj instanceof float[]) {
            setFloats((float[]) obj);
            return;
        }
        if (obj instanceof java.nio.ByteBuffer) {
            setData((java.nio.ByteBuffer) obj);
        } else if (obj instanceof android.graphics.Bitmap) {
            setBitmap((android.graphics.Bitmap) obj);
        } else {
            setGenericObjectValue(obj);
        }
    }

    public void setData(java.nio.ByteBuffer byteBuffer) {
        setData(byteBuffer, 0, byteBuffer.limit());
    }

    public void setData(byte[] bArr, int i, int i2) {
        setData(java.nio.ByteBuffer.wrap(bArr, i, i2));
    }

    public void setTimestamp(long j) {
        this.mTimestamp = j;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public void setDataFromFrame(android.filterfw.core.Frame frame) {
        setData(frame.getData());
    }

    protected boolean requestResize(int[] iArr) {
        return false;
    }

    public int getRefCount() {
        return this.mRefCount;
    }

    public android.filterfw.core.Frame release() {
        if (this.mFrameManager != null) {
            return this.mFrameManager.releaseFrame(this);
        }
        return this;
    }

    public android.filterfw.core.Frame retain() {
        if (this.mFrameManager != null) {
            return this.mFrameManager.retainFrame(this);
        }
        return this;
    }

    public android.filterfw.core.FrameManager getFrameManager() {
        return this.mFrameManager;
    }

    protected void assertFrameMutable() {
        if (isReadOnly()) {
            throw new java.lang.RuntimeException("Attempting to modify read-only frame!");
        }
    }

    protected void setReusable(boolean z) {
        this.mReusable = z;
    }

    protected void setFormat(android.filterfw.core.FrameFormat frameFormat) {
        this.mFormat = frameFormat.mutableCopy();
    }

    protected void setGenericObjectValue(java.lang.Object obj) {
        throw new java.lang.RuntimeException("Cannot set object value of unsupported type: " + obj.getClass());
    }

    protected static android.graphics.Bitmap convertBitmapToRGBA(android.graphics.Bitmap bitmap) {
        if (bitmap.getConfig() == android.graphics.Bitmap.Config.ARGB_8888) {
            return bitmap;
        }
        android.graphics.Bitmap copy = bitmap.copy(android.graphics.Bitmap.Config.ARGB_8888, false);
        if (copy == null) {
            throw new java.lang.RuntimeException("Error converting bitmap to RGBA!");
        }
        if (copy.getRowBytes() != copy.getWidth() * 4) {
            throw new java.lang.RuntimeException("Unsupported row byte count in bitmap!");
        }
        return copy;
    }

    protected void reset(android.filterfw.core.FrameFormat frameFormat) {
        this.mFormat = frameFormat.mutableCopy();
        this.mReadOnly = false;
        this.mRefCount = 1;
    }

    protected void onFrameStore() {
    }

    protected void onFrameFetch() {
    }

    final int incRefCount() {
        this.mRefCount++;
        return this.mRefCount;
    }

    final int decRefCount() {
        this.mRefCount--;
        return this.mRefCount;
    }

    final boolean isReusable() {
        return this.mReusable;
    }

    final void markReadOnly() {
        this.mReadOnly = true;
    }
}
