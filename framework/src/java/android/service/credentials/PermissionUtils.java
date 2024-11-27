package android.service.credentials;

/* loaded from: classes3.dex */
public class PermissionUtils {
    public static boolean hasPermission(android.content.Context context, java.lang.String str, java.lang.String str2) {
        return context.getPackageManager().checkPermission(str2, str) == 0;
    }
}
