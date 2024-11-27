package com.android.server.wm;

/* loaded from: classes3.dex */
class LaunchParamsPersister {
    private static final char ESCAPED_COMPONENT_SEPARATOR = '-';
    private static final java.lang.String LAUNCH_PARAMS_DIRNAME = "launch_params";
    private static final java.lang.String LAUNCH_PARAMS_FILE_SUFFIX = ".xml";
    private static final char OLD_ESCAPED_COMPONENT_SEPARATOR = '_';
    private static final char ORIGINAL_COMPONENT_SEPARATOR = '/';
    private static final java.lang.String TAG = "LaunchParamsPersister";
    private static final java.lang.String TAG_LAUNCH_PARAMS = "launch_params";
    private final android.util.SparseArray<android.util.ArrayMap<android.content.ComponentName, com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams>> mLaunchParamsMap;
    private com.android.server.pm.PackageList mPackageList;
    private final com.android.server.wm.PersisterQueue mPersisterQueue;
    private final com.android.server.wm.ActivityTaskSupervisor mSupervisor;
    private final java.util.function.IntFunction<java.io.File> mUserFolderGetter;
    private final android.util.ArrayMap<java.lang.String, android.util.ArraySet<android.content.ComponentName>> mWindowLayoutAffinityMap;

    LaunchParamsPersister(com.android.server.wm.PersisterQueue persisterQueue, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) {
        this(persisterQueue, activityTaskSupervisor, new java.util.function.IntFunction() { // from class: com.android.server.wm.LaunchParamsPersister$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                return android.os.Environment.getDataSystemCeDirectory(i);
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    LaunchParamsPersister(com.android.server.wm.PersisterQueue persisterQueue, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, java.util.function.IntFunction<java.io.File> intFunction) {
        this.mLaunchParamsMap = new android.util.SparseArray<>();
        this.mWindowLayoutAffinityMap = new android.util.ArrayMap<>();
        this.mPersisterQueue = persisterQueue;
        this.mSupervisor = activityTaskSupervisor;
        this.mUserFolderGetter = intFunction;
    }

    void onSystemReady() {
        this.mPackageList = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackageList(new com.android.server.wm.LaunchParamsPersister.PackageListObserver());
    }

    void onUnlockUser(int i) {
        loadLaunchParams(i);
    }

    void onCleanupUser(int i) {
        this.mLaunchParamsMap.remove(i);
    }

    private void loadLaunchParams(int i) {
        java.io.File file;
        java.io.FileInputStream fileInputStream;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.io.File launchParamFolder = getLaunchParamFolder(i);
        if (!launchParamFolder.isDirectory()) {
            android.util.Slog.i(TAG, "Didn't find launch param folder for user " + i);
            return;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(this.mPackageList.getPackageNames());
        java.io.File[] listFiles = launchParamFolder.listFiles();
        android.util.ArrayMap<android.content.ComponentName, com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams> arrayMap = new android.util.ArrayMap<>(listFiles.length);
        this.mLaunchParamsMap.put(i, arrayMap);
        int length = listFiles.length;
        int i2 = 0;
        while (i2 < length) {
            java.io.File file2 = listFiles[i2];
            if (!file2.isFile()) {
                android.util.Slog.w(TAG, file2.getAbsolutePath() + " is not a file.");
                file = launchParamFolder;
            } else if (!file2.getName().endsWith(LAUNCH_PARAMS_FILE_SUFFIX)) {
                android.util.Slog.w(TAG, "Unexpected params file name: " + file2.getName());
                arrayList.add(file2);
                file = launchParamFolder;
            } else {
                java.lang.String name = file2.getName();
                int indexOf = name.indexOf(95);
                if (indexOf != -1) {
                    if (name.indexOf(95, indexOf + 1) != -1) {
                        arrayList.add(file2);
                        file = launchParamFolder;
                    } else {
                        name = name.replace(OLD_ESCAPED_COMPONENT_SEPARATOR, ESCAPED_COMPONENT_SEPARATOR);
                        java.io.File file3 = new java.io.File(launchParamFolder, name);
                        if (file2.renameTo(file3)) {
                            file2 = file3;
                        } else {
                            arrayList.add(file2);
                            file = launchParamFolder;
                        }
                    }
                }
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(name.substring(0, name.length() - LAUNCH_PARAMS_FILE_SUFFIX.length()).replace(ESCAPED_COMPONENT_SEPARATOR, ORIGINAL_COMPONENT_SEPARATOR));
                if (unflattenFromString == null) {
                    android.util.Slog.w(TAG, "Unexpected file name: " + name);
                    arrayList.add(file2);
                    file = launchParamFolder;
                } else if (!arraySet.contains(unflattenFromString.getPackageName())) {
                    arrayList.add(file2);
                    file = launchParamFolder;
                } else {
                    try {
                        fileInputStream = new java.io.FileInputStream(file2);
                    } catch (java.lang.Exception e) {
                        e = e;
                        file = launchParamFolder;
                    }
                    try {
                        com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams persistableLaunchParams = new com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams();
                        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                        while (true) {
                            int next = resolvePullParser.next();
                            if (next == 1 || next == 3) {
                                break;
                            }
                            if (next == 2) {
                                java.lang.String name2 = resolvePullParser.getName();
                                if (!"launch_params".equals(name2)) {
                                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                    file = launchParamFolder;
                                    try {
                                        sb.append("Unexpected tag name: ");
                                        sb.append(name2);
                                        android.util.Slog.w(TAG, sb.toString());
                                        launchParamFolder = file;
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
                                } else {
                                    persistableLaunchParams.restore(file2, resolvePullParser);
                                    launchParamFolder = launchParamFolder;
                                }
                            }
                        }
                        file = launchParamFolder;
                        arrayMap.put(unflattenFromString, persistableLaunchParams);
                        addComponentNameToLaunchParamAffinityMapIfNotNull(unflattenFromString, persistableLaunchParams.mWindowLayoutAffinity);
                        try {
                            fileInputStream.close();
                        } catch (java.lang.Exception e2) {
                            e = e2;
                            android.util.Slog.w(TAG, "Failed to restore launch params for " + unflattenFromString, e);
                            arrayList.add(file2);
                            i2++;
                            launchParamFolder = file;
                        }
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                        file = launchParamFolder;
                    }
                }
            }
            i2++;
            launchParamFolder = file;
        }
        if (!arrayList.isEmpty()) {
            this.mPersisterQueue.addItem(new com.android.server.wm.LaunchParamsPersister.CleanUpComponentQueueItem(arrayList), true);
        }
    }

    void saveTask(com.android.server.wm.Task task) {
        saveTask(task, task.getDisplayContent());
    }

    void saveTask(com.android.server.wm.Task task, com.android.server.wm.DisplayContent displayContent) {
        android.content.ComponentName componentName = task.realActivity;
        if (componentName == null) {
            return;
        }
        int i = task.mUserId;
        android.util.ArrayMap<android.content.ComponentName, com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams> arrayMap = this.mLaunchParamsMap.get(i);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>();
            this.mLaunchParamsMap.put(i, arrayMap);
        }
        com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams computeIfAbsent = arrayMap.computeIfAbsent(componentName, new java.util.function.Function() { // from class: com.android.server.wm.LaunchParamsPersister$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams lambda$saveTask$0;
                lambda$saveTask$0 = com.android.server.wm.LaunchParamsPersister.this.lambda$saveTask$0((android.content.ComponentName) obj);
                return lambda$saveTask$0;
            }
        });
        boolean saveTaskToLaunchParam = saveTaskToLaunchParam(task, displayContent, computeIfAbsent);
        addComponentNameToLaunchParamAffinityMapIfNotNull(componentName, computeIfAbsent.mWindowLayoutAffinity);
        if (saveTaskToLaunchParam) {
            this.mPersisterQueue.updateLastOrAddItem(new com.android.server.wm.LaunchParamsPersister.LaunchParamsWriteQueueItem(i, componentName, computeIfAbsent), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams lambda$saveTask$0(android.content.ComponentName componentName) {
        return new com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams();
    }

    private boolean saveTaskToLaunchParam(com.android.server.wm.Task task, com.android.server.wm.DisplayContent displayContent, com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams persistableLaunchParams) {
        boolean z;
        android.view.DisplayInfo displayInfo = new android.view.DisplayInfo();
        displayContent.mDisplay.getDisplayInfo(displayInfo);
        boolean z2 = !java.util.Objects.equals(persistableLaunchParams.mDisplayUniqueId, displayInfo.uniqueId);
        persistableLaunchParams.mDisplayUniqueId = displayInfo.uniqueId;
        boolean z3 = z2 | (persistableLaunchParams.mWindowingMode != task.getWindowingMode());
        persistableLaunchParams.mWindowingMode = task.getWindowingMode();
        if (task.mLastNonFullscreenBounds != null) {
            z = z3 | (!java.util.Objects.equals(persistableLaunchParams.mBounds, task.mLastNonFullscreenBounds));
            persistableLaunchParams.mBounds.set(task.mLastNonFullscreenBounds);
        } else {
            z = z3 | (!persistableLaunchParams.mBounds.isEmpty());
            persistableLaunchParams.mBounds.setEmpty();
        }
        java.lang.String str = task.mWindowLayoutAffinity;
        boolean equals = z | java.util.Objects.equals(str, persistableLaunchParams.mWindowLayoutAffinity);
        persistableLaunchParams.mWindowLayoutAffinity = str;
        if (equals) {
            persistableLaunchParams.mTimestamp = java.lang.System.currentTimeMillis();
        }
        return equals;
    }

    private void addComponentNameToLaunchParamAffinityMapIfNotNull(android.content.ComponentName componentName, java.lang.String str) {
        if (str == null) {
            return;
        }
        this.mWindowLayoutAffinityMap.computeIfAbsent(str, new java.util.function.Function() { // from class: com.android.server.wm.LaunchParamsPersister$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.util.ArraySet lambda$addComponentNameToLaunchParamAffinityMapIfNotNull$1;
                lambda$addComponentNameToLaunchParamAffinityMapIfNotNull$1 = com.android.server.wm.LaunchParamsPersister.lambda$addComponentNameToLaunchParamAffinityMapIfNotNull$1((java.lang.String) obj);
                return lambda$addComponentNameToLaunchParamAffinityMapIfNotNull$1;
            }
        }).add(componentName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.util.ArraySet lambda$addComponentNameToLaunchParamAffinityMapIfNotNull$1(java.lang.String str) {
        return new android.util.ArraySet();
    }

    void getLaunchParams(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.LaunchParamsController.LaunchParams launchParams) {
        java.lang.String str;
        android.content.ComponentName componentName = task != null ? task.realActivity : activityRecord.mActivityComponent;
        int i = task != null ? task.mUserId : activityRecord.mUserId;
        if (task != null) {
            str = task.mWindowLayoutAffinity;
        } else {
            android.content.pm.ActivityInfo.WindowLayout windowLayout = activityRecord.info.windowLayout;
            str = windowLayout == null ? null : windowLayout.windowLayoutAffinity;
        }
        launchParams.reset();
        android.util.ArrayMap<android.content.ComponentName, com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams> arrayMap = this.mLaunchParamsMap.get(i);
        if (arrayMap == null) {
            return;
        }
        com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams persistableLaunchParams = arrayMap.get(componentName);
        if (str != null && this.mWindowLayoutAffinityMap.get(str) != null) {
            android.util.ArraySet<android.content.ComponentName> arraySet = this.mWindowLayoutAffinityMap.get(str);
            for (int i2 = 0; i2 < arraySet.size(); i2++) {
                com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams persistableLaunchParams2 = arrayMap.get(arraySet.valueAt(i2));
                if (persistableLaunchParams2 != null && (persistableLaunchParams == null || persistableLaunchParams2.mTimestamp > persistableLaunchParams.mTimestamp)) {
                    persistableLaunchParams = persistableLaunchParams2;
                }
            }
        }
        if (persistableLaunchParams == null) {
            return;
        }
        com.android.server.wm.DisplayContent displayContent = this.mSupervisor.mRootWindowContainer.getDisplayContent(persistableLaunchParams.mDisplayUniqueId);
        if (displayContent != null) {
            launchParams.mPreferredTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
        }
        launchParams.mWindowingMode = persistableLaunchParams.mWindowingMode;
        launchParams.mBounds.set(persistableLaunchParams.mBounds);
    }

    void removeRecordForPackage(final java.lang.String str) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mLaunchParamsMap.size(); i++) {
            java.io.File launchParamFolder = getLaunchParamFolder(this.mLaunchParamsMap.keyAt(i));
            android.util.ArrayMap<android.content.ComponentName, com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams> valueAt = this.mLaunchParamsMap.valueAt(i);
            for (int size = valueAt.size() - 1; size >= 0; size--) {
                android.content.ComponentName keyAt = valueAt.keyAt(size);
                if (keyAt.getPackageName().equals(str)) {
                    valueAt.removeAt(size);
                    arrayList.add(getParamFile(launchParamFolder, keyAt));
                }
            }
        }
        synchronized (this.mPersisterQueue) {
            this.mPersisterQueue.removeItems(new java.util.function.Predicate() { // from class: com.android.server.wm.LaunchParamsPersister$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeRecordForPackage$2;
                    lambda$removeRecordForPackage$2 = com.android.server.wm.LaunchParamsPersister.lambda$removeRecordForPackage$2(str, (com.android.server.wm.LaunchParamsPersister.LaunchParamsWriteQueueItem) obj);
                    return lambda$removeRecordForPackage$2;
                }
            }, com.android.server.wm.LaunchParamsPersister.LaunchParamsWriteQueueItem.class);
            this.mPersisterQueue.addItem(new com.android.server.wm.LaunchParamsPersister.CleanUpComponentQueueItem(arrayList), true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeRecordForPackage$2(java.lang.String str, com.android.server.wm.LaunchParamsPersister.LaunchParamsWriteQueueItem launchParamsWriteQueueItem) {
        return launchParamsWriteQueueItem.mComponentName.getPackageName().equals(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.io.File getParamFile(java.io.File file, android.content.ComponentName componentName) {
        return new java.io.File(file, componentName.flattenToShortString().replace(ORIGINAL_COMPONENT_SEPARATOR, ESCAPED_COMPONENT_SEPARATOR) + LAUNCH_PARAMS_FILE_SUFFIX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.io.File getLaunchParamFolder(int i) {
        return new java.io.File(this.mUserFolderGetter.apply(i), "launch_params");
    }

    private class PackageListObserver implements android.content.pm.PackageManagerInternal.PackageListObserver {
        private PackageListObserver() {
        }

        @Override // android.content.pm.PackageManagerInternal.PackageListObserver
        public void onPackageAdded(java.lang.String str, int i) {
        }

        @Override // android.content.pm.PackageManagerInternal.PackageListObserver
        public void onPackageRemoved(java.lang.String str, int i) {
            com.android.server.wm.WindowManagerGlobalLock globalLock = com.android.server.wm.LaunchParamsPersister.this.mSupervisor.mService.getGlobalLock();
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (globalLock) {
                try {
                    com.android.server.wm.LaunchParamsPersister.this.removeRecordForPackage(str);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class LaunchParamsWriteQueueItem implements com.android.server.wm.PersisterQueue.WriteQueueItem<com.android.server.wm.LaunchParamsPersister.LaunchParamsWriteQueueItem> {
        private final android.content.ComponentName mComponentName;
        private com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams mLaunchParams;
        private final int mUserId;

        private LaunchParamsWriteQueueItem(int i, android.content.ComponentName componentName, com.android.server.wm.LaunchParamsPersister.PersistableLaunchParams persistableLaunchParams) {
            this.mUserId = i;
            this.mComponentName = componentName;
            this.mLaunchParams = persistableLaunchParams;
        }

        private byte[] saveParamsToXml() {
            try {
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(byteArrayOutputStream);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.startTag((java.lang.String) null, "launch_params");
                this.mLaunchParams.saveToXml(resolveSerializer);
                resolveSerializer.endTag((java.lang.String) null, "launch_params");
                resolveSerializer.endDocument();
                resolveSerializer.flush();
                return byteArrayOutputStream.toByteArray();
            } catch (java.io.IOException e) {
                return null;
            }
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public void process() {
            java.io.FileOutputStream fileOutputStream;
            byte[] saveParamsToXml = saveParamsToXml();
            java.io.File launchParamFolder = com.android.server.wm.LaunchParamsPersister.this.getLaunchParamFolder(this.mUserId);
            if (!launchParamFolder.isDirectory() && !launchParamFolder.mkdir()) {
                android.util.Slog.w(com.android.server.wm.LaunchParamsPersister.TAG, "Failed to create folder for " + this.mUserId);
                return;
            }
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(com.android.server.wm.LaunchParamsPersister.this.getParamFile(launchParamFolder, this.mComponentName));
            try {
                fileOutputStream = atomicFile.startWrite();
                try {
                    fileOutputStream.write(saveParamsToXml);
                    atomicFile.finishWrite(fileOutputStream);
                } catch (java.lang.Exception e) {
                    e = e;
                    android.util.Slog.e(com.android.server.wm.LaunchParamsPersister.TAG, "Failed to write param file for " + this.mComponentName, e);
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                }
            } catch (java.lang.Exception e2) {
                e = e2;
                fileOutputStream = null;
            }
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public boolean matches(com.android.server.wm.LaunchParamsPersister.LaunchParamsWriteQueueItem launchParamsWriteQueueItem) {
            return this.mUserId == launchParamsWriteQueueItem.mUserId && this.mComponentName.equals(launchParamsWriteQueueItem.mComponentName);
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public void updateFrom(com.android.server.wm.LaunchParamsPersister.LaunchParamsWriteQueueItem launchParamsWriteQueueItem) {
            this.mLaunchParams = launchParamsWriteQueueItem.mLaunchParams;
        }
    }

    private class CleanUpComponentQueueItem implements com.android.server.wm.PersisterQueue.WriteQueueItem {
        private final java.util.List<java.io.File> mComponentFiles;

        private CleanUpComponentQueueItem(java.util.List<java.io.File> list) {
            this.mComponentFiles = list;
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public void process() {
            for (java.io.File file : this.mComponentFiles) {
                if (!file.delete()) {
                    android.util.Slog.w(com.android.server.wm.LaunchParamsPersister.TAG, "Failed to delete " + file.getAbsolutePath());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PersistableLaunchParams {
        private static final java.lang.String ATTR_BOUNDS = "bounds";
        private static final java.lang.String ATTR_DISPLAY_UNIQUE_ID = "display_unique_id";
        private static final java.lang.String ATTR_WINDOWING_MODE = "windowing_mode";
        private static final java.lang.String ATTR_WINDOW_LAYOUT_AFFINITY = "window_layout_affinity";
        final android.graphics.Rect mBounds;
        java.lang.String mDisplayUniqueId;
        long mTimestamp;

        @android.annotation.Nullable
        java.lang.String mWindowLayoutAffinity;
        int mWindowingMode;

        private PersistableLaunchParams() {
            this.mBounds = new android.graphics.Rect();
        }

        void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_DISPLAY_UNIQUE_ID, this.mDisplayUniqueId);
            typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_WINDOWING_MODE, this.mWindowingMode);
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_BOUNDS, this.mBounds.flattenToString());
            if (this.mWindowLayoutAffinity != null) {
                typedXmlSerializer.attribute((java.lang.String) null, ATTR_WINDOW_LAYOUT_AFFINITY, this.mWindowLayoutAffinity);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        void restore(java.io.File file, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
            char c;
            for (int i = 0; i < typedXmlPullParser.getAttributeCount(); i++) {
                java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(i);
                java.lang.String attributeName = typedXmlPullParser.getAttributeName(i);
                switch (attributeName.hashCode()) {
                    case -1499361012:
                        if (attributeName.equals(ATTR_DISPLAY_UNIQUE_ID)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1383205195:
                        if (attributeName.equals(ATTR_BOUNDS)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 748872656:
                        if (attributeName.equals(ATTR_WINDOWING_MODE)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1999609934:
                        if (attributeName.equals(ATTR_WINDOW_LAYOUT_AFFINITY)) {
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
                        this.mDisplayUniqueId = attributeValue;
                        break;
                    case 1:
                        this.mWindowingMode = java.lang.Integer.parseInt(attributeValue);
                        break;
                    case 2:
                        android.graphics.Rect unflattenFromString = android.graphics.Rect.unflattenFromString(attributeValue);
                        if (unflattenFromString != null) {
                            this.mBounds.set(unflattenFromString);
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        this.mWindowLayoutAffinity = attributeValue;
                        break;
                }
            }
            this.mTimestamp = file.lastModified();
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("PersistableLaunchParams{");
            sb.append(" windowingMode=" + this.mWindowingMode);
            sb.append(" displayUniqueId=" + this.mDisplayUniqueId);
            sb.append(" bounds=" + this.mBounds);
            if (this.mWindowLayoutAffinity != null) {
                sb.append(" launchParamsAffinity=" + this.mWindowLayoutAffinity);
            }
            sb.append(" timestamp=" + this.mTimestamp);
            sb.append(" }");
            return sb.toString();
        }
    }
}
