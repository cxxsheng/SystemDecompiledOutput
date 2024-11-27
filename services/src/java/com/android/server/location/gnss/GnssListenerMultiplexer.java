package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public abstract class GnssListenerMultiplexer<TRequest, TListener extends android.os.IInterface, TMergedRegistration> extends com.android.server.location.listeners.ListenerMultiplexer<android.os.IBinder, TListener, com.android.server.location.gnss.GnssListenerMultiplexer<TRequest, TListener, TMergedRegistration>.GnssListenerRegistration, TMergedRegistration> {
    protected final com.android.server.location.injector.AppForegroundHelper mAppForegroundHelper;
    protected final android.location.LocationManagerInternal mLocationManagerInternal;
    protected final com.android.server.location.injector.LocationPermissionsHelper mLocationPermissionsHelper;
    private final com.android.server.location.injector.PackageResetHelper mPackageResetHelper;
    protected final com.android.server.location.injector.SettingsHelper mSettingsHelper;
    protected final com.android.server.location.injector.UserInfoHelper mUserInfoHelper;
    private final com.android.server.location.injector.UserInfoHelper.UserListener mUserChangedListener = new com.android.server.location.injector.UserInfoHelper.UserListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda0
        @Override // com.android.server.location.injector.UserInfoHelper.UserListener
        public final void onUserChanged(int i, int i2) {
            com.android.server.location.gnss.GnssListenerMultiplexer.this.onUserChanged(i, i2);
        }
    };
    private final android.location.LocationManagerInternal.ProviderEnabledListener mProviderEnabledChangedListener = new android.location.LocationManagerInternal.ProviderEnabledListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda1
        public final void onProviderEnabledChanged(java.lang.String str, int i, boolean z) {
            com.android.server.location.gnss.GnssListenerMultiplexer.this.onProviderEnabledChanged(str, i, z);
        }
    };
    private final com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener mBackgroundThrottlePackageWhitelistChangedListener = new com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda2
        @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
        public final void onSettingChanged() {
            com.android.server.location.gnss.GnssListenerMultiplexer.this.onBackgroundThrottlePackageAllowlistChanged();
        }
    };
    private final com.android.server.location.injector.SettingsHelper.UserSettingChangedListener mLocationPackageBlacklistChangedListener = new com.android.server.location.injector.SettingsHelper.UserSettingChangedListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda3
        @Override // com.android.server.location.injector.SettingsHelper.UserSettingChangedListener
        public final void onSettingChanged(int i) {
            com.android.server.location.gnss.GnssListenerMultiplexer.this.onLocationPackageDenylistChanged(i);
        }
    };
    private final com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener mLocationPermissionsListener = new com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer.1
        @Override // com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener
        public void onLocationPermissionsChanged(@android.annotation.Nullable java.lang.String str) {
            com.android.server.location.gnss.GnssListenerMultiplexer.this.onLocationPermissionsChanged(str);
        }

        @Override // com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener
        public void onLocationPermissionsChanged(int i) {
            com.android.server.location.gnss.GnssListenerMultiplexer.this.onLocationPermissionsChanged(i);
        }
    };
    private final com.android.server.location.injector.AppForegroundHelper.AppForegroundListener mAppForegroundChangedListener = new com.android.server.location.injector.AppForegroundHelper.AppForegroundListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda4
        @Override // com.android.server.location.injector.AppForegroundHelper.AppForegroundListener
        public final void onAppForegroundChanged(int i, boolean z) {
            com.android.server.location.gnss.GnssListenerMultiplexer.this.onAppForegroundChanged(i, z);
        }
    };
    private final com.android.server.location.injector.PackageResetHelper.Responder mPackageResetResponder = new com.android.server.location.injector.PackageResetHelper.Responder() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer.2
        @Override // com.android.server.location.injector.PackageResetHelper.Responder
        public void onPackageReset(java.lang.String str) {
            com.android.server.location.gnss.GnssListenerMultiplexer.this.onPackageReset(str);
        }

        @Override // com.android.server.location.injector.PackageResetHelper.Responder
        public boolean isResetableForPackage(java.lang.String str) {
            return com.android.server.location.gnss.GnssListenerMultiplexer.this.isResetableForPackage(str);
        }
    };

    protected class GnssListenerRegistration extends com.android.server.location.listeners.BinderListenerRegistration<android.os.IBinder, TListener> {
        private boolean mForeground;
        private final android.location.util.identity.CallerIdentity mIdentity;
        private boolean mPermitted;
        private final TRequest mRequest;

        protected GnssListenerRegistration(TRequest trequest, android.location.util.identity.CallerIdentity callerIdentity, TListener tlistener) {
            super(callerIdentity.isMyProcess() ? com.android.server.FgThread.getExecutor() : com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, tlistener);
            this.mRequest = trequest;
            this.mIdentity = callerIdentity;
        }

        public final TRequest getRequest() {
            return this.mRequest;
        }

        public final android.location.util.identity.CallerIdentity getIdentity() {
            return this.mIdentity;
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public java.lang.String getTag() {
            return com.android.server.location.gnss.GnssManagerService.TAG;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public com.android.server.location.gnss.GnssListenerMultiplexer<TRequest, TListener, TMergedRegistration> getOwner() {
            return com.android.server.location.gnss.GnssListenerMultiplexer.this;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.location.listeners.BinderListenerRegistration
        public android.os.IBinder getBinderFromKey(android.os.IBinder iBinder) {
            return iBinder;
        }

        public boolean isForeground() {
            return this.mForeground;
        }

        boolean isPermitted() {
            return this.mPermitted;
        }

        @Override // com.android.server.location.listeners.BinderListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration
        protected void onRegister() {
            super.onRegister();
            this.mPermitted = com.android.server.location.gnss.GnssListenerMultiplexer.this.mLocationPermissionsHelper.hasLocationPermissions(2, this.mIdentity);
            this.mForeground = com.android.server.location.gnss.GnssListenerMultiplexer.this.mAppForegroundHelper.isAppForeground(this.mIdentity.getUid());
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
            boolean hasLocationPermissions = com.android.server.location.gnss.GnssListenerMultiplexer.this.mLocationPermissionsHelper.hasLocationPermissions(2, this.mIdentity);
            if (hasLocationPermissions != this.mPermitted) {
                this.mPermitted = hasLocationPermissions;
                return true;
            }
            return false;
        }

        boolean onForegroundChanged(int i, boolean z) {
            if (this.mIdentity.getUid() == i && z != this.mForeground) {
                this.mForeground = z;
                return true;
            }
            return false;
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.mIdentity);
            android.util.ArraySet arraySet = new android.util.ArraySet(2);
            if (!this.mForeground) {
                arraySet.add("bg");
            }
            if (!this.mPermitted) {
                arraySet.add("na");
            }
            if (!arraySet.isEmpty()) {
                sb.append(" ");
                sb.append(arraySet);
            }
            if (this.mRequest != null) {
                sb.append(" ");
                sb.append(this.mRequest);
            }
            return sb.toString();
        }
    }

    protected GnssListenerMultiplexer(com.android.server.location.injector.Injector injector) {
        this.mUserInfoHelper = injector.getUserInfoHelper();
        this.mSettingsHelper = injector.getSettingsHelper();
        this.mLocationPermissionsHelper = injector.getLocationPermissionsHelper();
        this.mAppForegroundHelper = injector.getAppForegroundHelper();
        this.mPackageResetHelper = injector.getPackageResetHelper();
        android.location.LocationManagerInternal locationManagerInternal = (android.location.LocationManagerInternal) com.android.server.LocalServices.getService(android.location.LocationManagerInternal.class);
        java.util.Objects.requireNonNull(locationManagerInternal);
        this.mLocationManagerInternal = locationManagerInternal;
    }

    public boolean isSupported() {
        return true;
    }

    protected void addListener(android.location.util.identity.CallerIdentity callerIdentity, TListener tlistener) {
        addListener(null, callerIdentity, tlistener);
    }

    protected void addListener(TRequest trequest, android.location.util.identity.CallerIdentity callerIdentity, TListener tlistener) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            putRegistration(tlistener.asBinder(), createRegistration(trequest, callerIdentity, tlistener));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    protected com.android.server.location.gnss.GnssListenerMultiplexer<TRequest, TListener, TMergedRegistration>.GnssListenerRegistration createRegistration(TRequest trequest, android.location.util.identity.CallerIdentity callerIdentity, TListener tlistener) {
        return new com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration(trequest, callerIdentity, tlistener);
    }

    public void removeListener(TListener tlistener) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            removeRegistration((com.android.server.location.gnss.GnssListenerMultiplexer<TRequest, TListener, TMergedRegistration>) tlistener.asBinder());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public boolean isActive(com.android.server.location.gnss.GnssListenerMultiplexer<TRequest, TListener, TMergedRegistration>.GnssListenerRegistration gnssListenerRegistration) {
        if (!isSupported()) {
            return false;
        }
        android.location.util.identity.CallerIdentity identity = gnssListenerRegistration.getIdentity();
        if (gnssListenerRegistration.isPermitted()) {
            return (gnssListenerRegistration.isForeground() || isBackgroundRestrictionExempt(identity)) && isActive(identity);
        }
        return false;
    }

    private boolean isActive(android.location.util.identity.CallerIdentity callerIdentity) {
        return callerIdentity.isSystemServer() ? this.mLocationManagerInternal.isProviderEnabledForUser("gps", this.mUserInfoHelper.getCurrentUserId()) : this.mLocationManagerInternal.isProviderEnabledForUser("gps", callerIdentity.getUserId()) && this.mUserInfoHelper.isVisibleUserId(callerIdentity.getUserId()) && !this.mSettingsHelper.isLocationPackageBlacklisted(callerIdentity.getUserId(), callerIdentity.getPackageName());
    }

    private boolean isBackgroundRestrictionExempt(android.location.util.identity.CallerIdentity callerIdentity) {
        if (callerIdentity.getUid() == 1000 || this.mSettingsHelper.getBackgroundThrottlePackageWhitelist().contains(callerIdentity.getPackageName())) {
            return true;
        }
        return this.mLocationManagerInternal.isProvider((java.lang.String) null, callerIdentity);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected TMergedRegistration mergeRegistrations(java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<TRequest, TListener, TMergedRegistration>.GnssListenerRegistration> collection) {
        if (android.os.Build.IS_DEBUGGABLE) {
            java.util.Iterator<com.android.server.location.gnss.GnssListenerMultiplexer<TRequest, TListener, TMergedRegistration>.GnssListenerRegistration> it = collection.iterator();
            while (it.hasNext()) {
                com.android.internal.util.Preconditions.checkState(it.next().getRequest() == null);
            }
            return null;
        }
        return null;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void onRegister() {
        if (!isSupported()) {
            return;
        }
        this.mUserInfoHelper.addListener(this.mUserChangedListener);
        this.mLocationManagerInternal.addProviderEnabledListener("gps", this.mProviderEnabledChangedListener);
        this.mSettingsHelper.addOnBackgroundThrottlePackageWhitelistChangedListener(this.mBackgroundThrottlePackageWhitelistChangedListener);
        this.mSettingsHelper.addOnLocationPackageBlacklistChangedListener(this.mLocationPackageBlacklistChangedListener);
        this.mLocationPermissionsHelper.addListener(this.mLocationPermissionsListener);
        this.mAppForegroundHelper.addListener(this.mAppForegroundChangedListener);
        this.mPackageResetHelper.register(this.mPackageResetResponder);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void onUnregister() {
        if (!isSupported()) {
            return;
        }
        this.mUserInfoHelper.removeListener(this.mUserChangedListener);
        this.mLocationManagerInternal.removeProviderEnabledListener("gps", this.mProviderEnabledChangedListener);
        this.mSettingsHelper.removeOnBackgroundThrottlePackageWhitelistChangedListener(this.mBackgroundThrottlePackageWhitelistChangedListener);
        this.mSettingsHelper.removeOnLocationPackageBlacklistChangedListener(this.mLocationPackageBlacklistChangedListener);
        this.mLocationPermissionsHelper.removeListener(this.mLocationPermissionsListener);
        this.mAppForegroundHelper.removeListener(this.mAppForegroundChangedListener);
        this.mPackageResetHelper.unregister(this.mPackageResetResponder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserChanged(final int i, int i2) {
        if (i2 == 1 || i2 == 4) {
            updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$onUserChanged$0;
                    lambda$onUserChanged$0 = com.android.server.location.gnss.GnssListenerMultiplexer.lambda$onUserChanged$0(i, (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                    return lambda$onUserChanged$0;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onUserChanged$0(int i, com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.getIdentity().getUserId() == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProviderEnabledChanged(java.lang.String str, final int i, boolean z) {
        com.android.internal.util.Preconditions.checkState("gps".equals(str));
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onProviderEnabledChanged$1;
                lambda$onProviderEnabledChanged$1 = com.android.server.location.gnss.GnssListenerMultiplexer.lambda$onProviderEnabledChanged$1(i, (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onProviderEnabledChanged$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onProviderEnabledChanged$1(int i, com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.getIdentity().getUserId() == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onBackgroundThrottlePackageAllowlistChanged$2(com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackgroundThrottlePackageAllowlistChanged() {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onBackgroundThrottlePackageAllowlistChanged$2;
                lambda$onBackgroundThrottlePackageAllowlistChanged$2 = com.android.server.location.gnss.GnssListenerMultiplexer.lambda$onBackgroundThrottlePackageAllowlistChanged$2((com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onBackgroundThrottlePackageAllowlistChanged$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationPackageDenylistChanged$3(int i, com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.getIdentity().getUserId() == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocationPackageDenylistChanged(final int i) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onLocationPackageDenylistChanged$3;
                lambda$onLocationPackageDenylistChanged$3 = com.android.server.location.gnss.GnssListenerMultiplexer.lambda$onLocationPackageDenylistChanged$3(i, (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onLocationPackageDenylistChanged$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationPermissionsChanged$4(java.lang.String str, com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.onLocationPermissionsChanged(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocationPermissionsChanged(@android.annotation.Nullable final java.lang.String str) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onLocationPermissionsChanged$4;
                lambda$onLocationPermissionsChanged$4 = com.android.server.location.gnss.GnssListenerMultiplexer.lambda$onLocationPermissionsChanged$4(str, (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onLocationPermissionsChanged$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onLocationPermissionsChanged$5(int i, com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.onLocationPermissionsChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocationPermissionsChanged(final int i) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onLocationPermissionsChanged$5;
                lambda$onLocationPermissionsChanged$5 = com.android.server.location.gnss.GnssListenerMultiplexer.lambda$onLocationPermissionsChanged$5(i, (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onLocationPermissionsChanged$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onAppForegroundChanged$6(int i, boolean z, com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.onForegroundChanged(i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAppForegroundChanged(final int i, final boolean z) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onAppForegroundChanged$6;
                lambda$onAppForegroundChanged$6 = com.android.server.location.gnss.GnssListenerMultiplexer.lambda$onAppForegroundChanged$6(i, z, (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onAppForegroundChanged$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageReset(final java.lang.String str) {
        updateRegistrations(new java.util.function.Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onPackageReset$7;
                lambda$onPackageReset$7 = com.android.server.location.gnss.GnssListenerMultiplexer.lambda$onPackageReset$7(str, (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onPackageReset$7;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onPackageReset$7(java.lang.String str, com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        if (gnssListenerRegistration.getIdentity().getPackageName().equals(str)) {
            gnssListenerRegistration.remove();
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isResetableForPackage(final java.lang.String str) {
        return findRegistration(new java.util.function.Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isResetableForPackage$8;
                lambda$isResetableForPackage$8 = com.android.server.location.gnss.GnssListenerMultiplexer.lambda$isResetableForPackage$8(str, (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$isResetableForPackage$8;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isResetableForPackage$8(java.lang.String str, com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.getIdentity().getPackageName().equals(str);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected java.lang.String getServiceState() {
        if (!isSupported()) {
            return "unsupported";
        }
        return super.getServiceState();
    }
}
