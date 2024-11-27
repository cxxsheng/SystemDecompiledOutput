package com.android.server.companion.virtual;

/* loaded from: classes.dex */
final class VirtualDeviceImpl extends android.companion.virtual.IVirtualDevice.Stub implements android.os.IBinder.DeathRecipient, com.android.server.companion.virtual.GenericWindowPolicyController.RunningAppsChangedListener {
    private static final int DEFAULT_VIRTUAL_DISPLAY_FLAGS = 24896;
    private static final int DEFAULT_VIRTUAL_DISPLAY_FLAGS_PRE_VIC = 137;
    private static final long PENDING_TRAMPOLINE_TIMEOUT_MS = 5000;
    private static final java.lang.String PERSISTENT_ID_PREFIX_CDM_ASSOCIATION = "companion:";
    private static final java.lang.String TAG = "VirtualDeviceImpl";
    private final android.companion.virtual.IVirtualDeviceActivityListener mActivityListener;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mVirtualDeviceLock"})
    private final java.util.Set<android.content.ComponentName> mActivityPolicyExemptions;
    private final android.os.IBinder mAppToken;
    private final android.companion.AssociationInfo mAssociationInfo;

    @android.annotation.NonNull
    private final android.content.AttributionSource mAttributionSource;
    private final int mBaseVirtualDisplayFlags;
    private final com.android.server.companion.virtual.CameraAccessController mCameraAccessController;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mVirtualDeviceLock"})
    private boolean mDefaultShowPointerIcon;
    private final int mDeviceId;

    @com.android.internal.annotations.GuardedBy({"mVirtualDeviceLock"})
    private final android.util.SparseIntArray mDevicePolicies;
    private final android.hardware.display.DisplayManagerGlobal mDisplayManager;
    private final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
    private final com.android.server.companion.virtual.InputController mInputController;

    @com.android.internal.annotations.GuardedBy({"mVirtualDeviceLock"})
    private final java.util.Map<android.os.IBinder, android.content.IntentFilter> mIntentInterceptors;

    @com.android.internal.annotations.GuardedBy({"mVirtualDeviceLock"})
    @android.annotation.Nullable
    private android.os.LocaleList mLocaleList;
    private final java.lang.String mOwnerPackageName;
    private final int mOwnerUid;
    private final android.companion.virtual.VirtualDeviceParams mParams;
    private final com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampolineCallback mPendingTrampolineCallback;
    private final android.content.ComponentName mPermissionDialogComponent;

    @android.annotation.Nullable
    private final java.lang.String mPersistentDeviceId;

    @android.annotation.NonNull
    private final android.companion.virtual.VirtualDevice mPublicVirtualDeviceObject;

    @android.annotation.NonNull
    private final java.util.function.Consumer<android.util.ArraySet<java.lang.Integer>> mRunningAppsChangedCallback;
    private final com.android.server.companion.virtual.SensorController mSensorController;
    private final com.android.server.companion.virtual.VirtualDeviceManagerService mService;
    private final android.companion.virtual.IVirtualDeviceSoundEffectListener mSoundEffectListener;
    private com.android.server.companion.virtual.audio.VirtualAudioController mVirtualAudioController;

    @android.annotation.Nullable
    private final com.android.server.companion.virtual.camera.VirtualCameraController mVirtualCameraController;
    private final java.lang.Object mVirtualDeviceLock;
    private final com.android.server.companion.virtual.VirtualDeviceLog mVirtualDeviceLog;

    @com.android.internal.annotations.GuardedBy({"mVirtualDeviceLock"})
    private final android.util.SparseArray<com.android.server.companion.virtual.VirtualDeviceImpl.VirtualDisplayWrapper> mVirtualDisplays;

    interface PendingTrampolineCallback {
        void startWaitingForPendingTrampoline(com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline pendingTrampoline);

        void stopWaitingForPendingTrampoline(com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline pendingTrampoline);
    }

    private android.companion.virtual.VirtualDeviceManager.ActivityListener createListenerAdapter() {
        return new android.companion.virtual.VirtualDeviceManager.ActivityListener() { // from class: com.android.server.companion.virtual.VirtualDeviceImpl.1
            public void onTopActivityChanged(int i, @android.annotation.NonNull android.content.ComponentName componentName) {
                try {
                    com.android.server.companion.virtual.VirtualDeviceImpl.this.mActivityListener.onTopActivityChanged(i, componentName, com.android.server.am.ProcessList.INVALID_ADJ);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.companion.virtual.VirtualDeviceImpl.TAG, "Unable to call mActivityListener", e);
                }
            }

            public void onTopActivityChanged(int i, @android.annotation.NonNull android.content.ComponentName componentName, int i2) {
                try {
                    com.android.server.companion.virtual.VirtualDeviceImpl.this.mActivityListener.onTopActivityChanged(i, componentName, i2);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.companion.virtual.VirtualDeviceImpl.TAG, "Unable to call mActivityListener", e);
                }
            }

