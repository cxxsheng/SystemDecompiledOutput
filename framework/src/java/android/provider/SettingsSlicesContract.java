package android.provider;

/* loaded from: classes3.dex */
public class SettingsSlicesContract {
    public static final java.lang.String AUTHORITY = "android.settings.slices";
    public static final android.net.Uri BASE_URI = new android.net.Uri.Builder().scheme("content").authority(AUTHORITY).build();
    public static final java.lang.String KEY_AIRPLANE_MODE = "airplane_mode";
    public static final java.lang.String KEY_BATTERY_SAVER = "battery_saver";
    public static final java.lang.String KEY_BLUETOOTH = "bluetooth";
    public static final java.lang.String KEY_LOCATION = "location";
    public static final java.lang.String KEY_WIFI = "wifi";
    public static final java.lang.String PATH_SETTING_ACTION = "action";
    public static final java.lang.String PATH_SETTING_INTENT = "intent";

    private SettingsSlicesContract() {
    }
}
