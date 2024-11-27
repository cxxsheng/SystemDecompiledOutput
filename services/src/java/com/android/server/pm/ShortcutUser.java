package com.android.server.pm;

/* loaded from: classes2.dex */
class ShortcutUser {
    private static final java.lang.String ATTR_KNOWN_LOCALES = "locales";
    private static final java.lang.String ATTR_LAST_APP_SCAN_OS_FINGERPRINT = "last-app-scan-fp";
    private static final java.lang.String ATTR_LAST_APP_SCAN_TIME = "last-app-scan-time2";
    private static final java.lang.String ATTR_RESTORE_SOURCE_FINGERPRINT = "restore-from-fp";
    private static final java.lang.String ATTR_VALUE = "value";
    static final java.lang.String DIRECTORY_LUANCHERS = "launchers";
    static final java.lang.String DIRECTORY_PACKAGES = "packages";
    private static final java.lang.String KEY_LAUNCHERS = "launchers";
    private static final java.lang.String KEY_PACKAGES = "packages";
    private static final java.lang.String KEY_USER_ID = "userId";
    private static final java.lang.String TAG = "ShortcutService";
    private static final java.lang.String TAG_LAUNCHER = "launcher";
    static final java.lang.String TAG_ROOT = "user";
    final android.app.appsearch.AppSearchManager mAppSearchManager;
    private java.lang.String mCachedLauncher;
    private java.lang.String mKnownLocales;
    private java.lang.String mLastAppScanOsFingerprint;
    private long mLastAppScanTime;
    private java.lang.String mRestoreFromOsFingerprint;
    final com.android.server.pm.ShortcutService mService;
    private final int mUserId;
    private final android.util.ArrayMap<java.lang.String, com.android.server.pm.ShortcutPackage> mPackages = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.content.pm.UserPackage, com.android.server.pm.ShortcutLauncher> mLaunchers = new android.util.ArrayMap<>();
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.internal.infra.AndroidFuture<android.app.appsearch.AppSearchSession>> mInFlightSessions = new java.util.ArrayList<>();
    final java.util.concurrent.Executor mExecutor = com.android.server.FgThread.getExecutor();

    public ShortcutUser(com.android.server.pm.ShortcutService shortcutService, int i) {
        this.mService = shortcutService;
        this.mUserId = i;
        this.mAppSearchManager = (android.app.appsearch.AppSearchManager) shortcutService.mContext.createContextAsUser(android.os.UserHandle.of(i), 0).getSystemService(android.app.appsearch.AppSearchManager.class);
    }

    public int getUserId() {
        return this.mUserId;
    }

    public long getLastAppScanTime() {
        return this.mLastAppScanTime;
    }

    public void setLastAppScanTime(long j) {
        this.mLastAppScanTime = j;
    }

    public java.lang.String getLastAppScanOsFingerprint() {
        return this.mLastAppScanOsFingerprint;
    }

