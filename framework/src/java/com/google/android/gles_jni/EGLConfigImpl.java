package com.google.android.gles_jni;

/* loaded from: classes5.dex */
public class EGLConfigImpl extends javax.microedition.khronos.egl.EGLConfig {
    private long mEGLConfig;

    EGLConfigImpl(long j) {
        this.mEGLConfig = j;
    }

    long get() {
        return this.mEGLConfig;
    }
}
