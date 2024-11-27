package com.android.server.timedetector;

/* loaded from: classes2.dex */
public final class GnssTimeUpdateService extends android.os.Binder {
    private static final java.lang.String ATTRIBUTION_TAG = "GnssTimeUpdateService";
    private static final boolean D = android.util.Log.isLoggable("GnssTimeUpdateService", 3);
    private static final java.time.Duration GNSS_TIME_UPDATE_ALARM_INTERVAL = java.time.Duration.ofHours(4);
    private static final java.lang.String TAG = "GnssTimeUpdateService";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.app.AlarmManager.OnAlarmListener mAlarmListener;
    private final android.app.AlarmManager mAlarmManager;
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private volatile android.app.time.UnixEpochTime mLastSuggestedGnssTime;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.location.LocationListener mLocationListener;
    private final android.location.LocationManager mLocationManager;
    private final android.location.LocationManagerInternal mLocationManagerInternal;
    private final com.android.server.timedetector.TimeDetectorInternal mTimeDetectorInternal;
    private final android.util.LocalLog mLocalLog = new android.util.LocalLog(10, false);
    private final java.util.concurrent.Executor mExecutor = com.android.server.FgThread.getExecutor();
    private final android.os.Handler mHandler = com.android.server.FgThread.getHandler();
    private final java.lang.Object mLock = new java.lang.Object();

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.timedetector.GnssTimeUpdateService mService;

        public Lifecycle(@android.annotation.NonNull android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            android.content.Context createAttributionContext = getContext().createAttributionContext("GnssTimeUpdateService");
            this.mService = new com.android.server.timedetector.GnssTimeUpdateService(createAttributionContext, (android.app.AlarmManager) createAttributionContext.getSystemService(android.app.AlarmManager.class), (android.location.LocationManager) createAttributionContext.getSystemService(android.location.LocationManager.class), (android.location.LocationManagerInternal) com.android.server.LocalServices.getService(android.location.LocationManagerInternal.class), (com.android.server.timedetector.TimeDetectorInternal) com.android.server.LocalServices.getService(com.android.server.timedetector.TimeDetectorInternal.class));
            publishBinderService("gnss_time_update_service", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 600) {
                this.mService.startGnssListeningInternal();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    GnssTimeUpdateService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.app.AlarmManager alarmManager, @android.annotation.NonNull android.location.LocationManager locationManager, @android.annotation.NonNull android.location.LocationManagerInternal locationManagerInternal, @android.annotation.NonNull com.android.server.timedetector.TimeDetectorInternal timeDetectorInternal) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        java.util.Objects.requireNonNull(alarmManager);
        this.mAlarmManager = alarmManager;
        java.util.Objects.requireNonNull(locationManager);
        this.mLocationManager = locationManager;
        java.util.Objects.requireNonNull(locationManagerInternal);
        this.mLocationManagerInternal = locationManagerInternal;
        java.util.Objects.requireNonNull(timeDetectorInternal);
        this.mTimeDetectorInternal = timeDetectorInternal;
    }

    @android.annotation.RequiresPermission("android.permission.SET_TIME")
    boolean startGnssListening() {
        this.mContext.enforceCallingPermission("android.permission.SET_TIME", "Start GNSS listening");
        this.mLocalLog.log("startGnssListening() called");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return startGnssListeningInternal();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean startGnssListeningInternal() {
        if (!this.mLocationManager.hasProvider("gps")) {
            logError("GPS provider does not exist on this device");
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (this.mLocationListener != null) {
                    logDebug("Already listening for GNSS updates");
                    return true;
                }
                if (this.mAlarmListener != null) {
                    this.mAlarmManager.cancel(this.mAlarmListener);
                    this.mAlarmListener = null;
                }
                startGnssListeningLocked();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startGnssListeningLocked() {
        logDebug("startGnssListeningLocked()");
        this.mLocationListener = new android.location.LocationListener() { // from class: com.android.server.timedetector.GnssTimeUpdateService$$ExternalSyntheticLambda1
            @Override // android.location.LocationListener
            public final void onLocationChanged(android.location.Location location) {
                com.android.server.timedetector.GnssTimeUpdateService.this.lambda$startGnssListeningLocked$0(location);
            }
        };
        this.mLocationManager.requestLocationUpdates("gps", new android.location.LocationRequest.Builder(com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME).setMinUpdateIntervalMillis(0L).build(), this.mExecutor, this.mLocationListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGnssListeningLocked$0(android.location.Location location) {
        handleLocationAvailable();
    }

    private void handleLocationAvailable() {
        logDebug("handleLocationAvailable()");
        android.location.LocationTime gnssTimeMillis = this.mLocationManagerInternal.getGnssTimeMillis();
        if (gnssTimeMillis != null) {
            java.lang.String str = "Passive location time received: " + gnssTimeMillis;
            logDebug(str);
            this.mLocalLog.log(str);
            suggestGnssTime(gnssTimeMillis);
        } else {
            logDebug("getGnssTimeMillis() returned null");
        }
        synchronized (this.mLock) {
            try {
                if (this.mLocationListener == null) {
                    logWarning("mLocationListener unexpectedly null");
                } else {
                    this.mLocationManager.removeUpdates(this.mLocationListener);
                    this.mLocationListener = null;
                }
                if (this.mAlarmListener != null) {
                    logWarning("mAlarmListener was unexpectedly non-null");
                    this.mAlarmManager.cancel(this.mAlarmListener);
                }
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + GNSS_TIME_UPDATE_ALARM_INTERVAL.toMillis();
                this.mAlarmListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.timedetector.GnssTimeUpdateService$$ExternalSyntheticLambda0
                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        com.android.server.timedetector.GnssTimeUpdateService.this.handleAlarmFired();
                    }
                };
                this.mAlarmManager.set(2, elapsedRealtime, "GnssTimeUpdateService", this.mAlarmListener, this.mHandler);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAlarmFired() {
        logDebug("handleAlarmFired()");
        synchronized (this.mLock) {
            this.mAlarmListener = null;
            startGnssListeningLocked();
        }
    }

    private void suggestGnssTime(android.location.LocationTime locationTime) {
        logDebug("suggestGnssTime()");
        android.app.time.UnixEpochTime unixEpochTime = new android.app.time.UnixEpochTime(locationTime.getElapsedRealtimeNanos() / 1000000, locationTime.getUnixEpochTimeMillis());
        this.mLastSuggestedGnssTime = unixEpochTime;
        this.mTimeDetectorInternal.suggestGnssTime(new com.android.server.timedetector.GnssTimeSuggestion(unixEpochTime));
    }

    @Override // android.os.Binder
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, "GnssTimeUpdateService", printWriter)) {
            printWriter.println("mLastSuggestedGnssTime: " + this.mLastSuggestedGnssTime);
            synchronized (this.mLock) {
                try {
                    printWriter.print("state: ");
                    if (this.mLocationListener != null) {
                        printWriter.println("time updates enabled");
                    } else {
                        printWriter.println("alarm enabled");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            printWriter.println("Log:");
            this.mLocalLog.dump(printWriter);
        }
    }

    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.timedetector.GnssTimeUpdateServiceShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    private void logError(java.lang.String str) {
        android.util.Log.e("GnssTimeUpdateService", str);
        this.mLocalLog.log(str);
    }

    private void logWarning(java.lang.String str) {
        android.util.Log.w("GnssTimeUpdateService", str);
        this.mLocalLog.log(str);
    }

    private void logDebug(java.lang.String str) {
        if (D) {
            android.util.Log.d("GnssTimeUpdateService", str);
        }
    }
}
