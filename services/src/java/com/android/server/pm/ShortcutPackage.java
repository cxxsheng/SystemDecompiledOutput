package com.android.server.pm;

/* loaded from: classes2.dex */
class ShortcutPackage extends com.android.server.pm.ShortcutPackageItem {
    private static final java.lang.String ATTR_ACTIVITY = "activity";
    private static final java.lang.String ATTR_BITMAP_PATH = "bitmap-path";
    private static final java.lang.String ATTR_CALL_COUNT = "call-count";
    private static final java.lang.String ATTR_DISABLED_MESSAGE = "dmessage";
    private static final java.lang.String ATTR_DISABLED_MESSAGE_RES_ID = "dmessageid";
    private static final java.lang.String ATTR_DISABLED_MESSAGE_RES_NAME = "dmessagename";
    private static final java.lang.String ATTR_DISABLED_REASON = "disabled-reason";
    private static final java.lang.String ATTR_FLAGS = "flags";
    private static final java.lang.String ATTR_ICON_RES_ID = "icon-res";
    private static final java.lang.String ATTR_ICON_RES_NAME = "icon-resname";
    private static final java.lang.String ATTR_ICON_URI = "icon-uri";
    private static final java.lang.String ATTR_ID = "id";
    private static final java.lang.String ATTR_INTENT_LEGACY = "intent";
    private static final java.lang.String ATTR_INTENT_NO_EXTRA = "intent-base";
    private static final java.lang.String ATTR_LAST_RESET = "last-reset";
    private static final java.lang.String ATTR_LOCUS_ID = "locus-id";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_NAME_XMLUTILS = "name";
    private static final java.lang.String ATTR_PERSON_IS_BOT = "is-bot";
    private static final java.lang.String ATTR_PERSON_IS_IMPORTANT = "is-important";
    private static final java.lang.String ATTR_PERSON_KEY = "key";
    private static final java.lang.String ATTR_PERSON_NAME = "name";
    private static final java.lang.String ATTR_PERSON_URI = "uri";
    private static final java.lang.String ATTR_RANK = "rank";
    private static final java.lang.String ATTR_SCHEMA_VERSON = "schema-version";
    private static final java.lang.String ATTR_SPLASH_SCREEN_THEME_NAME = "splash-screen-theme-name";
    private static final java.lang.String ATTR_TEXT = "text";
    private static final java.lang.String ATTR_TEXT_RES_ID = "textid";
    private static final java.lang.String ATTR_TEXT_RES_NAME = "textname";
    private static final java.lang.String ATTR_TIMESTAMP = "timestamp";
    private static final java.lang.String ATTR_TITLE = "title";
    private static final java.lang.String ATTR_TITLE_RES_ID = "titleid";
    private static final java.lang.String ATTR_TITLE_RES_NAME = "titlename";
    private static final java.lang.String KEY_BITMAPS = "bitmaps";
    private static final java.lang.String KEY_BITMAP_BYTES = "bitmapBytes";
    private static final java.lang.String KEY_DYNAMIC = "dynamic";
    private static final java.lang.String KEY_MANIFEST = "manifest";
    private static final java.lang.String KEY_PINNED = "pinned";
    private static final java.lang.String NAME_CAPABILITY = "capability";
    private static final java.lang.String NAME_CATEGORIES = "categories";
    private static final java.lang.String TAG = "ShortcutService";
    private static final java.lang.String TAG_CATEGORIES = "categories";
    private static final java.lang.String TAG_EXTRAS = "extras";
    private static final java.lang.String TAG_INTENT = "intent";
    private static final java.lang.String TAG_INTENT_EXTRAS_LEGACY = "intent-extras";
    private static final java.lang.String TAG_MAP_XMLUTILS = "map";
    private static final java.lang.String TAG_PERSON = "person";
    static final java.lang.String TAG_ROOT = "package";
    private static final java.lang.String TAG_SHARE_TARGET = "share-target";
    private static final java.lang.String TAG_SHORTCUT = "shortcut";
    private static final java.lang.String TAG_STRING_ARRAY_XMLUTILS = "string-array";
    private static final java.lang.String TAG_VERIFY = "ShortcutService.verify";
    private int mApiCallCount;
    private final java.util.concurrent.Executor mExecutor;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsAppSearchSchemaUpToDate;
    private long mLastKnownForegroundElapsedTime;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastReportedTime;
    private long mLastResetTime;
    private final int mPackageUid;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.pm.ShareTargetInfo> mShareTargets;
    final java.util.Comparator<android.content.pm.ShortcutInfo> mShortcutRankComparator;
    final java.util.Comparator<android.content.pm.ShortcutInfo> mShortcutTypeAndRankComparator;
    final java.util.Comparator<android.content.pm.ShortcutInfo> mShortcutTypeRankAndTimeComparator;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, android.content.pm.ShortcutInfo> mShortcuts;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, android.content.pm.ShortcutInfo> mTransientShortcuts;

