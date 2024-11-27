package com.android.server.devicepolicy;

/* loaded from: classes.dex */
abstract class UpdateInstaller {
    static final java.lang.String TAG = "UpdateInstaller";
    private android.app.admin.StartInstallingUpdateCallback mCallback;
    private com.android.server.devicepolicy.DevicePolicyConstants mConstants;
    protected android.content.Context mContext;

    @android.annotation.Nullable
    protected java.io.File mCopiedUpdateFile;
    private com.android.server.devicepolicy.DevicePolicyManagerService.Injector mInjector;
    private android.os.ParcelFileDescriptor mUpdateFileDescriptor;

    public abstract void installUpdateInThread();

    protected UpdateInstaller(android.content.Context context, android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.admin.StartInstallingUpdateCallback startInstallingUpdateCallback, com.android.server.devicepolicy.DevicePolicyManagerService.Injector injector, com.android.server.devicepolicy.DevicePolicyConstants devicePolicyConstants) {
        this.mContext = context;
        this.mCallback = startInstallingUpdateCallback;
        this.mUpdateFileDescriptor = parcelFileDescriptor;
        this.mInjector = injector;
        this.mConstants = devicePolicyConstants;
    }

    public void startInstallUpdate() {
        this.mCopiedUpdateFile = null;
        if (!isBatteryLevelSufficient()) {
            notifyCallbackOnError(5, "The battery level must be above " + this.mConstants.BATTERY_THRESHOLD_NOT_CHARGING + " while not charging or above " + this.mConstants.BATTERY_THRESHOLD_CHARGING + " while charging");
            return;
        }
        java.lang.Thread thread = new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.devicepolicy.UpdateInstaller$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.devicepolicy.UpdateInstaller.this.lambda$startInstallUpdate$0();
            }
        });
        thread.setPriority(10);
        thread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startInstallUpdate$0() {
        this.mCopiedUpdateFile = copyUpdateFileToDataOtaPackageDir();
        if (this.mCopiedUpdateFile == null) {
            notifyCallbackOnError(1, "Error while copying file.");
        } else {
            installUpdateInThread();
        }
    }

    private boolean isBatteryLevelSufficient() {
        android.content.Intent registerReceiver = this.mContext.registerReceiver(null, new android.content.IntentFilter("android.intent.action.BATTERY_CHANGED"));
        float calculateBatteryPercentage = calculateBatteryPercentage(registerReceiver);
        return registerReceiver.getIntExtra("plugged", -1) > 0 ? calculateBatteryPercentage >= ((float) this.mConstants.BATTERY_THRESHOLD_CHARGING) : calculateBatteryPercentage >= ((float) this.mConstants.BATTERY_THRESHOLD_NOT_CHARGING);
    }

    private float calculateBatteryPercentage(android.content.Intent intent) {
        return (intent.getIntExtra("level", -1) * 100) / intent.getIntExtra("scale", -1);
    }

    private java.io.File copyUpdateFileToDataOtaPackageDir() {
        try {
            java.io.File createNewFileWithPermissions = createNewFileWithPermissions();
            copyToFile(createNewFileWithPermissions);
            return createNewFileWithPermissions;
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, "Failed to copy update file to OTA directory", e);
            notifyCallbackOnError(1, android.util.Log.getStackTraceString(e));
            return null;
        }
    }

    private java.io.File createNewFileWithPermissions() throws java.io.IOException {
        java.io.File createTempFile = java.io.File.createTempFile("update", ".zip", new java.io.File(android.os.Environment.getDataDirectory() + "/ota_package"));
        android.os.FileUtils.setPermissions(createTempFile, 484, -1, -1);
        return createTempFile;
    }

    private void copyToFile(java.io.File file) throws java.io.IOException {
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
        try {
            android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(this.mUpdateFileDescriptor);
            try {
                android.os.FileUtils.copy(autoCloseInputStream, fileOutputStream);
                autoCloseInputStream.close();
                fileOutputStream.close();
            } finally {
            }
        } catch (java.lang.Throwable th) {
            try {
                fileOutputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    void cleanupUpdateFile() {
        if (this.mCopiedUpdateFile != null && this.mCopiedUpdateFile.exists()) {
            this.mCopiedUpdateFile.delete();
        }
    }

    protected void notifyCallbackOnError(int i, java.lang.String str) {
        cleanupUpdateFile();
        android.app.admin.DevicePolicyEventLogger.createEvent(74).setInt(i).write();
        try {
            this.mCallback.onStartInstallingUpdateError(i, str);
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Error while calling callback", e);
        }
    }

    protected void notifyCallbackOnSuccess() {
        cleanupUpdateFile();
        this.mInjector.powerManagerReboot("deviceowner");
    }
}
