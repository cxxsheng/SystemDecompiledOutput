package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class BatteryStatsManager {
    public static final int NUM_WIFI_STATES = 8;
    public static final int NUM_WIFI_SUPPL_STATES = 13;
    public static final int WIFI_STATE_OFF = 0;
    public static final int WIFI_STATE_OFF_SCANNING = 1;
    public static final int WIFI_STATE_ON_CONNECTED_P2P = 5;
    public static final int WIFI_STATE_ON_CONNECTED_STA = 4;
    public static final int WIFI_STATE_ON_CONNECTED_STA_P2P = 6;
    public static final int WIFI_STATE_ON_DISCONNECTED = 3;
    public static final int WIFI_STATE_ON_NO_NETWORKS = 2;
    public static final int WIFI_STATE_SOFT_AP = 7;
    public static final int WIFI_SUPPL_STATE_ASSOCIATED = 7;
    public static final int WIFI_SUPPL_STATE_ASSOCIATING = 6;
    public static final int WIFI_SUPPL_STATE_AUTHENTICATING = 5;
    public static final int WIFI_SUPPL_STATE_COMPLETED = 10;
    public static final int WIFI_SUPPL_STATE_DISCONNECTED = 1;
    public static final int WIFI_SUPPL_STATE_DORMANT = 11;
    public static final int WIFI_SUPPL_STATE_FOUR_WAY_HANDSHAKE = 8;
    public static final int WIFI_SUPPL_STATE_GROUP_HANDSHAKE = 9;
    public static final int WIFI_SUPPL_STATE_INACTIVE = 3;
    public static final int WIFI_SUPPL_STATE_INTERFACE_DISABLED = 2;
    public static final int WIFI_SUPPL_STATE_INVALID = 0;
    public static final int WIFI_SUPPL_STATE_SCANNING = 4;
    public static final int WIFI_SUPPL_STATE_UNINITIALIZED = 12;
    private final com.android.internal.app.IBatteryStats mBatteryStats;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WifiState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WifiSupplState {
    }

    public BatteryStatsManager(com.android.internal.app.IBatteryStats iBatteryStats) {
        this.mBatteryStats = iBatteryStats;
    }

    public android.os.BatteryUsageStats getBatteryUsageStats() {
        return getBatteryUsageStats(android.os.BatteryUsageStatsQuery.DEFAULT);
    }

    public android.os.BatteryUsageStats getBatteryUsageStats(android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        return getBatteryUsageStats(java.util.List.of(batteryUsageStatsQuery)).get(0);
    }

    public java.util.List<android.os.BatteryUsageStats> getBatteryUsageStats(java.util.List<android.os.BatteryUsageStatsQuery> list) {
        try {
            return this.mBatteryStats.getBatteryUsageStats(list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportWifiRssiChanged(int i) {
        try {
            this.mBatteryStats.noteWifiRssiChanged(i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiOn() {
        try {
            this.mBatteryStats.noteWifiOn();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiOff() {
        try {
            this.mBatteryStats.noteWifiOff();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiState(int i, java.lang.String str) {
        try {
            this.mBatteryStats.noteWifiState(i, str);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiScanStartedFromSource(android.os.WorkSource workSource) {
        try {
            this.mBatteryStats.noteWifiScanStartedFromSource(workSource);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiScanStoppedFromSource(android.os.WorkSource workSource) {
        try {
            this.mBatteryStats.noteWifiScanStoppedFromSource(workSource);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiBatchedScanStartedFromSource(android.os.WorkSource workSource, int i) {
        try {
            this.mBatteryStats.noteWifiBatchedScanStartedFromSource(workSource, i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiBatchedScanStoppedFromSource(android.os.WorkSource workSource) {
        try {
            this.mBatteryStats.noteWifiBatchedScanStoppedFromSource(workSource);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public android.os.connectivity.CellularBatteryStats getCellularBatteryStats() {
        try {
            return this.mBatteryStats.getCellularBatteryStats();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public android.os.connectivity.WifiBatteryStats getWifiBatteryStats() {
        try {
            return this.mBatteryStats.getWifiBatteryStats();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public android.os.WakeLockStats getWakeLockStats() {
        try {
            return this.mBatteryStats.getWakeLockStats();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.BluetoothBatteryStats getBluetoothBatteryStats() {
        try {
            return this.mBatteryStats.getBluetoothBatteryStats();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportFullWifiLockAcquiredFromSource(android.os.WorkSource workSource) {
        try {
            this.mBatteryStats.noteFullWifiLockAcquiredFromSource(workSource);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportFullWifiLockReleasedFromSource(android.os.WorkSource workSource) {
        try {
            this.mBatteryStats.noteFullWifiLockReleasedFromSource(workSource);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiSupplicantStateChanged(int i, boolean z) {
        try {
            this.mBatteryStats.noteWifiSupplicantStateChanged(i, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiMulticastEnabled(android.os.WorkSource workSource) {
        try {
            this.mBatteryStats.noteWifiMulticastEnabled(workSource.getAttributionUid());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiMulticastDisabled(android.os.WorkSource workSource) {
        try {
            this.mBatteryStats.noteWifiMulticastDisabled(workSource.getAttributionUid());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportMobileRadioPowerState(boolean z, int i) {
        try {
            this.mBatteryStats.noteMobileRadioPowerState(getDataConnectionPowerState(z), android.os.SystemClock.elapsedRealtimeNanos(), i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiRadioPowerState(boolean z, int i) {
        try {
            this.mBatteryStats.noteWifiRadioPowerState(getDataConnectionPowerState(z), android.os.SystemClock.elapsedRealtimeNanos(), i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void reportNetworkInterfaceForTransports(java.lang.String str, int[] iArr) throws java.lang.RuntimeException {
        try {
            this.mBatteryStats.noteNetworkInterfaceForTransports(str, iArr);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void reportBluetoothOn(int i, int i2, java.lang.String str) {
    }

    @java.lang.Deprecated
    public void reportBluetoothOff(int i, int i2, java.lang.String str) {
    }

    public void reportBleScanStarted(android.os.WorkSource workSource, boolean z) {
        try {
            this.mBatteryStats.noteBleScanStarted(workSource, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportBleScanStopped(android.os.WorkSource workSource, boolean z) {
        try {
            this.mBatteryStats.noteBleScanStopped(workSource, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportBleScanReset() {
        try {
            this.mBatteryStats.noteBleScanReset();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportBleScanResults(android.os.WorkSource workSource, int i) {
        try {
            this.mBatteryStats.noteBleScanResults(workSource, i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private static int getDataConnectionPowerState(boolean z) {
        return z ? 3 : 1;
    }

    public void setChargerAcOnline(boolean z, boolean z2) {
        try {
            this.mBatteryStats.setChargerAcOnline(z, z2);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setBatteryLevel(int i, boolean z) {
        try {
            this.mBatteryStats.setBatteryLevel(i, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unplugBattery(boolean z) {
        try {
            this.mBatteryStats.unplugBattery(z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void resetBattery(boolean z) {
        try {
            this.mBatteryStats.resetBattery(z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void suspendBatteryInput() {
        try {
            this.mBatteryStats.suspendBatteryInput();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
