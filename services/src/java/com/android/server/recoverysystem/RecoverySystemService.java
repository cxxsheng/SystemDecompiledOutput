package com.android.server.recoverysystem;

/* loaded from: classes2.dex */
public class RecoverySystemService extends android.os.IRecoverySystem.Stub implements com.android.internal.widget.RebootEscrowListener {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String AB_UPDATE = "ro.build.ab_update";
    private static final long APEX_INFO_SIZE_LIMIT = 2457600;
    private static final boolean DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String INIT_SERVICE_CLEAR_BCB = "init.svc.clear-bcb";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String INIT_SERVICE_SETUP_BCB = "init.svc.setup-bcb";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String INIT_SERVICE_UNCRYPT = "init.svc.uncrypt";
    static final java.lang.String LSKF_CAPTURED_COUNT_PREF = "lskf_captured_count";
    static final java.lang.String LSKF_CAPTURED_TIMESTAMP_PREF = "lskf_captured_timestamp";
    private static final int REBOOT_WATCHDOG_PAUSE_DURATION_MS = 20000;
    static final java.lang.String RECOVERY_WIPE_DATA_COMMAND = "--wipe_data";
    static final java.lang.String REQUEST_LSKF_COUNT_PREF_SUFFIX = "_request_lskf_count";
    static final java.lang.String REQUEST_LSKF_TIMESTAMP_PREF_SUFFIX = "_request_lskf_timestamp";
    private static final int ROR_NEED_PREPARATION = 0;
    private static final int ROR_NOT_REQUESTED = 0;
    private static final int ROR_REQUESTED_NEED_CLEAR = 1;
    private static final int ROR_REQUESTED_SKIP_CLEAR = 2;
    private static final int ROR_SKIP_PREPARATION_AND_NOTIFY = 1;
    private static final int ROR_SKIP_PREPARATION_NOT_NOTIFY = 2;
    private static final int SOCKET_CONNECTION_MAX_RETRY = 30;
    private static final java.lang.String TAG = "RecoverySystemService";
    private static final java.lang.String UNCRYPT_SOCKET = "uncrypt";

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.ArrayMap<java.lang.String, android.content.IntentSender> mCallerPendingRequest;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.ArraySet<java.lang.String> mCallerPreparedForReboot;
    private final android.content.Context mContext;
    private final com.android.server.recoverysystem.RecoverySystemService.Injector mInjector;
    private static final java.lang.Object sRequestLock = new java.lang.Object();
    static final android.util.FastImmutableArraySet<java.lang.Integer> FATAL_ARM_ESCROW_ERRORS = new android.util.FastImmutableArraySet<>(new java.lang.Integer[]{2, 3, 4, 5, 6});

    private @interface ResumeOnRebootActionsOnClear {
    }

    private @interface ResumeOnRebootActionsOnRequest {
    }

    static class RebootPreparationError {
        final int mProviderErrorCode;
        final int mRebootErrorCode;

        RebootPreparationError(int i, int i2) {
            this.mRebootErrorCode = i;
            this.mProviderErrorCode = i2;
        }

        int getErrorCodeForMetrics() {
            return this.mRebootErrorCode + this.mProviderErrorCode;
        }
    }

    public static class PreferencesManager {
        private static final java.lang.String METRICS_DIR = "recovery_system";
        private static final java.lang.String METRICS_PREFS_FILE = "RecoverySystemMetricsPrefs.xml";
        private final java.io.File mMetricsPrefsFile = new java.io.File(new java.io.File(android.os.Environment.getDataSystemCeDirectory(0), METRICS_DIR), METRICS_PREFS_FILE);
        protected final android.content.SharedPreferences mSharedPreferences;

        PreferencesManager(android.content.Context context) {
            this.mSharedPreferences = context.getSharedPreferences(this.mMetricsPrefsFile, 0);
        }

        public long getLong(java.lang.String str, long j) {
            return this.mSharedPreferences.getLong(str, j);
        }

        public int getInt(java.lang.String str, int i) {
            return this.mSharedPreferences.getInt(str, i);
        }

        public void putLong(java.lang.String str, long j) {
            this.mSharedPreferences.edit().putLong(str, j).commit();
        }

        public void putInt(java.lang.String str, int i) {
            this.mSharedPreferences.edit().putInt(str, i).commit();
        }

        public synchronized void incrementIntKey(java.lang.String str, int i) {
            putInt(str, getInt(str, i) + 1);
        }

        public void deletePrefsFile() {
            if (!this.mMetricsPrefsFile.delete()) {
                android.util.Slog.w(com.android.server.recoverysystem.RecoverySystemService.TAG, "Failed to delete metrics prefs");
            }
        }
    }

    static class Injector {
        protected final android.content.Context mContext;
        protected final com.android.server.recoverysystem.RecoverySystemService.PreferencesManager mPrefs;

        Injector(android.content.Context context) {
            this.mContext = context;
            this.mPrefs = new com.android.server.recoverysystem.RecoverySystemService.PreferencesManager(context);
        }

