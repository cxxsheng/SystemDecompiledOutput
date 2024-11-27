package com.android.server.accessibility;

/* loaded from: classes.dex */
public class RestrictedLockUtilsInternal {
    public static com.android.settingslib.RestrictedLockUtils.EnforcedAdmin checkIfAccessibilityServiceDisallowed(android.content.Context context, java.lang.String str, int i) {
        boolean z;
        android.app.admin.DevicePolicyManager devicePolicyManager = (android.app.admin.DevicePolicyManager) context.getSystemService(android.app.admin.DevicePolicyManager.class);
        if (devicePolicyManager == null) {
            return null;
        }
        com.android.settingslib.RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner = com.android.settingslib.RestrictedLockUtils.getProfileOrDeviceOwner(context, getUserHandleOf(i));
        boolean z2 = true;
        if (profileOrDeviceOwner == null) {
            z = true;
        } else {
            z = devicePolicyManager.isAccessibilityServicePermittedByAdmin(profileOrDeviceOwner.component, str, i);
        }
        int managedProfileId = getManagedProfileId(context, i);
        com.android.settingslib.RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner2 = com.android.settingslib.RestrictedLockUtils.getProfileOrDeviceOwner(context, getUserHandleOf(managedProfileId));
        if (profileOrDeviceOwner2 != null) {
            z2 = devicePolicyManager.isAccessibilityServicePermittedByAdmin(profileOrDeviceOwner2.component, str, managedProfileId);
        }
        if (!z && !z2) {
            return com.android.settingslib.RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
        }
        if (!z) {
            return profileOrDeviceOwner;
        }
        if (z2) {
            return null;
        }
        return profileOrDeviceOwner2;
    }

    public static com.android.settingslib.RestrictedLockUtils.EnforcedAdmin checkIfInputMethodDisallowed(android.content.Context context, java.lang.String str, int i) {
        boolean z;
        com.android.settingslib.RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
        android.app.admin.DevicePolicyManager devicePolicyManager = (android.app.admin.DevicePolicyManager) context.getSystemService(android.app.admin.DevicePolicyManager.class);
        if (devicePolicyManager == null) {
            return null;
        }
        com.android.settingslib.RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner = com.android.settingslib.RestrictedLockUtils.getProfileOrDeviceOwner(context, getUserHandleOf(i));
        boolean z2 = true;
        if (profileOrDeviceOwner == null) {
            z = true;
        } else {
            z = devicePolicyManager.isInputMethodPermittedByAdmin(profileOrDeviceOwner.component, str, i);
        }
        int managedProfileId = getManagedProfileId(context, i);
        if (managedProfileId == -10000) {
            enforcedAdmin = null;
        } else {
            enforcedAdmin = com.android.settingslib.RestrictedLockUtils.getProfileOrDeviceOwner(context, getUserHandleOf(managedProfileId));
            if (enforcedAdmin != null && devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()) {
                z2 = devicePolicyManager.getParentProfileInstance(android.os.UserManager.get(context).getUserInfo(managedProfileId)).isInputMethodPermittedByAdmin(enforcedAdmin.component, str, managedProfileId);
            }
        }
        if (!z && !z2) {
            return com.android.settingslib.RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
        }
        if (!z) {
            return profileOrDeviceOwner;
        }
        if (z2) {
            return null;
        }
        return enforcedAdmin;
    }

    private static int getManagedProfileId(android.content.Context context, int i) {
        for (android.content.pm.UserInfo userInfo : ((android.os.UserManager) context.getSystemService(android.os.UserManager.class)).getProfiles(i)) {
            if (userInfo.id != i && userInfo.isManagedProfile()) {
                return userInfo.id;
            }
        }
        return com.android.server.am.ProcessList.INVALID_ADJ;
    }

    private static android.os.UserHandle getUserHandleOf(int i) {
        if (i == -10000) {
            return null;
        }
        return android.os.UserHandle.of(i);
    }
}