            public void onDisplayEmpty(int i) {
                try {
                    com.android.server.companion.virtual.VirtualDeviceImpl.this.mActivityListener.onDisplayEmpty(i);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.companion.virtual.VirtualDeviceImpl.TAG, "Unable to call mActivityListener", e);
                }
            }
        };
    }

    VirtualDeviceImpl(android.content.Context context, android.companion.AssociationInfo associationInfo, com.android.server.companion.virtual.VirtualDeviceManagerService virtualDeviceManagerService, com.android.server.companion.virtual.VirtualDeviceLog virtualDeviceLog, android.os.IBinder iBinder, android.content.AttributionSource attributionSource, int i, com.android.server.companion.virtual.CameraAccessController cameraAccessController, com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampolineCallback pendingTrampolineCallback, android.companion.virtual.IVirtualDeviceActivityListener iVirtualDeviceActivityListener, android.companion.virtual.IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener, java.util.function.Consumer<android.util.ArraySet<java.lang.Integer>> consumer, android.companion.virtual.VirtualDeviceParams virtualDeviceParams) {
        this(context, associationInfo, virtualDeviceManagerService, virtualDeviceLog, iBinder, attributionSource, i, null, cameraAccessController, pendingTrampolineCallback, iVirtualDeviceActivityListener, iVirtualDeviceSoundEffectListener, consumer, virtualDeviceParams, android.hardware.display.DisplayManagerGlobal.getInstance(), android.companion.virtual.flags.Flags.virtualCamera() ? new com.android.server.companion.virtual.camera.VirtualCameraController(virtualDeviceParams.getDevicePolicy(5)) : null);
    }

    @com.android.internal.annotations.VisibleForTesting
    VirtualDeviceImpl(android.content.Context context, android.companion.AssociationInfo associationInfo, com.android.server.companion.virtual.VirtualDeviceManagerService virtualDeviceManagerService, com.android.server.companion.virtual.VirtualDeviceLog virtualDeviceLog, android.os.IBinder iBinder, android.content.AttributionSource attributionSource, int i, com.android.server.companion.virtual.InputController inputController, com.android.server.companion.virtual.CameraAccessController cameraAccessController, com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampolineCallback pendingTrampolineCallback, android.companion.virtual.IVirtualDeviceActivityListener iVirtualDeviceActivityListener, android.companion.virtual.IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener, java.util.function.Consumer<android.util.ArraySet<java.lang.Integer>> consumer, android.companion.virtual.VirtualDeviceParams virtualDeviceParams, android.hardware.display.DisplayManagerGlobal displayManagerGlobal, com.android.server.companion.virtual.camera.VirtualCameraController virtualCameraController) {
        super(android.os.PermissionEnforcer.fromContext(context));
        java.util.Set<android.content.ComponentName> allowedActivities;
        int i2;
        java.util.Set allowedActivities2;
        this.mVirtualDeviceLock = new java.lang.Object();
        this.mVirtualDisplays = new android.util.SparseArray<>();
        this.mIntentInterceptors = new android.util.ArrayMap();
        this.mDefaultShowPointerIcon = true;
        this.mLocaleList = null;
        this.mVirtualDeviceLog = virtualDeviceLog;
        this.mOwnerPackageName = attributionSource.getPackageName();
        this.mAttributionSource = attributionSource;
        this.mContext = context.createContextAsUser(android.os.UserHandle.getUserHandleForUid(attributionSource.getUid()), 0);
        this.mAssociationInfo = associationInfo;
        this.mPersistentDeviceId = createPersistentDeviceId(associationInfo.getId());
        this.mService = virtualDeviceManagerService;
        this.mPendingTrampolineCallback = pendingTrampolineCallback;
        this.mActivityListener = iVirtualDeviceActivityListener;
        this.mSoundEffectListener = iVirtualDeviceSoundEffectListener;
        this.mRunningAppsChangedCallback = consumer;
        this.mOwnerUid = attributionSource.getUid();
        this.mDeviceId = i;
        this.mAppToken = iBinder;
        this.mParams = virtualDeviceParams;
        this.mDevicePolicies = virtualDeviceParams.getDevicePolicies();
        this.mDisplayManager = displayManagerGlobal;
        this.mDisplayManagerInternal = (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
        if (inputController == null) {
            this.mInputController = new com.android.server.companion.virtual.InputController(context.getMainThreadHandler(), (android.view.WindowManager) context.getSystemService(android.view.WindowManager.class), this.mAttributionSource);
        } else {
            this.mInputController = inputController;
        }
        this.mSensorController = new com.android.server.companion.virtual.SensorController(this, this.mDeviceId, this.mAttributionSource, this.mParams.getVirtualSensorCallback(), this.mParams.getVirtualSensorConfigs());
        this.mCameraAccessController = cameraAccessController;
        if (this.mCameraAccessController != null) {
            this.mCameraAccessController.startObservingIfNeeded();
        }
        if (!android.companion.virtual.flags.Flags.streamPermissions()) {
            this.mPermissionDialogComponent = getPermissionDialogComponent();
        } else {
            this.mPermissionDialogComponent = null;
        }
        this.mVirtualCameraController = virtualCameraController;
        try {
            iBinder.linkToDeath(this, 0);
            this.mVirtualDeviceLog.logCreated(i, this.mOwnerUid);
            if (android.companion.virtual.flags.Flags.vdmPublicApis()) {
                this.mPublicVirtualDeviceObject = new android.companion.virtual.VirtualDevice(this, getDeviceId(), getPersistentDeviceId(), this.mParams.getName(), getDisplayName());
            } else {
                this.mPublicVirtualDeviceObject = new android.companion.virtual.VirtualDevice(this, getDeviceId(), getPersistentDeviceId(), this.mParams.getName());
            }
            if (android.companion.virtual.flags.Flags.dynamicPolicy()) {
                if (this.mParams.getDevicePolicy(3) == 0) {
                    allowedActivities2 = this.mParams.getBlockedActivities();
                } else {
                    allowedActivities2 = this.mParams.getAllowedActivities();
                }
                this.mActivityPolicyExemptions = new android.util.ArraySet(allowedActivities2);
            } else {
                if (this.mParams.getDefaultActivityPolicy() == 0) {
                    allowedActivities = this.mParams.getBlockedActivities();
                } else {
                    allowedActivities = this.mParams.getAllowedActivities();
                }
                this.mActivityPolicyExemptions = allowedActivities;
            }
            if (android.companion.virtual.flags.Flags.consistentDisplayFlags()) {
                i2 = DEFAULT_VIRTUAL_DISPLAY_FLAGS;
            } else {
                i2 = 25033;
            }
            this.mBaseVirtualDisplayFlags = this.mParams.getLockState() == 1 ? i2 | 4096 : i2;
            if (android.companion.virtual.flags.Flags.vdmCustomIme() && this.mParams.getInputMethodComponent() != null) {
                java.lang.String flattenToShortString = this.mParams.getInputMethodComponent().flattenToShortString();
                android.util.Slog.d(TAG, "Setting custom input method " + flattenToShortString + " as default for virtual device " + i);
                com.android.server.inputmethod.InputMethodManagerInternal.get().setVirtualDeviceInputMethodForAllUsers(this.mDeviceId, flattenToShortString);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.companion.virtual.SensorController getSensorControllerForTest() {
        return this.mSensorController;
    }

    static java.lang.String createPersistentDeviceId(int i) {
        return PERSISTENT_ID_PREFIX_CDM_ASSOCIATION + i;
    }

    int getBaseVirtualDisplayFlags() {
        return this.mBaseVirtualDisplayFlags;
    }

    com.android.server.companion.virtual.CameraAccessController getCameraAccessController() {
        return this.mCameraAccessController;
    }

    java.lang.CharSequence getDisplayName() {
        return this.mAssociationInfo.getDisplayName();
    }

    android.companion.virtual.VirtualDevice getPublicVirtualDeviceObject() {
        return this.mPublicVirtualDeviceObject;
    }

    android.os.LocaleList getDeviceLocaleList() {
        android.os.LocaleList localeList;
        synchronized (this.mVirtualDeviceLock) {
            localeList = this.mLocaleList;
        }
        return localeList;
    }

    public int getDevicePolicy(int i) {
        int i2;
        if (android.companion.virtual.flags.Flags.dynamicPolicy()) {
            synchronized (this.mVirtualDeviceLock) {
                i2 = this.mDevicePolicies.get(i, 0);
            }
            return i2;
        }
        return this.mParams.getDevicePolicy(i);
    }

    public int getAudioPlaybackSessionId() {
        return this.mParams.getAudioPlaybackSessionId();
    }

    public int getAudioRecordingSessionId() {
        return this.mParams.getAudioRecordingSessionId();
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    @android.annotation.Nullable
    public java.lang.String getPersistentDeviceId() {
        return this.mPersistentDeviceId;
    }

    public int getAssociationId() {
        return this.mAssociationInfo.getId();
    }

    public void launchPendingIntent(int i, android.app.PendingIntent pendingIntent, android.os.ResultReceiver resultReceiver) {
        java.util.Objects.requireNonNull(pendingIntent);
        synchronized (this.mVirtualDeviceLock) {
            if (!this.mVirtualDisplays.contains(i)) {
                throw new java.lang.SecurityException("Display ID " + i + " not found for this virtual device");
            }
        }
        if (pendingIntent.isActivity()) {
            try {
                sendPendingIntent(i, pendingIntent);
                resultReceiver.send(0, null);
                return;
            } catch (android.app.PendingIntent.CanceledException e) {
                android.util.Slog.w(TAG, "Pending intent canceled", e);
                resultReceiver.send(1, null);
                return;
            }
        }
        final com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline pendingTrampoline = new com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline(pendingIntent, resultReceiver, i);
        this.mPendingTrampolineCallback.startWaitingForPendingTrampoline(pendingTrampoline);
        this.mContext.getMainThreadHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.companion.virtual.VirtualDeviceImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.companion.virtual.VirtualDeviceImpl.this.lambda$launchPendingIntent$0(pendingTrampoline);
            }
        }, PENDING_TRAMPOLINE_TIMEOUT_MS);
        try {
            sendPendingIntent(i, pendingIntent);
        } catch (android.app.PendingIntent.CanceledException e2) {
            android.util.Slog.w(TAG, "Pending intent canceled", e2);
            resultReceiver.send(1, null);
            this.mPendingTrampolineCallback.stopWaitingForPendingTrampoline(pendingTrampoline);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$launchPendingIntent$0(com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline pendingTrampoline) {
        pendingTrampoline.mResultReceiver.send(2, null);
        this.mPendingTrampolineCallback.stopWaitingForPendingTrampoline(pendingTrampoline);
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void addActivityPolicyExemption(@android.annotation.NonNull android.content.ComponentName componentName) {
        super.addActivityPolicyExemption_enforcePermission();
        synchronized (this.mVirtualDeviceLock) {
            try {
                if (this.mActivityPolicyExemptions.add(componentName)) {
                    for (int i = 0; i < this.mVirtualDisplays.size(); i++) {
                        this.mVirtualDisplays.valueAt(i).getWindowPolicyController().addActivityPolicyExemption(componentName);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void removeActivityPolicyExemption(@android.annotation.NonNull android.content.ComponentName componentName) {
        super.removeActivityPolicyExemption_enforcePermission();
        synchronized (this.mVirtualDeviceLock) {
            try {
                if (this.mActivityPolicyExemptions.remove(componentName)) {
                    for (int i = 0; i < this.mVirtualDisplays.size(); i++) {
                        this.mVirtualDisplays.valueAt(i).getWindowPolicyController().removeActivityPolicyExemption(componentName);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void sendPendingIntent(int i, android.app.PendingIntent pendingIntent) throws android.app.PendingIntent.CanceledException {
        android.app.ActivityOptions launchDisplayId = android.app.ActivityOptions.makeBasic().setLaunchDisplayId(i);
        launchDisplayId.setPendingIntentBackgroundActivityLaunchAllowed(true);
        launchDisplayId.setPendingIntentBackgroundActivityLaunchAllowedByPermission(true);
        pendingIntent.send(this.mContext, 0, null, null, null, null, launchDisplayId.toBundle());
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void close() {
        int size;
        com.android.server.companion.virtual.VirtualDeviceImpl.VirtualDisplayWrapper[] virtualDisplayWrapperArr;
        super.close_enforcePermission();
        if (!this.mService.removeVirtualDevice(this.mDeviceId)) {
            return;
        }
        this.mVirtualDeviceLog.logClosed(this.mDeviceId, this.mOwnerUid);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mVirtualDeviceLock) {
                try {
                    if (this.mVirtualAudioController != null) {
                        this.mVirtualAudioController.stopListening();
                        this.mVirtualAudioController = null;
                    }
                    this.mLocaleList = null;
                    size = this.mVirtualDisplays.size();
                    virtualDisplayWrapperArr = new com.android.server.companion.virtual.VirtualDeviceImpl.VirtualDisplayWrapper[size];
                    for (int i = 0; i < this.mVirtualDisplays.size(); i++) {
                        virtualDisplayWrapperArr[i] = this.mVirtualDisplays.valueAt(i);
                    }
                    this.mVirtualDisplays.clear();
                } finally {
                }
            }
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.companion.virtual.VirtualDeviceImpl.VirtualDisplayWrapper virtualDisplayWrapper = virtualDisplayWrapperArr[i2];
                this.mDisplayManager.releaseVirtualDisplay(virtualDisplayWrapper.getToken());
                releaseOwnedVirtualDisplayResources(virtualDisplayWrapper);
            }
            this.mAppToken.unlinkToDeath(this, 0);
            if (this.mCameraAccessController != null) {
                this.mCameraAccessController.stopObservingIfNeeded();
            }
            if (android.companion.virtual.flags.Flags.vdmCustomIme() && this.mParams.getInputMethodComponent() != null) {
                com.android.server.inputmethod.InputMethodManagerInternal.get().setVirtualDeviceInputMethodForAllUsers(this.mDeviceId, null);
            }
            this.mInputController.close();
            this.mSensorController.close();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            if (this.mVirtualCameraController != null) {
                this.mVirtualCameraController.close();
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        close();
    }

    @Override // com.android.server.companion.virtual.GenericWindowPolicyController.RunningAppsChangedListener
    @android.annotation.RequiresPermission("android.permission.CAMERA_INJECT_EXTERNAL_CAMERA")
    public void onRunningAppsChanged(android.util.ArraySet<java.lang.Integer> arraySet) {
        if (this.mCameraAccessController != null) {
            this.mCameraAccessController.blockCameraAccessIfNeeded(arraySet);
        }
        this.mRunningAppsChangedCallback.accept(arraySet);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.companion.virtual.audio.VirtualAudioController getVirtualAudioControllerForTesting() {
        return this.mVirtualAudioController;
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void onAudioSessionStarting(int i, @android.annotation.NonNull android.companion.virtual.audio.IAudioRoutingCallback iAudioRoutingCallback, @android.annotation.Nullable android.companion.virtual.audio.IAudioConfigChangedCallback iAudioConfigChangedCallback) {
        super.onAudioSessionStarting_enforcePermission();
        synchronized (this.mVirtualDeviceLock) {
            try {
                if (!this.mVirtualDisplays.contains(i)) {
                    throw new java.lang.SecurityException("Cannot start audio session for a display not associated with this virtual device");
                }
                if (this.mVirtualAudioController == null) {
                    this.mVirtualAudioController = new com.android.server.companion.virtual.audio.VirtualAudioController(this.mContext, this.mAttributionSource);
                    this.mVirtualAudioController.startListening(this.mVirtualDisplays.get(i).getWindowPolicyController(), iAudioRoutingCallback, iAudioConfigChangedCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void onAudioSessionEnded() {
        super.onAudioSessionEnded_enforcePermission();
        synchronized (this.mVirtualDeviceLock) {
            try {
                if (this.mVirtualAudioController != null) {
                    this.mVirtualAudioController.stopListening();
                    this.mVirtualAudioController = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void setDevicePolicy(int i, int i2) {
        super.setDevicePolicy_enforcePermission();
        if (!android.companion.virtual.flags.Flags.dynamicPolicy()) {
            return;
        }
        switch (i) {
            case 2:
                synchronized (this.mVirtualDeviceLock) {
                    try {
                        this.mDevicePolicies.put(i, i2);
                        for (int i3 = 0; i3 < this.mVirtualDisplays.size(); i3++) {
                            this.mVirtualDisplays.valueAt(i3).getWindowPolicyController().setShowInHostDeviceRecents(i2 == 0);
                        }
                    } finally {
                    }
                }
                return;
            case 3:
                synchronized (this.mVirtualDeviceLock) {
                    try {
                        this.mDevicePolicies.put(i, i2);
                        for (int i4 = 0; i4 < this.mVirtualDisplays.size(); i4++) {
                            this.mVirtualDisplays.valueAt(i4).getWindowPolicyController().setActivityLaunchDefaultAllowed(i2 == 0);
                        }
                    } finally {
                    }
                }
                return;
            case 4:
                if (android.companion.virtual.flags.Flags.crossDeviceClipboard()) {
                    synchronized (this.mVirtualDeviceLock) {
                        this.mDevicePolicies.put(i, i2);
                    }
                    return;
                }
                return;
            default:
                throw new java.lang.IllegalArgumentException("Device policy " + i + " cannot be changed at runtime. ");
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void createVirtualDpad(android.hardware.input.VirtualDpadConfig virtualDpadConfig, @android.annotation.NonNull android.os.IBinder iBinder) {
        super.createVirtualDpad_enforcePermission();
        java.util.Objects.requireNonNull(virtualDpadConfig);
        checkVirtualInputDeviceDisplayIdAssociation(virtualDpadConfig.getAssociatedDisplayId());
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mInputController.createDpad(virtualDpadConfig.getInputDeviceName(), virtualDpadConfig.getVendorId(), virtualDpadConfig.getProductId(), iBinder, getTargetDisplayIdForInput(virtualDpadConfig.getAssociatedDisplayId()));
            } catch (com.android.server.companion.virtual.InputController.DeviceCreationException e) {
                throw new java.lang.IllegalArgumentException(e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void createVirtualKeyboard(android.hardware.input.VirtualKeyboardConfig virtualKeyboardConfig, @android.annotation.NonNull android.os.IBinder iBinder) {
        super.createVirtualKeyboard_enforcePermission();
        java.util.Objects.requireNonNull(virtualKeyboardConfig);
        checkVirtualInputDeviceDisplayIdAssociation(virtualKeyboardConfig.getAssociatedDisplayId());
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mInputController.createKeyboard(virtualKeyboardConfig.getInputDeviceName(), virtualKeyboardConfig.getVendorId(), virtualKeyboardConfig.getProductId(), iBinder, getTargetDisplayIdForInput(virtualKeyboardConfig.getAssociatedDisplayId()), virtualKeyboardConfig.getLanguageTag(), virtualKeyboardConfig.getLayoutType());
                synchronized (this.mVirtualDeviceLock) {
                    this.mLocaleList = android.os.LocaleList.forLanguageTags(virtualKeyboardConfig.getLanguageTag());
                }
            } catch (com.android.server.companion.virtual.InputController.DeviceCreationException e) {
                throw new java.lang.IllegalArgumentException(e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void createVirtualMouse(android.hardware.input.VirtualMouseConfig virtualMouseConfig, @android.annotation.NonNull android.os.IBinder iBinder) {
        super.createVirtualMouse_enforcePermission();
        java.util.Objects.requireNonNull(virtualMouseConfig);
        checkVirtualInputDeviceDisplayIdAssociation(virtualMouseConfig.getAssociatedDisplayId());
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mInputController.createMouse(virtualMouseConfig.getInputDeviceName(), virtualMouseConfig.getVendorId(), virtualMouseConfig.getProductId(), iBinder, virtualMouseConfig.getAssociatedDisplayId());
            } catch (com.android.server.companion.virtual.InputController.DeviceCreationException e) {
                throw new java.lang.IllegalArgumentException(e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void createVirtualTouchscreen(android.hardware.input.VirtualTouchscreenConfig virtualTouchscreenConfig, @android.annotation.NonNull android.os.IBinder iBinder) {
        super.createVirtualTouchscreen_enforcePermission();
        java.util.Objects.requireNonNull(virtualTouchscreenConfig);
        checkVirtualInputDeviceDisplayIdAssociation(virtualTouchscreenConfig.getAssociatedDisplayId());
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mInputController.createTouchscreen(virtualTouchscreenConfig.getInputDeviceName(), virtualTouchscreenConfig.getVendorId(), virtualTouchscreenConfig.getProductId(), iBinder, virtualTouchscreenConfig.getAssociatedDisplayId(), virtualTouchscreenConfig.getHeight(), virtualTouchscreenConfig.getWidth());
            } catch (com.android.server.companion.virtual.InputController.DeviceCreationException e) {
                throw new java.lang.IllegalArgumentException(e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void createVirtualNavigationTouchpad(android.hardware.input.VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig, @android.annotation.NonNull android.os.IBinder iBinder) {
        super.createVirtualNavigationTouchpad_enforcePermission();
        java.util.Objects.requireNonNull(virtualNavigationTouchpadConfig);
        checkVirtualInputDeviceDisplayIdAssociation(virtualNavigationTouchpadConfig.getAssociatedDisplayId());
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mInputController.createNavigationTouchpad(virtualNavigationTouchpadConfig.getInputDeviceName(), virtualNavigationTouchpadConfig.getVendorId(), virtualNavigationTouchpadConfig.getProductId(), iBinder, getTargetDisplayIdForInput(virtualNavigationTouchpadConfig.getAssociatedDisplayId()), virtualNavigationTouchpadConfig.getHeight(), virtualNavigationTouchpadConfig.getWidth());
            } catch (com.android.server.companion.virtual.InputController.DeviceCreationException e) {
                throw new java.lang.IllegalArgumentException(e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void createVirtualStylus(@android.annotation.NonNull android.hardware.input.VirtualStylusConfig virtualStylusConfig, @android.annotation.NonNull android.os.IBinder iBinder) {
        super.createVirtualStylus_enforcePermission();
        java.util.Objects.requireNonNull(virtualStylusConfig);
        java.util.Objects.requireNonNull(iBinder);
        checkVirtualInputDeviceDisplayIdAssociation(virtualStylusConfig.getAssociatedDisplayId());
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mInputController.createStylus(virtualStylusConfig.getInputDeviceName(), virtualStylusConfig.getVendorId(), virtualStylusConfig.getProductId(), iBinder, virtualStylusConfig.getAssociatedDisplayId(), virtualStylusConfig.getHeight(), virtualStylusConfig.getWidth());
            } catch (com.android.server.companion.virtual.InputController.DeviceCreationException e) {
                throw new java.lang.IllegalArgumentException(e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void unregisterInputDevice(android.os.IBinder iBinder) {
        super.unregisterInputDevice_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mInputController.unregisterInputDevice(iBinder);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getInputDeviceId(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mInputController.getInputDeviceId(iBinder);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public boolean sendDpadKeyEvent(android.os.IBinder iBinder, android.hardware.input.VirtualKeyEvent virtualKeyEvent) {
        super.sendDpadKeyEvent_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendDpadKeyEvent(iBinder, virtualKeyEvent);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public boolean sendKeyEvent(android.os.IBinder iBinder, android.hardware.input.VirtualKeyEvent virtualKeyEvent) {
        super.sendKeyEvent_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendKeyEvent(iBinder, virtualKeyEvent);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public boolean sendButtonEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseButtonEvent virtualMouseButtonEvent) {
        super.sendButtonEvent_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendButtonEvent(iBinder, virtualMouseButtonEvent);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public boolean sendTouchEvent(android.os.IBinder iBinder, android.hardware.input.VirtualTouchEvent virtualTouchEvent) {
        super.sendTouchEvent_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendTouchEvent(iBinder, virtualTouchEvent);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public boolean sendRelativeEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseRelativeEvent virtualMouseRelativeEvent) {
        super.sendRelativeEvent_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendRelativeEvent(iBinder, virtualMouseRelativeEvent);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public boolean sendScrollEvent(android.os.IBinder iBinder, android.hardware.input.VirtualMouseScrollEvent virtualMouseScrollEvent) {
        super.sendScrollEvent_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendScrollEvent(iBinder, virtualMouseScrollEvent);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.graphics.PointF getCursorPosition(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mInputController.getCursorPosition(iBinder);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public boolean sendStylusMotionEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.input.VirtualStylusMotionEvent virtualStylusMotionEvent) {
        super.sendStylusMotionEvent_enforcePermission();
        java.util.Objects.requireNonNull(iBinder);
        java.util.Objects.requireNonNull(virtualStylusMotionEvent);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendStylusMotionEvent(iBinder, virtualStylusMotionEvent);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public boolean sendStylusButtonEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.input.VirtualStylusButtonEvent virtualStylusButtonEvent) {
        super.sendStylusButtonEvent_enforcePermission();
        java.util.Objects.requireNonNull(iBinder);
        java.util.Objects.requireNonNull(virtualStylusButtonEvent);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendStylusButtonEvent(iBinder, virtualStylusButtonEvent);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void setShowPointerIcon(boolean z) {
        super.setShowPointerIcon_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mVirtualDeviceLock) {
                this.mDefaultShowPointerIcon = z;
            }
            for (int i : getDisplayIds()) {
                this.mInputController.setShowPointerIcon(z, i);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void setDisplayImePolicy(int i, int i2) {
        super.setDisplayImePolicy_enforcePermission();
        synchronized (this.mVirtualDeviceLock) {
            if (!this.mVirtualDisplays.contains(i)) {
                throw new java.lang.SecurityException("Display ID " + i + " not found for this virtual device");
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mInputController.setDisplayImePolicy(i, i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    @android.annotation.Nullable
    public java.util.List<android.companion.virtual.sensor.VirtualSensor> getVirtualSensorList() {
        super.getVirtualSensorList_enforcePermission();
        return this.mSensorController.getSensorList();
    }

    @android.annotation.Nullable
    android.companion.virtual.sensor.VirtualSensor getVirtualSensorByHandle(int i) {
        return this.mSensorController.getSensorByHandle(i);
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public boolean sendSensorEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.companion.virtual.sensor.VirtualSensorEvent virtualSensorEvent) {
        super.sendSensorEvent_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mSensorController.sendSensorEvent(iBinder, virtualSensorEvent);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void registerIntentInterceptor(android.companion.virtual.IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor, android.content.IntentFilter intentFilter) {
        super.registerIntentInterceptor_enforcePermission();
        java.util.Objects.requireNonNull(iVirtualDeviceIntentInterceptor);
        java.util.Objects.requireNonNull(intentFilter);
        synchronized (this.mVirtualDeviceLock) {
            this.mIntentInterceptors.put(iVirtualDeviceIntentInterceptor.asBinder(), intentFilter);
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void unregisterIntentInterceptor(@android.annotation.NonNull android.companion.virtual.IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor) {
        super.unregisterIntentInterceptor_enforcePermission();
        java.util.Objects.requireNonNull(iVirtualDeviceIntentInterceptor);
        synchronized (this.mVirtualDeviceLock) {
            this.mIntentInterceptors.remove(iVirtualDeviceIntentInterceptor.asBinder());
        }
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void registerVirtualCamera(@android.annotation.NonNull android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException {
        super.registerVirtualCamera_enforcePermission();
        java.util.Objects.requireNonNull(virtualCameraConfig);
        if (this.mVirtualCameraController == null) {
            throw new java.lang.UnsupportedOperationException("Virtual camera controller is not available");
        }
        this.mVirtualCameraController.registerCamera(virtualCameraConfig, this.mAttributionSource);
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void unregisterVirtualCamera(@android.annotation.NonNull android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException {
        super.unregisterVirtualCamera_enforcePermission();
        java.util.Objects.requireNonNull(virtualCameraConfig);
        if (this.mVirtualCameraController == null) {
            throw new java.lang.UnsupportedOperationException("Virtual camera controller is not available");
        }
        this.mVirtualCameraController.unregisterCamera(virtualCameraConfig);
    }

    @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public int getVirtualCameraId(@android.annotation.NonNull android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException {
        super.getVirtualCameraId_enforcePermission();
        java.util.Objects.requireNonNull(virtualCameraConfig);
        if (this.mVirtualCameraController == null) {
            throw new java.lang.UnsupportedOperationException("Virtual camera controller is not available");
        }
        return this.mVirtualCameraController.getCameraId(virtualCameraConfig);
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println("  VirtualDevice: ");
        printWriter.println("    mDeviceId: " + this.mDeviceId);
        printWriter.println("    mAssociationId: " + this.mAssociationInfo.getId());
        printWriter.println("    mOwnerPackageName: " + this.mOwnerPackageName);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("    ");
        sb.append("mParams: ");
        printWriter.println(sb.toString());
        this.mParams.dump(printWriter, "        ");
        printWriter.println("    mVirtualDisplayIds: ");
        synchronized (this.mVirtualDeviceLock) {
            try {
                printWriter.println("    mDevicePolicies: " + this.mDevicePolicies);
                for (int i = 0; i < this.mVirtualDisplays.size(); i++) {
                    printWriter.println("      " + this.mVirtualDisplays.keyAt(i));
                }
                printWriter.println("    mDefaultShowPointerIcon: " + this.mDefaultShowPointerIcon);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mInputController.dump(printWriter);
        this.mSensorController.dump(printWriter);
        if (this.mVirtualCameraController != null) {
            this.mVirtualCameraController.dump(printWriter, "    ");
        }
    }

    private int getTargetDisplayIdForInput(int i) {
        if (!android.companion.virtual.flags.Flags.interactiveScreenMirror()) {
            return i;
        }
        int displayIdToMirror = ((android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class)).getDisplayIdToMirror(i);
        return displayIdToMirror == -1 ? i : displayIdToMirror;
    }

    @com.android.internal.annotations.GuardedBy({"mVirtualDeviceLock"})
    private com.android.server.companion.virtual.GenericWindowPolicyController createWindowPolicyControllerLocked(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        boolean z;
        java.util.Set allowedCrossTaskNavigations;
        if (android.companion.virtual.flags.Flags.dynamicPolicy()) {
            z = getDevicePolicy(3) == 0;
        } else {
            z = this.mParams.getDefaultActivityPolicy() == 0;
        }
        boolean z2 = this.mParams.getDefaultNavigationPolicy() == 0;
        boolean z3 = getDevicePolicy(2) == 0;
        android.content.ComponentName homeComponent = android.companion.virtual.flags.Flags.vdmCustomHome() ? this.mParams.getHomeComponent() : null;
        android.content.AttributionSource attributionSource = this.mAttributionSource;
        android.util.ArraySet<android.os.UserHandle> allowedUserHandles = getAllowedUserHandles();
        java.util.Set<android.content.ComponentName> set2 = this.mActivityPolicyExemptions;
        if (z2) {
            allowedCrossTaskNavigations = this.mParams.getBlockedCrossTaskNavigations();
        } else {
            allowedCrossTaskNavigations = this.mParams.getAllowedCrossTaskNavigations();
        }
        com.android.server.companion.virtual.GenericWindowPolicyController genericWindowPolicyController = new com.android.server.companion.virtual.GenericWindowPolicyController(8192, 524288, attributionSource, allowedUserHandles, z, set2, z2, allowedCrossTaskNavigations, this.mPermissionDialogComponent, createListenerAdapter(), new com.android.server.companion.virtual.GenericWindowPolicyController.PipBlockedCallback() { // from class: com.android.server.companion.virtual.VirtualDeviceImpl$$ExternalSyntheticLambda2
            @Override // com.android.server.companion.virtual.GenericWindowPolicyController.PipBlockedCallback
            public final void onEnteringPipBlocked(int i) {
                com.android.server.companion.virtual.VirtualDeviceImpl.this.onEnteringPipBlocked(i);
            }
        }, new com.android.server.companion.virtual.GenericWindowPolicyController.ActivityBlockedCallback() { // from class: com.android.server.companion.virtual.VirtualDeviceImpl$$ExternalSyntheticLambda3
            @Override // com.android.server.companion.virtual.GenericWindowPolicyController.ActivityBlockedCallback
            public final void onActivityBlocked(int i, android.content.pm.ActivityInfo activityInfo) {
                com.android.server.companion.virtual.VirtualDeviceImpl.this.onActivityBlocked(i, activityInfo);
            }
        }, new com.android.server.companion.virtual.GenericWindowPolicyController.SecureWindowCallback() { // from class: com.android.server.companion.virtual.VirtualDeviceImpl$$ExternalSyntheticLambda4
            @Override // com.android.server.companion.virtual.GenericWindowPolicyController.SecureWindowCallback
            public final void onSecureWindowShown(int i, int i2) {
                com.android.server.companion.virtual.VirtualDeviceImpl.this.onSecureWindowShown(i, i2);
            }
        }, new com.android.server.companion.virtual.GenericWindowPolicyController.IntentListenerCallback() { // from class: com.android.server.companion.virtual.VirtualDeviceImpl$$ExternalSyntheticLambda5
            @Override // com.android.server.companion.virtual.GenericWindowPolicyController.IntentListenerCallback
            public final boolean shouldInterceptIntent(android.content.Intent intent) {
                boolean shouldInterceptIntent;
                shouldInterceptIntent = com.android.server.companion.virtual.VirtualDeviceImpl.this.shouldInterceptIntent(intent);
                return shouldInterceptIntent;
            }
        }, set, z3, homeComponent);
        genericWindowPolicyController.registerRunningAppsChangedListener(this);
        return genericWindowPolicyController;
    }

    private android.content.ComponentName getPermissionDialogComponent() {
        android.content.Intent intent = new android.content.Intent("android.content.pm.action.REQUEST_PERMISSIONS");
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        intent.setPackage(packageManager.getPermissionControllerPackageName());
        return intent.resolveActivity(packageManager);
    }

    int createVirtualDisplay(@android.annotation.NonNull android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, @android.annotation.NonNull android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, java.lang.String str) {
        com.android.server.companion.virtual.GenericWindowPolicyController createWindowPolicyControllerLocked;
        boolean z;
        synchronized (this.mVirtualDeviceLock) {
            createWindowPolicyControllerLocked = createWindowPolicyControllerLocked(virtualDisplayConfig.getDisplayCategories());
        }
        int createVirtualDisplay = this.mDisplayManagerInternal.createVirtualDisplay(virtualDisplayConfig, iVirtualDisplayCallback, this, createWindowPolicyControllerLocked, str);
        createWindowPolicyControllerLocked.setDisplayId(createVirtualDisplay, android.companion.virtual.flags.Flags.interactiveScreenMirror() && this.mDisplayManagerInternal.getDisplayIdToMirror(createVirtualDisplay) != -1);
        synchronized (this.mVirtualDeviceLock) {
            if (this.mVirtualDisplays.contains(createVirtualDisplay)) {
                createWindowPolicyControllerLocked.unregisterRunningAppsChangedListener(this);
                throw new java.lang.IllegalStateException("Virtual device already has a virtual display with ID " + createVirtualDisplay);
            }
            this.mVirtualDisplays.put(createVirtualDisplay, new com.android.server.companion.virtual.VirtualDeviceImpl.VirtualDisplayWrapper(iVirtualDisplayCallback, createWindowPolicyControllerLocked, createAndAcquireWakeLockForDisplay(createVirtualDisplay)));
            z = this.mDefaultShowPointerIcon;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mInputController.setShowPointerIcon(z, createVirtualDisplay);
            this.mInputController.setMousePointerAccelerationEnabled(false, createVirtualDisplay);
            this.mInputController.setDisplayEligibilityForPointerCapture(false, createVirtualDisplay);
            if ((this.mDisplayManagerInternal.getDisplayInfo(createVirtualDisplay).flags & 128) == 128) {
                this.mInputController.setDisplayImePolicy(createVirtualDisplay, 0);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
                com.android.modules.expresslog.Counter.logIncrementWithUid("virtual_devices.value_virtual_display_created_count", this.mAttributionSource.getUid());
            }
            return createVirtualDisplay;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private android.os.PowerManager.WakeLock createAndAcquireWakeLockForDisplay(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.os.PowerManager.WakeLock newWakeLock = ((android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class)).newWakeLock(10, "VirtualDeviceImpl:" + i, i);
            newWakeLock.acquire();
            return newWakeLock;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS")
    public void onActivityBlocked(int i, android.content.pm.ActivityInfo activityInfo) {
        this.mContext.startActivityAsUser(com.android.internal.app.BlockedAppStreamingActivity.createIntent(activityInfo, this.mAssociationInfo.getDisplayName()).addFlags(268468224), android.app.ActivityOptions.makeBasic().setLaunchDisplayId(i).toBundle(), android.os.UserHandle.SYSTEM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSecureWindowShown(int i, int i2) {
        synchronized (this.mVirtualDeviceLock) {
            try {
                if (this.mVirtualDisplays.contains(i)) {
                    if ((((android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class)).getDisplay(i).getFlags() & 8192) == 0) {
                        showToastWhereUidIsRunning(i2, android.R.string.upload_file, 1, this.mContext.getMainLooper());
                        if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
                            com.android.modules.expresslog.Counter.logIncrementWithUid("virtual_devices.value_secure_window_blocked_count", this.mAttributionSource.getUid());
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.util.ArraySet<android.os.UserHandle> getAllowedUserHandles() {
        android.util.ArraySet<android.os.UserHandle> arraySet = new android.util.ArraySet<>();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.admin.DevicePolicyManager devicePolicyManager = (android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class);
            for (android.os.UserHandle userHandle : ((android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)).getAllProfiles()) {
                int nearbyAppStreamingPolicy = devicePolicyManager.getNearbyAppStreamingPolicy(userHandle.getIdentifier());
                if (nearbyAppStreamingPolicy == 2 || nearbyAppStreamingPolicy == 0) {
                    arraySet.add(userHandle);
                } else if (nearbyAppStreamingPolicy == 3 && this.mParams.getUsersWithMatchingAccounts().contains(userHandle)) {
                    arraySet.add(userHandle);
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return arraySet;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    void onVirtualDisplayRemoved(int i) {
        com.android.server.companion.virtual.VirtualDeviceImpl.VirtualDisplayWrapper virtualDisplayWrapper;
        synchronized (this.mVirtualDeviceLock) {
            virtualDisplayWrapper = (com.android.server.companion.virtual.VirtualDeviceImpl.VirtualDisplayWrapper) this.mVirtualDisplays.removeReturnOld(i);
        }
        if (virtualDisplayWrapper == null) {
            android.util.Slog.w(TAG, "Virtual device " + this.mDeviceId + " doesn't have a virtual display with ID " + i);
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            releaseOwnedVirtualDisplayResources(virtualDisplayWrapper);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void checkVirtualInputDeviceDisplayIdAssociation(int i) {
        if (this.mContext.checkCallingPermission("android.permission.INJECT_EVENTS") == 0) {
            return;
        }
        synchronized (this.mVirtualDeviceLock) {
            try {
                if (!this.mVirtualDisplays.contains(i)) {
                    throw new java.lang.SecurityException("Cannot create a virtual input device for display " + i + " which not associated with this virtual device");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void releaseOwnedVirtualDisplayResources(com.android.server.companion.virtual.VirtualDeviceImpl.VirtualDisplayWrapper virtualDisplayWrapper) {
        virtualDisplayWrapper.getWakeLock().release();
        virtualDisplayWrapper.getWindowPolicyController().unregisterRunningAppsChangedListener(this);
    }

    int getOwnerUid() {
        return this.mOwnerUid;
    }

    public int[] getDisplayIds() {
        int[] iArr;
        synchronized (this.mVirtualDeviceLock) {
            try {
                int size = this.mVirtualDisplays.size();
                iArr = new int[size];
                for (int i = 0; i < size; i++) {
                    iArr[i] = this.mVirtualDisplays.keyAt(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return iArr;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.companion.virtual.GenericWindowPolicyController getDisplayWindowPolicyControllerForTest(int i) {
        com.android.server.companion.virtual.VirtualDeviceImpl.VirtualDisplayWrapper virtualDisplayWrapper;
        synchronized (this.mVirtualDeviceLock) {
            virtualDisplayWrapper = this.mVirtualDisplays.get(i);
        }
        if (virtualDisplayWrapper != null) {
            return virtualDisplayWrapper.getWindowPolicyController();
        }
        return null;
    }

    boolean isAppRunningOnVirtualDevice(int i) {
        synchronized (this.mVirtualDeviceLock) {
            for (int i2 = 0; i2 < this.mVirtualDisplays.size(); i2++) {
                try {
                    if (this.mVirtualDisplays.valueAt(i2).getWindowPolicyController().containsUid(i)) {
                        return true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return false;
        }
    }

    void showToastWhereUidIsRunning(int i, int i2, int i3, android.os.Looper looper) {
        showToastWhereUidIsRunning(i, this.mContext.getString(i2), i3, looper);
    }

    void showToastWhereUidIsRunning(int i, java.lang.String str, int i2, android.os.Looper looper) {
        android.util.IntArray displayIdsWhereUidIsRunning = getDisplayIdsWhereUidIsRunning(i);
        if (displayIdsWhereUidIsRunning.size() == 0) {
            return;
        }
        android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
        for (int i3 = 0; i3 < displayIdsWhereUidIsRunning.size(); i3++) {
            android.view.Display display = displayManager.getDisplay(displayIdsWhereUidIsRunning.get(i3));
            if (display != null && display.isValid()) {
                android.widget.Toast.makeText(this.mContext.createDisplayContext(display), looper, str, i2).show();
            }
        }
    }

    private android.util.IntArray getDisplayIdsWhereUidIsRunning(int i) {
        android.util.IntArray intArray = new android.util.IntArray();
        synchronized (this.mVirtualDeviceLock) {
            for (int i2 = 0; i2 < this.mVirtualDisplays.size(); i2++) {
                try {
                    if (this.mVirtualDisplays.valueAt(i2).getWindowPolicyController().containsUid(i)) {
                        intArray.add(this.mVirtualDisplays.keyAt(i2));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return intArray;
    }

    boolean isDisplayOwnedByVirtualDevice(int i) {
        boolean contains;
        synchronized (this.mVirtualDeviceLock) {
            contains = this.mVirtualDisplays.contains(i);
        }
        return contains;
    }

    boolean isInputDeviceOwnedByVirtualDevice(final int i) {
        return this.mInputController.getInputDeviceDescriptors().values().stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.companion.virtual.VirtualDeviceImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isInputDeviceOwnedByVirtualDevice$1;
                lambda$isInputDeviceOwnedByVirtualDevice$1 = com.android.server.companion.virtual.VirtualDeviceImpl.lambda$isInputDeviceOwnedByVirtualDevice$1(i, (com.android.server.companion.virtual.InputController.InputDeviceDescriptor) obj);
                return lambda$isInputDeviceOwnedByVirtualDevice$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isInputDeviceOwnedByVirtualDevice$1(int i, com.android.server.companion.virtual.InputController.InputDeviceDescriptor inputDeviceDescriptor) {
        return inputDeviceDescriptor.getInputDeviceId() == i;
    }

    void onEnteringPipBlocked(int i) {
    }

    void playSoundEffect(int i) {
        try {
            this.mSoundEffectListener.onPlaySoundEffect(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Unable to invoke sound effect listener", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldInterceptIntent(android.content.Intent intent) {
        boolean z;
        synchronized (this.mVirtualDeviceLock) {
            z = false;
            for (java.util.Map.Entry<android.os.IBinder, android.content.IntentFilter> entry : this.mIntentInterceptors.entrySet()) {
                if (entry.getValue().match(intent.getAction(), intent.getType(), intent.getScheme(), intent.getData(), intent.getCategories(), TAG) >= 0) {
                    try {
                        android.companion.virtual.IVirtualDeviceIntentInterceptor.Stub.asInterface(entry.getKey()).onIntentIntercepted(new android.content.Intent(intent.getAction(), intent.getData()));
                        z = true;
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Unable to call mVirtualDeviceIntentInterceptor", e);
                    }
                }
            }
        }
        return z;
    }

    static class PendingTrampoline {
        final int mDisplayId;
        final android.app.PendingIntent mPendingIntent;
        final android.os.ResultReceiver mResultReceiver;

        private PendingTrampoline(android.app.PendingIntent pendingIntent, android.os.ResultReceiver resultReceiver, int i) {
            this.mPendingIntent = pendingIntent;
            this.mResultReceiver = resultReceiver;
            this.mDisplayId = i;
        }

        public java.lang.String toString() {
            return "PendingTrampoline{pendingIntent=" + this.mPendingIntent + ", resultReceiver=" + this.mResultReceiver + ", displayId=" + this.mDisplayId + "}";
        }
    }

    private static final class VirtualDisplayWrapper {
        private final android.hardware.display.IVirtualDisplayCallback mToken;
        private final android.os.PowerManager.WakeLock mWakeLock;
        private final com.android.server.companion.virtual.GenericWindowPolicyController mWindowPolicyController;

        VirtualDisplayWrapper(@android.annotation.NonNull android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, @android.annotation.NonNull com.android.server.companion.virtual.GenericWindowPolicyController genericWindowPolicyController, @android.annotation.NonNull android.os.PowerManager.WakeLock wakeLock) {
            java.util.Objects.requireNonNull(iVirtualDisplayCallback);
            this.mToken = iVirtualDisplayCallback;
            java.util.Objects.requireNonNull(genericWindowPolicyController);
            this.mWindowPolicyController = genericWindowPolicyController;
            java.util.Objects.requireNonNull(wakeLock);
            this.mWakeLock = wakeLock;
        }

        com.android.server.companion.virtual.GenericWindowPolicyController getWindowPolicyController() {
            return this.mWindowPolicyController;
        }

        android.os.PowerManager.WakeLock getWakeLock() {
            return this.mWakeLock;
        }

        android.hardware.display.IVirtualDisplayCallback getToken() {
            return this.mToken;
        }
    }
}
