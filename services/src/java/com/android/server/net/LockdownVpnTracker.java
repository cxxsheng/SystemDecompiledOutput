package com.android.server.net;

/* loaded from: classes2.dex */
public class LockdownVpnTracker {
    public static final java.lang.String ACTION_LOCKDOWN_RESET = "com.android.server.action.LOCKDOWN_RESET";
    private static final java.lang.String TAG = "LockdownVpnTracker";

    @android.annotation.Nullable
    private java.lang.String mAcceptedEgressIface;

    @android.annotation.NonNull
    private final android.net.ConnectivityManager mCm;

    @android.annotation.NonNull
    private final android.app.PendingIntent mConfigIntent;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final android.app.NotificationManager mNotificationManager;

    @android.annotation.NonNull
    private final com.android.internal.net.VpnProfile mProfile;

    @android.annotation.NonNull
    private final android.app.PendingIntent mResetIntent;

    @android.annotation.NonNull
    private final com.android.server.connectivity.Vpn mVpn;

    @android.annotation.NonNull
    private final java.lang.Object mStateLock = new java.lang.Object();

    @android.annotation.NonNull
    private final com.android.server.net.LockdownVpnTracker.NetworkCallback mDefaultNetworkCallback = new com.android.server.net.LockdownVpnTracker.NetworkCallback();

    @android.annotation.NonNull
    private final com.android.server.net.LockdownVpnTracker.VpnNetworkCallback mVpnNetworkCallback = new com.android.server.net.LockdownVpnTracker.VpnNetworkCallback();

    private class NetworkCallback extends android.net.ConnectivityManager.NetworkCallback {
        private android.net.LinkProperties mLinkProperties;
        private android.net.Network mNetwork;

