package com.android.server.backup.fullbackup;

/* loaded from: classes.dex */
public class PerformAdbBackupTask extends com.android.server.backup.fullbackup.FullBackupTask implements com.android.server.backup.BackupRestoreTask {
    private final boolean mAllApps;
    private final com.android.server.backup.utils.BackupEligibilityRules mBackupEligibilityRules;
    private final boolean mCompress;
    private final int mCurrentOpToken;
    private final java.lang.String mCurrentPassword;
    private android.content.pm.PackageInfo mCurrentTarget;
    private final boolean mDoWidgets;
    private final java.lang.String mEncryptPassword;
    private final boolean mIncludeApks;
    private final boolean mIncludeObbs;
    private final boolean mIncludeShared;
    private final boolean mIncludeSystem;
    private final boolean mKeyValue;
    private final java.util.concurrent.atomic.AtomicBoolean mLatch;
    private final com.android.server.backup.OperationStorage mOperationStorage;
    private final android.os.ParcelFileDescriptor mOutputFile;
    private final java.util.ArrayList<java.lang.String> mPackages;
    private final com.android.server.backup.UserBackupManagerService mUserBackupManagerService;

    public PerformAdbBackupTask(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.OperationStorage operationStorage, android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver, boolean z, boolean z2, boolean z3, boolean z4, java.lang.String str, java.lang.String str2, boolean z5, boolean z6, boolean z7, boolean z8, java.lang.String[] strArr, java.util.concurrent.atomic.AtomicBoolean atomicBoolean, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        super(iFullBackupRestoreObserver);
        java.util.ArrayList<java.lang.String> arrayList;
        this.mUserBackupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        this.mCurrentOpToken = userBackupManagerService.generateRandomIntegerToken();
        this.mLatch = atomicBoolean;
        this.mOutputFile = parcelFileDescriptor;
        this.mIncludeApks = z;
        this.mIncludeObbs = z2;
        this.mIncludeShared = z3;
        this.mDoWidgets = z4;
        this.mAllApps = z5;
        this.mIncludeSystem = z6;
        if (strArr == null) {
            arrayList = new java.util.ArrayList<>();
        } else {
            arrayList = new java.util.ArrayList<>(java.util.Arrays.asList(strArr));
        }
        this.mPackages = arrayList;
        this.mCurrentPassword = str;
        if (str2 == null || "".equals(str2)) {
            this.mEncryptPassword = str;
        } else {
            this.mEncryptPassword = str2;
        }
        this.mCompress = z7;
        this.mKeyValue = z8;
        this.mBackupEligibilityRules = backupEligibilityRules;
    }

    private void addPackagesToSet(java.util.TreeMap<java.lang.String, android.content.pm.PackageInfo> treeMap, java.util.List<java.lang.String> list) {
        for (java.lang.String str : list) {
            if (!treeMap.containsKey(str)) {
                try {
                    treeMap.put(str, this.mUserBackupManagerService.getPackageManager().getPackageInfo(str, 134217728));
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Unknown package " + str + ", skipping");
                }
            }
        }
    }

