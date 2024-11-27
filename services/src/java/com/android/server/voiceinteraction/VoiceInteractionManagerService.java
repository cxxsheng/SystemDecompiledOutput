package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
public class VoiceInteractionManagerService extends com.android.server.SystemService {
    private static final java.lang.String CS_INTENT_FILTER = "com.android.contextualsearch.LAUNCH";
    private static final java.lang.String CS_KEY_FLAG_IS_MANAGED_PROFILE_VISIBLE = "com.android.contextualsearch.is_managed_profile_visible";
    private static final java.lang.String CS_KEY_FLAG_SCREENSHOT = "com.android.contextualsearch.screenshot";
    private static final java.lang.String CS_KEY_FLAG_SECURE_FOUND = "com.android.contextualsearch.flag_secure_found";
    private static final java.lang.String CS_KEY_FLAG_VISIBLE_PACKAGE_NAMES = "com.android.contextualsearch.visible_package_names";
    static final boolean DEBUG = false;
    static final java.lang.String TAG = "VoiceInteractionManager";
    final android.app.ActivityManagerInternal mAmInternal;
    final com.android.server.wm.ActivityTaskManagerInternal mAtmInternal;
    final android.content.Context mContext;
    private com.android.server.voiceinteraction.IEnrolledModelDb mDbHelper;
    final android.app.admin.DevicePolicyManagerInternal mDpmInternal;
    private final com.android.internal.app.IVoiceInteractionSessionListener mLatencyLoggingListener;
    final android.util.ArrayMap<java.lang.Integer, com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.SoundTriggerSession> mLoadedKeyphraseIds;
    private final com.android.server.voiceinteraction.IEnrolledModelDb mRealDbHelper;
    final android.content.ContentResolver mResolver;
    private final com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub mServiceStub;
    android.content.pm.ShortcutServiceInternal mShortcutServiceInternal;
    com.android.server.SoundTriggerInternal mSoundTriggerInternal;
    final com.android.server.pm.UserManagerInternal mUserManagerInternal;
    private com.android.internal.app.IVisualQueryRecognitionStatusListener mVisualQueryRecognitionStatusListener;
    private final android.os.RemoteCallbackList<com.android.internal.app.IVoiceInteractionSessionListener> mVoiceInteractionSessionListeners;
    final com.android.server.wm.WindowManagerInternal mWmInternal;

