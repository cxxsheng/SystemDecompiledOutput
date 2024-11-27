package com.android.server.timedetector;

/* loaded from: classes2.dex */
final class ServiceConfigAccessorImpl implements com.android.server.timedetector.ServiceConfigAccessor {
    private static final int[] DEFAULT_AUTOMATIC_TIME_ORIGIN_PRIORITIES = {1, 3};
    private static final java.util.Set<java.lang.String> SERVER_FLAGS_KEYS_TO_WATCH = java.util.Set.of(com.android.server.timedetector.ServerFlags.KEY_TIME_DETECTOR_LOWER_BOUND_MILLIS_OVERRIDE, com.android.server.timedetector.ServerFlags.KEY_TIME_DETECTOR_ORIGIN_PRIORITIES_OVERRIDE);
    private static final java.lang.Object SLOCK = new java.lang.Object();
    private static final int SYSTEM_CLOCK_CONFIRMATION_THRESHOLD_MILLIS = 1000;

    @com.android.internal.annotations.GuardedBy({"SLOCK"})
    @android.annotation.Nullable
    private static com.android.server.timedetector.ServiceConfigAccessor sInstance;

    @android.annotation.NonNull
    private final com.android.server.timedetector.ServiceConfigAccessorImpl.ConfigOriginPrioritiesSupplier mConfigOriginPrioritiesSupplier;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.List<com.android.server.timezonedetector.StateChangeListener> mConfigurationInternalListeners = new java.util.ArrayList();

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final android.content.ContentResolver mCr;

    @android.annotation.NonNull
    private final com.android.server.timedetector.ServerFlags mServerFlags;

    @android.annotation.NonNull
    private final com.android.server.timedetector.ServiceConfigAccessorImpl.ServerFlagsOriginPrioritiesSupplier mServerFlagsOriginPrioritiesSupplier;
    private final int mSystemClockUpdateThresholdMillis;

    @android.annotation.NonNull
    private final android.os.UserManager mUserManager;

