package com.android.server.location.provider;

/* loaded from: classes2.dex */
public class LocationProviderManager extends com.android.server.location.listeners.ListenerMultiplexer<java.lang.Object, com.android.server.location.provider.LocationProviderManager.LocationTransport, com.android.server.location.provider.LocationProviderManager.Registration, android.location.provider.ProviderRequest> implements com.android.server.location.provider.AbstractLocationProvider.Listener {
    private static final float FASTEST_INTERVAL_JITTER_PERCENTAGE = 0.1f;
    private static final long MAX_CURRENT_LOCATION_AGE_MS = 30000;
    private static final int MAX_FASTEST_INTERVAL_JITTER_MS = 30000;
    private static final long MAX_GET_CURRENT_LOCATION_TIMEOUT_MS = 30000;
    private static final long MAX_HIGH_POWER_INTERVAL_MS = 300000;
    private static final long MIN_COARSE_INTERVAL_MS = 600000;
    private static final long MIN_REQUEST_DELAY_MS = 30000;
    private static final int STATE_STARTED = 0;
    private static final int STATE_STOPPED = 2;
    private static final int STATE_STOPPING = 1;
    private static final long TEMPORARY_APP_ALLOWLIST_DURATION_MS = 10000;
    private static final java.lang.String WAKELOCK_TAG = "*location*";
    private static final long WAKELOCK_TIMEOUT_MS = 30000;
    private final com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener mAdasPackageAllowlistChangedListener;
    protected final com.android.server.location.injector.AlarmHelper mAlarmHelper;
    private final android.location.altitude.AltitudeConverter mAltitudeConverter;
    private final com.android.server.location.injector.AppForegroundHelper.AppForegroundListener mAppForegroundChangedListener;
    protected final com.android.server.location.injector.AppForegroundHelper mAppForegroundHelper;
    protected final com.android.server.location.injector.AppOpsHelper mAppOpsHelper;
    private final com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener mBackgroundThrottleIntervalChangedListener;
    private final com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener mBackgroundThrottlePackageWhitelistChangedListener;
    protected final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    @android.annotation.Nullable
    private android.app.AlarmManager.OnAlarmListener mDelayedRegister;

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private final android.util.SparseBooleanArray mEnabled;

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private final java.util.ArrayList<android.location.LocationManagerInternal.ProviderEnabledListener> mEnabledListeners;
    private final com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener mIgnoreSettingsPackageWhitelistChangedListener;
    private volatile boolean mIsAltitudeConverterIdle;

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private final android.util.SparseArray<com.android.server.location.provider.LocationProviderManager.LastLocation> mLastLocations;
    private final com.android.server.location.injector.SettingsHelper.UserSettingChangedListener mLocationEnabledChangedListener;
    protected final com.android.server.location.fudger.LocationFudger mLocationFudger;
    protected final android.location.LocationManagerInternal mLocationManagerInternal;
    private final com.android.server.location.injector.SettingsHelper.UserSettingChangedListener mLocationPackageBlacklistChangedListener;
    protected final com.android.server.location.injector.LocationPermissionsHelper mLocationPermissionsHelper;
    private final com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener mLocationPermissionsListener;
    private final com.android.server.location.injector.LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener mLocationPowerSaveModeChangedListener;
    protected final com.android.server.location.injector.LocationPowerSaveModeHelper mLocationPowerSaveModeHelper;
    protected final com.android.server.location.settings.LocationSettings mLocationSettings;
    protected final com.android.server.location.injector.LocationUsageLogger mLocationUsageLogger;
    private final com.android.server.location.settings.LocationSettings.LocationUserSettingsListener mLocationUserSettingsListener;
    protected final java.lang.String mName;
    private final com.android.server.location.injector.PackageResetHelper mPackageResetHelper;
    private final com.android.server.location.injector.PackageResetHelper.Responder mPackageResetResponder;

    @android.annotation.Nullable
    private final com.android.server.location.provider.PassiveLocationProviderManager mPassiveManager;
    protected final com.android.server.location.provider.MockableLocationProvider mProvider;
    private final java.util.concurrent.CopyOnWriteArrayList<android.location.provider.IProviderRequestListener> mProviderRequestListeners;
    private final java.util.Collection<java.lang.String> mRequiredPermissions;
    private final com.android.server.location.injector.ScreenInteractiveHelper.ScreenInteractiveChangedListener mScreenInteractiveChangedListener;
    protected final com.android.server.location.injector.ScreenInteractiveHelper mScreenInteractiveHelper;
    protected final com.android.server.location.injector.SettingsHelper mSettingsHelper;

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private int mState;

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    @android.annotation.Nullable
    private com.android.server.location.provider.LocationProviderManager.StateChangedListener mStateChangedListener;
    private final com.android.server.location.injector.UserInfoHelper.UserListener mUserChangedListener;
    protected final com.android.server.location.injector.UserInfoHelper mUserHelper;

    protected interface LocationTransport {
        void deliverOnFlushComplete(int i) throws java.lang.Exception;

        void deliverOnLocationChanged(android.location.LocationResult locationResult, @android.annotation.Nullable android.os.IRemoteCallback iRemoteCallback) throws java.lang.Exception;
    }

    protected interface ProviderTransport {
        void deliverOnProviderEnabledChanged(java.lang.String str, boolean z) throws java.lang.Exception;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface State {
    }

    public interface StateChangedListener {
        void onStateChanged(java.lang.String str, com.android.server.location.provider.AbstractLocationProvider.State state, com.android.server.location.provider.AbstractLocationProvider.State state2);
    }

    protected static final class LocationListenerTransport implements com.android.server.location.provider.LocationProviderManager.LocationTransport, com.android.server.location.provider.LocationProviderManager.ProviderTransport {
        private final android.location.ILocationListener mListener;

