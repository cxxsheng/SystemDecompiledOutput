package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class SurfaceUtils {
    private static final int BAD_VALUE = -android.system.OsConstants.EINVAL;
    private static final int BGRA_8888 = 5;
    private static final int USAGE_HW_COMPOSER = 2048;
    private static final int USAGE_RENDERSCRIPT = 1048576;

    private static native int nativeDetectSurfaceDataspace(android.view.Surface surface);

    private static native int nativeDetectSurfaceDimens(android.view.Surface surface, int[] iArr);

    private static native int nativeDetectSurfaceType(android.view.Surface surface);

    private static native long nativeDetectSurfaceUsageFlags(android.view.Surface surface);

    private static native long nativeGetSurfaceId(android.view.Surface surface);

    public static boolean isSurfaceForPreview(android.view.Surface surface) {
        com.android.internal.util.Preconditions.checkNotNull(surface);
        long nativeDetectSurfaceUsageFlags = nativeDetectSurfaceUsageFlags(surface);
        boolean z = (1114115 & nativeDetectSurfaceUsageFlags) == 0 && (nativeDetectSurfaceUsageFlags & 2816) != 0;
        getSurfaceFormat(surface);
        return z;
    }

    public static boolean isSurfaceForHwVideoEncoder(android.view.Surface surface) {
        com.android.internal.util.Preconditions.checkNotNull(surface);
        long nativeDetectSurfaceUsageFlags = nativeDetectSurfaceUsageFlags(surface);
        boolean z = (1050627 & nativeDetectSurfaceUsageFlags) == 0 && (nativeDetectSurfaceUsageFlags & 65536) != 0;
        getSurfaceFormat(surface);
        return z;
    }

    public static long getSurfaceId(android.view.Surface surface) {
        com.android.internal.util.Preconditions.checkNotNull(surface);
        try {
            return nativeGetSurfaceId(surface);
        } catch (java.lang.IllegalArgumentException e) {
            return 0L;
        }
    }

    public static long getSurfaceUsage(android.view.Surface surface) {
        com.android.internal.util.Preconditions.checkNotNull(surface);
        try {
            return nativeDetectSurfaceUsageFlags(surface);
        } catch (java.lang.IllegalArgumentException e) {
            return 0L;
        }
    }

    public static android.util.Size getSurfaceSize(android.view.Surface surface) {
        com.android.internal.util.Preconditions.checkNotNull(surface);
        int[] iArr = new int[2];
        if (nativeDetectSurfaceDimens(surface, iArr) == BAD_VALUE) {
            throw new java.lang.IllegalArgumentException("Surface was abandoned");
        }
        return new android.util.Size(iArr[0], iArr[1]);
    }

    public static int getSurfaceFormat(android.view.Surface surface) {
        com.android.internal.util.Preconditions.checkNotNull(surface);
        int nativeDetectSurfaceType = nativeDetectSurfaceType(surface);
        if (nativeDetectSurfaceType == BAD_VALUE) {
            throw new java.lang.IllegalArgumentException("Surface was abandoned");
        }
        if (nativeDetectSurfaceType >= 1 && nativeDetectSurfaceType <= 5) {
            return 34;
        }
        return nativeDetectSurfaceType;
    }

    public static int detectSurfaceFormat(android.view.Surface surface) {
        com.android.internal.util.Preconditions.checkNotNull(surface);
        int nativeDetectSurfaceType = nativeDetectSurfaceType(surface);
        if (nativeDetectSurfaceType == BAD_VALUE) {
            throw new java.lang.IllegalArgumentException("Surface was abandoned");
        }
        return nativeDetectSurfaceType;
    }

    public static int getSurfaceDataspace(android.view.Surface surface) {
        com.android.internal.util.Preconditions.checkNotNull(surface);
        int nativeDetectSurfaceDataspace = nativeDetectSurfaceDataspace(surface);
        if (nativeDetectSurfaceDataspace == BAD_VALUE) {
            throw new java.lang.IllegalArgumentException("Surface was abandoned");
        }
        return nativeDetectSurfaceDataspace;
    }

    public static boolean isFlexibleConsumer(android.view.Surface surface) {
        com.android.internal.util.Preconditions.checkNotNull(surface);
        long nativeDetectSurfaceUsageFlags = nativeDetectSurfaceUsageFlags(surface);
        return (1114112 & nativeDetectSurfaceUsageFlags) == 0 && (nativeDetectSurfaceUsageFlags & 2307) != 0;
    }

    private static void checkHighSpeedSurfaceFormat(android.view.Surface surface) {
        int surfaceFormat = getSurfaceFormat(surface);
        if (surfaceFormat != 34) {
            throw new java.lang.IllegalArgumentException("Surface format(" + surfaceFormat + ") is not for preview or hardware video encoding!");
        }
    }

    public static void checkConstrainedHighSpeedSurfaces(java.util.Collection<android.view.Surface> collection, android.util.Range<java.lang.Integer> range, android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap) {
        java.util.List asList;
        if (collection == null || collection.size() == 0 || collection.size() > 2) {
            throw new java.lang.IllegalArgumentException("Output target surface list must not be null and the size must be 1 or 2");
        }
        if (isPrivilegedApp()) {
            return;
        }
        if (range == null) {
            asList = java.util.Arrays.asList(streamConfigurationMap.getHighSpeedVideoSizes());
        } else {
            android.util.Range<java.lang.Integer>[] highSpeedVideoFpsRanges = streamConfigurationMap.getHighSpeedVideoFpsRanges();
            if (!java.util.Arrays.asList(highSpeedVideoFpsRanges).contains(range)) {
                throw new java.lang.IllegalArgumentException("Fps range " + range.toString() + " in the request is not a supported high speed fps range " + java.util.Arrays.toString(highSpeedVideoFpsRanges));
            }
            asList = java.util.Arrays.asList(streamConfigurationMap.getHighSpeedVideoSizesFor(range));
        }
        for (android.view.Surface surface : collection) {
            checkHighSpeedSurfaceFormat(surface);
            android.util.Size surfaceSize = getSurfaceSize(surface);
            if (!asList.contains(surfaceSize)) {
                throw new java.lang.IllegalArgumentException("Surface size " + surfaceSize.toString() + " is not part of the high speed supported size list " + java.util.Arrays.toString(asList.toArray()));
            }
            if (!isSurfaceForPreview(surface) && !isSurfaceForHwVideoEncoder(surface)) {
                throw new java.lang.IllegalArgumentException("This output surface is neither preview nor hardware video encoding surface");
            }
            if (isSurfaceForPreview(surface) && isSurfaceForHwVideoEncoder(surface)) {
                throw new java.lang.IllegalArgumentException("This output surface can not be both preview and hardware video encoding surface");
            }
        }
        if (collection.size() == 2) {
            java.util.Iterator<android.view.Surface> it = collection.iterator();
            if (isSurfaceForPreview(it.next()) == isSurfaceForPreview(it.next())) {
                throw new java.lang.IllegalArgumentException("The 2 output surfaces must have different type");
            }
        }
    }

    private static boolean isPrivilegedApp() {
        java.lang.String currentOpPackageName = android.app.ActivityThread.currentOpPackageName();
        java.lang.String str = android.os.SystemProperties.get("persist.vendor.camera.privapp.list");
        if (str.length() > 0) {
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(',');
            simpleStringSplitter.setString(str);
            java.util.Iterator<java.lang.String> it = simpleStringSplitter.iterator();
            while (it.hasNext()) {
                if (currentOpPackageName.equals(it.next())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
