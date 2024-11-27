package com.android.server.companion;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
class BackupRestoreProcessor {
    private static final int BACKUP_AND_RESTORE_VERSION = 0;
    static final java.lang.String TAG = "CDM_BackupRestoreProcessor";

    @android.annotation.NonNull
    private final com.android.server.companion.AssociationRequestsProcessor mAssociationRequestsProcessor;

    @android.annotation.NonNull
    private final com.android.server.companion.AssociationStoreImpl mAssociationStore;

    @com.android.internal.annotations.GuardedBy({"mAssociationsPendingAppInstall"})
    private final com.android.server.companion.CompanionDeviceManagerService.PerUserAssociationSet mAssociationsPendingAppInstall = new com.android.server.companion.CompanionDeviceManagerService.PerUserAssociationSet();

    @android.annotation.NonNull
    private final android.content.pm.PackageManagerInternal mPackageManager;

    @android.annotation.NonNull
    private final com.android.server.companion.PersistentDataStore mPersistentStore;

    @android.annotation.NonNull
    private final com.android.server.companion.CompanionDeviceManagerService mService;

    @android.annotation.NonNull
    private final com.android.server.companion.datatransfer.SystemDataTransferRequestStore mSystemDataTransferRequestStore;

    BackupRestoreProcessor(@android.annotation.NonNull com.android.server.companion.CompanionDeviceManagerService companionDeviceManagerService, @android.annotation.NonNull com.android.server.companion.AssociationStoreImpl associationStoreImpl, @android.annotation.NonNull com.android.server.companion.PersistentDataStore persistentDataStore, @android.annotation.NonNull com.android.server.companion.datatransfer.SystemDataTransferRequestStore systemDataTransferRequestStore, @android.annotation.NonNull com.android.server.companion.AssociationRequestsProcessor associationRequestsProcessor) {
        this.mService = companionDeviceManagerService;
        this.mPackageManager = companionDeviceManagerService.mPackageManagerInternal;
        this.mAssociationStore = associationStoreImpl;
        this.mPersistentStore = persistentDataStore;
        this.mSystemDataTransferRequestStore = systemDataTransferRequestStore;
        this.mAssociationRequestsProcessor = associationRequestsProcessor;
    }

    byte[] getBackupPayload(int i) {
        this.mService.persistStateForUser(i);
        byte[] backupPayload = this.mPersistentStore.getBackupPayload(i);
        int length = backupPayload.length;
        byte[] backupPayload2 = this.mSystemDataTransferRequestStore.getBackupPayload(i);
        int length2 = backupPayload2.length;
        return java.nio.ByteBuffer.allocate(length + 12 + length2).putInt(0).putInt(length).put(backupPayload).putInt(length2).put(backupPayload2).array();
    }

