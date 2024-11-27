package com.android.server.display.feature;

/* loaded from: classes.dex */
public class DeviceConfigParameterProvider {
    private static final java.lang.String TAG = "DisplayFeatureProvider";
    private final android.provider.DeviceConfigInterface mDeviceConfig;

    public DeviceConfigParameterProvider(android.provider.DeviceConfigInterface deviceConfigInterface) {
        this.mDeviceConfig = deviceConfigInterface;
    }

    public boolean isHdrOutputControlFeatureEnabled() {
        return android.provider.DeviceConfig.getBoolean("display_manager", "enable_hdr_output_control", true);
    }

    public boolean isNormalBrightnessControllerFeatureEnabled() {
        return android.provider.DeviceConfig.getBoolean("display_manager", "use_normal_brightness_mode_controller", false);
    }

    public boolean isDisableScreenWakeLocksWhileCachedFeatureEnabled() {
        return this.mDeviceConfig.getBoolean("display_manager", "disable_screen_wake_locks_while_cached", true);
    }

    public float getPeakRefreshRateDefault() {
        return this.mDeviceConfig.getFloat("display_manager", "peak_refresh_rate_default", -1.0f);
    }

    public java.lang.String getPowerThrottlingData() {
        return this.mDeviceConfig.getString("display_manager", "power_throttling_data", (java.lang.String) null);
    }

    public java.lang.String getBrightnessThrottlingData() {
        return this.mDeviceConfig.getString("display_manager", "brightness_throttling_data", (java.lang.String) null);
    }

    public int getRefreshRateInHbmSunlight() {
        return this.mDeviceConfig.getInt("display_manager", "refresh_rate_in_hbm_sunlight", -1);
    }

    public int getRefreshRateInHbmHdr() {
        return this.mDeviceConfig.getInt("display_manager", "refresh_rate_in_hbm_hdr", -1);
    }

    public int getRefreshRateInHighZone() {
        return this.mDeviceConfig.getInt("display_manager", "refresh_rate_in_high_zone", -1);
    }

    public int getRefreshRateInLowZone() {
        return this.mDeviceConfig.getInt("display_manager", "refresh_rate_in_zone", -1);
    }

    @android.annotation.Nullable
    public float[] getHighAmbientBrightnessThresholds() {
        return com.android.server.display.utils.DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(getIntArrayProperty("fixed_refresh_rate_high_ambient_brightness_thresholds"));
    }

    @android.annotation.Nullable
    public float[] getHighDisplayBrightnessThresholds() {
        return com.android.server.display.utils.DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(getIntArrayProperty("fixed_refresh_rate_high_display_brightness_thresholds"));
    }

    @android.annotation.Nullable
    public float[] getLowDisplayBrightnessThresholds() {
        return com.android.server.display.utils.DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(getIntArrayProperty("peak_refresh_rate_brightness_thresholds"));
    }

    @android.annotation.Nullable
    public float[] getLowAmbientBrightnessThresholds() {
        return com.android.server.display.utils.DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(getIntArrayProperty("peak_refresh_rate_ambient_thresholds"));
    }

    public void addOnPropertiesChangedListener(java.util.concurrent.Executor executor, android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
        this.mDeviceConfig.addOnPropertiesChangedListener("display_manager", executor, onPropertiesChangedListener);
    }

    public void removeOnPropertiesChangedListener(android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
        this.mDeviceConfig.removeOnPropertiesChangedListener(onPropertiesChangedListener);
    }

    @android.annotation.Nullable
    private int[] getIntArrayProperty(java.lang.String str) {
        java.lang.String string = this.mDeviceConfig.getString("display_manager", str, (java.lang.String) null);
        if (string != null) {
            return parseIntArray(string);
        }
        return null;
    }

    @android.annotation.Nullable
    private int[] parseIntArray(@android.annotation.NonNull java.lang.String str) {
        java.lang.String[] split = str.split(",");
        int length = split.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            try {
                iArr[i] = java.lang.Integer.parseInt(split[i]);
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.e(TAG, "Incorrect format for array: '" + str + "'", e);
                return null;
            }
        }
        return iArr;
    }
}
