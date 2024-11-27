package com.android.server.companion;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
public class CompanionDeviceManagerService extends com.android.server.SystemService {
    private static final int ASSOCIATIONS_IDS_PER_USER_RANGE = 100000;
    private static final long ASSOCIATION_REMOVAL_TIME_WINDOW_DEFAULT = java.util.concurrent.TimeUnit.DAYS.toMillis(90);
    static final boolean DEBUG = false;
    private static final int MAX_CN_LENGTH = 500;
    private static final long PAIR_WITHOUT_PROMPT_WINDOW_MS = 600000;
    private static final java.lang.String PREF_FILE_NAME = "companion_device_preferences.xml";
    private static final java.lang.String PREF_KEY_AUTO_REVOKE_GRANTS_DONE = "auto_revoke_grants_done";
    private static final java.lang.String SYS_PROP_DEBUG_REMOVAL_TIME_WINDOW = "debug.cdm.cdmservice.removal_time_window";
    static final java.lang.String TAG = "CDM_CompanionDeviceManagerService";
    private final android.app.ActivityManager mActivityManager;
    private final android.app.ActivityManagerInternal mAmInternal;
    private final com.android.internal.app.IAppOpsService mAppOpsManager;
    private com.android.server.companion.AssociationRequestsProcessor mAssociationRequestsProcessor;
    private com.android.server.companion.AssociationRevokeProcessor mAssociationRevokeProcessor;
    private final com.android.server.companion.AssociationStoreImpl mAssociationStore;
    private final com.android.server.companion.AssociationStore.OnChangeListener mAssociationStoreChangeListener;
    private final com.android.server.wm.ActivityTaskManagerInternal mAtmInternal;
    private com.android.server.companion.BackupRestoreProcessor mBackupRestoreProcessor;
    private com.android.server.companion.CompanionApplicationController mCompanionAppController;
    private com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController mCrossDeviceSyncController;
    private final com.android.server.companion.presence.CompanionDevicePresenceMonitor.Callback mDevicePresenceCallback;
    private com.android.server.companion.presence.CompanionDevicePresenceMonitor mDevicePresenceMonitor;
    private final android.os.RemoteCallbackList<android.companion.IOnAssociationsChangedListener> mListeners;
    private com.android.server.companion.presence.ObservableUuidStore mObservableUuidStore;
    final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final com.android.internal.content.PackageMonitor mPackageMonitor;
    private com.android.server.companion.PersistentDataStore mPersistentStore;
    private final android.os.PowerManagerInternal mPowerManagerInternal;
    private final android.os.PowerWhitelistManager mPowerWhitelistManager;

    @com.android.internal.annotations.GuardedBy({"mPreviouslyUsedIds"})
    private final android.util.SparseArray<java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>>> mPreviouslyUsedIds;
    private com.android.server.companion.datatransfer.SystemDataTransferProcessor mSystemDataTransferProcessor;
    private final com.android.server.companion.datatransfer.SystemDataTransferRequestStore mSystemDataTransferRequestStore;
    private com.android.server.companion.transport.CompanionTransportManager mTransportManager;
    private final android.os.UserManager mUserManager;
    private final com.android.server.companion.CompanionDeviceManagerService.PersistUserStateHandler mUserPersistenceHandler;

