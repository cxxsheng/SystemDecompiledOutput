package android.os;

/* loaded from: classes3.dex */
public abstract class FileObserver {
    public static final int ACCESS = 1;
    public static final int ALL_EVENTS = 4095;
    public static final int ATTRIB = 4;
    public static final int CLOSE_NOWRITE = 16;
    public static final int CLOSE_WRITE = 8;
    public static final int CREATE = 256;
    public static final int DELETE = 512;
    public static final int DELETE_SELF = 1024;
    private static final java.lang.String LOG_TAG = "FileObserver";
    public static final int MODIFY = 2;
    public static final int MOVED_FROM = 64;
    public static final int MOVED_TO = 128;
    public static final int MOVE_SELF = 2048;
    public static final int OPEN = 32;
    private static android.os.FileObserver.ObserverThread s_observerThread = new android.os.FileObserver.ObserverThread();
    private int[] mDescriptors;
    private final java.util.List<java.io.File> mFiles;
    private final int mMask;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NotifyEventType {
    }

    public abstract void onEvent(int i, java.lang.String str);

    private static class ObserverThread extends java.lang.Thread {
        private android.util.SparseArray<java.lang.ref.WeakReference> mRealObservers;
        private int m_fd;
        private java.util.HashMap<java.lang.Integer, java.lang.ref.WeakReference> m_observers;

        private native int init();

        private native void observe(int i);

        private native void startWatching(int i, java.lang.String[] strArr, int i2, int[] iArr);

        private native void stopWatching(int i, int[] iArr);

        public ObserverThread() {
            super(android.os.FileObserver.LOG_TAG);
            this.m_observers = new java.util.HashMap<>();
            this.mRealObservers = new android.util.SparseArray<>();
            this.m_fd = init();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            observe(this.m_fd);
        }

        public int[] startWatching(java.util.List<java.io.File> list, int i, android.os.FileObserver fileObserver) {
            int size = list.size();
            java.lang.String[] strArr = new java.lang.String[size];
            for (int i2 = 0; i2 < size; i2++) {
                strArr[i2] = list.get(i2).getAbsolutePath();
            }
            int[] iArr = new int[size];
            java.util.Arrays.fill(iArr, -1);
            startWatching(this.m_fd, strArr, i, iArr);
            java.lang.ref.WeakReference weakReference = new java.lang.ref.WeakReference(fileObserver);
            synchronized (this.mRealObservers) {
                for (int i3 = 0; i3 < size; i3++) {
                    int i4 = iArr[i3];
                    if (i4 >= 0) {
                        this.mRealObservers.put(i4, weakReference);
                    }
                }
            }
            return iArr;
        }

        public void stopWatching(int[] iArr) {
            stopWatching(this.m_fd, iArr);
        }

        public void onEvent(int i, int i2, java.lang.String str) {
            android.os.FileObserver fileObserver;
            synchronized (this.mRealObservers) {
                java.lang.ref.WeakReference weakReference = this.mRealObservers.get(i);
                if (weakReference == null) {
                    fileObserver = null;
                } else {
                    fileObserver = (android.os.FileObserver) weakReference.get();
                    if (fileObserver == null) {
                        this.mRealObservers.remove(i);
                    }
                }
            }
            if (fileObserver != null) {
                try {
                    fileObserver.onEvent(i2, str);
                } catch (java.lang.Throwable th) {
                    android.util.Log.wtf(android.os.FileObserver.LOG_TAG, "Unhandled exception in FileObserver " + fileObserver, th);
                }
            }
        }
    }

    static {
        s_observerThread.start();
    }

    @java.lang.Deprecated
    public FileObserver(java.lang.String str) {
        this(new java.io.File(str));
    }

    public FileObserver(java.io.File file) {
        this((java.util.List<java.io.File>) java.util.Arrays.asList(file));
    }

    public FileObserver(java.util.List<java.io.File> list) {
        this(list, 4095);
    }

    @java.lang.Deprecated
    public FileObserver(java.lang.String str, int i) {
        this(new java.io.File(str), i);
    }

    public FileObserver(java.io.File file, int i) {
        this((java.util.List<java.io.File>) java.util.Arrays.asList(file), i);
    }

    public FileObserver(java.util.List<java.io.File> list, int i) {
        this.mFiles = list;
        this.mMask = i;
    }

    protected void finalize() {
        stopWatching();
    }

    public void startWatching() {
        if (this.mDescriptors == null) {
            this.mDescriptors = s_observerThread.startWatching(this.mFiles, this.mMask, this);
        }
    }

    public void stopWatching() {
        if (this.mDescriptors != null) {
            s_observerThread.stopWatching(this.mDescriptors);
            this.mDescriptors = null;
        }
    }
}
