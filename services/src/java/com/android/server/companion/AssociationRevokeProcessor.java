package com.android.server.companion;

@android.annotation.SuppressLint({"LongLogTag"})
/* loaded from: classes.dex */
public class AssociationRevokeProcessor {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "CDM_AssociationRevokeProcessor";
    private final android.app.ActivityManager mActivityManager;

    @android.annotation.NonNull
    private final com.android.server.companion.AssociationStoreImpl mAssociationStore;

    @android.annotation.NonNull
    private final com.android.server.companion.CompanionApplicationController mCompanionAppController;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.companion.presence.CompanionDevicePresenceMonitor mDevicePresenceMonitor;
    private final com.android.server.companion.AssociationRevokeProcessor.OnPackageVisibilityChangeListener mOnPackageVisibilityChangeListener;

    @android.annotation.NonNull
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;

    @android.annotation.NonNull
    private final com.android.server.companion.CompanionDeviceManagerService mService;

    @android.annotation.NonNull
    private final com.android.server.companion.datatransfer.SystemDataTransferRequestStore mSystemDataTransferRequestStore;

    @com.android.internal.annotations.GuardedBy({"mRevokedAssociationsPendingRoleHolderRemoval"})
    private final com.android.server.companion.CompanionDeviceManagerService.PerUserAssociationSet mRevokedAssociationsPendingRoleHolderRemoval = new com.android.server.companion.CompanionDeviceManagerService.PerUserAssociationSet();

    @com.android.internal.annotations.GuardedBy({"mRevokedAssociationsPendingRoleHolderRemoval"})
    private final java.util.Map<java.lang.Integer, java.lang.String> mUidsPendingRoleHolderRemoval = new java.util.HashMap();

    AssociationRevokeProcessor(@android.annotation.NonNull com.android.server.companion.CompanionDeviceManagerService companionDeviceManagerService, @android.annotation.NonNull com.android.server.companion.AssociationStoreImpl associationStoreImpl, @android.annotation.NonNull android.content.pm.PackageManagerInternal packageManagerInternal, @android.annotation.NonNull com.android.server.companion.presence.CompanionDevicePresenceMonitor companionDevicePresenceMonitor, @android.annotation.NonNull com.android.server.companion.CompanionApplicationController companionApplicationController, @android.annotation.NonNull com.android.server.companion.datatransfer.SystemDataTransferRequestStore systemDataTransferRequestStore) {
        this.mService = companionDeviceManagerService;
        this.mContext = companionDeviceManagerService.getContext();
        this.mActivityManager = (android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class);
        this.mAssociationStore = associationStoreImpl;
        this.mPackageManagerInternal = packageManagerInternal;
        this.mOnPackageVisibilityChangeListener = new com.android.server.companion.AssociationRevokeProcessor.OnPackageVisibilityChangeListener(this.mActivityManager);
        this.mDevicePresenceMonitor = companionDevicePresenceMonitor;
        this.mCompanionAppController = companionApplicationController;
        this.mSystemDataTransferRequestStore = systemDataTransferRequestStore;
    }