        LocationListenerTransport(android.location.ILocationListener iLocationListener) {
            java.util.Objects.requireNonNull(iLocationListener);
            this.mListener = iLocationListener;
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
        public void deliverOnLocationChanged(android.location.LocationResult locationResult, @android.annotation.Nullable android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
            try {
                this.mListener.onLocationChanged(locationResult.asList(), iRemoteCallback);
            } catch (java.lang.RuntimeException e) {
                final java.lang.RuntimeException runtimeException = new java.lang.RuntimeException(e);
                com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$LocationListenerTransport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.provider.LocationProviderManager.LocationListenerTransport.lambda$deliverOnLocationChanged$0(runtimeException);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$deliverOnLocationChanged$0(java.lang.RuntimeException runtimeException) {
            throw runtimeException;
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
        public void deliverOnFlushComplete(int i) throws android.os.RemoteException {
            try {
                this.mListener.onFlushComplete(i);
            } catch (java.lang.RuntimeException e) {
                final java.lang.RuntimeException runtimeException = new java.lang.RuntimeException(e);
                com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$LocationListenerTransport$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.provider.LocationProviderManager.LocationListenerTransport.lambda$deliverOnFlushComplete$1(runtimeException);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$deliverOnFlushComplete$1(java.lang.RuntimeException runtimeException) {
            throw runtimeException;
        }

        @Override // com.android.server.location.provider.LocationProviderManager.ProviderTransport
        public void deliverOnProviderEnabledChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
            try {
                this.mListener.onProviderEnabledChanged(str, z);
            } catch (java.lang.RuntimeException e) {
                final java.lang.RuntimeException runtimeException = new java.lang.RuntimeException(e);
                com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$LocationListenerTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.provider.LocationProviderManager.LocationListenerTransport.lambda$deliverOnProviderEnabledChanged$2(runtimeException);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$deliverOnProviderEnabledChanged$2(java.lang.RuntimeException runtimeException) {
            throw runtimeException;
        }
    }

    protected static final class LocationPendingIntentTransport implements com.android.server.location.provider.LocationProviderManager.LocationTransport, com.android.server.location.provider.LocationProviderManager.ProviderTransport {
        private final android.content.Context mContext;
        private final android.app.PendingIntent mPendingIntent;

        public LocationPendingIntentTransport(android.content.Context context, android.app.PendingIntent pendingIntent) {
            this.mContext = context;
            this.mPendingIntent = pendingIntent;
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
        public void deliverOnLocationChanged(android.location.LocationResult locationResult, @android.annotation.Nullable final android.os.IRemoteCallback iRemoteCallback) throws android.app.PendingIntent.CanceledException {
            java.lang.Runnable runnable;
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setDontSendToRestrictedApps(true);
            makeBasic.setTemporaryAppAllowlist(10000L, 0, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_LOCATION_PROVIDER, "");
            android.content.Intent putExtra = new android.content.Intent().putExtra("location", locationResult.getLastLocation());
            if (locationResult.size() > 1) {
                putExtra.putExtra("locations", (android.os.Parcelable[]) locationResult.asList().toArray(new android.location.Location[0]));
            }
            if (iRemoteCallback == null) {
                runnable = null;
            } else {
                runnable = new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$LocationPendingIntentTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.provider.LocationProviderManager.LocationPendingIntentTransport.lambda$deliverOnLocationChanged$0(iRemoteCallback);
                    }
                };
            }
            com.android.server.location.provider.LocationProviderManager.PendingIntentSender.send(this.mPendingIntent, this.mContext, putExtra, runnable, makeBasic.toBundle());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$deliverOnLocationChanged$0(android.os.IRemoteCallback iRemoteCallback) {
            try {
                iRemoteCallback.sendResult((android.os.Bundle) null);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
        public void deliverOnFlushComplete(int i) throws android.app.PendingIntent.CanceledException {
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setDontSendToRestrictedApps(true);
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            this.mPendingIntent.send(this.mContext, 0, new android.content.Intent().putExtra("flushComplete", i), null, null, null, makeBasic.toBundle());
        }

        @Override // com.android.server.location.provider.LocationProviderManager.ProviderTransport
        public void deliverOnProviderEnabledChanged(java.lang.String str, boolean z) throws android.app.PendingIntent.CanceledException {
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setDontSendToRestrictedApps(true);
            this.mPendingIntent.send(this.mContext, 0, new android.content.Intent().putExtra("providerEnabled", z), null, null, null, makeBasic.toBundle());
        }
    }

    protected static final class GetCurrentLocationTransport implements com.android.server.location.provider.LocationProviderManager.LocationTransport {
        private final android.location.ILocationCallback mCallback;

        GetCurrentLocationTransport(android.location.ILocationCallback iLocationCallback) {
            java.util.Objects.requireNonNull(iLocationCallback);
            this.mCallback = iLocationCallback;
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
        public void deliverOnLocationChanged(@android.annotation.Nullable android.location.LocationResult locationResult, @android.annotation.Nullable android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
            com.android.internal.util.Preconditions.checkState(iRemoteCallback == null);
            try {
                if (locationResult != null) {
                    this.mCallback.onLocation(locationResult.getLastLocation());
                } else {
                    this.mCallback.onLocation((android.location.Location) null);
                }
            } catch (java.lang.RuntimeException e) {
                final java.lang.RuntimeException runtimeException = new java.lang.RuntimeException(e);
                com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$GetCurrentLocationTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.provider.LocationProviderManager.GetCurrentLocationTransport.lambda$deliverOnLocationChanged$0(runtimeException);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$deliverOnLocationChanged$0(java.lang.RuntimeException runtimeException) {
            throw runtimeException;
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
        public void deliverOnFlushComplete(int i) {
        }
    }

    protected abstract class Registration extends com.android.server.location.listeners.RemovableListenerRegistration<java.lang.Object, com.android.server.location.provider.LocationProviderManager.LocationTransport> {
        private final android.location.LocationRequest mBaseRequest;

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        private boolean mForeground;
        private final android.location.util.identity.CallerIdentity mIdentity;

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        private boolean mIsUsingHighPower;

        @android.annotation.Nullable
        private android.location.Location mLastLocation;
        private final int mPermissionLevel;

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        private boolean mPermitted;

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        private android.location.LocationRequest mProviderLocationRequest;

        @android.annotation.Nullable
        abstract com.android.internal.listeners.ListenerExecutor.ListenerOperation<com.android.server.location.provider.LocationProviderManager.LocationTransport> acceptLocationChange(android.location.LocationResult locationResult);

        protected Registration(android.location.LocationRequest locationRequest, android.location.util.identity.CallerIdentity callerIdentity, java.util.concurrent.Executor executor, com.android.server.location.provider.LocationProviderManager.LocationTransport locationTransport, int i) {
            super(executor, locationTransport);
            this.mLastLocation = null;
            com.android.internal.util.Preconditions.checkArgument(callerIdentity.getListenerId() != null);
            com.android.internal.util.Preconditions.checkArgument(i > 0);
            com.android.internal.util.Preconditions.checkArgument(!locationRequest.getWorkSource().isEmpty());
            java.util.Objects.requireNonNull(locationRequest);
            this.mBaseRequest = locationRequest;
            java.util.Objects.requireNonNull(callerIdentity);
            this.mIdentity = callerIdentity;
            this.mPermissionLevel = i;
            this.mProviderLocationRequest = locationRequest;
        }

        public final android.location.util.identity.CallerIdentity getIdentity() {
            return this.mIdentity;
        }

        public final android.location.LocationRequest getRequest() {
            android.location.LocationRequest locationRequest;
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                locationRequest = this.mProviderLocationRequest;
            }
            return locationRequest;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onRegister() {
            super.onRegister();
            if (com.android.server.location.LocationManagerService.D) {
                android.util.Log.d(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider added registration from " + getIdentity() + " -> " + getRequest());
            }
            com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderClientRegistered(com.android.server.location.provider.LocationProviderManager.this.mName, getIdentity(), this.mBaseRequest);
            this.mPermitted = com.android.server.location.provider.LocationProviderManager.this.mLocationPermissionsHelper.hasLocationPermissions(this.mPermissionLevel, getIdentity());
            this.mForeground = com.android.server.location.provider.LocationProviderManager.this.mAppForegroundHelper.isAppForeground(getIdentity().getUid());
            this.mProviderLocationRequest = calculateProviderLocationRequest();
            this.mIsUsingHighPower = isUsingHighPower();
            if (this.mForeground) {
                com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderClientForeground(com.android.server.location.provider.LocationProviderManager.this.mName, getIdentity());
            }
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration, com.android.server.location.listeners.ListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onUnregister() {
            com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderClientUnregistered(com.android.server.location.provider.LocationProviderManager.this.mName, getIdentity());
            if (com.android.server.location.LocationManagerService.D) {
                android.util.Log.d(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider removed registration from " + getIdentity());
            }
            super.onUnregister();
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onActive() {
            com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderClientActive(com.android.server.location.provider.LocationProviderManager.this.mName, getIdentity());
            if (!getRequest().isHiddenFromAppOps()) {
                com.android.server.location.provider.LocationProviderManager.this.mAppOpsHelper.startOpNoThrow(41, getIdentity());
            }
            onHighPowerUsageChanged();
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onInactive() {
            onHighPowerUsageChanged();
            if (!getRequest().isHiddenFromAppOps()) {
                com.android.server.location.provider.LocationProviderManager.this.mAppOpsHelper.finishOp(41, getIdentity());
            }
            com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderClientInactive(com.android.server.location.provider.LocationProviderManager.this.mName, getIdentity());
        }

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        final void setLastDeliveredLocation(@android.annotation.Nullable android.location.Location location) {
            this.mLastLocation = location;
        }

        public final android.location.Location getLastDeliveredLocation() {
            android.location.Location location;
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                location = this.mLastLocation;
            }
            return location;
        }

        public int getPermissionLevel() {
            int i;
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                i = this.mPermissionLevel;
            }
            return i;
        }

        public final boolean isForeground() {
            boolean z;
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                z = this.mForeground;
            }
            return z;
        }

        public final boolean isPermitted() {
            boolean z;
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                z = this.mPermitted;
            }
            return z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$flush$1(final int i) {
            executeOperation(new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.provider.LocationProviderManager$Registration$$ExternalSyntheticLambda0
                public final void operate(java.lang.Object obj) {
                    ((com.android.server.location.provider.LocationProviderManager.LocationTransport) obj).deliverOnFlushComplete(i);
                }
            });
        }

        public final void flush(final int i) {
            com.android.server.location.provider.LocationProviderManager.this.mProvider.getController().flush(new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$Registration$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.provider.LocationProviderManager.Registration.this.lambda$flush$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final com.android.server.location.listeners.ListenerMultiplexer<java.lang.Object, ? super com.android.server.location.provider.LocationProviderManager.LocationTransport, ?, ?> getOwner() {
            return com.android.server.location.provider.LocationProviderManager.this;
        }

        final boolean onProviderPropertiesChanged() {
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                onHighPowerUsageChanged();
            }
            return false;
        }

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        private void onHighPowerUsageChanged() {
            boolean isUsingHighPower = isUsingHighPower();
            if (isUsingHighPower != this.mIsUsingHighPower) {
                this.mIsUsingHighPower = isUsingHighPower;
                if (!getRequest().isHiddenFromAppOps()) {
                    if (this.mIsUsingHighPower) {
                        com.android.server.location.provider.LocationProviderManager.this.mAppOpsHelper.startOpNoThrow(42, getIdentity());
                    } else {
                        com.android.server.location.provider.LocationProviderManager.this.mAppOpsHelper.finishOp(42, getIdentity());
                    }
                }
            }
        }

        private boolean isUsingHighPower() {
            android.location.provider.ProviderProperties properties = com.android.server.location.provider.LocationProviderManager.this.getProperties();
            return properties != null && isActive() && getRequest().getIntervalMillis() < 300000 && properties.getPowerUsage() == 3;
        }

        final boolean onLocationPermissionsChanged(@android.annotation.Nullable java.lang.String str) {
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                if (str != null) {
                    try {
                        if (!getIdentity().getPackageName().equals(str)) {
                            return false;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return onLocationPermissionsChanged();
            }
        }

        final boolean onLocationPermissionsChanged(int i) {
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                try {
                    if (getIdentity().getUid() != i) {
                        return false;
                    }
                    return onLocationPermissionsChanged();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        private boolean onLocationPermissionsChanged() {
            boolean hasLocationPermissions = com.android.server.location.provider.LocationProviderManager.this.mLocationPermissionsHelper.hasLocationPermissions(this.mPermissionLevel, getIdentity());
            if (hasLocationPermissions != this.mPermitted) {
                if (com.android.server.location.LocationManagerService.D) {
                    android.util.Log.v(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider package " + getIdentity().getPackageName() + " permitted = " + hasLocationPermissions);
                }
                this.mPermitted = hasLocationPermissions;
                if (this.mPermitted) {
                    com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderClientPermitted(com.android.server.location.provider.LocationProviderManager.this.mName, getIdentity());
                    return true;
                }
                com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderClientUnpermitted(com.android.server.location.provider.LocationProviderManager.this.mName, getIdentity());
                return true;
            }
            return false;
        }

        final boolean onAdasGnssLocationEnabledChanged(int i) {
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                try {
                    if (getIdentity().getUserId() != i) {
                        return false;
                    }
                    return onProviderLocationRequestChanged();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        final boolean onForegroundChanged(int i, boolean z) {
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                try {
                    if (getIdentity().getUid() != i || z == this.mForeground) {
                        return false;
                    }
                    if (com.android.server.location.LocationManagerService.D) {
                        android.util.Log.v(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider uid " + i + " foreground = " + z);
                    }
                    this.mForeground = z;
                    if (this.mForeground) {
                        com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderClientForeground(com.android.server.location.provider.LocationProviderManager.this.mName, getIdentity());
                    } else {
                        com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderClientBackground(com.android.server.location.provider.LocationProviderManager.this.mName, getIdentity());
                    }
                    return onProviderLocationRequestChanged() || com.android.server.location.provider.LocationProviderManager.this.mLocationPowerSaveModeHelper.getLocationPowerSaveMode() == 3;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        final boolean onProviderLocationRequestChanged() {
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                try {
                    android.location.LocationRequest calculateProviderLocationRequest = calculateProviderLocationRequest();
                    if (this.mProviderLocationRequest.equals(calculateProviderLocationRequest)) {
                        return false;
                    }
                    android.location.LocationRequest locationRequest = this.mProviderLocationRequest;
                    this.mProviderLocationRequest = calculateProviderLocationRequest;
                    onHighPowerUsageChanged();
                    com.android.server.location.provider.LocationProviderManager.this.updateService();
                    return locationRequest.isBypass() != calculateProviderLocationRequest.isBypass();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        private android.location.LocationRequest calculateProviderLocationRequest() {
            android.location.LocationRequest.Builder builder = new android.location.LocationRequest.Builder(this.mBaseRequest);
            if (this.mPermissionLevel < 2) {
                builder.setQuality(104);
                if (this.mBaseRequest.getIntervalMillis() < 600000) {
                    builder.setIntervalMillis(600000L);
                }
                if (this.mBaseRequest.getMinUpdateIntervalMillis() < 600000) {
                    builder.setMinUpdateIntervalMillis(600000L);
                }
            }
            boolean isLocationSettingsIgnored = this.mBaseRequest.isLocationSettingsIgnored();
            boolean z = false;
            if (isLocationSettingsIgnored) {
                if (!com.android.server.location.provider.LocationProviderManager.this.mSettingsHelper.getIgnoreSettingsAllowlist().contains(getIdentity().getPackageName(), getIdentity().getAttributionTag()) && !com.android.server.location.provider.LocationProviderManager.this.mLocationManagerInternal.isProvider((java.lang.String) null, getIdentity())) {
                    isLocationSettingsIgnored = false;
                }
                builder.setLocationSettingsIgnored(isLocationSettingsIgnored);
            }
            boolean isAdasGnssBypass = this.mBaseRequest.isAdasGnssBypass();
            if (isAdasGnssBypass) {
                if (!"gps".equals(com.android.server.location.provider.LocationProviderManager.this.mName)) {
                    android.util.Log.e(com.android.server.location.LocationManagerService.TAG, "adas gnss bypass request received in non-gps provider");
                } else if (com.android.server.location.provider.LocationProviderManager.this.mUserHelper.isCurrentUserId(getIdentity().getUserId()) && com.android.server.location.provider.LocationProviderManager.this.mLocationSettings.getUserSettings(getIdentity().getUserId()).isAdasGnssLocationEnabled() && com.android.server.location.provider.LocationProviderManager.this.mSettingsHelper.getAdasAllowlist().contains(getIdentity().getPackageName(), getIdentity().getAttributionTag())) {
                    z = isAdasGnssBypass;
                }
                builder.setAdasGnssBypass(z);
            }
            if (!isLocationSettingsIgnored && !isThrottlingExempt() && !this.mForeground) {
                builder.setIntervalMillis(java.lang.Math.max(this.mBaseRequest.getIntervalMillis(), com.android.server.location.provider.LocationProviderManager.this.mSettingsHelper.getBackgroundThrottleIntervalMs()));
            }
            return builder.build();
        }

        private boolean isThrottlingExempt() {
            if (com.android.server.location.provider.LocationProviderManager.this.mSettingsHelper.getBackgroundThrottlePackageWhitelist().contains(getIdentity().getPackageName())) {
                return true;
            }
            return com.android.server.location.provider.LocationProviderManager.this.mLocationManagerInternal.isProvider((java.lang.String) null, getIdentity());
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(getIdentity());
            android.util.ArraySet arraySet = new android.util.ArraySet(2);
            if (!isForeground()) {
                arraySet.add("bg");
            }
            if (!isPermitted()) {
                arraySet.add("na");
            }
            if (!arraySet.isEmpty()) {
                sb.append(" ");
                sb.append(arraySet);
            }
            if (this.mPermissionLevel == 1) {
                sb.append(" (COARSE)");
            }
            sb.append(" ");
            sb.append(getRequest());
            return sb.toString();
        }
    }

    protected abstract class LocationRegistration extends com.android.server.location.provider.LocationProviderManager.Registration implements android.app.AlarmManager.OnAlarmListener, android.location.LocationManagerInternal.ProviderEnabledListener {

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        private long mExpirationRealtimeMs;

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        private int mNumLocationsDelivered;
        private volatile com.android.server.location.provider.LocationProviderManager.ProviderTransport mProviderTransport;
        final android.os.PowerManager.WakeLock mWakeLock;
        final com.android.server.location.provider.LocationProviderManager.ExternalWakeLockReleaser mWakeLockReleaser;

        protected abstract void onProviderOperationFailure(com.android.internal.listeners.ListenerExecutor.ListenerOperation<com.android.server.location.provider.LocationProviderManager.ProviderTransport> listenerOperation, java.lang.Exception exc);

        protected <TTransport extends com.android.server.location.provider.LocationProviderManager.LocationTransport & com.android.server.location.provider.LocationProviderManager.ProviderTransport> LocationRegistration(android.location.LocationRequest locationRequest, android.location.util.identity.CallerIdentity callerIdentity, java.util.concurrent.Executor executor, TTransport ttransport, int i) {
            super(locationRequest, callerIdentity, executor, ttransport, i);
            this.mNumLocationsDelivered = 0;
            this.mExpirationRealtimeMs = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            this.mProviderTransport = ttransport;
            android.os.PowerManager powerManager = (android.os.PowerManager) com.android.server.location.provider.LocationProviderManager.this.mContext.getSystemService(android.os.PowerManager.class);
            java.util.Objects.requireNonNull(powerManager);
            this.mWakeLock = powerManager.newWakeLock(1, com.android.server.location.provider.LocationProviderManager.WAKELOCK_TAG);
            this.mWakeLock.setReferenceCounted(true);
            this.mWakeLock.setWorkSource(locationRequest.getWorkSource());
            this.mWakeLockReleaser = new com.android.server.location.provider.LocationProviderManager.ExternalWakeLockReleaser(callerIdentity, this.mWakeLock);
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        protected void onListenerUnregister() {
            this.mProviderTransport = null;
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onRegister() {
            super.onRegister();
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            this.mExpirationRealtimeMs = getRequest().getExpirationRealtimeMs(elapsedRealtime);
            if (this.mExpirationRealtimeMs <= elapsedRealtime) {
                onAlarm();
            } else if (this.mExpirationRealtimeMs < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                com.android.server.location.provider.LocationProviderManager.this.mAlarmHelper.setDelayedAlarm(this.mExpirationRealtimeMs - elapsedRealtime, this, null);
            }
            com.android.server.location.provider.LocationProviderManager.this.addEnabledListener(this);
            int userId = getIdentity().getUserId();
            if (!com.android.server.location.provider.LocationProviderManager.this.isEnabled(userId)) {
                onProviderEnabledChanged(com.android.server.location.provider.LocationProviderManager.this.mName, userId, false);
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration, com.android.server.location.listeners.ListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onUnregister() {
            com.android.server.location.provider.LocationProviderManager.this.removeEnabledListener(this);
            if (this.mExpirationRealtimeMs < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                com.android.server.location.provider.LocationProviderManager.this.mAlarmHelper.cancel(this);
            }
            super.onUnregister();
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.ListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onActive() {
            long j;
            android.location.Location lastLocationUnsafe;
            super.onActive();
            if (android.app.compat.CompatChanges.isChangeEnabled(73144566L, getIdentity().getUid())) {
                long intervalMillis = getRequest().getIntervalMillis();
                android.location.Location lastDeliveredLocation = getLastDeliveredLocation();
                if (lastDeliveredLocation == null) {
                    j = intervalMillis;
                } else {
                    j = java.lang.Math.min(intervalMillis, lastDeliveredLocation.getElapsedRealtimeAgeMillis() - 1);
                }
                if (j <= 30000 || (lastLocationUnsafe = com.android.server.location.provider.LocationProviderManager.this.getLastLocationUnsafe(getIdentity().getUserId(), getPermissionLevel(), getRequest().isBypass(), j)) == null) {
                    return;
                }
                executeOperation(acceptLocationChange(android.location.LocationResult.wrap(new android.location.Location[]{lastLocationUnsafe})));
            }
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            if (com.android.server.location.LocationManagerService.D) {
                android.util.Log.d(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + getIdentity() + " expired at " + android.util.TimeUtils.formatRealtime(this.mExpirationRealtimeMs));
            }
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                this.mExpirationRealtimeMs = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                remove();
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        @android.annotation.Nullable
        com.android.internal.listeners.ListenerExecutor.ListenerOperation<com.android.server.location.provider.LocationProviderManager.LocationTransport> acceptLocationChange(android.location.LocationResult locationResult) {
            if (android.os.SystemClock.elapsedRealtime() >= this.mExpirationRealtimeMs) {
                if (com.android.server.location.LocationManagerService.D) {
                    android.util.Log.d(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + getIdentity() + " expired at " + android.util.TimeUtils.formatRealtime(this.mExpirationRealtimeMs));
                }
                remove();
                return null;
            }
            android.location.LocationResult permittedLocationResult = com.android.server.location.provider.LocationProviderManager.this.getPermittedLocationResult(locationResult, getPermissionLevel());
            java.util.Objects.requireNonNull(permittedLocationResult);
            final android.location.LocationResult filter = permittedLocationResult.filter(new java.util.function.Predicate<android.location.Location>() { // from class: com.android.server.location.provider.LocationProviderManager.LocationRegistration.1
                private android.location.Location mPreviousLocation;

                {
                    this.mPreviousLocation = com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.getLastDeliveredLocation();
                }

                @Override // java.util.function.Predicate
                public boolean test(android.location.Location location) {
                    if (java.lang.Double.isNaN(location.getLatitude()) || location.getLatitude() < -90.0d || location.getLatitude() > 90.0d || java.lang.Double.isNaN(location.getLongitude()) || location.getLongitude() < -180.0d || location.getLongitude() > 180.0d) {
                        android.util.Log.e(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.getIdentity() + " dropped delivery - invalid latitude or longitude.");
                        return false;
                    }
                    if (this.mPreviousLocation != null) {
                        long elapsedRealtimeMillis = location.getElapsedRealtimeMillis() - this.mPreviousLocation.getElapsedRealtimeMillis();
                        if (elapsedRealtimeMillis < com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.getRequest().getMinUpdateIntervalMillis() - java.lang.Math.min((long) (com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.getRequest().getIntervalMillis() * com.android.server.location.provider.LocationProviderManager.FASTEST_INTERVAL_JITTER_PERCENTAGE), 30000L)) {
                            if (com.android.server.location.LocationManagerService.D) {
                                android.util.Log.v(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.getIdentity() + " dropped delivery - too fast (deltaMs=" + elapsedRealtimeMillis + ").");
                            }
                            return false;
                        }
                        double minUpdateDistanceMeters = com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.getRequest().getMinUpdateDistanceMeters();
                        if (minUpdateDistanceMeters > 0.0d && location.distanceTo(this.mPreviousLocation) <= minUpdateDistanceMeters) {
                            if (com.android.server.location.LocationManagerService.D) {
                                android.util.Log.v(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.getIdentity() + " dropped delivery - too close");
                            }
                            return false;
                        }
                    }
                    this.mPreviousLocation = location;
                    return true;
                }
            });
            if (filter == null) {
                return null;
            }
            if (!com.android.server.location.provider.LocationProviderManager.this.mAppOpsHelper.noteOpNoThrow(com.android.server.location.LocationPermissions.asAppOp(getPermissionLevel()), getIdentity())) {
                if (com.android.server.location.LocationManagerService.D) {
                    android.util.Log.w(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + getIdentity() + " noteOp denied");
                }
                return null;
            }
            final boolean z = getRequest().getIntervalMillis() != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            return new com.android.internal.listeners.ListenerExecutor.ListenerOperation<com.android.server.location.provider.LocationProviderManager.LocationTransport>() { // from class: com.android.server.location.provider.LocationProviderManager.LocationRegistration.2
                public void onPreExecute() {
                    com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.setLastDeliveredLocation(filter.getLastLocation());
                    if (z) {
                        com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.mWakeLock.acquire(30000L);
                    }
                }

                public void operate(com.android.server.location.provider.LocationProviderManager.LocationTransport locationTransport) throws java.lang.Exception {
                    android.location.LocationResult locationResult2;
                    if (com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.getIdentity().getPid() == android.os.Process.myPid()) {
                        locationResult2 = filter.deepCopy();
                    } else {
                        locationResult2 = filter;
                    }
                    locationTransport.deliverOnLocationChanged(locationResult2, z ? com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.mWakeLockReleaser : null);
                    com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderDeliveredLocations(com.android.server.location.provider.LocationProviderManager.this.mName, filter.size(), com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.getIdentity());
                }

                public void onPostExecute(boolean z2) {
                    if (!z2 && z) {
                        com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.mWakeLock.release();
                    }
                    if (z2) {
                        com.android.server.location.provider.LocationProviderManager.LocationRegistration locationRegistration = com.android.server.location.provider.LocationProviderManager.LocationRegistration.this;
                        int i = locationRegistration.mNumLocationsDelivered + 1;
                        locationRegistration.mNumLocationsDelivered = i;
                        if (i >= com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.getRequest().getMaxUpdates()) {
                            if (com.android.server.location.LocationManagerService.D) {
                                android.util.Log.d(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.getIdentity() + " finished after " + com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.mNumLocationsDelivered + " updates");
                            }
                            com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.remove();
                        }
                    }
                }
            };
        }

        public void onProviderEnabledChanged(java.lang.String str, int i, final boolean z) {
            com.android.internal.util.Preconditions.checkState(com.android.server.location.provider.LocationProviderManager.this.mName.equals(str));
            if (i != getIdentity().getUserId()) {
                return;
            }
            executeSafely(getExecutor(), new java.util.function.Supplier() { // from class: com.android.server.location.provider.LocationProviderManager$LocationRegistration$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.server.location.provider.LocationProviderManager.ProviderTransport lambda$onProviderEnabledChanged$0;
                    lambda$onProviderEnabledChanged$0 = com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.lambda$onProviderEnabledChanged$0();
                    return lambda$onProviderEnabledChanged$0;
                }
            }, new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.provider.LocationProviderManager$LocationRegistration$$ExternalSyntheticLambda1
                public final void operate(java.lang.Object obj) {
                    com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.lambda$onProviderEnabledChanged$1(z, (com.android.server.location.provider.LocationProviderManager.ProviderTransport) obj);
                }
            }, new com.android.internal.listeners.ListenerExecutor.FailureCallback() { // from class: com.android.server.location.provider.LocationProviderManager$LocationRegistration$$ExternalSyntheticLambda2
                public final void onFailure(com.android.internal.listeners.ListenerExecutor.ListenerOperation listenerOperation, java.lang.Exception exc) {
                    com.android.server.location.provider.LocationProviderManager.LocationRegistration.this.onProviderOperationFailure(listenerOperation, exc);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.server.location.provider.LocationProviderManager.ProviderTransport lambda$onProviderEnabledChanged$0() {
            return this.mProviderTransport;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProviderEnabledChanged$1(boolean z, com.android.server.location.provider.LocationProviderManager.ProviderTransport providerTransport) throws java.lang.Exception {
            providerTransport.deliverOnProviderEnabledChanged(com.android.server.location.provider.LocationProviderManager.this.mName, z);
        }
    }

    protected final class LocationListenerRegistration extends com.android.server.location.provider.LocationProviderManager.LocationRegistration implements android.os.IBinder.DeathRecipient {
        LocationListenerRegistration(android.location.LocationRequest locationRequest, android.location.util.identity.CallerIdentity callerIdentity, com.android.server.location.provider.LocationProviderManager.LocationListenerTransport locationListenerTransport, int i) {
            super(locationRequest, callerIdentity, callerIdentity.isMyProcess() ? com.android.server.FgThread.getExecutor() : com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, locationListenerTransport, i);
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration, com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onRegister() {
            super.onRegister();
            try {
                ((android.os.IBinder) getKey()).linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                remove();
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration, com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration, com.android.server.location.listeners.ListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onUnregister() {
            try {
                ((android.os.IBinder) getKey()).unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e) {
                android.util.Log.w(getTag(), "failed to unregister binder death listener", e);
            }
            super.onUnregister();
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration
        protected void onProviderOperationFailure(com.android.internal.listeners.ListenerExecutor.ListenerOperation<com.android.server.location.provider.LocationProviderManager.ProviderTransport> listenerOperation, java.lang.Exception exc) {
            onTransportFailure(exc);
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public void onOperationFailure(com.android.internal.listeners.ListenerExecutor.ListenerOperation<com.android.server.location.provider.LocationProviderManager.LocationTransport> listenerOperation, java.lang.Exception exc) {
            onTransportFailure(exc);
        }

        private void onTransportFailure(java.lang.Exception exc) {
            if (exc instanceof android.os.RemoteException) {
                android.util.Log.w(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + getIdentity() + " removed", exc);
                remove();
                return;
            }
            throw new java.lang.AssertionError(exc);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            try {
                if (com.android.server.location.LocationManagerService.D) {
                    android.util.Log.d(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + getIdentity() + " died");
                }
                remove();
            } catch (java.lang.RuntimeException e) {
                throw new java.lang.AssertionError(e);
            }
        }
    }

    protected final class LocationPendingIntentRegistration extends com.android.server.location.provider.LocationProviderManager.LocationRegistration implements android.app.PendingIntent.CancelListener {
        LocationPendingIntentRegistration(android.location.LocationRequest locationRequest, android.location.util.identity.CallerIdentity callerIdentity, com.android.server.location.provider.LocationProviderManager.LocationPendingIntentTransport locationPendingIntentTransport, int i) {
            super(locationRequest, callerIdentity, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, locationPendingIntentTransport, i);
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration, com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onRegister() {
            super.onRegister();
            if (!((android.app.PendingIntent) getKey()).addCancelListener(com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this)) {
                remove();
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration, com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration, com.android.server.location.listeners.ListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onUnregister() {
            ((android.app.PendingIntent) getKey()).removeCancelListener(this);
            super.onUnregister();
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration
        protected void onProviderOperationFailure(com.android.internal.listeners.ListenerExecutor.ListenerOperation<com.android.server.location.provider.LocationProviderManager.ProviderTransport> listenerOperation, java.lang.Exception exc) {
            onTransportFailure(exc);
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public void onOperationFailure(com.android.internal.listeners.ListenerExecutor.ListenerOperation<com.android.server.location.provider.LocationProviderManager.LocationTransport> listenerOperation, java.lang.Exception exc) {
            onTransportFailure(exc);
        }

        private void onTransportFailure(java.lang.Exception exc) {
            if (exc instanceof android.app.PendingIntent.CanceledException) {
                android.util.Log.w(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + getIdentity() + " removed", exc);
                remove();
                return;
            }
            throw new java.lang.AssertionError(exc);
        }

        public void onCanceled(android.app.PendingIntent pendingIntent) {
            if (com.android.server.location.LocationManagerService.D) {
                android.util.Log.d(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + getIdentity() + " canceled");
            }
            remove();
        }
    }

    protected final class GetCurrentLocationListenerRegistration extends com.android.server.location.provider.LocationProviderManager.Registration implements android.os.IBinder.DeathRecipient, android.app.AlarmManager.OnAlarmListener {

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        private long mExpirationRealtimeMs;

        GetCurrentLocationListenerRegistration(android.location.LocationRequest locationRequest, android.location.util.identity.CallerIdentity callerIdentity, com.android.server.location.provider.LocationProviderManager.LocationTransport locationTransport, int i) {
            super(locationRequest, callerIdentity, callerIdentity.isMyProcess() ? com.android.server.FgThread.getExecutor() : com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, locationTransport, i);
            this.mExpirationRealtimeMs = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onRegister() {
            super.onRegister();
            try {
                ((android.os.IBinder) getKey()).linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                remove();
            }
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            this.mExpirationRealtimeMs = getRequest().getExpirationRealtimeMs(elapsedRealtime);
            if (this.mExpirationRealtimeMs <= elapsedRealtime) {
                onAlarm();
            } else if (this.mExpirationRealtimeMs < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                com.android.server.location.provider.LocationProviderManager.this.mAlarmHelper.setDelayedAlarm(this.mExpirationRealtimeMs - elapsedRealtime, this, null);
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration, com.android.server.location.listeners.ListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onUnregister() {
            if (this.mExpirationRealtimeMs < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                com.android.server.location.provider.LocationProviderManager.this.mAlarmHelper.cancel(this);
            }
            try {
                ((android.os.IBinder) getKey()).unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e) {
                android.util.Log.w(getTag(), "failed to unregister binder death listener", e);
            }
            super.onUnregister();
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.ListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onActive() {
            super.onActive();
            android.location.Location lastLocationUnsafe = com.android.server.location.provider.LocationProviderManager.this.getLastLocationUnsafe(getIdentity().getUserId(), getPermissionLevel(), getRequest().isBypass(), 30000L);
            if (lastLocationUnsafe != null) {
                executeOperation(acceptLocationChange(android.location.LocationResult.wrap(new android.location.Location[]{lastLocationUnsafe})));
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.ListenerRegistration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        protected void onInactive() {
            executeOperation(acceptLocationChange(null));
            super.onInactive();
        }

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        void deliverNull() {
            executeOperation(acceptLocationChange(null));
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            if (com.android.server.location.LocationManagerService.D) {
                android.util.Log.d(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + getIdentity() + " expired at " + android.util.TimeUtils.formatRealtime(this.mExpirationRealtimeMs));
            }
            synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                this.mExpirationRealtimeMs = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                executeOperation(acceptLocationChange(null));
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration
        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        @android.annotation.Nullable
        com.android.internal.listeners.ListenerExecutor.ListenerOperation<com.android.server.location.provider.LocationProviderManager.LocationTransport> acceptLocationChange(@android.annotation.Nullable android.location.LocationResult locationResult) {
            android.location.LocationResult locationResult2 = null;
            if (android.os.SystemClock.elapsedRealtime() >= this.mExpirationRealtimeMs) {
                if (com.android.server.location.LocationManagerService.D) {
                    android.util.Log.d(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + getIdentity() + " expired at " + android.util.TimeUtils.formatRealtime(this.mExpirationRealtimeMs));
                }
                locationResult = null;
            }
            if (locationResult != null && !com.android.server.location.provider.LocationProviderManager.this.mAppOpsHelper.noteOpNoThrow(com.android.server.location.LocationPermissions.asAppOp(getPermissionLevel()), getIdentity())) {
                if (com.android.server.location.LocationManagerService.D) {
                    android.util.Log.w(com.android.server.location.LocationManagerService.TAG, "noteOp denied for " + getIdentity());
                }
            } else {
                locationResult2 = locationResult;
            }
            if (locationResult2 != null) {
                locationResult2 = locationResult2.asLastLocationResult();
            }
            final android.location.LocationResult permittedLocationResult = com.android.server.location.provider.LocationProviderManager.this.getPermittedLocationResult(locationResult2, getPermissionLevel());
            return new com.android.internal.listeners.ListenerExecutor.ListenerOperation<com.android.server.location.provider.LocationProviderManager.LocationTransport>() { // from class: com.android.server.location.provider.LocationProviderManager.GetCurrentLocationListenerRegistration.1
                public void operate(com.android.server.location.provider.LocationProviderManager.LocationTransport locationTransport) throws java.lang.Exception {
                    android.location.LocationResult locationResult3;
                    if (com.android.server.location.provider.LocationProviderManager.GetCurrentLocationListenerRegistration.this.getIdentity().getPid() == android.os.Process.myPid() && permittedLocationResult != null) {
                        locationResult3 = permittedLocationResult.deepCopy();
                    } else {
                        locationResult3 = permittedLocationResult;
                    }
                    locationTransport.deliverOnLocationChanged(locationResult3, null);
                    com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderDeliveredLocations(com.android.server.location.provider.LocationProviderManager.this.mName, permittedLocationResult != null ? permittedLocationResult.size() : 0, com.android.server.location.provider.LocationProviderManager.GetCurrentLocationListenerRegistration.this.getIdentity());
                }

                public void onPostExecute(boolean z) {
                    if (z) {
                        com.android.server.location.provider.LocationProviderManager.GetCurrentLocationListenerRegistration.this.remove();
                    }
                }
            };
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public void onOperationFailure(com.android.internal.listeners.ListenerExecutor.ListenerOperation<com.android.server.location.provider.LocationProviderManager.LocationTransport> listenerOperation, java.lang.Exception exc) {
            if (exc instanceof android.os.RemoteException) {
                android.util.Log.w(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + getIdentity() + " removed", exc);
                remove();
                return;
            }
            throw new java.lang.AssertionError(exc);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            try {
                if (com.android.server.location.LocationManagerService.D) {
                    android.util.Log.d(com.android.server.location.LocationManagerService.TAG, com.android.server.location.provider.LocationProviderManager.this.mName + " provider registration " + getIdentity() + " died");
                }
                remove();
            } catch (java.lang.RuntimeException e) {
                throw new java.lang.AssertionError(e);
            }
        }
    }

    public LocationProviderManager(android.content.Context context, com.android.server.location.injector.Injector injector, java.lang.String str, @android.annotation.Nullable com.android.server.location.provider.PassiveLocationProviderManager passiveLocationProviderManager) {
        this(context, injector, str, passiveLocationProviderManager, java.util.Collections.emptyList());
    }

    public LocationProviderManager(android.content.Context context, com.android.server.location.injector.Injector injector, java.lang.String str, @android.annotation.Nullable com.android.server.location.provider.PassiveLocationProviderManager passiveLocationProviderManager, java.util.Collection<java.lang.String> collection) {
        this.mUserChangedListener = new com.android.server.location.injector.UserInfoHelper.UserListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda4
            @Override // com.android.server.location.injector.UserInfoHelper.UserListener
            public final void onUserChanged(int i, int i2) {
                com.android.server.location.provider.LocationProviderManager.this.onUserChanged(i, i2);
            }
        };
        this.mLocationUserSettingsListener = new com.android.server.location.settings.LocationSettings.LocationUserSettingsListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda6
            @Override // com.android.server.location.settings.LocationSettings.LocationUserSettingsListener
            public final void onLocationUserSettingsChanged(int i, com.android.server.location.settings.LocationUserSettings locationUserSettings, com.android.server.location.settings.LocationUserSettings locationUserSettings2) {
                com.android.server.location.provider.LocationProviderManager.this.onLocationUserSettingsChanged(i, locationUserSettings, locationUserSettings2);
            }
        };
        this.mLocationEnabledChangedListener = new com.android.server.location.injector.SettingsHelper.UserSettingChangedListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda7
            @Override // com.android.server.location.injector.SettingsHelper.UserSettingChangedListener
            public final void onSettingChanged(int i) {
                com.android.server.location.provider.LocationProviderManager.this.onLocationEnabledChanged(i);
            }
        };
        this.mBackgroundThrottlePackageWhitelistChangedListener = new com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda8
            @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
            public final void onSettingChanged() {
                com.android.server.location.provider.LocationProviderManager.this.onBackgroundThrottlePackageWhitelistChanged();
            }
        };
        this.mLocationPackageBlacklistChangedListener = new com.android.server.location.injector.SettingsHelper.UserSettingChangedListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda9
            @Override // com.android.server.location.injector.SettingsHelper.UserSettingChangedListener
            public final void onSettingChanged(int i) {
                com.android.server.location.provider.LocationProviderManager.this.onLocationPackageBlacklistChanged(i);
            }
        };
        this.mLocationPermissionsListener = new com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener() { // from class: com.android.server.location.provider.LocationProviderManager.1
            @Override // com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener
            public void onLocationPermissionsChanged(@android.annotation.Nullable java.lang.String str2) {
                com.android.server.location.provider.LocationProviderManager.this.onLocationPermissionsChanged(str2);
            }

            @Override // com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener
            public void onLocationPermissionsChanged(int i) {
                com.android.server.location.provider.LocationProviderManager.this.onLocationPermissionsChanged(i);
            }
        };
        this.mAppForegroundChangedListener = new com.android.server.location.injector.AppForegroundHelper.AppForegroundListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda10
            @Override // com.android.server.location.injector.AppForegroundHelper.AppForegroundListener
            public final void onAppForegroundChanged(int i, boolean z) {
                com.android.server.location.provider.LocationProviderManager.this.onAppForegroundChanged(i, z);
            }
        };
        this.mBackgroundThrottleIntervalChangedListener = new com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda11
            @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
            public final void onSettingChanged() {
                com.android.server.location.provider.LocationProviderManager.this.onBackgroundThrottleIntervalChanged();
            }
        };
        this.mAdasPackageAllowlistChangedListener = new com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda12
            @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
            public final void onSettingChanged() {
                com.android.server.location.provider.LocationProviderManager.this.onAdasAllowlistChanged();
            }
        };
        this.mIgnoreSettingsPackageWhitelistChangedListener = new com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda13
            @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
            public final void onSettingChanged() {
                com.android.server.location.provider.LocationProviderManager.this.onIgnoreSettingsWhitelistChanged();
            }
        };
        this.mLocationPowerSaveModeChangedListener = new com.android.server.location.injector.LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda14
            @Override // com.android.server.location.injector.LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener
            public final void onLocationPowerSaveModeChanged(int i) {
                com.android.server.location.provider.LocationProviderManager.this.onLocationPowerSaveModeChanged(i);
            }
        };
        this.mScreenInteractiveChangedListener = new com.android.server.location.injector.ScreenInteractiveHelper.ScreenInteractiveChangedListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda5
            @Override // com.android.server.location.injector.ScreenInteractiveHelper.ScreenInteractiveChangedListener
            public final void onScreenInteractiveChanged(boolean z) {
                com.android.server.location.provider.LocationProviderManager.this.onScreenInteractiveChanged(z);
            }
        };
        this.mPackageResetResponder = new com.android.server.location.injector.PackageResetHelper.Responder() { // from class: com.android.server.location.provider.LocationProviderManager.2
            @Override // com.android.server.location.injector.PackageResetHelper.Responder
            public void onPackageReset(java.lang.String str2) {
                com.android.server.location.provider.LocationProviderManager.this.onPackageReset(str2);
            }

            @Override // com.android.server.location.injector.PackageResetHelper.Responder
            public boolean isResetableForPackage(java.lang.String str2) {
                return com.android.server.location.provider.LocationProviderManager.this.isResetableForPackage(str2);
            }
        };
        this.mAltitudeConverter = new android.location.altitude.AltitudeConverter();
        this.mIsAltitudeConverterIdle = true;
        this.mContext = context;
        java.util.Objects.requireNonNull(str);
        this.mName = str;
        this.mPassiveManager = passiveLocationProviderManager;
        this.mState = 2;
        this.mEnabled = new android.util.SparseBooleanArray(2);
        this.mLastLocations = new android.util.SparseArray<>(2);
        this.mRequiredPermissions = collection;
        this.mEnabledListeners = new java.util.ArrayList<>();
        this.mProviderRequestListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        android.location.LocationManagerInternal locationManagerInternal = (android.location.LocationManagerInternal) com.android.server.LocalServices.getService(android.location.LocationManagerInternal.class);
        java.util.Objects.requireNonNull(locationManagerInternal);
        this.mLocationManagerInternal = locationManagerInternal;
        this.mLocationSettings = injector.getLocationSettings();
        this.mSettingsHelper = injector.getSettingsHelper();
        this.mUserHelper = injector.getUserInfoHelper();
        this.mAlarmHelper = injector.getAlarmHelper();
        this.mAppOpsHelper = injector.getAppOpsHelper();
        this.mLocationPermissionsHelper = injector.getLocationPermissionsHelper();
        this.mAppForegroundHelper = injector.getAppForegroundHelper();
        this.mLocationPowerSaveModeHelper = injector.getLocationPowerSaveModeHelper();
        this.mScreenInteractiveHelper = injector.getScreenInteractiveHelper();
        this.mLocationUsageLogger = injector.getLocationUsageLogger();
        this.mLocationFudger = new com.android.server.location.fudger.LocationFudger(this.mSettingsHelper.getCoarseLocationAccuracyM());
        this.mPackageResetHelper = injector.getPackageResetHelper();
        this.mProvider = new com.android.server.location.provider.MockableLocationProvider(this.mMultiplexerLock);
        this.mProvider.getController().setListener(this);
    }

    public void startManager(@android.annotation.Nullable com.android.server.location.provider.LocationProviderManager.StateChangedListener stateChangedListener) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(this.mState == 2);
                this.mState = 0;
                this.mStateChangedListener = stateChangedListener;
                this.mUserHelper.addListener(this.mUserChangedListener);
                this.mLocationSettings.registerLocationUserSettingsListener(this.mLocationUserSettingsListener);
                this.mSettingsHelper.addOnLocationEnabledChangedListener(this.mLocationEnabledChangedListener);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mProvider.getController().start();
                    onUserStarted(-1);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopManager() {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(this.mState == 0);
                this.mState = 1;
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    onEnabledChanged(-1);
                    removeRegistrationIf(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda17
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$stopManager$0;
                            lambda$stopManager$0 = com.android.server.location.provider.LocationProviderManager.lambda$stopManager$0(obj);
                            return lambda$stopManager$0;
                        }
                    });
                    this.mProvider.getController().stop();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    this.mUserHelper.removeListener(this.mUserChangedListener);
                    this.mLocationSettings.unregisterLocationUserSettingsListener(this.mLocationUserSettingsListener);
                    this.mSettingsHelper.removeOnLocationEnabledChangedListener(this.mLocationEnabledChangedListener);
                    com.android.internal.util.Preconditions.checkState(this.mEnabledListeners.isEmpty());
                    this.mProviderRequestListeners.clear();
                    this.mEnabled.clear();
                    this.mLastLocations.clear();
                    this.mStateChangedListener = null;
                    this.mState = 2;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$stopManager$0(java.lang.Object obj) {
        return true;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public com.android.server.location.provider.AbstractLocationProvider.State getState() {
        return this.mProvider.getState();
    }

    @android.annotation.Nullable
    public android.location.util.identity.CallerIdentity getProviderIdentity() {
        return this.mProvider.getState().identity;
    }

    @android.annotation.Nullable
    public android.location.provider.ProviderProperties getProperties() {
        return this.mProvider.getState().properties;
    }

    public boolean hasProvider() {
        return this.mProvider.getProvider() != null;
    }

    public boolean isEnabled(int i) {
        boolean valueAt;
        if (i == -10000) {
            return false;
        }
        if (i == -2) {
            return isEnabled(this.mUserHelper.getCurrentUserId());
        }
        com.android.internal.util.Preconditions.checkArgument(i >= 0);
        synchronized (this.mMultiplexerLock) {
            try {
                int indexOfKey = this.mEnabled.indexOfKey(i);
                if (indexOfKey < 0) {
                    android.util.Log.w(com.android.server.location.LocationManagerService.TAG, this.mName + " provider saw user " + i + " unexpectedly");
                    onEnabledChanged(i);
                    indexOfKey = this.mEnabled.indexOfKey(i);
                }
                valueAt = this.mEnabled.valueAt(indexOfKey);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return valueAt;
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    public boolean isVisibleToCaller() {
        if (android.os.Binder.getCallingUid() == 1000 || this.mProvider.isMock()) {
            return true;
        }
        java.util.Iterator<java.lang.String> it = this.mRequiredPermissions.iterator();
        while (it.hasNext()) {
            if (this.mContext.checkCallingOrSelfPermission(it.next()) != 0) {
                return false;
            }
        }
        return true;
    }

    public void addEnabledListener(android.location.LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
        synchronized (this.mMultiplexerLock) {
            com.android.internal.util.Preconditions.checkState(this.mState != 2);
            this.mEnabledListeners.add(providerEnabledListener);
        }
    }

    public void removeEnabledListener(android.location.LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
        synchronized (this.mMultiplexerLock) {
            com.android.internal.util.Preconditions.checkState(this.mState != 2);
            this.mEnabledListeners.remove(providerEnabledListener);
        }
    }

    public void addProviderRequestListener(android.location.provider.IProviderRequestListener iProviderRequestListener) {
        this.mProviderRequestListeners.add(iProviderRequestListener);
    }

    public void removeProviderRequestListener(android.location.provider.IProviderRequestListener iProviderRequestListener) {
        this.mProviderRequestListeners.remove(iProviderRequestListener);
    }

    public void setRealProvider(@android.annotation.Nullable com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(this.mState != 2);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mProvider.setRealProvider(abstractLocationProvider);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setMockProvider(@android.annotation.Nullable com.android.server.location.provider.MockLocationProvider mockLocationProvider) {
        synchronized (this.mMultiplexerLock) {
            try {
                boolean z = true;
                com.android.internal.util.Preconditions.checkState(this.mState != 2);
                com.android.server.location.eventlog.LocationEventLog locationEventLog = com.android.server.location.eventlog.LocationEventLog.EVENT_LOG;
                java.lang.String str = this.mName;
                if (mockLocationProvider == null) {
                    z = false;
                }
                locationEventLog.logProviderMocked(str, z);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mProvider.setMockProvider(mockLocationProvider);
                    if (mockLocationProvider == null) {
                        int size = this.mLastLocations.size();
                        for (int i = 0; i < size; i++) {
                            this.mLastLocations.valueAt(i).clearMock();
                        }
                        this.mLocationFudger.resetOffsets();
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setMockProviderAllowed(boolean z) {
        synchronized (this.mMultiplexerLock) {
            try {
                if (!this.mProvider.isMock()) {
                    throw new java.lang.IllegalArgumentException(this.mName + " provider is not a test provider");
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mProvider.setMockProviderAllowed(z);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setMockProviderLocation(android.location.Location location) {
        synchronized (this.mMultiplexerLock) {
            try {
                if (!this.mProvider.isMock()) {
                    throw new java.lang.IllegalArgumentException(this.mName + " provider is not a test provider");
                }
                java.lang.String provider = location.getProvider();
                if (!android.text.TextUtils.isEmpty(provider) && !this.mName.equals(provider)) {
                    android.util.EventLog.writeEvent(1397638484, "33091107", java.lang.Integer.valueOf(android.os.Binder.getCallingUid()), this.mName + "!=" + provider);
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mProvider.setMockProviderLocation(location);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public android.location.Location getLastLocation(android.location.LastLocationRequest lastLocationRequest, android.location.util.identity.CallerIdentity callerIdentity, int i) {
        android.location.LastLocationRequest calculateLastLocationRequest = calculateLastLocationRequest(lastLocationRequest, callerIdentity);
        if (!isActive(calculateLastLocationRequest.isBypass(), callerIdentity)) {
            return null;
        }
        android.location.Location permittedLocation = getPermittedLocation(getLastLocationUnsafe(callerIdentity.getUserId(), i, calculateLastLocationRequest.isBypass(), com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME), i);
        if (permittedLocation != null) {
            if (!this.mAppOpsHelper.noteOpNoThrow(com.android.server.location.LocationPermissions.asAppOp(i), callerIdentity)) {
                return null;
            }
            if (callerIdentity.getPid() == android.os.Process.myPid()) {
                return new android.location.Location(permittedLocation);
            }
            return permittedLocation;
        }
        return permittedLocation;
    }

    private android.location.LastLocationRequest calculateLastLocationRequest(android.location.LastLocationRequest lastLocationRequest, android.location.util.identity.CallerIdentity callerIdentity) {
        android.location.LastLocationRequest.Builder builder = new android.location.LastLocationRequest.Builder(lastLocationRequest);
        boolean isLocationSettingsIgnored = lastLocationRequest.isLocationSettingsIgnored();
        boolean z = false;
        if (isLocationSettingsIgnored) {
            if (!this.mSettingsHelper.getIgnoreSettingsAllowlist().contains(callerIdentity.getPackageName(), callerIdentity.getAttributionTag()) && !this.mLocationManagerInternal.isProvider((java.lang.String) null, callerIdentity)) {
                isLocationSettingsIgnored = false;
            }
            builder.setLocationSettingsIgnored(isLocationSettingsIgnored);
        }
        boolean isAdasGnssBypass = lastLocationRequest.isAdasGnssBypass();
        if (isAdasGnssBypass) {
            if (!"gps".equals(this.mName)) {
                android.util.Log.e(com.android.server.location.LocationManagerService.TAG, "adas gnss bypass request received in non-gps provider");
            } else if (this.mUserHelper.isCurrentUserId(callerIdentity.getUserId()) && this.mLocationSettings.getUserSettings(callerIdentity.getUserId()).isAdasGnssLocationEnabled() && this.mSettingsHelper.getAdasAllowlist().contains(callerIdentity.getPackageName(), callerIdentity.getAttributionTag())) {
                z = isAdasGnssBypass;
            }
            builder.setAdasGnssBypass(z);
        }
        return builder.build();
    }

    @android.annotation.Nullable
    public android.location.Location getLastLocationUnsafe(int i, int i2, boolean z, long j) {
        android.location.Location location;
        android.location.Location location2 = null;
        if (i == -1) {
            for (int i3 : this.mUserHelper.getRunningUserIds()) {
                android.location.Location lastLocationUnsafe = getLastLocationUnsafe(i3, i2, z, j);
                if (location2 == null || (lastLocationUnsafe != null && lastLocationUnsafe.getElapsedRealtimeNanos() > location2.getElapsedRealtimeNanos())) {
                    location2 = lastLocationUnsafe;
                }
            }
            return location2;
        }
        if (i == -2) {
            return getLastLocationUnsafe(this.mUserHelper.getCurrentUserId(), i2, z, j);
        }
        com.android.internal.util.Preconditions.checkArgument(i >= 0);
        synchronized (this.mMultiplexerLock) {
            com.android.internal.util.Preconditions.checkState(this.mState != 2);
            com.android.server.location.provider.LocationProviderManager.LastLocation lastLocation = this.mLastLocations.get(i);
            if (lastLocation == null) {
                location = null;
            } else {
                location = lastLocation.get(i2, z);
            }
        }
        if (location != null && location.getElapsedRealtimeAgeMillis() <= j) {
            return location;
        }
        return null;
    }

    public void injectLastLocation(android.location.Location location, int i) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(this.mState != 2);
                if (getLastLocationUnsafe(i, 2, false, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) == null) {
                    setLastLocation(location, i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void setLastLocation(android.location.Location location, int i) {
        if (i == -1) {
            for (int i2 : this.mUserHelper.getRunningUserIds()) {
                setLastLocation(location, i2);
            }
            return;
        }
        if (i == -2) {
            setLastLocation(location, this.mUserHelper.getCurrentUserId());
            return;
        }
        com.android.internal.util.Preconditions.checkArgument(i >= 0);
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.server.location.provider.LocationProviderManager.LastLocation lastLocation = this.mLastLocations.get(i);
                if (lastLocation == null) {
                    lastLocation = new com.android.server.location.provider.LocationProviderManager.LastLocation();
                    this.mLastLocations.put(i, lastLocation);
                }
                if (isEnabled(i)) {
                    lastLocation.set(location);
                }
                lastLocation.setBypass(location);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public android.os.ICancellationSignal getCurrentLocation(android.location.LocationRequest locationRequest, android.location.util.identity.CallerIdentity callerIdentity, int i, final android.location.ILocationCallback iLocationCallback) {
        android.location.LocationRequest locationRequest2;
        if (locationRequest.getDurationMillis() <= 30000) {
            locationRequest2 = locationRequest;
        } else {
            locationRequest2 = new android.location.LocationRequest.Builder(locationRequest).setDurationMillis(30000L).build();
        }
        final com.android.server.location.provider.LocationProviderManager.GetCurrentLocationListenerRegistration getCurrentLocationListenerRegistration = new com.android.server.location.provider.LocationProviderManager.GetCurrentLocationListenerRegistration(locationRequest2, callerIdentity, new com.android.server.location.provider.LocationProviderManager.GetCurrentLocationTransport(iLocationCallback), i);
        synchronized (this.mMultiplexerLock) {
            com.android.internal.util.Preconditions.checkState(this.mState != 2);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                putRegistration(iLocationCallback.asBinder(), getCurrentLocationListenerRegistration);
                if (!getCurrentLocationListenerRegistration.isActive()) {
                    getCurrentLocationListenerRegistration.deliverNull();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
        android.os.CancellationSignal.fromTransport(createTransport).setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda21
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                com.android.server.location.provider.LocationProviderManager.this.lambda$getCurrentLocation$2(iLocationCallback, getCurrentLocationListenerRegistration);
            }
        });
        return createTransport;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCurrentLocation$2(android.location.ILocationCallback iLocationCallback, com.android.server.location.provider.LocationProviderManager.GetCurrentLocationListenerRegistration getCurrentLocationListenerRegistration) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                removeRegistration(iLocationCallback.asBinder(), getCurrentLocationListenerRegistration);
            } catch (java.lang.RuntimeException e) {
                com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.provider.LocationProviderManager.lambda$getCurrentLocation$1(e);
                    }
                });
                throw e;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getCurrentLocation$1(java.lang.RuntimeException runtimeException) {
        throw new java.lang.AssertionError(runtimeException);
    }

    public void sendExtraCommand(int i, int i2, java.lang.String str, android.os.Bundle bundle) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mProvider.getController().sendExtraCommand(i, i2, str, bundle);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerLocationRequest(android.location.LocationRequest locationRequest, android.location.util.identity.CallerIdentity callerIdentity, int i, android.location.ILocationListener iLocationListener) {
        com.android.server.location.provider.LocationProviderManager.LocationListenerRegistration locationListenerRegistration = new com.android.server.location.provider.LocationProviderManager.LocationListenerRegistration(locationRequest, callerIdentity, new com.android.server.location.provider.LocationProviderManager.LocationListenerTransport(iLocationListener), i);
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(this.mState != 2);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    putRegistration(iLocationListener.asBinder(), locationListenerRegistration);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void registerLocationRequest(android.location.LocationRequest locationRequest, android.location.util.identity.CallerIdentity callerIdentity, int i, android.app.PendingIntent pendingIntent) {
        com.android.server.location.provider.LocationProviderManager.LocationPendingIntentRegistration locationPendingIntentRegistration = new com.android.server.location.provider.LocationProviderManager.LocationPendingIntentRegistration(locationRequest, callerIdentity, new com.android.server.location.provider.LocationProviderManager.LocationPendingIntentTransport(this.mContext, pendingIntent), i);
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(this.mState != 2);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    putRegistration(pendingIntent, locationPendingIntentRegistration);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void flush(android.location.ILocationListener iLocationListener, final int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!updateRegistration(iLocationListener.asBinder(), new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda32
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$flush$3;
                    lambda$flush$3 = com.android.server.location.provider.LocationProviderManager.lambda$flush$3(i, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                    return lambda$flush$3;
                }
            })) {
                throw new java.lang.IllegalArgumentException("unregistered listener cannot be flushed");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$flush$3(int i, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        registration.flush(i);
        return false;
    }

    public void flush(android.app.PendingIntent pendingIntent, final int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!updateRegistration(pendingIntent, new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda31
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$flush$4;
                    lambda$flush$4 = com.android.server.location.provider.LocationProviderManager.lambda$flush$4(i, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                    return lambda$flush$4;
                }
            })) {
                throw new java.lang.IllegalArgumentException("unregistered pending intent cannot be flushed");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$flush$4(int i, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        registration.flush(i);
        return false;
    }

    public void unregisterLocationRequest(android.location.ILocationListener iLocationListener) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(this.mState != 2);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    removeRegistration((com.android.server.location.provider.LocationProviderManager) iLocationListener.asBinder());
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterLocationRequest(android.app.PendingIntent pendingIntent) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(this.mState != 2);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    removeRegistration((com.android.server.location.provider.LocationProviderManager) pendingIntent);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected void onRegister() {
        this.mSettingsHelper.addOnBackgroundThrottleIntervalChangedListener(this.mBackgroundThrottleIntervalChangedListener);
        this.mSettingsHelper.addOnBackgroundThrottlePackageWhitelistChangedListener(this.mBackgroundThrottlePackageWhitelistChangedListener);
        this.mSettingsHelper.addOnLocationPackageBlacklistChangedListener(this.mLocationPackageBlacklistChangedListener);
        this.mSettingsHelper.addAdasAllowlistChangedListener(this.mAdasPackageAllowlistChangedListener);
        this.mSettingsHelper.addIgnoreSettingsAllowlistChangedListener(this.mIgnoreSettingsPackageWhitelistChangedListener);
        this.mLocationPermissionsHelper.addListener(this.mLocationPermissionsListener);
        this.mAppForegroundHelper.addListener(this.mAppForegroundChangedListener);
        this.mLocationPowerSaveModeHelper.addListener(this.mLocationPowerSaveModeChangedListener);
        this.mScreenInteractiveHelper.addListener(this.mScreenInteractiveChangedListener);
        this.mPackageResetHelper.register(this.mPackageResetResponder);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected void onUnregister() {
        this.mSettingsHelper.removeOnBackgroundThrottleIntervalChangedListener(this.mBackgroundThrottleIntervalChangedListener);
        this.mSettingsHelper.removeOnBackgroundThrottlePackageWhitelistChangedListener(this.mBackgroundThrottlePackageWhitelistChangedListener);
        this.mSettingsHelper.removeOnLocationPackageBlacklistChangedListener(this.mLocationPackageBlacklistChangedListener);
        this.mSettingsHelper.removeAdasAllowlistChangedListener(this.mAdasPackageAllowlistChangedListener);
        this.mSettingsHelper.removeIgnoreSettingsAllowlistChangedListener(this.mIgnoreSettingsPackageWhitelistChangedListener);
        this.mLocationPermissionsHelper.removeListener(this.mLocationPermissionsListener);
        this.mAppForegroundHelper.removeListener(this.mAppForegroundChangedListener);
        this.mLocationPowerSaveModeHelper.removeListener(this.mLocationPowerSaveModeChangedListener);
        this.mScreenInteractiveHelper.removeListener(this.mScreenInteractiveChangedListener);
        this.mPackageResetHelper.unregister(this.mPackageResetResponder);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    public void onRegistrationAdded(java.lang.Object obj, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        this.mLocationUsageLogger.logLocationApiUsage(0, 1, registration.getIdentity().getPackageName(), registration.getIdentity().getAttributionTag(), this.mName, registration.getRequest(), obj instanceof android.app.PendingIntent, obj instanceof android.os.IBinder, null, registration.isForeground());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    public void onRegistrationReplaced(java.lang.Object obj, com.android.server.location.provider.LocationProviderManager.Registration registration, java.lang.Object obj2, com.android.server.location.provider.LocationProviderManager.Registration registration2) {
        registration2.setLastDeliveredLocation(registration.getLastDeliveredLocation());
        super.onRegistrationReplaced((com.android.server.location.provider.LocationProviderManager.Registration) obj, (java.lang.Object) registration, (com.android.server.location.provider.LocationProviderManager.Registration) obj2, (java.lang.Object) registration2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    public void onRegistrationRemoved(java.lang.Object obj, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        this.mLocationUsageLogger.logLocationApiUsage(1, 1, registration.getIdentity().getPackageName(), registration.getIdentity().getAttributionTag(), this.mName, registration.getRequest(), obj instanceof android.app.PendingIntent, obj instanceof android.os.IBinder, null, registration.isForeground());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    public boolean registerWithService(android.location.provider.ProviderRequest providerRequest, java.util.Collection<com.android.server.location.provider.LocationProviderManager.Registration> collection) {
        if (!providerRequest.isActive()) {
            return true;
        }
        return reregisterWithService(android.location.provider.ProviderRequest.EMPTY_REQUEST, providerRequest, collection);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    public boolean reregisterWithService(android.location.provider.ProviderRequest providerRequest, final android.location.provider.ProviderRequest providerRequest2, java.util.Collection<com.android.server.location.provider.LocationProviderManager.Registration> collection) {
        long calculateRequestDelayMillis;
        if (!providerRequest.isBypass() && providerRequest2.isBypass()) {
            calculateRequestDelayMillis = 0;
        } else if (providerRequest2.getIntervalMillis() > providerRequest.getIntervalMillis()) {
            calculateRequestDelayMillis = 0;
        } else {
            calculateRequestDelayMillis = calculateRequestDelayMillis(providerRequest2.getIntervalMillis(), collection);
        }
        com.android.internal.util.Preconditions.checkState(calculateRequestDelayMillis >= 0 && calculateRequestDelayMillis <= providerRequest2.getIntervalMillis());
        if (calculateRequestDelayMillis < 30000) {
            setProviderRequest(providerRequest2);
        } else {
            if (com.android.server.location.LocationManagerService.D) {
                android.util.Log.d(com.android.server.location.LocationManagerService.TAG, this.mName + " provider delaying request update " + providerRequest2 + " by " + android.util.TimeUtils.formatDuration(calculateRequestDelayMillis));
            }
            if (this.mDelayedRegister != null) {
                this.mAlarmHelper.cancel(this.mDelayedRegister);
                this.mDelayedRegister = null;
            }
            this.mDelayedRegister = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.location.provider.LocationProviderManager.3
                @Override // android.app.AlarmManager.OnAlarmListener
                public void onAlarm() {
                    synchronized (((com.android.server.location.listeners.ListenerMultiplexer) com.android.server.location.provider.LocationProviderManager.this).mMultiplexerLock) {
                        try {
                            if (com.android.server.location.provider.LocationProviderManager.this.mDelayedRegister == this) {
                                com.android.server.location.provider.LocationProviderManager.this.mDelayedRegister = null;
                                com.android.server.location.provider.LocationProviderManager.this.setProviderRequest(providerRequest2);
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            };
            this.mAlarmHelper.setDelayedAlarm(calculateRequestDelayMillis, this.mDelayedRegister, null);
        }
        return true;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected void unregisterWithService() {
        setProviderRequest(android.location.provider.ProviderRequest.EMPTY_REQUEST);
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    void setProviderRequest(final android.location.provider.ProviderRequest providerRequest) {
        if (this.mDelayedRegister != null) {
            this.mAlarmHelper.cancel(this.mDelayedRegister);
            this.mDelayedRegister = null;
        }
        com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderUpdateRequest(this.mName, providerRequest);
        if (com.android.server.location.LocationManagerService.D) {
            android.util.Log.d(com.android.server.location.LocationManagerService.TAG, this.mName + " provider request changed to " + providerRequest);
        }
        this.mProvider.getController().setRequest(providerRequest);
        com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.provider.LocationProviderManager.this.lambda$setProviderRequest$5(providerRequest);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setProviderRequest$5(android.location.provider.ProviderRequest providerRequest) {
        java.util.Iterator<android.location.provider.IProviderRequestListener> it = this.mProviderRequestListeners.iterator();
        while (it.hasNext()) {
            android.location.provider.IProviderRequestListener next = it.next();
            try {
                next.onProviderRequestChanged(this.mName, providerRequest);
            } catch (android.os.RemoteException e) {
                this.mProviderRequestListeners.remove(next);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    public boolean isActive(com.android.server.location.provider.LocationProviderManager.Registration registration) {
        if (!registration.isPermitted()) {
            return false;
        }
        boolean isBypass = registration.getRequest().isBypass();
        if (!isActive(isBypass, registration.getIdentity())) {
            return false;
        }
        if (!isBypass) {
            switch (this.mLocationPowerSaveModeHelper.getLocationPowerSaveMode()) {
                case 1:
                    if (!"gps".equals(this.mName)) {
                        return true;
                    }
                    break;
                case 2:
                case 4:
                    break;
                case 3:
                    return registration.isForeground();
                default:
                    return true;
            }
            return this.mScreenInteractiveHelper.isInteractive();
        }
        return true;
    }

    private boolean isActive(boolean z, android.location.util.identity.CallerIdentity callerIdentity) {
        return callerIdentity.isSystemServer() ? z || isEnabled(this.mUserHelper.getCurrentUserId()) : (z || (isEnabled(callerIdentity.getUserId()) && this.mUserHelper.isVisibleUserId(callerIdentity.getUserId()))) && !this.mSettingsHelper.isLocationPackageBlacklisted(callerIdentity.getUserId(), callerIdentity.getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    public android.location.provider.ProviderRequest mergeRegistrations(java.util.Collection<com.android.server.location.provider.LocationProviderManager.Registration> collection) {
        long j;
        java.util.Iterator<com.android.server.location.provider.LocationProviderManager.Registration> it = collection.iterator();
        int i = 104;
        boolean z = false;
        long j2 = Long.MAX_VALUE;
        long j3 = Long.MAX_VALUE;
        boolean z2 = true;
        boolean z3 = false;
        while (it.hasNext()) {
            android.location.LocationRequest request = it.next().getRequest();
            if (request.getIntervalMillis() != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                j2 = java.lang.Math.min(request.getIntervalMillis(), j2);
                i = java.lang.Math.min(request.getQuality(), i);
                j3 = java.lang.Math.min(request.getMaxUpdateDelayMillis(), j3);
                z |= request.isAdasGnssBypass();
                z3 |= request.isLocationSettingsIgnored();
                z2 &= request.isLowPower();
            }
        }
        if (j2 == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            return android.location.provider.ProviderRequest.EMPTY_REQUEST;
        }
        if (j3 / 2 < j2) {
            j3 = 0;
        }
        try {
            j = java.lang.Math.multiplyExact(java.lang.Math.addExact(j2, 1000L) / 2, 3);
        } catch (java.lang.ArithmeticException e) {
            j = 9223372036854775806L;
        }
        android.os.WorkSource workSource = new android.os.WorkSource();
        for (com.android.server.location.provider.LocationProviderManager.Registration registration : collection) {
            if (registration.getRequest().getIntervalMillis() <= j) {
                workSource.add(registration.getRequest().getWorkSource());
            }
        }
        return new android.location.provider.ProviderRequest.Builder().setIntervalMillis(j2).setQuality(i).setMaxUpdateDelayMillis(j3).setAdasGnssBypass(z).setLocationSettingsIgnored(z3).setLowPower(z2).setWorkSource(workSource).build();
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected long calculateRequestDelayMillis(long j, java.util.Collection<com.android.server.location.provider.LocationProviderManager.Registration> collection) {
        for (com.android.server.location.provider.LocationProviderManager.Registration registration : collection) {
            long j2 = 0;
            if (j == 0) {
                break;
            }
            android.location.LocationRequest request = registration.getRequest();
            android.location.Location lastDeliveredLocation = registration.getLastDeliveredLocation();
            if (lastDeliveredLocation == null && !request.isLocationSettingsIgnored()) {
                lastDeliveredLocation = getLastLocationUnsafe(registration.getIdentity().getUserId(), registration.getPermissionLevel(), false, request.getIntervalMillis());
            }
            if (lastDeliveredLocation != null) {
                j2 = java.lang.Math.max(0L, request.getIntervalMillis() - lastDeliveredLocation.getElapsedRealtimeAgeMillis());
            }
            j = java.lang.Math.min(j, j2);
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserChanged(final int i, int i2) {
        synchronized (this.mMultiplexerLock) {
            try {
                if (this.mState == 2) {
                    return;
                }
                switch (i2) {
                    case 1:
                    case 4:
                        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda15
                            @Override // java.util.function.Predicate
                            public final boolean test(java.lang.Object obj) {
                                boolean lambda$onUserChanged$6;
                                lambda$onUserChanged$6 = com.android.server.location.provider.LocationProviderManager.lambda$onUserChanged$6(i, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                                return lambda$onUserChanged$6;
                            }
                        });
                        break;
                    case 2:
                        onUserStarted(i);
                        break;
                    case 3:
                        onUserStopped(i);
                        break;
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onUserChanged$6(int i, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        return registration.getIdentity().getUserId() == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocationUserSettingsChanged(final int i, com.android.server.location.settings.LocationUserSettings locationUserSettings, com.android.server.location.settings.LocationUserSettings locationUserSettings2) {
        if (locationUserSettings.isAdasGnssLocationEnabled() != locationUserSettings2.isAdasGnssLocationEnabled()) {
            updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda29
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$onLocationUserSettingsChanged$7;
                    lambda$onLocationUserSettingsChanged$7 = com.android.server.location.provider.LocationProviderManager.lambda$onLocationUserSettingsChanged$7(i, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                    return lambda$onLocationUserSettingsChanged$7;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationUserSettingsChanged$7(int i, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        return registration.onAdasGnssLocationEnabledChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocationEnabledChanged(int i) {
        synchronized (this.mMultiplexerLock) {
            try {
                if (this.mState == 2) {
                    return;
                }
                onEnabledChanged(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScreenInteractiveChanged(boolean z) {
        switch (this.mLocationPowerSaveModeHelper.getLocationPowerSaveMode()) {
            case 1:
                if (!"gps".equals(this.mName)) {
                    return;
                }
                break;
            case 2:
            case 4:
                break;
            case 3:
            default:
                return;
        }
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onScreenInteractiveChanged$8;
                lambda$onScreenInteractiveChanged$8 = com.android.server.location.provider.LocationProviderManager.lambda$onScreenInteractiveChanged$8((com.android.server.location.provider.LocationProviderManager.Registration) obj);
                return lambda$onScreenInteractiveChanged$8;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onScreenInteractiveChanged$8(com.android.server.location.provider.LocationProviderManager.Registration registration) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackgroundThrottlePackageWhitelistChanged() {
        updateRegistrations(new com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda22());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackgroundThrottleIntervalChanged() {
        updateRegistrations(new com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda22());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationPowerSaveModeChanged$9(com.android.server.location.provider.LocationProviderManager.Registration registration) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocationPowerSaveModeChanged(int i) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onLocationPowerSaveModeChanged$9;
                lambda$onLocationPowerSaveModeChanged$9 = com.android.server.location.provider.LocationProviderManager.lambda$onLocationPowerSaveModeChanged$9((com.android.server.location.provider.LocationProviderManager.Registration) obj);
                return lambda$onLocationPowerSaveModeChanged$9;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onAppForegroundChanged$10(int i, boolean z, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        return registration.onForegroundChanged(i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAppForegroundChanged(final int i, final boolean z) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onAppForegroundChanged$10;
                lambda$onAppForegroundChanged$10 = com.android.server.location.provider.LocationProviderManager.lambda$onAppForegroundChanged$10(i, z, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                return lambda$onAppForegroundChanged$10;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAdasAllowlistChanged() {
        updateRegistrations(new com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda22());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onIgnoreSettingsWhitelistChanged() {
        updateRegistrations(new com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda22());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationPackageBlacklistChanged$11(int i, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        return registration.getIdentity().getUserId() == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocationPackageBlacklistChanged(final int i) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onLocationPackageBlacklistChanged$11;
                lambda$onLocationPackageBlacklistChanged$11 = com.android.server.location.provider.LocationProviderManager.lambda$onLocationPackageBlacklistChanged$11(i, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                return lambda$onLocationPackageBlacklistChanged$11;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocationPermissionsChanged(@android.annotation.Nullable final java.lang.String str) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onLocationPermissionsChanged$12;
                lambda$onLocationPermissionsChanged$12 = com.android.server.location.provider.LocationProviderManager.lambda$onLocationPermissionsChanged$12(str, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                return lambda$onLocationPermissionsChanged$12;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationPermissionsChanged$12(java.lang.String str, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        return registration.onLocationPermissionsChanged(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationPermissionsChanged$13(int i, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        return registration.onLocationPermissionsChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocationPermissionsChanged(final int i) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onLocationPermissionsChanged$13;
                lambda$onLocationPermissionsChanged$13 = com.android.server.location.provider.LocationProviderManager.lambda$onLocationPermissionsChanged$13(i, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                return lambda$onLocationPermissionsChanged$13;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageReset(final java.lang.String str) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onPackageReset$14;
                lambda$onPackageReset$14 = com.android.server.location.provider.LocationProviderManager.lambda$onPackageReset$14(str, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                return lambda$onPackageReset$14;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onPackageReset$14(java.lang.String str, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        if (registration.getIdentity().getPackageName().equals(str)) {
            registration.remove();
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isResetableForPackage(final java.lang.String str) {
        return findRegistration(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isResetableForPackage$15;
                lambda$isResetableForPackage$15 = com.android.server.location.provider.LocationProviderManager.lambda$isResetableForPackage$15(str, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                return lambda$isResetableForPackage$15;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isResetableForPackage$15(java.lang.String str, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        return registration.getIdentity().getPackageName().equals(str);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    public void onStateChanged(final com.android.server.location.provider.AbstractLocationProvider.State state, final com.android.server.location.provider.AbstractLocationProvider.State state2) {
        if (state.allowed != state2.allowed) {
            onEnabledChanged(-1);
        }
        if (!java.util.Objects.equals(state.properties, state2.properties)) {
            updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return ((com.android.server.location.provider.LocationProviderManager.Registration) obj).onProviderPropertiesChanged();
                }
            });
        }
        if (this.mStateChangedListener != null) {
            final com.android.server.location.provider.LocationProviderManager.StateChangedListener stateChangedListener = this.mStateChangedListener;
            com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.provider.LocationProviderManager.this.lambda$onStateChanged$16(stateChangedListener, state, state2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStateChanged$16(com.android.server.location.provider.LocationProviderManager.StateChangedListener stateChangedListener, com.android.server.location.provider.AbstractLocationProvider.State state, com.android.server.location.provider.AbstractLocationProvider.State state2) {
        stateChangedListener.onStateChanged(this.mName, state, state2);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    public void onReportLocation(android.location.LocationResult locationResult) {
        final android.location.LocationResult locationResult2;
        android.location.Location lastLocationUnsafe;
        if (this.mPassiveManager != null) {
            locationResult2 = processReportedLocation(locationResult);
            if (locationResult2 == null) {
                return;
            } else {
                com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderReceivedLocations(this.mName, locationResult2.size());
            }
        } else {
            locationResult2 = locationResult;
        }
        if (this.mPassiveManager != null && (lastLocationUnsafe = getLastLocationUnsafe(-2, 2, true, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME)) != null && locationResult.get(0).getElapsedRealtimeNanos() < lastLocationUnsafe.getElapsedRealtimeNanos()) {
            android.util.Log.e(com.android.server.location.LocationManagerService.TAG, "non-monotonic location received from " + this.mName + " provider");
        }
        setLastLocation(locationResult2.getLastLocation(), -1);
        deliverToListeners(new java.util.function.Function() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda19
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.internal.listeners.ListenerExecutor.ListenerOperation lambda$onReportLocation$17;
                lambda$onReportLocation$17 = com.android.server.location.provider.LocationProviderManager.lambda$onReportLocation$17(locationResult2, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                return lambda$onReportLocation$17;
            }
        });
        if (this.mPassiveManager != null) {
            this.mPassiveManager.updateLocation(locationResult2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.internal.listeners.ListenerExecutor.ListenerOperation lambda$onReportLocation$17(android.location.LocationResult locationResult, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        return registration.acceptLocationChange(locationResult);
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    @android.annotation.Nullable
    private android.location.LocationResult processReportedLocation(android.location.LocationResult locationResult) {
        try {
            locationResult.validate();
            if (android.provider.DeviceConfig.getBoolean("location", "enable_location_provider_manager_msl", true)) {
                return locationResult.map(new java.util.function.Function() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda28
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        android.location.Location lambda$processReportedLocation$19;
                        lambda$processReportedLocation$19 = com.android.server.location.provider.LocationProviderManager.this.lambda$processReportedLocation$19((android.location.Location) obj);
                        return lambda$processReportedLocation$19;
                    }
                });
            }
            return locationResult;
        } catch (android.location.LocationResult.BadLocationException e) {
            android.util.Log.e(com.android.server.location.LocationManagerService.TAG, "Dropping invalid locations: " + e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.location.Location lambda$processReportedLocation$19(android.location.Location location) {
        if (!location.hasMslAltitude() && location.hasAltitude()) {
            try {
                final android.location.Location location2 = new android.location.Location(location);
                if (this.mAltitudeConverter.tryAddMslAltitudeToLocation(location2)) {
                    return location2;
                }
                if (this.mIsAltitudeConverterIdle) {
                    this.mIsAltitudeConverterIdle = false;
                    com.android.server.IoThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda23
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.location.provider.LocationProviderManager.this.lambda$processReportedLocation$18(location2);
                        }
                    });
                }
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.e(com.android.server.location.LocationManagerService.TAG, "not adding MSL altitude to location: " + e);
            }
        }
        return location;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processReportedLocation$18(android.location.Location location) {
        try {
            this.mAltitudeConverter.addMslAltitudeToLocation(this.mContext, location);
        } catch (java.io.IOException e) {
            android.util.Log.e(com.android.server.location.LocationManagerService.TAG, "not loading MSL altitude assets: " + e);
        }
        this.mIsAltitudeConverterIdle = true;
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private void onUserStarted(int i) {
        if (i == -10000) {
            return;
        }
        if (i == -1) {
            this.mEnabled.clear();
            onEnabledChanged(-1);
        } else {
            com.android.internal.util.Preconditions.checkArgument(i >= 0);
            this.mEnabled.delete(i);
            onEnabledChanged(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private void onUserStopped(int i) {
        if (i == -10000) {
            return;
        }
        if (i == -1) {
            this.mEnabled.clear();
            this.mLastLocations.clear();
        } else {
            com.android.internal.util.Preconditions.checkArgument(i >= 0);
            this.mEnabled.delete(i);
            this.mLastLocations.remove(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private void onEnabledChanged(final int i) {
        com.android.server.location.provider.LocationProviderManager.LastLocation lastLocation;
        if (i == -10000) {
            return;
        }
        if (i == -1) {
            for (int i2 : this.mUserHelper.getRunningUserIds()) {
                onEnabledChanged(i2);
            }
            return;
        }
        com.android.internal.util.Preconditions.checkArgument(i >= 0);
        final boolean z = this.mState == 0 && this.mProvider.getState().allowed && this.mSettingsHelper.isLocationEnabled(i);
        int indexOfKey = this.mEnabled.indexOfKey(i);
        java.lang.Boolean valueOf = indexOfKey < 0 ? null : java.lang.Boolean.valueOf(this.mEnabled.valueAt(indexOfKey));
        if (valueOf != null && valueOf.booleanValue() == z) {
            return;
        }
        this.mEnabled.put(i, z);
        if (valueOf != null || z) {
            if (com.android.server.location.LocationManagerService.D) {
                android.util.Log.d(com.android.server.location.LocationManagerService.TAG, "[u" + i + "] " + this.mName + " provider enabled = " + z);
            }
            com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logProviderEnabled(this.mName, i, z);
        }
        if (!z && (lastLocation = this.mLastLocations.get(i)) != null) {
            lastLocation.clearLocations();
        }
        if (valueOf != null) {
            if (!"passive".equals(this.mName)) {
                this.mContext.sendBroadcastAsUser(new android.content.Intent("android.location.PROVIDERS_CHANGED").putExtra("android.location.extra.PROVIDER_NAME", this.mName).putExtra("android.location.extra.PROVIDER_ENABLED", z).addFlags(1073741824).addFlags(268435456), android.os.UserHandle.of(i));
            }
            if (!this.mEnabledListeners.isEmpty()) {
                final android.location.LocationManagerInternal.ProviderEnabledListener[] providerEnabledListenerArr = (android.location.LocationManagerInternal.ProviderEnabledListener[]) this.mEnabledListeners.toArray(new android.location.LocationManagerInternal.ProviderEnabledListener[0]);
                com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda26
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.provider.LocationProviderManager.this.lambda$onEnabledChanged$20(providerEnabledListenerArr, i, z);
                    }
                });
            }
        }
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onEnabledChanged$21;
                lambda$onEnabledChanged$21 = com.android.server.location.provider.LocationProviderManager.lambda$onEnabledChanged$21(i, (com.android.server.location.provider.LocationProviderManager.Registration) obj);
                return lambda$onEnabledChanged$21;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnabledChanged$20(android.location.LocationManagerInternal.ProviderEnabledListener[] providerEnabledListenerArr, int i, boolean z) {
        for (android.location.LocationManagerInternal.ProviderEnabledListener providerEnabledListener : providerEnabledListenerArr) {
            providerEnabledListener.onProviderEnabledChanged(this.mName, i, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onEnabledChanged$21(int i, com.android.server.location.provider.LocationProviderManager.Registration registration) {
        return registration.getIdentity().getUserId() == i;
    }

    @android.annotation.Nullable
    android.location.Location getPermittedLocation(@android.annotation.Nullable android.location.Location location, int i) {
        switch (i) {
            case 1:
                if (location != null) {
                    return this.mLocationFudger.createCoarse(location);
                }
                return null;
            case 2:
                return location;
            default:
                throw new java.lang.AssertionError();
        }
    }

    @android.annotation.Nullable
    android.location.LocationResult getPermittedLocationResult(@android.annotation.Nullable android.location.LocationResult locationResult, int i) {
        switch (i) {
            case 1:
                if (locationResult != null) {
                    return this.mLocationFudger.createCoarse(locationResult);
                }
                return null;
            case 2:
                return locationResult;
            default:
                throw new java.lang.AssertionError();
        }
    }

    public void dump(java.io.FileDescriptor fileDescriptor, android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr) {
        synchronized (this.mMultiplexerLock) {
            try {
                indentingPrintWriter.print(this.mName);
                indentingPrintWriter.print(" provider");
                if (this.mProvider.isMock()) {
                    indentingPrintWriter.print(" [mock]");
                }
                indentingPrintWriter.println(":");
                indentingPrintWriter.increaseIndent();
                super.dump(fileDescriptor, (java.io.PrintWriter) indentingPrintWriter, strArr);
                int[] runningUserIds = this.mUserHelper.getRunningUserIds();
                for (int i : runningUserIds) {
                    if (runningUserIds.length != 1) {
                        indentingPrintWriter.print("user ");
                        indentingPrintWriter.print(i);
                        indentingPrintWriter.println(":");
                        indentingPrintWriter.increaseIndent();
                    }
                    indentingPrintWriter.print("last location=");
                    indentingPrintWriter.println(getLastLocationUnsafe(i, 2, false, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME));
                    indentingPrintWriter.print("enabled=");
                    indentingPrintWriter.println(isEnabled(i));
                    if (runningUserIds.length != 1) {
                        indentingPrintWriter.decreaseIndent();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mProvider.dump(fileDescriptor, indentingPrintWriter, strArr);
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected java.lang.String getServiceState() {
        return this.mProvider.getCurrentRequest().toString();
    }

    private static class LastLocation {

        @android.annotation.Nullable
        private android.location.Location mCoarseBypassLocation;

        @android.annotation.Nullable
        private android.location.Location mCoarseLocation;

        @android.annotation.Nullable
        private android.location.Location mFineBypassLocation;

        @android.annotation.Nullable
        private android.location.Location mFineLocation;

        LastLocation() {
        }

        public void clearMock() {
            if (this.mFineLocation != null && this.mFineLocation.isMock()) {
                this.mFineLocation = null;
            }
            if (this.mCoarseLocation != null && this.mCoarseLocation.isMock()) {
                this.mCoarseLocation = null;
            }
            if (this.mFineBypassLocation != null && this.mFineBypassLocation.isMock()) {
                this.mFineBypassLocation = null;
            }
            if (this.mCoarseBypassLocation != null && this.mCoarseBypassLocation.isMock()) {
                this.mCoarseBypassLocation = null;
            }
        }

        public void clearLocations() {
            this.mFineLocation = null;
            this.mCoarseLocation = null;
        }

        @android.annotation.Nullable
        public android.location.Location get(int i, boolean z) {
            switch (i) {
                case 1:
                    if (z) {
                        return this.mCoarseBypassLocation;
                    }
                    return this.mCoarseLocation;
                case 2:
                    if (z) {
                        return this.mFineBypassLocation;
                    }
                    return this.mFineLocation;
                default:
                    throw new java.lang.AssertionError();
            }
        }

        public void set(android.location.Location location) {
            this.mFineLocation = calculateNextFine(this.mFineLocation, location);
            this.mCoarseLocation = calculateNextCoarse(this.mCoarseLocation, location);
        }

        public void setBypass(android.location.Location location) {
            this.mFineBypassLocation = calculateNextFine(this.mFineBypassLocation, location);
            this.mCoarseBypassLocation = calculateNextCoarse(this.mCoarseBypassLocation, location);
        }

        private android.location.Location calculateNextFine(@android.annotation.Nullable android.location.Location location, android.location.Location location2) {
            if (location == null) {
                return location2;
            }
            if (location2.getElapsedRealtimeNanos() > location.getElapsedRealtimeNanos()) {
                return location2;
            }
            return location;
        }

        private android.location.Location calculateNextCoarse(@android.annotation.Nullable android.location.Location location, android.location.Location location2) {
            if (location == null) {
                return location2;
            }
            if (location2.getElapsedRealtimeMillis() - 600000 > location.getElapsedRealtimeMillis()) {
                return location2;
            }
            return location;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PendingIntentSender {
        private PendingIntentSender() {
        }

        public static void send(android.app.PendingIntent pendingIntent, android.content.Context context, android.content.Intent intent, @android.annotation.Nullable java.lang.Runnable runnable, android.os.Bundle bundle) throws android.app.PendingIntent.CanceledException {
            android.app.PendingIntent.OnFinished onFinished;
            com.android.server.location.provider.LocationProviderManager.PendingIntentSender.GatedCallback gatedCallback = null;
            byte b = 0;
            if (runnable != null) {
                final com.android.server.location.provider.LocationProviderManager.PendingIntentSender.GatedCallback gatedCallback2 = new com.android.server.location.provider.LocationProviderManager.PendingIntentSender.GatedCallback(runnable);
                onFinished = new android.app.PendingIntent.OnFinished() { // from class: com.android.server.location.provider.LocationProviderManager$PendingIntentSender$$ExternalSyntheticLambda0
                    @Override // android.app.PendingIntent.OnFinished
                    public final void onSendFinished(android.app.PendingIntent pendingIntent2, android.content.Intent intent2, int i, java.lang.String str, android.os.Bundle bundle2) {
                        com.android.server.location.provider.LocationProviderManager.PendingIntentSender.GatedCallback.this.run();
                    }
                };
                gatedCallback = gatedCallback2;
            } else {
                onFinished = null;
            }
            pendingIntent.send(context, 0, intent, onFinished, null, null, bundle);
            if (gatedCallback != null) {
                gatedCallback.allow();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class GatedCallback implements java.lang.Runnable {

            @com.android.internal.annotations.GuardedBy({"this"})
            @android.annotation.Nullable
            private java.lang.Runnable mCallback;

            @com.android.internal.annotations.GuardedBy({"this"})
            private boolean mGate;

            @com.android.internal.annotations.GuardedBy({"this"})
            private boolean mRun;

            private GatedCallback(@android.annotation.Nullable java.lang.Runnable runnable) {
                this.mCallback = runnable;
            }

            public void allow() {
                java.lang.Runnable runnable;
                synchronized (this) {
                    try {
                        this.mGate = true;
                        runnable = null;
                        if (this.mRun && this.mCallback != null) {
                            java.lang.Runnable runnable2 = this.mCallback;
                            this.mCallback = null;
                            runnable = runnable2;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (runnable != null) {
                    runnable.run();
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                java.lang.Runnable runnable;
                synchronized (this) {
                    try {
                        this.mRun = true;
                        runnable = null;
                        if (this.mGate && this.mCallback != null) {
                            java.lang.Runnable runnable2 = this.mCallback;
                            this.mCallback = null;
                            runnable = runnable2;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (runnable != null) {
                    runnable.run();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ExternalWakeLockReleaser extends android.os.IRemoteCallback.Stub {
        private final android.location.util.identity.CallerIdentity mIdentity;
        private final android.os.PowerManager.WakeLock mWakeLock;

        ExternalWakeLockReleaser(android.location.util.identity.CallerIdentity callerIdentity, android.os.PowerManager.WakeLock wakeLock) {
            this.mIdentity = callerIdentity;
            java.util.Objects.requireNonNull(wakeLock);
            this.mWakeLock = wakeLock;
        }

        public void sendResult(android.os.Bundle bundle) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    this.mWakeLock.release();
                } catch (java.lang.RuntimeException e) {
                    if (e.getClass() == java.lang.RuntimeException.class) {
                        android.util.Log.e(com.android.server.location.LocationManagerService.TAG, "wakelock over-released by " + this.mIdentity, e);
                    } else {
                        com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.LocationProviderManager$ExternalWakeLockReleaser$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.location.provider.LocationProviderManager.ExternalWakeLockReleaser.lambda$sendResult$0(e);
                            }
                        });
                        throw e;
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$sendResult$0(java.lang.RuntimeException runtimeException) {
            throw new java.lang.AssertionError(runtimeException);
        }
    }
}
