package com.android.server.display;

/* loaded from: classes.dex */
class NormalBrightnessModeController {

    @android.annotation.NonNull
    private java.util.Map<com.android.server.display.DisplayDeviceConfig.BrightnessLimitMapType, java.util.Map<java.lang.Float, java.lang.Float>> mMaxBrightnessLimits = new java.util.HashMap();
    private float mAmbientLux = Float.MAX_VALUE;
    private boolean mAutoBrightnessEnabled = false;
    private float mMaxBrightness = 1.0f;

    NormalBrightnessModeController() {
    }

    boolean onAmbientLuxChange(float f) {
        this.mAmbientLux = f;
        return recalculateMaxBrightness();
    }

    boolean setAutoBrightnessState(int i) {
        boolean z = i == 1;
        if (z == this.mAutoBrightnessEnabled) {
            return false;
        }
        this.mAutoBrightnessEnabled = z;
        return recalculateMaxBrightness();
    }

    float getCurrentBrightnessMax() {
        return this.mMaxBrightness;
    }

    boolean resetNbmData(@android.annotation.NonNull java.util.Map<com.android.server.display.DisplayDeviceConfig.BrightnessLimitMapType, java.util.Map<java.lang.Float, java.lang.Float>> map) {
        this.mMaxBrightnessLimits = map;
        return recalculateMaxBrightness();
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("NormalBrightnessModeController:");
        printWriter.println("  mAutoBrightnessEnabled=" + this.mAutoBrightnessEnabled);
        printWriter.println("  mAmbientLux=" + this.mAmbientLux);
        printWriter.println("  mMaxBrightness=" + this.mMaxBrightness);
        printWriter.println("  mMaxBrightnessLimits=" + this.mMaxBrightnessLimits);
    }

    private boolean recalculateMaxBrightness() {
        java.util.Map<java.lang.Float, java.lang.Float> map;
        if (!this.mAutoBrightnessEnabled) {
            map = null;
        } else {
            map = this.mMaxBrightnessLimits.get(com.android.server.display.DisplayDeviceConfig.BrightnessLimitMapType.ADAPTIVE);
        }
        if (map == null) {
            map = this.mMaxBrightnessLimits.get(com.android.server.display.DisplayDeviceConfig.BrightnessLimitMapType.DEFAULT);
        }
        float f = 1.0f;
        if (map != null) {
            float f2 = Float.MAX_VALUE;
            for (java.util.Map.Entry<java.lang.Float, java.lang.Float> entry : map.entrySet()) {
                float floatValue = entry.getKey().floatValue();
                if (floatValue > this.mAmbientLux && floatValue < f2) {
                    f = entry.getValue().floatValue();
                    f2 = floatValue;
                }
            }
        }
        if (this.mMaxBrightness != f) {
            this.mMaxBrightness = f;
            return true;
        }
        return false;
    }
}
