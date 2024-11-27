package com.android.server.display.config;

/* loaded from: classes.dex */
public class DisplayBrightnessMappingConfig {
    private static final java.lang.String DEFAULT_BRIGHTNESS_MAPPING_KEY = com.android.server.display.config.AutoBrightnessModeName._default.getRawName() + "_" + com.android.server.display.config.AutoBrightnessSettingName.normal.getRawName();
    private float[] mBrightnessLevelsNits;
    private final java.util.Map<java.lang.String, float[]> mBrightnessLevelsMap = new java.util.HashMap();
    private final java.util.Map<java.lang.String, float[]> mBrightnessLevelsLuxMap = new java.util.HashMap();

    public DisplayBrightnessMappingConfig(android.content.Context context, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, com.android.server.display.config.AutoBrightness autoBrightness, android.util.Spline spline) {
        java.lang.String rawName;
        java.lang.String rawName2;
        if (displayManagerFlags.areAutoBrightnessModesEnabled() && autoBrightness != null && autoBrightness.getLuxToBrightnessMapping() != null && autoBrightness.getLuxToBrightnessMapping().size() > 0) {
            for (com.android.server.display.config.LuxToBrightnessMapping luxToBrightnessMapping : autoBrightness.getLuxToBrightnessMapping()) {
                int size = luxToBrightnessMapping.getMap().getPoint().size();
                float[] fArr = new float[size];
                float[] fArr2 = new float[size];
                for (int i = 0; i < size; i++) {
                    fArr[i] = spline.interpolate(luxToBrightnessMapping.getMap().getPoint().get(i).getSecond().floatValue());
                    fArr2[i] = luxToBrightnessMapping.getMap().getPoint().get(i).getFirst().floatValue();
                }
                if (size == 0) {
                    throw new java.lang.IllegalArgumentException("A display brightness mapping should not be empty");
                }
                if (fArr2[0] != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    throw new java.lang.IllegalArgumentException("The first lux value in the display brightness mapping must be 0");
                }
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                if (luxToBrightnessMapping.getMode() == null) {
                    rawName = com.android.server.display.config.AutoBrightnessModeName._default.getRawName();
                } else {
                    rawName = luxToBrightnessMapping.getMode().getRawName();
                }
                sb.append(rawName);
                sb.append("_");
                if (luxToBrightnessMapping.getSetting() == null) {
                    rawName2 = com.android.server.display.config.AutoBrightnessSettingName.normal.getRawName();
                } else {
                    rawName2 = luxToBrightnessMapping.getSetting().getRawName();
                }
                sb.append(rawName2);
                java.lang.String sb2 = sb.toString();
                if (this.mBrightnessLevelsMap.containsKey(sb2) || this.mBrightnessLevelsLuxMap.containsKey(sb2)) {
                    throw new java.lang.IllegalArgumentException("A display brightness mapping with key " + sb2 + " already exists");
                }
                this.mBrightnessLevelsMap.put(sb2, fArr);
                this.mBrightnessLevelsLuxMap.put(sb2, fArr2);
            }
        }
        if (!this.mBrightnessLevelsMap.containsKey(DEFAULT_BRIGHTNESS_MAPPING_KEY) || !this.mBrightnessLevelsLuxMap.containsKey(DEFAULT_BRIGHTNESS_MAPPING_KEY)) {
            this.mBrightnessLevelsNits = com.android.server.display.DisplayDeviceConfig.getFloatArray(context.getResources().obtainTypedArray(android.R.array.config_autoBrightnessDisplayValuesNits), -1.0f);
            this.mBrightnessLevelsLuxMap.put(DEFAULT_BRIGHTNESS_MAPPING_KEY, com.android.server.display.DisplayDeviceConfig.getLuxLevels(context.getResources().getIntArray(android.R.array.config_autoBrightnessLevels)));
            this.mBrightnessLevelsMap.put(DEFAULT_BRIGHTNESS_MAPPING_KEY, brightnessArrayIntToFloat(context.getResources().getIntArray(android.R.array.config_autoBrightnessLcdBacklightValues), spline));
        }
    }

    public float[] getLuxArray(int i, int i2) {
        float[] fArr = this.mBrightnessLevelsLuxMap.get(autoBrightnessModeToString(i) + "_" + autoBrightnessPresetToString(i2));
        if (fArr != null) {
            return fArr;
        }
        return this.mBrightnessLevelsLuxMap.get(autoBrightnessModeToString(i) + "_" + com.android.server.display.config.AutoBrightnessSettingName.normal.getRawName());
    }

    public float[] getNitsArray() {
        return this.mBrightnessLevelsNits;
    }

    public float[] getBrightnessArray(int i, int i2) {
        float[] fArr = this.mBrightnessLevelsMap.get(autoBrightnessModeToString(i) + "_" + autoBrightnessPresetToString(i2));
        if (fArr != null) {
            return fArr;
        }
        return this.mBrightnessLevelsMap.get(autoBrightnessModeToString(i) + "_" + com.android.server.display.config.AutoBrightnessSettingName.normal.getRawName());
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("{");
        for (java.util.Map.Entry<java.lang.String, float[]> entry : this.mBrightnessLevelsLuxMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(java.util.Arrays.toString(entry.getValue()));
            sb.append(", ");
        }
        if (sb.length() > 2) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}");
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder("{");
        for (java.util.Map.Entry<java.lang.String, float[]> entry2 : this.mBrightnessLevelsMap.entrySet()) {
            sb2.append(entry2.getKey());
            sb2.append("=");
            sb2.append(java.util.Arrays.toString(entry2.getValue()));
            sb2.append(", ");
        }
        if (sb2.length() > 2) {
            sb2.delete(sb2.length() - 2, sb2.length());
        }
        sb2.append("}");
        return "mBrightnessLevelsNits= " + java.util.Arrays.toString(this.mBrightnessLevelsNits) + ", mBrightnessLevelsLuxMap= " + ((java.lang.Object) sb) + ", mBrightnessLevelsMap= " + ((java.lang.Object) sb2);
    }

    public static java.lang.String autoBrightnessModeToString(int i) {
        switch (i) {
            case 0:
                return com.android.server.display.config.AutoBrightnessModeName._default.getRawName();
            case 1:
                return com.android.server.display.config.AutoBrightnessModeName.idle.getRawName();
            case 2:
                return com.android.server.display.config.AutoBrightnessModeName.doze.getRawName();
            default:
                throw new java.lang.IllegalArgumentException("Unknown auto-brightness mode: " + i);
        }
    }

    public static java.lang.String autoBrightnessPresetToString(int i) {
        switch (i) {
            case 1:
                return com.android.server.display.config.AutoBrightnessSettingName.bright.getRawName();
            case 2:
                return com.android.server.display.config.AutoBrightnessSettingName.normal.getRawName();
            case 3:
                return com.android.server.display.config.AutoBrightnessSettingName.dim.getRawName();
            default:
                throw new java.lang.IllegalArgumentException("Unknown auto-brightness preset value: " + i);
        }
    }

    private float[] brightnessArrayIntToFloat(int[] iArr, android.util.Spline spline) {
        float[] fArr = new float[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            fArr[i] = spline.interpolate(com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat(iArr[i]));
        }
        return fArr;
    }
}
