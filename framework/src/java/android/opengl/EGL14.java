package android.opengl;

/* loaded from: classes3.dex */
public class EGL14 {
    public static final int EGL_ALPHA_MASK_SIZE = 12350;
    public static final int EGL_ALPHA_SIZE = 12321;
    public static final int EGL_BACK_BUFFER = 12420;
    public static final int EGL_BAD_ACCESS = 12290;
    public static final int EGL_BAD_ALLOC = 12291;
    public static final int EGL_BAD_ATTRIBUTE = 12292;
    public static final int EGL_BAD_CONFIG = 12293;
    public static final int EGL_BAD_CONTEXT = 12294;
    public static final int EGL_BAD_CURRENT_SURFACE = 12295;
    public static final int EGL_BAD_DISPLAY = 12296;
    public static final int EGL_BAD_MATCH = 12297;
    public static final int EGL_BAD_NATIVE_PIXMAP = 12298;
    public static final int EGL_BAD_NATIVE_WINDOW = 12299;
    public static final int EGL_BAD_PARAMETER = 12300;
    public static final int EGL_BAD_SURFACE = 12301;
    public static final int EGL_BIND_TO_TEXTURE_RGB = 12345;
    public static final int EGL_BIND_TO_TEXTURE_RGBA = 12346;
    public static final int EGL_BLUE_SIZE = 12322;
    public static final int EGL_BUFFER_DESTROYED = 12437;
    public static final int EGL_BUFFER_PRESERVED = 12436;
    public static final int EGL_BUFFER_SIZE = 12320;
    public static final int EGL_CLIENT_APIS = 12429;
    public static final int EGL_COLOR_BUFFER_TYPE = 12351;
    public static final int EGL_CONFIG_CAVEAT = 12327;
    public static final int EGL_CONFIG_ID = 12328;
    public static final int EGL_CONFORMANT = 12354;
    public static final int EGL_CONTEXT_CLIENT_TYPE = 12439;
    public static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
    public static final int EGL_CONTEXT_LOST = 12302;
    public static final int EGL_CORE_NATIVE_ENGINE = 12379;
    public static final int EGL_DEFAULT_DISPLAY = 0;
    public static final int EGL_DEPTH_SIZE = 12325;
    public static final int EGL_DISPLAY_SCALING = 10000;
    public static final int EGL_DRAW = 12377;
    public static final int EGL_EXTENSIONS = 12373;
    public static final int EGL_FALSE = 0;
    public static final int EGL_GREEN_SIZE = 12323;
    public static final int EGL_HEIGHT = 12374;
    public static final int EGL_HORIZONTAL_RESOLUTION = 12432;
    public static final int EGL_LARGEST_PBUFFER = 12376;
    public static final int EGL_LEVEL = 12329;
    public static final int EGL_LUMINANCE_BUFFER = 12431;
    public static final int EGL_LUMINANCE_SIZE = 12349;
    public static final int EGL_MATCH_NATIVE_PIXMAP = 12353;
    public static final int EGL_MAX_PBUFFER_HEIGHT = 12330;
    public static final int EGL_MAX_PBUFFER_PIXELS = 12331;
    public static final int EGL_MAX_PBUFFER_WIDTH = 12332;
    public static final int EGL_MAX_SWAP_INTERVAL = 12348;
    public static final int EGL_MIN_SWAP_INTERVAL = 12347;
    public static final int EGL_MIPMAP_LEVEL = 12419;
    public static final int EGL_MIPMAP_TEXTURE = 12418;
    public static final int EGL_MULTISAMPLE_RESOLVE = 12441;
    public static final int EGL_MULTISAMPLE_RESOLVE_BOX = 12443;
    public static final int EGL_MULTISAMPLE_RESOLVE_BOX_BIT = 512;
    public static final int EGL_MULTISAMPLE_RESOLVE_DEFAULT = 12442;
    public static final int EGL_NATIVE_RENDERABLE = 12333;
    public static final int EGL_NATIVE_VISUAL_ID = 12334;
    public static final int EGL_NATIVE_VISUAL_TYPE = 12335;
    public static final int EGL_NONE = 12344;
    public static final int EGL_NON_CONFORMANT_CONFIG = 12369;
    public static final int EGL_NOT_INITIALIZED = 12289;
    public static android.opengl.EGLContext EGL_NO_CONTEXT = null;
    public static android.opengl.EGLDisplay EGL_NO_DISPLAY = null;
    public static android.opengl.EGLSurface EGL_NO_SURFACE = null;
    public static final int EGL_NO_TEXTURE = 12380;
    public static final int EGL_OPENGL_API = 12450;
    public static final int EGL_OPENGL_BIT = 8;
    public static final int EGL_OPENGL_ES2_BIT = 4;
    public static final int EGL_OPENGL_ES_API = 12448;
    public static final int EGL_OPENGL_ES_BIT = 1;
    public static final int EGL_OPENVG_API = 12449;
    public static final int EGL_OPENVG_BIT = 2;
    public static final int EGL_OPENVG_IMAGE = 12438;
    public static final int EGL_PBUFFER_BIT = 1;
    public static final int EGL_PIXEL_ASPECT_RATIO = 12434;
    public static final int EGL_PIXMAP_BIT = 2;
    public static final int EGL_READ = 12378;
    public static final int EGL_RED_SIZE = 12324;
    public static final int EGL_RENDERABLE_TYPE = 12352;
    public static final int EGL_RENDER_BUFFER = 12422;
    public static final int EGL_RGB_BUFFER = 12430;
    public static final int EGL_SAMPLES = 12337;
    public static final int EGL_SAMPLE_BUFFERS = 12338;
    public static final int EGL_SINGLE_BUFFER = 12421;
    public static final int EGL_SLOW_CONFIG = 12368;
    public static final int EGL_STENCIL_SIZE = 12326;
    public static final int EGL_SUCCESS = 12288;
    public static final int EGL_SURFACE_TYPE = 12339;
    public static final int EGL_SWAP_BEHAVIOR = 12435;
    public static final int EGL_SWAP_BEHAVIOR_PRESERVED_BIT = 1024;
    public static final int EGL_TEXTURE_2D = 12383;
    public static final int EGL_TEXTURE_FORMAT = 12416;
    public static final int EGL_TEXTURE_RGB = 12381;
    public static final int EGL_TEXTURE_RGBA = 12382;
    public static final int EGL_TEXTURE_TARGET = 12417;
    public static final int EGL_TRANSPARENT_BLUE_VALUE = 12341;
    public static final int EGL_TRANSPARENT_GREEN_VALUE = 12342;
    public static final int EGL_TRANSPARENT_RED_VALUE = 12343;
    public static final int EGL_TRANSPARENT_RGB = 12370;
    public static final int EGL_TRANSPARENT_TYPE = 12340;
    public static final int EGL_TRUE = 1;
    public static final int EGL_VENDOR = 12371;
    public static final int EGL_VERSION = 12372;
    public static final int EGL_VERTICAL_RESOLUTION = 12433;
    public static final int EGL_VG_ALPHA_FORMAT = 12424;
    public static final int EGL_VG_ALPHA_FORMAT_NONPRE = 12427;
    public static final int EGL_VG_ALPHA_FORMAT_PRE = 12428;
    public static final int EGL_VG_ALPHA_FORMAT_PRE_BIT = 64;
    public static final int EGL_VG_COLORSPACE = 12423;
    public static final int EGL_VG_COLORSPACE_LINEAR = 12426;
    public static final int EGL_VG_COLORSPACE_LINEAR_BIT = 32;
    public static final int EGL_VG_COLORSPACE_sRGB = 12425;
    public static final int EGL_WIDTH = 12375;
    public static final int EGL_WINDOW_BIT = 4;

