package android.opengl;

/* loaded from: classes3.dex */
public class EGLExt {
    public static final int EGL_CONTEXT_FLAGS_KHR = 12540;
    public static final int EGL_CONTEXT_MAJOR_VERSION_KHR = 12440;
    public static final int EGL_CONTEXT_MINOR_VERSION_KHR = 12539;
    public static final int EGL_NO_NATIVE_FENCE_FD_ANDROID = -1;
    public static final int EGL_OPENGL_ES3_BIT_KHR = 64;
    public static final int EGL_RECORDABLE_ANDROID = 12610;
    public static final int EGL_SYNC_NATIVE_FENCE_ANDROID = 12612;
    public static final int EGL_SYNC_NATIVE_FENCE_FD_ANDROID = 12613;
    public static final int EGL_SYNC_NATIVE_FENCE_SIGNALED_ANDROID = 12614;

    private static native void _nativeClassInit();

    private static native int eglDupNativeFenceFDANDROIDImpl(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLSync eGLSync);

    public static native boolean eglPresentationTimeANDROID(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLSurface eGLSurface, long j);

    static {
        _nativeClassInit();
    }

    public static android.hardware.SyncFence eglDupNativeFenceFDANDROID(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLSync eGLSync) {
        int eglDupNativeFenceFDANDROIDImpl = eglDupNativeFenceFDANDROIDImpl(eGLDisplay, eGLSync);
        android.util.Log.d("EGL", "eglDupNativeFence returned " + eglDupNativeFenceFDANDROIDImpl);
        if (eglDupNativeFenceFDANDROIDImpl >= 0) {
            return android.hardware.SyncFence.create(android.os.ParcelFileDescriptor.adoptFd(eglDupNativeFenceFDANDROIDImpl));
        }
        return android.hardware.SyncFence.createEmpty();
    }
}
