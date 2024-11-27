package com.android.server.notification;

/* loaded from: classes2.dex */
public class PropConfig {
    private static final java.lang.String UNSET = "UNSET";

    public static int getInt(android.content.Context context, java.lang.String str, int i) {
        return android.os.SystemProperties.getInt(str, context.getResources().getInteger(i));
    }

    public static java.lang.String[] getStringArray(android.content.Context context, java.lang.String str, int i) {
        java.lang.String str2 = android.os.SystemProperties.get(str, UNSET);
        return !UNSET.equals(str2) ? str2.split(",") : context.getResources().getStringArray(i);
    }
}
