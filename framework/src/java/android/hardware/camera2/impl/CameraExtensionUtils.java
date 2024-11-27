package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public final class CameraExtensionUtils {
    public static final int JPEG_DEFAULT_QUALITY = 100;
    public static final int JPEG_DEFAULT_ROTATION = 0;
    public static final int[] SUPPORTED_CAPTURE_OUTPUT_FORMATS = {35, 256, 4101};
    private static final java.lang.String TAG = "CameraExtensionUtils";

    public static class SurfaceInfo {
        public int mWidth = 0;
        public int mHeight = 0;
        public int mFormat = 1;
        public long mUsage = 0;
    }

    public static final class HandlerExecutor implements java.util.concurrent.Executor {
        private final android.os.Handler mHandler;

        public HandlerExecutor(android.os.Handler handler) {
            this.mHandler = handler;
        }

        @Override // java.util.concurrent.Executor
        public void execute(java.lang.Runnable runnable) {
            try {
                this.mHandler.post(runnable);
            } catch (java.util.concurrent.RejectedExecutionException e) {
                android.util.Log.w(android.hardware.camera2.impl.CameraExtensionUtils.TAG, "Handler thread unavailable, skipping message!");
            }
        }
    }

    public static android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo querySurface(android.view.Surface surface) {
        android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo surfaceInfo = new android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo();
        int detectSurfaceFormat = android.hardware.camera2.utils.SurfaceUtils.detectSurfaceFormat(surface);
        int surfaceDataspace = android.hardware.camera2.utils.SurfaceUtils.getSurfaceDataspace(surface);
        android.util.Size surfaceSize = android.hardware.camera2.utils.SurfaceUtils.getSurfaceSize(surface);
        surfaceInfo.mFormat = detectSurfaceFormat;
        surfaceInfo.mWidth = surfaceSize.getWidth();
        surfaceInfo.mHeight = surfaceSize.getHeight();
        surfaceInfo.mUsage = android.hardware.camera2.utils.SurfaceUtils.getSurfaceUsage(surface);
        if (detectSurfaceFormat == 33 && surfaceDataspace == 146931712) {
            surfaceInfo.mFormat = 256;
            return surfaceInfo;
        }
        if (detectSurfaceFormat == 33 && surfaceDataspace == 4101) {
            surfaceInfo.mFormat = 4101;
            return surfaceInfo;
        }
        return surfaceInfo;
    }

    public static android.view.Surface getPostviewSurface(android.hardware.camera2.params.OutputConfiguration outputConfiguration, java.util.HashMap<java.lang.Integer, java.util.List<android.util.Size>> hashMap, int i) {
        if (outputConfiguration == null) {
            return null;
        }
        android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo querySurface = querySurface(outputConfiguration.getSurface());
        if (querySurface.mFormat == i) {
            if (!hashMap.containsKey(java.lang.Integer.valueOf(i))) {
                return null;
            }
            if (hashMap.get(java.lang.Integer.valueOf(querySurface.mFormat)).contains(new android.util.Size(querySurface.mWidth, querySurface.mHeight))) {
                return outputConfiguration.getSurface();
            }
            throw new java.lang.IllegalArgumentException("Postview size not supported!");
        }
        throw new java.lang.IllegalArgumentException("Postview format should be equivalent to  the capture format!");
    }

    public static android.view.Surface getBurstCaptureSurface(java.util.List<android.hardware.camera2.params.OutputConfiguration> list, java.util.HashMap<java.lang.Integer, java.util.List<android.util.Size>> hashMap) {
        for (android.hardware.camera2.params.OutputConfiguration outputConfiguration : list) {
            android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo querySurface = querySurface(outputConfiguration.getSurface());
            for (int i : SUPPORTED_CAPTURE_OUTPUT_FORMATS) {
                if (querySurface.mFormat == i) {
                    android.util.Size size = new android.util.Size(querySurface.mWidth, querySurface.mHeight);
                    if (hashMap.containsKey(java.lang.Integer.valueOf(i))) {
                        if (hashMap.get(java.lang.Integer.valueOf(querySurface.mFormat)).contains(size)) {
                            return outputConfiguration.getSurface();
                        }
                        throw new java.lang.IllegalArgumentException("Capture size not supported!");
                    }
                    return outputConfiguration.getSurface();
                }
            }
        }
        return null;
    }

    public static android.view.Surface getRepeatingRequestSurface(java.util.List<android.hardware.camera2.params.OutputConfiguration> list, java.util.List<android.util.Size> list2) {
        for (android.hardware.camera2.params.OutputConfiguration outputConfiguration : list) {
            android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo querySurface = querySurface(outputConfiguration.getSurface());
            if (querySurface.mFormat == 34 || (querySurface.mUsage & 2048) != 0 || querySurface.mFormat == 1) {
                android.util.Size size = new android.util.Size(querySurface.mWidth, querySurface.mHeight);
                if (list2 == null || !list2.contains(size)) {
                    throw new java.lang.IllegalArgumentException("Repeating request surface size " + size + " not supported!");
                }
                return outputConfiguration.getSurface();
            }
        }
        return null;
    }

    public static java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> getCharacteristicsMapNative(java.util.Map<java.lang.String, android.hardware.camera2.CameraCharacteristics> map) {
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.util.Map.Entry<java.lang.String, android.hardware.camera2.CameraCharacteristics> entry : map.entrySet()) {
            hashMap.put(entry.getKey(), entry.getValue().getNativeMetadata());
        }
        return hashMap;
    }
}
