package com.android.server.wm;

/* loaded from: classes3.dex */
public class TaskPersister implements com.android.server.wm.PersisterQueue.Listener {
    static final boolean DEBUG = false;
    private static final java.lang.String IMAGES_DIRNAME = "recent_images";
    static final java.lang.String IMAGE_EXTENSION = ".png";
    private static final java.lang.String PERSISTED_TASK_IDS_FILENAME = "persisted_taskIds.txt";
    static final java.lang.String TAG = "TaskPersister";
    private static final java.lang.String TAG_TASK = "task";
    private static final java.lang.String TASKS_DIRNAME = "recent_tasks";
    private static final java.lang.String TASK_FILENAME_SUFFIX = "_task.xml";
    private final java.lang.Object mIoLock;
    private final com.android.server.wm.PersisterQueue mPersisterQueue;
    private final com.android.server.wm.RecentTasks mRecentTasks;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private final java.io.File mTaskIdsDir;
    private final android.util.SparseArray<android.util.SparseBooleanArray> mTaskIdsInFile;
    private final com.android.server.wm.ActivityTaskSupervisor mTaskSupervisor;
    private final android.util.ArraySet<java.lang.Integer> mTmpTaskIds;

    TaskPersister(java.io.File file, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.RecentTasks recentTasks, com.android.server.wm.PersisterQueue persisterQueue) {
        this.mTaskIdsInFile = new android.util.SparseArray<>();
        this.mIoLock = new java.lang.Object();
        this.mTmpTaskIds = new android.util.ArraySet<>();
        java.io.File file2 = new java.io.File(file, IMAGES_DIRNAME);
        if (file2.exists() && (!android.os.FileUtils.deleteContents(file2) || !file2.delete())) {
            android.util.Slog.i(TAG, "Failure deleting legacy images directory: " + file2);
        }
        java.io.File file3 = new java.io.File(file, TASKS_DIRNAME);
        if (file3.exists() && (!android.os.FileUtils.deleteContents(file3) || !file3.delete())) {
            android.util.Slog.i(TAG, "Failure deleting legacy tasks directory: " + file3);
        }
        this.mTaskIdsDir = new java.io.File(android.os.Environment.getDataDirectory(), "system_de");
        this.mTaskSupervisor = activityTaskSupervisor;
        this.mService = activityTaskManagerService;
        this.mRecentTasks = recentTasks;
        this.mPersisterQueue = persisterQueue;
        this.mPersisterQueue.addListener(this);
    }

    @com.android.internal.annotations.VisibleForTesting
    TaskPersister(java.io.File file) {
        this.mTaskIdsInFile = new android.util.SparseArray<>();
        this.mIoLock = new java.lang.Object();
        this.mTmpTaskIds = new android.util.ArraySet<>();
        this.mTaskIdsDir = file;
        this.mTaskSupervisor = null;
        this.mService = null;
        this.mRecentTasks = null;
        this.mPersisterQueue = new com.android.server.wm.PersisterQueue();
        this.mPersisterQueue.addListener(this);
    }

