package com.android.server.sensorprivacy;

/* loaded from: classes2.dex */
public final class SensorPrivacyService extends com.android.server.SystemService {
    private static final int ACTION__ACTION_UNKNOWN = 0;
    private static final int ACTION__AUTOMOTIVE_DRIVER_ASSISTANCE_APPS = 5;
    private static final int ACTION__AUTOMOTIVE_DRIVER_ASSISTANCE_HELPFUL_APPS = 3;
    private static final int ACTION__AUTOMOTIVE_DRIVER_ASSISTANCE_REQUIRED_APPS = 4;
    private static final int ACTION__TOGGLE_OFF = 2;
    private static final int ACTION__TOGGLE_ON = 1;
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_LOGGING = false;
    public static final int REMINDER_DIALOG_DELAY_MILLIS = 500;
    private static final java.lang.String SENSOR_PRIVACY_CHANNEL_ID = "sensor_privacy";
    private final android.app.ActivityManager mActivityManager;
    private final android.app.ActivityManagerInternal mActivityManagerInternal;
    private final android.app.ActivityTaskManager mActivityTaskManager;
    private final android.app.AppOpsManager mAppOpsManager;
    private final android.app.AppOpsManagerInternal mAppOpsManagerInternal;
    private final android.os.IBinder mAppOpsRestrictionToken;
    private com.android.server.sensorprivacy.SensorPrivacyService.CallStateHelper mCallStateHelper;
    java.util.List<android.hardware.CameraPrivacyAllowlistEntry> mCameraPrivacyAllowlist;
    private com.android.server.sensorprivacy.CameraPrivacyLightController mCameraPrivacyLightController;
    private final android.content.Context mContext;
    private int mCurrentUser;
    private android.app.KeyguardManager mKeyguardManager;
    private final android.app.NotificationManager mNotificationManager;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyManagerInternalImpl mSensorPrivacyManagerInternal;
    private final com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl mSensorPrivacyServiceImpl;
    private final android.telephony.TelephonyManager mTelephonyManager;
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal;
    private static final java.lang.String TAG = com.android.server.sensorprivacy.SensorPrivacyService.class.getSimpleName();
    private static final java.lang.String ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY = com.android.server.sensorprivacy.SensorPrivacyService.class.getName() + ".action.disable_sensor_privacy";

