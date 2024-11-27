package android.opengl;

/* loaded from: classes3.dex */
public class GLDebugHelper {
    public static final int CONFIG_CHECK_GL_ERROR = 1;
    public static final int CONFIG_CHECK_THREAD = 2;
    public static final int CONFIG_LOG_ARGUMENT_NAMES = 4;
    public static final int ERROR_WRONG_THREAD = 28672;

    public static javax.microedition.khronos.opengles.GL wrap(javax.microedition.khronos.opengles.GL gl, int i, java.io.Writer writer) {
        if (i != 0) {
            gl = new android.opengl.GLErrorWrapper(gl, i);
        }
        if (writer != null) {
            return new android.opengl.GLLogWrapper(gl, writer, (i & 4) != 0);
        }
        return gl;
    }

    public static javax.microedition.khronos.egl.EGL wrap(javax.microedition.khronos.egl.EGL egl, int i, java.io.Writer writer) {
        if (writer != null) {
            return new android.opengl.EGLLogWrapper(egl, i, writer);
        }
        return egl;
    }
}
