package android.view;

/* loaded from: classes4.dex */
public class Surface implements android.os.Parcelable {
    public static final int CHANGE_FRAME_RATE_ALWAYS = 1;
    public static final int CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS = 0;
    public static final android.os.Parcelable.Creator<android.view.Surface> CREATOR = new android.os.Parcelable.Creator<android.view.Surface>() { // from class: android.view.Surface.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.Surface createFromParcel(android.os.Parcel parcel) {
            try {
                android.view.Surface surface = new android.view.Surface();
                surface.readFromParcel(parcel);
                return surface;
            } catch (java.lang.Exception e) {
                android.util.Log.e(android.view.Surface.TAG, "Exception creating surface from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.Surface[] newArray(int i) {
            return new android.view.Surface[i];
        }
    };
    public static final int FRAME_RATE_CATEGORY_DEFAULT = 0;
    public static final int FRAME_RATE_CATEGORY_HIGH = 5;
    public static final int FRAME_RATE_CATEGORY_HIGH_HINT = 4;
    public static final int FRAME_RATE_CATEGORY_LOW = 2;
    public static final int FRAME_RATE_CATEGORY_NORMAL = 3;
    public static final int FRAME_RATE_CATEGORY_NO_PREFERENCE = 1;
    public static final int FRAME_RATE_COMPATIBILITY_DEFAULT = 0;
    public static final int FRAME_RATE_COMPATIBILITY_EXACT = 100;
    public static final int FRAME_RATE_COMPATIBILITY_FIXED_SOURCE = 1;
    public static final int FRAME_RATE_COMPATIBILITY_GTE = 103;
    public static final int FRAME_RATE_COMPATIBILITY_MIN = 102;
    public static final int FRAME_RATE_COMPATIBILITY_NO_VOTE = 101;
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 2;
    public static final int ROTATION_270 = 3;
    public static final int ROTATION_90 = 1;
    public static final int SCALING_MODE_FREEZE = 0;
    public static final int SCALING_MODE_NO_SCALE_CROP = 3;
    public static final int SCALING_MODE_SCALE_CROP = 2;
    public static final int SCALING_MODE_SCALE_TO_WINDOW = 1;
    private static final long SURFACE_NATIVE_ALLOCATION_SIZE_BYTES = 5000000;
    private static final java.lang.String TAG = "Surface";
    private android.graphics.Matrix mCompatibleMatrix;
    private int mGenerationId;
    private android.view.Surface.HwuiContext mHwuiContext;
    private boolean mIsAutoRefreshEnabled;
    private boolean mIsSharedBufferModeEnabled;
    private boolean mIsSingleBuffered;
    private long mLockedObject;
    private java.lang.String mName;
    long mNativeObject;
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    final java.lang.Object mLock = new java.lang.Object();
    private final android.graphics.Canvas mCanvas = new android.view.Surface.CompatibleCanvas();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ChangeFrameRateStrategy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FrameRateCategory {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FrameRateCompatibility {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Rotation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScalingMode {
    }

    private static native void nativeAllocateBuffers(long j);

    private static native int nativeAttachAndQueueBufferWithColorSpace(long j, android.hardware.HardwareBuffer hardwareBuffer, int i);

    private static native long nativeCreateFromSurfaceControl(long j);

    private static native long nativeCreateFromSurfaceTexture(android.graphics.SurfaceTexture surfaceTexture) throws android.view.Surface.OutOfResourcesException;

    private static native void nativeDestroy(long j);

    private static native int nativeForceScopedDisconnect(long j);

    private static native long nativeGetFromBlastBufferQueue(long j, long j2);

    private static native long nativeGetFromSurfaceControl(long j, long j2);

    private static native int nativeGetHeight(long j);

    private static native long nativeGetNextFrameNumber(long j);

    private static native int nativeGetWidth(long j);

    private static native boolean nativeIsConsumerRunningBehind(long j);

    private static native boolean nativeIsValid(long j);

    private static native long nativeLockCanvas(long j, android.graphics.Canvas canvas, android.graphics.Rect rect) throws android.view.Surface.OutOfResourcesException;

    private static native long nativeReadFromParcel(long j, android.os.Parcel parcel);

    private static native void nativeRelease(long j);

    private static native int nativeSetAutoRefreshEnabled(long j, boolean z);

    private static native int nativeSetFrameRate(long j, float f, int i, int i2);

    private static native int nativeSetScalingMode(long j, int i);

    private static native int nativeSetSharedBufferModeEnabled(long j, boolean z);

    private static native void nativeUnlockCanvasAndPost(long j, android.graphics.Canvas canvas);

    private static native void nativeWriteToParcel(long j, android.os.Parcel parcel);

    public Surface() {
        registerNativeMemoryUsage();
    }

    public Surface(android.view.SurfaceControl surfaceControl) {
        copyFrom(surfaceControl);
        registerNativeMemoryUsage();
    }

    public Surface(android.graphics.SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            throw new java.lang.IllegalArgumentException("surfaceTexture must not be null");
        }
        this.mIsSingleBuffered = surfaceTexture.isSingleBuffered();
        synchronized (this.mLock) {
            this.mName = surfaceTexture.toString();
            setNativeObjectLocked(nativeCreateFromSurfaceTexture(surfaceTexture));
        }
        registerNativeMemoryUsage();
    }

    private Surface(long j) {
        synchronized (this.mLock) {
            setNativeObjectLocked(j);
        }
        registerNativeMemoryUsage();
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            release();
        } finally {
            super.finalize();
            freeNativeMemoryUsage();
        }
    }

    public void release() {
        synchronized (this.mLock) {
            if (this.mHwuiContext != null) {
                this.mHwuiContext.destroy();
                this.mHwuiContext = null;
            }
            if (this.mNativeObject != 0) {
                nativeRelease(this.mNativeObject);
                setNativeObjectLocked(0L);
            }
        }
    }

    public void destroy() {
        synchronized (this.mLock) {
            if (this.mNativeObject != 0) {
                nativeDestroy(this.mNativeObject);
            }
            release();
        }
    }

    public void hwuiDestroy() {
        if (this.mHwuiContext != null) {
            this.mHwuiContext.destroy();
            this.mHwuiContext = null;
        }
    }

    public boolean isValid() {
        synchronized (this.mLock) {
            if (this.mNativeObject == 0) {
                return false;
            }
            return nativeIsValid(this.mNativeObject);
        }
    }

    public int getGenerationId() {
        int i;
        synchronized (this.mLock) {
            i = this.mGenerationId;
        }
        return i;
    }

    public long getNextFrameNumber() {
        long nativeGetNextFrameNumber;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            nativeGetNextFrameNumber = nativeGetNextFrameNumber(this.mNativeObject);
        }
        return nativeGetNextFrameNumber;
    }