    private ServiceConfigAccessorImpl(@android.annotation.NonNull android.content.Context context) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        this.mCr = context.getContentResolver();
        this.mUserManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        this.mServerFlags = com.android.server.timedetector.ServerFlags.getInstance(this.mContext);
        this.mConfigOriginPrioritiesSupplier = new com.android.server.timedetector.ServiceConfigAccessorImpl.ConfigOriginPrioritiesSupplier(context);
        this.mServerFlagsOriginPrioritiesSupplier = new com.android.server.timedetector.ServiceConfigAccessorImpl.ServerFlagsOriginPrioritiesSupplier(this.mServerFlags);
        this.mSystemClockUpdateThresholdMillis = context.getResources().getInteger(android.R.integer.config_sidefpsSkipWaitForPowerVendorAcquireMessage);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        this.mContext.registerReceiverForAllUsers(new android.content.BroadcastReceiver() { // from class: com.android.server.timedetector.ServiceConfigAccessorImpl.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.timedetector.ServiceConfigAccessorImpl.this.handleConfigurationInternalChangeOnMainThread();
            }
        }, intentFilter, null, null);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("auto_time"), true, new android.database.ContentObserver(this.mContext.getMainThreadHandler()) { // from class: com.android.server.timedetector.ServiceConfigAccessorImpl.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.timedetector.ServiceConfigAccessorImpl.this.handleConfigurationInternalChangeOnMainThread();
            }
        });
        this.mServerFlags.addListener(new com.android.server.timezonedetector.StateChangeListener() { // from class: com.android.server.timedetector.ServiceConfigAccessorImpl$$ExternalSyntheticLambda0
            @Override // com.android.server.timezonedetector.StateChangeListener
            public final void onChange() {
                com.android.server.timedetector.ServiceConfigAccessorImpl.this.handleConfigurationInternalChangeOnMainThread();
            }
        }, SERVER_FLAGS_KEYS_TO_WATCH);
    }

    static com.android.server.timedetector.ServiceConfigAccessor getInstance(android.content.Context context) {
        com.android.server.timedetector.ServiceConfigAccessor serviceConfigAccessor;
        synchronized (SLOCK) {
            try {
                if (sInstance == null) {
                    sInstance = new com.android.server.timedetector.ServiceConfigAccessorImpl(context);
                }
                serviceConfigAccessor = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return serviceConfigAccessor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleConfigurationInternalChangeOnMainThread() {
        java.util.ArrayList arrayList;
        synchronized (this) {
            arrayList = new java.util.ArrayList(this.mConfigurationInternalListeners);
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((com.android.server.timezonedetector.StateChangeListener) it.next()).onChange();
        }
    }

    @Override // com.android.server.timedetector.ServiceConfigAccessor
    public synchronized void addConfigurationInternalChangeListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener) {
        java.util.List<com.android.server.timezonedetector.StateChangeListener> list = this.mConfigurationInternalListeners;
        java.util.Objects.requireNonNull(stateChangeListener);
        list.add(stateChangeListener);
    }

    @Override // com.android.server.timedetector.ServiceConfigAccessor
    public synchronized void removeConfigurationInternalChangeListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener) {
        java.util.List<com.android.server.timezonedetector.StateChangeListener> list = this.mConfigurationInternalListeners;
        java.util.Objects.requireNonNull(stateChangeListener);
        list.remove(stateChangeListener);
    }

    @Override // com.android.server.timedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public synchronized com.android.server.timedetector.ConfigurationInternal getCurrentUserConfigurationInternal() {
        return getConfigurationInternal(((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getCurrentUserId());
    }

    @Override // com.android.server.timedetector.ServiceConfigAccessor
    public synchronized boolean updateConfiguration(int i, @android.annotation.NonNull android.app.time.TimeConfiguration timeConfiguration, boolean z) {
        java.util.Objects.requireNonNull(timeConfiguration);
        android.app.time.TimeCapabilitiesAndConfig createCapabilitiesAndConfig = getCurrentUserConfigurationInternal().createCapabilitiesAndConfig(z);
        android.app.time.TimeConfiguration tryApplyConfigChanges = createCapabilitiesAndConfig.getCapabilities().tryApplyConfigChanges(createCapabilitiesAndConfig.getConfiguration(), timeConfiguration);
        if (tryApplyConfigChanges == null) {
            return false;
        }
        storeConfiguration(i, tryApplyConfigChanges);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void storeConfiguration(int i, @android.annotation.NonNull android.app.time.TimeConfiguration timeConfiguration) {
        java.util.Objects.requireNonNull(timeConfiguration);
        if (isAutoDetectionSupported()) {
            setAutoDetectionEnabledIfRequired(timeConfiguration.isAutoDetectionEnabled());
        }
    }

    @Override // com.android.server.timedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public synchronized com.android.server.timedetector.ConfigurationInternal getConfigurationInternal(int i) {
        android.app.timedetector.TimeDetectorHelper timeDetectorHelper;
        timeDetectorHelper = android.app.timedetector.TimeDetectorHelper.INSTANCE;
        return new com.android.server.timedetector.ConfigurationInternal.Builder(i).setUserConfigAllowed(isUserConfigAllowed(i)).setAutoDetectionSupported(isAutoDetectionSupported()).setAutoDetectionEnabledSetting(getAutoDetectionEnabledSetting()).setSystemClockUpdateThresholdMillis(getSystemClockUpdateThresholdMillis()).setSystemClockConfidenceThresholdMillis(getSystemClockConfidenceUpgradeThresholdMillis()).setAutoSuggestionLowerBound(getAutoSuggestionLowerBound()).setManualSuggestionLowerBound(timeDetectorHelper.getManualSuggestionLowerBound()).setSuggestionUpperBound(timeDetectorHelper.getSuggestionUpperBound()).setOriginPriorities(getOriginPriorities()).build();
    }

    private void setAutoDetectionEnabledIfRequired(boolean z) {
        if (getAutoDetectionEnabledSetting() != z) {
            android.provider.Settings.Global.putInt(this.mCr, "auto_time", z ? 1 : 0);
        }
    }

    private boolean isUserConfigAllowed(int i) {
        return !this.mUserManager.hasUserRestriction("no_config_date_time", android.os.UserHandle.of(i));
    }

    private boolean getAutoDetectionEnabledSetting() {
        return android.provider.Settings.Global.getInt(this.mCr, "auto_time", 1) > 0;
    }

    private boolean isAutoDetectionSupported() {
        for (int i : getOriginPriorities()) {
            if (i == 3 || i == 5 || i == 4) {
                return true;
            }
            if (i == 1 && this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony")) {
                return true;
            }
        }
        return false;
    }

    private int getSystemClockUpdateThresholdMillis() {
        return this.mSystemClockUpdateThresholdMillis;
    }

    private int getSystemClockConfidenceUpgradeThresholdMillis() {
        return 1000;
    }

    @android.annotation.NonNull
    private java.time.Instant getAutoSuggestionLowerBound() {
        return this.mServerFlags.getOptionalInstant(com.android.server.timedetector.ServerFlags.KEY_TIME_DETECTOR_LOWER_BOUND_MILLIS_OVERRIDE).orElse(android.app.timedetector.TimeDetectorHelper.INSTANCE.getAutoSuggestionLowerBoundDefault());
    }

    @android.annotation.NonNull
    private int[] getOriginPriorities() {
        int[] iArr = this.mServerFlagsOriginPrioritiesSupplier.get();
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = this.mConfigOriginPrioritiesSupplier.get();
        if (iArr2 != null) {
            return iArr2;
        }
        return DEFAULT_AUTOMATIC_TIME_ORIGIN_PRIORITIES;
    }

    private static abstract class BaseOriginPrioritiesSupplier implements java.util.function.Supplier<int[]> {

        @com.android.internal.annotations.GuardedBy({"this"})
        @android.annotation.Nullable
        private int[] mLastPriorityInts;

        @com.android.internal.annotations.GuardedBy({"this"})
        @android.annotation.Nullable
        private java.lang.String[] mLastPriorityStrings;

        private BaseOriginPrioritiesSupplier() {
        }

        @android.annotation.Nullable
        protected abstract java.lang.String[] lookupPriorityStrings();

        @Override // java.util.function.Supplier
        @android.annotation.Nullable
        public int[] get() {
            java.lang.String[] lookupPriorityStrings = lookupPriorityStrings();
            synchronized (this) {
                try {
                    if (java.util.Arrays.equals(this.mLastPriorityStrings, lookupPriorityStrings)) {
                        return this.mLastPriorityInts;
                    }
                    int[] iArr = null;
                    if (lookupPriorityStrings != null) {
                        int length = lookupPriorityStrings.length;
                        int[] iArr2 = new int[length];
                        for (int i = 0; i < length; i++) {
                            try {
                                java.lang.String str = lookupPriorityStrings[i];
                                com.android.internal.util.Preconditions.checkArgument(str != null);
                                iArr2[i] = com.android.server.timedetector.TimeDetectorStrategy.stringToOrigin(str.trim());
                            } catch (java.lang.IllegalArgumentException e) {
                            }
                        }
                        iArr = iArr2;
                    }
                    this.mLastPriorityStrings = lookupPriorityStrings;
                    this.mLastPriorityInts = iArr;
                    return iArr;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static class ConfigOriginPrioritiesSupplier extends com.android.server.timedetector.ServiceConfigAccessorImpl.BaseOriginPrioritiesSupplier {

        @android.annotation.NonNull
        private final android.content.Context mContext;

        private ConfigOriginPrioritiesSupplier(android.content.Context context) {
            super();
            java.util.Objects.requireNonNull(context);
            this.mContext = context;
        }

        @Override // com.android.server.timedetector.ServiceConfigAccessorImpl.BaseOriginPrioritiesSupplier
        @android.annotation.Nullable
        protected java.lang.String[] lookupPriorityStrings() {
            return this.mContext.getResources().getStringArray(android.R.array.config_autoTimeSourcesPriority);
        }
    }

    private static class ServerFlagsOriginPrioritiesSupplier extends com.android.server.timedetector.ServiceConfigAccessorImpl.BaseOriginPrioritiesSupplier {

        @android.annotation.NonNull
        private final com.android.server.timedetector.ServerFlags mServerFlags;

        private ServerFlagsOriginPrioritiesSupplier(com.android.server.timedetector.ServerFlags serverFlags) {
            super();
            java.util.Objects.requireNonNull(serverFlags);
            this.mServerFlags = serverFlags;
        }

        @Override // com.android.server.timedetector.ServiceConfigAccessorImpl.BaseOriginPrioritiesSupplier
        @android.annotation.Nullable
        protected java.lang.String[] lookupPriorityStrings() {
            return this.mServerFlags.getOptionalStringArray(com.android.server.timedetector.ServerFlags.KEY_TIME_DETECTOR_ORIGIN_PRIORITIES_OVERRIDE).orElse(null);
        }
    }
}
