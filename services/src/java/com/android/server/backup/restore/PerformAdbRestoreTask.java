package com.android.server.backup.restore;

/* loaded from: classes.dex */
public class PerformAdbRestoreTask implements java.lang.Runnable {
    private final com.android.server.backup.UserBackupManagerService mBackupManagerService;
    private final java.lang.String mCurrentPassword;
    private final java.lang.String mDecryptPassword;
    private final android.os.ParcelFileDescriptor mInputFile;
    private final java.util.concurrent.atomic.AtomicBoolean mLatchObject;
    private final com.android.server.backup.fullbackup.FullBackupObbConnection mObbConnection;
    private android.app.backup.IFullBackupRestoreObserver mObserver;
    private final com.android.server.backup.OperationStorage mOperationStorage;

    public PerformAdbRestoreTask(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.OperationStorage operationStorage, android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str, java.lang.String str2, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver, java.util.concurrent.atomic.AtomicBoolean atomicBoolean) {
        this.mBackupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        this.mInputFile = parcelFileDescriptor;
        this.mCurrentPassword = str;
        this.mDecryptPassword = str2;
        this.mObserver = iFullBackupRestoreObserver;
        this.mLatchObject = atomicBoolean;
        this.mObbConnection = new com.android.server.backup.fullbackup.FullBackupObbConnection(userBackupManagerService);
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x0167 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0190 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run() {
        java.io.InputStream parseBackupFileHeaderAndReturnTarStream;
        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "--- Performing full-dataset restore ---");
        this.mObbConnection.establish();
        this.mObserver = com.android.server.backup.utils.FullBackupRestoreObserverUtils.sendStartRestore(this.mObserver);
        java.io.FileInputStream fileInputStream = null;
        try {
            if (!this.mBackupManagerService.backupPasswordMatches(this.mCurrentPassword)) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Backup password mismatch; aborting");
                try {
                    this.mInputFile.close();
                } catch (java.io.IOException e) {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Close of restore data pipe threw", e);
                }
                synchronized (this.mLatchObject) {
                    this.mLatchObject.set(true);
                    this.mLatchObject.notifyAll();
                }
                this.mObbConnection.tearDown();
                this.mObserver = com.android.server.backup.utils.FullBackupRestoreObserverUtils.sendEndRestore(this.mObserver);
                android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Full restore pass complete.");
                this.mBackupManagerService.getWakelock().release();
                return;
            }
            java.io.FileInputStream fileInputStream2 = new java.io.FileInputStream(this.mInputFile.getFileDescriptor());
            try {
                parseBackupFileHeaderAndReturnTarStream = parseBackupFileHeaderAndReturnTarStream(fileInputStream2, this.mDecryptPassword);
            } catch (java.lang.Exception e2) {
                fileInputStream = fileInputStream2;
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to read restore input");
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (java.io.IOException e3) {
                        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Close of restore data pipe threw", e3);
                        synchronized (this.mLatchObject) {
                            this.mLatchObject.set(true);
                            this.mLatchObject.notifyAll();
                        }
                        this.mObbConnection.tearDown();
                        this.mObserver = com.android.server.backup.utils.FullBackupRestoreObserverUtils.sendEndRestore(this.mObserver);
                        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Full restore pass complete.");
                        this.mBackupManagerService.getWakelock().release();
                    }
                }
                this.mInputFile.close();
                synchronized (this.mLatchObject) {
                }
            } catch (java.lang.Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                java.lang.Throwable th2 = th;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (java.io.IOException e4) {
                        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Close of restore data pipe threw", e4);
                        synchronized (this.mLatchObject) {
                            this.mLatchObject.set(true);
                            this.mLatchObject.notifyAll();
                        }
                        this.mObbConnection.tearDown();
                        this.mObserver = com.android.server.backup.utils.FullBackupRestoreObserverUtils.sendEndRestore(this.mObserver);
                        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Full restore pass complete.");
                        this.mBackupManagerService.getWakelock().release();
                        throw th2;
                    }
                }
                this.mInputFile.close();
                synchronized (this.mLatchObject) {
                }
            }
            if (parseBackupFileHeaderAndReturnTarStream == null) {
                try {
                    fileInputStream2.close();
                    this.mInputFile.close();
                } catch (java.io.IOException e5) {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Close of restore data pipe threw", e5);
                }
                synchronized (this.mLatchObject) {
                    this.mLatchObject.set(true);
                    this.mLatchObject.notifyAll();
                }
                this.mObbConnection.tearDown();
                this.mObserver = com.android.server.backup.utils.FullBackupRestoreObserverUtils.sendEndRestore(this.mObserver);
                android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Full restore pass complete.");
                this.mBackupManagerService.getWakelock().release();
                return;
            }
            new com.android.server.backup.restore.FullRestoreEngineThread(new com.android.server.backup.restore.FullRestoreEngine(this.mBackupManagerService, this.mOperationStorage, null, this.mObserver, null, null, true, 0, true, new com.android.server.backup.utils.BackupEligibilityRules(this.mBackupManagerService.getPackageManager(), (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class), this.mBackupManagerService.getUserId(), this.mBackupManagerService.getContext(), 2)), parseBackupFileHeaderAndReturnTarStream).run();
            try {
                fileInputStream2.close();
                this.mInputFile.close();
            } catch (java.io.IOException e6) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Close of restore data pipe threw", e6);
            }
            synchronized (this.mLatchObject) {
                this.mLatchObject.set(true);
                this.mLatchObject.notifyAll();
            }
            this.mObbConnection.tearDown();
            this.mObserver = com.android.server.backup.utils.FullBackupRestoreObserverUtils.sendEndRestore(this.mObserver);
            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Full restore pass complete.");
            this.mBackupManagerService.getWakelock().release();
        } catch (java.lang.Throwable th3) {
            th = th3;
        }
    }

    private static void readFullyOrThrow(java.io.InputStream inputStream, byte[] bArr) throws java.io.IOException {
        int i = 0;
        while (i < bArr.length) {
            int read = inputStream.read(bArr, i, bArr.length - i);
            if (read <= 0) {
                throw new java.io.IOException("Couldn't fully read data");
            }
            i += read;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static java.io.InputStream parseBackupFileHeaderAndReturnTarStream(java.io.InputStream inputStream, java.lang.String str) throws java.io.IOException {
        boolean z;
        boolean z2;
        byte[] bArr = new byte[com.android.server.backup.UserBackupManagerService.BACKUP_FILE_HEADER_MAGIC.length()];
        readFullyOrThrow(inputStream, bArr);
        boolean z3 = false;
        if (java.util.Arrays.equals(com.android.server.backup.UserBackupManagerService.BACKUP_FILE_HEADER_MAGIC.getBytes("UTF-8"), bArr)) {
            java.lang.String readHeaderLine = readHeaderLine(inputStream);
            int parseInt = java.lang.Integer.parseInt(readHeaderLine);
            if (parseInt <= 5) {
                boolean z4 = parseInt == 1;
                boolean z5 = java.lang.Integer.parseInt(readHeaderLine(inputStream)) != 0;
                java.lang.String readHeaderLine2 = readHeaderLine(inputStream);
                if (readHeaderLine2.equals("none")) {
                    z3 = true;
                } else if (str != null && str.length() > 0) {
                    inputStream = decodeAesHeaderAndInitialize(str, readHeaderLine2, z4, inputStream);
                    if (inputStream != null) {
                        z3 = true;
                    }
                } else {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Archive is encrypted but no password given");
                }
                z2 = z3;
                z3 = z5;
            } else {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Wrong header version: " + readHeaderLine);
                z2 = false;
            }
            boolean z6 = z3;
            z3 = z2;
            z = z6;
        } else {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Didn't read the right header magic");
            z = false;
        }
        if (z3) {
            return z ? new java.util.zip.InflaterInputStream(inputStream) : inputStream;
        }
        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Invalid restore data; aborting.");
        return null;
    }

    private static java.lang.String readHeaderLine(java.io.InputStream inputStream) throws java.io.IOException {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(80);
        while (true) {
            int read = inputStream.read();
            if (read < 0 || read == 10) {
                break;
            }
            sb.append((char) read);
        }
        return sb.toString();
    }

    private static java.io.InputStream attemptEncryptionKeyDecryption(java.lang.String str, java.lang.String str2, byte[] bArr, byte[] bArr2, int i, java.lang.String str3, java.lang.String str4, java.io.InputStream inputStream, boolean z) {
        javax.crypto.CipherInputStream cipherInputStream = null;
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
            javax.crypto.SecretKey buildPasswordKey = com.android.server.backup.utils.PasswordUtils.buildPasswordKey(str2, str, bArr, i);
            cipher.init(2, new javax.crypto.spec.SecretKeySpec(buildPasswordKey.getEncoded(), "AES"), new javax.crypto.spec.IvParameterSpec(com.android.server.backup.utils.PasswordUtils.hexToByteArray(str3)));
            byte[] doFinal = cipher.doFinal(com.android.server.backup.utils.PasswordUtils.hexToByteArray(str4));
            int i2 = doFinal[0] + 1;
            byte[] copyOfRange = java.util.Arrays.copyOfRange(doFinal, 1, i2);
            int i3 = i2 + 1;
            int i4 = doFinal[i2] + i3;
            byte[] copyOfRange2 = java.util.Arrays.copyOfRange(doFinal, i3, i4);
            int i5 = i4 + 1;
            if (java.util.Arrays.equals(com.android.server.backup.utils.PasswordUtils.makeKeyChecksum(str2, copyOfRange2, bArr2, i), java.util.Arrays.copyOfRange(doFinal, i5, doFinal[i4] + i5))) {
                cipher.init(2, new javax.crypto.spec.SecretKeySpec(copyOfRange2, "AES"), new javax.crypto.spec.IvParameterSpec(copyOfRange));
                cipherInputStream = new javax.crypto.CipherInputStream(inputStream, cipher);
            } else if (z) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Incorrect password");
            }
        } catch (java.security.InvalidAlgorithmParameterException e) {
            if (z) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Needed parameter spec unavailable!", e);
            }
        } catch (java.security.InvalidKeyException e2) {
            if (z) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Illegal password; aborting");
            }
        } catch (java.security.NoSuchAlgorithmException e3) {
            if (z) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Needed decryption algorithm unavailable!");
            }
        } catch (javax.crypto.BadPaddingException e4) {
            if (z) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Incorrect password");
            }
        } catch (javax.crypto.IllegalBlockSizeException e5) {
            if (z) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Invalid block size in encryption key");
            }
        } catch (javax.crypto.NoSuchPaddingException e6) {
            if (z) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Needed padding mechanism unavailable!");
            }
        }
        return cipherInputStream;
    }

    private static java.io.InputStream decodeAesHeaderAndInitialize(java.lang.String str, java.lang.String str2, boolean z, java.io.InputStream inputStream) {
        java.io.InputStream inputStream2 = null;
        try {
            if (str2.equals(com.android.server.backup.utils.PasswordUtils.ENCRYPTION_ALGORITHM_NAME)) {
                byte[] hexToByteArray = com.android.server.backup.utils.PasswordUtils.hexToByteArray(readHeaderLine(inputStream));
                byte[] hexToByteArray2 = com.android.server.backup.utils.PasswordUtils.hexToByteArray(readHeaderLine(inputStream));
                int parseInt = java.lang.Integer.parseInt(readHeaderLine(inputStream));
                java.lang.String readHeaderLine = readHeaderLine(inputStream);
                java.lang.String readHeaderLine2 = readHeaderLine(inputStream);
                inputStream2 = attemptEncryptionKeyDecryption(str, com.android.server.backup.BackupPasswordManager.PBKDF_CURRENT, hexToByteArray, hexToByteArray2, parseInt, readHeaderLine, readHeaderLine2, inputStream, false);
                if (inputStream2 == null && z) {
                    inputStream2 = attemptEncryptionKeyDecryption(str, com.android.server.backup.BackupPasswordManager.PBKDF_FALLBACK, hexToByteArray, hexToByteArray2, parseInt, readHeaderLine, readHeaderLine2, inputStream, true);
                }
            } else {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Unsupported encryption method: " + str2);
            }
        } catch (java.io.IOException e) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Can't read input header");
        } catch (java.lang.NumberFormatException e2) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Can't parse restore data header");
        }
        return inputStream2;
    }
}