    public boolean isConsumerRunningBehind() {
        boolean nativeIsConsumerRunningBehind;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            nativeIsConsumerRunningBehind = nativeIsConsumerRunningBehind(this.mNativeObject);
        }
        return nativeIsConsumerRunningBehind;
    }

    public android.graphics.Point getDefaultSize() {
        android.graphics.Point point;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            point = new android.graphics.Point(nativeGetWidth(this.mNativeObject), nativeGetHeight(this.mNativeObject));
        }
        return point;
    }

    public android.graphics.Canvas lockCanvas(android.graphics.Rect rect) throws android.view.Surface.OutOfResourcesException, java.lang.IllegalArgumentException {
        android.graphics.Canvas canvas;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (this.mLockedObject != 0) {
                throw new java.lang.IllegalArgumentException("Surface was already locked");
            }
            this.mLockedObject = nativeLockCanvas(this.mNativeObject, this.mCanvas, rect);
            canvas = this.mCanvas;
        }
        return canvas;
    }

    public void unlockCanvasAndPost(android.graphics.Canvas canvas) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (this.mHwuiContext != null) {
                this.mHwuiContext.unlockAndPost(canvas);
            } else {
                unlockSwCanvasAndPost(canvas);
            }
        }
    }

    private void unlockSwCanvasAndPost(android.graphics.Canvas canvas) {
        if (canvas != this.mCanvas) {
            throw new java.lang.IllegalArgumentException("canvas object must be the same instance that was previously returned by lockCanvas");
        }
        if (this.mNativeObject != this.mLockedObject) {
            android.util.Log.w(TAG, "WARNING: Surface's mNativeObject (0x" + java.lang.Long.toHexString(this.mNativeObject) + ") != mLockedObject (0x" + java.lang.Long.toHexString(this.mLockedObject) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mLockedObject == 0) {
            throw new java.lang.IllegalStateException("Surface was not locked");
        }
        try {
            nativeUnlockCanvasAndPost(this.mLockedObject, canvas);
        } finally {
            nativeRelease(this.mLockedObject);
            this.mLockedObject = 0L;
        }
    }

    public android.graphics.Canvas lockHardwareCanvas() {
        android.graphics.Canvas lockCanvas;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (this.mHwuiContext == null) {
                this.mHwuiContext = new android.view.Surface.HwuiContext(false);
            }
            lockCanvas = this.mHwuiContext.lockCanvas(nativeGetWidth(this.mNativeObject), nativeGetHeight(this.mNativeObject));
        }
        return lockCanvas;
    }

    public android.graphics.Canvas lockHardwareWideColorGamutCanvas() {
        android.graphics.Canvas lockCanvas;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (this.mHwuiContext != null && !this.mHwuiContext.isWideColorGamut()) {
                this.mHwuiContext.destroy();
                this.mHwuiContext = null;
            }
            if (this.mHwuiContext == null) {
                this.mHwuiContext = new android.view.Surface.HwuiContext(true);
            }
            lockCanvas = this.mHwuiContext.lockCanvas(nativeGetWidth(this.mNativeObject), nativeGetHeight(this.mNativeObject));
        }
        return lockCanvas;
    }

    @java.lang.Deprecated
    public void unlockCanvas(android.graphics.Canvas canvas) {
        throw new java.lang.UnsupportedOperationException();
    }

    void setCompatibilityTranslator(android.content.res.CompatibilityInfo.Translator translator) {
        if (translator != null) {
            float f = translator.applicationScale;
            this.mCompatibleMatrix = new android.graphics.Matrix();
            this.mCompatibleMatrix.setScale(f, f);
        }
    }

    private void updateNativeObject(long j) {
        synchronized (this.mLock) {
            if (j == this.mNativeObject) {
                return;
            }
            if (this.mNativeObject != 0) {
                nativeRelease(this.mNativeObject);
            }
            setNativeObjectLocked(j);
        }
    }

    public void copyFrom(android.view.SurfaceControl surfaceControl) {
        if (surfaceControl == null) {
            throw new java.lang.IllegalArgumentException("other must not be null");
        }
        long j = surfaceControl.mNativeObject;
        if (j == 0) {
            throw new java.lang.NullPointerException("null SurfaceControl native object. Are you using a released SurfaceControl?");
        }
        updateNativeObject(nativeGetFromSurfaceControl(this.mNativeObject, j));
    }

    public void copyFrom(android.graphics.BLASTBufferQueue bLASTBufferQueue) {
        if (bLASTBufferQueue == null) {
            throw new java.lang.IllegalArgumentException("queue must not be null");
        }
        long j = bLASTBufferQueue.mNativeObject;
        if (j == 0) {
            throw new java.lang.NullPointerException("Null BLASTBufferQueue native object");
        }
        updateNativeObject(nativeGetFromBlastBufferQueue(this.mNativeObject, j));
    }

    public void createFrom(android.view.SurfaceControl surfaceControl) {
        if (surfaceControl == null) {
            throw new java.lang.IllegalArgumentException("other must not be null");
        }
        long j = surfaceControl.mNativeObject;
        if (j == 0) {
            throw new java.lang.NullPointerException("null SurfaceControl native object. Are you using a released SurfaceControl?");
        }
        long nativeCreateFromSurfaceControl = nativeCreateFromSurfaceControl(j);
        synchronized (this.mLock) {
            if (this.mNativeObject != 0) {
                nativeRelease(this.mNativeObject);
            }
            setNativeObjectLocked(nativeCreateFromSurfaceControl);
        }
    }

    @java.lang.Deprecated
    public void transferFrom(android.view.Surface surface) {
        long j;
        if (surface == null) {
            throw new java.lang.IllegalArgumentException("other must not be null");
        }
        if (surface != this) {
            synchronized (surface.mLock) {
                j = surface.mNativeObject;
                surface.setNativeObjectLocked(0L);
            }
            synchronized (this.mLock) {
                if (this.mNativeObject != 0) {
                    nativeRelease(this.mNativeObject);
                }
                setNativeObjectLocked(j);
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(android.os.Parcel parcel) {
        if (parcel == null) {
            throw new java.lang.IllegalArgumentException("source must not be null");
        }
        synchronized (this.mLock) {
            this.mName = parcel.readString();
            this.mIsSingleBuffered = parcel.readInt() != 0;
            setNativeObjectLocked(nativeReadFromParcel(this.mNativeObject, parcel));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (parcel == null) {
            throw new java.lang.IllegalArgumentException("dest must not be null");
        }
        synchronized (this.mLock) {
            parcel.writeString(this.mName);
            parcel.writeInt(this.mIsSingleBuffered ? 1 : 0);
            nativeWriteToParcel(this.mNativeObject, parcel);
        }
        if ((i & 1) != 0) {
            release();
        }
    }

    public java.lang.String toString() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = "Surface(name=" + this.mName + ")/@0x" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this));
        }
        return str;
    }

    private void setNativeObjectLocked(long j) {
        if (this.mNativeObject != j) {
            if (this.mNativeObject == 0 && j != 0) {
                this.mCloseGuard.open("Surface.release");
            } else if (this.mNativeObject != 0 && j == 0) {
                this.mCloseGuard.close();
            }
            this.mNativeObject = j;
            this.mGenerationId++;
            if (this.mHwuiContext != null) {
                this.mHwuiContext.updateSurface();
            }
        }
    }

    private void checkNotReleasedLocked() {
        if (this.mNativeObject == 0) {
            throw new java.lang.IllegalStateException("Surface has already been released.");
        }
    }

    public void allocateBuffers() {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            nativeAllocateBuffers(this.mNativeObject);
        }
    }

    public void setScalingMode(int i) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (nativeSetScalingMode(this.mNativeObject, i) != 0) {
                throw new java.lang.IllegalArgumentException("Invalid scaling mode: " + i);
            }
        }
    }

    void forceScopedDisconnect() {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (nativeForceScopedDisconnect(this.mNativeObject) != 0) {
                throw new java.lang.RuntimeException("Failed to disconnect Surface instance (bad object?)");
            }
        }
    }

    public void attachAndQueueBufferWithColorSpace(android.hardware.HardwareBuffer hardwareBuffer, android.graphics.ColorSpace colorSpace) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (colorSpace == null) {
                colorSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB);
            }
            int nativeAttachAndQueueBufferWithColorSpace = nativeAttachAndQueueBufferWithColorSpace(this.mNativeObject, hardwareBuffer, colorSpace.getId());
            if (nativeAttachAndQueueBufferWithColorSpace != 0) {
                throw new java.lang.RuntimeException("Failed to attach and queue buffer to Surface (bad object?), native error: " + nativeAttachAndQueueBufferWithColorSpace);
            }
        }
    }

    public boolean isSingleBuffered() {
        return this.mIsSingleBuffered;
    }

    public void setSharedBufferModeEnabled(boolean z) {
        if (this.mIsSharedBufferModeEnabled != z) {
            if (nativeSetSharedBufferModeEnabled(this.mNativeObject, z) != 0) {
                throw new java.lang.RuntimeException("Failed to set shared buffer mode on Surface (bad object?)");
            }
            this.mIsSharedBufferModeEnabled = z;
        }
    }

    public boolean isSharedBufferModeEnabled() {
        return this.mIsSharedBufferModeEnabled;
    }

    public void setAutoRefreshEnabled(boolean z) {
        if (this.mIsAutoRefreshEnabled != z) {
            if (nativeSetAutoRefreshEnabled(this.mNativeObject, z) != 0) {
                throw new java.lang.RuntimeException("Failed to set auto refresh on Surface (bad object?)");
            }
            this.mIsAutoRefreshEnabled = z;
        }
    }

    public boolean isAutoRefreshEnabled() {
        return this.mIsAutoRefreshEnabled;
    }

    public void setFrameRate(float f, int i, int i2) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            int nativeSetFrameRate = nativeSetFrameRate(this.mNativeObject, f, i, i2);
            if (nativeSetFrameRate == (-android.system.OsConstants.EINVAL)) {
                throw new java.lang.IllegalArgumentException("Invalid argument to Surface.setFrameRate()");
            }
            if (nativeSetFrameRate != 0) {
                android.util.Log.e(TAG, "Failed to set frame rate on Surface. Native error: " + nativeSetFrameRate);
            }
        }
    }

    public void clearFrameRate() {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            int nativeSetFrameRate = nativeSetFrameRate(this.mNativeObject, 0.0f, 0, 0);
            if (nativeSetFrameRate != 0) {
                throw new java.lang.RuntimeException("Failed to clear the frame rate on Surface. Native error: " + nativeSetFrameRate);
            }
        }
    }

    public void setFrameRate(float f, int i) {
        setFrameRate(f, i, 0);
    }

    public static class OutOfResourcesException extends java.lang.RuntimeException {
        public OutOfResourcesException() {
        }

        public OutOfResourcesException(java.lang.String str) {
            super(str);
        }
    }

    public static java.lang.String rotationToString(int i) {
        switch (i) {
            case 0:
                return "ROTATION_0";
            case 1:
                return "ROTATION_90";
            case 2:
                return "ROTATION_180";
            case 3:
                return "ROTATION_270";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private final class CompatibleCanvas extends android.graphics.Canvas {
        private android.graphics.Matrix mOrigMatrix;

        private CompatibleCanvas() {
            this.mOrigMatrix = null;
        }

        @Override // android.graphics.Canvas
        public void setMatrix(android.graphics.Matrix matrix) {
            if (android.view.Surface.this.mCompatibleMatrix == null || this.mOrigMatrix == null || this.mOrigMatrix.equals(matrix)) {
                super.setMatrix(matrix);
                return;
            }
            android.graphics.Matrix matrix2 = new android.graphics.Matrix(android.view.Surface.this.mCompatibleMatrix);
            matrix2.preConcat(matrix);
            super.setMatrix(matrix2);
        }

        @Override // android.graphics.Canvas
        public void getMatrix(android.graphics.Matrix matrix) {
            super.getMatrix(matrix);
            if (this.mOrigMatrix == null) {
                this.mOrigMatrix = new android.graphics.Matrix();
            }
            this.mOrigMatrix.set(matrix);
        }
    }

    private final class HwuiContext {
        private android.graphics.RecordingCanvas mCanvas;
        private android.graphics.HardwareRenderer mHardwareRenderer;
        private final boolean mIsWideColorGamut;
        private final android.graphics.RenderNode mRenderNode = android.graphics.RenderNode.create("HwuiCanvas", null);

        HwuiContext(boolean z) {
            this.mRenderNode.setClipToBounds(false);
            this.mRenderNode.setForceDarkAllowed(false);
            this.mIsWideColorGamut = z;
            this.mHardwareRenderer = new android.graphics.HardwareRenderer();
            this.mHardwareRenderer.setContentRoot(this.mRenderNode);
            this.mHardwareRenderer.setSurface(android.view.Surface.this, true);
            this.mHardwareRenderer.setColorMode(z ? 1 : 0);
            this.mHardwareRenderer.setLightSourceAlpha(0.0f, 0.0f);
            this.mHardwareRenderer.setLightSourceGeometry(0.0f, 0.0f, 0.0f, 0.0f);
        }

        android.graphics.Canvas lockCanvas(int i, int i2) {
            if (this.mCanvas != null) {
                throw new java.lang.IllegalStateException("Surface was already locked!");
            }
            this.mCanvas = this.mRenderNode.beginRecording(i, i2);
            return this.mCanvas;
        }

        void unlockAndPost(android.graphics.Canvas canvas) {
            if (canvas != this.mCanvas) {
                throw new java.lang.IllegalArgumentException("canvas object must be the same instance that was previously returned by lockCanvas");
            }
            this.mRenderNode.endRecording();
            this.mCanvas = null;
            this.mHardwareRenderer.createRenderRequest().setVsyncTime(java.lang.System.nanoTime()).syncAndDraw();
        }

        void updateSurface() {
            this.mHardwareRenderer.setSurface(android.view.Surface.this, true);
        }

        void destroy() {
            this.mHardwareRenderer.destroy();
        }

        boolean isWideColorGamut() {
            return this.mIsWideColorGamut;
        }
    }

    private static void registerNativeMemoryUsage() {
        if (android.view.flags.Flags.enableSurfaceNativeAllocRegistrationRo()) {
            dalvik.system.VMRuntime.getRuntime().registerNativeAllocation(SURFACE_NATIVE_ALLOCATION_SIZE_BYTES);
        }
    }

    private static void freeNativeMemoryUsage() {
        if (android.view.flags.Flags.enableSurfaceNativeAllocRegistrationRo()) {
            dalvik.system.VMRuntime.getRuntime().registerNativeFree(SURFACE_NATIVE_ALLOCATION_SIZE_BYTES);
        }
    }
}
