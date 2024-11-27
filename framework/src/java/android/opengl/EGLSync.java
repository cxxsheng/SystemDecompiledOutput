package android.opengl;

/* loaded from: classes3.dex */
public class EGLSync extends android.opengl.EGLObjectHandle {
    private EGLSync(long j) {
        super(j);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof android.opengl.EGLSync) && getNativeHandle() == ((android.opengl.EGLSync) obj).getNativeHandle();
    }
}
