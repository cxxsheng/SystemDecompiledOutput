package com.android.server.accessibility;

/* loaded from: classes.dex */
public class AccessibilitySecurityPolicy {
    private static final int KEEP_SOURCE_EVENT_TYPES = 71547327;
    private static final java.lang.String LOG_TAG = "AccessibilitySecurityPolicy";
    private static final int OWN_PROCESS_ID = android.os.Process.myPid();
    private final com.android.server.accessibility.AccessibilitySecurityPolicy.AccessibilityUserManager mAccessibilityUserManager;
    private com.android.server.accessibility.AccessibilityWindowManager mAccessibilityWindowManager;
    private final android.app.AppOpsManager mAppOpsManager;
    private android.appwidget.AppWidgetManagerInternal mAppWidgetService;
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final com.android.server.accessibility.PolicyWarningUIController mPolicyWarningUIController;
    private final android.os.UserManager mUserManager;
    private final android.util.ArraySet<android.content.ComponentName> mNonA11yCategoryServices = new android.util.ArraySet<>();
    private int mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
    private boolean mSendNonA11yToolNotificationEnabled = false;

    public interface AccessibilityUserManager {
        int getCurrentUserIdLocked();

        @android.annotation.Nullable
        android.util.SparseBooleanArray getVisibleUserIdsLocked();
    }

    public AccessibilitySecurityPolicy(com.android.server.accessibility.PolicyWarningUIController policyWarningUIController, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.accessibility.AccessibilitySecurityPolicy.AccessibilityUserManager accessibilityUserManager, @android.annotation.NonNull android.content.pm.PackageManagerInternal packageManagerInternal) {
        this.mContext = context;
        this.mAccessibilityUserManager = accessibilityUserManager;
        this.mPackageManager = this.mContext.getPackageManager();
        this.mPackageManagerInternal = packageManagerInternal;
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService("user");
        this.mAppOpsManager = (android.app.AppOpsManager) context.getSystemService("appops");
        this.mPolicyWarningUIController = policyWarningUIController;
    }

    public void setSendingNonA11yToolNotificationLocked(boolean z) {
        if (z == this.mSendNonA11yToolNotificationEnabled) {
            return;
        }
        this.mSendNonA11yToolNotificationEnabled = z;
        this.mPolicyWarningUIController.enableSendingNonA11yToolNotification(z);
        if (z) {
            for (int i = 0; i < this.mNonA11yCategoryServices.size(); i++) {
                this.mPolicyWarningUIController.onNonA11yCategoryServiceBound(this.mCurrentUserId, this.mNonA11yCategoryServices.valueAt(i));
            }
        }
    }

    public void setAccessibilityWindowManager(@android.annotation.NonNull com.android.server.accessibility.AccessibilityWindowManager accessibilityWindowManager) {
        this.mAccessibilityWindowManager = accessibilityWindowManager;
    }

    public void setAppWidgetManager(@android.annotation.NonNull android.appwidget.AppWidgetManagerInternal appWidgetManagerInternal) {
        this.mAppWidgetService = appWidgetManagerInternal;
    }

    public boolean canDispatchAccessibilityEventLocked(int i, @android.annotation.NonNull android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        switch (accessibilityEvent.getEventType()) {
            case 32:
            case 64:
            case 128:
            case 256:
            case 512:
            case 1024:
            case 16384:
            case 262144:
            case 524288:
            case 1048576:
            case 2097152:
            case 4194304:
            case 16777216:
                return true;
            default:
                return isRetrievalAllowingWindowLocked(i, accessibilityEvent.getWindowId());
        }
    }