    private ShortcutPackage(com.android.server.pm.ShortcutUser shortcutUser, int i, java.lang.String str, com.android.server.pm.ShortcutPackageInfo shortcutPackageInfo) {
        super(shortcutUser, i, str, shortcutPackageInfo == null ? com.android.server.pm.ShortcutPackageInfo.newEmpty() : shortcutPackageInfo);
        this.mShortcuts = new android.util.ArrayMap<>();
        this.mTransientShortcuts = new android.util.ArrayMap<>(0);
        this.mShareTargets = new java.util.ArrayList<>(0);
        this.mShortcutTypeAndRankComparator = new java.util.Comparator() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda36
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$new$19;
                lambda$new$19 = com.android.server.pm.ShortcutPackage.lambda$new$19((android.content.pm.ShortcutInfo) obj, (android.content.pm.ShortcutInfo) obj2);
                return lambda$new$19;
            }
        };
        this.mShortcutTypeRankAndTimeComparator = new java.util.Comparator() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda37
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$new$20;
                lambda$new$20 = com.android.server.pm.ShortcutPackage.lambda$new$20((android.content.pm.ShortcutInfo) obj, (android.content.pm.ShortcutInfo) obj2);
                return lambda$new$20;
            }
        };
        this.mShortcutRankComparator = new java.util.Comparator() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda38
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$new$25;
                lambda$new$25 = com.android.server.pm.ShortcutPackage.lambda$new$25((android.content.pm.ShortcutInfo) obj, (android.content.pm.ShortcutInfo) obj2);
                return lambda$new$25;
            }
        };
        this.mPackageUid = shortcutUser.mService.injectGetPackageUid(str, i);
        this.mExecutor = com.android.internal.os.BackgroundThread.getExecutor();
    }

    public ShortcutPackage(com.android.server.pm.ShortcutUser shortcutUser, int i, java.lang.String str) {
        this(shortcutUser, i, str, null);
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public int getOwnerUserId() {
        return getPackageUserId();
    }

    public int getPackageUid() {
        return this.mPackageUid;
    }

    @android.annotation.Nullable
    public android.content.res.Resources getPackageResources() {
        return this.mShortcutUser.mService.injectGetResourcesForApplicationAsUser(getPackageName(), getPackageUserId());
    }

    private boolean isAppSearchEnabled() {
        return this.mShortcutUser.mService.isAppSearchEnabled();
    }

    public int getShortcutCount() {
        int size;
        synchronized (this.mLock) {
            size = this.mShortcuts.size();
        }
        return size;
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    protected boolean canRestoreAnyVersion() {
        return true;
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    protected void onRestored(final int i) {
        java.lang.String.format("%s:-%s AND %s:%s", ATTR_FLAGS, 4096, "disabledReason", java.lang.Integer.valueOf(i));
        forEachShortcutMutate(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda41
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$onRestored$0(i, (android.content.pm.ShortcutInfo) obj);
            }
        });
        refreshPinnedFlags();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onRestored$0(int i, android.content.pm.ShortcutInfo shortcutInfo) {
        if (i == 0 && !shortcutInfo.hasFlags(4096) && shortcutInfo.getDisabledReason() == i) {
            return;
        }
        shortcutInfo.clearFlags(4096);
        shortcutInfo.setDisabledReason(i);
        if (i != 0) {
            shortcutInfo.addFlags(64);
        }
    }

    @android.annotation.Nullable
    public android.content.pm.ShortcutInfo findShortcutById(@android.annotation.Nullable java.lang.String str) {
        android.content.pm.ShortcutInfo shortcutInfo;
        if (str == null) {
            return null;
        }
        synchronized (this.mLock) {
            shortcutInfo = this.mShortcuts.get(str);
        }
        return shortcutInfo;
    }

    public boolean isShortcutExistsAndInvisibleToPublisher(java.lang.String str) {
        android.content.pm.ShortcutInfo findShortcutById = findShortcutById(str);
        return (findShortcutById == null || findShortcutById.isVisibleToPublisher()) ? false : true;
    }

    public boolean isShortcutExistsAndVisibleToPublisher(java.lang.String str) {
        android.content.pm.ShortcutInfo findShortcutById = findShortcutById(str);
        return findShortcutById != null && findShortcutById.isVisibleToPublisher();
    }

    private void ensureNotImmutable(@android.annotation.Nullable android.content.pm.ShortcutInfo shortcutInfo, boolean z) {
        if (shortcutInfo != null && shortcutInfo.isImmutable()) {
            if (!z || shortcutInfo.isVisibleToPublisher()) {
                throw new java.lang.IllegalArgumentException("Manifest shortcut ID=" + shortcutInfo.getId() + " may not be manipulated via APIs");
            }
        }
    }

    public void ensureNotImmutable(@android.annotation.NonNull java.lang.String str, boolean z) {
        ensureNotImmutable(findShortcutById(str), z);
    }

    public void ensureImmutableShortcutsNotIncludedWithIds(@android.annotation.NonNull java.util.List<java.lang.String> list, boolean z) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ensureNotImmutable(list.get(size), z);
        }
    }

    public void ensureImmutableShortcutsNotIncluded(@android.annotation.NonNull java.util.List<android.content.pm.ShortcutInfo> list, boolean z) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ensureNotImmutable(list.get(size).getId(), z);
        }
    }

    public void ensureNoBitmapIconIfShortcutIsLongLived(@android.annotation.NonNull java.util.List<android.content.pm.ShortcutInfo> list) {
        android.graphics.drawable.Icon icon;
        for (int size = list.size() - 1; size >= 0; size--) {
            android.content.pm.ShortcutInfo shortcutInfo = list.get(size);
            if (shortcutInfo.isLongLived() && (((icon = shortcutInfo.getIcon()) == null || icon.getType() == 1 || icon.getType() == 5) && (icon != null || shortcutInfo.hasIconFile()))) {
                android.util.Slog.e(TAG, "Invalid icon type in shortcut " + shortcutInfo.getId() + ". Bitmaps are not allowed in long-lived shortcuts. Use Resource icons, or Uri-based icons instead.");
                return;
            }
        }
    }

    public void ensureAllShortcutsVisibleToLauncher(@android.annotation.NonNull java.util.List<android.content.pm.ShortcutInfo> list) {
        for (android.content.pm.ShortcutInfo shortcutInfo : list) {
            if (shortcutInfo.isExcludedFromSurfaces(1)) {
                throw new java.lang.IllegalArgumentException("Shortcut ID=" + shortcutInfo.getId() + " is hidden from launcher and may not be manipulated via APIs");
            }
        }
    }

    private android.content.pm.ShortcutInfo forceDeleteShortcutInner(@android.annotation.NonNull java.lang.String str) {
        android.content.pm.ShortcutInfo remove;
        synchronized (this.mLock) {
            try {
                remove = this.mShortcuts.remove(str);
                if (remove != null) {
                    removeIcon(remove);
                    remove.clearFlags(1610629155);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return remove;
    }

    private void forceReplaceShortcutInner(@android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo) {
        com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
        forceDeleteShortcutInner(shortcutInfo.getId());
        shortcutService.saveIconAndFixUpShortcutLocked(this, shortcutInfo);
        shortcutService.fixUpShortcutResourceNamesAndValues(shortcutInfo);
        ensureShortcutCountBeforePush();
        saveShortcut(shortcutInfo);
    }

    public boolean addOrReplaceDynamicShortcut(@android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo) {
        com.android.internal.util.Preconditions.checkArgument(shortcutInfo.isEnabled(), "add/setDynamicShortcuts() cannot publish disabled shortcuts");
        shortcutInfo.addFlags(1);
        android.content.pm.ShortcutInfo findShortcutById = findShortcutById(shortcutInfo.getId());
        if (findShortcutById != null) {
            findShortcutById.ensureUpdatableWith(shortcutInfo, false);
            shortcutInfo.addFlags(findShortcutById.getFlags() & 1610629122);
        }
        if (shortcutInfo.isExcludedFromSurfaces(1)) {
            if (isAppSearchEnabled()) {
                synchronized (this.mLock) {
                    this.mTransientShortcuts.put(shortcutInfo.getId(), shortcutInfo);
                }
            }
        } else {
            forceReplaceShortcutInner(shortcutInfo);
        }
        if (findShortcutById != null) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x00a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean pushDynamicShortcut(@android.annotation.NonNull final android.content.pm.ShortcutInfo shortcutInfo, @android.annotation.NonNull java.util.List<android.content.pm.ShortcutInfo> list) {
        boolean z;
        com.android.internal.util.Preconditions.checkArgument(shortcutInfo.isEnabled(), "pushDynamicShortcuts() cannot publish disabled shortcuts");
        shortcutInfo.addFlags(1);
        list.clear();
        android.content.pm.ShortcutInfo findShortcutById = findShortcutById(shortcutInfo.getId());
        if (findShortcutById == null || !findShortcutById.isDynamic()) {
            com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
            int maxActivityShortcuts = shortcutService.getMaxActivityShortcuts();
            java.util.ArrayList<android.content.pm.ShortcutInfo> arrayList = sortShortcutsToActivities().get(shortcutInfo.getActivity());
            if (arrayList != null && arrayList.size() > maxActivityShortcuts) {
                shortcutService.wtf("Error pushing shortcut. There are already " + arrayList.size() + " shortcuts.");
            }
            if (arrayList != null && arrayList.size() == maxActivityShortcuts) {
                java.util.Collections.sort(arrayList, this.mShortcutTypeAndRankComparator);
                android.content.pm.ShortcutInfo shortcutInfo2 = arrayList.get(maxActivityShortcuts - 1);
                if (shortcutInfo2.isManifestShortcut()) {
                    android.util.Slog.e(TAG, "Failed to remove manifest shortcut while pushing dynamic shortcut " + shortcutInfo.getId());
                    return true;
                }
                list.add(shortcutInfo2);
                if (deleteDynamicWithId(shortcutInfo2.getId(), true, true) != null) {
                    z = true;
                } else {
                    z = false;
                }
                if (findShortcutById != null) {
                    findShortcutById.ensureUpdatableWith(shortcutInfo, false);
                    shortcutInfo.addFlags(findShortcutById.getFlags() & 1610629122);
                }
                if (!shortcutInfo.isExcludedFromSurfaces(1)) {
                    if (isAppSearchEnabled()) {
                        synchronized (this.mLock) {
                            this.mTransientShortcuts.put(shortcutInfo.getId(), shortcutInfo);
                        }
                    }
                } else {
                    forceReplaceShortcutInner(shortcutInfo);
                }
                if (isAppSearchEnabled()) {
                    runAsSystem(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda47
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.pm.ShortcutPackage.this.lambda$pushDynamicShortcut$3(shortcutInfo);
                        }
                    });
                }
                return z;
            }
        }
        z = false;
        if (findShortcutById != null) {
        }
        if (!shortcutInfo.isExcludedFromSurfaces(1)) {
        }
        if (isAppSearchEnabled()) {
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pushDynamicShortcut$3(final android.content.pm.ShortcutInfo shortcutInfo) {
        fromAppSearch().thenAccept(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$pushDynamicShortcut$2(shortcutInfo, (android.app.appsearch.AppSearchSession) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pushDynamicShortcut$2(android.content.pm.ShortcutInfo shortcutInfo, android.app.appsearch.AppSearchSession appSearchSession) {
        appSearchSession.reportUsage(new android.app.appsearch.ReportUsageRequest.Builder(getPackageName(), shortcutInfo.getId()).build(), this.mExecutor, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$pushDynamicShortcut$1((android.app.appsearch.AppSearchResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pushDynamicShortcut$1(android.app.appsearch.AppSearchResult appSearchResult) {
        if (!appSearchResult.isSuccess()) {
            android.util.Slog.e(TAG, "Failed to report usage via AppSearch. " + appSearchResult.getErrorMessage());
        }
    }

    private void ensureShortcutCountBeforePush() {
        int maxAppShortcuts = this.mShortcutUser.mService.getMaxAppShortcuts();
        synchronized (this.mLock) {
            try {
                java.util.List list = (java.util.List) this.mShortcuts.values().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda48
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$ensureShortcutCountBeforePush$4;
                        lambda$ensureShortcutCountBeforePush$4 = com.android.server.pm.ShortcutPackage.lambda$ensureShortcutCountBeforePush$4((android.content.pm.ShortcutInfo) obj);
                        return lambda$ensureShortcutCountBeforePush$4;
                    }
                }).collect(java.util.stream.Collectors.toList());
                if (list.size() >= maxAppShortcuts) {
                    java.util.Collections.sort(list, this.mShortcutTypeRankAndTimeComparator);
                    while (list.size() >= maxAppShortcuts) {
                        android.content.pm.ShortcutInfo shortcutInfo = (android.content.pm.ShortcutInfo) list.remove(list.size() - 1);
                        if (shortcutInfo.isDeclaredInManifest()) {
                            throw new java.lang.IllegalArgumentException(getPackageName() + " has published " + list.size() + " manifest shortcuts across different activities.");
                        }
                        forceDeleteShortcutInner(shortcutInfo.getId());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$ensureShortcutCountBeforePush$4(android.content.pm.ShortcutInfo shortcutInfo) {
        return !shortcutInfo.isPinned();
    }

    private java.util.List<android.content.pm.ShortcutInfo> removeOrphans() {
        final java.util.ArrayList arrayList = new java.util.ArrayList(1);
        forEachShortcut(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$removeOrphans$5(arrayList, (android.content.pm.ShortcutInfo) obj);
            }
        });
        if (!arrayList.isEmpty()) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                forceDeleteShortcutInner(((android.content.pm.ShortcutInfo) arrayList.get(size)).getId());
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeOrphans$5(java.util.List list, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo.isAlive()) {
            return;
        }
        list.add(shortcutInfo);
    }

    public java.util.List<android.content.pm.ShortcutInfo> deleteAllDynamicShortcuts() {
        boolean z;
        long injectCurrentTimeMillis = this.mShortcutUser.mService.injectCurrentTimeMillis();
        synchronized (this.mLock) {
            try {
                z = false;
                for (int size = this.mShortcuts.size() - 1; size >= 0; size--) {
                    android.content.pm.ShortcutInfo valueAt = this.mShortcuts.valueAt(size);
                    if (valueAt.isDynamic() && valueAt.isVisibleToPublisher()) {
                        valueAt.setTimestamp(injectCurrentTimeMillis);
                        valueAt.clearFlags(1);
                        valueAt.setRank(0);
                        z = true;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        removeAllShortcutsAsync();
        if (z) {
            return removeOrphans();
        }
        return null;
    }

    public android.content.pm.ShortcutInfo deleteDynamicWithId(@android.annotation.NonNull java.lang.String str, boolean z, boolean z2) {
        return deleteOrDisableWithId(str, false, false, z, 0, z2);
    }

    private android.content.pm.ShortcutInfo disableDynamicWithId(@android.annotation.NonNull java.lang.String str, boolean z, int i, boolean z2) {
        return deleteOrDisableWithId(str, true, false, z, i, z2);
    }

    public android.content.pm.ShortcutInfo deleteLongLivedWithId(@android.annotation.NonNull java.lang.String str, boolean z) {
        if (findShortcutById(str) != null) {
            mutateShortcut(str, null, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda46
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.content.pm.ShortcutInfo) obj).clearFlags(1610629120);
                }
            });
        }
        return deleteOrDisableWithId(str, false, false, z, 0, false);
    }

    public android.content.pm.ShortcutInfo disableWithId(@android.annotation.NonNull java.lang.String str, final java.lang.String str2, final int i, boolean z, boolean z2, int i2) {
        android.content.pm.ShortcutInfo deleteOrDisableWithId = deleteOrDisableWithId(str, true, z, z2, i2, false);
        mutateShortcut(str, null, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda32
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$disableWithId$7(str2, i, (android.content.pm.ShortcutInfo) obj);
            }
        });
        return deleteOrDisableWithId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disableWithId$7(java.lang.String str, int i, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo != null) {
            if (str != null) {
                shortcutInfo.setDisabledMessage(str);
            } else if (i != 0) {
                shortcutInfo.setDisabledMessageResId(i);
                this.mShortcutUser.mService.fixUpShortcutResourceNamesAndValues(shortcutInfo);
            }
        }
    }

    @android.annotation.Nullable
    private android.content.pm.ShortcutInfo deleteOrDisableWithId(@android.annotation.NonNull java.lang.String str, final boolean z, boolean z2, boolean z3, final int i, boolean z4) {
        com.android.internal.util.Preconditions.checkState(z == (i != 0), "disable and disabledReason disagree: " + z + " vs " + i);
        android.content.pm.ShortcutInfo findShortcutById = findShortcutById(str);
        if (findShortcutById == null || (!findShortcutById.isEnabled() && z3 && !findShortcutById.isVisibleToPublisher())) {
            return null;
        }
        if (!z2) {
            ensureNotImmutable(findShortcutById, true);
        }
        if (!z4) {
            removeShortcutAsync(str);
        }
        if (findShortcutById.isPinned() || findShortcutById.isCached()) {
            mutateShortcut(findShortcutById.getId(), findShortcutById, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda58
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.ShortcutPackage.this.lambda$deleteOrDisableWithId$8(z, i, (android.content.pm.ShortcutInfo) obj);
                }
            });
            return null;
        }
        forceDeleteShortcutInner(str);
        return findShortcutById;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteOrDisableWithId$8(boolean z, int i, android.content.pm.ShortcutInfo shortcutInfo) {
        shortcutInfo.setRank(0);
        shortcutInfo.clearFlags(33);
        if (z) {
            shortcutInfo.addFlags(64);
            if (shortcutInfo.getDisabledReason() == 0) {
                shortcutInfo.setDisabledReason(i);
            }
        }
        shortcutInfo.setTimestamp(this.mShortcutUser.mService.injectCurrentTimeMillis());
        if (this.mShortcutUser.mService.isDummyMainActivity(shortcutInfo.getActivity())) {
            shortcutInfo.setActivity(null);
        }
    }

    public void enableWithId(@android.annotation.NonNull java.lang.String str) {
        mutateShortcut(str, null, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda39
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$enableWithId$9((android.content.pm.ShortcutInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enableWithId$9(android.content.pm.ShortcutInfo shortcutInfo) {
        ensureNotImmutable(shortcutInfo, true);
        shortcutInfo.clearFlags(64);
        shortcutInfo.setDisabledReason(0);
    }

    public void updateInvisibleShortcutForPinRequestWith(@android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo) {
        java.util.Objects.requireNonNull(findShortcutById(shortcutInfo.getId()));
        this.mShortcutUser.mService.validateShortcutForPinRequest(shortcutInfo);
        shortcutInfo.addFlags(2);
        forceReplaceShortcutInner(shortcutInfo);
        adjustRanks();
    }

    public void refreshPinnedFlags() {
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        this.mShortcutUser.forAllLaunchers(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$refreshPinnedFlags$10(arraySet, (com.android.server.pm.ShortcutLauncher) obj);
            }
        });
        java.util.List<android.content.pm.ShortcutInfo> findAll = findAll(arraySet);
        if (findAll != null) {
            findAll.forEach(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda22
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.ShortcutPackage.lambda$refreshPinnedFlags$11((android.content.pm.ShortcutInfo) obj);
                }
            });
        }
        forEachShortcutMutate(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda23
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$refreshPinnedFlags$12(arraySet, (android.content.pm.ShortcutInfo) obj);
            }
        });
        this.mShortcutUser.forAllLaunchers(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda24
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.pm.ShortcutLauncher) obj).scheduleSave();
            }
        });
        removeOrphans();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshPinnedFlags$10(java.util.Set set, com.android.server.pm.ShortcutLauncher shortcutLauncher) {
        android.util.ArraySet<java.lang.String> pinnedShortcutIds = shortcutLauncher.getPinnedShortcutIds(getPackageName(), getPackageUserId());
        if (pinnedShortcutIds == null || pinnedShortcutIds.size() == 0) {
            return;
        }
        set.addAll(pinnedShortcutIds);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$refreshPinnedFlags$11(android.content.pm.ShortcutInfo shortcutInfo) {
        if (!shortcutInfo.isPinned()) {
            shortcutInfo.addFlags(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$refreshPinnedFlags$12(java.util.Set set, android.content.pm.ShortcutInfo shortcutInfo) {
        if (!set.contains(shortcutInfo.getId()) && shortcutInfo.isPinned()) {
            shortcutInfo.clearFlags(2);
        }
    }

    public int getApiCallCount(boolean z) {
        com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
        if (shortcutService.isUidForegroundLocked(this.mPackageUid) || this.mLastKnownForegroundElapsedTime < shortcutService.getUidLastForegroundElapsedTimeLocked(this.mPackageUid) || z) {
            this.mLastKnownForegroundElapsedTime = shortcutService.injectElapsedRealtime();
            resetRateLimiting();
        }
        long lastResetTimeLocked = shortcutService.getLastResetTimeLocked();
        long injectCurrentTimeMillis = shortcutService.injectCurrentTimeMillis();
        if (com.android.server.pm.ShortcutService.isClockValid(injectCurrentTimeMillis) && this.mLastResetTime > injectCurrentTimeMillis) {
            android.util.Slog.w(TAG, "Clock rewound");
            this.mLastResetTime = injectCurrentTimeMillis;
            this.mApiCallCount = 0;
            return this.mApiCallCount;
        }
        if (this.mLastResetTime < lastResetTimeLocked) {
            this.mApiCallCount = 0;
            this.mLastResetTime = lastResetTimeLocked;
        }
        return this.mApiCallCount;
    }

    public boolean tryApiCall(boolean z) {
        if (getApiCallCount(z) >= this.mShortcutUser.mService.mMaxUpdatesPerInterval) {
            return false;
        }
        this.mApiCallCount++;
        scheduleSave();
        return true;
    }

    public void resetRateLimiting() {
        if (this.mApiCallCount > 0) {
            this.mApiCallCount = 0;
            scheduleSave();
        }
    }

    public void resetRateLimitingForCommandLineNoSaving() {
        this.mApiCallCount = 0;
        this.mLastResetTime = 0L;
    }

    public void findAll(@android.annotation.NonNull java.util.List<android.content.pm.ShortcutInfo> list, @android.annotation.Nullable java.util.function.Predicate<android.content.pm.ShortcutInfo> predicate, int i) {
        findAll(list, predicate, i, null, 0, false);
    }

    public void findAll(@android.annotation.NonNull final java.util.List<android.content.pm.ShortcutInfo> list, @android.annotation.Nullable final java.util.function.Predicate<android.content.pm.ShortcutInfo> predicate, final int i, @android.annotation.Nullable final java.lang.String str, int i2, final boolean z) {
        if (getPackageInfo().isShadow()) {
            return;
        }
        final android.util.ArraySet<java.lang.String> pinnedShortcutIds = str == null ? null : this.mShortcutUser.mService.getLauncherShortcutsLocked(str, getPackageUserId(), i2).getPinnedShortcutIds(getPackageName(), getPackageUserId());
        forEachShortcut(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$findAll$13(list, predicate, i, str, pinnedShortcutIds, z, (android.content.pm.ShortcutInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: filter, reason: merged with bridge method [inline-methods] */
    public void lambda$findAll$13(@android.annotation.NonNull java.util.List<android.content.pm.ShortcutInfo> list, @android.annotation.Nullable java.util.function.Predicate<android.content.pm.ShortcutInfo> predicate, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, boolean z, @android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo) {
        boolean z2 = str == null || (arraySet != null && arraySet.contains(shortcutInfo.getId()));
        if (!z && shortcutInfo.isFloating() && !shortcutInfo.isCached() && !z2) {
            return;
        }
        android.content.pm.ShortcutInfo clone = shortcutInfo.clone(i);
        if (!z && !z2) {
            clone.clearFlags(2);
        }
        if (predicate == null || predicate.test(clone)) {
            if (!z2) {
                clone.clearFlags(2);
            }
            list.add(clone);
        }
    }

    public void resetThrottling() {
        this.mApiCallCount = 0;
    }

    public java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> getMatchingShareTargets(@android.annotation.NonNull android.content.IntentFilter intentFilter) {
        return getMatchingShareTargets(intentFilter, null);
    }

    java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> getMatchingShareTargets(@android.annotation.NonNull android.content.IntentFilter intentFilter, @android.annotation.Nullable java.lang.String str) {
        boolean z;
        synchronized (this.mLock) {
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i = 0; i < this.mShareTargets.size(); i++) {
                    com.android.server.pm.ShareTargetInfo shareTargetInfo = this.mShareTargets.get(i);
                    com.android.server.pm.ShareTargetInfo.TargetData[] targetDataArr = shareTargetInfo.mTargetData;
                    int length = targetDataArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        if (!intentFilter.hasDataType(targetDataArr[i2].mMimeType)) {
                            i2++;
                        } else {
                            arrayList.add(shareTargetInfo);
                            break;
                        }
                    }
                }
                if (arrayList.isEmpty()) {
                    return new java.util.ArrayList();
                }
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                findAll(arrayList2, new com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda18(), 9, str, 0, false);
                java.util.ArrayList arrayList3 = new java.util.ArrayList();
                for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                    java.util.Set<java.lang.String> categories = ((android.content.pm.ShortcutInfo) arrayList2.get(i3)).getCategories();
                    if (categories != null && !categories.isEmpty()) {
                        int i4 = 0;
                        while (true) {
                            if (i4 < arrayList.size()) {
                                com.android.server.pm.ShareTargetInfo shareTargetInfo2 = (com.android.server.pm.ShareTargetInfo) arrayList.get(i4);
                                int i5 = 0;
                                while (true) {
                                    if (i5 >= shareTargetInfo2.mCategories.length) {
                                        z = true;
                                        break;
                                    }
                                    if (categories.contains(shareTargetInfo2.mCategories[i5])) {
                                        i5++;
                                    } else {
                                        z = false;
                                        break;
                                    }
                                }
                                if (!z) {
                                    i4++;
                                } else {
                                    arrayList3.add(new android.content.pm.ShortcutManager.ShareShortcutInfo((android.content.pm.ShortcutInfo) arrayList2.get(i3), new android.content.ComponentName(getPackageName(), shareTargetInfo2.mTargetClass)));
                                    break;
                                }
                            }
                        }
                    }
                }
                return arrayList3;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean hasShareTargets() {
        boolean z;
        synchronized (this.mLock) {
            z = !this.mShareTargets.isEmpty();
        }
        return z;
    }

    int getSharingShortcutCount() {
        boolean z;
        synchronized (this.mLock) {
            try {
                if (this.mShareTargets.isEmpty()) {
                    return 0;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                findAll(arrayList, new com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda18(), 27);
                int i = 0;
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    java.util.Set<java.lang.String> categories = ((android.content.pm.ShortcutInfo) arrayList.get(i2)).getCategories();
                    if (categories != null && !categories.isEmpty()) {
                        int i3 = 0;
                        while (true) {
                            if (i3 < this.mShareTargets.size()) {
                                com.android.server.pm.ShareTargetInfo shareTargetInfo = this.mShareTargets.get(i3);
                                int i4 = 0;
                                while (true) {
                                    if (i4 >= shareTargetInfo.mCategories.length) {
                                        z = true;
                                        break;
                                    }
                                    if (categories.contains(shareTargetInfo.mCategories[i4])) {
                                        i4++;
                                    } else {
                                        z = false;
                                        break;
                                    }
                                }
                                if (!z) {
                                    i3++;
                                } else {
                                    i++;
                                    break;
                                }
                            }
                        }
                    }
                }
                return i;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArraySet<java.lang.String> getUsedBitmapFilesLocked() {
        final android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>(1);
        forEachShortcut(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda57
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$getUsedBitmapFilesLocked$14(arraySet, (android.content.pm.ShortcutInfo) obj);
            }
        });
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getUsedBitmapFilesLocked$14(android.util.ArraySet arraySet, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo.getBitmapPath() != null) {
            arraySet.add(getFileName(shortcutInfo.getBitmapPath()));
        }
    }

    public void cleanupDanglingBitmapFiles(@android.annotation.NonNull java.io.File file) {
        synchronized (this.mLock) {
            try {
                this.mShortcutBitmapSaver.waitForAllSavesLocked();
                android.util.ArraySet<java.lang.String> usedBitmapFilesLocked = getUsedBitmapFilesLocked();
                for (java.io.File file2 : file.listFiles()) {
                    if (file2.isFile() && !usedBitmapFilesLocked.contains(file2.getName())) {
                        file2.delete();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static java.lang.String getFileName(@android.annotation.NonNull java.lang.String str) {
        int lastIndexOf = str.lastIndexOf(java.io.File.separatorChar);
        if (lastIndexOf == -1) {
            return str;
        }
        return str.substring(lastIndexOf + 1);
    }

    private boolean areAllActivitiesStillEnabled() {
        final com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
        final java.util.ArrayList arrayList = new java.util.ArrayList(4);
        final boolean[] zArr = new boolean[1];
        forEachShortcutStopWhen(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda19
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$areAllActivitiesStillEnabled$15;
                lambda$areAllActivitiesStillEnabled$15 = com.android.server.pm.ShortcutPackage.this.lambda$areAllActivitiesStillEnabled$15(arrayList, shortcutService, zArr, (android.content.pm.ShortcutInfo) obj);
                return lambda$areAllActivitiesStillEnabled$15;
            }
        });
        return !zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$areAllActivitiesStillEnabled$15(java.util.ArrayList arrayList, com.android.server.pm.ShortcutService shortcutService, boolean[] zArr, android.content.pm.ShortcutInfo shortcutInfo) {
        android.content.ComponentName activity = shortcutInfo.getActivity();
        if (arrayList.contains(activity)) {
            return false;
        }
        arrayList.add(activity);
        if (activity == null || shortcutService.injectIsActivityEnabledAndExported(activity, getOwnerUserId())) {
            return false;
        }
        zArr[0] = true;
        return true;
    }

    public boolean rescanPackageIfNeeded(boolean z, boolean z2) {
        java.util.List<android.content.pm.ShortcutInfo> list;
        final com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
        long statStartTime = shortcutService.getStatStartTime();
        try {
            android.content.pm.PackageInfo packageInfo = this.mShortcutUser.mService.getPackageInfo(getPackageName(), getPackageUserId());
            if (packageInfo == null) {
                return false;
            }
            if (!z && !z2 && getPackageInfo().getVersionCode() == packageInfo.getLongVersionCode() && getPackageInfo().getLastUpdateTime() == packageInfo.lastUpdateTime) {
                if (areAllActivitiesStillEnabled()) {
                    return false;
                }
            }
            shortcutService.logDurationStat(14, statStartTime);
            synchronized (this.mLock) {
                try {
                    this.mShareTargets.size();
                    list = com.android.server.pm.ShortcutParser.parseShortcuts(this.mShortcutUser.mService, getPackageName(), getPackageUserId(), this.mShareTargets);
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Slog.e(TAG, "Failed to load shortcuts from AndroidManifest.xml.", e);
                    list = null;
                }
            }
            int size = list == null ? 0 : list.size();
            if (z && size == 0) {
                return false;
            }
            getPackageInfo().updateFromPackageInfo(packageInfo);
            final long versionCode = getPackageInfo().getVersionCode();
            forEachShortcutMutate(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.ShortcutPackage.this.lambda$rescanPackageIfNeeded$16(versionCode, (android.content.pm.ShortcutInfo) obj);
                }
            });
            if (!z) {
                final android.content.res.Resources packageResources = getPackageResources();
                forEachShortcutMutate(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda10
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.pm.ShortcutPackage.this.lambda$rescanPackageIfNeeded$17(shortcutService, packageResources, (android.content.pm.ShortcutInfo) obj);
                    }
                });
            }
            publishManifestShortcuts(list);
            if (list != null) {
                pushOutExcessShortcuts();
            }
            shortcutService.verifyStates();
            shortcutService.packageShortcutsChanged(this, null, null);
            return true;
        } finally {
            shortcutService.logDurationStat(14, statStartTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rescanPackageIfNeeded$16(long j, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo.getDisabledReason() != 100 || getPackageInfo().getBackupSourceVersionCode() > j) {
            return;
        }
        android.util.Slog.i(TAG, java.lang.String.format("Restoring shortcut: %s", shortcutInfo.getId()));
        shortcutInfo.clearFlags(64);
        shortcutInfo.setDisabledReason(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rescanPackageIfNeeded$17(com.android.server.pm.ShortcutService shortcutService, android.content.res.Resources resources, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo.isDynamic()) {
            if (shortcutInfo.getActivity() == null) {
                shortcutService.wtf("null activity detected.");
            } else if (!shortcutService.injectIsMainActivity(shortcutInfo.getActivity(), getPackageUserId())) {
                android.util.Slog.w(TAG, java.lang.String.format("%s is no longer main activity. Disabling shorcut %s.", getPackageName(), shortcutInfo.getId()));
                if (disableDynamicWithId(shortcutInfo.getId(), false, 2, false) != null) {
                    return;
                }
            }
        }
        if (!shortcutInfo.hasAnyResources() || resources == null) {
            return;
        }
        if (!shortcutInfo.isOriginallyFromManifest()) {
            shortcutInfo.lookupAndFillInResourceIds(resources);
        }
        shortcutInfo.setTimestamp(shortcutService.injectCurrentTimeMillis());
    }

    private boolean publishManifestShortcuts(java.util.List<android.content.pm.ShortcutInfo> list) {
        boolean z;
        final android.util.ArraySet arraySet = new android.util.ArraySet(1);
        forEachShortcut(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$publishManifestShortcuts$18(arraySet, (android.content.pm.ShortcutInfo) obj);
            }
        });
        boolean z2 = false;
        if (list != null) {
            int size = list.size();
            int i = 0;
            boolean z3 = false;
            while (i < size) {
                android.content.pm.ShortcutInfo shortcutInfo = list.get(i);
                boolean z4 = !shortcutInfo.isEnabled();
                java.lang.String id = shortcutInfo.getId();
                android.content.pm.ShortcutInfo findShortcutById = findShortcutById(id);
                if (findShortcutById != null) {
                    if (!findShortcutById.isOriginallyFromManifest()) {
                        android.util.Slog.e(TAG, "Shortcut with ID=" + shortcutInfo.getId() + " exists but is not from AndroidManifest.xml, not updating.");
                        i++;
                        z3 = true;
                    } else if (findShortcutById.isPinned()) {
                        shortcutInfo.addFlags(2);
                        z = true;
                        if (z4 || z) {
                            forceReplaceShortcutInner(shortcutInfo);
                            if (!z4 && !arraySet.isEmpty()) {
                                arraySet.remove(id);
                            }
                        }
                        i++;
                        z3 = true;
                    }
                }
                z = false;
                if (z4) {
                }
                forceReplaceShortcutInner(shortcutInfo);
                if (!z4) {
                    arraySet.remove(id);
                }
                i++;
                z3 = true;
            }
            z2 = z3;
        }
        if (!arraySet.isEmpty()) {
            int size2 = arraySet.size() - 1;
            while (size2 >= 0) {
                disableWithId((java.lang.String) arraySet.valueAt(size2), null, 0, true, false, 2);
                size2--;
                z2 = true;
            }
            removeOrphans();
        }
        adjustRanks();
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$publishManifestShortcuts$18(android.util.ArraySet arraySet, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo.isManifestShortcut()) {
            arraySet.add(shortcutInfo.getId());
        }
    }

    private boolean pushOutExcessShortcuts() {
        com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
        int maxActivityShortcuts = shortcutService.getMaxActivityShortcuts();
        android.util.ArrayMap<android.content.ComponentName, java.util.ArrayList<android.content.pm.ShortcutInfo>> sortShortcutsToActivities = sortShortcutsToActivities();
        for (int size = sortShortcutsToActivities.size() - 1; size >= 0; size--) {
            java.util.ArrayList<android.content.pm.ShortcutInfo> valueAt = sortShortcutsToActivities.valueAt(size);
            if (valueAt.size() > maxActivityShortcuts) {
                java.util.Collections.sort(valueAt, this.mShortcutTypeAndRankComparator);
                for (int size2 = valueAt.size() - 1; size2 >= maxActivityShortcuts; size2--) {
                    android.content.pm.ShortcutInfo shortcutInfo = valueAt.get(size2);
                    if (shortcutInfo.isManifestShortcut()) {
                        shortcutService.wtf("Found manifest shortcuts in excess list.");
                    } else {
                        deleteDynamicWithId(shortcutInfo.getId(), true, true);
                    }
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$19(android.content.pm.ShortcutInfo shortcutInfo, android.content.pm.ShortcutInfo shortcutInfo2) {
        if (shortcutInfo.isManifestShortcut() && !shortcutInfo2.isManifestShortcut()) {
            return -1;
        }
        if (!shortcutInfo.isManifestShortcut() && shortcutInfo2.isManifestShortcut()) {
            return 1;
        }
        return java.lang.Integer.compare(shortcutInfo.getRank(), shortcutInfo2.getRank());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$20(android.content.pm.ShortcutInfo shortcutInfo, android.content.pm.ShortcutInfo shortcutInfo2) {
        if (shortcutInfo.isDeclaredInManifest() && !shortcutInfo2.isDeclaredInManifest()) {
            return -1;
        }
        if (!shortcutInfo.isDeclaredInManifest() && shortcutInfo2.isDeclaredInManifest()) {
            return 1;
        }
        if (shortcutInfo.isDynamic() && shortcutInfo2.isDynamic()) {
            return java.lang.Integer.compare(shortcutInfo.getRank(), shortcutInfo2.getRank());
        }
        if (shortcutInfo.isDynamic()) {
            return -1;
        }
        if (shortcutInfo2.isDynamic()) {
            return 1;
        }
        if (shortcutInfo.isCached() && shortcutInfo2.isCached()) {
            if (shortcutInfo.hasFlags(536870912) && !shortcutInfo2.hasFlags(536870912)) {
                return -1;
            }
            if (!shortcutInfo.hasFlags(536870912) && shortcutInfo2.hasFlags(536870912)) {
                return 1;
            }
            if (shortcutInfo.hasFlags(1073741824) && !shortcutInfo2.hasFlags(1073741824)) {
                return -1;
            }
            if (!shortcutInfo.hasFlags(1073741824) && shortcutInfo2.hasFlags(1073741824)) {
                return 1;
            }
        }
        if (shortcutInfo.isCached()) {
            return -1;
        }
        if (shortcutInfo2.isCached()) {
            return 1;
        }
        return java.lang.Long.compare(shortcutInfo2.getLastChangedTimestamp(), shortcutInfo.getLastChangedTimestamp());
    }

    private android.util.ArrayMap<android.content.ComponentName, java.util.ArrayList<android.content.pm.ShortcutInfo>> sortShortcutsToActivities() {
        final android.util.ArrayMap<android.content.ComponentName, java.util.ArrayList<android.content.pm.ShortcutInfo>> arrayMap = new android.util.ArrayMap<>();
        forEachShortcut(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$sortShortcutsToActivities$22(arrayMap, (android.content.pm.ShortcutInfo) obj);
            }
        });
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sortShortcutsToActivities$22(android.util.ArrayMap arrayMap, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo.isFloating()) {
            return;
        }
        android.content.ComponentName activity = shortcutInfo.getActivity();
        if (activity == null) {
            this.mShortcutUser.mService.wtf("null activity detected.");
        } else {
            ((java.util.ArrayList) arrayMap.computeIfAbsent(activity, new java.util.function.Function() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda45
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.util.ArrayList lambda$sortShortcutsToActivities$21;
                    lambda$sortShortcutsToActivities$21 = com.android.server.pm.ShortcutPackage.lambda$sortShortcutsToActivities$21((android.content.ComponentName) obj);
                    return lambda$sortShortcutsToActivities$21;
                }
            })).add(shortcutInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.ArrayList lambda$sortShortcutsToActivities$21(android.content.ComponentName componentName) {
        return new java.util.ArrayList();
    }

    private void incrementCountForActivity(android.util.ArrayMap<android.content.ComponentName, java.lang.Integer> arrayMap, android.content.ComponentName componentName, int i) {
        java.lang.Integer num = arrayMap.get(componentName);
        if (num == null) {
            num = 0;
        }
        arrayMap.put(componentName, java.lang.Integer.valueOf(num.intValue() + i));
    }

    public void enforceShortcutCountsBeforeOperation(java.util.List<android.content.pm.ShortcutInfo> list, final int i) {
        com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
        final android.util.ArrayMap<android.content.ComponentName, java.lang.Integer> arrayMap = new android.util.ArrayMap<>(4);
        forEachShortcut(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda33
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$enforceShortcutCountsBeforeOperation$23(arrayMap, i, (android.content.pm.ShortcutInfo) obj);
            }
        });
        for (int size = list.size() - 1; size >= 0; size--) {
            android.content.pm.ShortcutInfo shortcutInfo = list.get(size);
            android.content.ComponentName activity = shortcutInfo.getActivity();
            if (activity == null) {
                if (i != 2) {
                    shortcutService.wtf("Activity must not be null at this point");
                }
            } else {
                android.content.pm.ShortcutInfo findShortcutById = findShortcutById(shortcutInfo.getId());
                if (findShortcutById == null) {
                    if (i != 2) {
                        incrementCountForActivity(arrayMap, activity, 1);
                    }
                } else if (!findShortcutById.isFloating() || i != 2) {
                    if (i != 0) {
                        android.content.ComponentName activity2 = findShortcutById.getActivity();
                        if (!findShortcutById.isFloating()) {
                            incrementCountForActivity(arrayMap, activity2, -1);
                        }
                    }
                    incrementCountForActivity(arrayMap, activity, 1);
                }
            }
        }
        for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
            shortcutService.enforceMaxActivityShortcuts(arrayMap.valueAt(size2).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enforceShortcutCountsBeforeOperation$23(android.util.ArrayMap arrayMap, int i, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo.isManifestShortcut()) {
            incrementCountForActivity(arrayMap, shortcutInfo.getActivity(), 1);
        } else if (shortcutInfo.isDynamic() && i != 0) {
            incrementCountForActivity(arrayMap, shortcutInfo.getActivity(), 1);
        }
    }

    public void resolveResourceStrings() {
        final com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
        final android.content.res.Resources packageResources = getPackageResources();
        final java.util.ArrayList arrayList = new java.util.ArrayList(1);
        if (packageResources != null) {
            forEachShortcutMutate(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda35
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.ShortcutPackage.lambda$resolveResourceStrings$24(packageResources, shortcutService, arrayList, (android.content.pm.ShortcutInfo) obj);
                }
            });
        }
        if (!com.android.internal.util.CollectionUtils.isEmpty(arrayList)) {
            shortcutService.packageShortcutsChanged(this, arrayList, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$resolveResourceStrings$24(android.content.res.Resources resources, com.android.server.pm.ShortcutService shortcutService, java.util.List list, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo.hasStringResources()) {
            shortcutInfo.resolveResourceStrings(resources);
            shortcutInfo.setTimestamp(shortcutService.injectCurrentTimeMillis());
            list.add(shortcutInfo);
        }
    }

    public void clearAllImplicitRanks() {
        forEachShortcutMutate(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda60
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.content.pm.ShortcutInfo) obj).clearImplicitRankAndRankChangedFlag();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$25(android.content.pm.ShortcutInfo shortcutInfo, android.content.pm.ShortcutInfo shortcutInfo2) {
        int compare = java.lang.Integer.compare(shortcutInfo.getRank(), shortcutInfo2.getRank());
        if (compare != 0) {
            return compare;
        }
        if (shortcutInfo.isRankChanged() != shortcutInfo2.isRankChanged()) {
            return shortcutInfo.isRankChanged() ? -1 : 1;
        }
        int compare2 = java.lang.Integer.compare(shortcutInfo.getImplicitRank(), shortcutInfo2.getImplicitRank());
        if (compare2 != 0) {
            return compare2;
        }
        return shortcutInfo.getId().compareTo(shortcutInfo2.getId());
    }

    public void adjustRanks() {
        com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
        final long injectCurrentTimeMillis = shortcutService.injectCurrentTimeMillis();
        forEachShortcutMutate(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda54
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$adjustRanks$26(injectCurrentTimeMillis, (android.content.pm.ShortcutInfo) obj);
            }
        });
        android.util.ArrayMap<android.content.ComponentName, java.util.ArrayList<android.content.pm.ShortcutInfo>> sortShortcutsToActivities = sortShortcutsToActivities();
        for (int size = sortShortcutsToActivities.size() - 1; size >= 0; size--) {
            java.util.ArrayList<android.content.pm.ShortcutInfo> valueAt = sortShortcutsToActivities.valueAt(size);
            java.util.Collections.sort(valueAt, this.mShortcutRankComparator);
            int size2 = valueAt.size();
            final int i = 0;
            for (int i2 = 0; i2 < size2; i2++) {
                android.content.pm.ShortcutInfo shortcutInfo = valueAt.get(i2);
                if (!shortcutInfo.isManifestShortcut()) {
                    if (shortcutInfo.isDynamic()) {
                        int i3 = i + 1;
                        if (shortcutInfo.getRank() != i) {
                            mutateShortcut(shortcutInfo.getId(), shortcutInfo, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda55
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.pm.ShortcutPackage.lambda$adjustRanks$27(injectCurrentTimeMillis, i, (android.content.pm.ShortcutInfo) obj);
                                }
                            });
                        }
                        i = i3;
                    } else {
                        shortcutService.wtf("Non-dynamic shortcut found. " + shortcutInfo.toInsecureString());
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$adjustRanks$26(long j, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo.isFloating() && shortcutInfo.getRank() != 0) {
            shortcutInfo.setTimestamp(j);
            shortcutInfo.setRank(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$adjustRanks$27(long j, int i, android.content.pm.ShortcutInfo shortcutInfo) {
        shortcutInfo.setTimestamp(j);
        shortcutInfo.setRank(i);
    }

    public boolean hasNonManifestShortcuts() {
        final boolean[] zArr = new boolean[1];
        forEachShortcutStopWhen(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$hasNonManifestShortcuts$28;
                lambda$hasNonManifestShortcuts$28 = com.android.server.pm.ShortcutPackage.lambda$hasNonManifestShortcuts$28(zArr, (android.content.pm.ShortcutInfo) obj);
                return lambda$hasNonManifestShortcuts$28;
            }
        });
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$hasNonManifestShortcuts$28(boolean[] zArr, android.content.pm.ShortcutInfo shortcutInfo) {
        if (!shortcutInfo.isDeclaredInManifest()) {
            zArr[0] = true;
            return true;
        }
        return false;
    }

    void reportShortcutUsed(@android.annotation.NonNull android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal, @android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            try {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
                if (elapsedRealtime - this.mLastReportedTime > shortcutService.mSaveDelayMillis) {
                    this.mLastReportedTime = elapsedRealtime;
                    long injectClearCallingIdentity = shortcutService.injectClearCallingIdentity();
                    try {
                        usageStatsManagerInternal.reportShortcutUsage(getPackageName(), str, getPackageUserId());
                    } finally {
                        shortcutService.injectRestoreCallingIdentity(injectClearCallingIdentity);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(@android.annotation.NonNull final java.io.PrintWriter printWriter, @android.annotation.NonNull final java.lang.String str, com.android.server.pm.ShortcutService.DumpFilter dumpFilter) {
        printWriter.println();
        printWriter.print(str);
        printWriter.print("Package: ");
        printWriter.print(getPackageName());
        printWriter.print("  UID: ");
        printWriter.print(this.mPackageUid);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("  ");
        printWriter.print("Calls: ");
        printWriter.print(getApiCallCount(false));
        printWriter.println();
        printWriter.print(str);
        printWriter.print("  ");
        printWriter.print("Last known FG: ");
        printWriter.print(this.mLastKnownForegroundElapsedTime);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("  ");
        printWriter.print("Last reset: [");
        printWriter.print(this.mLastResetTime);
        printWriter.print("] ");
        printWriter.print(com.android.server.pm.ShortcutService.formatTime(this.mLastResetTime));
        printWriter.println();
        getPackageInfo().dump(printWriter, str + "  ");
        printWriter.println();
        printWriter.print(str);
        printWriter.println("  Shortcuts:");
        final long[] jArr = new long[1];
        forEachShortcut(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda42
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$dump$29(printWriter, str, jArr, (android.content.pm.ShortcutInfo) obj);
            }
        });
        printWriter.print(str);
        printWriter.print("  ");
        printWriter.print("Total bitmap size: ");
        printWriter.print(jArr[0]);
        printWriter.print(" (");
        printWriter.print(android.text.format.Formatter.formatFileSize(this.mShortcutUser.mService.mContext, jArr[0]));
        printWriter.println(")");
        printWriter.println();
        synchronized (this.mLock) {
            this.mShortcutBitmapSaver.dumpLocked(printWriter, "  ");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$29(java.io.PrintWriter printWriter, java.lang.String str, long[] jArr, android.content.pm.ShortcutInfo shortcutInfo) {
        printWriter.println(shortcutInfo.toDumpString(str + "    "));
        if (shortcutInfo.getBitmapPath() != null) {
            long length = new java.io.File(shortcutInfo.getBitmapPath()).length();
            printWriter.print(str);
            printWriter.print("      ");
            printWriter.print("bitmap size=");
            printWriter.println(length);
            jArr[0] = jArr[0] + length;
        }
    }

    public void dumpShortcuts(@android.annotation.NonNull final java.io.PrintWriter printWriter, int i) {
        final int i2 = ((i & 4) != 0 ? 2 : 0) | ((i & 2) != 0 ? 1 : 0) | ((i & 1) != 0 ? 32 : 0) | ((i & 8) != 0 ? 1610629120 : 0);
        forEachShortcut(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda49
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$dumpShortcuts$30(i2, printWriter, (android.content.pm.ShortcutInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpShortcuts$30(int i, java.io.PrintWriter printWriter, android.content.pm.ShortcutInfo shortcutInfo) {
        if ((i & shortcutInfo.getFlags()) != 0) {
            printWriter.println(shortcutInfo.toDumpString(""));
        }
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public org.json.JSONObject dumpCheckin(boolean z) throws org.json.JSONException {
        org.json.JSONObject dumpCheckin = super.dumpCheckin(z);
        final int[] iArr = new int[1];
        final int[] iArr2 = new int[1];
        final int[] iArr3 = new int[1];
        final int[] iArr4 = new int[1];
        final long[] jArr = new long[1];
        forEachShortcut(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda56
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$dumpCheckin$31(iArr, iArr3, iArr2, iArr4, jArr, (android.content.pm.ShortcutInfo) obj);
            }
        });
        dumpCheckin.put(KEY_DYNAMIC, iArr[0]);
        dumpCheckin.put(KEY_MANIFEST, iArr3[0]);
        dumpCheckin.put(KEY_PINNED, iArr2[0]);
        dumpCheckin.put(KEY_BITMAPS, iArr4[0]);
        dumpCheckin.put(KEY_BITMAP_BYTES, jArr[0]);
        return dumpCheckin;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpCheckin$31(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, long[] jArr, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo.isDynamic()) {
            iArr[0] = iArr[0] + 1;
        }
        if (shortcutInfo.isDeclaredInManifest()) {
            iArr2[0] = iArr2[0] + 1;
        }
        if (shortcutInfo.isPinned()) {
            iArr3[0] = iArr3[0] + 1;
        }
        if (shortcutInfo.getBitmapPath() != null) {
            iArr4[0] = iArr4[0] + 1;
            jArr[0] = jArr[0] + new java.io.File(shortcutInfo.getBitmapPath()).length();
        }
    }

    private boolean hasNoShortcut() {
        if (!isAppSearchEnabled()) {
            return getShortcutCount() == 0;
        }
        final boolean[] zArr = new boolean[1];
        forEachShortcutStopWhen(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$hasNoShortcut$32;
                lambda$hasNoShortcut$32 = com.android.server.pm.ShortcutPackage.lambda$hasNoShortcut$32(zArr, (android.content.pm.ShortcutInfo) obj);
                return lambda$hasNoShortcut$32;
            }
        });
        return !zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$hasNoShortcut$32(boolean[] zArr, android.content.pm.ShortcutInfo shortcutInfo) {
        zArr[0] = true;
        return true;
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public void saveToXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        synchronized (this.mLock) {
            try {
                int size = this.mShortcuts.size();
                int size2 = this.mShareTargets.size();
                if (hasNoShortcut() && size2 == 0 && this.mApiCallCount == 0) {
                    return;
                }
                typedXmlSerializer.startTag((java.lang.String) null, "package");
                com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, "name", getPackageName());
                com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_CALL_COUNT, this.mApiCallCount);
                com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_LAST_RESET, this.mLastResetTime);
                if (!z) {
                    com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_SCHEMA_VERSON, this.mIsAppSearchSchemaUpToDate ? 3L : 0L);
                }
                getPackageInfo().saveToXml(this.mShortcutUser.mService, typedXmlSerializer, z);
                for (int i = 0; i < size; i++) {
                    saveShortcut(typedXmlSerializer, this.mShortcuts.valueAt(i), z, getPackageInfo().isBackupAllowed());
                }
                if (!z) {
                    for (int i2 = 0; i2 < size2; i2++) {
                        this.mShareTargets.get(i2).saveToXml(typedXmlSerializer);
                    }
                }
                typedXmlSerializer.endTag((java.lang.String) null, "package");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void saveShortcut(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, android.content.pm.ShortcutInfo shortcutInfo, boolean z, boolean z2) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
        if (z && (!shortcutInfo.isPinned() || !shortcutInfo.isEnabled())) {
            return;
        }
        boolean z3 = !z || z2;
        if (shortcutInfo.isIconPendingSave()) {
            removeIcon(shortcutInfo);
        }
        typedXmlSerializer.startTag((java.lang.String) null, TAG_SHORTCUT);
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_ID, shortcutInfo.getId());
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, "activity", shortcutInfo.getActivity());
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_TITLE, shortcutInfo.getTitle());
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_TITLE_RES_ID, shortcutInfo.getTitleResId());
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_TITLE_RES_NAME, shortcutInfo.getTitleResName());
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_SPLASH_SCREEN_THEME_NAME, shortcutInfo.getStartingThemeResName());
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_TEXT, shortcutInfo.getText());
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_TEXT_RES_ID, shortcutInfo.getTextResId());
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_TEXT_RES_NAME, shortcutInfo.getTextResName());
        if (z3) {
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_DISABLED_MESSAGE, shortcutInfo.getDisabledMessage());
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_DISABLED_MESSAGE_RES_ID, shortcutInfo.getDisabledMessageResourceId());
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_DISABLED_MESSAGE_RES_NAME, shortcutInfo.getDisabledMessageResName());
        }
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_DISABLED_REASON, shortcutInfo.getDisabledReason());
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, "timestamp", shortcutInfo.getLastChangedTimestamp());
        if (shortcutInfo.getLocusId() != null) {
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_LOCUS_ID, shortcutInfo.getLocusId().getId());
        }
        if (z) {
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_FLAGS, shortcutInfo.getFlags() & (-35342));
            if (getPackageInfo().getVersionCode() == 0) {
                shortcutService.wtf("Package version code should be available at this point.");
            }
        } else {
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_RANK, shortcutInfo.getRank());
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_FLAGS, shortcutInfo.getFlags());
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_ICON_RES_ID, shortcutInfo.getIconResourceId());
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_ICON_RES_NAME, shortcutInfo.getIconResName());
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_BITMAP_PATH, shortcutInfo.getBitmapPath());
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_ICON_URI, shortcutInfo.getIconUri());
        }
        if (z3) {
            java.util.Set<java.lang.String> categories = shortcutInfo.getCategories();
            if (categories != null && categories.size() > 0) {
                typedXmlSerializer.startTag((java.lang.String) null, "categories");
                com.android.internal.util.XmlUtils.writeStringArrayXml((java.lang.String[]) categories.toArray(new java.lang.String[categories.size()]), "categories", com.android.internal.util.XmlUtils.makeTyped(typedXmlSerializer));
                typedXmlSerializer.endTag((java.lang.String) null, "categories");
            }
            if (!z) {
                android.app.Person[] persons = shortcutInfo.getPersons();
                if (!com.android.internal.util.ArrayUtils.isEmpty(persons)) {
                    for (android.app.Person person : persons) {
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_PERSON);
                        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, "name", person.getName());
                        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_PERSON_URI, person.getUri());
                        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_PERSON_KEY, person.getKey());
                        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_PERSON_IS_BOT, person.isBot());
                        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_PERSON_IS_IMPORTANT, person.isImportant());
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_PERSON);
                    }
                }
            }
            android.content.Intent[] intentsNoExtras = shortcutInfo.getIntentsNoExtras();
            android.os.PersistableBundle[] intentPersistableExtrases = shortcutInfo.getIntentPersistableExtrases();
            if (intentsNoExtras != null && intentPersistableExtrases != null) {
                int length = intentsNoExtras.length;
                for (int i = 0; i < length; i++) {
                    typedXmlSerializer.startTag((java.lang.String) null, "intent");
                    com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_INTENT_NO_EXTRA, intentsNoExtras[i]);
                    com.android.server.pm.ShortcutService.writeTagExtra(typedXmlSerializer, TAG_EXTRAS, intentPersistableExtrases[i]);
                    typedXmlSerializer.endTag((java.lang.String) null, "intent");
                }
            }
            com.android.server.pm.ShortcutService.writeTagExtra(typedXmlSerializer, TAG_EXTRAS, shortcutInfo.getExtras());
            java.util.Map capabilityBindingsInternal = shortcutInfo.getCapabilityBindingsInternal();
            if (capabilityBindingsInternal != null && !capabilityBindingsInternal.isEmpty()) {
                com.android.internal.util.XmlUtils.writeMapXml(capabilityBindingsInternal, NAME_CAPABILITY, typedXmlSerializer);
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_SHORTCUT);
    }

    public static com.android.server.pm.ShortcutPackage loadFromFile(com.android.server.pm.ShortcutService shortcutService, com.android.server.pm.ShortcutUser shortcutUser, java.io.File file, boolean z) {
        java.io.FileInputStream fileInputStream;
        java.lang.Exception e;
        com.android.server.pm.ResilientAtomicFile resilientFile = com.android.server.pm.ShortcutPackageItem.getResilientFile(file);
        com.android.server.pm.ShortcutPackage shortcutPackage = null;
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
                                if (depth == 1 && "package".equals(name)) {
                                    shortcutPackage = loadFromXml(shortcutService, shortcutUser, resolvePullParser, z);
                                } else {
                                    com.android.server.pm.ShortcutService.throwForInvalidTag(depth, name);
                                }
                            }
                        } else {
                            resilientFile.close();
                            return shortcutPackage;
                        }
                    }
                } catch (java.lang.Exception e2) {
                    e = e2;
                    android.util.Slog.e(TAG, "Failed to read file " + resilientFile.getBaseFile(), e);
                    resilientFile.failRead(fileInputStream, e);
                    com.android.server.pm.ShortcutPackage loadFromFile = loadFromFile(shortcutService, shortcutUser, file, z);
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

    public static com.android.server.pm.ShortcutPackage loadFromXml(com.android.server.pm.ShortcutService shortcutService, com.android.server.pm.ShortcutUser shortcutUser, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String parseStringAttribute = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, "name");
        com.android.server.pm.ShortcutPackage shortcutPackage = new com.android.server.pm.ShortcutPackage(shortcutUser, shortcutUser.getUserId(), parseStringAttribute);
        synchronized (shortcutPackage.mLock) {
            try {
                shortcutPackage.mIsAppSearchSchemaUpToDate = com.android.server.pm.ShortcutService.parseIntAttribute(typedXmlPullParser, ATTR_SCHEMA_VERSON, 0) == 3;
                shortcutPackage.mApiCallCount = com.android.server.pm.ShortcutService.parseIntAttribute(typedXmlPullParser, ATTR_CALL_COUNT);
                shortcutPackage.mLastResetTime = com.android.server.pm.ShortcutService.parseLongAttribute(typedXmlPullParser, ATTR_LAST_RESET);
                int depth = typedXmlPullParser.getDepth();
                while (true) {
                    int next = typedXmlPullParser.next();
                    if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                    }
                    char c = 2;
                    if (next == 2) {
                        int depth2 = typedXmlPullParser.getDepth();
                        java.lang.String name = typedXmlPullParser.getName();
                        if (depth2 == depth + 1) {
                            switch (name.hashCode()) {
                                case -1923478059:
                                    if (name.equals("package-info")) {
                                        c = 0;
                                        break;
                                    }
                                    break;
                                case -1680817345:
                                    if (name.equals(TAG_SHARE_TARGET)) {
                                        break;
                                    }
                                    break;
                                case -342500282:
                                    if (name.equals(TAG_SHORTCUT)) {
                                        c = 1;
                                        break;
                                    }
                                    break;
                            }
                            c = 65535;
                            switch (c) {
                                case 0:
                                    shortcutPackage.getPackageInfo().loadFromXml(typedXmlPullParser, z);
                                    break;
                                case 1:
                                    try {
                                        android.content.pm.ShortcutInfo parseShortcut = parseShortcut(typedXmlPullParser, parseStringAttribute, shortcutUser.getUserId(), z);
                                        shortcutPackage.mShortcuts.put(parseShortcut.getId(), parseShortcut);
                                        break;
                                    } catch (java.io.IOException e) {
                                        throw e;
                                    } catch (java.lang.Exception e2) {
                                        android.util.Slog.e(TAG, "Failed parsing shortcut.", e2);
                                        break;
                                    }
                                case 2:
                                    shortcutPackage.mShareTargets.add(com.android.server.pm.ShareTargetInfo.loadFromXml(typedXmlPullParser));
                                    break;
                            }
                        }
                        com.android.server.pm.ShortcutService.warnForInvalidTag(depth2, name);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return shortcutPackage;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x01fd, code lost:
    
        if (r5 == null) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x01ff, code lost:
    
        android.content.pm.ShortcutInfo.setIntentExtras(r5, r35);
        r1.clear();
        r1.add(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x020a, code lost:
    
        if (r3 != 0) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x020e, code lost:
    
        if ((r7 & 64) == 0) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0210, code lost:
    
        r3 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0211, code lost:
    
        if (r45 == false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0213, code lost:
    
        r0 = r7 | 4096;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0217, code lost:
    
        if (r9 != null) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0219, code lost:
    
        r35 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0267, code lost:
    
        return new android.content.pm.ShortcutInfo(r44, r6, r43, r8, null, r10, r36, r41, r32, r14, r15, r16, r17, r18, r26, (android.content.Intent[]) r1.toArray(new android.content.Intent[r1.size()]), r15, r30, r23, r0, r15, r27, r28, r29, r3, (android.app.Person[]) r2.toArray(new android.app.Person[r2.size()]), r35, r33, r34);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x021c, code lost:
    
        r35 = new android.content.LocusId(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0216, code lost:
    
        r0 = r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.content.pm.ShortcutInfo parseShortcut(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, int i, boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int i2;
        java.lang.String str2;
        int i3;
        android.content.LocusId locusId;
        java.lang.String str3;
        int i4;
        java.lang.String str4;
        int i5;
        char c;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.lang.String parseStringAttribute = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_ID);
        android.content.ComponentName parseComponentNameAttribute = com.android.server.pm.ShortcutService.parseComponentNameAttribute(typedXmlPullParser, "activity");
        java.lang.String parseStringAttribute2 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_TITLE);
        int parseIntAttribute = com.android.server.pm.ShortcutService.parseIntAttribute(typedXmlPullParser, ATTR_TITLE_RES_ID);
        java.lang.String parseStringAttribute3 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_TITLE_RES_NAME);
        java.lang.String parseStringAttribute4 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_SPLASH_SCREEN_THEME_NAME);
        java.lang.String parseStringAttribute5 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_TEXT);
        int parseIntAttribute2 = com.android.server.pm.ShortcutService.parseIntAttribute(typedXmlPullParser, ATTR_TEXT_RES_ID);
        java.lang.String parseStringAttribute6 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_TEXT_RES_NAME);
        java.lang.String parseStringAttribute7 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_DISABLED_MESSAGE);
        int parseIntAttribute3 = com.android.server.pm.ShortcutService.parseIntAttribute(typedXmlPullParser, ATTR_DISABLED_MESSAGE_RES_ID);
        java.lang.String parseStringAttribute8 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_DISABLED_MESSAGE_RES_NAME);
        int parseIntAttribute4 = com.android.server.pm.ShortcutService.parseIntAttribute(typedXmlPullParser, ATTR_DISABLED_REASON);
        java.lang.String str5 = "intent";
        android.content.Intent parseIntentAttributeNoDefault = com.android.server.pm.ShortcutService.parseIntentAttributeNoDefault(typedXmlPullParser, "intent");
        int parseLongAttribute = (int) com.android.server.pm.ShortcutService.parseLongAttribute(typedXmlPullParser, ATTR_RANK);
        long parseLongAttribute2 = com.android.server.pm.ShortcutService.parseLongAttribute(typedXmlPullParser, "timestamp");
        int parseLongAttribute3 = (int) com.android.server.pm.ShortcutService.parseLongAttribute(typedXmlPullParser, ATTR_FLAGS);
        int parseLongAttribute4 = (int) com.android.server.pm.ShortcutService.parseLongAttribute(typedXmlPullParser, ATTR_ICON_RES_ID);
        java.lang.String parseStringAttribute9 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_ICON_RES_NAME);
        java.lang.String parseStringAttribute10 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_BITMAP_PATH);
        java.lang.String parseStringAttribute11 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_ICON_URI);
        java.lang.String parseStringAttribute12 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_LOCUS_ID);
        int depth = typedXmlPullParser.getDepth();
        android.util.ArraySet arraySet = null;
        android.os.PersistableBundle persistableBundle = null;
        java.util.Map map = null;
        android.os.PersistableBundle persistableBundle2 = null;
        while (true) {
            int next = typedXmlPullParser.next();
            java.lang.String str6 = parseStringAttribute5;
            if (next == 1) {
                i2 = parseIntAttribute;
                str2 = parseStringAttribute3;
                i3 = 1;
                locusId = null;
            } else if (next != 3 || typedXmlPullParser.getDepth() > depth) {
                if (next != 2) {
                    str3 = str5;
                    i4 = parseIntAttribute;
                    str4 = parseStringAttribute3;
                    i5 = depth;
                } else {
                    int depth2 = typedXmlPullParser.getDepth();
                    java.lang.String name = typedXmlPullParser.getName();
                    i5 = depth;
                    str4 = parseStringAttribute3;
                    switch (name.hashCode()) {
                        case -1289032093:
                            i4 = parseIntAttribute;
                            if (name.equals(TAG_EXTRAS)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1183762788:
                            i4 = parseIntAttribute;
                            if (name.equals(str5)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1044333900:
                            i4 = parseIntAttribute;
                            if (name.equals(TAG_INTENT_EXTRAS_LEGACY)) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1024600675:
                            i4 = parseIntAttribute;
                            if (name.equals(TAG_STRING_ARRAY_XMLUTILS)) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case -991716523:
                            i4 = parseIntAttribute;
                            if (name.equals(TAG_PERSON)) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 107868:
                            i4 = parseIntAttribute;
                            if (name.equals(TAG_MAP_XMLUTILS)) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1296516636:
                            if (name.equals("categories")) {
                                i4 = parseIntAttribute;
                                c = 3;
                                break;
                            }
                        default:
                            i4 = parseIntAttribute;
                            c = 65535;
                            break;
                    }
                    str3 = str5;
                    switch (c) {
                        case 0:
                            persistableBundle2 = android.os.PersistableBundle.restoreFromXml(typedXmlPullParser);
                            parseStringAttribute5 = str6;
                            parseIntAttribute = i4;
                            str5 = str3;
                            depth = i5;
                            parseStringAttribute3 = str4;
                            continue;
                        case 1:
                            arrayList.add(parseIntent(typedXmlPullParser));
                            break;
                        case 2:
                            persistableBundle = android.os.PersistableBundle.restoreFromXml(typedXmlPullParser);
                            parseStringAttribute5 = str6;
                            parseIntAttribute = i4;
                            str5 = str3;
                            depth = i5;
                            parseStringAttribute3 = str4;
                            continue;
                        case 3:
                            break;
                        case 4:
                            arrayList2.add(parsePerson(typedXmlPullParser));
                            break;
                        case 5:
                            if (!"categories".equals(com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, "name"))) {
                                break;
                            } else {
                                java.lang.String[] readThisStringArrayXml = com.android.internal.util.XmlUtils.readThisStringArrayXml(com.android.internal.util.XmlUtils.makeTyped(typedXmlPullParser), TAG_STRING_ARRAY_XMLUTILS, (java.lang.String[]) null);
                                android.util.ArraySet arraySet2 = new android.util.ArraySet(readThisStringArrayXml.length);
                                for (java.lang.String str7 : readThisStringArrayXml) {
                                    arraySet2.add(str7);
                                }
                                arraySet = arraySet2;
                                parseStringAttribute5 = str6;
                                parseIntAttribute = i4;
                                str5 = str3;
                                depth = i5;
                                parseStringAttribute3 = str4;
                            }
                        case 6:
                            if (!NAME_CAPABILITY.equals(com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, "name"))) {
                                break;
                            } else {
                                map = (java.util.Map) com.android.internal.util.XmlUtils.readValueXml(typedXmlPullParser, new java.lang.String[1]);
                                parseStringAttribute5 = str6;
                                parseIntAttribute = i4;
                                str5 = str3;
                                depth = i5;
                                parseStringAttribute3 = str4;
                            }
                        default:
                            throw com.android.server.pm.ShortcutService.throwForInvalidTag(depth2, name);
                    }
                }
                parseStringAttribute5 = str6;
                parseIntAttribute = i4;
                str5 = str3;
                depth = i5;
                parseStringAttribute3 = str4;
            } else {
                i2 = parseIntAttribute;
                str2 = parseStringAttribute3;
                i3 = 1;
                locusId = null;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x004b, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.content.Intent parseIntent(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        char c;
        android.content.Intent parseIntentAttribute = com.android.server.pm.ShortcutService.parseIntentAttribute(typedXmlPullParser, ATTR_INTENT_NO_EXTRA);
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1 && (next != 3 || typedXmlPullParser.getDepth() > depth)) {
                if (next == 2) {
                    int depth2 = typedXmlPullParser.getDepth();
                    java.lang.String name = typedXmlPullParser.getName();
                    switch (name.hashCode()) {
                        case -1289032093:
                            if (name.equals(TAG_EXTRAS)) {
                                c = 0;
                                break;
                            }
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            android.content.pm.ShortcutInfo.setIntentExtras(parseIntentAttribute, android.os.PersistableBundle.restoreFromXml(typedXmlPullParser));
                            break;
                        default:
                            throw com.android.server.pm.ShortcutService.throwForInvalidTag(depth2, name);
                    }
                }
            }
        }
    }

    private static android.app.Person parsePerson(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String parseStringAttribute = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, "name");
        java.lang.String parseStringAttribute2 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_PERSON_URI);
        java.lang.String parseStringAttribute3 = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_PERSON_KEY);
        boolean parseBooleanAttribute = com.android.server.pm.ShortcutService.parseBooleanAttribute(typedXmlPullParser, ATTR_PERSON_IS_BOT);
        boolean parseBooleanAttribute2 = com.android.server.pm.ShortcutService.parseBooleanAttribute(typedXmlPullParser, ATTR_PERSON_IS_IMPORTANT);
        android.app.Person.Builder builder = new android.app.Person.Builder();
        builder.setName(parseStringAttribute).setUri(parseStringAttribute2).setKey(parseStringAttribute3).setBot(parseBooleanAttribute).setImportant(parseBooleanAttribute2);
        return builder.build();
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<android.content.pm.ShortcutInfo> getAllShortcutsForTest() {
        java.util.ArrayList arrayList = new java.util.ArrayList(1);
        java.util.Objects.requireNonNull(arrayList);
        forEachShortcut(new com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda43(arrayList));
        return arrayList;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<com.android.server.pm.ShareTargetInfo> getAllShareTargetsForTest() {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new java.util.ArrayList(this.mShareTargets);
        }
        return arrayList;
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public void verifyStates() {
        super.verifyStates();
        final boolean[] zArr = new boolean[1];
        final com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
        android.util.ArrayMap<android.content.ComponentName, java.util.ArrayList<android.content.pm.ShortcutInfo>> sortShortcutsToActivities = sortShortcutsToActivities();
        for (int size = sortShortcutsToActivities.size() - 1; size >= 0; size--) {
            java.util.ArrayList<android.content.pm.ShortcutInfo> valueAt = sortShortcutsToActivities.valueAt(size);
            if (valueAt.size() > this.mShortcutUser.mService.getMaxActivityShortcuts()) {
                zArr[0] = true;
                android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": activity " + sortShortcutsToActivities.keyAt(size) + " has " + sortShortcutsToActivities.valueAt(size).size() + " shortcuts.");
            }
            java.util.Collections.sort(valueAt, new java.util.Comparator() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda25
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$verifyStates$33;
                    lambda$verifyStates$33 = com.android.server.pm.ShortcutPackage.lambda$verifyStates$33((android.content.pm.ShortcutInfo) obj, (android.content.pm.ShortcutInfo) obj2);
                    return lambda$verifyStates$33;
                }
            });
            java.util.ArrayList arrayList = new java.util.ArrayList(valueAt);
            arrayList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda26
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$verifyStates$34;
                    lambda$verifyStates$34 = com.android.server.pm.ShortcutPackage.lambda$verifyStates$34((android.content.pm.ShortcutInfo) obj);
                    return lambda$verifyStates$34;
                }
            });
            java.util.ArrayList arrayList2 = new java.util.ArrayList(valueAt);
            arrayList2.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda27
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$verifyStates$35;
                    lambda$verifyStates$35 = com.android.server.pm.ShortcutPackage.lambda$verifyStates$35((android.content.pm.ShortcutInfo) obj);
                    return lambda$verifyStates$35;
                }
            });
            verifyRanksSequential(arrayList);
            verifyRanksSequential(arrayList2);
        }
        forEachShortcut(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda28
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$verifyStates$36(zArr, shortcutService, (android.content.pm.ShortcutInfo) obj);
            }
        });
        if (zArr[0]) {
            throw new java.lang.IllegalStateException("See logcat for errors");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$verifyStates$33(android.content.pm.ShortcutInfo shortcutInfo, android.content.pm.ShortcutInfo shortcutInfo2) {
        return java.lang.Integer.compare(shortcutInfo.getRank(), shortcutInfo2.getRank());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$verifyStates$34(android.content.pm.ShortcutInfo shortcutInfo) {
        return !shortcutInfo.isDynamic();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$verifyStates$35(android.content.pm.ShortcutInfo shortcutInfo) {
        return !shortcutInfo.isManifestShortcut();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verifyStates$36(boolean[] zArr, com.android.server.pm.ShortcutService shortcutService, android.content.pm.ShortcutInfo shortcutInfo) {
        if (!shortcutInfo.isDeclaredInManifest() && !shortcutInfo.isDynamic() && !shortcutInfo.isPinned() && !shortcutInfo.isCached()) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " is not manifest, dynamic or pinned.");
        }
        if (shortcutInfo.isDeclaredInManifest() && shortcutInfo.isDynamic()) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " is both dynamic and manifest at the same time.");
        }
        if (shortcutInfo.getActivity() == null && !shortcutInfo.isFloating()) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " has null activity, but not floating.");
        }
        if ((shortcutInfo.isDynamic() || shortcutInfo.isManifestShortcut()) && !shortcutInfo.isEnabled()) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " is not floating, but is disabled.");
        }
        if (shortcutInfo.isFloating() && shortcutInfo.getRank() != 0) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " is floating, but has rank=" + shortcutInfo.getRank());
        }
        if (shortcutInfo.getIcon() != null) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " still has an icon");
        }
        if (shortcutInfo.hasAdaptiveBitmap() && !shortcutInfo.hasIconFile() && !shortcutInfo.hasIconUri()) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " has adaptive bitmap but was not saved to a file nor has icon uri.");
        }
        if (shortcutInfo.hasIconFile() && shortcutInfo.hasIconResource()) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " has both resource and bitmap icons");
        }
        if (shortcutInfo.hasIconFile() && shortcutInfo.hasIconUri()) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " has both url and bitmap icons");
        }
        if (shortcutInfo.hasIconUri() && shortcutInfo.hasIconResource()) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " has both url and resource icons");
        }
        if (shortcutInfo.isEnabled() != (shortcutInfo.getDisabledReason() == 0)) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " isEnabled() and getDisabledReason() disagree: " + shortcutInfo.isEnabled() + " vs " + shortcutInfo.getDisabledReason());
        }
        if (shortcutInfo.getDisabledReason() == 100 && getPackageInfo().getBackupSourceVersionCode() == -1) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " RESTORED_VERSION_LOWER with no backup source version code.");
        }
        if (shortcutService.isDummyMainActivity(shortcutInfo.getActivity())) {
            zArr[0] = true;
            android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " has a dummy target activity");
        }
    }

    void mutateShortcut(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.content.pm.ShortcutInfo shortcutInfo, @android.annotation.NonNull java.util.function.Consumer<android.content.pm.ShortcutInfo> consumer) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(consumer);
        synchronized (this.mLock) {
            if (shortcutInfo != null) {
                try {
                    consumer.accept(shortcutInfo);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            android.content.pm.ShortcutInfo findShortcutById = findShortcutById(str);
            if (findShortcutById == null) {
                return;
            }
            consumer.accept(findShortcutById);
            saveShortcut(findShortcutById);
        }
    }

    private void saveShortcut(@android.annotation.NonNull android.content.pm.ShortcutInfo... shortcutInfoArr) {
        java.util.Objects.requireNonNull(shortcutInfoArr);
        saveShortcut(java.util.Arrays.asList(shortcutInfoArr));
    }

    private void saveShortcut(@android.annotation.NonNull java.util.Collection<android.content.pm.ShortcutInfo> collection) {
        java.util.Objects.requireNonNull(collection);
        synchronized (this.mLock) {
            try {
                for (android.content.pm.ShortcutInfo shortcutInfo : collection) {
                    this.mShortcuts.put(shortcutInfo.getId(), shortcutInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    java.util.List<android.content.pm.ShortcutInfo> findAll(@android.annotation.NonNull java.util.Collection<java.lang.String> collection) {
        java.util.List<android.content.pm.ShortcutInfo> list;
        synchronized (this.mLock) {
            java.util.stream.Stream<java.lang.String> stream = collection.stream();
            final android.util.ArrayMap<java.lang.String, android.content.pm.ShortcutInfo> arrayMap = this.mShortcuts;
            java.util.Objects.requireNonNull(arrayMap);
            list = (java.util.List) stream.map(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return (android.content.pm.ShortcutInfo) arrayMap.get((java.lang.String) obj);
                }
            }).filter(new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return java.util.Objects.nonNull((android.content.pm.ShortcutInfo) obj);
                }
            }).collect(java.util.stream.Collectors.toList());
        }
        return list;
    }

    private void forEachShortcut(@android.annotation.NonNull final java.util.function.Consumer<android.content.pm.ShortcutInfo> consumer) {
        forEachShortcutStopWhen(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda53
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$forEachShortcut$37;
                lambda$forEachShortcut$37 = com.android.server.pm.ShortcutPackage.lambda$forEachShortcut$37(consumer, (android.content.pm.ShortcutInfo) obj);
                return lambda$forEachShortcut$37;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$forEachShortcut$37(java.util.function.Consumer consumer, android.content.pm.ShortcutInfo shortcutInfo) {
        consumer.accept(shortcutInfo);
        return false;
    }

    private void forEachShortcutMutate(@android.annotation.NonNull java.util.function.Consumer<android.content.pm.ShortcutInfo> consumer) {
        for (int size = this.mShortcuts.size() - 1; size >= 0; size--) {
            consumer.accept(this.mShortcuts.valueAt(size));
        }
    }

    private void forEachShortcutStopWhen(@android.annotation.NonNull java.util.function.Function<android.content.pm.ShortcutInfo, java.lang.Boolean> function) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mShortcuts.size() - 1; size >= 0; size--) {
                    if (function.apply(this.mShortcuts.valueAt(size)).booleanValue()) {
                        return;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public com.android.internal.infra.AndroidFuture<android.app.appsearch.AppSearchSession> setupSchema(@android.annotation.NonNull final android.app.appsearch.AppSearchSession appSearchSession) {
        android.app.appsearch.SetSchemaRequest.Builder addRequiredPermissionsForSchemaTypeVisibility = new android.app.appsearch.SetSchemaRequest.Builder().addSchemas(android.content.pm.AppSearchShortcutPerson.SCHEMA, android.content.pm.AppSearchShortcutInfo.SCHEMA).setForceOverride(true).addRequiredPermissionsForSchemaTypeVisibility("Shortcut", java.util.Collections.singleton(5)).addRequiredPermissionsForSchemaTypeVisibility("Shortcut", java.util.Collections.singleton(6)).addRequiredPermissionsForSchemaTypeVisibility("ShortcutPerson", java.util.Collections.singleton(5)).addRequiredPermissionsForSchemaTypeVisibility("ShortcutPerson", java.util.Collections.singleton(6));
        final com.android.internal.infra.AndroidFuture<android.app.appsearch.AppSearchSession> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        appSearchSession.setSchema(addRequiredPermissionsForSchemaTypeVisibility.build(), this.mExecutor, this.mShortcutUser.mExecutor, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda44
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$setupSchema$38(androidFuture, appSearchSession, (android.app.appsearch.AppSearchResult) obj);
            }
        });
        return androidFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setupSchema$38(com.android.internal.infra.AndroidFuture androidFuture, android.app.appsearch.AppSearchSession appSearchSession, android.app.appsearch.AppSearchResult appSearchResult) {
        if (!appSearchResult.isSuccess()) {
            androidFuture.completeExceptionally(new java.lang.IllegalArgumentException(appSearchResult.getErrorMessage()));
        } else {
            androidFuture.complete(appSearchSession);
        }
    }

    @android.annotation.NonNull
    private android.app.appsearch.SearchSpec getSearchSpec() {
        return new android.app.appsearch.SearchSpec.Builder().addFilterSchemas("Shortcut").addFilterNamespaces(getPackageName()).setTermMatch(1).setResultCountPerPage(this.mShortcutUser.mService.getMaxActivityShortcuts()).build();
    }

    private boolean verifyRanksSequential(java.util.List<android.content.pm.ShortcutInfo> list) {
        boolean z = false;
        for (int i = 0; i < list.size(); i++) {
            android.content.pm.ShortcutInfo shortcutInfo = list.get(i);
            if (shortcutInfo.getRank() != i) {
                android.util.Log.e(TAG_VERIFY, "Package " + getPackageName() + ": shortcut " + shortcutInfo.getId() + " rank=" + shortcutInfo.getRank() + " but expected to be " + i);
                z = true;
            }
        }
        return z;
    }

    void removeAllShortcutsAsync() {
        if (!isAppSearchEnabled()) {
            return;
        }
        runAsSystem(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.ShortcutPackage.this.lambda$removeAllShortcutsAsync$41();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeAllShortcutsAsync$41() {
        fromAppSearch().thenAccept(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$removeAllShortcutsAsync$40((android.app.appsearch.AppSearchSession) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeAllShortcutsAsync$40(android.app.appsearch.AppSearchSession appSearchSession) {
        appSearchSession.remove("", getSearchSpec(), this.mShortcutUser.mExecutor, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda52
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.lambda$removeAllShortcutsAsync$39((android.app.appsearch.AppSearchResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeAllShortcutsAsync$39(android.app.appsearch.AppSearchResult appSearchResult) {
        if (!appSearchResult.isSuccess()) {
            android.util.Slog.e(TAG, "Failed to remove shortcuts from AppSearch. " + appSearchResult.getErrorMessage());
        }
    }

    void getShortcutByIdsAsync(@android.annotation.NonNull final java.util.Set<java.lang.String> set, @android.annotation.NonNull final java.util.function.Consumer<java.util.List<android.content.pm.ShortcutInfo>> consumer) {
        if (!isAppSearchEnabled()) {
            consumer.accept(java.util.Collections.emptyList());
        } else {
            runAsSystem(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.ShortcutPackage.this.lambda$getShortcutByIdsAsync$43(set, consumer);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getShortcutByIdsAsync$43(final java.util.Set set, final java.util.function.Consumer consumer) {
        fromAppSearch().thenAccept(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$getShortcutByIdsAsync$42(set, consumer, (android.app.appsearch.AppSearchSession) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getShortcutByIdsAsync$42(java.util.Set set, java.util.function.Consumer consumer, android.app.appsearch.AppSearchSession appSearchSession) {
        appSearchSession.getByDocumentId(new android.app.appsearch.GetByDocumentIdRequest.Builder(getPackageName()).addIds(set).build(), this.mShortcutUser.mExecutor, new com.android.server.pm.ShortcutPackage.AnonymousClass1(consumer));
    }

    /* renamed from: com.android.server.pm.ShortcutPackage$1, reason: invalid class name */
    class AnonymousClass1 implements android.app.appsearch.BatchResultCallback<java.lang.String, android.app.appsearch.GenericDocument> {
        final /* synthetic */ java.util.function.Consumer val$cb;

        AnonymousClass1(java.util.function.Consumer consumer) {
            this.val$cb = consumer;
        }

        @Override // android.app.appsearch.BatchResultCallback
        public void onResult(@android.annotation.NonNull android.app.appsearch.AppSearchBatchResult<java.lang.String, android.app.appsearch.GenericDocument> appSearchBatchResult) {
            this.val$cb.accept((java.util.List) appSearchBatchResult.getSuccesses().values().stream().map(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutPackage$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.content.pm.ShortcutInfo lambda$onResult$0;
                    lambda$onResult$0 = com.android.server.pm.ShortcutPackage.AnonymousClass1.this.lambda$onResult$0((android.app.appsearch.GenericDocument) obj);
                    return lambda$onResult$0;
                }
            }).collect(java.util.stream.Collectors.toList()));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.content.pm.ShortcutInfo lambda$onResult$0(android.app.appsearch.GenericDocument genericDocument) {
            return android.content.pm.ShortcutInfo.createFromGenericDocument(com.android.server.pm.ShortcutPackage.this.mShortcutUser.getUserId(), genericDocument);
        }

        @Override // android.app.appsearch.BatchResultCallback
        public void onSystemError(@android.annotation.Nullable java.lang.Throwable th) {
            android.util.Slog.d(com.android.server.pm.ShortcutPackage.TAG, "Error retrieving shortcuts", th);
        }
    }

    private void removeShortcutAsync(@android.annotation.NonNull java.lang.String... strArr) {
        java.util.Objects.requireNonNull(strArr);
        removeShortcutAsync(java.util.Arrays.asList(strArr));
    }

    private void removeShortcutAsync(@android.annotation.NonNull final java.util.Collection<java.lang.String> collection) {
        if (!isAppSearchEnabled()) {
            return;
        }
        runAsSystem(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda40
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.ShortcutPackage.this.lambda$removeShortcutAsync$45(collection);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeShortcutAsync$45(final java.util.Collection collection) {
        fromAppSearch().thenAccept(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda31
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$removeShortcutAsync$44(collection, (android.app.appsearch.AppSearchSession) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeShortcutAsync$44(java.util.Collection collection, android.app.appsearch.AppSearchSession appSearchSession) {
        appSearchSession.remove(new android.app.appsearch.RemoveByDocumentIdRequest.Builder(getPackageName()).addIds((java.util.Collection<java.lang.String>) collection).build(), this.mShortcutUser.mExecutor, new android.app.appsearch.BatchResultCallback<java.lang.String, java.lang.Void>() { // from class: com.android.server.pm.ShortcutPackage.2
            @Override // android.app.appsearch.BatchResultCallback
            public void onResult(@android.annotation.NonNull android.app.appsearch.AppSearchBatchResult<java.lang.String, java.lang.Void> appSearchBatchResult) {
                if (!appSearchBatchResult.isSuccess()) {
                    java.util.Map<java.lang.String, android.app.appsearch.AppSearchResult<java.lang.Void>> failures = appSearchBatchResult.getFailures();
                    for (java.lang.String str : failures.keySet()) {
                        android.util.Slog.e(com.android.server.pm.ShortcutPackage.TAG, "Failed deleting " + str + ", error message:" + failures.get(str).getErrorMessage());
                    }
                }
            }

            @Override // android.app.appsearch.BatchResultCallback
            public void onSystemError(@android.annotation.Nullable java.lang.Throwable th) {
                android.util.Slog.e(com.android.server.pm.ShortcutPackage.TAG, "Error removing shortcuts", th);
            }
        });
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void scheduleSaveToAppSearchLocked() {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mShortcuts);
        if (!this.mTransientShortcuts.isEmpty()) {
            arrayMap.putAll((java.util.Map) this.mTransientShortcuts);
            this.mTransientShortcuts.clear();
        }
        saveShortcutsAsync((java.util.Collection) arrayMap.values().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return ((android.content.pm.ShortcutInfo) obj).usesQuota();
            }
        }).collect(java.util.stream.Collectors.toList()));
    }

    private void saveShortcutsAsync(@android.annotation.NonNull final java.util.Collection<android.content.pm.ShortcutInfo> collection) {
        java.util.Objects.requireNonNull(collection);
        if (!isAppSearchEnabled() || collection.isEmpty()) {
            return;
        }
        runAsSystem(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda34
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.ShortcutPackage.this.lambda$saveShortcutsAsync$47(collection);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveShortcutsAsync$47(final java.util.Collection collection) {
        fromAppSearch().thenAccept(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$saveShortcutsAsync$46(collection, (android.app.appsearch.AppSearchSession) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveShortcutsAsync$46(java.util.Collection collection, android.app.appsearch.AppSearchSession appSearchSession) {
        if (collection.isEmpty()) {
            return;
        }
        appSearchSession.put(new android.app.appsearch.PutDocumentsRequest.Builder().addGenericDocuments(android.content.pm.AppSearchShortcutInfo.toGenericDocuments(collection)).build(), this.mShortcutUser.mExecutor, new android.app.appsearch.BatchResultCallback<java.lang.String, java.lang.Void>() { // from class: com.android.server.pm.ShortcutPackage.3
            @Override // android.app.appsearch.BatchResultCallback
            public void onResult(@android.annotation.NonNull android.app.appsearch.AppSearchBatchResult<java.lang.String, java.lang.Void> appSearchBatchResult) {
                if (!appSearchBatchResult.isSuccess()) {
                    java.util.Iterator<android.app.appsearch.AppSearchResult<java.lang.Void>> it = appSearchBatchResult.getFailures().values().iterator();
                    while (it.hasNext()) {
                        android.util.Slog.e(com.android.server.pm.ShortcutPackage.TAG, it.next().getErrorMessage());
                    }
                }
            }

            @Override // android.app.appsearch.BatchResultCallback
            public void onSystemError(@android.annotation.Nullable java.lang.Throwable th) {
                android.util.Slog.d(com.android.server.pm.ShortcutPackage.TAG, "Error persisting shortcuts", th);
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    void getTopShortcutsFromPersistence(final com.android.internal.infra.AndroidFuture<java.util.List<android.content.pm.ShortcutInfo>> androidFuture) {
        if (!isAppSearchEnabled()) {
            androidFuture.complete((java.lang.Object) null);
        }
        runAsSystem(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.ShortcutPackage.this.lambda$getTopShortcutsFromPersistence$51(androidFuture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTopShortcutsFromPersistence$51(final com.android.internal.infra.AndroidFuture androidFuture) {
        fromAppSearch().thenAccept(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda59
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$getTopShortcutsFromPersistence$50(androidFuture, (android.app.appsearch.AppSearchSession) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTopShortcutsFromPersistence$50(final com.android.internal.infra.AndroidFuture androidFuture, android.app.appsearch.AppSearchSession appSearchSession) {
        appSearchSession.search("", getSearchSpec()).getNextPage(this.mShortcutUser.mExecutor, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda30
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutPackage.this.lambda$getTopShortcutsFromPersistence$49(androidFuture, (android.app.appsearch.AppSearchResult) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTopShortcutsFromPersistence$49(com.android.internal.infra.AndroidFuture androidFuture, android.app.appsearch.AppSearchResult appSearchResult) {
        if (!appSearchResult.isSuccess()) {
            androidFuture.completeExceptionally(new java.lang.IllegalStateException(appSearchResult.getErrorMessage()));
        } else {
            androidFuture.complete((java.util.List) ((java.util.List) appSearchResult.getResultValue()).stream().map(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda50
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((android.app.appsearch.SearchResult) obj).getGenericDocument();
                }
            }).map(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda51
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.content.pm.ShortcutInfo lambda$getTopShortcutsFromPersistence$48;
                    lambda$getTopShortcutsFromPersistence$48 = com.android.server.pm.ShortcutPackage.this.lambda$getTopShortcutsFromPersistence$48((android.app.appsearch.GenericDocument) obj);
                    return lambda$getTopShortcutsFromPersistence$48;
                }
            }).collect(java.util.stream.Collectors.toList()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.pm.ShortcutInfo lambda$getTopShortcutsFromPersistence$48(android.app.appsearch.GenericDocument genericDocument) {
        return android.content.pm.ShortcutInfo.createFromGenericDocument(this.mShortcutUser.getUserId(), genericDocument);
    }

    @android.annotation.NonNull
    private com.android.internal.infra.AndroidFuture<android.app.appsearch.AppSearchSession> fromAppSearch() {
        android.os.StrictMode.ThreadPolicy threadPolicy = android.os.StrictMode.getThreadPolicy();
        android.app.appsearch.AppSearchManager.SearchContext build = new android.app.appsearch.AppSearchManager.SearchContext.Builder(getPackageName()).build();
        com.android.internal.infra.AndroidFuture<android.app.appsearch.AppSearchSession> androidFuture = null;
        try {
            try {
                android.os.StrictMode.setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
                androidFuture = this.mShortcutUser.getAppSearch(build);
                synchronized (this.mLock) {
                    try {
                        if (!this.mIsAppSearchSchemaUpToDate) {
                            androidFuture = androidFuture.thenCompose(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda6
                                @Override // java.util.function.Function
                                public final java.lang.Object apply(java.lang.Object obj) {
                                    com.android.internal.infra.AndroidFuture androidFuture2;
                                    androidFuture2 = com.android.server.pm.ShortcutPackage.this.setupSchema((android.app.appsearch.AppSearchSession) obj);
                                    return androidFuture2;
                                }
                            });
                        }
                        this.mIsAppSearchSchemaUpToDate = true;
                    } finally {
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Failed to create app search session. pkg=" + getPackageName() + " user=" + this.mShortcutUser.getUserId(), e);
                java.util.Objects.requireNonNull(androidFuture);
                com.android.internal.infra.AndroidFuture<android.app.appsearch.AppSearchSession> androidFuture2 = androidFuture;
                androidFuture.completeExceptionally(e);
            }
            java.util.Objects.requireNonNull(androidFuture);
            return androidFuture;
        } finally {
            android.os.StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    private void runAsSystem(@android.annotation.NonNull java.lang.Runnable runnable) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            runnable.run();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    protected java.io.File getShortcutPackageItemFile() {
        return new java.io.File(new java.io.File(this.mShortcutUser.mService.injectUserDataPath(this.mShortcutUser.getUserId()), "packages"), getPackageName() + ".xml");
    }
}
