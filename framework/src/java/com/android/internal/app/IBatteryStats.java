package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IBatteryStats extends android.os.IInterface {
    long computeBatteryScreenOffRealtimeMs() throws android.os.RemoteException;

    long computeBatteryTimeRemaining() throws android.os.RemoteException;

    long computeChargeTimeRemaining() throws android.os.RemoteException;

    long getAwakeTimeBattery() throws android.os.RemoteException;

    long getAwakeTimePlugged() throws android.os.RemoteException;

    java.util.List<android.os.BatteryUsageStats> getBatteryUsageStats(java.util.List<android.os.BatteryUsageStatsQuery> list) throws android.os.RemoteException;

    android.os.BluetoothBatteryStats getBluetoothBatteryStats() throws android.os.RemoteException;

    android.os.connectivity.CellularBatteryStats getCellularBatteryStats() throws android.os.RemoteException;

    android.os.connectivity.GpsBatteryStats getGpsBatteryStats() throws android.os.RemoteException;

    long getScreenOffDischargeMah() throws android.os.RemoteException;

    android.os.WakeLockStats getWakeLockStats() throws android.os.RemoteException;

    android.os.connectivity.WifiBatteryStats getWifiBatteryStats() throws android.os.RemoteException;

    boolean isCharging() throws android.os.RemoteException;

    void noteBleScanReset() throws android.os.RemoteException;

    void noteBleScanResults(android.os.WorkSource workSource, int i) throws android.os.RemoteException;

    void noteBleScanStarted(android.os.WorkSource workSource, boolean z) throws android.os.RemoteException;

    void noteBleScanStopped(android.os.WorkSource workSource, boolean z) throws android.os.RemoteException;

    void noteBluetoothControllerActivity(android.bluetooth.BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) throws android.os.RemoteException;

    void noteChangeWakelockFromSource(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, android.os.WorkSource workSource2, int i3, java.lang.String str3, java.lang.String str4, int i4, boolean z) throws android.os.RemoteException;

    void noteConnectivityChanged(int i, java.lang.String str) throws android.os.RemoteException;

    void noteDeviceIdleMode(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void noteEvent(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void noteFlashlightOff(int i) throws android.os.RemoteException;

    void noteFlashlightOn(int i) throws android.os.RemoteException;

    void noteFullWifiLockAcquired(int i) throws android.os.RemoteException;

    void noteFullWifiLockAcquiredFromSource(android.os.WorkSource workSource) throws android.os.RemoteException;

    void noteFullWifiLockReleased(int i) throws android.os.RemoteException;

    void noteFullWifiLockReleasedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException;

    void noteGpsChanged(android.os.WorkSource workSource, android.os.WorkSource workSource2) throws android.os.RemoteException;

    void noteGpsSignalQuality(int i) throws android.os.RemoteException;

    void noteInteractive(boolean z) throws android.os.RemoteException;

    void noteJobFinish(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void noteJobStart(java.lang.String str, int i) throws android.os.RemoteException;

    void noteLongPartialWakelockFinish(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void noteLongPartialWakelockFinishFromSource(java.lang.String str, java.lang.String str2, android.os.WorkSource workSource) throws android.os.RemoteException;

    void noteLongPartialWakelockStart(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void noteLongPartialWakelockStartFromSource(java.lang.String str, java.lang.String str2, android.os.WorkSource workSource) throws android.os.RemoteException;

    void noteMobileRadioPowerState(int i, long j, int i2) throws android.os.RemoteException;

    void noteModemControllerActivity(android.telephony.ModemActivityInfo modemActivityInfo) throws android.os.RemoteException;

    void noteNetworkInterfaceForTransports(java.lang.String str, int[] iArr) throws android.os.RemoteException;

    void noteNetworkStatsEnabled() throws android.os.RemoteException;

    void notePhoneDataConnectionState(int i, boolean z, int i2, int i3, int i4) throws android.os.RemoteException;

    void notePhoneOff() throws android.os.RemoteException;

    void notePhoneOn() throws android.os.RemoteException;

    void notePhoneSignalStrength(android.telephony.SignalStrength signalStrength) throws android.os.RemoteException;

    void notePhoneState(int i) throws android.os.RemoteException;

    void noteResetAudio() throws android.os.RemoteException;

    void noteResetCamera() throws android.os.RemoteException;

    void noteResetFlashlight() throws android.os.RemoteException;

    void noteResetVideo() throws android.os.RemoteException;

    void noteScreenBrightness(int i) throws android.os.RemoteException;

    void noteScreenState(int i) throws android.os.RemoteException;

    void noteStartAudio(int i) throws android.os.RemoteException;

    void noteStartCamera(int i) throws android.os.RemoteException;

    void noteStartSensor(int i, int i2) throws android.os.RemoteException;

    void noteStartVideo(int i) throws android.os.RemoteException;

    void noteStartWakelock(int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z) throws android.os.RemoteException;

    void noteStartWakelockFromSource(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, boolean z) throws android.os.RemoteException;

    void noteStopAudio(int i) throws android.os.RemoteException;

    void noteStopCamera(int i) throws android.os.RemoteException;

    void noteStopSensor(int i, int i2) throws android.os.RemoteException;

    void noteStopVideo(int i) throws android.os.RemoteException;

    void noteStopWakelock(int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException;

    void noteStopWakelockFromSource(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException;

    void noteSyncFinish(java.lang.String str, int i) throws android.os.RemoteException;

    void noteSyncStart(java.lang.String str, int i) throws android.os.RemoteException;

    void noteUserActivity(int i, int i2) throws android.os.RemoteException;

    void noteVibratorOff(int i) throws android.os.RemoteException;

    void noteVibratorOn(int i, long j) throws android.os.RemoteException;

    void noteWakeUp(java.lang.String str, int i) throws android.os.RemoteException;

    void noteWakeupSensorEvent(long j, int i, int i2) throws android.os.RemoteException;

    void noteWifiBatchedScanStartedFromSource(android.os.WorkSource workSource, int i) throws android.os.RemoteException;

    void noteWifiBatchedScanStoppedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException;

    void noteWifiControllerActivity(android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo) throws android.os.RemoteException;

    void noteWifiMulticastDisabled(int i) throws android.os.RemoteException;

    void noteWifiMulticastEnabled(int i) throws android.os.RemoteException;

    void noteWifiOff() throws android.os.RemoteException;

    void noteWifiOn() throws android.os.RemoteException;

    void noteWifiRadioPowerState(int i, long j, int i2) throws android.os.RemoteException;

    void noteWifiRssiChanged(int i) throws android.os.RemoteException;

    void noteWifiRunning(android.os.WorkSource workSource) throws android.os.RemoteException;

    void noteWifiRunningChanged(android.os.WorkSource workSource, android.os.WorkSource workSource2) throws android.os.RemoteException;

    void noteWifiScanStarted(int i) throws android.os.RemoteException;

    void noteWifiScanStartedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException;

    void noteWifiScanStopped(int i) throws android.os.RemoteException;

    void noteWifiScanStoppedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException;

    void noteWifiState(int i, java.lang.String str) throws android.os.RemoteException;

    void noteWifiStopped(android.os.WorkSource workSource) throws android.os.RemoteException;

    void noteWifiSupplicantStateChanged(int i, boolean z) throws android.os.RemoteException;

    void resetBattery(boolean z) throws android.os.RemoteException;

    void setBatteryLevel(int i, boolean z) throws android.os.RemoteException;

    void setBatteryState(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j) throws android.os.RemoteException;

    void setChargerAcOnline(boolean z, boolean z2) throws android.os.RemoteException;

    boolean setChargingStateUpdateDelayMillis(int i) throws android.os.RemoteException;

    void suspendBatteryInput() throws android.os.RemoteException;

    android.os.health.HealthStatsParceler takeUidSnapshot(int i) throws android.os.RemoteException;

    android.os.health.HealthStatsParceler[] takeUidSnapshots(int[] iArr) throws android.os.RemoteException;

    void unplugBattery(boolean z) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IBatteryStats {
        @Override // com.android.internal.app.IBatteryStats
        public void noteStartSensor(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopSensor(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartVideo(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopVideo(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartAudio(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopAudio(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetVideo() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetAudio() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFlashlightOn(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFlashlightOff(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartCamera(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopCamera(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetCamera() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetFlashlight() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWakeupSensorEvent(long j, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public java.util.List<android.os.BatteryUsageStats> getBatteryUsageStats(java.util.List<android.os.BatteryUsageStatsQuery> list) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public boolean isCharging() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long computeBatteryTimeRemaining() throws android.os.RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long computeChargeTimeRemaining() throws android.os.RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long computeBatteryScreenOffRealtimeMs() throws android.os.RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long getScreenOffDischargeMah() throws android.os.RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteEvent(int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteSyncStart(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteSyncFinish(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteJobStart(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteJobFinish(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartWakelock(int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopWakelock(int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartWakelockFromSource(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteChangeWakelockFromSource(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, android.os.WorkSource workSource2, int i3, java.lang.String str3, java.lang.String str4, int i4, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopWakelockFromSource(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockStart(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockStartFromSource(java.lang.String str, java.lang.String str2, android.os.WorkSource workSource) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockFinish(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockFinishFromSource(java.lang.String str, java.lang.String str2, android.os.WorkSource workSource) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteVibratorOn(int i, long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteVibratorOff(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteGpsChanged(android.os.WorkSource workSource, android.os.WorkSource workSource2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteGpsSignalQuality(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteScreenState(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteScreenBrightness(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteUserActivity(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWakeUp(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteInteractive(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteConnectivityChanged(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteMobileRadioPowerState(int i, long j, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneOn() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneOff() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneSignalStrength(android.telephony.SignalStrength signalStrength) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneDataConnectionState(int i, boolean z, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneState(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiOn() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiOff() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRunning(android.os.WorkSource workSource) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRunningChanged(android.os.WorkSource workSource, android.os.WorkSource workSource2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiStopped(android.os.WorkSource workSource) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiState(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiSupplicantStateChanged(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRssiChanged(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockAcquired(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockReleased(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStarted(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStopped(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiMulticastEnabled(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiMulticastDisabled(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockAcquiredFromSource(android.os.WorkSource workSource) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockReleasedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStartedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStoppedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiBatchedScanStartedFromSource(android.os.WorkSource workSource, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiBatchedScanStoppedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRadioPowerState(int i, long j, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteNetworkInterfaceForTransports(java.lang.String str, int[] iArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteNetworkStatsEnabled() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteDeviceIdleMode(int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setBatteryState(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public long getAwakeTimeBattery() throws android.os.RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long getAwakeTimePlugged() throws android.os.RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanStarted(android.os.WorkSource workSource, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanStopped(android.os.WorkSource workSource, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanReset() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanResults(android.os.WorkSource workSource, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public android.os.connectivity.CellularBatteryStats getCellularBatteryStats() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public android.os.connectivity.WifiBatteryStats getWifiBatteryStats() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public android.os.connectivity.GpsBatteryStats getGpsBatteryStats() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public android.os.WakeLockStats getWakeLockStats() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public android.os.BluetoothBatteryStats getBluetoothBatteryStats() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public android.os.health.HealthStatsParceler takeUidSnapshot(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public android.os.health.HealthStatsParceler[] takeUidSnapshots(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBluetoothControllerActivity(android.bluetooth.BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteModemControllerActivity(android.telephony.ModemActivityInfo modemActivityInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiControllerActivity(android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public boolean setChargingStateUpdateDelayMillis(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setChargerAcOnline(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setBatteryLevel(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void unplugBattery(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void resetBattery(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void suspendBatteryInput() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IBatteryStats {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IBatteryStats";
        static final int TRANSACTION_computeBatteryScreenOffRealtimeMs = 20;
        static final int TRANSACTION_computeBatteryTimeRemaining = 18;
        static final int TRANSACTION_computeChargeTimeRemaining = 19;
        static final int TRANSACTION_getAwakeTimeBattery = 77;
        static final int TRANSACTION_getAwakeTimePlugged = 78;
        static final int TRANSACTION_getBatteryUsageStats = 16;
        static final int TRANSACTION_getBluetoothBatteryStats = 87;
        static final int TRANSACTION_getCellularBatteryStats = 83;
        static final int TRANSACTION_getGpsBatteryStats = 85;
        static final int TRANSACTION_getScreenOffDischargeMah = 21;
        static final int TRANSACTION_getWakeLockStats = 86;
        static final int TRANSACTION_getWifiBatteryStats = 84;
        static final int TRANSACTION_isCharging = 17;
        static final int TRANSACTION_noteBleScanReset = 81;
        static final int TRANSACTION_noteBleScanResults = 82;
        static final int TRANSACTION_noteBleScanStarted = 79;
        static final int TRANSACTION_noteBleScanStopped = 80;
        static final int TRANSACTION_noteBluetoothControllerActivity = 90;
        static final int TRANSACTION_noteChangeWakelockFromSource = 30;
        static final int TRANSACTION_noteConnectivityChanged = 45;
        static final int TRANSACTION_noteDeviceIdleMode = 75;
        static final int TRANSACTION_noteEvent = 22;
        static final int TRANSACTION_noteFlashlightOff = 10;
        static final int TRANSACTION_noteFlashlightOn = 9;
        static final int TRANSACTION_noteFullWifiLockAcquired = 60;
        static final int TRANSACTION_noteFullWifiLockAcquiredFromSource = 66;
        static final int TRANSACTION_noteFullWifiLockReleased = 61;
        static final int TRANSACTION_noteFullWifiLockReleasedFromSource = 67;
        static final int TRANSACTION_noteGpsChanged = 38;
        static final int TRANSACTION_noteGpsSignalQuality = 39;
        static final int TRANSACTION_noteInteractive = 44;
        static final int TRANSACTION_noteJobFinish = 26;
        static final int TRANSACTION_noteJobStart = 25;
        static final int TRANSACTION_noteLongPartialWakelockFinish = 34;
        static final int TRANSACTION_noteLongPartialWakelockFinishFromSource = 35;
        static final int TRANSACTION_noteLongPartialWakelockStart = 32;
        static final int TRANSACTION_noteLongPartialWakelockStartFromSource = 33;
        static final int TRANSACTION_noteMobileRadioPowerState = 46;
        static final int TRANSACTION_noteModemControllerActivity = 91;
        static final int TRANSACTION_noteNetworkInterfaceForTransports = 73;
        static final int TRANSACTION_noteNetworkStatsEnabled = 74;
        static final int TRANSACTION_notePhoneDataConnectionState = 50;
        static final int TRANSACTION_notePhoneOff = 48;
        static final int TRANSACTION_notePhoneOn = 47;
        static final int TRANSACTION_notePhoneSignalStrength = 49;
        static final int TRANSACTION_notePhoneState = 51;
        static final int TRANSACTION_noteResetAudio = 8;
        static final int TRANSACTION_noteResetCamera = 13;
        static final int TRANSACTION_noteResetFlashlight = 14;
        static final int TRANSACTION_noteResetVideo = 7;
        static final int TRANSACTION_noteScreenBrightness = 41;
        static final int TRANSACTION_noteScreenState = 40;
        static final int TRANSACTION_noteStartAudio = 5;
        static final int TRANSACTION_noteStartCamera = 11;
        static final int TRANSACTION_noteStartSensor = 1;
        static final int TRANSACTION_noteStartVideo = 3;
        static final int TRANSACTION_noteStartWakelock = 27;
        static final int TRANSACTION_noteStartWakelockFromSource = 29;
        static final int TRANSACTION_noteStopAudio = 6;
        static final int TRANSACTION_noteStopCamera = 12;
        static final int TRANSACTION_noteStopSensor = 2;
        static final int TRANSACTION_noteStopVideo = 4;
        static final int TRANSACTION_noteStopWakelock = 28;
        static final int TRANSACTION_noteStopWakelockFromSource = 31;
        static final int TRANSACTION_noteSyncFinish = 24;
        static final int TRANSACTION_noteSyncStart = 23;
        static final int TRANSACTION_noteUserActivity = 42;
        static final int TRANSACTION_noteVibratorOff = 37;
        static final int TRANSACTION_noteVibratorOn = 36;
        static final int TRANSACTION_noteWakeUp = 43;
        static final int TRANSACTION_noteWakeupSensorEvent = 15;
        static final int TRANSACTION_noteWifiBatchedScanStartedFromSource = 70;
        static final int TRANSACTION_noteWifiBatchedScanStoppedFromSource = 71;
        static final int TRANSACTION_noteWifiControllerActivity = 92;
        static final int TRANSACTION_noteWifiMulticastDisabled = 65;
        static final int TRANSACTION_noteWifiMulticastEnabled = 64;
        static final int TRANSACTION_noteWifiOff = 53;
        static final int TRANSACTION_noteWifiOn = 52;
        static final int TRANSACTION_noteWifiRadioPowerState = 72;
        static final int TRANSACTION_noteWifiRssiChanged = 59;
        static final int TRANSACTION_noteWifiRunning = 54;
        static final int TRANSACTION_noteWifiRunningChanged = 55;
        static final int TRANSACTION_noteWifiScanStarted = 62;
        static final int TRANSACTION_noteWifiScanStartedFromSource = 68;
        static final int TRANSACTION_noteWifiScanStopped = 63;
        static final int TRANSACTION_noteWifiScanStoppedFromSource = 69;
        static final int TRANSACTION_noteWifiState = 57;
        static final int TRANSACTION_noteWifiStopped = 56;
        static final int TRANSACTION_noteWifiSupplicantStateChanged = 58;
        static final int TRANSACTION_resetBattery = 97;
        static final int TRANSACTION_setBatteryLevel = 95;
        static final int TRANSACTION_setBatteryState = 76;
        static final int TRANSACTION_setChargerAcOnline = 94;
        static final int TRANSACTION_setChargingStateUpdateDelayMillis = 93;
        static final int TRANSACTION_suspendBatteryInput = 98;
        static final int TRANSACTION_takeUidSnapshot = 88;
        static final int TRANSACTION_takeUidSnapshots = 89;
        static final int TRANSACTION_unplugBattery = 96;
        private final android.os.PermissionEnforcer mEnforcer;
        static final java.lang.String[] PERMISSIONS_noteNetworkInterfaceForTransports = {android.Manifest.permission.NETWORK_STACK, android.net.NetworkStack.PERMISSION_MAINLINE_NETWORK_STACK};
        static final java.lang.String[] PERMISSIONS_getCellularBatteryStats = {android.Manifest.permission.UPDATE_DEVICE_STATS, android.Manifest.permission.BATTERY_STATS};
        static final java.lang.String[] PERMISSIONS_getWifiBatteryStats = {android.Manifest.permission.UPDATE_DEVICE_STATS, android.Manifest.permission.BATTERY_STATS};

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static com.android.internal.app.IBatteryStats asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IBatteryStats)) {
                return (com.android.internal.app.IBatteryStats) queryLocalInterface;
            }
            return new com.android.internal.app.IBatteryStats.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "noteStartSensor";
                case 2:
                    return "noteStopSensor";
                case 3:
                    return "noteStartVideo";
                case 4:
                    return "noteStopVideo";
                case 5:
                    return "noteStartAudio";
                case 6:
                    return "noteStopAudio";
                case 7:
                    return "noteResetVideo";
                case 8:
                    return "noteResetAudio";
                case 9:
                    return "noteFlashlightOn";
                case 10:
                    return "noteFlashlightOff";
                case 11:
                    return "noteStartCamera";
                case 12:
                    return "noteStopCamera";
                case 13:
                    return "noteResetCamera";
                case 14:
                    return "noteResetFlashlight";
                case 15:
                    return "noteWakeupSensorEvent";
                case 16:
                    return "getBatteryUsageStats";
                case 17:
                    return "isCharging";
                case 18:
                    return "computeBatteryTimeRemaining";
                case 19:
                    return "computeChargeTimeRemaining";
                case 20:
                    return "computeBatteryScreenOffRealtimeMs";
                case 21:
                    return "getScreenOffDischargeMah";
                case 22:
                    return "noteEvent";
                case 23:
                    return "noteSyncStart";
                case 24:
                    return "noteSyncFinish";
                case 25:
                    return "noteJobStart";
                case 26:
                    return "noteJobFinish";
                case 27:
                    return "noteStartWakelock";
                case 28:
                    return "noteStopWakelock";
                case 29:
                    return "noteStartWakelockFromSource";
                case 30:
                    return "noteChangeWakelockFromSource";
                case 31:
                    return "noteStopWakelockFromSource";
                case 32:
                    return "noteLongPartialWakelockStart";
                case 33:
                    return "noteLongPartialWakelockStartFromSource";
                case 34:
                    return "noteLongPartialWakelockFinish";
                case 35:
                    return "noteLongPartialWakelockFinishFromSource";
                case 36:
                    return "noteVibratorOn";
                case 37:
                    return "noteVibratorOff";
                case 38:
                    return "noteGpsChanged";
                case 39:
                    return "noteGpsSignalQuality";
                case 40:
                    return "noteScreenState";
                case 41:
                    return "noteScreenBrightness";
                case 42:
                    return "noteUserActivity";
                case 43:
                    return "noteWakeUp";
                case 44:
                    return "noteInteractive";
                case 45:
                    return "noteConnectivityChanged";
                case 46:
                    return "noteMobileRadioPowerState";
                case 47:
                    return "notePhoneOn";
                case 48:
                    return "notePhoneOff";
                case 49:
                    return "notePhoneSignalStrength";
                case 50:
                    return "notePhoneDataConnectionState";
                case 51:
                    return "notePhoneState";
                case 52:
                    return "noteWifiOn";
                case 53:
                    return "noteWifiOff";
                case 54:
                    return "noteWifiRunning";
                case 55:
                    return "noteWifiRunningChanged";
                case 56:
                    return "noteWifiStopped";
                case 57:
                    return "noteWifiState";
                case 58:
                    return "noteWifiSupplicantStateChanged";
                case 59:
                    return "noteWifiRssiChanged";
                case 60:
                    return "noteFullWifiLockAcquired";
                case 61:
                    return "noteFullWifiLockReleased";
                case 62:
                    return "noteWifiScanStarted";
                case 63:
                    return "noteWifiScanStopped";
                case 64:
                    return "noteWifiMulticastEnabled";
                case 65:
                    return "noteWifiMulticastDisabled";
                case 66:
                    return "noteFullWifiLockAcquiredFromSource";
                case 67:
                    return "noteFullWifiLockReleasedFromSource";
                case 68:
                    return "noteWifiScanStartedFromSource";
                case 69:
                    return "noteWifiScanStoppedFromSource";
                case 70:
                    return "noteWifiBatchedScanStartedFromSource";
                case 71:
                    return "noteWifiBatchedScanStoppedFromSource";
                case 72:
                    return "noteWifiRadioPowerState";
                case 73:
                    return "noteNetworkInterfaceForTransports";
                case 74:
                    return "noteNetworkStatsEnabled";
                case 75:
                    return "noteDeviceIdleMode";
                case 76:
                    return "setBatteryState";
                case 77:
                    return "getAwakeTimeBattery";
                case 78:
                    return "getAwakeTimePlugged";
                case 79:
                    return "noteBleScanStarted";
                case 80:
                    return "noteBleScanStopped";
                case 81:
                    return "noteBleScanReset";
                case 82:
                    return "noteBleScanResults";
                case 83:
                    return "getCellularBatteryStats";
                case 84:
                    return "getWifiBatteryStats";
                case 85:
                    return "getGpsBatteryStats";
                case 86:
                    return "getWakeLockStats";
                case 87:
                    return "getBluetoothBatteryStats";
                case 88:
                    return "takeUidSnapshot";
                case 89:
                    return "takeUidSnapshots";
                case 90:
                    return "noteBluetoothControllerActivity";
                case 91:
                    return "noteModemControllerActivity";
                case 92:
                    return "noteWifiControllerActivity";
                case 93:
                    return "setChargingStateUpdateDelayMillis";
                case 94:
                    return "setChargerAcOnline";
                case 95:
                    return "setBatteryLevel";
                case 96:
                    return "unplugBattery";
                case 97:
                    return "resetBattery";
                case 98:
                    return "suspendBatteryInput";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStartSensor(readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopSensor(readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStartVideo(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopVideo(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStartAudio(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopAudio(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    noteResetVideo();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    noteResetAudio();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteFlashlightOn(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteFlashlightOff(readInt10);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStartCamera(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopCamera(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    noteResetCamera();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    noteResetFlashlight();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    long readLong = parcel.readLong();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWakeupSensorEvent(readLong, readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.os.BatteryUsageStatsQuery.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.os.BatteryUsageStats> batteryUsageStats = getBatteryUsageStats(createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(batteryUsageStats, 1);
                    return true;
                case 17:
                    boolean isCharging = isCharging();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCharging);
                    return true;
                case 18:
                    long computeBatteryTimeRemaining = computeBatteryTimeRemaining();
                    parcel2.writeNoException();
                    parcel2.writeLong(computeBatteryTimeRemaining);
                    return true;
                case 19:
                    long computeChargeTimeRemaining = computeChargeTimeRemaining();
                    parcel2.writeNoException();
                    parcel2.writeLong(computeChargeTimeRemaining);
                    return true;
                case 20:
                    long computeBatteryScreenOffRealtimeMs = computeBatteryScreenOffRealtimeMs();
                    parcel2.writeNoException();
                    parcel2.writeLong(computeBatteryScreenOffRealtimeMs);
                    return true;
                case 21:
                    long screenOffDischargeMah = getScreenOffDischargeMah();
                    parcel2.writeNoException();
                    parcel2.writeLong(screenOffDischargeMah);
                    return true;
                case 22:
                    int readInt15 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteEvent(readInt15, readString, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    java.lang.String readString2 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteSyncStart(readString2, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    java.lang.String readString3 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteSyncFinish(readString3, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    java.lang.String readString4 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteJobStart(readString4, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    java.lang.String readString5 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteJobFinish(readString5, readInt20, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteStartWakelock(readInt22, readInt23, readString6, readString7, readInt24, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopWakelock(readInt25, readInt26, readString8, readString9, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    android.os.WorkSource workSource = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    int readInt28 = parcel.readInt();
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteStartWakelockFromSource(workSource, readInt28, readString10, readString11, readInt29, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    android.os.WorkSource workSource2 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    int readInt30 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    android.os.WorkSource workSource3 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    int readInt32 = parcel.readInt();
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteChangeWakelockFromSource(workSource2, readInt30, readString12, readString13, readInt31, workSource3, readInt32, readString14, readString15, readInt33, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    android.os.WorkSource workSource4 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    int readInt34 = parcel.readInt();
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopWakelockFromSource(workSource4, readInt34, readString16, readString17, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    java.lang.String readString18 = parcel.readString();
                    java.lang.String readString19 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteLongPartialWakelockStart(readString18, readString19, readInt36);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    java.lang.String readString20 = parcel.readString();
                    java.lang.String readString21 = parcel.readString();
                    android.os.WorkSource workSource5 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteLongPartialWakelockStartFromSource(readString20, readString21, workSource5);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    java.lang.String readString22 = parcel.readString();
                    java.lang.String readString23 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteLongPartialWakelockFinish(readString22, readString23, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    java.lang.String readString24 = parcel.readString();
                    java.lang.String readString25 = parcel.readString();
                    android.os.WorkSource workSource6 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteLongPartialWakelockFinishFromSource(readString24, readString25, workSource6);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt38 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    noteVibratorOn(readInt38, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteVibratorOff(readInt39);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    android.os.WorkSource workSource7 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    android.os.WorkSource workSource8 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteGpsChanged(workSource7, workSource8);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteGpsSignalQuality(readInt40);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteScreenState(readInt41);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteScreenBrightness(readInt42);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteUserActivity(readInt43, readInt44);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    java.lang.String readString26 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWakeUp(readString26, readInt45);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteInteractive(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int readInt46 = parcel.readInt();
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    noteConnectivityChanged(readInt46, readString27);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt47 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteMobileRadioPowerState(readInt47, readLong3, readInt48);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    notePhoneOn();
                    parcel2.writeNoException();
                    return true;
                case 48:
                    notePhoneOff();
                    parcel2.writeNoException();
                    return true;
                case 49:
                    android.telephony.SignalStrength signalStrength = (android.telephony.SignalStrength) parcel.readTypedObject(android.telephony.SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    notePhoneSignalStrength(signalStrength);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int readInt49 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notePhoneDataConnectionState(readInt49, readBoolean5, readInt50, readInt51, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notePhoneState(readInt53);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    noteWifiOn();
                    parcel2.writeNoException();
                    return true;
                case 53:
                    noteWifiOff();
                    parcel2.writeNoException();
                    return true;
                case 54:
                    android.os.WorkSource workSource9 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiRunning(workSource9);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    android.os.WorkSource workSource10 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    android.os.WorkSource workSource11 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiRunningChanged(workSource10, workSource11);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    android.os.WorkSource workSource12 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiStopped(workSource12);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int readInt54 = parcel.readInt();
                    java.lang.String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    noteWifiState(readInt54, readString28);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int readInt55 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteWifiSupplicantStateChanged(readInt55, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiRssiChanged(readInt56);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteFullWifiLockAcquired(readInt57);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteFullWifiLockReleased(readInt58);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiScanStarted(readInt59);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiScanStopped(readInt60);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiMulticastEnabled(readInt61);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiMulticastDisabled(readInt62);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    android.os.WorkSource workSource13 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteFullWifiLockAcquiredFromSource(workSource13);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    android.os.WorkSource workSource14 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteFullWifiLockReleasedFromSource(workSource14);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    android.os.WorkSource workSource15 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiScanStartedFromSource(workSource15);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    android.os.WorkSource workSource16 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiScanStoppedFromSource(workSource16);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    android.os.WorkSource workSource17 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiBatchedScanStartedFromSource(workSource17, readInt63);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    android.os.WorkSource workSource18 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiBatchedScanStoppedFromSource(workSource18);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int readInt64 = parcel.readInt();
                    long readLong4 = parcel.readLong();
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiRadioPowerState(readInt64, readLong4, readInt65);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    java.lang.String readString29 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    noteNetworkInterfaceForTransports(readString29, createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    noteNetworkStatsEnabled();
                    parcel2.writeNoException();
                    return true;
                case 75:
                    int readInt66 = parcel.readInt();
                    java.lang.String readString30 = parcel.readString();
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteDeviceIdleMode(readInt66, readString30, readInt67);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int readInt68 = parcel.readInt();
                    int readInt69 = parcel.readInt();
                    int readInt70 = parcel.readInt();
                    int readInt71 = parcel.readInt();
                    int readInt72 = parcel.readInt();
                    int readInt73 = parcel.readInt();
                    int readInt74 = parcel.readInt();
                    int readInt75 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setBatteryState(readInt68, readInt69, readInt70, readInt71, readInt72, readInt73, readInt74, readInt75, readLong5);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    long awakeTimeBattery = getAwakeTimeBattery();
                    parcel2.writeNoException();
                    parcel2.writeLong(awakeTimeBattery);
                    return true;
                case 78:
                    long awakeTimePlugged = getAwakeTimePlugged();
                    parcel2.writeNoException();
                    parcel2.writeLong(awakeTimePlugged);
                    return true;
                case 79:
                    android.os.WorkSource workSource19 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteBleScanStarted(workSource19, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    android.os.WorkSource workSource20 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteBleScanStopped(workSource20, readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    noteBleScanReset();
                    parcel2.writeNoException();
                    return true;
                case 82:
                    android.os.WorkSource workSource21 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteBleScanResults(workSource21, readInt76);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    android.os.connectivity.CellularBatteryStats cellularBatteryStats = getCellularBatteryStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cellularBatteryStats, 1);
                    return true;
                case 84:
                    android.os.connectivity.WifiBatteryStats wifiBatteryStats = getWifiBatteryStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiBatteryStats, 1);
                    return true;
                case 85:
                    android.os.connectivity.GpsBatteryStats gpsBatteryStats = getGpsBatteryStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(gpsBatteryStats, 1);
                    return true;
                case 86:
                    android.os.WakeLockStats wakeLockStats = getWakeLockStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wakeLockStats, 1);
                    return true;
                case 87:
                    android.os.BluetoothBatteryStats bluetoothBatteryStats = getBluetoothBatteryStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bluetoothBatteryStats, 1);
                    return true;
                case 88:
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.health.HealthStatsParceler takeUidSnapshot = takeUidSnapshot(readInt77);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(takeUidSnapshot, 1);
                    return true;
                case 89:
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    android.os.health.HealthStatsParceler[] takeUidSnapshots = takeUidSnapshots(createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(takeUidSnapshots, 1);
                    return true;
                case 90:
                    android.bluetooth.BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo = (android.bluetooth.BluetoothActivityEnergyInfo) parcel.readTypedObject(android.bluetooth.BluetoothActivityEnergyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteBluetoothControllerActivity(bluetoothActivityEnergyInfo);
                    return true;
                case 91:
                    android.telephony.ModemActivityInfo modemActivityInfo = (android.telephony.ModemActivityInfo) parcel.readTypedObject(android.telephony.ModemActivityInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteModemControllerActivity(modemActivityInfo);
                    return true;
                case 92:
                    android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo = (android.os.connectivity.WifiActivityEnergyInfo) parcel.readTypedObject(android.os.connectivity.WifiActivityEnergyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiControllerActivity(wifiActivityEnergyInfo);
                    return true;
                case 93:
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean chargingStateUpdateDelayMillis = setChargingStateUpdateDelayMillis(readInt78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(chargingStateUpdateDelayMillis);
                    return true;
                case 94:
                    boolean readBoolean9 = parcel.readBoolean();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setChargerAcOnline(readBoolean9, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    int readInt79 = parcel.readInt();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBatteryLevel(readInt79, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    unplugBattery(readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    resetBattery(readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    suspendBatteryInput();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IBatteryStats {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartSensor(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopSensor(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartVideo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopVideo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartAudio(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopAudio(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetVideo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetAudio() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFlashlightOn(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFlashlightOff(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartCamera(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopCamera(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetCamera() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetFlashlight() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWakeupSensorEvent(long j, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public java.util.List<android.os.BatteryUsageStats> getBatteryUsageStats(java.util.List<android.os.BatteryUsageStatsQuery> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.BatteryUsageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public boolean isCharging() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long computeBatteryTimeRemaining() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long computeChargeTimeRemaining() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long computeBatteryScreenOffRealtimeMs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long getScreenOffDischargeMah() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteEvent(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteSyncStart(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteSyncFinish(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteJobStart(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteJobFinish(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartWakelock(int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopWakelock(int i, int i2, java.lang.String str, java.lang.String str2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartWakelockFromSource(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteChangeWakelockFromSource(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, android.os.WorkSource workSource2, int i3, java.lang.String str3, java.lang.String str4, int i4, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(workSource2, 0);
                    obtain.writeInt(i3);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopWakelockFromSource(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockStart(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockStartFromSource(java.lang.String str, java.lang.String str2, android.os.WorkSource workSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockFinish(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockFinishFromSource(java.lang.String str, java.lang.String str2, android.os.WorkSource workSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteVibratorOn(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteVibratorOff(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteGpsChanged(android.os.WorkSource workSource, android.os.WorkSource workSource2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeTypedObject(workSource2, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteGpsSignalQuality(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteScreenState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteScreenBrightness(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteUserActivity(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWakeUp(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteInteractive(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteConnectivityChanged(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteMobileRadioPowerState(int i, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneOn() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneOff() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneSignalStrength(android.telephony.SignalStrength signalStrength) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(signalStrength, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneDataConnectionState(int i, boolean z, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiOn() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiOff() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRunning(android.os.WorkSource workSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRunningChanged(android.os.WorkSource workSource, android.os.WorkSource workSource2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeTypedObject(workSource2, 0);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiStopped(android.os.WorkSource workSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiState(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiSupplicantStateChanged(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRssiChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockAcquired(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockReleased(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStarted(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStopped(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiMulticastEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiMulticastDisabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockAcquiredFromSource(android.os.WorkSource workSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockReleasedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStartedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStoppedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiBatchedScanStartedFromSource(android.os.WorkSource workSource, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiBatchedScanStoppedFromSource(android.os.WorkSource workSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRadioPowerState(int i, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteNetworkInterfaceForTransports(java.lang.String str, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteNetworkStatsEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteDeviceIdleMode(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setBatteryState(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeInt(i7);
                    obtain.writeInt(i8);
                    obtain.writeLong(j);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long getAwakeTimeBattery() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long getAwakeTimePlugged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanStarted(android.os.WorkSource workSource, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanStopped(android.os.WorkSource workSource, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanReset() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanResults(android.os.WorkSource workSource, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public android.os.connectivity.CellularBatteryStats getCellularBatteryStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.connectivity.CellularBatteryStats) obtain2.readTypedObject(android.os.connectivity.CellularBatteryStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public android.os.connectivity.WifiBatteryStats getWifiBatteryStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.connectivity.WifiBatteryStats) obtain2.readTypedObject(android.os.connectivity.WifiBatteryStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public android.os.connectivity.GpsBatteryStats getGpsBatteryStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.connectivity.GpsBatteryStats) obtain2.readTypedObject(android.os.connectivity.GpsBatteryStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public android.os.WakeLockStats getWakeLockStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.WakeLockStats) obtain2.readTypedObject(android.os.WakeLockStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public android.os.BluetoothBatteryStats getBluetoothBatteryStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.BluetoothBatteryStats) obtain2.readTypedObject(android.os.BluetoothBatteryStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public android.os.health.HealthStatsParceler takeUidSnapshot(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.health.HealthStatsParceler) obtain2.readTypedObject(android.os.health.HealthStatsParceler.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public android.os.health.HealthStatsParceler[] takeUidSnapshots(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.health.HealthStatsParceler[]) obtain2.createTypedArray(android.os.health.HealthStatsParceler.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBluetoothControllerActivity(android.bluetooth.BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bluetoothActivityEnergyInfo, 0);
                    this.mRemote.transact(90, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteModemControllerActivity(android.telephony.ModemActivityInfo modemActivityInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(modemActivityInfo, 0);
                    this.mRemote.transact(91, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiControllerActivity(android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(wifiActivityEnergyInfo, 0);
                    this.mRemote.transact(92, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public boolean setChargingStateUpdateDelayMillis(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setChargerAcOnline(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setBatteryLevel(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void unplugBattery(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void resetBattery(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void suspendBatteryInput() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IBatteryStats.Stub.DESCRIPTOR);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void noteStartSensor_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopSensor_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartVideo_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopVideo_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartAudio_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopAudio_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteResetVideo_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteResetAudio_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFlashlightOn_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFlashlightOff_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartCamera_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopCamera_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteResetCamera_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteResetFlashlight_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getBatteryUsageStats_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void computeBatteryScreenOffRealtimeMs_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getScreenOffDischargeMah_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteEvent_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteSyncStart_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteSyncFinish_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteJobStart_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteJobFinish_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartWakelock_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopWakelock_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartWakelockFromSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteChangeWakelockFromSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopWakelockFromSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteLongPartialWakelockStart_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteLongPartialWakelockStartFromSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteLongPartialWakelockFinish_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteLongPartialWakelockFinishFromSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteVibratorOn_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteVibratorOff_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteGpsChanged_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteGpsSignalQuality_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteScreenState_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteScreenBrightness_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteUserActivity_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWakeUp_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteInteractive_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteConnectivityChanged_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteMobileRadioPowerState_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void notePhoneOn_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void notePhoneOff_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void notePhoneSignalStrength_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void notePhoneDataConnectionState_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void notePhoneState_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiOn_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiOff_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiRunning_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiRunningChanged_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiStopped_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiState_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiSupplicantStateChanged_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiRssiChanged_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFullWifiLockAcquired_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFullWifiLockReleased_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiScanStarted_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiScanStopped_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiMulticastEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiMulticastDisabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFullWifiLockAcquiredFromSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFullWifiLockReleasedFromSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiScanStartedFromSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiScanStoppedFromSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiBatchedScanStartedFromSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiBatchedScanStoppedFromSource_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiRadioPowerState_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteNetworkInterfaceForTransports_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_noteNetworkInterfaceForTransports, getCallingPid(), getCallingUid());
        }

        protected void noteNetworkStatsEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteDeviceIdleMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void setBatteryState_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getAwakeTimeBattery_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getAwakeTimePlugged_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBleScanStarted_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBleScanStopped_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBleScanReset_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBleScanResults_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getCellularBatteryStats_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getCellularBatteryStats, getCallingPid(), getCallingUid());
        }

        protected void getWifiBatteryStats_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getWifiBatteryStats, getCallingPid(), getCallingUid());
        }

        protected void getGpsBatteryStats_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getWakeLockStats_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getBluetoothBatteryStats_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBluetoothControllerActivity_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteModemControllerActivity_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiControllerActivity_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void setChargingStateUpdateDelayMillis_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.POWER_SAVER, getCallingPid(), getCallingUid());
        }

        protected void setChargerAcOnline_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }

        protected void setBatteryLevel_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }

        protected void unplugBattery_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }

        protected void resetBattery_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }

        protected void suspendBatteryInput_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 97;
        }
    }
}
