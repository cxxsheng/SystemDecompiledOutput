package com.android.server;

/* loaded from: classes.dex */
public class DynamicSystemService extends android.os.image.IDynamicSystemService.Stub {
    private static final int GSID_ROUGH_TIMEOUT_MS = 8192;
    private static final long MINIMUM_SD_MB = 30720;
    private static final java.lang.String PATH_DEFAULT = "/data/gsi/dsu/";
    private static final java.lang.String TAG = "DynamicSystemService";
    private android.content.Context mContext;
    private java.lang.String mDsuSlot;
    private volatile android.gsi.IGsiService mGsiService;
    private java.lang.String mInstallPath;

    DynamicSystemService(android.content.Context context) {
        this.mContext = context;
    }

    private android.gsi.IGsiService getGsiService() {
        if (this.mGsiService != null) {
            return this.mGsiService;
        }
        return android.gsi.IGsiService.Stub.asInterface(android.os.ServiceManager.waitForService("gsiservice"));
    }

    class GsiServiceCallback extends android.gsi.IGsiServiceCallback.Stub {
        private int mResult = -1;

        GsiServiceCallback() {
        }

        @Override // android.gsi.IGsiServiceCallback
        public synchronized void onResult(int i) {
            this.mResult = i;
            notify();
        }

        public int getResult() {
            return this.mResult;
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public boolean startInstallation(java.lang.String str) throws android.os.RemoteException {
        super.startInstallation_enforcePermission();
        android.gsi.IGsiService gsiService = getGsiService();
        this.mGsiService = gsiService;
        java.lang.String str2 = android.os.SystemProperties.get("os.aot.path");
        if (str2.isEmpty()) {
            int myUserId = android.os.UserHandle.myUserId();
            for (android.os.storage.VolumeInfo volumeInfo : ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).getVolumes()) {
                if (volumeInfo.getType() == 0 && volumeInfo.isMountedWritable() && "vfat".equalsIgnoreCase(volumeInfo.fsType)) {
                    long j = volumeInfo.getDisk().size >> 20;
                    android.util.Slog.i(TAG, volumeInfo.getPath() + ": " + j + " MB");
                    if (j < MINIMUM_SD_MB) {
                        android.util.Slog.i(TAG, volumeInfo.getPath() + ": insufficient storage");
                    } else {
                        java.io.File internalPathForUser = volumeInfo.getInternalPathForUser(myUserId);
                        if (internalPathForUser != null) {
                            str2 = new java.io.File(internalPathForUser, str).getPath();
                        }
                    }
                }
            }
            if (str2.isEmpty()) {
                str2 = PATH_DEFAULT + str;
            }
            android.util.Slog.i(TAG, "startInstallation -> " + str2);
        }
        this.mInstallPath = str2;
        this.mDsuSlot = str;
        if (gsiService.openInstall(str2) != 0) {
            android.util.Slog.i(TAG, "Failed to open " + str2);
            return false;
        }
        return true;
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public int createPartition(java.lang.String str, long j, boolean z) throws android.os.RemoteException {
        super.createPartition_enforcePermission();
        int createPartition = getGsiService().createPartition(str, j, z);
        if (createPartition != 0) {
            android.util.Slog.i(TAG, "Failed to create partition: " + str);
        }
        return createPartition;
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public boolean closePartition() throws android.os.RemoteException {
        super.closePartition_enforcePermission();
        if (getGsiService().closePartition() != 0) {
            android.util.Slog.i(TAG, "Partition installation completes with error");
            return false;
        }
        return true;
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public boolean finishInstallation() throws android.os.RemoteException {
        super.finishInstallation_enforcePermission();
        if (getGsiService().closeInstall() != 0) {
            android.util.Slog.i(TAG, "Failed to finish installation");
            return false;
        }
        return true;
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public android.gsi.GsiProgress getInstallationProgress() throws android.os.RemoteException {
        super.getInstallationProgress_enforcePermission();
        return getGsiService().getInstallProgress();
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public boolean abort() throws android.os.RemoteException {
        super.abort_enforcePermission();
        return getGsiService().cancelGsiInstall();
    }

    @android.annotation.RequiresNoPermission
    public boolean isInUse() {
        return android.os.SystemProperties.getBoolean("ro.gsid.image_running", false);
    }

    @android.annotation.RequiresNoPermission
    public boolean isInstalled() {
        boolean z = android.os.SystemProperties.getBoolean("gsid.image_installed", false);
        android.util.Slog.i(TAG, "isInstalled(): " + z);
        return z;
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public boolean isEnabled() throws android.os.RemoteException {
        super.isEnabled_enforcePermission();
        return getGsiService().isGsiEnabled();
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public boolean remove() throws android.os.RemoteException {
        super.remove_enforcePermission();
        try {
            com.android.server.DynamicSystemService.GsiServiceCallback gsiServiceCallback = new com.android.server.DynamicSystemService.GsiServiceCallback();
            synchronized (gsiServiceCallback) {
                getGsiService().removeGsiAsync(gsiServiceCallback);
                gsiServiceCallback.wait(8192L);
            }
            return gsiServiceCallback.getResult() == 0;
        } catch (java.lang.InterruptedException e) {
            android.util.Slog.e(TAG, "remove() was interrupted");
            return false;
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public boolean setEnable(boolean z, boolean z2) throws android.os.RemoteException {
        super.setEnable_enforcePermission();
        android.gsi.IGsiService gsiService = getGsiService();
        if (z) {
            try {
                getActiveDsuSlot();
                com.android.server.DynamicSystemService.GsiServiceCallback gsiServiceCallback = new com.android.server.DynamicSystemService.GsiServiceCallback();
                synchronized (gsiServiceCallback) {
                    gsiService.enableGsiAsync(z2, this.mDsuSlot, gsiServiceCallback);
                    gsiServiceCallback.wait(8192L);
                }
                return gsiServiceCallback.getResult() == 0;
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.e(TAG, "setEnable() was interrupted");
                return false;
            }
        }
        return gsiService.disableGsi();
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public boolean setAshmem(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) {
        super.setAshmem_enforcePermission();
        try {
            return getGsiService().setGsiAshmem(parcelFileDescriptor, j);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public boolean submitFromAshmem(long j) {
        super.submitFromAshmem_enforcePermission();
        try {
            return getGsiService().commitGsiChunkFromAshmem(j);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public boolean getAvbPublicKey(android.gsi.AvbPublicKey avbPublicKey) {
        super.getAvbPublicKey_enforcePermission();
        try {
            return getGsiService().getAvbPublicKey(avbPublicKey) == 0;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public long suggestScratchSize() throws android.os.RemoteException {
        super.suggestScratchSize_enforcePermission();
        return getGsiService().suggestScratchSize();
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_DYNAMIC_SYSTEM")
    public java.lang.String getActiveDsuSlot() throws android.os.RemoteException {
        super.getActiveDsuSlot_enforcePermission();
        if (this.mDsuSlot == null) {
            this.mDsuSlot = getGsiService().getActiveDsuSlot();
        }
        return this.mDsuSlot;
    }
}
