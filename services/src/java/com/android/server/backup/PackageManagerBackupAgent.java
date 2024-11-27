package com.android.server.backup;

/* loaded from: classes.dex */
public class PackageManagerBackupAgent extends android.app.backup.BackupAgent {
    private static final java.lang.String ANCESTRAL_RECORD_KEY = "@ancestral_record@";
    private static final int ANCESTRAL_RECORD_VERSION = 1;
    private static final boolean DEBUG = false;
    private static final java.lang.String DEFAULT_HOME_KEY = "@home@";
    private static final java.lang.String GLOBAL_METADATA_KEY = "@meta@";
    private static final java.lang.String STATE_FILE_HEADER = "=state=";
    private static final int STATE_FILE_VERSION = 2;
    private static final java.lang.String TAG = "PMBA";
    private static final int UNDEFINED_ANCESTRAL_RECORD_VERSION = -1;
    private java.util.List<android.content.pm.PackageInfo> mAllPackages;
    private boolean mHasMetadata;
    private android.content.pm.PackageManager mPackageManager;
    private android.content.ComponentName mRestoredHome;
    private java.lang.String mRestoredHomeInstaller;
    private java.util.ArrayList<byte[]> mRestoredHomeSigHashes;
    private long mRestoredHomeVersion;
    private java.util.HashMap<java.lang.String, com.android.server.backup.PackageManagerBackupAgent.Metadata> mRestoredSignatures;
    private android.content.ComponentName mStoredHomeComponent;
    private java.util.ArrayList<byte[]> mStoredHomeSigHashes;
    private long mStoredHomeVersion;
    private java.lang.String mStoredIncrementalVersion;
    private int mStoredSdkVersion;
    private int mUserId;
    private java.util.HashMap<java.lang.String, com.android.server.backup.PackageManagerBackupAgent.Metadata> mStateVersions = new java.util.HashMap<>();
    private final java.util.HashSet<java.lang.String> mExisting = new java.util.HashSet<>();

    interface RestoreDataConsumer {
        void consumeRestoreData(android.app.backup.BackupDataInput backupDataInput) throws java.io.IOException;
    }

    public class Metadata {
        public java.util.ArrayList<byte[]> sigHashes;
        public long versionCode;

        Metadata(long j, java.util.ArrayList<byte[]> arrayList) {
            this.versionCode = j;
            this.sigHashes = arrayList;
        }
    }

    public PackageManagerBackupAgent(android.content.pm.PackageManager packageManager, java.util.List<android.content.pm.PackageInfo> list, int i) {
        init(packageManager, list, i);
    }

    public PackageManagerBackupAgent(android.content.pm.PackageManager packageManager, int i, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        init(packageManager, null, i);
        evaluateStorablePackages(backupEligibilityRules);
    }

    private void init(android.content.pm.PackageManager packageManager, java.util.List<android.content.pm.PackageInfo> list, int i) {
        this.mPackageManager = packageManager;
        this.mAllPackages = list;
        this.mRestoredSignatures = null;
        this.mHasMetadata = false;
        this.mStoredSdkVersion = android.os.Build.VERSION.SDK_INT;
        this.mStoredIncrementalVersion = android.os.Build.VERSION.INCREMENTAL;
        this.mUserId = i;
    }

    public void evaluateStorablePackages(com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        this.mAllPackages = getStorableApplications(this.mPackageManager, this.mUserId, backupEligibilityRules);
    }

