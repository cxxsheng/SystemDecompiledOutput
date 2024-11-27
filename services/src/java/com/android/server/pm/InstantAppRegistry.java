package com.android.server.pm;

/* loaded from: classes2.dex */
public class InstantAppRegistry implements com.android.server.utils.Watchable, com.android.server.utils.Snappable {
    private static final java.lang.String ATTR_GRANTED = "granted";
    private static final java.lang.String ATTR_LABEL = "label";
    private static final java.lang.String ATTR_NAME = "name";
    private static final boolean DEBUG = false;
    private static final long DEFAULT_INSTALLED_INSTANT_APP_MAX_CACHE_PERIOD = 15552000000L;
    static final long DEFAULT_INSTALLED_INSTANT_APP_MIN_CACHE_PERIOD = 604800000;
    private static final long DEFAULT_UNINSTALLED_INSTANT_APP_MAX_CACHE_PERIOD = 15552000000L;
    static final long DEFAULT_UNINSTALLED_INSTANT_APP_MIN_CACHE_PERIOD = 604800000;
    private static final java.lang.String INSTANT_APPS_FOLDER = "instant";
    private static final java.lang.String INSTANT_APP_ANDROID_ID_FILE = "android_id";
    private static final java.lang.String INSTANT_APP_COOKIE_FILE_PREFIX = "cookie_";
    private static final java.lang.String INSTANT_APP_COOKIE_FILE_SIFFIX = ".dat";
    private static final java.lang.String INSTANT_APP_ICON_FILE = "icon.png";
    private static final java.lang.String INSTANT_APP_METADATA_FILE = "metadata.xml";
    private static final java.lang.String LOG_TAG = "InstantAppRegistry";
    private static final java.lang.String TAG_PACKAGE = "package";
    private static final java.lang.String TAG_PERMISSION = "permission";
    private static final java.lang.String TAG_PERMISSIONS = "permissions";
    private final android.content.Context mContext;
    private final com.android.server.pm.InstantAppRegistry.CookiePersistence mCookiePersistence;
    private final com.android.server.pm.DeletePackageHelper mDeletePackageHelper;

    @com.android.server.utils.Watched
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.utils.WatchedSparseArray<com.android.server.utils.WatchedSparseBooleanArray> mInstalledInstantAppUids;

    @com.android.server.utils.Watched
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.utils.WatchedSparseArray<com.android.server.utils.WatchedSparseArray<com.android.server.utils.WatchedSparseBooleanArray>> mInstantGrants;
    private final java.lang.Object mLock;
    private final com.android.server.utils.Watcher mObserver;
    private final com.android.server.pm.permission.PermissionManagerServiceInternal mPermissionManager;
    private final com.android.server.utils.SnapshotCache<com.android.server.pm.InstantAppRegistry> mSnapshot;

    @com.android.server.utils.Watched
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.utils.WatchedSparseArray<java.util.List<com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState>> mUninstalledInstantApps;
    private final com.android.server.pm.UserManagerInternal mUserManager;
    private final com.android.server.utils.WatchableImpl mWatchable;

    @Override // com.android.server.utils.Watchable
    public void registerObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void unregisterObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public boolean isRegisteredObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void dispatchChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChanged() {
        dispatchChange(this);
    }

    private com.android.server.utils.SnapshotCache<com.android.server.pm.InstantAppRegistry> makeCache() {
        return new com.android.server.utils.SnapshotCache<com.android.server.pm.InstantAppRegistry>(this, this) { // from class: com.android.server.pm.InstantAppRegistry.2
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.InstantAppRegistry createSnapshot() {
                com.android.server.pm.InstantAppRegistry instantAppRegistry = new com.android.server.pm.InstantAppRegistry();
                instantAppRegistry.mWatchable.seal();
                return instantAppRegistry;
            }
        };
    }

