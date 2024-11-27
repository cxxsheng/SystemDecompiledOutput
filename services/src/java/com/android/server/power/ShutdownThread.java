package com.android.server.power;

/* loaded from: classes2.dex */
public final class ShutdownThread extends java.lang.Thread {
    private static final int ACTION_DONE_POLL_WAIT_MS = 500;
    private static final int ACTIVITY_MANAGER_STOP_PERCENT = 4;
    private static final int BROADCAST_STOP_PERCENT = 2;
    private static final java.lang.String CHECK_POINTS_FILE_BASENAME = "/data/system/shutdown-checkpoints/checkpoints";
    private static final boolean DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_SHUTDOWN_VIBRATE_MS = 500;
    private static final int MAX_BROADCAST_TIME = 10000;
    private static final int MAX_CHECK_POINTS_DUMP_WAIT_TIME = 10000;
    private static final int MAX_RADIO_WAIT_TIME = 12000;
    private static final int MAX_UNCRYPT_WAIT_TIME = 900000;
    private static final java.lang.String METRICS_FILE_BASENAME = "/data/system/shutdown-metrics";
    private static final int MOUNT_SERVICE_STOP_PERCENT = 20;
    private static final int PACKAGE_MANAGER_STOP_PERCENT = 6;
    private static final int RADIOS_STATE_POLL_SLEEP_MS = 100;
    private static final int RADIO_STOP_PERCENT = 18;
    public static final java.lang.String REBOOT_SAFEMODE_PROPERTY = "persist.sys.safemode";
    public static final java.lang.String RO_SAFEMODE_PROPERTY = "ro.sys.safemode";
    public static final java.lang.String SHUTDOWN_ACTION_PROPERTY = "sys.shutdown.requested";
    private static final java.lang.String TAG = "ShutdownThread";
    private static java.lang.String mReason;
    private static boolean mReboot;
    private static boolean mRebootCustom;
    private static boolean mRebootHasProgressBar;
    private static boolean mRebootSafeMode;
    private static android.app.AlertDialog sConfirmDialog;
    private boolean mActionDone;
    private final java.lang.Object mActionDoneSync;
    private android.content.Context mContext;
    private android.os.PowerManager.WakeLock mCpuWakeLock;
    private android.os.Handler mHandler;
    private final com.android.server.power.ShutdownThread.Injector mInjector;
    private android.os.PowerManager mPowerManager;
    private android.app.ProgressDialog mProgressDialog;
    private android.os.PowerManager.WakeLock mScreenWakeLock;
    private static final java.lang.Object sIsStartedGuard = new java.lang.Object();
    private static boolean sIsStarted = false;
    private static final com.android.server.power.ShutdownThread sInstance = new com.android.server.power.ShutdownThread();
    private static final android.util.ArrayMap<java.lang.String, java.lang.Long> TRON_METRICS = new android.util.ArrayMap<>();
    private static java.lang.String METRIC_SYSTEM_SERVER = "shutdown_system_server";
    private static java.lang.String METRIC_SEND_BROADCAST = "shutdown_send_shutdown_broadcast";
    private static java.lang.String METRIC_AM = "shutdown_activity_manager";
    private static java.lang.String METRIC_PM = "shutdown_package_manager";
    private static java.lang.String METRIC_RADIOS = "shutdown_radios";
    private static java.lang.String METRIC_RADIO = "shutdown_radio";
    private static java.lang.String METRIC_SHUTDOWN_TIME_START = "begin_shutdown";

