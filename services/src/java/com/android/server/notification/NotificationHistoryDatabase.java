package com.android.server.notification;

/* loaded from: classes2.dex */
public class NotificationHistoryDatabase {
    private static final boolean DEBUG = com.android.server.notification.NotificationManagerService.DBG;
    private static final int DEFAULT_CURRENT_VERSION = 1;
    private static final int HISTORY_RETENTION_DAYS = 1;
    private static final long INVALID_FILE_TIME_MS = -1;
    private static final java.lang.String TAG = "NotiHistoryDatabase";
    private static final long WRITE_BUFFER_INTERVAL_MS = 1200000;
    private final android.os.Handler mFileWriteHandler;
    private final java.io.File mHistoryDir;
    private final java.io.File mVersionFile;
    private final java.lang.Object mLock = new java.lang.Object();
    private int mCurrentVersion = 1;

    @com.android.internal.annotations.VisibleForTesting
    final java.util.List<android.util.AtomicFile> mHistoryFiles = new java.util.ArrayList();

    @com.android.internal.annotations.VisibleForTesting
    android.app.NotificationHistory mBuffer = new android.app.NotificationHistory();
    private final com.android.server.notification.NotificationHistoryDatabase.WriteBufferRunnable mWriteBufferRunnable = new com.android.server.notification.NotificationHistoryDatabase.WriteBufferRunnable();

    public NotificationHistoryDatabase(android.os.Handler handler, java.io.File file) {
        this.mFileWriteHandler = handler;
        this.mVersionFile = new java.io.File(file, "version");
        this.mHistoryDir = new java.io.File(file, "history");
    }

