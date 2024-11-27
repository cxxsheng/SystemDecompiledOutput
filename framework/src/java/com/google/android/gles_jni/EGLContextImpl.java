package com.google.android.gles_jni;

/* loaded from: classes5.dex */
public class EGLContextImpl extends javax.microedition.khronos.egl.EGLContext {
    long mEGLContext;
    private com.google.android.gles_jni.GLImpl mGLContext = new com.google.android.gles_jni.GLImpl();

    public EGLContextImpl(long j) {
        this.mEGLContext = j;
    }

    @Override // javax.microedition.khronos.egl.EGLContext
    public javax.microedition.khronos.opengles.GL getGL() {
        return this.mGLContext;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mEGLContext == ((com.google.android.gles_jni.EGLContextImpl) obj).mEGLContext) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 527 + ((int) (this.mEGLContext ^ (this.mEGLContext >>> 32)));
    }
}
