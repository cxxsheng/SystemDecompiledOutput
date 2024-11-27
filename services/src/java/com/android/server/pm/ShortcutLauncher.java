package com.android.server.pm;

/* loaded from: classes2.dex */
class ShortcutLauncher extends com.android.server.pm.ShortcutPackageItem {
    private static final java.lang.String ATTR_LAUNCHER_USER_ID = "launcher-user";
    private static final java.lang.String ATTR_PACKAGE_NAME = "package-name";
    private static final java.lang.String ATTR_PACKAGE_USER_ID = "package-user";
    private static final java.lang.String ATTR_VALUE = "value";
    private static final java.lang.String TAG = "ShortcutService";
    private static final java.lang.String TAG_PACKAGE = "package";
    private static final java.lang.String TAG_PIN = "pin";
    static final java.lang.String TAG_ROOT = "launcher-pins";
    private final int mOwnerUserId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.content.pm.UserPackage, android.util.ArraySet<java.lang.String>> mPinnedShortcuts;

    private ShortcutLauncher(@android.annotation.NonNull com.android.server.pm.ShortcutUser shortcutUser, int i, @android.annotation.NonNull java.lang.String str, int i2, com.android.server.pm.ShortcutPackageInfo shortcutPackageInfo) {
        super(shortcutUser, i2, str, shortcutPackageInfo == null ? com.android.server.pm.ShortcutPackageInfo.newEmpty() : shortcutPackageInfo);
        this.mPinnedShortcuts = new android.util.ArrayMap<>();
        this.mOwnerUserId = i;
    }

