package com.android.server.companion.virtual;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
public class VirtualDeviceManagerService extends com.android.server.SystemService {
    private static final java.lang.String TAG = "VirtualDeviceManagerService";
    private static final java.lang.String VIRTUAL_DEVICE_NATIVE_SERVICE = "virtualdevice_native";

    @com.android.internal.annotations.GuardedBy({"mVirtualDeviceManagerLock"})
    private android.util.ArrayMap<java.lang.String, android.companion.AssociationInfo> mActiveAssociations;
    private final com.android.server.wm.ActivityInterceptorCallback mActivityInterceptorCallback;

    @com.android.internal.annotations.GuardedBy({"mVirtualDeviceManagerLock"})
    private final android.util.SparseArray<android.util.ArraySet<java.lang.Integer>> mAppsOnVirtualDevices;
    private final android.companion.CompanionDeviceManager.OnAssociationsChangedListener mCdmAssociationListener;
    private final android.os.Handler mHandler;
    private final com.android.server.companion.virtual.VirtualDeviceManagerService.VirtualDeviceManagerImpl mImpl;
    private final com.android.server.companion.virtual.VirtualDeviceManagerInternal mLocalService;
    private final com.android.server.companion.virtual.VirtualDeviceManagerService.VirtualDeviceManagerNativeImpl mNativeImpl;
    private final com.android.server.companion.virtual.VirtualDeviceManagerService.PendingTrampolineMap mPendingTrampolines;
    private final android.os.RemoteCallbackList<android.companion.virtual.IVirtualDeviceListener> mVirtualDeviceListeners;
    private com.android.server.companion.virtual.VirtualDeviceLog mVirtualDeviceLog;
    private final java.lang.Object mVirtualDeviceManagerLock;

    @com.android.internal.annotations.GuardedBy({"mVirtualDeviceManagerLock"})
    private final android.util.SparseArray<com.android.server.companion.virtual.VirtualDeviceImpl> mVirtualDevices;
    private static final java.util.List<java.lang.String> VIRTUAL_DEVICE_COMPANION_DEVICE_PROFILES = java.util.Arrays.asList("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION", "android.app.role.COMPANION_DEVICE_APP_STREAMING", "android.app.role.COMPANION_DEVICE_NEARBY_DEVICE_STREAMING");
    private static java.util.concurrent.atomic.AtomicInteger sNextUniqueIndex = new java.util.concurrent.atomic.AtomicInteger(1);

