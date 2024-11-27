package com.android.server.grammaticalinflection;

/* loaded from: classes.dex */
public class GrammaticalInflectionUtils {
    private static final java.lang.String TAG = "GrammaticalInflectionUtils";

    public static boolean checkSystemGrammaticalGenderPermission(@android.annotation.NonNull android.permission.PermissionManager permissionManager, @android.annotation.NonNull android.content.AttributionSource attributionSource) {
        if (permissionManager.checkPermissionForDataDelivery("android.permission.READ_SYSTEM_GRAMMATICAL_GENDER", attributionSource, (java.lang.String) null) != 0) {
            android.util.Log.v(TAG, "AttributionSource: " + attributionSource + " does not have READ_SYSTEM_GRAMMATICAL_GENDER permission.");
            return false;
        }
        return true;
    }
}
