package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class FingerprintProvider implements android.os.IBinder.DeathRecipient, com.android.server.biometrics.sensors.fingerprint.ServiceProvider {
    private static final java.lang.String TAG = "FingerprintProvider";

    @android.annotation.NonNull
    private final android.app.ActivityTaskManager mActivityTaskManager;
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
    private android.hardware.biometrics.fingerprint.IFingerprint mDaemon;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.biometrics.sensors.SensorList<com.android.server.biometrics.sensors.fingerprint.aidl.Sensor> mFingerprintSensors;

    @android.annotation.NonNull
    private final java.lang.String mHalInstanceName;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;

    @android.annotation.NonNull
    private final java.util.concurrent.atomic.AtomicLong mRequestCounter;

    @android.annotation.Nullable
    private android.hardware.fingerprint.ISidefpsController mSidefpsController;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.BiometricTaskStackListener mTaskStackListener;
    private boolean mTestHalEnabled;

    @android.annotation.Nullable
    private android.hardware.fingerprint.IUdfpsOverlayController mUdfpsOverlayController;

    /* JADX INFO: Access modifiers changed from: private */
    final class BiometricTaskStackListener extends android.app.TaskStackListener {
        private BiometricTaskStackListener() {
        }

        public void onTaskStackChanged() {
            com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$BiometricTaskStackListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.BiometricTaskStackListener.this.lambda$onTaskStackChanged$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskStackChanged$0() {
            for (int i = 0; i < com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mFingerprintSensors.size(); i++) {
                com.android.server.biometrics.sensors.BaseClientMonitor currentClient = com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mFingerprintSensors.valueAt(i).getScheduler().getCurrentClient();
                if (!(currentClient instanceof com.android.server.biometrics.sensors.AuthenticationClient)) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.getTag(), "Task stack changed for client: " + currentClient);
                } else if (!com.android.server.biometrics.Utils.isKeyguard(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mContext, currentClient.getOwnerString()) && !com.android.server.biometrics.Utils.isSystem(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mContext, currentClient.getOwnerString()) && com.android.server.biometrics.Utils.isBackground(currentClient.getOwnerString()) && !currentClient.isAlreadyDone()) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.getTag(), "Stopping background authentication, currentClient: " + currentClient);
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mFingerprintSensors.valueAt(i).getScheduler().cancelAuthenticationOrDetection(currentClient.getToken(), currentClient.getRequestId());
                }
            }
        }
    }

    public FingerprintProvider(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, @android.annotation.NonNull android.hardware.biometrics.fingerprint.SensorProps[] sensorPropsArr, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z) {
        this(context, biometricStateCallback, authenticationStateListeners, sensorPropsArr, str, lockoutResetDispatcher, gestureAvailabilityDispatcher, biometricContext, null, com.android.server.biometrics.BiometricHandlerProvider.getInstance(), z, false);
    }

    @com.android.internal.annotations.VisibleForTesting
    FingerprintProvider(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, @android.annotation.NonNull android.hardware.biometrics.fingerprint.SensorProps[] sensorPropsArr, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.Nullable android.hardware.biometrics.fingerprint.IFingerprint iFingerprint, @android.annotation.NonNull com.android.server.biometrics.BiometricHandlerProvider biometricHandlerProvider, boolean z, boolean z2) {
        this.mRequestCounter = new java.util.concurrent.atomic.AtomicLong(0L);
        this.mContext = context;
        this.mBiometricStateCallback = biometricStateCallback;
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mHalInstanceName = str;
        this.mFingerprintSensors = new com.android.server.biometrics.sensors.SensorList<>(android.app.ActivityManager.getService());
        com.android.server.biometrics.Flags.deHidl();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mActivityTaskManager = android.app.ActivityTaskManager.getInstance();
        this.mTaskStackListener = new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.BiometricTaskStackListener();
        this.mBiometricContext = biometricContext;
        this.mAuthSessionCoordinator = this.mBiometricContext.getAuthSessionCoordinator();
        this.mDaemon = iFingerprint;
        this.mTestHalEnabled = z2;
        this.mBiometricHandlerProvider = biometricHandlerProvider;
        initAuthenticationBroadcastReceiver();
        initSensors(z, sensorPropsArr, gestureAvailabilityDispatcher);
    }

    private void initAuthenticationBroadcastReceiver() {
        new com.android.server.biometrics.AuthenticationStatsBroadcastReceiver(this.mContext, 1, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$initAuthenticationBroadcastReceiver$0((com.android.server.biometrics.AuthenticationStatsCollector) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAuthenticationBroadcastReceiver$0(com.android.server.biometrics.AuthenticationStatsCollector authenticationStatsCollector) {
        android.util.Slog.d(getTag(), "Initializing AuthenticationStatsCollector");
        this.mAuthenticationStatsCollector = authenticationStatsCollector;
    }

    private void initSensors(boolean z, android.hardware.biometrics.fingerprint.SensorProps[] sensorPropsArr, com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher) {
        com.android.server.biometrics.Flags.deHidl();
        java.util.List<android.hardware.biometrics.SensorLocationInternal> workaroundSensorProps = getWorkaroundSensorProps(this.mContext);
        for (android.hardware.biometrics.fingerprint.SensorProps sensorProps : sensorPropsArr) {
            final int i = sensorProps.commonProps.sensorId;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (sensorProps.commonProps.componentInfo != null) {
                android.hardware.biometrics.common.ComponentInfo[] componentInfoArr = sensorProps.commonProps.componentInfo;
                int i2 = 0;
                for (int length = componentInfoArr.length; i2 < length; length = length) {
                    android.hardware.biometrics.common.ComponentInfo componentInfo = componentInfoArr[i2];
                    arrayList.add(new android.hardware.biometrics.ComponentInfoInternal(componentInfo.componentId, componentInfo.hardwareVersion, componentInfo.firmwareVersion, componentInfo.serialNumber, componentInfo.softwareVersion));
                    i2++;
                    componentInfoArr = componentInfoArr;
                }
            }
            com.android.server.biometrics.sensors.fingerprint.aidl.Sensor sensor = new com.android.server.biometrics.sensors.fingerprint.aidl.Sensor(this, this.mContext, this.mHandler, new android.hardware.fingerprint.FingerprintSensorPropertiesInternal(sensorProps.commonProps.sensorId, sensorProps.commonProps.sensorStrength, sensorProps.commonProps.maxEnrollmentsPerUser, arrayList, sensorProps.sensorType, sensorProps.halControlsIllumination, true, !workaroundSensorProps.isEmpty() ? workaroundSensorProps : (java.util.List) java.util.Arrays.stream(sensorProps.sensorLocations).map(new java.util.function.Function() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.hardware.biometrics.SensorLocationInternal lambda$initSensors$1;
                    lambda$initSensors$1 = com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.lambda$initSensors$1((android.hardware.biometrics.fingerprint.SensorLocation) obj);
                    return lambda$initSensors$1;
                }
            }).collect(java.util.stream.Collectors.toList())), this.mBiometricContext);
            sensor.init(gestureAvailabilityDispatcher, this.mLockoutResetDispatcher);
            this.mFingerprintSensors.addSensor(i, sensor, sensor.getLazySession().get() == null ? com.android.server.am.ProcessList.INVALID_ADJ : sensor.getLazySession().get().getUserId(), new android.app.SynchronousUserSwitchObserver() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.1
                public void onUserSwitching(int i3) {
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.scheduleInternalCleanup(i, i3, null);
                }
            });
            android.util.Slog.d(getTag(), "Added: " + this.mFingerprintSensors.get(i).toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.biometrics.SensorLocationInternal lambda$initSensors$1(android.hardware.biometrics.fingerprint.SensorLocation sensorLocation) {
        return new android.hardware.biometrics.SensorLocationInternal(sensorLocation.display, sensorLocation.sensorLocationX, sensorLocation.sensorLocationY, sensorLocation.sensorRadius);
    }

    private void addHidlSensors(@android.annotation.NonNull android.hardware.biometrics.fingerprint.SensorProps sensorProps, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, boolean z) {
        final int i = sensorProps.commonProps.sensorId;
        com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter hidlToAidlSensorAdapter = new com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter(this, this.mContext, this.mHandler, sensorProps, this.mLockoutResetDispatcher, this.mBiometricContext, z, new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$addHidlSensors$2(i);
            }
        });
        hidlToAidlSensorAdapter.init(gestureAvailabilityDispatcher, this.mLockoutResetDispatcher);
        this.mFingerprintSensors.addSensor(i, hidlToAidlSensorAdapter, hidlToAidlSensorAdapter.getLazySession().get() == null ? com.android.server.am.ProcessList.INVALID_ADJ : hidlToAidlSensorAdapter.getLazySession().get().getUserId(), new android.app.SynchronousUserSwitchObserver() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.2
            public void onUserSwitching(int i2) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.scheduleInternalCleanup(i, i2, null);
            }
        });
        android.util.Slog.d(getTag(), "Added: " + this.mFingerprintSensors.get(i).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHidlSensors$2(int i) {
        scheduleInternalCleanup(i, android.app.ActivityManager.getCurrentUser(), null);
    }

    private void addAidlSensors(@android.annotation.NonNull android.hardware.biometrics.fingerprint.SensorProps sensorProps, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull java.util.List<android.hardware.biometrics.SensorLocationInternal> list, boolean z) {
        final int i = sensorProps.commonProps.sensorId;
        com.android.server.biometrics.sensors.fingerprint.aidl.Sensor sensor = new com.android.server.biometrics.sensors.fingerprint.aidl.Sensor(this, this.mContext, this.mHandler, sensorProps, this.mBiometricContext, list, z);
        sensor.init(gestureAvailabilityDispatcher, this.mLockoutResetDispatcher);
        this.mFingerprintSensors.addSensor(i, sensor, sensor.getLazySession().get() == null ? com.android.server.am.ProcessList.INVALID_ADJ : sensor.getLazySession().get().getUserId(), new android.app.SynchronousUserSwitchObserver() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.3
            public void onUserSwitching(int i2) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.scheduleInternalCleanup(i, i2, null);
            }
        });
        android.util.Slog.d(getTag(), "Added: " + this.mFingerprintSensors.get(i).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getTag() {
        return "FingerprintProvider/" + this.mHalInstanceName;
    }

    boolean hasHalInstance() {
        if (this.mTestHalEnabled) {
            return true;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.hardware.biometrics.fingerprint.IFingerprint.DESCRIPTOR);
        sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
        sb.append(this.mHalInstanceName);
        return android.os.ServiceManager.checkService(sb.toString()) != null;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    synchronized android.hardware.biometrics.fingerprint.IFingerprint getHalInstance() {
        if (this.mTestHalEnabled) {
            return new com.android.server.biometrics.sensors.fingerprint.aidl.TestHal();
        }
        if (this.mDaemon != null) {
            return this.mDaemon;
        }
        android.util.Slog.d(getTag(), "Daemon was null, reconnecting");
        this.mDaemon = android.hardware.biometrics.fingerprint.IFingerprint.Stub.asInterface(android.os.Binder.allowBlocking(android.os.ServiceManager.waitForDeclaredService(android.hardware.biometrics.fingerprint.IFingerprint.DESCRIPTOR + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mHalInstanceName)));
        if (this.mDaemon == null) {
            android.util.Slog.e(getTag(), "Unable to get daemon");
            return null;
        }
        try {
            this.mDaemon.asBinder().linkToDeath(this, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(getTag(), "Unable to linkToDeath", e);
        }
        for (int i = 0; i < this.mFingerprintSensors.size(); i++) {
            int keyAt = this.mFingerprintSensors.keyAt(i);
            scheduleLoadAuthenticatorIds(keyAt);
            scheduleInternalCleanup(keyAt, android.app.ActivityManager.getCurrentUser(), null);
        }
        if (android.os.Build.isDebuggable()) {
            com.android.server.biometrics.sensors.fingerprint.FingerprintUtils fingerprintUtils = com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(this.mFingerprintSensors.keyAt(0));
            for (android.content.pm.UserInfo userInfo : android.os.UserManager.get(this.mContext).getAliveUsers()) {
                java.util.List<android.hardware.fingerprint.Fingerprint> biometricsForUser = fingerprintUtils.getBiometricsForUser(this.mContext, userInfo.id);
                android.util.Slog.d(getTag(), "Expecting enrollments for user " + userInfo.id + ": " + biometricsForUser.stream().map(new java.util.function.Function() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda15
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return java.lang.Integer.valueOf(((android.hardware.fingerprint.Fingerprint) obj).getBiometricId());
                    }
                }).toList());
            }
        }
        return this.mDaemon;
    }

    private void scheduleForSensor(int i, @android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        if (!this.mFingerprintSensors.contains(i)) {
            throw new java.lang.IllegalStateException("Unable to schedule client: " + baseClientMonitor + " for sensor: " + i);
        }
        this.mFingerprintSensors.get(i).getScheduler().scheduleClientMonitor(baseClientMonitor);
    }

    private void scheduleForSensor(int i, @android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        if (!this.mFingerprintSensors.contains(i)) {
            throw new java.lang.IllegalStateException("Unable to schedule client: " + baseClientMonitor + " for sensor: " + i);
        }
        this.mFingerprintSensors.get(i).getScheduler().scheduleClientMonitor(baseClientMonitor, clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean containsSensor(int i) {
        return this.mFingerprintSensors.contains(i);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    @android.annotation.NonNull
    public java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> getSensorProperties() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mFingerprintSensors.size(); i++) {
            arrayList.add(this.mFingerprintSensors.valueAt(i).getSensorProperties());
        }
        return arrayList;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    @android.annotation.Nullable
    public android.hardware.fingerprint.FingerprintSensorPropertiesInternal getSensorProperties(int i) {
        if (this.mFingerprintSensors.size() == 0) {
            return null;
        }
        if (i == -1) {
            return this.mFingerprintSensors.valueAt(0).getSensorProperties();
        }
        com.android.server.biometrics.sensors.fingerprint.aidl.Sensor sensor = this.mFingerprintSensors.get(i);
        if (sensor != null) {
            return sensor.getSensorProperties();
        }
        return null;
    }

    private void scheduleLoadAuthenticatorIds(int i) {
        java.util.Iterator it = android.os.UserManager.get(this.mContext).getAliveUsers().iterator();
        while (it.hasNext()) {
            scheduleLoadAuthenticatorIdsForUser(i, ((android.content.pm.UserInfo) it.next()).id);
        }
    }

    protected void scheduleLoadAuthenticatorIdsForUser(final int i, final int i2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$scheduleLoadAuthenticatorIdsForUser$3(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleLoadAuthenticatorIdsForUser$3(int i, int i2) {
        scheduleForSensor(i, new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintGetAuthenticatorIdClient(this.mContext, this.mFingerprintSensors.get(i).getLazySession(), i2, this.mContext.getOpPackageName(), i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mFingerprintSensors.get(i).getAuthenticatorIds()));
    }

    void scheduleInvalidationRequest(final int i, final int i2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$scheduleInvalidationRequest$4(i2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleInvalidationRequest$4(int i, int i2) {
        scheduleForSensor(i2, new com.android.server.biometrics.sensors.InvalidationRequesterClient(this.mContext, i, i2, com.android.server.biometrics.log.BiometricLogger.ofUnknown(this.mContext), this.mBiometricContext, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(i2)));
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleResetLockout(final int i, final int i2, @android.annotation.Nullable final byte[] bArr) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$scheduleResetLockout$5(i, i2, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleResetLockout$5(int i, int i2, byte[] bArr) {
        scheduleForSensor(i, new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintResetLockoutClient(this.mContext, this.mFingerprintSensors.get(i).getLazySession(), i2, this.mContext.getOpPackageName(), i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, bArr, this.mFingerprintSensors.get(i).getLockoutTracker(false), this.mLockoutResetDispatcher, com.android.server.biometrics.Utils.getCurrentStrength(i)));
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleGenerateChallenge(final int i, final int i2, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$scheduleGenerateChallenge$6(i, iBinder, iFingerprintServiceReceiver, i2, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleGenerateChallenge$6(int i, android.os.IBinder iBinder, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, int i2, java.lang.String str) {
        scheduleForSensor(i, new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintGenerateChallengeClient(this.mContext, this.mFingerprintSensors.get(i).getLazySession(), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFingerprintServiceReceiver), i2, str, i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext));
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleRevokeChallenge(final int i, final int i2, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final java.lang.String str, final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$scheduleRevokeChallenge$7(i, iBinder, i2, str, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRevokeChallenge$7(int i, android.os.IBinder iBinder, int i2, java.lang.String str, long j) {
        scheduleForSensor(i, new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintRevokeChallengeClient(this.mContext, this.mFingerprintSensors.get(i).getLazySession(), iBinder, i2, str, i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, j));
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public long scheduleEnroll(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final byte[] bArr, final int i2, @android.annotation.NonNull final android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, @android.annotation.NonNull final java.lang.String str, final int i3, @android.annotation.NonNull final android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) {
        final long incrementAndGet = this.mRequestCounter.incrementAndGet();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$scheduleEnroll$8(i, iBinder, incrementAndGet, iFingerprintServiceReceiver, i2, bArr, str, i3, fingerprintEnrollOptions);
            }
        });
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleEnroll$8(final int i, android.os.IBinder iBinder, long j, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, final int i2, byte[] bArr, java.lang.String str, int i3, android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) {
        com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient fingerprintEnrollClient = new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient(this.mContext, this.mFingerprintSensors.get(i).getLazySession(), iBinder, j, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFingerprintServiceReceiver), i2, bArr, str, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(i), i, createLogger(1, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mFingerprintSensors.get(i).getSensorProperties(), this.mUdfpsOverlayController, this.mSidefpsController, this.mAuthenticationStateListeners, this.mFingerprintSensors.get(i).getSensorProperties().maxEnrollmentsPerUser, i3, fingerprintEnrollOptions);
        com.android.server.biometrics.Flags.deHidl();
        scheduleForSensor(i, fingerprintEnrollClient, new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(this.mBiometricStateCallback, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.4
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                super.onClientFinished(baseClientMonitor, z);
                if (z) {
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.scheduleLoadAuthenticatorIdsForUser(i, i2);
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.scheduleInvalidationRequest(i, i2);
                }
            }
        }));
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void cancelEnrollment(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$cancelEnrollment$9(i, iBinder, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelEnrollment$9(int i, android.os.IBinder iBinder, long j) {
        this.mFingerprintSensors.get(i).getScheduler().cancelEnrollment(iBinder, j);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public long scheduleFingerDetect(@android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull final android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, final int i) {
        final long incrementAndGet = this.mRequestCounter.incrementAndGet();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$scheduleFingerDetect$10(fingerprintAuthenticateOptions, iBinder, incrementAndGet, clientMonitorCallbackConverter, i);
            }
        });
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleFingerDetect$10(android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, android.os.IBinder iBinder, long j, com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i) {
        int sensorId = fingerprintAuthenticateOptions.getSensorId();
        scheduleForSensor(sensorId, new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient(this.mContext, this.mFingerprintSensors.get(sensorId).getLazySession(), iBinder, j, clientMonitorCallbackConverter, fingerprintAuthenticateOptions, createLogger(2, i, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mUdfpsOverlayController, com.android.server.biometrics.Utils.isStrongBiometric(sensorId)), this.mBiometricStateCallback);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleAuthenticate(@android.annotation.NonNull final android.os.IBinder iBinder, final long j, final int i, @android.annotation.NonNull final com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull final android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, final long j2, final boolean z, final int i2, final boolean z2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$scheduleAuthenticate$11(fingerprintAuthenticateOptions, iBinder, j2, clientMonitorCallbackConverter, j, z, i, i2, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleAuthenticate$11(android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, android.os.IBinder iBinder, final long j, com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, int i, int i2, boolean z2) {
        final int userId = fingerprintAuthenticateOptions.getUserId();
        final int sensorId = fingerprintAuthenticateOptions.getSensorId();
        boolean isStrongBiometric = com.android.server.biometrics.Utils.isStrongBiometric(sensorId);
        com.android.server.biometrics.Flags.deHidl();
        scheduleForSensor(sensorId, new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient(this.mContext, this.mFingerprintSensors.get(sensorId).getLazySession(), iBinder, j, clientMonitorCallbackConverter, j2, z, fingerprintAuthenticateOptions, i, false, createLogger(2, i2, this.mAuthenticationStatsCollector), this.mBiometricContext, isStrongBiometric, this.mTaskStackListener, this.mUdfpsOverlayController, this.mSidefpsController, this.mAuthenticationStateListeners, z2, this.mFingerprintSensors.get(sensorId).getSensorProperties(), this.mHandler, com.android.server.biometrics.Utils.getCurrentStrength(sensorId), android.os.SystemClock.elapsedRealtimeClock(), null), new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.5
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mBiometricStateCallback.onClientStarted(baseClientMonitor);
                com.android.server.biometrics.Flags.deHidl();
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mAuthSessionCoordinator.authStartedFor(userId, sensorId, j);
            }

            private /* synthetic */ void lambda$onClientStarted$0(int i3, int i4, long j3) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mAuthSessionCoordinator.authStartedFor(i3, i4, j3);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onBiometricAction(int i3) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mBiometricStateCallback.onBiometricAction(i3);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z3) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mBiometricStateCallback.onClientFinished(baseClientMonitor, z3);
                com.android.server.biometrics.Flags.deHidl();
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mAuthSessionCoordinator.authEndedFor(userId, com.android.server.biometrics.Utils.getCurrentStrength(sensorId), sensorId, j, z3);
            }

            private /* synthetic */ void lambda$onClientFinished$1(int i3, int i4, long j3, boolean z3) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.mAuthSessionCoordinator.authEndedFor(i3, com.android.server.biometrics.Utils.getCurrentStrength(i4), i4, j3, z3);
            }
        });
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public long scheduleAuthenticate(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, boolean z, int i2, boolean z2) {
        long incrementAndGet = this.mRequestCounter.incrementAndGet();
        scheduleAuthenticate(iBinder, j, i, clientMonitorCallbackConverter, fingerprintAuthenticateOptions, incrementAndGet, z, i2, z2);
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startPreparedClient$12(int i, int i2) {
        this.mFingerprintSensors.get(i).getScheduler().startPreparedClient(i2);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void startPreparedClient(final int i, final int i2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$startPreparedClient$12(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelAuthentication$13(int i, android.os.IBinder iBinder, long j) {
        this.mFingerprintSensors.get(i).getScheduler().cancelAuthenticationOrDetection(iBinder, j);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void cancelAuthentication(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$cancelAuthentication$13(i, iBinder, j);
            }
        });
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleRemove(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, int i2, int i3, @android.annotation.NonNull java.lang.String str) {
        scheduleRemoveSpecifiedIds(i, iBinder, new int[]{i2}, i3, iFingerprintServiceReceiver, str);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleRemoveAll(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, int i2, @android.annotation.NonNull java.lang.String str) {
        java.util.List<android.hardware.fingerprint.Fingerprint> biometricsForUser = com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(i).getBiometricsForUser(this.mContext, i2);
        int[] iArr = new int[biometricsForUser.size()];
        for (int i3 = 0; i3 < biometricsForUser.size(); i3++) {
            iArr[i3] = biometricsForUser.get(i3).getBiometricId();
        }
        scheduleRemoveSpecifiedIds(i, iBinder, iArr, i2, iFingerprintServiceReceiver, str);
    }

    private void scheduleRemoveSpecifiedIds(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, final int[] iArr, final int i2, @android.annotation.NonNull final android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, @android.annotation.NonNull final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$scheduleRemoveSpecifiedIds$14(i, iBinder, iFingerprintServiceReceiver, iArr, i2, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRemoveSpecifiedIds$14(int i, android.os.IBinder iBinder, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, int[] iArr, int i2, java.lang.String str) {
        scheduleForSensor(i, new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintRemovalClient(this.mContext, this.mFingerprintSensors.get(i).getLazySession(), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFingerprintServiceReceiver), iArr, i2, str, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(i), i, createLogger(4, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mFingerprintSensors.get(i).getAuthenticatorIds()), this.mBiometricStateCallback);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleInternalCleanup(int i, int i2, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        scheduleInternalCleanup(i, i2, clientMonitorCallback, false);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleInternalCleanup(final int i, final int i2, @android.annotation.Nullable final com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback, final boolean z) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$scheduleInternalCleanup$15(i, i2, z, clientMonitorCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleInternalCleanup$15(int i, int i2, boolean z, com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintInternalCleanupClient fingerprintInternalCleanupClient = new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintInternalCleanupClient(this.mContext, this.mFingerprintSensors.get(i).getLazySession(), i2, this.mContext.getOpPackageName(), i, createLogger(3, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(i), this.mFingerprintSensors.get(i).getAuthenticatorIds());
        if (z) {
            fingerprintInternalCleanupClient.setFavorHalEnrollments();
        }
        scheduleForSensor(i, fingerprintInternalCleanupClient, new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(clientMonitorCallback, this.mBiometricStateCallback));
    }

    private com.android.server.biometrics.log.BiometricLogger createLogger(int i, int i2, com.android.server.biometrics.AuthenticationStatsCollector authenticationStatsCollector) {
        return new com.android.server.biometrics.log.BiometricLogger(this.mContext, 1, i, i2, authenticationStatsCollector);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean isHardwareDetected(int i) {
        com.android.server.biometrics.Flags.deHidl();
        return hasHalInstance();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void rename(int i, int i2, int i3, @android.annotation.NonNull java.lang.String str) {
        com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(i).renameBiometricForUser(this.mContext, i3, i2, str);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    @android.annotation.NonNull
    public java.util.List<android.hardware.fingerprint.Fingerprint> getEnrolledFingerprints(int i, int i2) {
        return com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(i).getBiometricsForUser(this.mContext, i2);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean hasEnrollments(int i, int i2) {
        return !getEnrolledFingerprints(i, i2).isEmpty();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleInvalidateAuthenticatorId(final int i, final int i2, @android.annotation.NonNull final android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$scheduleInvalidateAuthenticatorId$16(i, i2, iInvalidationCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleInvalidateAuthenticatorId$16(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
        scheduleForSensor(i, new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintInvalidationClient(this.mContext, this.mFingerprintSensors.get(i).getLazySession(), i2, i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mFingerprintSensors.get(i).getAuthenticatorIds(), iInvalidationCallback));
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public int getLockoutModeForUser(int i, int i2) {
        com.android.server.biometrics.Flags.deHidl();
        return this.mBiometricContext.getAuthSessionCoordinator().getLockoutStateFor(i2, com.android.server.biometrics.Utils.getCurrentStrength(i));
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public long getAuthenticatorId(int i, int i2) {
        return this.mFingerprintSensors.get(i).getAuthenticatorIds().getOrDefault(java.lang.Integer.valueOf(i2), 0L).longValue();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void onPointerDown(long j, int i, final android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        this.mFingerprintSensors.get(i).getScheduler().getCurrentClientIfMatches(j, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$onPointerDown$17(pointerContext, (com.android.server.biometrics.sensors.BaseClientMonitor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onPointerDown$17(android.hardware.biometrics.fingerprint.PointerContext pointerContext, com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        if (!(baseClientMonitor instanceof com.android.server.biometrics.sensors.fingerprint.Udfps)) {
            android.util.Slog.e(getTag(), "onPointerDown received during client: " + baseClientMonitor);
            return;
        }
        ((com.android.server.biometrics.sensors.fingerprint.Udfps) baseClientMonitor).onPointerDown(pointerContext);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void onPointerUp(long j, int i, final android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        this.mFingerprintSensors.get(i).getScheduler().getCurrentClientIfMatches(j, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$onPointerUp$18(pointerContext, (com.android.server.biometrics.sensors.BaseClientMonitor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onPointerUp$18(android.hardware.biometrics.fingerprint.PointerContext pointerContext, com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        if (!(baseClientMonitor instanceof com.android.server.biometrics.sensors.fingerprint.Udfps)) {
            android.util.Slog.e(getTag(), "onPointerUp received during client: " + baseClientMonitor);
            return;
        }
        ((com.android.server.biometrics.sensors.fingerprint.Udfps) baseClientMonitor).onPointerUp(pointerContext);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void onUdfpsUiEvent(final int i, long j, int i2) {
        this.mFingerprintSensors.get(i2).getScheduler().getCurrentClientIfMatches(j, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$onUdfpsUiEvent$19(i, (com.android.server.biometrics.sensors.BaseClientMonitor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onUdfpsUiEvent$19(int i, com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        if (!(baseClientMonitor instanceof com.android.server.biometrics.sensors.fingerprint.Udfps)) {
            android.util.Slog.e(getTag(), "onUdfpsUiEvent received during client: " + baseClientMonitor);
            return;
        }
        ((com.android.server.biometrics.sensors.fingerprint.Udfps) baseClientMonitor).onUdfpsUiEvent(i);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void setUdfpsOverlayController(@android.annotation.NonNull android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) {
        this.mUdfpsOverlayController = iUdfpsOverlayController;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void onPowerPressed() {
        android.os.IBinder.DeathRecipient currentClient;
        for (int i = 0; i < this.mFingerprintSensors.size() && (currentClient = this.mFingerprintSensors.valueAt(i).getScheduler().getCurrentClient()) != null; i++) {
            if (currentClient instanceof com.android.server.biometrics.sensors.fingerprint.PowerPressHandler) {
                ((com.android.server.biometrics.sensors.fingerprint.PowerPressHandler) currentClient).onPowerPressed();
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void setSidefpsController(@android.annotation.NonNull android.hardware.fingerprint.ISidefpsController iSidefpsController) {
        this.mSidefpsController = iSidefpsController;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public void dumpProtoState(int i, @android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, boolean z) {
        if (this.mFingerprintSensors.contains(i)) {
            this.mFingerprintSensors.get(i).dumpProtoState(i, protoOutputStream, z);
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
                int size = com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(i).getBiometricsForUser(this.mContext, identifier).size();
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
        this.mFingerprintSensors.get(i).getScheduler().dump(printWriter);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    @android.annotation.NonNull
    public android.hardware.biometrics.ITestSession createTestSession(int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str) {
        return this.mFingerprintSensors.get(i).createTestSession(iTestSessionCallback, this.mBiometricStateCallback);
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.util.Slog.e(getTag(), "HAL died");
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.lambda$binderDied$20();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$binderDied$20() {
        this.mDaemon = null;
        for (int i = 0; i < this.mFingerprintSensors.size(); i++) {
            com.android.server.biometrics.sensors.fingerprint.aidl.Sensor valueAt = this.mFingerprintSensors.valueAt(i);
            com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(this.mFingerprintSensors.keyAt(i)).incrementHALDeathCount();
            valueAt.onBinderDied();
        }
    }

    void setTestHalEnabled(boolean z) {
        this.mTestHalEnabled = z;
    }

    public boolean getTestHalEnabled() {
        return this.mTestHalEnabled;
    }

    @android.annotation.NonNull
    public static java.util.List<android.hardware.biometrics.SensorLocationInternal> getWorkaroundSensorProps(@android.annotation.NonNull android.content.Context context) {
        android.hardware.biometrics.SensorLocationInternal parseSensorLocation;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.res.TypedArray obtainTypedArray = context.getResources().obtainTypedArray(android.R.array.config_serialPorts);
        for (int i = 0; i < obtainTypedArray.length(); i++) {
            int resourceId = obtainTypedArray.getResourceId(i, -1);
            if (resourceId > 0 && (parseSensorLocation = parseSensorLocation(context.getResources().obtainTypedArray(resourceId))) != null) {
                arrayList.add(parseSensorLocation);
            }
        }
        obtainTypedArray.recycle();
        return arrayList;
    }

    @android.annotation.Nullable
    private static android.hardware.biometrics.SensorLocationInternal parseSensorLocation(@android.annotation.Nullable android.content.res.TypedArray typedArray) {
        if (typedArray == null) {
            return null;
        }
        try {
            return new android.hardware.biometrics.SensorLocationInternal(typedArray.getString(0), typedArray.getInt(1, 0), typedArray.getInt(2, 0), typedArray.getInt(3, 0));
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "malformed sensor location", e);
            return null;
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleWatchdog(int i) {
        android.util.Slog.d(getTag(), "Starting watchdog for fingerprint");
        com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.fingerprint.IFingerprint, android.hardware.biometrics.fingerprint.ISession> scheduler = this.mFingerprintSensors.get(i).getScheduler();
        if (scheduler == null) {
            return;
        }
        scheduler.startWatchdog();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void simulateVhalFingerDown(int i, int i2) {
        android.util.Slog.d(getTag(), "Simulate virtual HAL finger down event");
        com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession sessionForUser = this.mFingerprintSensors.get(i2).getSessionForUser(i);
        if (sessionForUser == null) {
            android.util.Slog.e(getTag(), "no existing hal session found - aborting");
            return;
        }
        android.hardware.biometrics.fingerprint.PointerContext pointerContext = new android.hardware.biometrics.fingerprint.PointerContext();
        try {
            sessionForUser.getSession().onPointerDownWithContext(pointerContext);
            sessionForUser.getSession().onUiReady();
            sessionForUser.getSession().onPointerUpWithContext(pointerContext);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(getTag(), "failed hal operation ", e);
        }
    }

    public void sendFingerprintReEnrollNotification() {
        this.mAuthenticationStatsCollector.sendFingerprintReEnrollNotification();
    }
}
