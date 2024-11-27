package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class SystemSettingsHelper extends com.android.server.location.injector.SettingsHelper {
    private static final long DEFAULT_BACKGROUND_THROTTLE_INTERVAL_MS = 1800000;
    private static final long DEFAULT_BACKGROUND_THROTTLE_PROXIMITY_ALERT_INTERVAL_MS = 1800000;
    private static final float DEFAULT_COARSE_LOCATION_ACCURACY_M = 2000.0f;
    private static final java.lang.String LOCATION_PACKAGE_ALLOWLIST = "locationPackagePrefixWhitelist";
    private static final java.lang.String LOCATION_PACKAGE_DENYLIST = "locationPackagePrefixBlacklist";
    private final com.android.server.location.injector.SystemSettingsHelper.LongGlobalSetting mBackgroundThrottleIntervalMs;
    private final com.android.server.location.injector.SystemSettingsHelper.StringSetCachedGlobalSetting mBackgroundThrottlePackageWhitelist;
    private final android.content.Context mContext;
    private final com.android.server.location.injector.SystemSettingsHelper.BooleanGlobalSetting mGnssMeasurementFullTracking;
    private final com.android.server.location.injector.SystemSettingsHelper.IntegerSecureSetting mLocationMode;
    private final com.android.server.location.injector.SystemSettingsHelper.StringListCachedSecureSetting mLocationPackageBlacklist;
    private final com.android.server.location.injector.SystemSettingsHelper.StringListCachedSecureSetting mLocationPackageWhitelist;
    private final com.android.server.location.injector.SystemSettingsHelper.PackageTagsListSetting mAdasPackageAllowlist = new com.android.server.location.injector.SystemSettingsHelper.PackageTagsListSetting("adas_settings_allowlist", new java.util.function.Supplier() { // from class: com.android.server.location.injector.SystemSettingsHelper$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            android.util.ArrayMap lambda$new$1;
            lambda$new$1 = com.android.server.location.injector.SystemSettingsHelper.lambda$new$1();
            return lambda$new$1;
        }
    });
    private final com.android.server.location.injector.SystemSettingsHelper.PackageTagsListSetting mIgnoreSettingsPackageAllowlist = new com.android.server.location.injector.SystemSettingsHelper.PackageTagsListSetting("ignore_settings_allowlist", new java.util.function.Supplier() { // from class: com.android.server.location.injector.SystemSettingsHelper$$ExternalSyntheticLambda2
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            android.util.ArrayMap lambda$new$2;
            lambda$new$2 = com.android.server.location.injector.SystemSettingsHelper.lambda$new$2();
            return lambda$new$2;
        }
    });

    public SystemSettingsHelper(android.content.Context context) {
        this.mContext = context;
        this.mLocationMode = new com.android.server.location.injector.SystemSettingsHelper.IntegerSecureSetting(context, "location_mode", com.android.server.FgThread.getHandler());
        this.mBackgroundThrottleIntervalMs = new com.android.server.location.injector.SystemSettingsHelper.LongGlobalSetting(context, "location_background_throttle_interval_ms", com.android.server.FgThread.getHandler());
        this.mGnssMeasurementFullTracking = new com.android.server.location.injector.SystemSettingsHelper.BooleanGlobalSetting(context, "enable_gnss_raw_meas_full_tracking", com.android.server.FgThread.getHandler());
        this.mLocationPackageBlacklist = new com.android.server.location.injector.SystemSettingsHelper.StringListCachedSecureSetting(context, LOCATION_PACKAGE_DENYLIST, com.android.server.FgThread.getHandler());
        this.mLocationPackageWhitelist = new com.android.server.location.injector.SystemSettingsHelper.StringListCachedSecureSetting(context, LOCATION_PACKAGE_ALLOWLIST, com.android.server.FgThread.getHandler());
        this.mBackgroundThrottlePackageWhitelist = new com.android.server.location.injector.SystemSettingsHelper.StringSetCachedGlobalSetting(context, "location_background_throttle_package_whitelist", new java.util.function.Supplier() { // from class: com.android.server.location.injector.SystemSettingsHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.util.ArraySet lambda$new$0;
                lambda$new$0 = com.android.server.location.injector.SystemSettingsHelper.lambda$new$0();
                return lambda$new$0;
            }
        }, com.android.server.FgThread.getHandler());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.util.ArraySet lambda$new$0() {
        return com.android.server.SystemConfig.getInstance().getAllowUnthrottledLocation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.util.ArrayMap lambda$new$1() {
        return com.android.server.SystemConfig.getInstance().getAllowAdasLocationSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.util.ArrayMap lambda$new$2() {
        return com.android.server.SystemConfig.getInstance().getAllowIgnoreLocationSettings();
    }

    public void onSystemReady() {
        this.mLocationMode.register();
        this.mBackgroundThrottleIntervalMs.register();
        this.mLocationPackageBlacklist.register();
        this.mLocationPackageWhitelist.register();
        this.mBackgroundThrottlePackageWhitelist.register();
        this.mIgnoreSettingsPackageAllowlist.register();
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public boolean isLocationEnabled(int i) {
        return this.mLocationMode.getValueForUser(0, i) != 0;
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void setLocationEnabled(boolean z, int i) {
        int i2;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            if (z) {
                i2 = 3;
            } else {
                i2 = 0;
            }
            android.provider.Settings.Secure.putIntForUser(contentResolver, "location_mode", i2, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addOnLocationEnabledChangedListener(com.android.server.location.injector.SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
        this.mLocationMode.addListener(userSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeOnLocationEnabledChangedListener(com.android.server.location.injector.SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
        this.mLocationMode.removeListener(userSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public long getBackgroundThrottleIntervalMs() {
        return this.mBackgroundThrottleIntervalMs.getValue(1800000L);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addOnBackgroundThrottleIntervalChangedListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mBackgroundThrottleIntervalMs.addListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeOnBackgroundThrottleIntervalChangedListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mBackgroundThrottleIntervalMs.removeListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public boolean isLocationPackageBlacklisted(int i, java.lang.String str) {
        java.util.List<java.lang.String> valueForUser = this.mLocationPackageBlacklist.getValueForUser(i);
        if (valueForUser.isEmpty()) {
            return false;
        }
        java.util.Iterator<java.lang.String> it = this.mLocationPackageWhitelist.getValueForUser(i).iterator();
        while (it.hasNext()) {
            if (str.startsWith(it.next())) {
                return false;
            }
        }
        java.util.Iterator<java.lang.String> it2 = valueForUser.iterator();
        while (it2.hasNext()) {
            if (str.startsWith(it2.next())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addOnLocationPackageBlacklistChangedListener(com.android.server.location.injector.SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
        this.mLocationPackageBlacklist.addListener(userSettingChangedListener);
        this.mLocationPackageWhitelist.addListener(userSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeOnLocationPackageBlacklistChangedListener(com.android.server.location.injector.SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
        this.mLocationPackageBlacklist.removeListener(userSettingChangedListener);
        this.mLocationPackageWhitelist.removeListener(userSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public java.util.Set<java.lang.String> getBackgroundThrottlePackageWhitelist() {
        return this.mBackgroundThrottlePackageWhitelist.getValue();
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addOnBackgroundThrottlePackageWhitelistChangedListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mBackgroundThrottlePackageWhitelist.addListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeOnBackgroundThrottlePackageWhitelistChangedListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mBackgroundThrottlePackageWhitelist.removeListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public boolean isGnssMeasurementsFullTrackingEnabled() {
        return this.mGnssMeasurementFullTracking.getValue(false);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addOnGnssMeasurementsFullTrackingEnabledChangedListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mGnssMeasurementFullTracking.addListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeOnGnssMeasurementsFullTrackingEnabledChangedListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mGnssMeasurementFullTracking.removeListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public android.os.PackageTagsList getAdasAllowlist() {
        return this.mAdasPackageAllowlist.getValue();
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addAdasAllowlistChangedListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mAdasPackageAllowlist.addListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeAdasAllowlistChangedListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mAdasPackageAllowlist.removeListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public android.os.PackageTagsList getIgnoreSettingsAllowlist() {
        return this.mIgnoreSettingsPackageAllowlist.getValue();
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addIgnoreSettingsAllowlistChangedListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mIgnoreSettingsPackageAllowlist.addListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeIgnoreSettingsAllowlistChangedListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mIgnoreSettingsPackageAllowlist.removeListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public long getBackgroundThrottleProximityAlertIntervalMs() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), "location_background_throttle_proximity_alert_interval_ms", 1800000L);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public float getCoarseLocationAccuracyM() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        try {
            return android.provider.Settings.Secure.getFloatForUser(contentResolver, "locationCoarseAccuracy", DEFAULT_COARSE_LOCATION_ACCURACY_M, contentResolver.getUserId());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void dump(java.io.FileDescriptor fileDescriptor, android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr) {
        try {
            int[] runningUserIds = android.app.ActivityManager.getService().getRunningUserIds();
            indentingPrintWriter.print("Location Setting: ");
            indentingPrintWriter.increaseIndent();
            if (runningUserIds.length > 1) {
                indentingPrintWriter.println();
                for (int i : runningUserIds) {
                    indentingPrintWriter.print("[u");
                    indentingPrintWriter.print(i);
                    indentingPrintWriter.print("] ");
                    indentingPrintWriter.println(isLocationEnabled(i));
                }
            } else {
                indentingPrintWriter.println(isLocationEnabled(runningUserIds[0]));
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Location Allow/Deny Packages:");
            indentingPrintWriter.increaseIndent();
            if (runningUserIds.length > 1) {
                for (int i2 : runningUserIds) {
                    java.util.List<java.lang.String> valueForUser = this.mLocationPackageBlacklist.getValueForUser(i2);
                    if (!valueForUser.isEmpty()) {
                        indentingPrintWriter.print("user ");
                        indentingPrintWriter.print(i2);
                        indentingPrintWriter.println(":");
                        indentingPrintWriter.increaseIndent();
                        for (java.lang.String str : valueForUser) {
                            indentingPrintWriter.print("[deny] ");
                            indentingPrintWriter.println(str);
                        }
                        for (java.lang.String str2 : this.mLocationPackageWhitelist.getValueForUser(i2)) {
                            indentingPrintWriter.print("[allow] ");
                            indentingPrintWriter.println(str2);
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                }
            } else {
                for (java.lang.String str3 : this.mLocationPackageBlacklist.getValueForUser(runningUserIds[0])) {
                    indentingPrintWriter.print("[deny] ");
                    indentingPrintWriter.println(str3);
                }
                for (java.lang.String str4 : this.mLocationPackageWhitelist.getValueForUser(runningUserIds[0])) {
                    indentingPrintWriter.print("[allow] ");
                    indentingPrintWriter.println(str4);
                }
            }
            indentingPrintWriter.decreaseIndent();
            java.util.Set<java.lang.String> value = this.mBackgroundThrottlePackageWhitelist.getValue();
            if (!value.isEmpty()) {
                indentingPrintWriter.println("Throttling Allow Packages:");
                indentingPrintWriter.increaseIndent();
                java.util.Iterator<java.lang.String> it = value.iterator();
                while (it.hasNext()) {
                    indentingPrintWriter.println(it.next());
                }
                indentingPrintWriter.decreaseIndent();
            }
            android.os.PackageTagsList value2 = this.mIgnoreSettingsPackageAllowlist.getValue();
            if (!value2.isEmpty()) {
                indentingPrintWriter.println("Emergency Bypass Allow Packages:");
                indentingPrintWriter.increaseIndent();
                value2.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            }
            android.os.PackageTagsList value3 = this.mAdasPackageAllowlist.getValue();
            if (!value3.isEmpty()) {
                indentingPrintWriter.println("ADAS Bypass Allow Packages:");
                indentingPrintWriter.increaseIndent();
                value3.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static abstract class ObservingSetting extends android.database.ContentObserver {
        private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.injector.SettingsHelper.UserSettingChangedListener> mListeners;

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean mRegistered;

        ObservingSetting(android.os.Handler handler) {
            super(handler);
            this.mListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        }

        protected synchronized boolean isRegistered() {
            return this.mRegistered;
        }

        protected synchronized void register(android.content.Context context, android.net.Uri uri) {
            if (this.mRegistered) {
                return;
            }
            context.getContentResolver().registerContentObserver(uri, false, this, -1);
            this.mRegistered = true;
        }

        public void addListener(com.android.server.location.injector.SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
            this.mListeners.add(userSettingChangedListener);
        }

        public void removeListener(com.android.server.location.injector.SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
            this.mListeners.remove(userSettingChangedListener);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            if (com.android.server.location.LocationManagerService.D) {
                android.util.Log.d(com.android.server.location.LocationManagerService.TAG, "location setting changed [u" + i + "]: " + uri);
            }
            java.util.Iterator<com.android.server.location.injector.SettingsHelper.UserSettingChangedListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onSettingChanged(i);
            }
        }
    }

    private static class IntegerSecureSetting extends com.android.server.location.injector.SystemSettingsHelper.ObservingSetting {
        private final android.content.Context mContext;
        private final java.lang.String mSettingName;

        IntegerSecureSetting(android.content.Context context, java.lang.String str, android.os.Handler handler) {
            super(handler);
            this.mContext = context;
            this.mSettingName = str;
        }

        void register() {
            register(this.mContext, android.provider.Settings.Secure.getUriFor(this.mSettingName));
        }

        public int getValueForUser(int i, int i2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), this.mSettingName, i, i2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private static class StringListCachedSecureSetting extends com.android.server.location.injector.SystemSettingsHelper.ObservingSetting {

        @com.android.internal.annotations.GuardedBy({"this"})
        private int mCachedUserId;

        @com.android.internal.annotations.GuardedBy({"this"})
        private java.util.List<java.lang.String> mCachedValue;
        private final android.content.Context mContext;
        private final java.lang.String mSettingName;

        StringListCachedSecureSetting(android.content.Context context, java.lang.String str, android.os.Handler handler) {
            super(handler);
            this.mContext = context;
            this.mSettingName = str;
            this.mCachedUserId = com.android.server.am.ProcessList.INVALID_ADJ;
        }

        public void register() {
            register(this.mContext, android.provider.Settings.Secure.getUriFor(this.mSettingName));
        }

        public synchronized java.util.List<java.lang.String> getValueForUser(int i) {
            java.util.List<java.lang.String> list;
            long clearCallingIdentity;
            java.util.List<java.lang.String> asList;
            try {
                com.android.internal.util.Preconditions.checkArgument(i != -10000);
                list = this.mCachedValue;
                if (i != this.mCachedUserId) {
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), this.mSettingName, i);
                    if (android.text.TextUtils.isEmpty(stringForUser)) {
                        asList = java.util.Collections.emptyList();
                    } else {
                        asList = java.util.Arrays.asList(stringForUser.split(","));
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (isRegistered()) {
                        this.mCachedUserId = i;
                        this.mCachedValue = asList;
                    }
                    list = asList;
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            } finally {
            }
            return list;
        }

        public synchronized void invalidateForUser(int i) {
            if (this.mCachedUserId == i) {
                this.mCachedUserId = com.android.server.am.ProcessList.INVALID_ADJ;
                this.mCachedValue = null;
            }
        }

        @Override // com.android.server.location.injector.SystemSettingsHelper.ObservingSetting, android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            invalidateForUser(i);
            super.onChange(z, uri, i);
        }
    }

    private static class BooleanGlobalSetting extends com.android.server.location.injector.SystemSettingsHelper.ObservingSetting {
        private final android.content.Context mContext;
        private final java.lang.String mSettingName;

        BooleanGlobalSetting(android.content.Context context, java.lang.String str, android.os.Handler handler) {
            super(handler);
            this.mContext = context;
            this.mSettingName = str;
        }

        public void register() {
            register(this.mContext, android.provider.Settings.Global.getUriFor(this.mSettingName));
        }

        public boolean getValue(boolean z) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), this.mSettingName, z ? 1 : 0) != 0;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private static class LongGlobalSetting extends com.android.server.location.injector.SystemSettingsHelper.ObservingSetting {
        private final android.content.Context mContext;
        private final java.lang.String mSettingName;

        LongGlobalSetting(android.content.Context context, java.lang.String str, android.os.Handler handler) {
            super(handler);
            this.mContext = context;
            this.mSettingName = str;
        }

        public void register() {
            register(this.mContext, android.provider.Settings.Global.getUriFor(this.mSettingName));
        }

        public long getValue(long j) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), this.mSettingName, j);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private static class StringSetCachedGlobalSetting extends com.android.server.location.injector.SystemSettingsHelper.ObservingSetting {
        private final java.util.function.Supplier<android.util.ArraySet<java.lang.String>> mBaseValuesSupplier;

        @com.android.internal.annotations.GuardedBy({"this"})
        private android.util.ArraySet<java.lang.String> mCachedValue;
        private final android.content.Context mContext;
        private final java.lang.String mSettingName;

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean mValid;

        StringSetCachedGlobalSetting(android.content.Context context, java.lang.String str, java.util.function.Supplier<android.util.ArraySet<java.lang.String>> supplier, android.os.Handler handler) {
            super(handler);
            this.mContext = context;
            this.mSettingName = str;
            this.mBaseValuesSupplier = supplier;
            this.mValid = false;
        }

        public void register() {
            register(this.mContext, android.provider.Settings.Global.getUriFor(this.mSettingName));
        }

        public synchronized java.util.Set<java.lang.String> getValue() {
            android.util.ArraySet<java.lang.String> arraySet;
            try {
                arraySet = this.mCachedValue;
                if (!this.mValid) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        android.util.ArraySet<java.lang.String> arraySet2 = new android.util.ArraySet<>(this.mBaseValuesSupplier.get());
                        java.lang.String string = android.provider.Settings.Global.getString(this.mContext.getContentResolver(), this.mSettingName);
                        if (!android.text.TextUtils.isEmpty(string)) {
                            arraySet2.addAll(java.util.Arrays.asList(string.split(",")));
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (isRegistered()) {
                            this.mValid = true;
                            this.mCachedValue = arraySet2;
                        }
                        arraySet = arraySet2;
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
            return arraySet;
        }

        public synchronized void invalidate() {
            this.mValid = false;
            this.mCachedValue = null;
        }

        @Override // com.android.server.location.injector.SystemSettingsHelper.ObservingSetting, android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            invalidate();
            super.onChange(z, uri, i);
        }
    }

    private static class DeviceConfigSetting implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener> mListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        protected final java.lang.String mName;

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean mRegistered;

        DeviceConfigSetting(java.lang.String str) {
            this.mName = str;
        }

        protected synchronized boolean isRegistered() {
            return this.mRegistered;
        }

        protected synchronized void register() {
            if (this.mRegistered) {
                return;
            }
            android.provider.DeviceConfig.addOnPropertiesChangedListener("location", com.android.server.FgThread.getExecutor(), this);
            this.mRegistered = true;
        }

        public void addListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
            this.mListeners.add(globalSettingChangedListener);
        }

        public void removeListener(com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
            this.mListeners.remove(globalSettingChangedListener);
        }

        public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            if (!properties.getKeyset().contains(this.mName)) {
                return;
            }
            onPropertiesChanged();
        }

        public void onPropertiesChanged() {
            if (com.android.server.location.LocationManagerService.D) {
                android.util.Log.d(com.android.server.location.LocationManagerService.TAG, "location device config setting changed: " + this.mName);
            }
            java.util.Iterator<com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onSettingChanged(-1);
            }
        }
    }

    private static class PackageTagsListSetting extends com.android.server.location.injector.SystemSettingsHelper.DeviceConfigSetting {
        private final java.util.function.Supplier<android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>>> mBaseValuesSupplier;

        @com.android.internal.annotations.GuardedBy({"this"})
        private android.os.PackageTagsList mCachedValue;

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean mValid;

        PackageTagsListSetting(java.lang.String str, java.util.function.Supplier<android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>>> supplier) {
            super(str);
            this.mBaseValuesSupplier = supplier;
        }

        public synchronized android.os.PackageTagsList getValue() {
            android.os.PackageTagsList packageTagsList;
            try {
                packageTagsList = this.mCachedValue;
                if (!this.mValid) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        android.os.PackageTagsList.Builder add = new android.os.PackageTagsList.Builder().add(this.mBaseValuesSupplier.get());
                        java.lang.String property = android.provider.DeviceConfig.getProperty("location", this.mName);
                        if (!android.text.TextUtils.isEmpty(property)) {
                            for (java.lang.String str : property.split(",")) {
                                if (!android.text.TextUtils.isEmpty(str)) {
                                    java.lang.String[] split = str.split(";");
                                    java.lang.String str2 = split[0];
                                    if (split.length == 1) {
                                        add.add(str2);
                                    } else {
                                        for (int i = 1; i < split.length; i++) {
                                            java.lang.String str3 = split[i];
                                            if ("null".equals(str3)) {
                                                str3 = null;
                                            }
                                            if (com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER.equals(str3)) {
                                                add.add(str2);
                                            } else {
                                                add.add(str2, str3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        android.os.PackageTagsList build = add.build();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (isRegistered()) {
                            this.mValid = true;
                            this.mCachedValue = build;
                        }
                        packageTagsList = build;
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
            return packageTagsList;
        }

        public synchronized void invalidate() {
            this.mValid = false;
            this.mCachedValue = null;
        }

        @Override // com.android.server.location.injector.SystemSettingsHelper.DeviceConfigSetting
        public void onPropertiesChanged() {
            invalidate();
            super.onPropertiesChanged();
        }
    }
}
