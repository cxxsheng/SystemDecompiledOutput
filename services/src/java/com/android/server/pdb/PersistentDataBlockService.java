package com.android.server.pdb;

/* loaded from: classes2.dex */
public class PersistentDataBlockService extends com.android.server.SystemService {
    public static final java.lang.String BOOTLOADER_LOCK_STATE = "ro.boot.vbmeta.device_state";
    public static final int DIGEST_SIZE_BYTES = 32;
    private static final java.lang.String FLASH_LOCK_LOCKED = "1";
    private static final java.lang.String FLASH_LOCK_PROP = "ro.boot.flash.locked";
    private static final java.lang.String FLASH_LOCK_UNLOCKED = "0";

    @com.android.internal.annotations.VisibleForTesting
    static final int FRP_CREDENTIAL_RESERVED_SIZE = 1000;
    private static final java.lang.String FRP_SECRET_FILE = "/data/system/frp_secret";

    @com.android.internal.annotations.VisibleForTesting
    static final int FRP_SECRET_SIZE = 32;
    private static final java.lang.String FRP_SECRET_TMP_FILE = "/data/system/frp_secret_tmp";
    private static final java.lang.String GSI_RUNNING_PROP = "ro.gsid.image_running";
    private static final java.lang.String GSI_SANDBOX = "/data/gsi_persistent_data";

    @com.android.internal.annotations.VisibleForTesting
    static final int HEADER_SIZE = 8;
    public static final int INIT_WAIT_TIMEOUT = 10;

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_DATA_BLOCK_SIZE = 102400;

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_FRP_CREDENTIAL_HANDLE_SIZE = 996;

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_TEST_MODE_DATA_SIZE = 9996;
    private static final java.lang.String OEM_UNLOCK_PROP = "sys.oem_unlock_allowed";
    private static final int PARTITION_TYPE_MARKER = 428873843;
    private static final java.lang.String PERSISTENT_DATA_BLOCK_PROP = "ro.frp.pst";

    @com.android.internal.annotations.VisibleForTesting
    static final int TEST_MODE_RESERVED_SIZE = 10000;
    public static final java.lang.String VERIFIED_BOOT_STATE = "ro.boot.verifiedbootstate";
    private int mAllowedUid;
    private long mBlockDeviceSize;
    private final android.content.Context mContext;
    private final java.lang.String mDataBlockFile;
    private boolean mFrpActive;
    private final boolean mFrpEnforced;
    private final java.lang.String mFrpSecretFile;
    private final java.lang.String mFrpSecretTmpFile;
    private final java.util.concurrent.CountDownLatch mInitDoneSignal;
    private com.android.server.pdb.PersistentDataBlockService.InternalService mInternalService;
    private final boolean mIsFileBacked;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsWritable;
    private final java.lang.Object mLock;
    private final android.os.IBinder mService;
    private static final java.lang.String TAG = com.android.server.pdb.PersistentDataBlockService.class.getSimpleName();

    @com.android.internal.annotations.VisibleForTesting
    static final byte[] FRP_SECRET_MAGIC = {-38, -62, -4, -51, -71, 27, 9, -120};

