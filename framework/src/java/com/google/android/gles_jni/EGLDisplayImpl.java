package com.google.android.gles_jni;

/* loaded from: classes5.dex */
public class EGLDisplayImpl extends javax.microedition.khronos.egl.EGLDisplay {
    long mEGLDisplay;

    public EGLDisplayImpl(long j) {
        this.mEGLDisplay = j;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mEGLDisplay == ((com.google.android.gles_jni.EGLDisplayImpl) obj).mEGLDisplay) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 527 + ((int) (this.mEGLDisplay ^ (this.mEGLDisplay >>> 32)));
    }
}
