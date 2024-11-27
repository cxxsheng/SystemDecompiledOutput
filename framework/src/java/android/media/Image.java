package android.media;

/* loaded from: classes2.dex */
public abstract class Image implements java.lang.AutoCloseable {
    private android.graphics.Rect mCropRect;
    protected boolean mIsImageValid = false;
    private int mDataSpace = 0;

    @Override // java.lang.AutoCloseable
    public abstract void close();

    public abstract int getFormat();

    public abstract int getHeight();

    public abstract android.media.Image.Plane[] getPlanes();

    public abstract int getScalingMode();

    public abstract long getTimestamp();

    public abstract int getTransform();

    public abstract int getWidth();

    protected Image() {
    }

    protected void throwISEIfImageIsInvalid() {
        if (!this.mIsImageValid) {
            throw new java.lang.IllegalStateException("Image is already closed");
        }
    }

    public android.hardware.SyncFence getFence() throws java.io.IOException {
        return android.hardware.SyncFence.createEmpty();
    }

    public int getPlaneCount() {
        return -1;
    }

    public android.hardware.HardwareBuffer getHardwareBuffer() {
        throwISEIfImageIsInvalid();
        return null;
    }

    public void setTimestamp(long j) {
        throwISEIfImageIsInvalid();
    }

    public void setFence(android.hardware.SyncFence syncFence) throws java.io.IOException {
        throwISEIfImageIsInvalid();
    }

    public int getDataSpace() {
        throwISEIfImageIsInvalid();
        return this.mDataSpace;
    }

    public void setDataSpace(int i) {
        throwISEIfImageIsInvalid();
        this.mDataSpace = i;
    }

    public android.graphics.Rect getCropRect() {
        throwISEIfImageIsInvalid();
        if (this.mCropRect == null) {
            return new android.graphics.Rect(0, 0, getWidth(), getHeight());
        }
        return new android.graphics.Rect(this.mCropRect);
    }

    public void setCropRect(android.graphics.Rect rect) {
        throwISEIfImageIsInvalid();
        if (rect != null) {
            android.graphics.Rect rect2 = new android.graphics.Rect(rect);
            if (!rect2.intersect(0, 0, getWidth(), getHeight())) {
                rect2.setEmpty();
            }
            rect = rect2;
        }
        this.mCropRect = rect;
    }

    public boolean isAttachable() {
        throwISEIfImageIsInvalid();
        return false;
    }

    java.lang.Object getOwner() {
        throwISEIfImageIsInvalid();
        return null;
    }

    long getNativeContext() {
        throwISEIfImageIsInvalid();
        return 0L;
    }

    public static abstract class Plane {
        public abstract java.nio.ByteBuffer getBuffer();

        public abstract int getPixelStride();

        public abstract int getRowStride();

        protected Plane() {
        }
    }
}