    public VirtualDeviceManagerService(android.content.Context context) {
        super(context);
        this.mVirtualDeviceManagerLock = new java.lang.Object();
        this.mVirtualDeviceLog = new com.android.server.companion.virtual.VirtualDeviceLog(getContext());
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mPendingTrampolines = new com.android.server.companion.virtual.VirtualDeviceManagerService.PendingTrampolineMap(this.mHandler);
        this.mActiveAssociations = new android.util.ArrayMap<>();
        this.mCdmAssociationListener = new android.companion.CompanionDeviceManager.OnAssociationsChangedListener() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService.1
            @android.annotation.RequiresPermission("android.permission.CREATE_VIRTUAL_DEVICE")
            public void onAssociationsChanged(@android.annotation.NonNull java.util.List<android.companion.AssociationInfo> list) {
                com.android.server.companion.virtual.VirtualDeviceManagerService.this.syncVirtualDevicesToCdmAssociations(list);
            }
        };
        this.mVirtualDeviceListeners = new android.os.RemoteCallbackList<>();
        this.mVirtualDevices = new android.util.SparseArray<>();
        this.mAppsOnVirtualDevices = new android.util.SparseArray<>();
        this.mActivityInterceptorCallback = new com.android.server.wm.ActivityInterceptorCallback() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService.2
            @Override // com.android.server.wm.ActivityInterceptorCallback
            @android.annotation.Nullable
            public com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptResult onInterceptActivityLaunch(@android.annotation.NonNull com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
                com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline remove;
                if (activityInterceptorInfo.getCallingPackage() == null || (remove = com.android.server.companion.virtual.VirtualDeviceManagerService.this.mPendingTrampolines.remove(activityInterceptorInfo.getCallingPackage())) == null) {
                    return null;
                }
                remove.mResultReceiver.send(0, null);
                android.app.ActivityOptions checkedOptions = activityInterceptorInfo.getCheckedOptions();
                if (checkedOptions == null) {
                    checkedOptions = android.app.ActivityOptions.makeBasic();
                }
                return new com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptResult(activityInterceptorInfo.getIntent(), checkedOptions.setLaunchDisplayId(remove.mDisplayId));
            }
        };
        this.mImpl = new com.android.server.companion.virtual.VirtualDeviceManagerService.VirtualDeviceManagerImpl();
        this.mNativeImpl = android.companion.virtual.flags.Flags.enableNativeVdm() ? new com.android.server.companion.virtual.VirtualDeviceManagerService.VirtualDeviceManagerNativeImpl() : null;
        this.mLocalService = new com.android.server.companion.virtual.VirtualDeviceManagerService.LocalService();
    }

    @Override // com.android.server.SystemService
    @android.annotation.RequiresPermission("android.permission.MANAGE_COMPANION_DEVICES")
    public void onStart() {
        publishBinderService("virtualdevice", this.mImpl);
        if (android.companion.virtual.flags.Flags.enableNativeVdm()) {
            publishBinderService(VIRTUAL_DEVICE_NATIVE_SERVICE, this.mNativeImpl);
        }
        publishLocalService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class, this.mLocalService);
        ((com.android.server.wm.ActivityTaskManagerInternal) getLocalService(com.android.server.wm.ActivityTaskManagerInternal.class)).registerActivityStartInterceptor(3, this.mActivityInterceptorCallback);
        if (android.companion.virtual.flags.Flags.persistentDeviceIdApi()) {
            android.companion.CompanionDeviceManager companionDeviceManager = (android.companion.CompanionDeviceManager) getContext().getSystemService(android.companion.CompanionDeviceManager.class);
            if (companionDeviceManager != null) {
                onCdmAssociationsChanged(companionDeviceManager.getAllAssociations(-1));
                companionDeviceManager.addOnAssociationsChangedListener(getContext().getMainExecutor(), new android.companion.CompanionDeviceManager.OnAssociationsChangedListener() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService$$ExternalSyntheticLambda0
                    public final void onAssociationsChanged(java.util.List list) {
                        com.android.server.companion.virtual.VirtualDeviceManagerService.this.onCdmAssociationsChanged(list);
                    }
                }, -1);
            } else {
                android.util.Slog.e(TAG, "Failed to find CompanionDeviceManager. No CDM association info  will be available.");
            }
        }
    }

    void onCameraAccessBlocked(int i) {
        java.util.ArrayList<com.android.server.companion.virtual.VirtualDeviceImpl> virtualDevicesSnapshot = getVirtualDevicesSnapshot();
        for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
            com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl = virtualDevicesSnapshot.get(i2);
            virtualDeviceImpl.showToastWhereUidIsRunning(i, getContext().getString(android.R.string.unsupported_display_size_message, virtualDeviceImpl.getDisplayName()), 1, android.os.Looper.myLooper());
        }
    }

    com.android.server.companion.virtual.CameraAccessController getCameraAccessController(android.os.UserHandle userHandle) {
        if (android.companion.virtual.flags.Flags.streamCamera()) {
            return null;
        }
        int identifier = userHandle.getIdentifier();
        synchronized (this.mVirtualDeviceManagerLock) {
            for (int i = 0; i < this.mVirtualDevices.size(); i++) {
                try {
                    com.android.server.companion.virtual.CameraAccessController cameraAccessController = this.mVirtualDevices.valueAt(i).getCameraAccessController();
                    if (cameraAccessController.getUserId() == identifier) {
                        return cameraAccessController;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return new com.android.server.companion.virtual.CameraAccessController(getContext().createContextAsUser(userHandle, 0), this.mLocalService, new com.android.server.companion.virtual.CameraAccessController.CameraAccessBlockedCallback() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService$$ExternalSyntheticLambda1
                @Override // com.android.server.companion.virtual.CameraAccessController.CameraAccessBlockedCallback
                public final void onCameraAccessBlocked(int i2) {
                    com.android.server.companion.virtual.VirtualDeviceManagerService.this.onCameraAccessBlocked(i2);
                }
            });
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.companion.virtual.VirtualDeviceManagerInternal getLocalServiceInstance() {
        return this.mLocalService;
    }

    @com.android.internal.annotations.VisibleForTesting
    void notifyRunningAppsChanged(int i, android.util.ArraySet<java.lang.Integer> arraySet) {
        synchronized (this.mVirtualDeviceManagerLock) {
            try {
                if (!this.mVirtualDevices.contains(i)) {
                    android.util.Slog.e(TAG, "notifyRunningAppsChanged called for unknown deviceId:" + i + " (maybe it was recently closed?)");
                    return;
                }
                this.mAppsOnVirtualDevices.put(i, arraySet);
                this.mLocalService.onAppsOnVirtualDeviceChanged();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void addVirtualDevice(com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl) {
        synchronized (this.mVirtualDeviceManagerLock) {
            this.mVirtualDevices.put(virtualDeviceImpl.getDeviceId(), virtualDeviceImpl);
        }
    }

    boolean removeVirtualDevice(final int i) {
        synchronized (this.mVirtualDeviceManagerLock) {
            try {
                if (!this.mVirtualDevices.contains(i)) {
                    return false;
                }
                this.mAppsOnVirtualDevices.remove(i);
                this.mVirtualDevices.remove(i);
                if (android.companion.virtual.flags.Flags.vdmPublicApis()) {
                    this.mVirtualDeviceListeners.broadcast(new java.util.function.Consumer() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.companion.virtual.VirtualDeviceManagerService.lambda$removeVirtualDevice$0(i, (android.companion.virtual.IVirtualDeviceListener) obj);
                        }
                    });
                }
                android.content.Intent intent = new android.content.Intent("android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED");
                intent.putExtra("android.companion.virtual.extra.VIRTUAL_DEVICE_ID", i);
                intent.setFlags(1073741824);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    getContext().sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
                    if (!android.companion.virtual.flags.Flags.persistentDeviceIdApi()) {
                        synchronized (this.mVirtualDeviceManagerLock) {
                            try {
                                if (this.mVirtualDevices.size() == 0) {
                                    unregisterCdmAssociationListener();
                                }
                            } finally {
                            }
                        }
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeVirtualDevice$0(int i, android.companion.virtual.IVirtualDeviceListener iVirtualDeviceListener) {
        try {
            iVirtualDeviceListener.onVirtualDeviceClosed(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.i(TAG, "Failed to invoke onVirtualDeviceClosed listener: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission("android.permission.CREATE_VIRTUAL_DEVICE")
    public void syncVirtualDevicesToCdmAssociations(java.util.List<android.companion.AssociationInfo> list) {
        java.util.HashSet hashSet = new java.util.HashSet();
        synchronized (this.mVirtualDeviceManagerLock) {
            try {
                if (this.mVirtualDevices.size() == 0) {
                    return;
                }
                java.util.HashSet hashSet2 = new java.util.HashSet(list.size());
                java.util.Iterator<android.companion.AssociationInfo> it = list.iterator();
                while (it.hasNext()) {
                    hashSet2.add(java.lang.Integer.valueOf(it.next().getId()));
                }
                for (int i = 0; i < this.mVirtualDevices.size(); i++) {
                    com.android.server.companion.virtual.VirtualDeviceImpl valueAt = this.mVirtualDevices.valueAt(i);
                    if (!hashSet2.contains(java.lang.Integer.valueOf(valueAt.getAssociationId()))) {
                        hashSet.add(valueAt);
                    }
                }
                java.util.Iterator it2 = hashSet.iterator();
                while (it2.hasNext()) {
                    ((com.android.server.companion.virtual.VirtualDeviceImpl) it2.next()).close();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission("android.permission.MANAGE_COMPANION_DEVICES")
    public void registerCdmAssociationListener() {
        ((android.companion.CompanionDeviceManager) getContext().getSystemService(android.companion.CompanionDeviceManager.class)).addOnAssociationsChangedListener(getContext().getMainExecutor(), this.mCdmAssociationListener);
    }

    @android.annotation.RequiresPermission("android.permission.MANAGE_COMPANION_DEVICES")
    private void unregisterCdmAssociationListener() {
        ((android.companion.CompanionDeviceManager) getContext().getSystemService(android.companion.CompanionDeviceManager.class)).removeOnAssociationsChangedListener(this.mCdmAssociationListener);
    }

    @android.annotation.RequiresPermission("android.permission.CREATE_VIRTUAL_DEVICE")
    void onCdmAssociationsChanged(java.util.List<android.companion.AssociationInfo> list) {
        java.util.Set<java.lang.String> keySet;
        android.util.ArrayMap<java.lang.String, android.companion.AssociationInfo> arrayMap = new android.util.ArrayMap<>();
        for (int i = 0; i < list.size(); i++) {
            android.companion.AssociationInfo associationInfo = list.get(i);
            if (VIRTUAL_DEVICE_COMPANION_DEVICE_PROFILES.contains(associationInfo.getDeviceProfile()) && !associationInfo.isRevoked()) {
                arrayMap.put(com.android.server.companion.virtual.VirtualDeviceImpl.createPersistentDeviceId(associationInfo.getId()), associationInfo);
            }
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        synchronized (this.mVirtualDeviceManagerLock) {
            try {
                keySet = this.mActiveAssociations.keySet();
                keySet.removeAll(arrayMap.keySet());
                this.mActiveAssociations = arrayMap;
                for (int i2 = 0; i2 < this.mVirtualDevices.size(); i2++) {
                    com.android.server.companion.virtual.VirtualDeviceImpl valueAt = this.mVirtualDevices.valueAt(i2);
                    if (keySet.contains(valueAt.getPersistentDeviceId())) {
                        hashSet.add(valueAt);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        java.util.Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            ((com.android.server.companion.virtual.VirtualDeviceImpl) it.next()).close();
        }
        if (!keySet.isEmpty()) {
            this.mLocalService.onPersistentDeviceIdsRemoved(keySet);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.ArrayList<com.android.server.companion.virtual.VirtualDeviceImpl> getVirtualDevicesSnapshot() {
        java.util.ArrayList<com.android.server.companion.virtual.VirtualDeviceImpl> arrayList;
        synchronized (this.mVirtualDeviceManagerLock) {
            try {
                arrayList = new java.util.ArrayList<>(this.mVirtualDevices.size());
                for (int i = 0; i < this.mVirtualDevices.size(); i++) {
                    arrayList.add(this.mVirtualDevices.valueAt(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    class VirtualDeviceManagerImpl extends android.companion.virtual.IVirtualDeviceManager.Stub {
        private final com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampolineCallback mPendingTrampolineCallback = new com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampolineCallback() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService.VirtualDeviceManagerImpl.1
            @Override // com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampolineCallback
            public void startWaitingForPendingTrampoline(com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline pendingTrampoline) {
                com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline put = com.android.server.companion.virtual.VirtualDeviceManagerService.this.mPendingTrampolines.put(pendingTrampoline.mPendingIntent.getCreatorPackage(), pendingTrampoline);
                if (put != null) {
                    put.mResultReceiver.send(2, null);
                }
            }

            @Override // com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampolineCallback
            public void stopWaitingForPendingTrampoline(com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline pendingTrampoline) {
                com.android.server.companion.virtual.VirtualDeviceManagerService.this.mPendingTrampolines.remove(pendingTrampoline.mPendingIntent.getCreatorPackage());
            }
        };

        VirtualDeviceManagerImpl() {
        }

        @android.annotation.EnforcePermission("android.permission.CREATE_VIRTUAL_DEVICE")
        public android.companion.virtual.IVirtualDevice createVirtualDevice(android.os.IBinder iBinder, android.content.AttributionSource attributionSource, int i, @android.annotation.NonNull android.companion.virtual.VirtualDeviceParams virtualDeviceParams, @android.annotation.NonNull android.companion.virtual.IVirtualDeviceActivityListener iVirtualDeviceActivityListener, @android.annotation.NonNull android.companion.virtual.IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) {
            createVirtualDevice_enforcePermission();
            attributionSource.enforceCallingUid();
            int callingUid = android.companion.virtual.IVirtualDeviceManager.Stub.getCallingUid();
            java.lang.String packageName = attributionSource.getPackageName();
            if (!com.android.server.companion.virtual.PermissionUtils.validateCallingPackageName(com.android.server.companion.virtual.VirtualDeviceManagerService.this.getContext(), packageName)) {
                throw new java.lang.SecurityException("Package name " + packageName + " does not belong to calling uid " + callingUid);
            }
            android.companion.AssociationInfo associationInfo = getAssociationInfo(packageName, i);
            if (associationInfo == null) {
                throw new java.lang.IllegalArgumentException("No association with ID " + i);
            }
            if (!com.android.server.companion.virtual.VirtualDeviceManagerService.VIRTUAL_DEVICE_COMPANION_DEVICE_PROFILES.contains(associationInfo.getDeviceProfile()) && android.companion.virtual.flags.Flags.persistentDeviceIdApi()) {
                throw new java.lang.IllegalArgumentException("Unsupported CDM Association device profile " + associationInfo.getDeviceProfile() + " for virtual device creation.");
            }
            java.util.Objects.requireNonNull(virtualDeviceParams);
            java.util.Objects.requireNonNull(iVirtualDeviceActivityListener);
            java.util.Objects.requireNonNull(iVirtualDeviceSoundEffectListener);
            com.android.server.companion.virtual.CameraAccessController cameraAccessController = com.android.server.companion.virtual.VirtualDeviceManagerService.this.getCameraAccessController(android.companion.virtual.IVirtualDeviceManager.Stub.getCallingUserHandle());
            final int andIncrement = com.android.server.companion.virtual.VirtualDeviceManagerService.sNextUniqueIndex.getAndIncrement();
            com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl = new com.android.server.companion.virtual.VirtualDeviceImpl(com.android.server.companion.virtual.VirtualDeviceManagerService.this.getContext(), associationInfo, com.android.server.companion.virtual.VirtualDeviceManagerService.this, com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceLog, iBinder, attributionSource, andIncrement, cameraAccessController, this.mPendingTrampolineCallback, iVirtualDeviceActivityListener, iVirtualDeviceSoundEffectListener, new java.util.function.Consumer() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService$VirtualDeviceManagerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.companion.virtual.VirtualDeviceManagerService.VirtualDeviceManagerImpl.this.lambda$createVirtualDevice$0(andIncrement, (android.util.ArraySet) obj);
                }
            }, virtualDeviceParams);
            if (android.companion.virtual.flags.Flags.expressMetrics()) {
                com.android.modules.expresslog.Counter.logIncrement("virtual_devices.value_virtual_devices_created_count");
            }
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                try {
                    if (!android.companion.virtual.flags.Flags.persistentDeviceIdApi() && com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.size() == 0) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            com.android.server.companion.virtual.VirtualDeviceManagerService.this.registerCdmAssociationListener();
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (java.lang.Throwable th) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.put(andIncrement, virtualDeviceImpl);
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
            if (android.companion.virtual.flags.Flags.vdmPublicApis()) {
                com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceListeners.broadcast(new java.util.function.Consumer() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService$VirtualDeviceManagerImpl$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.companion.virtual.VirtualDeviceManagerService.VirtualDeviceManagerImpl.lambda$createVirtualDevice$1(andIncrement, (android.companion.virtual.IVirtualDeviceListener) obj);
                    }
                });
            }
            if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
                com.android.modules.expresslog.Counter.logIncrementWithUid("virtual_devices.value_virtual_devices_created_with_uid_count", attributionSource.getUid());
            }
            return virtualDeviceImpl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createVirtualDevice$0(int i, android.util.ArraySet arraySet) {
            com.android.server.companion.virtual.VirtualDeviceManagerService.this.notifyRunningAppsChanged(i, arraySet);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$createVirtualDevice$1(int i, android.companion.virtual.IVirtualDeviceListener iVirtualDeviceListener) {
            try {
                iVirtualDeviceListener.onVirtualDeviceCreated(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.i(com.android.server.companion.virtual.VirtualDeviceManagerService.TAG, "Failed to invoke onVirtualDeviceCreated listener: " + e.getMessage());
            }
        }

        public int createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.companion.virtual.IVirtualDevice iVirtualDevice, java.lang.String str) throws android.os.RemoteException {
            com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl;
            java.util.Objects.requireNonNull(virtualDisplayConfig);
            int callingUid = android.companion.virtual.IVirtualDeviceManager.Stub.getCallingUid();
            if (!com.android.server.companion.virtual.PermissionUtils.validateCallingPackageName(com.android.server.companion.virtual.VirtualDeviceManagerService.this.getContext(), str)) {
                throw new java.lang.SecurityException("Package name " + str + " does not belong to calling uid " + callingUid);
            }
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(iVirtualDevice.getDeviceId());
                if (virtualDeviceImpl == null) {
                    throw new java.lang.SecurityException("Invalid VirtualDevice (deviceId = " + iVirtualDevice.getDeviceId() + ")");
                }
            }
            if (virtualDeviceImpl.getOwnerUid() != callingUid) {
                throw new java.lang.SecurityException("uid " + callingUid + " is not the owner of the supplied VirtualDevice (deviceId = " + iVirtualDevice.getDeviceId() + ")");
            }
            return virtualDeviceImpl.createVirtualDisplay(virtualDisplayConfig, iVirtualDisplayCallback, str);
        }

        public java.util.List<android.companion.virtual.VirtualDevice> getVirtualDevices() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                for (int i = 0; i < com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.size(); i++) {
                    try {
                        arrayList.add(((com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.valueAt(i)).getPublicVirtualDeviceObject());
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
            return arrayList;
        }

        public android.companion.virtual.VirtualDevice getVirtualDevice(int i) {
            com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            if (virtualDeviceImpl == null) {
                return null;
            }
            return virtualDeviceImpl.getPublicVirtualDeviceObject();
        }

        public void registerVirtualDeviceListener(android.companion.virtual.IVirtualDeviceListener iVirtualDeviceListener) {
            com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceListeners.register(iVirtualDeviceListener);
        }

        public void unregisterVirtualDeviceListener(android.companion.virtual.IVirtualDeviceListener iVirtualDeviceListener) {
            com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceListeners.unregister(iVirtualDeviceListener);
        }

        public int getDevicePolicy(int i, int i2) {
            int devicePolicy;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                try {
                    com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(i);
                    devicePolicy = virtualDeviceImpl != null ? virtualDeviceImpl.getDevicePolicy(i2) : 0;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return devicePolicy;
        }

        public int getDeviceIdForDisplayId(int i) {
            if (i == -1 || i == 0) {
                return 0;
            }
            java.util.ArrayList virtualDevicesSnapshot = com.android.server.companion.virtual.VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
            for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
                com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) virtualDevicesSnapshot.get(i2);
                if (virtualDeviceImpl.isDisplayOwnedByVirtualDevice(i)) {
                    return virtualDeviceImpl.getDeviceId();
                }
            }
            return 0;
        }

        @android.annotation.Nullable
        public java.lang.CharSequence getDisplayNameForPersistentDeviceId(@android.annotation.NonNull java.lang.String str) {
            android.companion.AssociationInfo associationInfo;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                associationInfo = (android.companion.AssociationInfo) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mActiveAssociations.get(str);
            }
            if (associationInfo == null) {
                return null;
            }
            return associationInfo.getDisplayName();
        }

        @android.annotation.NonNull
        public java.util.List<java.lang.String> getAllPersistentDeviceIds() {
            return new java.util.ArrayList(com.android.server.companion.virtual.VirtualDeviceManagerService.this.mLocalService.getAllPersistentDeviceIds());
        }

        public boolean isValidVirtualDeviceId(int i) {
            boolean contains;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                contains = com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.contains(i);
            }
            return contains;
        }

        public int getAudioPlaybackSessionId(int i) {
            int audioPlaybackSessionId;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                try {
                    com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(i);
                    audioPlaybackSessionId = virtualDeviceImpl != null ? virtualDeviceImpl.getAudioPlaybackSessionId() : 0;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return audioPlaybackSessionId;
        }

        public int getAudioRecordingSessionId(int i) {
            int audioRecordingSessionId;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                try {
                    com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(i);
                    audioRecordingSessionId = virtualDeviceImpl != null ? virtualDeviceImpl.getAudioRecordingSessionId() : 0;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return audioRecordingSessionId;
        }

        public void playSoundEffect(int i, int i2) {
            com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            if (virtualDeviceImpl != null) {
                virtualDeviceImpl.playSoundEffect(i2);
            }
        }

        public boolean isVirtualDeviceOwnedMirrorDisplay(int i) {
            return (getDeviceIdForDisplayId(i) == 0 || ((android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class)).getDisplayIdToMirror(i) == -1) ? false : true;
        }

        @android.annotation.Nullable
        private android.companion.AssociationInfo getAssociationInfo(java.lang.String str, int i) {
            android.os.UserHandle callingUserHandle = android.companion.virtual.IVirtualDeviceManager.Stub.getCallingUserHandle();
            android.companion.CompanionDeviceManager companionDeviceManager = (android.companion.CompanionDeviceManager) com.android.server.companion.virtual.VirtualDeviceManagerService.this.getContext().createContextAsUser(callingUserHandle, 0).getSystemService(android.companion.CompanionDeviceManager.class);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.List allAssociations = companionDeviceManager.getAllAssociations();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                int identifier = callingUserHandle.getIdentifier();
                if (allAssociations != null) {
                    int size = allAssociations.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        android.companion.AssociationInfo associationInfo = (android.companion.AssociationInfo) allAssociations.get(i2);
                        if (associationInfo.belongsToPackage(identifier, str) && i == associationInfo.getId()) {
                            return associationInfo;
                        }
                    }
                    return null;
                }
                android.util.Slog.w(com.android.server.companion.virtual.VirtualDeviceManagerService.TAG, "No associations for user " + identifier);
                return null;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (java.lang.Throwable th) {
                android.util.Slog.e(com.android.server.companion.virtual.VirtualDeviceManagerService.TAG, "Error during IPC", th);
                throw android.util.ExceptionUtils.propagate(th, android.os.RemoteException.class);
            }
        }

        public void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
            if (!com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(com.android.server.companion.virtual.VirtualDeviceManagerService.this.getContext(), com.android.server.companion.virtual.VirtualDeviceManagerService.TAG, printWriter)) {
                return;
            }
            printWriter.println("Created virtual devices: ");
            java.util.ArrayList virtualDevicesSnapshot = com.android.server.companion.virtual.VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
            for (int i = 0; i < virtualDevicesSnapshot.size(); i++) {
                ((com.android.server.companion.virtual.VirtualDeviceImpl) virtualDevicesSnapshot.get(i)).dump(fileDescriptor, printWriter, strArr);
            }
            com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceLog.dump(printWriter);
        }
    }

    final class VirtualDeviceManagerNativeImpl extends android.companion.virtualnative.IVirtualDeviceManagerNative.Stub {
        VirtualDeviceManagerNativeImpl() {
        }

        public int[] getDeviceIdsForUid(int i) {
            return com.android.server.companion.virtual.VirtualDeviceManagerService.this.mLocalService.getDeviceIdsForUid(i).stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray();
        }

        public int getDevicePolicy(int i, int i2) {
            return com.android.server.companion.virtual.VirtualDeviceManagerService.this.mImpl.getDevicePolicy(i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class LocalService extends com.android.server.companion.virtual.VirtualDeviceManagerInternal {

        @com.android.internal.annotations.GuardedBy({"mVirtualDeviceManagerLock"})
        private final android.util.ArraySet<java.lang.Integer> mAllUidsOnVirtualDevice;

        @com.android.internal.annotations.GuardedBy({"mVirtualDeviceManagerLock"})
        private final java.util.ArrayList<com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener> mAppsOnVirtualDeviceListeners;

        @com.android.internal.annotations.GuardedBy({"mVirtualDeviceManagerLock"})
        private final java.util.ArrayList<java.util.function.Consumer<java.lang.String>> mPersistentDeviceIdRemovedListeners;

        private LocalService() {
            this.mAppsOnVirtualDeviceListeners = new java.util.ArrayList<>();
            this.mPersistentDeviceIdRemovedListeners = new java.util.ArrayList<>();
            this.mAllUidsOnVirtualDevice = new android.util.ArraySet<>();
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public int getDeviceOwnerUid(int i) {
            com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            if (virtualDeviceImpl != null) {
                return virtualDeviceImpl.getOwnerUid();
            }
            return -1;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        @android.annotation.Nullable
        public android.companion.virtual.sensor.VirtualSensor getVirtualSensor(int i, int i2) {
            com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            if (virtualDeviceImpl != null) {
                return virtualDeviceImpl.getVirtualSensorByHandle(i2);
            }
            return null;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        @android.annotation.NonNull
        public android.util.ArraySet<java.lang.Integer> getDeviceIdsForUid(int i) {
            java.util.ArrayList virtualDevicesSnapshot = com.android.server.companion.virtual.VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
            android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
            for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
                com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) virtualDevicesSnapshot.get(i2);
                if (virtualDeviceImpl.isAppRunningOnVirtualDevice(i)) {
                    arraySet.add(java.lang.Integer.valueOf(virtualDeviceImpl.getDeviceId()));
                }
            }
            return arraySet;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public void onVirtualDisplayRemoved(android.companion.virtual.IVirtualDevice iVirtualDevice, int i) {
            com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(((com.android.server.companion.virtual.VirtualDeviceImpl) iVirtualDevice).getDeviceId());
            }
            if (virtualDeviceImpl != null) {
                virtualDeviceImpl.onVirtualDisplayRemoved(i);
            }
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public void onAppsOnVirtualDeviceChanged() {
            final com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener[] appsOnVirtualDeviceListenerArr;
            final android.util.ArraySet<? extends java.lang.Integer> arraySet = new android.util.ArraySet<>();
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                try {
                    int size = com.android.server.companion.virtual.VirtualDeviceManagerService.this.mAppsOnVirtualDevices.size();
                    for (int i = 0; i < size; i++) {
                        arraySet.addAll((android.util.ArraySet<? extends java.lang.Object>) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mAppsOnVirtualDevices.valueAt(i));
                    }
                    if (!this.mAllUidsOnVirtualDevice.equals(arraySet)) {
                        this.mAllUidsOnVirtualDevice.clear();
                        this.mAllUidsOnVirtualDevice.addAll(arraySet);
                        appsOnVirtualDeviceListenerArr = (com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener[]) this.mAppsOnVirtualDeviceListeners.toArray(new com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener[0]);
                    } else {
                        appsOnVirtualDeviceListenerArr = null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (appsOnVirtualDeviceListenerArr != null) {
                com.android.server.companion.virtual.VirtualDeviceManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.companion.virtual.VirtualDeviceManagerService.LocalService.lambda$onAppsOnVirtualDeviceChanged$0(appsOnVirtualDeviceListenerArr, arraySet);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onAppsOnVirtualDeviceChanged$0(com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener[] appsOnVirtualDeviceListenerArr, android.util.ArraySet arraySet) {
            for (com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener appsOnVirtualDeviceListener : appsOnVirtualDeviceListenerArr) {
                appsOnVirtualDeviceListener.onAppsOnAnyVirtualDeviceChanged(arraySet);
            }
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public void onPersistentDeviceIdsRemoved(final java.util.Set<java.lang.String> set) {
            final java.util.List copyOf;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                copyOf = java.util.List.copyOf(this.mPersistentDeviceIdRemovedListeners);
            }
            com.android.server.companion.virtual.VirtualDeviceManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.companion.virtual.VirtualDeviceManagerService.LocalService.lambda$onPersistentDeviceIdsRemoved$1(set, copyOf);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onPersistentDeviceIdsRemoved$1(java.util.Set set, java.util.List list) {
            java.util.Iterator it = set.iterator();
            while (it.hasNext()) {
                java.lang.String str = (java.lang.String) it.next();
                java.util.Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    ((java.util.function.Consumer) it2.next()).accept(str);
                }
            }
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public void onAuthenticationPrompt(int i) {
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                for (int i2 = 0; i2 < com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.size(); i2++) {
                    try {
                        ((com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.valueAt(i2)).showToastWhereUidIsRunning(i, android.R.string.app_category_video, 1, android.os.Looper.getMainLooper());
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public int getBaseVirtualDisplayFlags(android.companion.virtual.IVirtualDevice iVirtualDevice) {
            return ((com.android.server.companion.virtual.VirtualDeviceImpl) iVirtualDevice).getBaseVirtualDisplayFlags();
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        @android.annotation.Nullable
        public android.os.LocaleList getPreferredLocaleListForUid(int i) {
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                for (int i2 = 0; i2 < com.android.server.companion.virtual.VirtualDeviceManagerService.this.mAppsOnVirtualDevices.size(); i2++) {
                    try {
                        if (((android.util.ArraySet) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mAppsOnVirtualDevices.valueAt(i2)).contains(java.lang.Integer.valueOf(i))) {
                            return ((com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(com.android.server.companion.virtual.VirtualDeviceManagerService.this.mAppsOnVirtualDevices.keyAt(i2))).getDeviceLocaleList();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return null;
            }
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public boolean isAppRunningOnAnyVirtualDevice(int i) {
            java.util.ArrayList virtualDevicesSnapshot = com.android.server.companion.virtual.VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
            for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
                if (((com.android.server.companion.virtual.VirtualDeviceImpl) virtualDevicesSnapshot.get(i2)).isAppRunningOnVirtualDevice(i)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public boolean isInputDeviceOwnedByVirtualDevice(int i) {
            java.util.ArrayList virtualDevicesSnapshot = com.android.server.companion.virtual.VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
            for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
                if (((com.android.server.companion.virtual.VirtualDeviceImpl) virtualDevicesSnapshot.get(i2)).isInputDeviceOwnedByVirtualDevice(i)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        @android.annotation.NonNull
        public android.util.ArraySet<java.lang.Integer> getDisplayIdsForDevice(int i) {
            com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            return virtualDeviceImpl == null ? new android.util.ArraySet<>() : (android.util.ArraySet) java.util.Arrays.stream(virtualDeviceImpl.getDisplayIds()).boxed().collect(java.util.stream.Collectors.toCollection(new java.util.function.Supplier() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return new android.util.ArraySet();
                }
            }));
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public int getDeviceIdForDisplayId(int i) {
            return com.android.server.companion.virtual.VirtualDeviceManagerService.this.mImpl.getDeviceIdForDisplayId(i);
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public boolean isValidVirtualDeviceId(int i) {
            return com.android.server.companion.virtual.VirtualDeviceManagerService.this.mImpl.isValidVirtualDeviceId(i);
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        @android.annotation.Nullable
        public java.lang.String getPersistentIdForDevice(int i) {
            com.android.server.companion.virtual.VirtualDeviceImpl virtualDeviceImpl;
            if (i == 0) {
                return "default:0";
            }
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl = (com.android.server.companion.virtual.VirtualDeviceImpl) com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            if (virtualDeviceImpl == null) {
                return null;
            }
            return virtualDeviceImpl.getPersistentDeviceId();
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        @android.annotation.NonNull
        public java.util.Set<java.lang.String> getAllPersistentDeviceIds() {
            java.util.Set<java.lang.String> copyOf;
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                copyOf = java.util.Set.copyOf(com.android.server.companion.virtual.VirtualDeviceManagerService.this.mActiveAssociations.keySet());
            }
            return copyOf;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public void registerAppsOnVirtualDeviceListener(@android.annotation.NonNull com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener appsOnVirtualDeviceListener) {
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                this.mAppsOnVirtualDeviceListeners.add(appsOnVirtualDeviceListener);
            }
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public void unregisterAppsOnVirtualDeviceListener(@android.annotation.NonNull com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener appsOnVirtualDeviceListener) {
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                this.mAppsOnVirtualDeviceListeners.remove(appsOnVirtualDeviceListener);
            }
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public void registerPersistentDeviceIdRemovedListener(@android.annotation.NonNull java.util.function.Consumer<java.lang.String> consumer) {
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                this.mPersistentDeviceIdRemovedListeners.add(consumer);
            }
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public void unregisterPersistentDeviceIdRemovedListener(@android.annotation.NonNull java.util.function.Consumer<java.lang.String> consumer) {
            synchronized (com.android.server.companion.virtual.VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                this.mPersistentDeviceIdRemovedListeners.remove(consumer);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class PendingTrampolineMap {
        private static final int TRAMPOLINE_WAIT_MS = 5000;
        private final android.os.Handler mHandler;
        private final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline> mMap = new java.util.concurrent.ConcurrentHashMap<>();

        PendingTrampolineMap(android.os.Handler handler) {
            this.mHandler = handler;
        }

        com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline put(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull final com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline pendingTrampoline) {
            com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline put = this.mMap.put(str, pendingTrampoline);
            this.mHandler.removeCallbacksAndMessages(put);
            this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.companion.virtual.VirtualDeviceManagerService$PendingTrampolineMap$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.companion.virtual.VirtualDeviceManagerService.PendingTrampolineMap.this.lambda$put$0(pendingTrampoline);
                }
            }, pendingTrampoline, 5000L);
            return put;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$put$0(com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline pendingTrampoline) {
            java.lang.String creatorPackage = pendingTrampoline.mPendingIntent.getCreatorPackage();
            if (creatorPackage != null) {
                remove(creatorPackage);
            }
        }

        com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline remove(@android.annotation.NonNull java.lang.String str) {
            com.android.server.companion.virtual.VirtualDeviceImpl.PendingTrampoline remove = this.mMap.remove(str);
            this.mHandler.removeCallbacksAndMessages(remove);
            return remove;
        }
    }
}
