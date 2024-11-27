package android.app.admin;

/* loaded from: classes.dex */
public abstract class DeviceStateCache {
    public abstract boolean isDeviceProvisioned();

    public abstract boolean isUserOrganizationManaged(int i);

    protected DeviceStateCache() {
    }

    public static android.app.admin.DeviceStateCache getInstance() {
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        return devicePolicyManagerInternal != null ? devicePolicyManagerInternal.getDeviceStateCache() : android.app.admin.DeviceStateCache.EmptyDeviceStateCache.INSTANCE;
    }

    public boolean hasAffiliationWithDevice(int i) {
        return false;
    }

    private static class EmptyDeviceStateCache extends android.app.admin.DeviceStateCache {
        private static final android.app.admin.DeviceStateCache.EmptyDeviceStateCache INSTANCE = new android.app.admin.DeviceStateCache.EmptyDeviceStateCache();

        private EmptyDeviceStateCache() {
        }

        @Override // android.app.admin.DeviceStateCache
        public boolean isDeviceProvisioned() {
            return false;
        }

        @Override // android.app.admin.DeviceStateCache
        public boolean isUserOrganizationManaged(int i) {
            return false;
        }
    }
}
