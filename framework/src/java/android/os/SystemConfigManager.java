package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class SystemConfigManager {
    private static final java.lang.String TAG = android.os.SystemConfigManager.class.getSimpleName();
    private final android.os.ISystemConfig mInterface = android.os.ISystemConfig.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.SYSTEM_CONFIG_SERVICE));

    public java.util.Set<java.lang.String> getDisabledUntilUsedPreinstalledCarrierApps() {
        try {
            return new android.util.ArraySet(this.mInterface.getDisabledUntilUsedPreinstalledCarrierApps());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Caught remote exception");
            return java.util.Collections.emptySet();
        }
    }

    public java.util.Map<java.lang.String, java.util.List<java.lang.String>> getDisabledUntilUsedPreinstalledCarrierAssociatedApps() {
        try {
            return this.mInterface.getDisabledUntilUsedPreinstalledCarrierAssociatedApps();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Caught remote exception");
            return java.util.Collections.emptyMap();
        }
    }

    public java.util.Map<java.lang.String, java.util.List<android.os.CarrierAssociatedAppEntry>> getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() {
        try {
            return this.mInterface.getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Caught remote exception", e);
            return java.util.Collections.emptyMap();
        }
    }

    public int[] getSystemPermissionUids(java.lang.String str) {
        try {
            return this.mInterface.getSystemPermissionUids(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.util.List<android.content.ComponentName> getEnabledComponentOverrides(java.lang.String str) {
        try {
            return this.mInterface.getEnabledComponentOverrides(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.ComponentName> getDefaultVrComponents() {
        try {
            return this.mInterface.getDefaultVrComponents();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return java.util.Collections.emptyList();
        }
    }

    public java.util.List<java.lang.String> getPreventUserDisablePackages() {
        try {
            return this.mInterface.getPreventUserDisablePackages();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.util.Set<android.content.pm.SignedPackage> getEnhancedConfirmationTrustedPackages() {
        try {
            return (java.util.Set) this.mInterface.getEnhancedConfirmationTrustedPackages().stream().map(new android.os.SystemConfigManager$$ExternalSyntheticLambda0()).collect(java.util.stream.Collectors.toSet());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.util.Set<android.content.pm.SignedPackage> getEnhancedConfirmationTrustedInstallers() {
        try {
            return (java.util.Set) this.mInterface.getEnhancedConfirmationTrustedInstallers().stream().map(new android.os.SystemConfigManager$$ExternalSyntheticLambda0()).collect(java.util.stream.Collectors.toSet());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
