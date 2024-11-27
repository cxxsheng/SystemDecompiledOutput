package android.app.admin;

/* loaded from: classes.dex */
public abstract class DevicePolicyCache {
    public abstract boolean canAdminGrantSensorsPermissions();

    public abstract int getContentProtectionPolicy(int i);

    public abstract java.util.Map<java.lang.String, java.lang.String> getLauncherShortcutOverrides();

    public abstract int getPasswordQuality(int i);

    public abstract int getPermissionPolicy(int i);

    public abstract boolean isScreenCaptureAllowed(int i);

    protected DevicePolicyCache() {
    }

    public static android.app.admin.DevicePolicyCache getInstance() {
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        return devicePolicyManagerInternal != null ? devicePolicyManagerInternal.getDevicePolicyCache() : android.app.admin.DevicePolicyCache.EmptyDevicePolicyCache.INSTANCE;
    }

    private static class EmptyDevicePolicyCache extends android.app.admin.DevicePolicyCache {
        private static final android.app.admin.DevicePolicyCache.EmptyDevicePolicyCache INSTANCE = new android.app.admin.DevicePolicyCache.EmptyDevicePolicyCache();

        private EmptyDevicePolicyCache() {
        }

        @Override // android.app.admin.DevicePolicyCache
        public boolean isScreenCaptureAllowed(int i) {
            return true;
        }

        @Override // android.app.admin.DevicePolicyCache
        public int getPasswordQuality(int i) {
            return 0;
        }

        @Override // android.app.admin.DevicePolicyCache
        public int getPermissionPolicy(int i) {
            return 0;
        }

        @Override // android.app.admin.DevicePolicyCache
        public int getContentProtectionPolicy(int i) {
            return 1;
        }

        @Override // android.app.admin.DevicePolicyCache
        public boolean canAdminGrantSensorsPermissions() {
            return false;
        }

        @Override // android.app.admin.DevicePolicyCache
        public java.util.Map<java.lang.String, java.lang.String> getLauncherShortcutOverrides() {
            return java.util.Collections.EMPTY_MAP;
        }
    }
}
