package com.google.android.gles_jni;

/* loaded from: classes5.dex */
public class EGLSurfaceImpl extends javax.microedition.khronos.egl.EGLSurface {
    long mEGLSurface;

    public EGLSurfaceImpl() {
        this.mEGLSurface = 0L;
    }

    public EGLSurfaceImpl(long j) {
        this.mEGLSurface = j;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mEGLSurface == ((com.google.android.gles_jni.EGLSurfaceImpl) obj).mEGLSurface) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 527 + ((int) (this.mEGLSurface ^ (this.mEGLSurface >>> 32)));
    }
}
