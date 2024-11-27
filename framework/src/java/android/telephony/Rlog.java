package android.telephony;

/* loaded from: classes3.dex */
public final class Rlog {
    private static final boolean USER_BUILD = android.os.Build.IS_USER;

    private Rlog() {
    }

    public static int v(java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(1, 2, str, str2);
    }

    public static int v(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.println_native(1, 2, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int d(java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(1, 3, str, str2);
    }

    public static int d(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.println_native(1, 3, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int i(java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(1, 4, str, str2);
    }

    public static int i(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.println_native(1, 4, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int w(java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(1, 5, str, str2);
    }

    public static int w(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.println_native(1, 5, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int w(java.lang.String str, java.lang.Throwable th) {
        return android.util.Log.println_native(1, 5, str, android.util.Log.getStackTraceString(th));
    }

    public static int e(java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(1, 6, str, str2);
    }

    public static int e(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.println_native(1, 6, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int println(int i, java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(1, i, str, str2);
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
