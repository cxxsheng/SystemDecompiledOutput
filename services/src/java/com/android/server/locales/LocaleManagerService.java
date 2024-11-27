package com.android.server.locales;

/* loaded from: classes2.dex */
public class LocaleManagerService extends com.android.server.SystemService {
    private static final java.lang.String ATTR_NAME = "name";
    public static final boolean DEBUG = false;
    private static final java.lang.String LOCALE_CONFIGS = "locale_configs";
    private static final java.lang.String PROP_ALLOW_IME_QUERY_APP_LOCALE = "i18n.feature.allow_ime_query_app_locale";
    private static final java.lang.String PROP_DYNAMIC_LOCALES_CHANGE = "i18n.feature.dynamic_locales_change";
    private static final java.lang.String SUFFIX_FILE_NAME = ".xml";
    private static final java.lang.String TAG = "LocaleManagerService";
    private android.app.ActivityManagerInternal mActivityManagerInternal;
    private com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;
    private com.android.server.locales.LocaleManagerBackupHelper mBackupHelper;
    private final com.android.server.locales.LocaleManagerService.LocaleManagerBinderService mBinderService;
    final android.content.Context mContext;
    private android.content.pm.PackageManager mPackageManager;
    private final com.android.internal.content.PackageMonitor mPackageMonitor;
    private final java.lang.Object mWriteLock;

