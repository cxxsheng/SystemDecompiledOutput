package com.android.server.autofill;

/* loaded from: classes.dex */
public final class AutofillManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.autofill.AutofillManagerService, com.android.server.autofill.AutofillManagerServiceImpl> {
    private static final char COMPAT_PACKAGE_DELIMITER = ':';
    private static final char COMPAT_PACKAGE_URL_IDS_BLOCK_BEGIN = '[';
    private static final char COMPAT_PACKAGE_URL_IDS_BLOCK_END = ']';
    private static final char COMPAT_PACKAGE_URL_IDS_DELIMITER = ',';
    private static final int DEFAULT_AUGMENTED_AUTOFILL_REQUEST_TIMEOUT_MILLIS = 5000;
    private static final java.lang.String DEFAULT_PCC_FEATURE_PROVIDER_HINTS = "";
    private static final boolean DEFAULT_PCC_USE_FALLBACK = true;
    private static final boolean DEFAULT_PREFER_PROVIDER_OVER_PCC = true;
    static final java.lang.String RECEIVER_BUNDLE_EXTRA_SESSIONS = "sessions";
    private static final java.lang.String TAG = "AutofillManagerService";
    private static final java.lang.Object sLock = com.android.server.autofill.AutofillManagerService.class;

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static int sPartitionMaxCount = 10;

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static int sVisibleDatasetsMaxCount = 0;
    private final android.app.ActivityManagerInternal mAm;

    @android.annotation.NonNull
    final com.android.server.infra.FrameworkResourcesServiceNameResolver mAugmentedAutofillResolver;
    final com.android.server.autofill.AutofillManagerService.AugmentedAutofillState mAugmentedAutofillState;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mAugmentedServiceIdleUnbindTimeoutMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mAugmentedServiceRequestTimeoutMs;
    private final com.android.server.autofill.AutofillManagerService.AutofillCompatState mAutofillCompatState;

    @com.android.internal.annotations.GuardedBy({"mFlagLock"})
    private boolean mAutofillCredmanIntegrationEnabled;
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private final com.android.server.autofill.AutofillManagerService.DisabledInfoCache mDisabledInfoCache;

    @android.annotation.NonNull
    final com.android.server.infra.FrameworkResourcesServiceNameResolver mFieldClassificationResolver;
    private final java.lang.Object mFlagLock;

    @com.android.internal.annotations.GuardedBy({"mFlagLock"})
    private boolean mIsFillFieldsFromCurrentSessionOnly;
    private final com.android.server.autofill.AutofillManagerService.LocalService mLocalService;

    @com.android.internal.annotations.GuardedBy({"mFlagLock"})
    private int mMaxInputLengthForAutofill;

    @com.android.internal.annotations.GuardedBy({"mFlagLock"})
    private boolean mPccClassificationEnabled;

    @com.android.internal.annotations.GuardedBy({"mFlagLock"})
    private boolean mPccPreferProviderOverPcc;

    @com.android.internal.annotations.GuardedBy({"mFlagLock"})
    private java.lang.String mPccProviderHints;

    @com.android.internal.annotations.GuardedBy({"mFlagLock"})
    private boolean mPccUseFallbackDetection;
    private final android.util.LocalLog mRequestsHistory;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mSupportedSmartSuggestionModes;
    private final com.android.server.autofill.ui.AutoFillUI mUi;
    private final android.util.LocalLog mUiLatencyHistory;
    private final android.util.LocalLog mWtfHistory;

