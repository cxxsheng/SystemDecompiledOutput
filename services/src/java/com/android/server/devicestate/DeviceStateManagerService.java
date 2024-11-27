package com.android.server.devicestate;

/* loaded from: classes.dex */
public final class DeviceStateManagerService extends com.android.server.SystemService {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "DeviceStateManagerService";

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Optional<com.android.server.devicestate.OverrideRequest> mActiveBaseStateOverride;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Optional<com.android.server.devicestate.OverrideRequest> mActiveOverride;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Optional<android.hardware.devicestate.DeviceState> mBaseState;

    @android.annotation.NonNull
    private final com.android.server.devicestate.DeviceStateManagerService.BinderService mBinderService;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Optional<android.hardware.devicestate.DeviceState> mCommittedState;

    @android.annotation.NonNull
    private final com.android.server.devicestate.DeviceStateNotificationController mDeviceStateNotificationController;

    @android.annotation.NonNull
    private final com.android.server.devicestate.DeviceStatePolicy mDeviceStatePolicy;

    @android.annotation.NonNull
    private final com.android.server.devicestate.DeviceStateManagerService.DeviceStateProviderListener mDeviceStateProviderListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<android.hardware.devicestate.DeviceState> mDeviceStates;
    private java.util.Set<java.lang.Integer> mDeviceStatesAvailableForAppRequests;
    private java.util.Set<java.lang.Integer> mFoldedDeviceStates;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsPolicyWaitingForState;
    private final java.lang.Object mLock;

    @android.annotation.NonNull
    private final com.android.server.devicestate.OverrideRequestController mOverrideRequestController;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver mOverrideRequestScreenObserver;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Optional<android.hardware.devicestate.DeviceState> mPendingState;

    @com.android.internal.annotations.VisibleForTesting
    final android.app.IProcessObserver mProcessObserver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.devicestate.DeviceStateManagerService.ProcessRecord> mProcessRecords;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.devicestate.OverrideRequest mRearDisplayPendingOverrideRequest;

    @android.annotation.Nullable
    private android.hardware.devicestate.DeviceState mRearDisplayState;

    @android.annotation.NonNull
    private final com.android.server.devicestate.DeviceStateManagerService.SystemPropertySetter mSystemPropertySetter;

    @com.android.internal.annotations.VisibleForTesting
    interface SystemPropertySetter {
        void setDebugTracingDeviceStateProperty(java.lang.String str);
    }

    public DeviceStateManagerService(@android.annotation.NonNull android.content.Context context) {
        this(context, com.android.server.devicestate.DeviceStatePolicy.Provider.fromResources(context.getResources()).instantiate(context));
    }

