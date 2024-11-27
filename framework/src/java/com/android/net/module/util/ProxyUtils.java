package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class ProxyUtils {
    private static final java.lang.String EXCL_REGEX = "[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*";
    private static final java.lang.String NAME_IP_REGEX = "[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*(\\.[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*)*";
    public static final int PROXY_EXCLLIST_INVALID = 5;
    public static final int PROXY_HOSTNAME_EMPTY = 1;
    public static final int PROXY_HOSTNAME_INVALID = 2;
    public static final int PROXY_PORT_EMPTY = 3;
    public static final int PROXY_PORT_INVALID = 4;
    public static final int PROXY_VALID = 0;
    private static final java.lang.String HOSTNAME_REGEXP = "^$|^[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*(\\.[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*)*$";
    private static final java.util.regex.Pattern HOSTNAME_PATTERN = java.util.regex.Pattern.compile(HOSTNAME_REGEXP);
    private static final java.lang.String EXCLLIST_REGEXP = "^$|^[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*(,[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*)*$";
    private static final java.util.regex.Pattern EXCLLIST_PATTERN = java.util.regex.Pattern.compile(EXCLLIST_REGEXP);

    public static java.util.List<java.lang.String> exclusionStringAsList(java.lang.String str) {
        if (str == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Arrays.asList(str.toLowerCase(java.util.Locale.ROOT).split(","));
    }

    public static java.lang.String exclusionListAsString(java.lang.String[] strArr) {
        if (strArr == null) {
            return "";
        }
        return android.text.TextUtils.join(",", strArr);
    }

    public static int validate(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.util.regex.Matcher matcher = HOSTNAME_PATTERN.matcher(str);
        java.util.regex.Matcher matcher2 = EXCLLIST_PATTERN.matcher(str3);
        if (!matcher.matches()) {
            return 2;
        }
        if (!matcher2.matches()) {
            return 5;
        }
        if (str.length() > 0 && str2.length() == 0) {
            return 3;
        }
        if (str2.length() > 0) {
            if (str.length() == 0) {
                return 1;
            }
            try {
                int parseInt = java.lang.Integer.parseInt(str2);
                if (parseInt <= 0 || parseInt > 65535) {
                    return 4;
                }
                return 0;
            } catch (java.lang.NumberFormatException e) {
                return 4;
            }
        }
        return 0;
    }
}
