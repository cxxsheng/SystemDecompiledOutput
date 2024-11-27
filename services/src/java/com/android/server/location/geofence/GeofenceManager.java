package com.android.server.location.geofence;

/* loaded from: classes2.dex */
public class GeofenceManager extends com.android.server.location.listeners.ListenerMultiplexer<com.android.server.location.geofence.GeofenceManager.GeofenceKey, android.app.PendingIntent, com.android.server.location.geofence.GeofenceManager.GeofenceRegistration, android.location.LocationRequest> implements android.location.LocationListener {
    private static final java.lang.String ATTRIBUTION_TAG = "GeofencingService";
    private static final long MAX_LOCATION_AGE_MS = 300000;
    private static final long MAX_LOCATION_INTERVAL_MS = 7200000;
    private static final int MAX_SPEED_M_S = 100;
    private static final java.lang.String TAG = "GeofenceManager";
    private static final long WAKELOCK_TIMEOUT_MS = 30000;
    protected final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.location.Location mLastLocation;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.location.LocationManager mLocationManager;
    protected final com.android.server.location.injector.LocationPermissionsHelper mLocationPermissionsHelper;
    protected final com.android.server.location.injector.LocationUsageLogger mLocationUsageLogger;
    protected final com.android.server.location.injector.SettingsHelper mSettingsHelper;
    protected final com.android.server.location.injector.UserInfoHelper mUserInfoHelper;
    final java.lang.Object mLock = new java.lang.Object();
    private final com.android.server.location.injector.UserInfoHelper.UserListener mUserChangedListener = new com.android.server.location.injector.UserInfoHelper.UserListener() { // from class: com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda3
        @Override // com.android.server.location.injector.UserInfoHelper.UserListener
        public final void onUserChanged(int i, int i2) {
            com.android.server.location.geofence.GeofenceManager.this.onUserChanged(i, i2);
        }
    };
    private final com.android.server.location.injector.SettingsHelper.UserSettingChangedListener mLocationEnabledChangedListener = new com.android.server.location.injector.SettingsHelper.UserSettingChangedListener() { // from class: com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda4
        @Override // com.android.server.location.injector.SettingsHelper.UserSettingChangedListener
        public final void onSettingChanged(int i) {
            com.android.server.location.geofence.GeofenceManager.this.onLocationEnabledChanged(i);
        }
    };
    private final com.android.server.location.injector.SettingsHelper.UserSettingChangedListener mLocationPackageBlacklistChangedListener = new com.android.server.location.injector.SettingsHelper.UserSettingChangedListener() { // from class: com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda5
        @Override // com.android.server.location.injector.SettingsHelper.UserSettingChangedListener
        public final void onSettingChanged(int i) {
            com.android.server.location.geofence.GeofenceManager.this.onLocationPackageBlacklistChanged(i);
        }
    };
    private final com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener mLocationPermissionsListener = new com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener() { // from class: com.android.server.location.geofence.GeofenceManager.1
        @Override // com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener
        public void onLocationPermissionsChanged(@android.annotation.Nullable java.lang.String str) {
            com.android.server.location.geofence.GeofenceManager.this.onLocationPermissionsChanged(str);
        }

        @Override // com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener
        public void onLocationPermissionsChanged(int i) {
            com.android.server.location.geofence.GeofenceManager.this.onLocationPermissionsChanged(i);
        }
    };

    static class GeofenceKey {
        private final android.location.Geofence mGeofence;
        private final android.app.PendingIntent mPendingIntent;

        GeofenceKey(android.app.PendingIntent pendingIntent, android.location.Geofence geofence) {
            java.util.Objects.requireNonNull(pendingIntent);
            this.mPendingIntent = pendingIntent;
            java.util.Objects.requireNonNull(geofence);
            this.mGeofence = geofence;
        }

