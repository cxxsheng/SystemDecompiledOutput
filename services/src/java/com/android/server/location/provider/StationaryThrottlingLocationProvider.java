package com.android.server.location.provider;

/* loaded from: classes2.dex */
public final class StationaryThrottlingLocationProvider extends com.android.server.location.provider.DelegateLocationProvider implements com.android.server.location.injector.DeviceIdleHelper.DeviceIdleListener, com.android.server.DeviceIdleInternal.StationaryListener {
    private static final long MAX_STATIONARY_LOCATION_AGE_MS = 30000;
    private static final long MIN_INTERVAL_MS = 1000;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    com.android.server.location.provider.StationaryThrottlingLocationProvider.DeliverLastLocationRunnable mDeliverLastLocationCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDeviceIdle;
    private final com.android.server.location.injector.DeviceIdleHelper mDeviceIdleHelper;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDeviceStationary;
    private final com.android.server.location.injector.DeviceStationaryHelper mDeviceStationaryHelper;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mDeviceStationaryRealtimeMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.location.provider.ProviderRequest mIncomingRequest;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    android.location.Location mLastLocation;
    final java.lang.Object mLock;
    private final java.lang.String mName;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.location.provider.ProviderRequest mOutgoingRequest;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long mThrottlingIntervalMs;

    @Override // com.android.server.location.provider.DelegateLocationProvider, com.android.server.location.provider.AbstractLocationProvider.Listener
    public /* bridge */ /* synthetic */ void onStateChanged(com.android.server.location.provider.AbstractLocationProvider.State state, com.android.server.location.provider.AbstractLocationProvider.State state2) {
        super.onStateChanged(state, state2);
    }

    public StationaryThrottlingLocationProvider(java.lang.String str, com.android.server.location.injector.Injector injector, com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider) {
        super(com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, abstractLocationProvider);
        this.mLock = new java.lang.Object();
        this.mDeviceIdle = false;
        this.mDeviceStationary = false;
        this.mDeviceStationaryRealtimeMs = Long.MIN_VALUE;
        this.mIncomingRequest = android.location.provider.ProviderRequest.EMPTY_REQUEST;
        this.mOutgoingRequest = android.location.provider.ProviderRequest.EMPTY_REQUEST;
        this.mThrottlingIntervalMs = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        this.mDeliverLastLocationCallback = null;
        this.mName = str;
        this.mDeviceIdleHelper = injector.getDeviceIdleHelper();
        this.mDeviceStationaryHelper = injector.getDeviceStationaryHelper();
        initializeDelegate();
    }

    @Override // com.android.server.location.provider.DelegateLocationProvider, com.android.server.location.provider.AbstractLocationProvider.Listener
    public void onReportLocation(android.location.LocationResult locationResult) {
        super.onReportLocation(locationResult);
        synchronized (this.mLock) {
            this.mLastLocation = locationResult.getLastLocation();
            onThrottlingChangedLocked(false);
        }
    }

    @Override // com.android.server.location.provider.DelegateLocationProvider, com.android.server.location.provider.AbstractLocationProvider
    protected void onStart() {
        this.mDelegate.getController().start();
        synchronized (this.mLock) {
            this.mDeviceIdleHelper.addListener(this);
            onDeviceIdleChanged(this.mDeviceIdleHelper.isDeviceIdle());
        }
    }

