package android.filterfw.core;

/* loaded from: classes.dex */
public class NativeBuffer {
    private android.filterfw.core.Frame mAttachedFrame;
    private long mDataPointer;
    private boolean mOwnsData;
    private int mRefCount;
    private int mSize;

    private native boolean allocate(int i);

    private native boolean deallocate(boolean z);

    private native boolean nativeCopyTo(android.filterfw.core.NativeBuffer nativeBuffer);

    public NativeBuffer() {
        this.mDataPointer = 0L;
        this.mSize = 0;
        this.mOwnsData = false;
        this.mRefCount = 1;
    }

    public NativeBuffer(int i) {
        this.mDataPointer = 0L;
        this.mSize = 0;
        this.mOwnsData = false;
        this.mRefCount = 1;
        allocate(i * getElementSize());
        this.mOwnsData = true;
    }

    public android.filterfw.core.NativeBuffer mutableCopy() {
        try {
            android.filterfw.core.NativeBuffer nativeBuffer = (android.filterfw.core.NativeBuffer) getClass().newInstance();
            if (this.mSize > 0 && !nativeCopyTo(nativeBuffer)) {
                throw new java.lang.RuntimeException("Failed to copy NativeBuffer to mutable instance!");
            }
            return nativeBuffer;
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("Unable to allocate a copy of " + getClass() + "! Make sure the class has a default constructor!");
        }
    }

    public int size() {
        return this.mSize;
    }

    public int count() {
        if (this.mDataPointer != 0) {
            return this.mSize / getElementSize();
        }
        return 0;
    }

    public int getElementSize() {
        return 1;
    }

    public android.filterfw.core.NativeBuffer retain() {
        if (this.mAttachedFrame != null) {
            this.mAttachedFrame.retain();
        } else if (this.mOwnsData) {
            this.mRefCount++;
        }
        return this;
    }

    public android.filterfw.core.NativeBuffer release() {
        boolean z = false;
        if (this.mAttachedFrame != null) {
            z = this.mAttachedFrame.release() == null;
        } else if (this.mOwnsData) {
            this.mRefCount--;
            z = this.mRefCount == 0;
        }
        if (z) {
            deallocate(this.mOwnsData);
            return null;
        }
        return this;
    }

    public boolean isReadOnly() {
        if (this.mAttachedFrame != null) {
            return this.mAttachedFrame.isReadOnly();
        }
        return false;
    }

    static {
        java.lang.System.loadLibrary("filterfw");
    }

    void attachToFrame(android.filterfw.core.Frame frame) {
        this.mAttachedFrame = frame;
    }

    protected void assertReadable() {
        if (this.mDataPointer == 0 || this.mSize == 0 || (this.mAttachedFrame != null && !this.mAttachedFrame.hasNativeAllocation())) {
            throw new java.lang.NullPointerException("Attempting to read from null data frame!");
        }
    }

    protected void assertWritable() {
        if (isReadOnly()) {
            throw new java.lang.RuntimeException("Attempting to modify read-only native (structured) data!");
        }
    }
}
