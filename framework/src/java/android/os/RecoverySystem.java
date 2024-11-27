package android.os;

/* loaded from: classes3.dex */
public class RecoverySystem {
    private static final java.lang.String ACTION_EUICC_FACTORY_RESET = "com.android.internal.action.EUICC_FACTORY_RESET";
    private static final java.lang.String ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS = "com.android.internal.action.EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS";
    private static final long DEFAULT_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 30000;
    private static final long DEFAULT_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 45000;
    private static final java.lang.String LAST_INSTALL_PATH = "last_install";
    private static final java.lang.String LAST_PREFIX = "last_";
    private static final int LOG_FILE_MAX_LENGTH = 65536;
    private static final long MAX_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 60000;
    private static final long MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 90000;
    private static final long MIN_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 5000;
    private static final long MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS = 15000;
    private static final java.lang.String PACKAGE_NAME_EUICC_DATA_MANAGEMENT_CALLBACK = "android";
    private static final long PUBLISH_PROGRESS_INTERVAL_MS = 500;

    @android.annotation.SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_INVALID_PACKAGE_NAME = 2000;

    @android.annotation.SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_LSKF_NOT_CAPTURED = 3000;
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_NONE = 0;

    @android.annotation.SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_PROVIDER_PREPARATION_FAILURE = 5000;

    @android.annotation.SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_SLOT_MISMATCH = 4000;

    @android.annotation.SystemApi
    public static final int RESUME_ON_REBOOT_REBOOT_ERROR_UNSPECIFIED = 1000;
    private static final java.lang.String TAG = "RecoverySystem";
    private final android.os.IRecoverySystem mService;
    private static final java.io.File DEFAULT_KEYSTORE = new java.io.File("/system/etc/security/otacerts.zip");
    private static final java.io.File RECOVERY_DIR = new java.io.File("/cache/recovery");
    private static final java.io.File LOG_FILE = new java.io.File(RECOVERY_DIR, "log");
    public static final java.io.File BLOCK_MAP_FILE = new java.io.File(RECOVERY_DIR, "block.map");
    public static final java.io.File UNCRYPT_PACKAGE_FILE = new java.io.File(RECOVERY_DIR, "uncrypt_file");
    public static final java.io.File UNCRYPT_STATUS_FILE = new java.io.File(RECOVERY_DIR, "uncrypt_status");
    private static final java.lang.Object sRequestLock = new java.lang.Object();

    public interface ProgressListener {
        void onProgress(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResumeOnRebootRebootErrorCode {
    }

    private static java.util.HashSet<java.security.cert.X509Certificate> getTrustedCerts(java.io.File file) throws java.io.IOException, java.security.GeneralSecurityException {
        java.util.HashSet<java.security.cert.X509Certificate> hashSet = new java.util.HashSet<>();
        if (file == null) {
            file = DEFAULT_KEYSTORE;
        }
        java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(file);
        try {
            java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
            java.util.Enumeration<? extends java.util.zip.ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                java.io.InputStream inputStream = zipFile.getInputStream(entries.nextElement());
                try {
                    hashSet.add((java.security.cert.X509Certificate) certificateFactory.generateCertificate(inputStream));
                    inputStream.close();
                } finally {
                }
            }
            return hashSet;
        } finally {
            zipFile.close();
        }
    }

