package com.android.internal.view;

/* loaded from: classes5.dex */
public abstract class BaseSurfaceHolder implements android.view.SurfaceHolder {
    static final boolean DEBUG = false;
    private static final java.lang.String TAG = "BaseSurfaceHolder";
    android.view.SurfaceHolder.Callback[] mGottenCallbacks;
    boolean mHaveGottenCallbacks;
    android.graphics.Rect mTmpDirty;
    public final java.util.ArrayList<android.view.SurfaceHolder.Callback> mCallbacks = new java.util.ArrayList<>();
    public final java.util.concurrent.locks.ReentrantLock mSurfaceLock = new java.util.concurrent.locks.ReentrantLock();
    public android.view.Surface mSurface = new android.view.Surface();
    int mRequestedWidth = -1;
    int mRequestedHeight = -1;
    protected int mRequestedFormat = -1;
    int mRequestedType = -1;
    long mLastLockTime = 0;
    int mType = -1;
    final android.graphics.Rect mSurfaceFrame = new android.graphics.Rect();

    public abstract boolean onAllowLockCanvas();

    public abstract void onRelayoutContainer();

    public abstract void onUpdateSurface();

    public int getRequestedWidth() {
        return this.mRequestedWidth;
    }

    public int getRequestedHeight() {
        return this.mRequestedHeight;
    }

    public int getRequestedFormat() {
        return this.mRequestedFormat;
    }

    public int getRequestedType() {
        return this.mRequestedType;
    }

    @Override // android.view.SurfaceHolder
    public void addCallback(android.view.SurfaceHolder.Callback callback) {
        synchronized (this.mCallbacks) {
            if (!this.mCallbacks.contains(callback)) {
                this.mCallbacks.add(callback);
            }
        }
    }

    @Override // android.view.SurfaceHolder
    public void removeCallback(android.view.SurfaceHolder.Callback callback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.remove(callback);
        }
    }

    public android.view.SurfaceHolder.Callback[] getCallbacks() {
        if (this.mHaveGottenCallbacks) {
            return this.mGottenCallbacks;
        }
        synchronized (this.mCallbacks) {
            int size = this.mCallbacks.size();
            if (size > 0) {
                if (this.mGottenCallbacks == null || this.mGottenCallbacks.length != size) {
                    this.mGottenCallbacks = new android.view.SurfaceHolder.Callback[size];
                }
                this.mCallbacks.toArray(this.mGottenCallbacks);
            } else {
                this.mGottenCallbacks = null;
            }
            this.mHaveGottenCallbacks = true;
        }
        return this.mGottenCallbacks;
    }

    public void ungetCallbacks() {
        this.mHaveGottenCallbacks = false;
    }

    @Override // android.view.SurfaceHolder
    public void setFixedSize(int i, int i2) {
        if (this.mRequestedWidth != i || this.mRequestedHeight != i2) {
            this.mRequestedWidth = i;
            this.mRequestedHeight = i2;
            onRelayoutContainer();
        }
    }

    @Override // android.view.SurfaceHolder
    public void setSizeFromLayout() {
        if (this.mRequestedWidth != -1 || this.mRequestedHeight != -1) {
            this.mRequestedHeight = -1;
            this.mRequestedWidth = -1;
            onRelayoutContainer();
        }
    }

    @Override // android.view.SurfaceHolder
    public void setFormat(int i) {
        if (this.mRequestedFormat != i) {
            this.mRequestedFormat = i;
            onUpdateSurface();
        }
    }

    @Override // android.view.SurfaceHolder
    public void setType(int i) {
        switch (i) {
            case 1:
            case 2:
                i = 0;
                break;
        }
        switch (i) {
            case 0:
            case 3:
                if (this.mRequestedType != i) {
                    this.mRequestedType = i;
                    onUpdateSurface();
                    break;
                }
                break;
        }
    }

    @Override // android.view.SurfaceHolder
    public android.graphics.Canvas lockCanvas() {
        return internalLockCanvas(null, false);
    }

    @Override // android.view.SurfaceHolder
    public android.graphics.Canvas lockCanvas(android.graphics.Rect rect) {
        return internalLockCanvas(rect, false);
    }

    @Override // android.view.SurfaceHolder
    public android.graphics.Canvas lockHardwareCanvas() {
        return internalLockCanvas(null, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final android.graphics.Canvas internalLockCanvas(android.graphics.Rect rect, boolean z) {
        android.graphics.Canvas canvas;
        if (this.mType == 3) {
            throw new android.view.SurfaceHolder.BadSurfaceTypeException("Surface type is SURFACE_TYPE_PUSH_BUFFERS");
        }
        this.mSurfaceLock.lock();
        if (onAllowLockCanvas()) {
            if (rect == null) {
                if (this.mTmpDirty == null) {
                    this.mTmpDirty = new android.graphics.Rect();
                }
                this.mTmpDirty.set(this.mSurfaceFrame);
                rect = this.mTmpDirty;
            }
            try {
                if (z) {
                    canvas = this.mSurface.lockHardwareCanvas();
                } else {
                    canvas = this.mSurface.lockCanvas(rect);
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Exception locking surface", e);
            }
            if (canvas == null) {
                this.mLastLockTime = android.os.SystemClock.uptimeMillis();
                return canvas;
            }
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            long j = this.mLastLockTime + 100;
            if (j > uptimeMillis) {
                try {
                    java.lang.Thread.sleep(j - uptimeMillis);
                } catch (java.lang.InterruptedException e2) {
                }
                uptimeMillis = android.os.SystemClock.uptimeMillis();
            }
            this.mLastLockTime = uptimeMillis;
            this.mSurfaceLock.unlock();
            return null;
        }
        canvas = null;
        if (canvas == null) {
        }
    }

    @Override // android.view.SurfaceHolder
    public void unlockCanvasAndPost(android.graphics.Canvas canvas) {
        this.mSurface.unlockCanvasAndPost(canvas);
        this.mSurfaceLock.unlock();
    }

    @Override // android.view.SurfaceHolder
    public android.view.Surface getSurface() {
        return this.mSurface;
    }

    @Override // android.view.SurfaceHolder
    public android.graphics.Rect getSurfaceFrame() {
        return this.mSurfaceFrame;
    }

    public void setSurfaceFrameSize(int i, int i2) {
        this.mSurfaceFrame.top = 0;
        this.mSurfaceFrame.left = 0;
        this.mSurfaceFrame.right = i;
        this.mSurfaceFrame.bottom = i2;
    }
}