    private java.io.OutputStream emitAesBackupHeader(java.lang.StringBuilder sb, java.io.OutputStream outputStream) throws java.lang.Exception {
        byte[] randomBytes = this.mUserBackupManagerService.randomBytes(512);
        javax.crypto.SecretKey buildPasswordKey = com.android.server.backup.utils.PasswordUtils.buildPasswordKey(com.android.server.backup.BackupPasswordManager.PBKDF_CURRENT, this.mEncryptPassword, randomBytes, 10000);
        byte[] bArr = new byte[32];
        this.mUserBackupManagerService.getRng().nextBytes(bArr);
        byte[] randomBytes2 = this.mUserBackupManagerService.randomBytes(512);
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
        javax.crypto.spec.SecretKeySpec secretKeySpec = new javax.crypto.spec.SecretKeySpec(bArr, "AES");
        cipher.init(1, secretKeySpec);
        javax.crypto.CipherOutputStream cipherOutputStream = new javax.crypto.CipherOutputStream(outputStream, cipher);
        sb.append(com.android.server.backup.utils.PasswordUtils.ENCRYPTION_ALGORITHM_NAME);
        sb.append('\n');
        sb.append(com.android.server.backup.utils.PasswordUtils.byteArrayToHex(randomBytes));
        sb.append('\n');
        sb.append(com.android.server.backup.utils.PasswordUtils.byteArrayToHex(randomBytes2));
        sb.append('\n');
        sb.append(10000);
        sb.append('\n');
        javax.crypto.Cipher cipher2 = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher2.init(1, buildPasswordKey);
        sb.append(com.android.server.backup.utils.PasswordUtils.byteArrayToHex(cipher2.getIV()));
        sb.append('\n');
        byte[] iv = cipher.getIV();
        byte[] encoded = secretKeySpec.getEncoded();
        byte[] makeKeyChecksum = com.android.server.backup.utils.PasswordUtils.makeKeyChecksum(com.android.server.backup.BackupPasswordManager.PBKDF_CURRENT, secretKeySpec.getEncoded(), randomBytes2, 10000);
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(iv.length + encoded.length + makeKeyChecksum.length + 3);
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeByte(iv.length);
        dataOutputStream.write(iv);
        dataOutputStream.writeByte(encoded.length);
        dataOutputStream.write(encoded);
        dataOutputStream.writeByte(makeKeyChecksum.length);
        dataOutputStream.write(makeKeyChecksum);
        dataOutputStream.flush();
        sb.append(com.android.server.backup.utils.PasswordUtils.byteArrayToHex(cipher2.doFinal(byteArrayOutputStream.toByteArray())));
        sb.append('\n');
        return cipherOutputStream;
    }

