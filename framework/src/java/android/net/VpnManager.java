package android.net;

/* loaded from: classes2.dex */
public class VpnManager {
    public static final java.lang.String ACTION_VPN_MANAGER_EVENT = "android.net.action.VPN_MANAGER_EVENT";
    public static final java.lang.String CATEGORY_EVENT_ALWAYS_ON_STATE_CHANGED = "android.net.category.EVENT_ALWAYS_ON_STATE_CHANGED";
    public static final java.lang.String CATEGORY_EVENT_DEACTIVATED_BY_USER = "android.net.category.EVENT_DEACTIVATED_BY_USER";
    public static final java.lang.String CATEGORY_EVENT_IKE_ERROR = "android.net.category.EVENT_IKE_ERROR";
    public static final java.lang.String CATEGORY_EVENT_NETWORK_ERROR = "android.net.category.EVENT_NETWORK_ERROR";
    public static final int ERROR_CLASS_NOT_RECOVERABLE = 1;
    public static final int ERROR_CLASS_RECOVERABLE = 2;
    public static final int ERROR_CODE_NETWORK_IO = 3;
    public static final int ERROR_CODE_NETWORK_LOST = 2;
    public static final int ERROR_CODE_NETWORK_PROTOCOL_TIMEOUT = 1;
    public static final int ERROR_CODE_NETWORK_UNKNOWN_HOST = 0;
    public static final java.lang.String EXTRA_ERROR_CLASS = "android.net.extra.ERROR_CLASS";
    public static final java.lang.String EXTRA_ERROR_CODE = "android.net.extra.ERROR_CODE";
    public static final java.lang.String EXTRA_SESSION_KEY = "android.net.extra.SESSION_KEY";
    public static final java.lang.String EXTRA_TIMESTAMP_MILLIS = "android.net.extra.TIMESTAMP_MILLIS";
    public static final java.lang.String EXTRA_UNDERLYING_LINK_PROPERTIES = "android.net.extra.UNDERLYING_LINK_PROPERTIES";
    public static final java.lang.String EXTRA_UNDERLYING_NETWORK = "android.net.extra.UNDERLYING_NETWORK";
    public static final java.lang.String EXTRA_UNDERLYING_NETWORK_CAPABILITIES = "android.net.extra.UNDERLYING_NETWORK_CAPABILITIES";
    public static final java.lang.String EXTRA_VPN_PROFILE_STATE = "android.net.extra.VPN_PROFILE_STATE";
    public static final java.lang.String NOTIFICATION_CHANNEL_VPN = "VPN";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_LEGACY = 3;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_NONE = -1;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_OEM = 4;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_PLATFORM = 2;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int TYPE_VPN_SERVICE = 1;
    private final android.content.Context mContext;
    private final android.net.IVpnManager mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VpnType {
    }

    private static android.content.Intent getIntentForConfirmation() {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(android.content.ComponentName.unflattenFromString(android.content.res.Resources.getSystem().getString(com.android.internal.R.string.config_platformVpnConfirmDialogComponent)));
        return intent;
    }

    public VpnManager(android.content.Context context, android.net.IVpnManager iVpnManager) {
        this.mContext = (android.content.Context) com.android.internal.util.Preconditions.checkNotNull(context, "missing Context");
        this.mService = (android.net.IVpnManager) com.android.internal.util.Preconditions.checkNotNull(iVpnManager, "missing IVpnManager");
    }

    public android.content.Intent provisionVpnProfile(android.net.PlatformVpnProfile platformVpnProfile) {
        try {
            try {
                if (this.mService.provisionVpnProfile(platformVpnProfile.toVpnProfile(), this.mContext.getOpPackageName())) {
                    return null;
                }
                return getIntentForConfirmation();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (java.io.IOException | java.security.GeneralSecurityException e2) {
            throw new java.lang.IllegalArgumentException("Failed to serialize PlatformVpnProfile", e2);
        }
    }

    public void deleteProvisionedVpnProfile() {
        try {
            this.mService.deleteVpnProfile(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String startProvisionedVpnProfileSession() {
        try {
            return this.mService.startVpnProfile(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void startProvisionedVpnProfile() {
        startProvisionedVpnProfileSession();
    }

    public void stopProvisionedVpnProfile() {
        try {
            this.mService.stopVpnProfile(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public com.android.internal.net.VpnConfig getVpnConfig(int i) {
        try {
            return this.mService.getVpnConfig(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.net.VpnProfileState getProvisionedVpnProfileState() {
        try {
            return this.mService.getProvisionedVpnProfileState(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void factoryReset() {
        try {
            this.mService.factoryReset();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean prepareVpn(java.lang.String str, java.lang.String str2, int i) {
        try {
            return this.mService.prepareVpn(str, str2, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVpnPackageAuthorization(java.lang.String str, int i, int i2) {
        try {
            this.mService.setVpnPackageAuthorization(str, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAlwaysOnVpnPackageSupportedForUser(int i, java.lang.String str) {
        try {
            return this.mService.isAlwaysOnVpnPackageSupported(i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setAlwaysOnVpnPackageForUser(int i, java.lang.String str, boolean z, java.util.List<java.lang.String> list) {
        try {
            return this.mService.setAlwaysOnVpnPackage(i, str, z, list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getAlwaysOnVpnPackageForUser(int i) {
        try {
            return this.mService.getAlwaysOnVpnPackage(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVpnLockdownEnabled(int i) {
        try {
            return this.mService.isVpnLockdownEnabled(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setAppExclusionList(int i, java.lang.String str, java.util.List<java.lang.String> list) {
        try {
            return this.mService.setAppExclusionList(i, str, list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<java.lang.String> getAppExclusionList(int i, java.lang.String str) {
        try {
            return this.mService.getAppExclusionList(i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<java.lang.String> getVpnLockdownAllowlist(int i) {
        try {
            return this.mService.getVpnLockdownAllowlist(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public com.android.internal.net.LegacyVpnInfo getLegacyVpnInfo(int i) {
        try {
            return this.mService.getLegacyVpnInfo(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startLegacyVpn(com.android.internal.net.VpnProfile vpnProfile) {
        try {
            this.mService.startLegacyVpn(vpnProfile);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateLockdownVpn() {
        try {
            return this.mService.updateLockdownVpn();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public com.android.internal.net.VpnProfile[] getAllLegacyVpns() {
        try {
            return this.mService.getAllLegacyVpns();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
