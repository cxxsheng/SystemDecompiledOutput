package android.opengl;

/* loaded from: classes3.dex */
public class EGLContext extends android.opengl.EGLObjectHandle {
    private EGLContext(long j) {
        super(j);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof android.opengl.EGLContext) && getNativeHandle() == ((android.opengl.EGLContext) obj).getNativeHandle();
    }
}
