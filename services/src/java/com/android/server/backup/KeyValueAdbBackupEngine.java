package com.android.server.backup;

/* loaded from: classes.dex */
public class KeyValueAdbBackupEngine {
    private static final java.lang.String BACKUP_KEY_VALUE_BACKUP_DATA_FILENAME_SUFFIX = ".data";
    private static final java.lang.String BACKUP_KEY_VALUE_BLANK_STATE_FILENAME = "blank_state";
    private static final java.lang.String BACKUP_KEY_VALUE_DIRECTORY_NAME = "key_value_dir";
    private static final java.lang.String BACKUP_KEY_VALUE_NEW_STATE_FILENAME_SUFFIX = ".new";
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "KeyValueAdbBackupEngine";
    private final com.android.server.backup.BackupAgentTimeoutParameters mAgentTimeoutParameters;
    private android.os.ParcelFileDescriptor mBackupData;
    private final java.io.File mBackupDataName;
    private com.android.server.backup.UserBackupManagerService mBackupManagerService;
    private final java.io.File mBlankStateName;
    private final android.content.pm.PackageInfo mCurrentPackage;
    private final java.io.File mDataDir;
    private final java.io.File mManifestFile;
    private android.os.ParcelFileDescriptor mNewState;
    private final java.io.File mNewStateName;
    private final java.io.OutputStream mOutput;
    private final android.content.pm.PackageManager mPackageManager;
    private android.os.ParcelFileDescriptor mSavedState;
    private final java.io.File mStateDir;

    public KeyValueAdbBackupEngine(java.io.OutputStream outputStream, android.content.pm.PackageInfo packageInfo, com.android.server.backup.UserBackupManagerService userBackupManagerService, android.content.pm.PackageManager packageManager, java.io.File file, java.io.File file2) {
        this.mOutput = outputStream;
        this.mCurrentPackage = packageInfo;
        this.mBackupManagerService = userBackupManagerService;
        this.mPackageManager = packageManager;
        this.mDataDir = file2;
        this.mStateDir = new java.io.File(file, BACKUP_KEY_VALUE_DIRECTORY_NAME);
        this.mStateDir.mkdirs();
        java.lang.String str = this.mCurrentPackage.packageName;
        this.mBlankStateName = new java.io.File(this.mStateDir, BACKUP_KEY_VALUE_BLANK_STATE_FILENAME);
        this.mBackupDataName = new java.io.File(this.mDataDir, str + ".data");
        this.mNewStateName = new java.io.File(this.mStateDir, str + ".new");
        this.mManifestFile = new java.io.File(this.mDataDir, com.android.server.backup.UserBackupManagerService.BACKUP_MANIFEST_FILENAME);
        com.android.server.backup.BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        java.util.Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
    }

    public void backupOnePackage() throws java.io.IOException {
        android.app.IBackupAgent bindToAgent;
        android.content.pm.ApplicationInfo applicationInfo = this.mCurrentPackage.applicationInfo;
        try {
            try {
                prepareBackupFiles(this.mCurrentPackage.packageName);
                bindToAgent = bindToAgent(applicationInfo);
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.e(TAG, "Failed creating files for package " + this.mCurrentPackage.packageName + " will ignore package. " + e);
            }
            if (bindToAgent == null) {
                android.util.Slog.e(TAG, "Failed binding to BackupAgent for package " + this.mCurrentPackage.packageName);
                return;
            }
            if (invokeAgentForAdbBackup(this.mCurrentPackage.packageName, bindToAgent)) {
                writeBackupData();
                return;
            }
            android.util.Slog.e(TAG, "Backup Failed for package " + this.mCurrentPackage.packageName);
        } finally {
            cleanup();
        }
    }

    private void prepareBackupFiles(java.lang.String str) throws java.io.FileNotFoundException {
        this.mSavedState = android.os.ParcelFileDescriptor.open(this.mBlankStateName, android.hardware.audio.common.V2_0.AudioFormat.MP2);
        this.mBackupData = android.os.ParcelFileDescriptor.open(this.mBackupDataName, 1006632960);
        if (!android.os.SELinux.restorecon(this.mBackupDataName)) {
            android.util.Slog.e(TAG, "SELinux restorecon failed on " + this.mBackupDataName);
        }
        this.mNewState = android.os.ParcelFileDescriptor.open(this.mNewStateName, 1006632960);
    }

    private android.app.IBackupAgent bindToAgent(android.content.pm.ApplicationInfo applicationInfo) {
        try {
            return this.mBackupManagerService.bindToAgentSynchronous(applicationInfo, 0, 0);
        } catch (java.lang.SecurityException e) {
            android.util.Slog.e(TAG, "error in binding to agent for package " + applicationInfo.packageName + ". " + e);
            return null;
        }
    }

    private boolean invokeAgentForAdbBackup(java.lang.String str, android.app.IBackupAgent iBackupAgent) {
        int generateRandomIntegerToken = this.mBackupManagerService.generateRandomIntegerToken();
        try {
            this.mBackupManagerService.prepareOperationTimeout(generateRandomIntegerToken, this.mAgentTimeoutParameters.getKvBackupAgentTimeoutMillis(), null, 0);
            iBackupAgent.doBackup(this.mSavedState, this.mBackupData, this.mNewState, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, new com.android.server.backup.remote.ServiceBackupCallback(this.mBackupManagerService.getBackupManagerBinder(), generateRandomIntegerToken), 0);
            if (!this.mBackupManagerService.waitUntilOperationComplete(generateRandomIntegerToken)) {
                android.util.Slog.e(TAG, "Key-value backup failed on package " + str);
                return false;
            }
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error invoking agent for backup on " + str + ". " + e);
            return false;
        }
    }