    private ShutdownThread() {
        this(new com.android.server.power.ShutdownThread.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    ShutdownThread(com.android.server.power.ShutdownThread.Injector injector) {
        this.mActionDoneSync = new java.lang.Object();
        this.mInjector = injector;
    }

    public static void shutdown(android.content.Context context, java.lang.String str, boolean z) {
        mReboot = false;
        mRebootCustom = false;
        mRebootSafeMode = false;
        mReason = str;
        shutdownInner(context, z);
    }

    private static void shutdownInner(final android.content.Context context, boolean z) {
        int i;
        int i2;
        context.assertRuntimeOverlayThemable();
        synchronized (sIsStartedGuard) {
            try {
                if (sIsStarted) {
                    return;
                }
                com.android.server.power.ShutdownCheckPoints.recordCheckPoint(null);
                int integer = context.getResources().getInteger(android.R.integer.config_lightSensorWarmupTime);
                if (mRebootSafeMode) {
                    i = android.R.string.power_off;
                } else if (integer == 2) {
                    i = android.R.string.serviceClassData;
                } else {
                    i = android.R.string.serial_number;
                }
                if (z) {
                    com.android.server.power.ShutdownThread.CloseDialogReceiver closeDialogReceiver = new com.android.server.power.ShutdownThread.CloseDialogReceiver(context);
                    if (sConfirmDialog != null) {
                        sConfirmDialog.dismiss();
                    }
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                    if (mRebootSafeMode) {
                        i2 = android.R.string.prepend_shortcut_label;
                    } else {
                        i2 = android.R.string.policydesc_disableKeyguardFeatures;
                    }
                    sConfirmDialog = builder.setTitle(i2).setMessage(i).setPositiveButton(android.R.string.yes, new android.content.DialogInterface.OnClickListener() { // from class: com.android.server.power.ShutdownThread.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(android.content.DialogInterface dialogInterface, int i3) {
                            com.android.server.power.ShutdownThread.beginShutdownSequence(context);
                        }
                    }).setNegativeButton(android.R.string.no, (android.content.DialogInterface.OnClickListener) null).create();
                    closeDialogReceiver.dialog = sConfirmDialog;
                    sConfirmDialog.setOnDismissListener(closeDialogReceiver);
                    sConfirmDialog.getWindow().setType(2009);
                    sConfirmDialog.show();
                    return;
                }
                beginShutdownSequence(context);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static class CloseDialogReceiver extends android.content.BroadcastReceiver implements android.content.DialogInterface.OnDismissListener {
        public android.app.Dialog dialog;
        private android.content.Context mContext;

        CloseDialogReceiver(android.content.Context context) {
            this.mContext = context;
            context.registerReceiver(this, new android.content.IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 2);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            this.dialog.cancel();
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(android.content.DialogInterface dialogInterface) {
            this.mContext.unregisterReceiver(this);
        }
    }

    public static void reboot(android.content.Context context, java.lang.String str, boolean z) {
        mReboot = true;
        mRebootCustom = false;
        mRebootSafeMode = false;
        mRebootHasProgressBar = false;
        mReason = str;
        shutdownInner(context, z);
    }

    public static void rebootCustom(android.content.Context context, java.lang.String str, boolean z) {
        mReboot = true;
        mRebootCustom = true;
        mRebootSafeMode = false;
        mRebootHasProgressBar = false;
        mReason = str;
        shutdownInner(context, z);
    }

    public static void rebootSafeMode(android.content.Context context, boolean z) {
        if (((android.os.UserManager) context.getSystemService("user")).hasUserRestriction("no_safe_boot")) {
            return;
        }
        mReboot = true;
        mRebootCustom = false;
        mRebootSafeMode = true;
        mRebootHasProgressBar = false;
        mReason = null;
        shutdownInner(context, z);
    }

    private static android.app.ProgressDialog showShutdownDialog(android.content.Context context) {
        android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(context);
        if (mReason != null && mReason.startsWith("recovery-update")) {
            mRebootHasProgressBar = android.os.RecoverySystem.UNCRYPT_PACKAGE_FILE.exists() && !android.os.RecoverySystem.BLOCK_MAP_FILE.exists();
            progressDialog.setTitle(context.getText(android.R.string.printing_disabled_by));
            if (mRebootHasProgressBar) {
                progressDialog.setMax(100);
                progressDialog.setProgress(0);
                progressDialog.setIndeterminate(false);
                if (!context.getResources().getBoolean(android.R.bool.config_showGesturalNavigationHints)) {
                    progressDialog.setProgressPercentFormat(null);
                }
                progressDialog.setProgressNumberFormat(null);
                progressDialog.setProgressStyle(1);
                progressDialog.setMessage(context.getText(android.R.string.print_service_installed_message));
            } else {
                if (showSysuiReboot()) {
                    return null;
                }
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(context.getText(android.R.string.print_service_installed_title));
            }
        } else if (mReason != null && mReason.equals("recovery")) {
            if (com.android.server.RescueParty.isAttemptingFactoryReset()) {
                progressDialog.setTitle(context.getText(android.R.string.policydesc_disableKeyguardFeatures));
                progressDialog.setMessage(context.getText(android.R.string.serviceClassDataAsync));
                progressDialog.setIndeterminate(true);
            } else {
                if (mRebootCustom && showSysuiReboot()) {
                    return null;
                }
                progressDialog.setTitle(context.getText(android.R.string.preposition_for_time));
                progressDialog.setMessage(context.getText(android.R.string.preposition_for_date));
                progressDialog.setIndeterminate(true);
            }
        } else {
            if (showSysuiReboot()) {
                return null;
            }
            progressDialog.setTitle(context.getText(android.R.string.policydesc_disableKeyguardFeatures));
            progressDialog.setMessage(context.getText(android.R.string.serviceClassDataAsync));
            progressDialog.setIndeterminate(true);
        }
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setType(2009);
        progressDialog.show();
        return progressDialog;
    }

    private static boolean showSysuiReboot() {
        try {
            if (((com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class)).showShutdownUi(mReboot, mReason, mRebootCustom)) {
                return true;
            }
            return false;
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void beginShutdownSequence(android.content.Context context) {
        synchronized (sIsStartedGuard) {
            try {
                if (sIsStarted) {
                    return;
                }
                sIsStarted = true;
                sInstance.mProgressDialog = showShutdownDialog(context);
                sInstance.mContext = context;
                sInstance.mPowerManager = (android.os.PowerManager) context.getSystemService("power");
                sInstance.mCpuWakeLock = null;
                try {
                    sInstance.mCpuWakeLock = sInstance.mPowerManager.newWakeLock(1, "ShutdownThread-cpu");
                    sInstance.mCpuWakeLock.setReferenceCounted(false);
                    sInstance.mCpuWakeLock.acquire();
                } catch (java.lang.SecurityException e) {
                    android.util.Log.w(TAG, "No permission to acquire wake lock", e);
                    sInstance.mCpuWakeLock = null;
                }
                sInstance.mScreenWakeLock = null;
                if (sInstance.mPowerManager.isScreenOn()) {
                    try {
                        sInstance.mScreenWakeLock = sInstance.mPowerManager.newWakeLock(26, "ShutdownThread-screen");
                        sInstance.mScreenWakeLock.setReferenceCounted(false);
                        sInstance.mScreenWakeLock.acquire();
                    } catch (java.lang.SecurityException e2) {
                        android.util.Log.w(TAG, "No permission to acquire wake lock", e2);
                        sInstance.mScreenWakeLock = null;
                    }
                }
                if (android.app.admin.SecurityLog.isLoggingEnabled()) {
                    android.app.admin.SecurityLog.writeEvent(210010, new java.lang.Object[0]);
                }
                sInstance.mHandler = new android.os.Handler() { // from class: com.android.server.power.ShutdownThread.2
                };
                sInstance.start();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void actionDone() {
        synchronized (this.mActionDoneSync) {
            this.mActionDone = true;
            this.mActionDoneSync.notifyAll();
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        android.util.TimingsTraceLog newTimingsLog = newTimingsLog();
        newTimingsLog.traceBegin("SystemServerShutdown");
        metricShutdownStart();
        metricStarted(METRIC_SYSTEM_SERVER);
        java.lang.Thread newDumpThread = com.android.server.power.ShutdownCheckPoints.newDumpThread(new java.io.File(CHECK_POINTS_FILE_BASENAME));
        newDumpThread.start();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(mReboot ? "1" : "0");
        sb.append(mReason != null ? mReason : "");
        android.os.SystemProperties.set(SHUTDOWN_ACTION_PROPERTY, sb.toString());
        if (mRebootSafeMode) {
            android.os.SystemProperties.set(REBOOT_SAFEMODE_PROPERTY, "1");
        }
        newTimingsLog.traceBegin("DumpPreRebootInfo");
        try {
            android.util.Slog.i(TAG, "Logging pre-reboot information...");
            com.android.server.power.PreRebootLogger.log(this.mContext);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to log pre-reboot information", e);
        }
        newTimingsLog.traceEnd();
        metricStarted(METRIC_SEND_BROADCAST);
        newTimingsLog.traceBegin("SendShutdownBroadcast");
        android.util.Log.i(TAG, "Sending shutdown broadcast...");
        this.mActionDone = false;
        android.content.Intent intent = new android.content.Intent("android.intent.action.ACTION_SHUTDOWN");
        intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
        ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).broadcastIntentWithCallback(intent, new android.content.IIntentReceiver.Stub() { // from class: com.android.server.power.ShutdownThread.3
            public void performReceive(android.content.Intent intent2, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2) {
                android.os.Handler handler = com.android.server.power.ShutdownThread.this.mHandler;
                final com.android.server.power.ShutdownThread shutdownThread = com.android.server.power.ShutdownThread.this;
                handler.post(new java.lang.Runnable() { // from class: com.android.server.power.ShutdownThread$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.power.ShutdownThread.this.actionDone();
                    }
                });
            }
        }, (java.lang.String[]) null, -1, (int[]) null, (java.util.function.BiFunction) null, android.app.BroadcastOptions.makeBasic().setDeferralPolicy(2).toBundle());
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
        synchronized (this.mActionDoneSync) {
            while (true) {
                try {
                    if (this.mActionDone) {
                        break;
                    }
                    long elapsedRealtime2 = elapsedRealtime - android.os.SystemClock.elapsedRealtime();
                    if (elapsedRealtime2 <= 0) {
                        android.util.Log.w(TAG, "Shutdown broadcast timed out");
                        break;
                    } else {
                        if (mRebootHasProgressBar) {
                            sInstance.setRebootProgress((int) ((((com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY - elapsedRealtime2) * 1.0d) * 2.0d) / 10000.0d), null);
                        }
                        try {
                            this.mActionDoneSync.wait(java.lang.Math.min(elapsedRealtime2, 500L));
                        } catch (java.lang.InterruptedException e2) {
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(2, null);
        }
        newTimingsLog.traceEnd();
        metricEnded(METRIC_SEND_BROADCAST);
        android.util.Log.i(TAG, "Shutting down activity manager...");
        newTimingsLog.traceBegin("ShutdownActivityManager");
        metricStarted(METRIC_AM);
        android.app.IActivityManager asInterface = android.app.IActivityManager.Stub.asInterface(android.os.ServiceManager.checkService(com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY));
        if (asInterface != null) {
            try {
                asInterface.shutdown(10000);
            } catch (android.os.RemoteException e3) {
            }
        }
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(4, null);
        }
        newTimingsLog.traceEnd();
        metricEnded(METRIC_AM);
        android.util.Log.i(TAG, "Shutting down package manager...");
        newTimingsLog.traceBegin("ShutdownPackageManager");
        metricStarted(METRIC_PM);
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        if (packageManagerInternal != null) {
            packageManagerInternal.shutdown();
        }
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(6, null);
        }
        newTimingsLog.traceEnd();
        metricEnded(METRIC_PM);
        newTimingsLog.traceBegin("ShutdownRadios");
        metricStarted(METRIC_RADIOS);
        shutdownRadios(12000);
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(18, null);
        }
        newTimingsLog.traceEnd();
        metricEnded(METRIC_RADIOS);
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(20, null);
            uncrypt();
        }
        newTimingsLog.traceBegin("ShutdownCheckPointsDumpWait");
        try {
            newDumpThread.join(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        } catch (java.lang.InterruptedException e4) {
        }
        newTimingsLog.traceEnd();
        newTimingsLog.traceEnd();
        metricEnded(METRIC_SYSTEM_SERVER);
        saveMetrics(mReboot, mReason);
        rebootOrShutdown(this.mContext, mReboot, mReason);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.util.TimingsTraceLog newTimingsLog() {
        return new android.util.TimingsTraceLog("ShutdownTiming", 524288L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void metricStarted(java.lang.String str) {
        synchronized (TRON_METRICS) {
            TRON_METRICS.put(str, java.lang.Long.valueOf(android.os.SystemClock.elapsedRealtime() * (-1)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void metricEnded(java.lang.String str) {
        synchronized (TRON_METRICS) {
            TRON_METRICS.put(str, java.lang.Long.valueOf(android.os.SystemClock.elapsedRealtime() + TRON_METRICS.get(str).longValue()));
        }
    }

    private static void metricShutdownStart() {
        synchronized (TRON_METRICS) {
            TRON_METRICS.put(METRIC_SHUTDOWN_TIME_START, java.lang.Long.valueOf(java.lang.System.currentTimeMillis()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRebootProgress(final int i, final java.lang.CharSequence charSequence) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.ShutdownThread.4
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.server.power.ShutdownThread.this.mProgressDialog != null) {
                    com.android.server.power.ShutdownThread.this.mProgressDialog.setProgress(i);
                    if (charSequence != null) {
                        com.android.server.power.ShutdownThread.this.mProgressDialog.setMessage(charSequence);
                    }
                }
            }
        });
    }

    private void shutdownRadios(final int i) {
        long j = i;
        final long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + j;
        final boolean[] zArr = new boolean[1];
        java.lang.Thread thread = new java.lang.Thread() { // from class: com.android.server.power.ShutdownThread.5
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                android.util.TimingsTraceLog newTimingsLog = com.android.server.power.ShutdownThread.newTimingsLog();
                android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) com.android.server.power.ShutdownThread.this.mContext.getSystemService(android.telephony.TelephonyManager.class);
                boolean z = telephonyManager == null || !telephonyManager.isAnyRadioPoweredOn();
                if (!z) {
                    android.util.Log.w(com.android.server.power.ShutdownThread.TAG, "Turning off cellular radios...");
                    com.android.server.power.ShutdownThread.metricStarted(com.android.server.power.ShutdownThread.METRIC_RADIO);
                    telephonyManager.shutdownAllRadios();
                }
                android.util.Log.i(com.android.server.power.ShutdownThread.TAG, "Waiting for Radio...");
                long j2 = elapsedRealtime;
                long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                while (true) {
                    if (j2 - elapsedRealtime2 > 0) {
                        if (com.android.server.power.ShutdownThread.mRebootHasProgressBar) {
                            com.android.server.power.ShutdownThread.sInstance.setRebootProgress(((int) ((((i - r6) * 1.0d) * 12.0d) / i)) + 6, null);
                        }
                        if (!z && (!telephonyManager.isAnyRadioPoweredOn())) {
                            android.util.Log.i(com.android.server.power.ShutdownThread.TAG, "Radio turned off.");
                            com.android.server.power.ShutdownThread.metricEnded(com.android.server.power.ShutdownThread.METRIC_RADIO);
                            newTimingsLog.logDuration("ShutdownRadio", ((java.lang.Long) com.android.server.power.ShutdownThread.TRON_METRICS.get(com.android.server.power.ShutdownThread.METRIC_RADIO)).longValue());
                        }
                        if (z) {
                            android.util.Log.i(com.android.server.power.ShutdownThread.TAG, "Radio shutdown complete.");
                            zArr[0] = true;
                            return;
                        } else {
                            android.os.SystemClock.sleep(100L);
                            j2 = elapsedRealtime;
                            elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                        }
                    } else {
                        return;
                    }
                }
            }
        };
        thread.start();
        try {
            thread.join(j);
        } catch (java.lang.InterruptedException e) {
        }
        if (!zArr[0]) {
            android.util.Log.w(TAG, "Timed out waiting for Radio shutdown.");
        }
    }

    public static void rebootOrShutdown(android.content.Context context, boolean z, java.lang.String str) {
        if (z) {
            android.util.Log.i(TAG, "Rebooting, reason: " + str);
            com.android.server.power.PowerManagerService.lowLevelReboot(str);
            android.util.Log.e(TAG, "Reboot failed, will attempt shutdown instead");
            str = null;
        } else if (context != null) {
            try {
                sInstance.playShutdownVibration(context);
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "Failed to vibrate during shutdown.", e);
            }
        }
        android.util.Log.i(TAG, "Performing low-level shutdown...");
        com.android.server.power.PowerManagerService.lowLevelShutdown(str);
    }

    @com.android.internal.annotations.VisibleForTesting
    void playShutdownVibration(android.content.Context context) {
        android.os.Vibrator vibrator = this.mInjector.getVibrator(context);
        if (!vibrator.hasVibrator()) {
            return;
        }
        android.os.VibrationEffect validShutdownVibration = getValidShutdownVibration(context, vibrator);
        vibrator.vibrate(validShutdownVibration, android.os.VibrationAttributes.createForUsage(18));
        long duration = validShutdownVibration.getDuration();
        com.android.server.power.ShutdownThread.Injector injector = this.mInjector;
        if (duration < 0) {
            duration = 500;
        }
        injector.sleep(duration);
    }

    private static void saveMetrics(boolean z, java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("reboot:");
        sb.append(z ? "y" : "n");
        sb.append(",");
        sb.append("reason:");
        sb.append(str);
        int size = TRON_METRICS.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            java.lang.String keyAt = TRON_METRICS.keyAt(i);
            long longValue = TRON_METRICS.valueAt(i).longValue();
            if (longValue < 0) {
                android.util.Log.e(TAG, "metricEnded wasn't called for " + keyAt);
            } else {
                sb.append(',');
                sb.append(keyAt);
                sb.append(':');
                sb.append(longValue);
            }
        }
        java.io.File file = new java.io.File("/data/system/shutdown-metrics.tmp");
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
            try {
                fileOutputStream.write(sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
                z2 = true;
                fileOutputStream.close();
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Cannot save shutdown metrics", e);
        }
        if (z2) {
            file.renameTo(new java.io.File("/data/system/shutdown-metrics.txt"));
        }
    }

    private void uncrypt() {
        android.util.Log.i(TAG, "Calling uncrypt and monitoring the progress...");
        final android.os.RecoverySystem.ProgressListener progressListener = new android.os.RecoverySystem.ProgressListener() { // from class: com.android.server.power.ShutdownThread.6
            @Override // android.os.RecoverySystem.ProgressListener
            public void onProgress(int i) {
                if (i < 0 || i >= 100) {
                    if (i == 100) {
                        com.android.server.power.ShutdownThread.sInstance.setRebootProgress(i, com.android.server.power.ShutdownThread.this.mContext.getText(android.R.string.print_service_installed_title));
                        return;
                    }
                    return;
                }
                com.android.server.power.ShutdownThread.sInstance.setRebootProgress(((int) ((i * 80.0d) / 100.0d)) + 20, com.android.server.power.ShutdownThread.this.mContext.getText(android.R.string.preposition_for_year));
            }
        };
        final boolean[] zArr = {false};
        java.lang.Thread thread = new java.lang.Thread() { // from class: com.android.server.power.ShutdownThread.7
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    android.os.RecoverySystem.processPackage(com.android.server.power.ShutdownThread.this.mContext, new java.io.File(android.os.FileUtils.readTextFile(android.os.RecoverySystem.UNCRYPT_PACKAGE_FILE, 0, null)), progressListener);
                } catch (java.io.IOException e) {
                    android.util.Log.e(com.android.server.power.ShutdownThread.TAG, "Error uncrypting file", e);
                }
                zArr[0] = true;
            }
        };
        thread.start();
        try {
            thread.join(900000L);
        } catch (java.lang.InterruptedException e) {
        }
        if (!zArr[0]) {
            android.util.Log.w(TAG, "Timed out waiting for uncrypt.");
            try {
                android.os.FileUtils.stringToFile(android.os.RecoverySystem.UNCRYPT_STATUS_FILE, java.lang.String.format("uncrypt_time: %d\nuncrypt_error: %d\n", java.lang.Integer.valueOf(com.android.server.am.ProcessList.CACHED_APP_MIN_ADJ), 100));
            } catch (java.io.IOException e2) {
                android.util.Log.e(TAG, "Failed to write timeout message to uncrypt status", e2);
            }
        }
    }

    private android.os.VibrationEffect getValidShutdownVibration(android.content.Context context, android.os.Vibrator vibrator) {
        android.os.VibrationEffect parseVibrationEffectFromFile = parseVibrationEffectFromFile(this.mInjector.getDefaultShutdownVibrationEffectFilePath(context), vibrator);
        if (parseVibrationEffectFromFile == null) {
            return createDefaultVibrationEffect();
        }
        if (parseVibrationEffectFromFile.getDuration() == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            android.util.Log.w(TAG, "The parsed shutdown vibration is indefinite.");
            return createDefaultVibrationEffect();
        }
        return parseVibrationEffectFromFile;
    }

    private static android.os.VibrationEffect parseVibrationEffectFromFile(java.lang.String str, android.os.Vibrator vibrator) {
        if (!android.text.TextUtils.isEmpty(str)) {
            try {
                return android.os.vibrator.persistence.VibrationXmlParser.parseDocument(new java.io.FileReader(str)).resolve(vibrator);
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Error parsing default shutdown vibration effect.", e);
                return null;
            }
        }
        return null;
    }

    private static android.os.VibrationEffect createDefaultVibrationEffect() {
        return android.os.VibrationEffect.createOneShot(500L, -1);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        public android.os.Vibrator getVibrator(android.content.Context context) {
            return new android.os.SystemVibrator(context);
        }

        public void sleep(long j) {
            try {
                java.lang.Thread.sleep(j);
            } catch (java.lang.InterruptedException e) {
            }
        }

        public java.lang.String getDefaultShutdownVibrationEffectFilePath(android.content.Context context) {
            return context.getResources().getString(android.R.string.config_defaultOnDeviceIntelligenceService);
        }
    }
}
