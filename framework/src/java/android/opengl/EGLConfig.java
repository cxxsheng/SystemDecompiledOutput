package android.opengl;

/* loaded from: classes3.dex */
public class EGLConfig extends android.opengl.EGLObjectHandle {
    private EGLConfig(long j) {
        super(j);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof android.opengl.EGLConfig) && getNativeHandle() == ((android.opengl.EGLConfig) obj).getNativeHandle();
    }
}