    class KeyValueAdbBackupDataCopier implements java.lang.Runnable {
        private final android.content.pm.PackageInfo mPackage;
        private final android.os.ParcelFileDescriptor mPipe;
        private final int mToken;

        KeyValueAdbBackupDataCopier(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws java.io.IOException {
            this.mPackage = packageInfo;
            this.mPipe = android.os.ParcelFileDescriptor.dup(parcelFileDescriptor.getFileDescriptor());
            this.mToken = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                try {
                    android.app.backup.FullBackupDataOutput fullBackupDataOutput = new android.app.backup.FullBackupDataOutput(this.mPipe);
                    new com.android.server.backup.fullbackup.AppMetadataBackupWriter(fullBackupDataOutput, com.android.server.backup.KeyValueAdbBackupEngine.this.mPackageManager).backupManifest(this.mPackage, com.android.server.backup.KeyValueAdbBackupEngine.this.mManifestFile, com.android.server.backup.KeyValueAdbBackupEngine.this.mDataDir, "k", null, false);
                    com.android.server.backup.KeyValueAdbBackupEngine.this.mManifestFile.delete();
                    android.app.backup.FullBackup.backupToTar(this.mPackage.packageName, "k", (java.lang.String) null, com.android.server.backup.KeyValueAdbBackupEngine.this.mDataDir.getAbsolutePath(), com.android.server.backup.KeyValueAdbBackupEngine.this.mBackupDataName.getAbsolutePath(), fullBackupDataOutput);
                    try {
                        new java.io.FileOutputStream(this.mPipe.getFileDescriptor()).write(new byte[4]);
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(com.android.server.backup.KeyValueAdbBackupEngine.TAG, "Unable to finalize backup stream!");
                    }
                    try {
                        com.android.server.backup.KeyValueAdbBackupEngine.this.mBackupManagerService.getBackupManagerBinder().opComplete(this.mToken, 0L);
                    } catch (android.os.RemoteException e2) {
                    }
                } catch (java.io.IOException e3) {
                    android.util.Slog.e(com.android.server.backup.KeyValueAdbBackupEngine.TAG, "Error running full backup for " + this.mPackage.packageName + ". " + e3);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(this.mPipe);
            }
        }
    }

    private void writeBackupData() throws java.io.IOException {
        android.os.ParcelFileDescriptor parcelFileDescriptor;
        int generateRandomIntegerToken = this.mBackupManagerService.generateRandomIntegerToken();
        long kvBackupAgentTimeoutMillis = this.mAgentTimeoutParameters.getKvBackupAgentTimeoutMillis();
        android.os.ParcelFileDescriptor[] parcelFileDescriptorArr = null;
        try {
            try {
                android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
                try {
                    this.mBackupManagerService.prepareOperationTimeout(generateRandomIntegerToken, kvBackupAgentTimeoutMillis, null, 0);
                    com.android.server.backup.KeyValueAdbBackupEngine.KeyValueAdbBackupDataCopier keyValueAdbBackupDataCopier = new com.android.server.backup.KeyValueAdbBackupEngine.KeyValueAdbBackupDataCopier(this.mCurrentPackage, createPipe[1], generateRandomIntegerToken);
                    createPipe[1].close();
                    createPipe[1] = null;
                    new java.lang.Thread(keyValueAdbBackupDataCopier, "key-value-app-data-runner").start();
                    com.android.server.backup.utils.FullBackupUtils.routeSocketDataToOutput(createPipe[0], this.mOutput);
                    if (!this.mBackupManagerService.waitUntilOperationComplete(generateRandomIntegerToken)) {
                        android.util.Slog.e(TAG, "Full backup failed on package " + this.mCurrentPackage.packageName);
                    }
                    this.mOutput.flush();
                    libcore.io.IoUtils.closeQuietly(createPipe[0]);
                    parcelFileDescriptor = createPipe[1];
                } catch (java.io.IOException e) {
                    e = e;
                    parcelFileDescriptorArr = createPipe;
                    android.util.Slog.e(TAG, "Error backing up " + this.mCurrentPackage.packageName + ": " + e);
                    this.mOutput.flush();
                    if (parcelFileDescriptorArr != null) {
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptorArr[0]);
                        parcelFileDescriptor = parcelFileDescriptorArr[1];
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                    }
                    return;
                } catch (java.lang.Throwable th) {
                    th = th;
                    parcelFileDescriptorArr = createPipe;
                    this.mOutput.flush();
                    if (parcelFileDescriptorArr != null) {
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptorArr[0]);
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptorArr[1]);
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.io.IOException e2) {
            e = e2;
        }
        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
    }

    private void cleanup() {
        this.mBackupManagerService.tearDownAgentAndKill(this.mCurrentPackage.applicationInfo);
        this.mBlankStateName.delete();
        this.mNewStateName.delete();
        this.mBackupDataName.delete();
    }
}
