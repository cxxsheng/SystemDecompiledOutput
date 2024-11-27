package com.android.server.wallpaper;

/* loaded from: classes.dex */
class GLHelper {
    private static final java.lang.String TAG = com.android.server.wallpaper.GLHelper.class.getSimpleName();
    private static final int sMaxTextureSize;

    GLHelper() {
    }

    static {
        int i = android.os.SystemProperties.getInt("sys.max_texture_size", 0);
        if (i <= 0) {
            i = retrieveTextureSizeFromGL();
        }
        sMaxTextureSize = i;
    }

    private static int retrieveTextureSizeFromGL() {
        try {
            android.opengl.EGLDisplay eglGetDisplay = android.opengl.EGL14.eglGetDisplay(0);
            if (eglGetDisplay == null || eglGetDisplay == android.opengl.EGL14.EGL_NO_DISPLAY) {
                throw new java.lang.RuntimeException("eglGetDisplay failed: " + android.opengl.GLUtils.getEGLErrorString(android.opengl.EGL14.eglGetError()));
            }
            android.opengl.EGLConfig eGLConfig = null;
            if (!android.opengl.EGL14.eglInitialize(eglGetDisplay, null, 0, null, 1)) {
                throw new java.lang.RuntimeException("eglInitialize failed: " + android.opengl.GLUtils.getEGLErrorString(android.opengl.EGL14.eglGetError()));
            }
            int[] iArr = new int[1];
            android.opengl.EGLConfig[] eGLConfigArr = new android.opengl.EGLConfig[1];
            if (!android.opengl.EGL14.eglChooseConfig(eglGetDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 0, 12325, 0, 12326, 0, 12327, 12344, 12344}, 0, eGLConfigArr, 0, 1, iArr, 0)) {
                throw new java.lang.RuntimeException("eglChooseConfig failed: " + android.opengl.GLUtils.getEGLErrorString(android.opengl.EGL14.eglGetError()));
            }
            if (iArr[0] > 0) {
                eGLConfig = eGLConfigArr[0];
            }
            if (eGLConfig == null) {
                throw new java.lang.RuntimeException("eglConfig not initialized!");
            }
            android.opengl.EGLContext eglCreateContext = android.opengl.EGL14.eglCreateContext(eglGetDisplay, eGLConfig, android.opengl.EGL14.EGL_NO_CONTEXT, new int[]{12440, 2, 12344}, 0);
            if (eglCreateContext == null || eglCreateContext == android.opengl.EGL14.EGL_NO_CONTEXT) {
                throw new java.lang.RuntimeException("eglCreateContext failed: " + android.opengl.GLUtils.getEGLErrorString(android.opengl.EGL14.eglGetError()));
            }
            android.opengl.EGLSurface eglCreatePbufferSurface = android.opengl.EGL14.eglCreatePbufferSurface(eglGetDisplay, eGLConfig, new int[]{12375, 1, 12374, 1, 12344}, 0);
            android.opengl.EGL14.eglMakeCurrent(eglGetDisplay, eglCreatePbufferSurface, eglCreatePbufferSurface, eglCreateContext);
            int[] iArr2 = new int[1];
            android.opengl.GLES20.glGetIntegerv(3379, iArr2, 0);
            android.opengl.EGL14.eglMakeCurrent(eglGetDisplay, android.opengl.EGL14.EGL_NO_SURFACE, android.opengl.EGL14.EGL_NO_SURFACE, android.opengl.EGL14.EGL_NO_CONTEXT);
            android.opengl.EGL14.eglDestroySurface(eglGetDisplay, eglCreatePbufferSurface);
            android.opengl.EGL14.eglDestroyContext(eglGetDisplay, eglCreateContext);
            android.opengl.EGL14.eglTerminate(eglGetDisplay);
            return iArr2[0];
        } catch (java.lang.RuntimeException e) {
            android.util.Log.w(TAG, "Retrieve from GL failed", e);
            return Integer.MAX_VALUE;
        }
    }

    static int getMaxTextureSize() {
        return sMaxTextureSize;
    }
}