    private static native android.opengl.EGLSurface _eglCreateWindowSurface(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLConfig eGLConfig, java.lang.Object obj, int[] iArr, int i);

    private static native android.opengl.EGLSurface _eglCreateWindowSurfaceTexture(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLConfig eGLConfig, java.lang.Object obj, int[] iArr, int i);

    private static native void _nativeClassInit();

    public static native boolean eglBindAPI(int i);

    public static native boolean eglBindTexImage(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLSurface eGLSurface, int i);

    public static native boolean eglChooseConfig(android.opengl.EGLDisplay eGLDisplay, int[] iArr, int i, android.opengl.EGLConfig[] eGLConfigArr, int i2, int i3, int[] iArr2, int i4);

    public static native boolean eglCopyBuffers(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLSurface eGLSurface, int i);

    public static native android.opengl.EGLContext eglCreateContext(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLConfig eGLConfig, android.opengl.EGLContext eGLContext, int[] iArr, int i);

    public static native android.opengl.EGLSurface eglCreatePbufferFromClientBuffer(android.opengl.EGLDisplay eGLDisplay, int i, int i2, android.opengl.EGLConfig eGLConfig, int[] iArr, int i3);

    public static native android.opengl.EGLSurface eglCreatePbufferFromClientBuffer(android.opengl.EGLDisplay eGLDisplay, int i, long j, android.opengl.EGLConfig eGLConfig, int[] iArr, int i2);