    public void setLastAppScanOsFingerprint(java.lang.String str) {
        this.mLastAppScanOsFingerprint = str;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.util.ArrayMap<java.lang.String, com.android.server.pm.ShortcutPackage> getAllPackagesForTest() {
        return this.mPackages;
    }

    public boolean hasPackage(@android.annotation.NonNull java.lang.String str) {
        return this.mPackages.containsKey(str);
    }

    private void addPackage(@android.annotation.NonNull com.android.server.pm.ShortcutPackage shortcutPackage) {
        shortcutPackage.replaceUser(this);
        this.mPackages.put(shortcutPackage.getPackageName(), shortcutPackage);
    }

    public com.android.server.pm.ShortcutPackage removePackage(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.ShortcutPackage remove = this.mPackages.remove(str);
        if (remove != null) {
            remove.removeAllShortcutsAsync();
        }
        this.mService.cleanupBitmapsForPackage(this.mUserId, str);
        return remove;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.util.ArrayMap<android.content.pm.UserPackage, com.android.server.pm.ShortcutLauncher> getAllLaunchersForTest() {
        return this.mLaunchers;
    }

    private void addLauncher(com.android.server.pm.ShortcutLauncher shortcutLauncher) {
        shortcutLauncher.replaceUser(this);
        this.mLaunchers.put(android.content.pm.UserPackage.of(shortcutLauncher.getPackageUserId(), shortcutLauncher.getPackageName()), shortcutLauncher);
    }

    @android.annotation.Nullable
    public com.android.server.pm.ShortcutLauncher removeLauncher(int i, @android.annotation.NonNull java.lang.String str) {
        return this.mLaunchers.remove(android.content.pm.UserPackage.of(i, str));
    }

    @android.annotation.Nullable
    public com.android.server.pm.ShortcutPackage getPackageShortcutsIfExists(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.ShortcutPackage shortcutPackage = this.mPackages.get(str);
        if (shortcutPackage != null) {
            shortcutPackage.attemptToRestoreIfNeededAndSave();
        }
        return shortcutPackage;
    }

    @android.annotation.NonNull
    public com.android.server.pm.ShortcutPackage getPackageShortcuts(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.ShortcutPackage packageShortcutsIfExists = getPackageShortcutsIfExists(str);
        if (packageShortcutsIfExists == null) {
            com.android.server.pm.ShortcutPackage shortcutPackage = new com.android.server.pm.ShortcutPackage(this, this.mUserId, str);
            this.mPackages.put(str, shortcutPackage);
            return shortcutPackage;
        }
        return packageShortcutsIfExists;
    }

    @android.annotation.NonNull
    public com.android.server.pm.ShortcutLauncher getLauncherShortcuts(@android.annotation.NonNull java.lang.String str, int i) {
        android.content.pm.UserPackage of = android.content.pm.UserPackage.of(i, str);
        com.android.server.pm.ShortcutLauncher shortcutLauncher = this.mLaunchers.get(of);
        if (shortcutLauncher == null) {
            shortcutLauncher = new com.android.server.pm.ShortcutLauncher(this, this.mUserId, str, i);
            this.mLaunchers.put(of, shortcutLauncher);
        }
        shortcutLauncher.attemptToRestoreIfNeededAndSave();
        return shortcutLauncher;
    }

    public void forAllPackages(java.util.function.Consumer<? super com.android.server.pm.ShortcutPackage> consumer) {
        int size = this.mPackages.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(this.mPackages.valueAt(i));
        }
    }

    public void forAllLaunchers(java.util.function.Consumer<? super com.android.server.pm.ShortcutLauncher> consumer) {
        int size = this.mLaunchers.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(this.mLaunchers.valueAt(i));
        }
    }

    public void forAllPackageItems(java.util.function.Consumer<? super com.android.server.pm.ShortcutPackageItem> consumer) {
        forAllLaunchers(consumer);
        forAllPackages(consumer);
    }

