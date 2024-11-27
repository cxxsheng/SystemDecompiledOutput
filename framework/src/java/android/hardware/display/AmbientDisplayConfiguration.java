package android.hardware.display;

/* loaded from: classes2.dex */
public class AmbientDisplayConfiguration {
    private static final java.lang.String[] DOZE_SETTINGS = {android.provider.Settings.Secure.DOZE_ENABLED, android.provider.Settings.Secure.DOZE_ALWAYS_ON, android.provider.Settings.Secure.DOZE_PICK_UP_GESTURE, android.provider.Settings.Secure.DOZE_PULSE_ON_LONG_PRESS, android.provider.Settings.Secure.DOZE_DOUBLE_TAP_GESTURE, android.provider.Settings.Secure.DOZE_WAKE_LOCK_SCREEN_GESTURE, android.provider.Settings.Secure.DOZE_WAKE_DISPLAY_GESTURE, android.provider.Settings.Secure.DOZE_TAP_SCREEN_GESTURE};
    private static final java.lang.String[] NON_USER_CONFIGURABLE_DOZE_SETTINGS = {android.provider.Settings.Secure.DOZE_QUICK_PICKUP_GESTURE};
    private static final java.lang.String TAG = "AmbientDisplayConfig";
    private final boolean mAlwaysOnByDefault;
    private final android.content.Context mContext;
    private final boolean mPickupGestureEnabledByDefault;
    final android.util.SparseArray<java.util.Map<java.lang.String, java.lang.String>> mUsersInitialValues = new android.util.SparseArray<>();