    public ShortcutLauncher(@android.annotation.NonNull com.android.server.pm.ShortcutUser shortcutUser, int i, @android.annotation.NonNull java.lang.String str, int i2) {
        this(shortcutUser, i, str, i2, null);
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public int getOwnerUserId() {
        return this.mOwnerUserId;
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    protected boolean canRestoreAnyVersion() {
        return true;
    }

    private void onRestoreBlocked() {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new java.util.ArrayList(this.mPinnedShortcuts.keySet());
            this.mPinnedShortcuts.clear();
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.pm.ShortcutPackage packageShortcutsIfExists = this.mShortcutUser.getPackageShortcutsIfExists(((android.content.pm.UserPackage) arrayList.get(size)).packageName);
            if (packageShortcutsIfExists != null) {
                packageShortcutsIfExists.refreshPinnedFlags();
            }
        }
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    protected void onRestored(int i) {
        if (i != 0) {
            onRestoreBlocked();
        }
    }

    public void pinShortcuts(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<java.lang.String> list, boolean z) {
        com.android.server.pm.ShortcutPackage packageShortcutsIfExists = this.mShortcutUser.getPackageShortcutsIfExists(str);
        if (packageShortcutsIfExists == null) {
            return;
        }
        android.content.pm.UserPackage of = android.content.pm.UserPackage.of(i, str);
        int size = list.size();
        if (size == 0) {
            synchronized (this.mLock) {
                this.mPinnedShortcuts.remove(of);
            }
        } else {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            android.util.ArraySet<java.lang.String> arraySet2 = new android.util.ArraySet<>();
            for (int i2 = 0; i2 < size; i2++) {
                java.lang.String str2 = list.get(i2);
                android.content.pm.ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str2);
                if (findShortcutById != null) {
                    if (findShortcutById.isDynamic() || findShortcutById.isLongLived() || findShortcutById.isManifestShortcut() || z) {
                        arraySet2.add(str2);
                    } else {
                        arraySet.add(str2);
                    }
                }
            }
            synchronized (this.mLock) {
                try {
                    android.util.ArraySet<java.lang.String> arraySet3 = this.mPinnedShortcuts.get(of);
                    if (arraySet3 != null) {
                        java.util.Iterator it = arraySet.iterator();
                        while (it.hasNext()) {
                            java.lang.String str3 = (java.lang.String) it.next();
                            if (arraySet3.contains(str3)) {
                                arraySet2.add(str3);
                            }
                        }
                    }
                    this.mPinnedShortcuts.put(of, arraySet2);
                } finally {
                }
            }
        }
        packageShortcutsIfExists.refreshPinnedFlags();
    }

    @android.annotation.Nullable
    public android.util.ArraySet<java.lang.String> getPinnedShortcutIds(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.ArraySet<java.lang.String> arraySet;
        synchronized (this.mLock) {
            android.util.ArraySet<java.lang.String> arraySet2 = this.mPinnedShortcuts.get(android.content.pm.UserPackage.of(i, str));
            arraySet = arraySet2 == null ? null : new android.util.ArraySet<>((android.util.ArraySet) arraySet2);
        }
        return arraySet;
    }

    public boolean hasPinned(android.content.pm.ShortcutInfo shortcutInfo) {
        boolean z;
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<java.lang.String> arraySet = this.mPinnedShortcuts.get(android.content.pm.UserPackage.of(shortcutInfo.getUserId(), shortcutInfo.getPackage()));
                z = arraySet != null && arraySet.contains(shortcutInfo.getId());
            } finally {
            }
        }
        return z;
    }

    public void addPinnedShortcut(@android.annotation.NonNull java.lang.String str, int i, java.lang.String str2, boolean z) {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<java.lang.String> arraySet = this.mPinnedShortcuts.get(android.content.pm.UserPackage.of(i, str));
                if (arraySet != null) {
                    arrayList = new java.util.ArrayList(arraySet.size() + 1);
                    arrayList.addAll(arraySet);
                } else {
                    arrayList = new java.util.ArrayList(1);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        arrayList.add(str2);
        pinShortcuts(i, str, arrayList, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean cleanUpPackage(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mPinnedShortcuts.remove(android.content.pm.UserPackage.of(i, str)) != null;
        }
        return z;
    }

    public void ensurePackageInfo() {
        android.content.pm.PackageInfo packageInfoWithSignatures = this.mShortcutUser.mService.getPackageInfoWithSignatures(getPackageName(), getPackageUserId());
        if (packageInfoWithSignatures == null) {
            android.util.Slog.w(TAG, "Package not found: " + getPackageName());
            return;
        }
        getPackageInfo().updateFromPackageInfo(packageInfoWithSignatures);
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z) throws java.io.IOException {
        android.util.ArrayMap arrayMap;
        if (z && !getPackageInfo().isBackupAllowed()) {
            return;
        }
        synchronized (this.mLock) {
            arrayMap = new android.util.ArrayMap(this.mPinnedShortcuts);
        }
        int size = arrayMap.size();
        if (size == 0) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, TAG_ROOT);
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_PACKAGE_NAME, getPackageName());
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_LAUNCHER_USER_ID, getPackageUserId());
        getPackageInfo().saveToXml(this.mShortcutUser.mService, typedXmlSerializer, z);
        for (int i = 0; i < size; i++) {
            android.content.pm.UserPackage userPackage = (android.content.pm.UserPackage) arrayMap.keyAt(i);
            if (!z || userPackage.userId == getOwnerUserId()) {
                typedXmlSerializer.startTag((java.lang.String) null, "package");
                com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_PACKAGE_NAME, userPackage.packageName);
                com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_PACKAGE_USER_ID, userPackage.userId);
                android.util.ArraySet arraySet = (android.util.ArraySet) arrayMap.valueAt(i);
                int size2 = arraySet.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    com.android.server.pm.ShortcutService.writeTagValue(typedXmlSerializer, TAG_PIN, (java.lang.String) arraySet.valueAt(i2));
                }
                typedXmlSerializer.endTag((java.lang.String) null, "package");
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_ROOT);
    }

    public static com.android.server.pm.ShortcutLauncher loadFromFile(java.io.File file, com.android.server.pm.ShortcutUser shortcutUser, int i, boolean z) {
        java.io.FileInputStream fileInputStream;
        java.lang.Exception e;
        com.android.server.pm.ResilientAtomicFile resilientFile = com.android.server.pm.ShortcutPackageItem.getResilientFile(file);
        com.android.server.pm.ShortcutLauncher shortcutLauncher = null;
        try {
            try {
                fileInputStream = resilientFile.openRead();
                try {
                    if (fileInputStream == null) {
                        android.util.Slog.d(TAG, "Not found " + file);
                        resilientFile.close();
                        return null;
                    }
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next != 1) {
                            if (next == 2) {
                                int depth = resolvePullParser.getDepth();
                                java.lang.String name = resolvePullParser.getName();
                                if (depth == 1 && TAG_ROOT.equals(name)) {
                                    shortcutLauncher = loadFromXml(resolvePullParser, shortcutUser, i, z);
                                } else {
                                    com.android.server.pm.ShortcutService.throwForInvalidTag(depth, name);
                                }
                            }
                        } else {
                            resilientFile.close();
                            return shortcutLauncher;
                        }
                    }
                } catch (java.lang.Exception e2) {
                    e = e2;
                    android.util.Slog.e(TAG, "Failed to read file " + resilientFile.getBaseFile(), e);
                    resilientFile.failRead(fileInputStream, e);
                    com.android.server.pm.ShortcutLauncher loadFromFile = loadFromFile(file, shortcutUser, i, z);
                    resilientFile.close();
                    return loadFromFile;
                }
            } catch (java.lang.Throwable th) {
                if (resilientFile != null) {
                    try {
                        resilientFile.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.lang.Exception e3) {
            fileInputStream = null;
            e = e3;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004e, code lost:
    
        if (r4.equals("package") != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a5, code lost:
    
        if (r4.equals(com.android.server.pm.ShortcutLauncher.TAG_PIN) != false) goto L52;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.android.server.pm.ShortcutLauncher loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.pm.ShortcutUser shortcutUser, int i, boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.pm.ShortcutLauncher shortcutLauncher = new com.android.server.pm.ShortcutLauncher(shortcutUser, i, com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_PACKAGE_NAME), z ? i : com.android.server.pm.ShortcutService.parseIntAttribute(typedXmlPullParser, ATTR_LAUNCHER_USER_ID, i));
        int depth = typedXmlPullParser.getDepth();
        android.util.ArraySet<java.lang.String> arraySet = null;
        while (true) {
            int next = typedXmlPullParser.next();
            boolean z2 = true;
            if (next != 1 && (next != 3 || typedXmlPullParser.getDepth() > depth)) {
                if (next == 2) {
                    int depth2 = typedXmlPullParser.getDepth();
                    java.lang.String name = typedXmlPullParser.getName();
                    boolean z3 = false;
                    if (depth2 == depth + 1) {
                        switch (name.hashCode()) {
                            case -1923478059:
                                if (name.equals("package-info")) {
                                    z2 = false;
                                    break;
                                }
                                z2 = -1;
                                break;
                            case -807062458:
                                break;
                            default:
                                z2 = -1;
                                break;
                        }
                        switch (z2) {
                            case false:
                                shortcutLauncher.getPackageInfo().loadFromXml(typedXmlPullParser, z);
                                continue;
                            case true:
                                java.lang.String parseStringAttribute = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_PACKAGE_NAME);
                                int parseIntAttribute = z ? i : com.android.server.pm.ShortcutService.parseIntAttribute(typedXmlPullParser, ATTR_PACKAGE_USER_ID, i);
                                android.util.ArraySet<java.lang.String> arraySet2 = new android.util.ArraySet<>();
                                synchronized (shortcutLauncher.mLock) {
                                    shortcutLauncher.mPinnedShortcuts.put(android.content.pm.UserPackage.of(parseIntAttribute, parseStringAttribute), arraySet2);
                                }
                                arraySet = arraySet2;
                                continue;
                        }
                    }
                    if (depth2 == depth + 2) {
                        switch (name.hashCode()) {
                            case 110997:
                                break;
                            default:
                                z3 = -1;
                                break;
                        }
                        switch (z3) {
                            case false:
                                if (arraySet == null) {
                                    android.util.Slog.w(TAG, "pin in invalid place");
                                    break;
                                } else {
                                    arraySet.add(com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_VALUE));
                                    continue;
                                }
                        }
                    }
                    com.android.server.pm.ShortcutService.warnForInvalidTag(depth2, name);
                }
            }
        }
        return shortcutLauncher;
    }

    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, com.android.server.pm.ShortcutService.DumpFilter dumpFilter) {
        android.util.ArrayMap arrayMap;
        printWriter.println();
        printWriter.print(str);
        printWriter.print("Launcher: ");
        printWriter.print(getPackageName());
        printWriter.print("  Package user: ");
        printWriter.print(getPackageUserId());
        printWriter.print("  Owner user: ");
        printWriter.print(getOwnerUserId());
        printWriter.println();
        getPackageInfo().dump(printWriter, str + "  ");
        printWriter.println();
        synchronized (this.mLock) {
            arrayMap = new android.util.ArrayMap(this.mPinnedShortcuts);
        }
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            printWriter.println();
            android.content.pm.UserPackage userPackage = (android.content.pm.UserPackage) arrayMap.keyAt(i);
            printWriter.print(str);
            printWriter.print("  ");
            printWriter.print("Package: ");
            printWriter.print(userPackage.packageName);
            printWriter.print("  User: ");
            printWriter.println(userPackage.userId);
            android.util.ArraySet arraySet = (android.util.ArraySet) arrayMap.valueAt(i);
            int size2 = arraySet.size();
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print(str);
                printWriter.print("    Pinned: ");
                printWriter.print((java.lang.String) arraySet.valueAt(i2));
                printWriter.println();
            }
        }
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public org.json.JSONObject dumpCheckin(boolean z) throws org.json.JSONException {
        return super.dumpCheckin(z);
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    protected java.io.File getShortcutPackageItemFile() {
        return new java.io.File(new java.io.File(this.mShortcutUser.mService.injectUserDataPath(this.mShortcutUser.getUserId()), "launchers"), getPackageName() + getPackageUserId() + ".xml");
    }
}
