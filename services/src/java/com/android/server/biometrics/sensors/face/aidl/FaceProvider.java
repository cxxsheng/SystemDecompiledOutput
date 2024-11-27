package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class FaceProvider implements android.os.IBinder.DeathRecipient, com.android.server.biometrics.sensors.face.ServiceProvider {
    private static final int ENROLL_TIMEOUT_SEC = 75;
    private static final java.lang.String TAG = "FaceProvider";

    @android.annotation.NonNull
    private final android.app.ActivityTaskManager mActivityTaskManager;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthSessionCoordinator mAuthSessionCoordinator;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthenticationStateListeners mAuthenticationStateListeners;

    @android.annotation.Nullable
    private com.android.server.biometrics.AuthenticationStatsCollector mAuthenticationStatsCollector;

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.BiometricContext mBiometricContext;

    @android.annotation.NonNull
    private final com.android.server.biometrics.BiometricHandlerProvider mBiometricHandlerProvider;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.BiometricStateCallback mBiometricStateCallback;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private android.hardware.biometrics.face.IFace mDaemon;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.biometrics.sensors.SensorList<com.android.server.biometrics.sensors.face.aidl.Sensor> mFaceSensors;

    @android.annotation.NonNull
    private final java.lang.String mHalInstanceName;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;

    @android.annotation.NonNull
    private final java.util.concurrent.atomic.AtomicLong mRequestCounter;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.aidl.FaceProvider.BiometricTaskStackListener mTaskStackListener;
    private boolean mTestHalEnabled;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.UsageStats mUsageStats;

    /* JADX INFO: Access modifiers changed from: private */
    final class BiometricTaskStackListener extends android.app.TaskStackListener {
        private BiometricTaskStackListener() {
        }

        public void onTaskStackChanged() {
            com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$BiometricTaskStackListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.face.aidl.FaceProvider.BiometricTaskStackListener.this.lambda$onTaskStackChanged$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskStackChanged$0() {
            for (int i = 0; i < com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.mFaceSensors.size(); i++) {
                com.android.server.biometrics.sensors.BaseClientMonitor currentClient = com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.mFaceSensors.valueAt(i).getScheduler().getCurrentClient();
                if (!(currentClient instanceof com.android.server.biometrics.sensors.AuthenticationClient)) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.getTag(), "Task stack changed for client: " + currentClient);
                } else if (!com.android.server.biometrics.Utils.isKeyguard(com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.mContext, currentClient.getOwnerString()) && !com.android.server.biometrics.Utils.isSystem(com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.mContext, currentClient.getOwnerString()) && com.android.server.biometrics.Utils.isBackground(currentClient.getOwnerString()) && !currentClient.isAlreadyDone()) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.getTag(), "Stopping background authentication, currentClient: " + currentClient);
                    com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.mFaceSensors.valueAt(i).getScheduler().cancelAuthenticationOrDetection(currentClient.getToken(), currentClient.getRequestId());
                }
            }
        }
    }

    public FaceProvider(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, @android.annotation.NonNull android.hardware.biometrics.face.SensorProps[] sensorPropsArr, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z) {
        this(context, biometricStateCallback, authenticationStateListeners, sensorPropsArr, str, lockoutResetDispatcher, biometricContext, null, com.android.server.biometrics.BiometricHandlerProvider.getInstance(), z, false);
    }

    @com.android.internal.annotations.VisibleForTesting
    FaceProvider(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, @android.annotation.NonNull android.hardware.biometrics.face.SensorProps[] sensorPropsArr, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.Nullable android.hardware.biometrics.face.IFace iFace, @android.annotation.NonNull com.android.server.biometrics.BiometricHandlerProvider biometricHandlerProvider, boolean z, boolean z2) {
        this.mRequestCounter = new java.util.concurrent.atomic.AtomicLong(0L);
        this.mContext = context;
        this.mBiometricStateCallback = biometricStateCallback;
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mHalInstanceName = str;
        this.mFaceSensors = new com.android.server.biometrics.sensors.SensorList<>(android.app.ActivityManager.getService());
        com.android.server.biometrics.Flags.deHidl();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mUsageStats = new com.android.server.biometrics.sensors.face.UsageStats(context);
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mActivityTaskManager = android.app.ActivityTaskManager.getInstance();
        this.mTaskStackListener = new com.android.server.biometrics.sensors.face.aidl.FaceProvider.BiometricTaskStackListener();
        this.mBiometricContext = biometricContext;
        this.mAuthSessionCoordinator = this.mBiometricContext.getAuthSessionCoordinator();
        this.mDaemon = iFace;
        this.mTestHalEnabled = z2;
        this.mBiometricHandlerProvider = biometricHandlerProvider;
        initAuthenticationBroadcastReceiver();
        initSensors(z, sensorPropsArr);
    }

    private void initAuthenticationBroadcastReceiver() {
        new com.android.server.biometrics.AuthenticationStatsBroadcastReceiver(this.mContext, 4, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$initAuthenticationBroadcastReceiver$0((com.android.server.biometrics.AuthenticationStatsCollector) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAuthenticationBroadcastReceiver$0(com.android.server.biometrics.AuthenticationStatsCollector authenticationStatsCollector) {
        android.util.Slog.d(getTag(), "Initializing AuthenticationStatsCollector");
        this.mAuthenticationStatsCollector = authenticationStatsCollector;
    }

    private void initSensors(boolean z, android.hardware.biometrics.face.SensorProps[] sensorPropsArr) {
        com.android.server.biometrics.Flags.deHidl();
        for (android.hardware.biometrics.face.SensorProps sensorProps : sensorPropsArr) {
            final int i = sensorProps.commonProps.sensorId;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (sensorProps.commonProps.componentInfo != null) {
                android.hardware.biometrics.common.ComponentInfo[] componentInfoArr = sensorProps.commonProps.componentInfo;
                int length = componentInfoArr.length;
                int i2 = 0;
                while (i2 < length) {
                    android.hardware.biometrics.common.ComponentInfo componentInfo = componentInfoArr[i2];
                    arrayList.add(new android.hardware.biometrics.ComponentInfoInternal(componentInfo.componentId, componentInfo.hardwareVersion, componentInfo.firmwareVersion, componentInfo.serialNumber, componentInfo.softwareVersion));
                    i2++;
                    componentInfoArr = componentInfoArr;
                }
            }
            android.hardware.face.FaceSensorPropertiesInternal faceSensorPropertiesInternal = new android.hardware.face.FaceSensorPropertiesInternal(sensorProps.commonProps.sensorId, sensorProps.commonProps.sensorStrength, sensorProps.commonProps.maxEnrollmentsPerUser, arrayList, sensorProps.sensorType, sensorProps.supportsDetectInteraction, sensorProps.halControlsPreview, false);
            com.android.server.biometrics.sensors.face.aidl.Sensor sensor = new com.android.server.biometrics.sensors.face.aidl.Sensor(this, this.mContext, this.mHandler, faceSensorPropertiesInternal, this.mBiometricContext);
            sensor.init(this.mLockoutResetDispatcher, this);
            this.mFaceSensors.addSensor(i, sensor, sensor.getLazySession().get() == null ? com.android.server.am.ProcessList.INVALID_ADJ : sensor.getLazySession().get().getUserId(), new android.app.SynchronousUserSwitchObserver() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider.1
                public void onUserSwitching(int i3) {
                    com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.scheduleInternalCleanup(i, i3, null);
                }
            });
            android.util.Slog.d(getTag(), "Added: " + faceSensorPropertiesInternal);
        }
    }

    private void addHidlSensors(android.hardware.biometrics.face.SensorProps sensorProps, boolean z) {
        final int i = sensorProps.commonProps.sensorId;
        com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter hidlToAidlSensorAdapter = new com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter(this, this.mContext, this.mHandler, sensorProps, this.mLockoutResetDispatcher, this.mBiometricContext, z, new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$addHidlSensors$1(i);
            }
        });
        hidlToAidlSensorAdapter.init(this.mLockoutResetDispatcher, this);
        this.mFaceSensors.addSensor(i, hidlToAidlSensorAdapter, hidlToAidlSensorAdapter.getLazySession().get() == null ? com.android.server.am.ProcessList.INVALID_ADJ : hidlToAidlSensorAdapter.getLazySession().get().getUserId(), new android.app.SynchronousUserSwitchObserver() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider.2
            public void onUserSwitching(int i2) {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.scheduleInternalCleanup(i, i2, null);
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.scheduleGetFeature(i, new android.os.Binder(), i2, 1, null, com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.mContext.getOpPackageName());
            }
        });
        android.util.Slog.d(getTag(), "Added: " + this.mFaceSensors.get(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHidlSensors$1(int i) {
        scheduleInternalCleanup(i, android.app.ActivityManager.getCurrentUser(), null);
        scheduleGetFeature(i, new android.os.Binder(), android.app.ActivityManager.getCurrentUser(), 1, null, this.mContext.getOpPackageName());
    }

    private void addAidlSensors(android.hardware.biometrics.face.SensorProps sensorProps, boolean z) {
        final int i = sensorProps.commonProps.sensorId;
        com.android.server.biometrics.sensors.face.aidl.Sensor sensor = new com.android.server.biometrics.sensors.face.aidl.Sensor(this, this.mContext, this.mHandler, sensorProps, this.mBiometricContext, z);
        sensor.init(this.mLockoutResetDispatcher, this);
        this.mFaceSensors.addSensor(i, sensor, sensor.getLazySession().get() == null ? com.android.server.am.ProcessList.INVALID_ADJ : sensor.getLazySession().get().getUserId(), new android.app.SynchronousUserSwitchObserver() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider.3
            public void onUserSwitching(int i2) {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.scheduleInternalCleanup(i, i2, null);
            }
        });
        android.util.Slog.d(getTag(), "Added: " + this.mFaceSensors.get(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getTag() {
        return "FaceProvider/" + this.mHalInstanceName;
    }

    boolean hasHalInstance() {
        if (this.mTestHalEnabled) {
            return true;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.hardware.biometrics.face.IFace.DESCRIPTOR);
        sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
        sb.append(this.mHalInstanceName);
        return android.os.ServiceManager.checkService(sb.toString()) != null;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    synchronized android.hardware.biometrics.face.IFace getHalInstance() {
        if (this.mTestHalEnabled) {
            return new com.android.server.biometrics.sensors.face.aidl.TestHal();
        }
        if (this.mDaemon != null) {
            return this.mDaemon;
        }
        android.util.Slog.d(getTag(), "Daemon was null, reconnecting");
        this.mDaemon = android.hardware.biometrics.face.IFace.Stub.asInterface(android.os.Binder.allowBlocking(android.os.ServiceManager.waitForDeclaredService(android.hardware.biometrics.face.IFace.DESCRIPTOR + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mHalInstanceName)));
        if (this.mDaemon == null) {
            android.util.Slog.e(getTag(), "Unable to get daemon");
            return null;
        }
        try {
            this.mDaemon.asBinder().linkToDeath(this, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(getTag(), "Unable to linkToDeath", e);
        }
        for (int i = 0; i < this.mFaceSensors.size(); i++) {
            int keyAt = this.mFaceSensors.keyAt(i);
            scheduleLoadAuthenticatorIds(keyAt);
            scheduleInternalCleanup(keyAt, android.app.ActivityManager.getCurrentUser(), null);
        }
        if (android.os.Build.isDebuggable()) {
            com.android.server.biometrics.sensors.face.FaceUtils faceUtils = com.android.server.biometrics.sensors.face.FaceUtils.getInstance(this.mFaceSensors.keyAt(0));
            for (android.content.pm.UserInfo userInfo : android.os.UserManager.get(this.mContext).getAliveUsers()) {
                java.util.List<android.hardware.face.Face> biometricsForUser = faceUtils.getBiometricsForUser(this.mContext, userInfo.id);
                android.util.Slog.d(getTag(), "Expecting enrollments for user " + userInfo.id + ": " + biometricsForUser.stream().map(new java.util.function.Function() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda17
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return java.lang.Integer.valueOf(((android.hardware.face.Face) obj).getBiometricId());
                    }
                }).toList());
            }
        }
        return this.mDaemon;
    }

    private void scheduleForSensor(int i, @android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        if (!this.mFaceSensors.contains(i)) {
            throw new java.lang.IllegalStateException("Unable to schedule client: " + baseClientMonitor + " for sensor: " + i);
        }
        this.mFaceSensors.get(i).getScheduler().scheduleClientMonitor(baseClientMonitor);
    }

    private void scheduleForSensor(int i, @android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        if (!this.mFaceSensors.contains(i)) {
            throw new java.lang.IllegalStateException("Unable to schedule client: " + baseClientMonitor + " for sensor: " + i);
        }
        this.mFaceSensors.get(i).getScheduler().scheduleClientMonitor(baseClientMonitor, clientMonitorCallback);
    }

    private void scheduleLoadAuthenticatorIds(int i) {
        java.util.Iterator it = android.os.UserManager.get(this.mContext).getAliveUsers().iterator();
        while (it.hasNext()) {
            scheduleLoadAuthenticatorIdsForUser(i, ((android.content.pm.UserInfo) it.next()).id);
        }
    }

    protected void scheduleLoadAuthenticatorIdsForUser(final int i, final int i2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleLoadAuthenticatorIdsForUser$2(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleLoadAuthenticatorIdsForUser$2(int i, int i2) {
        scheduleForSensor(i, new com.android.server.biometrics.sensors.face.aidl.FaceGetAuthenticatorIdClient(this.mContext, this.mFaceSensors.get(i).getLazySession(), i2, this.mContext.getOpPackageName(), i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mFaceSensors.get(i).getAuthenticatorIds()));
    }

    void scheduleInvalidationRequest(final int i, final int i2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleInvalidationRequest$3(i2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleInvalidationRequest$3(int i, int i2) {
        scheduleForSensor(i2, new com.android.server.biometrics.sensors.InvalidationRequesterClient(this.mContext, i, i2, com.android.server.biometrics.log.BiometricLogger.ofUnknown(this.mContext), this.mBiometricContext, com.android.server.biometrics.sensors.face.FaceUtils.getInstance(i2)));
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean containsSensor(int i) {
        return this.mFaceSensors.contains(i);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    @android.annotation.NonNull
    public java.util.List<android.hardware.face.FaceSensorPropertiesInternal> getSensorProperties() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mFaceSensors.size(); i++) {
            arrayList.add(this.mFaceSensors.valueAt(i).getSensorProperties());
        }
        return arrayList;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    @android.annotation.NonNull
    public android.hardware.face.FaceSensorPropertiesInternal getSensorProperties(int i) {
        return this.mFaceSensors.get(i).getSensorProperties();
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    @android.annotation.NonNull
    public java.util.List<android.hardware.face.Face> getEnrolledFaces(int i, int i2) {
        return com.android.server.biometrics.sensors.face.FaceUtils.getInstance(i).getBiometricsForUser(this.mContext, i2);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean hasEnrollments(int i, int i2) {
        return !getEnrolledFaces(i, i2).isEmpty();
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleInvalidateAuthenticatorId(final int i, final int i2, @android.annotation.NonNull final android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleInvalidateAuthenticatorId$4(i, i2, iInvalidationCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleInvalidateAuthenticatorId$4(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
        scheduleForSensor(i, new com.android.server.biometrics.sensors.face.aidl.FaceInvalidationClient(this.mContext, this.mFaceSensors.get(i).getLazySession(), i2, i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mFaceSensors.get(i).getAuthenticatorIds(), iInvalidationCallback));
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public int getLockoutModeForUser(int i, int i2) {
        com.android.server.biometrics.Flags.deHidl();
        return this.mBiometricContext.getAuthSessionCoordinator().getLockoutStateFor(i2, com.android.server.biometrics.Utils.getCurrentStrength(i));
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public long getAuthenticatorId(int i, int i2) {
        return this.mFaceSensors.get(i).getAuthenticatorIds().getOrDefault(java.lang.Integer.valueOf(i2), 0L).longValue();
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean isHardwareDetected(int i) {
        com.android.server.biometrics.Flags.deHidl();
        return hasHalInstance();
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleGenerateChallenge(final int i, final int i2, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleGenerateChallenge$5(i, i2, iBinder, iFaceServiceReceiver, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleGenerateChallenge$5(int i, int i2, android.os.IBinder iBinder, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) {
        this.mFaceSensors.get(i).scheduleFaceUpdateActiveUserClient(i2);
        scheduleForSensor(i, new com.android.server.biometrics.sensors.face.aidl.FaceGenerateChallengeClient(this.mContext, this.mFaceSensors.get(i).getLazySession(), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), i2, str, i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext));
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleRevokeChallenge(final int i, final int i2, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final java.lang.String str, final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleRevokeChallenge$6(i, iBinder, i2, str, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRevokeChallenge$6(int i, android.os.IBinder iBinder, int i2, java.lang.String str, long j) {
        scheduleForSensor(i, new com.android.server.biometrics.sensors.face.aidl.FaceRevokeChallengeClient(this.mContext, this.mFaceSensors.get(i).getLazySession(), iBinder, i2, str, i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, j));
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public long scheduleEnroll(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final byte[] bArr, final int i2, @android.annotation.NonNull final android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final int[] iArr, @android.annotation.Nullable final android.view.Surface surface, final boolean z, final android.hardware.face.FaceEnrollOptions faceEnrollOptions) {
        final long incrementAndGet = this.mRequestCounter.incrementAndGet();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleEnroll$7(i, i2, iBinder, iFaceServiceReceiver, bArr, str, incrementAndGet, iArr, surface, z, faceEnrollOptions);
            }
        });
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleEnroll$7(final int i, final int i2, android.os.IBinder iBinder, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, byte[] bArr, java.lang.String str, long j, int[] iArr, android.view.Surface surface, boolean z, android.hardware.face.FaceEnrollOptions faceEnrollOptions) {
        this.mFaceSensors.get(i).scheduleFaceUpdateActiveUserClient(i2);
        com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient faceEnrollClient = new com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient(this.mContext, this.mFaceSensors.get(i).getLazySession(), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), i2, bArr, str, j, com.android.server.biometrics.sensors.face.FaceUtils.getInstance(i), iArr, 75, surface, i, createLogger(1, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mFaceSensors.get(i).getSensorProperties().maxEnrollmentsPerUser, z, faceEnrollOptions);
        com.android.server.biometrics.Flags.deHidl();
        scheduleForSensor(i, faceEnrollClient, new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(this.mBiometricStateCallback, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider.4
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z2) {
                super.onClientFinished(baseClientMonitor, z2);
                if (z2) {
                    com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.scheduleLoadAuthenticatorIdsForUser(i, i2);
                    com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.scheduleInvalidationRequest(i, i2);
                }
            }
        }));
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void cancelEnrollment(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$cancelEnrollment$8(i, iBinder, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelEnrollment$8(int i, android.os.IBinder iBinder, long j) {
        this.mFaceSensors.get(i).getScheduler().cancelEnrollment(iBinder, j);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public long scheduleFaceDetect(@android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull final android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, final int i) {
        final long incrementAndGet = this.mRequestCounter.incrementAndGet();
        final int sensorId = faceAuthenticateOptions.getSensorId();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleFaceDetect$9(sensorId, iBinder, incrementAndGet, clientMonitorCallbackConverter, faceAuthenticateOptions, i);
            }
        });
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleFaceDetect$9(int i, android.os.IBinder iBinder, long j, com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, int i2) {
        scheduleForSensor(i, new com.android.server.biometrics.sensors.face.aidl.FaceDetectClient(this.mContext, this.mFaceSensors.get(i).getLazySession(), iBinder, j, clientMonitorCallbackConverter, faceAuthenticateOptions, createLogger(2, i2, this.mAuthenticationStatsCollector), this.mBiometricContext, com.android.server.biometrics.Utils.isStrongBiometric(i)), this.mBiometricStateCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelFaceDetect$10(int i, android.os.IBinder iBinder, long j) {
        this.mFaceSensors.get(i).getScheduler().cancelAuthenticationOrDetection(iBinder, j);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void cancelFaceDetect(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$cancelFaceDetect$10(i, iBinder, j);
            }
        });
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleAuthenticate(@android.annotation.NonNull final android.os.IBinder iBinder, final long j, final int i, @android.annotation.NonNull final com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull final android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, final long j2, final boolean z, final int i2, final boolean z2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleAuthenticate$11(faceAuthenticateOptions, iBinder, j2, clientMonitorCallbackConverter, j, z, i, i2, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleAuthenticate$11(android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, android.os.IBinder iBinder, final long j, com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, int i, int i2, boolean z2) {
        final int userId = faceAuthenticateOptions.getUserId();
        final int sensorId = faceAuthenticateOptions.getSensorId();
        boolean isStrongBiometric = com.android.server.biometrics.Utils.isStrongBiometric(sensorId);
        this.mFaceSensors.get(sensorId).scheduleFaceUpdateActiveUserClient(userId);
        com.android.server.biometrics.Flags.deHidl();
        final com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient faceAuthenticationClient = new com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient(this.mContext, this.mFaceSensors.get(sensorId).getLazySession(), iBinder, j, clientMonitorCallbackConverter, j2, z, faceAuthenticateOptions, i, false, createLogger(2, i2, this.mAuthenticationStatsCollector), this.mBiometricContext, isStrongBiometric, this.mUsageStats, null, z2, com.android.server.biometrics.Utils.getCurrentStrength(sensorId), this.mAuthenticationStateListeners);
        scheduleForSensor(sensorId, faceAuthenticationClient, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider.5
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
                com.android.server.biometrics.Flags.deHidl();
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.mAuthSessionCoordinator.authStartedFor(userId, sensorId, j);
            }

            private /* synthetic */ void lambda$onClientStarted$0(int i3, int i4, long j3) {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.mAuthSessionCoordinator.authStartedFor(i3, i4, j3);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z3) {
                com.android.server.biometrics.Flags.deHidl();
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.mAuthSessionCoordinator.authEndedFor(userId, com.android.server.biometrics.Utils.getCurrentStrength(sensorId), sensorId, j, faceAuthenticationClient.wasAuthSuccessful());
            }

            private /* synthetic */ void lambda$onClientFinished$1(int i3, int i4, long j3, com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient faceAuthenticationClient2) {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.mAuthSessionCoordinator.authEndedFor(i3, com.android.server.biometrics.Utils.getCurrentStrength(i4), i4, j3, faceAuthenticationClient2.wasAuthSuccessful());
            }
        });
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public long scheduleAuthenticate(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, boolean z, int i2, boolean z2) {
        long incrementAndGet = this.mRequestCounter.incrementAndGet();
        scheduleAuthenticate(iBinder, j, i, clientMonitorCallbackConverter, faceAuthenticateOptions, incrementAndGet, z, i2, z2);
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelAuthentication$12(int i, android.os.IBinder iBinder, long j) {
        this.mFaceSensors.get(i).getScheduler().cancelAuthenticationOrDetection(iBinder, j);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void cancelAuthentication(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$cancelAuthentication$12(i, iBinder, j);
            }
        });
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleRemove(int i, @android.annotation.NonNull android.os.IBinder iBinder, int i2, int i3, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str) {
        scheduleRemoveSpecifiedIds(i, iBinder, new int[]{i2}, i3, iFaceServiceReceiver, str);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleRemoveAll(int i, @android.annotation.NonNull android.os.IBinder iBinder, int i2, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str) {
        java.util.List<android.hardware.face.Face> biometricsForUser = com.android.server.biometrics.sensors.face.FaceUtils.getInstance(i).getBiometricsForUser(this.mContext, i2);
        int[] iArr = new int[biometricsForUser.size()];
        for (int i3 = 0; i3 < biometricsForUser.size(); i3++) {
            iArr[i3] = biometricsForUser.get(i3).getBiometricId();
        }
        scheduleRemoveSpecifiedIds(i, iBinder, iArr, i2, iFaceServiceReceiver, str);
    }

    private void scheduleRemoveSpecifiedIds(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, final int[] iArr, final int i2, @android.annotation.NonNull final android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleRemoveSpecifiedIds$13(i, i2, iBinder, iFaceServiceReceiver, iArr, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRemoveSpecifiedIds$13(int i, int i2, android.os.IBinder iBinder, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, int[] iArr, java.lang.String str) {
        this.mFaceSensors.get(i).scheduleFaceUpdateActiveUserClient(i2);
        scheduleForSensor(i, new com.android.server.biometrics.sensors.face.aidl.FaceRemovalClient(this.mContext, this.mFaceSensors.get(i).getLazySession(), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), iArr, i2, str, com.android.server.biometrics.sensors.face.FaceUtils.getInstance(i), i, createLogger(4, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mFaceSensors.get(i).getAuthenticatorIds()), this.mBiometricStateCallback);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleResetLockout(final int i, final int i2, @android.annotation.NonNull final byte[] bArr) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleResetLockout$14(i, i2, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleResetLockout$14(int i, int i2, byte[] bArr) {
        this.mFaceSensors.get(i).scheduleFaceUpdateActiveUserClient(i2);
        scheduleForSensor(i, new com.android.server.biometrics.sensors.face.aidl.FaceResetLockoutClient(this.mContext, this.mFaceSensors.get(i).getLazySession(), i2, this.mContext.getOpPackageName(), i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, bArr, this.mFaceSensors.get(i).getLockoutTracker(false), this.mLockoutResetDispatcher, com.android.server.biometrics.Utils.getCurrentStrength(i)));
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleSetFeature(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, final int i2, final int i3, final boolean z, @android.annotation.NonNull final byte[] bArr, @android.annotation.NonNull final android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleSetFeature$15(i, i2, iBinder, iFaceServiceReceiver, i3, z, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleSetFeature$15(int i, int i2, android.os.IBinder iBinder, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, int i3, boolean z, byte[] bArr) {
        this.mFaceSensors.get(i).scheduleFaceUpdateActiveUserClient(i2);
        if (com.android.server.biometrics.sensors.face.FaceUtils.getInstance(i).getBiometricsForUser(this.mContext, i2).isEmpty()) {
            android.util.Slog.w(getTag(), "Ignoring setFeature, no templates enrolled for user: " + i2);
            return;
        }
        scheduleForSensor(i, new com.android.server.biometrics.sensors.face.aidl.FaceSetFeatureClient(this.mContext, this.mFaceSensors.get(i).getLazySession(), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), i2, this.mContext.getOpPackageName(), i, com.android.server.biometrics.log.BiometricLogger.ofUnknown(this.mContext), this.mBiometricContext, i3, z, bArr));
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleGetFeature(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, final int i2, final int i3, @android.annotation.NonNull final com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleGetFeature$16(i, i2, iBinder, clientMonitorCallbackConverter, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleGetFeature$16(int i, int i2, android.os.IBinder iBinder, com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i3) {
        this.mFaceSensors.get(i).scheduleFaceUpdateActiveUserClient(i2);
        if (com.android.server.biometrics.sensors.face.FaceUtils.getInstance(i).getBiometricsForUser(this.mContext, i2).isEmpty()) {
            android.util.Slog.w(getTag(), "Ignoring getFeature, no templates enrolled for user: " + i2);
            return;
        }
        scheduleForSensor(i, new com.android.server.biometrics.sensors.face.aidl.FaceGetFeatureClient(this.mContext, this.mFaceSensors.get(i).getLazySession(), iBinder, clientMonitorCallbackConverter, i2, this.mContext.getOpPackageName(), i, com.android.server.biometrics.log.BiometricLogger.ofUnknown(this.mContext), this.mBiometricContext, i3));
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void startPreparedClient(final int i, final int i2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$startPreparedClient$17(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startPreparedClient$17(int i, int i2) {
        this.mFaceSensors.get(i).getScheduler().startPreparedClient(i2);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleInternalCleanup(int i, int i2, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        scheduleInternalCleanup(i, i2, clientMonitorCallback, false);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleInternalCleanup(final int i, final int i2, @android.annotation.Nullable final com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback, final boolean z) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$scheduleInternalCleanup$18(i, i2, z, clientMonitorCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleInternalCleanup$18(int i, int i2, boolean z, com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        this.mFaceSensors.get(i).scheduleFaceUpdateActiveUserClient(i2);
        com.android.server.biometrics.sensors.face.aidl.FaceInternalCleanupClient faceInternalCleanupClient = new com.android.server.biometrics.sensors.face.aidl.FaceInternalCleanupClient(this.mContext, this.mFaceSensors.get(i).getLazySession(), i2, this.mContext.getOpPackageName(), i, createLogger(3, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, com.android.server.biometrics.sensors.face.FaceUtils.getInstance(i), this.mFaceSensors.get(i).getAuthenticatorIds());
        if (z) {
            faceInternalCleanupClient.setFavorHalEnrollments();
        }
        scheduleForSensor(i, faceInternalCleanupClient, new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(clientMonitorCallback, this.mBiometricStateCallback));
    }

    private com.android.server.biometrics.log.BiometricLogger createLogger(int i, int i2, com.android.server.biometrics.AuthenticationStatsCollector authenticationStatsCollector) {
        return new com.android.server.biometrics.log.BiometricLogger(this.mContext, 4, i, i2, authenticationStatsCollector);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public void dumpProtoState(int i, @android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, boolean z) {
        if (this.mFaceSensors.contains(i)) {
            this.mFaceSensors.get(i).dumpProtoState(i, protoOutputStream, z);
        }
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public void dumpProtoMetrics(int i, @android.annotation.NonNull java.io.FileDescriptor fileDescriptor) {
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public void dumpInternal(int i, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        com.android.server.biometrics.sensors.PerformanceTracker instanceForSensorId = com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(i);
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        try {
            jSONObject.put(com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE, getTag());
            org.json.JSONArray jSONArray = new org.json.JSONArray();
            java.util.Iterator it = android.os.UserManager.get(this.mContext).getUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
                int size = com.android.server.biometrics.sensors.face.FaceUtils.getInstance(i).getBiometricsForUser(this.mContext, identifier).size();
                org.json.JSONObject jSONObject2 = new org.json.JSONObject();
                jSONObject2.put("id", identifier);
                jSONObject2.put(com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT, size);
                jSONObject2.put("accept", instanceForSensorId.getAcceptForUser(identifier));
                jSONObject2.put("reject", instanceForSensorId.getRejectForUser(identifier));
                jSONObject2.put("acquire", instanceForSensorId.getAcquireForUser(identifier));
                jSONObject2.put("lockout", instanceForSensorId.getTimedLockoutForUser(identifier));
                jSONObject2.put("permanentLockout", instanceForSensorId.getPermanentLockoutForUser(identifier));
                jSONObject2.put("acceptCrypto", instanceForSensorId.getAcceptCryptoForUser(identifier));
                jSONObject2.put("rejectCrypto", instanceForSensorId.getRejectCryptoForUser(identifier));
                jSONObject2.put("acquireCrypto", instanceForSensorId.getAcquireCryptoForUser(identifier));
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("prints", jSONArray);
        } catch (org.json.JSONException e) {
            android.util.Slog.e(getTag(), "dump formatting failure", e);
        }
        printWriter.println(jSONObject);
        printWriter.println("HAL deaths since last reboot: " + instanceForSensorId.getHALDeathCount());
        printWriter.println("---AuthSessionCoordinator logs begin---");
        printWriter.println(this.mBiometricContext.getAuthSessionCoordinator());
        printWriter.println("---AuthSessionCoordinator logs end  ---");
        this.mFaceSensors.get(i).getScheduler().dump(printWriter);
        this.mUsageStats.print(printWriter);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    @android.annotation.NonNull
    public android.hardware.biometrics.ITestSession createTestSession(int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str) {
        return this.mFaceSensors.get(i).createTestSession(iTestSessionCallback);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void dumpHal(int i, @android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.lang.String[] strArr) {
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.util.Slog.e(getTag(), "HAL died");
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceProvider$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.lambda$binderDied$19();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$binderDied$19() {
        this.mDaemon = null;
        for (int i = 0; i < this.mFaceSensors.size(); i++) {
            com.android.server.biometrics.sensors.face.aidl.Sensor valueAt = this.mFaceSensors.valueAt(i);
            com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(this.mFaceSensors.keyAt(i)).incrementHALDeathCount();
            valueAt.onBinderDied();
        }
    }

    void setTestHalEnabled(boolean z) {
        this.mTestHalEnabled = z;
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleWatchdog(int i) {
        android.util.Slog.d(getTag(), "Starting watchdog for face");
        com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.face.IFace, android.hardware.biometrics.face.ISession> scheduler = this.mFaceSensors.get(i).getScheduler();
        if (scheduler == null) {
            return;
        }
        scheduler.startWatchdog();
    }

    public boolean getTestHalEnabled() {
        return this.mTestHalEnabled;
    }

    public void sendFaceReEnrollNotification() {
        this.mAuthenticationStatsCollector.sendFaceReEnrollNotification();
    }
}
