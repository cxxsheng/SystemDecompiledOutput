package android.opengl;

/* loaded from: classes3.dex */
public class GLSurfaceView extends android.view.SurfaceView implements android.view.SurfaceHolder.Callback2 {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean LOG_ATTACH_DETACH = false;
    private static final boolean LOG_EGL = false;
    private static final boolean LOG_PAUSE_RESUME = false;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_RENDERER_DRAW_FRAME = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final java.lang.String TAG = "GLSurfaceView";
    private static final android.opengl.GLSurfaceView.GLThreadManager sGLThreadManager = new android.opengl.GLSurfaceView.GLThreadManager();
    private int mDebugFlags;
    private boolean mDetached;
    private android.opengl.GLSurfaceView.EGLConfigChooser mEGLConfigChooser;
    private int mEGLContextClientVersion;
    private android.opengl.GLSurfaceView.EGLContextFactory mEGLContextFactory;
    private android.opengl.GLSurfaceView.EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    private android.opengl.GLSurfaceView.GLThread mGLThread;
    private android.opengl.GLSurfaceView.GLWrapper mGLWrapper;
    private boolean mPreserveEGLContextOnPause;
    private android.opengl.GLSurfaceView.Renderer mRenderer;
    private final java.lang.ref.WeakReference<android.opengl.GLSurfaceView> mThisWeakRef;

    public interface EGLConfigChooser {
        javax.microedition.khronos.egl.EGLConfig chooseConfig(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay);
    }

    public interface EGLContextFactory {
        javax.microedition.khronos.egl.EGLContext createContext(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig);

        void destroyContext(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext);
    }

    public interface EGLWindowSurfaceFactory {
        javax.microedition.khronos.egl.EGLSurface createWindowSurface(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, java.lang.Object obj);

        void destroySurface(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface);
    }

    public interface GLWrapper {
        javax.microedition.khronos.opengles.GL wrap(javax.microedition.khronos.opengles.GL gl);
    }

    public interface Renderer {
        void onDrawFrame(javax.microedition.khronos.opengles.GL10 gl10);

        void onSurfaceChanged(javax.microedition.khronos.opengles.GL10 gl10, int i, int i2);

        void onSurfaceCreated(javax.microedition.khronos.opengles.GL10 gl10, javax.microedition.khronos.egl.EGLConfig eGLConfig);
    }

    public GLSurfaceView(android.content.Context context) {
        super(context);
        this.mThisWeakRef = new java.lang.ref.WeakReference<>(this);
        init();
    }