    public void forPackageItem(@android.annotation.NonNull final java.lang.String str, final int i, final java.util.function.Consumer<com.android.server.pm.ShortcutPackageItem> consumer) {
        forAllPackageItems(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutUser.lambda$forPackageItem$0(i, str, consumer, (com.android.server.pm.ShortcutPackageItem) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$forPackageItem$0(int i, java.lang.String str, java.util.function.Consumer consumer, com.android.server.pm.ShortcutPackageItem shortcutPackageItem) {
        if (shortcutPackageItem.getPackageUserId() == i && shortcutPackageItem.getPackageName().equals(str)) {
            consumer.accept(shortcutPackageItem);
        }
    }

    public void onCalledByPublisher(@android.annotation.NonNull java.lang.String str) {
        detectLocaleChange();
        rescanPackageIfNeeded(str, false);
    }

    private java.lang.String getKnownLocales() {
        if (android.text.TextUtils.isEmpty(this.mKnownLocales)) {
            this.mKnownLocales = this.mService.injectGetLocaleTagsForUser(this.mUserId);
            this.mService.scheduleSaveUser(this.mUserId);
        }
        return this.mKnownLocales;
    }

    public void detectLocaleChange() {
        java.lang.String injectGetLocaleTagsForUser = this.mService.injectGetLocaleTagsForUser(this.mUserId);
        if (!android.text.TextUtils.isEmpty(this.mKnownLocales) && this.mKnownLocales.equals(injectGetLocaleTagsForUser)) {
            return;
        }
        this.mKnownLocales = injectGetLocaleTagsForUser;
        forAllPackages(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutUser.lambda$detectLocaleChange$1((com.android.server.pm.ShortcutPackage) obj);
            }
        });
        this.mService.scheduleSaveUser(this.mUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$detectLocaleChange$1(com.android.server.pm.ShortcutPackage shortcutPackage) {
        shortcutPackage.resetRateLimiting();
        shortcutPackage.resolveResourceStrings();
    }

    public void rescanPackageIfNeeded(@android.annotation.NonNull java.lang.String str, boolean z) {
        boolean z2 = !this.mPackages.containsKey(str);
        if (!getPackageShortcuts(str).rescanPackageIfNeeded(z2, z) && z2) {
            this.mPackages.remove(str);
        }
    }

    public void attemptToRestoreIfNeededAndSave(com.android.server.pm.ShortcutService shortcutService, @android.annotation.NonNull java.lang.String str, int i) {
        forPackageItem(str, i, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.pm.ShortcutPackageItem) obj).attemptToRestoreIfNeededAndSave();
            }
        });
    }

    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        typedXmlSerializer.startTag((java.lang.String) null, TAG_ROOT);
        if (!z) {
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_KNOWN_LOCALES, this.mKnownLocales);
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_LAST_APP_SCAN_TIME, this.mLastAppScanTime);
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_LAST_APP_SCAN_OS_FINGERPRINT, this.mLastAppScanOsFingerprint);
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_RESTORE_SOURCE_FINGERPRINT, this.mRestoreFromOsFingerprint);
        } else {
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_RESTORE_SOURCE_FINGERPRINT, this.mService.injectBuildFingerprint());
        }
        if (!z) {
            java.io.File injectUserDataPath = this.mService.injectUserDataPath(this.mUserId);
            android.os.FileUtils.deleteContents(new java.io.File(injectUserDataPath, "packages"));
            android.os.FileUtils.deleteContents(new java.io.File(injectUserDataPath, "launchers"));
        }
        int size = this.mLaunchers.size();
        for (int i = 0; i < size; i++) {
            saveShortcutPackageItem(typedXmlSerializer, this.mLaunchers.valueAt(i), z);
        }
        int size2 = this.mPackages.size();
        for (int i2 = 0; i2 < size2; i2++) {
            saveShortcutPackageItem(typedXmlSerializer, this.mPackages.valueAt(i2), z);
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_ROOT);
    }

    private void saveShortcutPackageItem(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.pm.ShortcutPackageItem shortcutPackageItem, boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        if (z) {
            if (shortcutPackageItem.getPackageUserId() != shortcutPackageItem.getOwnerUserId()) {
                return;
            }
            shortcutPackageItem.waitForBitmapSaves();
            shortcutPackageItem.saveToXml(typedXmlSerializer, z);
            return;
        }
        shortcutPackageItem.scheduleSave();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static com.android.server.pm.ShortcutUser loadFromXml(final com.android.server.pm.ShortcutService shortcutService, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, final int i, final boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, com.android.server.pm.ShortcutService.InvalidFileFormatException {
        char c;
        final com.android.server.pm.ShortcutUser shortcutUser = new com.android.server.pm.ShortcutUser(shortcutService, i);
        try {
            shortcutUser.mKnownLocales = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_KNOWN_LOCALES);
            long parseLongAttribute = com.android.server.pm.ShortcutService.parseLongAttribute(typedXmlPullParser, ATTR_LAST_APP_SCAN_TIME);
            if (parseLongAttribute >= shortcutService.injectCurrentTimeMillis()) {
                parseLongAttribute = 0;
            }
            shortcutUser.mLastAppScanTime = parseLongAttribute;
            shortcutUser.mLastAppScanOsFingerprint = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_LAST_APP_SCAN_OS_FINGERPRINT);
            shortcutUser.mRestoreFromOsFingerprint = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_RESTORE_SOURCE_FINGERPRINT);
            int depth = typedXmlPullParser.getDepth();
            boolean z2 = false;
            while (true) {
                int next = typedXmlPullParser.next();
                if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                }
                if (next == 2) {
                    int depth2 = typedXmlPullParser.getDepth();
                    java.lang.String name = typedXmlPullParser.getName();
                    if (depth2 == depth + 1) {
                        switch (name.hashCode()) {
                            case -1146595445:
                                if (name.equals("launcher-pins")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -807062458:
                                if (name.equals(com.android.server.pm.Settings.ATTR_PACKAGE)) {
                                    c = 0;
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
                                com.android.server.pm.ShortcutPackage loadFromXml = com.android.server.pm.ShortcutPackage.loadFromXml(shortcutService, shortcutUser, typedXmlPullParser, z);
                                shortcutUser.mPackages.put(loadFromXml.getPackageName(), loadFromXml);
                                z2 = true;
                                continue;
                            case 1:
                                shortcutUser.addLauncher(com.android.server.pm.ShortcutLauncher.loadFromXml(typedXmlPullParser, shortcutUser, i, z));
                                z2 = true;
                                continue;
                        }
                    }
                    com.android.server.pm.ShortcutService.warnForInvalidTag(depth2, name);
                }
            }
            if (z2) {
                shortcutService.scheduleSaveUser(i);
            } else {
                java.io.File injectUserDataPath = shortcutService.injectUserDataPath(i);
                forMainFilesIn(new java.io.File(injectUserDataPath, "packages"), new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.pm.ShortcutUser.lambda$loadFromXml$3(com.android.server.pm.ShortcutService.this, shortcutUser, z, (java.io.File) obj);
                    }
                });
                forMainFilesIn(new java.io.File(injectUserDataPath, "launchers"), new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.pm.ShortcutUser.lambda$loadFromXml$4(com.android.server.pm.ShortcutUser.this, i, z, (java.io.File) obj);
                    }
                });
            }
            return shortcutUser;
        } catch (java.lang.RuntimeException e) {
            throw new com.android.server.pm.ShortcutService.InvalidFileFormatException("Unable to parse file", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$loadFromXml$3(com.android.server.pm.ShortcutService shortcutService, com.android.server.pm.ShortcutUser shortcutUser, boolean z, java.io.File file) {
        com.android.server.pm.ShortcutPackage loadFromFile = com.android.server.pm.ShortcutPackage.loadFromFile(shortcutService, shortcutUser, file, z);
        if (loadFromFile != null) {
            shortcutUser.mPackages.put(loadFromFile.getPackageName(), loadFromFile);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$loadFromXml$4(com.android.server.pm.ShortcutUser shortcutUser, int i, boolean z, java.io.File file) {
        com.android.server.pm.ShortcutLauncher loadFromFile = com.android.server.pm.ShortcutLauncher.loadFromFile(file, shortcutUser, i, z);
        if (loadFromFile != null) {
            shortcutUser.addLauncher(loadFromFile);
        }
    }

    private static void forMainFilesIn(java.io.File file, java.util.function.Consumer<java.io.File> consumer) {
        if (!file.exists()) {
            return;
        }
        for (java.io.File file2 : file.listFiles()) {
            if (!file2.getName().endsWith(".reservecopy") && !file2.getName().endsWith(".backup")) {
                consumer.accept(file2);
            }
        }
    }

    public void setCachedLauncher(java.lang.String str) {
        this.mCachedLauncher = str;
    }

    public java.lang.String getCachedLauncher() {
        return this.mCachedLauncher;
    }

    public void resetThrottling() {
        for (int size = this.mPackages.size() - 1; size >= 0; size--) {
            this.mPackages.valueAt(size).resetThrottling();
        }
    }

    public void mergeRestoredFile(com.android.server.pm.ShortcutUser shortcutUser) {
        final com.android.server.pm.ShortcutService shortcutService = this.mService;
        final int[] iArr = new int[1];
        final int[] iArr2 = new int[1];
        final int[] iArr3 = new int[1];
        this.mLaunchers.clear();
        shortcutUser.forAllLaunchers(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutUser.this.lambda$mergeRestoredFile$5(shortcutService, iArr, (com.android.server.pm.ShortcutLauncher) obj);
            }
        });
        shortcutUser.forAllPackages(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutUser.this.lambda$mergeRestoredFile$6(shortcutService, iArr2, iArr3, (com.android.server.pm.ShortcutPackage) obj);
            }
        });
        shortcutUser.mLaunchers.clear();
        shortcutUser.mPackages.clear();
        this.mRestoreFromOsFingerprint = shortcutUser.mRestoreFromOsFingerprint;
        android.util.Slog.i(TAG, "Restored: L=" + iArr[0] + " P=" + iArr2[0] + " S=" + iArr3[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mergeRestoredFile$5(com.android.server.pm.ShortcutService shortcutService, int[] iArr, com.android.server.pm.ShortcutLauncher shortcutLauncher) {
        if (shortcutService.isPackageInstalled(shortcutLauncher.getPackageName(), getUserId()) && !shortcutService.shouldBackupApp(shortcutLauncher.getPackageName(), getUserId())) {
            return;
        }
        addLauncher(shortcutLauncher);
        iArr[0] = iArr[0] + 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mergeRestoredFile$6(com.android.server.pm.ShortcutService shortcutService, int[] iArr, int[] iArr2, com.android.server.pm.ShortcutPackage shortcutPackage) {
        if (shortcutService.isPackageInstalled(shortcutPackage.getPackageName(), getUserId()) && !shortcutService.shouldBackupApp(shortcutPackage.getPackageName(), getUserId())) {
            return;
        }
        com.android.server.pm.ShortcutPackage packageShortcutsIfExists = getPackageShortcutsIfExists(shortcutPackage.getPackageName());
        if (packageShortcutsIfExists != null && packageShortcutsIfExists.hasNonManifestShortcuts()) {
            android.util.Log.w(TAG, "Shortcuts for package " + shortcutPackage.getPackageName() + " are being restored. Existing non-manifeset shortcuts will be overwritten.");
        }
        shortcutPackage.removeAllShortcutsAsync();
        addPackage(shortcutPackage);
        iArr[0] = iArr[0] + 1;
        iArr2[0] = iArr2[0] + shortcutPackage.getShortcutCount();
    }

    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, com.android.server.pm.ShortcutService.DumpFilter dumpFilter) {
        if (dumpFilter.shouldDumpDetails()) {
            printWriter.print(str);
            printWriter.print("User: ");
            printWriter.print(this.mUserId);
            printWriter.print("  Known locales: ");
            printWriter.print(this.mKnownLocales);
            printWriter.print("  Last app scan: [");
            printWriter.print(this.mLastAppScanTime);
            printWriter.print("] ");
            printWriter.println(com.android.server.pm.ShortcutService.formatTime(this.mLastAppScanTime));
            str = str + str + "  ";
            printWriter.print(str);
            printWriter.print("Last app scan FP: ");
            printWriter.println(this.mLastAppScanOsFingerprint);
            printWriter.print(str);
            printWriter.print("Restore from FP: ");
            printWriter.print(this.mRestoreFromOsFingerprint);
            printWriter.println();
            printWriter.print(str);
            printWriter.print("Cached launcher: ");
            printWriter.print(this.mCachedLauncher);
            printWriter.println();
        }
        for (int i = 0; i < this.mLaunchers.size(); i++) {
            com.android.server.pm.ShortcutLauncher valueAt = this.mLaunchers.valueAt(i);
            if (dumpFilter.isPackageMatch(valueAt.getPackageName())) {
                valueAt.dump(printWriter, str, dumpFilter);
            }
        }
        for (int i2 = 0; i2 < this.mPackages.size(); i2++) {
            com.android.server.pm.ShortcutPackage valueAt2 = this.mPackages.valueAt(i2);
            if (dumpFilter.isPackageMatch(valueAt2.getPackageName())) {
                valueAt2.dump(printWriter, str, dumpFilter);
            }
        }
        if (dumpFilter.shouldDumpDetails()) {
            printWriter.println();
            printWriter.print(str);
            printWriter.println("Bitmap directories: ");
            dumpDirectorySize(printWriter, str + "  ", this.mService.getUserBitmapFilePath(this.mUserId));
        }
    }

    private void dumpDirectorySize(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, java.io.File file) {
        int i = 0;
        long j = 0;
        if (file.listFiles() != null) {
            java.io.File[] listFiles = file.listFiles();
            int length = listFiles.length;
            long j2 = 0;
            int i2 = 0;
            while (i < length) {
                java.io.File file2 = listFiles[i];
                if (file2.isFile()) {
                    i2++;
                    j2 += file2.length();
                } else if (file2.isDirectory()) {
                    dumpDirectorySize(printWriter, str + "  ", file2);
                }
                i++;
            }
            i = i2;
            j = j2;
        }
        printWriter.print(str);
        printWriter.print("Path: ");
        printWriter.print(file.getName());
        printWriter.print("/ has ");
        printWriter.print(i);
        printWriter.print(" files, size=");
        printWriter.print(j);
        printWriter.print(" (");
        printWriter.print(android.text.format.Formatter.formatFileSize(this.mService.mContext, j));
        printWriter.println(")");
    }

    public org.json.JSONObject dumpCheckin(boolean z) throws org.json.JSONException {
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        jSONObject.put("userId", this.mUserId);
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        for (int i = 0; i < this.mLaunchers.size(); i++) {
            jSONArray.put(this.mLaunchers.valueAt(i).dumpCheckin(z));
        }
        jSONObject.put("launchers", jSONArray);
        org.json.JSONArray jSONArray2 = new org.json.JSONArray();
        for (int i2 = 0; i2 < this.mPackages.size(); i2++) {
            jSONArray2.put(this.mPackages.valueAt(i2).dumpCheckin(z));
        }
        jSONObject.put("packages", jSONArray2);
        return jSONObject;
    }

    void logSharingShortcutStats(com.android.internal.logging.MetricsLogger metricsLogger) {
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < this.mPackages.size(); i3++) {
            if (this.mPackages.valueAt(i3).hasShareTargets()) {
                i++;
                i2 += this.mPackages.valueAt(i3).getSharingShortcutCount();
            }
        }
        android.metrics.LogMaker logMaker = new android.metrics.LogMaker(1717);
        metricsLogger.write(logMaker.setType(1).setSubtype(this.mUserId));
        metricsLogger.write(logMaker.setType(2).setSubtype(i));
        metricsLogger.write(logMaker.setType(3).setSubtype(i2));
    }

    @android.annotation.NonNull
    com.android.internal.infra.AndroidFuture<android.app.appsearch.AppSearchSession> getAppSearch(@android.annotation.NonNull android.app.appsearch.AppSearchManager.SearchContext searchContext) {
        final com.android.internal.infra.AndroidFuture<android.app.appsearch.AppSearchSession> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        synchronized (this.mLock) {
            this.mInFlightSessions.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return ((com.android.internal.infra.AndroidFuture) obj).isDone();
                }
            });
            this.mInFlightSessions.add(androidFuture);
        }
        if (this.mAppSearchManager == null) {
            androidFuture.completeExceptionally(new java.lang.RuntimeException("app search manager is null"));
            return androidFuture;
        }
        if (!this.mService.mUserManagerInternal.isUserUnlockingOrUnlocked(getUserId())) {
            androidFuture.completeExceptionally(new java.lang.RuntimeException("User " + getUserId() + " is "));
            return androidFuture;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mAppSearchManager.createSearchSession(searchContext, this.mExecutor, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.ShortcutUser.lambda$getAppSearch$7(androidFuture, (android.app.appsearch.AppSearchResult) obj);
                }
            });
            return androidFuture;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getAppSearch$7(com.android.internal.infra.AndroidFuture androidFuture, android.app.appsearch.AppSearchResult appSearchResult) {
        if (!appSearchResult.isSuccess()) {
            androidFuture.completeExceptionally(new java.lang.RuntimeException(appSearchResult.getErrorMessage()));
        } else {
            androidFuture.complete((android.app.appsearch.AppSearchSession) appSearchResult.getResultValue());
        }
    }

    void cancelAllInFlightTasks() {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<com.android.internal.infra.AndroidFuture<android.app.appsearch.AppSearchSession>> it = this.mInFlightSessions.iterator();
                while (it.hasNext()) {
                    it.next().cancel(true);
                }
                this.mInFlightSessions.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
