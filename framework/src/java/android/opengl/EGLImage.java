package android.opengl;

/* loaded from: classes3.dex */
public class EGLImage extends android.opengl.EGLObjectHandle {
    private EGLImage(long j) {
        super(j);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof android.opengl.EGLImage) && getNativeHandle() == ((android.opengl.EGLImage) obj).getNativeHandle();
    }
}