    public static java.util.List<android.content.pm.PackageInfo> getStorableApplications(android.content.pm.PackageManager packageManager, int i, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        java.util.List<android.content.pm.PackageInfo> installedPackagesAsUser = packageManager.getInstalledPackagesAsUser(134217728, i);
        for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
            if (!backupEligibilityRules.appIsEligibleForBackup(installedPackagesAsUser.get(size).applicationInfo)) {
                installedPackagesAsUser.remove(size);
            }
        }
        return installedPackagesAsUser;
    }

    public boolean hasMetadata() {
        return this.mHasMetadata;
    }

    public int getSourceSdk() {
        return this.mStoredSdkVersion;
    }

    public com.android.server.backup.PackageManagerBackupAgent.Metadata getRestoredMetadata(java.lang.String str) {
        if (this.mRestoredSignatures == null) {
            android.util.Slog.w(TAG, "getRestoredMetadata() before metadata read!");
            return null;
        }
        return this.mRestoredSignatures.get(str);
    }

    public java.util.Set<java.lang.String> getRestoredPackages() {
        if (this.mRestoredSignatures == null) {
            android.util.Slog.w(TAG, "getRestoredPackages() before metadata read!");
            return null;
        }
        return this.mRestoredSignatures.keySet();
    }

    @Override // android.app.backup.BackupAgent
    public void onBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
        parseStateFile(parcelFileDescriptor);
        if (this.mStoredIncrementalVersion == null || !this.mStoredIncrementalVersion.equals(android.os.Build.VERSION.INCREMENTAL)) {
            android.util.Slog.i(TAG, "Previous metadata " + this.mStoredIncrementalVersion + " mismatch vs " + android.os.Build.VERSION.INCREMENTAL + " - rewriting");
            this.mExisting.clear();
        }
        try {
            dataOutputStream.writeInt(1);
            writeEntity(backupDataOutput, ANCESTRAL_RECORD_KEY, byteArrayOutputStream.toByteArray());
            try {
                byteArrayOutputStream.reset();
                if (!this.mExisting.contains(GLOBAL_METADATA_KEY)) {
                    dataOutputStream.writeInt(android.os.Build.VERSION.SDK_INT);
                    dataOutputStream.writeUTF(android.os.Build.VERSION.INCREMENTAL);
                    writeEntity(backupDataOutput, GLOBAL_METADATA_KEY, byteArrayOutputStream.toByteArray());
                } else {
                    this.mExisting.remove(GLOBAL_METADATA_KEY);
                }
                java.util.Iterator<android.content.pm.PackageInfo> it = this.mAllPackages.iterator();
                while (it.hasNext()) {
                    java.lang.String str = it.next().packageName;
                    if (!str.equals(GLOBAL_METADATA_KEY)) {
                        try {
                            android.content.pm.PackageInfo packageInfoAsUser = this.mPackageManager.getPackageInfoAsUser(str, 134217728, this.mUserId);
                            if (this.mExisting.contains(str)) {
                                this.mExisting.remove(str);
                                if (packageInfoAsUser.getLongVersionCode() == this.mStateVersions.get(str).versionCode) {
                                }
                            }
                            android.content.pm.SigningInfo signingInfo = packageInfoAsUser.signingInfo;
                            if (signingInfo == null) {
                                android.util.Slog.w(TAG, "Not backing up package " + str + " since it appears to have no signatures.");
                            } else {
                                byteArrayOutputStream.reset();
                                if (packageInfoAsUser.versionCodeMajor != 0) {
                                    dataOutputStream.writeInt(Integer.MIN_VALUE);
                                    dataOutputStream.writeLong(packageInfoAsUser.getLongVersionCode());
                                } else {
                                    dataOutputStream.writeInt(packageInfoAsUser.versionCode);
                                }
                                writeSignatureHashArray(dataOutputStream, com.android.server.backup.BackupUtils.hashSignatureArray(signingInfo.getApkContentsSigners()));
                                writeEntity(backupDataOutput, str, byteArrayOutputStream.toByteArray());
                            }
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                            this.mExisting.add(str);
                        }
                    }
                }
                writeStateFile(this.mAllPackages, parcelFileDescriptor2);
            } catch (java.io.IOException e2) {
                android.util.Slog.e(TAG, "Unable to write package backup data file!");
            }
        } catch (java.io.IOException e3) {
            android.util.Slog.e(TAG, "Unable to write package backup data file!");
        }
    }

    private static void writeEntity(android.app.backup.BackupDataOutput backupDataOutput, java.lang.String str, byte[] bArr) throws java.io.IOException {
        backupDataOutput.writeEntityHeader(str, bArr.length);
        backupDataOutput.writeEntityData(bArr, bArr.length);
    }

    @Override // android.app.backup.BackupAgent
    public void onRestore(android.app.backup.BackupDataInput backupDataInput, int i, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        com.android.server.backup.PackageManagerBackupAgent.RestoreDataConsumer restoreDataConsumer = getRestoreDataConsumer(getAncestralRecordVersionValue(backupDataInput));
        if (restoreDataConsumer == null) {
            android.util.Slog.w(TAG, "Ancestral restore set version is unknown to this Android version; not restoring");
        } else {
            restoreDataConsumer.consumeRestoreData(backupDataInput);
        }
    }

    private int getAncestralRecordVersionValue(android.app.backup.BackupDataInput backupDataInput) throws java.io.IOException {
        if (backupDataInput.readNextHeader()) {
            java.lang.String key = backupDataInput.getKey();
            int dataSize = backupDataInput.getDataSize();
            if (ANCESTRAL_RECORD_KEY.equals(key)) {
                byte[] bArr = new byte[dataSize];
                backupDataInput.readEntityData(bArr, 0, dataSize);
                return new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr)).readInt();
            }
        }
        return -1;
    }

    private com.android.server.backup.PackageManagerBackupAgent.RestoreDataConsumer getRestoreDataConsumer(int i) {
        byte b = 0;
        switch (i) {
            case -1:
                return new com.android.server.backup.PackageManagerBackupAgent.LegacyRestoreDataConsumer();
            case 0:
            default:
                android.util.Slog.e(TAG, "Unrecognized ANCESTRAL_RECORD_VERSION: " + i);
                return null;
            case 1:
                return new com.android.server.backup.PackageManagerBackupAgent.AncestralVersion1RestoreDataConsumer();
        }
    }

    private static void writeSignatureHashArray(java.io.DataOutputStream dataOutputStream, java.util.ArrayList<byte[]> arrayList) throws java.io.IOException {
        dataOutputStream.writeInt(arrayList.size());
        java.util.Iterator<byte[]> it = arrayList.iterator();
        while (it.hasNext()) {
            byte[] next = it.next();
            dataOutputStream.writeInt(next.length);
            dataOutputStream.write(next);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.ArrayList<byte[]> readSignatureHashArray(java.io.DataInputStream dataInputStream) {
        try {
            try {
                int readInt = dataInputStream.readInt();
                if (readInt > 20) {
                    android.util.Slog.e(TAG, "Suspiciously large sig count in restore data; aborting");
                    throw new java.lang.IllegalStateException("Bad restore state");
                }
                java.util.ArrayList<byte[]> arrayList = new java.util.ArrayList<>(readInt);
                boolean z = false;
                for (int i = 0; i < readInt; i++) {
                    int readInt2 = dataInputStream.readInt();
                    byte[] bArr = new byte[readInt2];
                    dataInputStream.read(bArr);
                    arrayList.add(bArr);
                    if (readInt2 != 32) {
                        z = true;
                    }
                }
                if (z) {
                    return com.android.server.backup.BackupUtils.hashSignatureArray(arrayList);
                }
                return arrayList;
            } catch (java.io.EOFException e) {
                android.util.Slog.w(TAG, "Read empty signature block");
                return null;
            }
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Unable to read signatures");
            return null;
        }
    }

    private void parseStateFile(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        long j;
        this.mExisting.clear();
        this.mStateVersions.clear();
        boolean z = false;
        this.mStoredSdkVersion = 0;
        this.mStoredIncrementalVersion = null;
        this.mStoredHomeComponent = null;
        this.mStoredHomeVersion = 0L;
        this.mStoredHomeSigHashes = null;
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.BufferedInputStream(new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor())));
        try {
            java.lang.String readUTF = dataInputStream.readUTF();
            if (readUTF.equals(STATE_FILE_HEADER)) {
                int readInt = dataInputStream.readInt();
                if (readInt > 2) {
                    android.util.Slog.w(TAG, "Unsupported state file version " + readInt + ", redoing from start");
                    return;
                }
                readUTF = dataInputStream.readUTF();
            } else {
                android.util.Slog.i(TAG, "Older version of saved state - rewriting");
                z = true;
            }
            if (readUTF.equals(DEFAULT_HOME_KEY)) {
                this.mStoredHomeComponent = android.content.ComponentName.unflattenFromString(dataInputStream.readUTF());
                this.mStoredHomeVersion = dataInputStream.readLong();
                this.mStoredHomeSigHashes = readSignatureHashArray(dataInputStream);
                readUTF = dataInputStream.readUTF();
            }
            if (readUTF.equals(GLOBAL_METADATA_KEY)) {
                this.mStoredSdkVersion = dataInputStream.readInt();
                this.mStoredIncrementalVersion = dataInputStream.readUTF();
                if (!z) {
                    this.mExisting.add(GLOBAL_METADATA_KEY);
                }
                while (true) {
                    java.lang.String readUTF2 = dataInputStream.readUTF();
                    int readInt2 = dataInputStream.readInt();
                    if (readInt2 == Integer.MIN_VALUE) {
                        j = dataInputStream.readLong();
                    } else {
                        j = readInt2;
                    }
                    if (!z) {
                        this.mExisting.add(readUTF2);
                    }
                    this.mStateVersions.put(readUTF2, new com.android.server.backup.PackageManagerBackupAgent.Metadata(j, null));
                }
            } else {
                android.util.Slog.e(TAG, "No global metadata in state file!");
            }
        } catch (java.io.EOFException e) {
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Unable to read Package Manager state file: " + e2);
        }
    }

    private android.content.ComponentName getPreferredHomeComponent() {
        return this.mPackageManager.getHomeActivities(new java.util.ArrayList());
    }

    private void writeStateFile(java.util.List<android.content.pm.PackageInfo> list, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor())));
        try {
            dataOutputStream.writeUTF(STATE_FILE_HEADER);
            dataOutputStream.writeInt(2);
            dataOutputStream.writeUTF(GLOBAL_METADATA_KEY);
            dataOutputStream.writeInt(android.os.Build.VERSION.SDK_INT);
            dataOutputStream.writeUTF(android.os.Build.VERSION.INCREMENTAL);
            for (android.content.pm.PackageInfo packageInfo : list) {
                dataOutputStream.writeUTF(packageInfo.packageName);
                if (packageInfo.versionCodeMajor != 0) {
                    dataOutputStream.writeInt(Integer.MIN_VALUE);
                    dataOutputStream.writeLong(packageInfo.getLongVersionCode());
                } else {
                    dataOutputStream.writeInt(packageInfo.versionCode);
                }
            }
            dataOutputStream.flush();
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Unable to write package manager state file!");
        }
    }

    private class LegacyRestoreDataConsumer implements com.android.server.backup.PackageManagerBackupAgent.RestoreDataConsumer {
        private LegacyRestoreDataConsumer() {
        }

        @Override // com.android.server.backup.PackageManagerBackupAgent.RestoreDataConsumer
        public void consumeRestoreData(android.app.backup.BackupDataInput backupDataInput) throws java.io.IOException {
            long j;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.HashMap hashMap = new java.util.HashMap();
            while (true) {
                java.lang.String key = backupDataInput.getKey();
                int dataSize = backupDataInput.getDataSize();
                byte[] bArr = new byte[dataSize];
                backupDataInput.readEntityData(bArr, 0, dataSize);
                java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
                if (key.equals(com.android.server.backup.PackageManagerBackupAgent.GLOBAL_METADATA_KEY)) {
                    com.android.server.backup.PackageManagerBackupAgent.this.mStoredSdkVersion = dataInputStream.readInt();
                    com.android.server.backup.PackageManagerBackupAgent.this.mStoredIncrementalVersion = dataInputStream.readUTF();
                    com.android.server.backup.PackageManagerBackupAgent.this.mHasMetadata = true;
                } else if (key.equals(com.android.server.backup.PackageManagerBackupAgent.DEFAULT_HOME_KEY)) {
                    com.android.server.backup.PackageManagerBackupAgent.this.mRestoredHome = android.content.ComponentName.unflattenFromString(dataInputStream.readUTF());
                    com.android.server.backup.PackageManagerBackupAgent.this.mRestoredHomeVersion = dataInputStream.readLong();
                    com.android.server.backup.PackageManagerBackupAgent.this.mRestoredHomeInstaller = dataInputStream.readUTF();
                    com.android.server.backup.PackageManagerBackupAgent.this.mRestoredHomeSigHashes = com.android.server.backup.PackageManagerBackupAgent.readSignatureHashArray(dataInputStream);
                } else {
                    int readInt = dataInputStream.readInt();
                    if (readInt == Integer.MIN_VALUE) {
                        j = dataInputStream.readLong();
                    } else {
                        j = readInt;
                    }
                    java.util.ArrayList readSignatureHashArray = com.android.server.backup.PackageManagerBackupAgent.readSignatureHashArray(dataInputStream);
                    if (readSignatureHashArray == null || readSignatureHashArray.size() == 0) {
                        android.util.Slog.w(com.android.server.backup.PackageManagerBackupAgent.TAG, "Not restoring package " + key + " since it appears to have no signatures.");
                    } else {
                        android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo();
                        applicationInfo.packageName = key;
                        arrayList.add(applicationInfo);
                        hashMap.put(key, com.android.server.backup.PackageManagerBackupAgent.this.new Metadata(j, readSignatureHashArray));
                    }
                }
                if (!backupDataInput.readNextHeader()) {
                    com.android.server.backup.PackageManagerBackupAgent.this.mRestoredSignatures = hashMap;
                    return;
                }
            }
        }
    }

    private class AncestralVersion1RestoreDataConsumer implements com.android.server.backup.PackageManagerBackupAgent.RestoreDataConsumer {
        private AncestralVersion1RestoreDataConsumer() {
        }

        @Override // com.android.server.backup.PackageManagerBackupAgent.RestoreDataConsumer
        public void consumeRestoreData(android.app.backup.BackupDataInput backupDataInput) throws java.io.IOException {
            long j;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.HashMap hashMap = new java.util.HashMap();
            while (backupDataInput.readNextHeader()) {
                java.lang.String key = backupDataInput.getKey();
                int dataSize = backupDataInput.getDataSize();
                byte[] bArr = new byte[dataSize];
                backupDataInput.readEntityData(bArr, 0, dataSize);
                java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
                if (key.equals(com.android.server.backup.PackageManagerBackupAgent.GLOBAL_METADATA_KEY)) {
                    com.android.server.backup.PackageManagerBackupAgent.this.mStoredSdkVersion = dataInputStream.readInt();
                    com.android.server.backup.PackageManagerBackupAgent.this.mStoredIncrementalVersion = dataInputStream.readUTF();
                    com.android.server.backup.PackageManagerBackupAgent.this.mHasMetadata = true;
                } else if (key.equals(com.android.server.backup.PackageManagerBackupAgent.DEFAULT_HOME_KEY)) {
                    com.android.server.backup.PackageManagerBackupAgent.this.mRestoredHome = android.content.ComponentName.unflattenFromString(dataInputStream.readUTF());
                    com.android.server.backup.PackageManagerBackupAgent.this.mRestoredHomeVersion = dataInputStream.readLong();
                    com.android.server.backup.PackageManagerBackupAgent.this.mRestoredHomeInstaller = dataInputStream.readUTF();
                    com.android.server.backup.PackageManagerBackupAgent.this.mRestoredHomeSigHashes = com.android.server.backup.PackageManagerBackupAgent.readSignatureHashArray(dataInputStream);
                } else {
                    int readInt = dataInputStream.readInt();
                    if (readInt == Integer.MIN_VALUE) {
                        j = dataInputStream.readLong();
                    } else {
                        j = readInt;
                    }
                    java.util.ArrayList readSignatureHashArray = com.android.server.backup.PackageManagerBackupAgent.readSignatureHashArray(dataInputStream);
                    if (readSignatureHashArray == null || readSignatureHashArray.size() == 0) {
                        android.util.Slog.w(com.android.server.backup.PackageManagerBackupAgent.TAG, "Not restoring package " + key + " since it appears to have no signatures.");
                    } else {
                        android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo();
                        applicationInfo.packageName = key;
                        arrayList.add(applicationInfo);
                        hashMap.put(key, com.android.server.backup.PackageManagerBackupAgent.this.new Metadata(j, readSignatureHashArray));
                    }
                }
            }
            com.android.server.backup.PackageManagerBackupAgent.this.mRestoredSignatures = hashMap;
        }
    }
}