    public AmbientDisplayConfiguration(android.content.Context context) {
        this.mContext = context;
        this.mAlwaysOnByDefault = this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_dozeAlwaysOnEnabled);
        this.mPickupGestureEnabledByDefault = this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_dozePickupGestureEnabled);
    }

    public boolean enabled(int i) {
        return pulseOnNotificationEnabled(i) || pulseOnLongPressEnabled(i) || alwaysOnEnabled(i) || wakeLockScreenGestureEnabled(i) || wakeDisplayGestureEnabled(i) || pickupGestureEnabled(i) || tapGestureEnabled(i) || doubleTapGestureEnabled(i) || quickPickupSensorEnabled(i) || screenOffUdfpsEnabled(i);
    }

    public boolean pulseOnNotificationEnabled(int i) {
        return boolSettingDefaultOn(android.provider.Settings.Secure.DOZE_ENABLED, i) && pulseOnNotificationAvailable();
    }

    public boolean pulseOnNotificationAvailable() {
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_pulseOnNotificationsAvailable) && ambientDisplayAvailable();
    }

    public boolean pickupGestureEnabled(int i) {
        return boolSetting(android.provider.Settings.Secure.DOZE_PICK_UP_GESTURE, i, this.mPickupGestureEnabledByDefault ? 1 : 0) && dozePickupSensorAvailable();
    }

    public boolean dozePickupSensorAvailable() {
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_dozePulsePickup);
    }

    public boolean tapGestureEnabled(int i) {
        return boolSettingDefaultOn(android.provider.Settings.Secure.DOZE_TAP_SCREEN_GESTURE, i) && tapSensorAvailable();
    }

    public boolean tapSensorAvailable() {
        for (java.lang.String str : tapSensorTypeMapping()) {
            if (!android.text.TextUtils.isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean doubleTapGestureEnabled(int i) {
        return boolSettingDefaultOn(android.provider.Settings.Secure.DOZE_DOUBLE_TAP_GESTURE, i) && doubleTapSensorAvailable();
    }

    public boolean doubleTapSensorAvailable() {
        return !android.text.TextUtils.isEmpty(doubleTapSensorType());
    }

    public boolean quickPickupSensorEnabled(int i) {
        return boolSettingDefaultOn(android.provider.Settings.Secure.DOZE_QUICK_PICKUP_GESTURE, i) && !android.text.TextUtils.isEmpty(quickPickupSensorType()) && pickupGestureEnabled(i) && !alwaysOnEnabled(i);
    }

    public boolean screenOffUdfpsEnabled(int i) {
        return !android.text.TextUtils.isEmpty(udfpsLongPressSensorType()) && boolSettingDefaultOff("screen_off_udfps_enabled", i);
    }

    public boolean wakeScreenGestureAvailable() {
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_dozeWakeLockScreenSensorAvailable);
    }

    public boolean wakeLockScreenGestureEnabled(int i) {
        return boolSettingDefaultOn(android.provider.Settings.Secure.DOZE_WAKE_LOCK_SCREEN_GESTURE, i) && wakeScreenGestureAvailable();
    }

    public boolean wakeDisplayGestureEnabled(int i) {
        return boolSettingDefaultOn(android.provider.Settings.Secure.DOZE_WAKE_DISPLAY_GESTURE, i) && wakeScreenGestureAvailable();
    }

    public long getWakeLockScreenDebounce() {
        return this.mContext.getResources().getInteger(com.android.internal.R.integer.config_dozeWakeLockScreenDebounce);
    }

    public java.lang.String doubleTapSensorType() {
        return this.mContext.getResources().getString(com.android.internal.R.string.config_dozeDoubleTapSensorType);
    }

    public java.lang.String[] tapSensorTypeMapping() {
        java.lang.String[] stringArray = this.mContext.getResources().getStringArray(com.android.internal.R.array.config_dozeTapSensorPostureMapping);
        if (com.android.internal.util.ArrayUtils.isEmpty(stringArray)) {
            return new java.lang.String[]{this.mContext.getResources().getString(com.android.internal.R.string.config_dozeTapSensorType)};
        }
        return stringArray;
    }

    public java.lang.String longPressSensorType() {
        return this.mContext.getResources().getString(com.android.internal.R.string.config_dozeLongPressSensorType);
    }

    public java.lang.String udfpsLongPressSensorType() {
        return this.mContext.getResources().getString(com.android.internal.R.string.config_dozeUdfpsLongPressSensorType);
    }

    public java.lang.String quickPickupSensorType() {
        return this.mContext.getResources().getString(com.android.internal.R.string.config_quickPickupSensorType);
    }

    public boolean pulseOnLongPressEnabled(int i) {
        return pulseOnLongPressAvailable() && boolSettingDefaultOff(android.provider.Settings.Secure.DOZE_PULSE_ON_LONG_PRESS, i);
    }

    private boolean pulseOnLongPressAvailable() {
        return !android.text.TextUtils.isEmpty(longPressSensorType());
    }

    public boolean alwaysOnEnabled(int i) {
        return boolSetting(android.provider.Settings.Secure.DOZE_ALWAYS_ON, i, this.mAlwaysOnByDefault ? 1 : 0) && alwaysOnAvailable() && !accessibilityInversionEnabled(i);
    }

    public boolean alwaysOnAvailable() {
        return (alwaysOnDisplayDebuggingEnabled() || alwaysOnDisplayAvailable()) && ambientDisplayAvailable();
    }

    public boolean alwaysOnAvailableForUser(int i) {
        return alwaysOnAvailable() && !accessibilityInversionEnabled(i);
    }

    public java.lang.String ambientDisplayComponent() {
        return this.mContext.getResources().getString(com.android.internal.R.string.config_dozeComponent);
    }

    public boolean accessibilityInversionEnabled(int i) {
        return boolSettingDefaultOff(android.provider.Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, i);
    }

    public boolean ambientDisplayAvailable() {
        return !android.text.TextUtils.isEmpty(ambientDisplayComponent());
    }

    public boolean dozeSuppressed(int i) {
        return boolSettingDefaultOff(android.provider.Settings.Secure.SUPPRESS_DOZE, i);
    }

    private boolean alwaysOnDisplayAvailable() {
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_dozeAlwaysOnDisplayAvailable);
    }

    private boolean alwaysOnDisplayDebuggingEnabled() {
        return android.os.SystemProperties.getBoolean("debug.doze.aod", false) && android.os.Build.IS_DEBUGGABLE;
    }

    private boolean boolSettingDefaultOn(java.lang.String str, int i) {
        return boolSetting(str, i, 1);
    }

    private boolean boolSettingDefaultOff(java.lang.String str, int i) {
        return boolSetting(str, i, 0);
    }

    private boolean boolSetting(java.lang.String str, int i, int i2) {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), str, i2, i) != 0;
    }

    public void disableDozeSettings(int i) {
        disableDozeSettings(false, i);
    }

    public void disableDozeSettings(boolean z, int i) {
        java.util.Map<java.lang.String, java.lang.String> map = this.mUsersInitialValues.get(i);
        if (map != null && !map.isEmpty()) {
            throw new java.lang.IllegalStateException("Don't call #disableDozeSettings more than once,without first calling #restoreDozeSettings");
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (java.lang.String str : DOZE_SETTINGS) {
            arrayMap.put(str, getDozeSetting(str, i));
            putDozeSetting(str, android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, i);
        }
        if (z) {
            for (java.lang.String str2 : NON_USER_CONFIGURABLE_DOZE_SETTINGS) {
                arrayMap.put(str2, getDozeSetting(str2, i));
                putDozeSetting(str2, android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, i);
            }
        }
        this.mUsersInitialValues.put(i, arrayMap);
    }

    public void restoreDozeSettings(int i) {
        java.util.Map<java.lang.String, java.lang.String> map = this.mUsersInitialValues.get(i);
        if (map != null && !map.isEmpty()) {
            for (java.lang.String str : DOZE_SETTINGS) {
                putDozeSetting(str, map.get(str), i);
            }
            this.mUsersInitialValues.remove(i);
        }
    }

    private java.lang.String getDozeSetting(java.lang.String str, int i) {
        return android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), str, i);
    }

    private void putDozeSetting(java.lang.String str, java.lang.String str2, int i) {
        android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), str, str2, i);
    }
}