    private void removeThumbnails(final com.android.server.wm.Task task) {
        this.mPersisterQueue.removeItems(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskPersister$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeThumbnails$0;
                lambda$removeThumbnails$0 = com.android.server.wm.TaskPersister.lambda$removeThumbnails$0(com.android.server.wm.Task.this, (com.android.server.wm.TaskPersister.ImageWriteQueueItem) obj);
                return lambda$removeThumbnails$0;
            }
        }, com.android.server.wm.TaskPersister.ImageWriteQueueItem.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeThumbnails$0(com.android.server.wm.Task task, com.android.server.wm.TaskPersister.ImageWriteQueueItem imageWriteQueueItem) {
        return new java.io.File(imageWriteQueueItem.mFilePath).getName().startsWith(java.lang.Integer.toString(task.mTaskId));
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0036: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:40:0x0036 */
    @android.annotation.NonNull
    android.util.SparseBooleanArray readPersistedTaskIdsFromFileForUser(int i) {
        java.io.BufferedReader bufferedReader;
        java.io.BufferedReader bufferedReader2;
        java.lang.Exception e;
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        synchronized (this.mIoLock) {
            java.io.BufferedReader bufferedReader3 = null;
            try {
                try {
                    bufferedReader2 = new java.io.BufferedReader(new java.io.FileReader(getUserPersistedTaskIdsFile(i)));
                    while (true) {
                        try {
                            java.lang.String readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            }
                            for (java.lang.String str : readLine.split("\\s+")) {
                                sparseBooleanArray.put(java.lang.Integer.parseInt(str), true);
                            }
                        } catch (java.io.FileNotFoundException e2) {
                            bufferedReader3 = bufferedReader2;
                            libcore.io.IoUtils.closeQuietly(bufferedReader3);
                            android.util.Slog.i(TAG, "Loaded persisted task ids for user " + i);
                            return sparseBooleanArray;
                        } catch (java.lang.Exception e3) {
                            e = e3;
                            android.util.Slog.e(TAG, "Error while reading taskIds file for user " + i, e);
                            libcore.io.IoUtils.closeQuietly(bufferedReader2);
                            android.util.Slog.i(TAG, "Loaded persisted task ids for user " + i);
                            return sparseBooleanArray;
                        }
                    }
                } catch (java.io.FileNotFoundException e4) {
                } catch (java.lang.Exception e5) {
                    bufferedReader2 = null;
                    e = e5;
                } catch (java.lang.Throwable th) {
                    th = th;
                    libcore.io.IoUtils.closeQuietly(bufferedReader3);
                    throw th;
                }
                libcore.io.IoUtils.closeQuietly(bufferedReader2);
            } catch (java.lang.Throwable th2) {
                th = th2;
                bufferedReader3 = bufferedReader;
            }
        }
        android.util.Slog.i(TAG, "Loaded persisted task ids for user " + i);
        return sparseBooleanArray;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    @com.android.internal.annotations.VisibleForTesting
    void writePersistedTaskIdsForUser(@android.annotation.NonNull android.util.SparseBooleanArray sparseBooleanArray, int i) {
        int size;
        if (i < 0) {
            return;
        }
        java.io.File userPersistedTaskIdsFile = getUserPersistedTaskIdsFile(i);
        synchronized (this.mIoLock) {
            ?? r2 = 0;
            java.io.BufferedWriter bufferedWriter = null;
            try {
                try {
                    java.io.BufferedWriter bufferedWriter2 = new java.io.BufferedWriter(new java.io.FileWriter(userPersistedTaskIdsFile));
                    int i2 = 0;
                    while (true) {
                        try {
                            size = sparseBooleanArray.size();
                            if (i2 >= size) {
                                break;
                            }
                            if (sparseBooleanArray.valueAt(i2)) {
                                bufferedWriter2.write(java.lang.String.valueOf(sparseBooleanArray.keyAt(i2)));
                                bufferedWriter2.newLine();
                            }
                            i2++;
                        } catch (java.lang.Exception e) {
                            e = e;
                            bufferedWriter = bufferedWriter2;
                            android.util.Slog.e(TAG, "Error while writing taskIds file for user " + i, e);
                            libcore.io.IoUtils.closeQuietly(bufferedWriter);
                            r2 = bufferedWriter;
                        } catch (java.lang.Throwable th) {
                            th = th;
                            r2 = bufferedWriter2;
                            libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) r2);
                            throw th;
                        }
                    }
                    libcore.io.IoUtils.closeQuietly(bufferedWriter2);
                    r2 = size;
                } catch (java.lang.Exception e2) {
                    e = e2;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        }
    }

    void setPersistedTaskIds(int i, @android.annotation.NonNull android.util.SparseBooleanArray sparseBooleanArray) {
        this.mTaskIdsInFile.put(i, sparseBooleanArray);
    }

    void unloadUserDataFromMemory(int i) {
        this.mTaskIdsInFile.delete(i);
    }

    void wakeup(final com.android.server.wm.Task task, boolean z) {
        synchronized (this.mPersisterQueue) {
            try {
                if (task != null) {
                    com.android.server.wm.TaskPersister.TaskWriteQueueItem taskWriteQueueItem = (com.android.server.wm.TaskPersister.TaskWriteQueueItem) this.mPersisterQueue.findLastItem(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskPersister$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$wakeup$1;
                            lambda$wakeup$1 = com.android.server.wm.TaskPersister.lambda$wakeup$1(com.android.server.wm.Task.this, (com.android.server.wm.TaskPersister.TaskWriteQueueItem) obj);
                            return lambda$wakeup$1;
                        }
                    }, com.android.server.wm.TaskPersister.TaskWriteQueueItem.class);
                    if (taskWriteQueueItem != null && !task.inRecents) {
                        removeThumbnails(task);
                    }
                    if (taskWriteQueueItem == null && task.isPersistable) {
                        this.mPersisterQueue.addItem(new com.android.server.wm.TaskPersister.TaskWriteQueueItem(task, this.mService), z);
                    }
                } else {
                    this.mPersisterQueue.addItem(com.android.server.wm.PersisterQueue.EMPTY_ITEM, z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mPersisterQueue.yieldIfQueueTooDeep();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$wakeup$1(com.android.server.wm.Task task, com.android.server.wm.TaskPersister.TaskWriteQueueItem taskWriteQueueItem) {
        return task == taskWriteQueueItem.mTask;
    }

    void flush() {
        this.mPersisterQueue.flush();
    }

    void saveImage(android.graphics.Bitmap bitmap, java.lang.String str) {
        this.mPersisterQueue.updateLastOrAddItem(new com.android.server.wm.TaskPersister.ImageWriteQueueItem(str, bitmap), false);
    }

    android.graphics.Bitmap getTaskDescriptionIcon(java.lang.String str) {
        android.graphics.Bitmap imageFromWriteQueue = getImageFromWriteQueue(str);
        if (imageFromWriteQueue != null) {
            return imageFromWriteQueue;
        }
        return restoreImage(str);
    }

    private android.graphics.Bitmap getImageFromWriteQueue(final java.lang.String str) {
        com.android.server.wm.TaskPersister.ImageWriteQueueItem imageWriteQueueItem = (com.android.server.wm.TaskPersister.ImageWriteQueueItem) this.mPersisterQueue.findLastItem(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskPersister$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getImageFromWriteQueue$2;
                lambda$getImageFromWriteQueue$2 = com.android.server.wm.TaskPersister.lambda$getImageFromWriteQueue$2(str, (com.android.server.wm.TaskPersister.ImageWriteQueueItem) obj);
                return lambda$getImageFromWriteQueue$2;
            }
        }, com.android.server.wm.TaskPersister.ImageWriteQueueItem.class);
        if (imageWriteQueueItem != null) {
            return imageWriteQueueItem.mImage;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getImageFromWriteQueue$2(java.lang.String str, com.android.server.wm.TaskPersister.ImageWriteQueueItem imageWriteQueueItem) {
        return imageWriteQueueItem.mFilePath.equals(str);
    }

    private static java.lang.String fileToString(java.io.File file) {
        java.lang.String lineSeparator = java.lang.System.lineSeparator();
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(file));
            java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(((int) file.length()) * 2);
            while (true) {
                java.lang.String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuffer.append(readLine + lineSeparator);
                } else {
                    bufferedReader.close();
                    return stringBuffer.toString();
                }
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Couldn't read file " + file.getName());
            return null;
        }
    }

    private com.android.server.wm.Task taskIdToTask(int i, java.util.ArrayList<com.android.server.wm.Task> arrayList) {
        if (i < 0) {
            return null;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task task = arrayList.get(size);
            if (task.mTaskId == i) {
                return task;
            }
        }
        android.util.Slog.e(TAG, "Restore affiliation error looking for taskId=" + i);
        return null;
    }

    static com.android.server.wm.TaskPersister.RecentTaskFiles loadTasksForUser(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.io.File userTasksDir = getUserTasksDir(i);
        java.io.File[] listFiles = userTasksDir.listFiles();
        if (listFiles == null) {
            android.util.Slog.i(TAG, "loadTasksForUser: Unable to list files from " + userTasksDir + " exists=" + userTasksDir.exists());
            return new com.android.server.wm.TaskPersister.RecentTaskFiles(new java.io.File[0], arrayList);
        }
        for (java.io.File file : listFiles) {
            if (file.getName().endsWith(TASK_FILENAME_SUFFIX)) {
                try {
                    try {
                        arrayList.add(new com.android.server.wm.TaskPersister.RecentTaskFile(java.lang.Integer.parseInt(file.getName().substring(0, file.getName().length() - TASK_FILENAME_SUFFIX.length())), file));
                    } catch (java.io.IOException e) {
                        android.util.Slog.w(TAG, "Failed to read file: " + fileToString(file), e);
                        file.delete();
                    }
                } catch (java.lang.NumberFormatException e2) {
                    android.util.Slog.w(TAG, "Unexpected task file name", e2);
                }
            }
        }
        return new com.android.server.wm.TaskPersister.RecentTaskFiles(listFiles, arrayList);
    }

    java.util.ArrayList<com.android.server.wm.Task> restoreTasksForUserLocked(int i, com.android.server.wm.TaskPersister.RecentTaskFiles recentTaskFiles, android.util.IntArray intArray) {
        java.util.ArrayList<com.android.server.wm.TaskPersister.RecentTaskFile> arrayList;
        java.util.ArrayList<com.android.server.wm.Task> arrayList2 = new java.util.ArrayList<>();
        java.util.ArrayList<com.android.server.wm.TaskPersister.RecentTaskFile> arrayList3 = recentTaskFiles.mLoadedFiles;
        if (arrayList3.isEmpty()) {
            return arrayList2;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int i2 = 0;
        while (true) {
            int i3 = 1;
            if (i2 >= arrayList3.size()) {
                removeObsoleteFiles(arraySet, recentTaskFiles.mUserTaskFiles);
                for (int size = arrayList2.size() - 1; size >= 0; size--) {
                    com.android.server.wm.Task task = arrayList2.get(size);
                    task.setPrevAffiliate(taskIdToTask(task.mPrevAffiliateTaskId, arrayList2));
                    task.setNextAffiliate(taskIdToTask(task.mNextAffiliateTaskId, arrayList2));
                }
                java.util.Collections.sort(arrayList2, new java.util.Comparator<com.android.server.wm.Task>() { // from class: com.android.server.wm.TaskPersister.1
                    @Override // java.util.Comparator
                    public int compare(com.android.server.wm.Task task2, com.android.server.wm.Task task3) {
                        long j = task3.mLastTimeMoved - task2.mLastTimeMoved;
                        if (j < 0) {
                            return -1;
                        }
                        if (j > 0) {
                            return 1;
                        }
                        return 0;
                    }
                });
                return arrayList2;
            }
            com.android.server.wm.TaskPersister.RecentTaskFile recentTaskFile = arrayList3.get(i2);
            if (intArray.contains(recentTaskFile.mTaskId)) {
                android.util.Slog.w(TAG, "Task #" + recentTaskFile.mTaskId + " has already been created, so skip restoring");
                arrayList = arrayList3;
            } else {
                java.io.File file = recentTaskFile.mFile;
                try {
                    java.io.ByteArrayInputStream byteArrayInputStream = recentTaskFile.mXmlContent;
                    try {
                        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(byteArrayInputStream);
                        while (true) {
                            int next = resolvePullParser.next();
                            if (next == i3 || next == 3) {
                                break;
                            }
                            java.lang.String name = resolvePullParser.getName();
                            if (next != 2) {
                                arrayList = arrayList3;
                            } else if (TAG_TASK.equals(name)) {
                                com.android.server.wm.Task restoreFromXml = com.android.server.wm.Task.restoreFromXml(resolvePullParser, this.mTaskSupervisor);
                                if (restoreFromXml != null) {
                                    int i4 = restoreFromXml.mTaskId;
                                    boolean hasActivity = restoreFromXml.hasActivity();
                                    if (hasActivity) {
                                        arrayList = arrayList3;
                                        try {
                                            if (this.mRecentTasks.getTask(i4) != null) {
                                                android.util.Slog.wtf(TAG, "Existing persisted task with taskId " + i4 + " found");
                                            }
                                        } catch (java.lang.Throwable th) {
                                            th = th;
                                            java.lang.Throwable th2 = th;
                                            if (byteArrayInputStream != null) {
                                                try {
                                                    byteArrayInputStream.close();
                                                } catch (java.lang.Throwable th3) {
                                                    th2.addSuppressed(th3);
                                                }
                                            }
                                            throw th2;
                                        }
                                    } else {
                                        arrayList = arrayList3;
                                    }
                                    if (!hasActivity && this.mService.mRootWindowContainer.anyTaskForId(i4, 1) != null) {
                                        android.util.Slog.wtf(TAG, "Existing task with taskId " + i4 + " found");
                                    } else if (i != restoreFromXml.mUserId) {
                                        android.util.Slog.wtf(TAG, "Task with userId " + restoreFromXml.mUserId + " found in " + file.getAbsolutePath());
                                    } else {
                                        this.mTaskSupervisor.setNextTaskIdForUser(i4, i);
                                        restoreFromXml.isPersistable = true;
                                        arrayList2.add(restoreFromXml);
                                        arraySet.add(java.lang.Integer.valueOf(i4));
                                    }
                                } else {
                                    arrayList = arrayList3;
                                    android.util.Slog.e(TAG, "restoreTasksForUserLocked: Unable to restore taskFile=" + file + ": " + fileToString(file));
                                }
                            } else {
                                arrayList = arrayList3;
                                android.util.Slog.wtf(TAG, "restoreTasksForUserLocked: Unknown xml event=" + next + " name=" + name);
                            }
                            com.android.internal.util.XmlUtils.skipCurrentTag(resolvePullParser);
                            arrayList3 = arrayList;
                            i3 = 1;
                        }
                        arrayList = arrayList3;
                        if (byteArrayInputStream != null) {
                            try {
                                byteArrayInputStream.close();
                            } catch (java.lang.Exception e) {
                                e = e;
                                android.util.Slog.wtf(TAG, "Unable to parse " + file + ". Error ", e);
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                sb.append("Failing file: ");
                                sb.append(fileToString(file));
                                android.util.Slog.e(TAG, sb.toString());
                                file.delete();
                                i2++;
                                arrayList3 = arrayList;
                            }
                        }
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                        arrayList = arrayList3;
                    }
                } catch (java.lang.Exception e2) {
                    e = e2;
                    arrayList = arrayList3;
                }
            }
            i2++;
            arrayList3 = arrayList;
        }
    }

    @Override // com.android.server.wm.PersisterQueue.Listener
    public void onPreProcessItem(boolean z) {
        if (z) {
            this.mTmpTaskIds.clear();
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mRecentTasks.getPersistableTaskIds(this.mTmpTaskIds);
                    this.mService.mWindowManager.removeObsoleteTaskFiles(this.mTmpTaskIds, this.mRecentTasks.usersWithRecentsLoadedLocked());
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            removeObsoleteFiles(this.mTmpTaskIds);
        }
        writeTaskIdsFiles();
    }

    private static void removeObsoleteFiles(android.util.ArraySet<java.lang.Integer> arraySet, java.io.File[] fileArr) {
        if (fileArr == null) {
            android.util.Slog.e(TAG, "File error accessing recents directory (directory doesn't exist?).");
            return;
        }
        for (java.io.File file : fileArr) {
            java.lang.String name = file.getName();
            int indexOf = name.indexOf(95);
            if (indexOf > 0) {
                try {
                    if (!arraySet.contains(java.lang.Integer.valueOf(java.lang.Integer.parseInt(name.substring(0, indexOf))))) {
                        file.delete();
                    }
                } catch (java.lang.Exception e) {
                    android.util.Slog.wtf(TAG, "removeObsoleteFiles: Can't parse file=" + file.getName());
                    file.delete();
                }
            }
        }
    }

    private void writeTaskIdsFiles() {
        int i;
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                for (int i2 : this.mRecentTasks.usersWithRecentsLoadedLocked()) {
                    android.util.SparseBooleanArray taskIdsForLoadedUser = this.mRecentTasks.getTaskIdsForLoadedUser(i2);
                    android.util.SparseBooleanArray sparseBooleanArray = this.mTaskIdsInFile.get(i2);
                    if (sparseBooleanArray == null || !sparseBooleanArray.equals(taskIdsForLoadedUser)) {
                        android.util.SparseBooleanArray clone = taskIdsForLoadedUser.clone();
                        this.mTaskIdsInFile.put(i2, clone);
                        sparseArray.put(i2, clone);
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        for (i = 0; i < sparseArray.size(); i++) {
            writePersistedTaskIdsForUser((android.util.SparseBooleanArray) sparseArray.valueAt(i), sparseArray.keyAt(i));
        }
    }

    private void removeObsoleteFiles(android.util.ArraySet<java.lang.Integer> arraySet) {
        int[] usersWithRecentsLoadedLocked;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                usersWithRecentsLoadedLocked = this.mRecentTasks.usersWithRecentsLoadedLocked();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        for (int i : usersWithRecentsLoadedLocked) {
            removeObsoleteFiles(arraySet, getUserImagesDir(i).listFiles());
            removeObsoleteFiles(arraySet, getUserTasksDir(i).listFiles());
        }
    }

    static android.graphics.Bitmap restoreImage(java.lang.String str) {
        return android.graphics.BitmapFactory.decodeFile(str);
    }

    private java.io.File getUserPersistedTaskIdsFile(int i) {
        java.io.File file = new java.io.File(this.mTaskIdsDir, java.lang.String.valueOf(i));
        if (!file.exists() && !file.mkdirs()) {
            android.util.Slog.e(TAG, "Error while creating user directory: " + file);
        }
        return new java.io.File(file, PERSISTED_TASK_IDS_FILENAME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.io.File getUserTasksDir(int i) {
        return new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), TASKS_DIRNAME);
    }

    static java.io.File getUserImagesDir(int i) {
        return new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), IMAGES_DIRNAME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean createParentDirectory(java.lang.String str) {
        java.io.File parentFile = new java.io.File(str).getParentFile();
        return parentFile.isDirectory() || parentFile.mkdir();
    }

    private static class RecentTaskFile {
        final java.io.File mFile;
        final int mTaskId;
        final java.io.ByteArrayInputStream mXmlContent;

        RecentTaskFile(int i, java.io.File file) throws java.io.IOException {
            this.mTaskId = i;
            this.mFile = file;
            this.mXmlContent = new java.io.ByteArrayInputStream(java.nio.file.Files.readAllBytes(file.toPath()));
        }
    }

    static class RecentTaskFiles {
        final java.util.ArrayList<com.android.server.wm.TaskPersister.RecentTaskFile> mLoadedFiles;
        final java.io.File[] mUserTaskFiles;

        RecentTaskFiles(java.io.File[] fileArr, java.util.ArrayList<com.android.server.wm.TaskPersister.RecentTaskFile> arrayList) {
            this.mUserTaskFiles = fileArr;
            this.mLoadedFiles = arrayList;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TaskWriteQueueItem implements com.android.server.wm.PersisterQueue.WriteQueueItem {
        private final com.android.server.wm.ActivityTaskManagerService mService;
        private final com.android.server.wm.Task mTask;

        TaskWriteQueueItem(com.android.server.wm.Task task, com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
            this.mTask = task;
            this.mService = activityTaskManagerService;
        }

        private byte[] saveToXml(com.android.server.wm.Task task) throws java.lang.Exception {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(byteArrayOutputStream);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.startTag((java.lang.String) null, com.android.server.wm.TaskPersister.TAG_TASK);
            task.saveToXml(resolveSerializer);
            resolveSerializer.endTag((java.lang.String) null, com.android.server.wm.TaskPersister.TAG_TASK);
            resolveSerializer.endDocument();
            resolveSerializer.flush();
            return byteArrayOutputStream.toByteArray();
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public void process() {
            java.io.FileOutputStream fileOutputStream;
            byte[] saveToXml;
            android.util.AtomicFile atomicFile;
            com.android.server.wm.Task task = this.mTask;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    fileOutputStream = null;
                    if (task.inRecents) {
                        try {
                            saveToXml = saveToXml(task);
                        } catch (java.lang.Exception e) {
                        }
                    }
                    saveToXml = null;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            if (saveToXml != null) {
                try {
                    java.io.File userTasksDir = com.android.server.wm.TaskPersister.getUserTasksDir(task.mUserId);
                    if (!userTasksDir.isDirectory() && !userTasksDir.mkdirs()) {
                        android.util.Slog.e(com.android.server.wm.TaskPersister.TAG, "Failure creating tasks directory for user " + task.mUserId + ": " + userTasksDir + " Dropping persistence for task " + task);
                        return;
                    }
                    atomicFile = new android.util.AtomicFile(new java.io.File(userTasksDir, java.lang.String.valueOf(task.mTaskId) + com.android.server.wm.TaskPersister.TASK_FILENAME_SUFFIX));
                    try {
                        fileOutputStream = atomicFile.startWrite();
                        fileOutputStream.write(saveToXml);
                        atomicFile.finishWrite(fileOutputStream);
                    } catch (java.io.IOException e2) {
                        e = e2;
                        if (fileOutputStream != null) {
                            atomicFile.failWrite(fileOutputStream);
                        }
                        android.util.Slog.e(com.android.server.wm.TaskPersister.TAG, "Unable to open " + atomicFile + " for persisting. " + e);
                    }
                } catch (java.io.IOException e3) {
                    e = e3;
                    atomicFile = null;
                }
            }
        }

        public java.lang.String toString() {
            return "TaskWriteQueueItem{task=" + this.mTask + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ImageWriteQueueItem implements com.android.server.wm.PersisterQueue.WriteQueueItem<com.android.server.wm.TaskPersister.ImageWriteQueueItem> {
        final java.lang.String mFilePath;
        android.graphics.Bitmap mImage;

        ImageWriteQueueItem(java.lang.String str, android.graphics.Bitmap bitmap) {
            this.mFilePath = str;
            this.mImage = bitmap;
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public void process() {
            java.lang.String str = this.mFilePath;
            if (!com.android.server.wm.TaskPersister.createParentDirectory(str)) {
                android.util.Slog.e(com.android.server.wm.TaskPersister.TAG, "Error while creating images directory for file: " + str);
                return;
            }
            android.graphics.Bitmap bitmap = this.mImage;
            java.io.FileOutputStream fileOutputStream = null;
            try {
                try {
                    java.io.FileOutputStream fileOutputStream2 = new java.io.FileOutputStream(new java.io.File(str));
                    try {
                        bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                        libcore.io.IoUtils.closeQuietly(fileOutputStream2);
                    } catch (java.lang.Exception e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        android.util.Slog.e(com.android.server.wm.TaskPersister.TAG, "saveImage: unable to save " + str, e);
                        libcore.io.IoUtils.closeQuietly(fileOutputStream);
                    } catch (java.lang.Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        libcore.io.IoUtils.closeQuietly(fileOutputStream);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                }
            } catch (java.lang.Exception e2) {
                e = e2;
            }
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public boolean matches(com.android.server.wm.TaskPersister.ImageWriteQueueItem imageWriteQueueItem) {
            return this.mFilePath.equals(imageWriteQueueItem.mFilePath);
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public void updateFrom(com.android.server.wm.TaskPersister.ImageWriteQueueItem imageWriteQueueItem) {
            this.mImage = imageWriteQueueItem.mImage;
        }

        public java.lang.String toString() {
            return "ImageWriteQueueItem{path=" + this.mFilePath + ", image=(" + this.mImage.getWidth() + "x" + this.mImage.getHeight() + ")}";
        }
    }
}