    private native long nativeGetBlockDeviceSize(java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeWipe(java.lang.String str);

    public PersistentDataBlockService(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mInitDoneSignal = new java.util.concurrent.CountDownLatch(1);
        this.mAllowedUid = -1;
        this.mBlockDeviceSize = -1L;
        this.mFrpActive = false;
        this.mIsWritable = true;
        this.mService = new com.android.server.pdb.PersistentDataBlockService.AnonymousClass1();
        this.mInternalService = new com.android.server.pdb.PersistentDataBlockService.InternalService();
        this.mContext = context;
        this.mFrpEnforced = android.security.Flags.frpEnforcement();
        this.mFrpActive = this.mFrpEnforced;
        this.mFrpSecretFile = FRP_SECRET_FILE;
        this.mFrpSecretTmpFile = FRP_SECRET_TMP_FILE;
        if (android.os.SystemProperties.getBoolean(GSI_RUNNING_PROP, false)) {
            this.mIsFileBacked = true;
            this.mDataBlockFile = GSI_SANDBOX;
        } else {
            this.mIsFileBacked = false;
            this.mDataBlockFile = android.os.SystemProperties.get(PERSISTENT_DATA_BLOCK_PROP);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    PersistentDataBlockService(android.content.Context context, boolean z, java.lang.String str, long j, boolean z2, java.lang.String str2, java.lang.String str3) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mInitDoneSignal = new java.util.concurrent.CountDownLatch(1);
        this.mAllowedUid = -1;
        this.mBlockDeviceSize = -1L;
        this.mFrpActive = false;
        this.mIsWritable = true;
        this.mService = new com.android.server.pdb.PersistentDataBlockService.AnonymousClass1();
        this.mInternalService = new com.android.server.pdb.PersistentDataBlockService.InternalService();
        this.mContext = context;
        this.mIsFileBacked = z;
        this.mDataBlockFile = str;
        this.mBlockDeviceSize = j;
        this.mFrpEnforced = z2;
        this.mFrpActive = this.mFrpEnforced;
        this.mFrpSecretFile = str2;
        this.mFrpSecretTmpFile = str3;
    }

    private int getAllowedUid() {
        int mainUserId = ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getMainUserId();
        if (mainUserId < 0) {
            mainUserId = 0;
        }
        java.lang.String string = this.mContext.getResources().getString(android.R.string.config_notificationAccessConfirmationActivity);
        if (!android.text.TextUtils.isEmpty(string)) {
            try {
                return this.mContext.getPackageManager().getPackageUidAsUser(string, 1048576, mainUserId);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.e(TAG, "not able to find package " + string, e);
            }
        }
        return -1;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.pdb.PersistentDataBlockService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pdb.PersistentDataBlockService.this.lambda$onStart$0();
            }
        }, TAG + ".onStart");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$0() {
        enforceChecksumValidity();
        if (this.mFrpEnforced) {
            automaticallyDeactivateFrpIfPossible();
            setOemUnlockEnabledProperty(doGetOemUnlockEnabled());
            android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "secure_frp_mode", this.mFrpActive ? 1 : 0);
        } else {
            formatIfOemUnlockEnabled();
        }
        publishBinderService("persistent_data_block", this.mService);
        signalInitDone();
    }

    @com.android.internal.annotations.VisibleForTesting
    void signalInitDone() {
        this.mInitDoneSignal.countDown();
    }

    private void setOemUnlockEnabledProperty(boolean z) {
        setProperty(OEM_UNLOCK_PROP, z ? FLASH_LOCK_LOCKED : FLASH_LOCK_UNLOCKED);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            waitForInitDoneSignal();
            this.mAllowedUid = getAllowedUid();
            com.android.server.LocalServices.addService(com.android.server.pdb.PersistentDataBlockManagerInternal.class, this.mInternalService);
        }
        super.onBootPhase(i);
    }

    private void waitForInitDoneSignal() {
        try {
            if (!this.mInitDoneSignal.await(10L, java.util.concurrent.TimeUnit.SECONDS)) {
                throw new java.lang.IllegalStateException("Service " + TAG + " init timeout");
            }
        } catch (java.lang.InterruptedException e) {
            java.lang.Thread.currentThread().interrupt();
            throw new java.lang.IllegalStateException("Service " + TAG + " init interrupted", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAllowedUid(int i) {
        this.mAllowedUid = i;
    }

    private void formatIfOemUnlockEnabled() {
        boolean doGetOemUnlockEnabled = doGetOemUnlockEnabled();
        if (doGetOemUnlockEnabled) {
            synchronized (this.mLock) {
                formatPartitionLocked(true);
            }
        }
        setOemUnlockEnabledProperty(doGetOemUnlockEnabled);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceOemUnlockReadPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.READ_OEM_UNLOCK_STATE") == -1 && this.mContext.checkCallingOrSelfPermission("android.permission.OEM_UNLOCK_STATE") == -1) {
            throw new java.lang.SecurityException("Can't access OEM unlock state. Requires READ_OEM_UNLOCK_STATE or OEM_UNLOCK_STATE permission.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceOemUnlockWritePermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.OEM_UNLOCK_STATE", "Can't modify OEM unlock state");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceConfigureFrpPermission() {
        if (this.mFrpEnforced && this.mContext.checkCallingOrSelfPermission("android.permission.CONFIGURE_FACTORY_RESET_PROTECTION") == -1) {
            throw new java.lang.SecurityException("Can't configure Factory Reset Protection. Requires CONFIGURE_FACTORY_RESET_PROTECTION");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceUid(int i) {
        if (i != this.mAllowedUid && i != 0) {
            throw new java.lang.SecurityException("uid " + i + " not allowed to access PDB");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceIsAdmin() {
        if (!android.os.UserManager.get(this.mContext).isUserAdmin(android.os.UserHandle.getCallingUserId())) {
            throw new java.lang.SecurityException("Only the Admin user is allowed to change OEM unlock state");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceUserRestriction(java.lang.String str) {
        if (android.os.UserManager.get(this.mContext).hasUserRestriction(str)) {
            throw new java.lang.SecurityException("OEM unlock is disallowed by user restriction: " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTotalDataSizeLocked(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        dataInputStream.skipBytes(32);
        if (dataInputStream.readInt() == PARTITION_TYPE_MARKER) {
            return dataInputStream.readInt();
        }
        return 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getBlockDeviceSize() {
        synchronized (this.mLock) {
            try {
                if (this.mBlockDeviceSize == -1) {
                    if (this.mIsFileBacked) {
                        this.mBlockDeviceSize = 102400L;
                    } else {
                        this.mBlockDeviceSize = nativeGetBlockDeviceSize(this.mDataBlockFile);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mBlockDeviceSize;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getMaximumFrpDataSize() {
        return (int) (((getTestHarnessModeDataOffset() - 32) - 8) - (this.mFrpEnforced ? FRP_SECRET_MAGIC.length + 32 : 0L));
    }

    @com.android.internal.annotations.VisibleForTesting
    long getFrpCredentialDataOffset() {
        return getOemUnlockDataOffset() - 1000;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getFrpSecretMagicOffset() {
        return getFrpSecretDataOffset() - FRP_SECRET_MAGIC.length;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getFrpSecretDataOffset() {
        return getTestHarnessModeDataOffset() - 32;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getTestHarnessModeDataOffset() {
        return getFrpCredentialDataOffset() - com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getOemUnlockDataOffset() {
        return getBlockDeviceSize() - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean enforceChecksumValidity() {
        byte[] bArr = new byte[32];
        synchronized (this.mLock) {
            try {
                byte[] computeDigestLocked = computeDigestLocked(bArr);
                if (computeDigestLocked == null || !java.util.Arrays.equals(bArr, computeDigestLocked)) {
                    android.util.Slog.i(TAG, "Formatting FRP partition...");
                    formatPartitionLocked(false);
                    return false;
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.nio.channels.FileChannel getBlockOutputChannel() throws java.io.IOException {
        enforceFactoryResetProtectionInactive();
        return getBlockOutputChannelIgnoringFrp();
    }

    private java.nio.channels.FileChannel getBlockOutputChannelIgnoringFrp() throws java.io.FileNotFoundException {
        return new java.io.RandomAccessFile(this.mDataBlockFile, "rw").getChannel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean computeAndWriteDigestLocked() {
        byte[] computeDigestLocked = computeDigestLocked(null);
        if (computeDigestLocked == null) {
            return false;
        }
        try {
            java.nio.channels.FileChannel blockOutputChannel = getBlockOutputChannel();
            try {
                java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(32);
                allocate.put(computeDigestLocked);
                allocate.flip();
                blockOutputChannel.write(allocate);
                blockOutputChannel.force(true);
                blockOutputChannel.close();
                return true;
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "failed to write block checksum", e);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003b A[Catch: all -> 0x0025, IOException -> 0x0027, LOOP:0: B:11:0x0034->B:13:0x003b, LOOP_END, TRY_LEAVE, TryCatch #0 {IOException -> 0x0027, blocks: (B:30:0x001e, B:32:0x0021, B:10:0x002c, B:11:0x0034, B:13:0x003b, B:9:0x0029), top: B:29:0x001e, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private byte[] computeDigestLocked(byte[] bArr) {
        byte[] bArr2;
        int read;
        try {
            java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.FileInputStream(new java.io.File(this.mDataBlockFile)));
            try {
                java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
                if (bArr != null) {
                    try {
                        try {
                            if (bArr.length == 32) {
                                dataInputStream.read(bArr);
                                bArr2 = new byte[1024];
                                messageDigest.update(bArr2, 0, 32);
                                while (true) {
                                    read = dataInputStream.read(bArr2);
                                    if (read != -1) {
                                        libcore.io.IoUtils.closeQuietly(dataInputStream);
                                        return messageDigest.digest();
                                    }
                                    messageDigest.update(bArr2, 0, read);
                                }
                            }
                        } catch (java.io.IOException e) {
                            android.util.Slog.e(TAG, "failed to read partition", e);
                            libcore.io.IoUtils.closeQuietly(dataInputStream);
                            return null;
                        }
                    } catch (java.lang.Throwable th) {
                        libcore.io.IoUtils.closeQuietly(dataInputStream);
                        throw th;
                    }
                }
                dataInputStream.skipBytes(32);
                bArr2 = new byte[1024];
                messageDigest.update(bArr2, 0, 32);
                while (true) {
                    read = dataInputStream.read(bArr2);
                    if (read != -1) {
                    }
                    messageDigest.update(bArr2, 0, read);
                }
            } catch (java.security.NoSuchAlgorithmException e2) {
                android.util.Slog.e(TAG, "SHA-256 not supported?", e2);
                libcore.io.IoUtils.closeQuietly(dataInputStream);
                return null;
            }
        } catch (java.io.FileNotFoundException e3) {
            android.util.Slog.e(TAG, "partition not available?", e3);
            return null;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void formatPartitionLocked(boolean z) {
        try {
            java.nio.channels.FileChannel blockOutputChannelIgnoringFrp = getBlockOutputChannelIgnoringFrp();
            try {
                java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(40);
                allocate.put(new byte[32]);
                allocate.putInt(PARTITION_TYPE_MARKER);
                allocate.putInt(0);
                allocate.flip();
                blockOutputChannelIgnoringFrp.write(allocate);
                blockOutputChannelIgnoringFrp.force(true);
                int blockDeviceSize = ((int) getBlockDeviceSize()) - 40;
                blockOutputChannelIgnoringFrp.write(this.mFrpEnforced ? java.nio.ByteBuffer.allocate(((((blockDeviceSize + com.android.server.am.ProcessList.INVALID_ADJ) - FRP_SECRET_MAGIC.length) - 32) - 1000) - 1) : java.nio.ByteBuffer.allocate(((blockDeviceSize + com.android.server.am.ProcessList.INVALID_ADJ) - 1000) - 1));
                blockOutputChannelIgnoringFrp.force(true);
                if (this.mFrpEnforced) {
                    android.util.Slog.i(TAG, "Writing FRP secret magic");
                    blockOutputChannelIgnoringFrp.write(java.nio.ByteBuffer.wrap(FRP_SECRET_MAGIC));
                    android.util.Slog.i(TAG, "Writing default FRP secret");
                    blockOutputChannelIgnoringFrp.write(java.nio.ByteBuffer.allocate(32));
                    blockOutputChannelIgnoringFrp.force(true);
                    this.mFrpActive = false;
                }
                blockOutputChannelIgnoringFrp.position(blockOutputChannelIgnoringFrp.position() + com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
                blockOutputChannelIgnoringFrp.write(java.nio.ByteBuffer.allocate(1000));
                blockOutputChannelIgnoringFrp.force(true);
                java.nio.ByteBuffer allocate2 = java.nio.ByteBuffer.allocate(1000);
                allocate2.put((byte) 0);
                allocate2.flip();
                blockOutputChannelIgnoringFrp.write(allocate2);
                blockOutputChannelIgnoringFrp.force(true);
                blockOutputChannelIgnoringFrp.close();
                doSetOemUnlockEnabledLocked(z);
                computeAndWriteDigestLocked();
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "failed to format block", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean automaticallyDeactivateFrpIfPossible() {
        synchronized (this.mLock) {
            try {
                if (deactivateFrpWithFileSecret(this.mFrpSecretFile)) {
                    return true;
                }
                android.util.Slog.w(TAG, "Failed to deactivate with primary secret file, trying backup.");
                if (deactivateFrpWithFileSecret(this.mFrpSecretTmpFile)) {
                    moveFrpTempFileToPrimary();
                    return true;
                }
                android.util.Slog.w(TAG, "Failed to deactivate with backup secret file, trying default secret.");
                if (deactivateFrp(new byte[32])) {
                    return true;
                }
                if (isUpgradingFromPreVRelease()) {
                    android.util.Slog.w(TAG, "Upgrading from Android 14 or lower, defaulting FRP secret");
                    writeFrpMagicAndDefaultSecret();
                    this.mFrpActive = false;
                    return true;
                }
                android.util.Slog.e(TAG, "Did not find valid FRP secret, FRP remains active.");
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean deactivateFrpWithFileSecret(java.lang.String str) {
        try {
            return deactivateFrp(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(str, new java.lang.String[0])));
        } catch (java.io.IOException e) {
            android.util.Slog.i(TAG, "Failed to read FRP secret file: " + str + " " + e.getClass().getSimpleName());
            return false;
        }
    }

    private void moveFrpTempFileToPrimary() {
        try {
            java.nio.file.Files.move(java.nio.file.Paths.get(this.mFrpSecretTmpFile, new java.lang.String[0]), java.nio.file.Paths.get(this.mFrpSecretFile, new java.lang.String[0]), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error moving FRP backup file to primary (ignored)", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isFrpActive() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mFrpActive;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateFrpSecret(byte[] bArr) {
        try {
            java.nio.file.Files.write(java.nio.file.Paths.get(this.mFrpSecretTmpFile, new java.lang.String[0]), bArr, java.nio.file.StandardOpenOption.WRITE, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING, java.nio.file.StandardOpenOption.SYNC);
            if (!this.mInternalService.writeDataBuffer(getFrpSecretDataOffset(), java.nio.ByteBuffer.wrap(bArr))) {
                return false;
            }
            moveFrpTempFileToPrimary();
            return true;
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write FRP secret file", e);
            return false;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void activateFrp() {
        synchronized (this.mLock) {
            this.mFrpActive = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasFrpSecretMagic() {
        byte[] readDataBlock = readDataBlock(getFrpSecretMagicOffset(), FRP_SECRET_MAGIC.length);
        if (readDataBlock == null) {
            android.util.Slog.e(TAG, "Failed to read FRP magic region.");
            return false;
        }
        return java.util.Arrays.equals(readDataBlock, FRP_SECRET_MAGIC);
    }

    private byte[] getFrpSecret() {
        return readDataBlock(getFrpSecretDataOffset(), 32);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean deactivateFrp(byte[] bArr) {
        if (bArr == null || bArr.length != 32) {
            android.util.Slog.w(TAG, "Attempted to deactivate FRP with a null or incorrectly-sized secret");
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (!hasFrpSecretMagic()) {
                    android.util.Slog.i(TAG, "No FRP secret magic, system must have been upgraded.");
                    writeFrpMagicAndDefaultSecret();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        byte[] frpSecret = getFrpSecret();
        if (frpSecret == null || frpSecret.length != 32) {
            android.util.Slog.e(TAG, "Failed to read FRP secret from persistent data partition");
            return false;
        }
        if (java.security.MessageDigest.isEqual(bArr, frpSecret)) {
            this.mFrpActive = false;
            android.util.Slog.i(TAG, "FRP secret matched, FRP deactivated.");
            return true;
        }
        android.util.Slog.e(TAG, "FRP deactivation failed with secret " + java.util.HexFormat.of().formatHex(bArr));
        return false;
    }

    private void writeFrpMagicAndDefaultSecret() {
        try {
            java.nio.channels.FileChannel blockOutputChannelIgnoringFrp = getBlockOutputChannelIgnoringFrp();
            try {
                synchronized (this.mLock) {
                    android.util.Slog.i(TAG, "Writing default FRP secret");
                    blockOutputChannelIgnoringFrp.position(getFrpSecretDataOffset());
                    blockOutputChannelIgnoringFrp.write(java.nio.ByteBuffer.allocate(32));
                    blockOutputChannelIgnoringFrp.force(true);
                    android.util.Slog.i(TAG, "Writing FRP secret magic");
                    blockOutputChannelIgnoringFrp.position(getFrpSecretMagicOffset());
                    blockOutputChannelIgnoringFrp.write(java.nio.ByteBuffer.wrap(FRP_SECRET_MAGIC));
                    blockOutputChannelIgnoringFrp.force(true);
                    this.mFrpActive = false;
                }
                blockOutputChannelIgnoringFrp.close();
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write FRP magic and default secret", e);
        }
        computeAndWriteDigestLocked();
    }

    @com.android.internal.annotations.VisibleForTesting
    byte[] readDataBlock(long j, int i) {
        byte[] bArr;
        try {
            java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.FileInputStream(new java.io.File(this.mDataBlockFile)));
            try {
                synchronized (this.mLock) {
                    dataInputStream.skip(j);
                    bArr = new byte[i];
                    dataInputStream.readFully(bArr);
                }
                dataInputStream.close();
                return bArr;
            } finally {
            }
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("persistent partition not readable", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSetOemUnlockEnabledLocked(boolean z) {
        try {
            java.nio.channels.FileChannel blockOutputChannel = getBlockOutputChannel();
            try {
                blockOutputChannel.position(getBlockDeviceSize() - 1);
                java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(1);
                allocate.put(z ? (byte) 1 : (byte) 0);
                allocate.flip();
                blockOutputChannel.write(allocate);
                blockOutputChannel.force(true);
                blockOutputChannel.close();
            } catch (java.lang.Throwable th) {
                if (blockOutputChannel != null) {
                    try {
                        blockOutputChannel.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "unable to access persistent partition", e);
        } finally {
            setOemUnlockEnabledProperty(z);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setProperty(java.lang.String str, java.lang.String str2) {
        android.os.SystemProperties.set(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean doGetOemUnlockEnabled() {
        boolean z;
        try {
            java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.FileInputStream(new java.io.File(this.mDataBlockFile)));
            try {
                synchronized (this.mLock) {
                    dataInputStream.skip(getBlockDeviceSize() - 1);
                    z = dataInputStream.readByte() != 0;
                }
                return z;
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "unable to access persistent partition", e);
                return false;
            } finally {
                libcore.io.IoUtils.closeQuietly(dataInputStream);
            }
        } catch (java.io.FileNotFoundException e2) {
            android.util.Slog.e(TAG, "partition not available");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long doGetMaximumDataBlockSize() {
        long blockDeviceSize = (((((getBlockDeviceSize() - 8) - 32) - com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY) - (this.mFrpEnforced ? FRP_SECRET_MAGIC.length + 32 : 0L)) - 1000) - 1;
        if (blockDeviceSize <= 102400) {
            return blockDeviceSize;
        }
        return 102400L;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.service.persistentdata.IPersistentDataBlockService getInterfaceForTesting() {
        return android.service.persistentdata.IPersistentDataBlockService.Stub.asInterface(this.mService);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.pdb.PersistentDataBlockManagerInternal getInternalInterfaceForTesting() {
        return this.mInternalService;
    }

    /* renamed from: com.android.server.pdb.PersistentDataBlockService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.persistentdata.IPersistentDataBlockService.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int printFrpStatus(java.io.PrintWriter printWriter, boolean z) {
            com.android.server.pdb.PersistentDataBlockService.this.enforceUid(android.os.Binder.getCallingUid());
            printWriter.println("FRP state");
            printWriter.println("=========");
            printWriter.println("Enforcement enabled: " + com.android.server.pdb.PersistentDataBlockService.this.mFrpEnforced);
            printWriter.println("FRP state: " + com.android.server.pdb.PersistentDataBlockService.this.mFrpActive);
            printFrpDataFilesContents(printWriter, z);
            printFrpSecret(printWriter, z);
            printWriter.println("OEM unlock state: " + getOemUnlockEnabled());
            printWriter.println("Bootloader lock state: " + getFlashLockState());
            printWriter.println("Verified boot state: " + getVerifiedBootState());
            printWriter.println("Has FRP credential handle: " + hasFrpCredentialHandle());
            printWriter.println("FRP challenge block size: " + getDataBlockSize());
            return 1;
        }

        private void printFrpSecret(java.io.PrintWriter printWriter, boolean z) {
            if (com.android.server.pdb.PersistentDataBlockService.this.hasFrpSecretMagic()) {
                if (z) {
                    printWriter.println("FRP secret in PDB: " + java.util.HexFormat.of().formatHex(com.android.server.pdb.PersistentDataBlockService.this.readDataBlock(com.android.server.pdb.PersistentDataBlockService.this.getFrpSecretDataOffset(), 32)));
                    return;
                }
                printWriter.println("FRP secret present but omitted.");
                return;
            }
            printWriter.println("FRP magic not found");
        }

        private void printFrpDataFilesContents(java.io.PrintWriter printWriter, boolean z) {
            printFrpDataFileContents(printWriter, com.android.server.pdb.PersistentDataBlockService.this.mFrpSecretFile, z);
            printFrpDataFileContents(printWriter, com.android.server.pdb.PersistentDataBlockService.this.mFrpSecretTmpFile, z);
        }

        private void printFrpDataFileContents(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
            if (java.nio.file.Files.exists(java.nio.file.Paths.get(str, new java.lang.String[0]), new java.nio.file.LinkOption[0])) {
                if (z) {
                    try {
                        printWriter.println("FRP secret in " + str + ": " + java.util.HexFormat.of().formatHex(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(str, new java.lang.String[0]))));
                        return;
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "Failed to read " + str, e);
                        return;
                    }
                }
                printWriter.println("FRP secret file " + str + " exists, contents omitted.");
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            if (!com.android.server.pdb.PersistentDataBlockService.this.mFrpEnforced) {
                super.onShellCommand(fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            } else {
                new android.os.ShellCommand() { // from class: com.android.server.pdb.PersistentDataBlockService.1.1
                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    public int onCommand(java.lang.String str) {
                        char c;
                        if (str == null) {
                            return handleDefaultCommands(str);
                        }
                        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                        switch (str.hashCode()) {
                            case -1996763020:
                                if (str.equals("deactivate")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1655974669:
                                if (str.equals("activate")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1183761107:
                                if (str.equals("set_secret")) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -892481550:
                                if (str.equals("status")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 2042639620:
                                if (str.equals("auto_deactivate")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                return com.android.server.pdb.PersistentDataBlockService.AnonymousClass1.this.printFrpStatus(outPrintWriter, !com.android.server.pdb.PersistentDataBlockService.this.mFrpActive);
                            case 1:
                                com.android.server.pdb.PersistentDataBlockService.this.activateFrp();
                                return com.android.server.pdb.PersistentDataBlockService.AnonymousClass1.this.printFrpStatus(outPrintWriter, !com.android.server.pdb.PersistentDataBlockService.this.mFrpActive);
                            case 2:
                                byte[] hashSecretString = hashSecretString(getNextArg());
                                outPrintWriter.println("Attempting to deactivate with: " + java.util.HexFormat.of().formatHex(hashSecretString));
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                sb.append("Deactivation ");
                                sb.append(com.android.server.pdb.PersistentDataBlockService.this.deactivateFrp(hashSecretString) ? "succeeded" : "failed");
                                outPrintWriter.println(sb.toString());
                                return com.android.server.pdb.PersistentDataBlockService.AnonymousClass1.this.printFrpStatus(outPrintWriter, !com.android.server.pdb.PersistentDataBlockService.this.mFrpActive);
                            case 3:
                                boolean automaticallyDeactivateFrpIfPossible = com.android.server.pdb.PersistentDataBlockService.this.automaticallyDeactivateFrpIfPossible();
                                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                                sb2.append("Automatic deactivation ");
                                sb2.append(automaticallyDeactivateFrpIfPossible ? "succeeded" : "failed");
                                outPrintWriter.println(sb2.toString());
                                return com.android.server.pdb.PersistentDataBlockService.AnonymousClass1.this.printFrpStatus(outPrintWriter, !com.android.server.pdb.PersistentDataBlockService.this.mFrpActive);
                            case 4:
                                byte[] bArr = new byte[32];
                                java.lang.String nextArg = getNextArg();
                                if (!nextArg.equals("default")) {
                                    bArr = hashSecretString(nextArg);
                                }
                                outPrintWriter.println("Setting FRP secret to: " + java.util.HexFormat.of().formatHex(bArr) + " length: " + bArr.length);
                                com.android.server.pdb.PersistentDataBlockService.AnonymousClass1.this.setFactoryResetProtectionSecret(bArr);
                                return com.android.server.pdb.PersistentDataBlockService.AnonymousClass1.this.printFrpStatus(outPrintWriter, !com.android.server.pdb.PersistentDataBlockService.this.mFrpActive);
                            default:
                                return handleDefaultCommands(str);
                        }
                    }

                    public void onHelp() {
                        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                        outPrintWriter.println("Commands");
                        outPrintWriter.println("status: Print the FRP state and associated information.");
                        outPrintWriter.println("activate:  Put FRP into \"active\" mode.");
                        outPrintWriter.println("deactivate <secret>:  Deactivate with a hash of 'secret'.");
                        outPrintWriter.println("auto_deactivate: Deactivate with the stored secret or the default");
                        outPrintWriter.println("set_secret <secret>:  Set the stored secret to a hash of `secret`");
                    }

                    private static byte[] hashSecretString(java.lang.String str) {
                        try {
                            return java.security.MessageDigest.getInstance("SHA-256").digest(str.getBytes());
                        } catch (java.security.NoSuchAlgorithmException e) {
                            android.util.Slog.e("ShellCommand", "Can't happen", e);
                            return new byte[32];
                        }
                    }
                }.exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }
        }

        public int write(byte[] bArr) throws android.os.RemoteException {
            com.android.server.pdb.PersistentDataBlockService.this.enforceUid(android.os.Binder.getCallingUid());
            long doGetMaximumDataBlockSize = com.android.server.pdb.PersistentDataBlockService.this.doGetMaximumDataBlockSize();
            if (bArr.length > doGetMaximumDataBlockSize) {
                return (int) (-doGetMaximumDataBlockSize);
            }
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(bArr.length + 8 + 32);
            allocate.put(new byte[32]);
            allocate.putInt(com.android.server.pdb.PersistentDataBlockService.PARTITION_TYPE_MARKER);
            allocate.putInt(bArr.length);
            allocate.put(bArr);
            allocate.flip();
            synchronized (com.android.server.pdb.PersistentDataBlockService.this.mLock) {
                if (!com.android.server.pdb.PersistentDataBlockService.this.mIsWritable) {
                    return -1;
                }
                try {
                    java.nio.channels.FileChannel blockOutputChannel = com.android.server.pdb.PersistentDataBlockService.this.getBlockOutputChannel();
                    try {
                        blockOutputChannel.write(allocate);
                        blockOutputChannel.force(true);
                        blockOutputChannel.close();
                        if (!com.android.server.pdb.PersistentDataBlockService.this.computeAndWriteDigestLocked()) {
                            return -1;
                        }
                        return bArr.length;
                    } catch (java.lang.Throwable th) {
                        if (blockOutputChannel != null) {
                            try {
                                blockOutputChannel.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (java.io.IOException e) {
                    android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "failed writing to the persistent data block", e);
                    return -1;
                }
            }
        }

        public byte[] read() {
            java.io.DataInputStream dataInputStream;
            com.android.server.pdb.PersistentDataBlockService.this.enforceUid(android.os.Binder.getCallingUid());
            if (!com.android.server.pdb.PersistentDataBlockService.this.enforceChecksumValidity()) {
                return new byte[0];
            }
            try {
                try {
                    dataInputStream = new java.io.DataInputStream(new java.io.FileInputStream(new java.io.File(com.android.server.pdb.PersistentDataBlockService.this.mDataBlockFile)));
                    try {
                        synchronized (com.android.server.pdb.PersistentDataBlockService.this.mLock) {
                            int totalDataSizeLocked = com.android.server.pdb.PersistentDataBlockService.this.getTotalDataSizeLocked(dataInputStream);
                            if (totalDataSizeLocked == 0) {
                                return new byte[0];
                            }
                            byte[] bArr = new byte[totalDataSizeLocked];
                            int read = dataInputStream.read(bArr, 0, totalDataSizeLocked);
                            if (read >= totalDataSizeLocked) {
                                try {
                                    dataInputStream.close();
                                } catch (java.io.IOException e) {
                                    android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "failed to close OutputStream");
                                }
                                return bArr;
                            }
                            android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "failed to read entire data block. bytes read: " + read + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + totalDataSizeLocked);
                            try {
                                dataInputStream.close();
                            } catch (java.io.IOException e2) {
                                android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "failed to close OutputStream");
                            }
                            return null;
                        }
                    } catch (java.io.IOException e3) {
                        android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "failed to read data", e3);
                        try {
                            dataInputStream.close();
                        } catch (java.io.IOException e4) {
                            android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "failed to close OutputStream");
                        }
                        return null;
                    }
                } finally {
                    try {
                        dataInputStream.close();
                    } catch (java.io.IOException e5) {
                        android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "failed to close OutputStream");
                    }
                }
            } catch (java.io.FileNotFoundException e6) {
                android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "partition not available?", e6);
                return null;
            }
        }

        public void wipe() {
            int i;
            com.android.server.pdb.PersistentDataBlockService.this.enforceFactoryResetProtectionInactive();
            com.android.server.pdb.PersistentDataBlockService.this.enforceOemUnlockWritePermission();
            synchronized (com.android.server.pdb.PersistentDataBlockService.this.mLock) {
                if (com.android.server.pdb.PersistentDataBlockService.this.mIsFileBacked) {
                    try {
                        java.nio.file.Files.write(java.nio.file.Paths.get(com.android.server.pdb.PersistentDataBlockService.this.mDataBlockFile, new java.lang.String[0]), new byte[com.android.server.pdb.PersistentDataBlockService.MAX_DATA_BLOCK_SIZE], java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
                        i = 0;
                    } catch (java.io.IOException e) {
                        i = -1;
                    }
                } else {
                    i = com.android.server.pdb.PersistentDataBlockService.this.nativeWipe(com.android.server.pdb.PersistentDataBlockService.this.mDataBlockFile);
                }
                if (i < 0) {
                    android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "failed to wipe persistent partition");
                } else {
                    com.android.server.pdb.PersistentDataBlockService.this.mIsWritable = false;
                    android.util.Slog.i(com.android.server.pdb.PersistentDataBlockService.TAG, "persistent partition now wiped and unwritable");
                }
            }
        }

        public void setOemUnlockEnabled(boolean z) throws java.lang.SecurityException {
            if (android.app.ActivityManager.isUserAMonkey()) {
                return;
            }
            com.android.server.pdb.PersistentDataBlockService.this.enforceOemUnlockWritePermission();
            com.android.server.pdb.PersistentDataBlockService.this.enforceIsAdmin();
            if (z) {
                com.android.server.pdb.PersistentDataBlockService.this.enforceUserRestriction("no_oem_unlock");
                com.android.server.pdb.PersistentDataBlockService.this.enforceUserRestriction("no_factory_reset");
            }
            synchronized (com.android.server.pdb.PersistentDataBlockService.this.mLock) {
                com.android.server.pdb.PersistentDataBlockService.this.doSetOemUnlockEnabledLocked(z);
                com.android.server.pdb.PersistentDataBlockService.this.computeAndWriteDigestLocked();
            }
        }

        public boolean getOemUnlockEnabled() {
            com.android.server.pdb.PersistentDataBlockService.this.enforceOemUnlockReadPermission();
            return com.android.server.pdb.PersistentDataBlockService.this.doGetOemUnlockEnabled();
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int getFlashLockState() {
            boolean z;
            com.android.server.pdb.PersistentDataBlockService.this.enforceOemUnlockReadPermission();
            java.lang.String str = android.os.SystemProperties.get(com.android.server.pdb.PersistentDataBlockService.FLASH_LOCK_PROP);
            switch (str.hashCode()) {
                case 48:
                    if (str.equals(com.android.server.pdb.PersistentDataBlockService.FLASH_LOCK_UNLOCKED)) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 49:
                    if (str.equals(com.android.server.pdb.PersistentDataBlockService.FLASH_LOCK_LOCKED)) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    return 1;
                case true:
                    return 0;
                default:
                    return -1;
            }
        }

        private static java.lang.String getVerifiedBootState() {
            return android.os.SystemProperties.get(com.android.server.pdb.PersistentDataBlockService.VERIFIED_BOOT_STATE);
        }

        public int getDataBlockSize() {
            int totalDataSizeLocked;
            enforcePersistentDataBlockAccess();
            try {
                java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.FileInputStream(new java.io.File(com.android.server.pdb.PersistentDataBlockService.this.mDataBlockFile)));
                try {
                    synchronized (com.android.server.pdb.PersistentDataBlockService.this.mLock) {
                        totalDataSizeLocked = com.android.server.pdb.PersistentDataBlockService.this.getTotalDataSizeLocked(dataInputStream);
                    }
                    return totalDataSizeLocked;
                } catch (java.io.IOException e) {
                    android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "error reading data block size");
                    return 0;
                } finally {
                    libcore.io.IoUtils.closeQuietly(dataInputStream);
                }
            } catch (java.io.FileNotFoundException e2) {
                android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "partition not available");
                return 0;
            }
        }

        private void enforcePersistentDataBlockAccess() {
            if (com.android.server.pdb.PersistentDataBlockService.this.mContext.checkCallingPermission("android.permission.ACCESS_PDB_STATE") != 0) {
                com.android.server.pdb.PersistentDataBlockService.this.enforceUid(android.os.Binder.getCallingUid());
            }
        }

        private void enforceConfigureFrpPermissionOrPersistentDataBlockAccess() {
            if (!com.android.server.pdb.PersistentDataBlockService.this.mFrpEnforced) {
                enforcePersistentDataBlockAccess();
            } else if (com.android.server.pdb.PersistentDataBlockService.this.mContext.checkCallingOrSelfPermission("android.permission.CONFIGURE_FACTORY_RESET_PROTECTION") == -1) {
                enforcePersistentDataBlockAccess();
            }
        }

        public long getMaximumDataBlockSize() {
            com.android.server.pdb.PersistentDataBlockService.this.enforceUid(android.os.Binder.getCallingUid());
            return com.android.server.pdb.PersistentDataBlockService.this.doGetMaximumDataBlockSize();
        }

        public boolean hasFrpCredentialHandle() {
            enforceConfigureFrpPermissionOrPersistentDataBlockAccess();
            try {
                return com.android.server.pdb.PersistentDataBlockService.this.mInternalService.getFrpCredentialHandle() != null;
            } catch (java.lang.IllegalStateException e) {
                android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "error reading frp handle", e);
                throw new java.lang.UnsupportedOperationException("cannot read frp credential");
            }
        }

        public java.lang.String getPersistentDataPackageName() {
            enforcePersistentDataBlockAccess();
            return com.android.server.pdb.PersistentDataBlockService.this.mContext.getString(android.R.string.config_notificationAccessConfirmationActivity);
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.pdb.PersistentDataBlockService.this.mContext, com.android.server.pdb.PersistentDataBlockService.TAG, printWriter)) {
                printWriter.println("mDataBlockFile: " + com.android.server.pdb.PersistentDataBlockService.this.mDataBlockFile);
                printWriter.println("mIsFileBacked: " + com.android.server.pdb.PersistentDataBlockService.this.mIsFileBacked);
                printWriter.println("mInitDoneSignal: " + com.android.server.pdb.PersistentDataBlockService.this.mInitDoneSignal);
                printWriter.println("mAllowedUid: " + com.android.server.pdb.PersistentDataBlockService.this.mAllowedUid);
                printWriter.println("mBlockDeviceSize: " + com.android.server.pdb.PersistentDataBlockService.this.mBlockDeviceSize);
                synchronized (com.android.server.pdb.PersistentDataBlockService.this.mLock) {
                    printWriter.println("mIsWritable: " + com.android.server.pdb.PersistentDataBlockService.this.mIsWritable);
                }
                printFrpStatus(printWriter, false);
            }
        }

        public boolean isFactoryResetProtectionActive() {
            return com.android.server.pdb.PersistentDataBlockService.this.isFrpActive();
        }

        public boolean deactivateFactoryResetProtection(byte[] bArr) {
            com.android.server.pdb.PersistentDataBlockService.this.enforceConfigureFrpPermission();
            return com.android.server.pdb.PersistentDataBlockService.this.deactivateFrp(bArr);
        }

        public boolean setFactoryResetProtectionSecret(byte[] bArr) {
            com.android.server.pdb.PersistentDataBlockService.this.enforceConfigureFrpPermission();
            com.android.server.pdb.PersistentDataBlockService.this.enforceUid(android.os.Binder.getCallingUid());
            if (bArr == null || bArr.length != 32) {
                throw new java.lang.IllegalArgumentException("Invalid FRP secret: " + java.util.HexFormat.of().formatHex(bArr));
            }
            com.android.server.pdb.PersistentDataBlockService.this.enforceFactoryResetProtectionInactive();
            return com.android.server.pdb.PersistentDataBlockService.this.updateFrpSecret(bArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceFactoryResetProtectionInactive() {
        if (this.mFrpEnforced && isFrpActive()) {
            android.util.Slog.w(TAG, "Attempt to update PDB was blocked because FRP is active.");
            throw new java.lang.SecurityException("FRP is active");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isUpgradingFromPreVRelease() {
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        if (packageManagerInternal == null) {
            android.util.Slog.e(TAG, "Unable to retrieve PackageManagerInternal");
            return false;
        }
        return packageManagerInternal.isUpgradingFromLowerThan(10000);
    }

    private class InternalService implements com.android.server.pdb.PersistentDataBlockManagerInternal {
        private InternalService() {
        }

        @Override // com.android.server.pdb.PersistentDataBlockManagerInternal
        public void setFrpCredentialHandle(byte[] bArr) {
            writeInternal(bArr, com.android.server.pdb.PersistentDataBlockService.this.getFrpCredentialDataOffset(), com.android.server.pdb.PersistentDataBlockService.MAX_FRP_CREDENTIAL_HANDLE_SIZE);
        }

        @Override // com.android.server.pdb.PersistentDataBlockManagerInternal
        public byte[] getFrpCredentialHandle() {
            return readInternal(com.android.server.pdb.PersistentDataBlockService.this.getFrpCredentialDataOffset(), com.android.server.pdb.PersistentDataBlockService.MAX_FRP_CREDENTIAL_HANDLE_SIZE);
        }

        @Override // com.android.server.pdb.PersistentDataBlockManagerInternal
        public void setTestHarnessModeData(byte[] bArr) {
            writeInternal(bArr, com.android.server.pdb.PersistentDataBlockService.this.getTestHarnessModeDataOffset(), com.android.server.pdb.PersistentDataBlockService.MAX_TEST_MODE_DATA_SIZE);
        }

        @Override // com.android.server.pdb.PersistentDataBlockManagerInternal
        public byte[] getTestHarnessModeData() {
            byte[] readInternal = readInternal(com.android.server.pdb.PersistentDataBlockService.this.getTestHarnessModeDataOffset(), com.android.server.pdb.PersistentDataBlockService.MAX_TEST_MODE_DATA_SIZE);
            if (readInternal == null) {
                return new byte[0];
            }
            return readInternal;
        }

        @Override // com.android.server.pdb.PersistentDataBlockManagerInternal
        public void clearTestHarnessModeData() {
            writeDataBuffer(com.android.server.pdb.PersistentDataBlockService.this.getTestHarnessModeDataOffset(), java.nio.ByteBuffer.allocate(java.lang.Math.min(com.android.server.pdb.PersistentDataBlockService.MAX_TEST_MODE_DATA_SIZE, getTestHarnessModeData().length) + 4));
        }

        @Override // com.android.server.pdb.PersistentDataBlockManagerInternal
        public int getAllowedUid() {
            return com.android.server.pdb.PersistentDataBlockService.this.mAllowedUid;
        }

        @Override // com.android.server.pdb.PersistentDataBlockManagerInternal
        public boolean deactivateFactoryResetProtectionWithoutSecret() {
            synchronized (com.android.server.pdb.PersistentDataBlockService.this.mLock) {
                com.android.server.pdb.PersistentDataBlockService.this.mFrpActive = false;
            }
            return true;
        }

        private void writeInternal(byte[] bArr, long j, int i) {
            boolean z = true;
            com.android.internal.util.Preconditions.checkArgument(bArr == null || bArr.length > 0, "data must be null or non-empty");
            if (bArr != null && bArr.length > i) {
                z = false;
            }
            com.android.internal.util.Preconditions.checkArgument(z, "data must not be longer than " + i);
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(i + 4);
            allocate.putInt(bArr != null ? bArr.length : 0);
            if (bArr != null) {
                allocate.put(bArr);
            }
            allocate.flip();
            writeDataBuffer(j, allocate);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean writeDataBuffer(long j, java.nio.ByteBuffer byteBuffer) {
            synchronized (com.android.server.pdb.PersistentDataBlockService.this.mLock) {
                if (!com.android.server.pdb.PersistentDataBlockService.this.mIsWritable) {
                    return false;
                }
                try {
                    java.nio.channels.FileChannel blockOutputChannel = com.android.server.pdb.PersistentDataBlockService.this.getBlockOutputChannel();
                    try {
                        blockOutputChannel.position(j);
                        blockOutputChannel.write(byteBuffer);
                        blockOutputChannel.force(true);
                        blockOutputChannel.close();
                        return com.android.server.pdb.PersistentDataBlockService.this.computeAndWriteDigestLocked();
                    } catch (java.lang.Throwable th) {
                        if (blockOutputChannel != null) {
                            try {
                                blockOutputChannel.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (java.io.IOException e) {
                    android.util.Slog.e(com.android.server.pdb.PersistentDataBlockService.TAG, "unable to access persistent partition", e);
                    return false;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
        /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.AutoCloseable] */
        /* JADX WARN: Type inference failed for: r0v3, types: [java.io.DataInputStream, java.lang.AutoCloseable] */
        private byte[] readInternal(long j, int i) {
            ?? enforceChecksumValidity = com.android.server.pdb.PersistentDataBlockService.this.enforceChecksumValidity();
            if (enforceChecksumValidity == 0) {
                throw new java.lang.IllegalStateException("invalid checksum");
            }
            try {
                try {
                    enforceChecksumValidity = new java.io.DataInputStream(new java.io.FileInputStream(new java.io.File(com.android.server.pdb.PersistentDataBlockService.this.mDataBlockFile)));
                    try {
                        synchronized (com.android.server.pdb.PersistentDataBlockService.this.mLock) {
                            enforceChecksumValidity.skip(j);
                            int readInt = enforceChecksumValidity.readInt();
                            if (readInt <= 0 || readInt > i) {
                                libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) enforceChecksumValidity);
                                return null;
                            }
                            byte[] bArr = new byte[readInt];
                            enforceChecksumValidity.readFully(bArr);
                            return bArr;
                        }
                    } catch (java.io.IOException e) {
                        throw new java.lang.IllegalStateException("persistent partition not readable", e);
                    }
                } catch (java.io.FileNotFoundException e2) {
                    throw new java.lang.IllegalStateException("persistent partition not available");
                }
            } finally {
                libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) enforceChecksumValidity);
            }
        }

        @Override // com.android.server.pdb.PersistentDataBlockManagerInternal
        public void forceOemUnlockEnabled(boolean z) {
            synchronized (com.android.server.pdb.PersistentDataBlockService.this.mLock) {
                com.android.server.pdb.PersistentDataBlockService.this.doSetOemUnlockEnabledLocked(z);
                com.android.server.pdb.PersistentDataBlockService.this.computeAndWriteDigestLocked();
            }
        }
    }
}