        public android.app.PendingIntent getPendingIntent() {
            return this.mPendingIntent;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.location.geofence.GeofenceManager.GeofenceKey)) {
                return false;
            }
            com.android.server.location.geofence.GeofenceManager.GeofenceKey geofenceKey = (com.android.server.location.geofence.GeofenceManager.GeofenceKey) obj;
            return this.mPendingIntent.equals(geofenceKey.mPendingIntent) && this.mGeofence.equals(geofenceKey.mGeofence);
        }

        public int hashCode() {
            return this.mPendingIntent.hashCode();
        }
    }

    protected class GeofenceRegistration extends com.android.server.location.listeners.PendingIntentListenerRegistration<com.android.server.location.geofence.GeofenceManager.GeofenceKey, android.app.PendingIntent> {
        private static final int STATE_INSIDE = 1;
        private static final int STATE_OUTSIDE = 2;
        private static final int STATE_UNKNOWN = 0;

        @android.annotation.Nullable
        private android.location.Location mCachedLocation;
        private float mCachedLocationDistanceM;
        private final android.location.Location mCenter;
        private final android.location.Geofence mGeofence;
        private int mGeofenceState;
        private final android.location.util.identity.CallerIdentity mIdentity;
        private boolean mPermitted;
        private final android.os.PowerManager.WakeLock mWakeLock;

        GeofenceRegistration(android.location.Geofence geofence, android.location.util.identity.CallerIdentity callerIdentity, android.app.PendingIntent pendingIntent) {
            super(pendingIntent);
            this.mGeofence = geofence;
            this.mIdentity = callerIdentity;
            this.mCenter = new android.location.Location("");
            this.mCenter.setLatitude(geofence.getLatitude());
            this.mCenter.setLongitude(geofence.getLongitude());
            android.os.PowerManager powerManager = (android.os.PowerManager) com.android.server.location.geofence.GeofenceManager.this.mContext.getSystemService(android.os.PowerManager.class);
            java.util.Objects.requireNonNull(powerManager);
            this.mWakeLock = powerManager.newWakeLock(1, "GeofenceManager:" + callerIdentity.getPackageName());
            this.mWakeLock.setReferenceCounted(true);
            this.mWakeLock.setWorkSource(callerIdentity.addToWorkSource((android.os.WorkSource) null));
        }

        public android.location.Geofence getGeofence() {
            return this.mGeofence;
        }

        public android.location.util.identity.CallerIdentity getIdentity() {
            return this.mIdentity;
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public java.lang.String getTag() {
            return com.android.server.location.geofence.GeofenceManager.TAG;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.location.listeners.PendingIntentListenerRegistration
        public android.app.PendingIntent getPendingIntentFromKey(com.android.server.location.geofence.GeofenceManager.GeofenceKey geofenceKey) {
            return geofenceKey.getPendingIntent();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public com.android.server.location.geofence.GeofenceManager getOwner() {
            return com.android.server.location.geofence.GeofenceManager.this;
        }

        @Override // com.android.server.location.listeners.PendingIntentListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration
        protected void onRegister() {
            super.onRegister();
            this.mGeofenceState = 0;
            this.mPermitted = com.android.server.location.geofence.GeofenceManager.this.mLocationPermissionsHelper.hasLocationPermissions(2, this.mIdentity);
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        protected void onActive() {
            android.location.Location lastLocation = com.android.server.location.geofence.GeofenceManager.this.getLastLocation();
            if (lastLocation != null) {
                executeOperation(onLocationChanged(lastLocation));
            }
        }

        boolean isPermitted() {
            return this.mPermitted;
        }

        boolean onLocationPermissionsChanged(@android.annotation.Nullable java.lang.String str) {
            if (str == null || this.mIdentity.getPackageName().equals(str)) {
                return onLocationPermissionsChanged();
            }
            return false;
        }

        boolean onLocationPermissionsChanged(int i) {
            if (this.mIdentity.getUid() == i) {
                return onLocationPermissionsChanged();
            }
            return false;
        }

        private boolean onLocationPermissionsChanged() {
            boolean hasLocationPermissions = com.android.server.location.geofence.GeofenceManager.this.mLocationPermissionsHelper.hasLocationPermissions(2, this.mIdentity);
            if (hasLocationPermissions != this.mPermitted) {
                this.mPermitted = hasLocationPermissions;
                return true;
            }
            return false;
        }

        double getDistanceToBoundary(android.location.Location location) {
            if (!location.equals(this.mCachedLocation)) {
                this.mCachedLocation = location;
                this.mCachedLocationDistanceM = this.mCenter.distanceTo(this.mCachedLocation);
            }
            return java.lang.Math.abs(this.mGeofence.getRadius() - this.mCachedLocationDistanceM);
        }

        com.android.internal.listeners.ListenerExecutor.ListenerOperation<android.app.PendingIntent> onLocationChanged(android.location.Location location) {
            if (this.mGeofence.isExpired()) {
                remove();
                return null;
            }
            this.mCachedLocation = location;
            this.mCachedLocationDistanceM = this.mCenter.distanceTo(this.mCachedLocation);
            int i = this.mGeofenceState;
            if (this.mCachedLocationDistanceM <= java.lang.Math.max(this.mGeofence.getRadius(), location.getAccuracy())) {
                this.mGeofenceState = 1;
                if (i != 1) {
                    return new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.geofence.GeofenceManager$GeofenceRegistration$$ExternalSyntheticLambda1
                        public final void operate(java.lang.Object obj) {
                            com.android.server.location.geofence.GeofenceManager.GeofenceRegistration.this.lambda$onLocationChanged$0((android.app.PendingIntent) obj);
                        }
                    };
                }
            } else {
                this.mGeofenceState = 2;
                if (i == 1) {
                    return new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.geofence.GeofenceManager$GeofenceRegistration$$ExternalSyntheticLambda2
                        public final void operate(java.lang.Object obj) {
                            com.android.server.location.geofence.GeofenceManager.GeofenceRegistration.this.lambda$onLocationChanged$1((android.app.PendingIntent) obj);
                        }
                    };
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLocationChanged$0(android.app.PendingIntent pendingIntent) throws java.lang.Exception {
            sendIntent(pendingIntent, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLocationChanged$1(android.app.PendingIntent pendingIntent) throws java.lang.Exception {
            sendIntent(pendingIntent, false);
        }

        private void sendIntent(android.app.PendingIntent pendingIntent, boolean z) {
            android.content.Intent putExtra = new android.content.Intent().putExtra("entering", z);
            this.mWakeLock.acquire(30000L);
            try {
                pendingIntent.send(com.android.server.location.geofence.GeofenceManager.this.mContext, 0, putExtra, new android.app.PendingIntent.OnFinished() { // from class: com.android.server.location.geofence.GeofenceManager$GeofenceRegistration$$ExternalSyntheticLambda0
                    @Override // android.app.PendingIntent.OnFinished
                    public final void onSendFinished(android.app.PendingIntent pendingIntent2, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle) {
                        com.android.server.location.geofence.GeofenceManager.GeofenceRegistration.this.lambda$sendIntent$2(pendingIntent2, intent, i, str, bundle);
                    }
                }, null, null, com.android.server.PendingIntentUtils.createDontSendToRestrictedAppsBundle(null));
            } catch (android.app.PendingIntent.CanceledException e) {
                this.mWakeLock.release();
                com.android.server.location.geofence.GeofenceManager.this.removeRegistration(new com.android.server.location.geofence.GeofenceManager.GeofenceKey(pendingIntent, this.mGeofence), this);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendIntent$2(android.app.PendingIntent pendingIntent, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle) {
            this.mWakeLock.release();
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.mIdentity);
            android.util.ArraySet arraySet = new android.util.ArraySet(1);
            if (!this.mPermitted) {
                arraySet.add("na");
            }
            if (!arraySet.isEmpty()) {
                sb.append(" ");
                sb.append(arraySet);
            }
            sb.append(" ");
            sb.append(this.mGeofence);
            return sb.toString();
        }
    }

    public GeofenceManager(android.content.Context context, com.android.server.location.injector.Injector injector) {
        this.mContext = context.createAttributionContext(ATTRIBUTION_TAG);
        this.mUserInfoHelper = injector.getUserInfoHelper();
        this.mSettingsHelper = injector.getSettingsHelper();
        this.mLocationPermissionsHelper = injector.getLocationPermissionsHelper();
        this.mLocationUsageLogger = injector.getLocationUsageLogger();
    }

    private android.location.LocationManager getLocationManager() {
        android.location.LocationManager locationManager;
        synchronized (this.mLock) {
            try {
                if (this.mLocationManager == null) {
                    android.location.LocationManager locationManager2 = (android.location.LocationManager) this.mContext.getSystemService(android.location.LocationManager.class);
                    java.util.Objects.requireNonNull(locationManager2);
                    android.location.LocationManager locationManager3 = locationManager2;
                    this.mLocationManager = locationManager2;
                }
                locationManager = this.mLocationManager;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return locationManager;
    }

    public void addGeofence(android.location.Geofence geofence, android.app.PendingIntent pendingIntent, java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        com.android.server.location.LocationPermissions.enforceCallingOrSelfLocationPermission(this.mContext, 2);
        android.location.util.identity.CallerIdentity fromBinder = android.location.util.identity.CallerIdentity.fromBinder(this.mContext, str, str2, android.app.AppOpsManager.toReceiverId(pendingIntent));
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            putRegistration(new com.android.server.location.geofence.GeofenceManager.GeofenceKey(pendingIntent, geofence), new com.android.server.location.geofence.GeofenceManager.GeofenceRegistration(geofence, fromBinder, pendingIntent));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void removeGeofence(final android.app.PendingIntent pendingIntent) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            removeRegistrationIf(new java.util.function.Predicate() { // from class: com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeGeofence$0;
                    lambda$removeGeofence$0 = com.android.server.location.geofence.GeofenceManager.lambda$removeGeofence$0(pendingIntent, (com.android.server.location.geofence.GeofenceManager.GeofenceKey) obj);
                    return lambda$removeGeofence$0;
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeGeofence$0(android.app.PendingIntent pendingIntent, com.android.server.location.geofence.GeofenceManager.GeofenceKey geofenceKey) {
        return geofenceKey.getPendingIntent().equals(pendingIntent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public boolean isActive(com.android.server.location.geofence.GeofenceManager.GeofenceRegistration geofenceRegistration) {
        return geofenceRegistration.isPermitted() && isActive(geofenceRegistration.getIdentity());
    }

    private boolean isActive(android.location.util.identity.CallerIdentity callerIdentity) {
        return callerIdentity.isSystemServer() ? this.mSettingsHelper.isLocationEnabled(this.mUserInfoHelper.getCurrentUserId()) : this.mSettingsHelper.isLocationEnabled(callerIdentity.getUserId()) && this.mUserInfoHelper.isVisibleUserId(callerIdentity.getUserId()) && !this.mSettingsHelper.isLocationPackageBlacklisted(callerIdentity.getUserId(), callerIdentity.getPackageName());
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void onRegister() {
        this.mUserInfoHelper.addListener(this.mUserChangedListener);
        this.mSettingsHelper.addOnLocationEnabledChangedListener(this.mLocationEnabledChangedListener);
        this.mSettingsHelper.addOnLocationPackageBlacklistChangedListener(this.mLocationPackageBlacklistChangedListener);
        this.mLocationPermissionsHelper.addListener(this.mLocationPermissionsListener);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void onUnregister() {
        this.mUserInfoHelper.removeListener(this.mUserChangedListener);
        this.mSettingsHelper.removeOnLocationEnabledChangedListener(this.mLocationEnabledChangedListener);
        this.mSettingsHelper.removeOnLocationPackageBlacklistChangedListener(this.mLocationPackageBlacklistChangedListener);
        this.mLocationPermissionsHelper.removeListener(this.mLocationPermissionsListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationAdded(com.android.server.location.geofence.GeofenceManager.GeofenceKey geofenceKey, com.android.server.location.geofence.GeofenceManager.GeofenceRegistration geofenceRegistration) {
        this.mLocationUsageLogger.logLocationApiUsage(1, 4, geofenceRegistration.getIdentity().getPackageName(), geofenceRegistration.getIdentity().getAttributionTag(), null, null, false, true, geofenceRegistration.getGeofence(), true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationRemoved(com.android.server.location.geofence.GeofenceManager.GeofenceKey geofenceKey, com.android.server.location.geofence.GeofenceManager.GeofenceRegistration geofenceRegistration) {
        this.mLocationUsageLogger.logLocationApiUsage(1, 4, geofenceRegistration.getIdentity().getPackageName(), geofenceRegistration.getIdentity().getAttributionTag(), null, null, false, true, geofenceRegistration.getGeofence(), true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public boolean registerWithService(android.location.LocationRequest locationRequest, java.util.Collection<com.android.server.location.geofence.GeofenceManager.GeofenceRegistration> collection) {
        getLocationManager().requestLocationUpdates("fused", locationRequest, com.android.server.FgThread.getExecutor(), this);
        return true;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void unregisterWithService() {
        synchronized (this.mLock) {
            getLocationManager().removeUpdates(this);
            this.mLastLocation = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public android.location.LocationRequest mergeRegistrations(java.util.Collection<com.android.server.location.geofence.GeofenceManager.GeofenceRegistration> collection) {
        long backgroundThrottleProximityAlertIntervalMs;
        android.location.Location lastLocation = getLastLocation();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        android.os.WorkSource workSource = null;
        double d = Double.MAX_VALUE;
        for (com.android.server.location.geofence.GeofenceManager.GeofenceRegistration geofenceRegistration : collection) {
            if (!geofenceRegistration.getGeofence().isExpired(elapsedRealtime)) {
                workSource = geofenceRegistration.getIdentity().addToWorkSource(workSource);
                if (lastLocation != null) {
                    double distanceToBoundary = geofenceRegistration.getDistanceToBoundary(lastLocation);
                    if (distanceToBoundary < d) {
                        d = distanceToBoundary;
                    }
                }
            }
        }
        if (java.lang.Double.compare(d, Double.MAX_VALUE) < 0) {
            backgroundThrottleProximityAlertIntervalMs = (long) java.lang.Math.min(7200000.0d, java.lang.Math.max(this.mSettingsHelper.getBackgroundThrottleProximityAlertIntervalMs(), (d * 1000.0d) / 100.0d));
        } else {
            backgroundThrottleProximityAlertIntervalMs = this.mSettingsHelper.getBackgroundThrottleProximityAlertIntervalMs();
        }
        return new android.location.LocationRequest.Builder(backgroundThrottleProximityAlertIntervalMs).setMinUpdateIntervalMillis(0L).setHiddenFromAppOps(true).setWorkSource(workSource).build();
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(final android.location.Location location) {
        synchronized (this.mLock) {
            this.mLastLocation = location;
        }
        deliverToListeners(new java.util.function.Function() { // from class: com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.internal.listeners.ListenerExecutor.ListenerOperation lambda$onLocationChanged$1;
                lambda$onLocationChanged$1 = com.android.server.location.geofence.GeofenceManager.lambda$onLocationChanged$1(location, (com.android.server.location.geofence.GeofenceManager.GeofenceRegistration) obj);
                return lambda$onLocationChanged$1;
            }
        });
        updateService();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.internal.listeners.ListenerExecutor.ListenerOperation lambda$onLocationChanged$1(android.location.Location location, com.android.server.location.geofence.GeofenceManager.GeofenceRegistration geofenceRegistration) {
        return geofenceRegistration.onLocationChanged(location);
    }

    @android.annotation.Nullable
    android.location.Location getLastLocation() {
        android.location.Location location;
        synchronized (this.mLock) {
            location = this.mLastLocation;
        }
        if (location == null) {
            location = getLocationManager().getLastLocation();
        }
        if (location != null && location.getElapsedRealtimeAgeMillis() > 300000) {
            return null;
        }
        return location;
    }

    void onUserChanged(final int i, int i2) {
        if (i2 == 1 || i2 == 4) {
            updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$onUserChanged$2;
                    lambda$onUserChanged$2 = com.android.server.location.geofence.GeofenceManager.lambda$onUserChanged$2(i, (com.android.server.location.geofence.GeofenceManager.GeofenceRegistration) obj);
                    return lambda$onUserChanged$2;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onUserChanged$2(int i, com.android.server.location.geofence.GeofenceManager.GeofenceRegistration geofenceRegistration) {
        return geofenceRegistration.getIdentity().getUserId() == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationEnabledChanged$3(int i, com.android.server.location.geofence.GeofenceManager.GeofenceRegistration geofenceRegistration) {
        return geofenceRegistration.getIdentity().getUserId() == i;
    }

    void onLocationEnabledChanged(final int i) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onLocationEnabledChanged$3;
                lambda$onLocationEnabledChanged$3 = com.android.server.location.geofence.GeofenceManager.lambda$onLocationEnabledChanged$3(i, (com.android.server.location.geofence.GeofenceManager.GeofenceRegistration) obj);
                return lambda$onLocationEnabledChanged$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationPackageBlacklistChanged$4(int i, com.android.server.location.geofence.GeofenceManager.GeofenceRegistration geofenceRegistration) {
        return geofenceRegistration.getIdentity().getUserId() == i;
    }

    void onLocationPackageBlacklistChanged(final int i) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onLocationPackageBlacklistChanged$4;
                lambda$onLocationPackageBlacklistChanged$4 = com.android.server.location.geofence.GeofenceManager.lambda$onLocationPackageBlacklistChanged$4(i, (com.android.server.location.geofence.GeofenceManager.GeofenceRegistration) obj);
                return lambda$onLocationPackageBlacklistChanged$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationPermissionsChanged$5(java.lang.String str, com.android.server.location.geofence.GeofenceManager.GeofenceRegistration geofenceRegistration) {
        return geofenceRegistration.onLocationPermissionsChanged(str);
    }

    void onLocationPermissionsChanged(@android.annotation.Nullable final java.lang.String str) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onLocationPermissionsChanged$5;
                lambda$onLocationPermissionsChanged$5 = com.android.server.location.geofence.GeofenceManager.lambda$onLocationPermissionsChanged$5(str, (com.android.server.location.geofence.GeofenceManager.GeofenceRegistration) obj);
                return lambda$onLocationPermissionsChanged$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationPermissionsChanged$6(int i, com.android.server.location.geofence.GeofenceManager.GeofenceRegistration geofenceRegistration) {
        return geofenceRegistration.onLocationPermissionsChanged(i);
    }

    void onLocationPermissionsChanged(final int i) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.geofence.GeofenceManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onLocationPermissionsChanged$6;
                lambda$onLocationPermissionsChanged$6 = com.android.server.location.geofence.GeofenceManager.lambda$onLocationPermissionsChanged$6(i, (com.android.server.location.geofence.GeofenceManager.GeofenceRegistration) obj);
                return lambda$onLocationPermissionsChanged$6;
            }
        });
    }
}
