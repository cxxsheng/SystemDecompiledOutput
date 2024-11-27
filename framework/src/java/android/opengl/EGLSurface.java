package android.opengl;

/* loaded from: classes3.dex */
public class EGLSurface extends android.opengl.EGLObjectHandle {
    private EGLSurface(long j) {
        super(j);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof android.opengl.EGLSurface) && getNativeHandle() == ((android.opengl.EGLSurface) obj).getNativeHandle();
    }
}