    void applyRestoredPayload(byte[] bArr, int i) {
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
        if (wrap.getInt() != 0) {
            android.util.Slog.e(TAG, "Unsupported backup payload version");
            return;
        }
        byte[] bArr2 = new byte[wrap.getInt()];
        wrap.get(bArr2);
        java.util.HashSet<android.companion.AssociationInfo> hashSet = new java.util.HashSet();
        this.mPersistentStore.readStateFromPayload(bArr2, i, hashSet, new java.util.HashMap());
        byte[] bArr3 = new byte[wrap.getInt()];
        wrap.get(bArr3);
        java.util.List<android.companion.datatransfer.SystemDataTransferRequest> readRequestsFromPayload = this.mSystemDataTransferRequestStore.readRequestsFromPayload(bArr3, i);
        java.util.List<android.content.pm.ApplicationInfo> installedApplications = this.mPackageManager.getInstalledApplications(0L, i, android.os.UserHandle.getCallingUserId());
        for (final android.companion.AssociationInfo associationInfo : hashSet) {
            if (!associationInfo.isRevoked()) {
                java.util.List<android.companion.datatransfer.SystemDataTransferRequest> filter = com.android.internal.util.CollectionUtils.filter(readRequestsFromPayload, new java.util.function.Predicate() { // from class: com.android.server.companion.BackupRestoreProcessor$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$applyRestoredPayload$0;
                        lambda$applyRestoredPayload$0 = com.android.server.companion.BackupRestoreProcessor.lambda$applyRestoredPayload$0(associationInfo, (android.companion.datatransfer.SystemDataTransferRequest) obj);
                        return lambda$applyRestoredPayload$0;
                    }
                });
                if (!handleCollision(i, associationInfo, filter)) {
                    final java.lang.String packageName = associationInfo.getPackageName();
                    int newAssociationIdForPackage = this.mService.getNewAssociationIdForPackage(i, packageName);
                    android.companion.AssociationInfo build = new android.companion.AssociationInfo.Builder(newAssociationIdForPackage, i, packageName, associationInfo).build();
                    if (installedApplications.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.companion.BackupRestoreProcessor$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$applyRestoredPayload$1;
                            lambda$applyRestoredPayload$1 = com.android.server.companion.BackupRestoreProcessor.lambda$applyRestoredPayload$1(packageName, (android.content.pm.ApplicationInfo) obj);
                            return lambda$applyRestoredPayload$1;
                        }
                    })) {
                        this.mAssociationRequestsProcessor.maybeGrantRoleAndStoreAssociation(build, null, null);
                    } else {
                        addToPendingAppInstall(build);
                    }
                    java.util.Iterator<android.companion.datatransfer.SystemDataTransferRequest> it = filter.iterator();
                    while (it.hasNext()) {
                        android.companion.datatransfer.SystemDataTransferRequest copyWithNewId = it.next().copyWithNewId(newAssociationIdForPackage);
                        copyWithNewId.setUserId(i);
                        this.mSystemDataTransferRequestStore.writeRequest(i, copyWithNewId);
                    }
                }
            }
        }
        this.mService.persistStateForUser(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$applyRestoredPayload$0(android.companion.AssociationInfo associationInfo, android.companion.datatransfer.SystemDataTransferRequest systemDataTransferRequest) {
        return systemDataTransferRequest.getAssociationId() == associationInfo.getId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$applyRestoredPayload$1(java.lang.String str, android.content.pm.ApplicationInfo applicationInfo) {
        return str.equals(applicationInfo.packageName);
    }

    void addToPendingAppInstall(@android.annotation.NonNull android.companion.AssociationInfo associationInfo) {
        android.companion.AssociationInfo build = new android.companion.AssociationInfo.Builder(associationInfo).setPending(true).build();
        synchronized (this.mAssociationsPendingAppInstall) {
            ((java.util.Set) this.mAssociationsPendingAppInstall.forUser(build.getUserId())).add(build);
        }
    }

    void removeFromPendingAppInstall(@android.annotation.NonNull android.companion.AssociationInfo associationInfo) {
        synchronized (this.mAssociationsPendingAppInstall) {
            ((java.util.Set) this.mAssociationsPendingAppInstall.forUser(associationInfo.getUserId())).remove(associationInfo);
        }
    }

    @android.annotation.NonNull
    java.util.Set<android.companion.AssociationInfo> getAssociationsPendingAppInstallForUser(int i) {
        android.util.ArraySet arraySet;
        synchronized (this.mAssociationsPendingAppInstall) {
            arraySet = new android.util.ArraySet((java.util.Collection) this.mAssociationsPendingAppInstall.forUser(i));
        }
        return arraySet;
    }

    private boolean handleCollision(int i, final android.companion.AssociationInfo associationInfo, java.util.List<android.companion.datatransfer.SystemDataTransferRequest> list) {
        android.companion.AssociationInfo associationInfo2 = (android.companion.AssociationInfo) com.android.internal.util.CollectionUtils.find(this.mAssociationStore.getAssociationsForPackage(associationInfo.getUserId(), associationInfo.getPackageName()), new java.util.function.Predicate() { // from class: com.android.server.companion.BackupRestoreProcessor$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$handleCollision$2;
                lambda$handleCollision$2 = com.android.server.companion.BackupRestoreProcessor.lambda$handleCollision$2(associationInfo, (android.companion.AssociationInfo) obj);
                return lambda$handleCollision$2;
            }
        });
        if (associationInfo2 == null) {
            return false;
        }
        android.util.Log.d(TAG, "Conflict detected with association id=" + associationInfo2.getId() + " while restoring CDM backup. Keeping local association.");
        java.util.List<android.companion.datatransfer.SystemDataTransferRequest> readRequestsByAssociationId = this.mSystemDataTransferRequestStore.readRequestsByAssociationId(i, associationInfo2.getId());
        for (final android.companion.datatransfer.SystemDataTransferRequest systemDataTransferRequest : list) {
            if (!com.android.internal.util.CollectionUtils.any(readRequestsByAssociationId, new java.util.function.Predicate() { // from class: com.android.server.companion.BackupRestoreProcessor$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$handleCollision$3;
                    lambda$handleCollision$3 = com.android.server.companion.BackupRestoreProcessor.lambda$handleCollision$3(systemDataTransferRequest, (android.companion.datatransfer.SystemDataTransferRequest) obj);
                    return lambda$handleCollision$3;
                }
            })) {
                android.util.Log.d(TAG, "Restoring " + systemDataTransferRequest.getClass().getSimpleName() + " to an existing association id=" + associationInfo2.getId() + ".");
                android.companion.datatransfer.SystemDataTransferRequest copyWithNewId = systemDataTransferRequest.copyWithNewId(associationInfo2.getId());
                copyWithNewId.setUserId(i);
                this.mSystemDataTransferRequestStore.writeRequest(i, copyWithNewId);
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$handleCollision$2(android.companion.AssociationInfo associationInfo, android.companion.AssociationInfo associationInfo2) {
        return java.util.Objects.equals(associationInfo2.getDeviceMacAddress(), associationInfo.getDeviceMacAddress()) && (!android.companion.Flags.associationTag() || java.util.Objects.equals(associationInfo2.getTag(), associationInfo.getTag()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$handleCollision$3(android.companion.datatransfer.SystemDataTransferRequest systemDataTransferRequest, android.companion.datatransfer.SystemDataTransferRequest systemDataTransferRequest2) {
        return systemDataTransferRequest2.getDataType() == systemDataTransferRequest.getDataType();
    }
}
