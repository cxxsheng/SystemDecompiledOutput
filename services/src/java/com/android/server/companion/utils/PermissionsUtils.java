package com.android.server.companion.utils;

/* loaded from: classes.dex */
public final class PermissionsUtils {
    private static final java.util.Map<java.lang.String, java.lang.String> DEVICE_PROFILE_TO_PERMISSION;
    private static com.android.internal.app.IAppOpsService sAppOpsService;

    private PermissionsUtils() {
    }

    static {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        arrayMap.put("android.app.role.COMPANION_DEVICE_WATCH", "android.permission.REQUEST_COMPANION_PROFILE_WATCH");
        arrayMap.put("android.app.role.COMPANION_DEVICE_APP_STREAMING", "android.permission.REQUEST_COMPANION_PROFILE_APP_STREAMING");
        arrayMap.put("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION", "android.permission.REQUEST_COMPANION_PROFILE_AUTOMOTIVE_PROJECTION");
        arrayMap.put("android.app.role.COMPANION_DEVICE_COMPUTER", "android.permission.REQUEST_COMPANION_PROFILE_COMPUTER");
        arrayMap.put("android.app.role.COMPANION_DEVICE_GLASSES", "android.permission.REQUEST_COMPANION_PROFILE_GLASSES");
        arrayMap.put("android.app.role.COMPANION_DEVICE_NEARBY_DEVICE_STREAMING", "android.permission.REQUEST_COMPANION_PROFILE_NEARBY_DEVICE_STREAMING");
        DEVICE_PROFILE_TO_PERMISSION = java.util.Collections.unmodifiableMap(arrayMap);
        sAppOpsService = null;
    }

    public static void enforcePermissionForCreatingAssociation(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.companion.AssociationRequest associationRequest, int i) {
        enforcePermissionForRequestingProfile(context, associationRequest.getDeviceProfile(), i);
        if (associationRequest.isSelfManaged()) {
            enforcePermissionForRequestingSelfManaged(context, i);
        }
    }

    public static void enforcePermissionForRequestingProfile(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable java.lang.String str, int i) {
        if (str == null) {
            return;
        }
        if (!DEVICE_PROFILE_TO_PERMISSION.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("Unsupported device profile: " + str);
        }
        java.lang.String str2 = DEVICE_PROFILE_TO_PERMISSION.get(str);
        if (context.checkPermission(str2, android.os.Binder.getCallingPid(), i) != 0) {
            throw new java.lang.SecurityException("Application must hold " + str2 + " to associate with a device with " + str + " profile.");
        }
    }

    public static void enforcePermissionForRequestingSelfManaged(@android.annotation.NonNull android.content.Context context, int i) {
        if (context.checkPermission("android.permission.REQUEST_COMPANION_SELF_MANAGED", android.os.Binder.getCallingPid(), i) != 0) {
            throw new java.lang.SecurityException("Application does not hold android.permission.REQUEST_COMPANION_SELF_MANAGED");
        }
    }

    public static boolean checkCallerCanInteractWithUserId(@android.annotation.NonNull android.content.Context context, int i) {
        return android.os.UserHandle.getCallingUserId() == i || context.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") == 0;
    }

    public static void enforceCallerCanInteractWithUserId(@android.annotation.NonNull android.content.Context context, int i) {
        if (android.os.UserHandle.getCallingUserId() == i) {
            return;
        }
        context.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", null);
    }

    public static void enforceCallerIsSystemOrCanInteractWithUserId(@android.annotation.NonNull android.content.Context context, int i) {
        if (android.os.Binder.getCallingUid() == 1000) {
            return;
        }
        enforceCallerCanInteractWithUserId(context, i);
    }

    public static boolean checkCallerIsSystemOr(int i, @android.annotation.NonNull java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 1000) {
            return true;
        }
        return android.os.UserHandle.getCallingUserId() == i && checkPackage(callingUid, str);
    }

    public static void enforceCallerIsSystemOr(int i, @android.annotation.NonNull java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 1000) {
            return;
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (android.os.UserHandle.getCallingUserId() != i) {
            throw new java.lang.SecurityException("Calling UserId (" + callingUserId + ") does not match the expected UserId (" + i + ")");
        }
        if (!checkPackage(callingUid, str)) {
            throw new java.lang.SecurityException(str + " doesn't belong to calling uid (" + callingUid + ")");
        }
    }

    public static boolean checkCallerCanManageCompanionDevice(@android.annotation.NonNull android.content.Context context) {
        return android.os.Binder.getCallingUid() == 1000 || context.checkCallingPermission("android.permission.MANAGE_COMPANION_DEVICES") == 0;
    }

    public static void enforceCallerCanManageAssociationsForPackage(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        if (checkCallerCanManageAssociationsForPackage(context, i, str)) {
            return;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Caller (uid=");
        sb.append(android.os.Binder.getCallingUid());
        sb.append(") does not have permissions to ");
        if (str2 == null) {
            str2 = "manage associations";
        }
        sb.append(str2);
        sb.append(" for u");
        sb.append(i);
        sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
        sb.append(str);
        throw new java.lang.SecurityException(sb.toString());
    }

    public static void enforceCallerCanObservingDevicePresenceByUuid(@android.annotation.NonNull android.content.Context context) {
        if (context.checkCallingPermission("android.permission.REQUEST_OBSERVE_DEVICE_UUID_PRESENCE") != 0) {
            throw new java.lang.SecurityException("Caller (uid=" + android.os.Binder.getCallingUid() + ") does not have permissions to request observing device presence base on the UUID");
        }
    }

    public static boolean checkCallerCanManageAssociationsForPackage(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull java.lang.String str) {
        if (checkCallerIsSystemOr(i, str)) {
            return true;
        }
        if (checkCallerCanInteractWithUserId(context, i)) {
            return checkCallerCanManageCompanionDevice(context);
        }
        return false;
    }

    @android.annotation.Nullable
    public static android.companion.AssociationInfo sanitizeWithCallerChecks(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable android.companion.AssociationInfo associationInfo) {
        if (associationInfo == null || !checkCallerCanManageAssociationsForPackage(context, associationInfo.getUserId(), associationInfo.getPackageName())) {
            return null;
        }
        return associationInfo;
    }

    private static boolean checkPackage(int i, @android.annotation.NonNull java.lang.String str) {
        try {
            return getAppOpsService().checkPackage(i, str) == 0;
        } catch (android.os.RemoteException e) {
            return true;
        }
    }

    private static com.android.internal.app.IAppOpsService getAppOpsService() {
        if (sAppOpsService == null) {
            synchronized (com.android.server.companion.utils.PermissionsUtils.class) {
                try {
                    if (sAppOpsService == null) {
                        sAppOpsService = com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService("appops"));
                    }
                } finally {
                }
            }
        }
        return sAppOpsService;
    }
}