    public CompanionDeviceManagerService(android.content.Context context) {
        super(context);
        this.mPreviouslyUsedIds = new android.util.SparseArray<>();
        this.mListeners = new android.os.RemoteCallbackList<>();
        this.mAssociationStoreChangeListener = new com.android.server.companion.AssociationStore.OnChangeListener() { // from class: com.android.server.companion.CompanionDeviceManagerService.1
            @Override // com.android.server.companion.AssociationStore.OnChangeListener
            public void onAssociationChanged(int i, android.companion.AssociationInfo associationInfo) {
                com.android.server.companion.CompanionDeviceManagerService.this.onAssociationChangedInternal(i, associationInfo);
            }
        };
        this.mDevicePresenceCallback = new com.android.server.companion.presence.CompanionDevicePresenceMonitor.Callback() { // from class: com.android.server.companion.CompanionDeviceManagerService.2
            @Override // com.android.server.companion.presence.CompanionDevicePresenceMonitor.Callback
            public void onDeviceAppeared(int i) {
                com.android.server.companion.CompanionDeviceManagerService.this.onDeviceAppearedInternal(i);
            }

            @Override // com.android.server.companion.presence.CompanionDevicePresenceMonitor.Callback
            public void onDeviceDisappeared(int i) {
                com.android.server.companion.CompanionDeviceManagerService.this.onDeviceDisappearedInternal(i);
            }

            @Override // com.android.server.companion.presence.CompanionDevicePresenceMonitor.Callback
            public void onDevicePresenceEvent(int i, int i2) {
                com.android.server.companion.CompanionDeviceManagerService.this.onDevicePresenceEventInternal(i, i2);
            }

            @Override // com.android.server.companion.presence.CompanionDevicePresenceMonitor.Callback
            public void onDevicePresenceEventByUuid(com.android.server.companion.presence.ObservableUuid observableUuid, int i) {
                com.android.server.companion.CompanionDeviceManagerService.this.onDevicePresenceEventByUuidInternal(observableUuid, i);
            }
        };
        this.mPackageMonitor = new com.android.internal.content.PackageMonitor() { // from class: com.android.server.companion.CompanionDeviceManagerService.3
            public void onPackageRemoved(java.lang.String str, int i) {
                com.android.server.companion.CompanionDeviceManagerService.this.onPackageRemoveOrDataClearedInternal(getChangingUserId(), str);
            }

            public void onPackageDataCleared(java.lang.String str, int i) {
                com.android.server.companion.CompanionDeviceManagerService.this.onPackageRemoveOrDataClearedInternal(getChangingUserId(), str);
            }

            public void onPackageModified(java.lang.String str) {
                com.android.server.companion.CompanionDeviceManagerService.this.onPackageModifiedInternal(getChangingUserId(), str);
            }

            public void onPackageAdded(java.lang.String str, int i) {
                com.android.server.companion.CompanionDeviceManagerService.this.onPackageAddedInternal(getChangingUserId(), str);
            }
        };
        this.mActivityManager = (android.app.ActivityManager) context.getSystemService(android.app.ActivityManager.class);
        this.mPowerWhitelistManager = (android.os.PowerWhitelistManager) context.getSystemService(android.os.PowerWhitelistManager.class);
        this.mAppOpsManager = com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService("appops"));
        this.mAtmInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        this.mAmInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mUserManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        this.mUserPersistenceHandler = new com.android.server.companion.CompanionDeviceManagerService.PersistUserStateHandler();
        this.mAssociationStore = new com.android.server.companion.AssociationStoreImpl();
        this.mSystemDataTransferRequestStore = new com.android.server.companion.datatransfer.SystemDataTransferRequestStore();
        this.mPowerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
        this.mObservableUuidStore = new com.android.server.companion.presence.ObservableUuidStore();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        android.content.Context context = getContext();
        this.mPersistentStore = new com.android.server.companion.PersistentDataStore();
        this.mAssociationRequestsProcessor = new com.android.server.companion.AssociationRequestsProcessor(this, this.mAssociationStore);
        this.mBackupRestoreProcessor = new com.android.server.companion.BackupRestoreProcessor(this, this.mAssociationStore, this.mPersistentStore, this.mSystemDataTransferRequestStore, this.mAssociationRequestsProcessor);
        loadAssociationsFromDisk();
        this.mObservableUuidStore.getObservableUuidsForUser(getContext().getUserId());
        this.mAssociationStore.registerListener(this.mAssociationStoreChangeListener);
        this.mDevicePresenceMonitor = new com.android.server.companion.presence.CompanionDevicePresenceMonitor(this.mUserManager, this.mAssociationStore, this.mObservableUuidStore, this.mDevicePresenceCallback);
        this.mCompanionAppController = new com.android.server.companion.CompanionApplicationController(context, this.mAssociationStore, this.mObservableUuidStore, this.mDevicePresenceMonitor, this.mPowerManagerInternal);
        this.mTransportManager = new com.android.server.companion.transport.CompanionTransportManager(context, this.mAssociationStore);
        this.mSystemDataTransferProcessor = new com.android.server.companion.datatransfer.SystemDataTransferProcessor(this, this.mPackageManagerInternal, this.mAssociationStore, this.mSystemDataTransferRequestStore, this.mTransportManager);
        this.mAssociationRevokeProcessor = new com.android.server.companion.AssociationRevokeProcessor(this, this.mAssociationStore, this.mPackageManagerInternal, this.mDevicePresenceMonitor, this.mCompanionAppController, this.mSystemDataTransferRequestStore);
        this.mCrossDeviceSyncController = new com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController(getContext(), this.mTransportManager);
        publishBinderService("companiondevice", new com.android.server.companion.CompanionDeviceManagerService.CompanionDeviceManagerImpl());
        com.android.server.LocalServices.addService(com.android.server.companion.CompanionDeviceManagerServiceInternal.class, new com.android.server.companion.CompanionDeviceManagerService.LocalService());
    }

    void loadAssociationsFromDisk() {
        android.util.ArraySet<android.companion.AssociationInfo> arraySet = new android.util.ArraySet();
        synchronized (this.mPreviouslyUsedIds) {
            this.mPersistentStore.readStateForUsers(this.mUserManager.getAliveUsers(), arraySet, this.mPreviouslyUsedIds);
        }
        android.util.ArraySet arraySet2 = new android.util.ArraySet(arraySet.size());
        android.util.ArraySet arraySet3 = new android.util.ArraySet();
        for (android.companion.AssociationInfo associationInfo : arraySet) {
            if (associationInfo.isPending()) {
                this.mBackupRestoreProcessor.addToPendingAppInstall(associationInfo);
            } else if (!associationInfo.isRevoked()) {
                arraySet2.add(associationInfo);
            } else if (this.mAssociationRevokeProcessor.maybeRemoveRoleHolderForAssociation(associationInfo)) {
                arraySet3.add(java.lang.Integer.valueOf(associationInfo.getUserId()));
            } else {
                this.mAssociationRevokeProcessor.addToPendingRoleHolderRemoval(associationInfo);
            }
        }
        this.mAssociationStore.setAssociations(arraySet2);
        java.util.Iterator it = arraySet3.iterator();
        while (it.hasNext()) {
            persistStateForUser(((java.lang.Integer) it.next()).intValue());
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        android.content.Context context = getContext();
        if (i == 500) {
            this.mPackageMonitor.register(context, com.android.server.FgThread.get().getLooper(), android.os.UserHandle.ALL, true);
            this.mDevicePresenceMonitor.init(context);
        } else if (i == 1000) {
            com.android.server.companion.InactiveAssociationsRemovalService.schedule(getContext());
            this.mCrossDeviceSyncController.onBootCompleted();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        java.util.List<android.companion.AssociationInfo> associationsForUser = this.mAssociationStore.getAssociationsForUser(userIdentifier);
        if (associationsForUser.isEmpty()) {
            return;
        }
        updateAtm(userIdentifier, associationsForUser);
        com.android.internal.os.BackgroundThread.getHandler().sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.companion.CompanionDeviceManagerService) obj).maybeGrantAutoRevokeExemptions();
            }
        }, this), java.util.concurrent.TimeUnit.MINUTES.toMillis(10L));
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocked(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        java.util.Set<android.bluetooth.BluetoothDevice> set = this.mDevicePresenceMonitor.getPendingConnectedDevices().get(userIdentifier);
        java.util.List<com.android.server.companion.presence.ObservableUuid> observableUuidsForUser = this.mObservableUuidStore.getObservableUuidsForUser(userIdentifier);
        if (set != null) {
            for (android.bluetooth.BluetoothDevice bluetoothDevice : set) {
                android.os.ParcelUuid[] uuids = bluetoothDevice.getUuids();
                java.util.List emptyList = com.android.internal.util.ArrayUtils.isEmpty(uuids) ? java.util.Collections.emptyList() : java.util.Arrays.asList(uuids);
                for (android.companion.AssociationInfo associationInfo : this.mAssociationStore.getAssociationsByAddress(bluetoothDevice.getAddress())) {
                    android.util.Slog.i(TAG, "onUserUnlocked, device id( " + associationInfo.getId() + " ) is connected");
                    this.mDevicePresenceMonitor.onBluetoothCompanionDeviceConnected(associationInfo.getId());
                }
                for (com.android.server.companion.presence.ObservableUuid observableUuid : observableUuidsForUser) {
                    if (emptyList.contains(observableUuid.getUuid())) {
                        android.util.Slog.i(TAG, "onUserUnlocked, UUID( " + observableUuid.getUuid() + " ) is connected");
                        this.mDevicePresenceMonitor.onDevicePresenceEventByUuid(observableUuid, 2);
                    }
                }
            }
        }
    }

    @android.annotation.NonNull
    android.companion.AssociationInfo getAssociationWithCallerChecks(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.companion.AssociationInfo sanitizeWithCallerChecks = com.android.server.companion.utils.PermissionsUtils.sanitizeWithCallerChecks(getContext(), this.mAssociationStore.getAssociationsForPackageWithAddress(i, str, str2));
        if (sanitizeWithCallerChecks != null) {
            return sanitizeWithCallerChecks;
        }
        throw new java.lang.IllegalArgumentException("Association does not exist or the caller does not have permissions to manage it (ie. it belongs to a different package or a different user).");
    }

    @android.annotation.NonNull
    android.companion.AssociationInfo getAssociationWithCallerChecks(int i) {
        android.companion.AssociationInfo sanitizeWithCallerChecks = com.android.server.companion.utils.PermissionsUtils.sanitizeWithCallerChecks(getContext(), this.mAssociationStore.getAssociationById(i));
        if (sanitizeWithCallerChecks != null) {
            return sanitizeWithCallerChecks;
        }
        throw new java.lang.IllegalArgumentException("Association does not exist or the caller does not have permissions to manage it (ie. it belongs to a different package or a different user).");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDeviceAppearedInternal(int i) {
        android.companion.AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        if (associationById.shouldBindWhenPresent()) {
            bindApplicationIfNeeded(associationById);
            this.mCompanionAppController.notifyCompanionApplicationDeviceAppeared(associationById);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDeviceDisappearedInternal(int i) {
        android.companion.AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        if (this.mCompanionAppController.isCompanionApplicationBound(associationById.getUserId(), associationById.getPackageName()) && associationById.shouldBindWhenPresent()) {
            this.mCompanionAppController.notifyCompanionApplicationDeviceDisappeared(associationById);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDevicePresenceEventInternal(int i, int i2) {
        android.util.Slog.i(TAG, "onDevicePresenceEventInternal() id=" + i + " event= " + i2);
        android.companion.AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        java.lang.String packageName = associationById.getPackageName();
        int userId = associationById.getUserId();
        switch (i2) {
            case 0:
            case 2:
            case 4:
                if (associationById.shouldBindWhenPresent()) {
                    bindApplicationIfNeeded(associationById);
                    this.mCompanionAppController.notifyCompanionDevicePresenceEvent(associationById, i2);
                    break;
                }
                break;
            case 1:
            case 3:
            case 5:
                if (this.mCompanionAppController.isCompanionApplicationBound(userId, packageName)) {
                    if (associationById.shouldBindWhenPresent()) {
                        this.mCompanionAppController.notifyCompanionDevicePresenceEvent(associationById, i2);
                    }
                    if (!shouldBindPackage(userId, packageName)) {
                        this.mCompanionAppController.unbindCompanionApplication(userId, packageName);
                        break;
                    }
                }
                break;
            default:
                android.util.Slog.e(TAG, "Event: " + i2 + "is not supported");
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDevicePresenceEventByUuidInternal(com.android.server.companion.presence.ObservableUuid observableUuid, int i) {
        android.util.Slog.i(TAG, "onDevicePresenceEventByUuidInternal() id=" + observableUuid.getUuid() + "for package=" + observableUuid.getPackageName() + " event=" + i);
        java.lang.String packageName = observableUuid.getPackageName();
        int userId = observableUuid.getUserId();
        switch (i) {
            case 2:
                if (!this.mCompanionAppController.isCompanionApplicationBound(userId, packageName)) {
                    this.mCompanionAppController.bindCompanionApplication(userId, packageName, false);
                }
                this.mCompanionAppController.notifyUuidDevicePresenceEvent(observableUuid, i);
                break;
            case 3:
                if (this.mCompanionAppController.isCompanionApplicationBound(userId, packageName)) {
                    this.mCompanionAppController.notifyUuidDevicePresenceEvent(observableUuid, i);
                    if (!shouldBindPackage(userId, packageName)) {
                        this.mCompanionAppController.unbindCompanionApplication(userId, packageName);
                        break;
                    }
                }
                break;
            default:
                android.util.Slog.e(TAG, "Event: " + i + "is not supported");
                break;
        }
    }

    private void bindApplicationIfNeeded(android.companion.AssociationInfo associationInfo) {
        java.lang.String packageName = associationInfo.getPackageName();
        int userId = associationInfo.getUserId();
        boolean isSelfManaged = associationInfo.isSelfManaged();
        if (!this.mCompanionAppController.isCompanionApplicationBound(userId, packageName)) {
            this.mCompanionAppController.bindCompanionApplication(userId, packageName, isSelfManaged);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldBindPackage(int i, @android.annotation.NonNull java.lang.String str) {
        java.util.List<android.companion.AssociationInfo> associationsForPackage = this.mAssociationStore.getAssociationsForPackage(i, str);
        java.util.List<com.android.server.companion.presence.ObservableUuid> observableUuidsForPackage = this.mObservableUuidStore.getObservableUuidsForPackage(i, str);
        for (android.companion.AssociationInfo associationInfo : associationsForPackage) {
            if (associationInfo.shouldBindWhenPresent() && this.mDevicePresenceMonitor.isDevicePresent(associationInfo.getId())) {
                return true;
            }
        }
        java.util.Iterator<com.android.server.companion.presence.ObservableUuid> it = observableUuidsForPackage.iterator();
        while (it.hasNext()) {
            if (this.mDevicePresenceMonitor.isDeviceUuidPresent(it.next().getUuid())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAssociationChangedInternal(int i, android.companion.AssociationInfo associationInfo) {
        int id = associationInfo.getId();
        int userId = associationInfo.getUserId();
        java.lang.String packageName = associationInfo.getPackageName();
        if (i == 1) {
            markIdAsPreviouslyUsedForPackage(id, userId, packageName);
        }
        java.util.List<android.companion.AssociationInfo> associationsForUser = this.mAssociationStore.getAssociationsForUser(userId);
        this.mUserPersistenceHandler.postPersistUserState(userId);
        if (i != 3) {
            notifyListeners(userId, associationsForUser);
        }
        updateAtm(userId, associationsForUser);
    }

    void persistStateForUser(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mAssociationStore.getAssociationsForUser(i));
        arrayList.addAll(this.mAssociationRevokeProcessor.getPendingRoleHolderRemovalAssociationsForUser(i));
        arrayList.addAll(this.mBackupRestoreProcessor.getAssociationsPendingAppInstallForUser(i));
        this.mPersistentStore.persistStateForUser(i, arrayList, getPreviouslyUsedIdsForUser(i));
    }

    private void notifyListeners(final int i, @android.annotation.NonNull final java.util.List<android.companion.AssociationInfo> list) {
        this.mListeners.broadcast(new java.util.function.BiConsumer() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.companion.CompanionDeviceManagerService.lambda$notifyListeners$0(i, list, (android.companion.IOnAssociationsChangedListener) obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifyListeners$0(int i, java.util.List list, android.companion.IOnAssociationsChangedListener iOnAssociationsChangedListener, java.lang.Object obj) {
        int intValue = ((java.lang.Integer) obj).intValue();
        if (intValue == i || intValue == -1) {
            try {
                iOnAssociationsChangedListener.onAssociationsChanged(list);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private void markIdAsPreviouslyUsedForPackage(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        synchronized (this.mPreviouslyUsedIds) {
            try {
                java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map = this.mPreviouslyUsedIds.get(i2);
                if (map == null) {
                    map = new java.util.HashMap<>();
                    this.mPreviouslyUsedIds.put(i2, map);
                }
                map.computeIfAbsent(str, new java.util.function.Function() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        java.util.Set lambda$markIdAsPreviouslyUsedForPackage$1;
                        lambda$markIdAsPreviouslyUsedForPackage$1 = com.android.server.companion.CompanionDeviceManagerService.lambda$markIdAsPreviouslyUsedForPackage$1((java.lang.String) obj);
                        return lambda$markIdAsPreviouslyUsedForPackage$1;
                    }
                }).add(java.lang.Integer.valueOf(i));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.Set lambda$markIdAsPreviouslyUsedForPackage$1(java.lang.String str) {
        return new java.util.HashSet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageRemoveOrDataClearedInternal(int i, @android.annotation.NonNull java.lang.String str) {
        java.util.List<android.companion.AssociationInfo> associationsForPackage = this.mAssociationStore.getAssociationsForPackage(i, str);
        java.util.List<com.android.server.companion.presence.ObservableUuid> observableUuidsForPackage = this.mObservableUuidStore.getObservableUuidsForPackage(i, str);
        java.util.Iterator<android.companion.AssociationInfo> it = associationsForPackage.iterator();
        while (it.hasNext()) {
            this.mAssociationStore.removeAssociation(it.next().getId());
        }
        java.util.Iterator<android.companion.AssociationInfo> it2 = associationsForPackage.iterator();
        while (it2.hasNext()) {
            this.mAssociationRevokeProcessor.maybeRemoveRoleHolderForAssociation(it2.next());
        }
        java.util.Iterator<com.android.server.companion.presence.ObservableUuid> it3 = observableUuidsForPackage.iterator();
        while (it3.hasNext()) {
            this.mObservableUuidStore.removeObservableUuid(i, it3.next().getUuid(), str);
        }
        this.mCompanionAppController.onPackagesChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageModifiedInternal(int i, @android.annotation.NonNull java.lang.String str) {
        java.util.Iterator<android.companion.AssociationInfo> it = this.mAssociationStore.getAssociationsForPackage(i, str).iterator();
        while (it.hasNext()) {
            updateSpecialAccessPermissionForAssociatedPackage(it.next());
        }
        this.mCompanionAppController.onPackagesChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageAddedInternal(int i, @android.annotation.NonNull java.lang.String str) {
        for (android.companion.AssociationInfo associationInfo : this.mBackupRestoreProcessor.getAssociationsPendingAppInstallForUser(i)) {
            if (str.equals(associationInfo.getPackageName())) {
                this.mAssociationRequestsProcessor.maybeGrantRoleAndStoreAssociation(new android.companion.AssociationInfo.Builder(associationInfo).setPending(false).build(), null, null);
                this.mBackupRestoreProcessor.removeFromPendingAppInstall(associationInfo);
            }
        }
    }

    void removeInactiveSelfManagedAssociations() {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long j = android.os.SystemProperties.getLong(SYS_PROP_DEBUG_REMOVAL_TIME_WINDOW, -1L);
        if (j <= 0) {
            j = ASSOCIATION_REMOVAL_TIME_WINDOW_DEFAULT;
        }
        for (android.companion.AssociationInfo associationInfo : this.mAssociationStore.getAssociations()) {
            if (associationInfo.isSelfManaged()) {
                if (currentTimeMillis - associationInfo.getLastTimeConnectedMs() >= j) {
                    int id = associationInfo.getId();
                    android.util.Slog.i(TAG, "Removing inactive self-managed association id=" + id);
                    this.mAssociationRevokeProcessor.disassociateInternal(id);
                }
            }
        }
    }

    class CompanionDeviceManagerImpl extends android.companion.ICompanionDeviceManager.Stub {
        CompanionDeviceManagerImpl() {
        }

        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (java.lang.Throwable th) {
                android.util.Slog.e(com.android.server.companion.CompanionDeviceManagerService.TAG, "Error during IPC", th);
                throw android.util.ExceptionUtils.propagate(th, android.os.RemoteException.class);
            }
        }

        public void associate(android.companion.AssociationRequest associationRequest, android.companion.IAssociationRequestCallback iAssociationRequestCallback, java.lang.String str, int i) throws android.os.RemoteException {
            android.util.Slog.i(com.android.server.companion.CompanionDeviceManagerService.TAG, "associate() request=" + associationRequest + ", package=u" + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str);
            com.android.server.companion.utils.PermissionsUtils.enforceCallerCanManageAssociationsForPackage(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), i, str, "create associations");
            com.android.server.companion.CompanionDeviceManagerService.this.mAssociationRequestsProcessor.processNewAssociationRequest(associationRequest, str, i, iAssociationRequestCallback);
        }

        public android.app.PendingIntent buildAssociationCancellationIntent(java.lang.String str, int i) throws android.os.RemoteException {
            android.util.Slog.i(com.android.server.companion.CompanionDeviceManagerService.TAG, "buildAssociationCancellationIntent() package=u" + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str);
            com.android.server.companion.utils.PermissionsUtils.enforceCallerCanManageAssociationsForPackage(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), i, str, "build association cancellation intent");
            return com.android.server.companion.CompanionDeviceManagerService.this.mAssociationRequestsProcessor.buildAssociationCancellationIntent(str, i);
        }

        public java.util.List<android.companion.AssociationInfo> getAssociations(java.lang.String str, int i) {
            com.android.server.companion.utils.PermissionsUtils.enforceCallerCanManageAssociationsForPackage(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), i, str, "get associations");
            if (!com.android.server.companion.utils.PermissionsUtils.checkCallerCanManageCompanionDevice(com.android.server.companion.CompanionDeviceManagerService.this.getContext())) {
                com.android.server.companion.utils.PackageUtils.enforceUsesCompanionDeviceFeature(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), i, str);
            }
            return com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForPackage(i, str);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_COMPANION_DEVICES")
        public java.util.List<android.companion.AssociationInfo> getAllAssociationsForUser(int i) throws android.os.RemoteException {
            getAllAssociationsForUser_enforcePermission();
            com.android.server.companion.utils.PermissionsUtils.enforceCallerIsSystemOrCanInteractWithUserId(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), i);
            if (i == -1) {
                return java.util.List.copyOf(com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.getAssociations());
            }
            return com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForUser(i);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_COMPANION_DEVICES")
        public void addOnAssociationsChangedListener(android.companion.IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) {
            addOnAssociationsChangedListener_enforcePermission();
            com.android.server.companion.utils.PermissionsUtils.enforceCallerIsSystemOrCanInteractWithUserId(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), i);
            com.android.server.companion.CompanionDeviceManagerService.this.mListeners.register(iOnAssociationsChangedListener, java.lang.Integer.valueOf(i));
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_COMPANION_DEVICES")
        public void removeOnAssociationsChangedListener(android.companion.IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) {
            removeOnAssociationsChangedListener_enforcePermission();
            com.android.server.companion.utils.PermissionsUtils.enforceCallerIsSystemOrCanInteractWithUserId(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), i);
            com.android.server.companion.CompanionDeviceManagerService.this.mListeners.unregister(iOnAssociationsChangedListener);
        }

        @android.annotation.EnforcePermission("android.permission.USE_COMPANION_TRANSPORTS")
        public void addOnTransportsChangedListener(android.companion.IOnTransportsChangedListener iOnTransportsChangedListener) {
            addOnTransportsChangedListener_enforcePermission();
            com.android.server.companion.CompanionDeviceManagerService.this.mTransportManager.addListener(iOnTransportsChangedListener);
        }

        @android.annotation.EnforcePermission("android.permission.USE_COMPANION_TRANSPORTS")
        public void removeOnTransportsChangedListener(android.companion.IOnTransportsChangedListener iOnTransportsChangedListener) {
            removeOnTransportsChangedListener_enforcePermission();
            com.android.server.companion.CompanionDeviceManagerService.this.mTransportManager.removeListener(iOnTransportsChangedListener);
        }

        @android.annotation.EnforcePermission("android.permission.USE_COMPANION_TRANSPORTS")
        public void sendMessage(int i, byte[] bArr, int[] iArr) {
            sendMessage_enforcePermission();
            com.android.server.companion.CompanionDeviceManagerService.this.mTransportManager.sendMessage(i, bArr, iArr);
        }

        @android.annotation.EnforcePermission("android.permission.USE_COMPANION_TRANSPORTS")
        public void addOnMessageReceivedListener(int i, android.companion.IOnMessageReceivedListener iOnMessageReceivedListener) {
            addOnMessageReceivedListener_enforcePermission();
            com.android.server.companion.CompanionDeviceManagerService.this.mTransportManager.addListener(i, iOnMessageReceivedListener);
        }

        @android.annotation.EnforcePermission("android.permission.USE_COMPANION_TRANSPORTS")
        public void removeOnMessageReceivedListener(int i, android.companion.IOnMessageReceivedListener iOnMessageReceivedListener) {
            removeOnMessageReceivedListener_enforcePermission();
            com.android.server.companion.CompanionDeviceManagerService.this.mTransportManager.removeListener(i, iOnMessageReceivedListener);
        }

        @java.lang.Deprecated
        public void legacyDisassociate(java.lang.String str, java.lang.String str2, int i) {
            android.util.Log.i(com.android.server.companion.CompanionDeviceManagerService.TAG, "legacyDisassociate() pkg=u" + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str2 + ", macAddress=" + str);
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(str2);
            com.android.server.companion.CompanionDeviceManagerService.this.mAssociationRevokeProcessor.disassociateInternal(com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i, str2, str).getId());
        }

        public void disassociate(int i) {
            android.util.Log.i(com.android.server.companion.CompanionDeviceManagerService.TAG, "disassociate() associationId=" + i);
            com.android.server.companion.CompanionDeviceManagerService.this.mAssociationRevokeProcessor.disassociateInternal(com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i).getId());
        }

        public android.app.PendingIntent requestNotificationAccess(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            int callingUid = android.companion.ICompanionDeviceManager.Stub.getCallingUid();
            java.lang.String packageName = componentName.getPackageName();
            checkCanCallNotificationApi(packageName, i);
            if (componentName.flattenToString().length() > 500) {
                throw new java.lang.IllegalArgumentException("Component name is too long.");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.companion.utils.PackageUtils.isRestrictedSettingsAllowed(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), packageName, callingUid)) {
                    return android.app.PendingIntent.getActivityAsUser(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), 0, com.android.internal.notification.NotificationAccessConfirmationActivityContract.launcherIntent(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), i, componentName), 1409286144, null, new android.os.UserHandle(i));
                }
                android.util.Slog.e(com.android.server.companion.CompanionDeviceManagerService.TAG, "Side loaded app must enable restricted setting before request the notification access");
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @java.lang.Deprecated
        public boolean hasNotificationAccess(android.content.ComponentName componentName) throws android.os.RemoteException {
            checkCanCallNotificationApi(componentName.getPackageName(), android.os.UserHandle.getCallingUserId());
            return ((android.app.NotificationManager) com.android.server.companion.CompanionDeviceManagerService.this.getContext().getSystemService(android.app.NotificationManager.class)).isNotificationListenerAccessGranted(componentName);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_COMPANION_DEVICES")
        public boolean isDeviceAssociatedForWifiConnection(java.lang.String str, final java.lang.String str2, int i) {
            isDeviceAssociatedForWifiConnection_enforcePermission();
            if (com.android.server.companion.CompanionDeviceManagerService.this.getContext().getPackageManager().checkPermission("android.permission.COMPANION_APPROVE_WIFI_CONNECTIONS", str) == 0) {
                return true;
            }
            return com.android.internal.util.CollectionUtils.any(com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForPackage(i, str), new java.util.function.Predicate() { // from class: com.android.server.companion.CompanionDeviceManagerService$CompanionDeviceManagerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$isDeviceAssociatedForWifiConnection$0;
                    lambda$isDeviceAssociatedForWifiConnection$0 = com.android.server.companion.CompanionDeviceManagerService.CompanionDeviceManagerImpl.lambda$isDeviceAssociatedForWifiConnection$0(str2, (android.companion.AssociationInfo) obj);
                    return lambda$isDeviceAssociatedForWifiConnection$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$isDeviceAssociatedForWifiConnection$0(java.lang.String str, android.companion.AssociationInfo associationInfo) {
            return associationInfo.isLinkedTo(str);
        }

        @android.annotation.EnforcePermission("android.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE")
        public void registerDevicePresenceListenerService(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            registerDevicePresenceListenerService_enforcePermission();
            registerDevicePresenceListenerActive(str2, str, true);
        }

        @android.annotation.EnforcePermission("android.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE")
        public void unregisterDevicePresenceListenerService(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            unregisterDevicePresenceListenerService_enforcePermission();
            registerDevicePresenceListenerActive(str2, str, false);
        }

        @android.annotation.EnforcePermission("android.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE")
        public void startObservingDevicePresence(android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest, java.lang.String str, int i) {
            startObservingDevicePresence_enforcePermission();
            registerDevicePresenceListener(observingDevicePresenceRequest, str, i, true);
        }

        @android.annotation.EnforcePermission("android.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE")
        public void stopObservingDevicePresence(android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest, java.lang.String str, int i) {
            stopObservingDevicePresence_enforcePermission();
            registerDevicePresenceListener(observingDevicePresenceRequest, str, i, false);
        }

        private void registerDevicePresenceListener(android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest, java.lang.String str, int i, boolean z) {
            com.android.server.companion.utils.PackageUtils.enforceUsesCompanionDeviceFeature(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), i, str);
            com.android.server.companion.utils.PermissionsUtils.enforceCallerIsSystemOr(i, str);
            android.companion.AssociationInfo associationById = com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.getAssociationById(observingDevicePresenceRequest.getAssociationId());
            android.os.ParcelUuid uuid = observingDevicePresenceRequest.getUuid();
            if (uuid != null) {
                com.android.server.companion.utils.PermissionsUtils.enforceCallerCanObservingDevicePresenceByUuid(com.android.server.companion.CompanionDeviceManagerService.this.getContext());
                if (z) {
                    startObservingDevicePresenceByUuid(uuid, str, i);
                    return;
                } else {
                    stopObservingDevicePresenceByUuid(uuid, str, i);
                    return;
                }
            }
            if (associationById == null) {
                throw new java.lang.IllegalArgumentException("App " + str + " is not associated with device " + observingDevicePresenceRequest.getAssociationId() + " for user " + i);
            }
            processDevicePresenceListener(associationById, i, str, z);
        }

        private void startObservingDevicePresenceByUuid(android.os.ParcelUuid parcelUuid, java.lang.String str, int i) {
            java.util.Iterator<com.android.server.companion.presence.ObservableUuid> it = com.android.server.companion.CompanionDeviceManagerService.this.mObservableUuidStore.getObservableUuidsForPackage(i, str).iterator();
            while (it.hasNext()) {
                if (it.next().getUuid().equals(parcelUuid)) {
                    android.util.Slog.i(com.android.server.companion.CompanionDeviceManagerService.TAG, "The uuid: " + parcelUuid + " for package:" + str + "has been already scheduled for observing");
                    return;
                }
            }
            com.android.server.companion.CompanionDeviceManagerService.this.mObservableUuidStore.writeObservableUuid(i, new com.android.server.companion.presence.ObservableUuid(i, parcelUuid, str, java.lang.Long.valueOf(java.lang.System.currentTimeMillis())));
        }

        private void stopObservingDevicePresenceByUuid(android.os.ParcelUuid parcelUuid, java.lang.String str, int i) {
            boolean z;
            java.util.Iterator<com.android.server.companion.presence.ObservableUuid> it = com.android.server.companion.CompanionDeviceManagerService.this.mObservableUuidStore.getObservableUuidsForPackage(i, str).iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                } else if (it.next().getUuid().equals(parcelUuid)) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                android.util.Slog.i(com.android.server.companion.CompanionDeviceManagerService.TAG, "The uuid: " + parcelUuid.toString() + " for package:" + str + "has NOT been scheduled for observing yet");
                return;
            }
            com.android.server.companion.CompanionDeviceManagerService.this.mObservableUuidStore.removeObservableUuid(i, parcelUuid, str);
            com.android.server.companion.CompanionDeviceManagerService.this.mDevicePresenceMonitor.removeCurrentConnectedUuidDevice(parcelUuid);
            if (!com.android.server.companion.CompanionDeviceManagerService.this.shouldBindPackage(i, str)) {
                com.android.server.companion.CompanionDeviceManagerService.this.mCompanionAppController.unbindCompanionApplication(i, str);
            }
        }

        public android.app.PendingIntent buildPermissionTransferUserConsentIntent(java.lang.String str, int i, int i2) {
            return com.android.server.companion.CompanionDeviceManagerService.this.mSystemDataTransferProcessor.buildPermissionTransferUserConsentIntent(str, i, i2);
        }

        public boolean isPermissionTransferUserConsented(java.lang.String str, int i, int i2) {
            return com.android.server.companion.CompanionDeviceManagerService.this.mSystemDataTransferProcessor.isPermissionTransferUserConsented(str, i, i2);
        }

        public void startSystemDataTransfer(java.lang.String str, int i, int i2, android.companion.ISystemDataTransferCallback iSystemDataTransferCallback) {
            com.android.server.companion.CompanionDeviceManagerService.this.mSystemDataTransferProcessor.startSystemDataTransfer(str, i, i2, iSystemDataTransferCallback);
        }

        @android.annotation.EnforcePermission("android.permission.DELIVER_COMPANION_MESSAGES")
        public void attachSystemDataTransport(java.lang.String str, int i, int i2, android.os.ParcelFileDescriptor parcelFileDescriptor) {
            attachSystemDataTransport_enforcePermission();
            com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i2);
            com.android.server.companion.CompanionDeviceManagerService.this.mTransportManager.attachSystemDataTransport(str, i, i2, parcelFileDescriptor);
        }

        @android.annotation.EnforcePermission("android.permission.DELIVER_COMPANION_MESSAGES")
        public void detachSystemDataTransport(java.lang.String str, int i, int i2) {
            detachSystemDataTransport_enforcePermission();
            com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i2);
            com.android.server.companion.CompanionDeviceManagerService.this.mTransportManager.detachSystemDataTransport(str, i, i2);
        }

        public void enableSystemDataSync(int i, int i2) {
            com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i);
            com.android.server.companion.CompanionDeviceManagerService.this.mAssociationRequestsProcessor.enableSystemDataSync(i, i2);
        }

        public void disableSystemDataSync(int i, int i2) {
            com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i);
            com.android.server.companion.CompanionDeviceManagerService.this.mAssociationRequestsProcessor.disableSystemDataSync(i, i2);
        }

        public void enablePermissionsSync(int i) {
            com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i);
            com.android.server.companion.CompanionDeviceManagerService.this.mSystemDataTransferProcessor.enablePermissionsSync(i);
        }

        public void disablePermissionsSync(int i) {
            com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i);
            com.android.server.companion.CompanionDeviceManagerService.this.mSystemDataTransferProcessor.disablePermissionsSync(i);
        }

        public android.companion.datatransfer.PermissionSyncRequest getPermissionSyncRequest(int i) {
            if (com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.getAssociationById(i) == null) {
                return null;
            }
            com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i);
            return com.android.server.companion.CompanionDeviceManagerService.this.mSystemDataTransferProcessor.getPermissionSyncRequest(i);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_COMPANION_DEVICES")
        public void enableSecureTransport(boolean z) {
            enableSecureTransport_enforcePermission();
            com.android.server.companion.CompanionDeviceManagerService.this.mTransportManager.enableSecureTransport(z);
        }

        public void notifyDeviceAppeared(int i) {
            android.companion.AssociationInfo associationWithCallerChecks = com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i);
            if (!associationWithCallerChecks.isSelfManaged()) {
                throw new java.lang.IllegalArgumentException("Association with ID " + i + " is not self-managed. notifyDeviceAppeared(int) can only be called for self-managed associations.");
            }
            android.companion.AssociationInfo build = new android.companion.AssociationInfo.Builder(associationWithCallerChecks).setLastTimeConnected(java.lang.System.currentTimeMillis()).build();
            com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.updateAssociation(build);
            com.android.server.companion.CompanionDeviceManagerService.this.mDevicePresenceMonitor.onSelfManagedDeviceConnected(i);
            java.lang.String deviceProfile = build.getDeviceProfile();
            if ("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION".equals(deviceProfile)) {
                android.util.Slog.i(com.android.server.companion.CompanionDeviceManagerService.TAG, "Enable hint mode for device device profile: " + deviceProfile);
                com.android.server.companion.CompanionDeviceManagerService.this.mPowerManagerInternal.setPowerMode(18, true);
            }
        }

        public void notifyDeviceDisappeared(int i) {
            android.companion.AssociationInfo associationWithCallerChecks = com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i);
            if (!associationWithCallerChecks.isSelfManaged()) {
                throw new java.lang.IllegalArgumentException("Association with ID " + i + " is not self-managed. notifyDeviceAppeared(int) can only be called for self-managed associations.");
            }
            com.android.server.companion.CompanionDeviceManagerService.this.mDevicePresenceMonitor.onSelfManagedDeviceDisconnected(i);
            java.lang.String deviceProfile = associationWithCallerChecks.getDeviceProfile();
            if ("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION".equals(deviceProfile)) {
                android.util.Slog.i(com.android.server.companion.CompanionDeviceManagerService.TAG, "Disable hint mode for device profile: " + deviceProfile);
                com.android.server.companion.CompanionDeviceManagerService.this.mPowerManagerInternal.setPowerMode(18, false);
            }
        }

        public boolean isCompanionApplicationBound(java.lang.String str, int i) {
            return com.android.server.companion.CompanionDeviceManagerService.this.mCompanionAppController.isCompanionApplicationBound(i, str);
        }

        private void registerDevicePresenceListenerActive(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            com.android.server.companion.utils.PermissionsUtils.enforceCallerIsSystemOr(callingUserId, str);
            android.companion.AssociationInfo associationsForPackageWithAddress = com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForPackageWithAddress(callingUserId, str, str2);
            if (associationsForPackageWithAddress == null) {
                throw new android.os.RemoteException(new android.companion.DeviceNotAssociatedException("App " + str + " is not associated with device " + str2 + " for user " + callingUserId));
            }
            processDevicePresenceListener(associationsForPackageWithAddress, callingUserId, str, z);
        }

        private void processDevicePresenceListener(android.companion.AssociationInfo associationInfo, int i, java.lang.String str, boolean z) {
            if (z == associationInfo.isNotifyOnDeviceNearby()) {
                return;
            }
            android.companion.AssociationInfo build = new android.companion.AssociationInfo.Builder(associationInfo).setNotifyOnDeviceNearby(z).build();
            com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.updateAssociation(build);
            int id = build.getId();
            if (z && com.android.server.companion.CompanionDeviceManagerService.this.mDevicePresenceMonitor.isDevicePresent(id)) {
                android.util.Slog.i(com.android.server.companion.CompanionDeviceManagerService.TAG, "Device is already present. Triggering callback.");
                if (com.android.server.companion.CompanionDeviceManagerService.this.mDevicePresenceMonitor.isBlePresent(id) || com.android.server.companion.CompanionDeviceManagerService.this.mDevicePresenceMonitor.isSimulatePresent(id)) {
                    com.android.server.companion.CompanionDeviceManagerService.this.onDeviceAppearedInternal(id);
                    com.android.server.companion.CompanionDeviceManagerService.this.onDevicePresenceEventInternal(id, 0);
                } else if (com.android.server.companion.CompanionDeviceManagerService.this.mDevicePresenceMonitor.isBtConnected(id)) {
                    com.android.server.companion.CompanionDeviceManagerService.this.onDevicePresenceEventInternal(id, 2);
                }
            }
            if (!z && !com.android.server.companion.CompanionDeviceManagerService.this.shouldBindPackage(i, str)) {
                com.android.server.companion.CompanionDeviceManagerService.this.mCompanionAppController.unbindCompanionApplication(i, str);
            }
        }

        @android.annotation.EnforcePermission("android.permission.ASSOCIATE_COMPANION_DEVICES")
        public void createAssociation(java.lang.String str, java.lang.String str2, int i, byte[] bArr) {
            createAssociation_enforcePermission();
            if (!com.android.server.companion.CompanionDeviceManagerService.this.getContext().getPackageManager().hasSigningCertificate(str, bArr, 1)) {
                android.util.Slog.e(com.android.server.companion.CompanionDeviceManagerService.TAG, "Given certificate doesn't match the package certificate.");
            } else {
                com.android.server.companion.CompanionDeviceManagerService.this.createNewAssociation(i, str, android.net.MacAddress.fromString(str2), null, null, false);
            }
        }

        private void checkCanCallNotificationApi(java.lang.String str, int i) {
            com.android.server.companion.utils.PermissionsUtils.enforceCallerIsSystemOr(i, str);
            if (android.companion.ICompanionDeviceManager.Stub.getCallingUid() == 1000) {
                return;
            }
            com.android.server.companion.utils.PackageUtils.enforceUsesCompanionDeviceFeature(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), i, str);
            com.android.internal.util.Preconditions.checkState(!com.android.internal.util.ArrayUtils.isEmpty(com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForPackage(i, str)), "App must have an association before calling this API");
        }

        public boolean canPairWithoutPrompt(java.lang.String str, java.lang.String str2, int i) {
            android.companion.AssociationInfo associationsForPackageWithAddress = com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.getAssociationsForPackageWithAddress(i, str, str2);
            return associationsForPackageWithAddress != null && java.lang.System.currentTimeMillis() - associationsForPackageWithAddress.getTimeApprovedMs() < 600000;
        }

        public void setAssociationTag(int i, java.lang.String str) {
            com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.updateAssociation(new android.companion.AssociationInfo.Builder(com.android.server.companion.CompanionDeviceManagerService.this.getAssociationWithCallerChecks(i)).setTag(str).build());
        }

        public void clearAssociationTag(int i) {
            setAssociationTag(i, null);
        }

        public byte[] getBackupPayload(int i) {
            android.util.Log.i(com.android.server.companion.CompanionDeviceManagerService.TAG, "getBackupPayload() userId=" + i);
            return com.android.server.companion.CompanionDeviceManagerService.this.mBackupRestoreProcessor.getBackupPayload(i);
        }

        public void applyRestoredPayload(byte[] bArr, int i) {
            android.util.Log.i(com.android.server.companion.CompanionDeviceManagerService.TAG, "applyRestoredPayload() userId=" + i);
            com.android.server.companion.CompanionDeviceManagerService.this.mBackupRestoreProcessor.applyRestoredPayload(bArr, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int handleShellCommand(@android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor2, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr) {
            return new com.android.server.companion.CompanionDeviceShellCommand(com.android.server.companion.CompanionDeviceManagerService.this, com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore, com.android.server.companion.CompanionDeviceManagerService.this.mDevicePresenceMonitor, com.android.server.companion.CompanionDeviceManagerService.this.mTransportManager, com.android.server.companion.CompanionDeviceManagerService.this.mSystemDataTransferProcessor, com.android.server.companion.CompanionDeviceManagerService.this.mAssociationRequestsProcessor, com.android.server.companion.CompanionDeviceManagerService.this.mBackupRestoreProcessor, com.android.server.companion.CompanionDeviceManagerService.this.mAssociationRevokeProcessor).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        }

        public void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
            if (!com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(com.android.server.companion.CompanionDeviceManagerService.this.getContext(), com.android.server.companion.CompanionDeviceManagerService.TAG, printWriter)) {
                return;
            }
            com.android.server.companion.CompanionDeviceManagerService.this.mAssociationStore.dump(printWriter);
            com.android.server.companion.CompanionDeviceManagerService.this.mDevicePresenceMonitor.dump(printWriter);
            com.android.server.companion.CompanionDeviceManagerService.this.mCompanionAppController.dump(printWriter);
            com.android.server.companion.CompanionDeviceManagerService.this.mTransportManager.dump(printWriter);
            com.android.server.companion.CompanionDeviceManagerService.this.mSystemDataTransferRequestStore.dump(printWriter);
        }
    }

    void createNewAssociation(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.net.MacAddress macAddress, @android.annotation.Nullable java.lang.CharSequence charSequence, @android.annotation.Nullable java.lang.String str2, boolean z) {
        this.mAssociationRequestsProcessor.createAssociation(i, str, macAddress, charSequence, str2, null, z, null, null);
    }

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> getPreviouslyUsedIdsForUser(int i) {
        java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> previouslyUsedIdsForUserLocked;
        synchronized (this.mPreviouslyUsedIds) {
            previouslyUsedIdsForUserLocked = getPreviouslyUsedIdsForUserLocked(i);
        }
        return previouslyUsedIdsForUserLocked;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mPreviouslyUsedIds"})
    private java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> getPreviouslyUsedIdsForUserLocked(int i) {
        java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map = this.mPreviouslyUsedIds.get(i);
        if (map == null) {
            return java.util.Collections.emptyMap();
        }
        return deepUnmodifiableCopy(map);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mPreviouslyUsedIds"})
    private java.util.Set<java.lang.Integer> getPreviouslyUsedIdsForPackageLocked(int i, @android.annotation.NonNull java.lang.String str) {
        java.util.Set<java.lang.Integer> set = getPreviouslyUsedIdsForUserLocked(i).get(str);
        if (set == null) {
            return java.util.Collections.emptySet();
        }
        return set;
    }

    int getNewAssociationIdForPackage(int i, @android.annotation.NonNull java.lang.String str) {
        int firstAssociationIdForUser;
        synchronized (this.mPreviouslyUsedIds) {
            try {
                android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
                java.util.Iterator<android.companion.AssociationInfo> it = this.mAssociationStore.getAssociations().iterator();
                while (it.hasNext()) {
                    sparseBooleanArray.put(it.next().getId(), true);
                }
                java.util.Iterator<android.companion.AssociationInfo> it2 = this.mBackupRestoreProcessor.getAssociationsPendingAppInstallForUser(i).iterator();
                while (it2.hasNext()) {
                    sparseBooleanArray.put(it2.next().getId(), true);
                }
                java.util.Set<java.lang.Integer> previouslyUsedIdsForPackageLocked = getPreviouslyUsedIdsForPackageLocked(i, str);
                firstAssociationIdForUser = getFirstAssociationIdForUser(i);
                int lastAssociationIdForUser = getLastAssociationIdForUser(i);
                do {
                    if (sparseBooleanArray.get(firstAssociationIdForUser) || previouslyUsedIdsForPackageLocked.contains(java.lang.Integer.valueOf(firstAssociationIdForUser))) {
                        firstAssociationIdForUser++;
                    }
                } while (firstAssociationIdForUser <= lastAssociationIdForUser);
                throw new java.lang.RuntimeException("Cannot create a new Association ID for " + str + " for user " + i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return firstAssociationIdForUser;
    }

    void updateSpecialAccessPermissionForAssociatedPackage(android.companion.AssociationInfo associationInfo) {
        final android.content.pm.PackageInfo packageInfo = com.android.server.companion.utils.PackageUtils.getPackageInfo(getContext(), associationInfo.getUserId(), associationInfo.getPackageName());
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.companion.CompanionDeviceManagerService$$ExternalSyntheticLambda2
            public final void runOrThrow() {
                com.android.server.companion.CompanionDeviceManagerService.this.lambda$updateSpecialAccessPermissionForAssociatedPackage$2(packageInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateSpecialAccessPermissionAsSystem, reason: merged with bridge method [inline-methods] */
    public void lambda$updateSpecialAccessPermissionForAssociatedPackage$2(android.content.pm.PackageInfo packageInfo) {
        if (packageInfo == null) {
            return;
        }
        if (containsEither(packageInfo.requestedPermissions, "android.permission.RUN_IN_BACKGROUND", "android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND")) {
            this.mPowerWhitelistManager.addToWhitelist(packageInfo.packageName);
        } else {
            try {
                this.mPowerWhitelistManager.removeFromWhitelist(packageInfo.packageName);
            } catch (java.lang.UnsupportedOperationException e) {
                android.util.Slog.w(TAG, packageInfo.packageName + " can't be removed from power save whitelist. It might due to the package is whitelisted by the system.");
            }
        }
        android.net.NetworkPolicyManager from = android.net.NetworkPolicyManager.from(getContext());
        try {
            if (containsEither(packageInfo.requestedPermissions, "android.permission.USE_DATA_IN_BACKGROUND", "android.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND")) {
                from.addUidPolicy(packageInfo.applicationInfo.uid, 4);
            } else {
                from.removeUidPolicy(packageInfo.applicationInfo.uid, 4);
            }
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Slog.e(TAG, e2.getMessage());
        }
        exemptFromAutoRevoke(packageInfo.packageName, packageInfo.applicationInfo.uid);
    }

    private void exemptFromAutoRevoke(java.lang.String str, int i) {
        try {
            this.mAppOpsManager.setMode(97, i, str, 1);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Error while granting auto revoke exemption for " + str, e);
        }
    }

    private void updateAtm(int i, java.util.List<android.companion.AssociationInfo> list) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<android.companion.AssociationInfo> it = list.iterator();
        while (it.hasNext()) {
            int packageUid = this.mPackageManagerInternal.getPackageUid(it.next().getPackageName(), 0L, i);
            if (packageUid >= 0) {
                arraySet.add(java.lang.Integer.valueOf(packageUid));
            }
        }
        if (this.mAtmInternal != null) {
            this.mAtmInternal.setCompanionAppUids(i, arraySet);
        }
        if (this.mAmInternal != null) {
            this.mAmInternal.setCompanionAppUids(i, new android.util.ArraySet((java.util.Collection) arraySet));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeGrantAutoRevokeExemptions() {
        android.util.Slog.d(TAG, "maybeGrantAutoRevokeExemptions()");
        android.content.pm.PackageManager packageManager = getContext().getPackageManager();
        for (int i : ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserIds()) {
            android.content.SharedPreferences sharedPreferences = getContext().getSharedPreferences(new java.io.File(android.os.Environment.getUserSystemDirectory(i), PREF_FILE_NAME), 0);
            if (!sharedPreferences.getBoolean(PREF_KEY_AUTO_REVOKE_GRANTS_DONE, false)) {
                try {
                    for (android.companion.AssociationInfo associationInfo : this.mAssociationStore.getAssociationsForUser(i)) {
                        try {
                            exemptFromAutoRevoke(associationInfo.getPackageName(), packageManager.getPackageUidAsUser(associationInfo.getPackageName(), i));
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                            android.util.Slog.w(TAG, "Unknown companion package: " + associationInfo.getPackageName(), e);
                        }
                    }
                } finally {
                    sharedPreferences.edit().putBoolean(PREF_KEY_AUTO_REVOKE_GRANTS_DONE, true).apply();
                }
            }
        }
    }

    static int getFirstAssociationIdForUser(int i) {
        return (i * ASSOCIATIONS_IDS_PER_USER_RANGE) + 1;
    }

    static int getLastAssociationIdForUser(int i) {
        return (i + 1) * ASSOCIATIONS_IDS_PER_USER_RANGE;
    }

    private static java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> deepUnmodifiableCopy(java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map) {
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.util.Map.Entry<java.lang.String, java.util.Set<java.lang.Integer>> entry : map.entrySet()) {
            hashMap.put(entry.getKey(), java.util.Collections.unmodifiableSet(new java.util.HashSet(entry.getValue())));
        }
        return java.util.Collections.unmodifiableMap(hashMap);
    }

    private static <T> boolean containsEither(T[] tArr, T t, T t2) {
        return com.android.internal.util.ArrayUtils.contains(tArr, t) || com.android.internal.util.ArrayUtils.contains(tArr, t2);
    }

    private class LocalService implements com.android.server.companion.CompanionDeviceManagerServiceInternal {
        private LocalService() {
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void removeInactiveSelfManagedAssociations() {
            com.android.server.companion.CompanionDeviceManagerService.this.removeInactiveSelfManagedAssociations();
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void registerCallMetadataSyncCallback(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncControllerCallback crossDeviceSyncControllerCallback, int i) {
            if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM)) {
                com.android.server.companion.CompanionDeviceManagerService.this.mCrossDeviceSyncController.registerCallMetadataSyncCallback(crossDeviceSyncControllerCallback, i);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void crossDeviceSync(int i, java.util.Collection<com.android.server.companion.datatransfer.contextsync.CrossDeviceCall> collection) {
            if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM)) {
                com.android.server.companion.CompanionDeviceManagerService.this.mCrossDeviceSyncController.syncToAllDevicesForUserId(i, collection);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void crossDeviceSync(android.companion.AssociationInfo associationInfo, java.util.Collection<com.android.server.companion.datatransfer.contextsync.CrossDeviceCall> collection) {
            if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM)) {
                com.android.server.companion.CompanionDeviceManagerService.this.mCrossDeviceSyncController.syncToSingleDevice(associationInfo, collection);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void sendCrossDeviceSyncMessage(int i, byte[] bArr) {
            if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM)) {
                com.android.server.companion.CompanionDeviceManagerService.this.mCrossDeviceSyncController.syncMessageToDevice(i, bArr);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void sendCrossDeviceSyncMessageToAllDevices(int i, byte[] bArr) {
            if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM)) {
                com.android.server.companion.CompanionDeviceManagerService.this.mCrossDeviceSyncController.syncMessageToAllDevicesForUserId(i, bArr);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void addSelfOwnedCallId(java.lang.String str) {
            if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM)) {
                com.android.server.companion.CompanionDeviceManagerService.this.mCrossDeviceSyncController.addSelfOwnedCallId(str);
            }
        }

        @Override // com.android.server.companion.CompanionDeviceManagerServiceInternal
        public void removeSelfOwnedCallId(java.lang.String str) {
            if (com.android.server.companion.CompanionDeviceConfig.isEnabled(com.android.server.companion.CompanionDeviceConfig.ENABLE_CONTEXT_SYNC_TELECOM)) {
                com.android.server.companion.CompanionDeviceManagerService.this.mCrossDeviceSyncController.removeSelfOwnedCallId(str);
            }
        }
    }

    void persistState() {
        this.mUserPersistenceHandler.clearMessages();
        java.util.Iterator it = this.mUserManager.getAliveUsers().iterator();
        while (it.hasNext()) {
            persistStateForUser(((android.content.pm.UserInfo) it.next()).id);
        }
    }

    @android.annotation.SuppressLint({"HandlerLeak"})
    private class PersistUserStateHandler extends android.os.Handler {
        PersistUserStateHandler() {
            super(com.android.internal.os.BackgroundThread.get().getLooper());
        }

        synchronized void postPersistUserState(int i) {
            if (!hasMessages(i)) {
                sendMessage(obtainMessage(i));
            }
        }

        synchronized void clearMessages() {
            removeCallbacksAndMessages(null);
        }

        @Override // android.os.Handler
        public void handleMessage(@android.annotation.NonNull android.os.Message message) {
            com.android.server.companion.CompanionDeviceManagerService.this.persistStateForUser(message.what);
        }
    }

    void postPersistUserState(int i) {
        this.mUserPersistenceHandler.postPersistUserState(i);
    }

    static class PerUserAssociationSet extends com.android.internal.infra.PerUser<java.util.Set<android.companion.AssociationInfo>> {
        PerUserAssociationSet() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @android.annotation.NonNull
        public java.util.Set<android.companion.AssociationInfo> create(int i) {
            return new android.util.ArraySet();
        }
    }
}