    public SensorPrivacyService(android.content.Context context) {
        super(context);
        this.mAppOpsRestrictionToken = new android.os.Binder();
        this.mCameraPrivacyAllowlist = new java.util.ArrayList();
        this.mCurrentUser = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mContext = context;
        this.mAppOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        this.mAppOpsManagerInternal = (android.app.AppOpsManagerInternal) getLocalService(android.app.AppOpsManagerInternal.class);
        this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) getLocalService(com.android.server.pm.UserManagerInternal.class);
        this.mActivityManager = (android.app.ActivityManager) context.getSystemService(android.app.ActivityManager.class);
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) getLocalService(android.app.ActivityManagerInternal.class);
        this.mActivityTaskManager = (android.app.ActivityTaskManager) context.getSystemService(android.app.ActivityTaskManager.class);
        this.mTelephonyManager = (android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class);
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) getLocalService(android.content.pm.PackageManagerInternal.class);
        this.mNotificationManager = (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
        this.mSensorPrivacyServiceImpl = new com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl();
        for (java.util.Map.Entry<java.lang.String, java.lang.Boolean> entry : com.android.server.SystemConfig.getInstance().getCameraPrivacyAllowlist().entrySet()) {
            android.hardware.CameraPrivacyAllowlistEntry cameraPrivacyAllowlistEntry = new android.hardware.CameraPrivacyAllowlistEntry();
            cameraPrivacyAllowlistEntry.packageName = entry.getKey();
            cameraPrivacyAllowlistEntry.isMandatory = entry.getValue().booleanValue();
            this.mCameraPrivacyAllowlist.add(cameraPrivacyAllowlistEntry);
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService(SENSOR_PRIVACY_CHANNEL_ID, this.mSensorPrivacyServiceImpl);
        this.mSensorPrivacyManagerInternal = new com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyManagerInternalImpl();
        publishLocalService(android.hardware.SensorPrivacyManagerInternal.class, this.mSensorPrivacyManagerInternal);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            this.mKeyguardManager = (android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class);
            this.mCallStateHelper = new com.android.server.sensorprivacy.SensorPrivacyService.CallStateHelper();
            this.mSensorPrivacyServiceImpl.registerSettingsObserver();
        } else if (i == 550) {
            this.mCameraPrivacyLightController = new com.android.server.sensorprivacy.CameraPrivacyLightController(this.mContext);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(com.android.server.SystemService.TargetUser targetUser) {
        if (this.mCurrentUser == -10000) {
            this.mCurrentUser = targetUser.getUserIdentifier();
            this.mSensorPrivacyServiceImpl.userSwitching(com.android.server.am.ProcessList.INVALID_ADJ, targetUser.getUserIdentifier());
        }
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(com.android.server.SystemService.TargetUser targetUser, com.android.server.SystemService.TargetUser targetUser2) {
        this.mCurrentUser = targetUser2.getUserIdentifier();
        this.mSensorPrivacyServiceImpl.userSwitching(targetUser.getUserIdentifier(), targetUser2.getUserIdentifier());
    }

    class SensorPrivacyServiceImpl extends android.hardware.ISensorPrivacyManager.Stub implements android.app.AppOpsManager.OnOpNotedInternalListener, android.app.AppOpsManager.OnOpStartedListener, android.os.IBinder.DeathRecipient, com.android.server.pm.UserManagerInternal.UserRestrictionsListener {
        private final com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyHandler mHandler;
        private final java.lang.Object mLock = new java.lang.Object();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.util.ArrayMap<android.util.Pair<java.lang.Integer, android.os.UserHandle>, java.util.ArrayList<android.os.IBinder>> mSuppressReminders = new android.util.ArrayMap<>();
        private final android.util.ArrayMap<com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.SensorUseReminderDialogInfo, android.util.ArraySet<java.lang.Integer>> mQueuedSensorUseReminderDialogs = new android.util.ArrayMap<>();
        private com.android.server.sensorprivacy.SensorPrivacyStateController mSensorPrivacyStateController = com.android.server.sensorprivacy.SensorPrivacyStateController.getInstance();

        /* JADX INFO: Access modifiers changed from: private */
        class SensorUseReminderDialogInfo {
            private java.lang.String mPackageName;
            private int mTaskId;
            private android.os.UserHandle mUser;

            SensorUseReminderDialogInfo(int i, android.os.UserHandle userHandle, java.lang.String str) {
                this.mTaskId = i;
                this.mUser = userHandle;
                this.mPackageName = str;
            }

            public boolean equals(java.lang.Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || !(obj instanceof com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.SensorUseReminderDialogInfo)) {
                    return false;
                }
                com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.SensorUseReminderDialogInfo sensorUseReminderDialogInfo = (com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.SensorUseReminderDialogInfo) obj;
                if (this.mTaskId == sensorUseReminderDialogInfo.mTaskId && java.util.Objects.equals(this.mUser, sensorUseReminderDialogInfo.mUser) && java.util.Objects.equals(this.mPackageName, sensorUseReminderDialogInfo.mPackageName)) {
                    return true;
                }
                return false;
            }

            public int hashCode() {
                return java.util.Objects.hash(java.lang.Integer.valueOf(this.mTaskId), this.mUser, this.mPackageName);
            }
        }

        SensorPrivacyServiceImpl() {
            this.mHandler = com.android.server.sensorprivacy.SensorPrivacyService.this.new SensorPrivacyHandler(com.android.server.FgThread.get().getLooper(), com.android.server.sensorprivacy.SensorPrivacyService.this.mContext);
            correctStateIfNeeded();
            int[] iArr = {27, 100, 26, 101, 121};
            com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsManager.startWatchingNoted(iArr, this);
            com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsManager.startWatchingStarted(iArr, this);
            com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.setToggleSensorPrivacy(((android.os.UserHandle) intent.getParcelableExtra("android.intent.extra.USER", android.os.UserHandle.class)).getIdentifier(), 5, intent.getIntExtra(android.hardware.SensorPrivacyManager.EXTRA_SENSOR, 0), false);
                    int intExtra = intent.getIntExtra(android.hardware.SensorPrivacyManager.EXTRA_NOTIFICATION_ID, 0);
                    if (intExtra != 0) {
                        com.android.server.sensorprivacy.SensorPrivacyService.this.mNotificationManager.cancel(intExtra);
                    }
                }
            }, new android.content.IntentFilter(com.android.server.sensorprivacy.SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY), "android.permission.MANAGE_SENSOR_PRIVACY", null, 2);
            com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.registerReceiver(new com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.AnonymousClass2(com.android.server.sensorprivacy.SensorPrivacyService.this), new android.content.IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
            com.android.server.sensorprivacy.SensorPrivacyService.this.mUserManagerInternal.addUserRestrictionsListener(this);
            com.android.server.sensorprivacy.SensorPrivacyStateController sensorPrivacyStateController = this.mSensorPrivacyStateController;
            com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyHandler sensorPrivacyHandler = this.mHandler;
            final com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyHandler sensorPrivacyHandler2 = this.mHandler;
            java.util.Objects.requireNonNull(sensorPrivacyHandler2);
            sensorPrivacyStateController.setAllSensorPrivacyListener(sensorPrivacyHandler, new com.android.server.sensorprivacy.SensorPrivacyStateController.AllSensorPrivacyListener() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda6
                @Override // com.android.server.sensorprivacy.SensorPrivacyStateController.AllSensorPrivacyListener
                public final void onAllSensorPrivacyChanged(boolean z) {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyHandler.this.handleSensorPrivacyChanged(z);
                }
            });
            this.mSensorPrivacyStateController.setSensorPrivacyListener(this.mHandler, new com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyListener() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda7
                @Override // com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyListener
                public final void onSensorPrivacyChanged(int i, int i2, int i3, com.android.server.sensorprivacy.SensorState sensorState) {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.lambda$new$0(i, i2, i3, sensorState);
                }
            });
        }

        /* renamed from: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$2, reason: invalid class name */
        class AnonymousClass2 extends android.content.BroadcastReceiver {
            final /* synthetic */ com.android.server.sensorprivacy.SensorPrivacyService val$this$0;

            AnonymousClass2(com.android.server.sensorprivacy.SensorPrivacyService sensorPrivacyService) {
                this.val$this$0 = sensorPrivacyService;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.mSensorPrivacyStateController.forEachState(new com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyStateConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$2$$ExternalSyntheticLambda0
                    @Override // com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyStateConsumer
                    public final void accept(int i, int i2, int i3, com.android.server.sensorprivacy.SensorState sensorState) {
                        com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.AnonymousClass2.this.lambda$onReceive$0(i, i2, i3, sensorState);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onReceive$0(int i, int i2, int i3, com.android.server.sensorprivacy.SensorState sensorState) {
                com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.logSensorPrivacyToggle(5, i3, sensorState.isEnabled(), sensorState.getLastChange(), true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(int i, int i2, int i3, com.android.server.sensorprivacy.SensorState sensorState) {
            this.mHandler.handleSensorPrivacyChanged(i2, i, i3, sensorState.isEnabled());
            if (com.android.internal.camera.flags.Flags.cameraPrivacyAllowlist()) {
                this.mHandler.handleSensorPrivacyChanged(i2, i, i3, sensorState.getState());
            }
        }

        private void correctStateIfNeeded() {
            this.mSensorPrivacyStateController.forEachState(new com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyStateConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda14
                @Override // com.android.server.sensorprivacy.SensorPrivacyStateController.SensorPrivacyStateConsumer
                public final void accept(int i, int i2, int i3, com.android.server.sensorprivacy.SensorState sensorState) {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.lambda$correctStateIfNeeded$1(i, i2, i3, sensorState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$correctStateIfNeeded$1(int i, int i2, int i3, com.android.server.sensorprivacy.SensorState sensorState) {
            if (i == 1 && !supportsSensorToggle(1, i3) && sensorState.isEnabled()) {
                setToggleSensorPrivacyUnchecked(1, i2, 5, i3, false);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
        public void onUserRestrictionsChanged(int i, android.os.Bundle bundle, android.os.Bundle bundle2) {
            if (!bundle2.getBoolean("disallow_camera_toggle") && bundle.getBoolean("disallow_camera_toggle")) {
                setToggleSensorPrivacyUnchecked(1, i, 5, 2, false);
            }
            if (!bundle2.getBoolean("disallow_microphone_toggle") && bundle.getBoolean("disallow_microphone_toggle")) {
                setToggleSensorPrivacyUnchecked(1, i, 5, 1, false);
            }
        }

        public void onOpStarted(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4) {
            onOpNoted(i, i2, str, str2, i3, i4);
        }

        public void onOpNoted(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4) {
            if ((i3 & 13) == 0) {
                return;
            }
            int i5 = 1;
            if (i4 == 1) {
                if (i != 27 && i != 100 && i != 121) {
                    if (i == 26 || i == 101) {
                        i5 = 2;
                    } else {
                        return;
                    }
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    onSensorUseStarted(i2, str, i5);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        @android.annotation.RequiresPermission("android.permission.OBSERVE_SENSOR_PRIVACY")
        private void onSensorUseStarted(int i, java.lang.String str, int i2) {
            java.lang.String str2;
            android.os.UserHandle of = android.os.UserHandle.of(com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser);
            if (com.android.internal.camera.flags.Flags.cameraPrivacyAllowlist() && i2 == 2 && isAutomotive(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext)) {
                if (!isCameraPrivacyEnabled(str)) {
                    return;
                }
            } else if (!isCombinedToggleSensorPrivacyEnabled(i2)) {
                return;
            }
            if (i == 1000) {
                return;
            }
            synchronized (this.mLock) {
                try {
                    if (this.mSuppressReminders.containsKey(new android.util.Pair(java.lang.Integer.valueOf(i2), of))) {
                        android.util.Log.d(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Suppressed sensor privacy reminder for " + str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + of);
                        return;
                    }
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    java.util.List tasks = com.android.server.sensorprivacy.SensorPrivacyService.this.mActivityTaskManager.getTasks(Integer.MAX_VALUE);
                    int size = tasks.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        android.app.ActivityManager.RunningTaskInfo runningTaskInfo = (android.app.ActivityManager.RunningTaskInfo) tasks.get(i3);
                        if (runningTaskInfo.isVisible) {
                            if (runningTaskInfo.topActivity.getPackageName().equals(str)) {
                                if (runningTaskInfo.isFocused) {
                                    enqueueSensorUseReminderDialogAsync(runningTaskInfo.taskId, of, str, i2);
                                    return;
                                }
                                arrayList.add(runningTaskInfo);
                            } else if (runningTaskInfo.topActivity.flattenToString().equals(getSensorUseActivityName(new android.util.ArraySet<>(java.util.Arrays.asList(java.lang.Integer.valueOf(i2))))) && runningTaskInfo.isFocused) {
                                enqueueSensorUseReminderDialogAsync(runningTaskInfo.taskId, of, str, i2);
                            }
                        }
                    }
                    if (arrayList.size() == 1) {
                        enqueueSensorUseReminderDialogAsync(((android.app.ActivityManager.RunningTaskInfo) arrayList.get(0)).taskId, of, str, i2);
                        return;
                    }
                    if (arrayList.size() > 1) {
                        showSensorUseReminderNotification(of, str, i2);
                        return;
                    }
                    java.util.List<android.app.ActivityManager.RunningServiceInfo> runningServices = com.android.server.sensorprivacy.SensorPrivacyService.this.mActivityManager.getRunningServices(Integer.MAX_VALUE);
                    int size2 = runningServices.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        android.app.ActivityManager.RunningServiceInfo runningServiceInfo = runningServices.get(i4);
                        if (runningServiceInfo.foreground && runningServiceInfo.service.getPackageName().equals(str)) {
                            showSensorUseReminderNotification(of, str, i2);
                            return;
                        }
                    }
                    java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getContentResolver(), "default_input_method", of.getIdentifier());
                    if (stringForUser == null) {
                        str2 = null;
                    } else {
                        str2 = android.content.ComponentName.unflattenFromString(stringForUser).getPackageName();
                    }
                    try {
                        int uidCapability = com.android.server.sensorprivacy.SensorPrivacyService.this.mActivityManagerInternal.getUidCapability(i);
                        if (i2 == 1) {
                            android.service.voice.VoiceInteractionManagerInternal voiceInteractionManagerInternal = (android.service.voice.VoiceInteractionManagerInternal) com.android.server.LocalServices.getService(android.service.voice.VoiceInteractionManagerInternal.class);
                            if (voiceInteractionManagerInternal != null && voiceInteractionManagerInternal.hasActiveSession(str)) {
                                enqueueSensorUseReminderDialogAsync(-1, of, str, i2);
                                return;
                            } else if (android.text.TextUtils.equals(str, str2) && (uidCapability & 4) != 0) {
                                enqueueSensorUseReminderDialogAsync(-1, of, str, i2);
                                return;
                            }
                        }
                        if (i2 == 2 && android.text.TextUtils.equals(str, str2) && (uidCapability & 2) != 0) {
                            enqueueSensorUseReminderDialogAsync(-1, of, str, i2);
                            return;
                        }
                        android.util.Log.i(com.android.server.sensorprivacy.SensorPrivacyService.TAG, str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i + " started using sensor " + i2 + " but no activity or foreground service was running. The user will not be informed. System components should check if sensor privacy is enabled for the sensor before accessing it.");
                    } catch (java.lang.IllegalArgumentException e) {
                        android.util.Log.w(com.android.server.sensorprivacy.SensorPrivacyService.TAG, e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void enqueueSensorUseReminderDialogAsync(int i, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str, int i2) {
            this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda11
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl) obj).enqueueSensorUseReminderDialog(((java.lang.Integer) obj2).intValue(), (android.os.UserHandle) obj3, (java.lang.String) obj4, ((java.lang.Integer) obj5).intValue());
                }
            }, this, java.lang.Integer.valueOf(i), userHandle, str, java.lang.Integer.valueOf(i2)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void enqueueSensorUseReminderDialog(int i, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str, int i2) {
            com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.SensorUseReminderDialogInfo sensorUseReminderDialogInfo = new com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.SensorUseReminderDialogInfo(i, userHandle, str);
            if (!this.mQueuedSensorUseReminderDialogs.containsKey(sensorUseReminderDialogInfo)) {
                android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
                if ((i2 == 1 && this.mSuppressReminders.containsKey(new android.util.Pair(2, userHandle))) || (i2 == 2 && this.mSuppressReminders.containsKey(new android.util.Pair(1, userHandle)))) {
                    arraySet.add(1);
                    arraySet.add(2);
                } else {
                    arraySet.add(java.lang.Integer.valueOf(i2));
                }
                this.mQueuedSensorUseReminderDialogs.put(sensorUseReminderDialogInfo, arraySet);
                this.mHandler.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl) obj).showSensorUserReminderDialog((com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.SensorUseReminderDialogInfo) obj2);
                    }
                }, this, sensorUseReminderDialogInfo), 500L);
                return;
            }
            this.mQueuedSensorUseReminderDialogs.get(sensorUseReminderDialogInfo).add(java.lang.Integer.valueOf(i2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void showSensorUserReminderDialog(@android.annotation.NonNull com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.SensorUseReminderDialogInfo sensorUseReminderDialogInfo) {
            android.util.ArraySet<java.lang.Integer> arraySet = this.mQueuedSensorUseReminderDialogs.get(sensorUseReminderDialogInfo);
            this.mQueuedSensorUseReminderDialogs.remove(sensorUseReminderDialogInfo);
            if (arraySet == null) {
                android.util.Log.e(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Unable to show sensor use dialog because sensor set is null. Was the dialog queue modified from outside the handler thread?");
                return;
            }
            android.content.Intent intent = new android.content.Intent();
            intent.setComponent(android.content.ComponentName.unflattenFromString(getSensorUseActivityName(arraySet)));
            android.app.ActivityOptions makeBasic = android.app.ActivityOptions.makeBasic();
            makeBasic.setLaunchTaskId(sensorUseReminderDialogInfo.mTaskId);
            makeBasic.setTaskOverlay(true, true);
            intent.addFlags(8650752);
            intent.putExtra("android.intent.extra.PACKAGE_NAME", sensorUseReminderDialogInfo.mPackageName);
            if (arraySet.size() == 1) {
                intent.putExtra(android.hardware.SensorPrivacyManager.EXTRA_SENSOR, arraySet.valueAt(0));
            } else if (arraySet.size() == 2) {
                intent.putExtra(android.hardware.SensorPrivacyManager.EXTRA_ALL_SENSORS, true);
            } else {
                android.util.Log.e(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Attempted to show sensor use dialog for " + arraySet.size() + " sensors");
                return;
            }
            com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.startActivityAsUser(intent, makeBasic.toBundle(), android.os.UserHandle.SYSTEM);
        }

        private java.lang.String getSensorUseActivityName(android.util.ArraySet<java.lang.Integer> arraySet) {
            java.util.Iterator<java.lang.Integer> it = arraySet.iterator();
            while (it.hasNext()) {
                if (isToggleSensorPrivacyEnabled(2, it.next().intValue())) {
                    return com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getResources().getString(android.R.string.config_satellite_sim_spn_identifier);
                }
            }
            return com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getResources().getString(android.R.string.config_satellite_sim_plmn_identifier);
        }

        private void showSensorUseReminderNotification(@android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str, int i) {
            int i2;
            int i3;
            int i4;
            long j;
            try {
                java.lang.CharSequence loadLabel = com.android.server.sensorprivacy.SensorPrivacyService.this.getUiContext().getPackageManager().getApplicationInfoAsUser(str, 0, userHandle).loadLabel(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getPackageManager());
                if (i == 1) {
                    i2 = android.R.drawable.ic_mic;
                    i3 = android.R.string.screen_compat_mode_hint;
                    i4 = 65;
                } else {
                    i2 = android.R.drawable.ic_camera;
                    i3 = android.R.string.scNullCipherIssueNonEncryptedSummaryNotification;
                    i4 = 66;
                }
                android.app.NotificationChannel notificationChannel = new android.app.NotificationChannel(com.android.server.sensorprivacy.SensorPrivacyService.SENSOR_PRIVACY_CHANNEL_ID, com.android.server.sensorprivacy.SensorPrivacyService.this.getUiContext().getString(android.R.string.scNullCipherIssueNonEncryptedSummary), 4);
                notificationChannel.setSound(null, null);
                notificationChannel.setBypassDnd(true);
                notificationChannel.enableVibration(false);
                notificationChannel.setBlockable(false);
                com.android.server.sensorprivacy.SensorPrivacyService.this.mNotificationManager.createNotificationChannel(notificationChannel);
                android.graphics.drawable.Icon createWithResource = android.graphics.drawable.Icon.createWithResource(com.android.server.sensorprivacy.SensorPrivacyService.this.getUiContext().getResources(), i2);
                java.lang.String string = com.android.server.sensorprivacy.SensorPrivacyService.this.getUiContext().getString(i3);
                android.text.Spanned fromHtml = android.text.Html.fromHtml(com.android.server.sensorprivacy.SensorPrivacyService.this.getUiContext().getString(android.R.string.screen_compat_mode_scale, loadLabel), 0);
                android.app.PendingIntent activity = android.app.PendingIntent.getActivity(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext, i, new android.content.Intent(((android.safetycenter.SafetyCenterManager) com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getSystemService(android.safetycenter.SafetyCenterManager.class)).isSafetyCenterEnabled() ? "android.settings.PRIVACY_CONTROLS" : "android.settings.PRIVACY_SETTINGS"), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
                java.lang.String string2 = com.android.server.sensorprivacy.SensorPrivacyService.this.getUiContext().getString(android.R.string.scNullCipherIssueNonEncryptedTitle);
                android.app.PendingIntent broadcast = android.app.PendingIntent.getBroadcast(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext, i, new android.content.Intent(com.android.server.sensorprivacy.SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY).setPackage(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getPackageName()).putExtra(android.hardware.SensorPrivacyManager.EXTRA_SENSOR, i).putExtra(android.hardware.SensorPrivacyManager.EXTRA_NOTIFICATION_ID, i4).putExtra("android.intent.extra.USER", userHandle), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
                android.app.NotificationManager notificationManager = com.android.server.sensorprivacy.SensorPrivacyService.this.mNotificationManager;
                android.app.Notification.Builder extend = new android.app.Notification.Builder(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext, com.android.server.sensorprivacy.SensorPrivacyService.SENSOR_PRIVACY_CHANNEL_ID).setContentTitle(string).setContentText(fromHtml).setSmallIcon(createWithResource).addAction(new android.app.Notification.Action.Builder(createWithResource, string2, broadcast).build()).setContentIntent(activity).extend(new android.app.Notification.TvExtender());
                if (isTelevision(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext)) {
                    j = 1;
                } else {
                    j = 0;
                }
                notificationManager.notify(i4, extend.setTimeoutAfter(j).build());
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.e(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Cannot show sensor use notification for " + str);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void showSensorStateChangedActivity(int i, int i2) {
            java.lang.String string = com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getResources().getString(android.R.string.config_satellite_service_package);
            if (android.text.TextUtils.isEmpty(string)) {
                return;
            }
            android.content.Intent intent = new android.content.Intent();
            intent.setComponent(android.content.ComponentName.unflattenFromString(string));
            android.app.ActivityOptions makeBasic = android.app.ActivityOptions.makeBasic();
            makeBasic.setTaskOverlay(true, true);
            intent.addFlags(8650752);
            intent.putExtra(android.hardware.SensorPrivacyManager.EXTRA_SENSOR, i);
            intent.putExtra(android.hardware.SensorPrivacyManager.EXTRA_TOGGLE_TYPE, i2);
            com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.startActivityAsUser(intent, makeBasic.toBundle(), android.os.UserHandle.SYSTEM);
        }

        private boolean isTelevision(android.content.Context context) {
            return (context.getResources().getConfiguration().uiMode & 15) == 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isAutomotive(android.content.Context context) {
            return (context.getResources().getConfiguration().uiMode & 15) == 3;
        }

        public void setSensorPrivacy(boolean z) {
            enforceManageSensorPrivacyPermission();
            this.mSensorPrivacyStateController.setAllSensorState(z);
        }

        public void setToggleSensorPrivacy(int i, int i2, int i3, boolean z) {
            int i4;
            enforceManageSensorPrivacyPermission();
            if (i != -2) {
                i4 = i;
            } else {
                i4 = com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser;
            }
            if (!canChangeToggleSensorPrivacy(i4, i3)) {
                return;
            }
            if (z && !supportsSensorToggle(1, i3)) {
                return;
            }
            setToggleSensorPrivacyUnchecked(1, i4, i2, i3, z);
        }

        @android.annotation.RequiresPermission("android.permission.MANAGE_SENSOR_PRIVACY")
        public void setToggleSensorPrivacyState(int i, int i2, int i3, int i4) {
            int i5;
            enforceManageSensorPrivacyPermission();
            if (i != -2) {
                i5 = i;
            } else {
                i5 = com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser;
            }
            if (!canChangeToggleSensorPrivacy(i5, i3) || !supportsSensorToggle(1, i3)) {
                return;
            }
            setToggleSensorPrivacyStateUnchecked(1, i5, i2, i3, i4);
        }

        private void setToggleSensorPrivacyStateUnchecked(final int i, final int i2, final int i3, final int i4, final int i5) {
            final long[] jArr = new long[1];
            this.mSensorPrivacyStateController.atomic(new java.lang.Runnable() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.lambda$setToggleSensorPrivacyStateUnchecked$3(i, i2, i4, jArr, i5, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setToggleSensorPrivacyStateUnchecked$3(int i, final int i2, final int i3, final long[] jArr, final int i4, final int i5) {
            jArr[0] = this.mSensorPrivacyStateController.getState(i, i2, i3).getLastChange();
            this.mSensorPrivacyStateController.setState(i, i2, i3, i4, this.mHandler, new com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda9
                @Override // com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback
                public final void callback(boolean z) {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.lambda$setToggleSensorPrivacyStateUnchecked$2(i2, i5, i3, i4, jArr, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setToggleSensorPrivacyStateUnchecked$2(int i, int i2, int i3, int i4, long[] jArr, boolean z) {
            if (z && i == com.android.server.sensorprivacy.SensorPrivacyService.this.mUserManagerInternal.getProfileParentId(i)) {
                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda8
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                        ((com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl) obj).logSensorPrivacyStateToggle(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), ((java.lang.Long) obj5).longValue(), ((java.lang.Boolean) obj6).booleanValue());
                    }
                }, this, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Long.valueOf(jArr[0]), false));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void logSensorPrivacyStateToggle(int i, int i2, int i3, long j, boolean z) {
            int i4;
            int i5;
            int i6;
            long max = java.lang.Math.max(0L, (com.android.server.sensorprivacy.SensorPrivacyService.getCurrentTimeMillis() - j) / 60000);
            if (z) {
                i4 = 0;
            } else {
                switch (i3) {
                    case 1:
                        i4 = 2;
                        break;
                    case 2:
                        i4 = 1;
                        break;
                    case 3:
                        i4 = 3;
                        break;
                    case 4:
                        i4 = 4;
                        break;
                    case 5:
                        i4 = 5;
                        break;
                    default:
                        i4 = 0;
                        break;
                }
            }
            switch (i2) {
                case 1:
                    i5 = 1;
                    break;
                case 2:
                    i5 = 2;
                    break;
                default:
                    i5 = 0;
                    break;
            }
            switch (i) {
                case 1:
                    i6 = 3;
                    break;
                case 2:
                    i6 = 2;
                    break;
                case 3:
                    i6 = 1;
                    break;
                default:
                    i6 = 0;
                    break;
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PRIVACY_SENSOR_TOGGLE_INTERACTION, i5, i4, i6, max);
        }

        @android.annotation.RequiresPermission("android.permission.MANAGE_SENSOR_PRIVACY")
        public void setToggleSensorPrivacyStateForProfileGroup(int i, final int i2, final int i3, final int i4) {
            enforceManageSensorPrivacyPermission();
            if (i == -2) {
                i = com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser;
            }
            final int profileParentId = com.android.server.sensorprivacy.SensorPrivacyService.this.mUserManagerInternal.getProfileParentId(i);
            com.android.server.sensorprivacy.SensorPrivacyService.this.forAllUsers(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda13
                public final void acceptOrThrow(java.lang.Object obj) {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.lambda$setToggleSensorPrivacyStateForProfileGroup$4(profileParentId, i2, i3, i4, (java.lang.Integer) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setToggleSensorPrivacyStateForProfileGroup$4(int i, int i2, int i3, int i4, java.lang.Integer num) throws java.lang.Exception {
            if (i == com.android.server.sensorprivacy.SensorPrivacyService.this.mUserManagerInternal.getProfileParentId(num.intValue())) {
                setToggleSensorPrivacyState(num.intValue(), i2, i3, i4);
            }
        }

        @android.annotation.RequiresPermission("android.permission.OBSERVE_SENSOR_PRIVACY")
        public java.util.List<android.hardware.CameraPrivacyAllowlistEntry> getCameraPrivacyAllowlist() {
            enforceObserveSensorPrivacyPermission();
            return com.android.server.sensorprivacy.SensorPrivacyService.this.mCameraPrivacyAllowlist;
        }

        @android.annotation.RequiresPermission("android.permission.OBSERVE_SENSOR_PRIVACY")
        public boolean isCameraPrivacyEnabled(java.lang.String str) {
            enforceObserveSensorPrivacyPermission();
            int state = this.mSensorPrivacyStateController.getState(1, com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser, 2).getState();
            if (state == 1) {
                return true;
            }
            if (state == 2) {
                return false;
            }
            if (state == 3) {
                for (android.hardware.CameraPrivacyAllowlistEntry cameraPrivacyAllowlistEntry : com.android.server.sensorprivacy.SensorPrivacyService.this.mCameraPrivacyAllowlist) {
                    if (str.equals(cameraPrivacyAllowlistEntry.packageName) && !cameraPrivacyAllowlistEntry.isMandatory) {
                        return false;
                    }
                }
                return true;
            }
            if (state == 4) {
                for (android.hardware.CameraPrivacyAllowlistEntry cameraPrivacyAllowlistEntry2 : com.android.server.sensorprivacy.SensorPrivacyService.this.mCameraPrivacyAllowlist) {
                    if (str.equals(cameraPrivacyAllowlistEntry2.packageName) && cameraPrivacyAllowlistEntry2.isMandatory) {
                        return false;
                    }
                }
                return true;
            }
            if (state != 5) {
                return false;
            }
            java.util.Iterator<android.hardware.CameraPrivacyAllowlistEntry> it = com.android.server.sensorprivacy.SensorPrivacyService.this.mCameraPrivacyAllowlist.iterator();
            while (it.hasNext()) {
                if (str.equals(it.next().packageName)) {
                    return false;
                }
            }
            return true;
        }

        @android.annotation.RequiresPermission("android.permission.OBSERVE_SENSOR_PRIVACY")
        public int getToggleSensorPrivacyState(int i, int i2) {
            enforceObserveSensorPrivacyPermission();
            return this.mSensorPrivacyStateController.getState(i, com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser, i2).getState();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setToggleSensorPrivacyUnchecked(final int i, final int i2, final int i3, final int i4, final boolean z) {
            final long[] jArr = new long[1];
            this.mSensorPrivacyStateController.atomic(new java.lang.Runnable() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.lambda$setToggleSensorPrivacyUnchecked$6(i, i2, i4, jArr, z, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setToggleSensorPrivacyUnchecked$6(int i, final int i2, final int i3, final long[] jArr, final boolean z, final int i4) {
            jArr[0] = this.mSensorPrivacyStateController.getState(i, i2, i3).getLastChange();
            this.mSensorPrivacyStateController.setState(i, i2, i3, z, this.mHandler, new com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda0
                @Override // com.android.server.sensorprivacy.SensorPrivacyStateController.SetStateResultCallback
                public final void callback(boolean z2) {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.lambda$setToggleSensorPrivacyUnchecked$5(i2, i4, i3, z, jArr, z2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setToggleSensorPrivacyUnchecked$5(int i, int i2, int i3, boolean z, long[] jArr, boolean z2) {
            if (z2 && i == com.android.server.sensorprivacy.SensorPrivacyService.this.mUserManagerInternal.getProfileParentId(i)) {
                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda12
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                        ((com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl) obj).logSensorPrivacyToggle(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Boolean) obj4).booleanValue(), ((java.lang.Long) obj5).longValue(), ((java.lang.Boolean) obj6).booleanValue());
                    }
                }, this, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Boolean.valueOf(z), java.lang.Long.valueOf(jArr[0]), false));
            }
        }

        private boolean canChangeToggleSensorPrivacy(int i, int i2) {
            if (i2 == 1 && com.android.server.sensorprivacy.SensorPrivacyService.this.mCallStateHelper.isInEmergencyCall()) {
                android.util.Log.i(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Can't change mic toggle during an emergency call");
                return false;
            }
            if (requiresAuthentication() && com.android.server.sensorprivacy.SensorPrivacyService.this.mKeyguardManager != null && com.android.server.sensorprivacy.SensorPrivacyService.this.mKeyguardManager.isDeviceLocked(i)) {
                android.util.Log.i(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Can't change mic/cam toggle while device is locked");
                return false;
            }
            if (i2 == 1 && com.android.server.sensorprivacy.SensorPrivacyService.this.mUserManagerInternal.getUserRestriction(i, "disallow_microphone_toggle")) {
                android.util.Log.i(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Can't change mic toggle due to admin restriction");
                return false;
            }
            if (i2 != 2 || !com.android.server.sensorprivacy.SensorPrivacyService.this.mUserManagerInternal.getUserRestriction(i, "disallow_camera_toggle")) {
                return true;
            }
            android.util.Log.i(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Can't change camera toggle due to admin restriction");
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void logSensorPrivacyToggle(int i, int i2, boolean z, long j, boolean z2) {
            int i3;
            int i4;
            int i5;
            long max = java.lang.Math.max(0L, (com.android.server.sensorprivacy.SensorPrivacyService.getCurrentTimeMillis() - j) / 60000);
            if (z2) {
                i3 = 0;
            } else if (z) {
                i3 = 2;
            } else {
                i3 = 1;
            }
            switch (i2) {
                case 1:
                    i4 = 1;
                    break;
                case 2:
                    i4 = 2;
                    break;
                default:
                    i4 = 0;
                    break;
            }
            switch (i) {
                case 1:
                    i5 = 3;
                    break;
                case 2:
                    i5 = 2;
                    break;
                case 3:
                    i5 = 1;
                    break;
                default:
                    i5 = 0;
                    break;
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PRIVACY_SENSOR_TOGGLE_INTERACTION, i4, i3, i5, max);
        }

        public void setToggleSensorPrivacyForProfileGroup(int i, final int i2, final int i3, final boolean z) {
            enforceManageSensorPrivacyPermission();
            if (i == -2) {
                i = com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser;
            }
            final int profileParentId = com.android.server.sensorprivacy.SensorPrivacyService.this.mUserManagerInternal.getProfileParentId(i);
            com.android.server.sensorprivacy.SensorPrivacyService.this.forAllUsers(new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda10
                public final void acceptOrThrow(java.lang.Object obj) {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.lambda$setToggleSensorPrivacyForProfileGroup$7(profileParentId, i2, i3, z, (java.lang.Integer) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setToggleSensorPrivacyForProfileGroup$7(int i, int i2, int i3, boolean z, java.lang.Integer num) throws java.lang.Exception {
            if (i == com.android.server.sensorprivacy.SensorPrivacyService.this.mUserManagerInternal.getProfileParentId(num.intValue())) {
                setToggleSensorPrivacy(num.intValue(), i2, i3, z);
            }
        }

        @android.annotation.RequiresPermission("android.permission.MANAGE_SENSOR_PRIVACY")
        private void enforceManageSensorPrivacyPermission() {
            if (com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_SENSOR_PRIVACY") == 0) {
            } else {
                throw new java.lang.SecurityException("Changing sensor privacy requires the following permission: android.permission.MANAGE_SENSOR_PRIVACY");
            }
        }

        @android.annotation.RequiresPermission("android.permission.OBSERVE_SENSOR_PRIVACY")
        private void enforceObserveSensorPrivacyPermission() {
            if (android.os.UserHandle.getCallingAppId() == android.os.UserHandle.getAppId(com.android.server.sensorprivacy.SensorPrivacyService.this.mPackageManagerInternal.getPackageUid(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getString(android.R.string.config_systemUi), 1048576L, 0)) || com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.checkCallingOrSelfPermission("android.permission.OBSERVE_SENSOR_PRIVACY") == 0) {
            } else {
                throw new java.lang.SecurityException("Observing sensor privacy changes requires the following permission: android.permission.OBSERVE_SENSOR_PRIVACY");
            }
        }

        public boolean isSensorPrivacyEnabled() {
            enforceObserveSensorPrivacyPermission();
            return this.mSensorPrivacyStateController.getAllSensorState();
        }

        public boolean isToggleSensorPrivacyEnabled(int i, int i2) {
            enforceObserveSensorPrivacyPermission();
            return this.mSensorPrivacyStateController.getState(i, com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser, i2).isEnabled();
        }

        public boolean isCombinedToggleSensorPrivacyEnabled(int i) {
            return isToggleSensorPrivacyEnabled(1, i) || isToggleSensorPrivacyEnabled(2, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isToggleSensorPrivacyEnabledInternal(int i, int i2, int i3) {
            return this.mSensorPrivacyStateController.getState(i2, i, i3).isEnabled();
        }

        public boolean supportsSensorToggle(int i, int i2) {
            if (i == 1) {
                if (i2 == 1) {
                    return com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getResources().getBoolean(android.R.bool.config_supportsHardwareMicToggle);
                }
                if (i2 == 2) {
                    return com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getResources().getBoolean(android.R.bool.config_supportSystemNavigationKeys);
                }
            } else if (i == 2) {
                if (i2 == 1) {
                    return com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getResources().getBoolean(android.R.bool.config_supportsConcurrentInternalDisplays);
                }
                if (i2 == 2) {
                    return com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getResources().getBoolean(android.R.bool.config_supportsCamToggle);
                }
            }
            throw new java.lang.IllegalArgumentException("Invalid arguments. toggleType=" + i + " sensor=" + i2);
        }

        public void addSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) {
            enforceObserveSensorPrivacyPermission();
            if (iSensorPrivacyListener == null) {
                throw new java.lang.NullPointerException("listener cannot be null");
            }
            this.mHandler.addListener(iSensorPrivacyListener);
        }

        public void addToggleSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) {
            enforceObserveSensorPrivacyPermission();
            if (iSensorPrivacyListener == null) {
                throw new java.lang.IllegalArgumentException("listener cannot be null");
            }
            this.mHandler.addToggleListener(iSensorPrivacyListener);
        }

        public void removeSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) {
            enforceObserveSensorPrivacyPermission();
            if (iSensorPrivacyListener == null) {
                throw new java.lang.NullPointerException("listener cannot be null");
            }
            this.mHandler.removeListener(iSensorPrivacyListener);
        }

        public void removeToggleSensorPrivacyListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) {
            enforceObserveSensorPrivacyPermission();
            if (iSensorPrivacyListener == null) {
                throw new java.lang.IllegalArgumentException("listener cannot be null");
            }
            this.mHandler.removeToggleListener(iSensorPrivacyListener);
        }

        public void suppressToggleSensorPrivacyReminders(int i, int i2, android.os.IBinder iBinder, boolean z) {
            enforceManageSensorPrivacyPermission();
            if (i == -2) {
                i = com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser;
            }
            java.util.Objects.requireNonNull(iBinder);
            android.util.Pair<java.lang.Integer, android.os.UserHandle> pair = new android.util.Pair<>(java.lang.Integer.valueOf(i2), android.os.UserHandle.of(i));
            synchronized (this.mLock) {
                try {
                    if (z) {
                        try {
                            iBinder.linkToDeath(this, 0);
                            java.util.ArrayList<android.os.IBinder> arrayList = this.mSuppressReminders.get(pair);
                            if (arrayList == null) {
                                arrayList = new java.util.ArrayList<>(1);
                                this.mSuppressReminders.put(pair, arrayList);
                            }
                            arrayList.add(iBinder);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Could not suppress sensor use reminder", e);
                        }
                    } else {
                        this.mHandler.removeSuppressPackageReminderToken(pair, iBinder);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean requiresAuthentication() {
            enforceObserveSensorPrivacyPermission();
            return com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getResources().getBoolean(android.R.bool.config_secondaryBuiltInDisplayIsRound);
        }

        public void showSensorUseDialog(int i) {
            if (android.os.Binder.getCallingUid() != 1000) {
                throw new java.lang.SecurityException("Can only be called by the system uid");
            }
            if (!isCombinedToggleSensorPrivacyEnabled(i)) {
                return;
            }
            enqueueSensorUseReminderDialogAsync(-1, android.os.UserHandle.of(com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser), com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void userSwitching(final int i, final int i2) {
            int i3;
            final boolean[] zArr = new boolean[2];
            final boolean[] zArr2 = new boolean[2];
            final boolean[] zArr3 = new boolean[2];
            final boolean[] zArr4 = new boolean[2];
            this.mSensorPrivacyStateController.atomic(new java.lang.Runnable() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.lambda$userSwitching$8(zArr3, i, zArr4, zArr, i2, zArr2);
                }
            });
            this.mSensorPrivacyStateController.atomic(new java.lang.Runnable() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.lambda$userSwitching$9(zArr3, i, zArr4, zArr, i2, zArr2);
                }
            });
            if (i != -10000 && zArr3[0] == zArr[0] && zArr3[1] == zArr[1]) {
                i3 = i2;
            } else {
                i3 = i2;
                this.mHandler.handleSensorPrivacyChanged(i3, 1, 1, zArr[0]);
                this.mHandler.handleSensorPrivacyChanged(i3, 2, 1, zArr[1]);
                setGlobalRestriction(1, zArr[0] || zArr[1]);
            }
            if (i == -10000 || zArr4[0] != zArr2[0] || zArr4[1] != zArr2[1]) {
                this.mHandler.handleSensorPrivacyChanged(i3, 1, 2, zArr2[0]);
                this.mHandler.handleSensorPrivacyChanged(i3, 2, 2, zArr2[1]);
                setGlobalRestriction(2, zArr2[0] || zArr2[1]);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$userSwitching$8(boolean[] zArr, int i, boolean[] zArr2, boolean[] zArr3, int i2, boolean[] zArr4) {
            zArr[0] = isToggleSensorPrivacyEnabledInternal(i, 1, 1);
            zArr2[0] = isToggleSensorPrivacyEnabledInternal(i, 1, 2);
            zArr3[0] = isToggleSensorPrivacyEnabledInternal(i2, 1, 1);
            zArr4[0] = isToggleSensorPrivacyEnabledInternal(i2, 1, 2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$userSwitching$9(boolean[] zArr, int i, boolean[] zArr2, boolean[] zArr3, int i2, boolean[] zArr4) {
            zArr[1] = isToggleSensorPrivacyEnabledInternal(i, 2, 1);
            zArr2[1] = isToggleSensorPrivacyEnabledInternal(i, 2, 2);
            zArr3[1] = isToggleSensorPrivacyEnabledInternal(i2, 2, 1);
            zArr4[1] = isToggleSensorPrivacyEnabledInternal(i2, 2, 2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setGlobalRestriction(int i, boolean z) {
            switch (i) {
                case 1:
                    com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsManagerInternal.setGlobalRestriction(27, z, com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsRestrictionToken);
                    com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsManagerInternal.setGlobalRestriction(136, z, com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsRestrictionToken);
                    com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsManagerInternal.setGlobalRestriction(100, z, com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsRestrictionToken);
                    com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsManagerInternal.setGlobalRestriction(120, z, com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsRestrictionToken);
                    com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsManagerInternal.setGlobalRestriction(121, z && !(android.provider.Settings.Global.getInt(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getContentResolver(), "receive_explicit_user_interaction_audio_enabled", 1) == 1), com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsRestrictionToken);
                    break;
                case 2:
                    com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsManagerInternal.setGlobalRestriction(26, z, com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsRestrictionToken);
                    com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsManagerInternal.setGlobalRestriction(101, z, com.android.server.sensorprivacy.SensorPrivacyService.this.mAppOpsRestrictionToken);
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeSuppressPackageReminderToken(@android.annotation.NonNull android.util.Pair<java.lang.Integer, android.os.UserHandle> pair, @android.annotation.NonNull android.os.IBinder iBinder) {
            synchronized (this.mLock) {
                try {
                    java.util.ArrayList<android.os.IBinder> arrayList = this.mSuppressReminders.get(pair);
                    if (arrayList == null) {
                        android.util.Log.e(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "No tokens for " + pair);
                        return;
                    }
                    if (arrayList.remove(iBinder)) {
                        iBinder.unlinkToDeath(this, 0);
                        if (arrayList.isEmpty()) {
                            this.mSuppressReminders.remove(pair);
                        }
                    } else {
                        android.util.Log.w(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Could not remove sensor use reminder suppression token " + iBinder + " from " + pair);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerSettingsObserver() {
            com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("receive_explicit_user_interaction_audio_enabled"), false, new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.3
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.setGlobalRestriction(1, com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.isCombinedToggleSensorPrivacyEnabled(1));
                }
            });
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied(@android.annotation.NonNull android.os.IBinder iBinder) {
            synchronized (this.mLock) {
                try {
                    java.util.Iterator<android.util.Pair<java.lang.Integer, android.os.UserHandle>> it = this.mSuppressReminders.keySet().iterator();
                    while (it.hasNext()) {
                        removeSuppressPackageReminderToken(it.next(), iBinder);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            java.lang.String str;
            java.util.Objects.requireNonNull(fileDescriptor);
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext, com.android.server.sensorprivacy.SensorPrivacyService.TAG, printWriter)) {
                int i = 0;
                boolean z = false;
                while (i < strArr.length && (str = strArr[i]) != null && str.length() > 0 && str.charAt(0) == '-') {
                    i++;
                    if ("--proto".equals(str)) {
                        z = true;
                    } else {
                        printWriter.println("Unknown argument: " + str + "; use -h for help");
                    }
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (z) {
                        this.mSensorPrivacyStateController.dump(new com.android.internal.util.dump.DualDumpOutputStream(new android.util.proto.ProtoOutputStream(fileDescriptor)));
                    } else {
                        printWriter.println("SENSOR PRIVACY MANAGER STATE (dumpsys sensor_privacy)");
                        this.mSensorPrivacyStateController.dump(new com.android.internal.util.dump.DualDumpOutputStream(new android.util.IndentingPrintWriter(printWriter, "  ")));
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int sensorStrToId(@android.annotation.Nullable java.lang.String str) {
            char c;
            if (str == null) {
                return 0;
            }
            switch (str.hashCode()) {
                case -1367751899:
                    if (str.equals("camera")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1370921258:
                    if (str.equals("microphone")) {
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
            }
            return 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @android.annotation.RequiresPermission("android.permission.MANAGE_SENSOR_PRIVACY")
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new android.os.ShellCommand() { // from class: com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.4
                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                @android.annotation.RequiresPermission("android.permission.MANAGE_SENSOR_PRIVACY")
                public int onCommand(java.lang.String str) {
                    boolean z;
                    if (str == null) {
                        return handleDefaultCommands(str);
                    }
                    int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                    java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                    switch (str.hashCode()) {
                        case -1298848381:
                            if (str.equals("enable")) {
                                z = false;
                                break;
                            }
                            z = -1;
                            break;
                        case -796493428:
                            if (str.equals("automotive_driver_assistance_apps")) {
                                z = 2;
                                break;
                            }
                            z = -1;
                            break;
                        case -383529137:
                            if (str.equals("automotive_driver_assistance_helpful_apps")) {
                                z = 3;
                                break;
                            }
                            z = -1;
                            break;
                        case 1436447032:
                            if (str.equals("automotive_driver_assistance_required_apps")) {
                                z = 4;
                                break;
                            }
                            z = -1;
                            break;
                        case 1671308008:
                            if (str.equals("disable")) {
                                z = true;
                                break;
                            }
                            z = -1;
                            break;
                        default:
                            z = -1;
                            break;
                    }
                    switch (z) {
                        case false:
                            int sensorStrToId = com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.sensorStrToId(getNextArgRequired());
                            if (sensorStrToId == 0) {
                                outPrintWriter.println("Invalid sensor");
                                return -1;
                            }
                            com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.setToggleSensorPrivacy(parseInt, 4, sensorStrToId, true);
                            return 0;
                        case true:
                            int sensorStrToId2 = com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.sensorStrToId(getNextArgRequired());
                            if (sensorStrToId2 == 0) {
                                outPrintWriter.println("Invalid sensor");
                                return -1;
                            }
                            com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.setToggleSensorPrivacy(parseInt, 4, sensorStrToId2, false);
                            return 0;
                        case true:
                            if (com.android.internal.camera.flags.Flags.cameraPrivacyAllowlist()) {
                                int sensorStrToId3 = com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.sensorStrToId(getNextArgRequired());
                                if (!com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.isAutomotive(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext) || sensorStrToId3 != 2) {
                                    outPrintWriter.println("Command not valid for this sensor");
                                    return -1;
                                }
                                com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.setToggleSensorPrivacyState(parseInt, 4, sensorStrToId3, 5);
                            }
                            return 0;
                        case true:
                            if (com.android.internal.camera.flags.Flags.cameraPrivacyAllowlist()) {
                                int sensorStrToId4 = com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.sensorStrToId(getNextArgRequired());
                                if (!com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.isAutomotive(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext) || sensorStrToId4 != 2) {
                                    outPrintWriter.println("Command not valid for this sensor");
                                    return -1;
                                }
                                com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.setToggleSensorPrivacyState(parseInt, 4, sensorStrToId4, 3);
                            }
                            return 0;
                        case true:
                            if (com.android.internal.camera.flags.Flags.cameraPrivacyAllowlist()) {
                                int sensorStrToId5 = com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.sensorStrToId(getNextArgRequired());
                                if (!com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.isAutomotive(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext) || sensorStrToId5 != 2) {
                                    outPrintWriter.println("Command not valid for this sensor");
                                    return -1;
                                }
                                com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.setToggleSensorPrivacyState(parseInt, 4, sensorStrToId5, 4);
                            }
                            return 0;
                        default:
                            return handleDefaultCommands(str);
                    }
                }

                public void onHelp() {
                    java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                    outPrintWriter.println("Sensor privacy manager (sensor_privacy) commands:");
                    outPrintWriter.println("  help");
                    outPrintWriter.println("    Print this help text.");
                    outPrintWriter.println("");
                    outPrintWriter.println("  enable USER_ID SENSOR");
                    outPrintWriter.println("    Enable privacy for a certain sensor.");
                    outPrintWriter.println("");
                    outPrintWriter.println("  disable USER_ID SENSOR");
                    outPrintWriter.println("    Disable privacy for a certain sensor.");
                    outPrintWriter.println("");
                    if (com.android.internal.camera.flags.Flags.cameraPrivacyAllowlist() && com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl.this.isAutomotive(com.android.server.sensorprivacy.SensorPrivacyService.this.mContext)) {
                        outPrintWriter.println("  automotive_driver_assistance_apps USER_ID SENSOR");
                        outPrintWriter.println("    Disable privacy for automotive apps which help you drive and apps which are required by OEM");
                        outPrintWriter.println("");
                        outPrintWriter.println("  automotive_driver_assistance_helpful_apps USER_ID SENSOR");
                        outPrintWriter.println("    Disable privacy for automotive apps which help you drive.");
                        outPrintWriter.println("");
                        outPrintWriter.println("  automotive_driver_assistance_required_apps USER_ID SENSOR");
                        outPrintWriter.println("    Disable privacy for automotive apps which are required by OEM.");
                        outPrintWriter.println("");
                    }
                }
            }.exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SensorPrivacyHandler extends android.os.Handler {
        private static final int MESSAGE_SENSOR_PRIVACY_CHANGED = 1;
        private final android.content.Context mContext;

        @com.android.internal.annotations.GuardedBy({"mListenerLock"})
        private final android.util.ArrayMap<android.hardware.ISensorPrivacyListener, android.util.Pair<com.android.server.sensorprivacy.SensorPrivacyService.DeathRecipient, java.lang.Integer>> mDeathRecipients;
        private final java.lang.Object mListenerLock;

        @com.android.internal.annotations.GuardedBy({"mListenerLock"})
        private final android.os.RemoteCallbackList<android.hardware.ISensorPrivacyListener> mListeners;

        @com.android.internal.annotations.GuardedBy({"mListenerLock"})
        private final android.os.RemoteCallbackList<android.hardware.ISensorPrivacyListener> mToggleSensorListeners;

        SensorPrivacyHandler(android.os.Looper looper, android.content.Context context) {
            super(looper);
            this.mListenerLock = new java.lang.Object();
            this.mListeners = new android.os.RemoteCallbackList<>();
            this.mToggleSensorListeners = new android.os.RemoteCallbackList<>();
            this.mDeathRecipients = new android.util.ArrayMap<>();
            this.mContext = context;
        }

        public void addListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) {
            synchronized (this.mListenerLock) {
                try {
                    if (this.mListeners.register(iSensorPrivacyListener)) {
                        addDeathRecipient(iSensorPrivacyListener);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void addToggleListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) {
            synchronized (this.mListenerLock) {
                try {
                    if (this.mToggleSensorListeners.register(iSensorPrivacyListener)) {
                        addDeathRecipient(iSensorPrivacyListener);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void removeListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) {
            synchronized (this.mListenerLock) {
                try {
                    if (this.mListeners.unregister(iSensorPrivacyListener)) {
                        removeDeathRecipient(iSensorPrivacyListener);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void removeToggleListener(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) {
            synchronized (this.mListenerLock) {
                try {
                    if (this.mToggleSensorListeners.unregister(iSensorPrivacyListener)) {
                        removeDeathRecipient(iSensorPrivacyListener);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void handleSensorPrivacyChanged(boolean z) {
            int beginBroadcast = this.mListeners.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                android.hardware.ISensorPrivacyListener broadcastItem = this.mListeners.getBroadcastItem(i);
                try {
                    broadcastItem.onSensorPrivacyChanged(-1, -1, z);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Caught an exception notifying listener " + broadcastItem + ": ", e);
                }
            }
            this.mListeners.finishBroadcast();
        }

        public void handleSensorPrivacyChanged(int i, int i2, int i3, boolean z) {
            com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyManagerInternal.dispatch(i, i3, z);
            if (i == com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser) {
                com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.setGlobalRestriction(i3, com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.isCombinedToggleSensorPrivacyEnabled(i3));
            }
            if (i != com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser) {
                return;
            }
            synchronized (this.mListenerLock) {
                try {
                    int beginBroadcast = this.mToggleSensorListeners.beginBroadcast();
                    for (int i4 = 0; i4 < beginBroadcast; i4++) {
                        android.hardware.ISensorPrivacyListener broadcastItem = this.mToggleSensorListeners.getBroadcastItem(i4);
                        try {
                            broadcastItem.onSensorPrivacyChanged(i2, i3, z);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Caught an exception notifying listener " + broadcastItem + ": ", e);
                        }
                    }
                } finally {
                    this.mToggleSensorListeners.finishBroadcast();
                }
            }
            com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.showSensorStateChangedActivity(i3, i2);
        }

        public void handleSensorPrivacyChanged(int i, int i2, int i3, int i4) {
            if (i == com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser) {
                com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.setGlobalRestriction(i3, com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.isCombinedToggleSensorPrivacyEnabled(i3));
            }
            if (i != com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser) {
                return;
            }
            synchronized (this.mListenerLock) {
                try {
                    int beginBroadcast = this.mToggleSensorListeners.beginBroadcast();
                    for (int i5 = 0; i5 < beginBroadcast; i5++) {
                        android.hardware.ISensorPrivacyListener broadcastItem = this.mToggleSensorListeners.getBroadcastItem(i5);
                        try {
                            broadcastItem.onSensorPrivacyStateChanged(i2, i3, i4);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(com.android.server.sensorprivacy.SensorPrivacyService.TAG, "Caught an exception notifying listener " + broadcastItem + ": ", e);
                        }
                    }
                } finally {
                    this.mToggleSensorListeners.finishBroadcast();
                }
            }
            com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.showSensorStateChangedActivity(i3, i2);
        }

        public void removeSuppressPackageReminderToken(android.util.Pair<java.lang.Integer, android.os.UserHandle> pair, android.os.IBinder iBinder) {
            sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyHandler$$ExternalSyntheticLambda0
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl) obj).removeSuppressPackageReminderToken((android.util.Pair) obj2, (android.os.IBinder) obj3);
                }
            }, com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl, pair, iBinder));
        }

        private void addDeathRecipient(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) {
            android.util.Pair<com.android.server.sensorprivacy.SensorPrivacyService.DeathRecipient, java.lang.Integer> pair;
            android.util.Pair<com.android.server.sensorprivacy.SensorPrivacyService.DeathRecipient, java.lang.Integer> pair2 = this.mDeathRecipients.get(iSensorPrivacyListener);
            if (pair2 != null) {
                pair = new android.util.Pair<>((com.android.server.sensorprivacy.SensorPrivacyService.DeathRecipient) pair2.first, java.lang.Integer.valueOf(((java.lang.Integer) pair2.second).intValue() + 1));
            } else {
                pair = new android.util.Pair<>(com.android.server.sensorprivacy.SensorPrivacyService.this.new DeathRecipient(iSensorPrivacyListener), 1);
            }
            this.mDeathRecipients.put(iSensorPrivacyListener, pair);
        }

        private void removeDeathRecipient(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) {
            android.util.Pair<com.android.server.sensorprivacy.SensorPrivacyService.DeathRecipient, java.lang.Integer> pair = this.mDeathRecipients.get(iSensorPrivacyListener);
            if (pair == null) {
                return;
            }
            int intValue = ((java.lang.Integer) pair.second).intValue() - 1;
            if (intValue == 0) {
                this.mDeathRecipients.remove(iSensorPrivacyListener);
                ((com.android.server.sensorprivacy.SensorPrivacyService.DeathRecipient) pair.first).destroy();
            } else {
                this.mDeathRecipients.put(iSensorPrivacyListener, new android.util.Pair<>((com.android.server.sensorprivacy.SensorPrivacyService.DeathRecipient) pair.first, java.lang.Integer.valueOf(intValue)));
            }
        }
    }

    private final class DeathRecipient implements android.os.IBinder.DeathRecipient {
        private android.hardware.ISensorPrivacyListener mListener;

        DeathRecipient(android.hardware.ISensorPrivacyListener iSensorPrivacyListener) {
            this.mListener = iSensorPrivacyListener;
            try {
                this.mListener.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.removeSensorPrivacyListener(this.mListener);
            com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.removeToggleSensorPrivacyListener(this.mListener);
        }

        public void destroy() {
            try {
                this.mListener.asBinder().unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forAllUsers(com.android.internal.util.FunctionalUtils.ThrowingConsumer<java.lang.Integer> throwingConsumer) {
        for (int i : this.mUserManagerInternal.getUserIds()) {
            throwingConsumer.accept(java.lang.Integer.valueOf(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SensorPrivacyManagerInternalImpl extends android.hardware.SensorPrivacyManagerInternal {
        private android.util.ArrayMap<java.lang.Integer, android.util.ArraySet<android.hardware.SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener>> mAllUserListeners;
        private android.util.ArrayMap<java.lang.Integer, android.util.ArrayMap<java.lang.Integer, android.util.ArraySet<android.hardware.SensorPrivacyManagerInternal.OnSensorPrivacyChangedListener>>> mListeners;
        private final java.lang.Object mLock;

        private SensorPrivacyManagerInternalImpl() {
            this.mListeners = new android.util.ArrayMap<>();
            this.mAllUserListeners = new android.util.ArrayMap<>();
            this.mLock = new java.lang.Object();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatch(final int i, int i2, final boolean z) {
            android.util.ArraySet<android.hardware.SensorPrivacyManagerInternal.OnSensorPrivacyChangedListener> arraySet;
            synchronized (this.mLock) {
                try {
                    android.util.ArraySet<android.hardware.SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener> arraySet2 = this.mAllUserListeners.get(java.lang.Integer.valueOf(i2));
                    if (arraySet2 != null) {
                        for (int i3 = 0; i3 < arraySet2.size(); i3++) {
                            final android.hardware.SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener valueAt = arraySet2.valueAt(i3);
                            com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyManagerInternalImpl$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    valueAt.onSensorPrivacyChanged(i, z);
                                }
                            });
                        }
                    }
                    android.util.ArrayMap<java.lang.Integer, android.util.ArraySet<android.hardware.SensorPrivacyManagerInternal.OnSensorPrivacyChangedListener>> arrayMap = this.mListeners.get(java.lang.Integer.valueOf(i));
                    if (arrayMap != null && (arraySet = arrayMap.get(java.lang.Integer.valueOf(i2))) != null) {
                        for (int i4 = 0; i4 < arraySet.size(); i4++) {
                            final android.hardware.SensorPrivacyManagerInternal.OnSensorPrivacyChangedListener valueAt2 = arraySet.valueAt(i4);
                            com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.sensorprivacy.SensorPrivacyService$SensorPrivacyManagerInternalImpl$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    valueAt2.onSensorPrivacyChanged(z);
                                }
                            });
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean isSensorPrivacyEnabled(int i, int i2) {
            return com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.isToggleSensorPrivacyEnabledInternal(i, 1, i2);
        }

        public void addSensorPrivacyListener(int i, int i2, android.hardware.SensorPrivacyManagerInternal.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
            synchronized (this.mLock) {
                try {
                    android.util.ArrayMap<java.lang.Integer, android.util.ArraySet<android.hardware.SensorPrivacyManagerInternal.OnSensorPrivacyChangedListener>> arrayMap = this.mListeners.get(java.lang.Integer.valueOf(i));
                    if (arrayMap == null) {
                        arrayMap = new android.util.ArrayMap<>();
                        this.mListeners.put(java.lang.Integer.valueOf(i), arrayMap);
                    }
                    android.util.ArraySet<android.hardware.SensorPrivacyManagerInternal.OnSensorPrivacyChangedListener> arraySet = arrayMap.get(java.lang.Integer.valueOf(i2));
                    if (arraySet == null) {
                        arraySet = new android.util.ArraySet<>();
                        arrayMap.put(java.lang.Integer.valueOf(i2), arraySet);
                    }
                    arraySet.add(onSensorPrivacyChangedListener);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void addSensorPrivacyListenerForAllUsers(int i, android.hardware.SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener onUserSensorPrivacyChangedListener) {
            synchronized (this.mLock) {
                try {
                    android.util.ArraySet<android.hardware.SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener> arraySet = this.mAllUserListeners.get(java.lang.Integer.valueOf(i));
                    if (arraySet == null) {
                        arraySet = new android.util.ArraySet<>();
                        this.mAllUserListeners.put(java.lang.Integer.valueOf(i), arraySet);
                    }
                    arraySet.add(onUserSensorPrivacyChangedListener);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setPhysicalToggleSensorPrivacy(int i, int i2, boolean z) {
            com.android.server.sensorprivacy.SensorPrivacyService.SensorPrivacyServiceImpl sensorPrivacyServiceImpl = com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl;
            if (i == -2) {
                i = com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser;
            }
            if (i == -10000) {
                i = com.android.server.sensorprivacy.SensorPrivacyService.this.mContext.getUserId();
            }
            sensorPrivacyServiceImpl.setToggleSensorPrivacyUnchecked(2, i, 5, i2, z);
            if (!z) {
                sensorPrivacyServiceImpl.setToggleSensorPrivacyUnchecked(1, i, 5, i2, z);
            }
        }
    }

    private class CallStateHelper {
        private boolean mIsInEmergencyCall;
        private boolean mMicUnmutedForEmergencyCall;
        private java.lang.Object mCallStateLock = new java.lang.Object();
        private com.android.server.sensorprivacy.SensorPrivacyService.CallStateHelper.OutgoingEmergencyStateCallback mEmergencyStateCallback = new com.android.server.sensorprivacy.SensorPrivacyService.CallStateHelper.OutgoingEmergencyStateCallback();
        private com.android.server.sensorprivacy.SensorPrivacyService.CallStateHelper.CallStateCallback mCallStateCallback = new com.android.server.sensorprivacy.SensorPrivacyService.CallStateHelper.CallStateCallback();

        CallStateHelper() {
            com.android.server.sensorprivacy.SensorPrivacyService.this.mTelephonyManager.registerTelephonyCallback(com.android.server.FgThread.getExecutor(), this.mEmergencyStateCallback);
            com.android.server.sensorprivacy.SensorPrivacyService.this.mTelephonyManager.registerTelephonyCallback(com.android.server.FgThread.getExecutor(), this.mCallStateCallback);
        }

        boolean isInEmergencyCall() {
            boolean z;
            synchronized (this.mCallStateLock) {
                z = this.mIsInEmergencyCall;
            }
            return z;
        }

        private class OutgoingEmergencyStateCallback extends android.telephony.TelephonyCallback implements android.telephony.TelephonyCallback.OutgoingEmergencyCallListener {
            private OutgoingEmergencyStateCallback() {
            }

            public void onOutgoingEmergencyCall(android.telephony.emergency.EmergencyNumber emergencyNumber, int i) {
                com.android.server.sensorprivacy.SensorPrivacyService.CallStateHelper.this.onEmergencyCall();
            }
        }

        private class CallStateCallback extends android.telephony.TelephonyCallback implements android.telephony.TelephonyCallback.CallStateListener {
            private CallStateCallback() {
            }

            @Override // android.telephony.TelephonyCallback.CallStateListener
            public void onCallStateChanged(int i) {
                if (i == 0) {
                    com.android.server.sensorprivacy.SensorPrivacyService.CallStateHelper.this.onCallOver();
                } else {
                    com.android.server.sensorprivacy.SensorPrivacyService.CallStateHelper.this.onCall();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onEmergencyCall() {
            synchronized (this.mCallStateLock) {
                try {
                    if (!this.mIsInEmergencyCall) {
                        this.mIsInEmergencyCall = true;
                        if (com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.isToggleSensorPrivacyEnabled(1, 1)) {
                            com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.setToggleSensorPrivacyUnchecked(1, com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser, 5, 1, false);
                            this.mMicUnmutedForEmergencyCall = true;
                        } else {
                            this.mMicUnmutedForEmergencyCall = false;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onCall() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (this.mCallStateLock) {
                    com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.showSensorUseDialog(1);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onCallOver() {
            synchronized (this.mCallStateLock) {
                try {
                    if (this.mIsInEmergencyCall) {
                        this.mIsInEmergencyCall = false;
                        if (this.mMicUnmutedForEmergencyCall) {
                            com.android.server.sensorprivacy.SensorPrivacyService.this.mSensorPrivacyServiceImpl.setToggleSensorPrivacyUnchecked(1, com.android.server.sensorprivacy.SensorPrivacyService.this.mCurrentUser, 5, 1, true);
                            this.mMicUnmutedForEmergencyCall = false;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    static long getCurrentTimeMillis() {
        return android.os.SystemClock.elapsedRealtime();
    }
}