    private void finalizeBackup(java.io.OutputStream outputStream) {
        try {
            outputStream.write(new byte[1024]);
        } catch (java.io.IOException e) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Error attempting to finalize backup stream");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0475 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0448 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0192 A[Catch: all -> 0x03cd, Exception -> 0x0434, RemoteException -> 0x0439, TRY_ENTER, TRY_LEAVE, TryCatch #34 {all -> 0x03cd, blocks: (B:48:0x011a, B:51:0x0137, B:118:0x0192, B:121:0x01ab, B:125:0x01c2, B:263:0x01bb), top: B:47:0x011a }] */
    /* JADX WARN: Removed duplicated region for block: B:169:0x04ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x04d2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0141 A[Catch: all -> 0x0128, Exception -> 0x012d, RemoteException -> 0x0131, TRY_ENTER, TRY_LEAVE, TryCatch #7 {all -> 0x0128, blocks: (B:294:0x011e, B:53:0x0141, B:124:0x01b0, B:127:0x01d3), top: B:293:0x011e }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x04bf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0492 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 3 */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run() {
        java.lang.Throwable th;
        boolean z;
        android.app.backup.IBackupManagerMonitor iBackupManagerMonitor;
        android.app.backup.IBackupManagerMonitor iBackupManagerMonitor2;
        java.util.ArrayList arrayList;
        android.app.backup.IBackupManagerMonitor iBackupManagerMonitor3;
        android.content.pm.PackageInfo packageInfo;
        java.lang.String str;
        java.util.List<java.lang.String> widgetParticipants;
        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "--- Performing adb backup" + (this.mKeyValue ? ", including key-value backups" : "") + " ---");
        java.util.TreeMap<java.lang.String, android.content.pm.PackageInfo> treeMap = new java.util.TreeMap<>();
        com.android.server.backup.fullbackup.FullBackupObbConnection fullBackupObbConnection = new com.android.server.backup.fullbackup.FullBackupObbConnection(this.mUserBackupManagerService);
        fullBackupObbConnection.establish();
        sendStartBackup();
        android.content.pm.PackageManager packageManager = this.mUserBackupManagerService.getPackageManager();
        boolean z2 = false;
        if (this.mAllApps) {
            java.util.List<android.content.pm.PackageInfo> installedPackages = packageManager.getInstalledPackages(134217728);
            for (int i = 0; i < installedPackages.size(); i++) {
                android.content.pm.PackageInfo packageInfo2 = installedPackages.get(i);
                if (this.mIncludeSystem || (packageInfo2.applicationInfo.flags & 1) == 0) {
                    treeMap.put(packageInfo2.packageName, packageInfo2);
                }
            }
        }
        if (this.mDoWidgets && (widgetParticipants = com.android.server.AppWidgetBackupBridge.getWidgetParticipants(0)) != null) {
            addPackagesToSet(treeMap, widgetParticipants);
        }
        if (this.mPackages != null) {
            addPackagesToSet(treeMap, this.mPackages);
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.Iterator<java.util.Map.Entry<java.lang.String, android.content.pm.PackageInfo>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            android.content.pm.PackageInfo value = it.next().getValue();
            if (!this.mBackupEligibilityRules.appIsEligibleForBackup(value.applicationInfo) || this.mBackupEligibilityRules.appIsStopped(value.applicationInfo)) {
                it.remove();
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Package " + value.packageName + " is not eligible for backup, removing.");
            } else if (this.mBackupEligibilityRules.appIsKeyValueOnly(value)) {
                it.remove();
                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Package " + value.packageName + " is key-value.");
                arrayList2.add(value);
            }
        }
        java.util.ArrayList arrayList3 = new java.util.ArrayList(treeMap.values());
        android.app.backup.IBackupManagerMonitor fileOutputStream = new java.io.FileOutputStream(this.mOutputFile.getFileDescriptor());
        android.app.backup.IBackupManagerMonitor iBackupManagerMonitor4 = null;
        try {
            try {
                try {
                } catch (android.os.RemoteException e) {
                } catch (java.lang.Exception e2) {
                    e = e2;
                }
                if (this.mEncryptPassword != null) {
                    try {
                        try {
                        } catch (android.os.RemoteException e3) {
                            z2 = true;
                            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "App died during full backup");
                            if (iBackupManagerMonitor4 != null) {
                                try {
                                    iBackupManagerMonitor4.flush();
                                    iBackupManagerMonitor4.close();
                                } catch (java.io.IOException e4) {
                                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "IO error closing adb backup file: " + e4.getMessage());
                                    synchronized (this.mLatch) {
                                        this.mLatch.set(z2);
                                        this.mLatch.notifyAll();
                                    }
                                    sendEndBackup();
                                    fullBackupObbConnection.tearDown();
                                    android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Full backup pass complete.");
                                    this.mUserBackupManagerService.getWakelock().release();
                                    return;
                                }
                            }
                            this.mOutputFile.close();
                            synchronized (this.mLatch) {
                            }
                        } catch (java.lang.Exception e5) {
                            e = e5;
                            z2 = true;
                            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Internal exception during full backup", e);
                            if (iBackupManagerMonitor4 != null) {
                                try {
                                    iBackupManagerMonitor4.flush();
                                    iBackupManagerMonitor4.close();
                                } catch (java.io.IOException e6) {
                                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "IO error closing adb backup file: " + e6.getMessage());
                                    synchronized (this.mLatch) {
                                        this.mLatch.set(z2);
                                        this.mLatch.notifyAll();
                                    }
                                    sendEndBackup();
                                    fullBackupObbConnection.tearDown();
                                    android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Full backup pass complete.");
                                    this.mUserBackupManagerService.getWakelock().release();
                                    return;
                                }
                            }
                            this.mOutputFile.close();
                            synchronized (this.mLatch) {
                            }
                        }
                        if (this.mEncryptPassword.length() > 0) {
                            z = true;
                            if (this.mUserBackupManagerService.backupPasswordMatches(this.mCurrentPassword)) {
                                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Backup password mismatch; aborting");
                                try {
                                    this.mOutputFile.close();
                                } catch (java.io.IOException e7) {
                                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "IO error closing adb backup file: " + e7.getMessage());
                                }
                                synchronized (this.mLatch) {
                                    this.mLatch.set(true);
                                    this.mLatch.notifyAll();
                                }
                                sendEndBackup();
                                fullBackupObbConnection.tearDown();
                                android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Full backup pass complete.");
                                this.mUserBackupManagerService.getWakelock().release();
                                return;
                            }
                            java.lang.StringBuilder sb = new java.lang.StringBuilder(1024);
                            sb.append(com.android.server.backup.UserBackupManagerService.BACKUP_FILE_HEADER_MAGIC);
                            sb.append(5);
                            sb.append(this.mCompress ? "\n1\n" : "\n0\n");
                            try {
                                if (z) {
                                    iBackupManagerMonitor = emitAesBackupHeader(sb, fileOutputStream);
                                } else {
                                    sb.append("none\n");
                                    iBackupManagerMonitor = fileOutputStream;
                                }
                                fileOutputStream.write(sb.toString().getBytes("UTF-8"));
                                android.app.backup.IBackupManagerMonitor deflaterOutputStream = this.mCompress ? new java.util.zip.DeflaterOutputStream((java.io.OutputStream) iBackupManagerMonitor, new java.util.zip.Deflater(9), true) : iBackupManagerMonitor;
                                try {
                                    if (this.mIncludeShared) {
                                        try {
                                            try {
                                                arrayList3.add(this.mUserBackupManagerService.getPackageManager().getPackageInfo(com.android.server.backup.UserBackupManagerService.SHARED_BACKUP_AGENT_PACKAGE, 0));
                                            } catch (android.os.RemoteException e8) {
                                                iBackupManagerMonitor4 = deflaterOutputStream;
                                                z2 = true;
                                                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "App died during full backup");
                                                if (iBackupManagerMonitor4 != null) {
                                                }
                                                this.mOutputFile.close();
                                                synchronized (this.mLatch) {
                                                }
                                            } catch (java.lang.Exception e9) {
                                                e = e9;
                                                iBackupManagerMonitor4 = deflaterOutputStream;
                                                z2 = true;
                                                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Internal exception during full backup", e);
                                                if (iBackupManagerMonitor4 != null) {
                                                }
                                                this.mOutputFile.close();
                                                synchronized (this.mLatch) {
                                                }
                                            } catch (java.lang.Throwable th2) {
                                                th = th2;
                                                iBackupManagerMonitor4 = deflaterOutputStream;
                                                z2 = true;
                                                if (iBackupManagerMonitor4 != null) {
                                                }
                                                this.mOutputFile.close();
                                                synchronized (this.mLatch) {
                                                }
                                            }
                                        } catch (android.content.pm.PackageManager.NameNotFoundException e10) {
                                            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to find shared-storage backup handler");
                                        }
                                    }
                                    int size = arrayList3.size();
                                    int i2 = 0;
                                    while (i2 < size) {
                                        try {
                                            android.content.pm.PackageInfo packageInfo3 = (android.content.pm.PackageInfo) arrayList3.get(i2);
                                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "--- Performing full backup for package " + packageInfo3.packageName + " ---");
                                            boolean equals = packageInfo3.packageName.equals(com.android.server.backup.UserBackupManagerService.SHARED_BACKUP_AGENT_PACKAGE);
                                            android.app.backup.IBackupManagerMonitor iBackupManagerMonitor5 = deflaterOutputStream;
                                            int i3 = i2;
                                            android.app.backup.IBackupManagerMonitor iBackupManagerMonitor6 = iBackupManagerMonitor4;
                                            java.util.ArrayList arrayList4 = arrayList3;
                                            java.util.ArrayList arrayList5 = arrayList2;
                                            try {
                                                com.android.server.backup.fullbackup.FullBackupEngine fullBackupEngine = new com.android.server.backup.fullbackup.FullBackupEngine(this.mUserBackupManagerService, deflaterOutputStream, null, packageInfo3, this.mIncludeApks, this, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, this.mCurrentOpToken, 0, this.mBackupEligibilityRules, new com.android.server.backup.utils.BackupManagerMonitorEventSender(iBackupManagerMonitor4));
                                                if (equals) {
                                                    str = "Shared storage";
                                                    packageInfo = packageInfo3;
                                                } else {
                                                    packageInfo = packageInfo3;
                                                    str = packageInfo.packageName;
                                                }
                                                sendOnBackupPackage(str);
                                                this.mCurrentTarget = packageInfo;
                                                fullBackupEngine.backupOnePackage();
                                                if (!this.mIncludeObbs || equals) {
                                                    iBackupManagerMonitor3 = iBackupManagerMonitor5;
                                                } else {
                                                    iBackupManagerMonitor3 = iBackupManagerMonitor5;
                                                    try {
                                                        if (!fullBackupObbConnection.backupObbs(packageInfo, iBackupManagerMonitor3)) {
                                                            throw new java.lang.RuntimeException("Failure writing OBB stack for " + packageInfo);
                                                        }
                                                    } catch (android.os.RemoteException e11) {
                                                        iBackupManagerMonitor4 = iBackupManagerMonitor3;
                                                        z2 = true;
                                                        android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "App died during full backup");
                                                        if (iBackupManagerMonitor4 != null) {
                                                        }
                                                        this.mOutputFile.close();
                                                        synchronized (this.mLatch) {
                                                        }
                                                    } catch (java.lang.Exception e12) {
                                                        e = e12;
                                                        iBackupManagerMonitor4 = iBackupManagerMonitor3;
                                                        z2 = true;
                                                        android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Internal exception during full backup", e);
                                                        if (iBackupManagerMonitor4 != null) {
                                                        }
                                                        this.mOutputFile.close();
                                                        synchronized (this.mLatch) {
                                                        }
                                                    } catch (java.lang.Throwable th3) {
                                                        th = th3;
                                                        th = th;
                                                        iBackupManagerMonitor4 = iBackupManagerMonitor3;
                                                        z2 = true;
                                                        if (iBackupManagerMonitor4 != null) {
                                                        }
                                                        this.mOutputFile.close();
                                                        synchronized (this.mLatch) {
                                                        }
                                                    }
                                                }
                                                i2 = i3 + 1;
                                                deflaterOutputStream = iBackupManagerMonitor3;
                                                arrayList3 = arrayList4;
                                                iBackupManagerMonitor4 = iBackupManagerMonitor6;
                                                arrayList2 = arrayList5;
                                            } catch (android.os.RemoteException e13) {
                                                iBackupManagerMonitor3 = iBackupManagerMonitor5;
                                            } catch (java.lang.Exception e14) {
                                                e = e14;
                                                iBackupManagerMonitor3 = iBackupManagerMonitor5;
                                            } catch (java.lang.Throwable th4) {
                                                th = th4;
                                                iBackupManagerMonitor3 = iBackupManagerMonitor5;
                                            }
                                        } catch (android.os.RemoteException e15) {
                                            iBackupManagerMonitor3 = deflaterOutputStream;
                                        } catch (java.lang.Exception e16) {
                                            e = e16;
                                            iBackupManagerMonitor3 = deflaterOutputStream;
                                        } catch (java.lang.Throwable th5) {
                                            th = th5;
                                            iBackupManagerMonitor3 = deflaterOutputStream;
                                        }
                                    }
                                    iBackupManagerMonitor2 = deflaterOutputStream;
                                    arrayList = arrayList2;
                                } catch (android.os.RemoteException e17) {
                                    iBackupManagerMonitor2 = deflaterOutputStream;
                                    z2 = true;
                                } catch (java.lang.Exception e18) {
                                    e = e18;
                                    iBackupManagerMonitor2 = deflaterOutputStream;
                                    z2 = true;
                                } catch (java.lang.Throwable th6) {
                                    th = th6;
                                    iBackupManagerMonitor2 = deflaterOutputStream;
                                    z2 = true;
                                }
                                try {
                                    if (this.mKeyValue) {
                                        java.util.Iterator it2 = arrayList.iterator();
                                        while (it2.hasNext()) {
                                            android.content.pm.PackageInfo packageInfo4 = (android.content.pm.PackageInfo) it2.next();
                                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "--- Performing key-value backup for package " + packageInfo4.packageName + " ---");
                                            com.android.server.backup.KeyValueAdbBackupEngine keyValueAdbBackupEngine = new com.android.server.backup.KeyValueAdbBackupEngine(iBackupManagerMonitor2, packageInfo4, this.mUserBackupManagerService, this.mUserBackupManagerService.getPackageManager(), this.mUserBackupManagerService.getBaseStateDir(), this.mUserBackupManagerService.getDataDir());
                                            sendOnBackupPackage(packageInfo4.packageName);
                                            keyValueAdbBackupEngine.backupOnePackage();
                                        }
                                    }
                                    finalizeBackup(iBackupManagerMonitor2);
                                    if (iBackupManagerMonitor2 != null) {
                                        try {
                                            iBackupManagerMonitor2.flush();
                                            iBackupManagerMonitor2.close();
                                        } catch (java.io.IOException e19) {
                                            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "IO error closing adb backup file: " + e19.getMessage());
                                        }
                                    }
                                    this.mOutputFile.close();
                                    synchronized (this.mLatch) {
                                        this.mLatch.set(true);
                                        this.mLatch.notifyAll();
                                    }
                                } catch (android.os.RemoteException e20) {
                                    z2 = true;
                                    iBackupManagerMonitor4 = iBackupManagerMonitor2;
                                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "App died during full backup");
                                    if (iBackupManagerMonitor4 != null) {
                                    }
                                    this.mOutputFile.close();
                                    synchronized (this.mLatch) {
                                    }
                                } catch (java.lang.Exception e21) {
                                    e = e21;
                                    z2 = true;
                                    iBackupManagerMonitor4 = iBackupManagerMonitor2;
                                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Internal exception during full backup", e);
                                    if (iBackupManagerMonitor4 != null) {
                                    }
                                    this.mOutputFile.close();
                                    synchronized (this.mLatch) {
                                    }
                                } catch (java.lang.Throwable th7) {
                                    th = th7;
                                    z2 = true;
                                    th = th;
                                    iBackupManagerMonitor4 = iBackupManagerMonitor2;
                                    if (iBackupManagerMonitor4 != null) {
                                    }
                                    this.mOutputFile.close();
                                    synchronized (this.mLatch) {
                                    }
                                }
                                sendEndBackup();
                                fullBackupObbConnection.tearDown();
                                android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Full backup pass complete.");
                                this.mUserBackupManagerService.getWakelock().release();
                                return;
                            } catch (java.lang.Exception e22) {
                                z2 = true;
                                try {
                                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to emit archive header", e22);
                                    try {
                                        this.mOutputFile.close();
                                    } catch (java.io.IOException e23) {
                                        android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "IO error closing adb backup file: " + e23.getMessage());
                                    }
                                    synchronized (this.mLatch) {
                                        this.mLatch.set(true);
                                        this.mLatch.notifyAll();
                                        sendEndBackup();
                                        fullBackupObbConnection.tearDown();
                                        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Full backup pass complete.");
                                        this.mUserBackupManagerService.getWakelock().release();
                                        return;
                                    }
                                } catch (android.os.RemoteException e24) {
                                    iBackupManagerMonitor4 = null;
                                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "App died during full backup");
                                    if (iBackupManagerMonitor4 != null) {
                                    }
                                    this.mOutputFile.close();
                                    synchronized (this.mLatch) {
                                    }
                                } catch (java.lang.Exception e25) {
                                    e = e25;
                                    iBackupManagerMonitor4 = null;
                                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Internal exception during full backup", e);
                                    if (iBackupManagerMonitor4 != null) {
                                    }
                                    this.mOutputFile.close();
                                    synchronized (this.mLatch) {
                                    }
                                } catch (java.lang.Throwable th8) {
                                    th = th8;
                                    iBackupManagerMonitor4 = null;
                                    if (iBackupManagerMonitor4 != null) {
                                    }
                                    this.mOutputFile.close();
                                    synchronized (this.mLatch) {
                                    }
                                }
                            }
                        }
                    } catch (java.lang.Throwable th9) {
                        th = th9;
                        z2 = true;
                        if (iBackupManagerMonitor4 != null) {
                            try {
                                iBackupManagerMonitor4.flush();
                                iBackupManagerMonitor4.close();
                            } catch (java.io.IOException e26) {
                                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "IO error closing adb backup file: " + e26.getMessage());
                                synchronized (this.mLatch) {
                                }
                            }
                        }
                        this.mOutputFile.close();
                        synchronized (this.mLatch) {
                            this.mLatch.set(z2);
                            this.mLatch.notifyAll();
                        }
                        sendEndBackup();
                        fullBackupObbConnection.tearDown();
                        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Full backup pass complete.");
                        this.mUserBackupManagerService.getWakelock().release();
                        throw th;
                    }
                }
                z = false;
                if (this.mUserBackupManagerService.backupPasswordMatches(this.mCurrentPassword)) {
                }
            } catch (java.lang.Throwable th10) {
                th = th10;
                z2 = true;
                th = th;
                if (iBackupManagerMonitor4 != null) {
                }
                this.mOutputFile.close();
                synchronized (this.mLatch) {
                }
            }
        } catch (java.lang.Throwable th11) {
            th = th11;
            th = th;
            if (iBackupManagerMonitor4 != null) {
            }
            this.mOutputFile.close();
            synchronized (this.mLatch) {
            }
        }
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void execute() {
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void operationComplete(long j) {
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void handleCancel(boolean z) {
        android.content.pm.PackageInfo packageInfo = this.mCurrentTarget;
        android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "adb backup cancel of " + packageInfo);
        if (packageInfo != null) {
            this.mUserBackupManagerService.tearDownAgentAndKill(this.mCurrentTarget.applicationInfo);
        }
        this.mOperationStorage.removeOperation(this.mCurrentOpToken);
    }
}
