package android.opengl;

/* loaded from: classes3.dex */
public class GLException extends java.lang.RuntimeException {
    private final int mError;

    public GLException(int i) {
        super(getErrorString(i));
        this.mError = i;
    }

    public GLException(int i, java.lang.String str) {
        super(str);
        this.mError = i;
    }

    private static java.lang.String getErrorString(int i) {
        java.lang.String gluErrorString = android.opengl.GLU.gluErrorString(i);
        if (gluErrorString == null) {
            return "Unknown error 0x" + java.lang.Integer.toHexString(i);
        }
        return gluErrorString;
    }

    int getError() {
        return this.mError;
    }
}
