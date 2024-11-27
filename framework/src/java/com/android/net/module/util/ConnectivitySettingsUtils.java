package com.android.net.module.util;

/* loaded from: classes5.dex */
public class ConnectivitySettingsUtils {
    public static final java.lang.String PRIVATE_DNS_DEFAULT_MODE = "private_dns_default_mode";
    public static final java.lang.String PRIVATE_DNS_MODE = "private_dns_mode";
    public static final int PRIVATE_DNS_MODE_OFF = 1;
    public static final java.lang.String PRIVATE_DNS_MODE_OFF_STRING = "off";
    public static final int PRIVATE_DNS_MODE_OPPORTUNISTIC = 2;
    public static final java.lang.String PRIVATE_DNS_MODE_OPPORTUNISTIC_STRING = "opportunistic";
    public static final int PRIVATE_DNS_MODE_PROVIDER_HOSTNAME = 3;
    public static final java.lang.String PRIVATE_DNS_MODE_PROVIDER_HOSTNAME_STRING = "hostname";
    public static final java.lang.String PRIVATE_DNS_SPECIFIER = "private_dns_specifier";

    public static java.lang.String getPrivateDnsModeAsString(int i) {
        switch (i) {
            case 1:
                return "off";
            case 2:
                return PRIVATE_DNS_MODE_OPPORTUNISTIC_STRING;
            case 3:
                return PRIVATE_DNS_MODE_PROVIDER_HOSTNAME_STRING;
            default:
                throw new java.lang.IllegalArgumentException("Invalid private dns mode: " + i);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int getPrivateDnsModeAsInt(java.lang.String str) {
        char c;
        if (android.text.TextUtils.isEmpty(str)) {
            return 2;
        }
        switch (str.hashCode()) {
            case -539229175:
                if (str.equals(PRIVATE_DNS_MODE_OPPORTUNISTIC_STRING)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -299803597:
                if (str.equals(PRIVATE_DNS_MODE_PROVIDER_HOSTNAME_STRING)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 109935:
                if (str.equals("off")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return 2;
    }

    public static int getPrivateDnsMode(android.content.Context context) {
        android.content.ContentResolver contentResolver = context.getContentResolver();
        java.lang.String string = android.provider.Settings.Global.getString(contentResolver, "private_dns_mode");
        if (android.text.TextUtils.isEmpty(string)) {
            string = android.provider.Settings.Global.getString(contentResolver, "private_dns_default_mode");
        }
        return getPrivateDnsModeAsInt(string);
    }

    public static void setPrivateDnsMode(android.content.Context context, int i) {
        if (i != 1 && i != 2 && i != 3) {
            throw new java.lang.IllegalArgumentException("Invalid private dns mode: " + i);
        }
        android.provider.Settings.Global.putString(context.getContentResolver(), "private_dns_mode", getPrivateDnsModeAsString(i));
    }

    public static java.lang.String getPrivateDnsHostname(android.content.Context context) {
        return android.provider.Settings.Global.getString(context.getContentResolver(), "private_dns_specifier");
    }

    public static void setPrivateDnsHostname(android.content.Context context, java.lang.String str) {
        android.provider.Settings.Global.putString(context.getContentResolver(), "private_dns_specifier", str);
    }
}