    /* renamed from: com.android.server.autofill.AutofillManagerService$1, reason: invalid class name */
    class AnonymousClass1 extends android.content.BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(com.android.server.autofill.AutofillManagerService.TAG, "Close system dialogs");
                }
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                    com.android.server.autofill.AutofillManagerService.this.visitServicesLocked(new com.android.server.infra.AbstractMasterSystemService.Visitor() { // from class: com.android.server.autofill.AutofillManagerService$1$$ExternalSyntheticLambda0
                        @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
                        public final void visit(java.lang.Object obj) {
                            ((com.android.server.autofill.AutofillManagerServiceImpl) obj).forceRemoveFinishedSessionsLocked();
                        }
                    });
                }
                com.android.server.autofill.AutofillManagerService.this.mUi.hideAll(null);
            }
        }
    }

    public AutofillManagerService(android.content.Context context) {
        super(context, new com.android.server.infra.SecureSettingsServiceNameResolver(context, "autofill_service"), "no_autofill", 4);
        this.mRequestsHistory = new android.util.LocalLog(20);
        this.mUiLatencyHistory = new android.util.LocalLog(20);
        this.mWtfHistory = new android.util.LocalLog(50);
        this.mAutofillCompatState = new com.android.server.autofill.AutofillManagerService.AutofillCompatState();
        this.mDisabledInfoCache = new com.android.server.autofill.AutofillManagerService.DisabledInfoCache();
        this.mLocalService = new com.android.server.autofill.AutofillManagerService.LocalService();
        this.mBroadcastReceiver = new com.android.server.autofill.AutofillManagerService.AnonymousClass1();
        this.mAugmentedAutofillState = new com.android.server.autofill.AutofillManagerService.AugmentedAutofillState();
        this.mFlagLock = new java.lang.Object();
        this.mUi = new com.android.server.autofill.ui.AutoFillUI(android.app.ActivityThread.currentActivityThread().getSystemUiContext());
        this.mAm = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        android.provider.DeviceConfig.addOnPropertiesChangedListener("autofill", android.app.ActivityThread.currentApplication().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda1
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.autofill.AutofillManagerService.this.lambda$new$0(properties);
            }
        });
        setLogLevelFromSettings();
        setMaxPartitionsFromSettings();
        setMaxVisibleDatasetsFromSettings();
        setDeviceConfigProperties();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        context.registerReceiver(this.mBroadcastReceiver, intentFilter, null, com.android.server.FgThread.getHandler(), 2);
        this.mAugmentedAutofillResolver = new com.android.server.infra.FrameworkResourcesServiceNameResolver(getContext(), android.R.string.config_defaultAccessibilityNotificationSound);
        this.mAugmentedAutofillResolver.setOnTemporaryServiceNameChangedCallback(new com.android.server.infra.ServiceNameResolver.NameResolverListener() { // from class: com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda2
            @Override // com.android.server.infra.ServiceNameResolver.NameResolverListener
            public final void onNameResolved(int i, java.lang.String str, boolean z) {
                com.android.server.autofill.AutofillManagerService.this.lambda$new$1(i, str, z);
            }
        });
        this.mFieldClassificationResolver = new com.android.server.infra.FrameworkResourcesServiceNameResolver(getContext(), android.R.string.config_defaultContextualSearchLegacyEnabled);
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Resolving FieldClassificationService to serviceName: " + this.mFieldClassificationResolver.readServiceName(0));
        }
        this.mFieldClassificationResolver.setOnTemporaryServiceNameChangedCallback(new com.android.server.infra.ServiceNameResolver.NameResolverListener() { // from class: com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda3
            @Override // com.android.server.infra.ServiceNameResolver.NameResolverListener
            public final void onNameResolved(int i, java.lang.String str, boolean z) {
                com.android.server.autofill.AutofillManagerService.this.lambda$new$2(i, str, z);
            }
        });
        if (this.mSupportedSmartSuggestionModes != 0) {
            java.util.List<android.content.pm.UserInfo> supportedUsers = getSupportedUsers();
            for (int i = 0; i < supportedUsers.size(); i++) {
                int i2 = supportedUsers.get(i).id;
                getServiceForUserLocked(i2);
                this.mAugmentedAutofillState.setServiceInfo(i2, this.mAugmentedAutofillResolver.getServiceName(i2), this.mAugmentedAutofillResolver.isTemporary(i2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.provider.DeviceConfig.Properties properties) {
        onDeviceConfigChange(properties.getKeyset());
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected java.lang.String getServiceSettingsProperty() {
        return "autofill_service";
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void registerForExtraSettingsChanges(@android.annotation.NonNull android.content.ContentResolver contentResolver, @android.annotation.NonNull android.database.ContentObserver contentObserver) {
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("autofill_logging_level"), false, contentObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("autofill_max_partitions_size"), false, contentObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("autofill_max_visible_datasets"), false, contentObserver, -1);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("selected_input_method_subtype"), false, contentObserver, -1);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void onSettingsChanged(int i, @android.annotation.NonNull java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1848997872:
                if (str.equals("autofill_max_visible_datasets")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1299292969:
                if (str.equals("autofill_logging_level")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1048937777:
                if (str.equals("autofill_max_partitions_size")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1194058837:
                if (str.equals("selected_input_method_subtype")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                setLogLevelFromSettings();
                return;
            case 1:
                setMaxPartitionsFromSettings();
                return;
            case 2:
                setMaxVisibleDatasetsFromSettings();
                return;
            case 3:
                handleInputMethodSwitch(i);
                return;
            default:
                android.util.Slog.w(TAG, "Unexpected property (" + str + "); updating cache instead");
                synchronized (this.mLock) {
                    updateCachedServiceLocked(i);
                }
                return;
        }
    }

    private void handleInputMethodSwitch(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = peekServiceForUserWithLocalBinderIdentityLocked(i);
                if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                    peekServiceForUserWithLocalBinderIdentityLocked.onSwitchInputMethod();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void onDeviceConfigChange(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        char c;
        for (java.lang.String str : set) {
            switch (str.hashCode()) {
                case -1681497033:
                    if (str.equals("pcc_classification_enabled")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1644292860:
                    if (str.equals("prefer_provider_over_pcc")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -1546842390:
                    if (str.equals("augmented_service_idle_unbind_timeout")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -987506216:
                    if (str.equals("augmented_service_request_timeout")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 139432258:
                    if (str.equals("pcc_classification_hints")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 346686040:
                    if (str.equals("android.service.autofill.autofill_credman_integration")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1168452547:
                    if (str.equals("compat_mode_allowed_packages")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1169876393:
                    if (str.equals("pcc_use_fallback")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1709136986:
                    if (str.equals("smart_suggestion_supported_modes")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    setDeviceConfigProperties();
                    break;
                case '\b':
                    updateCachedServices();
                    break;
                default:
                    android.util.Slog.i(this.mTag, "Ignoring change on " + str);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onAugmentedServiceNameChanged, reason: merged with bridge method [inline-methods] */
    public void lambda$new$1(int i, @android.annotation.Nullable java.lang.String str, boolean z) {
        this.mAugmentedAutofillState.setServiceInfo(i, str, z);
        synchronized (this.mLock) {
            try {
                com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = peekServiceForUserWithLocalBinderIdentityLocked(i);
                if (peekServiceForUserWithLocalBinderIdentityLocked == null) {
                    getServiceForUserWithLocalBinderIdentityLocked(i);
                } else {
                    peekServiceForUserWithLocalBinderIdentityLocked.updateRemoteAugmentedAutofillService();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onFieldClassificationServiceNameChanged, reason: merged with bridge method [inline-methods] */
    public void lambda$new$2(int i, @android.annotation.Nullable java.lang.String str, boolean z) {
        synchronized (this.mLock) {
            try {
                com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = peekServiceForUserWithLocalBinderIdentityLocked(i);
                if (peekServiceForUserWithLocalBinderIdentityLocked == null) {
                    getServiceForUserWithLocalBinderIdentityLocked(i);
                } else {
                    peekServiceForUserWithLocalBinderIdentityLocked.updateRemoteFieldClassificationService();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public com.android.server.autofill.AutofillManagerServiceImpl getServiceForUserWithLocalBinderIdentityLocked(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return getServiceForUserLocked(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return peekServiceForUserLocked(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.autofill.AutofillManagerServiceImpl newServiceLocked(int i, boolean z) {
        return new com.android.server.autofill.AutofillManagerServiceImpl(this, this.mLock, this.mUiLatencyHistory, this.mWtfHistory, i, this.mUi, this.mAutofillCompatState, z, this.mDisabledInfoCache);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public void onServiceRemoved(@android.annotation.NonNull com.android.server.autofill.AutofillManagerServiceImpl autofillManagerServiceImpl, int i) {
        autofillManagerServiceImpl.destroyLocked();
        this.mDisabledInfoCache.remove(i);
        this.mAutofillCompatState.removeCompatibilityModeRequests(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public void onServiceEnabledLocked(@android.annotation.NonNull com.android.server.autofill.AutofillManagerServiceImpl autofillManagerServiceImpl, int i) {
        addCompatibilityModeRequestsLocked(autofillManagerServiceImpl, i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    protected void enforceCallingPermissionForManagement() {
        getContext().enforceCallingPermission("android.permission.MANAGE_AUTO_FILL", TAG);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("autofill", new com.android.server.autofill.AutofillManagerService.AutoFillManagerServiceStub());
        publishLocalService(android.view.autofill.AutofillManagerInternal.class, this.mLocalService);
    }

    @Override // com.android.server.SystemService
    public boolean isUserSupported(com.android.server.SystemService.TargetUser targetUser) {
        return targetUser.isFull() || targetUser.isProfile();
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "Hiding UI when user switched");
        }
        this.mUi.hideAll(null);
    }

    int getSupportedSmartSuggestionModesLocked() {
        return this.mSupportedSmartSuggestionModes;
    }

    void logRequestLocked(@android.annotation.NonNull java.lang.String str) {
        this.mRequestsHistory.log(str);
    }

    boolean isInstantServiceAllowed() {
        return this.mAllowInstantService;
    }

    void removeAllSessions(int i, com.android.internal.os.IResultReceiver iResultReceiver) {
        android.util.Slog.i(TAG, "removeAllSessions() for userId " + i);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            try {
                if (i != -1) {
                    com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserLocked = peekServiceForUserLocked(i);
                    if (peekServiceForUserLocked != null) {
                        peekServiceForUserLocked.forceRemoveAllSessionsLocked();
                    }
                } else {
                    visitServicesLocked(new com.android.server.infra.AbstractMasterSystemService.Visitor() { // from class: com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda4
                        @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
                        public final void visit(java.lang.Object obj) {
                            ((com.android.server.autofill.AutofillManagerServiceImpl) obj).forceRemoveAllSessionsLocked();
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        try {
            iResultReceiver.send(0, new android.os.Bundle());
        } catch (android.os.RemoteException e) {
        }
    }

    void listSessions(int i, com.android.internal.os.IResultReceiver iResultReceiver) {
        android.util.Slog.i(TAG, "listSessions() for userId " + i);
        enforceCallingPermissionForManagement();
        android.os.Bundle bundle = new android.os.Bundle();
        final java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        synchronized (this.mLock) {
            try {
                if (i != -1) {
                    com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserLocked = peekServiceForUserLocked(i);
                    if (peekServiceForUserLocked != null) {
                        peekServiceForUserLocked.listSessionsLocked(arrayList);
                    }
                } else {
                    visitServicesLocked(new com.android.server.infra.AbstractMasterSystemService.Visitor() { // from class: com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda0
                        @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
                        public final void visit(java.lang.Object obj) {
                            ((com.android.server.autofill.AutofillManagerServiceImpl) obj).listSessionsLocked(arrayList);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        bundle.putStringArrayList(RECEIVER_BUNDLE_EXTRA_SESSIONS, arrayList);
        try {
            iResultReceiver.send(0, bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    void reset() {
        android.util.Slog.i(TAG, "reset()");
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            visitServicesLocked(new com.android.server.infra.AbstractMasterSystemService.Visitor() { // from class: com.android.server.autofill.AutofillManagerService$$ExternalSyntheticLambda5
                @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
                public final void visit(java.lang.Object obj) {
                    ((com.android.server.autofill.AutofillManagerServiceImpl) obj).destroyLocked();
                }
            });
            clearCacheLocked();
        }
    }

    void setLogLevel(int i) {
        android.util.Slog.i(TAG, "setLogLevel(): " + i);
        enforceCallingPermissionForManagement();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.provider.Settings.Global.putInt(getContext().getContentResolver(), "autofill_logging_level", i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setLogLevelFromSettings() {
        boolean z;
        int i = android.provider.Settings.Global.getInt(getContext().getContentResolver(), "autofill_logging_level", android.view.autofill.AutofillManager.DEFAULT_LOGGING_LEVEL);
        boolean z2 = false;
        if (i != 0) {
            z = true;
            if (i == 4) {
                z2 = true;
            } else if (i == 2) {
                z = false;
                z2 = true;
            } else {
                android.util.Slog.w(TAG, "setLogLevelFromSettings(): invalid level: " + i);
            }
            if (!z2 || com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "setLogLevelFromSettings(): level=" + i + ", debug=" + z2 + ", verbose=" + z);
            }
            synchronized (this.mLock) {
                setLoggingLevelsLocked(z2, z);
            }
            return;
        }
        z = false;
        if (!z2) {
        }
        android.util.Slog.d(TAG, "setLogLevelFromSettings(): level=" + i + ", debug=" + z2 + ", verbose=" + z);
        synchronized (this.mLock) {
        }
    }

    int getLogLevel() {
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            try {
                if (com.android.server.autofill.Helper.sVerbose) {
                    return 4;
                }
                return com.android.server.autofill.Helper.sDebug ? 2 : 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    int getMaxPartitions() {
        int i;
        synchronized (this.mLock) {
            i = sPartitionMaxCount;
        }
        return i;
    }

    void setMaxPartitions(int i) {
        android.util.Slog.i(TAG, "setMaxPartitions(): " + i);
        enforceCallingPermissionForManagement();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.provider.Settings.Global.putInt(getContext().getContentResolver(), "autofill_max_partitions_size", i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void setMaxPartitionsFromSettings() {
        int i = android.provider.Settings.Global.getInt(getContext().getContentResolver(), "autofill_max_partitions_size", 10);
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "setMaxPartitionsFromSettings(): " + i);
        }
        synchronized (sLock) {
            sPartitionMaxCount = i;
        }
    }

    int getMaxVisibleDatasets() {
        int i;
        synchronized (sLock) {
            i = sVisibleDatasetsMaxCount;
        }
        return i;
    }

    void setMaxVisibleDatasets(int i) {
        android.util.Slog.i(TAG, "setMaxVisibleDatasets(): " + i);
        enforceCallingPermissionForManagement();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.provider.Settings.Global.putInt(getContext().getContentResolver(), "autofill_max_visible_datasets", i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void setMaxVisibleDatasetsFromSettings() {
        int i = android.provider.Settings.Global.getInt(getContext().getContentResolver(), "autofill_max_visible_datasets", 0);
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "setMaxVisibleDatasetsFromSettings(): " + i);
        }
        synchronized (sLock) {
            sVisibleDatasetsMaxCount = i;
        }
    }

    private void setDeviceConfigProperties() {
        synchronized (this.mLock) {
            try {
                this.mAugmentedServiceIdleUnbindTimeoutMs = android.provider.DeviceConfig.getInt("autofill", "augmented_service_idle_unbind_timeout", 0);
                this.mAugmentedServiceRequestTimeoutMs = android.provider.DeviceConfig.getInt("autofill", "augmented_service_request_timeout", 5000);
                this.mSupportedSmartSuggestionModes = android.provider.DeviceConfig.getInt("autofill", "smart_suggestion_supported_modes", 1);
                if (this.verbose) {
                    android.util.Slog.v(this.mTag, "setDeviceConfigProperties() for AugmentedAutofill: augmentedIdleTimeout=" + this.mAugmentedServiceIdleUnbindTimeoutMs + ", augmentedRequestTimeout=" + this.mAugmentedServiceRequestTimeoutMs + ", smartSuggestionMode=" + android.view.autofill.AutofillManager.getSmartSuggestionModeToString(this.mSupportedSmartSuggestionModes));
                }
            } finally {
            }
        }
        synchronized (this.mFlagLock) {
            try {
                this.mPccClassificationEnabled = android.provider.DeviceConfig.getBoolean("autofill", "pcc_classification_enabled", false);
                this.mPccPreferProviderOverPcc = android.provider.DeviceConfig.getBoolean("autofill", "prefer_provider_over_pcc", true);
                this.mPccUseFallbackDetection = android.provider.DeviceConfig.getBoolean("autofill", "pcc_use_fallback", true);
                this.mPccProviderHints = android.provider.DeviceConfig.getString("autofill", "pcc_classification_hints", "");
                this.mMaxInputLengthForAutofill = android.provider.DeviceConfig.getInt("autofill", "max_input_length_for_autofill", 3);
                this.mAutofillCredmanIntegrationEnabled = android.service.autofill.Flags.autofillCredmanIntegration();
                this.mIsFillFieldsFromCurrentSessionOnly = android.service.autofill.Flags.fillFieldsFromCurrentSessionOnly();
                if (this.verbose) {
                    android.util.Slog.v(this.mTag, "setDeviceConfigProperties() for PCC: mPccClassificationEnabled=" + this.mPccClassificationEnabled + ", mPccPreferProviderOverPcc=" + this.mPccPreferProviderOverPcc + ", mPccUseFallbackDetection=" + this.mPccUseFallbackDetection + ", mPccProviderHints=" + this.mPccProviderHints + ", mAutofillCredmanIntegrationEnabled=" + this.mAutofillCredmanIntegrationEnabled);
                }
            } finally {
            }
        }
    }

    private void updateCachedServices() {
        for (android.content.pm.UserInfo userInfo : getSupportedUsers()) {
            synchronized (this.mLock) {
                updateCachedServiceLocked(userInfo.id);
            }
        }
    }

    void calculateScore(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, @android.annotation.NonNull android.os.RemoteCallback remoteCallback) {
        enforceCallingPermissionForManagement();
        new com.android.server.autofill.FieldClassificationStrategy(getContext(), -2).calculateScores(remoteCallback, java.util.Arrays.asList(android.view.autofill.AutofillValue.forText(str2)), new java.lang.String[]{str3}, new java.lang.String[]{null}, str, null, null, null);
    }

    java.lang.Boolean getFullScreenMode() {
        enforceCallingPermissionForManagement();
        return com.android.server.autofill.Helper.sFullScreenMode;
    }

    void setFullScreenMode(@android.annotation.Nullable java.lang.Boolean bool) {
        enforceCallingPermissionForManagement();
        com.android.server.autofill.Helper.sFullScreenMode = bool;
    }

    void setTemporaryAugmentedAutofillService(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        android.util.Slog.i(this.mTag, "setTemporaryAugmentedAutofillService(" + i + ") to " + str + " for " + i2 + "ms");
        enforceCallingPermissionForManagement();
        java.util.Objects.requireNonNull(str);
        if (i2 > 120000) {
            throw new java.lang.IllegalArgumentException("Max duration is 120000 (called with " + i2 + ")");
        }
        this.mAugmentedAutofillResolver.setTemporaryService(i, str, i2);
    }

    void resetTemporaryAugmentedAutofillService(int i) {
        enforceCallingPermissionForManagement();
        this.mAugmentedAutofillResolver.resetTemporaryService(i);
    }

    boolean isDefaultAugmentedServiceEnabled(int i) {
        enforceCallingPermissionForManagement();
        return this.mAugmentedAutofillResolver.isDefaultServiceEnabled(i);
    }

    boolean setDefaultAugmentedServiceEnabled(int i, boolean z) {
        android.util.Slog.i(this.mTag, "setDefaultAugmentedServiceEnabled() for userId " + i + ": " + z);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            try {
                com.android.server.autofill.AutofillManagerServiceImpl serviceForUserLocked = getServiceForUserLocked(i);
                if (serviceForUserLocked != null) {
                    if (this.mAugmentedAutofillResolver.setDefaultServiceEnabled(i, z)) {
                        serviceForUserLocked.updateRemoteAugmentedAutofillService();
                        return true;
                    }
                    if (this.debug) {
                        android.util.Slog.d(TAG, "setDefaultAugmentedServiceEnabled(): already " + z);
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isFieldDetectionServiceEnabledForUser(int i) {
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            try {
                com.android.server.autofill.AutofillManagerServiceImpl serviceForUserLocked = getServiceForUserLocked(i);
                if (serviceForUserLocked != null) {
                    return serviceForUserLocked.isPccClassificationEnabled();
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    java.lang.String getFieldDetectionServiceName(int i) {
        enforceCallingPermissionForManagement();
        return this.mFieldClassificationResolver.readServiceName(i);
    }

    boolean setTemporaryDetectionService(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        android.util.Slog.i(this.mTag, "setTemporaryDetectionService(" + i + ") to " + str + " for " + i2 + "ms");
        enforceCallingPermissionForManagement();
        java.util.Objects.requireNonNull(str);
        this.mFieldClassificationResolver.setTemporaryService(i, str, i2);
        return false;
    }

    void resetTemporaryDetectionService(int i) {
        enforceCallingPermissionForManagement();
        this.mFieldClassificationResolver.resetTemporaryService(i);
    }

    boolean requestSavedPasswordCount(int i, @android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) {
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            try {
                com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserLocked = peekServiceForUserLocked(i);
                if (peekServiceForUserLocked != null) {
                    peekServiceForUserLocked.requestSavedPasswordCount(iResultReceiver);
                    return true;
                }
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "requestSavedPasswordCount(): no service for " + i);
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void setLoggingLevelsLocked(boolean z, boolean z2) {
        com.android.server.autofill.Helper.sDebug = z;
        android.view.autofill.Helper.sDebug = z;
        this.debug = z;
        com.android.server.autofill.Helper.sVerbose = z2;
        android.view.autofill.Helper.sVerbose = z2;
        this.verbose = z2;
    }

    private void addCompatibilityModeRequestsLocked(@android.annotation.NonNull com.android.server.autofill.AutofillManagerServiceImpl autofillManagerServiceImpl, int i) {
        this.mAutofillCompatState.reset(i);
        android.util.ArrayMap<java.lang.String, java.lang.Long> compatibilityPackagesLocked = autofillManagerServiceImpl.getCompatibilityPackagesLocked();
        if (compatibilityPackagesLocked == null || compatibilityPackagesLocked.isEmpty()) {
            return;
        }
        java.util.Map<java.lang.String, java.lang.String[]> allowedCompatModePackages = getAllowedCompatModePackages();
        int size = compatibilityPackagesLocked.size();
        for (int i2 = 0; i2 < size; i2++) {
            java.lang.String keyAt = compatibilityPackagesLocked.keyAt(i2);
            if (allowedCompatModePackages == null || !allowedCompatModePackages.containsKey(keyAt)) {
                android.util.Slog.w(TAG, "Ignoring not allowed compat package " + keyAt);
            } else {
                java.lang.Long valueAt = compatibilityPackagesLocked.valueAt(i2);
                if (valueAt != null) {
                    this.mAutofillCompatState.addCompatibilityModeRequest(keyAt, valueAt.longValue(), allowedCompatModePackages.get(keyAt), i);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getAllowedCompatModePackagesFromDeviceConfig() {
        java.lang.String string = android.provider.DeviceConfig.getString("autofill", "compat_mode_allowed_packages", (java.lang.String) null);
        if (!android.text.TextUtils.isEmpty(string)) {
            return string;
        }
        return getAllowedCompatModePackagesFromSettings();
    }

    private java.lang.String getAllowedCompatModePackagesFromSettings() {
        return android.provider.Settings.Global.getString(getContext().getContentResolver(), "autofill_compat_mode_allowed_packages");
    }

    @android.annotation.Nullable
    private java.util.Map<java.lang.String, java.lang.String[]> getAllowedCompatModePackages() {
        return getAllowedCompatModePackages(getAllowedCompatModePackagesFromDeviceConfig());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void send(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver, int i) {
        try {
            iResultReceiver.send(i, (android.os.Bundle) null);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Error async reporting result to client: " + e);
        }
    }

    private void send(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver, @android.annotation.NonNull android.os.Bundle bundle) {
        try {
            iResultReceiver.send(0, bundle);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Error async reporting result to client: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void send(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver, @android.annotation.Nullable java.lang.String str) {
        send(iResultReceiver, com.android.internal.util.SyncResultReceiver.bundleFor(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void send(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver, @android.annotation.Nullable java.lang.String[] strArr) {
        send(iResultReceiver, com.android.internal.util.SyncResultReceiver.bundleFor(strArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void send(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver, @android.annotation.Nullable android.os.Parcelable parcelable) {
        send(iResultReceiver, com.android.internal.util.SyncResultReceiver.bundleFor(parcelable));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void send(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver, boolean z) {
        send(iResultReceiver, z ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void send(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver, int i, int i2) {
        try {
            iResultReceiver.send(i, com.android.internal.util.SyncResultReceiver.bundleFor(i2));
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Error async reporting result to client: " + e);
        }
    }

    public boolean isPccClassificationFlagEnabled() {
        boolean z;
        synchronized (this.mFlagLock) {
            z = this.mPccClassificationEnabled;
        }
        return z;
    }

    public boolean isAutofillCredmanIntegrationEnabled() {
        boolean z;
        synchronized (this.mFlagLock) {
            z = this.mAutofillCredmanIntegrationEnabled;
        }
        return z;
    }

    public boolean preferProviderOverPcc() {
        boolean z;
        synchronized (this.mFlagLock) {
            z = this.mPccPreferProviderOverPcc;
        }
        return z;
    }

    public boolean shouldUsePccFallback() {
        boolean z;
        synchronized (this.mFlagLock) {
            z = this.mPccUseFallbackDetection;
        }
        return z;
    }

    public java.lang.String getPccProviderHints() {
        java.lang.String str;
        synchronized (this.mFlagLock) {
            str = this.mPccProviderHints;
        }
        return str;
    }

    public int getMaxInputLengthForAutofill() {
        int i;
        synchronized (this.mFlagLock) {
            i = this.mMaxInputLengthForAutofill;
        }
        return i;
    }

    public boolean getIsFillFieldsFromCurrentSessionOnly() {
        boolean z;
        synchronized (this.mFlagLock) {
            z = this.mIsFillFieldsFromCurrentSessionOnly;
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    static java.util.Map<java.lang.String, java.lang.String[]> getAllowedCompatModePackages(java.lang.String str) {
        java.util.ArrayList arrayList;
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(COMPAT_PACKAGE_DELIMITER);
        simpleStringSplitter.setString(str);
        while (simpleStringSplitter.hasNext()) {
            java.lang.String next = simpleStringSplitter.next();
            int indexOf = next.indexOf(91);
            if (indexOf == -1) {
                arrayList = null;
            } else if (next.charAt(next.length() - 1) != ']') {
                android.util.Slog.w(TAG, "Ignoring entry '" + next + "' on '" + str + "'because it does not end on '" + COMPAT_PACKAGE_URL_IDS_BLOCK_END + "'");
            } else {
                java.lang.String substring = next.substring(0, indexOf);
                arrayList = new java.util.ArrayList();
                java.lang.String substring2 = next.substring(indexOf + 1, next.length() - 1);
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "pkg:" + substring + ": block:" + next + ": urls:" + arrayList + ": block:" + substring2 + ":");
                }
                android.text.TextUtils.SimpleStringSplitter simpleStringSplitter2 = new android.text.TextUtils.SimpleStringSplitter(COMPAT_PACKAGE_URL_IDS_DELIMITER);
                simpleStringSplitter2.setString(substring2);
                while (simpleStringSplitter2.hasNext()) {
                    arrayList.add(simpleStringSplitter2.next());
                }
                next = substring;
            }
            if (arrayList == null) {
                arrayMap.put(next, null);
            } else {
                java.lang.String[] strArr = new java.lang.String[arrayList.size()];
                arrayList.toArray(strArr);
                arrayMap.put(next, strArr);
            }
        }
        return arrayMap;
    }

    public static int getPartitionMaxCount() {
        int i;
        synchronized (sLock) {
            i = sPartitionMaxCount;
        }
        return i;
    }

    public static int getVisibleDatasetsMaxCount() {
        int i;
        synchronized (sLock) {
            i = sVisibleDatasetsMaxCount;
        }
        return i;
    }

    private final class LocalService extends android.view.autofill.AutofillManagerInternal {
        private LocalService() {
        }

        public void onBackKeyPressed() {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(com.android.server.autofill.AutofillManagerService.TAG, "onBackKeyPressed()");
            }
            com.android.server.autofill.AutofillManagerService.this.mUi.hideAll(null);
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                com.android.server.autofill.AutofillManagerService.this.getServiceForUserWithLocalBinderIdentityLocked(android.os.UserHandle.getCallingUserId()).onBackKeyPressed();
            }
        }

        public android.content.AutofillOptions getAutofillOptions(@android.annotation.NonNull java.lang.String str, long j, int i) {
            int i2;
            if (com.android.server.autofill.AutofillManagerService.this.verbose) {
                i2 = 6;
            } else if (com.android.server.autofill.AutofillManagerService.this.debug) {
                i2 = 2;
            } else {
                i2 = 0;
            }
            android.content.AutofillOptions autofillOptions = new android.content.AutofillOptions(i2, com.android.server.autofill.AutofillManagerService.this.mAutofillCompatState.isCompatibilityModeRequested(str, j, i));
            com.android.server.autofill.AutofillManagerService.this.mAugmentedAutofillState.injectAugmentedAutofillInfo(autofillOptions, i, str);
            injectDisableAppInfo(autofillOptions, i, str);
            return autofillOptions;
        }

        public boolean isAugmentedAutofillServiceForUser(int i, int i2) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                try {
                    com.android.server.autofill.AutofillManagerServiceImpl autofillManagerServiceImpl = (com.android.server.autofill.AutofillManagerServiceImpl) com.android.server.autofill.AutofillManagerService.this.peekServiceForUserLocked(i2);
                    if (autofillManagerServiceImpl != null) {
                        return autofillManagerServiceImpl.isAugmentedAutofillServiceForUserLocked(i);
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void injectDisableAppInfo(@android.annotation.NonNull android.content.AutofillOptions autofillOptions, int i, java.lang.String str) {
            autofillOptions.appDisabledExpiration = com.android.server.autofill.AutofillManagerService.this.mDisabledInfoCache.getAppDisabledExpiration(i, str);
            autofillOptions.disabledActivities = com.android.server.autofill.AutofillManagerService.this.mDisabledInfoCache.getAppDisabledActivities(i, str);
        }
    }

    static final class PackageCompatState {
        private final long maxVersionCode;
        private final java.lang.String[] urlBarResourceIds;

        PackageCompatState(long j, java.lang.String[] strArr) {
            this.maxVersionCode = j;
            this.urlBarResourceIds = strArr;
        }

        public java.lang.String toString() {
            return "maxVersionCode=" + this.maxVersionCode + ", urlBarResourceIds=" + java.util.Arrays.toString(this.urlBarResourceIds);
        }
    }

    static final class DisabledInfoCache {
        private final java.lang.Object mLock = new java.lang.Object();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseArray<com.android.server.autofill.AutofillManagerService.AutofillDisabledInfo> mCache = new android.util.SparseArray<>();

        DisabledInfoCache() {
        }

        void remove(int i) {
            synchronized (this.mLock) {
                this.mCache.remove(i);
            }
        }

        void addDisabledAppLocked(int i, @android.annotation.NonNull java.lang.String str, long j) {
            java.util.Objects.requireNonNull(str);
            synchronized (this.mLock) {
                getOrCreateAutofillDisabledInfoByUserIdLocked(i).putDisableAppsLocked(str, j);
            }
        }

        void addDisabledActivityLocked(int i, @android.annotation.NonNull android.content.ComponentName componentName, long j) {
            java.util.Objects.requireNonNull(componentName);
            synchronized (this.mLock) {
                getOrCreateAutofillDisabledInfoByUserIdLocked(i).putDisableActivityLocked(componentName, j);
            }
        }

        boolean isAutofillDisabledLocked(int i, @android.annotation.NonNull android.content.ComponentName componentName) {
            boolean isAutofillDisabledLocked;
            java.util.Objects.requireNonNull(componentName);
            synchronized (this.mLock) {
                try {
                    com.android.server.autofill.AutofillManagerService.AutofillDisabledInfo autofillDisabledInfo = this.mCache.get(i);
                    isAutofillDisabledLocked = autofillDisabledInfo != null ? autofillDisabledInfo.isAutofillDisabledLocked(componentName) : false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return isAutofillDisabledLocked;
        }

        long getAppDisabledExpiration(int i, @android.annotation.NonNull java.lang.String str) {
            java.lang.Long valueOf;
            java.util.Objects.requireNonNull(str);
            synchronized (this.mLock) {
                try {
                    com.android.server.autofill.AutofillManagerService.AutofillDisabledInfo autofillDisabledInfo = this.mCache.get(i);
                    valueOf = java.lang.Long.valueOf(autofillDisabledInfo != null ? autofillDisabledInfo.getAppDisabledExpirationLocked(str) : 0L);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return valueOf.longValue();
        }

        @android.annotation.Nullable
        android.util.ArrayMap<java.lang.String, java.lang.Long> getAppDisabledActivities(int i, @android.annotation.NonNull java.lang.String str) {
            android.util.ArrayMap<java.lang.String, java.lang.Long> appDisabledActivitiesLocked;
            java.util.Objects.requireNonNull(str);
            synchronized (this.mLock) {
                try {
                    com.android.server.autofill.AutofillManagerService.AutofillDisabledInfo autofillDisabledInfo = this.mCache.get(i);
                    appDisabledActivitiesLocked = autofillDisabledInfo != null ? autofillDisabledInfo.getAppDisabledActivitiesLocked(str) : null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return appDisabledActivitiesLocked;
        }

        void dump(int i, java.lang.String str, java.io.PrintWriter printWriter) {
            synchronized (this.mLock) {
                try {
                    com.android.server.autofill.AutofillManagerService.AutofillDisabledInfo autofillDisabledInfo = this.mCache.get(i);
                    if (autofillDisabledInfo != null) {
                        autofillDisabledInfo.dumpLocked(str, printWriter);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.NonNull
        private com.android.server.autofill.AutofillManagerService.AutofillDisabledInfo getOrCreateAutofillDisabledInfoByUserIdLocked(int i) {
            com.android.server.autofill.AutofillManagerService.AutofillDisabledInfo autofillDisabledInfo = this.mCache.get(i);
            if (autofillDisabledInfo == null) {
                com.android.server.autofill.AutofillManagerService.AutofillDisabledInfo autofillDisabledInfo2 = new com.android.server.autofill.AutofillManagerService.AutofillDisabledInfo();
                this.mCache.put(i, autofillDisabledInfo2);
                return autofillDisabledInfo2;
            }
            return autofillDisabledInfo;
        }
    }

    private static final class AutofillDisabledInfo {
        private android.util.ArrayMap<android.content.ComponentName, java.lang.Long> mDisabledActivities;
        private android.util.ArrayMap<java.lang.String, java.lang.Long> mDisabledApps;

        private AutofillDisabledInfo() {
        }

        void putDisableAppsLocked(@android.annotation.NonNull java.lang.String str, long j) {
            if (this.mDisabledApps == null) {
                this.mDisabledApps = new android.util.ArrayMap<>(1);
            }
            this.mDisabledApps.put(str, java.lang.Long.valueOf(j));
        }

        void putDisableActivityLocked(@android.annotation.NonNull android.content.ComponentName componentName, long j) {
            if (this.mDisabledActivities == null) {
                this.mDisabledActivities = new android.util.ArrayMap<>(1);
            }
            this.mDisabledActivities.put(componentName, java.lang.Long.valueOf(j));
        }

        long getAppDisabledExpirationLocked(@android.annotation.NonNull java.lang.String str) {
            java.lang.Long l;
            if (this.mDisabledApps == null || (l = this.mDisabledApps.get(str)) == null) {
                return 0L;
            }
            return l.longValue();
        }

        android.util.ArrayMap<java.lang.String, java.lang.Long> getAppDisabledActivitiesLocked(@android.annotation.NonNull java.lang.String str) {
            android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap = null;
            if (this.mDisabledActivities == null) {
                return null;
            }
            int size = this.mDisabledActivities.size();
            for (int i = 0; i < size; i++) {
                android.content.ComponentName keyAt = this.mDisabledActivities.keyAt(i);
                if (str.equals(keyAt.getPackageName())) {
                    if (arrayMap == null) {
                        arrayMap = new android.util.ArrayMap<>();
                    }
                    arrayMap.put(keyAt.flattenToShortString(), java.lang.Long.valueOf(this.mDisabledActivities.valueAt(i).longValue()));
                }
            }
            return arrayMap;
        }

        boolean isAutofillDisabledLocked(@android.annotation.NonNull android.content.ComponentName componentName) {
            long j;
            java.lang.Long l;
            if (this.mDisabledActivities == null) {
                j = 0;
            } else {
                j = android.os.SystemClock.elapsedRealtime();
                java.lang.Long l2 = this.mDisabledActivities.get(componentName);
                if (l2 != null) {
                    if (l2.longValue() >= j) {
                        return true;
                    }
                    if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "Removing " + componentName.toShortString() + " from disabled list");
                    }
                    this.mDisabledActivities.remove(componentName);
                }
            }
            java.lang.String packageName = componentName.getPackageName();
            if (this.mDisabledApps == null || (l = this.mDisabledApps.get(packageName)) == null) {
                return false;
            }
            if (j == 0) {
                j = android.os.SystemClock.elapsedRealtime();
            }
            if (l.longValue() >= j) {
                return true;
            }
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "Removing " + packageName + " from disabled list");
            }
            this.mDisabledApps.remove(packageName);
            return false;
        }

        void dumpLocked(java.lang.String str, java.io.PrintWriter printWriter) {
            printWriter.print(str);
            printWriter.print("Disabled apps: ");
            if (this.mDisabledApps == null) {
                printWriter.println("N/A");
            } else {
                int size = this.mDisabledApps.size();
                printWriter.println(size);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                for (int i = 0; i < size; i++) {
                    java.lang.String keyAt = this.mDisabledApps.keyAt(i);
                    long longValue = this.mDisabledApps.valueAt(i).longValue();
                    sb.append(str);
                    sb.append(str);
                    sb.append(i);
                    sb.append(". ");
                    sb.append(keyAt);
                    sb.append(": ");
                    android.util.TimeUtils.formatDuration(longValue - elapsedRealtime, sb);
                    sb.append('\n');
                }
                printWriter.println(sb);
            }
            printWriter.print(str);
            printWriter.print("Disabled activities: ");
            if (this.mDisabledActivities == null) {
                printWriter.println("N/A");
                return;
            }
            int size2 = this.mDisabledActivities.size();
            printWriter.println(size2);
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
            for (int i2 = 0; i2 < size2; i2++) {
                android.content.ComponentName keyAt2 = this.mDisabledActivities.keyAt(i2);
                long longValue2 = this.mDisabledActivities.valueAt(i2).longValue();
                sb2.append(str);
                sb2.append(str);
                sb2.append(i2);
                sb2.append(". ");
                sb2.append(keyAt2);
                sb2.append(": ");
                android.util.TimeUtils.formatDuration(longValue2 - elapsedRealtime2, sb2);
                sb2.append('\n');
            }
            printWriter.println(sb2);
        }
    }

    static final class AutofillCompatState {
        private final java.lang.Object mLock = new java.lang.Object();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.util.SparseArray<android.util.ArrayMap<java.lang.String, com.android.server.autofill.AutofillManagerService.PackageCompatState>> mUserSpecs;

        AutofillCompatState() {
        }

        boolean isCompatibilityModeRequested(@android.annotation.NonNull java.lang.String str, long j, int i) {
            synchronized (this.mLock) {
                try {
                    if (this.mUserSpecs == null) {
                        return false;
                    }
                    android.util.ArrayMap<java.lang.String, com.android.server.autofill.AutofillManagerService.PackageCompatState> arrayMap = this.mUserSpecs.get(i);
                    if (arrayMap == null) {
                        return false;
                    }
                    com.android.server.autofill.AutofillManagerService.PackageCompatState packageCompatState = arrayMap.get(str);
                    if (packageCompatState == null) {
                        return false;
                    }
                    return j <= packageCompatState.maxVersionCode;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.Nullable
        java.lang.String[] getUrlBarResourceIds(@android.annotation.NonNull java.lang.String str, int i) {
            synchronized (this.mLock) {
                try {
                    if (this.mUserSpecs == null) {
                        return null;
                    }
                    android.util.ArrayMap<java.lang.String, com.android.server.autofill.AutofillManagerService.PackageCompatState> arrayMap = this.mUserSpecs.get(i);
                    if (arrayMap == null) {
                        return null;
                    }
                    com.android.server.autofill.AutofillManagerService.PackageCompatState packageCompatState = arrayMap.get(str);
                    if (packageCompatState == null) {
                        return null;
                    }
                    return packageCompatState.urlBarResourceIds;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void addCompatibilityModeRequest(@android.annotation.NonNull java.lang.String str, long j, @android.annotation.Nullable java.lang.String[] strArr, int i) {
            synchronized (this.mLock) {
                try {
                    if (this.mUserSpecs == null) {
                        this.mUserSpecs = new android.util.SparseArray<>();
                    }
                    android.util.ArrayMap<java.lang.String, com.android.server.autofill.AutofillManagerService.PackageCompatState> arrayMap = this.mUserSpecs.get(i);
                    if (arrayMap == null) {
                        arrayMap = new android.util.ArrayMap<>();
                        this.mUserSpecs.put(i, arrayMap);
                    }
                    arrayMap.put(str, new com.android.server.autofill.AutofillManagerService.PackageCompatState(j, strArr));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void removeCompatibilityModeRequests(int i) {
            synchronized (this.mLock) {
                try {
                    if (this.mUserSpecs != null) {
                        this.mUserSpecs.remove(i);
                        if (this.mUserSpecs.size() <= 0) {
                            this.mUserSpecs = null;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void reset(int i) {
            synchronized (this.mLock) {
                try {
                    if (this.mUserSpecs != null) {
                        this.mUserSpecs.delete(i);
                        int size = this.mUserSpecs.size();
                        if (size == 0) {
                            if (com.android.server.autofill.Helper.sVerbose) {
                                android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "reseting mUserSpecs");
                            }
                            this.mUserSpecs = null;
                        } else if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "mUserSpecs down to " + size);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
            synchronized (this.mLock) {
                try {
                    if (this.mUserSpecs == null) {
                        printWriter.println("N/A");
                        return;
                    }
                    printWriter.println();
                    java.lang.String str2 = str + "  ";
                    for (int i = 0; i < this.mUserSpecs.size(); i++) {
                        int keyAt = this.mUserSpecs.keyAt(i);
                        printWriter.print(str);
                        printWriter.print("User: ");
                        printWriter.println(keyAt);
                        android.util.ArrayMap<java.lang.String, com.android.server.autofill.AutofillManagerService.PackageCompatState> valueAt = this.mUserSpecs.valueAt(i);
                        for (int i2 = 0; i2 < valueAt.size(); i2++) {
                            java.lang.String keyAt2 = valueAt.keyAt(i2);
                            com.android.server.autofill.AutofillManagerService.PackageCompatState valueAt2 = valueAt.valueAt(i2);
                            printWriter.print(str2);
                            printWriter.print(keyAt2);
                            printWriter.print(": ");
                            printWriter.println(valueAt2);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    static final class AugmentedAutofillState extends com.android.internal.infra.GlobalWhitelistState {

        @com.android.internal.annotations.GuardedBy({"mGlobalWhitelistStateLock"})
        private final android.util.SparseArray<java.lang.String> mServicePackages = new android.util.SparseArray<>();

        @com.android.internal.annotations.GuardedBy({"mGlobalWhitelistStateLock"})
        private final android.util.SparseBooleanArray mTemporaryServices = new android.util.SparseBooleanArray();

        AugmentedAutofillState() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setServiceInfo(int i, @android.annotation.Nullable java.lang.String str, boolean z) {
            synchronized (((com.android.internal.infra.GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
                try {
                    if (z) {
                        this.mTemporaryServices.put(i, true);
                    } else {
                        this.mTemporaryServices.delete(i);
                    }
                    if (str != null) {
                        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
                        if (unflattenFromString == null) {
                            android.util.Slog.w(com.android.server.autofill.AutofillManagerService.TAG, "setServiceInfo(): invalid name: " + str);
                            this.mServicePackages.remove(i);
                        } else {
                            this.mServicePackages.put(i, unflattenFromString.getPackageName());
                        }
                    } else {
                        this.mServicePackages.remove(i);
                    }
                } finally {
                }
            }
        }

        public void injectAugmentedAutofillInfo(@android.annotation.NonNull android.content.AutofillOptions autofillOptions, int i, @android.annotation.NonNull java.lang.String str) {
            synchronized (((com.android.internal.infra.GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
                try {
                    if (((com.android.internal.infra.GlobalWhitelistState) this).mWhitelisterHelpers == null) {
                        return;
                    }
                    com.android.internal.infra.WhitelistHelper whitelistHelper = (com.android.internal.infra.WhitelistHelper) ((com.android.internal.infra.GlobalWhitelistState) this).mWhitelisterHelpers.get(i);
                    if (whitelistHelper != null) {
                        autofillOptions.augmentedAutofillEnabled = whitelistHelper.isWhitelisted(str);
                        autofillOptions.whitelistedActivitiesForAugmentedAutofill = whitelistHelper.getWhitelistedComponents(str);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean isWhitelisted(int i, @android.annotation.NonNull android.content.ComponentName componentName) {
            synchronized (((com.android.internal.infra.GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
                try {
                    if (!super.isWhitelisted(i, componentName)) {
                        return false;
                    }
                    if (android.os.Build.IS_USER && this.mTemporaryServices.get(i)) {
                        java.lang.String packageName = componentName.getPackageName();
                        if (!packageName.equals(this.mServicePackages.get(i))) {
                            android.util.Slog.w(com.android.server.autofill.AutofillManagerService.TAG, "Ignoring package " + packageName + " for augmented autofill while using temporary service " + this.mServicePackages.get(i));
                            return false;
                        }
                    }
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void dump(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter) {
            super.dump(str, printWriter);
            synchronized (((com.android.internal.infra.GlobalWhitelistState) this).mGlobalWhitelistStateLock) {
                try {
                    if (this.mServicePackages.size() > 0) {
                        printWriter.print(str);
                        printWriter.print("Service packages: ");
                        printWriter.println(this.mServicePackages);
                    }
                    if (this.mTemporaryServices.size() > 0) {
                        printWriter.print(str);
                        printWriter.print("Temp services: ");
                        printWriter.println(this.mTemporaryServices);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class AutoFillManagerServiceStub extends android.view.autofill.IAutoFillManager.Stub {
        AutoFillManagerServiceStub() {
        }

        public void addClient(android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, android.content.ComponentName componentName, int i, com.android.internal.os.IResultReceiver iResultReceiver) {
            int i2 = 0;
            try {
                try {
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                        int addClientLocked = com.android.server.autofill.AutofillManagerService.this.getServiceForUserWithLocalBinderIdentityLocked(i).addClientLocked(iAutoFillManagerClient, componentName);
                        if (addClientLocked != 0) {
                            i2 = 0 | addClientLocked;
                        }
                        if (com.android.server.autofill.Helper.sDebug) {
                            i2 |= 2;
                        }
                        if (com.android.server.autofill.Helper.sVerbose) {
                            i2 |= 4;
                        }
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "addClient(): failed " + e.toString());
                }
            } finally {
                com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, i2);
            }
        }

        public void removeClient(android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, int i) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                try {
                    com.android.server.autofill.AutofillManagerServiceImpl autofillManagerServiceImpl = (com.android.server.autofill.AutofillManagerServiceImpl) com.android.server.autofill.AutofillManagerService.this.peekServiceForUserLocked(i);
                    if (autofillManagerServiceImpl != null) {
                        autofillManagerServiceImpl.removeClientLocked(iAutoFillManagerClient);
                    } else if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "removeClient(): no service for " + i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setAuthenticationResult(android.os.Bundle bundle, int i, int i2, int i3) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                com.android.server.autofill.AutofillManagerService.this.getServiceForUserWithLocalBinderIdentityLocked(i3).setAuthenticationResultLocked(bundle, i, i2, android.view.autofill.IAutoFillManager.Stub.getCallingUid());
            }
        }

        public void setHasCallback(int i, int i2, boolean z) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                com.android.server.autofill.AutofillManagerService.this.getServiceForUserWithLocalBinderIdentityLocked(i2).setHasCallback(i, android.view.autofill.IAutoFillManager.Stub.getCallingUid(), z);
            }
        }

        public void startSession(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i, boolean z, int i2, android.content.ComponentName componentName, boolean z2, com.android.internal.os.IResultReceiver iResultReceiver) {
            long startSessionLocked;
            java.util.Objects.requireNonNull(iBinder, "activityToken");
            java.util.Objects.requireNonNull(iBinder2, "clientCallback");
            java.util.Objects.requireNonNull(autofillId, "autofillId");
            java.util.Objects.requireNonNull(componentName, "clientActivity");
            java.lang.String packageName = componentName.getPackageName();
            java.util.Objects.requireNonNull(packageName);
            com.android.internal.util.Preconditions.checkArgument(i == android.os.UserHandle.getUserId(android.view.autofill.IAutoFillManager.Stub.getCallingUid()), "userId");
            try {
                com.android.server.autofill.AutofillManagerService.this.getContext().getPackageManager().getPackageInfoAsUser(packageName, 0, i);
                int taskIdForActivity = com.android.server.autofill.AutofillManagerService.this.mAm.getTaskIdForActivity(iBinder, false);
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                    startSessionLocked = com.android.server.autofill.AutofillManagerService.this.getServiceForUserWithLocalBinderIdentityLocked(i).startSessionLocked(iBinder, taskIdForActivity, android.view.autofill.IAutoFillManager.Stub.getCallingUid(), iBinder2, autofillId, rect, autofillValue, z, componentName, z2, ((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mAllowInstantService, i2);
                }
                int i3 = (int) startSessionLocked;
                int i4 = (int) (startSessionLocked >> 32);
                if (i4 != 0) {
                    com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, i3, i4);
                } else {
                    com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, i3);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new java.lang.IllegalArgumentException(packageName + " is not a valid package", e);
            }
        }

        public void getFillEventHistory(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            android.service.autofill.FillEventHistory fillEventHistory = null;
            try {
                try {
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                        try {
                            com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                fillEventHistory = peekServiceForUserWithLocalBinderIdentityLocked.getFillEventHistory(android.view.autofill.IAutoFillManager.Stub.getCallingUid());
                            } else if (com.android.server.autofill.Helper.sVerbose) {
                                android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "getFillEventHistory(): no service for " + callingUserId);
                            }
                        } finally {
                        }
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "getFillEventHistory(): failed " + e.toString());
                }
            } finally {
                com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, fillEventHistory);
            }
        }

        public void getUserData(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            android.service.autofill.UserData userData = null;
            try {
                try {
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                        try {
                            com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                userData = peekServiceForUserWithLocalBinderIdentityLocked.getUserData(android.view.autofill.IAutoFillManager.Stub.getCallingUid());
                            } else if (com.android.server.autofill.Helper.sVerbose) {
                                android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "getUserData(): no service for " + callingUserId);
                            }
                        } finally {
                        }
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "getUserData(): failed " + e.toString());
                }
            } finally {
                com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, userData);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:40:0x0082  */
        /* JADX WARN: Type inference failed for: r1v0, types: [android.service.autofill.UserData] */
        /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void getUserDataId(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            android.service.autofill.UserData userData;
            android.service.autofill.UserData userData2;
            int callingUserId = android.os.UserHandle.getCallingUserId();
            r1 = null;
            java.lang.String id = null;
            try {
                try {
                } catch (java.lang.Exception e) {
                    e = e;
                    userData = null;
                } catch (java.lang.Throwable th) {
                    th = th;
                    com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, 0 != 0 ? r1.getId() : 0);
                    throw th;
                }
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                    try {
                        com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                        if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                            userData2 = peekServiceForUserWithLocalBinderIdentityLocked.getUserData(android.view.autofill.IAutoFillManager.Stub.getCallingUid());
                        } else {
                            if (com.android.server.autofill.Helper.sVerbose) {
                                android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "getUserDataId(): no service for " + callingUserId);
                            }
                            userData2 = null;
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        userData = null;
                    }
                    try {
                        if (userData2 != null) {
                            id = userData2.getId();
                        }
                        com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, id);
                    } catch (java.lang.Throwable th3) {
                        userData = userData2;
                        th = th3;
                        while (true) {
                            try {
                                try {
                                    throw th;
                                } catch (java.lang.Exception e2) {
                                    e = e2;
                                    android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "getUserDataId(): failed " + e.toString());
                                    if (userData != null) {
                                        id = userData.getId();
                                    }
                                    com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, id);
                                }
                            } catch (java.lang.Throwable th4) {
                                th = th4;
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th5) {
                th = th5;
                com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, 0 != 0 ? r1.getId() : 0);
                throw th;
            }
        }

        public void setUserData(android.service.autofill.UserData userData) throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                try {
                    com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.setUserData(android.view.autofill.IAutoFillManager.Stub.getCallingUid(), userData);
                    } else if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "setUserData(): no service for " + callingUserId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void isFieldClassificationEnabled(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            boolean z = false;
            try {
                try {
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                        try {
                            com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                z = peekServiceForUserWithLocalBinderIdentityLocked.isFieldClassificationEnabled(android.view.autofill.IAutoFillManager.Stub.getCallingUid());
                            } else if (com.android.server.autofill.Helper.sVerbose) {
                                android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "isFieldClassificationEnabled(): no service for " + callingUserId);
                            }
                        } finally {
                        }
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "isFieldClassificationEnabled(): failed " + e.toString());
                }
            } finally {
                com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, z);
            }
        }

        public void getDefaultFieldClassificationAlgorithm(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            java.lang.String str = null;
            try {
                try {
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                        try {
                            com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                str = peekServiceForUserWithLocalBinderIdentityLocked.getDefaultFieldClassificationAlgorithm(android.view.autofill.IAutoFillManager.Stub.getCallingUid());
                            } else if (com.android.server.autofill.Helper.sVerbose) {
                                android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "getDefaultFcAlgorithm(): no service for " + callingUserId);
                            }
                        } finally {
                        }
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "getDefaultFieldClassificationAlgorithm(): failed " + e.toString());
                }
            } finally {
                com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, str);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0040, code lost:
        
            r8 = r6.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0042, code lost:
        
            if (r7 == false) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x0044, code lost:
        
            r1 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0047, code lost:
        
            r8.send(r9, r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0083, code lost:
        
            return;
         */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0088  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void setAugmentedAutofillWhitelist(@android.annotation.Nullable java.util.List<java.lang.String> list, @android.annotation.Nullable java.util.List<android.content.ComponentName> list2, @android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            boolean z;
            boolean z2;
            int callingUserId = android.os.UserHandle.getCallingUserId();
            try {
                try {
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                        try {
                            com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                z2 = peekServiceForUserWithLocalBinderIdentityLocked.setAugmentedAutofillWhitelistLocked(list, list2, android.view.autofill.IAutoFillManager.Stub.getCallingUid());
                            } else {
                                if (com.android.server.autofill.Helper.sVerbose) {
                                    android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "setAugmentedAutofillWhitelist(): no service for " + callingUserId);
                                }
                                z2 = false;
                            }
                            try {
                            } catch (java.lang.Throwable th) {
                                z = z2;
                                th = th;
                                while (true) {
                                    try {
                                        break;
                                    } catch (java.lang.Throwable th2) {
                                        th = th2;
                                    }
                                }
                                throw th;
                            }
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                            z = false;
                        }
                    }
                } catch (java.lang.Exception e) {
                    e = e;
                    z = false;
                } catch (java.lang.Throwable th4) {
                    th = th4;
                    list2 = null;
                    com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, list2 != null ? 0 : -1);
                    throw th;
                }
                try {
                    break;
                    throw th;
                } catch (java.lang.Exception e2) {
                    e = e2;
                    android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "setAugmentedAutofillWhitelist(): failed " + e.toString());
                    com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, z ? 0 : -1);
                }
            } catch (java.lang.Throwable th5) {
                th = th5;
                com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, list2 != null ? 0 : -1);
                throw th;
            }
        }

        public void getAvailableFieldClassificationAlgorithms(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            java.lang.String[] strArr = null;
            try {
                synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                    try {
                        com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                        if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                            strArr = peekServiceForUserWithLocalBinderIdentityLocked.getAvailableFieldClassificationAlgorithms(android.view.autofill.IAutoFillManager.Stub.getCallingUid());
                        } else if (com.android.server.autofill.Helper.sVerbose) {
                            android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "getAvailableFcAlgorithms(): no service for " + callingUserId);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "getAvailableFieldClassificationAlgorithms(): failed " + e.toString());
            } finally {
                com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, strArr);
            }
        }

        public void getAutofillServiceComponentName(@android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            android.content.ComponentName componentName = null;
            try {
                try {
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                        try {
                            com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                componentName = peekServiceForUserWithLocalBinderIdentityLocked.getServiceComponentName();
                            } else if (com.android.server.autofill.Helper.sVerbose) {
                                android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "getAutofillServiceComponentName(): no service for " + callingUserId);
                            }
                        } finally {
                        }
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "getAutofillServiceComponentName(): failed " + e.toString());
                }
            } finally {
                com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, componentName);
            }
        }

        public void restoreSession(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2, @android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            boolean z = false;
            try {
                try {
                    java.util.Objects.requireNonNull(iBinder, "activityToken");
                    java.util.Objects.requireNonNull(iBinder2, "appCallback");
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                        try {
                            com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(callingUserId);
                            if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                                z = peekServiceForUserWithLocalBinderIdentityLocked.restoreSession(i, android.view.autofill.IAutoFillManager.Stub.getCallingUid(), iBinder, iBinder2);
                            } else if (com.android.server.autofill.Helper.sVerbose) {
                                android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "restoreSession(): no service for " + callingUserId);
                            }
                        } finally {
                        }
                    }
                } finally {
                    com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, z);
                }
            } catch (java.lang.Exception e) {
                android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "restoreSession(): failed " + e.toString());
            }
        }

        public void updateSession(int i, android.view.autofill.AutofillId autofillId, android.graphics.Rect rect, android.view.autofill.AutofillValue autofillValue, int i2, int i3, int i4) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                try {
                    com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i4);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.updateSessionLocked(i, android.view.autofill.IAutoFillManager.Stub.getCallingUid(), autofillId, rect, autofillValue, i2, i3);
                    } else if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "updateSession(): no service for " + i4);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setAutofillFailure(int i, @android.annotation.NonNull java.util.List<android.view.autofill.AutofillId> list, int i2) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                try {
                    com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i2);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.setAutofillFailureLocked(i, android.view.autofill.IAutoFillManager.Stub.getCallingUid(), list);
                    } else if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "setAutofillFailure(): no service for " + i2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void finishSession(int i, int i2, int i3) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                try {
                    com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i2);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.finishSessionLocked(i, android.view.autofill.IAutoFillManager.Stub.getCallingUid(), i3);
                    } else if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "finishSession(): no service for " + i2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void cancelSession(int i, int i2) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                try {
                    com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i2);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.cancelSessionLocked(i, android.view.autofill.IAutoFillManager.Stub.getCallingUid());
                    } else if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "cancelSession(): no service for " + i2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void disableOwnedAutofillServices(int i) {
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                try {
                    com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i);
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.disableOwnedAutofillServicesLocked(android.os.Binder.getCallingUid());
                    } else if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(com.android.server.autofill.AutofillManagerService.TAG, "cancelSession(): no service for " + i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void isServiceSupported(int i, @android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) {
            try {
                try {
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                        r0 = com.android.server.autofill.AutofillManagerService.this.isDisabledLocked(i) ? false : true;
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "isServiceSupported(): failed " + e.toString());
                }
            } finally {
                com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, r0);
            }
        }

        public void isServiceEnabled(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.internal.os.IResultReceiver iResultReceiver) {
            boolean z = false;
            try {
                try {
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                        z = java.util.Objects.equals(str, com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(i).getServicePackageName());
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.wtf(com.android.server.autofill.AutofillManagerService.TAG, "isServiceEnabled(): failed " + e.toString());
                }
            } finally {
                com.android.server.autofill.AutofillManagerService.this.send(iResultReceiver, z);
            }
        }

        public void onPendingSaveUi(int i, android.os.IBinder iBinder) {
            java.util.Objects.requireNonNull(iBinder, "token");
            boolean z = true;
            if (i != 1 && i != 2) {
                z = false;
            }
            com.android.internal.util.Preconditions.checkArgument(z, "invalid operation: %d", new java.lang.Object[]{java.lang.Integer.valueOf(i)});
            synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                try {
                    com.android.server.autofill.AutofillManagerServiceImpl peekServiceForUserWithLocalBinderIdentityLocked = com.android.server.autofill.AutofillManagerService.this.peekServiceForUserWithLocalBinderIdentityLocked(android.os.UserHandle.getCallingUserId());
                    if (peekServiceForUserWithLocalBinderIdentityLocked != null) {
                        peekServiceForUserWithLocalBinderIdentityLocked.onPendingSaveUi(i, iBinder);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            boolean z;
            char c;
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.autofill.AutofillManagerService.this.getContext(), com.android.server.autofill.AutofillManagerService.TAG, printWriter)) {
                boolean z2 = false;
                if (strArr == null) {
                    z = true;
                } else {
                    boolean z3 = false;
                    z = true;
                    for (java.lang.String str : strArr) {
                        switch (str.hashCode()) {
                            case 900765093:
                                if (str.equals("--ui-only")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1098711592:
                                if (str.equals("--no-history")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1333069025:
                                if (str.equals("--help")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                z = false;
                                break;
                            case 1:
                                z3 = true;
                                break;
                            case 2:
                                printWriter.println("Usage: dumpsys autofill [--ui-only|--no-history]");
                                return;
                            default:
                                android.util.Slog.w(com.android.server.autofill.AutofillManagerService.TAG, "Ignoring invalid dump arg: " + str);
                                break;
                        }
                    }
                    z2 = z3;
                }
                if (z2) {
                    com.android.server.autofill.AutofillManagerService.this.mUi.dump(printWriter);
                    return;
                }
                boolean z4 = com.android.server.autofill.Helper.sDebug;
                boolean z5 = com.android.server.autofill.Helper.sVerbose;
                try {
                    com.android.server.autofill.Helper.sVerbose = true;
                    com.android.server.autofill.Helper.sDebug = true;
                    synchronized (((com.android.server.infra.AbstractMasterSystemService) com.android.server.autofill.AutofillManagerService.this).mLock) {
                        try {
                            printWriter.print("sDebug: ");
                            printWriter.print(z4);
                            printWriter.print(" sVerbose: ");
                            printWriter.println(z5);
                            printWriter.print("Flags: ");
                            synchronized (com.android.server.autofill.AutofillManagerService.this.mFlagLock) {
                                printWriter.print("mPccClassificationEnabled=");
                                printWriter.print(com.android.server.autofill.AutofillManagerService.this.mPccClassificationEnabled);
                                printWriter.print(";");
                                printWriter.print("mPccPreferProviderOverPcc=");
                                printWriter.print(com.android.server.autofill.AutofillManagerService.this.mPccPreferProviderOverPcc);
                                printWriter.print(";");
                                printWriter.print("mPccUseFallbackDetection=");
                                printWriter.print(com.android.server.autofill.AutofillManagerService.this.mPccUseFallbackDetection);
                                printWriter.print(";");
                                printWriter.print("mPccProviderHints=");
                                printWriter.println(com.android.server.autofill.AutofillManagerService.this.mPccProviderHints);
                                printWriter.print(";");
                                printWriter.print("mAutofillCredmanIntegrationEnabled=");
                                printWriter.println(com.android.server.autofill.AutofillManagerService.this.mAutofillCredmanIntegrationEnabled);
                            }
                            com.android.server.autofill.AutofillManagerService.this.dumpLocked("", printWriter);
                            com.android.server.autofill.AutofillManagerService.this.mAugmentedAutofillResolver.dumpShort(printWriter);
                            printWriter.println();
                            printWriter.print("Max partitions per session: ");
                            printWriter.println(com.android.server.autofill.AutofillManagerService.sPartitionMaxCount);
                            printWriter.print("Max visible datasets: ");
                            printWriter.println(com.android.server.autofill.AutofillManagerService.sVisibleDatasetsMaxCount);
                            if (com.android.server.autofill.Helper.sFullScreenMode != null) {
                                printWriter.print("Overridden full-screen mode: ");
                                printWriter.println(com.android.server.autofill.Helper.sFullScreenMode);
                            }
                            printWriter.println("User data constraints: ");
                            android.service.autofill.UserData.dumpConstraints("  ", printWriter);
                            com.android.server.autofill.AutofillManagerService.this.mUi.dump(printWriter);
                            printWriter.print("Autofill Compat State: ");
                            com.android.server.autofill.AutofillManagerService.this.mAutofillCompatState.dump("  ", printWriter);
                            printWriter.print("from device config: ");
                            printWriter.println(com.android.server.autofill.AutofillManagerService.this.getAllowedCompatModePackagesFromDeviceConfig());
                            if (com.android.server.autofill.AutofillManagerService.this.mSupportedSmartSuggestionModes != 0) {
                                printWriter.print("Smart Suggestion modes: ");
                                printWriter.println(android.view.autofill.AutofillManager.getSmartSuggestionModeToString(com.android.server.autofill.AutofillManagerService.this.mSupportedSmartSuggestionModes));
                            }
                            printWriter.print("Augmented Service Idle Unbind Timeout: ");
                            printWriter.println(com.android.server.autofill.AutofillManagerService.this.mAugmentedServiceIdleUnbindTimeoutMs);
                            printWriter.print("Augmented Service Request Timeout: ");
                            printWriter.println(com.android.server.autofill.AutofillManagerService.this.mAugmentedServiceRequestTimeoutMs);
                            if (z) {
                                printWriter.println();
                                printWriter.println("Requests history:");
                                printWriter.println();
                                com.android.server.autofill.AutofillManagerService.this.mRequestsHistory.reverseDump(fileDescriptor, printWriter, strArr);
                                printWriter.println();
                                printWriter.println("UI latency history:");
                                printWriter.println();
                                com.android.server.autofill.AutofillManagerService.this.mUiLatencyHistory.reverseDump(fileDescriptor, printWriter, strArr);
                                printWriter.println();
                                printWriter.println("WTF history:");
                                printWriter.println();
                                com.android.server.autofill.AutofillManagerService.this.mWtfHistory.reverseDump(fileDescriptor, printWriter, strArr);
                            }
                            printWriter.println("Augmented Autofill State: ");
                            com.android.server.autofill.AutofillManagerService.this.mAugmentedAutofillState.dump("  ", printWriter);
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                } finally {
                    com.android.server.autofill.Helper.sDebug = z4;
                    com.android.server.autofill.Helper.sVerbose = z5;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.autofill.AutofillManagerServiceShellCommand(com.android.server.autofill.AutofillManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }
}
