package android.opengl;

/* loaded from: classes3.dex */
public class EGLDisplay extends android.opengl.EGLObjectHandle {
    private EGLDisplay(long j) {
        super(j);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof android.opengl.EGLDisplay) && getNativeHandle() == ((android.opengl.EGLDisplay) obj).getNativeHandle();
    }
}
