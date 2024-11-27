package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public class SubtypeLocaleUtils {
    public static java.util.Locale constructLocaleFromString(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        java.lang.String[] split = str.split(android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD, 3);
        if (split.length >= 1 && "tl".equals(split[0])) {
            split[0] = "fil";
        }
        if (split.length == 1) {
            return new java.util.Locale(split[0]);
        }
        if (split.length == 2) {
            return new java.util.Locale(split[0], split[1]);
        }
        if (split.length == 3) {
            return new java.util.Locale(split[0], split[1], split[2]);
        }
        return null;
    }
}
