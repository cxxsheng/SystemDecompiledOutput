package com.android.internal.org.bouncycastle.util;

/* loaded from: classes4.dex */
public class IPAddress {
    public static boolean isValid(java.lang.String str) {
        return isValidIPv4(str) || isValidIPv6(str);
    }

    public static boolean isValidWithNetMask(java.lang.String str) {
        return isValidIPv4WithNetmask(str) || isValidIPv6WithNetmask(str);
    }

    public static boolean isValidIPv4(java.lang.String str) {
        int indexOf;
        if (str.length() == 0) {
            return false;
        }
        java.lang.String str2 = str + android.media.MediaMetrics.SEPARATOR;
        int i = 0;
        int i2 = 0;
        while (i < str2.length() && (indexOf = str2.indexOf(46, i)) > i) {
            if (i2 == 4) {
                return false;
            }
            try {
                int parseInt = java.lang.Integer.parseInt(str2.substring(i, indexOf));
                if (parseInt < 0 || parseInt > 255) {
                    return false;
                }
                i = indexOf + 1;
                i2++;
            } catch (java.lang.NumberFormatException e) {
                return false;
            }
        }
        return i2 == 4;
    }

    public static boolean isValidIPv4WithNetmask(java.lang.String str) {
        int indexOf = str.indexOf("/");
        java.lang.String substring = str.substring(indexOf + 1);
        if (indexOf <= 0 || !isValidIPv4(str.substring(0, indexOf))) {
            return false;
        }
        return isValidIPv4(substring) || isMaskValue(substring, 32);
    }

    public static boolean isValidIPv6WithNetmask(java.lang.String str) {
        int indexOf = str.indexOf("/");
        java.lang.String substring = str.substring(indexOf + 1);
        if (indexOf <= 0 || !isValidIPv6(str.substring(0, indexOf))) {
            return false;
        }
        return isValidIPv6(substring) || isMaskValue(substring, 128);
    }

    private static boolean isMaskValue(java.lang.String str, int i) {
        try {
            int parseInt = java.lang.Integer.parseInt(str);
            return parseInt >= 0 && parseInt <= i;
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidIPv6(java.lang.String str) {
        int indexOf;
        if (str.length() == 0) {
            return false;
        }
        java.lang.String str2 = str + ":";
        int i = 0;
        int i2 = 0;
        boolean z = false;
        while (i < str2.length() && (indexOf = str2.indexOf(58, i)) >= i) {
            if (i2 == 8) {
                return false;
            }
            if (i != indexOf) {
                java.lang.String substring = str2.substring(i, indexOf);
                if (indexOf == str2.length() - 1 && substring.indexOf(46) > 0) {
                    if (!isValidIPv4(substring)) {
                        return false;
                    }
                    i2++;
                } else {
                    try {
                        int parseInt = java.lang.Integer.parseInt(str2.substring(i, indexOf), 16);
                        if (parseInt < 0 || parseInt > 65535) {
                            return false;
                        }
                    } catch (java.lang.NumberFormatException e) {
                        return false;
                    }
                }
            } else {
                if (indexOf != 1 && indexOf != str2.length() - 1 && z) {
                    return false;
                }
                z = true;
            }
            i = indexOf + 1;
            i2++;
        }
        return i2 == 8 || z;
    }
}
