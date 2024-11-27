package android.media;

/* loaded from: classes2.dex */
public class CameraProfile {
    public static final int QUALITY_HIGH = 2;
    public static final int QUALITY_LOW = 0;
    public static final int QUALITY_MEDIUM = 1;
    private static final java.util.HashMap<java.lang.Integer, int[]> sCache = new java.util.HashMap<>();

    private static final native int native_get_image_encoding_quality_level(int i, int i2);

    private static final native int native_get_num_image_encoding_quality_levels(int i);

    private static final native void native_init();

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }

    public static int getJpegEncodingQualityParameter(int i) {
        int numberOfCameras = android.hardware.Camera.getNumberOfCameras();
        android.hardware.Camera.CameraInfo cameraInfo = new android.hardware.Camera.CameraInfo();
        for (int i2 = 0; i2 < numberOfCameras; i2++) {
            android.hardware.Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == 0) {
                return getJpegEncodingQualityParameter(i2, i);
            }
        }
        return 0;
    }

    public static int getJpegEncodingQualityParameter(int i, int i2) {
        int i3;
        if (i2 < 0 || i2 > 2) {
            throw new java.lang.IllegalArgumentException("Unsupported quality level: " + i2);
        }
        synchronized (sCache) {
            int[] iArr = sCache.get(java.lang.Integer.valueOf(i));
            if (iArr == null) {
                iArr = getImageEncodingQualityLevels(i);
                sCache.put(java.lang.Integer.valueOf(i), iArr);
            }
            i3 = iArr[i2];
        }
        return i3;
    }

    private static int[] getImageEncodingQualityLevels(int i) {
        int native_get_num_image_encoding_quality_levels = native_get_num_image_encoding_quality_levels(i);
        if (native_get_num_image_encoding_quality_levels != 3) {
            throw new java.lang.RuntimeException("Unexpected Jpeg encoding quality levels " + native_get_num_image_encoding_quality_levels);
        }
        int[] iArr = new int[native_get_num_image_encoding_quality_levels];
        for (int i2 = 0; i2 < native_get_num_image_encoding_quality_levels; i2++) {
            iArr[i2] = native_get_image_encoding_quality_level(i, i2);
        }
        java.util.Arrays.sort(iArr);
        return iArr;
    }
}
