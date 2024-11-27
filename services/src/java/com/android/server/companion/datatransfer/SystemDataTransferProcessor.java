package com.android.server.companion.datatransfer;

/* loaded from: classes.dex */
public class SystemDataTransferProcessor {
    private static final java.lang.String EXTRA_COMPANION_DEVICE_NAME = "companion_device_name";
    private static final java.lang.String EXTRA_PERMISSION_SYNC_REQUEST = "permission_sync_request";
    private static final java.lang.String EXTRA_SYSTEM_DATA_TRANSFER_RESULT_RECEIVER = "system_data_transfer_result_receiver";
    private static final java.lang.String LOG_TAG = "CDM_SystemDataTransferProcessor";
    private static final int RESULT_CODE_SYSTEM_DATA_TRANSFER_ALLOWED = 0;
    private static final int RESULT_CODE_SYSTEM_DATA_TRANSFER_DISALLOWED = 1;
    private final com.android.server.companion.AssociationStore mAssociationStore;
    private final android.content.ComponentName mCompanionDeviceDataTransferActivity;
    private final android.content.Context mContext;
    private final java.util.concurrent.ExecutorService mExecutor;
    private final android.os.ResultReceiver mOnSystemDataTransferRequestConfirmationReceiver = new android.os.ResultReceiver(android.os.Handler.getMain()) { // from class: com.android.server.companion.datatransfer.SystemDataTransferProcessor.2
        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, android.os.Bundle bundle) {
            android.util.Slog.d(com.android.server.companion.datatransfer.SystemDataTransferProcessor.LOG_TAG, "onReceiveResult() code=" + i + ", data=" + bundle);
            if (i == 0 || i == 1) {
                android.companion.datatransfer.SystemDataTransferRequest systemDataTransferRequest = (android.companion.datatransfer.PermissionSyncRequest) bundle.getParcelable(com.android.server.companion.datatransfer.SystemDataTransferProcessor.EXTRA_PERMISSION_SYNC_REQUEST, android.companion.datatransfer.PermissionSyncRequest.class);
                if (systemDataTransferRequest != null) {
                    systemDataTransferRequest.setUserConsented(i == 0);
                    android.util.Slog.i(com.android.server.companion.datatransfer.SystemDataTransferProcessor.LOG_TAG, "Recording request: " + systemDataTransferRequest);
                    com.android.server.companion.datatransfer.SystemDataTransferProcessor.this.mSystemDataTransferRequestStore.writeRequest(systemDataTransferRequest.getUserId(), systemDataTransferRequest);
                    return;
                }
                return;
            }
            android.util.Slog.e(com.android.server.companion.datatransfer.SystemDataTransferProcessor.LOG_TAG, "Unknown result code:" + i);
        }
    };
    private final android.content.pm.PackageManagerInternal mPackageManager;
    private final android.permission.PermissionControllerManager mPermissionControllerManager;
    private final com.android.server.companion.datatransfer.SystemDataTransferRequestStore mSystemDataTransferRequestStore;
    private final com.android.server.companion.transport.CompanionTransportManager mTransportManager;

    public SystemDataTransferProcessor(com.android.server.companion.CompanionDeviceManagerService companionDeviceManagerService, android.content.pm.PackageManagerInternal packageManagerInternal, com.android.server.companion.AssociationStore associationStore, com.android.server.companion.datatransfer.SystemDataTransferRequestStore systemDataTransferRequestStore, com.android.server.companion.transport.CompanionTransportManager companionTransportManager) {
        this.mContext = companionDeviceManagerService.getContext();
        this.mPackageManager = packageManagerInternal;
        this.mAssociationStore = associationStore;
        this.mSystemDataTransferRequestStore = systemDataTransferRequestStore;
        this.mTransportManager = companionTransportManager;
        this.mTransportManager.addListener(1669491075, new android.companion.IOnMessageReceivedListener() { // from class: com.android.server.companion.datatransfer.SystemDataTransferProcessor.1
            public void onMessageReceived(int i, byte[] bArr) throws android.os.RemoteException {
                com.android.server.companion.datatransfer.SystemDataTransferProcessor.this.onReceivePermissionRestore(bArr);
            }

            public android.os.IBinder asBinder() {
                return null;
            }
        });
        this.mPermissionControllerManager = (android.permission.PermissionControllerManager) this.mContext.getSystemService(android.permission.PermissionControllerManager.class);
        this.mExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();
        this.mCompanionDeviceDataTransferActivity = android.content.ComponentName.createRelative(this.mContext.getString(android.R.string.config_cameraLaunchGestureSensorStringType), ".CompanionDeviceDataTransferActivity");
    }

    @android.annotation.NonNull
    private android.companion.AssociationInfo resolveAssociation(java.lang.String str, int i, int i2) {
        android.companion.AssociationInfo sanitizeWithCallerChecks = com.android.server.companion.utils.PermissionsUtils.sanitizeWithCallerChecks(this.mContext, this.mAssociationStore.getAssociationById(i2));
        if (sanitizeWithCallerChecks == null) {
            throw new android.companion.DeviceNotAssociatedException("Association " + i2 + " is not associated with the app " + str + " for user " + i);
        }
        return sanitizeWithCallerChecks;
    }

    public boolean isPermissionTransferUserConsented(java.lang.String str, int i, int i2) {
        resolveAssociation(str, i, i2);
        android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest = getPermissionSyncRequest(i2);
        if (permissionSyncRequest == null) {
            return false;
        }
        return permissionSyncRequest.isUserConsented();
    }

    public android.app.PendingIntent buildPermissionTransferUserConsentIntent(java.lang.String str, int i, int i2) {
        if (com.android.server.companion.utils.PackageUtils.isPackageAllowlisted(this.mContext, this.mPackageManager, str)) {
            android.util.Slog.i(LOG_TAG, "User consent Intent should be skipped. Returning null.");
            if (getPermissionSyncRequest(i2) == null) {
                android.companion.datatransfer.SystemDataTransferRequest permissionSyncRequest = new android.companion.datatransfer.PermissionSyncRequest(i2);
                permissionSyncRequest.setUserConsented(true);
                this.mSystemDataTransferRequestStore.writeRequest(i, permissionSyncRequest);
                return null;
            }
            return null;
        }
        android.companion.AssociationInfo resolveAssociation = resolveAssociation(str, i, i2);
        android.util.Slog.i(LOG_TAG, "Creating permission sync intent for userId [" + i + "] associationId [" + i2 + "]");
        android.os.Bundle bundle = new android.os.Bundle();
        android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest2 = new android.companion.datatransfer.PermissionSyncRequest(i2);
        permissionSyncRequest2.setUserId(i);
        bundle.putParcelable(EXTRA_PERMISSION_SYNC_REQUEST, permissionSyncRequest2);
        bundle.putCharSequence(EXTRA_COMPANION_DEVICE_NAME, resolveAssociation.getDisplayName());
        bundle.putParcelable(EXTRA_SYSTEM_DATA_TRANSFER_RESULT_RECEIVER, com.android.server.companion.utils.Utils.prepareForIpc(this.mOnSystemDataTransferRequestConfirmationReceiver));
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(this.mCompanionDeviceDataTransferActivity);
        intent.putExtras(bundle);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.app.PendingIntent.getActivityAsUser(this.mContext, i2, intent, 1409286144, android.app.ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(1).toBundle(), android.os.UserHandle.CURRENT);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void startSystemDataTransfer(java.lang.String str, int i, final int i2, final android.companion.ISystemDataTransferCallback iSystemDataTransferCallback) {
        android.util.Slog.i(LOG_TAG, "Start system data transfer for package [" + str + "] userId [" + i + "] associationId [" + i2 + "]");
        resolveAssociation(str, i, i2);
        android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest = getPermissionSyncRequest(i2);
        if (permissionSyncRequest == null || !permissionSyncRequest.isUserConsented()) {
            java.lang.String str2 = "User " + i + " hasn't consented permission sync for associationId [" + i2 + ".";
            android.util.Slog.e(LOG_TAG, str2);
            try {
                iSystemDataTransferCallback.onError(str2);
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mPermissionControllerManager.getRuntimePermissionBackup(android.os.UserHandle.of(i), this.mExecutor, new java.util.function.Consumer() { // from class: com.android.server.companion.datatransfer.SystemDataTransferProcessor$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.companion.datatransfer.SystemDataTransferProcessor.this.lambda$startSystemDataTransfer$0(i2, iSystemDataTransferCallback, (byte[]) obj);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startSystemDataTransfer$0(int i, android.companion.ISystemDataTransferCallback iSystemDataTransferCallback, byte[] bArr) {
        translateFutureToCallback(this.mTransportManager.requestPermissionRestore(i, bArr), iSystemDataTransferCallback);
    }

    public void enablePermissionsSync(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int userId = this.mAssociationStore.getAssociationById(i).getUserId();
            android.companion.datatransfer.SystemDataTransferRequest permissionSyncRequest = new android.companion.datatransfer.PermissionSyncRequest(i);
            permissionSyncRequest.setUserConsented(true);
            this.mSystemDataTransferRequestStore.writeRequest(userId, permissionSyncRequest);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void disablePermissionsSync(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int userId = this.mAssociationStore.getAssociationById(i).getUserId();
            android.companion.datatransfer.SystemDataTransferRequest permissionSyncRequest = new android.companion.datatransfer.PermissionSyncRequest(i);
            permissionSyncRequest.setUserConsented(false);
            this.mSystemDataTransferRequestStore.writeRequest(userId, permissionSyncRequest);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.Nullable
    public android.companion.datatransfer.PermissionSyncRequest getPermissionSyncRequest(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.Iterator<android.companion.datatransfer.SystemDataTransferRequest> it = this.mSystemDataTransferRequestStore.readRequestsByAssociationId(this.mAssociationStore.getAssociationById(i).getUserId(), i).iterator();
            while (it.hasNext()) {
                android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest = (android.companion.datatransfer.SystemDataTransferRequest) it.next();
                if (permissionSyncRequest instanceof android.companion.datatransfer.PermissionSyncRequest) {
                    return permissionSyncRequest;
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void removePermissionSyncRequest(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSystemDataTransferRequestStore.removeRequestsByAssociationId(this.mAssociationStore.getAssociationById(i).getUserId(), i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReceivePermissionRestore(byte[] bArr) {
        if (!android.os.Build.isDebuggable() && !this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch")) {
            android.util.Slog.e(LOG_TAG, "Permissions restore is only available on watch.");
            return;
        }
        android.util.Slog.i(LOG_TAG, "Applying permissions.");
        android.os.UserHandle user = this.mContext.getUser();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mPermissionControllerManager.stageAndApplyRuntimePermissionsBackup(bArr, user);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static void translateFutureToCallback(@android.annotation.NonNull java.util.concurrent.Future<?> future, @android.annotation.Nullable android.companion.ISystemDataTransferCallback iSystemDataTransferCallback) {
        try {
            future.get(15L, java.util.concurrent.TimeUnit.SECONDS);
            if (iSystemDataTransferCallback != null) {
                try {
                    iSystemDataTransferCallback.onResult();
                } catch (android.os.RemoteException e) {
                }
            }
        } catch (java.lang.Exception e2) {
            if (iSystemDataTransferCallback != null) {
                try {
                    iSystemDataTransferCallback.onError(e2.getMessage());
                } catch (android.os.RemoteException e3) {
                }
            }
        }
    }
}