    public InstantAppRegistry(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal permissionManagerServiceInternal, @android.annotation.NonNull com.android.server.pm.UserManagerInternal userManagerInternal, @android.annotation.NonNull com.android.server.pm.DeletePackageHelper deletePackageHelper) {
        this.mLock = new java.lang.Object();
        this.mWatchable = new com.android.server.utils.WatchableImpl();
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.InstantAppRegistry.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.pm.InstantAppRegistry.this.onChanged();
            }
        };
        this.mContext = context;
        this.mPermissionManager = permissionManagerServiceInternal;
        this.mUserManager = userManagerInternal;
        this.mDeletePackageHelper = deletePackageHelper;
        this.mCookiePersistence = new com.android.server.pm.InstantAppRegistry.CookiePersistence(com.android.internal.os.BackgroundThread.getHandler().getLooper());
        this.mUninstalledInstantApps = new com.android.server.utils.WatchedSparseArray<>();
        this.mInstantGrants = new com.android.server.utils.WatchedSparseArray<>();
        this.mInstalledInstantAppUids = new com.android.server.utils.WatchedSparseArray<>();
        this.mUninstalledInstantApps.registerObserver(this.mObserver);
        this.mInstantGrants.registerObserver(this.mObserver);
        this.mInstalledInstantAppUids.registerObserver(this.mObserver);
        com.android.server.utils.Watchable.verifyWatchedAttributes(this, this.mObserver);
        this.mSnapshot = makeCache();
    }

    private InstantAppRegistry(com.android.server.pm.InstantAppRegistry instantAppRegistry) {
        this.mLock = new java.lang.Object();
        this.mWatchable = new com.android.server.utils.WatchableImpl();
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.InstantAppRegistry.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.pm.InstantAppRegistry.this.onChanged();
            }
        };
        this.mContext = instantAppRegistry.mContext;
        this.mPermissionManager = instantAppRegistry.mPermissionManager;
        this.mUserManager = instantAppRegistry.mUserManager;
        this.mDeletePackageHelper = instantAppRegistry.mDeletePackageHelper;
        this.mCookiePersistence = null;
        this.mUninstalledInstantApps = new com.android.server.utils.WatchedSparseArray<>(instantAppRegistry.mUninstalledInstantApps);
        this.mInstantGrants = new com.android.server.utils.WatchedSparseArray<>(instantAppRegistry.mInstantGrants);
        this.mInstalledInstantAppUids = new com.android.server.utils.WatchedSparseArray<>(instantAppRegistry.mInstalledInstantAppUids);
        this.mSnapshot = null;
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.pm.InstantAppRegistry snapshot() {
        return this.mSnapshot.snapshot();
    }

    public byte[] getInstantAppCookie(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        synchronized (this.mLock) {
            try {
                byte[] pendingPersistCookieLPr = this.mCookiePersistence.getPendingPersistCookieLPr(androidPackage, i);
                if (pendingPersistCookieLPr != null) {
                    return pendingPersistCookieLPr;
                }
                java.io.File peekInstantCookieFile = peekInstantCookieFile(androidPackage.getPackageName(), i);
                if (peekInstantCookieFile != null && peekInstantCookieFile.exists()) {
                    try {
                        return libcore.io.IoUtils.readFileAsByteArray(peekInstantCookieFile.toString());
                    } catch (java.io.IOException e) {
                        android.util.Slog.w(LOG_TAG, "Error reading cookie file: " + peekInstantCookieFile);
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean setInstantAppCookie(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable byte[] bArr, int i, int i2) {
        synchronized (this.mLock) {
            if (bArr != null) {
                try {
                    if (bArr.length > 0 && bArr.length > i) {
                        android.util.Slog.e(LOG_TAG, "Instant app cookie for package " + androidPackage.getPackageName() + " size " + bArr.length + " bytes while max size is " + i);
                        return false;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mCookiePersistence.schedulePersistLPw(i2, androidPackage, bArr);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void persistInstantApplicationCookie(@android.annotation.Nullable byte[] bArr, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.File file, int i) {
        synchronized (this.mLock) {
            try {
                java.io.File instantApplicationDir = getInstantApplicationDir(str, i);
                if (!instantApplicationDir.exists() && !instantApplicationDir.mkdirs()) {
                    android.util.Slog.e(LOG_TAG, "Cannot create instant app cookie directory");
                    return;
                }
                if (file.exists() && !file.delete()) {
                    android.util.Slog.e(LOG_TAG, "Cannot delete instant app cookie file");
                }
                if (bArr == null || bArr.length <= 0) {
                    return;
                }
                try {
                    java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
                    try {
                        fileOutputStream.write(bArr, 0, bArr.length);
                        fileOutputStream.close();
                    } finally {
                    }
                } catch (java.io.IOException e) {
                    android.util.Slog.e(LOG_TAG, "Error writing instant app cookie file: " + file, e);
                }
            } finally {
            }
        }
    }

    @android.annotation.Nullable
    public android.graphics.Bitmap getInstantAppIcon(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                java.io.File file = new java.io.File(getInstantApplicationDir(str, i), INSTANT_APP_ICON_FILE);
                if (!file.exists()) {
                    return null;
                }
                return android.graphics.BitmapFactory.decodeFile(file.toString());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public java.lang.String getInstantAppAndroidId(@android.annotation.NonNull java.lang.String str, int i) {
        java.io.FileOutputStream fileOutputStream;
        synchronized (this.mLock) {
            try {
                java.io.File file = new java.io.File(getInstantApplicationDir(str, i), INSTANT_APP_ANDROID_ID_FILE);
                if (file.exists()) {
                    try {
                        return libcore.io.IoUtils.readFileAsString(file.getAbsolutePath());
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(LOG_TAG, "Failed to read instant app android id file: " + file, e);
                    }
                }
                byte[] bArr = new byte[8];
                new java.security.SecureRandom().nextBytes(bArr);
                java.lang.String encodeToString = libcore.util.HexEncoding.encodeToString(bArr, false);
                java.io.File instantApplicationDir = getInstantApplicationDir(str, i);
                if (!instantApplicationDir.exists() && !instantApplicationDir.mkdirs()) {
                    android.util.Slog.e(LOG_TAG, "Cannot create instant app cookie directory");
                    return encodeToString;
                }
                java.io.File file2 = new java.io.File(getInstantApplicationDir(str, i), INSTANT_APP_ANDROID_ID_FILE);
                try {
                    fileOutputStream = new java.io.FileOutputStream(file2);
                } catch (java.io.IOException e2) {
                    android.util.Slog.e(LOG_TAG, "Error writing instant app android id file: " + file2, e2);
                }
                try {
                    fileOutputStream.write(encodeToString.getBytes());
                    fileOutputStream.close();
                    return encodeToString;
                } catch (java.lang.Throwable th) {
                    try {
                        fileOutputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th3) {
                throw th3;
            }
        }
    }

    @android.annotation.Nullable
    public java.util.List<android.content.pm.InstantAppInfo> getInstantApps(@android.annotation.NonNull com.android.server.pm.Computer computer, int i) {
        java.util.List<android.content.pm.InstantAppInfo> installedInstantApplications = getInstalledInstantApplications(computer, i);
        java.util.List<android.content.pm.InstantAppInfo> uninstalledInstantApplications = getUninstalledInstantApplications(computer, i);
        if (installedInstantApplications != null) {
            if (uninstalledInstantApplications != null) {
                installedInstantApplications.addAll(uninstalledInstantApplications);
            }
            return installedInstantApplications;
        }
        return uninstalledInstantApplications;
    }

    public void onPackageInstalled(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int[] iArr) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        final com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
        if (pkg == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                for (int i : iArr) {
                    if (packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                        propagateInstantAppPermissionsIfNeeded(pkg, i);
                        if (packageStateInternal.getUserStateOrDefault(i).isInstantApp()) {
                            addInstantApp(i, packageStateInternal.getAppId());
                        }
                        removeUninstalledInstantAppStateLPw(new java.util.function.Predicate() { // from class: com.android.server.pm.InstantAppRegistry$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(java.lang.Object obj) {
                                boolean lambda$onPackageInstalled$0;
                                lambda$onPackageInstalled$0 = com.android.server.pm.InstantAppRegistry.lambda$onPackageInstalled$0(pkg, (com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState) obj);
                                return lambda$onPackageInstalled$0;
                            }
                        }, i);
                        java.io.File instantApplicationDir = getInstantApplicationDir(pkg.getPackageName(), i);
                        new java.io.File(instantApplicationDir, INSTANT_APP_METADATA_FILE).delete();
                        new java.io.File(instantApplicationDir, INSTANT_APP_ICON_FILE).delete();
                        java.io.File peekInstantCookieFile = peekInstantCookieFile(pkg.getPackageName(), i);
                        if (peekInstantCookieFile != null) {
                            java.lang.String name = peekInstantCookieFile.getName();
                            java.lang.String substring = name.substring(INSTANT_APP_COOKIE_FILE_PREFIX.length(), name.length() - INSTANT_APP_COOKIE_FILE_SIFFIX.length());
                            if (pkg.getSigningDetails().checkCapability(substring, 1)) {
                                return;
                            }
                            for (java.lang.String str2 : android.util.PackageUtils.computeSignaturesSha256Digests(pkg.getSigningDetails().getSignatures())) {
                                if (str2.equals(substring)) {
                                    return;
                                }
                            }
                            android.util.Slog.i(LOG_TAG, "Signature for package " + pkg.getPackageName() + " changed - dropping cookie");
                            this.mCookiePersistence.cancelPendingPersistLPw(pkg, i);
                            peekInstantCookieFile.delete();
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onPackageInstalled$0(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState uninstalledInstantAppState) {
        return uninstalledInstantAppState.mInstantAppInfo.getPackageName().equals(androidPackage.getPackageName());
    }

    public void onPackageUninstalled(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting, @android.annotation.NonNull int[] iArr, boolean z) {
        if (packageSetting == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                for (int i : iArr) {
                    if (!z || !packageSetting.getInstalled(i)) {
                        if (packageSetting.getInstantApp(i)) {
                            addUninstalledInstantAppLPw(packageSetting, i);
                            removeInstantAppLPw(i, packageSetting.getAppId());
                        } else {
                            deleteDir(getInstantApplicationDir(androidPackage.getPackageName(), i));
                            this.mCookiePersistence.cancelPendingPersistLPw(androidPackage, i);
                            removeAppLPw(i, packageSetting.getAppId());
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onUserRemoved(int i) {
        synchronized (this.mLock) {
            this.mUninstalledInstantApps.remove(i);
            this.mInstalledInstantAppUids.remove(i);
            this.mInstantGrants.remove(i);
            deleteDir(getInstantApplicationsDir(i));
        }
    }

    public boolean isInstantAccessGranted(int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                com.android.server.utils.WatchedSparseArray<com.android.server.utils.WatchedSparseBooleanArray> watchedSparseArray = this.mInstantGrants.get(i);
                if (watchedSparseArray == null) {
                    return false;
                }
                com.android.server.utils.WatchedSparseBooleanArray watchedSparseBooleanArray = watchedSparseArray.get(i2);
                if (watchedSparseBooleanArray == null) {
                    return false;
                }
                return watchedSparseBooleanArray.get(i3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean grantInstantAccess(int i, @android.annotation.Nullable android.content.Intent intent, int i2, int i3) {
        java.util.Set<java.lang.String> categories;
        synchronized (this.mLock) {
            try {
                if (this.mInstalledInstantAppUids == null) {
                    return false;
                }
                com.android.server.utils.WatchedSparseBooleanArray watchedSparseBooleanArray = this.mInstalledInstantAppUids.get(i);
                if (watchedSparseBooleanArray == null || !watchedSparseBooleanArray.get(i3)) {
                    return false;
                }
                if (watchedSparseBooleanArray.get(i2)) {
                    return false;
                }
                if (intent != null && "android.intent.action.VIEW".equals(intent.getAction()) && (categories = intent.getCategories()) != null && categories.contains("android.intent.category.BROWSABLE")) {
                    return false;
                }
                com.android.server.utils.WatchedSparseArray<com.android.server.utils.WatchedSparseBooleanArray> watchedSparseArray = this.mInstantGrants.get(i);
                if (watchedSparseArray == null) {
                    watchedSparseArray = new com.android.server.utils.WatchedSparseArray<>();
                    this.mInstantGrants.put(i, watchedSparseArray);
                }
                com.android.server.utils.WatchedSparseBooleanArray watchedSparseBooleanArray2 = watchedSparseArray.get(i2);
                if (watchedSparseBooleanArray2 == null) {
                    watchedSparseBooleanArray2 = new com.android.server.utils.WatchedSparseBooleanArray();
                    watchedSparseArray.put(i2, watchedSparseBooleanArray2);
                }
                watchedSparseBooleanArray2.put(i3, true);
                return true;
            } finally {
            }
        }
    }

    public void addInstantApp(int i, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.utils.WatchedSparseBooleanArray watchedSparseBooleanArray = this.mInstalledInstantAppUids.get(i);
                if (watchedSparseBooleanArray == null) {
                    watchedSparseBooleanArray = new com.android.server.utils.WatchedSparseBooleanArray();
                    this.mInstalledInstantAppUids.put(i, watchedSparseBooleanArray);
                }
                watchedSparseBooleanArray.put(i2, true);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        onChanged();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void removeInstantAppLPw(int i, int i2) {
        com.android.server.utils.WatchedSparseBooleanArray watchedSparseBooleanArray;
        if (this.mInstalledInstantAppUids == null || (watchedSparseBooleanArray = this.mInstalledInstantAppUids.get(i)) == null) {
            return;
        }
        try {
            watchedSparseBooleanArray.delete(i2);
            if (this.mInstantGrants == null) {
                return;
            }
            com.android.server.utils.WatchedSparseArray<com.android.server.utils.WatchedSparseBooleanArray> watchedSparseArray = this.mInstantGrants.get(i);
            if (watchedSparseArray == null) {
                return;
            }
            for (int size = watchedSparseArray.size() - 1; size >= 0; size--) {
                watchedSparseArray.valueAt(size).delete(i2);
            }
        } finally {
            onChanged();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void removeAppLPw(int i, int i2) {
        com.android.server.utils.WatchedSparseArray<com.android.server.utils.WatchedSparseBooleanArray> watchedSparseArray;
        if (this.mInstantGrants == null || (watchedSparseArray = this.mInstantGrants.get(i)) == null) {
            return;
        }
        watchedSparseArray.delete(i2);
        onChanged();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addUninstalledInstantAppLPw(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i) {
        android.content.pm.InstantAppInfo createInstantAppInfoForPackage = createInstantAppInfoForPackage(packageStateInternal, i, false);
        if (createInstantAppInfoForPackage == null) {
            return;
        }
        java.util.List<com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState> list = this.mUninstalledInstantApps.get(i);
        if (list == null) {
            list = new java.util.ArrayList<>();
            this.mUninstalledInstantApps.put(i, list);
        }
        list.add(new com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState(createInstantAppInfoForPackage, java.lang.System.currentTimeMillis()));
        writeUninstalledInstantAppMetadata(createInstantAppInfoForPackage, i);
        writeInstantApplicationIconLPw(packageStateInternal.getPkg(), i);
    }

    private void writeInstantApplicationIconLPw(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        android.graphics.Bitmap bitmap;
        if (!getInstantApplicationDir(androidPackage.getPackageName(), i).exists()) {
            return;
        }
        android.graphics.drawable.Drawable loadIcon = com.android.server.pm.parsing.pkg.AndroidPackageUtils.generateAppInfoWithoutState(androidPackage).loadIcon(this.mContext.getPackageManager());
        if (loadIcon instanceof android.graphics.drawable.BitmapDrawable) {
            bitmap = ((android.graphics.drawable.BitmapDrawable) loadIcon).getBitmap();
        } else {
            android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(loadIcon.getIntrinsicWidth(), loadIcon.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888);
            android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
            loadIcon.setBounds(0, 0, loadIcon.getIntrinsicWidth(), loadIcon.getIntrinsicHeight());
            loadIcon.draw(canvas);
            bitmap = createBitmap;
        }
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(new java.io.File(getInstantApplicationDir(androidPackage.getPackageName(), i), INSTANT_APP_ICON_FILE));
            try {
                bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(LOG_TAG, "Error writing instant app icon", e);
        }
    }

    boolean hasInstantApplicationMetadata(java.lang.String str, int i) {
        return hasUninstalledInstantAppState(str, i) || hasInstantAppMetadata(str, i);
    }

    public void deleteInstantApplicationMetadata(@android.annotation.NonNull final java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                removeUninstalledInstantAppStateLPw(new java.util.function.Predicate() { // from class: com.android.server.pm.InstantAppRegistry$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$deleteInstantApplicationMetadata$1;
                        lambda$deleteInstantApplicationMetadata$1 = com.android.server.pm.InstantAppRegistry.lambda$deleteInstantApplicationMetadata$1(str, (com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState) obj);
                        return lambda$deleteInstantApplicationMetadata$1;
                    }
                }, i);
                java.io.File instantApplicationDir = getInstantApplicationDir(str, i);
                new java.io.File(instantApplicationDir, INSTANT_APP_METADATA_FILE).delete();
                new java.io.File(instantApplicationDir, INSTANT_APP_ICON_FILE).delete();
                new java.io.File(instantApplicationDir, INSTANT_APP_ANDROID_ID_FILE).delete();
                java.io.File peekInstantCookieFile = peekInstantCookieFile(str, i);
                if (peekInstantCookieFile != null) {
                    peekInstantCookieFile.delete();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$deleteInstantApplicationMetadata$1(java.lang.String str, com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState uninstalledInstantAppState) {
        return uninstalledInstantAppState.mInstantAppInfo.getPackageName().equals(str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void removeUninstalledInstantAppStateLPw(@android.annotation.NonNull java.util.function.Predicate<com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState> predicate, int i) {
        java.util.List<com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState> list;
        if (this.mUninstalledInstantApps == null || (list = this.mUninstalledInstantApps.get(i)) == null) {
            return;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            if (predicate.test(list.get(size))) {
                list.remove(size);
                if (list.isEmpty()) {
                    this.mUninstalledInstantApps.remove(i);
                    onChanged();
                    return;
                }
            }
        }
    }

    private boolean hasUninstalledInstantAppState(java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                if (this.mUninstalledInstantApps == null) {
                    return false;
                }
                java.util.List<com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState> list = this.mUninstalledInstantApps.get(i);
                if (list == null) {
                    return false;
                }
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (str.equals(list.get(i2).mInstantAppInfo.getPackageName())) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean hasInstantAppMetadata(java.lang.String str, int i) {
        java.io.File instantApplicationDir = getInstantApplicationDir(str, i);
        return new java.io.File(instantApplicationDir, INSTANT_APP_METADATA_FILE).exists() || new java.io.File(instantApplicationDir, INSTANT_APP_ICON_FILE).exists() || new java.io.File(instantApplicationDir, INSTANT_APP_ANDROID_ID_FILE).exists() || peekInstantCookieFile(str, i) != null;
    }

    void pruneInstantApps(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        try {
            pruneInstantApps(computer, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), "installed_instant_app_max_cache_period", 15552000000L), android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), "uninstalled_instant_app_max_cache_period", 15552000000L));
        } catch (java.io.IOException e) {
            android.util.Slog.e(LOG_TAG, "Error pruning installed and uninstalled instant apps", e);
        }
    }

    boolean pruneInstalledInstantApps(@android.annotation.NonNull com.android.server.pm.Computer computer, long j, long j2) {
        try {
            return pruneInstantApps(computer, j, j2, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
        } catch (java.io.IOException e) {
            android.util.Slog.e(LOG_TAG, "Error pruning installed instant apps", e);
            return false;
        }
    }

    boolean pruneUninstalledInstantApps(@android.annotation.NonNull com.android.server.pm.Computer computer, long j, long j2) {
        try {
            return pruneInstantApps(computer, j, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, j2);
        } catch (java.io.IOException e) {
            android.util.Slog.e(LOG_TAG, "Error pruning uninstalled instant apps", e);
            return false;
        }
    }

    private boolean pruneInstantApps(@android.annotation.NonNull com.android.server.pm.Computer computer, long j, long j2, final long j3) throws java.io.IOException {
        java.io.File[] listFiles;
        java.io.File findPathForUuid = ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).findPathForUuid(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL);
        if (findPathForUuid.getUsableSpace() >= j) {
            return true;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        int[] userIds = this.mUserManager.getUserIds();
        final android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = computer.getPackageStates();
        int size = packageStates.size();
        java.util.ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i);
            com.android.server.pm.pkg.AndroidPackage pkg = valueAt == null ? null : valueAt.getPkg();
            if (pkg != null && currentTimeMillis - valueAt.getTransientState().getLatestPackageUseTimeInMills() >= j2) {
                int length = userIds.length;
                int i2 = 0;
                boolean z = false;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = valueAt.getUserStateOrDefault(userIds[i2]);
                    if (userStateOrDefault.isInstalled()) {
                        if (userStateOrDefault.isInstantApp()) {
                            z = true;
                        } else {
                            z = false;
                            break;
                        }
                    }
                    i2++;
                }
                if (z) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(pkg.getPackageName());
                }
            }
        }
        if (arrayList != null) {
            arrayList.sort(new java.util.Comparator() { // from class: com.android.server.pm.InstantAppRegistry$$ExternalSyntheticLambda2
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$pruneInstantApps$2;
                    lambda$pruneInstantApps$2 = com.android.server.pm.InstantAppRegistry.lambda$pruneInstantApps$2(packageStates, (java.lang.String) obj, (java.lang.String) obj2);
                    return lambda$pruneInstantApps$2;
                }
            });
        }
        if (arrayList != null) {
            int size2 = arrayList.size();
            for (int i3 = 0; i3 < size2; i3++) {
                if (this.mDeletePackageHelper.deletePackageX((java.lang.String) arrayList.get(i3), -1L, 0, 2, true) == 1 && findPathForUuid.getUsableSpace() >= j) {
                    return true;
                }
            }
        }
        synchronized (this.mLock) {
            try {
                for (int i4 : this.mUserManager.getUserIds()) {
                    removeUninstalledInstantAppStateLPw(new java.util.function.Predicate() { // from class: com.android.server.pm.InstantAppRegistry$$ExternalSyntheticLambda3
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$pruneInstantApps$3;
                            lambda$pruneInstantApps$3 = com.android.server.pm.InstantAppRegistry.lambda$pruneInstantApps$3(j3, (com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState) obj);
                            return lambda$pruneInstantApps$3;
                        }
                    }, i4);
                    java.io.File instantApplicationsDir = getInstantApplicationsDir(i4);
                    if (instantApplicationsDir.exists() && (listFiles = instantApplicationsDir.listFiles()) != null) {
                        for (java.io.File file : listFiles) {
                            if (file.isDirectory()) {
                                java.io.File file2 = new java.io.File(file, INSTANT_APP_METADATA_FILE);
                                if (file2.exists() && java.lang.System.currentTimeMillis() - file2.lastModified() > j3) {
                                    deleteDir(file);
                                    if (findPathForUuid.getUsableSpace() >= j) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$pruneInstantApps$2(android.util.ArrayMap arrayMap, java.lang.String str, java.lang.String str2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = (com.android.server.pm.pkg.PackageStateInternal) arrayMap.get(str);
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal3 = (com.android.server.pm.pkg.PackageStateInternal) arrayMap.get(str2);
        com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal2 == null ? null : packageStateInternal2.getPkg();
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg2 = packageStateInternal3 != null ? packageStateInternal3.getPkg() : null;
        if (pkg == null && pkg2 == null) {
            return 0;
        }
        if (pkg == null) {
            return -1;
        }
        if (pkg2 == null) {
            return 1;
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal4 = (com.android.server.pm.pkg.PackageStateInternal) arrayMap.get(pkg.getPackageName());
        if (packageStateInternal4 == null || (packageStateInternal = (com.android.server.pm.pkg.PackageStateInternal) arrayMap.get(pkg2.getPackageName())) == null) {
            return 0;
        }
        if (packageStateInternal4.getTransientState().getLatestPackageUseTimeInMills() > packageStateInternal.getTransientState().getLatestPackageUseTimeInMills()) {
            return 1;
        }
        if (packageStateInternal4.getTransientState().getLatestPackageUseTimeInMills() < packageStateInternal.getTransientState().getLatestPackageUseTimeInMills() || com.android.server.pm.pkg.PackageStateUtils.getEarliestFirstInstallTime(packageStateInternal4.getUserStates()) <= com.android.server.pm.pkg.PackageStateUtils.getEarliestFirstInstallTime(packageStateInternal.getUserStates())) {
            return -1;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$pruneInstantApps$3(long j, com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState uninstalledInstantAppState) {
        return java.lang.System.currentTimeMillis() - uninstalledInstantAppState.mTimestamp > j;
    }

    @android.annotation.Nullable
    private java.util.List<android.content.pm.InstantAppInfo> getInstalledInstantApplications(@android.annotation.NonNull com.android.server.pm.Computer computer, int i) {
        android.content.pm.InstantAppInfo createInstantAppInfoForPackage;
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = computer.getPackageStates();
        int size = packageStates.size();
        java.util.ArrayList arrayList = null;
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i2);
            if (valueAt != null && valueAt.getUserStateOrDefault(i).isInstantApp() && (createInstantAppInfoForPackage = createInstantAppInfoForPackage(valueAt, i, true)) != null) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.add(createInstantAppInfoForPackage);
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    private android.content.pm.InstantAppInfo createInstantAppInfoForPackage(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, boolean z) {
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
        if (pkg == null || !packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
            return null;
        }
        java.lang.String[] strArr = new java.lang.String[pkg.getRequestedPermissions().size()];
        pkg.getRequestedPermissions().toArray(strArr);
        java.util.Set<java.lang.String> grantedPermissions = this.mPermissionManager.getGrantedPermissions(pkg.getPackageName(), i);
        java.lang.String[] strArr2 = new java.lang.String[grantedPermissions.size()];
        grantedPermissions.toArray(strArr2);
        android.content.pm.ApplicationInfo generateApplicationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(packageStateInternal.getPkg(), 0L, packageStateInternal.getUserStateOrDefault(i), i, packageStateInternal);
        if (z) {
            return new android.content.pm.InstantAppInfo(generateApplicationInfo, strArr, strArr2);
        }
        return new android.content.pm.InstantAppInfo(generateApplicationInfo.packageName, generateApplicationInfo.loadLabel(this.mContext.getPackageManager()), strArr, strArr2);
    }

    @android.annotation.Nullable
    private java.util.List<android.content.pm.InstantAppInfo> getUninstalledInstantApplications(@android.annotation.NonNull com.android.server.pm.Computer computer, int i) {
        java.util.List<com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState> uninstalledInstantAppStates = getUninstalledInstantAppStates(i);
        java.util.ArrayList arrayList = null;
        if (uninstalledInstantAppStates == null || uninstalledInstantAppStates.isEmpty()) {
            return null;
        }
        int size = uninstalledInstantAppStates.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState uninstalledInstantAppState = uninstalledInstantAppStates.get(i2);
            if (arrayList == null) {
                arrayList = new java.util.ArrayList();
            }
            arrayList.add(uninstalledInstantAppState.mInstantAppInfo);
        }
        return arrayList;
    }

    @android.annotation.SuppressLint({"MissingPermission"})
    private void propagateInstantAppPermissionsIfNeeded(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        android.content.pm.InstantAppInfo peekOrParseUninstalledInstantAppInfo = peekOrParseUninstalledInstantAppInfo(androidPackage.getPackageName(), i);
        if (peekOrParseUninstalledInstantAppInfo == null || com.android.internal.util.ArrayUtils.isEmpty(peekOrParseUninstalledInstantAppInfo.getGrantedPermissions())) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            for (java.lang.String str : peekOrParseUninstalledInstantAppInfo.getGrantedPermissions()) {
                if (canPropagatePermission(str) && androidPackage.getRequestedPermissions().contains(str)) {
                    ((android.permission.PermissionManager) this.mContext.getSystemService(android.permission.PermissionManager.class)).grantRuntimePermission(androidPackage.getPackageName(), str, android.os.UserHandle.of(i));
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean canPropagatePermission(@android.annotation.NonNull java.lang.String str) {
        android.content.pm.PermissionInfo permissionInfo = ((android.permission.PermissionManager) this.mContext.getSystemService(android.permission.PermissionManager.class)).getPermissionInfo(str, 0);
        if (permissionInfo != null) {
            return (permissionInfo.getProtection() == 1 || (permissionInfo.getProtectionFlags() & 32) != 0) && (permissionInfo.getProtectionFlags() & 4096) != 0;
        }
        return false;
    }

    @android.annotation.NonNull
    private android.content.pm.InstantAppInfo peekOrParseUninstalledInstantAppInfo(@android.annotation.NonNull java.lang.String str, int i) {
        java.util.List<com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState> list;
        synchronized (this.mLock) {
            try {
                if (this.mUninstalledInstantApps != null && (list = this.mUninstalledInstantApps.get(i)) != null) {
                    int size = list.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState uninstalledInstantAppState = list.get(i2);
                        if (uninstalledInstantAppState.mInstantAppInfo.getPackageName().equals(str)) {
                            return uninstalledInstantAppState.mInstantAppInfo;
                        }
                    }
                }
                com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState parseMetadataFile = parseMetadataFile(new java.io.File(getInstantApplicationDir(str, i), INSTANT_APP_METADATA_FILE));
                if (parseMetadataFile == null) {
                    return null;
                }
                return parseMetadataFile.mInstantAppInfo;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    private java.util.List<com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState> getUninstalledInstantAppStates(int i) {
        java.util.List<com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState> list;
        java.io.File[] listFiles;
        com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState parseMetadataFile;
        synchronized (this.mLock) {
            try {
                if (this.mUninstalledInstantApps == null) {
                    list = null;
                } else {
                    list = this.mUninstalledInstantApps.get(i);
                    if (list != null) {
                        return list;
                    }
                }
                java.io.File instantApplicationsDir = getInstantApplicationsDir(i);
                if (instantApplicationsDir.exists() && (listFiles = instantApplicationsDir.listFiles()) != null) {
                    for (java.io.File file : listFiles) {
                        if (file.isDirectory() && (parseMetadataFile = parseMetadataFile(new java.io.File(file, INSTANT_APP_METADATA_FILE))) != null) {
                            if (list == null) {
                                list = new java.util.ArrayList<>();
                            }
                            list.add(parseMetadataFile);
                        }
                    }
                }
                synchronized (this.mLock) {
                    this.mUninstalledInstantApps.put(i, list);
                }
                return list;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    private static com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState parseMetadataFile(@android.annotation.NonNull java.io.File file) {
        if (!file.exists()) {
            return null;
        }
        try {
            java.io.FileInputStream openRead = new android.util.AtomicFile(file).openRead();
            try {
                try {
                    return new com.android.server.pm.InstantAppRegistry.UninstalledInstantAppState(parseMetadata(android.util.Xml.resolvePullParser(openRead), file.getParentFile().getName()), file.lastModified());
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    throw new java.lang.IllegalStateException("Failed parsing instant metadata file: " + file, e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(openRead);
            }
        } catch (java.io.FileNotFoundException e2) {
            android.util.Slog.i(LOG_TAG, "No instant metadata file");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public static java.io.File computeInstantCookieFile(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        return new java.io.File(getInstantApplicationDir(str, i), INSTANT_APP_COOKIE_FILE_PREFIX + str2 + INSTANT_APP_COOKIE_FILE_SIFFIX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public static java.io.File peekInstantCookieFile(@android.annotation.NonNull java.lang.String str, int i) {
        java.io.File[] listFiles;
        java.io.File instantApplicationDir = getInstantApplicationDir(str, i);
        if (!instantApplicationDir.exists() || (listFiles = instantApplicationDir.listFiles()) == null) {
            return null;
        }
        for (java.io.File file : listFiles) {
            if (!file.isDirectory() && file.getName().startsWith(INSTANT_APP_COOKIE_FILE_PREFIX) && file.getName().endsWith(INSTANT_APP_COOKIE_FILE_SIFFIX)) {
                return file;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    private static android.content.pm.InstantAppInfo parseMetadata(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if ("package".equals(typedXmlPullParser.getName())) {
                return parsePackage(typedXmlPullParser, str);
            }
        }
        return null;
    }

    private static android.content.pm.InstantAppInfo parsePackage(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_LABEL);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (TAG_PERMISSIONS.equals(typedXmlPullParser.getName())) {
                parsePermissions(typedXmlPullParser, arrayList, arrayList2);
            }
        }
        java.lang.String[] strArr = new java.lang.String[arrayList.size()];
        arrayList.toArray(strArr);
        java.lang.String[] strArr2 = new java.lang.String[arrayList2.size()];
        arrayList2.toArray(strArr2);
        return new android.content.pm.InstantAppInfo(str, attributeValue, strArr, strArr2);
    }

    private static void parsePermissions(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.NonNull java.util.List<java.lang.String> list2) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (TAG_PERMISSION.equals(typedXmlPullParser.getName())) {
                java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name");
                list.add(readStringAttribute);
                if (typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_GRANTED, false)) {
                    list2.add(readStringAttribute);
                }
            }
        }
    }

    private void writeUninstalledInstantAppMetadata(@android.annotation.NonNull android.content.pm.InstantAppInfo instantAppInfo, int i) {
        java.io.File instantApplicationDir = getInstantApplicationDir(instantAppInfo.getPackageName(), i);
        if (!instantApplicationDir.exists() && !instantApplicationDir.mkdirs()) {
            return;
        }
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(instantApplicationDir, INSTANT_APP_METADATA_FILE));
        java.io.FileOutputStream fileOutputStream = null;
        try {
            java.io.FileOutputStream startWrite = atomicFile.startWrite();
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.startTag((java.lang.String) null, "package");
                resolveSerializer.attribute((java.lang.String) null, ATTR_LABEL, instantAppInfo.loadLabel(this.mContext.getPackageManager()).toString());
                resolveSerializer.startTag((java.lang.String) null, TAG_PERMISSIONS);
                for (java.lang.String str : instantAppInfo.getRequestedPermissions()) {
                    resolveSerializer.startTag((java.lang.String) null, TAG_PERMISSION);
                    resolveSerializer.attribute((java.lang.String) null, "name", str);
                    if (com.android.internal.util.ArrayUtils.contains(instantAppInfo.getGrantedPermissions(), str)) {
                        resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_GRANTED, true);
                    }
                    resolveSerializer.endTag((java.lang.String) null, TAG_PERMISSION);
                }
                resolveSerializer.endTag((java.lang.String) null, TAG_PERMISSIONS);
                resolveSerializer.endTag((java.lang.String) null, "package");
                resolveSerializer.endDocument();
                atomicFile.finishWrite(startWrite);
                libcore.io.IoUtils.closeQuietly(startWrite);
            } catch (java.lang.Throwable th) {
                th = th;
                fileOutputStream = startWrite;
                try {
                    android.util.Slog.wtf(LOG_TAG, "Failed to write instant state, restoring backup", th);
                    atomicFile.failWrite(fileOutputStream);
                } finally {
                    libcore.io.IoUtils.closeQuietly(fileOutputStream);
                }
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    @android.annotation.NonNull
    private static java.io.File getInstantApplicationsDir(int i) {
        return new java.io.File(android.os.Environment.getUserSystemDirectory(i), INSTANT_APPS_FOLDER);
    }

    @android.annotation.NonNull
    private static java.io.File getInstantApplicationDir(java.lang.String str, int i) {
        return new java.io.File(getInstantApplicationsDir(i), str);
    }

    private static void deleteDir(@android.annotation.NonNull java.io.File file) {
        java.io.File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (java.io.File file2 : listFiles) {
                deleteDir(file2);
            }
        }
        file.delete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class UninstalledInstantAppState {
        final android.content.pm.InstantAppInfo mInstantAppInfo;
        final long mTimestamp;

        public UninstalledInstantAppState(android.content.pm.InstantAppInfo instantAppInfo, long j) {
            this.mInstantAppInfo = instantAppInfo;
            this.mTimestamp = j;
        }
    }

    private final class CookiePersistence extends android.os.Handler {
        private static final long PERSIST_COOKIE_DELAY_MILLIS = 1000;
        private final android.util.SparseArray<android.util.ArrayMap<java.lang.String, com.android.internal.os.SomeArgs>> mPendingPersistCookies;

        public CookiePersistence(android.os.Looper looper) {
            super(looper);
            this.mPendingPersistCookies = new android.util.SparseArray<>();
        }

        public void schedulePersistLPw(int i, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull byte[] bArr) {
            java.io.File computeInstantCookieFile = com.android.server.pm.InstantAppRegistry.computeInstantCookieFile(androidPackage.getPackageName(), android.util.PackageUtils.computeSignaturesSha256Digest(androidPackage.getSigningDetails().getSignatures()), i);
            if (!androidPackage.getSigningDetails().hasSignatures()) {
                android.util.Slog.wtf(com.android.server.pm.InstantAppRegistry.LOG_TAG, "Parsed Instant App contains no valid signatures!");
            }
            java.io.File peekInstantCookieFile = com.android.server.pm.InstantAppRegistry.peekInstantCookieFile(androidPackage.getPackageName(), i);
            if (peekInstantCookieFile != null && !computeInstantCookieFile.equals(peekInstantCookieFile)) {
                peekInstantCookieFile.delete();
            }
            cancelPendingPersistLPw(androidPackage, i);
            addPendingPersistCookieLPw(i, androidPackage, bArr, computeInstantCookieFile);
            sendMessageDelayed(obtainMessage(i, androidPackage), 1000L);
        }

        @android.annotation.Nullable
        public byte[] getPendingPersistCookieLPr(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
            com.android.internal.os.SomeArgs someArgs;
            android.util.ArrayMap<java.lang.String, com.android.internal.os.SomeArgs> arrayMap = this.mPendingPersistCookies.get(i);
            if (arrayMap != null && (someArgs = arrayMap.get(androidPackage.getPackageName())) != null) {
                return (byte[]) someArgs.arg1;
            }
            return null;
        }

        public void cancelPendingPersistLPw(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
            removeMessages(i, androidPackage);
            com.android.internal.os.SomeArgs removePendingPersistCookieLPr = removePendingPersistCookieLPr(androidPackage, i);
            if (removePendingPersistCookieLPr != null) {
                removePendingPersistCookieLPr.recycle();
            }
        }

        private void addPendingPersistCookieLPw(int i, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull java.io.File file) {
            android.util.ArrayMap<java.lang.String, com.android.internal.os.SomeArgs> arrayMap = this.mPendingPersistCookies.get(i);
            if (arrayMap == null) {
                arrayMap = new android.util.ArrayMap<>();
                this.mPendingPersistCookies.put(i, arrayMap);
            }
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = bArr;
            obtain.arg2 = file;
            arrayMap.put(androidPackage.getPackageName(), obtain);
        }

        private com.android.internal.os.SomeArgs removePendingPersistCookieLPr(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
            android.util.ArrayMap<java.lang.String, com.android.internal.os.SomeArgs> arrayMap = this.mPendingPersistCookies.get(i);
            if (arrayMap == null) {
                return null;
            }
            com.android.internal.os.SomeArgs remove = arrayMap.remove(androidPackage.getPackageName());
            if (arrayMap.isEmpty()) {
                this.mPendingPersistCookies.remove(i);
                return remove;
            }
            return remove;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = message.what;
            com.android.server.pm.pkg.AndroidPackage androidPackage = (com.android.server.pm.pkg.AndroidPackage) message.obj;
            com.android.internal.os.SomeArgs removePendingPersistCookieLPr = removePendingPersistCookieLPr(androidPackage, i);
            if (removePendingPersistCookieLPr == null) {
                return;
            }
            byte[] bArr = (byte[]) removePendingPersistCookieLPr.arg1;
            java.io.File file = (java.io.File) removePendingPersistCookieLPr.arg2;
            removePendingPersistCookieLPr.recycle();
            com.android.server.pm.InstantAppRegistry.this.persistInstantApplicationCookie(bArr, androidPackage.getPackageName(), file, i);
        }
    }
}