        public android.content.Context getContext() {
            return this.mContext;
        }

        public com.android.internal.widget.LockSettingsInternal getLockSettingsService() {
            return (com.android.internal.widget.LockSettingsInternal) com.android.server.LocalServices.getService(com.android.internal.widget.LockSettingsInternal.class);
        }

        public android.os.PowerManager getPowerManager() {
            return (android.os.PowerManager) this.mContext.getSystemService("power");
        }

        public java.lang.String systemPropertiesGet(java.lang.String str) {
            return android.os.SystemProperties.get(str);
        }

        public void systemPropertiesSet(java.lang.String str, java.lang.String str2) {
            android.os.SystemProperties.set(str, str2);
        }

        public boolean uncryptPackageFileDelete() {
            return android.os.RecoverySystem.UNCRYPT_PACKAGE_FILE.delete();
        }

        public java.lang.String getUncryptPackageFileName() {
            return android.os.RecoverySystem.UNCRYPT_PACKAGE_FILE.getName();
        }

        public java.io.FileWriter getUncryptPackageFileWriter() throws java.io.IOException {
            return new java.io.FileWriter(android.os.RecoverySystem.UNCRYPT_PACKAGE_FILE);
        }

        public com.android.server.recoverysystem.RecoverySystemService.UncryptSocket connectService() {
            com.android.server.recoverysystem.RecoverySystemService.UncryptSocket uncryptSocket = new com.android.server.recoverysystem.RecoverySystemService.UncryptSocket();
            if (!uncryptSocket.connectService()) {
                uncryptSocket.close();
                return null;
            }
            return uncryptSocket;
        }

        public android.hardware.boot.IBootControl getBootControl() throws android.os.RemoteException {
            java.lang.String str = android.hardware.boot.IBootControl.DESCRIPTOR + "/default";
            if (android.os.ServiceManager.isDeclared(str)) {
                android.util.Slog.i(com.android.server.recoverysystem.RecoverySystemService.TAG, "AIDL version of BootControl HAL present, using instance " + str);
                return android.hardware.boot.IBootControl.Stub.asInterface(android.os.ServiceManager.waitForDeclaredService(str));
            }
            com.android.server.recoverysystem.hal.BootControlHIDL service = com.android.server.recoverysystem.hal.BootControlHIDL.getService();
            if (!com.android.server.recoverysystem.hal.BootControlHIDL.isServicePresent()) {
                android.util.Slog.e(com.android.server.recoverysystem.RecoverySystemService.TAG, "Neither AIDL nor HIDL version of the BootControl HAL is present.");
                return null;
            }
            if (!com.android.server.recoverysystem.hal.BootControlHIDL.isV1_2ServicePresent()) {
                android.util.Slog.w(com.android.server.recoverysystem.RecoverySystemService.TAG, "Device doesn't implement boot control HAL V1_2.");
                return null;
            }
            return service;
        }

        public void threadSleep(long j) throws java.lang.InterruptedException {
            java.lang.Thread.sleep(j);
        }

        public int getUidFromPackageName(java.lang.String str) {
            try {
                return this.mContext.getPackageManager().getPackageUidAsUser(str, 0);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.w(com.android.server.recoverysystem.RecoverySystemService.TAG, "Failed to find uid for " + str);
                return -1;
            }
        }

        public com.android.server.recoverysystem.RecoverySystemService.PreferencesManager getMetricsPrefs() {
            return this.mPrefs;
        }

        public long getCurrentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }

