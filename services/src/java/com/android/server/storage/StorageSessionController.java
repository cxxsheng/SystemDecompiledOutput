package com.android.server.storage;

/* loaded from: classes2.dex */
public final class StorageSessionController {
    private static final java.lang.String TAG = "StorageSessionController";
    private final android.content.Context mContext;
    private volatile int mExternalStorageServiceAppId;
    private volatile android.content.ComponentName mExternalStorageServiceComponent;
    private volatile java.lang.String mExternalStorageServicePackageName;
    private volatile boolean mIsResetting;
    private final android.os.UserManager mUserManager;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.storage.StorageUserConnection> mConnections = new android.util.SparseArray<>();

    public StorageSessionController(android.content.Context context) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
    }

    public int getConnectionUserIdForVolume(android.os.storage.VolumeInfo volumeInfo) {
        boolean isMediaSharedWithParent = ((android.os.UserManager) this.mContext.createContextAsUser(android.os.UserHandle.of(volumeInfo.mountUserId), 0).getSystemService(android.os.UserManager.class)).isMediaSharedWithParent();
        android.content.pm.UserInfo userInfo = this.mUserManager.getUserInfo(volumeInfo.mountUserId);
        if (userInfo != null && isMediaSharedWithParent) {
            return userInfo.profileGroupId;
        }
        return volumeInfo.mountUserId;
    }

    public void onVolumeMount(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.storage.VolumeInfo volumeInfo) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
        com.android.server.storage.StorageUserConnection storageUserConnection;
        if (!shouldHandle(volumeInfo)) {
            return;
        }
        android.util.Slog.i(TAG, "On volume mount " + volumeInfo);
        java.lang.String id = volumeInfo.getId();
        int connectionUserIdForVolume = getConnectionUserIdForVolume(volumeInfo);
        synchronized (this.mLock) {
            try {
                storageUserConnection = this.mConnections.get(connectionUserIdForVolume);
                if (storageUserConnection == null) {
                    android.util.Slog.i(TAG, "Creating connection for user: " + connectionUserIdForVolume);
                    storageUserConnection = new com.android.server.storage.StorageUserConnection(this.mContext, connectionUserIdForVolume, this);
                    this.mConnections.put(connectionUserIdForVolume, storageUserConnection);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.util.Slog.i(TAG, "Creating and starting session with id: " + id);
        storageUserConnection.startSession(id, parcelFileDescriptor, volumeInfo.getPath().getPath(), volumeInfo.getInternalPath().getPath());
    }

    public void notifyVolumeStateChanged(android.os.storage.VolumeInfo volumeInfo) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
        if (!shouldHandle(volumeInfo)) {
            return;
        }
        java.lang.String id = volumeInfo.getId();
        int connectionUserIdForVolume = getConnectionUserIdForVolume(volumeInfo);
        synchronized (this.mLock) {
            try {
                com.android.server.storage.StorageUserConnection storageUserConnection = this.mConnections.get(connectionUserIdForVolume);
                if (storageUserConnection != null) {
                    android.util.Slog.i(TAG, "Notifying volume state changed for session with id: " + id);
                    storageUserConnection.notifyVolumeStateChanged(id, volumeInfo.buildStorageVolume(this.mContext, volumeInfo.getMountUserId(), false));
                } else {
                    android.util.Slog.w(TAG, "No available storage user connection for userId : " + connectionUserIdForVolume);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void freeCache(java.lang.String str, long j) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
        synchronized (this.mLock) {
            try {
                int size = this.mConnections.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.storage.StorageUserConnection storageUserConnection = this.mConnections.get(this.mConnections.keyAt(i));
                    if (storageUserConnection != null) {
                        storageUserConnection.freeCache(str, j);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifyAnrDelayStarted(java.lang.String str, int i, int i2, int i3) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
        com.android.server.storage.StorageUserConnection storageUserConnection;
        int userId = android.os.UserHandle.getUserId(i);
        synchronized (this.mLock) {
            storageUserConnection = this.mConnections.get(userId);
        }
        if (storageUserConnection != null) {
            storageUserConnection.notifyAnrDelayStarted(str, i, i2, i3);
        }
    }

    @android.annotation.Nullable
    public com.android.server.storage.StorageUserConnection onVolumeRemove(android.os.storage.VolumeInfo volumeInfo) {
        if (!shouldHandle(volumeInfo)) {
            return null;
        }
        android.util.Slog.i(TAG, "On volume remove " + volumeInfo);
        java.lang.String id = volumeInfo.getId();
        int connectionUserIdForVolume = getConnectionUserIdForVolume(volumeInfo);
        synchronized (this.mLock) {
            try {
                com.android.server.storage.StorageUserConnection storageUserConnection = this.mConnections.get(connectionUserIdForVolume);
                if (storageUserConnection != null) {
                    android.util.Slog.i(TAG, "Removed session for vol with id: " + id);
                    storageUserConnection.removeSession(id);
                    return storageUserConnection;
                }
                android.util.Slog.w(TAG, "Session already removed for vol with id: " + id);
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onVolumeUnmount(android.os.storage.VolumeInfo volumeInfo) {
        com.android.server.storage.StorageUserConnection onVolumeRemove = onVolumeRemove(volumeInfo);
        android.util.Slog.i(TAG, "On volume unmount " + volumeInfo);
        if (onVolumeRemove != null) {
            java.lang.String id = volumeInfo.getId();
            try {
                onVolumeRemove.removeSessionAndWait(id);
            } catch (com.android.server.storage.StorageSessionController.ExternalStorageServiceException e) {
                android.util.Slog.e(TAG, "Failed to end session for vol with id: " + id, e);
            }
        }
    }

    public void onUnlockUser(int i) throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
        android.util.Slog.i(TAG, "On user unlock " + i);
        if (i == 0) {
            initExternalStorageServiceComponent();
        }
    }

    public void onUserStopping(int i) {
        com.android.server.storage.StorageUserConnection storageUserConnection;
        if (!shouldHandle(null)) {
            return;
        }
        synchronized (this.mLock) {
            storageUserConnection = this.mConnections.get(i);
        }
        if (storageUserConnection != null) {
            android.util.Slog.i(TAG, "Removing all sessions for user: " + i);
            storageUserConnection.removeAllSessions();
            return;
        }
        android.util.Slog.w(TAG, "No connection found for user: " + i);
    }

    public void onReset(android.os.IVold iVold, java.lang.Runnable runnable) {
        if (!shouldHandle(null)) {
            return;
        }
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        synchronized (this.mLock) {
            try {
                this.mIsResetting = true;
                android.util.Slog.i(TAG, "Started resetting external storage service...");
                for (int i = 0; i < this.mConnections.size(); i++) {
                    sparseArray.put(this.mConnections.keyAt(i), this.mConnections.valueAt(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            com.android.server.storage.StorageUserConnection storageUserConnection = (com.android.server.storage.StorageUserConnection) sparseArray.valueAt(i2);
            for (java.lang.String str : storageUserConnection.getAllSessionIds()) {
                try {
                    android.util.Slog.i(TAG, "Unmounting " + str);
                    iVold.unmount(str);
                    android.util.Slog.i(TAG, "Unmounted " + str);
                } catch (android.os.ServiceSpecificException | android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Failed to unmount volume: " + str, e);
                }
                try {
                    android.util.Slog.i(TAG, "Exiting " + str);
                    storageUserConnection.removeSessionAndWait(str);
                    android.util.Slog.i(TAG, "Exited " + str);
                } catch (com.android.server.storage.StorageSessionController.ExternalStorageServiceException | java.lang.IllegalStateException e2) {
                    android.util.Slog.e(TAG, "Failed to exit session: " + str + ". Killing MediaProvider...", e2);
                    killExternalStorageService(sparseArray.keyAt(i2));
                }
            }
            storageUserConnection.close();
        }
        runnable.run();
        synchronized (this.mLock) {
            this.mConnections.clear();
            this.mIsResetting = false;
            android.util.Slog.i(TAG, "Finished resetting external storage service");
        }
    }

    private void initExternalStorageServiceComponent() throws com.android.server.storage.StorageSessionController.ExternalStorageServiceException {
        android.util.Slog.i(TAG, "Initialialising...");
        android.content.pm.ProviderInfo resolveContentProvider = this.mContext.getPackageManager().resolveContentProvider("media", 1835008);
        if (resolveContentProvider == null) {
            throw new com.android.server.storage.StorageSessionController.ExternalStorageServiceException("No valid MediaStore provider found");
        }
        this.mExternalStorageServicePackageName = resolveContentProvider.applicationInfo.packageName;
        this.mExternalStorageServiceAppId = android.os.UserHandle.getAppId(resolveContentProvider.applicationInfo.uid);
        android.content.pm.ServiceInfo resolveExternalStorageServiceAsUser = resolveExternalStorageServiceAsUser(0);
        if (resolveExternalStorageServiceAsUser == null) {
            throw new com.android.server.storage.StorageSessionController.ExternalStorageServiceException("No valid ExternalStorageService component found");
        }
        android.content.ComponentName componentName = new android.content.ComponentName(resolveExternalStorageServiceAsUser.packageName, resolveExternalStorageServiceAsUser.name);
        if (!"android.permission.BIND_EXTERNAL_STORAGE_SERVICE".equals(resolveExternalStorageServiceAsUser.permission)) {
            throw new com.android.server.storage.StorageSessionController.ExternalStorageServiceException(componentName.flattenToShortString() + " does not require permission android.permission.BIND_EXTERNAL_STORAGE_SERVICE");
        }
        this.mExternalStorageServiceComponent = componentName;
    }

    @android.annotation.Nullable
    public android.content.ComponentName getExternalStorageServiceComponentName() {
        return this.mExternalStorageServiceComponent;
    }

    public void notifyAppIoBlocked(java.lang.String str, int i, int i2, int i3) {
        com.android.server.storage.StorageUserConnection storageUserConnection;
        int userId = android.os.UserHandle.getUserId(i);
        synchronized (this.mLock) {
            storageUserConnection = this.mConnections.get(userId);
        }
        if (storageUserConnection != null) {
            storageUserConnection.notifyAppIoBlocked(str, i, i2, i3);
        }
    }

    public void notifyAppIoResumed(java.lang.String str, int i, int i2, int i3) {
        com.android.server.storage.StorageUserConnection storageUserConnection;
        int userId = android.os.UserHandle.getUserId(i);
        synchronized (this.mLock) {
            storageUserConnection = this.mConnections.get(userId);
        }
        if (storageUserConnection != null) {
            storageUserConnection.notifyAppIoResumed(str, i, i2, i3);
        }
    }

    public boolean isAppIoBlocked(int i) {
        com.android.server.storage.StorageUserConnection storageUserConnection;
        int userId = android.os.UserHandle.getUserId(i);
        synchronized (this.mLock) {
            storageUserConnection = this.mConnections.get(userId);
        }
        if (storageUserConnection != null) {
            return storageUserConnection.isAppIoBlocked(i);
        }
        return false;
    }

    private void killExternalStorageService(int i) {
        try {
            android.app.ActivityManager.getService().killApplication(this.mExternalStorageServicePackageName, this.mExternalStorageServiceAppId, i, "storage_session_controller reset", 13);
        } catch (android.os.RemoteException e) {
            android.util.Slog.i(TAG, "Failed to kill the ExtenalStorageService for user " + i);
        }
    }

    public static boolean isEmulatedOrPublic(android.os.storage.VolumeInfo volumeInfo) {
        return volumeInfo.type == 2 || (volumeInfo.type == 0 && volumeInfo.isVisible());
    }

    public static class ExternalStorageServiceException extends java.lang.Exception {
        public ExternalStorageServiceException(java.lang.Throwable th) {
            super(th);
        }

        public ExternalStorageServiceException(java.lang.String str) {
            super(str);
        }

        public ExternalStorageServiceException(java.lang.String str, java.lang.Throwable th) {
            super(str, th);
        }
    }

    private static boolean isSupportedVolume(android.os.storage.VolumeInfo volumeInfo) {
        return isEmulatedOrPublic(volumeInfo) || volumeInfo.type == 5;
    }

    private boolean shouldHandle(@android.annotation.Nullable android.os.storage.VolumeInfo volumeInfo) {
        return !this.mIsResetting && (volumeInfo == null || isSupportedVolume(volumeInfo));
    }

    public boolean supportsExternalStorage(int i) {
        return resolveExternalStorageServiceAsUser(i) != null;
    }

    private android.content.pm.ServiceInfo resolveExternalStorageServiceAsUser(int i) {
        android.content.Intent intent = new android.content.Intent("android.service.storage.ExternalStorageService");
        intent.setPackage(this.mExternalStorageServicePackageName);
        android.content.pm.ResolveInfo resolveServiceAsUser = this.mContext.getPackageManager().resolveServiceAsUser(intent, 132, i);
        if (resolveServiceAsUser == null) {
            return null;
        }
        return resolveServiceAsUser.serviceInfo;
    }
}
