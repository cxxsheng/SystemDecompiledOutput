package android.opengl;

/* loaded from: classes3.dex */
class EGLLogWrapper implements javax.microedition.khronos.egl.EGL11 {
    private int mArgCount;
    boolean mCheckError;
    private javax.microedition.khronos.egl.EGL10 mEgl10;
    java.io.Writer mLog;
    boolean mLogArgumentNames;

    public EGLLogWrapper(javax.microedition.khronos.egl.EGL egl, int i, java.io.Writer writer) {
        this.mEgl10 = (javax.microedition.khronos.egl.EGL10) egl;
        this.mLog = writer;
        this.mLogArgumentNames = (i & 4) != 0;
        this.mCheckError = (i & 1) != 0;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglChooseConfig(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int[] iArr, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr, int i, int[] iArr2) {
        begin("eglChooseConfig");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("attrib_list", iArr);
        arg("config_size", i);
        end();
        boolean eglChooseConfig = this.mEgl10.eglChooseConfig(eGLDisplay, iArr, eGLConfigArr, i, iArr2);
        arg("configs", (java.lang.Object[]) eGLConfigArr);
        arg("num_config", iArr2);
        returns(eglChooseConfig);
        checkError();
        return eglChooseConfig;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglCopyBuffers(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, java.lang.Object obj) {
        begin("eglCopyBuffers");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        arg("native_pixmap", obj);
        end();
        boolean eglCopyBuffers = this.mEgl10.eglCopyBuffers(eGLDisplay, eGLSurface, obj);
        returns(eglCopyBuffers);
        checkError();
        return eglCopyBuffers;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLContext eglCreateContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, javax.microedition.khronos.egl.EGLContext eGLContext, int[] iArr) {
        begin("eglCreateContext");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("share_context", eGLContext);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLContext eglCreateContext = this.mEgl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        returns(eglCreateContext);
        checkError();
        return eglCreateContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreatePbufferSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int[] iArr) {
        begin("eglCreatePbufferSurface");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLSurface eglCreatePbufferSurface = this.mEgl10.eglCreatePbufferSurface(eGLDisplay, eGLConfig, iArr);
        returns(eglCreatePbufferSurface);
        checkError();
        return eglCreatePbufferSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreatePixmapSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, java.lang.Object obj, int[] iArr) {
        begin("eglCreatePixmapSurface");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("native_pixmap", obj);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLSurface eglCreatePixmapSurface = this.mEgl10.eglCreatePixmapSurface(eGLDisplay, eGLConfig, obj, iArr);
        returns(eglCreatePixmapSurface);
        checkError();
        return eglCreatePixmapSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglCreateWindowSurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, java.lang.Object obj, int[] iArr) {
        begin("eglCreateWindowSurface");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("native_window", obj);
        arg("attrib_list", iArr);
        end();
        javax.microedition.khronos.egl.EGLSurface eglCreateWindowSurface = this.mEgl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, iArr);
        returns(eglCreateWindowSurface);
        checkError();
        return eglCreateWindowSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglDestroyContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext) {
        begin("eglDestroyContext");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("context", eGLContext);
        end();
        boolean eglDestroyContext = this.mEgl10.eglDestroyContext(eGLDisplay, eGLContext);
        returns(eglDestroyContext);
        checkError();
        return eglDestroyContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglDestroySurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface) {
        begin("eglDestroySurface");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        end();
        boolean eglDestroySurface = this.mEgl10.eglDestroySurface(eGLDisplay, eGLSurface);
        returns(eglDestroySurface);
        checkError();
        return eglDestroySurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglGetConfigAttrib(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int i, int[] iArr) {
        begin("eglGetConfigAttrib");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config", eGLConfig);
        arg("attribute", i);
        end();
        boolean eglGetConfigAttrib = this.mEgl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, iArr);
        arg("value", iArr);
        returns(eglGetConfigAttrib);
        checkError();
        return false;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglGetConfigs(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr, int i, int[] iArr) {
        begin("eglGetConfigs");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("config_size", i);
        end();
        boolean eglGetConfigs = this.mEgl10.eglGetConfigs(eGLDisplay, eGLConfigArr, i, iArr);
        arg("configs", (java.lang.Object[]) eGLConfigArr);
        arg("num_config", iArr);
        returns(eglGetConfigs);
        checkError();
        return eglGetConfigs;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLContext eglGetCurrentContext() {
        begin("eglGetCurrentContext");
        end();
        javax.microedition.khronos.egl.EGLContext eglGetCurrentContext = this.mEgl10.eglGetCurrentContext();
        returns(eglGetCurrentContext);
        checkError();
        return eglGetCurrentContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLDisplay eglGetCurrentDisplay() {
        begin("eglGetCurrentDisplay");
        end();
        javax.microedition.khronos.egl.EGLDisplay eglGetCurrentDisplay = this.mEgl10.eglGetCurrentDisplay();
        returns(eglGetCurrentDisplay);
        checkError();
        return eglGetCurrentDisplay;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLSurface eglGetCurrentSurface(int i) {
        begin("eglGetCurrentSurface");
        arg("readdraw", i);
        end();
        javax.microedition.khronos.egl.EGLSurface eglGetCurrentSurface = this.mEgl10.eglGetCurrentSurface(i);
        returns(eglGetCurrentSurface);
        checkError();
        return eglGetCurrentSurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public javax.microedition.khronos.egl.EGLDisplay eglGetDisplay(java.lang.Object obj) {
        begin("eglGetDisplay");
        arg("native_display", obj);
        end();
        javax.microedition.khronos.egl.EGLDisplay eglGetDisplay = this.mEgl10.eglGetDisplay(obj);
        returns(eglGetDisplay);
        checkError();
        return eglGetDisplay;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public int eglGetError() {
        begin("eglGetError");
        end();
        int eglGetError = this.mEgl10.eglGetError();
        returns(getErrorString(eglGetError));
        return eglGetError;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglInitialize(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int[] iArr) {
        begin("eglInitialize");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        end();
        boolean eglInitialize = this.mEgl10.eglInitialize(eGLDisplay, iArr);
        returns(eglInitialize);
        arg("major_minor", iArr);
        checkError();
        return eglInitialize;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglMakeCurrent(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, javax.microedition.khronos.egl.EGLSurface eGLSurface2, javax.microedition.khronos.egl.EGLContext eGLContext) {
        begin("eglMakeCurrent");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("draw", eGLSurface);
        arg("read", eGLSurface2);
        arg("context", eGLContext);
        end();
        boolean eglMakeCurrent = this.mEgl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface2, eGLContext);
        returns(eglMakeCurrent);
        checkError();
        return eglMakeCurrent;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglQueryContext(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext, int i, int[] iArr) {
        begin("eglQueryContext");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("context", eGLContext);
        arg("attribute", i);
        end();
        boolean eglQueryContext = this.mEgl10.eglQueryContext(eGLDisplay, eGLContext, i, iArr);
        returns(iArr[0]);
        returns(eglQueryContext);
        checkError();
        return eglQueryContext;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public java.lang.String eglQueryString(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, int i) {
        begin("eglQueryString");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("name", i);
        end();
        java.lang.String eglQueryString = this.mEgl10.eglQueryString(eGLDisplay, i);
        returns(eglQueryString);
        checkError();
        return eglQueryString;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglQuerySurface(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface, int i, int[] iArr) {
        begin("eglQuerySurface");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        arg("attribute", i);
        end();
        boolean eglQuerySurface = this.mEgl10.eglQuerySurface(eGLDisplay, eGLSurface, i, iArr);
        returns(iArr[0]);
        returns(eglQuerySurface);
        checkError();
        return eglQuerySurface;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglReleaseThread() {
        begin("eglReleaseThread");
        end();
        boolean eglReleaseThread = this.mEgl10.eglReleaseThread();
        returns(eglReleaseThread);
        checkError();
        return eglReleaseThread;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglSwapBuffers(javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface) {
        begin("eglSwapBuffers");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        arg("surface", eGLSurface);
        end();
        boolean eglSwapBuffers = this.mEgl10.eglSwapBuffers(eGLDisplay, eGLSurface);
        returns(eglSwapBuffers);
        checkError();
        return eglSwapBuffers;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglTerminate(javax.microedition.khronos.egl.EGLDisplay eGLDisplay) {
        begin("eglTerminate");
        arg(android.content.Context.DISPLAY_SERVICE, eGLDisplay);
        end();
        boolean eglTerminate = this.mEgl10.eglTerminate(eGLDisplay);
        returns(eglTerminate);
        checkError();
        return eglTerminate;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglWaitGL() {
        begin("eglWaitGL");
        end();
        boolean eglWaitGL = this.mEgl10.eglWaitGL();
        returns(eglWaitGL);
        checkError();
        return eglWaitGL;
    }

    @Override // javax.microedition.khronos.egl.EGL10
    public boolean eglWaitNative(int i, java.lang.Object obj) {
        begin("eglWaitNative");
        arg(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_ENGINE, i);
        arg("bindTarget", obj);
        end();
        boolean eglWaitNative = this.mEgl10.eglWaitNative(i, obj);
        returns(eglWaitNative);
        checkError();
        return eglWaitNative;
    }

    private void checkError() {
        int eglGetError = this.mEgl10.eglGetError();
        if (eglGetError != 12288) {
            java.lang.String str = "eglError: " + getErrorString(eglGetError);
            logLine(str);
            if (this.mCheckError) {
                throw new android.opengl.GLException(eglGetError, str);
            }
        }
    }

    private void logLine(java.lang.String str) {
        log(str + '\n');
    }

    private void log(java.lang.String str) {
        try {
            this.mLog.write(str);
        } catch (java.io.IOException e) {
        }
    }

    private void begin(java.lang.String str) {
        log(str + '(');
        this.mArgCount = 0;
    }

    private void arg(java.lang.String str, java.lang.String str2) {
        int i = this.mArgCount;
        this.mArgCount = i + 1;
        if (i > 0) {
            log(", ");
        }
        if (this.mLogArgumentNames) {
            log(str + "=");
        }
        log(str2);
    }

    private void end() {
        log(");\n");
        flush();
    }

    private void flush() {
        try {
            this.mLog.flush();
        } catch (java.io.IOException e) {
            this.mLog = null;
        }
    }

    private void arg(java.lang.String str, int i) {
        arg(str, java.lang.Integer.toString(i));
    }

    private void arg(java.lang.String str, java.lang.Object obj) {
        arg(str, toString(obj));
    }

    private void arg(java.lang.String str, javax.microedition.khronos.egl.EGLDisplay eGLDisplay) {
        if (eGLDisplay == javax.microedition.khronos.egl.EGL10.EGL_DEFAULT_DISPLAY) {
            arg(str, "EGL10.EGL_DEFAULT_DISPLAY");
        } else if (eGLDisplay == EGL_NO_DISPLAY) {
            arg(str, "EGL10.EGL_NO_DISPLAY");
        } else {
            arg(str, toString(eGLDisplay));
        }
    }

    private void arg(java.lang.String str, javax.microedition.khronos.egl.EGLContext eGLContext) {
        if (eGLContext == javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT) {
            arg(str, "EGL10.EGL_NO_CONTEXT");
        } else {
            arg(str, toString(eGLContext));
        }
    }

    private void arg(java.lang.String str, javax.microedition.khronos.egl.EGLSurface eGLSurface) {
        if (eGLSurface == javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE) {
            arg(str, "EGL10.EGL_NO_SURFACE");
        } else {
            arg(str, toString(eGLSurface));
        }
    }

    private void returns(java.lang.String str) {
        log(" returns " + str + ";\n");
        flush();
    }

    private void returns(int i) {
        returns(java.lang.Integer.toString(i));
    }

    private void returns(boolean z) {
        returns(java.lang.Boolean.toString(z));
    }

    private void returns(java.lang.Object obj) {
        returns(toString(obj));
    }

    private java.lang.String toString(java.lang.Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.toString();
    }

    private void arg(java.lang.String str, int[] iArr) {
        if (iArr == null) {
            arg(str, "null");
        } else {
            arg(str, toString(iArr.length, iArr, 0));
        }
    }

    private void arg(java.lang.String str, java.lang.Object[] objArr) {
        if (objArr == null) {
            arg(str, "null");
        } else {
            arg(str, toString(objArr.length, objArr, 0));
        }
    }

    private java.lang.String toString(int i, int[] iArr, int i2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{\n");
        int length = iArr.length;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i2 + i3;
            sb.append(" [" + i4 + "] = ");
            if (i4 < 0 || i4 >= length) {
                sb.append("out of bounds");
            } else {
                sb.append(iArr[i4]);
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private java.lang.String toString(int i, java.lang.Object[] objArr, int i2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{\n");
        int length = objArr.length;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i2 + i3;
            sb.append(" [" + i4 + "] = ");
            if (i4 < 0 || i4 >= length) {
                sb.append("out of bounds");
            } else {
                sb.append(objArr[i4]);
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private static java.lang.String getHex(int i) {
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static java.lang.String getErrorString(int i) {
        switch (i) {
            case 12288:
                return "EGL_SUCCESS";
            case 12289:
                return "EGL_NOT_INITIALIZED";
            case 12290:
                return "EGL_BAD_ACCESS";
            case 12291:
                return "EGL_BAD_ALLOC";
            case 12292:
                return "EGL_BAD_ATTRIBUTE";
            case 12293:
                return "EGL_BAD_CONFIG";
            case 12294:
                return "EGL_BAD_CONTEXT";
            case 12295:
                return "EGL_BAD_CURRENT_SURFACE";
            case 12296:
                return "EGL_BAD_DISPLAY";
            case 12297:
                return "EGL_BAD_MATCH";
            case 12298:
                return "EGL_BAD_NATIVE_PIXMAP";
            case 12299:
                return "EGL_BAD_NATIVE_WINDOW";
            case 12300:
                return "EGL_BAD_PARAMETER";
            case 12301:
                return "EGL_BAD_SURFACE";
            case 12302:
                return "EGL_CONTEXT_LOST";
            default:
                return getHex(i);
        }
    }
}