    void disassociateInternal(int i) {
        android.companion.AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        int userId = associationById.getUserId();
        java.lang.String packageName = associationById.getPackageName();
        java.lang.String deviceProfile = associationById.getDeviceProfile();
        if (!maybeRemoveRoleHolderForAssociation(associationById)) {
            addToPendingRoleHolderRemoval(associationById);
        }
        boolean isDevicePresent = this.mDevicePresenceMonitor.isDevicePresent(i);
        this.mAssociationStore.removeAssociation(i);
        com.android.server.companion.utils.MetricUtils.logRemoveAssociation(deviceProfile);
        this.mSystemDataTransferRequestStore.removeRequestsByAssociationId(userId, i);
        if (isDevicePresent && associationById.isNotifyOnDeviceNearby() && !com.android.internal.util.CollectionUtils.any(this.mAssociationStore.getAssociationsForPackage(userId, packageName), new java.util.function.Predicate() { // from class: com.android.server.companion.AssociationRevokeProcessor$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$disassociateInternal$0;
                lambda$disassociateInternal$0 = com.android.server.companion.AssociationRevokeProcessor.this.lambda$disassociateInternal$0((android.companion.AssociationInfo) obj);
                return lambda$disassociateInternal$0;
            }
        })) {
            this.mCompanionAppController.unbindCompanionApplication(userId, packageName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$disassociateInternal$0(android.companion.AssociationInfo associationInfo) {
        return associationInfo.isNotifyOnDeviceNearby() && this.mDevicePresenceMonitor.isDevicePresent(associationInfo.getId());
    }

    boolean maybeRemoveRoleHolderForAssociation(@android.annotation.NonNull android.companion.AssociationInfo associationInfo) {
        final java.lang.String deviceProfile = associationInfo.getDeviceProfile();
        if (deviceProfile == null || deviceProfile.equals("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION")) {
            return true;
        }
        final int id = associationInfo.getId();
        int userId = associationInfo.getUserId();
        java.lang.String packageName = associationInfo.getPackageName();
        if (com.android.internal.util.CollectionUtils.any(this.mAssociationStore.getAssociationsForPackage(userId, packageName), new java.util.function.Predicate() { // from class: com.android.server.companion.AssociationRevokeProcessor$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$maybeRemoveRoleHolderForAssociation$1;
                lambda$maybeRemoveRoleHolderForAssociation$1 = com.android.server.companion.AssociationRevokeProcessor.lambda$maybeRemoveRoleHolderForAssociation$1(deviceProfile, id, (android.companion.AssociationInfo) obj);
                return lambda$maybeRemoveRoleHolderForAssociation$1;
            }
        })) {
            return true;
        }
        if (getPackageProcessImportance(userId, packageName) <= 200) {
            android.util.Slog.i(TAG, "Cannot remove role holder for the removed association id=" + id + " now - process is visible.");
            return false;
        }
        com.android.server.companion.utils.RolesUtils.removeRoleHolderForAssociation(this.mContext, associationInfo.getUserId(), associationInfo.getPackageName(), associationInfo.getDeviceProfile());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$maybeRemoveRoleHolderForAssociation$1(java.lang.String str, int i, android.companion.AssociationInfo associationInfo) {
        return str.equals(associationInfo.getDeviceProfile()) && i != associationInfo.getId();
    }

    @android.annotation.SuppressLint({"MissingPermission"})
    private int getPackageProcessImportance(final int i, @android.annotation.NonNull final java.lang.String str) {
        return ((java.lang.Integer) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.companion.AssociationRevokeProcessor$$ExternalSyntheticLambda0
            public final java.lang.Object getOrThrow() {
                java.lang.Integer lambda$getPackageProcessImportance$2;
                lambda$getPackageProcessImportance$2 = com.android.server.companion.AssociationRevokeProcessor.this.lambda$getPackageProcessImportance$2(str, i);
                return lambda$getPackageProcessImportance$2;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$getPackageProcessImportance$2(java.lang.String str, int i) throws java.lang.Exception {
        return java.lang.Integer.valueOf(this.mActivityManager.getUidImportance(this.mPackageManagerInternal.getPackageUid(str, 0L, i)));
    }

    void addToPendingRoleHolderRemoval(@android.annotation.NonNull android.companion.AssociationInfo associationInfo) {
        android.companion.AssociationInfo build = new android.companion.AssociationInfo.Builder(associationInfo).setRevoked(true).build();
        java.lang.String packageName = build.getPackageName();
        int packageUid = this.mPackageManagerInternal.getPackageUid(packageName, 0L, build.getUserId());
        synchronized (this.mRevokedAssociationsPendingRoleHolderRemoval) {
            try {
                ((java.util.Set) this.mRevokedAssociationsPendingRoleHolderRemoval.forUser(build.getUserId())).add(build);
                if (!this.mUidsPendingRoleHolderRemoval.containsKey(java.lang.Integer.valueOf(packageUid))) {
                    this.mUidsPendingRoleHolderRemoval.put(java.lang.Integer.valueOf(packageUid), packageName);
                    if (this.mUidsPendingRoleHolderRemoval.size() == 1) {
                        this.mOnPackageVisibilityChangeListener.startListening();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeFromPendingRoleHolderRemoval(@android.annotation.NonNull android.companion.AssociationInfo associationInfo) {
        final java.lang.String packageName = associationInfo.getPackageName();
        int userId = associationInfo.getUserId();
        int packageUid = this.mPackageManagerInternal.getPackageUid(packageName, 0L, userId);
        synchronized (this.mRevokedAssociationsPendingRoleHolderRemoval) {
            try {
                ((java.util.Set) this.mRevokedAssociationsPendingRoleHolderRemoval.forUser(userId)).remove(associationInfo);
                if (!com.android.internal.util.CollectionUtils.any(getPendingRoleHolderRemovalAssociationsForUser(userId), new java.util.function.Predicate() { // from class: com.android.server.companion.AssociationRevokeProcessor$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$removeFromPendingRoleHolderRemoval$3;
                        lambda$removeFromPendingRoleHolderRemoval$3 = com.android.server.companion.AssociationRevokeProcessor.lambda$removeFromPendingRoleHolderRemoval$3(packageName, (android.companion.AssociationInfo) obj);
                        return lambda$removeFromPendingRoleHolderRemoval$3;
                    }
                })) {
                    this.mUidsPendingRoleHolderRemoval.remove(java.lang.Integer.valueOf(packageUid));
                }
                if (this.mUidsPendingRoleHolderRemoval.isEmpty()) {
                    this.mOnPackageVisibilityChangeListener.stopListening();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeFromPendingRoleHolderRemoval$3(java.lang.String str, android.companion.AssociationInfo associationInfo) {
        return str.equals(associationInfo.getPackageName());
    }

    @android.annotation.NonNull
    java.util.Set<android.companion.AssociationInfo> getPendingRoleHolderRemovalAssociationsForUser(int i) {
        android.util.ArraySet arraySet;
        synchronized (this.mRevokedAssociationsPendingRoleHolderRemoval) {
            arraySet = new android.util.ArraySet((java.util.Collection) this.mRevokedAssociationsPendingRoleHolderRemoval.forUser(i));
        }
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getPackageNameByUid(int i) {
        java.lang.String str;
        synchronized (this.mRevokedAssociationsPendingRoleHolderRemoval) {
            str = this.mUidsPendingRoleHolderRemoval.get(java.lang.Integer.valueOf(i));
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class OnPackageVisibilityChangeListener implements android.app.ActivityManager.OnUidImportanceListener {

        @android.annotation.NonNull
        final android.app.ActivityManager mAm;

        OnPackageVisibilityChangeListener(@android.annotation.NonNull android.app.ActivityManager activityManager) {
            this.mAm = activityManager;
        }

        @android.annotation.SuppressLint({"MissingPermission"})
        void startListening() {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.companion.AssociationRevokeProcessor$OnPackageVisibilityChangeListener$$ExternalSyntheticLambda1
                public final void runOrThrow() {
                    com.android.server.companion.AssociationRevokeProcessor.OnPackageVisibilityChangeListener.this.lambda$startListening$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startListening$0() throws java.lang.Exception {
            this.mAm.addOnUidImportanceListener(this, 200);
        }

        @android.annotation.SuppressLint({"MissingPermission"})
        void stopListening() {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.companion.AssociationRevokeProcessor$OnPackageVisibilityChangeListener$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    com.android.server.companion.AssociationRevokeProcessor.OnPackageVisibilityChangeListener.this.lambda$stopListening$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stopListening$1() throws java.lang.Exception {
            this.mAm.removeOnUidImportanceListener(this);
        }

        public void onUidImportance(int i, int i2) {
            java.lang.String packageNameByUid;
            if (i2 <= 200 || (packageNameByUid = com.android.server.companion.AssociationRevokeProcessor.this.getPackageNameByUid(i)) == null) {
                return;
            }
            int userId = android.os.UserHandle.getUserId(i);
            boolean z = false;
            for (android.companion.AssociationInfo associationInfo : com.android.server.companion.AssociationRevokeProcessor.this.getPendingRoleHolderRemovalAssociationsForUser(userId)) {
                if (packageNameByUid.equals(associationInfo.getPackageName()) && com.android.server.companion.AssociationRevokeProcessor.this.maybeRemoveRoleHolderForAssociation(associationInfo)) {
                    com.android.server.companion.AssociationRevokeProcessor.this.removeFromPendingRoleHolderRemoval(associationInfo);
                    z = true;
                }
            }
            if (z) {
                com.android.server.companion.AssociationRevokeProcessor.this.mService.postPersistUserState(userId);
            }
        }
    }
}
