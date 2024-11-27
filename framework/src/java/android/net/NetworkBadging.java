package android.net;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public class NetworkBadging {
    public static final int BADGING_4K = 30;
    public static final int BADGING_HD = 20;
    public static final int BADGING_NONE = 0;
    public static final int BADGING_SD = 10;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Badging {
    }

    private NetworkBadging() {
    }

    public static android.graphics.drawable.Drawable getWifiIcon(int i, int i2, android.content.res.Resources.Theme theme) {
        return android.content.res.Resources.getSystem().getDrawable(getWifiSignalResource(i), theme);
    }

    private static int getWifiSignalResource(int i) {
        switch (i) {
            case 0:
                return com.android.internal.R.drawable.ic_wifi_signal_0;
            case 1:
                return com.android.internal.R.drawable.ic_wifi_signal_1;
            case 2:
                return com.android.internal.R.drawable.ic_wifi_signal_2;
            case 3:
                return com.android.internal.R.drawable.ic_wifi_signal_3;
            case 4:
                return com.android.internal.R.drawable.ic_wifi_signal_4;
            default:
                throw new java.lang.IllegalArgumentException("Invalid signal level: " + i);
        }
    }
}
