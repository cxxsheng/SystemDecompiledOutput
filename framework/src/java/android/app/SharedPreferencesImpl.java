package android.app;

/* loaded from: classes.dex */
final class SharedPreferencesImpl implements android.content.SharedPreferences {
    private static final long CALLBACK_ON_CLEAR_CHANGE = 119147584;
    private static final boolean DEBUG = false;
    private static final long MAX_FSYNC_DURATION_MILLIS = 256;
    private static final java.lang.String TAG = "SharedPreferencesImpl";
    private final java.io.File mBackupFile;
    private long mCurrentMemoryStateGeneration;
    private long mDiskStateGeneration;
    private final java.io.File mFile;
    private boolean mLoaded;
    private final int mMode;
    private long mStatSize;
    private android.system.StructTimespec mStatTimestamp;
    private static final java.lang.Object CONTENT = new java.lang.Object();
    private static final java.util.concurrent.ThreadPoolExecutor sLoadExecutor = new java.util.concurrent.ThreadPoolExecutor(0, 1, 10, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.LinkedBlockingQueue(), new android.app.SharedPreferencesImpl.SharedPreferencesThreadFactory());
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.lang.Object mWritingToDiskLock = new java.lang.Object();
    private int mDiskWritesInFlight = 0;
    private final java.util.WeakHashMap<android.content.SharedPreferences.OnSharedPreferenceChangeListener, java.lang.Object> mListeners = new java.util.WeakHashMap<>();
    private final com.android.internal.util.ExponentiallyBucketedHistogram mSyncTimes = new com.android.internal.util.ExponentiallyBucketedHistogram(16);
    private int mNumSync = 0;
    private java.util.Map<java.lang.String, java.lang.Object> mMap = null;
    private java.lang.Throwable mThrowable = null;

    SharedPreferencesImpl(java.io.File file, int i) {
        this.mLoaded = false;
        this.mFile = file;
        this.mBackupFile = makeBackupFile(file);
        this.mMode = i;
        this.mLoaded = false;
        startLoadFromDisk();
    }