    public VoiceInteractionManagerService(android.content.Context context) {
        super(context);
        this.mLoadedKeyphraseIds = new android.util.ArrayMap<>();
        this.mVoiceInteractionSessionListeners = new android.os.RemoteCallbackList<>();
        this.mLatencyLoggingListener = new com.android.internal.app.IVoiceInteractionSessionListener.Stub() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService.3
            public void onVoiceSessionShown() throws android.os.RemoteException {
            }

            public void onVoiceSessionHidden() throws android.os.RemoteException {
            }

            public void onVoiceSessionWindowVisibilityChanged(boolean z) throws android.os.RemoteException {
                if (z) {
                    com.android.server.voiceinteraction.HotwordMetricsLogger.stopHotwordTriggerToUiLatencySession(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext);
                }
            }

            public void onSetUiHints(android.os.Bundle bundle) throws android.os.RemoteException {
            }

            public android.os.IBinder asBinder() {
                return com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub;
            }
        };
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        java.util.Objects.requireNonNull(userManagerInternal);
        this.mUserManagerInternal = userManagerInternal;
        com.android.server.voiceinteraction.DatabaseHelper databaseHelper = new com.android.server.voiceinteraction.DatabaseHelper(context);
        this.mRealDbHelper = databaseHelper;
        this.mDbHelper = databaseHelper;
        this.mServiceStub = new com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub();
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        java.util.Objects.requireNonNull(activityManagerInternal);
        this.mAmInternal = activityManagerInternal;
        com.android.server.wm.ActivityTaskManagerInternal activityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        java.util.Objects.requireNonNull(activityTaskManagerInternal);
        this.mAtmInternal = activityTaskManagerInternal;
        this.mWmInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        this.mDpmInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        ((com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class)).setVoiceInteractionPackagesProvider(new com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService.1
            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider
            public java.lang.String[] getPackages(int i) {
                com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub.initForUser(i);
                android.content.ComponentName curInteractor = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub.getCurInteractor(i);
                if (curInteractor != null) {
                    return new java.lang.String[]{curInteractor.getPackageName()};
                }
                return null;
            }
        });
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("voiceinteraction", this.mServiceStub);
        publishLocalService(android.service.voice.VoiceInteractionManagerInternal.class, new com.android.server.voiceinteraction.VoiceInteractionManagerService.LocalService());
        this.mAmInternal.setVoiceInteractionManagerProvider(new android.app.ActivityManagerInternal.VoiceInteractionManagerProvider() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService.2
            public void notifyActivityDestroyed(android.os.IBinder iBinder) {
                com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub.notifyActivityDestroyed(iBinder);
            }
        });
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (500 == i) {
            android.content.pm.ShortcutServiceInternal shortcutServiceInternal = (android.content.pm.ShortcutServiceInternal) com.android.server.LocalServices.getService(android.content.pm.ShortcutServiceInternal.class);
            java.util.Objects.requireNonNull(shortcutServiceInternal);
            this.mShortcutServiceInternal = shortcutServiceInternal;
            this.mSoundTriggerInternal = (com.android.server.SoundTriggerInternal) com.android.server.LocalServices.getService(com.android.server.SoundTriggerInternal.class);
            return;
        }
        if (i == 600) {
            this.mServiceStub.systemRunning(isSafeMode());
        } else if (i == 1000) {
            this.mServiceStub.registerVoiceInteractionSessionListener(this.mLatencyLoggingListener);
        }
    }

    @Override // com.android.server.SystemService
    public boolean isUserSupported(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        return targetUser.isFull();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUserSupported(@android.annotation.NonNull android.content.pm.UserInfo userInfo) {
        return userInfo.isFull();
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mServiceStub.initForUser(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mServiceStub.initForUser(targetUser.getUserIdentifier());
        this.mServiceStub.switchImplementationIfNeeded(false);
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        this.mServiceStub.switchUser(targetUser2.getUserIdentifier());
    }

    class LocalService extends android.service.voice.VoiceInteractionManagerInternal {
        LocalService() {
        }

        public void startLocalVoiceInteraction(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.os.Bundle bundle) {
            com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub.startLocalVoiceInteraction(iBinder, str, bundle);
        }

        public boolean supportsLocalVoiceInteraction() {
            return com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub.supportsLocalVoiceInteraction();
        }

        public void stopLocalVoiceInteraction(android.os.IBinder iBinder) {
            com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub.stopLocalVoiceInteraction(iBinder);
        }

        public boolean hasActiveSession(java.lang.String str) {
            com.android.server.voiceinteraction.VoiceInteractionSessionConnection voiceInteractionSessionConnection;
            com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub.mImpl;
            if (voiceInteractionManagerServiceImpl == null || (voiceInteractionSessionConnection = voiceInteractionManagerServiceImpl.mActiveSession) == null) {
                return false;
            }
            return android.text.TextUtils.equals(str, voiceInteractionSessionConnection.mSessionComponentName.getPackageName());
        }

        public java.lang.String getVoiceInteractorPackageName(android.os.IBinder iBinder) {
            com.android.server.voiceinteraction.VoiceInteractionSessionConnection voiceInteractionSessionConnection;
            com.android.internal.app.IVoiceInteractor iVoiceInteractor;
            com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub.mImpl;
            if (voiceInteractionManagerServiceImpl == null || (voiceInteractionSessionConnection = voiceInteractionManagerServiceImpl.mActiveSession) == null || (iVoiceInteractor = voiceInteractionSessionConnection.mInteractor) == null || iVoiceInteractor.asBinder() != iBinder) {
                return null;
            }
            return voiceInteractionSessionConnection.mSessionComponentName.getPackageName();
        }

        public android.service.voice.VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity getHotwordDetectionServiceIdentity() {
            com.android.server.voiceinteraction.HotwordDetectionConnection hotwordDetectionConnection;
            com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub.mImpl;
            if (voiceInteractionManagerServiceImpl == null || (hotwordDetectionConnection = voiceInteractionManagerServiceImpl.mHotwordDetectionConnection) == null) {
                return null;
            }
            return hotwordDetectionConnection.mIdentity;
        }

        public void onPreCreatedUserConversion(int i) {
            com.android.server.utils.Slogf.d(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "onPreCreatedUserConversion(%d): calling onRoleHoldersChanged() again", java.lang.Integer.valueOf(i));
            com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub.mRoleObserver.onRoleHoldersChanged("android.app.role.ASSISTANT", android.os.UserHandle.of(i));
        }

        public void startListeningFromWearable(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.content.ComponentName componentName, int i, android.service.voice.VoiceInteractionManagerInternal.WearableHotwordDetectionCallback wearableHotwordDetectionCallback) {
            android.util.Slog.d(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "#startListeningFromWearable");
            com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub.mImpl;
            if (voiceInteractionManagerServiceImpl == null) {
                wearableHotwordDetectionCallback.onError("Unable to start listening from wearable because the service impl is null.");
                return;
            }
            if (componentName != null && !componentName.equals(voiceInteractionManagerServiceImpl.mComponent)) {
                wearableHotwordDetectionCallback.onError(android.text.TextUtils.formatSimple("Unable to start listening from wearable because the target VoiceInteractionService %s is different from the current VoiceInteractionService %s", new java.lang.Object[]{componentName, voiceInteractionManagerServiceImpl.mComponent}));
            } else {
                if (i != voiceInteractionManagerServiceImpl.mUser) {
                    wearableHotwordDetectionCallback.onError(android.text.TextUtils.formatSimple("Unable to start listening from wearable because the target userId %s is different from the current VoiceInteractionManagerServiceImpl's userId %s", new java.lang.Object[]{java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(voiceInteractionManagerServiceImpl.mUser)}));
                    return;
                }
                synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub) {
                    voiceInteractionManagerServiceImpl.startListeningFromWearableLocked(parcelFileDescriptor, audioFormat, persistableBundle, wearableHotwordDetectionCallback);
                }
            }
        }
    }

    class VoiceInteractionManagerServiceStub extends com.android.internal.app.IVoiceInteractionManagerService.Stub {
        private static final int SHOW_SESSION_START_ID = 0;
        private final boolean IS_HDS_REQUIRED;
        private int mCurUser;
        private boolean mCurUserSupported;
        private final boolean mEnableService;
        volatile com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl mImpl;
        private final com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.RoleObserver mRoleObserver;
        private boolean mSafeMode;

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean mTemporarilyDisabled;

        @com.android.internal.annotations.GuardedBy({"this"})
        private int mShowSessionId = 0;
        com.android.internal.content.PackageMonitor mPackageMonitor = new com.android.internal.content.PackageMonitor() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.2
            public boolean onHandleForceStop(android.content.Intent intent, java.lang.String[] strArr, int i, boolean z) {
                boolean z2;
                boolean z3;
                int userId = android.os.UserHandle.getUserId(i);
                android.content.ComponentName curInteractor = com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.getCurInteractor(userId);
                android.content.ComponentName curRecognizer = com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.getCurRecognizer(userId);
                int length = strArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        z2 = false;
                        z3 = false;
                        break;
                    }
                    java.lang.String str = strArr[i2];
                    if (curInteractor != null && str.equals(curInteractor.getPackageName())) {
                        z3 = false;
                        z2 = true;
                        break;
                    }
                    if (curRecognizer == null || !str.equals(curRecognizer.getPackageName())) {
                        i2++;
                    } else {
                        z2 = false;
                        z3 = true;
                        break;
                    }
                }
                if (z2 && z) {
                    synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                        try {
                            android.util.Slog.i(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Force stopping current voice interactor: " + com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.getCurInteractor(userId));
                            com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.unloadAllKeyphraseModels();
                            if (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mImpl != null) {
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mImpl.shutdownLocked();
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.setImplLocked(null);
                            }
                            com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.switchImplementationIfNeededLocked(true);
                        } finally {
                        }
                    }
                } else if (z3 && z) {
                    synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                        android.util.Slog.i(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Force stopping current voice recognizer: " + com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.getCurRecognizer(userId));
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.initRecognizer(userId);
                    }
                }
                return z2 || z3;
            }

            public void onPackageModified(@android.annotation.NonNull java.lang.String str) {
                if (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mCurUser != getChangingUserId() || isPackageAppearing(str) != 0) {
                    return;
                }
                if (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.getCurRecognizer(com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mCurUser) == null) {
                    com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.initRecognizer(com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mCurUser);
                }
                java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_interaction_service", com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mCurUser);
                android.content.ComponentName curInteractor = com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.getCurInteractor(com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mCurUser);
                if (curInteractor == null && !"".equals(stringForUser)) {
                    android.service.voice.VoiceInteractionServiceInfo findAvailInteractor = com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.findAvailInteractor(com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mCurUser, str);
                    if (findAvailInteractor != null) {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.setCurInteractor(new android.content.ComponentName(findAvailInteractor.getServiceInfo().packageName, findAvailInteractor.getServiceInfo().name), com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mCurUser);
                        return;
                    }
                    return;
                }
                if (didSomePackagesChange()) {
                    if (curInteractor != null && str.equals(curInteractor.getPackageName())) {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.switchImplementationIfNeeded(true);
                        return;
                    }
                    return;
                }
                if (curInteractor != null && isComponentModified(curInteractor.getClassName())) {
                    com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.switchImplementationIfNeeded(true);
                }
            }

            public void onSomePackagesChanged() {
                int changingUserId = getChangingUserId();
                synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                    try {
                        android.content.ComponentName curInteractor = com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.getCurInteractor(changingUserId);
                        android.content.ComponentName curRecognizer = com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.getCurRecognizer(changingUserId);
                        android.content.ComponentName curAssistant = com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.getCurAssistant(changingUserId);
                        if (curRecognizer == null && anyPackagesAppearing()) {
                            com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.initRecognizer(changingUserId);
                        }
                        if (curInteractor != null) {
                            if (isPackageDisappearing(curInteractor.getPackageName()) == 3) {
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.setCurInteractor(null, changingUserId);
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.setCurRecognizer(null, changingUserId);
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.resetCurAssistant(changingUserId);
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.initForUser(changingUserId);
                                return;
                            }
                            if (isPackageAppearing(curInteractor.getPackageName()) != 0) {
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.resetServicesIfNoRecognitionService(curInteractor, changingUserId);
                                if (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mImpl != null && curInteractor.getPackageName().equals(com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mImpl.mComponent.getPackageName())) {
                                    com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.switchImplementationIfNeededLocked(true);
                                }
                            }
                            return;
                        }
                        if (curAssistant != null) {
                            if (isPackageDisappearing(curAssistant.getPackageName()) == 3) {
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.setCurInteractor(null, changingUserId);
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.setCurRecognizer(null, changingUserId);
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.resetCurAssistant(changingUserId);
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.initForUser(changingUserId);
                                return;
                            }
                            if (isPackageAppearing(curAssistant.getPackageName()) != 0) {
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.resetServicesIfNoRecognitionService(curAssistant, changingUserId);
                            }
                        }
                        if (curRecognizer != null) {
                            int isPackageDisappearing = isPackageDisappearing(curRecognizer.getPackageName());
                            if (isPackageDisappearing == 3 || isPackageDisappearing == 2) {
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.setCurRecognizer(com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.findAvailRecognizer(null, changingUserId), changingUserId);
                            } else if (isPackageModified(curRecognizer.getPackageName())) {
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.setCurRecognizer(com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.findAvailRecognizer(curRecognizer.getPackageName(), changingUserId), changingUserId);
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };

        VoiceInteractionManagerServiceStub() {
            this.IS_HDS_REQUIRED = com.android.server.policy.AppOpsPolicy.isHotwordDetectionServiceRequired(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getPackageManager());
            this.mEnableService = shouldEnableService(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext);
            this.mRoleObserver = new com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.RoleObserver(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getMainExecutor());
        }

        void handleUserStop(java.lang.String str, int i) {
            synchronized (this) {
                try {
                    android.content.ComponentName curInteractor = getCurInteractor(i);
                    if (curInteractor != null && str.equals(curInteractor.getPackageName())) {
                        android.util.Slog.d(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "switchImplementation for user stop.");
                        switchImplementationIfNeededLocked(true);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        int getNextShowSessionId() {
            int i;
            synchronized (this) {
                try {
                    if (this.mShowSessionId == 2147483646) {
                        this.mShowSessionId = 0;
                    }
                    this.mShowSessionId++;
                    i = this.mShowSessionId;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return i;
        }

        int getShowSessionId() {
            int i;
            synchronized (this) {
                i = this.mShowSessionId;
            }
            return i;
        }

        @android.annotation.NonNull
        public com.android.internal.app.IVoiceInteractionSoundTriggerSession createSoundTriggerSessionAsOriginator(@android.annotation.NonNull android.media.permission.Identity identity, android.os.IBinder iBinder, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties) {
            boolean z;
            boolean z2;
            java.util.Objects.requireNonNull(identity);
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    z = true;
                    z2 = (this.mImpl == null || this.mImpl.mHotwordDetectionConnection == null) ? false : true;
                } finally {
                }
            }
            android.media.permission.SafeCloseable establishIdentityDirect = android.media.permission.PermissionUtil.establishIdentityDirect(identity);
            try {
                if (this.IS_HDS_REQUIRED) {
                    z = z2;
                }
                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.SoundTriggerSession soundTriggerSession = new com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.SoundTriggerSession(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mSoundTriggerInternal.attach(iBinder, moduleProperties, z), identity);
                if (establishIdentityDirect != null) {
                    establishIdentityDirect.close();
                }
                return soundTriggerSession;
            } catch (java.lang.Throwable th) {
                if (establishIdentityDirect != null) {
                    try {
                        establishIdentityDirect.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties(android.media.permission.Identity identity) {
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
            }
            return com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mSoundTriggerInternal.listModuleProperties(identity);
        }

        void startLocalVoiceInteraction(@android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.os.Bundle bundle) {
            if (this.mImpl == null) {
                return;
            }
            final int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.voiceinteraction.HotwordMetricsLogger.cancelHotwordTriggerToUiLatencySession(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext);
                this.mImpl.showSessionLocked(bundle, 16, str, new com.android.internal.app.IVoiceInteractionSessionShowCallback.Stub() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.1
                    public void onFailed() {
                    }

                    public void onShown() {
                        synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                            try {
                                if (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mImpl != null) {
                                    com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mImpl.grantImplicitAccessLocked(callingUid, null);
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mAtmInternal.onLocalVoiceInteractionStarted(iBinder, com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mImpl.mActiveSession.mSession, com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.mImpl.mActiveSession.mInteractor);
                    }
                }, iBinder);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void stopLocalVoiceInteraction(android.os.IBinder iBinder) {
            if (this.mImpl == null) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mImpl.finishLocked(iBinder, true);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean supportsLocalVoiceInteraction() {
            if (this.mImpl == null) {
                return false;
            }
            return this.mImpl.supportsLocalVoiceInteraction();
        }

        void notifyActivityDestroyed(@android.annotation.NonNull final android.os.IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null || iBinder == null) {
                        return;
                    }
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda2
                        public final void runOrThrow() {
                            com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.lambda$notifyActivityDestroyed$0(iBinder);
                        }
                    });
                } finally {
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyActivityDestroyed$0(android.os.IBinder iBinder) throws java.lang.Exception {
            this.mImpl.notifyActivityDestroyedLocked(iBinder);
        }

        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (java.lang.RuntimeException e) {
                if (!(e instanceof java.lang.SecurityException)) {
                    android.util.Slog.wtf(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "VoiceInteractionManagerService Crash", e);
                }
                throw e;
            }
        }

        public void initForUser(int i) {
            initForUserNoTracing(i);
        }

        /* JADX WARN: Removed duplicated region for block: B:59:0x00d0  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x00e5  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void initForUserNoTracing(int i) {
            android.service.voice.VoiceInteractionServiceInfo voiceInteractionServiceInfo;
            android.content.ComponentName componentName;
            android.content.pm.ServiceInfo serviceInfo;
            android.content.pm.ServiceInfo serviceInfo2;
            java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_interaction_service", i);
            android.content.ComponentName curRecognizer = getCurRecognizer(i);
            if (stringForUser == null && curRecognizer != null && this.mEnableService) {
                voiceInteractionServiceInfo = findAvailInteractor(i, curRecognizer.getPackageName());
                if (voiceInteractionServiceInfo != null) {
                    curRecognizer = null;
                }
            } else {
                voiceInteractionServiceInfo = null;
            }
            java.lang.String forceVoiceInteractionServicePackage = getForceVoiceInteractionServicePackage(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getResources());
            if (forceVoiceInteractionServicePackage != null && (voiceInteractionServiceInfo = findAvailInteractor(i, forceVoiceInteractionServicePackage)) != null) {
                curRecognizer = null;
            }
            if (!this.mEnableService && stringForUser != null && !android.text.TextUtils.isEmpty(stringForUser)) {
                setCurInteractor(null, i);
                stringForUser = "";
            }
            if (curRecognizer != null) {
                android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
                if (android.text.TextUtils.isEmpty(stringForUser)) {
                    componentName = null;
                } else {
                    componentName = android.content.ComponentName.unflattenFromString(stringForUser);
                }
                try {
                    serviceInfo = packageManager.getServiceInfo(curRecognizer, 786560L, i);
                    if (serviceInfo != null) {
                        try {
                            com.android.server.voiceinteraction.RecognitionServiceInfo parseInfo = com.android.server.voiceinteraction.RecognitionServiceInfo.parseInfo(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getPackageManager(), serviceInfo);
                            if (!android.text.TextUtils.isEmpty(parseInfo.getParseError())) {
                                android.util.Log.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Parse error in getAvailableServices: " + parseInfo.getParseError());
                            }
                            if (!parseInfo.isSelectableAsDefault()) {
                                serviceInfo = null;
                            }
                        } catch (android.os.RemoteException e) {
                            serviceInfo2 = null;
                            if (serviceInfo != null) {
                                return;
                            }
                            if (voiceInteractionServiceInfo == null) {
                                voiceInteractionServiceInfo = findAvailInteractor(i, null);
                            }
                            if (voiceInteractionServiceInfo == null) {
                            }
                            initRecognizer(i);
                        }
                    }
                    if (componentName == null) {
                        serviceInfo2 = null;
                    } else {
                        serviceInfo2 = packageManager.getServiceInfo(componentName, 786432L, i);
                    }
                } catch (android.os.RemoteException e2) {
                    serviceInfo = null;
                }
                if (serviceInfo != null && (componentName == null || serviceInfo2 != null)) {
                    return;
                }
            }
            if (voiceInteractionServiceInfo == null && this.mEnableService && !"".equals(stringForUser)) {
                voiceInteractionServiceInfo = findAvailInteractor(i, null);
            }
            if (voiceInteractionServiceInfo == null) {
                setCurInteractor(new android.content.ComponentName(voiceInteractionServiceInfo.getServiceInfo().packageName, voiceInteractionServiceInfo.getServiceInfo().name), i);
            } else {
                setCurInteractor(null, i);
            }
            initRecognizer(i);
        }

        public void initRecognizer(int i) {
            android.content.ComponentName findAvailRecognizer = findAvailRecognizer(null, i);
            if (findAvailRecognizer != null) {
                setCurRecognizer(findAvailRecognizer, i);
            }
        }

        private boolean shouldEnableService(android.content.Context context) {
            if (getForceVoiceInteractionServicePackage(context.getResources()) != null) {
                return true;
            }
            return context.getPackageManager().hasSystemFeature("android.software.voice_recognizers");
        }

        private java.lang.String getForceVoiceInteractionServicePackage(android.content.res.Resources resources) {
            java.lang.String string = resources.getString(android.R.string.config_dreamsDefaultComponent);
            if (android.text.TextUtils.isEmpty(string)) {
                return null;
            }
            return string;
        }

        public void systemRunning(boolean z) {
            this.mSafeMode = z;
            this.mPackageMonitor.register(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext, com.android.internal.os.BackgroundThread.getHandler().getLooper(), android.os.UserHandle.ALL, true);
            new com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.SettingsObserver(com.android.server.UiThread.getHandler());
            synchronized (this) {
                setCurrentUserLocked(android.app.ActivityManager.getCurrentUser());
                switchImplementationIfNeededLocked(false);
            }
        }

        private void setCurrentUserLocked(int i) {
            this.mCurUser = i;
            this.mCurUserSupported = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.isUserSupported(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mUserManagerInternal.getUserInfo(this.mCurUser));
        }

        public void switchUser(final int i) {
            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.lambda$switchUser$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$switchUser$1(int i) {
            synchronized (this) {
                setCurrentUserLocked(i);
                switchImplementationIfNeededLocked(false);
            }
        }

        void switchImplementationIfNeeded(boolean z) {
            synchronized (this) {
                switchImplementationIfNeededLocked(z);
            }
        }

        void switchImplementationIfNeededLocked(boolean z) {
            if (!this.mCurUserSupported) {
                if (this.mImpl != null) {
                    this.mImpl.shutdownLocked();
                    setImplLocked(null);
                    return;
                }
                return;
            }
            switchImplementationIfNeededNoTracingLocked(z);
        }

        void switchImplementationIfNeededNoTracingLocked(boolean z) {
            android.content.pm.ServiceInfo serviceInfo;
            android.content.ComponentName componentName;
            if (!this.mSafeMode) {
                java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mResolver, "voice_interaction_service", this.mCurUser);
                if (stringForUser != null && !stringForUser.isEmpty()) {
                    try {
                        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(stringForUser);
                        serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(unflattenFromString, 0L, this.mCurUser);
                        componentName = unflattenFromString;
                    } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                        android.util.Slog.wtf(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Bad voice interaction service name " + stringForUser, e);
                        serviceInfo = null;
                        componentName = null;
                    }
                } else {
                    serviceInfo = null;
                    componentName = null;
                }
                boolean z2 = (componentName == null || serviceInfo == null) ? false : true;
                if (com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mUserManagerInternal.isUserUnlockingOrUnlocked(this.mCurUser)) {
                    if (!z2) {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mShortcutServiceInternal.setShortcutHostPackage(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, (java.lang.String) null, this.mCurUser);
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mAtmInternal.setAllowAppSwitches(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, -1, this.mCurUser);
                    } else {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mShortcutServiceInternal.setShortcutHostPackage(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, componentName.getPackageName(), this.mCurUser);
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mAtmInternal.setAllowAppSwitches(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, serviceInfo.applicationInfo.uid, this.mCurUser);
                    }
                }
                if (z || this.mImpl == null || this.mImpl.mUser != this.mCurUser || !this.mImpl.mComponent.equals(componentName)) {
                    unloadAllKeyphraseModels();
                    if (this.mImpl != null) {
                        this.mImpl.shutdownLocked();
                    }
                    if (z2) {
                        setImplLocked(new com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext, com.android.server.UiThread.getHandler(), this, this.mCurUser, componentName));
                        this.mImpl.startLocked();
                    } else {
                        setImplLocked(null);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.util.List<android.content.pm.ResolveInfo> queryInteractorServices(int i, @android.annotation.Nullable java.lang.String str) {
            return com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getPackageManager().queryIntentServicesAsUser(new android.content.Intent("android.service.voice.VoiceInteractionService").setPackage(str), 786560, i);
        }

        android.service.voice.VoiceInteractionServiceInfo findAvailInteractor(int i, @android.annotation.Nullable java.lang.String str) {
            java.util.List<android.content.pm.ResolveInfo> queryInteractorServices = queryInteractorServices(i, str);
            int size = queryInteractorServices.size();
            android.service.voice.VoiceInteractionServiceInfo voiceInteractionServiceInfo = null;
            if (size == 0) {
                android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "no available voice interaction services found for user " + i);
                return null;
            }
            for (int i2 = 0; i2 < size; i2++) {
                android.content.pm.ServiceInfo serviceInfo = queryInteractorServices.get(i2).serviceInfo;
                if ((serviceInfo.applicationInfo.flags & 1) != 0) {
                    android.service.voice.VoiceInteractionServiceInfo voiceInteractionServiceInfo2 = new android.service.voice.VoiceInteractionServiceInfo(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getPackageManager(), serviceInfo);
                    if (voiceInteractionServiceInfo2.getParseError() != null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Bad interaction service " + serviceInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + serviceInfo.name + ": " + voiceInteractionServiceInfo2.getParseError());
                    } else if (voiceInteractionServiceInfo == null) {
                        voiceInteractionServiceInfo = voiceInteractionServiceInfo2;
                    } else {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "More than one voice interaction service, picking first " + new android.content.ComponentName(voiceInteractionServiceInfo.getServiceInfo().packageName, voiceInteractionServiceInfo.getServiceInfo().name) + " over " + new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name));
                    }
                }
            }
            return voiceInteractionServiceInfo;
        }

        android.content.ComponentName getCurInteractor(int i) {
            java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_interaction_service", i);
            if (android.text.TextUtils.isEmpty(stringForUser)) {
                return null;
            }
            return android.content.ComponentName.unflattenFromString(stringForUser);
        }

        void setCurInteractor(android.content.ComponentName componentName, int i) {
            android.provider.Settings.Secure.putStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_interaction_service", componentName != null ? componentName.flattenToShortString() : "", i);
        }

        android.content.ComponentName findAvailRecognizer(java.lang.String str, int i) {
            if (str == null) {
                str = getDefaultRecognizer();
            }
            java.util.List<com.android.server.voiceinteraction.RecognitionServiceInfo> availableServices = com.android.server.voiceinteraction.RecognitionServiceInfo.getAvailableServices(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext, i);
            if (availableServices.size() == 0) {
                android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "no available voice recognition services found for user " + i);
                return null;
            }
            java.util.List<com.android.server.voiceinteraction.RecognitionServiceInfo> removeNonSelectableAsDefault = removeNonSelectableAsDefault(availableServices);
            if (availableServices.size() == 0) {
                android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "No selectableAsDefault recognition services found for user " + i + ". Falling back to non selectableAsDefault ones.");
                availableServices = removeNonSelectableAsDefault;
            }
            int size = availableServices.size();
            if (str != null) {
                for (int i2 = 0; i2 < size; i2++) {
                    android.content.pm.ServiceInfo serviceInfo = availableServices.get(i2).getServiceInfo();
                    if (str.equals(serviceInfo.packageName)) {
                        return new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
                    }
                }
            }
            if (size > 1) {
                android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "more than one voice recognition service found, picking first");
            }
            android.content.pm.ServiceInfo serviceInfo2 = availableServices.get(0).getServiceInfo();
            return new android.content.ComponentName(serviceInfo2.packageName, serviceInfo2.name);
        }

        private java.util.List<com.android.server.voiceinteraction.RecognitionServiceInfo> removeNonSelectableAsDefault(java.util.List<com.android.server.voiceinteraction.RecognitionServiceInfo> list) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int size = list.size() - 1; size >= 0; size--) {
                if (!list.get(size).isSelectableAsDefault()) {
                    arrayList.add(list.remove(size));
                }
            }
            return arrayList;
        }

        @android.annotation.Nullable
        public java.lang.String getDefaultRecognizer() {
            java.lang.String string = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getString(android.R.string.config_systemSpeechRecognizer);
            if (android.text.TextUtils.isEmpty(string)) {
                return null;
            }
            return string;
        }

        android.content.ComponentName getCurRecognizer(int i) {
            java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_recognition_service", i);
            if (android.text.TextUtils.isEmpty(stringForUser)) {
                return null;
            }
            return android.content.ComponentName.unflattenFromString(stringForUser);
        }

        void setCurRecognizer(android.content.ComponentName componentName, int i) {
            android.provider.Settings.Secure.putStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_recognition_service", componentName != null ? componentName.flattenToShortString() : "", i);
        }

        android.content.ComponentName getCurAssistant(int i) {
            java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getContentResolver(), "assistant", i);
            if (android.text.TextUtils.isEmpty(stringForUser)) {
                return null;
            }
            return android.content.ComponentName.unflattenFromString(stringForUser);
        }

        void resetCurAssistant(int i) {
            android.provider.Settings.Secure.putStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getContentResolver(), "assistant", null, i);
        }

        void forceRestartHotwordDetector() {
            this.mImpl.forceRestartHotwordDetector();
        }

        void setDebugHotwordLogging(boolean z) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "setTemporaryLogging without running voice interaction service");
                    } else {
                        this.mImpl.setDebugHotwordLoggingLocked(z);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void showSession(@android.annotation.Nullable android.os.Bundle bundle, int i, @android.annotation.Nullable java.lang.String str) {
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.showSessionLocked(bundle, i, str, null, null);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean deliverNewSession(android.os.IBinder iBinder, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor) {
            boolean deliverNewSessionLocked;
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        throw new java.lang.SecurityException("deliverNewSession without running voice interaction service");
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        deliverNewSessionLocked = this.mImpl.deliverNewSessionLocked(iBinder, iVoiceInteractionSession, iVoiceInteractor);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return deliverNewSessionLocked;
        }

        public boolean showSessionFromSession(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.Nullable android.os.Bundle bundle, int i, @android.annotation.Nullable java.lang.String str) {
            long clearCallingIdentity;
            android.content.Intent contextualSearchIntent;
            synchronized (this) {
                try {
                    java.lang.String string = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getResources().getString(android.R.string.config_defaultAutofillService);
                    java.lang.String string2 = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getResources().getString(android.R.string.config_defaultAugmentedAutofillService);
                    if (bundle != null && bundle.containsKey(string)) {
                        if (bundle.getBoolean(string2, true) && (contextualSearchIntent = getContextualSearchIntent(bundle)) != null) {
                            android.util.Slog.d(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Handed over to contextual search helper.");
                            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                return startContextualSearch(contextualSearchIntent);
                            } finally {
                            }
                        }
                        if (!bundle.getBoolean(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getResources().getString(android.R.string.config_defaultBugReportHandlerApp), true)) {
                            return false;
                        }
                        java.lang.String string3 = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getResources().getString(android.R.string.config_defaultCaptivePortalLoginPackageName);
                        android.content.ComponentName curInteractor = getCurInteractor(android.os.Binder.getCallingUserHandle().getIdentifier());
                        if (curInteractor != null && string3.equals(curInteractor.getPackageName())) {
                            android.util.Slog.d(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Contextual search not supported yet. Proceeding with VIS.");
                        }
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Contextual Search not supported yet. Returning failure.");
                        return false;
                    }
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "showSessionFromSession without running voice interaction service");
                        return false;
                    }
                    if (iBinder == null) {
                        com.android.server.voiceinteraction.HotwordMetricsLogger.cancelHotwordTriggerToUiLatencySession(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext);
                    }
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        return this.mImpl.showSessionLocked(bundle, i, str, null, null);
                    } finally {
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean hideSessionFromSession(android.os.IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "hideSessionFromSession without running voice interaction service");
                        return false;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        return this.mImpl.hideSessionLocked();
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int startVoiceActivity(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "startVoiceActivity without running voice interaction service");
                        return -96;
                    }
                    int callingPid = android.os.Binder.getCallingPid();
                    int callingUid = android.os.Binder.getCallingUid();
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        android.content.pm.ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getPackageManager(), 131072);
                        if (resolveActivityInfo != null) {
                            this.mImpl.grantImplicitAccessLocked(resolveActivityInfo.applicationInfo.uid, intent);
                        } else {
                            android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Cannot find ActivityInfo in startVoiceActivity.");
                        }
                        int startVoiceActivityLocked = this.mImpl.startVoiceActivityLocked(str2, callingPid, callingUid, iBinder, intent, str);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return startVoiceActivityLocked;
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
        }

        public int startAssistantActivity(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.os.Bundle bundle) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "startAssistantActivity without running voice interaction service");
                        return -96;
                    }
                    int callingPid = android.os.Binder.getCallingPid();
                    int callingUid = android.os.Binder.getCallingUid();
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        return this.mImpl.startAssistantActivityLocked(str2, callingPid, callingUid, iBinder, intent, str, bundle);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void requestDirectActions(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.os.IBinder iBinder2, @android.annotation.Nullable android.os.RemoteCallback remoteCallback, @android.annotation.NonNull android.os.RemoteCallback remoteCallback2) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "requestDirectActions without running voice interaction service");
                        remoteCallback2.sendResult((android.os.Bundle) null);
                    } else {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            this.mImpl.requestDirectActionsLocked(iBinder, i, iBinder2, remoteCallback, remoteCallback2);
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void performDirectAction(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.Bundle bundle, int i, android.os.IBinder iBinder2, @android.annotation.Nullable android.os.RemoteCallback remoteCallback, @android.annotation.NonNull android.os.RemoteCallback remoteCallback2) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "performDirectAction without running voice interaction service");
                        remoteCallback2.sendResult((android.os.Bundle) null);
                    } else {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            this.mImpl.performDirectActionLocked(iBinder, str, bundle, i, iBinder2, remoteCallback, remoteCallback2);
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setKeepAwake(android.os.IBinder iBinder, boolean z) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "setKeepAwake without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.setKeepAwakeLocked(iBinder, z);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void closeSystemDialogs(android.os.IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "closeSystemDialogs without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.closeSystemDialogsLocked(iBinder);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void finish(android.os.IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "finish without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.finishLocked(iBinder, false);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setDisabledShowContext(int i) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "setDisabledShowContext without running voice interaction service");
                        return;
                    }
                    int callingUid = android.os.Binder.getCallingUid();
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.setDisabledShowContextLocked(callingUid, i);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int getDisabledShowContext() {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "getDisabledShowContext without running voice interaction service");
                        return 0;
                    }
                    int callingUid = android.os.Binder.getCallingUid();
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        return this.mImpl.getDisabledShowContextLocked(callingUid);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int getUserDisabledShowContext() {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "getUserDisabledShowContext without running voice interaction service");
                        return 0;
                    }
                    int callingUid = android.os.Binder.getCallingUid();
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        return this.mImpl.getUserDisabledShowContextLocked(callingUid);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public void setDisabled(boolean z) {
            super.setDisabled_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mTemporarilyDisabled == z) {
                        return;
                    }
                    this.mTemporarilyDisabled = z;
                    if (this.mTemporarilyDisabled) {
                        android.util.Slog.i(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "setDisabled(): temporarily disabling and hiding current session");
                        try {
                            hideCurrentSession();
                        } catch (android.os.RemoteException e) {
                            android.util.Log.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Failed to call hideCurrentSession", e);
                        }
                    } else {
                        android.util.Slog.i(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "setDisabled(): re-enabling");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void startListeningVisibleActivityChanged(@android.annotation.NonNull android.os.IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "startListeningVisibleActivityChanged without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.startListeningVisibleActivityChangedLocked(iBinder);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void stopListeningVisibleActivityChanged(@android.annotation.NonNull android.os.IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "stopListeningVisibleActivityChanged without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.stopListeningVisibleActivityChangedLocked(iBinder);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void notifyActivityEventChanged(@android.annotation.NonNull final android.os.IBinder iBinder, final int i) {
            synchronized (this) {
                try {
                    if (this.mImpl == null || iBinder == null) {
                        return;
                    }
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda7
                        public final void runOrThrow() {
                            com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.lambda$notifyActivityEventChanged$2(iBinder, i);
                        }
                    });
                } finally {
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyActivityEventChanged$2(android.os.IBinder iBinder, int i) throws java.lang.Exception {
            this.mImpl.notifyActivityEventChangedLocked(iBinder, i);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_HOTWORD_DETECTION")
        public void updateState(@android.annotation.Nullable final android.os.PersistableBundle persistableBundle, @android.annotation.Nullable final android.os.SharedMemory sharedMemory, @android.annotation.NonNull final android.os.IBinder iBinder) {
            super.updateState_enforcePermission();
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda3
                    public final void runOrThrow() {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.lambda$updateState$3(persistableBundle, sharedMemory, iBinder);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateState$3(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IBinder iBinder) throws java.lang.Exception {
            this.mImpl.updateStateLocked(persistableBundle, sharedMemory, iBinder);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_HOTWORD_DETECTION")
        public void initAndVerifyDetector(@android.annotation.NonNull final android.media.permission.Identity identity, @android.annotation.Nullable final android.os.PersistableBundle persistableBundle, @android.annotation.Nullable final android.os.SharedMemory sharedMemory, @android.annotation.NonNull final android.os.IBinder iBinder, final com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, final int i) {
            super.initAndVerifyDetector_enforcePermission();
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
                identity.uid = android.os.Binder.getCallingUid();
                identity.pid = android.os.Binder.getCallingPid();
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda6
                    public final void runOrThrow() {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.lambda$initAndVerifyDetector$4(identity, persistableBundle, sharedMemory, iBinder, iHotwordRecognitionStatusCallback, i);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$initAndVerifyDetector$4(android.media.permission.Identity identity, android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IBinder iBinder, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i) throws java.lang.Exception {
            this.mImpl.initAndVerifyDetectorLocked(identity, persistableBundle, sharedMemory, iBinder, iHotwordRecognitionStatusCallback, i);
        }

        public void destroyDetector(@android.annotation.NonNull final android.os.IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "destroyDetector without running voice interaction service");
                    } else {
                        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda4
                            public final void runOrThrow() {
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.lambda$destroyDetector$5(iBinder);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$destroyDetector$5(android.os.IBinder iBinder) throws java.lang.Exception {
            this.mImpl.destroyDetectorLocked(iBinder);
        }

        public void shutdownHotwordDetectionService() {
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "shutdownHotwordDetectionService without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.shutdownHotwordDetectionServiceLocked();
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public void subscribeVisualQueryRecognitionStatus(com.android.internal.app.IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener) {
            super.subscribeVisualQueryRecognitionStatus_enforcePermission();
            synchronized (this) {
                com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVisualQueryRecognitionStatusListener = iVisualQueryRecognitionStatusListener;
            }
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public void enableVisualQueryDetection(com.android.internal.app.IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) {
            super.enableVisualQueryDetection_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "enableVisualQueryDetection without running voice interaction service");
                    } else {
                        this.mImpl.setVisualQueryDetectionAttentionListenerLocked(iVisualQueryDetectionAttentionListener);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public void disableVisualQueryDetection() {
            super.disableVisualQueryDetection_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "disableVisualQueryDetection without running voice interaction service");
                    } else {
                        this.mImpl.setVisualQueryDetectionAttentionListenerLocked(null);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void startPerceiving(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) throws android.os.RemoteException {
            enforceCallingPermission("android.permission.RECORD_AUDIO");
            enforceCallingPermission("android.permission.CAMERA");
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "startPerceiving without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (this.mImpl.startPerceivingLocked(iVisualQueryDetectionVoiceInteractionCallback) && com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVisualQueryRecognitionStatusListener != null) {
                            com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVisualQueryRecognitionStatusListener.onStartPerceiving();
                        }
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void stopPerceiving() throws android.os.RemoteException {
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "stopPerceiving without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (this.mImpl.stopPerceivingLocked() && com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVisualQueryRecognitionStatusListener != null) {
                            com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVisualQueryRecognitionStatusListener.onStopPerceiving();
                        }
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void startListeningFromMic(android.media.AudioFormat audioFormat, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws android.os.RemoteException {
            enforceCallingPermission("android.permission.RECORD_AUDIO");
            enforceCallingPermission("android.permission.CAPTURE_AUDIO_HOTWORD");
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "startListeningFromMic without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.startListeningFromMicLocked(audioFormat, iMicrophoneHotwordDetectionVoiceInteractionCallback);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void startListeningFromExternalSource(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, @android.annotation.NonNull android.os.IBinder iBinder, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws android.os.RemoteException {
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "startListeningFromExternalSource without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.startListeningFromExternalSourceLocked(parcelFileDescriptor, audioFormat, persistableBundle, iBinder, iMicrophoneHotwordDetectionVoiceInteractionCallback);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void stopListeningFromMic() throws android.os.RemoteException {
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "stopListeningFromMic without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.stopListeningFromMicLocked();
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void triggerHardwareRecognitionEventForTest(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) throws android.os.RemoteException {
            enforceCallingPermission("android.permission.RECORD_AUDIO");
            enforceCallingPermission("android.permission.CAPTURE_AUDIO_HOTWORD");
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "triggerHardwareRecognitionEventForTest without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.triggerHardwareRecognitionEventForTestLocked(keyphraseRecognitionEvent, iHotwordRecognitionStatusCallback);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, java.lang.String str) {
            enforceCallerAllowedToEnrollVoiceModel();
            if (str == null) {
                throw new java.lang.IllegalArgumentException("Illegal argument(s) in getKeyphraseSoundModel");
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper.getKeyphraseSoundModel(i, callingUserId, str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int updateKeyphraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
            enforceCallerAllowedToEnrollVoiceModel();
            if (keyphraseSoundModel == null) {
                throw new java.lang.IllegalArgumentException("Model must not be null");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper.updateKeyphraseSoundModel(keyphraseSoundModel)) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return Integer.MIN_VALUE;
                }
                synchronized (this) {
                    try {
                        if (this.mImpl != null && this.mImpl.mService != null) {
                            this.mImpl.notifySoundModelsChangedLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (java.lang.Throwable th2) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th2;
            }
        }

        public int deleteKeyphraseSoundModel(int i, java.lang.String str) {
            int unloadKeyphraseModel;
            enforceCallerAllowedToEnrollVoiceModel();
            if (str == null) {
                throw new java.lang.IllegalArgumentException("Illegal argument(s) in deleteKeyphraseSoundModel");
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.SoundTriggerSession soundTriggerSession = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mLoadedKeyphraseIds.get(java.lang.Integer.valueOf(i));
                if (soundTriggerSession != null && (unloadKeyphraseModel = soundTriggerSession.unloadKeyphraseModel(i)) != 0) {
                    android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Unable to unload keyphrase sound model:" + unloadKeyphraseModel);
                }
                boolean deleteKeyphraseSoundModel = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper.deleteKeyphraseSoundModel(i, callingUserId, str);
                int i2 = deleteKeyphraseSoundModel ? 0 : Integer.MIN_VALUE;
                if (deleteKeyphraseSoundModel) {
                    synchronized (this) {
                        try {
                            if (this.mImpl != null && this.mImpl.mService != null) {
                                this.mImpl.notifySoundModelsChangedLocked();
                            }
                            com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mLoadedKeyphraseIds.remove(java.lang.Integer.valueOf(i));
                        } finally {
                        }
                    }
                }
                return i2;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_VOICE_KEYPHRASES")
        public void setModelDatabaseForTestEnabled(boolean z, android.os.IBinder iBinder) {
            super.setModelDatabaseForTestEnabled_enforcePermission();
            enforceCallerAllowedToEnrollVoiceModel();
            synchronized (this) {
                try {
                    if (z) {
                        final com.android.server.voiceinteraction.TestModelEnrollmentDatabase testModelEnrollmentDatabase = new com.android.server.voiceinteraction.TestModelEnrollmentDatabase();
                        try {
                            iBinder.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda5
                                @Override // android.os.IBinder.DeathRecipient
                                public final void binderDied() {
                                    com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.lambda$setModelDatabaseForTestEnabled$6(testModelEnrollmentDatabase);
                                }
                            }, 0);
                            com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper = testModelEnrollmentDatabase;
                            this.mImpl.notifySoundModelsChangedLocked();
                        } catch (android.os.RemoteException e) {
                        }
                    } else if (com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper != com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mRealDbHelper) {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mRealDbHelper;
                        this.mImpl.notifySoundModelsChangedLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setModelDatabaseForTestEnabled$6(com.android.server.voiceinteraction.TestModelEnrollmentDatabase testModelEnrollmentDatabase) {
            synchronized (this) {
                try {
                    if (com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper == testModelEnrollmentDatabase) {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mRealDbHelper;
                        this.mImpl.notifySoundModelsChangedLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean isEnrolledForKeyphrase(int i, java.lang.String str) {
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
            }
            if (str == null) {
                throw new java.lang.IllegalArgumentException("Illegal argument(s) in isEnrolledForKeyphrase");
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper.getKeyphraseSoundModel(i, callingUserId, str) != null;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.Nullable
        public android.hardware.soundtrigger.KeyphraseMetadata getEnrolledKeyphraseMetadata(java.lang.String str, java.lang.String str2) {
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
            }
            if (str2 == null) {
                throw new java.lang.IllegalArgumentException("Illegal argument(s) in isEnrolledForKeyphrase");
            }
            int callingUserId = android.os.UserHandle.getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper.getKeyphraseSoundModel(str, callingUserId, str2);
                if (keyphraseSoundModel == null) {
                    return null;
                }
                for (android.hardware.soundtrigger.SoundTrigger.Keyphrase keyphrase : keyphraseSoundModel.getKeyphrases()) {
                    if (str.equals(keyphrase.getText())) {
                        android.util.ArraySet arraySet = new android.util.ArraySet();
                        arraySet.add(keyphrase.getLocale());
                        return new android.hardware.soundtrigger.KeyphraseMetadata(keyphrase.getId(), keyphrase.getText(), arraySet, keyphrase.getRecognitionModes());
                    }
                }
                return null;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private class SoundTriggerSession extends com.android.internal.app.IVoiceInteractionSoundTriggerSession.Stub {
            final com.android.server.SoundTriggerInternal.Session mSession;
            private com.android.internal.app.IHotwordRecognitionStatusCallback mSessionExternalCallback;
            private android.hardware.soundtrigger.IRecognitionStatusCallback mSessionInternalCallback;
            private final android.media.permission.Identity mVoiceInteractorIdentity;

            SoundTriggerSession(com.android.server.SoundTriggerInternal.Session session, android.media.permission.Identity identity) {
                this.mSession = session;
                this.mVoiceInteractorIdentity = identity;
            }

            public android.hardware.soundtrigger.SoundTrigger.ModuleProperties getDspModuleProperties() {
                android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties;
                synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                    try {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            moduleProperties = this.mSession.getModuleProperties();
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return moduleProperties;
            }

            public int startRecognition(int i, java.lang.String str, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, boolean z) {
                synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                    try {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                        if (iHotwordRecognitionStatusCallback == null || recognitionConfig == null || str == null) {
                            throw new java.lang.IllegalArgumentException("Illegal argument(s) in startRecognition");
                        }
                        if (z) {
                            com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.enforceCallingPermission("android.permission.SOUND_TRIGGER_RUN_IN_BATTERY_SAVER");
                        }
                    } finally {
                    }
                }
                int callingUserId = android.os.UserHandle.getCallingUserId();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper.getKeyphraseSoundModel(i, callingUserId, str);
                    if (keyphraseSoundModel != null && keyphraseSoundModel.getUuid() != null && keyphraseSoundModel.getKeyphrases() != null) {
                        synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                            try {
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mLoadedKeyphraseIds.put(java.lang.Integer.valueOf(i), this);
                                if (this.mSessionExternalCallback != null) {
                                    if (this.mSessionInternalCallback != null) {
                                        if (iHotwordRecognitionStatusCallback.asBinder() != this.mSessionExternalCallback.asBinder()) {
                                        }
                                    }
                                }
                                this.mSessionInternalCallback = com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.createSoundTriggerCallbackLocked(iHotwordRecognitionStatusCallback, this.mVoiceInteractorIdentity);
                                this.mSessionExternalCallback = iHotwordRecognitionStatusCallback;
                            } finally {
                            }
                        }
                        return this.mSession.startRecognition(i, keyphraseSoundModel, this.mSessionInternalCallback, recognitionConfig, z);
                    }
                    android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "No matching sound model found in startRecognition");
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return Integer.MIN_VALUE;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public int stopRecognition(int i, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) {
                android.hardware.soundtrigger.IRecognitionStatusCallback createSoundTriggerCallbackLocked;
                synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                    try {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                        if (this.mSessionExternalCallback != null && this.mSessionInternalCallback != null && iHotwordRecognitionStatusCallback.asBinder() == this.mSessionExternalCallback.asBinder()) {
                            createSoundTriggerCallbackLocked = this.mSessionInternalCallback;
                            this.mSessionExternalCallback = null;
                            this.mSessionInternalCallback = null;
                        }
                        createSoundTriggerCallbackLocked = com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.createSoundTriggerCallbackLocked(iHotwordRecognitionStatusCallback, this.mVoiceInteractorIdentity);
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "stopRecognition() called with a different callback thanstartRecognition()");
                        this.mSessionExternalCallback = null;
                        this.mSessionInternalCallback = null;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mSession.stopRecognition(i, createSoundTriggerCallbackLocked);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public int setParameter(int i, @android.hardware.soundtrigger.ModelParams int i2, int i3) {
                synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                    com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mSession.setParameter(i, i2, i3);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public int getParameter(int i, @android.hardware.soundtrigger.ModelParams int i2) {
                synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                    com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mSession.getParameter(i, i2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @android.annotation.Nullable
            public android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(int i, @android.hardware.soundtrigger.ModelParams int i2) {
                synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                    com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mSession.queryParameter(i, i2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public void detach() {
                this.mSession.detach();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public int unloadKeyphraseModel(int i) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mSession.unloadKeyphraseModel(i);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void unloadAllKeyphraseModels() {
            for (int i = 0; i < com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mLoadedKeyphraseIds.size(); i++) {
                try {
                    int intValue = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mLoadedKeyphraseIds.keyAt(i).intValue();
                    int unloadKeyphraseModel = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mLoadedKeyphraseIds.valueAt(i).unloadKeyphraseModel(intValue);
                    if (unloadKeyphraseModel != 0) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Failed to unload keyphrase " + intValue + ":" + unloadKeyphraseModel);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mLoadedKeyphraseIds.clear();
        }

        public android.content.ComponentName getActiveServiceComponentName() {
            android.content.ComponentName componentName;
            synchronized (this) {
                try {
                    componentName = this.mImpl != null ? this.mImpl.mComponent : null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return componentName;
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public boolean showSessionForActiveService(@android.annotation.Nullable android.os.Bundle bundle, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, @android.annotation.Nullable android.os.IBinder iBinder) {
            super.showSessionForActiveService_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "showSessionForActiveService without running voice interactionservice");
                        return false;
                    }
                    if (this.mTemporarilyDisabled) {
                        android.util.Slog.i(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "showSessionForActiveService(): ignored while temporarily disabled");
                        return false;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        com.android.server.voiceinteraction.HotwordMetricsLogger.cancelHotwordTriggerToUiLatencySession(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext);
                        return this.mImpl.showSessionLocked(bundle, i | 1 | 2, str, iVoiceInteractionSessionShowCallback, iBinder);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public void hideCurrentSession() throws android.os.RemoteException {
            super.hideCurrentSession_enforcePermission();
            if (this.mImpl == null) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (this.mImpl.mActiveSession != null && this.mImpl.mActiveSession.mSession != null) {
                    try {
                        this.mImpl.mActiveSession.mSession.closeSystemDialogs();
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Failed to call closeSystemDialogs", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public void launchVoiceAssistFromKeyguard() {
            super.launchVoiceAssistFromKeyguard_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "launchVoiceAssistFromKeyguard without running voice interactionservice");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mImpl.launchVoiceAssistFromKeyguard();
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public boolean isSessionRunning() {
            boolean z;
            super.isSessionRunning_enforcePermission();
            synchronized (this) {
                try {
                    z = (this.mImpl == null || this.mImpl.mActiveSession == null) ? false : true;
                } finally {
                }
            }
            return z;
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public boolean activeServiceSupportsAssist() {
            boolean z;
            super.activeServiceSupportsAssist_enforcePermission();
            synchronized (this) {
                try {
                    z = (this.mImpl == null || this.mImpl.mInfo == null || !this.mImpl.mInfo.getSupportsAssist()) ? false : true;
                } finally {
                }
            }
            return z;
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public boolean activeServiceSupportsLaunchFromKeyguard() throws android.os.RemoteException {
            boolean z;
            super.activeServiceSupportsLaunchFromKeyguard_enforcePermission();
            synchronized (this) {
                try {
                    z = (this.mImpl == null || this.mImpl.mInfo == null || !this.mImpl.mInfo.getSupportsLaunchFromKeyguard()) ? false : true;
                } finally {
                }
            }
            return z;
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public void onLockscreenShown() {
            super.onLockscreenShown_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (this.mImpl.mActiveSession != null && this.mImpl.mActiveSession.mSession != null) {
                            try {
                                this.mImpl.mActiveSession.mSession.onLockscreenShown();
                            } catch (android.os.RemoteException e) {
                                android.util.Log.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Failed to call onLockscreenShown", e);
                            }
                        }
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public void registerVoiceInteractionSessionListener(com.android.internal.app.IVoiceInteractionSessionListener iVoiceInteractionSessionListener) {
            super.registerVoiceInteractionSessionListener_enforcePermission();
            synchronized (this) {
                com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.register(iVoiceInteractionSessionListener);
            }
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_VOICE_INTERACTION_SERVICE")
        public void getActiveServiceSupportedActions(java.util.List<java.lang.String> list, com.android.internal.app.IVoiceActionCheckCallback iVoiceActionCheckCallback) {
            super.getActiveServiceSupportedActions_enforcePermission();
            synchronized (this) {
                if (this.mImpl == null) {
                    try {
                        iVoiceActionCheckCallback.onComplete((java.util.List) null);
                    } catch (android.os.RemoteException e) {
                    }
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mImpl.getActiveServiceSupportedActions(list, iVoiceActionCheckCallback);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void onSessionShown() {
            synchronized (this) {
                int beginBroadcast = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.getBroadcastItem(i).onVoiceSessionShown();
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Error delivering voice interaction open event.", e);
                    }
                }
                com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.finishBroadcast();
            }
        }

        public void onSessionHidden() {
            synchronized (this) {
                int beginBroadcast = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.getBroadcastItem(i).onVoiceSessionHidden();
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Error delivering voice interaction closed event.", e);
                    }
                }
                com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.finishBroadcast();
            }
        }

        public void setSessionWindowVisible(android.os.IBinder iBinder, final boolean z) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "setSessionWindowVisible called without running voice interaction service");
                        return;
                    }
                    if (this.mImpl.mActiveSession == null || iBinder != this.mImpl.mActiveSession.mToken) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "setSessionWindowVisible does not match active session");
                        return;
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.broadcast(new java.util.function.Consumer() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.lambda$setSessionWindowVisible$7(z, (com.android.internal.app.IVoiceInteractionSessionListener) obj);
                            }
                        });
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$setSessionWindowVisible$7(boolean z, com.android.internal.app.IVoiceInteractionSessionListener iVoiceInteractionSessionListener) {
            try {
                iVoiceInteractionSessionListener.onVoiceSessionWindowVisibilityChanged(z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Error delivering window visibility event to listener.", e);
            }
        }

        public boolean getAccessibilityDetectionEnabled() {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "registerAccessibilityDetectionSettingsListener called without running voice interaction service");
                        return false;
                    }
                    return this.mImpl.getAccessibilityDetectionEnabled();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void registerAccessibilityDetectionSettingsListener(com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "registerAccessibilityDetectionSettingsListener called without running voice interaction service");
                    } else {
                        this.mImpl.registerAccessibilityDetectionSettingsListenerLocked(iVoiceInteractionAccessibilitySettingsListener);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void unregisterAccessibilityDetectionSettingsListener(com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "unregisterAccessibilityDetectionSettingsListener called without running voice interaction service");
                    } else {
                        this.mImpl.unregisterAccessibilityDetectionSettingsListenerLocked(iVoiceInteractionAccessibilitySettingsListener);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext, com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, printWriter)) {
                synchronized (this) {
                    try {
                        printWriter.println("VOICE INTERACTION MANAGER (dumpsys voiceinteraction)");
                        printWriter.println("  mEnableService: " + this.mEnableService);
                        printWriter.println("  mTemporarilyDisabled: " + this.mTemporarilyDisabled);
                        printWriter.println("  mCurUser: " + this.mCurUser);
                        printWriter.println("  mCurUserSupported: " + this.mCurUserSupported);
                        printWriter.println("  mIsHdsRequired: " + this.IS_HDS_REQUIRED);
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.dumpSupportedUsers(printWriter, "  ");
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDbHelper.dump(printWriter);
                        if (this.mImpl == null) {
                            printWriter.println("  (No active implementation)");
                        } else {
                            this.mImpl.dumpLocked(fileDescriptor, printWriter, strArr);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.voiceinteraction.VoiceInteractionManagerServiceShellCommand(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mServiceStub).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public void setUiHints(android.os.Bundle bundle) {
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
                int beginBroadcast = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.getBroadcastItem(i).onSetUiHints(bundle);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "Error delivering UI hints.", e);
                    }
                }
                com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.finishBroadcast();
            }
        }

        private boolean isCallerHoldingPermission(java.lang.String str) {
            return com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.checkCallingOrSelfPermission(str) == 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void enforceCallingPermission(java.lang.String str) {
            if (!isCallerHoldingPermission(str)) {
                throw new java.lang.SecurityException("Caller does not hold the permission " + str);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void enforceIsCurrentVoiceInteractionService() {
            if (!isCallerCurrentVoiceInteractionService()) {
                throw new java.lang.SecurityException("Caller is not the current voice interaction service");
            }
        }

        private void enforceIsCallerPreinstalledAssistant() {
            if (!isCallerPreinstalledAssistant()) {
                throw new java.lang.SecurityException("Caller is not the pre-installed assistant.");
            }
        }

        private void enforceCallerAllowedToEnrollVoiceModel() {
            if (isCallerHoldingPermission("android.permission.KEYPHRASE_ENROLLMENT_APPLICATION")) {
                return;
            }
            enforceCallingPermission("android.permission.MANAGE_VOICE_KEYPHRASES");
            enforceIsCurrentVoiceInteractionService();
        }

        private boolean isCallerCurrentVoiceInteractionService() {
            return this.mImpl != null && this.mImpl.mInfo.getServiceInfo().applicationInfo.uid == android.os.Binder.getCallingUid();
        }

        private boolean isCallerPreinstalledAssistant() {
            return this.mImpl != null && this.mImpl.getApplicationInfo().uid == android.os.Binder.getCallingUid() && (this.mImpl.getApplicationInfo().isSystemApp() || this.mImpl.getApplicationInfo().isUpdatedSystemApp());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setImplLocked(com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl) {
            this.mImpl = voiceInteractionManagerServiceImpl;
            com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mAtmInternal.notifyActiveVoiceInteractionServiceChanged(getActiveServiceComponentName());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.hardware.soundtrigger.IRecognitionStatusCallback createSoundTriggerCallbackLocked(com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, android.media.permission.Identity identity) {
            if (this.mImpl == null) {
                return null;
            }
            return this.mImpl.createSoundTriggerCallbackLocked(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext, iHotwordRecognitionStatusCallback, identity);
        }

        class RoleObserver implements android.app.role.OnRoleHoldersChangedListener {
            private android.content.pm.PackageManager mPm;
            private android.app.role.RoleManager mRm;

            RoleObserver(@android.annotation.NonNull java.util.concurrent.Executor executor) {
                this.mPm = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getPackageManager();
                this.mRm = (android.app.role.RoleManager) com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getSystemService(android.app.role.RoleManager.class);
                this.mRm.addOnRoleHoldersChangedListenerAsUser(executor, this, android.os.UserHandle.ALL);
                if (this.mRm.isRoleAvailable("android.app.role.ASSISTANT")) {
                    onRoleHoldersChanged("android.app.role.ASSISTANT", android.os.UserHandle.of(((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getCurrentUserId()));
                }
            }

            public void onRoleHoldersChanged(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
                android.content.pm.UserInfo userInfo;
                if (!str.equals("android.app.role.ASSISTANT")) {
                    return;
                }
                java.util.List roleHoldersAsUser = this.mRm.getRoleHoldersAsUser(str, userHandle);
                if (roleHoldersAsUser.isEmpty() && (userInfo = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mUserManagerInternal.getUserInfo(userHandle.getIdentifier())) != null && userInfo.preCreated) {
                    com.android.server.utils.Slogf.d(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "onRoleHoldersChanged(): ignoring pre-created user %s for now, this method will be called again when it's converted to a real user", userInfo.toFullString());
                    return;
                }
                int identifier = userHandle.getIdentifier();
                java.lang.String str2 = "";
                if (roleHoldersAsUser.isEmpty()) {
                    android.provider.Settings.Secure.putStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.getContext().getContentResolver(), "assistant", "", identifier);
                    android.provider.Settings.Secure.putStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.getContext().getContentResolver(), "voice_interaction_service", "", identifier);
                    return;
                }
                java.lang.String str3 = (java.lang.String) roleHoldersAsUser.get(0);
                java.util.Iterator it = com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.queryInteractorServices(identifier, str3).iterator();
                while (it.hasNext()) {
                    android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) it.next()).serviceInfo;
                    android.service.voice.VoiceInteractionServiceInfo voiceInteractionServiceInfo = new android.service.voice.VoiceInteractionServiceInfo(this.mPm, serviceInfo);
                    if (voiceInteractionServiceInfo.getSupportsAssist()) {
                        java.lang.String flattenToShortString = serviceInfo.getComponentName().flattenToShortString();
                        if (voiceInteractionServiceInfo.getRecognitionService() != null) {
                            str2 = flattenToShortString;
                        } else {
                            android.util.Slog.e(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "The RecognitionService must be set to avoid boot loop on earlier platform version. Also make sure that this is a valid RecognitionService when running on Android 11 or earlier.");
                        }
                        android.provider.Settings.Secure.putStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.getContext().getContentResolver(), "assistant", str2, identifier);
                        android.provider.Settings.Secure.putStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.getContext().getContentResolver(), "voice_interaction_service", str2, identifier);
                        return;
                    }
                }
                java.util.Iterator it2 = this.mPm.queryIntentActivitiesAsUser(new android.content.Intent("android.intent.action.ASSIST").setPackage(str3), 851968, identifier).iterator();
                if (it2.hasNext()) {
                    android.provider.Settings.Secure.putStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.getContext().getContentResolver(), "assistant", ((android.content.pm.ResolveInfo) it2.next()).activityInfo.getComponentName().flattenToShortString(), identifier);
                    android.provider.Settings.Secure.putStringForUser(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.getContext().getContentResolver(), "voice_interaction_service", "", identifier);
                }
            }
        }

        class SettingsObserver extends android.database.ContentObserver {
            SettingsObserver(android.os.Handler handler) {
                super(handler);
                com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("voice_interaction_service"), false, this, -1);
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this) {
                    com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this.switchImplementationIfNeededLocked(false);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void resetServicesIfNoRecognitionService(android.content.ComponentName componentName, int i) {
            java.util.Iterator<android.content.pm.ResolveInfo> it = queryInteractorServices(i, componentName.getPackageName()).iterator();
            while (it.hasNext()) {
                android.service.voice.VoiceInteractionServiceInfo voiceInteractionServiceInfo = new android.service.voice.VoiceInteractionServiceInfo(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getPackageManager(), it.next().serviceInfo);
                if (voiceInteractionServiceInfo.getSupportsAssist() && voiceInteractionServiceInfo.getRecognitionService() == null) {
                    android.util.Slog.e(com.android.server.voiceinteraction.VoiceInteractionManagerService.TAG, "The RecognitionService must be set to avoid boot loop on earlier platform version. Also make sure that this is a valid RecognitionService when running on Android 11 or earlier.");
                    setCurInteractor(null, i);
                    resetCurAssistant(i);
                }
            }
        }

        private android.content.Intent getContextualSearchIntent(android.os.Bundle bundle) {
            android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer;
            java.lang.String string = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getResources().getString(android.R.string.config_defaultCaptivePortalLoginPackageName);
            if (string.isEmpty()) {
                return null;
            }
            android.content.Intent intent = new android.content.Intent(com.android.server.voiceinteraction.VoiceInteractionManagerService.CS_INTENT_FILTER);
            intent.setPackage(string);
            android.content.pm.ResolveInfo resolveActivity = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getPackageManager().resolveActivity(intent, 2097152);
            if (resolveActivity == null) {
                return null;
            }
            intent.setComponent(resolveActivity.getComponentInfo().getComponentName());
            intent.addFlags(268795904);
            intent.putExtras(bundle);
            boolean isAssistDataAllowed = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mAtmInternal.isAssistDataAllowed();
            java.util.List<com.android.server.wm.ActivityAssistInfo> topVisibleActivities = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mAtmInternal.getTopVisibleActivities();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            boolean z = false;
            for (com.android.server.wm.ActivityAssistInfo activityAssistInfo : topVisibleActivities) {
                if (isAssistDataAllowed) {
                    arrayList.add(activityAssistInfo.getComponentName().getPackageName());
                }
                if (com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDpmInternal != null && com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mDpmInternal.isUserOrganizationManaged(activityAssistInfo.getUserId())) {
                    z = true;
                }
            }
            if (com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mWmInternal != null) {
                screenshotHardwareBuffer = com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mWmInternal.takeAssistScreenshot();
            } else {
                screenshotHardwareBuffer = null;
            }
            android.graphics.Bitmap asBitmap = screenshotHardwareBuffer != null ? screenshotHardwareBuffer.asBitmap() : null;
            if (asBitmap != null) {
                intent.putExtra(com.android.server.voiceinteraction.VoiceInteractionManagerService.CS_KEY_FLAG_SECURE_FOUND, screenshotHardwareBuffer.containsSecureLayers());
                if (isAssistDataAllowed) {
                    intent.putExtra(com.android.server.voiceinteraction.VoiceInteractionManagerService.CS_KEY_FLAG_SCREENSHOT, asBitmap.asShared());
                }
            }
            intent.putExtra(com.android.server.voiceinteraction.VoiceInteractionManagerService.CS_KEY_FLAG_IS_MANAGED_PROFILE_VISIBLE, z);
            if (isAssistDataAllowed) {
                intent.putExtra(com.android.server.voiceinteraction.VoiceInteractionManagerService.CS_KEY_FLAG_VISIBLE_PACKAGE_NAMES, arrayList);
            }
            return intent;
        }

        @android.annotation.RequiresPermission("android.permission.START_TASKS_FROM_RECENTS")
        private boolean startContextualSearch(android.content.Intent intent) {
            android.app.ActivityOptions makeCustomTaskAnimation = android.app.ActivityOptions.makeCustomTaskAnimation(com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext, 0, 0, null, null, null);
            makeCustomTaskAnimation.setDisableStartingWindow(true);
            return com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mAtmInternal.startActivityWithScreenshot(intent, com.android.server.voiceinteraction.VoiceInteractionManagerService.this.mContext.getPackageName(), android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), null, makeCustomTaskAnimation.toBundle(), android.os.Binder.getCallingUserHandle().getIdentifier()) == 0;
        }
    }
}
