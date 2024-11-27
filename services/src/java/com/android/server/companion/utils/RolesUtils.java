package com.android.server.companion.utils;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
public final class RolesUtils {
    private static final java.lang.String TAG = "CDM_RolesUtils";

    private RolesUtils() {
    }

    public static boolean isRoleHolder(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        return ((android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class)).getRoleHoldersAsUser(str2, android.os.UserHandle.of(i)).contains(str);
    }

    public static void addRoleHolderForAssociation(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.companion.AssociationInfo associationInfo, @android.annotation.NonNull java.util.function.Consumer<java.lang.Boolean> consumer) {
        java.lang.String deviceProfile = associationInfo.getDeviceProfile();
        if (deviceProfile == null) {
            consumer.accept(true);
        } else {
            ((android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class)).addRoleHolderAsUser(deviceProfile, associationInfo.getPackageName(), 1, android.os.UserHandle.of(associationInfo.getUserId()), context.getMainExecutor(), consumer);
        }
    }

    public static void removeRoleHolderForAssociation(@android.annotation.NonNull android.content.Context context, final int i, final java.lang.String str, final java.lang.String str2) {
        if (str2 == null) {
            return;
        }
        android.app.role.RoleManager roleManager = (android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class);
        android.os.UserHandle of = android.os.UserHandle.of(i);
        android.util.Slog.i(TAG, "Removing CDM role=" + str2 + " for userId=" + i + ", packageName=" + str);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            roleManager.removeRoleHolderAsUser(str2, str, 1, of, context.getMainExecutor(), new java.util.function.Consumer() { // from class: com.android.server.companion.utils.RolesUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.companion.utils.RolesUtils.lambda$removeRoleHolderForAssociation$0(i, str, str2, (java.lang.Boolean) obj);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeRoleHolderForAssociation$0(int i, java.lang.String str, java.lang.String str2, java.lang.Boolean bool) {
        if (!bool.booleanValue()) {
            android.util.Slog.e(TAG, "Failed to remove userId=" + i + ", packageName=" + str + " from the list of " + str2 + " holders.");
        }
    }
}
