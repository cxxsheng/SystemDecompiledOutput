package android.os;

/* loaded from: classes3.dex */
public final class Trace {
    public static final int MAX_SECTION_NAME_LEN = 127;
    private static final java.lang.String TAG = "Trace";
    public static final long TRACE_TAG_ACTIVITY_MANAGER = 64;
    public static final long TRACE_TAG_ADB = 4194304;

    @android.annotation.SystemApi
    public static final long TRACE_TAG_AIDL = 16777216;
    public static final long TRACE_TAG_ALWAYS = 1;
    public static final long TRACE_TAG_APP = 4096;
    public static final long TRACE_TAG_AUDIO = 256;
    public static final long TRACE_TAG_BIONIC = 65536;
    public static final long TRACE_TAG_CAMERA = 1024;
    public static final long TRACE_TAG_DALVIK = 16384;
    public static final long TRACE_TAG_DATABASE = 1048576;
    public static final long TRACE_TAG_GRAPHICS = 2;
    public static final long TRACE_TAG_HAL = 2048;
    public static final long TRACE_TAG_INPUT = 4;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final long TRACE_TAG_NETWORK = 2097152;
    public static final long TRACE_TAG_NEVER = 0;
    public static final long TRACE_TAG_NNAPI = 33554432;
    private static final long TRACE_TAG_NOT_READY = Long.MIN_VALUE;
    public static final long TRACE_TAG_PACKAGE_MANAGER = 262144;
    public static final long TRACE_TAG_POWER = 131072;
    public static final long TRACE_TAG_RESOURCES = 8192;
    public static final long TRACE_TAG_RRO = 67108864;
    public static final long TRACE_TAG_RS = 32768;
    public static final long TRACE_TAG_SYNC_MANAGER = 128;
    public static final long TRACE_TAG_SYSTEM_SERVER = 524288;
    public static final long TRACE_TAG_THERMAL = 134217728;
    public static final long TRACE_TAG_VIBRATOR = 8388608;
    public static final long TRACE_TAG_VIDEO = 512;
    public static final long TRACE_TAG_VIEW = 8;
    public static final long TRACE_TAG_WEBVIEW = 16;
    public static final long TRACE_TAG_WINDOW_MANAGER = 32;
    private static volatile long sEnabledTags = Long.MIN_VALUE;
    private static int sZygoteDebugFlags = 0;

    @dalvik.annotation.optimization.FastNative
    private static native void nativeAsyncTraceBegin(long j, java.lang.String str, int i);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeAsyncTraceEnd(long j, java.lang.String str, int i);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeAsyncTraceForTrackBegin(long j, java.lang.String str, java.lang.String str2, int i);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeAsyncTraceForTrackEnd(long j, java.lang.String str, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nativeGetEnabledTags();

    @dalvik.annotation.optimization.FastNative
    private static native void nativeInstant(long j, java.lang.String str);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeInstantForTrack(long j, java.lang.String str, java.lang.String str2);

    private static native void nativeSetAppTracingAllowed(boolean z);

    private static native void nativeSetTracingEnabled(boolean z);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeTraceBegin(long j, java.lang.String str);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeTraceCounter(long j, java.lang.String str, long j2);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeTraceEnd(long j);

    private static long nativeGetEnabledTags$ravenwood() {
        return 0L;
    }

    private static void nativeSetAppTracingAllowed$ravenwood(boolean z) {
    }

    private static void nativeSetTracingEnabled$ravenwood(boolean z) {
    }

    private Trace() {
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static boolean isTagEnabled(long j) {
        return (j & nativeGetEnabledTags()) != 0;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static void traceCounter(long j, java.lang.String str, int i) {
        if (isTagEnabled(j)) {
            nativeTraceCounter(j, str, i);
        }
    }

    public static void setAppTracingAllowed(boolean z) {
        nativeSetAppTracingAllowed(z);
    }

    public static void setTracingEnabled(boolean z, int i) {
        nativeSetTracingEnabled(z);
        sZygoteDebugFlags = i;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static void traceBegin(long j, java.lang.String str) {
        if (isTagEnabled(j)) {
            nativeTraceBegin(j, str);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static void traceEnd(long j) {
        if (isTagEnabled(j)) {
            nativeTraceEnd(j);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static void asyncTraceBegin(long j, java.lang.String str, int i) {
        if (isTagEnabled(j)) {
            nativeAsyncTraceBegin(j, str, i);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static void asyncTraceEnd(long j, java.lang.String str, int i) {
        if (isTagEnabled(j)) {
            nativeAsyncTraceEnd(j, str, i);
        }
    }

    public static void asyncTraceForTrackBegin(long j, java.lang.String str, java.lang.String str2, int i) {
        if (isTagEnabled(j)) {
            nativeAsyncTraceForTrackBegin(j, str, str2, i);
        }
    }

    public static void asyncTraceForTrackEnd(long j, java.lang.String str, int i) {
        if (isTagEnabled(j)) {
            nativeAsyncTraceForTrackEnd(j, str, i);
        }
    }

    public static void instant(long j, java.lang.String str) {
        if (isTagEnabled(j)) {
            nativeInstant(j, str);
        }
    }

    public static void instantForTrack(long j, java.lang.String str, java.lang.String str2) {
        if (isTagEnabled(j)) {
            nativeInstantForTrack(j, str, str2);
        }
    }

    public static boolean isEnabled() {
        return isTagEnabled(4096L);
    }

    public static void beginSection(java.lang.String str) {
        if (isTagEnabled(4096L)) {
            if (str.length() > 127) {
                throw new java.lang.IllegalArgumentException("sectionName is too long");
            }
            nativeTraceBegin(4096L, str);
        }
    }

    public static void endSection() {
        if (isTagEnabled(4096L)) {
            nativeTraceEnd(4096L);
        }
    }

    public static void beginAsyncSection(java.lang.String str, int i) {
        asyncTraceBegin(4096L, str, i);
    }

    public static void endAsyncSection(java.lang.String str, int i) {
        asyncTraceEnd(4096L, str, i);
    }

    public static void setCounter(java.lang.String str, long j) {
        if (isTagEnabled(4096L)) {
            nativeTraceCounter(4096L, str, j);
        }
    }
}
