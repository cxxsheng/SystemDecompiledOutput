package com.android.server.display.config;

/* loaded from: classes.dex */
public class HdrBrightnessData {
    public final long mBrightnessDecreaseDebounceMillis;
    public final long mBrightnessIncreaseDebounceMillis;
    public final java.util.Map<java.lang.Float, java.lang.Float> mMaxBrightnessLimits;
    public final float mScreenBrightnessRampDecrease;
    public final float mScreenBrightnessRampIncrease;

    @com.android.internal.annotations.VisibleForTesting
    public HdrBrightnessData(java.util.Map<java.lang.Float, java.lang.Float> map, long j, float f, long j2, float f2) {
        this.mMaxBrightnessLimits = map;
        this.mBrightnessIncreaseDebounceMillis = j;
        this.mScreenBrightnessRampIncrease = f;
        this.mBrightnessDecreaseDebounceMillis = j2;
        this.mScreenBrightnessRampDecrease = f2;
    }

    public java.lang.String toString() {
        return "HdrBrightnessData {mMaxBrightnessLimits: " + this.mMaxBrightnessLimits + ", mBrightnessIncreaseDebounceMillis: " + this.mBrightnessIncreaseDebounceMillis + ", mScreenBrightnessRampIncrease: " + this.mScreenBrightnessRampIncrease + ", mBrightnessDecreaseDebounceMillis: " + this.mBrightnessDecreaseDebounceMillis + ", mScreenBrightnessRampDecrease: " + this.mScreenBrightnessRampDecrease + "} ";
    }

    @android.annotation.Nullable
    public static com.android.server.display.config.HdrBrightnessData loadConfig(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.HdrBrightnessConfig hdrBrightnessConfig = displayConfiguration.getHdrBrightnessConfig();
        if (hdrBrightnessConfig == null) {
            return null;
        }
        java.util.List<com.android.server.display.config.NonNegativeFloatToFloatPoint> point = hdrBrightnessConfig.getBrightnessMap().getPoint();
        java.util.HashMap hashMap = new java.util.HashMap();
        for (com.android.server.display.config.NonNegativeFloatToFloatPoint nonNegativeFloatToFloatPoint : point) {
            hashMap.put(java.lang.Float.valueOf(nonNegativeFloatToFloatPoint.getFirst().floatValue()), java.lang.Float.valueOf(nonNegativeFloatToFloatPoint.getSecond().floatValue()));
        }
        return new com.android.server.display.config.HdrBrightnessData(hashMap, hdrBrightnessConfig.getBrightnessIncreaseDebounceMillis().longValue(), hdrBrightnessConfig.getScreenBrightnessRampIncrease().floatValue(), hdrBrightnessConfig.getBrightnessDecreaseDebounceMillis().longValue(), hdrBrightnessConfig.getScreenBrightnessRampDecrease().floatValue());
    }
}
