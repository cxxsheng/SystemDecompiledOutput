package android.media;

/* loaded from: classes2.dex */
public class MediaRouter2Utils {
    static final java.lang.String SEPARATOR = ":";
    static final java.lang.String TAG = "MR2Utils";

    public static java.lang.String toUniqueId(java.lang.String str, java.lang.String str2) {
        com.android.internal.util.Preconditions.checkArgument((android.text.TextUtils.isEmpty(str) || android.text.TextUtils.isEmpty(str2)) ? false : true);
        return str + ":" + str2;
    }

    public static java.lang.String getProviderId(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            android.util.Log.w(TAG, "getProviderId: uniqueId shouldn't be empty");
            return null;
        }
        int indexOf = str.indexOf(":");
        if (indexOf == -1) {
            return null;
        }
        java.lang.String substring = str.substring(0, indexOf);
        if (android.text.TextUtils.isEmpty(substring)) {
            return null;
        }
        return substring;
    }

    public static java.lang.String getOriginalId(java.lang.String str) {
        int i;
        if (android.text.TextUtils.isEmpty(str)) {
            android.util.Log.w(TAG, "getOriginalId: uniqueId shouldn't be empty");
            return null;
        }
        int indexOf = str.indexOf(":");
        if (indexOf == -1 || (i = indexOf + 1) >= str.length()) {
            return null;
        }
        java.lang.String substring = str.substring(i);
        if (android.text.TextUtils.isEmpty(substring)) {
            return null;
        }
        return substring;
    }
}
