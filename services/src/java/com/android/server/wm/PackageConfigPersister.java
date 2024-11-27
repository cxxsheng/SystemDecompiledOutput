package com.android.server.wm;

/* loaded from: classes3.dex */
public class PackageConfigPersister {
    private static final java.lang.String ATTR_LOCALES = "locale_list";
    private static final java.lang.String ATTR_NIGHT_MODE = "night_mode";
    private static final java.lang.String ATTR_PACKAGE_NAME = "package_name";
    private static final boolean DEBUG = false;
    private static final java.lang.String PACKAGE_DIRNAME = "package_configs";
    private static final java.lang.String SUFFIX_FILE_NAME = "_config.xml";
    private static final java.lang.String TAG = com.android.server.wm.PackageConfigPersister.class.getSimpleName();
    private static final java.lang.String TAG_CONFIG = "config";
    private final com.android.server.wm.ActivityTaskManagerService mAtm;
    private final com.android.server.wm.PersisterQueue mPersisterQueue;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.server.wm.PackageConfigPersister.PackageConfigRecord>> mPendingWrite = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.server.wm.PackageConfigPersister.PackageConfigRecord>> mModified = new android.util.SparseArray<>();

    /* JADX INFO: Access modifiers changed from: private */
    public static java.io.File getUserConfigsDir(int i) {
        return new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), PACKAGE_DIRNAME);
    }

    PackageConfigPersister(com.android.server.wm.PersisterQueue persisterQueue, com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        this.mPersisterQueue = persisterQueue;
        this.mAtm = activityTaskManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void loadUserPackages(int i) {
        char c;
        synchronized (this.mLock) {
            try {
                java.io.File userConfigsDir = getUserConfigsDir(i);
                java.io.File[] listFiles = userConfigsDir.listFiles();
                if (listFiles == null) {
                    android.util.Slog.v(TAG, "loadPackages: empty list files from " + userConfigsDir);
                    return;
                }
                for (java.io.File file : listFiles) {
                    if (file.getName().endsWith(SUFFIX_FILE_NAME)) {
                        try {
                            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
                            try {
                                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                                java.lang.String str = null;
                                java.lang.Integer num = null;
                                android.os.LocaleList localeList = null;
                                while (true) {
                                    int next = resolvePullParser.next();
                                    if (next != 1 && next != 3) {
                                        java.lang.String name = resolvePullParser.getName();
                                        if (next == 2 && TAG_CONFIG.equals(name)) {
                                            for (int attributeCount = resolvePullParser.getAttributeCount() - 1; attributeCount >= 0; attributeCount--) {
                                                java.lang.String attributeName = resolvePullParser.getAttributeName(attributeCount);
                                                java.lang.String attributeValue = resolvePullParser.getAttributeValue(attributeCount);
                                                switch (attributeName.hashCode()) {
                                                    case -1877165340:
                                                        if (attributeName.equals(ATTR_PACKAGE_NAME)) {
                                                            c = 0;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    case -601793174:
                                                        if (attributeName.equals(ATTR_NIGHT_MODE)) {
                                                            c = 1;
                                                            break;
                                                        }
                                                        c = 65535;
                                                        break;
                                                    case 1912882019:
                                                        if (attributeName.equals(ATTR_LOCALES)) {
                                                            c = 2;
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
                                                        str = attributeValue;
                                                        break;
                                                    case 1:
                                                        num = java.lang.Integer.valueOf(java.lang.Integer.parseInt(attributeValue));
                                                        break;
                                                    case 2:
                                                        localeList = android.os.LocaleList.forLanguageTags(attributeValue);
                                                        break;
                                                }
                                            }
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(resolvePullParser);
                                    }
                                }
                                if (str != null) {
                                    try {
                                        com.android.server.wm.PackageConfigPersister.PackageConfigRecord findRecordOrCreate = findRecordOrCreate(this.mModified, str, i);
                                        findRecordOrCreate.mNightMode = num;
                                        findRecordOrCreate.mLocales = localeList;
                                    } catch (java.lang.Throwable th) {
                                        th = th;
                                        java.lang.Throwable th2 = th;
                                        try {
                                            fileInputStream.close();
                                        } catch (java.lang.Throwable th3) {
                                            th2.addSuppressed(th3);
                                        }
                                        throw th2;
                                    }
                                }
                                try {
                                    fileInputStream.close();
                                } catch (java.io.FileNotFoundException e) {
                                    e = e;
                                    e.printStackTrace();
                                } catch (java.io.IOException e2) {
                                    e = e2;
                                    e.printStackTrace();
                                } catch (org.xmlpull.v1.XmlPullParserException e3) {
                                    e = e3;
                                    e.printStackTrace();
                                }
                            } catch (java.lang.Throwable th4) {
                                th = th4;
                            }
                        } catch (java.io.FileNotFoundException e4) {
                            e = e4;
                        } catch (java.io.IOException e5) {
                            e = e5;
                        } catch (org.xmlpull.v1.XmlPullParserException e6) {
                            e = e6;
                        }
                    }
                }
            } finally {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void updateConfigIfNeeded(@android.annotation.NonNull com.android.server.wm.ConfigurationContainer configurationContainer, int i, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                com.android.server.wm.PackageConfigPersister.PackageConfigRecord findRecord = findRecord(this.mModified, str, i);
                if (findRecord != null) {
                    configurationContainer.applyAppSpecificConfig(findRecord.mNightMode, com.android.server.wm.LocaleOverlayHelper.combineLocalesIfOverlayExists(findRecord.mLocales, this.mAtm.getGlobalConfiguration().getLocales()), findRecord.mGrammaticalGender);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean updateFromImpl(java.lang.String str, int i, com.android.server.wm.PackageConfigurationUpdaterImpl packageConfigurationUpdaterImpl) {
        boolean z;
        synchronized (this.mLock) {
            try {
                com.android.server.wm.PackageConfigPersister.PackageConfigRecord findRecord = findRecord(this.mModified, str, i);
                if (findRecord != null) {
                    z = true;
                } else {
                    findRecord = findRecordOrCreate(this.mModified, str, i);
                    z = false;
                }
                boolean updateNightMode = updateNightMode(packageConfigurationUpdaterImpl.getNightMode(), findRecord);
                boolean updateLocales = updateLocales(packageConfigurationUpdaterImpl.getLocales(), findRecord);
                boolean updateGender = updateGender(packageConfigurationUpdaterImpl.getGrammaticalGender(), findRecord);
                if ((findRecord.mNightMode != null && !findRecord.isResetNightMode()) || ((findRecord.mLocales != null && !findRecord.mLocales.isEmpty()) || (findRecord.mGrammaticalGender != null && findRecord.mGrammaticalGender.intValue() != 0))) {
                    if (!updateNightMode && !updateLocales && !updateGender) {
                        return false;
                    }
                    com.android.server.wm.PackageConfigPersister.PackageConfigRecord findRecord2 = findRecord(this.mPendingWrite, findRecord.mName, findRecord.mUserId);
                    if (findRecord2 == null) {
                        findRecord2 = findRecordOrCreate(this.mPendingWrite, findRecord.mName, findRecord.mUserId);
                    }
                    if (!updateNightMode(findRecord.mNightMode, findRecord2) && !updateLocales(findRecord.mLocales, findRecord2) && !updateGender(findRecord.mGrammaticalGender, findRecord2)) {
                        return false;
                    }
                    this.mPersisterQueue.addItem(new com.android.server.wm.PackageConfigPersister.WriteProcessItem(findRecord2), false);
                    return true;
                }
                removePackage(str, i);
                return z;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean updateNightMode(java.lang.Integer num, com.android.server.wm.PackageConfigPersister.PackageConfigRecord packageConfigRecord) {
        if (num == null || num.equals(packageConfigRecord.mNightMode)) {
            return false;
        }
        packageConfigRecord.mNightMode = num;
        return true;
    }

    private boolean updateLocales(android.os.LocaleList localeList, com.android.server.wm.PackageConfigPersister.PackageConfigRecord packageConfigRecord) {
        if (localeList == null || localeList.equals(packageConfigRecord.mLocales)) {
            return false;
        }
        packageConfigRecord.mLocales = localeList;
        return true;
    }

    private boolean updateGender(java.lang.Integer num, com.android.server.wm.PackageConfigPersister.PackageConfigRecord packageConfigRecord) {
        if (num == null || num.equals(packageConfigRecord.mGrammaticalGender)) {
            return false;
        }
        packageConfigRecord.mGrammaticalGender = num;
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void removeUser(int i) {
        synchronized (this.mLock) {
            try {
                java.util.HashMap<java.lang.String, com.android.server.wm.PackageConfigPersister.PackageConfigRecord> hashMap = this.mModified.get(i);
                java.util.HashMap<java.lang.String, com.android.server.wm.PackageConfigPersister.PackageConfigRecord> hashMap2 = this.mPendingWrite.get(i);
                if ((hashMap != null && hashMap.size() != 0) || (hashMap2 != null && hashMap2.size() != 0)) {
                    new java.util.HashMap(hashMap).forEach(new java.util.function.BiConsumer() { // from class: com.android.server.wm.PackageConfigPersister$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            com.android.server.wm.PackageConfigPersister.this.lambda$removeUser$0((java.lang.String) obj, (com.android.server.wm.PackageConfigPersister.PackageConfigRecord) obj2);
                        }
                    });
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeUser$0(java.lang.String str, com.android.server.wm.PackageConfigPersister.PackageConfigRecord packageConfigRecord) {
        removePackage(packageConfigRecord.mName, packageConfigRecord.mUserId);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onPackageUninstall(java.lang.String str, int i) {
        synchronized (this.mLock) {
            removePackage(str, i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onPackageDataCleared(java.lang.String str, int i) {
        synchronized (this.mLock) {
            removePackage(str, i);
        }
    }

    private void removePackage(java.lang.String str, int i) {
        final com.android.server.wm.PackageConfigPersister.PackageConfigRecord findRecord = findRecord(this.mPendingWrite, str, i);
        if (findRecord != null) {
            removeRecord(this.mPendingWrite, findRecord);
            this.mPersisterQueue.removeItems(new java.util.function.Predicate() { // from class: com.android.server.wm.PackageConfigPersister$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removePackage$1;
                    lambda$removePackage$1 = com.android.server.wm.PackageConfigPersister.lambda$removePackage$1(com.android.server.wm.PackageConfigPersister.PackageConfigRecord.this, (com.android.server.wm.PackageConfigPersister.WriteProcessItem) obj);
                    return lambda$removePackage$1;
                }
            }, com.android.server.wm.PackageConfigPersister.WriteProcessItem.class);
        }
        com.android.server.wm.PackageConfigPersister.PackageConfigRecord findRecord2 = findRecord(this.mModified, str, i);
        if (findRecord2 != null) {
            removeRecord(this.mModified, findRecord2);
            this.mPersisterQueue.addItem(new com.android.server.wm.PackageConfigPersister.DeletePackageItem(i, str), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removePackage$1(com.android.server.wm.PackageConfigPersister.PackageConfigRecord packageConfigRecord, com.android.server.wm.PackageConfigPersister.WriteProcessItem writeProcessItem) {
        return writeProcessItem.mRecord.mName == packageConfigRecord.mName && writeProcessItem.mRecord.mUserId == packageConfigRecord.mUserId;
    }

    com.android.server.wm.ActivityTaskManagerInternal.PackageConfig findPackageConfiguration(java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.wm.PackageConfigPersister.PackageConfigRecord findRecord = findRecord(this.mModified, str, i);
                if (findRecord == null) {
                    android.util.Slog.w(TAG, "App-specific configuration not found for packageName: " + str + " and userId: " + i);
                    return null;
                }
                return new com.android.server.wm.ActivityTaskManagerInternal.PackageConfig(findRecord.mNightMode, findRecord.mLocales, findRecord.mGrammaticalGender);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dump(java.io.PrintWriter printWriter, int i) {
        printWriter.println("INSTALLED PACKAGES HAVING APP-SPECIFIC CONFIGURATIONS");
        printWriter.println("Current user ID : " + i);
        synchronized (this.mLock) {
            try {
                java.util.HashMap<java.lang.String, com.android.server.wm.PackageConfigPersister.PackageConfigRecord> hashMap = this.mModified.get(i);
                if (hashMap != null) {
                    for (com.android.server.wm.PackageConfigPersister.PackageConfigRecord packageConfigRecord : hashMap.values()) {
                        printWriter.println();
                        printWriter.println("    PackageName : " + packageConfigRecord.mName);
                        printWriter.println("        NightMode : " + packageConfigRecord.mNightMode);
                        printWriter.println("        Locales : " + packageConfigRecord.mLocales);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    static class PackageConfigRecord {
        java.lang.Integer mGrammaticalGender;
        android.os.LocaleList mLocales;
        final java.lang.String mName;
        java.lang.Integer mNightMode;
        final int mUserId;

        PackageConfigRecord(java.lang.String str, int i) {
            this.mName = str;
            this.mUserId = i;
        }

        boolean isResetNightMode() {
            return this.mNightMode.intValue() == 0;
        }

        public java.lang.String toString() {
            return "PackageConfigRecord package name: " + this.mName + " userId " + this.mUserId + " nightMode " + this.mNightMode + " locales " + this.mLocales;
        }
    }

    private com.android.server.wm.PackageConfigPersister.PackageConfigRecord findRecordOrCreate(android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.server.wm.PackageConfigPersister.PackageConfigRecord>> sparseArray, java.lang.String str, int i) {
        java.util.HashMap<java.lang.String, com.android.server.wm.PackageConfigPersister.PackageConfigRecord> hashMap = sparseArray.get(i);
        if (hashMap == null) {
            hashMap = new java.util.HashMap<>();
            sparseArray.put(i, hashMap);
        }
        com.android.server.wm.PackageConfigPersister.PackageConfigRecord packageConfigRecord = hashMap.get(str);
        if (packageConfigRecord != null) {
            return packageConfigRecord;
        }
        com.android.server.wm.PackageConfigPersister.PackageConfigRecord packageConfigRecord2 = new com.android.server.wm.PackageConfigPersister.PackageConfigRecord(str, i);
        hashMap.put(str, packageConfigRecord2);
        return packageConfigRecord2;
    }

    private com.android.server.wm.PackageConfigPersister.PackageConfigRecord findRecord(android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.server.wm.PackageConfigPersister.PackageConfigRecord>> sparseArray, java.lang.String str, int i) {
        java.util.HashMap<java.lang.String, com.android.server.wm.PackageConfigPersister.PackageConfigRecord> hashMap = sparseArray.get(i);
        if (hashMap == null) {
            return null;
        }
        return hashMap.get(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeRecord(android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.server.wm.PackageConfigPersister.PackageConfigRecord>> sparseArray, com.android.server.wm.PackageConfigPersister.PackageConfigRecord packageConfigRecord) {
        java.util.HashMap<java.lang.String, com.android.server.wm.PackageConfigPersister.PackageConfigRecord> hashMap = sparseArray.get(packageConfigRecord.mUserId);
        if (hashMap != null) {
            hashMap.remove(packageConfigRecord.mName);
        }
    }

    private static class DeletePackageItem implements com.android.server.wm.PersisterQueue.WriteQueueItem {
        final java.lang.String mPackageName;
        final int mUserId;

        DeletePackageItem(int i, java.lang.String str) {
            this.mUserId = i;
            this.mPackageName = str;
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public void process() {
            java.io.File userConfigsDir = com.android.server.wm.PackageConfigPersister.getUserConfigsDir(this.mUserId);
            if (!userConfigsDir.isDirectory()) {
                return;
            }
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(userConfigsDir, this.mPackageName + com.android.server.wm.PackageConfigPersister.SUFFIX_FILE_NAME));
            if (atomicFile.exists()) {
                atomicFile.delete();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class WriteProcessItem implements com.android.server.wm.PersisterQueue.WriteQueueItem {
        final com.android.server.wm.PackageConfigPersister.PackageConfigRecord mRecord;

        WriteProcessItem(com.android.server.wm.PackageConfigPersister.PackageConfigRecord packageConfigRecord) {
            this.mRecord = packageConfigRecord;
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public void process() {
            java.io.FileOutputStream fileOutputStream;
            byte[] bArr;
            android.util.AtomicFile atomicFile;
            synchronized (com.android.server.wm.PackageConfigPersister.this.mLock) {
                fileOutputStream = null;
                try {
                    bArr = saveToXml();
                } catch (java.lang.Exception e) {
                    bArr = null;
                }
                com.android.server.wm.PackageConfigPersister.this.removeRecord(com.android.server.wm.PackageConfigPersister.this.mPendingWrite, this.mRecord);
            }
            if (bArr != null) {
                try {
                    java.io.File userConfigsDir = com.android.server.wm.PackageConfigPersister.getUserConfigsDir(this.mRecord.mUserId);
                    if (!userConfigsDir.isDirectory() && !userConfigsDir.mkdirs()) {
                        android.util.Slog.e(com.android.server.wm.PackageConfigPersister.TAG, "Failure creating tasks directory for user " + this.mRecord.mUserId + ": " + userConfigsDir);
                        return;
                    }
                    atomicFile = new android.util.AtomicFile(new java.io.File(userConfigsDir, this.mRecord.mName + com.android.server.wm.PackageConfigPersister.SUFFIX_FILE_NAME));
                    try {
                        fileOutputStream = atomicFile.startWrite();
                        fileOutputStream.write(bArr);
                        atomicFile.finishWrite(fileOutputStream);
                    } catch (java.io.IOException e2) {
                        e = e2;
                        if (fileOutputStream != null) {
                            atomicFile.failWrite(fileOutputStream);
                        }
                        android.util.Slog.e(com.android.server.wm.PackageConfigPersister.TAG, "Unable to open " + atomicFile + " for persisting. " + e);
                    }
                } catch (java.io.IOException e3) {
                    e = e3;
                    atomicFile = null;
                }
            }
        }

        private byte[] saveToXml() throws java.io.IOException {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(byteArrayOutputStream);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.startTag((java.lang.String) null, com.android.server.wm.PackageConfigPersister.TAG_CONFIG);
            resolveSerializer.attribute((java.lang.String) null, com.android.server.wm.PackageConfigPersister.ATTR_PACKAGE_NAME, this.mRecord.mName);
            if (this.mRecord.mNightMode != null) {
                resolveSerializer.attributeInt((java.lang.String) null, com.android.server.wm.PackageConfigPersister.ATTR_NIGHT_MODE, this.mRecord.mNightMode.intValue());
            }
            if (this.mRecord.mLocales != null) {
                resolveSerializer.attribute((java.lang.String) null, com.android.server.wm.PackageConfigPersister.ATTR_LOCALES, this.mRecord.mLocales.toLanguageTags());
            }
            resolveSerializer.endTag((java.lang.String) null, com.android.server.wm.PackageConfigPersister.TAG_CONFIG);
            resolveSerializer.endDocument();
            resolveSerializer.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }
}