    private DeviceStateManagerService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.devicestate.DeviceStatePolicy deviceStatePolicy) {
        this(context, deviceStatePolicy, new com.android.server.devicestate.DeviceStateManagerService.SystemPropertySetter() { // from class: com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda3
            @Override // com.android.server.devicestate.DeviceStateManagerService.SystemPropertySetter
            public final void setDebugTracingDeviceStateProperty(java.lang.String str) {
                android.os.SystemProperties.set("debug.tracing.device_state", str);
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    DeviceStateManagerService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.devicestate.DeviceStatePolicy deviceStatePolicy, @android.annotation.NonNull com.android.server.devicestate.DeviceStateManagerService.SystemPropertySetter systemPropertySetter) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mDeviceStates = new android.util.SparseArray<>();
        this.mCommittedState = java.util.Optional.empty();
        this.mPendingState = java.util.Optional.empty();
        this.mIsPolicyWaitingForState = false;
        this.mBaseState = java.util.Optional.empty();
        this.mActiveOverride = java.util.Optional.empty();
        this.mActiveBaseStateOverride = java.util.Optional.empty();
        this.mProcessRecords = new android.util.SparseArray<>();
        this.mDeviceStatesAvailableForAppRequests = new java.util.HashSet();
        this.mProcessObserver = new android.app.IProcessObserver.Stub() { // from class: com.android.server.devicestate.DeviceStateManagerService.1
            public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
                synchronized (com.android.server.devicestate.DeviceStateManagerService.this.mLock) {
                    try {
                        if (com.android.server.devicestate.DeviceStateManagerService.this.shouldCancelOverrideRequestWhenRequesterNotOnTop()) {
                            com.android.server.devicestate.OverrideRequest overrideRequest = (com.android.server.devicestate.OverrideRequest) com.android.server.devicestate.DeviceStateManagerService.this.mActiveOverride.get();
                            if (i == overrideRequest.getPid() && i2 == overrideRequest.getUid()) {
                                if (!z) {
                                    com.android.server.devicestate.DeviceStateManagerService.this.mOverrideRequestController.cancelRequest(overrideRequest);
                                }
                            }
                        }
                    } finally {
                    }
                }
            }

            public void onProcessStarted(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
            }

            public void onProcessDied(int i, int i2) {
            }

            public void onForegroundServicesChanged(int i, int i2, int i3) {
            }
        };
        this.mOverrideRequestScreenObserver = new com.android.server.devicestate.DeviceStateManagerService.OverrideRequestScreenObserver();
        this.mSystemPropertySetter = systemPropertySetter;
        this.mHandler = new android.os.Handler(com.android.server.DisplayThread.get().getLooper());
        this.mOverrideRequestController = new com.android.server.devicestate.OverrideRequestController(new com.android.server.devicestate.OverrideRequestController.StatusChangeListener() { // from class: com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda6
            @Override // com.android.server.devicestate.OverrideRequestController.StatusChangeListener
            public final void onStatusChanged(com.android.server.devicestate.OverrideRequest overrideRequest, int i, int i2) {
                com.android.server.devicestate.DeviceStateManagerService.this.onOverrideRequestStatusChangedLocked(overrideRequest, i, i2);
            }
        });
        this.mDeviceStatePolicy = deviceStatePolicy;
        this.mDeviceStateProviderListener = new com.android.server.devicestate.DeviceStateManagerService.DeviceStateProviderListener();
        this.mDeviceStatePolicy.getDeviceStateProvider().setListener(this.mDeviceStateProviderListener);
        this.mBinderService = new com.android.server.devicestate.DeviceStateManagerService.BinderService();
        this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        this.mDeviceStateNotificationController = new com.android.server.devicestate.DeviceStateNotificationController(context, this.mHandler, new java.lang.Runnable() { // from class: com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.devicestate.DeviceStateManagerService.this.lambda$new$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        synchronized (this.mLock) {
            java.util.Optional<com.android.server.devicestate.OverrideRequest> optional = this.mActiveOverride;
            com.android.server.devicestate.OverrideRequestController overrideRequestController = this.mOverrideRequestController;
            java.util.Objects.requireNonNull(overrideRequestController);
            optional.ifPresent(new com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda4(overrideRequestController));
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("device_state", this.mBinderService);
        publishLocalService(android.hardware.devicestate.DeviceStateManagerInternal.class, new com.android.server.devicestate.DeviceStateManagerService.LocalService());
        synchronized (this.mLock) {
            readStatesAvailableForRequestFromApps();
            this.mFoldedDeviceStates = readFoldedStates();
        }
        this.mActivityTaskManagerInternal.registerScreenObserver(this.mOverrideRequestScreenObserver);
        ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).registerProcessObserver(this.mProcessObserver);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.Handler getHandler() {
        return this.mHandler;
    }

    @android.annotation.NonNull
    java.util.Optional<android.hardware.devicestate.DeviceState> getCommittedState() {
        java.util.Optional<android.hardware.devicestate.DeviceState> optional;
        synchronized (this.mLock) {
            optional = this.mCommittedState;
        }
        return optional;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    java.util.Optional<android.hardware.devicestate.DeviceState> getPendingState() {
        java.util.Optional<android.hardware.devicestate.DeviceState> optional;
        synchronized (this.mLock) {
            optional = this.mPendingState;
        }
        return optional;
    }

    @android.annotation.NonNull
    java.util.Optional<android.hardware.devicestate.DeviceState> getBaseState() {
        java.util.Optional<android.hardware.devicestate.DeviceState> optional;
        synchronized (this.mLock) {
            optional = this.mBaseState;
        }
        return optional;
    }

    @android.annotation.NonNull
    java.util.Optional<android.hardware.devicestate.DeviceState> getOverrideState() {
        synchronized (this.mLock) {
            try {
                if (this.mActiveOverride.isPresent()) {
                    return getStateLocked(this.mActiveOverride.get().getRequestedStateIdentifier());
                }
                return java.util.Optional.empty();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    java.util.Optional<android.hardware.devicestate.DeviceState> getOverrideBaseState() {
        synchronized (this.mLock) {
            try {
                if (this.mActiveBaseStateOverride.isPresent()) {
                    return getStateLocked(this.mActiveBaseStateOverride.get().getRequestedStateIdentifier());
                }
                return java.util.Optional.empty();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.hardware.devicestate.DeviceState[] getSupportedStates() {
        android.hardware.devicestate.DeviceState[] deviceStateArr;
        synchronized (this.mLock) {
            try {
                int size = this.mDeviceStates.size();
                deviceStateArr = new android.hardware.devicestate.DeviceState[size];
                for (int i = 0; i < size; i++) {
                    deviceStateArr[i] = this.mDeviceStates.valueAt(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return deviceStateArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getSupportedStateIdentifiersLocked() {
        int size = this.mDeviceStates.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = this.mDeviceStates.valueAt(i).getIdentifier();
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.hardware.devicestate.DeviceStateInfo getDeviceStateInfoLocked() {
        return new android.hardware.devicestate.DeviceStateInfo(getSupportedStateIdentifiersLocked(), this.mBaseState.isPresent() ? this.mBaseState.get().getIdentifier() : -1, this.mCommittedState.isPresent() ? this.mCommittedState.get().getIdentifier() : -1);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.hardware.devicestate.IDeviceStateManager getBinderService() {
        return this.mBinderService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSupportedStates(android.hardware.devicestate.DeviceState[] deviceStateArr, int i) {
        synchronized (this.mLock) {
            try {
                int[] supportedStateIdentifiersLocked = getSupportedStateIdentifiersLocked();
                this.mDeviceStates.clear();
                boolean z = false;
                for (android.hardware.devicestate.DeviceState deviceState : deviceStateArr) {
                    if (deviceState.hasFlag(1)) {
                        z = true;
                    }
                    this.mDeviceStates.put(deviceState.getIdentifier(), deviceState);
                }
                this.mOverrideRequestController.setStickyRequestsAllowed(z);
                int[] supportedStateIdentifiersLocked2 = getSupportedStateIdentifiersLocked();
                if (java.util.Arrays.equals(supportedStateIdentifiersLocked, supportedStateIdentifiersLocked2)) {
                    return;
                }
                this.mOverrideRequestController.handleNewSupportedStates(supportedStateIdentifiersLocked2, i);
                updatePendingStateLocked();
                setRearDisplayStateLocked();
                notifyDeviceStateInfoChangedAsync();
                this.mHandler.post(new com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda0(this));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setRearDisplayStateLocked() {
        int integer = getContext().getResources().getInteger(android.R.integer.config_delay_for_ims_dereg_millis);
        if (integer != -1) {
            this.mRearDisplayState = this.mDeviceStates.get(integer);
        }
    }

    private boolean isSupportedStateLocked(int i) {
        return this.mDeviceStates.contains(i);
    }

    @android.annotation.Nullable
    private java.util.Optional<android.hardware.devicestate.DeviceState> getStateLocked(int i) {
        return java.util.Optional.ofNullable(this.mDeviceStates.get(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBaseState(int i) {
        synchronized (this.mLock) {
            try {
                java.util.Optional<android.hardware.devicestate.DeviceState> stateLocked = getStateLocked(i);
                if (!stateLocked.isPresent()) {
                    throw new java.lang.IllegalArgumentException("Base state is not supported");
                }
                android.hardware.devicestate.DeviceState deviceState = stateLocked.get();
                if (this.mBaseState.isPresent() && this.mBaseState.get().equals(deviceState)) {
                    return;
                }
                if (this.mRearDisplayPendingOverrideRequest != null) {
                    handleRearDisplayBaseStateChangedLocked(i);
                }
                this.mBaseState = java.util.Optional.of(deviceState);
                if (deviceState.hasFlag(1)) {
                    this.mOverrideRequestController.cancelOverrideRequest();
                }
                this.mOverrideRequestController.handleBaseStateChanged(i);
                updatePendingStateLocked();
                notifyDeviceStateInfoChangedAsync();
                this.mHandler.post(new com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda0(this));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean updatePendingStateLocked() {
        android.hardware.devicestate.DeviceState deviceState;
        if (this.mPendingState.isPresent()) {
            return false;
        }
        if (this.mActiveOverride.isPresent()) {
            deviceState = getStateLocked(this.mActiveOverride.get().getRequestedStateIdentifier()).get();
        } else if (this.mBaseState.isPresent() && isSupportedStateLocked(this.mBaseState.get().getIdentifier())) {
            deviceState = this.mBaseState.get();
        } else {
            deviceState = null;
        }
        if (deviceState == null) {
            return false;
        }
        if (this.mCommittedState.isPresent() && deviceState.equals(this.mCommittedState.get())) {
            return false;
        }
        this.mPendingState = java.util.Optional.of(deviceState);
        this.mIsPolicyWaitingForState = true;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyPolicyIfNeeded() {
        if (java.lang.Thread.holdsLock(this.mLock)) {
            java.lang.Throwable th = new java.lang.Throwable("Attempting to notify DeviceStatePolicy with service lock held");
            th.fillInStackTrace();
            android.util.Slog.w(TAG, th);
        }
        synchronized (this.mLock) {
            try {
                if (this.mIsPolicyWaitingForState) {
                    this.mIsPolicyWaitingForState = false;
                    this.mDeviceStatePolicy.configureDeviceForState(this.mPendingState.get().getIdentifier(), new java.lang.Runnable() { // from class: com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.devicestate.DeviceStateManagerService.this.commitPendingState();
                        }
                    });
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commitPendingState() {
        com.android.server.devicestate.DeviceStateManagerService.ProcessRecord processRecord;
        synchronized (this.mLock) {
            try {
                android.hardware.devicestate.DeviceState deviceState = this.mPendingState.get();
                com.android.internal.util.FrameworkStatsLog.write(350, deviceState.getIdentifier(), !this.mCommittedState.isPresent());
                java.lang.String str = deviceState.getIdentifier() + ":" + deviceState.getName();
                android.os.Trace.instantForTrack(524288L, "DeviceStateChanged", str);
                this.mSystemPropertySetter.setDebugTracingDeviceStateProperty(str);
                this.mCommittedState = java.util.Optional.of(deviceState);
                this.mPendingState = java.util.Optional.empty();
                updatePendingStateLocked();
                notifyDeviceStateInfoChangedAsync();
                com.android.server.devicestate.OverrideRequest orElse = this.mActiveOverride.orElse(null);
                if (orElse != null && orElse.getRequestedStateIdentifier() == deviceState.getIdentifier() && (processRecord = this.mProcessRecords.get(orElse.getPid())) != null) {
                    processRecord.notifyRequestActiveAsync(orElse.getToken());
                }
                this.mHandler.post(new com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda0(this));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void notifyDeviceStateInfoChangedAsync() {
        synchronized (this.mLock) {
            try {
                if (this.mPendingState.isPresent()) {
                    android.util.Slog.i(TAG, "Cannot notify device state info change when pending state is present.");
                    return;
                }
                if (!this.mBaseState.isPresent() || !this.mCommittedState.isPresent()) {
                    android.util.Slog.e(TAG, "Cannot notify device state info change before the initial state has been committed.");
                    return;
                }
                if (this.mProcessRecords.size() == 0) {
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i = 0; i < this.mProcessRecords.size(); i++) {
                    arrayList.add(this.mProcessRecords.valueAt(i));
                }
                android.hardware.devicestate.DeviceStateInfo deviceStateInfoLocked = getDeviceStateInfoLocked();
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    ((com.android.server.devicestate.DeviceStateManagerService.ProcessRecord) arrayList.get(i2)).notifyDeviceStateInfoAsync(deviceStateInfoLocked);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onOverrideRequestStatusChangedLocked(@android.annotation.NonNull com.android.server.devicestate.OverrideRequest overrideRequest, int i, int i2) {
        if (overrideRequest.getRequestType() == 1) {
            switch (i) {
                case 1:
                    enableBaseStateRequestLocked(overrideRequest);
                    return;
                case 2:
                    if (this.mActiveBaseStateOverride.isPresent() && this.mActiveBaseStateOverride.get() == overrideRequest) {
                        this.mActiveBaseStateOverride = java.util.Optional.empty();
                        break;
                    }
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown request status: " + i);
            }
        } else if (overrideRequest.getRequestType() == 0) {
            switch (i) {
                case 1:
                    this.mActiveOverride = java.util.Optional.of(overrideRequest);
                    this.mDeviceStateNotificationController.showStateActiveNotificationIfNeeded(overrideRequest.getRequestedStateIdentifier(), overrideRequest.getUid());
                    break;
                case 2:
                    if (this.mActiveOverride.isPresent() && this.mActiveOverride.get() == overrideRequest) {
                        this.mActiveOverride = java.util.Optional.empty();
                        this.mDeviceStateNotificationController.cancelNotification(overrideRequest.getRequestedStateIdentifier());
                        if ((i2 & 1) == 1) {
                            this.mDeviceStateNotificationController.showThermalCriticalNotificationIfNeeded(overrideRequest.getRequestedStateIdentifier());
                            break;
                        } else if ((i2 & 2) == 2) {
                            this.mDeviceStateNotificationController.showPowerSaveNotificationIfNeeded(overrideRequest.getRequestedStateIdentifier());
                            break;
                        }
                    }
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown request status: " + i);
            }
        } else {
            throw new java.lang.IllegalArgumentException("Unknown OverrideRest type: " + overrideRequest.getRequestType());
        }
        boolean updatePendingStateLocked = updatePendingStateLocked();
        com.android.server.devicestate.DeviceStateManagerService.ProcessRecord processRecord = this.mProcessRecords.get(overrideRequest.getPid());
        if (processRecord == null) {
            this.mHandler.post(new com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda0(this));
            return;
        }
        if (i == 1) {
            if (!updatePendingStateLocked && !this.mPendingState.isPresent()) {
                processRecord.notifyRequestActiveAsync(overrideRequest.getToken());
            }
        } else {
            processRecord.notifyRequestCanceledAsync(overrideRequest.getToken());
        }
        this.mHandler.post(new com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda0(this));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void enableBaseStateRequestLocked(com.android.server.devicestate.OverrideRequest overrideRequest) {
        setBaseState(overrideRequest.getRequestedStateIdentifier());
        this.mActiveBaseStateOverride = java.util.Optional.of(overrideRequest);
        this.mProcessRecords.get(overrideRequest.getPid()).notifyRequestActiveAsync(overrideRequest.getToken());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerProcess(int i, android.hardware.devicestate.IDeviceStateManagerCallback iDeviceStateManagerCallback) {
        synchronized (this.mLock) {
            try {
                if (this.mProcessRecords.contains(i)) {
                    throw new java.lang.SecurityException("The calling process has already registered an IDeviceStateManagerCallback.");
                }
                com.android.server.devicestate.DeviceStateManagerService.ProcessRecord processRecord = new com.android.server.devicestate.DeviceStateManagerService.ProcessRecord(iDeviceStateManagerCallback, i, new com.android.server.devicestate.DeviceStateManagerService.ProcessRecord.DeathListener() { // from class: com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda2
                    @Override // com.android.server.devicestate.DeviceStateManagerService.ProcessRecord.DeathListener
                    public final void onProcessDied(com.android.server.devicestate.DeviceStateManagerService.ProcessRecord processRecord2) {
                        com.android.server.devicestate.DeviceStateManagerService.this.handleProcessDied(processRecord2);
                    }
                }, this.mHandler);
                try {
                    iDeviceStateManagerCallback.asBinder().linkToDeath(processRecord, 0);
                    this.mProcessRecords.put(i, processRecord);
                    android.hardware.devicestate.DeviceStateInfo deviceStateInfoLocked = this.mCommittedState.isPresent() ? getDeviceStateInfoLocked() : null;
                    if (deviceStateInfoLocked != null) {
                        processRecord.notifyDeviceStateInfoAsync(deviceStateInfoLocked);
                    }
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleProcessDied(com.android.server.devicestate.DeviceStateManagerService.ProcessRecord processRecord) {
        synchronized (this.mLock) {
            try {
                this.mProcessRecords.remove(processRecord.mPid);
                this.mOverrideRequestController.handleProcessDied(processRecord.mPid);
                if (shouldCancelOverrideRequestWhenRequesterNotOnTop()) {
                    this.mOverrideRequestController.cancelRequest(this.mActiveOverride.get());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestStateInternal(int i, int i2, int i3, int i4, @android.annotation.NonNull android.os.IBinder iBinder, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mProcessRecords.get(i3) == null) {
                    throw new java.lang.IllegalStateException("Process " + i3 + " has no registered callback.");
                }
                if (this.mOverrideRequestController.hasRequest(iBinder, 0)) {
                    throw new java.lang.IllegalStateException("Request has already been made for the supplied token: " + iBinder);
                }
                java.util.Optional<android.hardware.devicestate.DeviceState> stateLocked = getStateLocked(i);
                if (!stateLocked.isPresent()) {
                    throw new java.lang.IllegalArgumentException("Requested state: " + i + " is not supported.");
                }
                com.android.server.devicestate.OverrideRequest overrideRequest = new com.android.server.devicestate.OverrideRequest(iBinder, i3, i4, stateLocked.get(), i2, 0);
                if (!z && this.mRearDisplayState != null && i == this.mRearDisplayState.getIdentifier()) {
                    showRearDisplayEducationalOverlayLocked(overrideRequest);
                } else {
                    this.mOverrideRequestController.addRequest(overrideRequest);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void showRearDisplayEducationalOverlayLocked(@android.annotation.NonNull com.android.server.devicestate.OverrideRequest overrideRequest) {
        this.mRearDisplayPendingOverrideRequest = overrideRequest;
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.showRearDisplayDialog(this.mBaseState.get().getIdentifier());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelStateRequestInternal(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mProcessRecords.get(i) == null) {
                    throw new java.lang.IllegalStateException("Process " + i + " has no registered callback.");
                }
                java.util.Optional<com.android.server.devicestate.OverrideRequest> optional = this.mActiveOverride;
                com.android.server.devicestate.OverrideRequestController overrideRequestController = this.mOverrideRequestController;
                java.util.Objects.requireNonNull(overrideRequestController);
                optional.ifPresent(new com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda4(overrideRequestController));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestBaseStateOverrideInternal(int i, int i2, int i3, int i4, @android.annotation.NonNull android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                java.util.Optional<android.hardware.devicestate.DeviceState> stateLocked = getStateLocked(i);
                if (!stateLocked.isPresent()) {
                    throw new java.lang.IllegalArgumentException("Requested state: " + i + " is not supported.");
                }
                if (this.mProcessRecords.get(i3) == null) {
                    throw new java.lang.IllegalStateException("Process " + i3 + " has no registered callback.");
                }
                if (this.mOverrideRequestController.hasRequest(iBinder, 1)) {
                    throw new java.lang.IllegalStateException("Request has already been made for the supplied token: " + iBinder);
                }
                this.mOverrideRequestController.addBaseStateRequest(new com.android.server.devicestate.OverrideRequest(iBinder, i3, i4, stateLocked.get(), i2, 1));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelBaseStateOverrideInternal(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mProcessRecords.get(i) == null) {
                    throw new java.lang.IllegalStateException("Process " + i + " has no registered callback.");
                }
                setBaseState(this.mDeviceStateProviderListener.mCurrentBaseState);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStateRequestOverlayDismissedInternal(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mRearDisplayPendingOverrideRequest != null) {
                    if (z) {
                        this.mProcessRecords.get(this.mRearDisplayPendingOverrideRequest.getPid()).notifyRequestCanceledAsync(this.mRearDisplayPendingOverrideRequest.getToken());
                    } else {
                        this.mOverrideRequestController.addRequest(this.mRearDisplayPendingOverrideRequest);
                    }
                    this.mRearDisplayPendingOverrideRequest = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpInternal(java.io.PrintWriter printWriter) {
        printWriter.println("DEVICE STATE MANAGER (dumpsys device_state)");
        synchronized (this.mLock) {
            try {
                printWriter.println("  mCommittedState=" + this.mCommittedState);
                printWriter.println("  mPendingState=" + this.mPendingState);
                printWriter.println("  mBaseState=" + this.mBaseState);
                printWriter.println("  mOverrideState=" + getOverrideState());
                int size = this.mProcessRecords.size();
                printWriter.println();
                printWriter.println("Registered processes: size=" + size);
                for (int i = 0; i < size; i++) {
                    printWriter.println("  " + i + ": mPid=" + this.mProcessRecords.valueAt(i).mPid);
                }
                this.mOverrideRequestController.dumpInternal(printWriter);
                printWriter.println();
                this.mDeviceStatePolicy.dump(printWriter, null);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void assertCanRequestDeviceState(int i, int i2, int i3) {
        if (!(isTopApp(i) && isForegroundApp(i, i2) && isStateAvailableForAppRequests(i3))) {
            getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DEVICE_STATE", "Permission required to request device state, or the call must come from the top app and be a device state that is available for apps to request.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void assertCanControlDeviceState(int i, int i2) {
        if (!(isTopApp(i) && isForegroundApp(i, i2))) {
            getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DEVICE_STATE", "Permission required to request device state, or the call must come from the top app.");
        }
    }

    private boolean isForegroundApp(int i, int i2) {
        try {
            java.util.List runningAppProcesses = android.app.ActivityManager.getService().getRunningAppProcesses();
            for (int i3 = 0; i3 < runningAppProcesses.size(); i3++) {
                android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo = (android.app.ActivityManager.RunningAppProcessInfo) runningAppProcesses.get(i3);
                if (runningAppProcessInfo.pid == i && runningAppProcessInfo.uid == i2 && runningAppProcessInfo.importance <= 100) {
                    return true;
                }
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "am.getRunningAppProcesses() failed", e);
        }
        return false;
    }

    private boolean isTopApp(int i) {
        com.android.server.wm.WindowProcessController topApp = this.mActivityTaskManagerInternal.getTopApp();
        return topApp != null && topApp.getPid() == i;
    }

    private boolean isStateAvailableForAppRequests(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mDeviceStatesAvailableForAppRequests.contains(java.lang.Integer.valueOf(i));
        }
        return contains;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void readStatesAvailableForRequestFromApps() {
        for (java.lang.String str : getContext().getResources().getStringArray(android.R.array.config_default_vm_number)) {
            int integer = getContext().getResources().getInteger(getContext().getResources().getIdentifier(str, "integer", com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME));
            if (isValidState(integer)) {
                this.mDeviceStatesAvailableForAppRequests.add(java.lang.Integer.valueOf(integer));
            } else {
                android.util.Slog.e(TAG, "Invalid device state was found in the configuration file. State id: " + integer);
            }
        }
    }

    private java.util.Set<java.lang.Integer> readFoldedStates() {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i : getContext().getResources().getIntArray(android.R.array.config_face_acquire_vendor_keyguard_ignorelist)) {
            hashSet.add(java.lang.Integer.valueOf(i));
        }
        return hashSet;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isValidState(int i) {
        for (int i2 = 0; i2 < this.mDeviceStates.size(); i2++) {
            if (i == this.mDeviceStates.valueAt(i2).getIdentifier()) {
                return true;
            }
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void handleRearDisplayBaseStateChangedLocked(int i) {
        if (isDeviceOpeningLocked(i)) {
            onStateRequestOverlayDismissedInternal(false);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isDeviceOpeningLocked(final int i) {
        return this.mBaseState.filter(new java.util.function.Predicate() { // from class: com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isDeviceOpeningLocked$2;
                lambda$isDeviceOpeningLocked$2 = com.android.server.devicestate.DeviceStateManagerService.this.lambda$isDeviceOpeningLocked$2(i, (android.hardware.devicestate.DeviceState) obj);
                return lambda$isDeviceOpeningLocked$2;
            }
        }).isPresent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isDeviceOpeningLocked$2(int i, android.hardware.devicestate.DeviceState deviceState) {
        return this.mFoldedDeviceStates.contains(java.lang.Integer.valueOf(deviceState.getIdentifier())) && !this.mFoldedDeviceStates.contains(java.lang.Integer.valueOf(i));
    }

    private final class DeviceStateProviderListener implements com.android.server.devicestate.DeviceStateProvider.Listener {
        int mCurrentBaseState;

        private DeviceStateProviderListener() {
        }

        @Override // com.android.server.devicestate.DeviceStateProvider.Listener
        public void onSupportedDeviceStatesChanged(android.hardware.devicestate.DeviceState[] deviceStateArr, int i) {
            if (deviceStateArr.length == 0) {
                throw new java.lang.IllegalArgumentException("Supported device states must not be empty");
            }
            com.android.server.devicestate.DeviceStateManagerService.this.updateSupportedStates(deviceStateArr, i);
        }

        @Override // com.android.server.devicestate.DeviceStateProvider.Listener
        public void onStateChanged(int i) {
            if (i < 0 || i > 10000) {
                throw new java.lang.IllegalArgumentException("Invalid identifier: " + i);
            }
            this.mCurrentBaseState = i;
            com.android.server.devicestate.DeviceStateManagerService.this.setBaseState(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ProcessRecord implements android.os.IBinder.DeathRecipient {
        private static final int STATUS_ACTIVE = 0;
        private static final int STATUS_CANCELED = 2;
        private static final int STATUS_SUSPENDED = 1;
        private final android.hardware.devicestate.IDeviceStateManagerCallback mCallback;
        private final com.android.server.devicestate.DeviceStateManagerService.ProcessRecord.DeathListener mDeathListener;
        private final android.os.Handler mHandler;
        private final java.util.WeakHashMap<android.os.IBinder, java.lang.Integer> mLastNotifiedStatus = new java.util.WeakHashMap<>();
        private final int mPid;

        public interface DeathListener {
            void onProcessDied(com.android.server.devicestate.DeviceStateManagerService.ProcessRecord processRecord);
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        private @interface RequestStatus {
        }

        ProcessRecord(android.hardware.devicestate.IDeviceStateManagerCallback iDeviceStateManagerCallback, int i, com.android.server.devicestate.DeviceStateManagerService.ProcessRecord.DeathListener deathListener, android.os.Handler handler) {
            this.mCallback = iDeviceStateManagerCallback;
            this.mPid = i;
            this.mDeathListener = deathListener;
            this.mHandler = handler;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mDeathListener.onProcessDied(this);
        }

        public void notifyDeviceStateInfoAsync(@android.annotation.NonNull final android.hardware.devicestate.DeviceStateInfo deviceStateInfo) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.devicestate.DeviceStateManagerService$ProcessRecord$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.devicestate.DeviceStateManagerService.ProcessRecord.this.lambda$notifyDeviceStateInfoAsync$0(deviceStateInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyDeviceStateInfoAsync$0(android.hardware.devicestate.DeviceStateInfo deviceStateInfo) {
            boolean isTagEnabled = android.os.Trace.isTagEnabled(524288L);
            if (isTagEnabled) {
                android.os.Trace.traceBegin(524288L, "notifyDeviceStateInfoAsync(pid=" + this.mPid + ")");
            }
            try {
                try {
                    this.mCallback.onDeviceStateInfoChanged(deviceStateInfo);
                    if (!isTagEnabled) {
                        return;
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.devicestate.DeviceStateManagerService.TAG, "Failed to notify process " + this.mPid + " that device state changed.", e);
                    if (!isTagEnabled) {
                        return;
                    }
                }
                android.os.Trace.traceEnd(524288L);
            } catch (java.lang.Throwable th) {
                if (isTagEnabled) {
                    android.os.Trace.traceEnd(524288L);
                }
                throw th;
            }
        }

        public void notifyRequestActiveAsync(final android.os.IBinder iBinder) {
            java.lang.Integer num = this.mLastNotifiedStatus.get(iBinder);
            if (num != null && (num.intValue() == 0 || num.intValue() == 2)) {
                return;
            }
            this.mLastNotifiedStatus.put(iBinder, 0);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.devicestate.DeviceStateManagerService$ProcessRecord$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.devicestate.DeviceStateManagerService.ProcessRecord.this.lambda$notifyRequestActiveAsync$1(iBinder);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRequestActiveAsync$1(android.os.IBinder iBinder) {
            try {
                this.mCallback.onRequestActive(iBinder);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.devicestate.DeviceStateManagerService.TAG, "Failed to notify process " + this.mPid + " that request state changed.", e);
            }
        }

        public void notifyRequestCanceledAsync(final android.os.IBinder iBinder) {
            java.lang.Integer num = this.mLastNotifiedStatus.get(iBinder);
            if (num == null || num.intValue() != 2) {
                this.mLastNotifiedStatus.put(iBinder, 2);
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.devicestate.DeviceStateManagerService$ProcessRecord$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.devicestate.DeviceStateManagerService.ProcessRecord.this.lambda$notifyRequestCanceledAsync$2(iBinder);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRequestCanceledAsync$2(android.os.IBinder iBinder) {
            try {
                this.mCallback.onRequestCanceled(iBinder);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.devicestate.DeviceStateManagerService.TAG, "Failed to notify process " + this.mPid + " that request state changed.", e);
            }
        }
    }

    private final class BinderService extends android.hardware.devicestate.IDeviceStateManager.Stub {
        private BinderService() {
        }

        public android.hardware.devicestate.DeviceStateInfo getDeviceStateInfo() {
            android.hardware.devicestate.DeviceStateInfo deviceStateInfoLocked;
            synchronized (com.android.server.devicestate.DeviceStateManagerService.this.mLock) {
                deviceStateInfoLocked = com.android.server.devicestate.DeviceStateManagerService.this.getDeviceStateInfoLocked();
            }
            return deviceStateInfoLocked;
        }

        public void registerCallback(android.hardware.devicestate.IDeviceStateManagerCallback iDeviceStateManagerCallback) {
            if (iDeviceStateManagerCallback == null) {
                throw new java.lang.IllegalArgumentException("Device state callback must not be null.");
            }
            int callingPid = android.os.Binder.getCallingPid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.devicestate.DeviceStateManagerService.this.registerProcess(callingPid, iDeviceStateManagerCallback);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestState(android.os.IBinder iBinder, int i, int i2) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.devicestate.DeviceStateManagerService.this.assertCanRequestDeviceState(callingPid, callingUid, i);
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("Request token must not be null.");
            }
            boolean z = com.android.server.devicestate.DeviceStateManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.CONTROL_DEVICE_STATE") == 0;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.devicestate.DeviceStateManagerService.this.requestStateInternal(i, i2, callingPid, callingUid, iBinder, z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void cancelStateRequest() {
            int callingPid = android.os.Binder.getCallingPid();
            com.android.server.devicestate.DeviceStateManagerService.this.assertCanControlDeviceState(callingPid, android.os.Binder.getCallingUid());
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.devicestate.DeviceStateManagerService.this.cancelStateRequestInternal(callingPid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestBaseStateOverride(android.os.IBinder iBinder, int i, int i2) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.devicestate.DeviceStateManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DEVICE_STATE", "Permission required to control base state of device.");
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("Request token must not be null.");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.devicestate.DeviceStateManagerService.this.requestBaseStateOverrideInternal(i, i2, callingPid, callingUid, iBinder);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void cancelBaseStateOverride() {
            int callingPid = android.os.Binder.getCallingPid();
            com.android.server.devicestate.DeviceStateManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DEVICE_STATE", "Permission required to control base state of device.");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.devicestate.DeviceStateManagerService.this.cancelBaseStateOverrideInternal(callingPid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DEVICE_STATE")
        public void onStateRequestOverlayDismissed(boolean z) {
            onStateRequestOverlayDismissed_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.devicestate.DeviceStateManagerService.this.onStateRequestOverlayDismissedInternal(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.devicestate.DeviceStateManagerShellCommand(com.android.server.devicestate.DeviceStateManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.devicestate.DeviceStateManagerService.this.getContext(), com.android.server.devicestate.DeviceStateManagerService.TAG, printWriter)) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.devicestate.DeviceStateManagerService.this.dumpInternal(printWriter);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    private final class LocalService extends android.hardware.devicestate.DeviceStateManagerInternal {
        private LocalService() {
        }

        public int[] getSupportedStateIdentifiers() {
            int[] supportedStateIdentifiersLocked;
            synchronized (com.android.server.devicestate.DeviceStateManagerService.this.mLock) {
                supportedStateIdentifiersLocked = com.android.server.devicestate.DeviceStateManagerService.this.getSupportedStateIdentifiersLocked();
            }
            return supportedStateIdentifiersLocked;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean shouldCancelOverrideRequestWhenRequesterNotOnTop() {
        if (this.mActiveOverride.isEmpty()) {
            return false;
        }
        return this.mDeviceStates.get(this.mActiveOverride.get().getRequestedStateIdentifier()).hasFlag(8);
    }

    private class OverrideRequestScreenObserver implements com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver {
        private OverrideRequestScreenObserver() {
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
        public void onAwakeStateChanged(boolean z) {
            synchronized (com.android.server.devicestate.DeviceStateManagerService.this.mLock) {
                if (!z) {
                    try {
                        if (com.android.server.devicestate.DeviceStateManagerService.this.shouldCancelOverrideRequestWhenRequesterNotOnTop()) {
                            com.android.server.devicestate.DeviceStateManagerService.this.mOverrideRequestController.cancelRequest((com.android.server.devicestate.OverrideRequest) com.android.server.devicestate.DeviceStateManagerService.this.mActiveOverride.get());
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
        public void onKeyguardStateChanged(boolean z) {
            synchronized (com.android.server.devicestate.DeviceStateManagerService.this.mLock) {
                if (z) {
                    try {
                        if (com.android.server.devicestate.DeviceStateManagerService.this.shouldCancelOverrideRequestWhenRequesterNotOnTop()) {
                            com.android.server.devicestate.DeviceStateManagerService.this.mOverrideRequestController.cancelRequest((com.android.server.devicestate.OverrideRequest) com.android.server.devicestate.DeviceStateManagerService.this.mActiveOverride.get());
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }
}
