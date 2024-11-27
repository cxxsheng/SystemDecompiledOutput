package com.android.graphics.flags;

/* loaded from: classes4.dex */
public final class Flags {
    private static com.android.graphics.flags.FeatureFlags FEATURE_FLAGS = new com.android.graphics.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_EXACT_COMPUTE_BOUNDS = "com.android.graphics.flags.exact_compute_bounds";
    public static final java.lang.String FLAG_YUV_IMAGE_COMPRESS_TO_ULTRA_HDR = "com.android.graphics.flags.yuv_image_compress_to_ultra_hdr";

    public static boolean exactComputeBounds() {
        return FEATURE_FLAGS.exactComputeBounds();
    }

    public static boolean yuvImageCompressToUltraHdr() {
        return FEATURE_FLAGS.yuvImageCompressToUltraHdr();
    }
}
