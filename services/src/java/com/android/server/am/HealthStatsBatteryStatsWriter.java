package com.android.server.am;

/* loaded from: classes.dex */
public class HealthStatsBatteryStatsWriter {
    private final long mNowRealtimeMs = android.os.SystemClock.elapsedRealtime();
    private final long mNowUptimeMs = android.os.SystemClock.uptimeMillis();

    public void writeUid(android.os.health.HealthStatsWriter healthStatsWriter, android.os.BatteryStats batteryStats, android.os.BatteryStats.Uid uid) {
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.WIFI_BYTES_TRANSFER_BY_FG_BG, batteryStats.computeBatteryRealtime(this.mNowRealtimeMs * 1000, 0) / 1000);
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER, batteryStats.computeBatteryUptime(this.mNowUptimeMs * 1000, 0) / 1000);
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG, batteryStats.computeBatteryScreenOffRealtime(this.mNowRealtimeMs * 1000, 0) / 1000);
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.KERNEL_WAKELOCK, batteryStats.computeBatteryScreenOffUptime(this.mNowUptimeMs * 1000, 0) / 1000);
        for (java.util.Map.Entry entry : uid.getWakelockStats().entrySet()) {
            java.lang.String str = (java.lang.String) entry.getKey();
            android.os.BatteryStats.Uid.Wakelock wakelock = (android.os.BatteryStats.Uid.Wakelock) entry.getValue();
            addTimers(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE, str, wakelock.getWakeTime(1));
            addTimers(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.BLUETOOTH_BYTES_TRANSFER, str, wakelock.getWakeTime(0));
            addTimers(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.BLUETOOTH_ACTIVITY_INFO, str, wakelock.getWakeTime(2));
            addTimers(healthStatsWriter, 10008, str, wakelock.getWakeTime(18));
        }
        for (java.util.Map.Entry entry2 : uid.getSyncStats().entrySet()) {
            addTimers(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.CPU_TIME_PER_UID, (java.lang.String) entry2.getKey(), (android.os.BatteryStats.Timer) entry2.getValue());
        }
        for (java.util.Map.Entry entry3 : uid.getJobStats().entrySet()) {
            addTimers(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.CPU_TIME_PER_UID_FREQ, (java.lang.String) entry3.getKey(), (android.os.BatteryStats.Timer) entry3.getValue());
        }
        android.util.SparseArray sensorStats = uid.getSensorStats();
        int size = sensorStats.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sensorStats.keyAt(i);
            if (keyAt == -10000) {
                addTimer(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.WIFI_ACTIVITY_INFO, ((android.os.BatteryStats.Uid.Sensor) sensorStats.valueAt(i)).getSensorTime());
            } else {
                addTimers(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.MODEM_ACTIVITY_INFO, java.lang.Integer.toString(keyAt), ((android.os.BatteryStats.Uid.Sensor) sensorStats.valueAt(i)).getSensorTime());
            }
        }
        android.util.SparseArray pidStats = uid.getPidStats();
        int size2 = pidStats.size();
        for (int i2 = 0; i2 < size2; i2++) {
            android.os.health.HealthStatsWriter healthStatsWriter2 = new android.os.health.HealthStatsWriter(android.os.health.PidHealthStats.CONSTANTS);
            writePid(healthStatsWriter2, (android.os.BatteryStats.Uid.Pid) pidStats.valueAt(i2));
            healthStatsWriter.addStats(com.android.internal.util.FrameworkStatsLog.PROCESS_MEMORY_STATE, java.lang.Integer.toString(pidStats.keyAt(i2)), healthStatsWriter2);
        }
        for (java.util.Map.Entry entry4 : uid.getProcessStats().entrySet()) {
            android.os.health.HealthStatsWriter healthStatsWriter3 = new android.os.health.HealthStatsWriter(android.os.health.ProcessHealthStats.CONSTANTS);
            writeProc(healthStatsWriter3, (android.os.BatteryStats.Uid.Proc) entry4.getValue());
            healthStatsWriter.addStats(com.android.internal.util.FrameworkStatsLog.SYSTEM_ELAPSED_REALTIME, (java.lang.String) entry4.getKey(), healthStatsWriter3);
        }
        for (java.util.Map.Entry entry5 : uid.getPackageStats().entrySet()) {
            android.os.health.HealthStatsWriter healthStatsWriter4 = new android.os.health.HealthStatsWriter(android.os.health.PackageHealthStats.CONSTANTS);
            writePkg(healthStatsWriter4, (android.os.BatteryStats.Uid.Pkg) entry5.getValue());
            healthStatsWriter.addStats(com.android.internal.util.FrameworkStatsLog.SYSTEM_UPTIME, (java.lang.String) entry5.getKey(), healthStatsWriter4);
        }
        android.os.BatteryStats.ControllerActivityCounter wifiControllerActivity = uid.getWifiControllerActivity();
        if (wifiControllerActivity != null) {
            healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.CPU_ACTIVE_TIME, wifiControllerActivity.getIdleTimeCounter().getCountLocked(0));
            healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.CPU_CLUSTER_TIME, wifiControllerActivity.getRxTimeCounter().getCountLocked(0));
            long j = 0;
            for (android.os.BatteryStats.LongCounter longCounter : wifiControllerActivity.getTxTimeCounters()) {
                j += longCounter.getCountLocked(0);
            }
            healthStatsWriter.addMeasurement(10018, j);
            healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.REMAINING_BATTERY_CAPACITY, wifiControllerActivity.getPowerCounter().getCountLocked(0));
        }
        android.os.BatteryStats.ControllerActivityCounter bluetoothControllerActivity = uid.getBluetoothControllerActivity();
        if (bluetoothControllerActivity != null) {
            healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.FULL_BATTERY_CAPACITY, bluetoothControllerActivity.getIdleTimeCounter().getCountLocked(0));
            healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.TEMPERATURE, bluetoothControllerActivity.getRxTimeCounter().getCountLocked(0));
            long j2 = 0;
            for (android.os.BatteryStats.LongCounter longCounter2 : bluetoothControllerActivity.getTxTimeCounters()) {
                j2 += longCounter2.getCountLocked(0);
            }
            healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.BINDER_CALLS, j2);
            healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.BINDER_CALLS_EXCEPTIONS, bluetoothControllerActivity.getPowerCounter().getCountLocked(0));
        }
        android.os.BatteryStats.ControllerActivityCounter modemControllerActivity = uid.getModemControllerActivity();
        if (modemControllerActivity != null) {
            healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.LOOPER_STATS, modemControllerActivity.getIdleTimeCounter().getCountLocked(0));
            healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.DISK_STATS, modemControllerActivity.getRxTimeCounter().getCountLocked(0));
            long j3 = 0;
            for (android.os.BatteryStats.LongCounter longCounter3 : modemControllerActivity.getTxTimeCounters()) {
                j3 += longCounter3.getCountLocked(0);
            }
            healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.DIRECTORY_USAGE, j3);
            healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.APP_SIZE, modemControllerActivity.getPowerCounter().getCountLocked(0));
        }
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.CATEGORY_SIZE, uid.getWifiRunningTime(this.mNowRealtimeMs * 1000, 0) / 1000);
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.PROC_STATS, uid.getFullWifiLockTime(this.mNowRealtimeMs * 1000, 0) / 1000);
        healthStatsWriter.addTimer(com.android.internal.util.FrameworkStatsLog.BATTERY_VOLTAGE, uid.getWifiScanCount(0), uid.getWifiScanTime(this.mNowRealtimeMs * 1000, 0) / 1000);
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.NUM_FINGERPRINTS_ENROLLED, uid.getWifiMulticastTime(this.mNowRealtimeMs * 1000, 0) / 1000);
        addTimer(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.DISK_IO, uid.getAudioTurnedOnTimer());
        addTimer(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.POWER_PROFILE, uid.getVideoTurnedOnTimer());
        addTimer(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.PROC_STATS_PKG_PROC, uid.getFlashlightTurnedOnTimer());
        addTimer(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.PROCESS_CPU_TIME, uid.getCameraTurnedOnTimer());
        addTimer(healthStatsWriter, 10036, uid.getForegroundActivityTimer());
        addTimer(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.CPU_TIME_PER_THREAD_FREQ, uid.getBluetoothScanTimer());
        addTimer(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.ON_DEVICE_POWER_MEASUREMENT, uid.getProcessStateTimer(0));
        addTimer(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.DEVICE_CALCULATED_POWER_USE, uid.getProcessStateTimer(1));
        addTimer(healthStatsWriter, 10040, uid.getProcessStateTimer(4));
        addTimer(healthStatsWriter, 10041, uid.getProcessStateTimer(2));
        addTimer(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.PROCESS_MEMORY_HIGH_WATER_MARK, uid.getProcessStateTimer(3));
        addTimer(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.BATTERY_LEVEL, uid.getProcessStateTimer(6));
        addTimer(healthStatsWriter, com.android.internal.util.FrameworkStatsLog.BUILD_INFORMATION, uid.getVibratorOnTimer());
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.BATTERY_CYCLE_COUNT, uid.getUserActivityCount(0, 0));
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.DEBUG_ELAPSED_CLOCK, uid.getUserActivityCount(1, 0));
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.DEBUG_FAILING_ELAPSED_CLOCK, uid.getUserActivityCount(2, 0));
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.NUM_FACES_ENROLLED, uid.getNetworkActivityBytes(0, 0));
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.ROLE_HOLDER, uid.getNetworkActivityBytes(1, 0));
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.DANGEROUS_PERMISSION_STATE, uid.getNetworkActivityBytes(2, 0));
        healthStatsWriter.addMeasurement(10051, uid.getNetworkActivityBytes(3, 0));
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.TIME_ZONE_DATA_INFO, uid.getNetworkActivityBytes(4, 0));
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.EXTERNAL_STORAGE_INFO, uid.getNetworkActivityBytes(5, 0));
        healthStatsWriter.addMeasurement(10054, uid.getNetworkActivityPackets(0, 0));
        healthStatsWriter.addMeasurement(10055, uid.getNetworkActivityPackets(1, 0));
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.SYSTEM_ION_HEAP_SIZE, uid.getNetworkActivityPackets(2, 0));
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.APPS_ON_EXTERNAL_STORAGE_INFO, uid.getNetworkActivityPackets(3, 0));
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.FACE_SETTINGS, uid.getNetworkActivityPackets(4, 0));
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.COOLING_DEVICE, uid.getNetworkActivityPackets(5, 0));
        healthStatsWriter.addTimer(com.android.internal.util.FrameworkStatsLog.PROCESS_SYSTEM_ION_HEAP_SIZE, uid.getMobileRadioActiveCount(0), uid.getMobileRadioActiveTime(0));
        healthStatsWriter.addMeasurement(10062, uid.getUserCpuTimeUs(0) / 1000);
        healthStatsWriter.addMeasurement(10063, uid.getSystemCpuTimeUs(0) / 1000);
        healthStatsWriter.addMeasurement(com.android.internal.util.FrameworkStatsLog.PROCESS_MEMORY_SNAPSHOT, 0L);
    }

    public void writePid(android.os.health.HealthStatsWriter healthStatsWriter, android.os.BatteryStats.Uid.Pid pid) {
        if (pid == null) {
            return;
        }
        healthStatsWriter.addMeasurement(20001, pid.mWakeNesting);
        healthStatsWriter.addMeasurement(20002, pid.mWakeSumMs);
        healthStatsWriter.addMeasurement(20002, pid.mWakeStartMs);
    }

    public void writeProc(android.os.health.HealthStatsWriter healthStatsWriter, android.os.BatteryStats.Uid.Proc proc) {
        healthStatsWriter.addMeasurement(com.android.server.wm.EventLogTags.WM_FINISH_ACTIVITY, proc.getUserTime(0));
        healthStatsWriter.addMeasurement(com.android.server.wm.EventLogTags.WM_TASK_TO_FRONT, proc.getSystemTime(0));
        healthStatsWriter.addMeasurement(com.android.server.wm.EventLogTags.WM_NEW_INTENT, proc.getStarts(0));
        healthStatsWriter.addMeasurement(com.android.server.wm.EventLogTags.WM_CREATE_TASK, proc.getNumCrashes(0));
        healthStatsWriter.addMeasurement(com.android.server.wm.EventLogTags.WM_CREATE_ACTIVITY, proc.getNumAnrs(0));
        healthStatsWriter.addMeasurement(com.android.server.wm.EventLogTags.WM_RESTART_ACTIVITY, proc.getForegroundTime(0));
    }

    public void writePkg(android.os.health.HealthStatsWriter healthStatsWriter, android.os.BatteryStats.Uid.Pkg pkg) {
        for (java.util.Map.Entry entry : pkg.getServiceStats().entrySet()) {
            android.os.health.HealthStatsWriter healthStatsWriter2 = new android.os.health.HealthStatsWriter(android.os.health.ServiceHealthStats.CONSTANTS);
            writeServ(healthStatsWriter2, (android.os.BatteryStats.Uid.Pkg.Serv) entry.getValue());
            healthStatsWriter.addStats(com.android.server.EventLogTags.STREAM_DEVICES_CHANGED, (java.lang.String) entry.getKey(), healthStatsWriter2);
        }
        for (java.util.Map.Entry entry2 : pkg.getWakeupAlarmStats().entrySet()) {
            if (((android.os.BatteryStats.Counter) entry2.getValue()) != null) {
                healthStatsWriter.addMeasurements(40002, (java.lang.String) entry2.getKey(), r1.getCountLocked(0));
            }
        }
    }

    public void writeServ(android.os.health.HealthStatsWriter healthStatsWriter, android.os.BatteryStats.Uid.Pkg.Serv serv) {
        healthStatsWriter.addMeasurement(50001, serv.getStarts(0));
        healthStatsWriter.addMeasurement(50002, serv.getLaunches(0));
    }

    private void addTimer(android.os.health.HealthStatsWriter healthStatsWriter, int i, android.os.BatteryStats.Timer timer) {
        if (timer != null) {
            healthStatsWriter.addTimer(i, timer.getCountLocked(0), timer.getTotalTimeLocked(this.mNowRealtimeMs * 1000, 0) / 1000);
        }
    }

    private void addTimers(android.os.health.HealthStatsWriter healthStatsWriter, int i, java.lang.String str, android.os.BatteryStats.Timer timer) {
        if (timer != null) {
            healthStatsWriter.addTimers(i, str, new android.os.health.TimerStat(timer.getCountLocked(0), timer.getTotalTimeLocked(this.mNowRealtimeMs * 1000, 0) / 1000));
        }
    }
}
