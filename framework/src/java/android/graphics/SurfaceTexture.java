package android.graphics;

/* loaded from: classes.dex */
public class SurfaceTexture {
    private final android.os.Looper mCreatorLooper;
    private long mFrameAvailableListener;
    private boolean mIsSingleBuffered;
    private android.os.Handler mOnFrameAvailableHandler;
    private android.os.Handler mOnSetFrameRateHandler;
    private long mProducer;
    private long mSurfaceTexture;

    public interface OnFrameAvailableListener {
        void onFrameAvailable(android.graphics.SurfaceTexture surfaceTexture);
    }

    public interface OnSetFrameRateListener {
        void onSetFrameRate(android.graphics.SurfaceTexture surfaceTexture, float f, int i, int i2);
    }

    private native int nativeAttachToGLContext(int i);

    private native int nativeDetachFromGLContext();

    private native void nativeFinalize();

    private native int nativeGetDataSpace();

    private native long nativeGetTimestamp();

    private native void nativeGetTransformMatrix(float[] fArr);

    private native void nativeInit(boolean z, int i, boolean z2, java.lang.ref.WeakReference<android.graphics.SurfaceTexture> weakReference) throws android.view.Surface.OutOfResourcesException;

    private native boolean nativeIsReleased();

    private native void nativeRelease();

    private native void nativeReleaseTexImage();

    private native void nativeSetDefaultBufferSize(int i, int i2);

    private native void nativeUpdateTexImage();

    @java.lang.Deprecated
    public static class OutOfResourcesException extends java.lang.Exception {
        public OutOfResourcesException() {
        }

        public OutOfResourcesException(java.lang.String str) {
            super(str);
        }
    }

    public SurfaceTexture(int i) {
        this(i, false);
    }

    public SurfaceTexture(int i, boolean z) {
        this.mCreatorLooper = android.os.Looper.myLooper();
        this.mIsSingleBuffered = z;
        nativeInit(false, i, z, new java.lang.ref.WeakReference<>(this));
    }

    public SurfaceTexture(boolean z) {
        this.mCreatorLooper = android.os.Looper.myLooper();
        this.mIsSingleBuffered = z;
        nativeInit(true, 0, z, new java.lang.ref.WeakReference<>(this));
    }

    public void setOnFrameAvailableListener(android.graphics.SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener) {
        setOnFrameAvailableListener(onFrameAvailableListener, null);
    }

    public void setOnFrameAvailableListener(final android.graphics.SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener, android.os.Handler handler) {
        android.os.Looper mainLooper;
        if (onFrameAvailableListener != null) {
            if (handler != null) {
                mainLooper = handler.getLooper();
            } else {
                mainLooper = this.mCreatorLooper != null ? this.mCreatorLooper : android.os.Looper.getMainLooper();
            }
            this.mOnFrameAvailableHandler = new android.os.Handler(mainLooper, null, true) { // from class: android.graphics.SurfaceTexture.1
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    onFrameAvailableListener.onFrameAvailable(android.graphics.SurfaceTexture.this);
                }
            };
            return;
        }
        this.mOnFrameAvailableHandler = null;
    }

    private static class SetFrameRateArgs {
        final int mChangeFrameRateStrategy;
        final int mCompatibility;
        final float mFrameRate;

        SetFrameRateArgs(float f, int i, int i2) {
            this.mFrameRate = f;
            this.mCompatibility = i;
            this.mChangeFrameRateStrategy = i2;
        }
    }

    public void setOnSetFrameRateListener(final android.graphics.SurfaceTexture.OnSetFrameRateListener onSetFrameRateListener, android.os.Handler handler) {
        android.os.Looper mainLooper;
        if (onSetFrameRateListener != null) {
            if (handler != null) {
                mainLooper = handler.getLooper();
            } else {
                mainLooper = this.mCreatorLooper != null ? this.mCreatorLooper : android.os.Looper.getMainLooper();
            }
            this.mOnSetFrameRateHandler = new android.os.Handler(mainLooper, null, true) { // from class: android.graphics.SurfaceTexture.2
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    android.os.Trace.traceBegin(8L, "onSetFrameRateHandler");
                    try {
                        android.graphics.SurfaceTexture.SetFrameRateArgs setFrameRateArgs = (android.graphics.SurfaceTexture.SetFrameRateArgs) message.obj;
                        onSetFrameRateListener.onSetFrameRate(android.graphics.SurfaceTexture.this, setFrameRateArgs.mFrameRate, setFrameRateArgs.mCompatibility, setFrameRateArgs.mChangeFrameRateStrategy);
                    } finally {
                        android.os.Trace.traceEnd(8L);
                    }
                }
            };
            return;
        }
        this.mOnSetFrameRateHandler = null;
    }

    public void setDefaultBufferSize(int i, int i2) {
        nativeSetDefaultBufferSize(i, i2);
    }

    public void updateTexImage() {
        nativeUpdateTexImage();
    }

    public void releaseTexImage() {
        nativeReleaseTexImage();
    }

    public void detachFromGLContext() {
        if (nativeDetachFromGLContext() != 0) {
            throw new java.lang.RuntimeException("Error during detachFromGLContext (see logcat for details)");
        }
    }

    public void attachToGLContext(int i) {
        if (nativeAttachToGLContext(i) != 0) {
            throw new java.lang.RuntimeException("Error during attachToGLContext (see logcat for details)");
        }
    }

    public void getTransformMatrix(float[] fArr) {
        if (fArr.length != 16) {
            throw new java.lang.IllegalArgumentException();
        }
        nativeGetTransformMatrix(fArr);
    }

    public long getTimestamp() {
        return nativeGetTimestamp();
    }

    public int getDataSpace() {
        return nativeGetDataSpace();
    }

    public void release() {
        nativeRelease();
    }

    public boolean isReleased() {
        return nativeIsReleased();
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            nativeFinalize();
        } finally {
            super.finalize();
        }
    }

    private static void postEventFromNative(java.lang.ref.WeakReference<android.graphics.SurfaceTexture> weakReference) {
        android.os.Handler handler;
        android.graphics.SurfaceTexture surfaceTexture = weakReference.get();
        if (surfaceTexture != null && (handler = surfaceTexture.mOnFrameAvailableHandler) != null) {
            handler.sendEmptyMessage(0);
        }
    }

    private static void postOnSetFrameRateEventFromNative(java.lang.ref.WeakReference<android.graphics.SurfaceTexture> weakReference, float f, int i, int i2) {
        android.graphics.SurfaceTexture surfaceTexture;
        android.os.Handler handler;
        android.os.Trace.traceBegin(8L, "postOnSetFrameRateEventFromNative");
        try {
            if (android.view.flags.Flags.toolkitSetFrameRateReadOnly() && (surfaceTexture = weakReference.get()) != null && (handler = surfaceTexture.mOnSetFrameRateHandler) != null) {
                android.os.Message message = new android.os.Message();
                message.obj = new android.graphics.SurfaceTexture.SetFrameRateArgs(f, i, i2);
                handler.sendMessage(message);
            }
        } finally {
            android.os.Trace.traceEnd(8L);
        }
    }

    public boolean isSingleBuffered() {
        return this.mIsSingleBuffered;
    }
}