    @android.annotation.Nullable
    public java.lang.String resolveValidReportedPackageLocked(@android.annotation.Nullable java.lang.CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            return null;
        }
        if (i == 1000) {
            return charSequence.toString();
        }
        java.lang.String charSequence2 = charSequence.toString();
        int uid = android.os.UserHandle.getUid(i2, i);
        if (isValidPackageForUid(charSequence2, uid)) {
            return charSequence.toString();
        }
        if (this.mAppWidgetService != null && com.android.internal.util.ArrayUtils.contains(this.mAppWidgetService.getHostedWidgetPackages(uid), charSequence2)) {
            return charSequence.toString();
        }
        if (this.mContext.checkPermission("android.permission.ACT_AS_PACKAGE_FOR_ACCESSIBILITY", i3, uid) == 0) {
            return charSequence.toString();
        }
        java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(uid);
        if (com.android.internal.util.ArrayUtils.isEmpty(packagesForUid)) {
            return null;
        }
        return packagesForUid[0];
    }

    @android.annotation.NonNull
    public java.lang.String[] computeValidReportedPackages(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.ArraySet hostedWidgetPackages;
        if (android.os.UserHandle.getAppId(i) == 1000) {
            return libcore.util.EmptyArray.STRING;
        }
        java.lang.String[] strArr = {str};
        if (this.mAppWidgetService != null && (hostedWidgetPackages = this.mAppWidgetService.getHostedWidgetPackages(i)) != null && !hostedWidgetPackages.isEmpty()) {
            java.lang.String[] strArr2 = new java.lang.String[hostedWidgetPackages.size() + 1];
            int i2 = 0;
            java.lang.System.arraycopy(strArr, 0, strArr2, 0, 1);
            int size = hostedWidgetPackages.size();
            while (i2 < size) {
                int i3 = 1 + i2;
                strArr2[i3] = (java.lang.String) hostedWidgetPackages.valueAt(i2);
                i2 = i3;
            }
            return strArr2;
        }
        return strArr;
    }

    public void updateEventSourceLocked(@android.annotation.NonNull android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if ((accessibilityEvent.getEventType() & KEEP_SOURCE_EVENT_TYPES) == 0) {
            accessibilityEvent.setSource(null);
        }
    }

    public boolean canGetAccessibilityNodeInfoLocked(int i, @android.annotation.NonNull com.android.server.accessibility.AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection, int i2) {
        return canRetrieveWindowContentLocked(abstractAccessibilityServiceConnection) && isRetrievalAllowingWindowLocked(i, i2);
    }

    public boolean canRetrieveWindowsLocked(@android.annotation.NonNull com.android.server.accessibility.AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        return canRetrieveWindowContentLocked(abstractAccessibilityServiceConnection) && abstractAccessibilityServiceConnection.mRetrieveInteractiveWindows;
    }

    public boolean canRetrieveWindowContentLocked(@android.annotation.NonNull com.android.server.accessibility.AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        return (abstractAccessibilityServiceConnection.getCapabilities() & 1) != 0;
    }

    public boolean canControlMagnification(@android.annotation.NonNull com.android.server.accessibility.AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        return (abstractAccessibilityServiceConnection.getCapabilities() & 16) != 0;
    }

    public boolean canPerformGestures(@android.annotation.NonNull com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection) {
        return (accessibilityServiceConnection.getCapabilities() & 32) != 0;
    }

    public boolean canCaptureFingerprintGestures(@android.annotation.NonNull com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection) {
        return (accessibilityServiceConnection.getCapabilities() & 64) != 0;
    }

    public boolean canTakeScreenshotLocked(@android.annotation.NonNull com.android.server.accessibility.AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        return (abstractAccessibilityServiceConnection.getCapabilities() & 128) != 0;
    }

    int canEnableDisableInputMethod(java.lang.String str, com.android.server.accessibility.AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) throws java.lang.SecurityException {
        android.view.inputmethod.InputMethodInfo inputMethodInfo;
        java.lang.String packageName = abstractAccessibilityServiceConnection.getComponentName().getPackageName();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        java.util.List<android.view.inputmethod.InputMethodInfo> inputMethodListAsUser = com.android.server.inputmethod.InputMethodManagerInternal.get().getInputMethodListAsUser(callingUserId);
        if (inputMethodListAsUser != null) {
            java.util.Iterator<android.view.inputmethod.InputMethodInfo> it = inputMethodListAsUser.iterator();
            while (it.hasNext()) {
                inputMethodInfo = it.next();
                if (inputMethodInfo.getId().equals(str)) {
                    break;
                }
            }
        }
        inputMethodInfo = null;
        if (inputMethodInfo == null || !inputMethodInfo.getPackageName().equals(packageName)) {
            throw new java.lang.SecurityException("The input method is in a different package with the accessibility service");
        }
        if (com.android.server.accessibility.RestrictedLockUtilsInternal.checkIfInputMethodDisallowed(this.mContext, inputMethodInfo.getPackageName(), callingUserId) != null) {
            return 1;
        }
        return 0;
    }

    public int resolveProfileParentLocked(int i) {
        if (i != this.mAccessibilityUserManager.getCurrentUserIdLocked()) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.pm.UserInfo profileParent = this.mUserManager.getProfileParent(i);
                if (profileParent != null) {
                    return profileParent.getUserHandle().getIdentifier();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return i;
    }

    public int resolveCallingUserIdEnforcingPermissionsLocked(int i) {
        int callingUid = android.os.Binder.getCallingUid();
        int currentUserIdLocked = this.mAccessibilityUserManager.getCurrentUserIdLocked();
        if (callingUid == 0 || callingUid == 1000 || callingUid == 2000) {
            if (i == -2 || i == -3) {
                return currentUserIdLocked;
            }
            return resolveProfileParentLocked(i);
        }
        int userId = android.os.UserHandle.getUserId(callingUid);
        if (userId == i) {
            return resolveProfileParentLocked(i);
        }
        if (resolveProfileParentLocked(userId) == currentUserIdLocked && (i == -2 || i == -3)) {
            return currentUserIdLocked;
        }
        if (!hasPermission("android.permission.INTERACT_ACROSS_USERS") && !hasPermission("android.permission.INTERACT_ACROSS_USERS_FULL")) {
            throw new java.lang.SecurityException("Call from user " + userId + " as user " + i + " without permission INTERACT_ACROSS_USERS or INTERACT_ACROSS_USERS_FULL not allowed.");
        }
        if (i == -2 || i == -3) {
            return currentUserIdLocked;
        }
        return resolveProfileParentLocked(i);
    }

    public boolean isCallerInteractingAcrossUsers(int i) {
        return android.os.Binder.getCallingPid() == android.os.Process.myPid() || android.os.Binder.getCallingUid() == 2000 || i == -2 || i == -3;
    }

    private boolean isValidPackageForUid(java.lang.String str, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mPackageManagerInternal.isSameApp(str, 4194304L, i, android.os.UserHandle.getUserId(i));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean isRetrievalAllowingWindowLocked(int i, int i2) {
        if (android.os.Binder.getCallingUid() == 1000) {
            return true;
        }
        if (android.os.Binder.getCallingUid() != 2000 || isShellAllowedToRetrieveWindowLocked(i, i2)) {
            return this.mAccessibilityWindowManager.resolveParentWindowIdLocked(i2) == this.mAccessibilityWindowManager.getActiveWindowId(i) || this.mAccessibilityWindowManager.findA11yWindowInfoByIdLocked(i2) != null;
        }
        return false;
    }

    private boolean isShellAllowedToRetrieveWindowLocked(int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.os.IBinder windowTokenForUserAndWindowIdLocked = this.mAccessibilityWindowManager.getWindowTokenForUserAndWindowIdLocked(i, i2);
            if (windowTokenForUserAndWindowIdLocked == null) {
                return false;
            }
            if (this.mAccessibilityWindowManager.getWindowOwnerUserId(windowTokenForUserAndWindowIdLocked) == -10000) {
                return false;
            }
            return !this.mUserManager.hasUserRestriction("no_debugging_features", android.os.UserHandle.of(r4));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void enforceCallingPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        if (OWN_PROCESS_ID != android.os.Binder.getCallingPid() && !hasPermission(str)) {
            throw new java.lang.SecurityException("You do not have " + str + " required to call " + str2 + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid());
        }
    }

    public boolean hasPermission(@android.annotation.NonNull java.lang.String str) {
        return this.mContext.checkCallingPermission(str) == 0;
    }

    public boolean canRegisterService(@android.annotation.NonNull android.content.pm.ServiceInfo serviceInfo) {
        if (!"android.permission.BIND_ACCESSIBILITY_SERVICE".equals(serviceInfo.permission)) {
            android.util.Slog.w(LOG_TAG, "Skipping accessibility service " + new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString() + ": it does not require the permission android.permission.BIND_ACCESSIBILITY_SERVICE");
            return false;
        }
        if ((serviceInfo.flags & 4) != 0) {
            android.util.Slog.w(LOG_TAG, "Skipping accessibility service " + new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString() + ": the service is the external one and doesn't allow to register as an accessibility service ");
            return false;
        }
        if (this.mAppOpsManager.noteOpNoThrow("android:bind_accessibility_service", serviceInfo.applicationInfo.uid, serviceInfo.packageName, null, null) != 0) {
            android.util.Slog.w(LOG_TAG, "Skipping accessibility service " + new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString() + ": disallowed by AppOps");
            return false;
        }
        return true;
    }

    public boolean checkAccessibilityAccess(com.android.server.accessibility.AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        java.lang.String packageName = abstractAccessibilityServiceConnection.getComponentName().getPackageName();
        android.content.pm.ResolveInfo resolveInfo = abstractAccessibilityServiceConnection.getServiceInfo().getResolveInfo();
        if (resolveInfo == null) {
            return true;
        }
        int i = resolveInfo.serviceInfo.applicationInfo.uid;
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        java.lang.String attributionTag = abstractAccessibilityServiceConnection.getAttributionTag();
        try {
            if (OWN_PROCESS_ID == callingPid) {
                return this.mAppOpsManager.noteOpNoThrow("android:access_accessibility", i, packageName, attributionTag, null) == 0;
            }
            return this.mAppOpsManager.noteOp("android:access_accessibility", i, packageName, attributionTag, null) == 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void enforceCallingOrSelfPermission(@android.annotation.NonNull java.lang.String str) {
        if (this.mContext.checkCallingOrSelfPermission(str) != 0) {
            throw new java.lang.SecurityException("Caller does not hold permission " + str);
        }
    }

    public void checkForAccessibilityPermissionOrRole() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_ACCESSIBILITY") == 0) {
            return;
        }
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.role.RoleManager roleManager = (android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class);
            if (roleManager != null) {
                java.util.List roleHoldersAsUser = roleManager.getRoleHoldersAsUser("android.app.role.COMPANION_DEVICE_APP_STREAMING", android.os.UserHandle.getUserHandleForUid(callingUid));
                java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(callingUid);
                if (packagesForUid != null) {
                    for (java.lang.String str : packagesForUid) {
                        if (roleHoldersAsUser.contains(str)) {
                            return;
                        }
                    }
                }
            }
            throw new java.lang.SecurityException("Cannot register a proxy for a device without the android.app.role.COMPANION_DEVICE_APP_STREAMING role or the MANAGE_ACCESSIBILITY permission.");
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onBoundServicesChangedLocked(int i, java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList) {
        if (this.mAccessibilityUserManager.getCurrentUserIdLocked() != i) {
            return;
        }
        android.util.ArraySet<? extends android.content.ComponentName> arraySet = new android.util.ArraySet<>();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            android.accessibilityservice.AccessibilityServiceInfo serviceInfo = arrayList.get(i2).getServiceInfo();
            android.content.ComponentName clone = serviceInfo.getComponentName().clone();
            if (!serviceInfo.isAccessibilityTool()) {
                arraySet.add(clone);
                if (this.mNonA11yCategoryServices.contains(clone)) {
                    this.mNonA11yCategoryServices.remove(clone);
                } else if (this.mSendNonA11yToolNotificationEnabled) {
                    this.mPolicyWarningUIController.onNonA11yCategoryServiceBound(i, clone);
                }
            }
        }
        for (int i3 = 0; i3 < this.mNonA11yCategoryServices.size(); i3++) {
            this.mPolicyWarningUIController.onNonA11yCategoryServiceUnbound(i, this.mNonA11yCategoryServices.valueAt(i3));
        }
        this.mNonA11yCategoryServices.clear();
        this.mNonA11yCategoryServices.addAll(arraySet);
    }

    public void onSwitchUserLocked(int i, java.util.Set<android.content.ComponentName> set) {
        if (this.mCurrentUserId == i) {
            return;
        }
        this.mPolicyWarningUIController.onSwitchUser(i, new android.util.ArraySet(set));
        for (int i2 = 0; i2 < this.mNonA11yCategoryServices.size(); i2++) {
            this.mPolicyWarningUIController.onNonA11yCategoryServiceUnbound(this.mCurrentUserId, this.mNonA11yCategoryServices.valueAt(i2));
        }
        this.mNonA11yCategoryServices.clear();
        this.mCurrentUserId = i;
    }

    public void onEnabledServicesChangedLocked(int i, java.util.Set<android.content.ComponentName> set) {
        if (this.mAccessibilityUserManager.getCurrentUserIdLocked() != i) {
            return;
        }
        this.mPolicyWarningUIController.onEnabledServicesChanged(i, new android.util.ArraySet(set));
    }
}
