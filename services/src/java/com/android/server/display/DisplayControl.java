package com.android.server.display;

/* loaded from: classes.dex */
public class DisplayControl {
    private static native android.os.IBinder nativeCreateDisplay(java.lang.String str, boolean z, float f);

    private static native void nativeDestroyDisplay(android.os.IBinder iBinder);

    private static native boolean nativeGetHdrOutputConversionSupport();

    private static native int[] nativeGetHdrOutputTypesWithLatency();

    private static native long[] nativeGetPhysicalDisplayIds();

    private static native android.os.IBinder nativeGetPhysicalDisplayToken(long j);

    private static native int[] nativeGetSupportedHdrOutputTypes();

    private static native void nativeOverrideHdrTypes(android.os.IBinder iBinder, int[] iArr);

    private static native int nativeSetHdrConversionMode(int i, int i2, int[] iArr, int i3);

    public static android.os.IBinder createDisplay(java.lang.String str, boolean z) {
        java.util.Objects.requireNonNull(str, "name must not be null");
        return nativeCreateDisplay(str, z, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
    }

    public static android.os.IBinder createDisplay(java.lang.String str, boolean z, float f) {
        java.util.Objects.requireNonNull(str, "name must not be null");
        return nativeCreateDisplay(str, z, f);
    }

    public static void destroyDisplay(android.os.IBinder iBinder) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        nativeDestroyDisplay(iBinder);
    }

    @android.annotation.RequiresPermission("android.permission.ACCESS_SURFACE_FLINGER")
    public static void overrideHdrTypes(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull int[] iArr) {
        nativeOverrideHdrTypes(iBinder, iArr);
    }

    public static long[] getPhysicalDisplayIds() {
        return nativeGetPhysicalDisplayIds();
    }

    public static android.os.IBinder getPhysicalDisplayToken(long j) {
        return nativeGetPhysicalDisplayToken(j);
    }

    public static int setHdrConversionMode(int i, int i2, int[] iArr) {
        return nativeSetHdrConversionMode(i, i2, iArr, iArr != null ? iArr.length : 0);
    }

    public static int[] getSupportedHdrOutputTypes() {
        return nativeGetSupportedHdrOutputTypes();
    }

    public static int[] getHdrOutputTypesWithLatency() {
        return nativeGetHdrOutputTypesWithLatency();
    }

    public static boolean getHdrOutputConversionSupport() {
        return nativeGetHdrOutputConversionSupport();
    }
}
