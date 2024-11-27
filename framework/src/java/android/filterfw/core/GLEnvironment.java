package android.filterfw.core;

/* loaded from: classes.dex */
public class GLEnvironment {
    private int glEnvId;
    private boolean mManageContext = true;

    private native boolean nativeActivate();

    private native boolean nativeActivateSurfaceId(int i);

    private native int nativeAddSurface(android.view.Surface surface);

    private native int nativeAddSurfaceFromMediaRecorder(android.media.MediaRecorder mediaRecorder);

    private native int nativeAddSurfaceWidthHeight(android.view.Surface surface, int i, int i2);

    private native boolean nativeAllocate();

    private native boolean nativeDeactivate();

    private native boolean nativeDeallocate();

    private native boolean nativeDisconnectSurfaceMediaSource(android.media.MediaRecorder mediaRecorder);

    private native boolean nativeInitWithCurrentContext();

    private native boolean nativeInitWithNewContext();

    private native boolean nativeIsActive();

    private static native boolean nativeIsAnyContextActive();

    private native boolean nativeIsContextActive();

    private native boolean nativeRemoveSurfaceId(int i);

    private native boolean nativeSetSurfaceTimestamp(long j);

    private native boolean nativeSwapBuffers();

    public GLEnvironment() {
        nativeAllocate();
    }

    private GLEnvironment(android.filterfw.core.NativeAllocatorTag nativeAllocatorTag) {
    }

    public synchronized void tearDown() {
        if (this.glEnvId != -1) {
            nativeDeallocate();
            this.glEnvId = -1;
        }
    }

    protected void finalize() throws java.lang.Throwable {
        tearDown();
    }

    public void initWithNewContext() {
        this.mManageContext = true;
        if (!nativeInitWithNewContext()) {
            throw new java.lang.RuntimeException("Could not initialize GLEnvironment with new context!");
        }
    }

    public void initWithCurrentContext() {
        this.mManageContext = false;
        if (!nativeInitWithCurrentContext()) {
            throw new java.lang.RuntimeException("Could not initialize GLEnvironment with current context!");
        }
    }

    public boolean isActive() {
        return nativeIsActive();
    }

    public boolean isContextActive() {
        return nativeIsContextActive();
    }

    public static boolean isAnyContextActive() {
        return nativeIsAnyContextActive();
    }

    public void activate() {
        if (android.os.Looper.myLooper() != null && android.os.Looper.myLooper().equals(android.os.Looper.getMainLooper())) {
            android.util.Log.e("FilterFramework", "Activating GL context in UI thread!");
        }
        if (this.mManageContext && !nativeActivate()) {
            throw new java.lang.RuntimeException("Could not activate GLEnvironment!");
        }
    }

    public void deactivate() {
        if (this.mManageContext && !nativeDeactivate()) {
            throw new java.lang.RuntimeException("Could not deactivate GLEnvironment!");
        }
    }

    public void swapBuffers() {
        if (!nativeSwapBuffers()) {
            throw new java.lang.RuntimeException("Error swapping EGL buffers!");
        }
    }

    public int registerSurface(android.view.Surface surface) {
        int nativeAddSurface = nativeAddSurface(surface);
        if (nativeAddSurface < 0) {
            throw new java.lang.RuntimeException("Error registering surface " + surface + "!");
        }
        return nativeAddSurface;
    }

    public int registerSurfaceTexture(android.graphics.SurfaceTexture surfaceTexture, int i, int i2) {
        android.view.Surface surface = new android.view.Surface(surfaceTexture);
        int nativeAddSurfaceWidthHeight = nativeAddSurfaceWidthHeight(surface, i, i2);
        surface.release();
        if (nativeAddSurfaceWidthHeight < 0) {
            throw new java.lang.RuntimeException("Error registering surfaceTexture " + surfaceTexture + "!");
        }
        return nativeAddSurfaceWidthHeight;
    }

    public int registerSurfaceFromMediaRecorder(android.media.MediaRecorder mediaRecorder) {
        int nativeAddSurfaceFromMediaRecorder = nativeAddSurfaceFromMediaRecorder(mediaRecorder);
        if (nativeAddSurfaceFromMediaRecorder < 0) {
            throw new java.lang.RuntimeException("Error registering surface from MediaRecorder" + mediaRecorder + "!");
        }
        return nativeAddSurfaceFromMediaRecorder;
    }

    public void activateSurfaceWithId(int i) {
        if (!nativeActivateSurfaceId(i)) {
            throw new java.lang.RuntimeException("Could not activate surface " + i + "!");
        }
    }

    public void unregisterSurfaceId(int i) {
        if (!nativeRemoveSurfaceId(i)) {
            throw new java.lang.RuntimeException("Could not unregister surface " + i + "!");
        }
    }

    public void setSurfaceTimestamp(long j) {
        if (!nativeSetSurfaceTimestamp(j)) {
            throw new java.lang.RuntimeException("Could not set timestamp for current surface!");
        }
    }

    static {
        java.lang.System.loadLibrary("filterfw");
    }
}