    public static native android.opengl.EGLSurface eglCreatePbufferSurface(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLConfig eGLConfig, int[] iArr, int i);

    @java.lang.Deprecated
    public static native android.opengl.EGLSurface eglCreatePixmapSurface(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLConfig eGLConfig, int i, int[] iArr, int i2);

    public static native boolean eglDestroyContext(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLContext eGLContext);

    public static native boolean eglDestroySurface(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLSurface eGLSurface);

    public static native boolean eglGetConfigAttrib(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLConfig eGLConfig, int i, int[] iArr, int i2);

    public static native boolean eglGetConfigs(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLConfig[] eGLConfigArr, int i, int i2, int[] iArr, int i3);

    public static native android.opengl.EGLContext eglGetCurrentContext();

    public static native android.opengl.EGLDisplay eglGetCurrentDisplay();

    public static native android.opengl.EGLSurface eglGetCurrentSurface(int i);

    public static native android.opengl.EGLDisplay eglGetDisplay(int i);

    public static native android.opengl.EGLDisplay eglGetDisplay(long j);

    public static native int eglGetError();

    public static native boolean eglInitialize(android.opengl.EGLDisplay eGLDisplay, int[] iArr, int i, int[] iArr2, int i2);

    public static native boolean eglMakeCurrent(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLSurface eGLSurface, android.opengl.EGLSurface eGLSurface2, android.opengl.EGLContext eGLContext);

    public static native int eglQueryAPI();

    public static native boolean eglQueryContext(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLContext eGLContext, int i, int[] iArr, int i2);

    public static native java.lang.String eglQueryString(android.opengl.EGLDisplay eGLDisplay, int i);

    public static native boolean eglQuerySurface(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLSurface eGLSurface, int i, int[] iArr, int i2);

    public static native boolean eglReleaseTexImage(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLSurface eGLSurface, int i);

    public static native boolean eglReleaseThread();

    public static native boolean eglSurfaceAttrib(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLSurface eGLSurface, int i, int i2);

    public static native boolean eglSwapBuffers(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLSurface eGLSurface);

    public static native boolean eglSwapInterval(android.opengl.EGLDisplay eGLDisplay, int i);

    public static native boolean eglTerminate(android.opengl.EGLDisplay eGLDisplay);

    public static native boolean eglWaitClient();

    public static native boolean eglWaitGL();

    public static native boolean eglWaitNative(int i);

    static {
        _nativeClassInit();
    }

    public static android.opengl.EGLSurface eglCreateWindowSurface(android.opengl.EGLDisplay eGLDisplay, android.opengl.EGLConfig eGLConfig, java.lang.Object obj, int[] iArr, int i) {
        android.view.Surface surface;
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
            return _eglCreateWindowSurface(eGLDisplay, eGLConfig, surface, iArr, i);
        }
        if (obj instanceof android.graphics.SurfaceTexture) {
            return _eglCreateWindowSurfaceTexture(eGLDisplay, eGLConfig, obj, iArr, i);
        }
        throw new java.lang.UnsupportedOperationException("eglCreateWindowSurface() can only be called with an instance of Surface, SurfaceView, SurfaceTexture or SurfaceHolder at the moment, this will be fixed later.");
    }
}