    public static void verifyPackage(java.io.File file, final android.os.RecoverySystem.ProgressListener progressListener, java.io.File file2) throws java.io.IOException, java.security.GeneralSecurityException {
        final long length = file.length();
        final java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(file, "r");
        try {
            final long currentTimeMillis = java.lang.System.currentTimeMillis();
            boolean z = false;
            if (progressListener != null) {
                progressListener.onProgress(0);
            }
            randomAccessFile.seek(length - 6);
            byte[] bArr = new byte[6];
            randomAccessFile.readFully(bArr);
            if (bArr[2] != -1 || bArr[3] != -1) {
                throw new java.security.SignatureException("no signature in file (no footer)");
            }
            final int i = (bArr[4] & 255) | ((bArr[5] & 255) << 8);
            int i2 = ((bArr[1] & 255) << 8) | (bArr[0] & 255);
            int i3 = i + 22;
            byte[] bArr2 = new byte[i3];
            randomAccessFile.seek(length - i3);
            randomAccessFile.readFully(bArr2);
            if (bArr2[0] != 80 || bArr2[1] != 75 || bArr2[2] != 5 || bArr2[3] != 6) {
                throw new java.security.SignatureException("no signature in file (bad footer)");
            }
            for (int i4 = 4; i4 < i3 - 3; i4++) {
                if (bArr2[i4] == 80 && bArr2[i4 + 1] == 75 && bArr2[i4 + 2] == 5) {
                    if (bArr2[i4 + 3] == 6) {
                        throw new java.security.SignatureException("EOCD marker found after start of EOCD");
                    }
                }
            }
            sun.security.pkcs.PKCS7 pkcs7 = new sun.security.pkcs.PKCS7(new java.io.ByteArrayInputStream(bArr2, i3 - i2, i2));
            java.security.cert.X509Certificate[] certificates = pkcs7.getCertificates();
            if (certificates == null || certificates.length == 0) {
                throw new java.security.SignatureException("signature contains no certificates");
            }
            java.security.PublicKey publicKey = certificates[0].getPublicKey();
            sun.security.pkcs.SignerInfo[] signerInfos = pkcs7.getSignerInfos();
            if (signerInfos == null || signerInfos.length == 0) {
                throw new java.security.SignatureException("signature contains no signedData");
            }
            sun.security.pkcs.SignerInfo signerInfo = signerInfos[0];
            java.util.Iterator<java.security.cert.X509Certificate> it = getTrustedCerts(file2 == null ? DEFAULT_KEYSTORE : file2).iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().getPublicKey().equals(publicKey)) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!z) {
                throw new java.security.SignatureException("signature doesn't match any trusted key");
            }
            randomAccessFile.seek(0L);
            sun.security.pkcs.SignerInfo verify = pkcs7.verify(signerInfo, new java.io.InputStream() { // from class: android.os.RecoverySystem.1
                long lastPublishTime;
                long toRead;
                long soFar = 0;
                int lastPercent = 0;

                {
                    this.toRead = (length - i) - 2;
                    this.lastPublishTime = currentTimeMillis;
                }

                @Override // java.io.InputStream
                public int read() throws java.io.IOException {
                    throw new java.lang.UnsupportedOperationException();
                }

                @Override // java.io.InputStream
                public int read(byte[] bArr3, int i5, int i6) throws java.io.IOException {
                    if (this.soFar >= this.toRead || java.lang.Thread.currentThread().isInterrupted()) {
                        return -1;
                    }
                    if (this.soFar + i6 > this.toRead) {
                        i6 = (int) (this.toRead - this.soFar);
                    }
                    int read = randomAccessFile.read(bArr3, i5, i6);
                    this.soFar += read;
                    if (progressListener != null) {
                        long currentTimeMillis2 = java.lang.System.currentTimeMillis();
                        int i7 = (int) ((this.soFar * 100) / this.toRead);
                        if (i7 > this.lastPercent && currentTimeMillis2 - this.lastPublishTime > android.os.RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                            this.lastPercent = i7;
                            this.lastPublishTime = currentTimeMillis2;
                            progressListener.onProgress(this.lastPercent);
                        }
                    }
                    return read;
                }
            });
            boolean interrupted = java.lang.Thread.interrupted();
            if (progressListener != null) {
                progressListener.onProgress(100);
            }
            if (interrupted) {
                throw new java.security.SignatureException("verification was interrupted");
            }
            if (verify == null) {
                throw new java.security.SignatureException("signature digest verification failed");
            }
        } finally {
            randomAccessFile.close();
        }
    }

    @java.lang.Deprecated
    private static boolean verifyPackageCompatibility(java.io.InputStream inputStream) throws java.io.IOException {
        return true;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static boolean verifyPackageCompatibility(java.io.File file) throws java.io.IOException {
        return true;
    }

    @android.annotation.SystemApi
    public static void processPackage(android.content.Context context, java.io.File file, android.os.RecoverySystem.ProgressListener progressListener, android.os.Handler handler) throws java.io.IOException {
        android.os.RecoverySystem.AnonymousClass2 anonymousClass2;
        java.lang.String canonicalPath = file.getCanonicalPath();
        if (!canonicalPath.startsWith("/data/")) {
            return;
        }
        android.os.RecoverySystem recoverySystem = (android.os.RecoverySystem) context.getSystemService("recovery");
        if (progressListener == null) {
            anonymousClass2 = null;
        } else {
            if (handler == null) {
                handler = new android.os.Handler(context.getMainLooper());
            }
            anonymousClass2 = new android.os.RecoverySystem.AnonymousClass2(handler, progressListener);
        }
        if (!recoverySystem.uncrypt(canonicalPath, anonymousClass2)) {
            throw new java.io.IOException("process package failed");
        }
    }

    /* renamed from: android.os.RecoverySystem$2, reason: invalid class name */
    class AnonymousClass2 extends android.os.IRecoverySystemProgressListener.Stub {
        int lastProgress = 0;
        long lastPublishTime = java.lang.System.currentTimeMillis();
        final /* synthetic */ android.os.RecoverySystem.ProgressListener val$listener;
        final /* synthetic */ android.os.Handler val$progressHandler;

        AnonymousClass2(android.os.Handler handler, android.os.RecoverySystem.ProgressListener progressListener) {
            this.val$progressHandler = handler;
            this.val$listener = progressListener;
        }

        @Override // android.os.IRecoverySystemProgressListener
        public void onProgress(final int i) {
            final long currentTimeMillis = java.lang.System.currentTimeMillis();
            this.val$progressHandler.post(new java.lang.Runnable() { // from class: android.os.RecoverySystem.2.1
                @Override // java.lang.Runnable
                public void run() {
                    if (i > android.os.RecoverySystem.AnonymousClass2.this.lastProgress && currentTimeMillis - android.os.RecoverySystem.AnonymousClass2.this.lastPublishTime > android.os.RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                        android.os.RecoverySystem.AnonymousClass2.this.lastProgress = i;
                        android.os.RecoverySystem.AnonymousClass2.this.lastPublishTime = currentTimeMillis;
                        android.os.RecoverySystem.AnonymousClass2.this.val$listener.onProgress(i);
                    }
                }
            });
        }
    }

    @android.annotation.SystemApi
    public static void processPackage(android.content.Context context, java.io.File file, android.os.RecoverySystem.ProgressListener progressListener) throws java.io.IOException {
        processPackage(context, file, progressListener, null);
    }

    public static void installPackage(android.content.Context context, java.io.File file) throws java.io.IOException {
        installPackage(context, file, false);
    }

    @android.annotation.SystemApi
    public static void installPackage(android.content.Context context, java.io.File file, boolean z) throws java.io.IOException {
        synchronized (sRequestLock) {
            LOG_FILE.delete();
            UNCRYPT_PACKAGE_FILE.delete();
            java.lang.String canonicalPath = file.getCanonicalPath();
            android.util.Log.w(TAG, "!!! REBOOTING TO INSTALL " + canonicalPath + " !!!");
            boolean endsWith = canonicalPath.endsWith("_s.zip");
            if (canonicalPath.startsWith("/data/")) {
                if (z) {
                    if (!BLOCK_MAP_FILE.exists()) {
                        android.util.Log.e(TAG, "Package claimed to have been processed but failed to find the block map file.");
                        throw new java.io.IOException("Failed to find block map file");
                    }
                } else {
                    java.io.FileWriter fileWriter = new java.io.FileWriter(UNCRYPT_PACKAGE_FILE);
                    try {
                        fileWriter.write(canonicalPath + "\n");
                        fileWriter.close();
                        if (!UNCRYPT_PACKAGE_FILE.setReadable(true, false) || !UNCRYPT_PACKAGE_FILE.setWritable(true, false)) {
                            android.util.Log.e(TAG, "Error setting permission for " + UNCRYPT_PACKAGE_FILE);
                        }
                        BLOCK_MAP_FILE.delete();
                    } catch (java.lang.Throwable th) {
                        fileWriter.close();
                        throw th;
                    }
                }
                canonicalPath = "@/cache/recovery/block.map";
            }
            java.lang.String str = ("--update_package=" + canonicalPath + "\n") + ("--locale=" + java.util.Locale.getDefault().toLanguageTag() + "\n");
            if (endsWith) {
                str = str + "--security\n";
            }
            android.os.RecoverySystem recoverySystem = (android.os.RecoverySystem) context.getSystemService("recovery");
            if (!recoverySystem.setupBcb(str)) {
                throw new java.io.IOException("Setup BCB failed");
            }
            try {
            } catch (android.os.RemoteException e) {
                recoverySystem.clearBcb();
                e.rethrowAsRuntimeException();
            }
            if (!recoverySystem.allocateSpaceForUpdate(file)) {
                recoverySystem.clearBcb();
                throw new java.io.IOException("Failed to allocate space for update " + file.getAbsolutePath());
            }
            android.os.PowerManager powerManager = (android.os.PowerManager) context.getSystemService(android.content.Context.POWER_SERVICE);
            java.lang.String str2 = android.os.PowerManager.REBOOT_RECOVERY_UPDATE;
            if (context.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_LEANBACK) && ((android.hardware.display.DisplayManager) context.getSystemService(android.hardware.display.DisplayManager.class)).getDisplay(0).getState() != 2) {
                str2 = android.os.PowerManager.REBOOT_RECOVERY_UPDATE + ",quiescent";
            }
            powerManager.reboot(str2);
            throw new java.io.IOException("Reboot failed (no permissions?)");
        }
    }

    @android.annotation.SystemApi
    public static void prepareForUnattendedUpdate(android.content.Context context, java.lang.String str, android.content.IntentSender intentSender) throws java.io.IOException {
        if (str == null) {
            throw new java.lang.NullPointerException("updateToken == null");
        }
        android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) context.getSystemService(android.app.KeyguardManager.class);
        if (keyguardManager == null || !keyguardManager.isDeviceSecure()) {
            throw new java.io.IOException("Failed to request LSKF because the device doesn't have a lock screen. ");
        }
        if (!((android.os.RecoverySystem) context.getSystemService("recovery")).requestLskf(context.getPackageName(), intentSender)) {
            throw new java.io.IOException("preparation for update failed");
        }
    }

    @android.annotation.SystemApi
    public static void clearPrepareForUnattendedUpdate(android.content.Context context) throws java.io.IOException {
        if (!((android.os.RecoverySystem) context.getSystemService("recovery")).clearLskf(context.getPackageName())) {
            throw new java.io.IOException("could not reset unattended update state");
        }
    }

    @android.annotation.SystemApi
    public static void rebootAndApply(android.content.Context context, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        if (str == null) {
            throw new java.lang.NullPointerException("updateToken == null");
        }
        if (((android.os.RecoverySystem) context.getSystemService("recovery")).rebootWithLskfAssumeSlotSwitch(context.getPackageName(), str2) != 0) {
            throw new java.io.IOException("system not prepared to apply update");
        }
    }

    @android.annotation.SystemApi
    public static boolean isPreparedForUnattendedUpdate(android.content.Context context) throws java.io.IOException {
        return ((android.os.RecoverySystem) context.getSystemService(android.os.RecoverySystem.class)).isLskfCaptured(context.getPackageName());
    }

    @android.annotation.SystemApi
    public static int rebootAndApply(android.content.Context context, java.lang.String str, boolean z) throws java.io.IOException {
        return ((android.os.RecoverySystem) context.getSystemService(android.os.RecoverySystem.class)).rebootWithLskf(context.getPackageName(), str, z);
    }

    @android.annotation.SystemApi
    public static void scheduleUpdateOnBoot(android.content.Context context, java.io.File file) throws java.io.IOException {
        java.lang.String canonicalPath = file.getCanonicalPath();
        boolean endsWith = canonicalPath.endsWith("_s.zip");
        if (canonicalPath.startsWith("/data/")) {
            canonicalPath = "@/cache/recovery/block.map";
        }
        java.lang.String str = ("--update_package=" + canonicalPath + "\n") + ("--locale=" + java.util.Locale.getDefault().toLanguageTag() + "\n");
        if (endsWith) {
            str = str + "--security\n";
        }
        if (!((android.os.RecoverySystem) context.getSystemService("recovery")).setupBcb(str)) {
            throw new java.io.IOException("schedule update on boot failed");
        }
    }

    @android.annotation.SystemApi
    public static void cancelScheduledUpdate(android.content.Context context) throws java.io.IOException {
        if (!((android.os.RecoverySystem) context.getSystemService("recovery")).clearBcb()) {
            throw new java.io.IOException("cancel scheduled update failed");
        }
    }

    public static void rebootWipeUserData(android.content.Context context) throws java.io.IOException {
        rebootWipeUserData(context, false, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(android.content.Context context, java.lang.String str) throws java.io.IOException {
        rebootWipeUserData(context, false, str, false, false);
    }

    public static void rebootWipeUserData(android.content.Context context, boolean z) throws java.io.IOException {
        rebootWipeUserData(context, z, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(android.content.Context context, boolean z, java.lang.String str, boolean z2) throws java.io.IOException {
        rebootWipeUserData(context, z, str, z2, false);
    }

    public static void rebootWipeUserData(android.content.Context context, boolean z, java.lang.String str, boolean z2, boolean z3) throws java.io.IOException {
        rebootWipeUserData(context, z, str, z2, z3, false);
    }

    public static void rebootWipeUserData(android.content.Context context, boolean z, java.lang.String str, boolean z2, boolean z3, boolean z4) throws java.io.IOException {
        java.lang.String str2;
        java.lang.String str3;
        android.os.UserManager userManager = (android.os.UserManager) context.getSystemService("user");
        if (!z2 && userManager.hasUserRestriction(android.os.UserManager.DISALLOW_FACTORY_RESET)) {
            throw new java.lang.SecurityException("Wiping data is not allowed for this user.");
        }
        final android.os.ConditionVariable conditionVariable = new android.os.ConditionVariable();
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_MASTER_CLEAR_NOTIFICATION);
        intent.addFlags(285212672);
        context.sendOrderedBroadcastAsUser(intent, android.os.UserHandle.SYSTEM, android.Manifest.permission.MASTER_CLEAR, new android.content.BroadcastReceiver() { // from class: android.os.RecoverySystem.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent2) {
                android.os.ConditionVariable.this.open();
            }
        }, null, 0, null, null);
        conditionVariable.block();
        android.telephony.euicc.EuiccManager euiccManager = (android.telephony.euicc.EuiccManager) context.getSystemService(android.telephony.euicc.EuiccManager.class);
        if (z3) {
            wipeEuiccData(context, "android");
        } else {
            removeEuiccInvisibleSubs(context, euiccManager);
        }
        java.lang.String str4 = null;
        if (!z) {
            str2 = null;
        } else {
            str2 = "--shutdown_after";
        }
        if (android.text.TextUtils.isEmpty(str)) {
            str3 = null;
        } else {
            str3 = "--reason=" + sanitizeArg(str + "," + android.text.format.DateFormat.format("yyyy-MM-ddTHH:mm:ssZ", java.lang.System.currentTimeMillis()).toString());
        }
        if (z4) {
            str4 = "--keep_memtag_mode";
        }
        bootCommand(context, str2, "--wipe_data", str3, "--locale=" + java.util.Locale.getDefault().toLanguageTag(), str4);
    }

    public static boolean wipeEuiccData(android.content.Context context, java.lang.String str) {
        if (android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.EUICC_PROVISIONED, 0) == 0) {
            android.util.Log.d(TAG, "Skipping eUICC wipe/retain as it is not provisioned");
            return true;
        }
        android.telephony.euicc.EuiccManager euiccManager = (android.telephony.euicc.EuiccManager) context.getSystemService(android.content.Context.EUICC_SERVICE);
        if (euiccManager == null || !euiccManager.isEnabled()) {
            return false;
        }
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(false);
        android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: android.os.RecoverySystem.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (android.os.RecoverySystem.ACTION_EUICC_FACTORY_RESET.equals(intent.getAction())) {
                    if (getResultCode() != 0) {
                        android.util.Log.e(android.os.RecoverySystem.TAG, "Error wiping euicc data, Detailed code = " + intent.getIntExtra(android.telephony.euicc.EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0));
                    } else {
                        android.util.Log.d(android.os.RecoverySystem.TAG, "Successfully wiped euicc data.");
                        atomicBoolean.set(true);
                    }
                    countDownLatch.countDown();
                }
            }
        };
        android.content.Intent intent = new android.content.Intent(ACTION_EUICC_FACTORY_RESET);
        intent.setPackage(str);
        android.app.PendingIntent broadcastAsUser = android.app.PendingIntent.getBroadcastAsUser(context, 0, intent, android.media.audio.Enums.AUDIO_FORMAT_DTS_HD, android.os.UserHandle.SYSTEM);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(ACTION_EUICC_FACTORY_RESET);
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("euiccWipeFinishReceiverThread");
        handlerThread.start();
        context.getApplicationContext().registerReceiver(broadcastReceiver, intentFilter, null, new android.os.Handler(handlerThread.getLooper()));
        euiccManager.eraseSubscriptions(broadcastAsUser);
        try {
            long j = android.provider.Settings.Global.getLong(context.getContentResolver(), android.provider.Settings.Global.EUICC_FACTORY_RESET_TIMEOUT_MILLIS, 30000L);
            if (j < 5000) {
                j = 5000;
            } else if (j > 60000) {
                j = 60000;
            }
            if (countDownLatch.await(j, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                context.getApplicationContext().unregisterReceiver(broadcastReceiver);
                return atomicBoolean.get();
            }
            android.util.Log.e(TAG, "Timeout wiping eUICC data.");
            return false;
        } catch (java.lang.InterruptedException e) {
            java.lang.Thread.currentThread().interrupt();
            android.util.Log.e(TAG, "Wiping eUICC data interrupted", e);
            return false;
        } finally {
            context.getApplicationContext().unregisterReceiver(broadcastReceiver);
        }
    }

    private static void removeEuiccInvisibleSubs(android.content.Context context, android.telephony.euicc.EuiccManager euiccManager) {
        if (android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.EUICC_PROVISIONED, 0) == 0) {
            android.util.Log.i(TAG, "Skip removing eUICC invisible profiles as it is not provisioned.");
            return;
        }
        if (euiccManager == null || !euiccManager.isEnabled()) {
            android.util.Log.i(TAG, "Skip removing eUICC invisible profiles as eUICC manager is not available.");
            return;
        }
        java.util.List<android.telephony.SubscriptionInfo> availableSubscriptionInfoList = ((android.telephony.SubscriptionManager) context.getSystemService(android.telephony.SubscriptionManager.class)).getAvailableSubscriptionInfoList();
        if (availableSubscriptionInfoList == null || availableSubscriptionInfoList.isEmpty()) {
            android.util.Log.i(TAG, "Skip removing eUICC invisible profiles as no available profiles found.");
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.telephony.SubscriptionInfo subscriptionInfo : availableSubscriptionInfoList) {
            if (subscriptionInfo.isEmbedded() && subscriptionInfo.getGroupUuid() != null && subscriptionInfo.isOpportunistic()) {
                arrayList.add(subscriptionInfo);
            }
        }
        removeEuiccInvisibleSubs(context, arrayList, euiccManager);
    }

    private static boolean removeEuiccInvisibleSubs(android.content.Context context, java.util.List<android.telephony.SubscriptionInfo> list, android.telephony.euicc.EuiccManager euiccManager) {
        if (list == null || list.isEmpty()) {
            android.util.Log.i(TAG, "There are no eUICC invisible profiles needed to be removed.");
            return true;
        }
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(list.size());
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(0);
        android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: android.os.RecoverySystem.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (android.os.RecoverySystem.ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS.equals(intent.getAction())) {
                    if (getResultCode() != 0) {
                        android.util.Log.e(android.os.RecoverySystem.TAG, "Error removing euicc opportunistic profile, Detailed code = " + intent.getIntExtra(android.telephony.euicc.EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0));
                    } else {
                        android.util.Log.e(android.os.RecoverySystem.TAG, "Successfully remove euicc opportunistic profile.");
                        atomicInteger.incrementAndGet();
                    }
                    countDownLatch.countDown();
                }
            }
        };
        android.content.Intent intent = new android.content.Intent(ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS);
        intent.setPackage("android");
        android.app.PendingIntent broadcastAsUser = android.app.PendingIntent.getBroadcastAsUser(context, 0, intent, android.media.audio.Enums.AUDIO_FORMAT_DTS_HD, android.os.UserHandle.SYSTEM);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(ACTION_EUICC_REMOVE_INVISIBLE_SUBSCRIPTIONS);
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("euiccRemovingSubsReceiverThread");
        handlerThread.start();
        context.getApplicationContext().registerReceiver(broadcastReceiver, intentFilter, null, new android.os.Handler(handlerThread.getLooper()));
        for (android.telephony.SubscriptionInfo subscriptionInfo : list) {
            try {
                android.util.Log.i(TAG, "Remove invisible subscription " + subscriptionInfo.getSubscriptionId() + " from card " + subscriptionInfo.getCardId());
                euiccManager.createForCardId(subscriptionInfo.getCardId()).deleteSubscription(subscriptionInfo.getSubscriptionId(), broadcastAsUser);
            } catch (java.lang.InterruptedException e) {
                java.lang.Thread.currentThread().interrupt();
                android.util.Log.e(TAG, "Removing invisible euicc profiles interrupted", e);
                return false;
            } finally {
                context.getApplicationContext().unregisterReceiver(broadcastReceiver);
                handlerThread.quit();
            }
        }
        long j = android.provider.Settings.Global.getLong(context.getContentResolver(), android.provider.Settings.Global.EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS, DEFAULT_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS);
        if (j < MIN_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS) {
            j = 15000;
        } else if (j > MAX_EUICC_REMOVING_INVISIBLE_PROFILES_TIMEOUT_MILLIS) {
            j = 90000;
        }
        if (!countDownLatch.await(j, java.util.concurrent.TimeUnit.MILLISECONDS)) {
            android.util.Log.e(TAG, "Timeout removing invisible euicc profiles.");
            return false;
        }
        context.getApplicationContext().unregisterReceiver(broadcastReceiver);
        handlerThread.quit();
        return atomicInteger.get() == list.size();
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void rebootPromptAndWipeUserData(android.content.Context context, java.lang.String str) throws java.io.IOException {
        android.os.IVold iVold;
        boolean z;
        java.lang.String str2;
        try {
            iVold = android.os.IVold.Stub.asInterface(android.os.ServiceManager.checkService("vold"));
        } catch (java.lang.Exception e) {
            iVold = null;
        }
        try {
            if (iVold != null) {
                z = iVold.needsCheckpoint();
            } else {
                android.util.Log.w(TAG, "Failed to get vold");
                z = false;
            }
        } catch (java.lang.Exception e2) {
            android.util.Log.w(TAG, "Failed to check for checkpointing");
            z = false;
            if (!z) {
            }
        }
        if (!z) {
            try {
                iVold.abortChanges("rescueparty", false);
                android.util.Log.i(TAG, "Rescue Party requested wipe. Aborting update");
                return;
            } catch (java.lang.Exception e3) {
                android.util.Log.i(TAG, "Rescue Party requested wipe. Rebooting instead.");
                ((android.os.PowerManager) context.getSystemService(android.content.Context.POWER_SERVICE)).reboot("rescueparty");
                return;
            }
        }
        if (android.text.TextUtils.isEmpty(str)) {
            str2 = null;
        } else {
            str2 = "--reason=" + sanitizeArg(str);
        }
        bootCommand(context, null, "--prompt_and_wipe_data", str2, "--locale=" + java.util.Locale.getDefault().toString());
    }

    public static void rebootWipeCache(android.content.Context context) throws java.io.IOException {
        rebootWipeCache(context, context.getPackageName());
    }

    public static void rebootWipeCache(android.content.Context context, java.lang.String str) throws java.io.IOException {
        java.lang.String str2;
        if (android.text.TextUtils.isEmpty(str)) {
            str2 = null;
        } else {
            str2 = "--reason=" + sanitizeArg(str);
        }
        bootCommand(context, "--wipe_cache", str2, "--locale=" + java.util.Locale.getDefault().toLanguageTag());
    }

    @android.annotation.SystemApi
    public static void rebootWipeAb(android.content.Context context, java.io.File file, java.lang.String str) throws java.io.IOException {
        java.lang.String str2;
        if (android.text.TextUtils.isEmpty(str)) {
            str2 = null;
        } else {
            str2 = "--reason=" + sanitizeArg(str);
        }
        bootCommand(context, "--wipe_ab", "--wipe_package=" + file.getCanonicalPath(), str2, "--locale=" + java.util.Locale.getDefault().toLanguageTag());
    }

    private static void bootCommand(android.content.Context context, java.lang.String... strArr) throws java.io.IOException {
        LOG_FILE.delete();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (java.lang.String str : strArr) {
            if (!android.text.TextUtils.isEmpty(str)) {
                sb.append(str);
                sb.append("\n");
            }
        }
        ((android.os.RecoverySystem) context.getSystemService("recovery")).rebootRecoveryWithCommand(sb.toString());
        throw new java.io.IOException("Reboot failed (no permissions?)");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static java.lang.String handleAftermath(android.content.Context context) {
        java.lang.String str;
        boolean exists;
        int i;
        java.lang.String[] list;
        java.lang.String str2 = null;
        try {
            str = android.os.FileUtils.readTextFile(LOG_FILE, -65536, "...\n");
        } catch (java.io.FileNotFoundException e) {
            android.util.Log.i(TAG, "No recovery log file");
            str = null;
            exists = BLOCK_MAP_FILE.exists();
            if (!exists) {
                try {
                    str2 = android.os.FileUtils.readTextFile(UNCRYPT_PACKAGE_FILE, 0, null);
                } catch (java.io.IOException e2) {
                    android.util.Log.e(TAG, "Error reading uncrypt file", e2);
                }
                if (str2 != null) {
                    if (!UNCRYPT_PACKAGE_FILE.delete()) {
                    }
                }
            }
            list = RECOVERY_DIR.list();
            while (list != null) {
                if (!list[i].startsWith(LAST_PREFIX)) {
                    recursiveDelete(new java.io.File(RECOVERY_DIR, list[i]));
                }
            }
            return str;
        } catch (java.io.IOException e3) {
            android.util.Log.e(TAG, "Error reading recovery log", e3);
            str = null;
            exists = BLOCK_MAP_FILE.exists();
            if (!exists) {
            }
            list = RECOVERY_DIR.list();
            while (list != null) {
            }
            return str;
        }
        exists = BLOCK_MAP_FILE.exists();
        if (!exists && UNCRYPT_PACKAGE_FILE.exists()) {
            str2 = android.os.FileUtils.readTextFile(UNCRYPT_PACKAGE_FILE, 0, null);
            if (str2 != null && str2.startsWith("/data")) {
                if (!UNCRYPT_PACKAGE_FILE.delete()) {
                    android.util.Log.i(TAG, "Deleted: " + str2);
                } else {
                    android.util.Log.e(TAG, "Can't delete: " + str2);
                }
            }
        }
        list = RECOVERY_DIR.list();
        for (i = 0; list != null && i < list.length; i++) {
            if (!list[i].startsWith(LAST_PREFIX) && !list[i].equals(LAST_INSTALL_PATH) && ((!exists || !list[i].equals(BLOCK_MAP_FILE.getName())) && (!exists || !list[i].equals(UNCRYPT_PACKAGE_FILE.getName())))) {
                recursiveDelete(new java.io.File(RECOVERY_DIR, list[i]));
            }
        }
        return str;
    }

    private static void recursiveDelete(java.io.File file) {
        if (file.isDirectory()) {
            java.lang.String[] list = file.list();
            for (int i = 0; list != null && i < list.length; i++) {
                recursiveDelete(new java.io.File(file, list[i]));
            }
        }
        if (!file.delete()) {
            android.util.Log.e(TAG, "Can't delete: " + file);
        } else {
            android.util.Log.i(TAG, "Deleted: " + file);
        }
    }

    private boolean uncrypt(java.lang.String str, android.os.IRecoverySystemProgressListener iRecoverySystemProgressListener) {
        try {
            return this.mService.uncrypt(str, iRecoverySystemProgressListener);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    private boolean setupBcb(java.lang.String str) {
        try {
            return this.mService.setupBcb(str);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    private boolean allocateSpaceForUpdate(java.io.File file) throws android.os.RemoteException {
        return this.mService.allocateSpaceForUpdate(file.getAbsolutePath());
    }

    private boolean clearBcb() {
        try {
            return this.mService.clearBcb();
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    private void rebootRecoveryWithCommand(java.lang.String str) {
        try {
            this.mService.rebootRecoveryWithCommand(str);
        } catch (android.os.RemoteException e) {
        }
    }

    private boolean requestLskf(java.lang.String str, android.content.IntentSender intentSender) throws java.io.IOException {
        android.util.Log.i(TAG, java.lang.String.format("<%s> is requesting LSFK", str));
        try {
            boolean requestLskf = this.mService.requestLskf(str, intentSender);
            android.util.Log.i(TAG, java.lang.String.format("LSKF Request isValid = %b", java.lang.Boolean.valueOf(requestLskf)));
            return requestLskf;
        } catch (android.os.RemoteException | java.lang.SecurityException e) {
            throw new java.io.IOException("could not request LSKF capture", e);
        }
    }

    private boolean clearLskf(java.lang.String str) throws java.io.IOException {
        try {
            return this.mService.clearLskf(str);
        } catch (android.os.RemoteException | java.lang.SecurityException e) {
            throw new java.io.IOException("could not clear LSKF", e);
        }
    }

    private boolean isLskfCaptured(java.lang.String str) throws java.io.IOException {
        try {
            return this.mService.isLskfCaptured(str);
        } catch (android.os.RemoteException | java.lang.SecurityException e) {
            throw new java.io.IOException("could not get LSKF capture state", e);
        }
    }

    private int rebootWithLskf(java.lang.String str, java.lang.String str2, boolean z) throws java.io.IOException {
        try {
            return this.mService.rebootWithLskf(str, str2, z);
        } catch (android.os.RemoteException | java.lang.SecurityException e) {
            throw new java.io.IOException("could not reboot for update", e);
        }
    }

    private int rebootWithLskfAssumeSlotSwitch(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        try {
            return this.mService.rebootWithLskfAssumeSlotSwitch(str, str2);
        } catch (android.os.RemoteException | java.lang.RuntimeException e) {
            throw new java.io.IOException("could not reboot for update", e);
        }
    }

    private static java.lang.String sanitizeArg(java.lang.String str) {
        return str.replace((char) 0, '?').replace('\n', '?');
    }

    public RecoverySystem() {
        this.mService = null;
    }

    public RecoverySystem(android.os.IRecoverySystem iRecoverySystem) {
        this.mService = iRecoverySystem;
    }
}