        private NetworkCallback() {
            this.mNetwork = null;
            this.mLinkProperties = null;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(android.net.Network network, android.net.LinkProperties linkProperties) {
            boolean z;
            if (network.equals(this.mNetwork)) {
                z = false;
            } else {
                this.mNetwork = network;
                z = true;
            }
            this.mLinkProperties = linkProperties;
            if (z) {
                synchronized (com.android.server.net.LockdownVpnTracker.this.mStateLock) {
                    com.android.server.net.LockdownVpnTracker.this.handleStateChangedLocked();
                }
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(android.net.Network network) {
            this.mNetwork = null;
            this.mLinkProperties = null;
            synchronized (com.android.server.net.LockdownVpnTracker.this.mStateLock) {
                com.android.server.net.LockdownVpnTracker.this.handleStateChangedLocked();
            }
        }

        public android.net.Network getNetwork() {
            return this.mNetwork;
        }

        public android.net.LinkProperties getLinkProperties() {
            return this.mLinkProperties;
        }
    }

    private class VpnNetworkCallback extends com.android.server.net.LockdownVpnTracker.NetworkCallback {
        private VpnNetworkCallback() {
            super();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(android.net.Network network) {
            synchronized (com.android.server.net.LockdownVpnTracker.this.mStateLock) {
                com.android.server.net.LockdownVpnTracker.this.handleStateChangedLocked();
            }
        }

        @Override // com.android.server.net.LockdownVpnTracker.NetworkCallback, android.net.ConnectivityManager.NetworkCallback
        public void onLost(android.net.Network network) {
            onAvailable(network);
        }
    }

    public LockdownVpnTracker(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.connectivity.Vpn vpn, @android.annotation.NonNull com.android.internal.net.VpnProfile vpnProfile) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        this.mCm = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
        java.util.Objects.requireNonNull(handler);
        this.mHandler = handler;
        java.util.Objects.requireNonNull(vpn);
        this.mVpn = vpn;
        java.util.Objects.requireNonNull(vpnProfile);
        this.mProfile = vpnProfile;
        this.mNotificationManager = (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
        this.mConfigIntent = android.app.PendingIntent.getActivity(this.mContext, 0, new android.content.Intent("android.settings.VPN_SETTINGS"), 67108864);
        android.content.Intent intent = new android.content.Intent(ACTION_LOCKDOWN_RESET);
        intent.addFlags(1073741824);
        this.mResetIntent = android.app.PendingIntent.getBroadcast(this.mContext, 0, intent, 67108864);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStateChangedLocked() {
        android.net.Network network = this.mDefaultNetworkCallback.getNetwork();
        android.net.LinkProperties linkProperties = this.mDefaultNetworkCallback.getLinkProperties();
        android.net.NetworkInfo networkInfo = this.mVpn.getNetworkInfo();
        com.android.internal.net.VpnConfig legacyVpnConfig = this.mVpn.getLegacyVpnConfig();
        boolean z = network == null;
        boolean z2 = linkProperties == null || !android.text.TextUtils.equals(this.mAcceptedEgressIface, linkProperties.getInterfaceName());
        java.lang.String interfaceName = linkProperties == null ? null : linkProperties.getInterfaceName();
        android.util.Log.d(TAG, "handleStateChanged: egress=" + this.mAcceptedEgressIface + "->" + interfaceName);
        if (z || z2) {
            this.mAcceptedEgressIface = null;
            this.mVpn.stopVpnRunnerPrivileged();
        }
        if (z) {
            hideNotification();
            return;
        }
        if (!networkInfo.isConnectedOrConnecting()) {
            if (!this.mProfile.isValidLockdownProfile()) {
                android.util.Log.e(TAG, "Invalid VPN profile; requires IP-based server and DNS");
                showNotification(android.R.string.user_creation_account_exists, android.R.drawable.vpn_connected);
                return;
            }
            android.util.Log.d(TAG, "Active network connected; starting VPN");
            showNotification(android.R.string.usb_uvc_notification_title, android.R.drawable.vpn_connected);
            this.mAcceptedEgressIface = interfaceName;
            try {
                this.mVpn.startLegacyVpnPrivileged(this.mProfile);
                return;
            } catch (java.lang.IllegalStateException e) {
                this.mAcceptedEgressIface = null;
                android.util.Log.e(TAG, "Failed to start VPN", e);
                showNotification(android.R.string.user_creation_account_exists, android.R.drawable.vpn_connected);
                return;
            }
        }
        if (networkInfo.isConnected() && legacyVpnConfig != null) {
            android.util.Log.d(TAG, "VPN connected using iface=" + legacyVpnConfig.interfaze + ", sourceAddr=" + legacyVpnConfig.addresses.toString());
            showNotification(android.R.string.usb_unsupported_audio_accessory_title, android.R.drawable.view_accessibility_focused);
        }
    }

    public void init() {
        synchronized (this.mStateLock) {
            initLocked();
        }
    }

    private void initLocked() {
        android.util.Log.d(TAG, "initLocked()");
        this.mVpn.setEnableTeardown(false);
        this.mVpn.setLockdown(true);
        this.mCm.setLegacyLockdownVpnEnabled(true);
        handleStateChangedLocked();
        this.mCm.registerSystemDefaultNetworkCallback(this.mDefaultNetworkCallback, this.mHandler);
        this.mCm.registerNetworkCallback(new android.net.NetworkRequest.Builder().clearCapabilities().addTransportType(4).build(), this.mVpnNetworkCallback, this.mHandler);
    }

    public void shutdown() {
        synchronized (this.mStateLock) {
            shutdownLocked();
        }
    }

    private void shutdownLocked() {
        android.util.Log.d(TAG, "shutdownLocked()");
        this.mAcceptedEgressIface = null;
        this.mVpn.stopVpnRunnerPrivileged();
        this.mVpn.setLockdown(false);
        this.mCm.setLegacyLockdownVpnEnabled(false);
        hideNotification();
        this.mVpn.setEnableTeardown(true);
        this.mCm.unregisterNetworkCallback(this.mDefaultNetworkCallback);
        this.mCm.unregisterNetworkCallback(this.mVpnNetworkCallback);
    }

    public void reset() {
        android.util.Log.d(TAG, "reset()");
        synchronized (this.mStateLock) {
            shutdownLocked();
            initLocked();
            handleStateChangedLocked();
        }
    }

    private void showNotification(int i, int i2) {
        this.mNotificationManager.notify(null, 20, new android.app.Notification.Builder(this.mContext, "VPN").setWhen(0L).setSmallIcon(i2).setContentTitle(this.mContext.getString(i)).setContentText(this.mContext.getString(android.R.string.usb_unsupported_audio_accessory_message)).setContentIntent(this.mConfigIntent).setOngoing(true).addAction(android.R.drawable.ic_menu_play_clip, this.mContext.getString(android.R.string.reboot_to_update_prepare), this.mResetIntent).setColor(this.mContext.getColor(android.R.color.system_notification_accent_color)).build());
    }

    private void hideNotification() {
        this.mNotificationManager.cancel(null, 20);
    }
}
