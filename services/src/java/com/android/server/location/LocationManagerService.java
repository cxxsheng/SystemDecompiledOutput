package com.android.server.location;

/* loaded from: classes2.dex */
public class LocationManagerService extends android.location.ILocationManager.Stub implements com.android.server.location.provider.LocationProviderManager.StateChangedListener {
    private static final java.lang.String ATTRIBUTION_TAG = "LocationService";
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mDeprecatedGnssBatchingLock"})
    @android.annotation.Nullable
    private android.location.ILocationListener mDeprecatedGnssBatchingListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mExtraLocationControllerPackage;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mExtraLocationControllerPackageEnabled;
    private com.android.server.location.provider.proxy.ProxyGeocodeProvider mGeocodeProvider;
    private final com.android.server.location.geofence.GeofenceManager mGeofenceManager;
    private final com.android.server.location.injector.Injector mInjector;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    android.location.LocationManagerInternal.LocationPackageTagsListener mLocationTagsChangedListener;
    private final com.android.server.location.provider.PassiveLocationProviderManager mPassiveManager;
    public static final java.lang.String TAG = "LocationManagerService";
    public static final boolean D = android.util.Log.isLoggable(TAG, 3);
    final java.lang.Object mLock = new java.lang.Object();

    @android.annotation.Nullable
    private volatile com.android.server.location.gnss.GnssManagerService mGnssManagerService = null;
    private final java.lang.Object mDeprecatedGnssBatchingLock = new java.lang.Object();
    final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.provider.LocationProviderManager> mProviderManagers = new java.util.concurrent.CopyOnWriteArrayList<>();
    private final com.android.server.location.LocationManagerService.LocalService mLocalService = new com.android.server.location.LocationManagerService.LocalService();

