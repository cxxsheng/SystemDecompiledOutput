package com.android.telephony;

/* loaded from: classes5.dex */
public final class Rlog {
    private static final boolean USER_BUILD = com.android.internal.telephony.util.TelephonyUtils.IS_USER;

    private Rlog() {
    }

    private static int log(int i, java.lang.String str, java.lang.String str2) {
        return android.util.Log.logToRadioBuffer(i, str, str2);
    }

    public static int v(java.lang.String str, java.lang.String str2) {
        return log(2, str, str2);
    }

    public static int v(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return log(2, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int d(java.lang.String str, java.lang.String str2) {
        return log(3, str, str2);
    }

    public static int d(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return log(3, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int i(java.lang.String str, java.lang.String str2) {
        return log(4, str, str2);
    }

    public static int i(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return log(4, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int w(java.lang.String str, java.lang.String str2) {
        return log(5, str, str2);
    }

    public static int w(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return log(5, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int w(java.lang.String str, java.lang.Throwable th) {
        return log(5, str, android.util.Log.getStackTraceString(th));
    }

    public static int e(java.lang.String str, java.lang.String str2) {
        return log(6, str, str2);
    }

    public static int e(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return log(6, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int println(int i, java.lang.String str, java.lang.String str2) {
        return log(i, str, str2);
    }

    public static boolean isLoggable(java.lang.String str, int i) {
        return android.util.Log.isLoggable(str, i);
    }

    public static java.lang.String pii(java.lang.String str, java.lang.Object obj) {
        java.lang.String valueOf = java.lang.String.valueOf(obj);
        if (obj == null || android.text.TextUtils.isEmpty(valueOf) || isLoggable(str, 2)) {
            return valueOf;
        }
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + secureHash(valueOf.getBytes()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static java.lang.String pii(boolean z, java.lang.Object obj) {
        java.lang.String valueOf = java.lang.String.valueOf(obj);
        if (obj == null || android.text.TextUtils.isEmpty(valueOf) || z) {
            return valueOf;
        }
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + secureHash(valueOf.getBytes()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static java.lang.String piiHandle(java.lang.Object obj) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (obj instanceof android.net.Uri) {
            android.net.Uri uri = (android.net.Uri) obj;
            java.lang.String scheme = uri.getScheme();
            if (!android.text.TextUtils.isEmpty(scheme)) {
                sb.append(scheme).append(":");
            }
            java.lang.String schemeSpecificPart = uri.getSchemeSpecificPart();
            if (android.telecom.PhoneAccount.SCHEME_TEL.equals(scheme)) {
                obfuscatePhoneNumber(sb, schemeSpecificPart);
            } else if ("sip".equals(scheme)) {
                for (int i = 0; i < schemeSpecificPart.length(); i++) {
                    char charAt = schemeSpecificPart.charAt(i);
                    if (charAt != '@' && charAt != '.') {
                        charAt = '*';
                    }
                    sb.append(charAt);
                }
            } else {
                sb.append("***");
            }
        } else if (obj instanceof java.lang.String) {
            obfuscatePhoneNumber(sb, (java.lang.String) obj);
        }
        return sb.toString();
    }

    private static void obfuscatePhoneNumber(java.lang.StringBuilder sb, java.lang.String str) {
        int dialableCount = getDialableCount(str) - (USER_BUILD ? 0 : 2);
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            boolean isDialable = android.telephony.PhoneNumberUtils.isDialable(charAt);
            if (isDialable) {
                dialableCount--;
            }
            sb.append((!isDialable || dialableCount < 0) ? java.lang.Character.valueOf(charAt) : "*");
        }
    }

    private static int getDialableCount(java.lang.String str) {
        int i = 0;
        for (char c : str.toCharArray()) {
            if (android.telephony.PhoneNumberUtils.isDialable(c)) {
                i++;
            }
        }
        return i;
    }

    private static java.lang.String secureHash(byte[] bArr) {
        if (USER_BUILD) {
            return "****";
        }
        try {
            return android.util.Base64.encodeToString(java.security.MessageDigest.getInstance("SHA-1").digest(bArr), 11);
        } catch (java.security.NoSuchAlgorithmException e) {
            return "####";
        }
    }
}
