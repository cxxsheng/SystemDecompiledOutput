package android.mtp;

/* loaded from: classes2.dex */
public class MtpStorageManager {
    private static final int IN_IGNORED = 32768;
    private static final int IN_ISDIR = 1073741824;
    private static final int IN_ONLYDIR = 16777216;
    private static final int IN_Q_OVERFLOW = 16384;
    private static final java.lang.String TAG = android.mtp.MtpStorageManager.class.getSimpleName();
    public static boolean sDebug = false;
    private android.mtp.MtpStorageManager.MtpNotifier mMtpNotifier;
    private java.util.Set<java.lang.String> mSubdirectories;
    private java.util.HashMap<java.lang.Integer, android.mtp.MtpStorageManager.MtpObject> mObjects = new java.util.HashMap<>();
    private java.util.HashMap<java.lang.Integer, android.mtp.MtpStorageManager.MtpObject> mRoots = new java.util.HashMap<>();
    private int mNextObjectId = 1;
    private int mNextStorageId = 1;
    private volatile boolean mCheckConsistency = false;
    private java.lang.Thread mConsistencyThread = new java.lang.Thread(new java.lang.Runnable() { // from class: android.mtp.MtpStorageManager$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            android.mtp.MtpStorageManager.this.lambda$new$0();
        }
    });

    public static abstract class MtpNotifier {
        public abstract void sendObjectAdded(int i);

        public abstract void sendObjectInfoChanged(int i);

        public abstract void sendObjectRemoved(int i);
    }

    private enum MtpObjectState {
        NORMAL,
        FROZEN,
        FROZEN_ADDED,
        FROZEN_REMOVED,
        FROZEN_ONESHOT_ADD,
        FROZEN_ONESHOT_DEL
    }

    private enum MtpOperation {
        NONE,
        ADD,
        RENAME,
        COPY,
        DELETE
    }

    private class MtpObjectObserver extends android.os.FileObserver {
        android.mtp.MtpStorageManager.MtpObject mObject;

        MtpObjectObserver(android.mtp.MtpStorageManager.MtpObject mtpObject) {
            super(mtpObject.getPath().toString(), 16778184);
            this.mObject = mtpObject;
        }

        /* JADX WARN: Code restructure failed: missing block: B:35:0x00d6, code lost:
        
            android.util.Log.w(android.mtp.MtpStorageManager.TAG, "Object was null in event " + r7);
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x00f1, code lost:
        
            return;
         */
        @Override // android.os.FileObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onEvent(int i, java.lang.String str) {
            synchronized (android.mtp.MtpStorageManager.this) {
                if ((i & 16384) != 0) {
                    android.util.Log.e(android.mtp.MtpStorageManager.TAG, "Received Inotify overflow event!");
                }
                android.mtp.MtpStorageManager.MtpObject child = this.mObject.getChild(str);
                if ((i & 128) == 0 && (i & 256) == 0) {
                    if ((i & 64) == 0 && (i & 512) == 0) {
                        if ((32768 & i) != 0) {
                            if (android.mtp.MtpStorageManager.sDebug) {
                                android.util.Log.i(android.mtp.MtpStorageManager.TAG, "inotify for " + this.mObject.getPath() + " deleted");
                            }
                            if (this.mObject.mObserver != null) {
                                this.mObject.mObserver.stopWatching();
                            }
                            this.mObject.mObserver = null;
                        } else if ((i & 8) != 0) {
                            if (android.mtp.MtpStorageManager.sDebug) {
                                android.util.Log.i(android.mtp.MtpStorageManager.TAG, "inotify for " + this.mObject.getPath() + " CLOSE_WRITE: " + str);
                            }
                            android.mtp.MtpStorageManager.this.handleChangedObject(this.mObject, str);
                        } else {
                            android.util.Log.w(android.mtp.MtpStorageManager.TAG, "Got unrecognized event " + str + " " + i);
                        }
                    }
                    if (android.mtp.MtpStorageManager.sDebug) {
                        android.util.Log.i(android.mtp.MtpStorageManager.TAG, "Got inotify removed event for " + str + " " + i);
                    }
                    android.mtp.MtpStorageManager.this.handleRemovedObject(child);
                }
                if (android.mtp.MtpStorageManager.sDebug) {
                    android.util.Log.i(android.mtp.MtpStorageManager.TAG, "Got inotify added event for " + str + " " + i);
                }
                android.mtp.MtpStorageManager.this.handleAddedObject(this.mObject, str, (i & 1073741824) != 0);
            }
        }

        @Override // android.os.FileObserver
        public void finalize() {
        }
    }

    public static class MtpObject {
        private java.util.HashMap<java.lang.String, android.mtp.MtpStorageManager.MtpObject> mChildren;
        private int mId;
        private boolean mIsDir;
        private java.lang.String mName;
        private android.mtp.MtpStorageManager.MtpObject mParent;
        private android.mtp.MtpStorage mStorage;
        private android.os.FileObserver mObserver = null;
        private boolean mVisited = false;
        private android.mtp.MtpStorageManager.MtpObjectState mState = android.mtp.MtpStorageManager.MtpObjectState.NORMAL;
        private android.mtp.MtpStorageManager.MtpOperation mOp = android.mtp.MtpStorageManager.MtpOperation.NONE;

        MtpObject(java.lang.String str, int i, android.mtp.MtpStorage mtpStorage, android.mtp.MtpStorageManager.MtpObject mtpObject, boolean z) {
            this.mId = i;
            this.mName = str;
            this.mStorage = (android.mtp.MtpStorage) com.android.internal.util.Preconditions.checkNotNull(mtpStorage);
            this.mParent = mtpObject;
            this.mIsDir = z;
            this.mChildren = this.mIsDir ? new java.util.HashMap<>() : null;
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public int getId() {
            return this.mId;
        }

        public boolean isDir() {
            return this.mIsDir;
        }

        public int getFormat() {
            if (this.mIsDir) {
                return 12289;
            }
            return android.media.MediaFile.getFormatCode(this.mName, null);
        }

        public int getStorageId() {
            return getRoot().getId();
        }

        public long getModifiedTime() {
            return getPath().toFile().lastModified() / 1000;
        }

        public android.mtp.MtpStorageManager.MtpObject getParent() {
            return this.mParent;
        }

        public android.mtp.MtpStorageManager.MtpObject getRoot() {
            return isRoot() ? this : this.mParent.getRoot();
        }

        public long getSize() {
            if (this.mIsDir) {
                return 0L;
            }
            return maybeApplyTranscodeLengthWorkaround(getPath().toFile().length());
        }

        private long maybeApplyTranscodeLengthWorkaround(long j) {
            if (this.mStorage.isHostWindows() && isTranscodeMtpEnabled() && isFileTranscodeSupported()) {
                return j * 2;
            }
            return j;
        }

        private boolean isTranscodeMtpEnabled() {
            return android.os.SystemProperties.getBoolean("sys.fuse.transcode_mtp", false);
        }

        private boolean isFileTranscodeSupported() {
            try {
                return android.system.Os.stat(getPath().toString()).st_nlink > 1;
            } catch (android.system.ErrnoException e) {
                android.util.Log.w(android.mtp.MtpStorageManager.TAG, "Failed to stat path: " + getPath() + ". Ignoring transcoding.");
                return false;
            }
        }

        public java.nio.file.Path getPath() {
            return isRoot() ? java.nio.file.Paths.get(this.mName, new java.lang.String[0]) : this.mParent.getPath().resolve(this.mName);
        }

        public boolean isRoot() {
            return this.mParent == null;
        }

        public java.lang.String getVolumeName() {
            return this.mStorage.getVolumeName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setName(java.lang.String str) {
            this.mName = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setId(int i) {
            this.mId = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isVisited() {
            return this.mVisited;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setParent(android.mtp.MtpStorageManager.MtpObject mtpObject) {
            if (getStorageId() != mtpObject.getStorageId()) {
                this.mStorage = (android.mtp.MtpStorage) com.android.internal.util.Preconditions.checkNotNull(mtpObject.getStorage());
            }
            this.mParent = mtpObject;
        }

        private android.mtp.MtpStorage getStorage() {
            return this.mStorage;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDir(boolean z) {
            if (z != this.mIsDir) {
                this.mIsDir = z;
                this.mChildren = this.mIsDir ? new java.util.HashMap<>() : null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setVisited(boolean z) {
            this.mVisited = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.mtp.MtpStorageManager.MtpObjectState getState() {
            return this.mState;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setState(android.mtp.MtpStorageManager.MtpObjectState mtpObjectState) {
            this.mState = mtpObjectState;
            if (this.mState == android.mtp.MtpStorageManager.MtpObjectState.NORMAL) {
                this.mOp = android.mtp.MtpStorageManager.MtpOperation.NONE;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.mtp.MtpStorageManager.MtpOperation getOperation() {
            return this.mOp;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOperation(android.mtp.MtpStorageManager.MtpOperation mtpOperation) {
            this.mOp = mtpOperation;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.os.FileObserver getObserver() {
            return this.mObserver;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setObserver(android.os.FileObserver fileObserver) {
            this.mObserver = fileObserver;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addChild(android.mtp.MtpStorageManager.MtpObject mtpObject) {
            this.mChildren.put(mtpObject.getName(), mtpObject);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.mtp.MtpStorageManager.MtpObject getChild(java.lang.String str) {
            return this.mChildren.get(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.util.Collection<android.mtp.MtpStorageManager.MtpObject> getChildren() {
            return this.mChildren.values();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean exists() {
            return getPath().toFile().exists();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.mtp.MtpStorageManager.MtpObject copy(boolean z) {
            android.mtp.MtpStorageManager.MtpObject mtpObject = new android.mtp.MtpStorageManager.MtpObject(this.mName, this.mId, this.mStorage, this.mParent, this.mIsDir);
            mtpObject.mIsDir = this.mIsDir;
            mtpObject.mVisited = this.mVisited;
            mtpObject.mState = this.mState;
            mtpObject.mChildren = this.mIsDir ? new java.util.HashMap<>() : null;
            if (z && this.mIsDir) {
                java.util.Iterator<android.mtp.MtpStorageManager.MtpObject> it = this.mChildren.values().iterator();
                while (it.hasNext()) {
                    android.mtp.MtpStorageManager.MtpObject copy = it.next().copy(true);
                    copy.setParent(mtpObject);
                    mtpObject.addChild(copy);
                }
            }
            return mtpObject;
        }
    }

    public MtpStorageManager(android.mtp.MtpStorageManager.MtpNotifier mtpNotifier, java.util.Set<java.lang.String> set) {
        this.mMtpNotifier = mtpNotifier;
        this.mSubdirectories = set;
        if (this.mCheckConsistency) {
            this.mConsistencyThread.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        while (this.mCheckConsistency) {
            try {
                java.lang.Thread.sleep(15000L);
                if (checkConsistency()) {
                    android.util.Log.v(TAG, "Cache is consistent");
                } else {
                    android.util.Log.w(TAG, "Cache is not consistent");
                }
            } catch (java.lang.InterruptedException e) {
                return;
            }
        }
    }

    public synchronized void close() {
        for (android.mtp.MtpStorageManager.MtpObject mtpObject : this.mObjects.values()) {
            if (mtpObject.getObserver() != null) {
                mtpObject.getObserver().stopWatching();
                mtpObject.setObserver(null);
            }
        }
        for (android.mtp.MtpStorageManager.MtpObject mtpObject2 : this.mRoots.values()) {
            if (mtpObject2.getObserver() != null) {
                mtpObject2.getObserver().stopWatching();
                mtpObject2.setObserver(null);
            }
        }
        if (this.mCheckConsistency) {
            this.mCheckConsistency = false;
            this.mConsistencyThread.interrupt();
            try {
                this.mConsistencyThread.join();
            } catch (java.lang.InterruptedException e) {
            }
        }
    }

    public synchronized void setSubdirectories(java.util.Set<java.lang.String> set) {
        this.mSubdirectories = set;
    }

    public synchronized android.mtp.MtpStorage addMtpStorage(android.os.storage.StorageVolume storageVolume, java.util.function.Supplier<java.lang.Boolean> supplier) {
        android.mtp.MtpStorage mtpStorage;
        int nextStorageId = ((getNextStorageId() & 65535) << 16) + 1;
        mtpStorage = new android.mtp.MtpStorage(storageVolume, nextStorageId, supplier);
        this.mRoots.put(java.lang.Integer.valueOf(nextStorageId), new android.mtp.MtpStorageManager.MtpObject(mtpStorage.getPath(), nextStorageId, mtpStorage, null, true));
        return mtpStorage;
    }

    public synchronized void removeMtpStorage(android.mtp.MtpStorage mtpStorage) {
        removeObjectFromCache(getStorageRoot(mtpStorage.getStorageId()), true, true);
    }

    private synchronized boolean isSpecialSubDir(android.mtp.MtpStorageManager.MtpObject mtpObject) {
        boolean z;
        if (mtpObject.getParent().isRoot() && this.mSubdirectories != null) {
            z = this.mSubdirectories.contains(mtpObject.getName()) ? false : true;
        }
        return z;
    }

    public synchronized android.mtp.MtpStorageManager.MtpObject getByPath(java.lang.String str) {
        android.mtp.MtpStorageManager.MtpObject mtpObject = null;
        for (android.mtp.MtpStorageManager.MtpObject mtpObject2 : this.mRoots.values()) {
            if (str.startsWith(mtpObject2.getName())) {
                str = str.substring(mtpObject2.getName().length());
                mtpObject = mtpObject2;
            }
        }
        for (java.lang.String str2 : str.split("/")) {
            if (mtpObject != null && mtpObject.isDir()) {
                if (!"".equals(str2)) {
                    if (!mtpObject.isVisited()) {
                        getChildren(mtpObject);
                    }
                    mtpObject = mtpObject.getChild(str2);
                }
            }
            return null;
        }
        return mtpObject;
    }

    public synchronized android.mtp.MtpStorageManager.MtpObject getObject(int i) {
        if (i == 0 || i == -1) {
            android.util.Log.w(TAG, "Can't get root storages with getObject()");
            return null;
        }
        if (!this.mObjects.containsKey(java.lang.Integer.valueOf(i))) {
            android.util.Log.w(TAG, "Id " + i + " doesn't exist");
            return null;
        }
        return this.mObjects.get(java.lang.Integer.valueOf(i));
    }

    public android.mtp.MtpStorageManager.MtpObject getStorageRoot(int i) {
        if (!this.mRoots.containsKey(java.lang.Integer.valueOf(i))) {
            android.util.Log.w(TAG, "StorageId " + i + " doesn't exist");
            return null;
        }
        return this.mRoots.get(java.lang.Integer.valueOf(i));
    }

    private int getNextObjectId() {
        int i = this.mNextObjectId;
        this.mNextObjectId = (int) (this.mNextObjectId + 1);
        return i;
    }

    private int getNextStorageId() {
        int i = this.mNextStorageId;
        this.mNextStorageId = i + 1;
        return i;
    }

    public synchronized java.util.List<android.mtp.MtpStorageManager.MtpObject> getObjects(int i, int i2, int i3) {
        boolean z = true;
        boolean z2 = i == 0;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (i == -1) {
            i = 0;
        }
        if (i3 == -1 && i == 0) {
            java.util.Iterator<android.mtp.MtpStorageManager.MtpObject> it = this.mRoots.values().iterator();
            while (it.hasNext()) {
                z &= getObjects(arrayList, it.next(), i2, z2);
            }
            if (!z) {
                arrayList = null;
            }
            return arrayList;
        }
        android.mtp.MtpStorageManager.MtpObject storageRoot = i == 0 ? getStorageRoot(i3) : getObject(i);
        if (storageRoot == null) {
            return null;
        }
        if (!getObjects(arrayList, storageRoot, i2, z2)) {
            arrayList = null;
        }
        return arrayList;
    }

    private synchronized boolean getObjects(java.util.List<android.mtp.MtpStorageManager.MtpObject> list, android.mtp.MtpStorageManager.MtpObject mtpObject, int i, boolean z) {
        java.util.Collection<android.mtp.MtpStorageManager.MtpObject> children = getChildren(mtpObject);
        if (children == null) {
            return false;
        }
        for (android.mtp.MtpStorageManager.MtpObject mtpObject2 : children) {
            if (i == 0 || mtpObject2.getFormat() == i) {
                list.add(mtpObject2);
            }
        }
        boolean z2 = true;
        if (z) {
            boolean z3 = true;
            for (android.mtp.MtpStorageManager.MtpObject mtpObject3 : children) {
                if (mtpObject3.isDir()) {
                    z3 &= getObjects(list, mtpObject3, i, true);
                }
            }
            z2 = z3;
        }
        return z2;
    }

    private synchronized java.util.Collection<android.mtp.MtpStorageManager.MtpObject> getChildren(android.mtp.MtpStorageManager.MtpObject mtpObject) {
        if (mtpObject != null) {
            if (mtpObject.isDir()) {
                if (!mtpObject.isVisited()) {
                    java.nio.file.Path path = mtpObject.getPath();
                    if (mtpObject.getObserver() != null) {
                        android.util.Log.e(TAG, "Observer is not null!");
                    }
                    mtpObject.setObserver(new android.mtp.MtpStorageManager.MtpObjectObserver(mtpObject));
                    mtpObject.getObserver().startWatching();
                    try {
                        java.nio.file.DirectoryStream<java.nio.file.Path> newDirectoryStream = java.nio.file.Files.newDirectoryStream(path);
                        try {
                            for (java.nio.file.Path path2 : newDirectoryStream) {
                                addObjectToCache(mtpObject, path2.getFileName().toString(), path2.toFile().isDirectory());
                            }
                            if (newDirectoryStream != null) {
                                newDirectoryStream.close();
                            }
                            mtpObject.setVisited(true);
                        } catch (java.lang.Throwable th) {
                            if (newDirectoryStream != null) {
                                try {
                                    newDirectoryStream.close();
                                } catch (java.lang.Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (java.io.IOException | java.nio.file.DirectoryIteratorException e) {
                        android.util.Log.e(TAG, e.toString());
                        mtpObject.getObserver().stopWatching();
                        mtpObject.setObserver(null);
                        return null;
                    }
                }
                return mtpObject.getChildren();
            }
        }
        android.util.Log.w(TAG, "Can't find children of " + (mtpObject == null ? "null" : java.lang.Integer.valueOf(mtpObject.getId())));
        return null;
    }

    private synchronized android.mtp.MtpStorageManager.MtpObject addObjectToCache(android.mtp.MtpStorageManager.MtpObject mtpObject, java.lang.String str, boolean z) {
        if (!mtpObject.isRoot() && getObject(mtpObject.getId()) != mtpObject) {
            return null;
        }
        if (mtpObject.getChild(str) != null) {
            return null;
        }
        if (this.mSubdirectories != null && mtpObject.isRoot() && !this.mSubdirectories.contains(str)) {
            return null;
        }
        android.mtp.MtpStorageManager.MtpObject mtpObject2 = new android.mtp.MtpStorageManager.MtpObject(str, getNextObjectId(), mtpObject.mStorage, mtpObject, z);
        this.mObjects.put(java.lang.Integer.valueOf(mtpObject2.getId()), mtpObject2);
        mtpObject.addChild(mtpObject2);
        return mtpObject2;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0047 A[Catch: all -> 0x00d6, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:12:0x0025, B:13:0x0041, B:15:0x0047, B:20:0x0076, B:22:0x007a, B:23:0x0096, B:25:0x009c, B:26:0x00a7, B:29:0x00af, B:30:0x00bc, B:32:0x00c2, B:50:0x005f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x009c A[Catch: all -> 0x00d6, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:12:0x0025, B:13:0x0041, B:15:0x0047, B:20:0x0076, B:22:0x007a, B:23:0x0096, B:25:0x009c, B:26:0x00a7, B:29:0x00af, B:30:0x00bc, B:32:0x00c2, B:50:0x005f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c2 A[Catch: all -> 0x00d6, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:10:0x0021, B:12:0x0025, B:13:0x0041, B:15:0x0047, B:20:0x0076, B:22:0x007a, B:23:0x0096, B:25:0x009c, B:26:0x00a7, B:29:0x00af, B:30:0x00bc, B:32:0x00c2, B:50:0x005f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized boolean removeObjectFromCache(android.mtp.MtpStorageManager.MtpObject mtpObject, boolean z, boolean z2) {
        boolean z3;
        java.util.Iterator it;
        if (!mtpObject.isRoot() && !mtpObject.getParent().mChildren.remove(mtpObject.getName(), mtpObject)) {
            z3 = false;
            if (!z3 && sDebug) {
                android.util.Log.w(TAG, "Failed to remove from parent " + mtpObject.getPath());
            }
            if (!mtpObject.isRoot()) {
                z3 = this.mRoots.remove(java.lang.Integer.valueOf(mtpObject.getId()), mtpObject) && z3;
            } else if (z) {
                z3 = this.mObjects.remove(java.lang.Integer.valueOf(mtpObject.getId()), mtpObject) && z3;
            }
            if (!z3 && sDebug) {
                android.util.Log.w(TAG, "Failed to remove from global cache " + mtpObject.getPath());
            }
            if (mtpObject.getObserver() != null) {
                mtpObject.getObserver().stopWatching();
                mtpObject.setObserver(null);
            }
            if (mtpObject.isDir() && z2) {
                it = new java.util.ArrayList(mtpObject.getChildren()).iterator();
                while (it.hasNext()) {
                    z3 = removeObjectFromCache((android.mtp.MtpStorageManager.MtpObject) it.next(), z, true) && z3;
                }
            }
        }
        z3 = true;
        if (!z3) {
            android.util.Log.w(TAG, "Failed to remove from parent " + mtpObject.getPath());
        }
        if (!mtpObject.isRoot()) {
        }
        if (!z3) {
            android.util.Log.w(TAG, "Failed to remove from global cache " + mtpObject.getPath());
        }
        if (mtpObject.getObserver() != null) {
        }
        if (mtpObject.isDir()) {
            it = new java.util.ArrayList(mtpObject.getChildren()).iterator();
            while (it.hasNext()) {
            }
        }
        return z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleAddedObject(android.mtp.MtpStorageManager.MtpObject mtpObject, java.lang.String str, boolean z) {
        android.mtp.MtpStorageManager.MtpOperation mtpOperation = android.mtp.MtpStorageManager.MtpOperation.NONE;
        android.mtp.MtpStorageManager.MtpObject child = mtpObject.getChild(str);
        if (child != null) {
            android.mtp.MtpStorageManager.MtpObjectState state = child.getState();
            mtpOperation = child.getOperation();
            if (child.isDir() != z && state != android.mtp.MtpStorageManager.MtpObjectState.FROZEN_REMOVED) {
                android.util.Log.d(TAG, "Inconsistent directory info! " + child.getPath());
            }
            child.setDir(z);
            switch (state) {
                case FROZEN:
                case FROZEN_REMOVED:
                    child.setState(android.mtp.MtpStorageManager.MtpObjectState.FROZEN_ADDED);
                    break;
                case FROZEN_ONESHOT_ADD:
                    child.setState(android.mtp.MtpStorageManager.MtpObjectState.NORMAL);
                    break;
                case NORMAL:
                case FROZEN_ADDED:
                    return;
                default:
                    android.util.Log.w(TAG, "Unexpected state in add " + str + " " + state);
                    break;
            }
            if (sDebug) {
                android.util.Log.i(TAG, state + " transitioned to " + child.getState() + " in op " + mtpOperation);
            }
        } else {
            child = addObjectToCache(mtpObject, str, z);
            if (child != null) {
                this.mMtpNotifier.sendObjectAdded(child.getId());
            } else {
                if (sDebug) {
                    android.util.Log.w(TAG, "object " + str + " already exists");
                }
                return;
            }
        }
        if (z) {
            if (mtpOperation == android.mtp.MtpStorageManager.MtpOperation.RENAME) {
                return;
            }
            if (mtpOperation == android.mtp.MtpStorageManager.MtpOperation.COPY && !child.isVisited()) {
                return;
            }
            if (child.getObserver() != null) {
                android.util.Log.e(TAG, "Observer is not null!");
                return;
            }
            child.setObserver(new android.mtp.MtpStorageManager.MtpObjectObserver(child));
            child.getObserver().startWatching();
            child.setVisited(true);
            try {
                java.nio.file.DirectoryStream<java.nio.file.Path> newDirectoryStream = java.nio.file.Files.newDirectoryStream(child.getPath());
                try {
                    for (java.nio.file.Path path : newDirectoryStream) {
                        if (sDebug) {
                            android.util.Log.i(TAG, "Manually handling event for " + path.getFileName().toString());
                        }
                        handleAddedObject(child, path.getFileName().toString(), path.toFile().isDirectory());
                    }
                    if (newDirectoryStream != null) {
                        newDirectoryStream.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (newDirectoryStream != null) {
                        try {
                            newDirectoryStream.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException | java.nio.file.DirectoryIteratorException e) {
                android.util.Log.e(TAG, e.toString());
                child.getObserver().stopWatching();
                child.setObserver(null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleRemovedObject(android.mtp.MtpStorageManager.MtpObject mtpObject) {
        android.mtp.MtpStorageManager.MtpObjectState state = mtpObject.getState();
        android.mtp.MtpStorageManager.MtpOperation operation = mtpObject.getOperation();
        boolean z = true;
        switch (state) {
            case FROZEN:
                mtpObject.setState(android.mtp.MtpStorageManager.MtpObjectState.FROZEN_REMOVED);
                break;
            case FROZEN_REMOVED:
            case FROZEN_ONESHOT_ADD:
            default:
                android.util.Log.e(TAG, "Got unexpected object remove for " + mtpObject.getName());
                break;
            case NORMAL:
                if (removeObjectFromCache(mtpObject, true, true)) {
                    this.mMtpNotifier.sendObjectRemoved(mtpObject.getId());
                    break;
                }
                break;
            case FROZEN_ADDED:
                mtpObject.setState(android.mtp.MtpStorageManager.MtpObjectState.FROZEN_REMOVED);
                break;
            case FROZEN_ONESHOT_DEL:
                if (operation == android.mtp.MtpStorageManager.MtpOperation.RENAME) {
                    z = false;
                }
                removeObjectFromCache(mtpObject, z, false);
                break;
        }
        if (sDebug) {
            android.util.Log.i(TAG, state + " transitioned to " + mtpObject.getState() + " in op " + operation);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleChangedObject(android.mtp.MtpStorageManager.MtpObject mtpObject, java.lang.String str) {
        android.mtp.MtpStorageManager.MtpOperation mtpOperation = android.mtp.MtpStorageManager.MtpOperation.NONE;
        android.mtp.MtpStorageManager.MtpObject child = mtpObject.getChild(str);
        if (child != null) {
            if (!child.isDir() && child.getSize() > 0) {
                child.getState();
                child.getOperation();
                this.mMtpNotifier.sendObjectInfoChanged(child.getId());
                if (sDebug) {
                    android.util.Log.d(TAG, "sendObjectInfoChanged: id=" + child.getId() + ",size=" + child.getSize());
                }
            }
        } else if (sDebug) {
            android.util.Log.w(TAG, "object " + str + " null");
        }
    }

    public void flushEvents() {
        try {
            java.lang.Thread.sleep(500L);
        } catch (java.lang.InterruptedException e) {
        }
    }

    public synchronized void dump() {
        java.util.Iterator<java.lang.Integer> it = this.mObjects.keySet().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            android.mtp.MtpStorageManager.MtpObject mtpObject = this.mObjects.get(java.lang.Integer.valueOf(intValue));
            android.util.Log.i(TAG, intValue + " | " + (mtpObject.getParent() == null ? java.lang.Integer.valueOf(mtpObject.getParent().getId()) : "null") + " | " + mtpObject.getName() + " | " + (mtpObject.isDir() ? "dir" : "obj") + " | " + (mtpObject.isVisited() ? "v" : "nv") + " | " + mtpObject.getState());
        }
    }

    public synchronized boolean checkConsistency() {
        boolean z;
        java.util.ArrayList<android.mtp.MtpStorageManager.MtpObject> arrayList = new java.util.ArrayList();
        arrayList.addAll(this.mRoots.values());
        arrayList.addAll(this.mObjects.values());
        z = true;
        for (android.mtp.MtpStorageManager.MtpObject mtpObject : arrayList) {
            if (!mtpObject.exists()) {
                android.util.Log.w(TAG, "Object doesn't exist " + mtpObject.getPath() + " " + mtpObject.getId());
                z = false;
            }
            if (mtpObject.getState() != android.mtp.MtpStorageManager.MtpObjectState.NORMAL) {
                android.util.Log.w(TAG, "Object " + mtpObject.getPath() + " in state " + mtpObject.getState());
                z = false;
            }
            if (mtpObject.getOperation() != android.mtp.MtpStorageManager.MtpOperation.NONE) {
                android.util.Log.w(TAG, "Object " + mtpObject.getPath() + " in operation " + mtpObject.getOperation());
                z = false;
            }
            if (!mtpObject.isRoot() && this.mObjects.get(java.lang.Integer.valueOf(mtpObject.getId())) != mtpObject) {
                android.util.Log.w(TAG, "Object " + mtpObject.getPath() + " is not in map correctly");
                z = false;
            }
            if (mtpObject.getParent() != null) {
                if (mtpObject.getParent().isRoot() && mtpObject.getParent() != this.mRoots.get(java.lang.Integer.valueOf(mtpObject.getParent().getId()))) {
                    android.util.Log.w(TAG, "Root parent is not in root mapping " + mtpObject.getPath());
                    z = false;
                }
                if (!mtpObject.getParent().isRoot() && mtpObject.getParent() != this.mObjects.get(java.lang.Integer.valueOf(mtpObject.getParent().getId()))) {
                    android.util.Log.w(TAG, "Parent is not in object mapping " + mtpObject.getPath());
                    z = false;
                }
                if (mtpObject.getParent().getChild(mtpObject.getName()) != mtpObject) {
                    android.util.Log.w(TAG, "Child does not exist in parent " + mtpObject.getPath());
                    z = false;
                }
            }
            if (mtpObject.isDir()) {
                if (mtpObject.isVisited() == (mtpObject.getObserver() == null)) {
                    android.util.Log.w(TAG, mtpObject.getPath() + " is " + (mtpObject.isVisited() ? "" : "not ") + " visited but observer is " + mtpObject.getObserver());
                    z = false;
                }
                if (!mtpObject.isVisited() && mtpObject.getChildren().size() > 0) {
                    android.util.Log.w(TAG, mtpObject.getPath() + " is not visited but has children");
                    z = false;
                }
                try {
                    java.nio.file.DirectoryStream<java.nio.file.Path> newDirectoryStream = java.nio.file.Files.newDirectoryStream(mtpObject.getPath());
                    try {
                        java.util.HashSet hashSet = new java.util.HashSet();
                        for (java.nio.file.Path path : newDirectoryStream) {
                            if (mtpObject.isVisited() && mtpObject.getChild(path.getFileName().toString()) == null && (this.mSubdirectories == null || !mtpObject.isRoot() || this.mSubdirectories.contains(path.getFileName().toString()))) {
                                android.util.Log.w(TAG, "File exists in fs but not in children " + path);
                                z = false;
                            }
                            hashSet.add(path.toString());
                        }
                        for (android.mtp.MtpStorageManager.MtpObject mtpObject2 : mtpObject.getChildren()) {
                            if (!hashSet.contains(mtpObject2.getPath().toString())) {
                                android.util.Log.w(TAG, "File in children doesn't exist in fs " + mtpObject2.getPath());
                                z = false;
                            }
                            if (mtpObject2 != this.mObjects.get(java.lang.Integer.valueOf(mtpObject2.getId()))) {
                                android.util.Log.w(TAG, "Child is not in object map " + mtpObject2.getPath());
                                z = false;
                            }
                        }
                        if (newDirectoryStream != null) {
                            newDirectoryStream.close();
                        }
                    } catch (java.lang.Throwable th) {
                        if (newDirectoryStream != null) {
                            try {
                                newDirectoryStream.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (java.io.IOException | java.nio.file.DirectoryIteratorException e) {
                    android.util.Log.w(TAG, e.toString());
                    z = false;
                }
            }
        }
        return z;
    }

    public synchronized int beginSendObject(android.mtp.MtpStorageManager.MtpObject mtpObject, java.lang.String str, int i) {
        if (sDebug) {
            android.util.Log.v(TAG, "beginSendObject " + str);
        }
        if (!mtpObject.isDir()) {
            return -1;
        }
        if (mtpObject.isRoot() && this.mSubdirectories != null && !this.mSubdirectories.contains(str)) {
            return -1;
        }
        getChildren(mtpObject);
        android.mtp.MtpStorageManager.MtpObject addObjectToCache = addObjectToCache(mtpObject, str, i == 12289);
        if (addObjectToCache == null) {
            return -1;
        }
        addObjectToCache.setState(android.mtp.MtpStorageManager.MtpObjectState.FROZEN);
        addObjectToCache.setOperation(android.mtp.MtpStorageManager.MtpOperation.ADD);
        return addObjectToCache.getId();
    }

    public synchronized boolean endSendObject(android.mtp.MtpStorageManager.MtpObject mtpObject, boolean z) {
        if (sDebug) {
            android.util.Log.v(TAG, "endSendObject " + z);
        }
        return generalEndAddObject(mtpObject, z, true);
    }

    public synchronized boolean beginRenameObject(android.mtp.MtpStorageManager.MtpObject mtpObject, java.lang.String str) {
        if (sDebug) {
            android.util.Log.v(TAG, "beginRenameObject " + mtpObject.getName() + " " + str);
        }
        if (mtpObject.isRoot()) {
            return false;
        }
        if (isSpecialSubDir(mtpObject)) {
            return false;
        }
        if (mtpObject.getParent().getChild(str) != null) {
            return false;
        }
        android.mtp.MtpStorageManager.MtpObject copy = mtpObject.copy(false);
        mtpObject.setName(str);
        mtpObject.getParent().addChild(mtpObject);
        copy.getParent().addChild(copy);
        return generalBeginRenameObject(copy, mtpObject);
    }

    public synchronized boolean endRenameObject(android.mtp.MtpStorageManager.MtpObject mtpObject, java.lang.String str, boolean z) {
        android.mtp.MtpStorageManager.MtpObject child;
        if (sDebug) {
            android.util.Log.v(TAG, "endRenameObject " + z);
        }
        android.mtp.MtpStorageManager.MtpObject parent = mtpObject.getParent();
        child = parent.getChild(str);
        if (z) {
            child = mtpObject;
            mtpObject = child;
        } else {
            android.mtp.MtpStorageManager.MtpObjectState state = child.getState();
            child.setName(mtpObject.getName());
            child.setState(mtpObject.getState());
            mtpObject.setName(str);
            mtpObject.setState(state);
            parent.addChild(child);
            parent.addChild(mtpObject);
        }
        return generalEndRenameObject(mtpObject, child, z);
    }

    public synchronized boolean beginRemoveObject(android.mtp.MtpStorageManager.MtpObject mtpObject) {
        boolean z;
        if (sDebug) {
            android.util.Log.v(TAG, "beginRemoveObject " + mtpObject.getName());
        }
        if (!mtpObject.isRoot() && !isSpecialSubDir(mtpObject)) {
            z = generalBeginRemoveObject(mtpObject, android.mtp.MtpStorageManager.MtpOperation.DELETE);
        }
        return z;
    }

    public synchronized boolean endRemoveObject(android.mtp.MtpStorageManager.MtpObject mtpObject, boolean z) {
        boolean z2;
        boolean z3;
        if (sDebug) {
            android.util.Log.v(TAG, "endRemoveObject " + z);
        }
        z2 = false;
        if (!mtpObject.isDir()) {
            z3 = true;
        } else {
            java.util.Iterator it = new java.util.ArrayList(mtpObject.getChildren()).iterator();
            z3 = true;
            while (it.hasNext()) {
                android.mtp.MtpStorageManager.MtpObject mtpObject2 = (android.mtp.MtpStorageManager.MtpObject) it.next();
                if (mtpObject2.getOperation() == android.mtp.MtpStorageManager.MtpOperation.DELETE) {
                    z3 = endRemoveObject(mtpObject2, z) && z3;
                }
            }
        }
        if (generalEndRemoveObject(mtpObject, z, true) && z3) {
            z2 = true;
        }
        return z2;
    }

    public synchronized boolean beginMoveObject(android.mtp.MtpStorageManager.MtpObject mtpObject, android.mtp.MtpStorageManager.MtpObject mtpObject2) {
        if (sDebug) {
            android.util.Log.v(TAG, "beginMoveObject " + mtpObject2.getPath());
        }
        boolean z = false;
        if (mtpObject.isRoot()) {
            return false;
        }
        if (isSpecialSubDir(mtpObject)) {
            return false;
        }
        getChildren(mtpObject2);
        if (mtpObject2.getChild(mtpObject.getName()) != null) {
            return false;
        }
        if (mtpObject.getStorageId() != mtpObject2.getStorageId()) {
            android.mtp.MtpStorageManager.MtpObject copy = mtpObject.copy(true);
            copy.setParent(mtpObject2);
            mtpObject2.addChild(copy);
            if (generalBeginRemoveObject(mtpObject, android.mtp.MtpStorageManager.MtpOperation.RENAME) && generalBeginCopyObject(copy, false)) {
                z = true;
            }
            return z;
        }
        android.mtp.MtpStorageManager.MtpObject copy2 = mtpObject.copy(false);
        mtpObject.setParent(mtpObject2);
        copy2.getParent().addChild(copy2);
        mtpObject.getParent().addChild(mtpObject);
        return generalBeginRenameObject(copy2, mtpObject);
    }

    public synchronized boolean endMoveObject(android.mtp.MtpStorageManager.MtpObject mtpObject, android.mtp.MtpStorageManager.MtpObject mtpObject2, java.lang.String str, boolean z) {
        if (sDebug) {
            android.util.Log.v(TAG, "endMoveObject " + z);
        }
        android.mtp.MtpStorageManager.MtpObject child = mtpObject.getChild(str);
        android.mtp.MtpStorageManager.MtpObject child2 = mtpObject2.getChild(str);
        boolean z2 = false;
        if (child != null && child2 != null) {
            if (mtpObject.getStorageId() != child2.getStorageId()) {
                boolean endRemoveObject = endRemoveObject(child, z);
                if (generalEndCopyObject(child2, z, true) && endRemoveObject) {
                    z2 = true;
                }
                return z2;
            }
            if (!z) {
                android.mtp.MtpStorageManager.MtpObjectState state = child.getState();
                child.setParent(child2.getParent());
                child.setState(child2.getState());
                child2.setParent(mtpObject);
                child2.setState(state);
                child.getParent().addChild(child);
                mtpObject.addChild(child2);
                child = child2;
                child2 = child;
            }
            return generalEndRenameObject(child, child2, z);
        }
        return false;
    }

    public synchronized int beginCopyObject(android.mtp.MtpStorageManager.MtpObject mtpObject, android.mtp.MtpStorageManager.MtpObject mtpObject2) {
        if (sDebug) {
            android.util.Log.v(TAG, "beginCopyObject " + mtpObject.getName() + " to " + mtpObject2.getPath());
        }
        java.lang.String name = mtpObject.getName();
        if (!mtpObject2.isDir()) {
            return -1;
        }
        if (mtpObject2.isRoot() && this.mSubdirectories != null && !this.mSubdirectories.contains(name)) {
            return -1;
        }
        getChildren(mtpObject2);
        if (mtpObject2.getChild(name) != null) {
            return -1;
        }
        android.mtp.MtpStorageManager.MtpObject copy = mtpObject.copy(mtpObject.isDir());
        mtpObject2.addChild(copy);
        copy.setParent(mtpObject2);
        if (!generalBeginCopyObject(copy, true)) {
            return -1;
        }
        return copy.getId();
    }

    public synchronized boolean endCopyObject(android.mtp.MtpStorageManager.MtpObject mtpObject, boolean z) {
        if (sDebug) {
            android.util.Log.v(TAG, "endCopyObject " + mtpObject.getName() + " " + z);
        }
        return generalEndCopyObject(mtpObject, z, false);
    }

    private synchronized boolean generalEndAddObject(android.mtp.MtpStorageManager.MtpObject mtpObject, boolean z, boolean z2) {
        switch (mtpObject.getState()) {
            case FROZEN:
                if (z) {
                    mtpObject.setState(android.mtp.MtpStorageManager.MtpObjectState.FROZEN_ONESHOT_ADD);
                    break;
                } else if (!removeObjectFromCache(mtpObject, z2, false)) {
                    return false;
                }
                break;
            case FROZEN_REMOVED:
                if (!removeObjectFromCache(mtpObject, z2, false)) {
                    return false;
                }
                if (z) {
                    this.mMtpNotifier.sendObjectRemoved(mtpObject.getId());
                    break;
                }
                break;
            case FROZEN_ONESHOT_ADD:
            case NORMAL:
            default:
                return false;
            case FROZEN_ADDED:
                mtpObject.setState(android.mtp.MtpStorageManager.MtpObjectState.NORMAL);
                if (!z) {
                    android.mtp.MtpStorageManager.MtpObject parent = mtpObject.getParent();
                    if (!removeObjectFromCache(mtpObject, z2, false)) {
                        return false;
                    }
                    handleAddedObject(parent, mtpObject.getName(), mtpObject.isDir());
                    break;
                }
                break;
        }
        return true;
    }

    private synchronized boolean generalEndRemoveObject(android.mtp.MtpStorageManager.MtpObject mtpObject, boolean z, boolean z2) {
        switch (mtpObject.getState()) {
            case FROZEN:
                if (z) {
                    mtpObject.setState(android.mtp.MtpStorageManager.MtpObjectState.FROZEN_ONESHOT_DEL);
                    break;
                } else {
                    mtpObject.setState(android.mtp.MtpStorageManager.MtpObjectState.NORMAL);
                    break;
                }
            case FROZEN_REMOVED:
                if (!removeObjectFromCache(mtpObject, z2, false)) {
                    return false;
                }
                if (!z) {
                    this.mMtpNotifier.sendObjectRemoved(mtpObject.getId());
                    break;
                }
                break;
            case FROZEN_ONESHOT_ADD:
            case NORMAL:
            default:
                return false;
            case FROZEN_ADDED:
                mtpObject.setState(android.mtp.MtpStorageManager.MtpObjectState.NORMAL);
                if (z) {
                    android.mtp.MtpStorageManager.MtpObject parent = mtpObject.getParent();
                    if (!removeObjectFromCache(mtpObject, z2, false)) {
                        return false;
                    }
                    handleAddedObject(parent, mtpObject.getName(), mtpObject.isDir());
                    break;
                }
                break;
        }
        return true;
    }

    private synchronized boolean generalBeginRenameObject(android.mtp.MtpStorageManager.MtpObject mtpObject, android.mtp.MtpStorageManager.MtpObject mtpObject2) {
        mtpObject.setState(android.mtp.MtpStorageManager.MtpObjectState.FROZEN);
        mtpObject2.setState(android.mtp.MtpStorageManager.MtpObjectState.FROZEN);
        mtpObject.setOperation(android.mtp.MtpStorageManager.MtpOperation.RENAME);
        mtpObject2.setOperation(android.mtp.MtpStorageManager.MtpOperation.RENAME);
        return true;
    }

    private synchronized boolean generalEndRenameObject(android.mtp.MtpStorageManager.MtpObject mtpObject, android.mtp.MtpStorageManager.MtpObject mtpObject2, boolean z) {
        return generalEndAddObject(mtpObject2, z, z) && generalEndRemoveObject(mtpObject, z, !z);
    }

    private synchronized boolean generalBeginRemoveObject(android.mtp.MtpStorageManager.MtpObject mtpObject, android.mtp.MtpStorageManager.MtpOperation mtpOperation) {
        mtpObject.setState(android.mtp.MtpStorageManager.MtpObjectState.FROZEN);
        mtpObject.setOperation(mtpOperation);
        if (mtpObject.isDir()) {
            java.util.Iterator it = mtpObject.getChildren().iterator();
            while (it.hasNext()) {
                generalBeginRemoveObject((android.mtp.MtpStorageManager.MtpObject) it.next(), mtpOperation);
            }
        }
        return true;
    }

    private synchronized boolean generalBeginCopyObject(android.mtp.MtpStorageManager.MtpObject mtpObject, boolean z) {
        mtpObject.setState(android.mtp.MtpStorageManager.MtpObjectState.FROZEN);
        mtpObject.setOperation(android.mtp.MtpStorageManager.MtpOperation.COPY);
        if (z) {
            mtpObject.setId(getNextObjectId());
            this.mObjects.put(java.lang.Integer.valueOf(mtpObject.getId()), mtpObject);
        }
        if (mtpObject.isDir()) {
            java.util.Iterator it = mtpObject.getChildren().iterator();
            while (it.hasNext()) {
                if (!generalBeginCopyObject((android.mtp.MtpStorageManager.MtpObject) it.next(), z)) {
                    return false;
                }
            }
        }
        return true;
    }

    private synchronized boolean generalEndCopyObject(android.mtp.MtpStorageManager.MtpObject mtpObject, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        boolean z5;
        if (z && z2) {
            this.mObjects.put(java.lang.Integer.valueOf(mtpObject.getId()), mtpObject);
        }
        z3 = false;
        if (!mtpObject.isDir()) {
            z4 = true;
        } else {
            java.util.Iterator it = new java.util.ArrayList(mtpObject.getChildren()).iterator();
            z4 = true;
            while (it.hasNext()) {
                android.mtp.MtpStorageManager.MtpObject mtpObject2 = (android.mtp.MtpStorageManager.MtpObject) it.next();
                if (mtpObject2.getOperation() == android.mtp.MtpStorageManager.MtpOperation.COPY) {
                    z4 = generalEndCopyObject(mtpObject2, z, z2) && z4;
                }
            }
        }
        if (!z && z2) {
            z5 = false;
            if (generalEndAddObject(mtpObject, z, z5) && z4) {
                z3 = true;
            }
        }
        z5 = true;
        if (generalEndAddObject(mtpObject, z, z5)) {
            z3 = true;
        }
        return z3;
    }
}