    public LocaleManagerService(android.content.Context context) {
        super(context);
        this.mWriteLock = new java.lang.Object();
        this.mContext = context;
        this.mBinderService = new com.android.server.locales.LocaleManagerService.LocaleManagerBinderService();
        this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mPackageManager = this.mContext.getPackageManager();
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG, 10);
        handlerThread.start();
        final com.android.server.locales.SystemAppUpdateTracker systemAppUpdateTracker = new com.android.server.locales.SystemAppUpdateTracker(this);
        handlerThread.getThreadHandler().postAtFrontOfQueue(new java.lang.Runnable() { // from class: com.android.server.locales.LocaleManagerService.1
            @Override // java.lang.Runnable
            public void run() {
                systemAppUpdateTracker.init();
            }
        });
        this.mBackupHelper = new com.android.server.locales.LocaleManagerBackupHelper(this, this.mPackageManager, handlerThread);
        this.mPackageMonitor = new com.android.server.locales.LocaleManagerServicePackageMonitor(this.mBackupHelper, systemAppUpdateTracker, this);
        this.mPackageMonitor.register(context, handlerThread.getLooper(), android.os.UserHandle.ALL, true);
    }

    @com.android.internal.annotations.VisibleForTesting
    LocaleManagerService(android.content.Context context, com.android.server.wm.ActivityTaskManagerInternal activityTaskManagerInternal, android.app.ActivityManagerInternal activityManagerInternal, android.content.pm.PackageManager packageManager, com.android.server.locales.LocaleManagerBackupHelper localeManagerBackupHelper, com.android.internal.content.PackageMonitor packageMonitor) {
        super(context);
        this.mWriteLock = new java.lang.Object();
        this.mContext = context;
        this.mBinderService = new com.android.server.locales.LocaleManagerService.LocaleManagerBinderService();
        this.mActivityTaskManagerInternal = activityTaskManagerInternal;
        this.mActivityManagerInternal = activityManagerInternal;
        this.mPackageManager = packageManager;
        this.mBackupHelper = localeManagerBackupHelper;
        this.mPackageMonitor = packageMonitor;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_LOCALE, this.mBinderService);
        com.android.server.LocalServices.addService(com.android.server.locales.LocaleManagerInternal.class, new com.android.server.locales.LocaleManagerService.LocaleManagerInternalImpl());
    }

    private final class LocaleManagerInternalImpl extends com.android.server.locales.LocaleManagerInternal {
        private LocaleManagerInternalImpl() {
        }

        @Override // com.android.server.locales.LocaleManagerInternal
        @android.annotation.Nullable
        public byte[] getBackupPayload(int i) {
            checkCallerIsSystem();
            return com.android.server.locales.LocaleManagerService.this.mBackupHelper.getBackupPayload(i);
        }

        @Override // com.android.server.locales.LocaleManagerInternal
        public void stageAndApplyRestoredPayload(byte[] bArr, int i) {
            com.android.server.locales.LocaleManagerService.this.mBackupHelper.stageAndApplyRestoredPayload(bArr, i);
        }

        private void checkCallerIsSystem() {
            if (android.os.Binder.getCallingUid() != 1000) {
                throw new java.lang.SecurityException("Caller is not system.");
            }
        }
    }

    private final class LocaleManagerBinderService extends android.app.ILocaleManager.Stub {
        private LocaleManagerBinderService() {
        }

        public void setApplicationLocales(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.os.LocaleList localeList, boolean z) throws android.os.RemoteException {
            int i2;
            if (z) {
                i2 = 1;
            } else {
                i2 = 2;
            }
            com.android.server.locales.LocaleManagerService.this.setApplicationLocales(str, i, localeList, z, i2);
        }

        @android.annotation.NonNull
        public android.os.LocaleList getApplicationLocales(@android.annotation.NonNull java.lang.String str, int i) throws android.os.RemoteException {
            return com.android.server.locales.LocaleManagerService.this.getApplicationLocales(str, i);
        }

        @android.annotation.NonNull
        public android.os.LocaleList getSystemLocales() throws android.os.RemoteException {
            return com.android.server.locales.LocaleManagerService.this.getSystemLocales();
        }

        public void setOverrideLocaleConfig(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable android.app.LocaleConfig localeConfig) throws android.os.RemoteException {
            com.android.server.locales.LocaleManagerService.this.setOverrideLocaleConfig(str, i, localeConfig);
        }

        @android.annotation.Nullable
        public android.app.LocaleConfig getOverrideLocaleConfig(@android.annotation.NonNull java.lang.String str, int i) {
            return com.android.server.locales.LocaleManagerService.this.getOverrideLocaleConfig(str, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.locales.LocaleManagerShellCommand(com.android.server.locales.LocaleManagerService.this.mBinderService).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    public void setApplicationLocales(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.os.LocaleList localeList, boolean z, int i2) throws android.os.RemoteException, java.lang.IllegalArgumentException {
        com.android.server.locales.AppLocaleChangedAtomRecord appLocaleChangedAtomRecord = new com.android.server.locales.AppLocaleChangedAtomRecord(android.os.Binder.getCallingUid());
        try {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(localeList);
            appLocaleChangedAtomRecord.setCaller(i2);
            appLocaleChangedAtomRecord.setNewLocales(localeList.toLanguageTags());
            int handleIncomingUser = this.mActivityManagerInternal.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, 0, "setApplicationLocales", (java.lang.String) null);
            if (!isPackageOwnedByCaller(str, handleIncomingUser, appLocaleChangedAtomRecord, null)) {
                enforceChangeConfigurationPermission(appLocaleChangedAtomRecord);
            }
            this.mBackupHelper.persistLocalesModificationInfo(handleIncomingUser, str, z, localeList.isEmpty());
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                setApplicationLocalesUnchecked(str, handleIncomingUser, localeList, appLocaleChangedAtomRecord);
                logAppLocalesMetric(appLocaleChangedAtomRecord);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (java.lang.Throwable th) {
            logAppLocalesMetric(appLocaleChangedAtomRecord);
            throw th;
        }
    }

    private void setApplicationLocalesUnchecked(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.os.LocaleList localeList, @android.annotation.NonNull com.android.server.locales.AppLocaleChangedAtomRecord appLocaleChangedAtomRecord) {
        appLocaleChangedAtomRecord.setPrevLocales(getApplicationLocalesUnchecked(str, i).toLanguageTags());
        if (this.mActivityTaskManagerInternal.createPackageConfigurationUpdater(str, i).setLocales(localeList).commit()) {
            notifyAppWhoseLocaleChanged(str, i, localeList);
            notifyInstallerOfAppWhoseLocaleChanged(str, i, localeList);
            notifyRegisteredReceivers(str, i, localeList);
            this.mBackupHelper.notifyBackupManager();
            appLocaleChangedAtomRecord.setStatus(1);
            return;
        }
        appLocaleChangedAtomRecord.setStatus(2);
    }

    private void notifyRegisteredReceivers(java.lang.String str, int i, android.os.LocaleList localeList) {
        this.mContext.sendBroadcastAsUser(createBaseIntent("android.intent.action.APPLICATION_LOCALE_CHANGED", str, localeList), android.os.UserHandle.of(i), "android.permission.READ_APP_SPECIFIC_LOCALES");
    }

    void notifyInstallerOfAppWhoseLocaleChanged(java.lang.String str, int i, android.os.LocaleList localeList) {
        java.lang.String installingPackageName = getInstallingPackageName(str, i);
        if (installingPackageName != null) {
            android.content.Intent createBaseIntent = createBaseIntent("android.intent.action.APPLICATION_LOCALE_CHANGED", str, localeList);
            createBaseIntent.setPackage(installingPackageName);
            this.mContext.sendBroadcastAsUser(createBaseIntent, android.os.UserHandle.of(i));
        }
    }

    private void notifyAppWhoseLocaleChanged(java.lang.String str, int i, android.os.LocaleList localeList) {
        android.content.Intent createBaseIntent = createBaseIntent("android.intent.action.LOCALE_CHANGED", str, localeList);
        createBaseIntent.setPackage(str);
        createBaseIntent.addFlags(2097152);
        this.mContext.sendBroadcastAsUser(createBaseIntent, android.os.UserHandle.of(i));
    }

    static android.content.Intent createBaseIntent(java.lang.String str, java.lang.String str2, android.os.LocaleList localeList) {
        return new android.content.Intent(str).putExtra("android.intent.extra.PACKAGE_NAME", str2).putExtra("android.intent.extra.LOCALE_LIST", localeList).addFlags(android.hardware.audio.common.V2_0.AudioFormat.EVRCB);
    }

    private boolean isPackageOwnedByCaller(java.lang.String str, int i, @android.annotation.Nullable com.android.server.locales.AppLocaleChangedAtomRecord appLocaleChangedAtomRecord, @android.annotation.Nullable com.android.server.locales.AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord) {
        int packageUid = getPackageUid(str, i);
        if (packageUid < 0) {
            android.util.Slog.w(TAG, "Unknown package " + str + " for user " + i);
            if (appLocaleChangedAtomRecord != null) {
                appLocaleChangedAtomRecord.setStatus(3);
            } else if (appSupportedLocalesChangedAtomRecord != null) {
                appSupportedLocalesChangedAtomRecord.setStatus(3);
            }
            throw new java.lang.IllegalArgumentException("Unknown package: " + str + " for user " + i);
        }
        if (appLocaleChangedAtomRecord != null) {
            appLocaleChangedAtomRecord.setTargetUid(packageUid);
        } else if (appSupportedLocalesChangedAtomRecord != null) {
            appSupportedLocalesChangedAtomRecord.setTargetUid(packageUid);
        }
        return android.os.UserHandle.isSameApp(android.os.Binder.getCallingUid(), packageUid);
    }

    private void enforceChangeConfigurationPermission(@android.annotation.NonNull com.android.server.locales.AppLocaleChangedAtomRecord appLocaleChangedAtomRecord) {
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.CHANGE_CONFIGURATION", "setApplicationLocales");
        } catch (java.lang.SecurityException e) {
            appLocaleChangedAtomRecord.setStatus(4);
            throw e;
        }
    }

    @android.annotation.NonNull
    public android.os.LocaleList getApplicationLocales(@android.annotation.NonNull java.lang.String str, int i) throws android.os.RemoteException, java.lang.IllegalArgumentException {
        java.util.Objects.requireNonNull(str);
        int handleIncomingUser = this.mActivityManagerInternal.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, 0, "getApplicationLocales", (java.lang.String) null);
        if (!isPackageOwnedByCaller(str, handleIncomingUser, null, null) && !isCallerInstaller(str, handleIncomingUser) && (!isCallerFromCurrentInputMethod(handleIncomingUser) || !this.mActivityManagerInternal.isAppForeground(getPackageUid(str, handleIncomingUser)))) {
            enforceReadAppSpecificLocalesPermission();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return getApplicationLocalesUnchecked(str, handleIncomingUser);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.NonNull
    private android.os.LocaleList getApplicationLocalesUnchecked(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.wm.ActivityTaskManagerInternal.PackageConfig applicationConfig = this.mActivityTaskManagerInternal.getApplicationConfig(str, i);
        if (applicationConfig == null) {
            return android.os.LocaleList.getEmptyLocaleList();
        }
        android.os.LocaleList localeList = applicationConfig.mLocales;
        return localeList != null ? localeList : android.os.LocaleList.getEmptyLocaleList();
    }

    private boolean isCallerInstaller(java.lang.String str, int i) {
        int packageUid;
        java.lang.String installingPackageName = getInstallingPackageName(str, i);
        return installingPackageName != null && (packageUid = getPackageUid(installingPackageName, i)) >= 0 && android.os.UserHandle.isSameApp(android.os.Binder.getCallingUid(), packageUid);
    }

    private boolean isCallerFromCurrentInputMethod(int i) {
        if (!android.os.SystemProperties.getBoolean(PROP_ALLOW_IME_QUERY_APP_LOCALE, true)) {
            return false;
        }
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "default_input_method", i);
        if (android.text.TextUtils.isEmpty(stringForUser)) {
            return false;
        }
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(stringForUser);
        if (unflattenFromString == null) {
            android.util.Slog.d(TAG, "inValid input method");
            return false;
        }
        int packageUid = getPackageUid(unflattenFromString.getPackageName(), i);
        return packageUid >= 0 && android.os.UserHandle.isSameApp(android.os.Binder.getCallingUid(), packageUid);
    }

    private void enforceReadAppSpecificLocalesPermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_APP_SPECIFIC_LOCALES", "getApplicationLocales");
    }

    private int getPackageUid(java.lang.String str, int i) {
        try {
            return this.mPackageManager.getPackageUidAsUser(str, android.content.pm.PackageManager.PackageInfoFlags.of(0L), i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    @android.annotation.Nullable
    java.lang.String getInstallingPackageName(java.lang.String str, int i) {
        try {
            return this.mContext.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().getInstallSourceInfo(str).getInstallingPackageName();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w(TAG, "Package not found " + str);
            return null;
        }
    }

    @android.annotation.NonNull
    public android.os.LocaleList getSystemLocales() throws android.os.RemoteException {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return getSystemLocalesUnchecked();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.NonNull
    private android.os.LocaleList getSystemLocalesUnchecked() throws android.os.RemoteException {
        android.os.LocaleList localeList;
        android.content.res.Configuration configuration = android.app.ActivityManager.getService().getConfiguration();
        if (configuration == null) {
            localeList = null;
        } else {
            localeList = configuration.getLocales();
        }
        if (localeList == null) {
            return android.os.LocaleList.getEmptyLocaleList();
        }
        return localeList;
    }

    private void logAppLocalesMetric(@android.annotation.NonNull com.android.server.locales.AppLocaleChangedAtomRecord appLocaleChangedAtomRecord) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.APPLICATION_LOCALES_CHANGED, appLocaleChangedAtomRecord.mCallingUid, appLocaleChangedAtomRecord.mTargetUid, appLocaleChangedAtomRecord.mNewLocales, appLocaleChangedAtomRecord.mPrevLocales, appLocaleChangedAtomRecord.mStatus, appLocaleChangedAtomRecord.mCaller);
    }

    public void setOverrideLocaleConfig(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable android.app.LocaleConfig localeConfig) throws java.lang.IllegalArgumentException {
        if (!android.os.SystemProperties.getBoolean(PROP_DYNAMIC_LOCALES_CHANGE, true)) {
            return;
        }
        com.android.server.locales.AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord = new com.android.server.locales.AppSupportedLocalesChangedAtomRecord(android.os.Binder.getCallingUid());
        try {
            java.util.Objects.requireNonNull(str);
            int handleIncomingUser = this.mActivityManagerInternal.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, 0, "setOverrideLocaleConfig", (java.lang.String) null);
            if (!isPackageOwnedByCaller(str, handleIncomingUser, null, appSupportedLocalesChangedAtomRecord)) {
                enforceSetAppSpecificLocaleConfigPermission(appSupportedLocalesChangedAtomRecord);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                setOverrideLocaleConfigUnchecked(str, handleIncomingUser, localeConfig, appSupportedLocalesChangedAtomRecord);
                logAppSupportedLocalesChangedMetric(appSupportedLocalesChangedAtomRecord);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (java.lang.Throwable th) {
            logAppSupportedLocalesChangedMetric(appSupportedLocalesChangedAtomRecord);
            throw th;
        }
    }

    private void setOverrideLocaleConfigUnchecked(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable android.app.LocaleConfig localeConfig, @android.annotation.NonNull com.android.server.locales.AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord) {
        java.io.FileOutputStream fileOutputStream;
        synchronized (this.mWriteLock) {
            try {
                try {
                    android.app.LocaleConfig fromContextIgnoringOverride = android.app.LocaleConfig.fromContextIgnoringOverride(this.mContext.createPackageContext(str, 0));
                    java.io.File xmlFileNameForUser = getXmlFileNameForUser(str, i);
                    if (localeConfig == null) {
                        if (xmlFileNameForUser.exists()) {
                            android.util.Slog.d(TAG, "remove the override LocaleConfig");
                            xmlFileNameForUser.delete();
                        }
                        removeUnsupportedAppLocales(str, i, fromContextIgnoringOverride, 5);
                        appSupportedLocalesChangedAtomRecord.setOverrideRemoved(true);
                        appSupportedLocalesChangedAtomRecord.setStatus(1);
                        return;
                    }
                    if (localeConfig.isSameLocaleConfig(getOverrideLocaleConfig(str, i))) {
                        android.util.Slog.d(TAG, "the same override, ignore it");
                        appSupportedLocalesChangedAtomRecord.setSameAsPrevConfig(true);
                        return;
                    }
                    android.os.LocaleList supportedLocales = localeConfig.getSupportedLocales();
                    if (supportedLocales == null) {
                        supportedLocales = android.os.LocaleList.getEmptyLocaleList();
                    }
                    appSupportedLocalesChangedAtomRecord.setNumLocales(supportedLocales.size());
                    android.util.AtomicFile atomicFile = new android.util.AtomicFile(xmlFileNameForUser);
                    try {
                        fileOutputStream = atomicFile.startWrite();
                    } catch (java.lang.Exception e) {
                        e = e;
                        fileOutputStream = null;
                    }
                    try {
                        fileOutputStream.write(toXmlByteArray(supportedLocales));
                        atomicFile.finishWrite(fileOutputStream);
                        removeUnsupportedAppLocales(str, i, localeConfig, 5);
                        if (localeConfig.isSameLocaleConfig(fromContextIgnoringOverride)) {
                            android.util.Slog.d(TAG, "setOverrideLocaleConfig, same as the app's LocaleConfig");
                            appSupportedLocalesChangedAtomRecord.setSameAsResConfig(true);
                        }
                        appSupportedLocalesChangedAtomRecord.setStatus(1);
                    } catch (java.lang.Exception e2) {
                        e = e2;
                        android.util.Slog.e(TAG, "Failed to write file " + atomicFile, e);
                        if (fileOutputStream != null) {
                            atomicFile.failWrite(fileOutputStream);
                        }
                        appSupportedLocalesChangedAtomRecord.setStatus(2);
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                    android.util.Slog.e(TAG, "Unknown package name " + str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void removeUnsupportedAppLocales(java.lang.String str, int i, android.app.LocaleConfig localeConfig, int i2) {
        android.os.LocaleList applicationLocalesUnchecked = getApplicationLocalesUnchecked(str, i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        boolean z = true;
        if (localeConfig == null) {
            android.util.Slog.i(TAG, "There is no LocaleConfig, reset app locales");
        } else {
            boolean z2 = false;
            for (int i3 = 0; i3 < applicationLocalesUnchecked.size(); i3++) {
                if (!localeConfig.containsLocale(applicationLocalesUnchecked.get(i3))) {
                    android.util.Slog.i(TAG, "Missing from the LocaleConfig, reset app locales");
                    z2 = true;
                } else {
                    arrayList.add(applicationLocalesUnchecked.get(i3));
                }
            }
            z = z2;
        }
        if (z) {
            try {
                setApplicationLocales(str, i, new android.os.LocaleList((java.util.Locale[]) arrayList.toArray(new java.util.Locale[arrayList.size()])), this.mBackupHelper.areLocalesSetFromDelegate(i, str), i2);
            } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
                android.util.Slog.e(TAG, "Could not set locales for " + str, e);
            }
        }
    }

    private void enforceSetAppSpecificLocaleConfigPermission(com.android.server.locales.AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord) {
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.SET_APP_SPECIFIC_LOCALECONFIG", "setOverrideLocaleConfig");
        } catch (java.lang.SecurityException e) {
            appSupportedLocalesChangedAtomRecord.setStatus(4);
            throw e;
        }
    }

    @android.annotation.Nullable
    public android.app.LocaleConfig getOverrideLocaleConfig(@android.annotation.NonNull java.lang.String str, int i) {
        if (!android.os.SystemProperties.getBoolean(PROP_DYNAMIC_LOCALES_CHANGE, true)) {
            return null;
        }
        java.util.Objects.requireNonNull(str);
        java.io.File xmlFileNameForUser = getXmlFileNameForUser(str, this.mActivityManagerInternal.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, 0, "getOverrideLocaleConfig", (java.lang.String) null));
        if (!xmlFileNameForUser.exists()) {
            return null;
        }
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(xmlFileNameForUser);
            try {
                android.app.LocaleConfig localeConfig = new android.app.LocaleConfig(android.os.LocaleList.forLanguageTags(java.lang.String.join(",", loadFromXml(android.util.Xml.resolvePullParser(fileInputStream)))));
                fileInputStream.close();
                return localeConfig;
            } catch (java.lang.Throwable th) {
                try {
                    fileInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(TAG, "Failed to parse XML configuration from " + xmlFileNameForUser, e);
            return null;
        }
    }

    void deleteOverrideLocaleConfig(@android.annotation.NonNull java.lang.String str, int i) {
        java.io.File xmlFileNameForUser = getXmlFileNameForUser(str, i);
        if (xmlFileNameForUser.exists()) {
            android.util.Slog.d(TAG, "Delete the override LocaleConfig.");
            xmlFileNameForUser.delete();
        }
    }

    private byte[] toXmlByteArray(android.os.LocaleList localeList) {
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            try {
                com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
                newFastSerializer.setOutput(byteArrayOutputStream, java.nio.charset.StandardCharsets.UTF_8.name());
                newFastSerializer.startDocument((java.lang.String) null, true);
                newFastSerializer.startTag((java.lang.String) null, "locale-config");
                for (java.lang.String str : new java.util.ArrayList(java.util.Arrays.asList(localeList.toLanguageTags().split(",")))) {
                    newFastSerializer.startTag((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_LOCALE);
                    newFastSerializer.attribute((java.lang.String) null, "name", str);
                    newFastSerializer.endTag((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_LOCALE);
                }
                newFastSerializer.endTag((java.lang.String) null, "locale-config");
                newFastSerializer.endDocument();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            } finally {
            }
        } catch (java.io.IOException e) {
            return null;
        }
    }

    @android.annotation.NonNull
    private java.util.List<java.lang.String> loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.internal.util.XmlUtils.beginDocument(typedXmlPullParser, "locale-config");
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            java.lang.String name = typedXmlPullParser.getName();
            if (com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_LOCALE.equals(name)) {
                arrayList.add(typedXmlPullParser.getAttributeValue((java.lang.String) null, "name"));
            } else {
                android.util.Slog.w(TAG, "Unexpected tag name: " + name);
                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    private java.io.File getXmlFileNameForUser(@android.annotation.NonNull java.lang.String str, int i) {
        return new java.io.File(new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), LOCALE_CONFIGS), str + SUFFIX_FILE_NAME);
    }

    private void logAppSupportedLocalesChangedMetric(@android.annotation.NonNull com.android.server.locales.AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.APP_SUPPORTED_LOCALES_CHANGED, appSupportedLocalesChangedAtomRecord.mCallingUid, appSupportedLocalesChangedAtomRecord.mTargetUid, appSupportedLocalesChangedAtomRecord.mNumLocales, appSupportedLocalesChangedAtomRecord.mOverrideRemoved, appSupportedLocalesChangedAtomRecord.mSameAsResConfig, appSupportedLocalesChangedAtomRecord.mSameAsPrevConfig, appSupportedLocalesChangedAtomRecord.mStatus);
    }
}