    public GLSurfaceView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mThisWeakRef = new java.lang.ref.WeakReference<>(this);
        init();
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mGLThread != null) {
                this.mGLThread.requestExitAndWait();
            }
        } finally {
            super.finalize();
        }
    }

    private void init() {
        getHolder().addCallback(this);
    }

    public void setGLWrapper(android.opengl.GLSurfaceView.GLWrapper gLWrapper) {
        this.mGLWrapper = gLWrapper;
    }

    public void setDebugFlags(int i) {
        this.mDebugFlags = i;
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    public void setPreserveEGLContextOnPause(boolean z) {
        this.mPreserveEGLContextOnPause = z;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.mPreserveEGLContextOnPause;
    }

    public void setRenderer(android.opengl.GLSurfaceView.Renderer renderer) {
        checkRenderThreadState();
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new android.opengl.GLSurfaceView.SimpleEGLConfigChooser(true);
        }
        byte b = 0;
        if (this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new android.opengl.GLSurfaceView.DefaultContextFactory();
        }
        if (this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new android.opengl.GLSurfaceView.DefaultWindowSurfaceFactory();
        }
        this.mRenderer = renderer;
        this.mGLThread = new android.opengl.GLSurfaceView.GLThread(this.mThisWeakRef);
        this.mGLThread.start();
    }

    public void setEGLContextFactory(android.opengl.GLSurfaceView.EGLContextFactory eGLContextFactory) {
        checkRenderThreadState();
        this.mEGLContextFactory = eGLContextFactory;
    }

    public void setEGLWindowSurfaceFactory(android.opengl.GLSurfaceView.EGLWindowSurfaceFactory eGLWindowSurfaceFactory) {
        checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = eGLWindowSurfaceFactory;
    }

    public void setEGLConfigChooser(android.opengl.GLSurfaceView.EGLConfigChooser eGLConfigChooser) {
        checkRenderThreadState();
        this.mEGLConfigChooser = eGLConfigChooser;
    }

    public void setEGLConfigChooser(boolean z) {
        setEGLConfigChooser(new android.opengl.GLSurfaceView.SimpleEGLConfigChooser(z));
    }

    public void setEGLConfigChooser(int i, int i2, int i3, int i4, int i5, int i6) {
        setEGLConfigChooser(new android.opengl.GLSurfaceView.ComponentSizeChooser(i, i2, i3, i4, i5, i6));
    }

    public void setEGLContextClientVersion(int i) {
        checkRenderThreadState();
        this.mEGLContextClientVersion = i;
    }

    public void setRenderMode(int i) {
        this.mGLThread.setRenderMode(i);
    }

    public int getRenderMode() {
        return this.mGLThread.getRenderMode();
    }

    public void requestRender() {
        this.mGLThread.requestRender();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(android.view.SurfaceHolder surfaceHolder) {
        this.mGLThread.surfaceCreated();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(android.view.SurfaceHolder surfaceHolder) {
        this.mGLThread.surfaceDestroyed();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(android.view.SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.mGLThread.onWindowResize(i2, i3);
    }

    @Override // android.view.SurfaceHolder.Callback2
    public void surfaceRedrawNeededAsync(android.view.SurfaceHolder surfaceHolder, java.lang.Runnable runnable) {
        if (this.mGLThread != null) {
            this.mGLThread.requestRenderAndNotify(runnable);
        }
    }

    @Override // android.view.SurfaceHolder.Callback2
    @java.lang.Deprecated
    public void surfaceRedrawNeeded(android.view.SurfaceHolder surfaceHolder) {
    }

    public void onPause() {
        this.mGLThread.onPause();
    }

    public void onResume() {
        this.mGLThread.onResume();
    }

    public void queueEvent(java.lang.Runnable runnable) {
        this.mGLThread.queueEvent(runnable);
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onAttachedToWindow() {
        int i;
        super.onAttachedToWindow();
        if (this.mDetached && this.mRenderer != null) {
            if (this.mGLThread == null) {
                i = 1;
            } else {
                i = this.mGLThread.getRenderMode();
            }
            this.mGLThread = new android.opengl.GLSurfaceView.GLThread(this.mThisWeakRef);
            if (i != 1) {
                this.mGLThread.setRenderMode(i);
            }
            this.mGLThread.start();
        }
        this.mDetached = false;
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() {
        if (this.mGLThread != null) {
            this.mGLThread.requestExitAndWait();
        }
        this.mDetached = true;
        super.onDetachedFromWindow();
    }

    private class DefaultContextFactory implements android.opengl.GLSurfaceView.EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION;

        private DefaultContextFactory() {
            this.EGL_CONTEXT_CLIENT_VERSION = 12440;
        }

        @Override // android.opengl.GLSurfaceView.EGLContextFactory
        public javax.microedition.khronos.egl.EGLContext createContext(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig) {
            int[] iArr = {this.EGL_CONTEXT_CLIENT_VERSION, android.opengl.GLSurfaceView.this.mEGLContextClientVersion, 12344};
            javax.microedition.khronos.egl.EGLContext eGLContext = javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT;
            if (android.opengl.GLSurfaceView.this.mEGLContextClientVersion == 0) {
                iArr = null;
            }
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        }

        @Override // android.opengl.GLSurfaceView.EGLContextFactory
        public void destroyContext(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLContext eGLContext) {
            if (!egl10.eglDestroyContext(eGLDisplay, eGLContext)) {
                android.util.Log.e("DefaultContextFactory", "display:" + eGLDisplay + " context: " + eGLContext);
                android.opengl.GLSurfaceView.EglHelper.throwEglException("eglDestroyContex", egl10.eglGetError());
            }
        }
    }

    private static class DefaultWindowSurfaceFactory implements android.opengl.GLSurfaceView.EGLWindowSurfaceFactory {
        private DefaultWindowSurfaceFactory() {
        }

        @Override // android.opengl.GLSurfaceView.EGLWindowSurfaceFactory
        public javax.microedition.khronos.egl.EGLSurface createWindowSurface(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, java.lang.Object obj) {
            try {
                return egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, null);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.e(android.opengl.GLSurfaceView.TAG, "eglCreateWindowSurface", e);
                return null;
            }
        }

        @Override // android.opengl.GLSurfaceView.EGLWindowSurfaceFactory
        public void destroySurface(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLSurface eGLSurface) {
            egl10.eglDestroySurface(eGLDisplay, eGLSurface);
        }
    }

    private abstract class BaseConfigChooser implements android.opengl.GLSurfaceView.EGLConfigChooser {
        protected int[] mConfigSpec;

        abstract javax.microedition.khronos.egl.EGLConfig chooseConfig(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr);

        public BaseConfigChooser(int[] iArr) {
            this.mConfigSpec = filterConfigSpec(iArr);
        }

        @Override // android.opengl.GLSurfaceView.EGLConfigChooser
        public javax.microedition.khronos.egl.EGLConfig chooseConfig(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, null, 0, iArr)) {
                throw new java.lang.IllegalArgumentException("eglChooseConfig failed");
            }
            int i = iArr[0];
            if (i <= 0) {
                throw new java.lang.IllegalArgumentException("No configs match configSpec");
            }
            javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr = new javax.microedition.khronos.egl.EGLConfig[i];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, eGLConfigArr, i, iArr)) {
                throw new java.lang.IllegalArgumentException("eglChooseConfig#2 failed");
            }
            javax.microedition.khronos.egl.EGLConfig chooseConfig = chooseConfig(egl10, eGLDisplay, eGLConfigArr);
            if (chooseConfig == null) {
                throw new java.lang.IllegalArgumentException("No config chosen");
            }
            return chooseConfig;
        }

        private int[] filterConfigSpec(int[] iArr) {
            if (android.opengl.GLSurfaceView.this.mEGLContextClientVersion != 2 && android.opengl.GLSurfaceView.this.mEGLContextClientVersion != 3) {
                return iArr;
            }
            int length = iArr.length;
            int[] iArr2 = new int[length + 2];
            int i = length - 1;
            java.lang.System.arraycopy(iArr, 0, iArr2, 0, i);
            iArr2[i] = 12352;
            if (android.opengl.GLSurfaceView.this.mEGLContextClientVersion == 2) {
                iArr2[length] = 4;
            } else {
                iArr2[length] = 64;
            }
            iArr2[length + 1] = 12344;
            return iArr2;
        }
    }

    private class ComponentSizeChooser extends android.opengl.GLSurfaceView.BaseConfigChooser {
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue;

        public ComponentSizeChooser(int i, int i2, int i3, int i4, int i5, int i6) {
            super(new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12344});
            this.mValue = new int[1];
            this.mRedSize = i;
            this.mGreenSize = i2;
            this.mBlueSize = i3;
            this.mAlphaSize = i4;
            this.mDepthSize = i5;
            this.mStencilSize = i6;
        }

        @Override // android.opengl.GLSurfaceView.BaseConfigChooser
        public javax.microedition.khronos.egl.EGLConfig chooseConfig(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr) {
            for (javax.microedition.khronos.egl.EGLConfig eGLConfig : eGLConfigArr) {
                int findConfigAttrib = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12325, 0);
                int findConfigAttrib2 = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12326, 0);
                if (findConfigAttrib >= this.mDepthSize && findConfigAttrib2 >= this.mStencilSize) {
                    int findConfigAttrib3 = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12324, 0);
                    int findConfigAttrib4 = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12323, 0);
                    int findConfigAttrib5 = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12322, 0);
                    int findConfigAttrib6 = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12321, 0);
                    if (findConfigAttrib3 == this.mRedSize && findConfigAttrib4 == this.mGreenSize && findConfigAttrib5 == this.mBlueSize && findConfigAttrib6 == this.mAlphaSize) {
                        return eGLConfig;
                    }
                }
            }
            return null;
        }

        private int findConfigAttrib(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int i, int i2) {
            if (egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, this.mValue)) {
                return this.mValue[0];
            }
            return i2;
        }
    }

    private class SimpleEGLConfigChooser extends android.opengl.GLSurfaceView.ComponentSizeChooser {
        public SimpleEGLConfigChooser(boolean z) {
            super(8, 8, 8, 0, z ? 16 : 0, 0);
        }
    }

    private static class EglHelper {
        javax.microedition.khronos.egl.EGL10 mEgl;
        javax.microedition.khronos.egl.EGLConfig mEglConfig;
        javax.microedition.khronos.egl.EGLContext mEglContext;
        javax.microedition.khronos.egl.EGLDisplay mEglDisplay;
        javax.microedition.khronos.egl.EGLSurface mEglSurface;
        private java.lang.ref.WeakReference<android.opengl.GLSurfaceView> mGLSurfaceViewWeakRef;

        public EglHelper(java.lang.ref.WeakReference<android.opengl.GLSurfaceView> weakReference) {
            this.mGLSurfaceViewWeakRef = weakReference;
        }

        public void start() {
            this.mEgl = (javax.microedition.khronos.egl.EGL10) javax.microedition.khronos.egl.EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(javax.microedition.khronos.egl.EGL10.EGL_DEFAULT_DISPLAY);
            if (this.mEglDisplay == javax.microedition.khronos.egl.EGL10.EGL_NO_DISPLAY) {
                throw new java.lang.RuntimeException("eglGetDisplay failed");
            }
            if (!this.mEgl.eglInitialize(this.mEglDisplay, new int[2])) {
                throw new java.lang.RuntimeException("eglInitialize failed");
            }
            android.opengl.GLSurfaceView gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
            if (gLSurfaceView == null) {
                this.mEglConfig = null;
                this.mEglContext = null;
            } else {
                this.mEglConfig = gLSurfaceView.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
                this.mEglContext = gLSurfaceView.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
            }
            if (this.mEglContext == null || this.mEglContext == javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT) {
                this.mEglContext = null;
                throwEglException("createContext");
            }
            this.mEglSurface = null;
        }

        public boolean createSurface() {
            if (this.mEgl == null) {
                throw new java.lang.RuntimeException("egl not initialized");
            }
            if (this.mEglDisplay == null) {
                throw new java.lang.RuntimeException("eglDisplay not initialized");
            }
            if (this.mEglConfig == null) {
                throw new java.lang.RuntimeException("mEglConfig not initialized");
            }
            destroySurfaceImp();
            android.opengl.GLSurfaceView gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
            if (gLSurfaceView != null) {
                this.mEglSurface = gLSurfaceView.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, gLSurfaceView.getHolder());
            } else {
                this.mEglSurface = null;
            }
            if (this.mEglSurface == null || this.mEglSurface == javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE) {
                if (this.mEgl.eglGetError() == 12299) {
                    android.util.Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                }
                return false;
            }
            if (!this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
                logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.mEgl.eglGetError());
                return false;
            }
            return true;
        }

        javax.microedition.khronos.opengles.GL createGL() {
            android.opengl.GLSurfaceView.LogWriter logWriter;
            javax.microedition.khronos.opengles.GL gl = this.mEglContext.getGL();
            android.opengl.GLSurfaceView gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
            if (gLSurfaceView != null) {
                if (gLSurfaceView.mGLWrapper != null) {
                    gl = gLSurfaceView.mGLWrapper.wrap(gl);
                }
                if ((gLSurfaceView.mDebugFlags & 3) != 0) {
                    int i = (gLSurfaceView.mDebugFlags & 1) == 0 ? 0 : 1;
                    if ((gLSurfaceView.mDebugFlags & 2) == 0) {
                        logWriter = null;
                    } else {
                        logWriter = new android.opengl.GLSurfaceView.LogWriter();
                    }
                    return android.opengl.GLDebugHelper.wrap(gl, i, logWriter);
                }
                return gl;
            }
            return gl;
        }

        public int swap() {
            if (!this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
                return this.mEgl.eglGetError();
            }
            return 12288;
        }

        public void destroySurface() {
            destroySurfaceImp();
        }

        private void destroySurfaceImp() {
            if (this.mEglSurface != null && this.mEglSurface != javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE, javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE, javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT);
                android.opengl.GLSurfaceView gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
                if (gLSurfaceView != null) {
                    gLSurfaceView.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
                }
                this.mEglSurface = null;
            }
        }

        public void finish() {
            if (this.mEglContext != null) {
                android.opengl.GLSurfaceView gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
                if (gLSurfaceView != null) {
                    gLSurfaceView.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }
                this.mEglContext = null;
            }
            if (this.mEglDisplay != null) {
                this.mEgl.eglTerminate(this.mEglDisplay);
                this.mEglDisplay = null;
            }
        }

        private void throwEglException(java.lang.String str) {
            throwEglException(str, this.mEgl.eglGetError());
        }

        public static void throwEglException(java.lang.String str, int i) {
            throw new java.lang.RuntimeException(formatEglError(str, i));
        }

        public static void logEglErrorAsWarning(java.lang.String str, java.lang.String str2, int i) {
            android.util.Log.w(str, formatEglError(str2, i));
        }

        public static java.lang.String formatEglError(java.lang.String str, int i) {
            return str + " failed: " + android.opengl.EGLLogWrapper.getErrorString(i);
        }
    }

    static class GLThread extends java.lang.Thread {
        private android.opengl.GLSurfaceView.EglHelper mEglHelper;
        private boolean mExited;
        private boolean mFinishedCreatingEglSurface;
        private java.lang.ref.WeakReference<android.opengl.GLSurfaceView> mGLSurfaceViewWeakRef;
        private boolean mHasSurface;
        private boolean mHaveEglContext;
        private boolean mHaveEglSurface;
        private boolean mPaused;
        private boolean mRenderComplete;
        private boolean mRequestPaused;
        private boolean mShouldExit;
        private boolean mShouldReleaseEglContext;
        private boolean mSurfaceIsBad;
        private boolean mWaitingForSurface;
        private java.util.ArrayList<java.lang.Runnable> mEventQueue = new java.util.ArrayList<>();
        private boolean mSizeChanged = true;
        private java.lang.Runnable mFinishDrawingRunnable = null;
        private int mWidth = 0;
        private int mHeight = 0;
        private boolean mRequestRender = true;
        private int mRenderMode = 1;
        private boolean mWantRenderNotification = false;

        GLThread(java.lang.ref.WeakReference<android.opengl.GLSurfaceView> weakReference) {
            this.mGLSurfaceViewWeakRef = weakReference;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            setName("GLThread " + getId());
            try {
                guardedRun();
            } catch (java.lang.InterruptedException e) {
            } catch (java.lang.Throwable th) {
                android.opengl.GLSurfaceView.sGLThreadManager.threadExiting(this);
                throw th;
            }
            android.opengl.GLSurfaceView.sGLThreadManager.threadExiting(this);
        }

        private void stopEglSurfaceLocked() {
            if (this.mHaveEglSurface) {
                this.mHaveEglSurface = false;
                this.mEglHelper.destroySurface();
            }
        }

        private void stopEglContextLocked() {
            if (this.mHaveEglContext) {
                this.mEglHelper.finish();
                this.mHaveEglContext = false;
                android.opengl.GLSurfaceView.sGLThreadManager.releaseEglContextLocked(this);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:117:0x025b  */
        /* JADX WARN: Removed duplicated region for block: B:121:0x025e  */
        /* JADX WARN: Removed duplicated region for block: B:205:0x0289 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void guardedRun() throws java.lang.InterruptedException {
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            this.mEglHelper = new android.opengl.GLSurfaceView.EglHelper(this.mGLSurfaceViewWeakRef);
            this.mHaveEglContext = false;
            this.mHaveEglSurface = false;
            this.mWantRenderNotification = false;
            boolean z6 = false;
            boolean z7 = false;
            boolean z8 = false;
            boolean z9 = false;
            boolean z10 = false;
            boolean z11 = false;
            boolean z12 = false;
            boolean z13 = false;
            int i = 0;
            int i2 = 0;
            java.lang.Runnable runnable = null;
            javax.microedition.khronos.opengles.GL10 gl10 = null;
            java.lang.Runnable runnable2 = null;
            while (true) {
                try {
                    synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                        while (!this.mShouldExit) {
                            if (this.mEventQueue.isEmpty()) {
                                if (this.mPaused != this.mRequestPaused) {
                                    z5 = this.mRequestPaused;
                                    this.mPaused = this.mRequestPaused;
                                    android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                                } else {
                                    z5 = false;
                                }
                                if (this.mShouldReleaseEglContext) {
                                    stopEglSurfaceLocked();
                                    stopEglContextLocked();
                                    this.mShouldReleaseEglContext = false;
                                    z8 = true;
                                }
                                if (z6) {
                                    stopEglSurfaceLocked();
                                    stopEglContextLocked();
                                    z6 = false;
                                }
                                if (z5 && this.mHaveEglSurface) {
                                    stopEglSurfaceLocked();
                                }
                                if (z5 && this.mHaveEglContext) {
                                    android.opengl.GLSurfaceView gLSurfaceView = this.mGLSurfaceViewWeakRef.get();
                                    if (!(gLSurfaceView == null ? false : gLSurfaceView.mPreserveEGLContextOnPause)) {
                                        stopEglContextLocked();
                                    }
                                }
                                if (!this.mHasSurface && !this.mWaitingForSurface) {
                                    if (this.mHaveEglSurface) {
                                        stopEglSurfaceLocked();
                                    }
                                    this.mWaitingForSurface = true;
                                    this.mSurfaceIsBad = false;
                                    android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                                }
                                if (this.mHasSurface && this.mWaitingForSurface) {
                                    this.mWaitingForSurface = false;
                                    android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                                }
                                if (z7) {
                                    this.mWantRenderNotification = false;
                                    this.mRenderComplete = true;
                                    android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                                    z7 = false;
                                }
                                if (this.mFinishDrawingRunnable != null) {
                                    runnable = this.mFinishDrawingRunnable;
                                    this.mFinishDrawingRunnable = null;
                                }
                                if (readyToDraw()) {
                                    if (!this.mHaveEglContext) {
                                        if (z8) {
                                            z8 = false;
                                        } else {
                                            try {
                                                this.mEglHelper.start();
                                                this.mHaveEglContext = true;
                                                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                                                z9 = true;
                                            } catch (java.lang.RuntimeException e) {
                                                android.opengl.GLSurfaceView.sGLThreadManager.releaseEglContextLocked(this);
                                                throw e;
                                            }
                                        }
                                    }
                                    if (this.mHaveEglContext && !this.mHaveEglSurface) {
                                        this.mHaveEglSurface = true;
                                        z10 = true;
                                        z11 = true;
                                        z12 = true;
                                    }
                                    if (this.mHaveEglSurface) {
                                        if (this.mSizeChanged) {
                                            i = this.mWidth;
                                            i2 = this.mHeight;
                                            this.mWantRenderNotification = true;
                                            this.mSizeChanged = false;
                                            z10 = true;
                                            z12 = true;
                                        }
                                        z = false;
                                        this.mRequestRender = false;
                                        android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                                        if (this.mWantRenderNotification) {
                                            z13 = true;
                                        }
                                    }
                                } else if (runnable != null) {
                                    android.util.Log.w(android.opengl.GLSurfaceView.TAG, "Warning, !readyToDraw() but waiting for draw finished! Early reporting draw finished.");
                                    runnable.run();
                                    runnable = null;
                                }
                                android.opengl.GLSurfaceView.sGLThreadManager.wait();
                            } else {
                                runnable2 = this.mEventQueue.remove(0);
                                z = false;
                            }
                        }
                        synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                            stopEglSurfaceLocked();
                            stopEglContextLocked();
                        }
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                    }
                }
                if (runnable2 == null) {
                    if (z10) {
                        if (this.mEglHelper.createSurface()) {
                            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                                this.mFinishedCreatingEglSurface = true;
                                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                            }
                            z10 = z;
                        } else {
                            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                                this.mFinishedCreatingEglSurface = true;
                                this.mSurfaceIsBad = true;
                                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                            }
                        }
                        synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                            stopEglSurfaceLocked();
                            stopEglContextLocked();
                            throw th;
                        }
                    }
                    if (z11) {
                        gl10 = (javax.microedition.khronos.opengles.GL10) this.mEglHelper.createGL();
                        z11 = z;
                    }
                    boolean z14 = z6;
                    if (z9) {
                        android.opengl.GLSurfaceView gLSurfaceView2 = this.mGLSurfaceViewWeakRef.get();
                        if (gLSurfaceView2 != null) {
                            try {
                                z2 = z7;
                                android.os.Trace.traceBegin(8L, "onSurfaceCreated");
                                gLSurfaceView2.mRenderer.onSurfaceCreated(gl10, this.mEglHelper.mEglConfig);
                                android.os.Trace.traceEnd(8L);
                            } finally {
                            }
                        } else {
                            z2 = z7;
                        }
                        z9 = false;
                    } else {
                        z2 = z7;
                    }
                    if (z12) {
                        android.opengl.GLSurfaceView gLSurfaceView3 = this.mGLSurfaceViewWeakRef.get();
                        if (gLSurfaceView3 != null) {
                            try {
                                z3 = z8;
                                android.os.Trace.traceBegin(8L, "onSurfaceChanged");
                                gLSurfaceView3.mRenderer.onSurfaceChanged(gl10, i, i2);
                                android.os.Trace.traceEnd(8L);
                            } finally {
                            }
                        } else {
                            z3 = z8;
                        }
                        z12 = false;
                    } else {
                        z3 = z8;
                    }
                    android.opengl.GLSurfaceView gLSurfaceView4 = this.mGLSurfaceViewWeakRef.get();
                    if (gLSurfaceView4 != null) {
                        try {
                            android.os.Trace.traceBegin(8L, "onDrawFrame");
                            gLSurfaceView4.mRenderer.onDrawFrame(gl10);
                            if (runnable != null) {
                                runnable.run();
                                runnable = null;
                            }
                            android.os.Trace.traceEnd(8L);
                        } finally {
                        }
                    }
                    int swap = this.mEglHelper.swap();
                    switch (swap) {
                        case 12288:
                            z4 = true;
                            if (z13) {
                                z7 = z4;
                                z13 = false;
                            } else {
                                z7 = z2;
                            }
                            z6 = z14;
                            z8 = z3;
                            break;
                        case 12302:
                            z14 = true;
                            z4 = true;
                            if (z13) {
                            }
                            z6 = z14;
                            z8 = z3;
                            break;
                        default:
                            android.opengl.GLSurfaceView.EglHelper.logEglErrorAsWarning("GLThread", "eglSwapBuffers", swap);
                            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                                z4 = true;
                                this.mSurfaceIsBad = true;
                                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                            }
                            if (z13) {
                            }
                            z6 = z14;
                            z8 = z3;
                            break;
                    }
                } else {
                    runnable2.run();
                    runnable2 = null;
                }
            }
        }

        public boolean ableToDraw() {
            return this.mHaveEglContext && this.mHaveEglSurface && readyToDraw();
        }

        private boolean readyToDraw() {
            return !this.mPaused && this.mHasSurface && !this.mSurfaceIsBad && this.mWidth > 0 && this.mHeight > 0 && (this.mRequestRender || this.mRenderMode == 1);
        }

        public void setRenderMode(int i) {
            if (i < 0 || i > 1) {
                throw new java.lang.IllegalArgumentException("renderMode");
            }
            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                this.mRenderMode = i;
                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        public int getRenderMode() {
            int i;
            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                i = this.mRenderMode;
            }
            return i;
        }

        public void requestRender() {
            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                this.mRequestRender = true;
                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        public void requestRenderAndNotify(final java.lang.Runnable runnable) {
            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                if (java.lang.Thread.currentThread() == this) {
                    return;
                }
                this.mWantRenderNotification = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                final java.lang.Runnable runnable2 = this.mFinishDrawingRunnable;
                this.mFinishDrawingRunnable = new java.lang.Runnable() { // from class: android.opengl.GLSurfaceView$GLThread$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.opengl.GLSurfaceView.GLThread.lambda$requestRenderAndNotify$0(runnable2, runnable);
                    }
                };
                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        static /* synthetic */ void lambda$requestRenderAndNotify$0(java.lang.Runnable runnable, java.lang.Runnable runnable2) {
            if (runnable != null) {
                runnable.run();
            }
            if (runnable2 != null) {
                runnable2.run();
            }
        }

        public void surfaceCreated() {
            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                this.mHasSurface = true;
                this.mFinishedCreatingEglSurface = false;
                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                while (this.mWaitingForSurface && !this.mFinishedCreatingEglSurface && !this.mExited) {
                    try {
                        android.opengl.GLSurfaceView.sGLThreadManager.wait();
                    } catch (java.lang.InterruptedException e) {
                        java.lang.Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void surfaceDestroyed() {
            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                this.mHasSurface = false;
                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mWaitingForSurface && !this.mExited) {
                    try {
                        android.opengl.GLSurfaceView.sGLThreadManager.wait();
                    } catch (java.lang.InterruptedException e) {
                        java.lang.Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onPause() {
            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                this.mRequestPaused = true;
                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused) {
                    try {
                        android.opengl.GLSurfaceView.sGLThreadManager.wait();
                    } catch (java.lang.InterruptedException e) {
                        java.lang.Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onResume() {
            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                this.mRequestPaused = false;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && this.mPaused && !this.mRenderComplete) {
                    try {
                        android.opengl.GLSurfaceView.sGLThreadManager.wait();
                    } catch (java.lang.InterruptedException e) {
                        java.lang.Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onWindowResize(int i, int i2) {
            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                this.mWidth = i;
                this.mHeight = i2;
                this.mSizeChanged = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                if (java.lang.Thread.currentThread() == this) {
                    return;
                }
                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused && !this.mRenderComplete && ableToDraw()) {
                    try {
                        android.opengl.GLSurfaceView.sGLThreadManager.wait();
                    } catch (java.lang.InterruptedException e) {
                        java.lang.Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestExitAndWait() {
            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                this.mShouldExit = true;
                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited) {
                    try {
                        android.opengl.GLSurfaceView.sGLThreadManager.wait();
                    } catch (java.lang.InterruptedException e) {
                        java.lang.Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestReleaseEglContextLocked() {
            this.mShouldReleaseEglContext = true;
            android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
        }

        public void queueEvent(java.lang.Runnable runnable) {
            if (runnable == null) {
                throw new java.lang.IllegalArgumentException("r must not be null");
            }
            synchronized (android.opengl.GLSurfaceView.sGLThreadManager) {
                this.mEventQueue.add(runnable);
                android.opengl.GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }
    }

    static class LogWriter extends java.io.Writer {
        private java.lang.StringBuilder mBuilder = new java.lang.StringBuilder();

        LogWriter() {
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            flushBuilder();
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
            flushBuilder();
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                char c = cArr[i + i3];
                if (c == '\n') {
                    flushBuilder();
                } else {
                    this.mBuilder.append(c);
                }
            }
        }

        private void flushBuilder() {
            if (this.mBuilder.length() > 0) {
                android.util.Log.v(android.opengl.GLSurfaceView.TAG, this.mBuilder.toString());
                this.mBuilder.delete(0, this.mBuilder.length());
            }
        }
    }

    private void checkRenderThreadState() {
        if (this.mGLThread != null) {
            throw new java.lang.IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    private static class GLThreadManager {
        private static java.lang.String TAG = "GLThreadManager";

        private GLThreadManager() {
        }

        public synchronized void threadExiting(android.opengl.GLSurfaceView.GLThread gLThread) {
            gLThread.mExited = true;
            notifyAll();
        }

        public void releaseEglContextLocked(android.opengl.GLSurfaceView.GLThread gLThread) {
            notifyAll();
        }
    }
}