        public void reportRebootEscrowPreparationMetrics(int i, @com.android.server.recoverysystem.RecoverySystemService.ResumeOnRebootActionsOnRequest int i2, int i3) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.REBOOT_ESCROW_PREPARATION_REPORTED, i, i2, i3);
        }

        public void reportRebootEscrowLskfCapturedMetrics(int i, int i2, int i3) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.REBOOT_ESCROW_LSKF_CAPTURE_REPORTED, i, i2, i3);
        }

        public void reportRebootEscrowRebootMetrics(int i, int i2, int i3, int i4, boolean z, boolean z2, int i5, int i6) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.REBOOT_ESCROW_REBOOT_REPORTED, i, i2, i3, i4, z, z2, i5, i6);
        }
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private com.android.server.recoverysystem.RecoverySystemService mRecoverySystemService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 500) {
                this.mRecoverySystemService.onSystemServicesReady();
            }
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mRecoverySystemService = new com.android.server.recoverysystem.RecoverySystemService(getContext());
            publishBinderService("recovery", this.mRecoverySystemService);
        }
    }

    private RecoverySystemService(android.content.Context context) {
        this(new com.android.server.recoverysystem.RecoverySystemService.Injector(context));
    }

    @com.android.internal.annotations.VisibleForTesting
    RecoverySystemService(com.android.server.recoverysystem.RecoverySystemService.Injector injector) {
        this.mCallerPendingRequest = new android.util.ArrayMap<>();
        this.mCallerPreparedForReboot = new android.util.ArraySet<>();
        this.mInjector = injector;
        this.mContext = injector.getContext();
    }

    @com.android.internal.annotations.VisibleForTesting
    void onSystemServicesReady() {
        com.android.internal.widget.LockSettingsInternal lockSettingsService = this.mInjector.getLockSettingsService();
        if (lockSettingsService == null) {
            android.util.Slog.e(TAG, "Failed to get lock settings service, skipping set RebootEscrowListener");
        } else {
            lockSettingsService.setRebootEscrowListener(this);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00b0, code lost:
    
        android.util.Slog.e(com.android.server.recoverysystem.RecoverySystemService.TAG, "uncrypt failed with status: " + r4);
        r9.sendAck();
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00cf, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean uncrypt(java.lang.String str, android.os.IRecoverySystemProgressListener iRecoverySystemProgressListener) {
        synchronized (sRequestLock) {
            try {
                this.mContext.enforceCallingOrSelfPermission("android.permission.RECOVERY", null);
                if (!checkAndWaitForUncryptService()) {
                    android.util.Slog.e(TAG, "uncrypt service is unavailable.");
                    return false;
                }
                this.mInjector.uncryptPackageFileDelete();
                try {
                    java.io.FileWriter uncryptPackageFileWriter = this.mInjector.getUncryptPackageFileWriter();
                    try {
                        uncryptPackageFileWriter.write(str + "\n");
                        uncryptPackageFileWriter.close();
                        this.mInjector.systemPropertiesSet("ctl.start", UNCRYPT_SOCKET);
                        com.android.server.recoverysystem.RecoverySystemService.UncryptSocket connectService = this.mInjector.connectService();
                        if (connectService == null) {
                            android.util.Slog.e(TAG, "Failed to connect to uncrypt socket");
                            return false;
                        }
                        int i = Integer.MIN_VALUE;
                        while (true) {
                            try {
                                try {
                                    int percentageUncrypted = connectService.getPercentageUncrypted();
                                    if (percentageUncrypted != i || i == Integer.MIN_VALUE) {
                                        if (percentageUncrypted < 0 || percentageUncrypted > 100) {
                                            break;
                                        }
                                        android.util.Slog.i(TAG, "uncrypt read status: " + percentageUncrypted);
                                        if (iRecoverySystemProgressListener != null) {
                                            try {
                                                iRecoverySystemProgressListener.onProgress(percentageUncrypted);
                                            } catch (android.os.RemoteException e) {
                                                android.util.Slog.w(TAG, "RemoteException when posting progress");
                                            }
                                        }
                                        if (percentageUncrypted == 100) {
                                            android.util.Slog.i(TAG, "uncrypt successfully finished.");
                                            connectService.sendAck();
                                            return true;
                                        }
                                        i = percentageUncrypted;
                                    }
                                } finally {
                                    connectService.close();
                                }
                            } catch (java.io.IOException e2) {
                                android.util.Slog.e(TAG, "IOException when reading status: ", e2);
                                return false;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        if (uncryptPackageFileWriter != null) {
                            try {
                                uncryptPackageFileWriter.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (java.io.IOException e3) {
                    android.util.Slog.e(TAG, "IOException when writing \"" + this.mInjector.getUncryptPackageFileName() + "\":", e3);
                    return false;
                }
            } finally {
            }
        }
    }

    public boolean clearBcb() {
        boolean z;
        synchronized (sRequestLock) {
            z = setupOrClearBcb(false, null);
        }
        return z;
    }

    public boolean setupBcb(java.lang.String str) {
        boolean z;
        synchronized (sRequestLock) {
            z = setupOrClearBcb(true, str);
        }
        return z;
    }

    public void rebootRecoveryWithCommand(java.lang.String str) {
        boolean z = str != null && str.contains(RECOVERY_WIPE_DATA_COMMAND);
        synchronized (sRequestLock) {
            try {
                if (!setupOrClearBcb(true, str)) {
                    android.util.Slog.e(TAG, "rebootRecoveryWithCommand failed to setup BCB");
                    return;
                }
                if (z) {
                    deleteSecrets();
                }
                this.mInjector.getPowerManager().reboot("recovery");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static void deleteSecrets() {
        com.android.server.utils.Slogf.w(TAG, "deleteSecrets");
        try {
            android.security.AndroidKeyStoreMaintenance.deleteAllKeys();
        } catch (android.security.KeyStoreException e) {
            android.util.Log.wtf(TAG, "Failed to delete all keys from keystore.", e);
        }
        try {
            android.hardware.security.secretkeeper.ISecretkeeper secretKeeper = getSecretKeeper();
            if (secretKeeper != null) {
                com.android.server.utils.Slogf.i(TAG, "ISecretkeeper.deleteAll();");
                secretKeeper.deleteAll();
            }
        } catch (android.os.RemoteException e2) {
            android.util.Log.wtf(TAG, "Failed to delete all secrets from secretkeeper.", e2);
        }
    }

    @android.annotation.Nullable
    private static android.hardware.security.secretkeeper.ISecretkeeper getSecretKeeper() {
        try {
            return android.hardware.security.secretkeeper.ISecretkeeper.Stub.asInterface(android.os.ServiceManager.waitForDeclaredService(android.hardware.security.secretkeeper.ISecretkeeper.DESCRIPTOR + "/default"));
        } catch (java.lang.SecurityException e) {
            android.util.Slog.w(TAG, "Does not have permissions to get AIDL secretkeeper service");
            return null;
        }
    }

    private void enforcePermissionForResumeOnReboot() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.RECOVERY") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.REBOOT") != 0) {
            throw new java.lang.SecurityException("Caller must have android.permission.RECOVERY or android.permission.REBOOT for resume on reboot.");
        }
    }

    private void reportMetricsOnRequestLskf(java.lang.String str, int i) {
        int size;
        int uidFromPackageName = this.mInjector.getUidFromPackageName(str);
        synchronized (this) {
            size = this.mCallerPendingRequest.size();
        }
        com.android.server.recoverysystem.RecoverySystemService.PreferencesManager metricsPrefs = this.mInjector.getMetricsPrefs();
        metricsPrefs.putLong(str + REQUEST_LSKF_TIMESTAMP_PREF_SUFFIX, this.mInjector.getCurrentTimeMillis());
        metricsPrefs.incrementIntKey(str + REQUEST_LSKF_COUNT_PREF_SUFFIX, 0);
        this.mInjector.reportRebootEscrowPreparationMetrics(uidFromPackageName, i, size);
    }

    public boolean requestLskf(java.lang.String str, android.content.IntentSender intentSender) {
        enforcePermissionForResumeOnReboot();
        if (str == null) {
            android.util.Slog.w(TAG, "Missing packageName when requesting lskf.");
            return false;
        }
        int updateRoRPreparationStateOnNewRequest = updateRoRPreparationStateOnNewRequest(str, intentSender);
        reportMetricsOnRequestLskf(str, updateRoRPreparationStateOnNewRequest);
        switch (updateRoRPreparationStateOnNewRequest) {
            case 0:
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.internal.widget.LockSettingsInternal lockSettingsService = this.mInjector.getLockSettingsService();
                    if (lockSettingsService == null) {
                        android.util.Slog.e(TAG, "Failed to get lock settings service, skipping prepareRebootEscrow");
                        return false;
                    }
                    if (lockSettingsService.prepareRebootEscrow()) {
                        return true;
                    }
                    clearRoRPreparationState();
                    return false;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            case 1:
                sendPreparedForRebootIntentIfNeeded(intentSender);
                return true;
            case 2:
                return true;
            default:
                throw new java.lang.IllegalStateException("Unsupported action type on new request " + updateRoRPreparationStateOnNewRequest);
        }
    }

    @com.android.server.recoverysystem.RecoverySystemService.ResumeOnRebootActionsOnRequest
    private synchronized int updateRoRPreparationStateOnNewRequest(java.lang.String str, android.content.IntentSender intentSender) {
        try {
            if (!this.mCallerPreparedForReboot.isEmpty()) {
                if (this.mCallerPreparedForReboot.contains(str)) {
                    android.util.Slog.i(TAG, "RoR already has prepared for " + str);
                }
                this.mCallerPreparedForReboot.add(str);
                return 1;
            }
            boolean isEmpty = this.mCallerPendingRequest.isEmpty();
            if (this.mCallerPendingRequest.containsKey(str)) {
                android.util.Slog.i(TAG, "Duplicate RoR preparation request for " + str);
            }
            this.mCallerPendingRequest.put(str, intentSender);
            return isEmpty ? 0 : 2;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private void reportMetricsOnPreparedForReboot() {
        java.util.ArrayList<java.lang.String> arrayList;
        int i;
        long currentTimeMillis = this.mInjector.getCurrentTimeMillis();
        synchronized (this) {
            arrayList = new java.util.ArrayList(this.mCallerPreparedForReboot);
        }
        com.android.server.recoverysystem.RecoverySystemService.PreferencesManager metricsPrefs = this.mInjector.getMetricsPrefs();
        metricsPrefs.putLong(LSKF_CAPTURED_TIMESTAMP_PREF, currentTimeMillis);
        metricsPrefs.incrementIntKey(LSKF_CAPTURED_COUNT_PREF, 0);
        for (java.lang.String str : arrayList) {
            int uidFromPackageName = this.mInjector.getUidFromPackageName(str);
            long j = metricsPrefs.getLong(str + REQUEST_LSKF_TIMESTAMP_PREF_SUFFIX, -1L);
            if (j != -1 && currentTimeMillis > j) {
                i = ((int) (currentTimeMillis - j)) / 1000;
            } else {
                i = -1;
            }
            android.util.Slog.i(TAG, java.lang.String.format("Reporting lskf captured, lskf capture takes %d seconds for package %s", java.lang.Integer.valueOf(i), str));
            this.mInjector.reportRebootEscrowLskfCapturedMetrics(uidFromPackageName, arrayList.size(), i);
        }
    }

    public void onPreparedForReboot(boolean z) {
        if (!z) {
            return;
        }
        updateRoRPreparationStateOnPreparedForReboot();
        reportMetricsOnPreparedForReboot();
    }

    private synchronized void updateRoRPreparationStateOnPreparedForReboot() {
        try {
            if (!this.mCallerPreparedForReboot.isEmpty()) {
                android.util.Slog.w(TAG, "onPreparedForReboot called when some clients have prepared.");
            }
            if (this.mCallerPendingRequest.isEmpty()) {
                android.util.Slog.w(TAG, "onPreparedForReboot called but no client has requested.");
            }
            for (int i = 0; i < this.mCallerPendingRequest.size(); i++) {
                sendPreparedForRebootIntentIfNeeded(this.mCallerPendingRequest.valueAt(i));
                this.mCallerPreparedForReboot.add(this.mCallerPendingRequest.keyAt(i));
            }
            this.mCallerPendingRequest.clear();
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private void sendPreparedForRebootIntentIfNeeded(android.content.IntentSender intentSender) {
        if (intentSender != null) {
            try {
                intentSender.sendIntent(null, 0, null, null, null);
            } catch (android.content.IntentSender.SendIntentException e) {
                android.util.Slog.w(TAG, "Could not send intent for prepared reboot: " + e.getMessage());
            }
        }
    }

    public boolean clearLskf(java.lang.String str) {
        enforcePermissionForResumeOnReboot();
        if (str == null) {
            android.util.Slog.w(TAG, "Missing packageName when clearing lskf.");
            return false;
        }
        int updateRoRPreparationStateOnClear = updateRoRPreparationStateOnClear(str);
        switch (updateRoRPreparationStateOnClear) {
            case 0:
                android.util.Slog.w(TAG, "RoR clear called before preparation for caller " + str);
                return true;
            case 1:
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.internal.widget.LockSettingsInternal lockSettingsService = this.mInjector.getLockSettingsService();
                    if (lockSettingsService != null) {
                        return lockSettingsService.clearRebootEscrow();
                    }
                    android.util.Slog.e(TAG, "Failed to get lock settings service, skipping clearRebootEscrow");
                    return false;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            case 2:
                return true;
            default:
                throw new java.lang.IllegalStateException("Unsupported action type on clear " + updateRoRPreparationStateOnClear);
        }
    }

    @com.android.server.recoverysystem.RecoverySystemService.ResumeOnRebootActionsOnClear
    private synchronized int updateRoRPreparationStateOnClear(java.lang.String str) {
        boolean z = false;
        if (!this.mCallerPreparedForReboot.contains(str) && !this.mCallerPendingRequest.containsKey(str)) {
            android.util.Slog.w(TAG, str + " hasn't prepared for resume on reboot");
            return 0;
        }
        this.mCallerPendingRequest.remove(str);
        this.mCallerPreparedForReboot.remove(str);
        if (this.mCallerPendingRequest.isEmpty() && this.mCallerPreparedForReboot.isEmpty()) {
            z = true;
        }
        return z ? 1 : 2;
    }

    private boolean isAbDevice() {
        return "true".equalsIgnoreCase(this.mInjector.systemPropertiesGet(AB_UPDATE));
    }

    private boolean verifySlotForNextBoot(boolean z) {
        if (!isAbDevice()) {
            android.util.Slog.w(TAG, "Device isn't a/b, skipping slot verification.");
            return true;
        }
        try {
            android.hardware.boot.IBootControl bootControl = this.mInjector.getBootControl();
            if (bootControl == null) {
                android.util.Slog.w(TAG, "Cannot get the boot control HAL, skipping slot verification.");
                return true;
            }
            try {
                int currentSlot = bootControl.getCurrentSlot();
                if (currentSlot != 0 && currentSlot != 1) {
                    throw new java.lang.IllegalStateException("Current boot slot should be 0 or 1, got " + currentSlot);
                }
                int activeBootSlot = bootControl.getActiveBootSlot();
                if (z) {
                    currentSlot = currentSlot == 0 ? 1 : 0;
                }
                if (activeBootSlot == currentSlot) {
                    return true;
                }
                android.util.Slog.w(TAG, "The next active boot slot doesn't match the expected value, expected " + currentSlot + ", got " + activeBootSlot);
                return false;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to query the active slots", e);
                return false;
            }
        } catch (android.os.RemoteException e2) {
            android.util.Slog.w(TAG, "Failed to get the boot control HAL " + e2);
            return false;
        }
    }

    private com.android.server.recoverysystem.RecoverySystemService.RebootPreparationError armRebootEscrow(java.lang.String str, boolean z) {
        if (str == null) {
            android.util.Slog.w(TAG, "Missing packageName when rebooting with lskf.");
            return new com.android.server.recoverysystem.RecoverySystemService.RebootPreparationError(2000, 0);
        }
        if (!isLskfCaptured(str)) {
            return new com.android.server.recoverysystem.RecoverySystemService.RebootPreparationError(3000, 0);
        }
        if (!verifySlotForNextBoot(z)) {
            return new com.android.server.recoverysystem.RecoverySystemService.RebootPreparationError(4000, 0);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.internal.widget.LockSettingsInternal lockSettingsService = this.mInjector.getLockSettingsService();
            if (lockSettingsService == null) {
                android.util.Slog.e(TAG, "Failed to get lock settings service, skipping armRebootEscrow");
                return new com.android.server.recoverysystem.RecoverySystemService.RebootPreparationError(5000, 3);
            }
            int armRebootEscrow = lockSettingsService.armRebootEscrow();
            if (armRebootEscrow == 0) {
                return new com.android.server.recoverysystem.RecoverySystemService.RebootPreparationError(0, 0);
            }
            android.util.Slog.w(TAG, "Failure to escrow key for reboot, providerErrorCode: " + armRebootEscrow);
            return new com.android.server.recoverysystem.RecoverySystemService.RebootPreparationError(5000, armRebootEscrow);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean useServerBasedRoR() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.DeviceConfig.getBoolean("ota", "server_based_ror_enabled", false);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void reportMetricsOnRebootWithLskf(java.lang.String str, boolean z, com.android.server.recoverysystem.RecoverySystemService.RebootPreparationError rebootPreparationError) {
        int size;
        int i;
        int uidFromPackageName = this.mInjector.getUidFromPackageName(str);
        boolean useServerBasedRoR = useServerBasedRoR();
        synchronized (this) {
            size = this.mCallerPreparedForReboot.size();
        }
        long currentTimeMillis = this.mInjector.getCurrentTimeMillis();
        com.android.server.recoverysystem.RecoverySystemService.PreferencesManager metricsPrefs = this.mInjector.getMetricsPrefs();
        long j = metricsPrefs.getLong(LSKF_CAPTURED_TIMESTAMP_PREF, -1L);
        if (j != -1 && currentTimeMillis > j) {
            i = ((int) (currentTimeMillis - j)) / 1000;
        } else {
            i = -1;
        }
        int i2 = metricsPrefs.getInt(str + REQUEST_LSKF_COUNT_PREF_SUFFIX, -1);
        int i3 = metricsPrefs.getInt(LSKF_CAPTURED_COUNT_PREF, -1);
        android.util.Slog.i(TAG, java.lang.String.format("Reporting reboot with lskf, package name %s, client count %d, request count %d, lskf captured count %d, duration since lskf captured %d seconds.", str, java.lang.Integer.valueOf(size), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i)));
        this.mInjector.reportRebootEscrowRebootMetrics(rebootPreparationError.getErrorCodeForMetrics(), uidFromPackageName, size, i2, z, useServerBasedRoR, i, i3);
    }

    private synchronized void clearRoRPreparationState() {
        this.mCallerPendingRequest.clear();
        this.mCallerPreparedForReboot.clear();
    }

    private void clearRoRPreparationStateOnRebootFailure(com.android.server.recoverysystem.RecoverySystemService.RebootPreparationError rebootPreparationError) {
        if (!FATAL_ARM_ESCROW_ERRORS.contains(java.lang.Integer.valueOf(rebootPreparationError.mProviderErrorCode))) {
            return;
        }
        android.util.Slog.w(TAG, "Clearing resume on reboot states for all clients on arm escrow error: " + rebootPreparationError.mProviderErrorCode);
        clearRoRPreparationState();
    }

    private int rebootWithLskfImpl(java.lang.String str, java.lang.String str2, boolean z) {
        com.android.server.recoverysystem.RecoverySystemService.RebootPreparationError armRebootEscrow = armRebootEscrow(str, z);
        reportMetricsOnRebootWithLskf(str, z, armRebootEscrow);
        clearRoRPreparationStateOnRebootFailure(armRebootEscrow);
        int i = armRebootEscrow.mRebootErrorCode;
        if (i != 0) {
            return i;
        }
        this.mInjector.getMetricsPrefs().deletePrefsFile();
        com.android.server.Watchdog.getInstance().pauseWatchingCurrentThreadFor(REBOOT_WATCHDOG_PAUSE_DURATION_MS, "reboot can be slow");
        this.mInjector.getPowerManager().reboot(str2);
        return 1000;
    }

    @android.annotation.EnforcePermission("android.permission.RECOVERY")
    public int rebootWithLskfAssumeSlotSwitch(java.lang.String str, java.lang.String str2) {
        rebootWithLskfAssumeSlotSwitch_enforcePermission();
        return rebootWithLskfImpl(str, str2, true);
    }

    public int rebootWithLskf(java.lang.String str, java.lang.String str2, boolean z) {
        enforcePermissionForResumeOnReboot();
        return rebootWithLskfImpl(str, str2, z);
    }

    private static android.apex.CompressedApexInfoList getCompressedApexInfoList(java.lang.String str) throws java.io.IOException {
        java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(str);
        try {
            java.util.zip.ZipEntry entry = zipFile.getEntry("apex_info.pb");
            if (entry == null) {
                zipFile.close();
                return null;
            }
            if (entry.getSize() >= APEX_INFO_SIZE_LIMIT) {
                throw new java.lang.IllegalArgumentException("apex_info.pb has size " + entry.getSize() + " which is larger than the permitted limit" + APEX_INFO_SIZE_LIMIT);
            }
            if (entry.getSize() == 0) {
                android.apex.CompressedApexInfoList compressedApexInfoList = new android.apex.CompressedApexInfoList();
                compressedApexInfoList.apexInfos = new android.apex.CompressedApexInfo[0];
                zipFile.close();
                return compressedApexInfoList;
            }
            android.util.Log.i(TAG, "Allocating " + entry.getSize() + " bytes of memory to store OTA Metadata");
            int size = (int) entry.getSize();
            byte[] bArr = new byte[size];
            java.io.InputStream inputStream = zipFile.getInputStream(entry);
            try {
                int read = inputStream.read(bArr);
                java.lang.String str2 = "Read " + read + " when expecting " + size;
                android.util.Log.e(TAG, str2);
                if (read != size) {
                    throw new java.io.IOException(str2);
                }
                inputStream.close();
                android.ota.nano.OtaPackageMetadata.ApexMetadata parseFrom = android.ota.nano.OtaPackageMetadata.ApexMetadata.parseFrom(bArr);
                android.apex.CompressedApexInfoList compressedApexInfoList2 = new android.apex.CompressedApexInfoList();
                compressedApexInfoList2.apexInfos = (android.apex.CompressedApexInfo[]) java.util.Arrays.stream(parseFrom.apexInfo).filter(new java.util.function.Predicate() { // from class: com.android.server.recoverysystem.RecoverySystemService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean z;
                        z = ((android.ota.nano.OtaPackageMetadata.ApexInfo) obj).isCompressed;
                        return z;
                    }
                }).map(new java.util.function.Function() { // from class: com.android.server.recoverysystem.RecoverySystemService$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        android.apex.CompressedApexInfo lambda$getCompressedApexInfoList$1;
                        lambda$getCompressedApexInfoList$1 = com.android.server.recoverysystem.RecoverySystemService.lambda$getCompressedApexInfoList$1((android.ota.nano.OtaPackageMetadata.ApexInfo) obj);
                        return lambda$getCompressedApexInfoList$1;
                    }
                }).toArray(new java.util.function.IntFunction() { // from class: com.android.server.recoverysystem.RecoverySystemService$$ExternalSyntheticLambda2
                    @Override // java.util.function.IntFunction
                    public final java.lang.Object apply(int i) {
                        android.apex.CompressedApexInfo[] lambda$getCompressedApexInfoList$2;
                        lambda$getCompressedApexInfoList$2 = com.android.server.recoverysystem.RecoverySystemService.lambda$getCompressedApexInfoList$2(i);
                        return lambda$getCompressedApexInfoList$2;
                    }
                });
                zipFile.close();
                return compressedApexInfoList2;
            } finally {
            }
        } catch (java.lang.Throwable th) {
            try {
                zipFile.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.apex.CompressedApexInfo lambda$getCompressedApexInfoList$1(android.ota.nano.OtaPackageMetadata.ApexInfo apexInfo) {
        android.apex.CompressedApexInfo compressedApexInfo = new android.apex.CompressedApexInfo();
        compressedApexInfo.moduleName = apexInfo.packageName;
        compressedApexInfo.decompressedSize = apexInfo.decompressedSize;
        compressedApexInfo.versionCode = apexInfo.version;
        return compressedApexInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.apex.CompressedApexInfo[] lambda$getCompressedApexInfoList$2(int i) {
        return new android.apex.CompressedApexInfo[i];
    }

    @android.annotation.EnforcePermission("android.permission.RECOVERY")
    public boolean allocateSpaceForUpdate(java.lang.String str) {
        allocateSpaceForUpdate_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                try {
                    android.apex.CompressedApexInfoList compressedApexInfoList = getCompressedApexInfoList(str);
                    if (compressedApexInfoList == null) {
                        android.util.Log.i(TAG, "apex_info.pb not present in OTA package. Assuming device doesn't support compressedAPEX, continueing without allocating space.");
                        return true;
                    }
                    com.android.server.pm.ApexManager.getInstance().reserveSpaceForCompressedApex(compressedApexInfoList);
                    return true;
                } catch (android.os.RemoteException e) {
                    e.rethrowAsRuntimeException();
                    return false;
                }
            } catch (java.io.IOException | java.lang.UnsupportedOperationException e2) {
                android.util.Slog.e(TAG, "Failed to reserve space for compressed apex: ", e2);
                return false;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isLskfCaptured(java.lang.String str) {
        boolean contains;
        enforcePermissionForResumeOnReboot();
        synchronized (this) {
            contains = this.mCallerPreparedForReboot.contains(str);
        }
        if (!contains) {
            android.util.Slog.i(TAG, "Reboot requested before prepare completed for caller " + str);
            return false;
        }
        return true;
    }

    private boolean checkAndWaitForUncryptService() {
        for (int i = 0; i < 30; i++) {
            if (!(android.net.INetd.IF_FLAG_RUNNING.equals(this.mInjector.systemPropertiesGet(INIT_SERVICE_UNCRYPT)) || android.net.INetd.IF_FLAG_RUNNING.equals(this.mInjector.systemPropertiesGet(INIT_SERVICE_SETUP_BCB)) || android.net.INetd.IF_FLAG_RUNNING.equals(this.mInjector.systemPropertiesGet(INIT_SERVICE_CLEAR_BCB)))) {
                return true;
            }
            try {
                this.mInjector.threadSleep(1000L);
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.w(TAG, "Interrupted:", e);
            }
        }
        return false;
    }

    private boolean setupOrClearBcb(boolean z, java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.RECOVERY", null);
        if (!checkAndWaitForUncryptService()) {
            android.util.Slog.e(TAG, "uncrypt service is unavailable.");
            return false;
        }
        if (z) {
            this.mInjector.systemPropertiesSet("ctl.start", "setup-bcb");
        } else {
            this.mInjector.systemPropertiesSet("ctl.start", "clear-bcb");
        }
        com.android.server.recoverysystem.RecoverySystemService.UncryptSocket connectService = this.mInjector.connectService();
        if (connectService == null) {
            android.util.Slog.e(TAG, "Failed to connect to uncrypt socket");
            return false;
        }
        try {
            if (z) {
                try {
                    connectService.sendCommand(str);
                } catch (java.io.IOException e) {
                    android.util.Slog.e(TAG, "IOException when communicating with uncrypt:", e);
                    connectService.close();
                    return false;
                }
            }
            int percentageUncrypted = connectService.getPercentageUncrypted();
            connectService.sendAck();
            if (percentageUncrypted != 100) {
                android.util.Slog.e(TAG, "uncrypt failed with status: " + percentageUncrypted);
                connectService.close();
                return false;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("uncrypt ");
            sb.append(z ? "setup" : "clear");
            sb.append(" bcb successfully finished.");
            android.util.Slog.i(TAG, sb.toString());
            connectService.close();
            return true;
        } catch (java.lang.Throwable th) {
            connectService.close();
            throw th;
        }
    }

    public static class UncryptSocket {
        private java.io.DataInputStream mInputStream;
        private android.net.LocalSocket mLocalSocket;
        private java.io.DataOutputStream mOutputStream;

        public boolean connectService() {
            boolean z;
            this.mLocalSocket = new android.net.LocalSocket();
            int i = 0;
            while (true) {
                if (i >= 30) {
                    z = false;
                    break;
                }
                try {
                    this.mLocalSocket.connect(new android.net.LocalSocketAddress(com.android.server.recoverysystem.RecoverySystemService.UNCRYPT_SOCKET, android.net.LocalSocketAddress.Namespace.RESERVED));
                    z = true;
                    break;
                } catch (java.io.IOException e) {
                    try {
                        java.lang.Thread.sleep(1000L);
                    } catch (java.lang.InterruptedException e2) {
                        android.util.Slog.w(com.android.server.recoverysystem.RecoverySystemService.TAG, "Interrupted:", e2);
                    }
                    i++;
                }
            }
            if (!z) {
                android.util.Slog.e(com.android.server.recoverysystem.RecoverySystemService.TAG, "Timed out connecting to uncrypt socket");
                close();
                return false;
            }
            try {
                this.mInputStream = new java.io.DataInputStream(this.mLocalSocket.getInputStream());
                this.mOutputStream = new java.io.DataOutputStream(this.mLocalSocket.getOutputStream());
                return true;
            } catch (java.io.IOException e3) {
                close();
                return false;
            }
        }

        public void sendCommand(java.lang.String str) throws java.io.IOException {
            byte[] bytes = str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            this.mOutputStream.writeInt(bytes.length);
            this.mOutputStream.write(bytes, 0, bytes.length);
        }

        public int getPercentageUncrypted() throws java.io.IOException {
            return this.mInputStream.readInt();
        }

        public void sendAck() throws java.io.IOException {
            this.mOutputStream.writeInt(0);
        }

        public void close() {
            libcore.io.IoUtils.closeQuietly(this.mInputStream);
            libcore.io.IoUtils.closeQuietly(this.mOutputStream);
            libcore.io.IoUtils.closeQuietly(this.mLocalSocket);
        }
    }

    private boolean isCallerShell() {
        int callingUid = android.os.Binder.getCallingUid();
        return callingUid == 2000 || callingUid == 0;
    }

    private void enforceShell() {
        if (!isCallerShell()) {
            throw new java.lang.SecurityException("Caller must be shell");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        enforceShell();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            new com.android.server.recoverysystem.RecoverySystemShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
