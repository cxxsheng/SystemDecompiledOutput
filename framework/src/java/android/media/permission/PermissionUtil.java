package android.media.permission;

/* loaded from: classes2.dex */
public class PermissionUtil {
    public static android.media.permission.SafeCloseable establishIdentityIndirect(android.content.Context context, java.lang.String str, android.media.permission.Identity identity, android.media.permission.Identity identity2) {
        java.util.Objects.requireNonNull(context);
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(identity);
        java.util.Objects.requireNonNull(identity2);
        identity.pid = android.os.Binder.getCallingPid();
        identity.uid = android.os.Binder.getCallingUid();
        context.enforcePermission(str, identity.pid, identity.uid, java.lang.String.format("Middleman must have the %s permision.", str));
        return new android.media.permission.CompositeSafeCloseable(android.media.permission.IdentityContext.create(identity2), android.media.permission.ClearCallingIdentityContext.create());
    }

    public static android.media.permission.SafeCloseable establishIdentityDirect(android.media.permission.Identity identity) {
        java.util.Objects.requireNonNull(identity);
        identity.uid = android.os.Binder.getCallingUid();
        identity.pid = android.os.Binder.getCallingPid();
        return new android.media.permission.CompositeSafeCloseable(android.media.permission.IdentityContext.create(identity), android.media.permission.ClearCallingIdentityContext.create());
    }

    public static int checkPermissionForDataDelivery(android.content.Context context, android.media.permission.Identity identity, java.lang.String str, java.lang.String str2) {
        return android.content.PermissionChecker.checkPermissionForDataDelivery(context, str, identity.pid, identity.uid, identity.packageName, identity.attributionTag, str2);
    }

    public static int checkPermissionForPreflight(android.content.Context context, android.media.permission.Identity identity, java.lang.String str) {
        return android.content.PermissionChecker.checkPermissionForPreflight(context, str, identity.pid, identity.uid, identity.packageName);
    }
}