    @Override // com.android.server.location.provider.DelegateLocationProvider, com.android.server.location.provider.AbstractLocationProvider
    protected void onStop() {
        synchronized (this.mLock) {
            try {
                this.mDeviceIdleHelper.removeListener(this);
                onDeviceIdleChanged(false);
                this.mIncomingRequest = android.location.provider.ProviderRequest.EMPTY_REQUEST;
                this.mOutgoingRequest = android.location.provider.ProviderRequest.EMPTY_REQUEST;
                this.mThrottlingIntervalMs = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                if (this.mDeliverLastLocationCallback != null) {
                    com.android.server.FgThread.getHandler().removeCallbacks(this.mDeliverLastLocationCallback);
                    this.mDeliverLastLocationCallback = null;
                }
                this.mLastLocation = null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mDelegate.getController().stop();
    }

    @Override // com.android.server.location.provider.DelegateLocationProvider, com.android.server.location.provider.AbstractLocationProvider
    protected void onSetRequest(android.location.provider.ProviderRequest providerRequest) {
        synchronized (this.mLock) {
            this.mIncomingRequest = providerRequest;
            onThrottlingChangedLocked(true);
        }
    }

    @Override // com.android.server.location.injector.DeviceIdleHelper.DeviceIdleListener
    public void onDeviceIdleChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z == this.mDeviceIdle) {
                    return;
                }
                this.mDeviceIdle = z;
                if (z) {
                    this.mDeviceStationaryHelper.addListener(this);
                } else {
                    this.mDeviceStationaryHelper.removeListener(this);
                    this.mDeviceStationary = false;
                    this.mDeviceStationaryRealtimeMs = Long.MIN_VALUE;
                    onThrottlingChangedLocked(false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onDeviceStationaryChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mDeviceIdle) {
                    if (this.mDeviceStationary == z) {
                        return;
                    }
                    this.mDeviceStationary = z;
                    if (this.mDeviceStationary) {
                        this.mDeviceStationaryRealtimeMs = android.os.SystemClock.elapsedRealtime();
                    } else {
                        this.mDeviceStationaryRealtimeMs = Long.MIN_VALUE;
                    }
                    onThrottlingChangedLocked(false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onThrottlingChangedLocked(boolean z) {
        long j;
        android.location.provider.ProviderRequest providerRequest;
        if (this.mDeviceStationary && this.mDeviceIdle && !this.mIncomingRequest.isLocationSettingsIgnored() && this.mIncomingRequest.getQuality() != 100 && this.mLastLocation != null && this.mLastLocation.getElapsedRealtimeAgeMillis(this.mDeviceStationaryRealtimeMs) <= 30000) {
            j = java.lang.Math.max(this.mIncomingRequest.getIntervalMillis(), 1000L);
        } else {
            j = Long.MAX_VALUE;
        }
        if (j != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            providerRequest = android.location.provider.ProviderRequest.EMPTY_REQUEST;
        } else {
            providerRequest = this.mIncomingRequest;
        }
        if (!providerRequest.equals(this.mOutgoingRequest)) {
            this.mOutgoingRequest = providerRequest;
            this.mDelegate.getController().setRequest(this.mOutgoingRequest);
        }
        if (j == this.mThrottlingIntervalMs) {
            return;
        }
        long j2 = this.mThrottlingIntervalMs;
        this.mThrottlingIntervalMs = j;
        if (this.mThrottlingIntervalMs != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            if (j2 == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                if (com.android.server.location.LocationManagerService.D) {
                    android.util.Log.d(com.android.server.location.LocationManagerService.TAG, this.mName + " provider stationary throttled");
                }
                com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderStationaryThrottled(this.mName, true, this.mOutgoingRequest);
            }
            if (this.mDeliverLastLocationCallback != null) {
                com.android.server.FgThread.getHandler().removeCallbacks(this.mDeliverLastLocationCallback);
            }
            this.mDeliverLastLocationCallback = new com.android.server.location.provider.StationaryThrottlingLocationProvider.DeliverLastLocationRunnable();
            com.android.internal.util.Preconditions.checkState(this.mLastLocation != null);
            if (z) {
                com.android.server.FgThread.getHandler().post(this.mDeliverLastLocationCallback);
                return;
            } else {
                com.android.server.FgThread.getHandler().postDelayed(this.mDeliverLastLocationCallback, this.mThrottlingIntervalMs - this.mLastLocation.getElapsedRealtimeAgeMillis());
                return;
            }
        }
        if (j2 != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderStationaryThrottled(this.mName, false, this.mOutgoingRequest);
            if (com.android.server.location.LocationManagerService.D) {
                android.util.Log.d(com.android.server.location.LocationManagerService.TAG, this.mName + " provider stationary unthrottled");
            }
        }
        com.android.server.FgThread.getHandler().removeCallbacks(this.mDeliverLastLocationCallback);
        this.mDeliverLastLocationCallback = null;
    }

    @Override // com.android.server.location.provider.DelegateLocationProvider, com.android.server.location.provider.AbstractLocationProvider
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (this.mThrottlingIntervalMs != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            printWriter.println("stationary throttled=" + this.mLastLocation);
        } else {
            printWriter.print("stationary throttled=false");
            if (!this.mDeviceIdle) {
                printWriter.print(" (not idle)");
            }
            if (!this.mDeviceStationary) {
                printWriter.print(" (not stationary)");
            }
            printWriter.println();
        }
        this.mDelegate.dump(fileDescriptor, printWriter, strArr);
    }

    private class DeliverLastLocationRunnable implements java.lang.Runnable {
        DeliverLastLocationRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.location.provider.StationaryThrottlingLocationProvider.this.mLock) {
                try {
                    if (com.android.server.location.provider.StationaryThrottlingLocationProvider.this.mDeliverLastLocationCallback != this) {
                        return;
                    }
                    if (com.android.server.location.provider.StationaryThrottlingLocationProvider.this.mLastLocation == null) {
                        return;
                    }
                    android.location.Location location = new android.location.Location(com.android.server.location.provider.StationaryThrottlingLocationProvider.this.mLastLocation);
                    location.setTime(java.lang.System.currentTimeMillis());
                    location.setElapsedRealtimeNanos(android.os.SystemClock.elapsedRealtimeNanos());
                    if (location.hasSpeed()) {
                        location.removeSpeed();
                        if (location.hasSpeedAccuracy()) {
                            location.removeSpeedAccuracy();
                        }
                    }
                    if (location.hasBearing()) {
                        location.removeBearing();
                        if (location.hasBearingAccuracy()) {
                            location.removeBearingAccuracy();
                        }
                    }
                    com.android.server.location.provider.StationaryThrottlingLocationProvider.this.mLastLocation = location;
                    com.android.server.FgThread.getHandler().postDelayed(this, com.android.server.location.provider.StationaryThrottlingLocationProvider.this.mThrottlingIntervalMs);
                    com.android.server.location.provider.StationaryThrottlingLocationProvider.this.reportLocation(android.location.LocationResult.wrap(new android.location.Location[]{location}));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
