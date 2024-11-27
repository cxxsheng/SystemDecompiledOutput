package javax.microedition.khronos.egl;

/* loaded from: classes5.dex */
public abstract class EGLContext {
    private static final javax.microedition.khronos.egl.EGL EGL_INSTANCE = new com.google.android.gles_jni.EGLImpl();

    public abstract javax.microedition.khronos.opengles.GL getGL();

    public static javax.microedition.khronos.egl.EGL getEGL() {
        return EGL_INSTANCE;
    }
}