    private void startLoadFromDisk() {
        synchronized (this.mLock) {
            this.mLoaded = false;
        }
        sLoadExecutor.execute(new java.lang.Runnable() { // from class: android.app.SharedPreferencesImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.app.SharedPreferencesImpl.this.lambda$startLoadFromDisk$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00c2  */
    /* JADX WARN: Type inference failed for: r2v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* renamed from: loadFromDisk, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void lambda$startLoadFromDisk$0() {
        java.util.HashMap<java.lang.String, ?> hashMap;
        android.system.StructStat structStat;
        java.lang.Object obj;
        ?? canRead;
        java.io.BufferedInputStream bufferedInputStream;
        synchronized (this.mLock) {
            if (this.mLoaded) {
                return;
            }
            if (this.mBackupFile.exists()) {
                this.mFile.delete();
                this.mBackupFile.renameTo(this.mFile);
            }
            if (this.mFile.exists() && !this.mFile.canRead()) {
                android.util.Log.w(TAG, "Attempt to read preferences file " + this.mFile + " without permission");
            }
            java.lang.Throwable th = null;
            try {
                structStat = android.system.Os.stat(this.mFile.getPath());
                try {
                    canRead = this.mFile.canRead();
                } catch (android.system.ErrnoException e) {
                    hashMap = null;
                } catch (java.lang.Throwable th2) {
                    hashMap = null;
                    th = th2;
                }
            } catch (android.system.ErrnoException e2) {
                structStat = null;
                hashMap = null;
            } catch (java.lang.Throwable th3) {
                hashMap = null;
                th = th3;
                structStat = null;
            }
            try {
                if (canRead != 0) {
                    try {
                        bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(this.mFile), 16384);
                    } catch (java.lang.Exception e3) {
                        e = e3;
                        bufferedInputStream = null;
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                        canRead = 0;
                        libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) canRead);
                        throw th;
                    }
                    try {
                        hashMap = com.android.internal.util.XmlUtils.readMapXml(bufferedInputStream);
                        try {
                            libcore.io.IoUtils.closeQuietly(bufferedInputStream);
                        } catch (android.system.ErrnoException e4) {
                        } catch (java.lang.Throwable th5) {
                            th = th5;
                        }
                    } catch (java.lang.Exception e5) {
                        e = e5;
                        android.util.Log.w(TAG, "Cannot read " + this.mFile.getAbsolutePath(), e);
                        libcore.io.IoUtils.closeQuietly(bufferedInputStream);
                        hashMap = null;
                        synchronized (this.mLock) {
                        }
                    }
                    synchronized (this.mLock) {
                        this.mLoaded = true;
                        this.mThrowable = th;
                        if (th == null) {
                            try {
                                if (hashMap != null) {
                                    this.mMap = hashMap;
                                    this.mStatTimestamp = structStat.st_mtim;
                                    this.mStatSize = structStat.st_size;
                                } else {
                                    this.mMap = new java.util.HashMap();
                                }
                            } catch (java.lang.Throwable th6) {
                                try {
                                    this.mThrowable = th6;
                                    obj = this.mLock;
                                } catch (java.lang.Throwable th7) {
                                    this.mLock.notifyAll();
                                    throw th7;
                                }
                            }
                        }
                        obj = this.mLock;
                        obj.notifyAll();
                    }
                    return;
                }
                hashMap = null;
                synchronized (this.mLock) {
                }
            } catch (java.lang.Throwable th8) {
                th = th8;
            }
        }
    }

    static java.io.File makeBackupFile(java.io.File file) {
        return new java.io.File(file.getPath() + ".bak");
    }

    void startReloadIfChangedUnexpectedly() {
        synchronized (this.mLock) {
            if (hasFileChangedUnexpectedly()) {
                startLoadFromDisk();
            }
        }
    }

    private boolean hasFileChangedUnexpectedly() {
        boolean z;
        synchronized (this.mLock) {
            if (this.mDiskWritesInFlight > 0) {
                return false;
            }
            try {
                dalvik.system.BlockGuard.getThreadPolicy().onReadFromDisk();
                android.system.StructStat stat = android.system.Os.stat(this.mFile.getPath());
                synchronized (this.mLock) {
                    z = (stat.st_mtim.equals(this.mStatTimestamp) && this.mStatSize == stat.st_size) ? false : true;
                }
                return z;
            } catch (android.system.ErrnoException e) {
                return true;
            }
        }
    }

    @Override // android.content.SharedPreferences
    public void registerOnSharedPreferenceChangeListener(android.content.SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        synchronized (this.mLock) {
            this.mListeners.put(onSharedPreferenceChangeListener, CONTENT);
        }
    }

    @Override // android.content.SharedPreferences
    public void unregisterOnSharedPreferenceChangeListener(android.content.SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        synchronized (this.mLock) {
            this.mListeners.remove(onSharedPreferenceChangeListener);
        }
    }

    private void awaitLoadedLocked() {
        if (!this.mLoaded) {
            dalvik.system.BlockGuard.getThreadPolicy().onReadFromDisk();
        }
        while (!this.mLoaded) {
            try {
                this.mLock.wait();
            } catch (java.lang.InterruptedException e) {
            }
        }
        if (this.mThrowable != null) {
            throw new java.lang.IllegalStateException(this.mThrowable);
        }
    }

    @Override // android.content.SharedPreferences
    public java.util.Map<java.lang.String, ?> getAll() {
        java.util.HashMap hashMap;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            hashMap = new java.util.HashMap(this.mMap);
        }
        return hashMap;
    }

    @Override // android.content.SharedPreferences
    public java.lang.String getString(java.lang.String str, java.lang.String str2) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            java.lang.String str3 = (java.lang.String) this.mMap.get(str);
            if (str3 != null) {
                str2 = str3;
            }
        }
        return str2;
    }

    @Override // android.content.SharedPreferences
    public java.util.Set<java.lang.String> getStringSet(java.lang.String str, java.util.Set<java.lang.String> set) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            java.util.Set<java.lang.String> set2 = (java.util.Set) this.mMap.get(str);
            if (set2 != null) {
                set = set2;
            }
        }
        return set;
    }

    @Override // android.content.SharedPreferences
    public int getInt(java.lang.String str, int i) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            java.lang.Integer num = (java.lang.Integer) this.mMap.get(str);
            if (num != null) {
                i = num.intValue();
            }
        }
        return i;
    }

    @Override // android.content.SharedPreferences
    public long getLong(java.lang.String str, long j) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            java.lang.Long l = (java.lang.Long) this.mMap.get(str);
            if (l != null) {
                j = l.longValue();
            }
        }
        return j;
    }

    @Override // android.content.SharedPreferences
    public float getFloat(java.lang.String str, float f) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            java.lang.Float f2 = (java.lang.Float) this.mMap.get(str);
            if (f2 != null) {
                f = f2.floatValue();
            }
        }
        return f;
    }

    @Override // android.content.SharedPreferences
    public boolean getBoolean(java.lang.String str, boolean z) {
        synchronized (this.mLock) {
            awaitLoadedLocked();
            java.lang.Boolean bool = (java.lang.Boolean) this.mMap.get(str);
            if (bool != null) {
                z = bool.booleanValue();
            }
        }
        return z;
    }

    @Override // android.content.SharedPreferences
    public boolean contains(java.lang.String str) {
        boolean containsKey;
        synchronized (this.mLock) {
            awaitLoadedLocked();
            containsKey = this.mMap.containsKey(str);
        }
        return containsKey;
    }

    @Override // android.content.SharedPreferences
    public android.content.SharedPreferences.Editor edit() {
        synchronized (this.mLock) {
            awaitLoadedLocked();
        }
        return new android.app.SharedPreferencesImpl.EditorImpl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MemoryCommitResult {
        final boolean keysCleared;
        final java.util.List<java.lang.String> keysModified;
        final java.util.Set<android.content.SharedPreferences.OnSharedPreferenceChangeListener> listeners;
        final java.util.Map<java.lang.String, java.lang.Object> mapToWriteToDisk;
        final long memoryStateGeneration;
        boolean wasWritten;
        volatile boolean writeToDiskResult;
        final java.util.concurrent.CountDownLatch writtenToDiskLatch;

        private MemoryCommitResult(long j, boolean z, java.util.List<java.lang.String> list, java.util.Set<android.content.SharedPreferences.OnSharedPreferenceChangeListener> set, java.util.Map<java.lang.String, java.lang.Object> map) {
            this.writtenToDiskLatch = new java.util.concurrent.CountDownLatch(1);
            this.writeToDiskResult = false;
            this.wasWritten = false;
            this.memoryStateGeneration = j;
            this.keysCleared = z;
            this.keysModified = list;
            this.listeners = set;
            this.mapToWriteToDisk = map;
        }

        void setDiskWriteResult(boolean z, boolean z2) {
            this.wasWritten = z;
            this.writeToDiskResult = z2;
            this.writtenToDiskLatch.countDown();
        }
    }

    public final class EditorImpl implements android.content.SharedPreferences.Editor {
        private final java.lang.Object mEditorLock = new java.lang.Object();
        private final java.util.Map<java.lang.String, java.lang.Object> mModified = new java.util.HashMap();
        private boolean mClear = false;

        public EditorImpl() {
        }

        @Override // android.content.SharedPreferences.Editor
        public android.content.SharedPreferences.Editor putString(java.lang.String str, java.lang.String str2) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, str2);
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public android.content.SharedPreferences.Editor putStringSet(java.lang.String str, java.util.Set<java.lang.String> set) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, set == null ? null : new java.util.HashSet(set));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public android.content.SharedPreferences.Editor putInt(java.lang.String str, int i) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, java.lang.Integer.valueOf(i));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public android.content.SharedPreferences.Editor putLong(java.lang.String str, long j) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, java.lang.Long.valueOf(j));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public android.content.SharedPreferences.Editor putFloat(java.lang.String str, float f) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, java.lang.Float.valueOf(f));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public android.content.SharedPreferences.Editor putBoolean(java.lang.String str, boolean z) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, java.lang.Boolean.valueOf(z));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public android.content.SharedPreferences.Editor remove(java.lang.String str) {
            synchronized (this.mEditorLock) {
                this.mModified.put(str, this);
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public android.content.SharedPreferences.Editor clear() {
            synchronized (this.mEditorLock) {
                this.mClear = true;
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public void apply() {
            final long currentTimeMillis = java.lang.System.currentTimeMillis();
            final android.app.SharedPreferencesImpl.MemoryCommitResult commitToMemory = commitToMemory();
            final java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.app.SharedPreferencesImpl.EditorImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        commitToMemory.writtenToDiskLatch.await();
                    } catch (java.lang.InterruptedException e) {
                    }
                }
            };
            android.app.QueuedWork.addFinisher(runnable);
            android.app.SharedPreferencesImpl.this.enqueueDiskWrite(commitToMemory, new java.lang.Runnable() { // from class: android.app.SharedPreferencesImpl.EditorImpl.2
                @Override // java.lang.Runnable
                public void run() {
                    runnable.run();
                    android.app.QueuedWork.removeFinisher(runnable);
                }
            });
            lambda$notifyListeners$0(commitToMemory);
        }

        /* JADX WARN: Removed duplicated region for block: B:38:0x00c4 A[Catch: all -> 0x00ee, TryCatch #1 {, blocks: (B:15:0x0063, B:17:0x0067, B:19:0x006e, B:20:0x0074, B:21:0x007b, B:22:0x0085, B:24:0x008b, B:42:0x00a0, B:44:0x00a6, B:46:0x00ac, B:49:0x00b3, B:38:0x00c4, B:29:0x00b7, B:36:0x00be, B:57:0x00c9, B:59:0x00d0, B:60:0x00dc, B:61:0x00e2), top: B:14:0x0063, outer: #0 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private android.app.SharedPreferencesImpl.MemoryCommitResult commitToMemory() {
            java.util.Map map;
            java.util.ArrayList arrayList;
            java.util.HashSet hashSet;
            boolean z;
            long j;
            java.lang.Object obj;
            boolean z2;
            synchronized (android.app.SharedPreferencesImpl.this.mLock) {
                if (android.app.SharedPreferencesImpl.this.mDiskWritesInFlight > 0) {
                    android.app.SharedPreferencesImpl.this.mMap = new java.util.HashMap(android.app.SharedPreferencesImpl.this.mMap);
                }
                map = android.app.SharedPreferencesImpl.this.mMap;
                android.app.SharedPreferencesImpl.this.mDiskWritesInFlight++;
                boolean z3 = false;
                boolean z4 = android.app.SharedPreferencesImpl.this.mListeners.size() > 0;
                if (!z4) {
                    arrayList = null;
                    hashSet = null;
                } else {
                    arrayList = new java.util.ArrayList();
                    hashSet = new java.util.HashSet(android.app.SharedPreferencesImpl.this.mListeners.keySet());
                }
                synchronized (this.mEditorLock) {
                    if (!this.mClear) {
                        z = false;
                    } else {
                        if (map.isEmpty()) {
                            z2 = false;
                        } else {
                            map.clear();
                            z2 = true;
                        }
                        this.mClear = false;
                        z3 = z2;
                        z = true;
                    }
                    for (java.util.Map.Entry<java.lang.String, java.lang.Object> entry : this.mModified.entrySet()) {
                        java.lang.String key = entry.getKey();
                        java.lang.Object value = entry.getValue();
                        if (value != this && value != null) {
                            if (!map.containsKey(key) || (obj = map.get(key)) == null || !obj.equals(value)) {
                                map.put(key, value);
                                if (z4) {
                                    arrayList.add(key);
                                }
                                z3 = true;
                            }
                        }
                        if (map.containsKey(key)) {
                            map.remove(key);
                            if (z4) {
                            }
                            z3 = true;
                        }
                    }
                    this.mModified.clear();
                    if (z3) {
                        android.app.SharedPreferencesImpl.this.mCurrentMemoryStateGeneration++;
                    }
                    j = android.app.SharedPreferencesImpl.this.mCurrentMemoryStateGeneration;
                }
            }
            return new android.app.SharedPreferencesImpl.MemoryCommitResult(j, z, arrayList, hashSet, map);
        }

        @Override // android.content.SharedPreferences.Editor
        public boolean commit() {
            android.app.SharedPreferencesImpl.MemoryCommitResult commitToMemory = commitToMemory();
            android.app.SharedPreferencesImpl.this.enqueueDiskWrite(commitToMemory, null);
            try {
                commitToMemory.writtenToDiskLatch.await();
                lambda$notifyListeners$0(commitToMemory);
                return commitToMemory.writeToDiskResult;
            } catch (java.lang.InterruptedException e) {
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: notifyListeners, reason: merged with bridge method [inline-methods] */
        public void lambda$notifyListeners$0(final android.app.SharedPreferencesImpl.MemoryCommitResult memoryCommitResult) {
            if (memoryCommitResult.listeners != null) {
                if (memoryCommitResult.keysModified == null && !memoryCommitResult.keysCleared) {
                    return;
                }
                if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) {
                    if (memoryCommitResult.keysCleared && android.compat.Compatibility.isChangeEnabled(android.app.SharedPreferencesImpl.CALLBACK_ON_CLEAR_CHANGE)) {
                        for (android.content.SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener : memoryCommitResult.listeners) {
                            if (onSharedPreferenceChangeListener != null) {
                                onSharedPreferenceChangeListener.onSharedPreferenceChanged(android.app.SharedPreferencesImpl.this, null);
                            }
                        }
                    }
                    for (int size = memoryCommitResult.keysModified.size() - 1; size >= 0; size--) {
                        java.lang.String str = memoryCommitResult.keysModified.get(size);
                        for (android.content.SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener2 : memoryCommitResult.listeners) {
                            if (onSharedPreferenceChangeListener2 != null) {
                                onSharedPreferenceChangeListener2.onSharedPreferenceChanged(android.app.SharedPreferencesImpl.this, str);
                            }
                        }
                    }
                    return;
                }
                android.app.ActivityThread.sMainThreadHandler.post(new java.lang.Runnable() { // from class: android.app.SharedPreferencesImpl$EditorImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.SharedPreferencesImpl.EditorImpl.this.lambda$notifyListeners$0(memoryCommitResult);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enqueueDiskWrite(final android.app.SharedPreferencesImpl.MemoryCommitResult memoryCommitResult, final java.lang.Runnable runnable) {
        boolean z;
        final boolean z2 = runnable == null;
        java.lang.Runnable runnable2 = new java.lang.Runnable() { // from class: android.app.SharedPreferencesImpl.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (android.app.SharedPreferencesImpl.this.mWritingToDiskLock) {
                    android.app.SharedPreferencesImpl.this.writeToFile(memoryCommitResult, z2);
                }
                synchronized (android.app.SharedPreferencesImpl.this.mLock) {
                    android.app.SharedPreferencesImpl sharedPreferencesImpl = android.app.SharedPreferencesImpl.this;
                    sharedPreferencesImpl.mDiskWritesInFlight--;
                }
                if (runnable != null) {
                    runnable.run();
                }
            }
        };
        if (z2) {
            synchronized (this.mLock) {
                z = this.mDiskWritesInFlight == 1;
            }
            if (z) {
                runnable2.run();
                return;
            }
        }
        android.app.QueuedWork.queue(runnable2, !z2);
    }

    private static java.io.FileOutputStream createFileOutputStream(java.io.File file) {
        try {
            return new java.io.FileOutputStream(file);
        } catch (java.io.FileNotFoundException e) {
            java.io.File parentFile = file.getParentFile();
            if (!parentFile.mkdir()) {
                android.util.Log.e(TAG, "Couldn't create directory for SharedPreferences file " + file);
                return null;
            }
            android.os.FileUtils.setPermissions(parentFile.getPath(), 505, -1, -1);
            try {
                return new java.io.FileOutputStream(file);
            } catch (java.io.FileNotFoundException e2) {
                android.util.Log.e(TAG, "Couldn't create SharedPreferences file " + file, e2);
                return null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeToFile(android.app.SharedPreferencesImpl.MemoryCommitResult memoryCommitResult, boolean z) {
        boolean z2;
        if (this.mFile.exists()) {
            if (this.mDiskStateGeneration >= memoryCommitResult.memoryStateGeneration) {
                z2 = false;
            } else if (z) {
                z2 = true;
            } else {
                synchronized (this.mLock) {
                    if (this.mCurrentMemoryStateGeneration != memoryCommitResult.memoryStateGeneration) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                }
            }
            if (!z2) {
                memoryCommitResult.setDiskWriteResult(false, true);
                return;
            } else if (!this.mBackupFile.exists()) {
                if (!this.mFile.renameTo(this.mBackupFile)) {
                    android.util.Log.e(TAG, "Couldn't rename file " + this.mFile + " to backup file " + this.mBackupFile);
                    memoryCommitResult.setDiskWriteResult(false, false);
                    return;
                }
            } else {
                this.mFile.delete();
            }
        }
        try {
            java.io.FileOutputStream createFileOutputStream = createFileOutputStream(this.mFile);
            if (createFileOutputStream == null) {
                memoryCommitResult.setDiskWriteResult(false, false);
                return;
            }
            com.android.internal.util.XmlUtils.writeMapXml(memoryCommitResult.mapToWriteToDisk, createFileOutputStream);
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            android.os.FileUtils.sync(createFileOutputStream);
            long currentTimeMillis2 = java.lang.System.currentTimeMillis();
            createFileOutputStream.close();
            android.app.ContextImpl.setFilePermissionsFromMode(this.mFile.getPath(), this.mMode, 0);
            try {
                android.system.StructStat stat = android.system.Os.stat(this.mFile.getPath());
                synchronized (this.mLock) {
                    this.mStatTimestamp = stat.st_mtim;
                    this.mStatSize = stat.st_size;
                }
            } catch (android.system.ErrnoException e) {
            }
            this.mBackupFile.delete();
            this.mDiskStateGeneration = memoryCommitResult.memoryStateGeneration;
            memoryCommitResult.setDiskWriteResult(true, true);
            long j = currentTimeMillis2 - currentTimeMillis;
            this.mSyncTimes.add((int) j);
            this.mNumSync++;
            if (this.mNumSync % 1024 == 0 || j > 256) {
                this.mSyncTimes.log(TAG, "Time required to fsync " + this.mFile + ": ");
            }
        } catch (java.io.IOException e2) {
            android.util.Log.w(TAG, "writeToFile: Got exception:", e2);
            if (this.mFile.exists() && !this.mFile.delete()) {
                android.util.Log.e(TAG, "Couldn't clean up partially-written file " + this.mFile);
            }
            memoryCommitResult.setDiskWriteResult(false, false);
        } catch (org.xmlpull.v1.XmlPullParserException e3) {
            android.util.Log.w(TAG, "writeToFile: Got exception:", e3);
            if (this.mFile.exists()) {
                android.util.Log.e(TAG, "Couldn't clean up partially-written file " + this.mFile);
            }
            memoryCommitResult.setDiskWriteResult(false, false);
        }
    }

    private static final class SharedPreferencesThreadFactory implements java.util.concurrent.ThreadFactory {
        private SharedPreferencesThreadFactory() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public java.lang.Thread newThread(java.lang.Runnable runnable) {
            java.lang.Thread newThread = java.util.concurrent.Executors.defaultThreadFactory().newThread(runnable);
            newThread.setName("SharedPreferences");
            return newThread;
        }
    }
}
