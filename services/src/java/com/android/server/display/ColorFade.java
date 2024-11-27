package com.android.server.display;

/* loaded from: classes.dex */
final class ColorFade {
    private static final int COLOR_FADE_LAYER = 1073741825;
    private static final int DEJANK_FRAMES = 3;
    private static final int EGL_GL_COLORSPACE_DISPLAY_P3_PASSTHROUGH_EXT = 13456;
    private static final int EGL_GL_COLORSPACE_KHR = 12445;
    private static final int EGL_PROTECTED_CONTENT_EXT = 12992;
    public static final int MODE_COOL_DOWN = 1;
    public static final int MODE_FADE = 2;
    public static final int MODE_WARM_UP = 0;
    private android.graphics.BLASTBufferQueue mBLASTBufferQueue;
    private android.view.SurfaceControl mBLASTSurfaceControl;
    private boolean mCreatedResources;
    private int mDisplayHeight;
    private final int mDisplayId;
    private int mDisplayLayerStack;
    private final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
    private int mDisplayWidth;
    private android.opengl.EGLConfig mEglConfig;
    private android.opengl.EGLContext mEglContext;
    private android.opengl.EGLDisplay mEglDisplay;
    private android.opengl.EGLSurface mEglSurface;
    private final int[] mGLBuffers;
    private int mGammaLoc;
    private boolean mLastWasProtectedContent;
    private boolean mLastWasWideColor;
    private int mMode;
    private int mOpacityLoc;
    private boolean mPrepared;
    private int mProgram;
    private final float[] mProjMatrix;
    private int mProjMatrixLoc;
    private android.view.Surface mSurface;
    private float mSurfaceAlpha;
    private android.view.SurfaceControl mSurfaceControl;
    private com.android.server.display.ColorFade.NaturalSurfaceLayout mSurfaceLayout;
    private boolean mSurfaceVisible;
    private final java.nio.FloatBuffer mTexCoordBuffer;
    private int mTexCoordLoc;
    private final float[] mTexMatrix;
    private int mTexMatrixLoc;
    private final int[] mTexNames;
    private boolean mTexNamesGenerated;
    private int mTexUnitLoc;
    private final android.view.SurfaceControl.Transaction mTransaction;
    private final java.nio.FloatBuffer mVertexBuffer;
    private int mVertexLoc;
    private static final java.lang.String TAG = "ColorFade";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);

    public ColorFade(int i) {
        this(i, (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class));
    }

    @com.android.internal.annotations.VisibleForTesting
    ColorFade(int i, android.hardware.display.DisplayManagerInternal displayManagerInternal) {
        this.mTexNames = new int[1];
        this.mTexMatrix = new float[16];
        this.mProjMatrix = new float[16];
        this.mGLBuffers = new int[2];
        this.mVertexBuffer = createNativeFloatBuffer(8);
        this.mTexCoordBuffer = createNativeFloatBuffer(8);
        this.mTransaction = new android.view.SurfaceControl.Transaction();
        this.mDisplayId = i;
        this.mDisplayManagerInternal = displayManagerInternal;
    }

    public boolean prepare(android.content.Context context, int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "prepare: mode=" + i);
        }
        this.mMode = i;
        android.view.DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(this.mDisplayId);
        if (displayInfo == null) {
            return false;
        }
        this.mDisplayLayerStack = displayInfo.layerStack;
        this.mDisplayWidth = displayInfo.getNaturalWidth();
        this.mDisplayHeight = displayInfo.getNaturalHeight();
        boolean z = displayInfo.colorMode == 9;
        this.mPrepared = true;
        android.window.ScreenCapture.ScreenshotHardwareBuffer captureScreen = captureScreen();
        if (captureScreen == null) {
            dismiss();
            return false;
        }
        boolean hasProtectedContent = com.android.internal.policy.TransitionAnimation.hasProtectedContent(captureScreen.getHardwareBuffer());
        if (!createSurfaceControl(captureScreen.containsSecureLayers())) {
            dismiss();
            return false;
        }
        if (this.mMode == 2) {
            return true;
        }
        if (!createEglContext(hasProtectedContent) || !createEglSurface(hasProtectedContent, z) || !setScreenshotTextureAndSetViewport(captureScreen, displayInfo.rotation)) {
            dismiss();
            return false;
        }
        if (!attachEglContext()) {
            return false;
        }
        try {
            if (!initGLShaders(context) || !initGLBuffers() || checkGlErrors("prepare")) {
                detachEglContext();
                dismiss();
                return false;
            }
            detachEglContext();
            this.mCreatedResources = true;
            this.mLastWasProtectedContent = hasProtectedContent;
            this.mLastWasWideColor = z;
            if (i == 1) {
                for (int i2 = 0; i2 < 3; i2++) {
                    draw(1.0f);
                }
            }
            return true;
        } finally {
            detachEglContext();
        }
    }

    private java.lang.String readFile(android.content.Context context, int i) {
        try {
            return new java.lang.String(libcore.io.Streams.readFully(new java.io.InputStreamReader(context.getResources().openRawResource(i))));
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Unrecognized shader " + java.lang.Integer.toString(i));
            throw new java.lang.RuntimeException(e);
        }
    }

    private int loadShader(android.content.Context context, int i, int i2) {
        java.lang.String readFile = readFile(context, i);
        int glCreateShader = android.opengl.GLES20.glCreateShader(i2);
        android.opengl.GLES20.glShaderSource(glCreateShader, readFile);
        android.opengl.GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        android.opengl.GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] == 0) {
            android.util.Slog.e(TAG, "Could not compile shader " + glCreateShader + ", " + i2 + ":");
            android.util.Slog.e(TAG, android.opengl.GLES20.glGetShaderSource(glCreateShader));
            android.util.Slog.e(TAG, android.opengl.GLES20.glGetShaderInfoLog(glCreateShader));
            android.opengl.GLES20.glDeleteShader(glCreateShader);
            return 0;
        }
        return glCreateShader;
    }

    private boolean initGLShaders(android.content.Context context) {
        int loadShader = loadShader(context, android.R.raw.color_fade_vert, 35633);
        int loadShader2 = loadShader(context, android.R.raw.color_fade_frag, 35632);
        android.opengl.GLES20.glReleaseShaderCompiler();
        if (loadShader == 0 || loadShader2 == 0) {
            return false;
        }
        this.mProgram = android.opengl.GLES20.glCreateProgram();
        android.opengl.GLES20.glAttachShader(this.mProgram, loadShader);
        android.opengl.GLES20.glAttachShader(this.mProgram, loadShader2);
        android.opengl.GLES20.glDeleteShader(loadShader);
        android.opengl.GLES20.glDeleteShader(loadShader2);
        android.opengl.GLES20.glLinkProgram(this.mProgram);
        this.mVertexLoc = android.opengl.GLES20.glGetAttribLocation(this.mProgram, "position");
        this.mTexCoordLoc = android.opengl.GLES20.glGetAttribLocation(this.mProgram, "uv");
        this.mProjMatrixLoc = android.opengl.GLES20.glGetUniformLocation(this.mProgram, "proj_matrix");
        this.mTexMatrixLoc = android.opengl.GLES20.glGetUniformLocation(this.mProgram, "tex_matrix");
        this.mOpacityLoc = android.opengl.GLES20.glGetUniformLocation(this.mProgram, "opacity");
        this.mGammaLoc = android.opengl.GLES20.glGetUniformLocation(this.mProgram, "gamma");
        this.mTexUnitLoc = android.opengl.GLES20.glGetUniformLocation(this.mProgram, "texUnit");
        android.opengl.GLES20.glUseProgram(this.mProgram);
        android.opengl.GLES20.glUniform1i(this.mTexUnitLoc, 0);
        android.opengl.GLES20.glUseProgram(0);
        return true;
    }

    private void destroyGLShaders() {
        android.opengl.GLES20.glDeleteProgram(this.mProgram);
        checkGlErrors("glDeleteProgram");
    }

    private boolean initGLBuffers() {
        setQuad(this.mVertexBuffer, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, this.mDisplayWidth, this.mDisplayHeight);
        android.opengl.GLES20.glBindTexture(36197, this.mTexNames[0]);
        android.opengl.GLES20.glTexParameteri(36197, 10240, 9728);
        android.opengl.GLES20.glTexParameteri(36197, 10241, 9728);
        android.opengl.GLES20.glTexParameteri(36197, 10242, 33071);
        android.opengl.GLES20.glTexParameteri(36197, 10243, 33071);
        android.opengl.GLES20.glBindTexture(36197, 0);
        android.opengl.GLES20.glGenBuffers(2, this.mGLBuffers, 0);
        android.opengl.GLES20.glBindBuffer(34962, this.mGLBuffers[0]);
        android.opengl.GLES20.glBufferData(34962, this.mVertexBuffer.capacity() * 4, this.mVertexBuffer, 35044);
        android.opengl.GLES20.glBindBuffer(34962, this.mGLBuffers[1]);
        android.opengl.GLES20.glBufferData(34962, this.mTexCoordBuffer.capacity() * 4, this.mTexCoordBuffer, 35044);
        android.opengl.GLES20.glBindBuffer(34962, 0);
        return true;
    }

    private void destroyGLBuffers() {
        android.opengl.GLES20.glDeleteBuffers(2, this.mGLBuffers, 0);
        checkGlErrors("glDeleteBuffers");
    }

    private static void setQuad(java.nio.FloatBuffer floatBuffer, float f, float f2, float f3, float f4) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "setQuad: x=" + f + ", y=" + f2 + ", w=" + f3 + ", h=" + f4);
        }
        floatBuffer.put(0, f);
        floatBuffer.put(1, f2);
        floatBuffer.put(2, f);
        float f5 = f4 + f2;
        floatBuffer.put(3, f5);
        float f6 = f + f3;
        floatBuffer.put(4, f6);
        floatBuffer.put(5, f5);
        floatBuffer.put(6, f6);
        floatBuffer.put(7, f2);
    }

    public void dismissResources() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "dismissResources");
        }
        if (this.mCreatedResources) {
            attachEglContext();
            try {
                destroyScreenshotTexture();
                destroyGLShaders();
                destroyGLBuffers();
                destroyEglSurface();
                detachEglContext();
                android.opengl.GLES20.glFlush();
                this.mCreatedResources = false;
            } catch (java.lang.Throwable th) {
                detachEglContext();
                throw th;
            }
        }
    }

    public void dismiss() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "dismiss");
        }
        if (this.mPrepared) {
            dismissResources();
            destroySurface();
            this.mPrepared = false;
        }
    }

    public void destroy() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "destroy");
        }
        if (this.mPrepared) {
            if (this.mCreatedResources) {
                attachEglContext();
                try {
                    destroyScreenshotTexture();
                    destroyGLShaders();
                    destroyGLBuffers();
                    destroyEglSurface();
                } finally {
                    detachEglContext();
                }
            }
            destroyEglContext();
            destroySurface();
        }
    }

    public boolean draw(float f) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "drawFrame: level=" + f);
        }
        if (!this.mPrepared) {
            return false;
        }
        if (this.mMode == 2) {
            return showSurface(1.0f - f);
        }
        if (!attachEglContext()) {
            return false;
        }
        try {
            android.opengl.GLES20.glClearColor(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f);
            android.opengl.GLES20.glClear(16384);
            double d = 1.0f - f;
            double cos = java.lang.Math.cos(3.141592653589793d * d);
            drawFaded(((float) (-java.lang.Math.pow(d, 2.0d))) + 1.0f, 1.0f / ((float) ((((((cos < 0.0d ? -1.0d : 1.0d) * 0.5d) * java.lang.Math.pow(cos, 2.0d)) + 0.5d) * 0.9d) + 0.1d)));
            if (checkGlErrors("drawFrame")) {
                return false;
            }
            android.opengl.EGL14.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
            detachEglContext();
            return showSurface(1.0f);
        } finally {
            detachEglContext();
        }
    }

    private void drawFaded(float f, float f2) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "drawFaded: opacity=" + f + ", gamma=" + f2);
        }
        android.opengl.GLES20.glUseProgram(this.mProgram);
        android.opengl.GLES20.glUniformMatrix4fv(this.mProjMatrixLoc, 1, false, this.mProjMatrix, 0);
        android.opengl.GLES20.glUniformMatrix4fv(this.mTexMatrixLoc, 1, false, this.mTexMatrix, 0);
        android.opengl.GLES20.glUniform1f(this.mOpacityLoc, f);
        android.opengl.GLES20.glUniform1f(this.mGammaLoc, f2);
        android.opengl.GLES20.glActiveTexture(33984);
        android.opengl.GLES20.glBindTexture(36197, this.mTexNames[0]);
        android.opengl.GLES20.glBindBuffer(34962, this.mGLBuffers[0]);
        android.opengl.GLES20.glEnableVertexAttribArray(this.mVertexLoc);
        android.opengl.GLES20.glVertexAttribPointer(this.mVertexLoc, 2, 5126, false, 0, 0);
        android.opengl.GLES20.glBindBuffer(34962, this.mGLBuffers[1]);
        android.opengl.GLES20.glEnableVertexAttribArray(this.mTexCoordLoc);
        android.opengl.GLES20.glVertexAttribPointer(this.mTexCoordLoc, 2, 5126, false, 0, 0);
        android.opengl.GLES20.glDrawArrays(6, 0, 4);
        android.opengl.GLES20.glBindTexture(36197, 0);
        android.opengl.GLES20.glBindBuffer(34962, 0);
    }

    private void ortho(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f2 - f;
        this.mProjMatrix[0] = 2.0f / f7;
        this.mProjMatrix[1] = 0.0f;
        this.mProjMatrix[2] = 0.0f;
        this.mProjMatrix[3] = 0.0f;
        this.mProjMatrix[4] = 0.0f;
        float f8 = f4 - f3;
        this.mProjMatrix[5] = 2.0f / f8;
        this.mProjMatrix[6] = 0.0f;
        this.mProjMatrix[7] = 0.0f;
        this.mProjMatrix[8] = 0.0f;
        this.mProjMatrix[9] = 0.0f;
        float f9 = f6 - f5;
        this.mProjMatrix[10] = (-2.0f) / f9;
        this.mProjMatrix[11] = 0.0f;
        this.mProjMatrix[12] = (-(f2 + f)) / f7;
        this.mProjMatrix[13] = (-(f4 + f3)) / f8;
        this.mProjMatrix[14] = (-(f6 + f5)) / f9;
        this.mProjMatrix[15] = 1.0f;
    }

    private boolean setScreenshotTextureAndSetViewport(android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, int i) {
        if (!attachEglContext()) {
            return false;
        }
        try {
            if (!this.mTexNamesGenerated) {
                android.opengl.GLES20.glGenTextures(1, this.mTexNames, 0);
                if (checkGlErrors("glGenTextures")) {
                    detachEglContext();
                    return false;
                }
                this.mTexNamesGenerated = true;
            }
            android.graphics.SurfaceTexture surfaceTexture = new android.graphics.SurfaceTexture(this.mTexNames[0]);
            android.view.Surface surface = new android.view.Surface(surfaceTexture);
            try {
                surface.attachAndQueueBufferWithColorSpace(screenshotHardwareBuffer.getHardwareBuffer(), screenshotHardwareBuffer.getColorSpace());
                surfaceTexture.updateTexImage();
                surfaceTexture.getTransformMatrix(this.mTexMatrix);
                surface.release();
                surfaceTexture.release();
                int i2 = 2;
                if (i != 1) {
                    i2 = i == 2 ? 4 : i == 3 ? 6 : 0;
                }
                this.mTexCoordBuffer.put(i2, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                this.mTexCoordBuffer.put(i2 + 1, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                this.mTexCoordBuffer.put((i2 + 2) % 8, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                this.mTexCoordBuffer.put((i2 + 3) % 8, 1.0f);
                this.mTexCoordBuffer.put((i2 + 4) % 8, 1.0f);
                this.mTexCoordBuffer.put((i2 + 5) % 8, 1.0f);
                this.mTexCoordBuffer.put((i2 + 6) % 8, 1.0f);
                this.mTexCoordBuffer.put((i2 + 7) % 8, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                android.opengl.GLES20.glViewport(0, 0, this.mDisplayWidth, this.mDisplayHeight);
                ortho(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, this.mDisplayWidth, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, this.mDisplayHeight, -1.0f, 1.0f);
                detachEglContext();
                return true;
            } catch (java.lang.Throwable th) {
                surface.release();
                surfaceTexture.release();
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            detachEglContext();
            throw th2;
        }
    }

    private void destroyScreenshotTexture() {
        if (this.mTexNamesGenerated) {
            this.mTexNamesGenerated = false;
            android.opengl.GLES20.glDeleteTextures(1, this.mTexNames, 0);
            checkGlErrors("glDeleteTextures");
        }
    }

    private android.window.ScreenCapture.ScreenshotHardwareBuffer captureScreen() {
        android.window.ScreenCapture.ScreenshotHardwareBuffer systemScreenshot = this.mDisplayManagerInternal.systemScreenshot(this.mDisplayId);
        if (systemScreenshot == null) {
            android.util.Slog.e(TAG, "Failed to take screenshot. Buffer is null");
            return null;
        }
        return systemScreenshot;
    }

    private boolean createSurfaceControl(boolean z) {
        if (this.mSurfaceControl != null) {
            this.mTransaction.setSecure(this.mSurfaceControl, z).apply();
            return true;
        }
        try {
            android.view.SurfaceControl.Builder callsite = new android.view.SurfaceControl.Builder().setName(TAG).setSecure(z).setCallsite("ColorFade.createSurface");
            if (this.mMode == 2) {
                callsite.setColorLayer();
            } else {
                callsite.setContainerLayer();
            }
            this.mSurfaceControl = callsite.build();
            this.mTransaction.setLayerStack(this.mSurfaceControl, this.mDisplayLayerStack);
            this.mTransaction.setWindowCrop(this.mSurfaceControl, this.mDisplayWidth, this.mDisplayHeight);
            this.mSurfaceLayout = new com.android.server.display.ColorFade.NaturalSurfaceLayout(this.mDisplayManagerInternal, this.mDisplayId, this.mSurfaceControl);
            this.mSurfaceLayout.onDisplayTransaction(this.mTransaction);
            this.mTransaction.apply();
            if (this.mMode != 2) {
                this.mBLASTSurfaceControl = new android.view.SurfaceControl.Builder().setName("ColorFade BLAST").setParent(this.mSurfaceControl).setHidden(false).setSecure(z).setBLASTLayer().build();
                this.mBLASTBufferQueue = new android.graphics.BLASTBufferQueue(TAG, this.mBLASTSurfaceControl, this.mDisplayWidth, this.mDisplayHeight, -3);
                this.mSurface = this.mBLASTBufferQueue.createSurface();
            }
            return true;
        } catch (android.view.Surface.OutOfResourcesException e) {
            android.util.Slog.e(TAG, "Unable to create surface.", e);
            return false;
        }
    }

    private boolean createEglContext(boolean z) {
        if (this.mEglDisplay == null) {
            this.mEglDisplay = android.opengl.EGL14.eglGetDisplay(0);
            if (this.mEglDisplay == android.opengl.EGL14.EGL_NO_DISPLAY) {
                logEglError("eglGetDisplay");
                return false;
            }
            int[] iArr = new int[2];
            if (!android.opengl.EGL14.eglInitialize(this.mEglDisplay, iArr, 0, iArr, 1)) {
                this.mEglDisplay = null;
                logEglError("eglInitialize");
                return false;
            }
        }
        if (this.mEglConfig == null) {
            int[] iArr2 = new int[1];
            android.opengl.EGLConfig[] eGLConfigArr = new android.opengl.EGLConfig[1];
            if (!android.opengl.EGL14.eglChooseConfig(this.mEglDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12344}, 0, eGLConfigArr, 0, 1, iArr2, 0)) {
                logEglError("eglChooseConfig");
                return false;
            }
            if (iArr2[0] > 0) {
                this.mEglConfig = eGLConfigArr[0];
            } else {
                android.util.Slog.e(TAG, "no valid config found");
                return false;
            }
        }
        if (this.mEglContext != null && z != this.mLastWasProtectedContent) {
            android.opengl.EGL14.eglDestroyContext(this.mEglDisplay, this.mEglContext);
            this.mEglContext = null;
        }
        if (this.mEglContext == null) {
            int[] iArr3 = {12440, 2, 12344, 12344, 12344};
            if (z) {
                iArr3[2] = EGL_PROTECTED_CONTENT_EXT;
                iArr3[3] = 1;
            }
            this.mEglContext = android.opengl.EGL14.eglCreateContext(this.mEglDisplay, this.mEglConfig, android.opengl.EGL14.EGL_NO_CONTEXT, iArr3, 0);
            if (this.mEglContext == null) {
                logEglError("eglCreateContext");
                return false;
            }
        }
        return true;
    }

    private boolean createEglSurface(boolean z, boolean z2) {
        boolean z3 = (z == this.mLastWasProtectedContent && z2 == this.mLastWasWideColor) ? false : true;
        if (this.mEglSurface != null && z3) {
            android.opengl.EGL14.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
            this.mEglSurface = null;
        }
        if (this.mEglSurface == null) {
            int[] iArr = new int[5];
            iArr[0] = 12344;
            iArr[1] = 12344;
            int i = 2;
            iArr[2] = 12344;
            iArr[3] = 12344;
            iArr[4] = 12344;
            if (!z2) {
                i = 0;
            } else {
                iArr[0] = EGL_GL_COLORSPACE_KHR;
                iArr[1] = EGL_GL_COLORSPACE_DISPLAY_P3_PASSTHROUGH_EXT;
            }
            if (z) {
                iArr[i] = EGL_PROTECTED_CONTENT_EXT;
                iArr[i + 1] = 1;
            }
            this.mEglSurface = android.opengl.EGL14.eglCreateWindowSurface(this.mEglDisplay, this.mEglConfig, this.mSurface, iArr, 0);
            if (this.mEglSurface == null) {
                logEglError("eglCreateWindowSurface");
                return false;
            }
        }
        return true;
    }

    private void destroyEglSurface() {
        if (this.mEglSurface != null) {
            if (!android.opengl.EGL14.eglDestroySurface(this.mEglDisplay, this.mEglSurface)) {
                logEglError("eglDestroySurface");
            }
            this.mEglSurface = null;
        }
    }

    private void destroySurface() {
        if (this.mSurfaceControl != null) {
            this.mSurfaceLayout.dispose();
            this.mSurfaceLayout = null;
            this.mTransaction.remove(this.mSurfaceControl).apply();
            if (this.mSurface != null) {
                this.mSurface.release();
                this.mSurface = null;
            }
            if (this.mBLASTSurfaceControl != null) {
                this.mBLASTSurfaceControl.release();
                this.mBLASTSurfaceControl = null;
                this.mBLASTBufferQueue.destroy();
                this.mBLASTBufferQueue = null;
            }
            this.mSurfaceControl = null;
            this.mSurfaceVisible = false;
            this.mSurfaceAlpha = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
    }

    private boolean showSurface(float f) {
        if (!this.mSurfaceVisible || this.mSurfaceAlpha != f) {
            this.mTransaction.setLayer(this.mSurfaceControl, 1073741825).setAlpha(this.mSurfaceControl, f).show(this.mSurfaceControl).apply();
            this.mSurfaceVisible = true;
            this.mSurfaceAlpha = f;
        }
        return true;
    }

    private boolean attachEglContext() {
        if (this.mEglSurface == null) {
            return false;
        }
        if (!android.opengl.EGL14.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
            logEglError("eglMakeCurrent");
            return false;
        }
        return true;
    }

    private void detachEglContext() {
        if (this.mEglDisplay != null) {
            android.opengl.EGL14.eglMakeCurrent(this.mEglDisplay, android.opengl.EGL14.EGL_NO_SURFACE, android.opengl.EGL14.EGL_NO_SURFACE, android.opengl.EGL14.EGL_NO_CONTEXT);
        }
    }

    private void destroyEglContext() {
        if (this.mEglDisplay != null && this.mEglContext != null) {
            android.opengl.EGL14.eglDestroyContext(this.mEglDisplay, this.mEglContext);
        }
    }

    private static java.nio.FloatBuffer createNativeFloatBuffer(int i) {
        java.nio.ByteBuffer allocateDirect = java.nio.ByteBuffer.allocateDirect(i * 4);
        allocateDirect.order(java.nio.ByteOrder.nativeOrder());
        return allocateDirect.asFloatBuffer();
    }

    private static void logEglError(java.lang.String str) {
        android.util.Slog.e(TAG, str + " failed: error " + android.opengl.EGL14.eglGetError(), new java.lang.Throwable());
    }

    private static boolean checkGlErrors(java.lang.String str) {
        return checkGlErrors(str, true);
    }

    private static boolean checkGlErrors(java.lang.String str, boolean z) {
        boolean z2 = false;
        while (true) {
            int glGetError = android.opengl.GLES20.glGetError();
            if (glGetError != 0) {
                if (z) {
                    android.util.Slog.e(TAG, str + " failed: error " + glGetError, new java.lang.Throwable());
                }
                z2 = true;
            } else {
                return z2;
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Color Fade State:");
        printWriter.println("  mPrepared=" + this.mPrepared);
        printWriter.println("  mMode=" + this.mMode);
        printWriter.println("  mDisplayLayerStack=" + this.mDisplayLayerStack);
        printWriter.println("  mDisplayWidth=" + this.mDisplayWidth);
        printWriter.println("  mDisplayHeight=" + this.mDisplayHeight);
        printWriter.println("  mSurfaceVisible=" + this.mSurfaceVisible);
        printWriter.println("  mSurfaceAlpha=" + this.mSurfaceAlpha);
    }

    private static final class NaturalSurfaceLayout implements android.hardware.display.DisplayManagerInternal.DisplayTransactionListener {
        private final int mDisplayId;
        private final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
        private android.view.SurfaceControl mSurfaceControl;

        public NaturalSurfaceLayout(android.hardware.display.DisplayManagerInternal displayManagerInternal, int i, android.view.SurfaceControl surfaceControl) {
            this.mDisplayManagerInternal = displayManagerInternal;
            this.mDisplayId = i;
            this.mSurfaceControl = surfaceControl;
            this.mDisplayManagerInternal.registerDisplayTransactionListener(this);
        }

        public void dispose() {
            synchronized (this) {
                this.mSurfaceControl = null;
            }
            this.mDisplayManagerInternal.unregisterDisplayTransactionListener(this);
        }

        public void onDisplayTransaction(android.view.SurfaceControl.Transaction transaction) {
            synchronized (this) {
                try {
                    if (this.mSurfaceControl == null) {
                        return;
                    }
                    android.view.DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(this.mDisplayId);
                    if (displayInfo == null) {
                        return;
                    }
                    switch (displayInfo.rotation) {
                        case 0:
                            transaction.setPosition(this.mSurfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                            transaction.setMatrix(this.mSurfaceControl, 1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f);
                            break;
                        case 1:
                            transaction.setPosition(this.mSurfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, displayInfo.logicalHeight);
                            transaction.setMatrix(this.mSurfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, -1.0f, 1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                            break;
                        case 2:
                            transaction.setPosition(this.mSurfaceControl, displayInfo.logicalWidth, displayInfo.logicalHeight);
                            transaction.setMatrix(this.mSurfaceControl, -1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, -1.0f);
                            break;
                        case 3:
                            transaction.setPosition(this.mSurfaceControl, displayInfo.logicalWidth, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                            transaction.setMatrix(this.mSurfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f, -1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                            break;
                    }
                } finally {
                }
            }
        }
    }
}
