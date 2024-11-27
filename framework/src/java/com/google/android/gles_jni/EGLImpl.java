package com.google.android.gles_jni;

/* loaded from: classes5.dex */
public class EGLImpl implements javax.microedition.khronos.egl.EGL10 {
    private com.google.android.gles_jni.EGLContextImpl mContext = new com.google.android.gles_jni.EGLContextImpl(-1);
    private com.google.android.gles_jni.EGLDisplayImpl mDisplay = new com.google.android.gles_jni.EGLDisplayImpl(-1);
    private com.google.android.gles_jni.EGLSurfaceImpl mSurface = new com.google.android.gles_jni.EGLSurfaceImpl(-1);

    private native long _eglCreateContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, javax.microedition.khronos.egl.EGLContext eGLContext, int[] iArr);

    private native long _eglCreatePbufferSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int[] iArr);

    private native void _eglCreatePixmapSurface(javax.microedition.khronos.egl.EGLSurface eGLSurface, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, java.lang.Object obj, int[] iArr);

    private native long _eglCreateWindowSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, java.lang.Object obj, int[] iArr);

    private native long _eglCreateWindowSurfaceTexture(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, java.lang.Object obj, int[] iArr);

    private native long _eglGetCurrentContext();

    private native long _eglGetCurrentDisplay();

    private native long _eglGetCurrentSurface(int i);

    private native long _eglGetDisplay(java.lang.Object obj);

    private static native void _nativeClassInit();

    public static native int getInitCount(javax.microedition.khronos.egl.EGLDisplay eGLDisplay);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglChooseConfig(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int[] iArr, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr, int i, int[] iArr2);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglCopyBuffers(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, java.lang.Object obj);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglDestroyContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglDestroySurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglGetConfigAttrib(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int i, int[] iArr);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglGetConfigs(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr, int i, int[] iArr);

    @Override // javax.microedition.khronos.egl.EGL10
    public native int eglGetError();

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglInitialize(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int[] iArr);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglMakeCurrent(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, javax.microedition.khronos.egl.EGLSurface eGLSurface2, javax.microedition.khronos.egl.EGLContext eGLContext);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglQueryContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext, int i, int[] iArr);

    @Override // javax.microedition.khronos.egl.EGL10
    public native java.lang.String eglQueryString(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int i);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglQuerySurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, int i, int[] iArr);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglReleaseThread();

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglSwapBuffers(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglTerminate(javax.microedition.khronos.egl.EGLDisplay eGLDisplay);

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglWaitGL();

    @Override // javax.microedition.khronos.egl.EGL10
    public native boolean eglWaitNative(int i, java.lang.Object obj);

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLContext eglCreateContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, javax.microedition.khronos.egl.EGLContext eGLContext, int[] iArr) {
        long _eglCreateContext = _eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        if (_eglCreateContext == 0) {
            return javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT;
        }
        return new com.google.android.gles_jni.EGLContextImpl(_eglCreateContext);
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreatePbufferSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int[] iArr) {
        long _eglCreatePbufferSurface = _eglCreatePbufferSurface(eGLDisplay, eGLConfig, iArr);
        if (_eglCreatePbufferSurface == 0) {
            return javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE;
        }
        return new com.google.android.gles_jni.EGLSurfaceImpl(_eglCreatePbufferSurface);
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreatePixmapSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, java.lang.Object obj, int[] iArr) {
        com.google.android.gles_jni.EGLSurfaceImpl eGLSurfaceImpl = new com.google.android.gles_jni.EGLSurfaceImpl();
        _eglCreatePixmapSurface(eGLSurfaceImpl, eGLDisplay, eGLConfig, obj, iArr);
        if (eGLSurfaceImpl.mEGLSurface == 0) {
            return javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE;
        }
        return eGLSurfaceImpl;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreateWindowSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, java.lang.Object obj, int[] iArr) {
        android.view.Surface surface;
        long _eglCreateWindowSurfaceTexture;
        if (obj instanceof android.view.SurfaceView) {
            surface = ((android.view.SurfaceView) obj).getHolder().getSurface();
        } else if (obj instanceof android.view.SurfaceHolder) {
            surface = ((android.view.SurfaceHolder) obj).getSurface();
        } else if (!(obj instanceof android.view.Surface)) {
            surface = null;
        } else {
            surface = (android.view.Surface) obj;
        }
        if (surface != null) {
            _eglCreateWindowSurfaceTexture = _eglCreateWindowSurface(eGLDisplay, eGLConfig, surface, iArr);
        } else if (obj instanceof android.graphics.SurfaceTexture) {
            _eglCreateWindowSurfaceTexture = _eglCreateWindowSurfaceTexture(eGLDisplay, eGLConfig, obj, iArr);
        } else {
            throw new java.lang.UnsupportedOperationException("eglCreateWindowSurface() can only be called with an instance of Surface, SurfaceView, SurfaceHolder or SurfaceTexture at the moment.");
        }
        if (_eglCreateWindowSurfaceTexture == 0) {
            return javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE;
        }
        return new com.google.android.gles_jni.EGLSurfaceImpl(_eglCreateWindowSurfaceTexture);
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public synchronized javax.microedition.khronos.egl.EGLDisplay eglGetDisplay(java.lang.Object obj) {
        long _eglGetDisplay = _eglGetDisplay(obj);
        if (_eglGetDisplay == 0) {
            return javax.microedition.khronos.egl.EGL10.EGL_NO_DISPLAY;
        }
        if (this.mDisplay.mEGLDisplay != _eglGetDisplay) {
            this.mDisplay = new com.google.android.gles_jni.EGLDisplayImpl(_eglGetDisplay);
        }
        return this.mDisplay;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public synchronized javax.microedition.khronos.egl.EGLContext eglGetCurrentContext() {
        long _eglGetCurrentContext = _eglGetCurrentContext();
        if (_eglGetCurrentContext == 0) {
            return javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT;
        }
        if (this.mContext.mEGLContext != _eglGetCurrentContext) {
            this.mContext = new com.google.android.gles_jni.EGLContextImpl(_eglGetCurrentContext);
        }
        return this.mContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public synchronized javax.microedition.khronos.egl.EGLDisplay eglGetCurrentDisplay() {
        long _eglGetCurrentDisplay = _eglGetCurrentDisplay();
        if (_eglGetCurrentDisplay == 0) {
            return javax.microedition.khronos.egl.EGL10.EGL_NO_DISPLAY;
        }
        if (this.mDisplay.mEGLDisplay != _eglGetCurrentDisplay) {
            this.mDisplay = new com.google.android.gles_jni.EGLDisplayImpl(_eglGetCurrentDisplay);
        }
        return this.mDisplay;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public synchronized javax.microedition.khronos.egl.EGLSurface eglGetCurrentSurface(int i) {
        long _eglGetCurrentSurface = _eglGetCurrentSurface(i);
        if (_eglGetCurrentSurface == 0) {
            return javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE;
        }
        if (this.mSurface.mEGLSurface != _eglGetCurrentSurface) {
            this.mSurface = new com.google.android.gles_jni.EGLSurfaceImpl(_eglGetCurrentSurface);
        }
        return this.mSurface;
    }

    static {
        _nativeClassInit();
    }
}
