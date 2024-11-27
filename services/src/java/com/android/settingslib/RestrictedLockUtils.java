package com.android.settingslib;

/* loaded from: classes3.dex */
public class RestrictedLockUtils {
    @androidx.annotation.RequiresApi(23)
    public static com.android.settingslib.RestrictedLockUtils.EnforcedAdmin getProfileOrDeviceOwner(android.content.Context context, android.os.UserHandle userHandle) {
        return getProfileOrDeviceOwner(context, null, userHandle);
    }

    @androidx.annotation.RequiresApi(23)
    public static com.android.settingslib.RestrictedLockUtils.EnforcedAdmin getProfileOrDeviceOwner(android.content.Context context, java.lang.String str, android.os.UserHandle userHandle) {
        android.app.admin.DevicePolicyManager devicePolicyManager;
        android.content.ComponentName deviceOwnerComponentOnAnyUser;
        if (userHandle == null || (devicePolicyManager = (android.app.admin.DevicePolicyManager) context.getSystemService("device_policy")) == null) {
            return null;
        }
        try {
            android.content.ComponentName profileOwner = ((android.app.admin.DevicePolicyManager) context.createPackageContextAsUser(context.getPackageName(), 0, userHandle).getSystemService(android.app.admin.DevicePolicyManager.class)).getProfileOwner();
            if (profileOwner != null) {
                return new com.android.settingslib.RestrictedLockUtils.EnforcedAdmin(profileOwner, str, userHandle);
            }
            if (!java.util.Objects.equals(devicePolicyManager.getDeviceOwnerUser(), userHandle) || (deviceOwnerComponentOnAnyUser = devicePolicyManager.getDeviceOwnerComponentOnAnyUser()) == null) {
                return null;
            }
            return new com.android.settingslib.RestrictedLockUtils.EnforcedAdmin(deviceOwnerComponentOnAnyUser, str, userHandle);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    @androidx.annotation.RequiresApi(23)
    public static void sendShowAdminSupportDetailsIntent(android.content.Context context, com.android.settingslib.RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        android.content.Intent showAdminSupportDetailsIntent = getShowAdminSupportDetailsIntent(context, enforcedAdmin);
        int myUserId = android.os.UserHandle.myUserId();
        if (enforcedAdmin != null) {
            if (enforcedAdmin.user != null && isCurrentUserOrProfile(context, enforcedAdmin.user.getIdentifier())) {
                myUserId = enforcedAdmin.user.getIdentifier();
            }
            showAdminSupportDetailsIntent.putExtra("android.app.extra.RESTRICTION", enforcedAdmin.enforcedRestriction);
        }
        context.startActivityAsUser(showAdminSupportDetailsIntent, android.os.UserHandle.of(myUserId));
    }

    public static android.content.Intent getShowAdminSupportDetailsIntent(android.content.Context context, com.android.settingslib.RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        android.content.Intent intent = new android.content.Intent("android.settings.SHOW_ADMIN_SUPPORT_DETAILS");
        if (enforcedAdmin != null) {
            if (enforcedAdmin.component != null) {
                intent.putExtra("android.app.extra.DEVICE_ADMIN", enforcedAdmin.component);
            }
            intent.putExtra("android.intent.extra.USER", enforcedAdmin.user);
        }
        return intent;
    }

    @androidx.annotation.RequiresApi(23)
    public static boolean isCurrentUserOrProfile(android.content.Context context, int i) {
        return ((android.os.UserManager) context.getSystemService(android.os.UserManager.class)).getUserProfiles().contains(android.os.UserHandle.of(i));
    }

    public static class EnforcedAdmin {
        public static final com.android.settingslib.RestrictedLockUtils.EnforcedAdmin MULTIPLE_ENFORCED_ADMIN = new com.android.settingslib.RestrictedLockUtils.EnforcedAdmin();

        @androidx.annotation.Nullable
        public android.content.ComponentName component;

        @androidx.annotation.Nullable
        public java.lang.String enforcedRestriction;

        @androidx.annotation.Nullable
        public android.os.UserHandle user;

        public static com.android.settingslib.RestrictedLockUtils.EnforcedAdmin createDefaultEnforcedAdminWithRestriction(java.lang.String str) {
            com.android.settingslib.RestrictedLockUtils.EnforcedAdmin enforcedAdmin = new com.android.settingslib.RestrictedLockUtils.EnforcedAdmin();
            enforcedAdmin.enforcedRestriction = str;
            return enforcedAdmin;
        }

        public EnforcedAdmin(android.content.ComponentName componentName, android.os.UserHandle userHandle) {
            this.component = null;
            this.enforcedRestriction = null;
            this.user = null;
            this.component = componentName;
            this.user = userHandle;
        }

        public EnforcedAdmin(android.content.ComponentName componentName, java.lang.String str, android.os.UserHandle userHandle) {
            this.component = null;
            this.enforcedRestriction = null;
            this.user = null;
            this.component = componentName;
            this.enforcedRestriction = str;
            this.user = userHandle;
        }

        public EnforcedAdmin(com.android.settingslib.RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
            this.component = null;
            this.enforcedRestriction = null;
            this.user = null;
            if (enforcedAdmin == null) {
                throw new java.lang.IllegalArgumentException();
            }
            this.component = enforcedAdmin.component;
            this.enforcedRestriction = enforcedAdmin.enforcedRestriction;
            this.user = enforcedAdmin.user;
        }

        public EnforcedAdmin() {
            this.component = null;
            this.enforcedRestriction = null;
            this.user = null;
        }

        public static com.android.settingslib.RestrictedLockUtils.EnforcedAdmin combine(com.android.settingslib.RestrictedLockUtils.EnforcedAdmin enforcedAdmin, com.android.settingslib.RestrictedLockUtils.EnforcedAdmin enforcedAdmin2) {
            if (enforcedAdmin == null) {
                return enforcedAdmin2;
            }
            if (enforcedAdmin2 == null) {
                return enforcedAdmin;
            }
            if (enforcedAdmin.equals(enforcedAdmin2)) {
                return enforcedAdmin;
            }
            if (!enforcedAdmin.enforcedRestriction.equals(enforcedAdmin2.enforcedRestriction)) {
                throw new java.lang.IllegalArgumentException("Admins with different restriction cannot be combined");
            }
            return MULTIPLE_ENFORCED_ADMIN;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.settingslib.RestrictedLockUtils.EnforcedAdmin enforcedAdmin = (com.android.settingslib.RestrictedLockUtils.EnforcedAdmin) obj;
            if (java.util.Objects.equals(this.user, enforcedAdmin.user) && java.util.Objects.equals(this.component, enforcedAdmin.component) && java.util.Objects.equals(this.enforcedRestriction, enforcedAdmin.enforcedRestriction)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.component, this.enforcedRestriction, this.user);
        }

        public java.lang.String toString() {
            return "EnforcedAdmin{component=" + this.component + ", enforcedRestriction='" + this.enforcedRestriction + ", user=" + this.user + '}';
        }
    }

    @androidx.annotation.RequiresApi(33)
    @java.lang.Deprecated
    public static void sendShowRestrictedSettingDialogIntent(android.content.Context context, java.lang.String str, int i) {
        context.startActivity(getShowRestrictedSettingsIntent(str, i));
    }

    @java.lang.Deprecated
    private static android.content.Intent getShowRestrictedSettingsIntent(java.lang.String str, int i) {
        android.content.Intent intent = new android.content.Intent("android.settings.SHOW_RESTRICTED_SETTING_DIALOG");
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra("android.intent.extra.UID", i);
        return intent;
    }
}