    public static class Lifecycle extends com.android.server.SystemService {
        private final com.android.server.location.LocationManagerService mService;
        private final com.android.server.location.LocationManagerService.SystemInjector mSystemInjector;
        private final com.android.server.location.LocationManagerService.Lifecycle.LifecycleUserInfoHelper mUserInfoHelper;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mUserInfoHelper = new com.android.server.location.LocationManagerService.Lifecycle.LifecycleUserInfoHelper(context);
            this.mSystemInjector = new com.android.server.location.LocationManagerService.SystemInjector(context, this.mUserInfoHelper);
            this.mService = new com.android.server.location.LocationManagerService(context, this.mSystemInjector);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishBinderService("location", this.mService);
            android.location.LocationManager.invalidateLocalLocationEnabledCaches();
            android.location.LocationManager.disableLocalLocationEnabledCaches();
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 500) {
                this.mSystemInjector.onSystemReady();
                this.mService.onSystemReady();
            } else if (i == 600) {
                this.mService.onSystemThirdPartyAppsCanStart();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(com.android.server.SystemService.TargetUser targetUser) {
            this.mUserInfoHelper.onUserStarted(targetUser.getUserIdentifier());
            this.mService.logLocationEnabledState();
            this.mService.logEmergencyState();
        }

        @Override // com.android.server.SystemService
        public void onUserSwitching(com.android.server.SystemService.TargetUser targetUser, com.android.server.SystemService.TargetUser targetUser2) {
            this.mUserInfoHelper.onCurrentUserChanged(targetUser.getUserIdentifier(), targetUser2.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(com.android.server.SystemService.TargetUser targetUser) {
            this.mUserInfoHelper.onUserStopped(targetUser.getUserIdentifier());
        }

        private static class LifecycleUserInfoHelper extends com.android.server.location.injector.SystemUserInfoHelper {
            LifecycleUserInfoHelper(android.content.Context context) {
                super(context);
            }

            void onUserStarted(int i) {
                dispatchOnUserStarted(i);
            }

            void onUserStopped(int i) {
                dispatchOnUserStopped(i);
            }

            void onCurrentUserChanged(int i, int i2) {
                dispatchOnCurrentUserChanged(i, i2);
            }
        }
    }

    LocationManagerService(android.content.Context context, com.android.server.location.injector.Injector injector) {
        this.mContext = context.createAttributionContext(ATTRIBUTION_TAG);
        this.mInjector = injector;
        com.android.server.LocalServices.addService(android.location.LocationManagerInternal.class, this.mLocalService);
        this.mGeofenceManager = new com.android.server.location.geofence.GeofenceManager(this.mContext, injector);
        this.mInjector.getLocationSettings().registerLocationUserSettingsListener(new com.android.server.location.settings.LocationSettings.LocationUserSettingsListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda5
            @Override // com.android.server.location.settings.LocationSettings.LocationUserSettingsListener
            public final void onLocationUserSettingsChanged(int i, com.android.server.location.settings.LocationUserSettings locationUserSettings, com.android.server.location.settings.LocationUserSettings locationUserSettings2) {
                com.android.server.location.LocationManagerService.this.onLocationUserSettingsChanged(i, locationUserSettings, locationUserSettings2);
            }
        });
        this.mInjector.getSettingsHelper().addOnLocationEnabledChangedListener(new com.android.server.location.injector.SettingsHelper.UserSettingChangedListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda6
            @Override // com.android.server.location.injector.SettingsHelper.UserSettingChangedListener
            public final void onSettingChanged(int i) {
                com.android.server.location.LocationManagerService.this.onLocationModeChanged(i);
            }
        });
        this.mInjector.getSettingsHelper().addAdasAllowlistChangedListener(new com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda7
            @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
            public final void onSettingChanged() {
                com.android.server.location.LocationManagerService.this.lambda$new$0();
            }
        });
        this.mInjector.getSettingsHelper().addIgnoreSettingsAllowlistChangedListener(new com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda8
            @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
            public final void onSettingChanged() {
                com.android.server.location.LocationManagerService.this.lambda$new$1();
            }
        });
        this.mInjector.getUserInfoHelper().addListener(new com.android.server.location.injector.UserInfoHelper.UserListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda9
            @Override // com.android.server.location.injector.UserInfoHelper.UserListener
            public final void onUserChanged(int i, int i2) {
                com.android.server.location.LocationManagerService.this.lambda$new$2(i, i2);
            }
        });
        this.mInjector.getEmergencyHelper().addOnEmergencyStateChangedListener(new com.android.server.location.injector.EmergencyHelper.EmergencyStateChangedListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda10
            @Override // com.android.server.location.injector.EmergencyHelper.EmergencyStateChangedListener
            public final void onStateChanged() {
                com.android.server.location.LocationManagerService.this.onEmergencyStateChanged();
            }
        });
        this.mPassiveManager = new com.android.server.location.provider.PassiveLocationProviderManager(this.mContext, injector);
        addLocationProviderManager(this.mPassiveManager, new com.android.server.location.provider.PassiveLocationProvider(this.mContext));
        com.android.server.pm.permission.LegacyPermissionManagerInternal legacyPermissionManagerInternal = (com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class);
        legacyPermissionManagerInternal.setLocationPackagesProvider(new com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda11
            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider
            public final java.lang.String[] getPackages(int i) {
                java.lang.String[] lambda$new$3;
                lambda$new$3 = com.android.server.location.LocationManagerService.this.lambda$new$3(i);
                return lambda$new$3;
            }
        });
        legacyPermissionManagerInternal.setLocationExtraPackagesProvider(new com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda12
            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider
            public final java.lang.String[] getPackages(int i) {
                java.lang.String[] lambda$new$4;
                lambda$new$4 = com.android.server.location.LocationManagerService.this.lambda$new$4(i);
                return lambda$new$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        refreshAppOpsRestrictions(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        refreshAppOpsRestrictions(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(int i, int i2) {
        if (i2 == 2) {
            refreshAppOpsRestrictions(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String[] lambda$new$3(int i) {
        return this.mContext.getResources().getStringArray(android.R.array.config_localPrivateDisplayPorts);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String[] lambda$new$4(int i) {
        return this.mContext.getResources().getStringArray(android.R.array.config_keep_warming_services);
    }

    @android.annotation.Nullable
    com.android.server.location.provider.LocationProviderManager getLocationProviderManager(java.lang.String str) {
        if (str == null) {
            return null;
        }
        java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            com.android.server.location.provider.LocationProviderManager next = it.next();
            if (str.equals(next.getName())) {
                if (!next.isVisibleToCaller()) {
                    return null;
                }
                return next;
            }
        }
        return null;
    }

    private com.android.server.location.provider.LocationProviderManager getOrAddLocationProviderManager(java.lang.String str) {
        synchronized (this.mProviderManagers) {
            try {
                java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
                while (it.hasNext()) {
                    com.android.server.location.provider.LocationProviderManager next = it.next();
                    if (str.equals(next.getName())) {
                        return next;
                    }
                }
                com.android.server.location.provider.LocationProviderManager locationProviderManager = new com.android.server.location.provider.LocationProviderManager(this.mContext, this.mInjector, str, this.mPassiveManager);
                addLocationProviderManager(locationProviderManager, null);
                return locationProviderManager;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void addLocationProviderManager(com.android.server.location.provider.LocationProviderManager locationProviderManager, @android.annotation.Nullable com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider) {
        synchronized (this.mProviderManagers) {
            try {
                com.android.internal.util.Preconditions.checkState(getLocationProviderManager(locationProviderManager.getName()) == null);
                locationProviderManager.startManager(this);
                if (abstractLocationProvider != null) {
                    if (locationProviderManager != this.mPassiveManager) {
                        if (android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "location_enable_stationary_throttle", this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch") ? 0 : 1) != 0) {
                            abstractLocationProvider = new com.android.server.location.provider.StationaryThrottlingLocationProvider(locationProviderManager.getName(), this.mInjector, abstractLocationProvider);
                        }
                    }
                    locationProviderManager.setRealProvider(abstractLocationProvider);
                }
                this.mProviderManagers.add(locationProviderManager);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void removeLocationProviderManager(com.android.server.location.provider.LocationProviderManager locationProviderManager) {
        synchronized (this.mProviderManagers) {
            com.android.internal.util.Preconditions.checkArgument(this.mProviderManagers.remove(locationProviderManager));
            locationProviderManager.setMockProvider(null);
            locationProviderManager.setRealProvider(null);
            locationProviderManager.stopManager();
        }
    }

    void onSystemReady() {
        if (android.os.Build.IS_DEBUGGABLE) {
            android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
            java.util.Objects.requireNonNull(appOpsManager);
            appOpsManager.startWatchingNoted(new int[]{1, 0}, new android.app.AppOpsManager.OnOpNotedListener() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda2
                public final void onOpNoted(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2, int i3) {
                    com.android.server.location.LocationManagerService.this.lambda$onSystemReady$5(str, i, str2, str3, i2, i3);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$5(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2, int i3) {
        if (!isLocationEnabledForUser(android.os.UserHandle.getUserId(i))) {
            android.util.Log.w(TAG, "location noteOp with location off - " + android.location.util.identity.CallerIdentity.forTest(i, 0, str2, str3));
        }
    }

    void onSystemThirdPartyAppsCanStart() {
        com.android.server.location.provider.proxy.ProxyLocationProvider create = com.android.server.location.provider.proxy.ProxyLocationProvider.create(this.mContext, "network", "com.android.location.service.v3.NetworkLocationProvider", android.R.bool.config_enableMotionPrediction, android.R.string.config_mainBuiltInDisplayCutout);
        if (create == null) {
            android.util.Log.w(TAG, "no network location provider found");
        } else {
            addLocationProviderManager(new com.android.server.location.provider.LocationProviderManager(this.mContext, this.mInjector, "network", this.mPassiveManager), create);
        }
        com.android.internal.util.Preconditions.checkState(!this.mContext.getPackageManager().queryIntentServicesAsUser(new android.content.Intent("com.android.location.service.FusedLocationProvider"), 1572864, 0).isEmpty(), "Unable to find a direct boot aware fused location provider");
        com.android.server.location.provider.proxy.ProxyLocationProvider create2 = com.android.server.location.provider.proxy.ProxyLocationProvider.create(this.mContext, "fused", "com.android.location.service.FusedLocationProvider", android.R.bool.config_enableContextSyncInCall, android.R.string.config_emergency_call_number);
        if (create2 == null) {
            android.util.Log.wtf(TAG, "no fused location provider found");
        } else {
            addLocationProviderManager(new com.android.server.location.provider.LocationProviderManager(this.mContext, this.mInjector, "fused", this.mPassiveManager), create2);
        }
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.location") && com.android.server.location.gnss.hal.GnssNative.isSupported()) {
            this.mGnssManagerService = new com.android.server.location.gnss.GnssManagerService(this.mContext, this.mInjector, com.android.server.location.gnss.hal.GnssNative.create(this.mInjector, new com.android.server.location.gnss.GnssConfiguration(this.mContext)));
            this.mGnssManagerService.onSystemReady();
            com.android.server.location.provider.AbstractLocationProvider create3 = !this.mContext.getResources().getBoolean(android.R.bool.config_useCurrentRotationOnRotationLockChange) ? com.android.server.location.provider.proxy.ProxyLocationProvider.create(this.mContext, "gps", "android.location.provider.action.GNSS_PROVIDER", android.R.bool.config_enableContextSyncInCall, android.R.string.config_extensionFallbackPackageName) : null;
            if (create3 == null) {
                create3 = this.mGnssManagerService.getGnssLocationProvider();
            } else {
                addLocationProviderManager(new com.android.server.location.provider.LocationProviderManager(this.mContext, this.mInjector, "gps_hardware", null, java.util.Collections.singletonList("android.permission.LOCATION_HARDWARE")), this.mGnssManagerService.getGnssLocationProvider());
            }
            addLocationProviderManager(new com.android.server.location.provider.LocationProviderManager(this.mContext, this.mInjector, "gps", this.mPassiveManager), create3);
        }
        this.mGeocodeProvider = com.android.server.location.provider.proxy.ProxyGeocodeProvider.createAndRegister(this.mContext);
        if (this.mGeocodeProvider == null) {
            android.util.Log.e(TAG, "no geocoder provider found");
        }
        if (com.android.server.location.HardwareActivityRecognitionProxy.createAndRegister(this.mContext) == null) {
            android.util.Log.e(TAG, "unable to bind ActivityRecognitionProxy");
        }
        if (this.mGnssManagerService != null && com.android.server.location.geofence.GeofenceProxy.createAndBind(this.mContext, this.mGnssManagerService.getGnssGeofenceProxy()) == null) {
            android.util.Log.e(TAG, "unable to bind to GeofenceProxy");
        }
        for (java.lang.String str : this.mContext.getResources().getStringArray(android.R.array.config_system_condition_providers)) {
            java.lang.String[] split = str.split(",");
            getOrAddLocationProviderManager(split[0].trim()).setMockProvider(new com.android.server.location.provider.MockLocationProvider(new android.location.provider.ProviderProperties.Builder().setHasNetworkRequirement(java.lang.Boolean.parseBoolean(split[1])).setHasSatelliteRequirement(java.lang.Boolean.parseBoolean(split[2])).setHasCellRequirement(java.lang.Boolean.parseBoolean(split[3])).setHasMonetaryCost(java.lang.Boolean.parseBoolean(split[4])).setHasAltitudeSupport(java.lang.Boolean.parseBoolean(split[5])).setHasSpeedSupport(java.lang.Boolean.parseBoolean(split[6])).setHasBearingSupport(java.lang.Boolean.parseBoolean(split[7])).setPowerUsage(java.lang.Integer.parseInt(split[8])).setAccuracy(java.lang.Integer.parseInt(split[9])).build(), android.location.util.identity.CallerIdentity.fromContext(this.mContext), java.util.Collections.emptySet()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocationUserSettingsChanged(int i, com.android.server.location.settings.LocationUserSettings locationUserSettings, com.android.server.location.settings.LocationUserSettings locationUserSettings2) {
        if (locationUserSettings.isAdasGnssLocationEnabled() != locationUserSettings2.isAdasGnssLocationEnabled()) {
            boolean isAdasGnssLocationEnabled = locationUserSettings2.isAdasGnssLocationEnabled();
            if (D) {
                android.util.Log.d(TAG, "[u" + i + "] adas gnss location enabled = " + isAdasGnssLocationEnabled);
            }
            com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logAdasLocationEnabled(i, isAdasGnssLocationEnabled);
            this.mContext.sendBroadcastAsUser(new android.content.Intent("android.location.action.ADAS_GNSS_ENABLED_CHANGED").putExtra("android.location.extra.ADAS_GNSS_ENABLED", isAdasGnssLocationEnabled).addFlags(1073741824).addFlags(268435456), android.os.UserHandle.of(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocationModeChanged(int i) {
        boolean isLocationEnabled = this.mInjector.getSettingsHelper().isLocationEnabled(i);
        android.location.LocationManager.invalidateLocalLocationEnabledCaches();
        if (D) {
            android.util.Log.d(TAG, "[u" + i + "] location enabled = " + isLocationEnabled);
        }
        com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logLocationEnabled(i, isLocationEnabled);
        logLocationEnabledState();
        this.mContext.sendBroadcastAsUser(new android.content.Intent("android.location.MODE_CHANGED").putExtra("android.location.extra.LOCATION_ENABLED", isLocationEnabled).addFlags(1073741824).addFlags(268435456), android.os.UserHandle.of(i));
        refreshAppOpsRestrictions(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onEmergencyStateChanged() {
        logEmergencyState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logEmergencyState() {
        this.mInjector.getLocationUsageLogger().logEmergencyStateChanged(this.mInjector.getEmergencyHelper().isInEmergency(Long.MIN_VALUE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logLocationEnabledState() {
        boolean z = false;
        for (int i : this.mInjector.getUserInfoHelper().getRunningUserIds()) {
            z = this.mInjector.getSettingsHelper().isLocationEnabled(i);
            if (z) {
                break;
            }
        }
        this.mInjector.getLocationUsageLogger().logLocationEnabledStateChanged(z);
    }

    public int getGnssYearOfHardware() {
        if (this.mGnssManagerService == null) {
            return 0;
        }
        return this.mGnssManagerService.getGnssYearOfHardware();
    }

    @android.annotation.Nullable
    public java.lang.String getGnssHardwareModelName() {
        return this.mGnssManagerService == null ? "" : this.mGnssManagerService.getGnssHardwareModelName();
    }

    public int getGnssBatchSize() {
        if (this.mGnssManagerService == null) {
            return 0;
        }
        return this.mGnssManagerService.getGnssBatchSize();
    }

    @android.annotation.EnforcePermission("android.permission.LOCATION_HARDWARE")
    public void startGnssBatch(long j, android.location.ILocationListener iLocationListener, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        startGnssBatch_enforcePermission();
        if (this.mGnssManagerService == null) {
            return;
        }
        long millis = java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(j);
        synchronized (this.mDeprecatedGnssBatchingLock) {
            stopGnssBatch();
            registerLocationListener("gps", new android.location.LocationRequest.Builder(millis).setMaxUpdateDelayMillis(millis * this.mGnssManagerService.getGnssBatchSize()).setHiddenFromAppOps(true).build(), iLocationListener, str, str2, str3);
            this.mDeprecatedGnssBatchingListener = iLocationListener;
        }
    }

    @android.annotation.EnforcePermission("android.permission.LOCATION_HARDWARE")
    public void flushGnssBatch() {
        flushGnssBatch_enforcePermission();
        if (this.mGnssManagerService == null) {
            return;
        }
        synchronized (this.mDeprecatedGnssBatchingLock) {
            try {
                if (this.mDeprecatedGnssBatchingListener != null) {
                    requestListenerFlush("gps", this.mDeprecatedGnssBatchingListener, 0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.LOCATION_HARDWARE")
    public void stopGnssBatch() {
        stopGnssBatch_enforcePermission();
        if (this.mGnssManagerService == null) {
            return;
        }
        synchronized (this.mDeprecatedGnssBatchingLock) {
            try {
                if (this.mDeprecatedGnssBatchingListener != null) {
                    android.location.ILocationListener iLocationListener = this.mDeprecatedGnssBatchingListener;
                    this.mDeprecatedGnssBatchingListener = null;
                    unregisterLocationListener(iLocationListener);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean hasProvider(java.lang.String str) {
        return getLocationProviderManager(str) != null;
    }

    public java.util.List<java.lang.String> getAllProviders() {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mProviderManagers.size());
        java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            com.android.server.location.provider.LocationProviderManager next = it.next();
            if (next.isVisibleToCaller()) {
                arrayList.add(next.getName());
            }
        }
        return arrayList;
    }

    public java.util.List<java.lang.String> getProviders(android.location.Criteria criteria, boolean z) {
        java.util.ArrayList arrayList;
        if (!com.android.server.location.LocationPermissions.checkCallingOrSelfLocationPermission(this.mContext, 1)) {
            return java.util.Collections.emptyList();
        }
        synchronized (this.mLock) {
            try {
                arrayList = new java.util.ArrayList(this.mProviderManagers.size());
                java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
                while (it.hasNext()) {
                    com.android.server.location.provider.LocationProviderManager next = it.next();
                    if (next.isVisibleToCaller()) {
                        java.lang.String name = next.getName();
                        if (!z || next.isEnabled(android.os.UserHandle.getCallingUserId())) {
                            if (criteria == null || android.location.LocationProvider.propertiesMeetCriteria(name, next.getProperties(), criteria)) {
                                arrayList.add(name);
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public java.lang.String getBestProvider(android.location.Criteria criteria, boolean z) {
        java.util.List<java.lang.String> providers;
        synchronized (this.mLock) {
            try {
                providers = getProviders(criteria, z);
                if (providers.isEmpty()) {
                    providers = getProviders(null, z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (providers.isEmpty()) {
            return null;
        }
        if (providers.contains("fused")) {
            return "fused";
        }
        if (providers.contains("gps")) {
            return "gps";
        }
        if (providers.contains("network")) {
            return "network";
        }
        return providers.get(0);
    }

    public java.lang.String[] getBackgroundThrottlingWhitelist() {
        return (java.lang.String[]) this.mInjector.getSettingsHelper().getBackgroundThrottlePackageWhitelist().toArray(new java.lang.String[0]);
    }

    public android.os.PackageTagsList getIgnoreSettingsAllowlist() {
        return this.mInjector.getSettingsHelper().getIgnoreSettingsAllowlist();
    }

    public android.os.PackageTagsList getAdasAllowlist() {
        return this.mInjector.getSettingsHelper().getAdasAllowlist();
    }

    @android.annotation.Nullable
    public android.os.ICancellationSignal getCurrentLocation(java.lang.String str, android.location.LocationRequest locationRequest, android.location.ILocationCallback iLocationCallback, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, java.lang.String str4) {
        android.location.util.identity.CallerIdentity fromBinder = android.location.util.identity.CallerIdentity.fromBinder(this.mContext, str2, str3, str4);
        int permissionLevel = com.android.server.location.LocationPermissions.getPermissionLevel(this.mContext, fromBinder.getUid(), fromBinder.getPid());
        com.android.server.location.LocationPermissions.enforceLocationPermission(fromBinder.getUid(), permissionLevel, 1);
        com.android.internal.util.Preconditions.checkState((fromBinder.getPid() == android.os.Process.myPid() && str3 == null) ? false : true);
        android.location.LocationRequest validateLocationRequest = validateLocationRequest(str, locationRequest, fromBinder);
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        com.android.internal.util.Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        return locationProviderManager.getCurrentLocation(validateLocationRequest, fromBinder, permissionLevel, iLocationCallback);
    }

    public void registerLocationListener(java.lang.String str, android.location.LocationRequest locationRequest, android.location.ILocationListener iLocationListener, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, java.lang.String str4) {
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        if (activityManagerInternal != null) {
            activityManagerInternal.logFgsApiBegin(3, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid());
        }
        android.location.util.identity.CallerIdentity fromBinder = android.location.util.identity.CallerIdentity.fromBinder(this.mContext, str2, str3, str4);
        int permissionLevel = com.android.server.location.LocationPermissions.getPermissionLevel(this.mContext, fromBinder.getUid(), fromBinder.getPid());
        com.android.server.location.LocationPermissions.enforceLocationPermission(fromBinder.getUid(), permissionLevel, 1);
        if (fromBinder.getPid() == android.os.Process.myPid() && str3 == null) {
            android.util.Log.w(TAG, "system location request with no attribution tag", new java.lang.IllegalArgumentException());
        }
        android.location.LocationRequest validateLocationRequest = validateLocationRequest(str, locationRequest, fromBinder);
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        com.android.internal.util.Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        locationProviderManager.registerLocationRequest(validateLocationRequest, fromBinder, permissionLevel, iLocationListener);
    }

    public void registerLocationPendingIntent(java.lang.String str, android.location.LocationRequest locationRequest, android.app.PendingIntent pendingIntent, java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
        android.location.util.identity.CallerIdentity fromBinder = android.location.util.identity.CallerIdentity.fromBinder(this.mContext, str2, str3, android.app.AppOpsManager.toReceiverId(pendingIntent));
        int permissionLevel = com.android.server.location.LocationPermissions.getPermissionLevel(this.mContext, fromBinder.getUid(), fromBinder.getPid());
        com.android.server.location.LocationPermissions.enforceLocationPermission(fromBinder.getUid(), permissionLevel, 1);
        com.android.internal.util.Preconditions.checkArgument((fromBinder.getPid() == android.os.Process.myPid() && str3 == null) ? false : true);
        if (android.app.compat.CompatChanges.isChangeEnabled(169887240L, fromBinder.getUid())) {
            if (locationRequest.isLowPower() || locationRequest.isHiddenFromAppOps() || locationRequest.isLocationSettingsIgnored() || !locationRequest.getWorkSource().isEmpty()) {
                throw new java.lang.SecurityException("PendingIntent location requests may not use system APIs: " + locationRequest);
            }
        }
        android.location.LocationRequest validateLocationRequest = validateLocationRequest(str, locationRequest, fromBinder);
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        com.android.internal.util.Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        locationProviderManager.registerLocationRequest(validateLocationRequest, fromBinder, permissionLevel, pendingIntent);
    }

    private android.location.LocationRequest validateLocationRequest(java.lang.String str, android.location.LocationRequest locationRequest, android.location.util.identity.CallerIdentity callerIdentity) {
        if (!locationRequest.getWorkSource().isEmpty()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_DEVICE_STATS", "setting a work source requires android.permission.UPDATE_DEVICE_STATS");
        }
        android.location.LocationRequest.Builder builder = new android.location.LocationRequest.Builder(locationRequest);
        if (!android.app.compat.CompatChanges.isChangeEnabled(168936375L, android.os.Binder.getCallingUid()) && this.mContext.checkCallingPermission("android.permission.LOCATION_HARDWARE") != 0) {
            builder.setLowPower(false);
        }
        android.os.WorkSource workSource = new android.os.WorkSource(locationRequest.getWorkSource());
        if (workSource.size() > 0 && workSource.getPackageName(0) == null) {
            android.util.Log.w(TAG, "received (and ignoring) illegal worksource with no package name");
            workSource.clear();
        } else {
            java.util.List workChains = workSource.getWorkChains();
            if (workChains != null && !workChains.isEmpty() && ((android.os.WorkSource.WorkChain) workChains.get(0)).getAttributionTag() == null) {
                android.util.Log.w(TAG, "received (and ignoring) illegal worksource with no attribution tag");
                workSource.clear();
            }
        }
        if (workSource.isEmpty()) {
            callerIdentity.addToWorkSource(workSource);
        }
        builder.setWorkSource(workSource);
        android.location.LocationRequest build = builder.build();
        boolean isProvider = this.mLocalService.isProvider(null, callerIdentity);
        if (build.isLowPower() && android.app.compat.CompatChanges.isChangeEnabled(168936375L, callerIdentity.getUid())) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.LOCATION_HARDWARE", "low power request requires android.permission.LOCATION_HARDWARE");
        }
        if (build.isHiddenFromAppOps()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_APP_OPS_STATS", "hiding from app ops requires android.permission.UPDATE_APP_OPS_STATS");
        }
        if (build.isAdasGnssBypass()) {
            if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                throw new java.lang.IllegalArgumentException("adas gnss bypass requests are only allowed on automotive devices");
            }
            if (!"gps".equals(str)) {
                throw new java.lang.IllegalArgumentException("adas gnss bypass requests are only allowed on the \"gps\" provider");
            }
            if (!isProvider) {
                com.android.server.location.LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
            }
        }
        if (build.isLocationSettingsIgnored() && !isProvider) {
            com.android.server.location.LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
        }
        return build;
    }

    public void requestListenerFlush(java.lang.String str, android.location.ILocationListener iLocationListener, int i) {
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        com.android.internal.util.Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        java.util.Objects.requireNonNull(iLocationListener);
        locationProviderManager.flush(iLocationListener, i);
    }

    public void requestPendingIntentFlush(java.lang.String str, android.app.PendingIntent pendingIntent, int i) {
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        com.android.internal.util.Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        java.util.Objects.requireNonNull(pendingIntent);
        locationProviderManager.flush(pendingIntent, i);
    }

    public void unregisterLocationListener(android.location.ILocationListener iLocationListener) {
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        if (activityManagerInternal != null) {
            activityManagerInternal.logFgsApiEnd(3, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid());
        }
        java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            it.next().unregisterLocationRequest(iLocationListener);
        }
    }

    public void unregisterLocationPendingIntent(android.app.PendingIntent pendingIntent) {
        java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            it.next().unregisterLocationRequest(pendingIntent);
        }
    }

    public android.location.Location getLastLocation(java.lang.String str, android.location.LastLocationRequest lastLocationRequest, java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
        android.location.util.identity.CallerIdentity fromBinder = android.location.util.identity.CallerIdentity.fromBinder(this.mContext, str2, str3);
        int permissionLevel = com.android.server.location.LocationPermissions.getPermissionLevel(this.mContext, fromBinder.getUid(), fromBinder.getPid());
        boolean z = true;
        com.android.server.location.LocationPermissions.enforceLocationPermission(fromBinder.getUid(), permissionLevel, 1);
        if (fromBinder.getPid() == android.os.Process.myPid() && str3 == null) {
            z = false;
        }
        com.android.internal.util.Preconditions.checkArgument(z);
        android.location.LastLocationRequest validateLastLocationRequest = validateLastLocationRequest(str, lastLocationRequest, fromBinder);
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        if (locationProviderManager == null) {
            return null;
        }
        return locationProviderManager.getLastLocation(validateLastLocationRequest, fromBinder, permissionLevel);
    }

    private android.location.LastLocationRequest validateLastLocationRequest(java.lang.String str, android.location.LastLocationRequest lastLocationRequest, android.location.util.identity.CallerIdentity callerIdentity) {
        android.location.LastLocationRequest build = new android.location.LastLocationRequest.Builder(lastLocationRequest).build();
        boolean isProvider = this.mLocalService.isProvider(null, callerIdentity);
        if (build.isHiddenFromAppOps()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_APP_OPS_STATS", "hiding from app ops requires android.permission.UPDATE_APP_OPS_STATS");
        }
        if (build.isAdasGnssBypass()) {
            if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                throw new java.lang.IllegalArgumentException("adas gnss bypass requests are only allowed on automotive devices");
            }
            if (!"gps".equals(str)) {
                throw new java.lang.IllegalArgumentException("adas gnss bypass requests are only allowed on the \"gps\" provider");
            }
            if (!isProvider) {
                com.android.server.location.LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
            }
        }
        if (build.isLocationSettingsIgnored() && !isProvider) {
            com.android.server.location.LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
        }
        return build;
    }

    public android.location.LocationTime getGnssTimeMillis() {
        return this.mLocalService.getGnssTimeMillis();
    }

    @android.annotation.EnforcePermission(allOf = {"android.permission.LOCATION_HARDWARE", "android.permission.ACCESS_FINE_LOCATION"})
    public void injectLocation(android.location.Location location) {
        super.injectLocation_enforcePermission();
        com.android.internal.util.Preconditions.checkArgument(location.isComplete());
        int callingUserId = android.os.UserHandle.getCallingUserId();
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(location.getProvider());
        if (locationProviderManager != null && locationProviderManager.isEnabled(callingUserId)) {
            java.util.Objects.requireNonNull(location);
            locationProviderManager.injectLastLocation(location, callingUserId);
        }
    }

    public void requestGeofence(android.location.Geofence geofence, android.app.PendingIntent pendingIntent, java.lang.String str, java.lang.String str2) {
        this.mGeofenceManager.addGeofence(geofence, pendingIntent, str, str2);
    }

    public void removeGeofence(android.app.PendingIntent pendingIntent) {
        this.mGeofenceManager.removeGeofence(pendingIntent);
    }

    public void registerGnssStatusCallback(android.location.IGnssStatusListener iGnssStatusListener, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.registerGnssStatusCallback(iGnssStatusListener, str, str2, str3);
        }
    }

    public void unregisterGnssStatusCallback(android.location.IGnssStatusListener iGnssStatusListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.unregisterGnssStatusCallback(iGnssStatusListener);
        }
    }

    public void registerGnssNmeaCallback(android.location.IGnssNmeaListener iGnssNmeaListener, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.registerGnssNmeaCallback(iGnssNmeaListener, str, str2, str3);
        }
    }

    public void unregisterGnssNmeaCallback(android.location.IGnssNmeaListener iGnssNmeaListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.unregisterGnssNmeaCallback(iGnssNmeaListener);
        }
    }

    public void addGnssMeasurementsListener(android.location.GnssMeasurementRequest gnssMeasurementRequest, android.location.IGnssMeasurementsListener iGnssMeasurementsListener, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.addGnssMeasurementsListener(gnssMeasurementRequest, iGnssMeasurementsListener, str, str2, str3);
        }
    }

    public void removeGnssMeasurementsListener(android.location.IGnssMeasurementsListener iGnssMeasurementsListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.removeGnssMeasurementsListener(iGnssMeasurementsListener);
        }
    }

    public void addGnssAntennaInfoListener(android.location.IGnssAntennaInfoListener iGnssAntennaInfoListener, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.addGnssAntennaInfoListener(iGnssAntennaInfoListener, str, str2, str3);
        }
    }

    public void removeGnssAntennaInfoListener(android.location.IGnssAntennaInfoListener iGnssAntennaInfoListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.removeGnssAntennaInfoListener(iGnssAntennaInfoListener);
        }
    }

    @android.annotation.EnforcePermission("android.permission.INTERACT_ACROSS_USERS")
    @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS")
    public void addProviderRequestListener(android.location.provider.IProviderRequestListener iProviderRequestListener) {
        addProviderRequestListener_enforcePermission();
        java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            com.android.server.location.provider.LocationProviderManager next = it.next();
            if (next.isVisibleToCaller()) {
                next.addProviderRequestListener(iProviderRequestListener);
            }
        }
    }

    public void removeProviderRequestListener(android.location.provider.IProviderRequestListener iProviderRequestListener) {
        java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            it.next().removeProviderRequestListener(iProviderRequestListener);
        }
    }

    public void injectGnssMeasurementCorrections(android.location.GnssMeasurementCorrections gnssMeasurementCorrections) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.injectGnssMeasurementCorrections(gnssMeasurementCorrections);
        }
    }

    public android.location.GnssCapabilities getGnssCapabilities() {
        return this.mGnssManagerService == null ? new android.location.GnssCapabilities.Builder().build() : this.mGnssManagerService.getGnssCapabilities();
    }

    public java.util.List<android.location.GnssAntennaInfo> getGnssAntennaInfos() {
        if (this.mGnssManagerService == null) {
            return null;
        }
        return this.mGnssManagerService.getGnssAntennaInfos();
    }

    public void addGnssNavigationMessageListener(android.location.IGnssNavigationMessageListener iGnssNavigationMessageListener, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.addGnssNavigationMessageListener(iGnssNavigationMessageListener, str, str2, str3);
        }
    }

    public void removeGnssNavigationMessageListener(android.location.IGnssNavigationMessageListener iGnssNavigationMessageListener) {
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.removeGnssNavigationMessageListener(iGnssNavigationMessageListener);
        }
    }

    public void sendExtraCommand(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        com.android.server.location.LocationPermissions.enforceCallingOrSelfLocationPermission(this.mContext, 1);
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_LOCATION_EXTRA_COMMANDS", null);
        java.util.Objects.requireNonNull(str);
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        if (locationProviderManager != null) {
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            java.util.Objects.requireNonNull(str2);
            locationProviderManager.sendExtraCommand(callingUid, callingPid, str2, bundle);
        }
        this.mInjector.getLocationUsageLogger().logLocationApiUsage(0, 5, str);
        this.mInjector.getLocationUsageLogger().logLocationApiUsage(1, 5, str);
    }

    public android.location.provider.ProviderProperties getProviderProperties(java.lang.String str) {
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        com.android.internal.util.Preconditions.checkArgument(locationProviderManager != null, "provider \"" + str + "\" does not exist");
        return locationProviderManager.getProperties();
    }

    @android.annotation.EnforcePermission("android.permission.READ_DEVICE_CONFIG")
    public boolean isProviderPackage(@android.annotation.Nullable java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
        isProviderPackage_enforcePermission();
        java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            com.android.server.location.provider.LocationProviderManager next = it.next();
            if (str == null || str.equals(next.getName())) {
                android.location.util.identity.CallerIdentity providerIdentity = next.getProviderIdentity();
                if (providerIdentity != null && providerIdentity.getPackageName().equals(str2) && (str3 == null || java.util.Objects.equals(providerIdentity.getAttributionTag(), str3))) {
                    return true;
                }
            }
        }
        return false;
    }

    @android.annotation.EnforcePermission("android.permission.READ_DEVICE_CONFIG")
    public java.util.List<java.lang.String> getProviderPackages(java.lang.String str) {
        getProviderPackages_enforcePermission();
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        if (locationProviderManager == null) {
            return java.util.Collections.emptyList();
        }
        android.location.util.identity.CallerIdentity providerIdentity = locationProviderManager.getProviderIdentity();
        if (providerIdentity == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.singletonList(providerIdentity.getPackageName());
    }

    @android.annotation.EnforcePermission("android.permission.LOCATION_HARDWARE")
    public void setExtraLocationControllerPackage(java.lang.String str) {
        super.setExtraLocationControllerPackage_enforcePermission();
        synchronized (this.mLock) {
            this.mExtraLocationControllerPackage = str;
        }
    }

    public java.lang.String getExtraLocationControllerPackage() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = this.mExtraLocationControllerPackage;
        }
        return str;
    }

    @android.annotation.EnforcePermission("android.permission.LOCATION_HARDWARE")
    public void setExtraLocationControllerPackageEnabled(boolean z) {
        super.setExtraLocationControllerPackageEnabled_enforcePermission();
        synchronized (this.mLock) {
            this.mExtraLocationControllerPackageEnabled = z;
        }
    }

    public boolean isExtraLocationControllerPackageEnabled() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mExtraLocationControllerPackageEnabled && this.mExtraLocationControllerPackage != null;
            } finally {
            }
        }
        return z;
    }

    public void setLocationEnabledForUser(boolean z, int i) {
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, "setLocationEnabledForUser", null);
        this.mContext.enforceCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS", null);
        android.location.LocationManager.invalidateLocalLocationEnabledCaches();
        this.mInjector.getSettingsHelper().setLocationEnabled(z, handleIncomingUser);
    }

    public boolean isLocationEnabledForUser(int i) {
        return this.mInjector.getSettingsHelper().isLocationEnabled(android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, "isLocationEnabledForUser", null));
    }

    public void setAdasGnssLocationEnabledForUser(final boolean z, int i) {
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, "setAdasGnssLocationEnabledForUser", null);
        com.android.server.location.LocationPermissions.enforceCallingOrSelfBypassPermission(this.mContext);
        this.mInjector.getLocationSettings().updateUserSettings(handleIncomingUser, new java.util.function.Function() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.location.settings.LocationUserSettings lambda$setAdasGnssLocationEnabledForUser$6;
                lambda$setAdasGnssLocationEnabledForUser$6 = com.android.server.location.LocationManagerService.lambda$setAdasGnssLocationEnabledForUser$6(z, (com.android.server.location.settings.LocationUserSettings) obj);
                return lambda$setAdasGnssLocationEnabledForUser$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.location.settings.LocationUserSettings lambda$setAdasGnssLocationEnabledForUser$6(boolean z, com.android.server.location.settings.LocationUserSettings locationUserSettings) {
        return locationUserSettings.withAdasGnssLocationEnabled(z);
    }

    public boolean isAdasGnssLocationEnabledForUser(int i) {
        return this.mInjector.getLocationSettings().getUserSettings(android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, "isAdasGnssLocationEnabledForUser", null)).isAdasGnssLocationEnabled();
    }

    public boolean isProviderEnabledForUser(java.lang.String str, int i) {
        return this.mLocalService.isProviderEnabledForUser(str, i);
    }

    @android.annotation.EnforcePermission("android.permission.CONTROL_AUTOMOTIVE_GNSS")
    @android.annotation.RequiresPermission("android.permission.CONTROL_AUTOMOTIVE_GNSS")
    public void setAutomotiveGnssSuspended(boolean z) {
        super.setAutomotiveGnssSuspended_enforcePermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new java.lang.IllegalStateException("setAutomotiveGnssSuspended only allowed on automotive devices");
        }
        if (this.mGnssManagerService != null) {
            this.mGnssManagerService.setAutomotiveGnssSuspended(z);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CONTROL_AUTOMOTIVE_GNSS")
    @android.annotation.RequiresPermission("android.permission.CONTROL_AUTOMOTIVE_GNSS")
    public boolean isAutomotiveGnssSuspended() {
        super.isAutomotiveGnssSuspended_enforcePermission();
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new java.lang.IllegalStateException("isAutomotiveGnssSuspended only allowed on automotive devices");
        }
        if (this.mGnssManagerService != null) {
            return this.mGnssManagerService.isAutomotiveGnssSuspended();
        }
        return false;
    }

    public boolean isGeocodeAvailable() {
        return this.mGeocodeProvider != null;
    }

    public void reverseGeocode(android.location.provider.ReverseGeocodeRequest reverseGeocodeRequest, android.location.provider.IGeocodeCallback iGeocodeCallback) {
        com.android.internal.util.Preconditions.checkArgument(android.location.util.identity.CallerIdentity.fromBinder(this.mContext, reverseGeocodeRequest.getCallingPackage(), reverseGeocodeRequest.getCallingAttributionTag()).getUid() == reverseGeocodeRequest.getCallingUid());
        if (this.mGeocodeProvider != null) {
            this.mGeocodeProvider.reverseGeocode(reverseGeocodeRequest, iGeocodeCallback);
        } else {
            try {
                iGeocodeCallback.onError((java.lang.String) null);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void forwardGeocode(android.location.provider.ForwardGeocodeRequest forwardGeocodeRequest, android.location.provider.IGeocodeCallback iGeocodeCallback) {
        com.android.internal.util.Preconditions.checkArgument(android.location.util.identity.CallerIdentity.fromBinder(this.mContext, forwardGeocodeRequest.getCallingPackage(), forwardGeocodeRequest.getCallingAttributionTag()).getUid() == forwardGeocodeRequest.getCallingUid());
        if (this.mGeocodeProvider != null) {
            this.mGeocodeProvider.forwardGeocode(forwardGeocodeRequest, iGeocodeCallback);
        } else {
            try {
                iGeocodeCallback.onError((java.lang.String) null);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void addTestProvider(java.lang.String str, android.location.provider.ProviderProperties providerProperties, java.util.List<java.lang.String> list, java.lang.String str2, java.lang.String str3) {
        android.location.util.identity.CallerIdentity fromBinderUnsafe = android.location.util.identity.CallerIdentity.fromBinderUnsafe(str2, str3);
        if (!this.mInjector.getAppOpsHelper().noteOp(58, fromBinderUnsafe)) {
            return;
        }
        getOrAddLocationProviderManager(str).setMockProvider(new com.android.server.location.provider.MockLocationProvider(providerProperties, fromBinderUnsafe, new android.util.ArraySet(list)));
    }

    public void removeTestProvider(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (!this.mInjector.getAppOpsHelper().noteOp(58, android.location.util.identity.CallerIdentity.fromBinderUnsafe(str2, str3))) {
            return;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
                if (locationProviderManager == null) {
                    return;
                }
                locationProviderManager.setMockProvider(null);
                if (!locationProviderManager.hasProvider()) {
                    removeLocationProviderManager(locationProviderManager);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setTestProviderLocation(java.lang.String str, android.location.Location location, java.lang.String str2, java.lang.String str3) {
        if (!this.mInjector.getAppOpsHelper().noteOp(58, android.location.util.identity.CallerIdentity.fromBinderUnsafe(str2, str3))) {
            return;
        }
        com.android.internal.util.Preconditions.checkArgument(location.isComplete(), "incomplete location object, missing timestamp or accuracy?");
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        if (locationProviderManager == null) {
            throw new java.lang.IllegalArgumentException("provider doesn't exist: " + str);
        }
        locationProviderManager.setMockProviderLocation(location);
    }

    public void setTestProviderEnabled(java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3) {
        if (!this.mInjector.getAppOpsHelper().noteOp(58, android.location.util.identity.CallerIdentity.fromBinderUnsafe(str2, str3))) {
            return;
        }
        com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(str);
        if (locationProviderManager == null) {
            throw new java.lang.IllegalArgumentException("provider doesn't exist: " + str);
        }
        locationProviderManager.setMockProviderAllowed(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int handleShellCommand(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3, java.lang.String[] strArr) {
        return new com.android.server.location.LocationShellCommand(this.mContext, this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (!com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            return;
        }
        final android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
        if (strArr.length > 0) {
            com.android.server.location.provider.LocationProviderManager locationProviderManager = getLocationProviderManager(strArr[0]);
            if (locationProviderManager != null) {
                indentingPrintWriter.println("Provider:");
                indentingPrintWriter.increaseIndent();
                locationProviderManager.dump(fileDescriptor, indentingPrintWriter, strArr);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("Event Log:");
                indentingPrintWriter.increaseIndent();
                com.android.server.location.eventlog.LocationEventLog locationEventLog = com.android.server.location.eventlog.LocationEventLog.EVENT_LOG;
                java.util.Objects.requireNonNull(indentingPrintWriter);
                locationEventLog.iterate(new java.util.function.Consumer() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        indentingPrintWriter.println((java.lang.String) obj);
                    }
                }, locationProviderManager.getName());
                indentingPrintWriter.decreaseIndent();
                return;
            }
            if ("--gnssmetrics".equals(strArr[0])) {
                if (this.mGnssManagerService != null) {
                    this.mGnssManagerService.dump(fileDescriptor, indentingPrintWriter, strArr);
                    return;
                }
                return;
            }
        }
        indentingPrintWriter.println("Location Manager State:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("User Info:");
        indentingPrintWriter.increaseIndent();
        this.mInjector.getUserInfoHelper().dump(fileDescriptor, indentingPrintWriter, strArr);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Location Settings:");
        indentingPrintWriter.increaseIndent();
        this.mInjector.getSettingsHelper().dump(fileDescriptor, indentingPrintWriter, strArr);
        this.mInjector.getLocationSettings().dump(fileDescriptor, indentingPrintWriter, strArr);
        indentingPrintWriter.decreaseIndent();
        synchronized (this.mLock) {
            try {
                if (this.mExtraLocationControllerPackage != null) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("Location Controller Extra Package: ");
                    sb.append(this.mExtraLocationControllerPackage);
                    sb.append(this.mExtraLocationControllerPackageEnabled ? " [enabled]" : " [disabled]");
                    indentingPrintWriter.println(sb.toString());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.println("Location Providers:");
        indentingPrintWriter.increaseIndent();
        java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            it.next().dump(fileDescriptor, indentingPrintWriter, strArr);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Historical Aggregate Location Provider Data:");
        indentingPrintWriter.increaseIndent();
        android.util.ArrayMap<java.lang.String, android.util.ArrayMap<android.location.util.identity.CallerIdentity, com.android.server.location.eventlog.LocationEventLog.AggregateStats>> copyAggregateStats = com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.copyAggregateStats();
        for (int i = 0; i < copyAggregateStats.size(); i++) {
            indentingPrintWriter.print(copyAggregateStats.keyAt(i));
            indentingPrintWriter.println(":");
            indentingPrintWriter.increaseIndent();
            android.util.ArrayMap<android.location.util.identity.CallerIdentity, com.android.server.location.eventlog.LocationEventLog.AggregateStats> valueAt = copyAggregateStats.valueAt(i);
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                indentingPrintWriter.print(valueAt.keyAt(i2));
                indentingPrintWriter.print(": ");
                valueAt.valueAt(i2).updateTotals();
                indentingPrintWriter.println(valueAt.valueAt(i2));
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Historical Aggregate Gnss Measurement Provider Data:");
        indentingPrintWriter.increaseIndent();
        android.util.ArrayMap<android.location.util.identity.CallerIdentity, com.android.server.location.eventlog.LocationEventLog.GnssMeasurementAggregateStats> copyGnssMeasurementAggregateStats = com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.copyGnssMeasurementAggregateStats();
        for (int i3 = 0; i3 < copyGnssMeasurementAggregateStats.size(); i3++) {
            indentingPrintWriter.print(copyGnssMeasurementAggregateStats.keyAt(i3));
            indentingPrintWriter.print(": ");
            copyGnssMeasurementAggregateStats.valueAt(i3).updateTotals();
            indentingPrintWriter.println(copyGnssMeasurementAggregateStats.valueAt(i3));
        }
        indentingPrintWriter.decreaseIndent();
        if (this.mGnssManagerService != null) {
            indentingPrintWriter.println("GNSS Manager:");
            indentingPrintWriter.increaseIndent();
            this.mGnssManagerService.dump(fileDescriptor, indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.println("Geofence Manager:");
        indentingPrintWriter.increaseIndent();
        this.mGeofenceManager.dump(fileDescriptor, indentingPrintWriter, strArr);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Event Log:");
        indentingPrintWriter.increaseIndent();
        com.android.server.location.eventlog.LocationEventLog locationEventLog2 = com.android.server.location.eventlog.LocationEventLog.EVENT_LOG;
        java.util.Objects.requireNonNull(indentingPrintWriter);
        locationEventLog2.iterate(new java.util.function.Consumer() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                indentingPrintWriter.println((java.lang.String) obj);
            }
        });
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.location.provider.LocationProviderManager.StateChangedListener
    public void onStateChanged(java.lang.String str, com.android.server.location.provider.AbstractLocationProvider.State state, com.android.server.location.provider.AbstractLocationProvider.State state2) {
        if (!java.util.Objects.equals(state.identity, state2.identity)) {
            refreshAppOpsRestrictions(-1);
        }
        if (!state.extraAttributionTags.equals(state2.extraAttributionTags) || !java.util.Objects.equals(state.identity, state2.identity)) {
            synchronized (this.mLock) {
                try {
                    final android.location.LocationManagerInternal.LocationPackageTagsListener locationPackageTagsListener = this.mLocationTagsChangedListener;
                    if (locationPackageTagsListener != null) {
                        final int uid = state.identity != null ? state.identity.getUid() : -1;
                        final int uid2 = state2.identity != null ? state2.identity.getUid() : -1;
                        if (uid != -1) {
                            final android.os.PackageTagsList calculateAppOpsLocationSourceTags = calculateAppOpsLocationSourceTags(uid);
                            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    locationPackageTagsListener.onLocationPackageTagsChanged(uid, calculateAppOpsLocationSourceTags);
                                }
                            });
                        }
                        if (uid2 != -1 && uid2 != uid) {
                            final android.os.PackageTagsList calculateAppOpsLocationSourceTags2 = calculateAppOpsLocationSourceTags(uid2);
                            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.LocationManagerService$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    locationPackageTagsListener.onLocationPackageTagsChanged(uid2, calculateAppOpsLocationSourceTags2);
                                }
                            });
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void refreshAppOpsRestrictions(int i) {
        android.os.PackageTagsList packageTagsList;
        if (i == -1) {
            for (int i2 : this.mInjector.getUserInfoHelper().getRunningUserIds()) {
                refreshAppOpsRestrictions(i2);
            }
            return;
        }
        com.android.internal.util.Preconditions.checkArgument(i >= 0);
        boolean isLocationEnabled = this.mInjector.getSettingsHelper().isLocationEnabled(i);
        if (isLocationEnabled) {
            packageTagsList = null;
        } else {
            android.os.PackageTagsList.Builder builder = new android.os.PackageTagsList.Builder();
            java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
            while (it.hasNext()) {
                android.location.util.identity.CallerIdentity providerIdentity = it.next().getProviderIdentity();
                if (providerIdentity != null) {
                    builder.add(providerIdentity.getPackageName(), providerIdentity.getAttributionTag());
                }
            }
            builder.add(this.mInjector.getSettingsHelper().getIgnoreSettingsAllowlist());
            builder.add(this.mInjector.getSettingsHelper().getAdasAllowlist());
            packageTagsList = builder.build();
        }
        android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        java.util.Objects.requireNonNull(appOpsManager);
        appOpsManager.setUserRestrictionForUser(0, !isLocationEnabled, this, packageTagsList, i);
        appOpsManager.setUserRestrictionForUser(1, !isLocationEnabled, this, packageTagsList, i);
    }

    android.os.PackageTagsList calculateAppOpsLocationSourceTags(int i) {
        android.os.PackageTagsList.Builder builder = new android.os.PackageTagsList.Builder();
        java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = this.mProviderManagers.iterator();
        while (it.hasNext()) {
            com.android.server.location.provider.LocationProviderManager next = it.next();
            com.android.server.location.provider.AbstractLocationProvider.State state = next.getState();
            if (state.identity != null && state.identity.getUid() == i) {
                builder.add(state.identity.getPackageName(), state.extraAttributionTags);
                if (state.extraAttributionTags.isEmpty() || state.identity.getAttributionTag() != null) {
                    builder.add(state.identity.getPackageName(), state.identity.getAttributionTag());
                } else {
                    android.util.Log.e(TAG, next.getName() + " provider has specified a null attribution tag and a non-empty set of extra attribution tags - dropping the null attribution tag");
                }
            }
        }
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class LocalService extends android.location.LocationManagerInternal {
        LocalService() {
        }

        public boolean isProviderEnabledForUser(@android.annotation.NonNull java.lang.String str, int i) {
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, false, "isProviderEnabledForUser", null);
            com.android.server.location.provider.LocationProviderManager locationProviderManager = com.android.server.location.LocationManagerService.this.getLocationProviderManager(str);
            if (locationProviderManager == null) {
                return false;
            }
            return locationProviderManager.isEnabled(handleIncomingUser);
        }

        public void addProviderEnabledListener(java.lang.String str, android.location.LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
            com.android.server.location.provider.LocationProviderManager locationProviderManager = com.android.server.location.LocationManagerService.this.getLocationProviderManager(str);
            java.util.Objects.requireNonNull(locationProviderManager);
            locationProviderManager.addEnabledListener(providerEnabledListener);
        }

        public void removeProviderEnabledListener(java.lang.String str, android.location.LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
            com.android.server.location.provider.LocationProviderManager locationProviderManager = com.android.server.location.LocationManagerService.this.getLocationProviderManager(str);
            java.util.Objects.requireNonNull(locationProviderManager);
            locationProviderManager.removeEnabledListener(providerEnabledListener);
        }

        public boolean isProvider(@android.annotation.Nullable java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity) {
            java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = com.android.server.location.LocationManagerService.this.mProviderManagers.iterator();
            while (it.hasNext()) {
                com.android.server.location.provider.LocationProviderManager next = it.next();
                if (str == null || str.equals(next.getName())) {
                    if (callerIdentity.equals(next.getProviderIdentity()) && next.isVisibleToCaller()) {
                        return true;
                    }
                }
            }
            return false;
        }

        @android.annotation.Nullable
        public android.location.LocationTime getGnssTimeMillis() {
            android.location.Location lastLocationUnsafe;
            com.android.server.location.provider.LocationProviderManager locationProviderManager = com.android.server.location.LocationManagerService.this.getLocationProviderManager("gps");
            if (locationProviderManager == null || (lastLocationUnsafe = locationProviderManager.getLastLocationUnsafe(-1, 2, false, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME)) == null) {
                return null;
            }
            return new android.location.LocationTime(lastLocationUnsafe.getTime(), lastLocationUnsafe.getElapsedRealtimeNanos());
        }

        public void setLocationPackageTagsListener(@android.annotation.Nullable final android.location.LocationManagerInternal.LocationPackageTagsListener locationPackageTagsListener) {
            synchronized (com.android.server.location.LocationManagerService.this.mLock) {
                try {
                    com.android.server.location.LocationManagerService.this.mLocationTagsChangedListener = locationPackageTagsListener;
                    if (locationPackageTagsListener != null) {
                        android.util.ArraySet arraySet = new android.util.ArraySet(com.android.server.location.LocationManagerService.this.mProviderManagers.size());
                        java.util.Iterator<com.android.server.location.provider.LocationProviderManager> it = com.android.server.location.LocationManagerService.this.mProviderManagers.iterator();
                        while (it.hasNext()) {
                            android.location.util.identity.CallerIdentity providerIdentity = it.next().getProviderIdentity();
                            if (providerIdentity != null) {
                                arraySet.add(java.lang.Integer.valueOf(providerIdentity.getUid()));
                            }
                        }
                        java.util.Iterator it2 = arraySet.iterator();
                        while (it2.hasNext()) {
                            final int intValue = ((java.lang.Integer) it2.next()).intValue();
                            final android.os.PackageTagsList calculateAppOpsLocationSourceTags = com.android.server.location.LocationManagerService.this.calculateAppOpsLocationSourceTags(intValue);
                            if (!calculateAppOpsLocationSourceTags.isEmpty()) {
                                com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.LocationManagerService$LocalService$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        locationPackageTagsListener.onLocationPackageTagsChanged(intValue, calculateAppOpsLocationSourceTags);
                                    }
                                });
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static final class SystemInjector implements com.android.server.location.injector.Injector {
        private final com.android.server.location.injector.AlarmHelper mAlarmHelper;
        private final com.android.server.location.injector.SystemAppForegroundHelper mAppForegroundHelper;
        private final com.android.server.location.injector.SystemAppOpsHelper mAppOpsHelper;
        private final android.content.Context mContext;
        private final com.android.server.location.injector.SystemDeviceIdleHelper mDeviceIdleHelper;

        @com.android.internal.annotations.GuardedBy({"this"})
        @android.annotation.Nullable
        private com.android.server.location.injector.SystemEmergencyHelper mEmergencyCallHelper;
        private final com.android.server.location.injector.SystemLocationPermissionsHelper mLocationPermissionsHelper;
        private final com.android.server.location.injector.SystemLocationPowerSaveModeHelper mLocationPowerSaveModeHelper;
        private final com.android.server.location.settings.LocationSettings mLocationSettings;
        private final com.android.server.location.injector.PackageResetHelper mPackageResetHelper;
        private final com.android.server.location.injector.SystemScreenInteractiveHelper mScreenInteractiveHelper;
        private final com.android.server.location.injector.SystemSettingsHelper mSettingsHelper;

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean mSystemReady;
        private final com.android.server.location.injector.SystemUserInfoHelper mUserInfoHelper;
        private final com.android.server.location.injector.SystemDeviceStationaryHelper mDeviceStationaryHelper = new com.android.server.location.injector.SystemDeviceStationaryHelper();
        private final com.android.server.location.injector.LocationUsageLogger mLocationUsageLogger = new com.android.server.location.injector.LocationUsageLogger();

        SystemInjector(android.content.Context context, com.android.server.location.injector.SystemUserInfoHelper systemUserInfoHelper) {
            this.mContext = context;
            this.mUserInfoHelper = systemUserInfoHelper;
            this.mLocationSettings = new com.android.server.location.settings.LocationSettings(context);
            this.mAlarmHelper = new com.android.server.location.injector.SystemAlarmHelper(context);
            this.mAppOpsHelper = new com.android.server.location.injector.SystemAppOpsHelper(context);
            this.mLocationPermissionsHelper = new com.android.server.location.injector.SystemLocationPermissionsHelper(context, this.mAppOpsHelper);
            this.mSettingsHelper = new com.android.server.location.injector.SystemSettingsHelper(context);
            this.mAppForegroundHelper = new com.android.server.location.injector.SystemAppForegroundHelper(context);
            this.mLocationPowerSaveModeHelper = new com.android.server.location.injector.SystemLocationPowerSaveModeHelper(context);
            this.mScreenInteractiveHelper = new com.android.server.location.injector.SystemScreenInteractiveHelper(context);
            this.mDeviceIdleHelper = new com.android.server.location.injector.SystemDeviceIdleHelper(context);
            this.mPackageResetHelper = new com.android.server.location.injector.SystemPackageResetHelper(context);
        }

        synchronized void onSystemReady() {
            try {
                this.mUserInfoHelper.onSystemReady();
                this.mAppOpsHelper.onSystemReady();
                this.mLocationPermissionsHelper.onSystemReady();
                this.mSettingsHelper.onSystemReady();
                this.mAppForegroundHelper.onSystemReady();
                this.mLocationPowerSaveModeHelper.onSystemReady();
                this.mScreenInteractiveHelper.onSystemReady();
                this.mDeviceStationaryHelper.onSystemReady();
                this.mDeviceIdleHelper.onSystemReady();
                if (this.mEmergencyCallHelper != null) {
                    this.mEmergencyCallHelper.onSystemReady();
                }
                this.mSystemReady = true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.UserInfoHelper getUserInfoHelper() {
            return this.mUserInfoHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.settings.LocationSettings getLocationSettings() {
            return this.mLocationSettings;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.AlarmHelper getAlarmHelper() {
            return this.mAlarmHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.AppOpsHelper getAppOpsHelper() {
            return this.mAppOpsHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.LocationPermissionsHelper getLocationPermissionsHelper() {
            return this.mLocationPermissionsHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.SettingsHelper getSettingsHelper() {
            return this.mSettingsHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.AppForegroundHelper getAppForegroundHelper() {
            return this.mAppForegroundHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.LocationPowerSaveModeHelper getLocationPowerSaveModeHelper() {
            return this.mLocationPowerSaveModeHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.ScreenInteractiveHelper getScreenInteractiveHelper() {
            return this.mScreenInteractiveHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.DeviceStationaryHelper getDeviceStationaryHelper() {
            return this.mDeviceStationaryHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.DeviceIdleHelper getDeviceIdleHelper() {
            return this.mDeviceIdleHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public synchronized com.android.server.location.injector.EmergencyHelper getEmergencyHelper() {
            try {
                if (this.mEmergencyCallHelper == null) {
                    this.mEmergencyCallHelper = new com.android.server.location.injector.SystemEmergencyHelper(this.mContext);
                    if (this.mSystemReady) {
                        this.mEmergencyCallHelper.onSystemReady();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
            return this.mEmergencyCallHelper;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.LocationUsageLogger getLocationUsageLogger() {
            return this.mLocationUsageLogger;
        }

        @Override // com.android.server.location.injector.Injector
        public com.android.server.location.injector.PackageResetHelper getPackageResetHelper() {
            return this.mPackageResetHelper;
        }
    }
}
