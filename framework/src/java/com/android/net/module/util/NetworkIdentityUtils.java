package com.android.net.module.util;

/* loaded from: classes5.dex */
public class NetworkIdentityUtils {
    public static java.lang.String scrubSubscriberId(java.lang.String str) {
        if (str != null) {
            return str.substring(0, java.lang.Math.min(6, str.length())) + android.telecom.Logging.Session.TRUNCATE_STRING;
        }
        return "null";
    }

    public static java.lang.String[] scrubSubscriberIds(java.lang.String[] strArr) {
        if (strArr == null) {
            return null;
        }
        int length = strArr.length;
        java.lang.String[] strArr2 = new java.lang.String[length];
        for (int i = 0; i < length; i++) {
            strArr2[i] = scrubSubscriberId(strArr[i]);
        }
        return strArr2;
    }
}