    public void init() {
        synchronized (this.mLock) {
            try {
                try {
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(TAG, "could not create needed files", e);
                }
                if (!this.mHistoryDir.exists() && !this.mHistoryDir.mkdir()) {
                    throw new java.lang.IllegalStateException("could not create history directory");
                }
                this.mVersionFile.createNewFile();
                checkVersionAndBuildLocked();
                indexFilesLocked();
                prune();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void indexFilesLocked() {
        this.mHistoryFiles.clear();
        java.io.File[] listFiles = this.mHistoryDir.listFiles();
        if (listFiles == null) {
            return;
        }
        java.util.Arrays.sort(listFiles, new java.util.Comparator() { // from class: com.android.server.notification.NotificationHistoryDatabase$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$indexFilesLocked$0;
                lambda$indexFilesLocked$0 = com.android.server.notification.NotificationHistoryDatabase.lambda$indexFilesLocked$0((java.io.File) obj, (java.io.File) obj2);
                return lambda$indexFilesLocked$0;
            }
        });
        for (java.io.File file : listFiles) {
            this.mHistoryFiles.add(new android.util.AtomicFile(file));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$indexFilesLocked$0(java.io.File file, java.io.File file2) {
        return java.lang.Long.compare(safeParseLong(file2.getName()), safeParseLong(file.getName()));
    }

    private void checkVersionAndBuildLocked() {
        int i;
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(this.mVersionFile));
            try {
                i = java.lang.Integer.parseInt(bufferedReader.readLine());
                bufferedReader.close();
            } catch (java.lang.Throwable th) {
                try {
                    bufferedReader.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException | java.lang.NumberFormatException e) {
            i = 0;
        }
        if (i != this.mCurrentVersion && this.mVersionFile.exists()) {
            try {
                java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter(this.mVersionFile));
                try {
                    bufferedWriter.write(java.lang.Integer.toString(this.mCurrentVersion));
                    bufferedWriter.write("\n");
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } finally {
                }
            } catch (java.io.IOException e2) {
                android.util.Slog.e(TAG, "Failed to write new version");
                throw new java.lang.RuntimeException(e2);
            }
        }
    }

    public void forceWriteToDisk() {
        this.mFileWriteHandler.post(this.mWriteBufferRunnable);
    }

    public void onPackageRemoved(java.lang.String str) {
        this.mFileWriteHandler.post(new com.android.server.notification.NotificationHistoryDatabase.RemovePackageRunnable(str));
    }

    public void deleteNotificationHistoryItem(java.lang.String str, long j) {
        this.mFileWriteHandler.post(new com.android.server.notification.NotificationHistoryDatabase.RemoveNotificationRunnable(str, j));
    }

    public void deleteConversations(java.lang.String str, java.util.Set<java.lang.String> set) {
        this.mFileWriteHandler.post(new com.android.server.notification.NotificationHistoryDatabase.RemoveConversationRunnable(str, set));
    }

    public void deleteNotificationChannel(java.lang.String str, java.lang.String str2) {
        this.mFileWriteHandler.post(new com.android.server.notification.NotificationHistoryDatabase.RemoveChannelRunnable(str, str2));
    }

    public void addNotification(android.app.NotificationHistory.HistoricalNotification historicalNotification) {
        synchronized (this.mLock) {
            try {
                this.mBuffer.addNewNotificationToWrite(historicalNotification);
                if (this.mBuffer.getHistoryCount() == 1) {
                    this.mFileWriteHandler.postDelayed(this.mWriteBufferRunnable, WRITE_BUFFER_INTERVAL_MS);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.app.NotificationHistory readNotificationHistory() {
        android.app.NotificationHistory notificationHistory;
        synchronized (this.mLock) {
            notificationHistory = new android.app.NotificationHistory();
            notificationHistory.addNotificationsToWrite(this.mBuffer);
            for (android.util.AtomicFile atomicFile : this.mHistoryFiles) {
                try {
                    readLocked(atomicFile, notificationHistory, new com.android.server.notification.NotificationHistoryFilter.Builder().build());
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(TAG, "error reading " + atomicFile.getBaseFile().getAbsolutePath(), e);
                }
            }
        }
        return notificationHistory;
    }

    public android.app.NotificationHistory readNotificationHistory(java.lang.String str, java.lang.String str2, int i) {
        android.app.NotificationHistory notificationHistory;
        synchronized (this.mLock) {
            notificationHistory = new android.app.NotificationHistory();
            for (android.util.AtomicFile atomicFile : this.mHistoryFiles) {
                try {
                    readLocked(atomicFile, notificationHistory, new com.android.server.notification.NotificationHistoryFilter.Builder().setPackage(str).setChannel(str, str2).setMaxNotifications(i).build());
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(TAG, "error reading " + atomicFile.getBaseFile().getAbsolutePath(), e);
                }
                if (i == notificationHistory.getHistoryCount()) {
                    break;
                }
            }
        }
        return notificationHistory;
    }

    public void disableHistory() {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<android.util.AtomicFile> it = this.mHistoryFiles.iterator();
                while (it.hasNext()) {
                    it.next().delete();
                }
                this.mHistoryDir.delete();
                this.mHistoryFiles.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void prune() {
        prune(1, java.lang.System.currentTimeMillis());
    }

    void prune(int i, long j) {
        synchronized (this.mLock) {
            try {
                java.util.GregorianCalendar gregorianCalendar = new java.util.GregorianCalendar();
                gregorianCalendar.setTimeInMillis(j);
                gregorianCalendar.add(5, i * (-1));
                for (int size = this.mHistoryFiles.size() - 1; size >= 0; size--) {
                    android.util.AtomicFile atomicFile = this.mHistoryFiles.get(size);
                    long safeParseLong = safeParseLong(atomicFile.getBaseFile().getName());
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "File " + atomicFile.getBaseFile().getName() + " created on " + safeParseLong);
                    }
                    if (safeParseLong <= gregorianCalendar.getTimeInMillis()) {
                        deleteFile(atomicFile);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void removeFilePathFromHistory(java.lang.String str) {
        if (str == null) {
            return;
        }
        java.util.Iterator<android.util.AtomicFile> it = this.mHistoryFiles.iterator();
        while (it.hasNext()) {
            android.util.AtomicFile next = it.next();
            if (next != null && str.equals(next.getBaseFile().getAbsolutePath())) {
                it.remove();
                return;
            }
        }
    }

    private void deleteFile(android.util.AtomicFile atomicFile) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Removed " + atomicFile.getBaseFile().getName());
        }
        atomicFile.delete();
        removeFilePathFromHistory(atomicFile.getBaseFile().getAbsolutePath());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeLocked(android.util.AtomicFile atomicFile, android.app.NotificationHistory notificationHistory) throws java.io.IOException {
        java.io.FileOutputStream startWrite = atomicFile.startWrite();
        try {
            com.android.server.notification.NotificationHistoryProtoHelper.write(startWrite, notificationHistory, this.mCurrentVersion);
            atomicFile.finishWrite(startWrite);
            atomicFile.failWrite(null);
        } catch (java.lang.Throwable th) {
            atomicFile.failWrite(startWrite);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void readLocked(android.util.AtomicFile atomicFile, android.app.NotificationHistory notificationHistory, com.android.server.notification.NotificationHistoryFilter notificationHistoryFilter) throws java.io.IOException {
        java.io.FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = atomicFile.openRead();
                com.android.server.notification.NotificationHistoryProtoHelper.read(fileInputStream, notificationHistory, notificationHistoryFilter);
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.e(TAG, "Cannot open " + atomicFile.getBaseFile().getAbsolutePath(), e);
                throw e;
            }
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    private static long safeParseLong(java.lang.String str) {
        try {
            return java.lang.Long.parseLong(str);
        } catch (java.lang.NumberFormatException e) {
            return -1L;
        }
    }

    final class WriteBufferRunnable implements java.lang.Runnable {
        WriteBufferRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            run(new android.util.AtomicFile(new java.io.File(com.android.server.notification.NotificationHistoryDatabase.this.mHistoryDir, java.lang.String.valueOf(java.lang.System.currentTimeMillis()))));
        }

        void run(android.util.AtomicFile atomicFile) {
            synchronized (com.android.server.notification.NotificationHistoryDatabase.this.mLock) {
                if (com.android.server.notification.NotificationHistoryDatabase.DEBUG) {
                    android.util.Slog.d(com.android.server.notification.NotificationHistoryDatabase.TAG, "WriteBufferRunnable " + atomicFile.getBaseFile().getAbsolutePath());
                }
                try {
                    com.android.server.notification.NotificationHistoryDatabase.this.writeLocked(atomicFile, com.android.server.notification.NotificationHistoryDatabase.this.mBuffer);
                    com.android.server.notification.NotificationHistoryDatabase.this.mHistoryFiles.add(0, atomicFile);
                    com.android.server.notification.NotificationHistoryDatabase.this.mBuffer = new android.app.NotificationHistory();
                } catch (java.io.IOException e) {
                    android.util.Slog.e(com.android.server.notification.NotificationHistoryDatabase.TAG, "Failed to write buffer to disk. not flushing buffer", e);
                }
            }
        }
    }

    private final class RemovePackageRunnable implements java.lang.Runnable {
        private java.lang.String mPkg;

        public RemovePackageRunnable(java.lang.String str) {
            this.mPkg = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (com.android.server.notification.NotificationHistoryDatabase.DEBUG) {
                android.util.Slog.d(com.android.server.notification.NotificationHistoryDatabase.TAG, "RemovePackageRunnable " + this.mPkg);
            }
            synchronized (com.android.server.notification.NotificationHistoryDatabase.this.mLock) {
                com.android.server.notification.NotificationHistoryDatabase.this.mBuffer.removeNotificationsFromWrite(this.mPkg);
                for (android.util.AtomicFile atomicFile : com.android.server.notification.NotificationHistoryDatabase.this.mHistoryFiles) {
                    try {
                        android.app.NotificationHistory notificationHistory = new android.app.NotificationHistory();
                        com.android.server.notification.NotificationHistoryDatabase.readLocked(atomicFile, notificationHistory, new com.android.server.notification.NotificationHistoryFilter.Builder().build());
                        notificationHistory.removeNotificationsFromWrite(this.mPkg);
                        com.android.server.notification.NotificationHistoryDatabase.this.writeLocked(atomicFile, notificationHistory);
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(com.android.server.notification.NotificationHistoryDatabase.TAG, "Cannot clean up file on pkg removal " + atomicFile.getBaseFile().getAbsolutePath(), e);
                    }
                }
            }
        }
    }

    final class RemoveNotificationRunnable implements java.lang.Runnable {
        private android.app.NotificationHistory mNotificationHistory;
        private java.lang.String mPkg;
        private long mPostedTime;

        public RemoveNotificationRunnable(java.lang.String str, long j) {
            this.mPkg = str;
            this.mPostedTime = j;
        }

        @com.android.internal.annotations.VisibleForTesting
        void setNotificationHistory(android.app.NotificationHistory notificationHistory) {
            this.mNotificationHistory = notificationHistory;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.app.NotificationHistory notificationHistory;
            if (com.android.server.notification.NotificationHistoryDatabase.DEBUG) {
                android.util.Slog.d(com.android.server.notification.NotificationHistoryDatabase.TAG, "RemoveNotificationRunnable");
            }
            synchronized (com.android.server.notification.NotificationHistoryDatabase.this.mLock) {
                try {
                    com.android.server.notification.NotificationHistoryDatabase.this.mBuffer.removeNotificationFromWrite(this.mPkg, this.mPostedTime);
                    for (android.util.AtomicFile atomicFile : com.android.server.notification.NotificationHistoryDatabase.this.mHistoryFiles) {
                        try {
                            if (this.mNotificationHistory != null) {
                                notificationHistory = this.mNotificationHistory;
                            } else {
                                notificationHistory = new android.app.NotificationHistory();
                            }
                            com.android.server.notification.NotificationHistoryDatabase.readLocked(atomicFile, notificationHistory, new com.android.server.notification.NotificationHistoryFilter.Builder().build());
                            if (notificationHistory.removeNotificationFromWrite(this.mPkg, this.mPostedTime)) {
                                com.android.server.notification.NotificationHistoryDatabase.this.writeLocked(atomicFile, notificationHistory);
                            }
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(com.android.server.notification.NotificationHistoryDatabase.TAG, "Cannot clean up file on notification removal " + atomicFile.getBaseFile().getName(), e);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class RemoveConversationRunnable implements java.lang.Runnable {
        private java.util.Set<java.lang.String> mConversationIds;
        private android.app.NotificationHistory mNotificationHistory;
        private java.lang.String mPkg;

        public RemoveConversationRunnable(java.lang.String str, java.util.Set<java.lang.String> set) {
            this.mPkg = str;
            this.mConversationIds = set;
        }

        @com.android.internal.annotations.VisibleForTesting
        void setNotificationHistory(android.app.NotificationHistory notificationHistory) {
            this.mNotificationHistory = notificationHistory;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.app.NotificationHistory notificationHistory;
            if (com.android.server.notification.NotificationHistoryDatabase.DEBUG) {
                android.util.Slog.d(com.android.server.notification.NotificationHistoryDatabase.TAG, "RemoveConversationRunnable " + this.mPkg + " " + this.mConversationIds);
            }
            synchronized (com.android.server.notification.NotificationHistoryDatabase.this.mLock) {
                try {
                    com.android.server.notification.NotificationHistoryDatabase.this.mBuffer.removeConversationsFromWrite(this.mPkg, this.mConversationIds);
                    for (android.util.AtomicFile atomicFile : com.android.server.notification.NotificationHistoryDatabase.this.mHistoryFiles) {
                        try {
                            if (this.mNotificationHistory != null) {
                                notificationHistory = this.mNotificationHistory;
                            } else {
                                notificationHistory = new android.app.NotificationHistory();
                            }
                            com.android.server.notification.NotificationHistoryDatabase.readLocked(atomicFile, notificationHistory, new com.android.server.notification.NotificationHistoryFilter.Builder().build());
                            if (notificationHistory.removeConversationsFromWrite(this.mPkg, this.mConversationIds)) {
                                com.android.server.notification.NotificationHistoryDatabase.this.writeLocked(atomicFile, notificationHistory);
                            }
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(com.android.server.notification.NotificationHistoryDatabase.TAG, "Cannot clean up file on conversation removal " + atomicFile.getBaseFile().getName(), e);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class RemoveChannelRunnable implements java.lang.Runnable {
        private java.lang.String mChannelId;
        private android.app.NotificationHistory mNotificationHistory;
        private java.lang.String mPkg;

        RemoveChannelRunnable(java.lang.String str, java.lang.String str2) {
            this.mPkg = str;
            this.mChannelId = str2;
        }

        @com.android.internal.annotations.VisibleForTesting
        void setNotificationHistory(android.app.NotificationHistory notificationHistory) {
            this.mNotificationHistory = notificationHistory;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.app.NotificationHistory notificationHistory;
            if (com.android.server.notification.NotificationHistoryDatabase.DEBUG) {
                android.util.Slog.d(com.android.server.notification.NotificationHistoryDatabase.TAG, "RemoveChannelRunnable");
            }
            synchronized (com.android.server.notification.NotificationHistoryDatabase.this.mLock) {
                try {
                    com.android.server.notification.NotificationHistoryDatabase.this.mBuffer.removeChannelFromWrite(this.mPkg, this.mChannelId);
                    for (android.util.AtomicFile atomicFile : com.android.server.notification.NotificationHistoryDatabase.this.mHistoryFiles) {
                        try {
                            if (this.mNotificationHistory != null) {
                                notificationHistory = this.mNotificationHistory;
                            } else {
                                notificationHistory = new android.app.NotificationHistory();
                            }
                            com.android.server.notification.NotificationHistoryDatabase.readLocked(atomicFile, notificationHistory, new com.android.server.notification.NotificationHistoryFilter.Builder().build());
                            if (notificationHistory.removeChannelFromWrite(this.mPkg, this.mChannelId)) {
                                com.android.server.notification.NotificationHistoryDatabase.this.writeLocked(atomicFile, notificationHistory);
                            }
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(com.android.server.notification.NotificationHistoryDatabase.TAG, "Cannot clean up file on channel removal " + atomicFile.getBaseFile().getName(), e);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
