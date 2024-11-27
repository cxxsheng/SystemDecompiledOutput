package android.os;

/* loaded from: classes3.dex */
public final class BinderProxy implements android.os.IBinder {
    private static final int NATIVE_ALLOCATION_SIZE = 1000;
    private final long mNativeData;
    private static volatile android.os.Binder.ProxyTransactListener sTransactListener = null;
    private static final android.os.BinderProxy.ProxyMap sProxyMap = new android.os.BinderProxy.ProxyMap();
    volatile boolean mWarnOnBlocking = android.os.Binder.sWarnOnBlocking;
    private java.util.List<android.os.IBinder.DeathRecipient> mDeathRecipients = java.util.Collections.synchronizedList(new java.util.ArrayList());

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getNativeFinalizer();

    private native void linkToDeathNative(android.os.IBinder.DeathRecipient deathRecipient, int i) throws android.os.RemoteException;

    private native boolean unlinkToDeathNative(android.os.IBinder.DeathRecipient deathRecipient, int i);

    @Override // android.os.IBinder
    public native android.os.IBinder getExtension() throws android.os.RemoteException;

    @Override // android.os.IBinder
    public native java.lang.String getInterfaceDescriptor() throws android.os.RemoteException;

    @Override // android.os.IBinder
    public native boolean isBinderAlive();

    @Override // android.os.IBinder
    public native boolean pingBinder();

    public native boolean transactNative(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException;

    private static class BinderProxyMapSizeException extends java.lang.AssertionError {
        BinderProxyMapSizeException(java.lang.String str) {
            super(str);
        }
    }

    public static void setTransactListener(android.os.Binder.ProxyTransactListener proxyTransactListener) {
        sTransactListener = proxyTransactListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ProxyMap {
        private static final int CRASH_AT_SIZE = 25000;
        private static final int LOG_MAIN_INDEX_SIZE = 8;
        private static final int MAIN_INDEX_MASK = 255;
        private static final int MAIN_INDEX_SIZE = 256;
        static final int MAX_NUM_INTERFACES_TO_DUMP = 10;
        private static final int WARN_INCREMENT = 10;
        private final java.lang.Long[][] mMainIndexKeys;
        private final java.util.ArrayList<java.lang.ref.WeakReference<android.os.BinderProxy>>[] mMainIndexValues;
        private int mRandom;
        private int mWarnBucketSize;

        private ProxyMap() {
            this.mWarnBucketSize = 20;
            this.mMainIndexKeys = new java.lang.Long[256][];
            this.mMainIndexValues = new java.util.ArrayList[256];
        }

        private static int hash(long j) {
            return ((int) ((j >> 10) ^ (j >> 2))) & 255;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int size() {
            int i = 0;
            for (java.util.ArrayList<java.lang.ref.WeakReference<android.os.BinderProxy>> arrayList : this.mMainIndexValues) {
                if (arrayList != null) {
                    i += arrayList.size();
                }
            }
            return i;
        }

        private int unclearedSize() {
            int i = 0;
            for (java.util.ArrayList<java.lang.ref.WeakReference<android.os.BinderProxy>> arrayList : this.mMainIndexValues) {
                if (arrayList != null) {
                    java.util.Iterator<java.lang.ref.WeakReference<android.os.BinderProxy>> it = arrayList.iterator();
                    while (it.hasNext()) {
                        if (!it.next().refersTo(null)) {
                            i++;
                        }
                    }
                }
            }
            return i;
        }

        private void remove(int i, int i2) {
            java.lang.Long[] lArr = this.mMainIndexKeys[i];
            java.util.ArrayList<java.lang.ref.WeakReference<android.os.BinderProxy>> arrayList = this.mMainIndexValues[i];
            int size = arrayList.size() - 1;
            if (i2 != size) {
                lArr[i2] = lArr[size];
                arrayList.set(i2, arrayList.get(size));
            }
            arrayList.remove(size);
        }

        android.os.BinderProxy get(long j) {
            int hash = hash(j);
            java.lang.Long[] lArr = this.mMainIndexKeys[hash];
            if (lArr == null) {
                return null;
            }
            java.util.ArrayList<java.lang.ref.WeakReference<android.os.BinderProxy>> arrayList = this.mMainIndexValues[hash];
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (j == lArr[i].longValue()) {
                    android.os.BinderProxy binderProxy = arrayList.get(i).get();
                    if (binderProxy != null) {
                        return binderProxy;
                    }
                    remove(hash, i);
                    return null;
                }
            }
            return null;
        }

        void set(long j, android.os.BinderProxy binderProxy) {
            int hash = hash(j);
            java.util.ArrayList<java.lang.ref.WeakReference<android.os.BinderProxy>> arrayList = this.mMainIndexValues[hash];
            if (arrayList == null) {
                java.util.ArrayList<java.lang.ref.WeakReference<android.os.BinderProxy>>[] arrayListArr = this.mMainIndexValues;
                java.util.ArrayList<java.lang.ref.WeakReference<android.os.BinderProxy>> arrayList2 = new java.util.ArrayList<>();
                arrayListArr[hash] = arrayList2;
                this.mMainIndexKeys[hash] = new java.lang.Long[1];
                arrayList = arrayList2;
            }
            int size = arrayList.size();
            java.lang.ref.WeakReference<android.os.BinderProxy> weakReference = new java.lang.ref.WeakReference<>(binderProxy);
            for (int i = 0; i < size; i++) {
                if (arrayList.get(i).refersTo(null)) {
                    arrayList.set(i, weakReference);
                    this.mMainIndexKeys[hash][i] = java.lang.Long.valueOf(j);
                    if (i < size - 1) {
                        int i2 = this.mRandom + 1;
                        this.mRandom = i2;
                        int i3 = i + 1;
                        int floorMod = i3 + java.lang.Math.floorMod(i2, size - i3);
                        if (arrayList.get(floorMod).refersTo(null)) {
                            remove(hash, floorMod);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            arrayList.add(size, weakReference);
            java.lang.Long[] lArr = this.mMainIndexKeys[hash];
            if (lArr.length == size) {
                java.lang.Long[] lArr2 = new java.lang.Long[(size / 2) + size + 2];
                java.lang.System.arraycopy(lArr, 0, lArr2, 0, size);
                lArr2[size] = java.lang.Long.valueOf(j);
                this.mMainIndexKeys[hash] = lArr2;
            } else {
                lArr[size] = java.lang.Long.valueOf(j);
            }
            if (size >= this.mWarnBucketSize) {
                int size2 = size();
                android.util.Log.v("Binder", "BinderProxy map growth! bucket size = " + size + " total = " + size2);
                this.mWarnBucketSize += 10;
                if (size2 >= CRASH_AT_SIZE) {
                    int unclearedSize = unclearedSize();
                    if (unclearedSize >= CRASH_AT_SIZE) {
                        dumpProxyInterfaceCounts();
                        dumpPerUidProxyCounts();
                        java.lang.Runtime.getRuntime().gc();
                        throw new android.os.BinderProxy.BinderProxyMapSizeException("Binder ProxyMap has too many entries: " + size2 + " (total), " + unclearedSize + " (uncleared), " + unclearedSize() + " (uncleared after GC). BinderProxy leak?");
                    }
                    if (size2 > (unclearedSize * 3) / 2) {
                        android.util.Log.v("Binder", "BinderProxy map has many cleared entries: " + (size2 - unclearedSize) + " of " + size2 + " are cleared");
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.os.BinderProxy.InterfaceCount[] getSortedInterfaceCounts(int i) {
            int i2;
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("negative interface count");
            }
            final java.util.HashMap hashMap = new java.util.HashMap();
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            synchronized (android.os.BinderProxy.sProxyMap) {
                for (java.util.ArrayList<java.lang.ref.WeakReference<android.os.BinderProxy>> arrayList2 : this.mMainIndexValues) {
                    if (arrayList2 != null) {
                        arrayList.addAll(arrayList2);
                    }
                }
            }
            try {
                android.app.ActivityManager.getService().enableAppFreezer(false);
            } catch (android.os.RemoteException e) {
                android.util.Log.e("Binder", "RemoteException while disabling app freezer");
            }
            java.util.concurrent.ExecutorService newSingleThreadExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();
            newSingleThreadExecutor.submit(new java.lang.Runnable() { // from class: android.os.BinderProxy$ProxyMap$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.BinderProxy.ProxyMap.lambda$getSortedInterfaceCounts$0(arrayList, hashMap);
                }
            });
            try {
                newSingleThreadExecutor.shutdown();
                if (!newSingleThreadExecutor.awaitTermination(20L, java.util.concurrent.TimeUnit.SECONDS)) {
                    android.util.Log.e("Binder", "Failed to complete binder proxy dump, dumping what we have so far.");
                }
            } catch (java.lang.InterruptedException e2) {
            }
            try {
                android.app.ActivityManager.getService().enableAppFreezer(true);
            } catch (android.os.RemoteException e3) {
                android.util.Log.e("Binder", "RemoteException while re-enabling app freezer");
            }
            java.util.Map.Entry[] entryArr = (java.util.Map.Entry[]) hashMap.entrySet().toArray(new java.util.Map.Entry[hashMap.size()]);
            java.util.Arrays.sort(entryArr, new java.util.Comparator() { // from class: android.os.BinderProxy$ProxyMap$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int compareTo;
                    compareTo = ((java.lang.Integer) ((java.util.Map.Entry) obj2).getValue()).compareTo((java.lang.Integer) ((java.util.Map.Entry) obj).getValue());
                    return compareTo;
                }
            });
            int min = java.lang.Math.min(i, entryArr.length);
            android.os.BinderProxy.InterfaceCount[] interfaceCountArr = new android.os.BinderProxy.InterfaceCount[min];
            for (i2 = 0; i2 < min; i2++) {
                interfaceCountArr[i2] = new android.os.BinderProxy.InterfaceCount((java.lang.String) entryArr[i2].getKey(), ((java.lang.Integer) entryArr[i2].getValue()).intValue());
            }
            return interfaceCountArr;
        }

        static /* synthetic */ void lambda$getSortedInterfaceCounts$0(java.util.ArrayList arrayList, java.util.Map map) {
            java.lang.String str;
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                android.os.BinderProxy binderProxy = (android.os.BinderProxy) ((java.lang.ref.WeakReference) it.next()).get();
                if (binderProxy == null) {
                    str = "<cleared weak-ref>";
                } else {
                    try {
                        java.lang.String interfaceDescriptor = binderProxy.getInterfaceDescriptor();
                        if ((interfaceDescriptor == null || interfaceDescriptor.isEmpty()) && !binderProxy.isBinderAlive()) {
                            str = "<proxy to dead node>";
                        } else {
                            str = interfaceDescriptor;
                        }
                    } catch (java.lang.Throwable th) {
                        str = "<exception during getDescriptor>";
                    }
                }
                java.lang.Integer num = (java.lang.Integer) map.get(str);
                if (num != null) {
                    map.put(str, java.lang.Integer.valueOf(num.intValue() + 1));
                } else {
                    map.put(str, 1);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpProxyInterfaceCounts() {
            android.os.BinderProxy.InterfaceCount[] sortedInterfaceCounts = getSortedInterfaceCounts(10);
            android.util.Log.v("Binder", "BinderProxy descriptor histogram (top " + java.lang.Integer.toString(10) + "):");
            int i = 0;
            while (i < sortedInterfaceCounts.length) {
                int i2 = i + 1;
                android.util.Log.v("Binder", " #" + i2 + ": " + sortedInterfaceCounts[i]);
                i = i2;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpPerUidProxyCounts() {
            android.util.SparseIntArray nGetBinderProxyPerUidCounts = com.android.internal.os.BinderInternal.nGetBinderProxyPerUidCounts();
            if (nGetBinderProxyPerUidCounts.size() == 0) {
                return;
            }
            android.util.Log.d("Binder", "Per Uid Binder Proxy Counts:");
            for (int i = 0; i < nGetBinderProxyPerUidCounts.size(); i++) {
                android.util.Log.d("Binder", "UID : " + nGetBinderProxyPerUidCounts.keyAt(i) + "  count = " + nGetBinderProxyPerUidCounts.valueAt(i));
            }
        }
    }

    public static final class InterfaceCount {
        private final int mCount;
        private final java.lang.String mInterfaceName;

        InterfaceCount(java.lang.String str, int i) {
            this.mInterfaceName = str;
            this.mCount = i;
        }

        public java.lang.String toString() {
            return this.mInterfaceName + " x" + java.lang.Integer.toString(this.mCount);
        }
    }

    public static android.os.BinderProxy.InterfaceCount[] getSortedInterfaceCounts(int i) {
        return sProxyMap.getSortedInterfaceCounts(i);
    }

    public static int getProxyCount() {
        int size;
        synchronized (sProxyMap) {
            size = sProxyMap.size();
        }
        return size;
    }

    public static void dumpProxyDebugInfo() {
        if (android.os.Build.IS_DEBUGGABLE) {
            sProxyMap.dumpProxyInterfaceCounts();
            sProxyMap.dumpPerUidProxyCounts();
        }
    }

    private static android.os.BinderProxy getInstance(long j, long j2) {
        synchronized (sProxyMap) {
            try {
                android.os.BinderProxy binderProxy = sProxyMap.get(j2);
                if (binderProxy != null) {
                    return binderProxy;
                }
                android.os.BinderProxy binderProxy2 = new android.os.BinderProxy(j);
                android.os.BinderProxy.NoImagePreloadHolder.sRegistry.registerNativeAllocation(binderProxy2, j);
                sProxyMap.set(j2, binderProxy2);
                return binderProxy2;
            } catch (java.lang.Throwable th) {
                libcore.util.NativeAllocationRegistry.applyFreeFunction(android.os.BinderProxy.NoImagePreloadHolder.sNativeFinalizer, j);
                throw th;
            }
        }
    }

    private BinderProxy(long j) {
        this.mNativeData = j;
    }

    private static class NoImagePreloadHolder {
        public static final long sNativeFinalizer = android.os.BinderProxy.getNativeFinalizer();
        public static final libcore.util.NativeAllocationRegistry sRegistry = new libcore.util.NativeAllocationRegistry(android.os.BinderProxy.class.getClassLoader(), sNativeFinalizer, 1000);

        private NoImagePreloadHolder() {
        }
    }

    @Override // android.os.IBinder
    public android.os.IInterface queryLocalInterface(java.lang.String str) {
        return null;
    }

    @Override // android.os.IBinder
    public boolean transact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        java.lang.Object obj;
        android.os.Binder.checkParcel(this, i, parcel, "Unreasonably large binder buffer");
        boolean z = this.mWarnOnBlocking;
        if (z && (i2 & 1) == 0 && android.os.Binder.sWarnOnBlockingOnCurrentThread.get().booleanValue()) {
            z = false;
            this.mWarnOnBlocking = false;
            if (android.os.Build.IS_USERDEBUG || android.os.Build.IS_ENG) {
                android.util.Log.wtf("Binder", "Outgoing transactions from this process must be FLAG_ONEWAY", new java.lang.Throwable());
            } else {
                android.util.Log.w("Binder", "Outgoing transactions from this process must be FLAG_ONEWAY", new java.lang.Throwable());
            }
        }
        boolean isStackTrackingEnabled = android.os.Binder.isStackTrackingEnabled();
        if (isStackTrackingEnabled) {
            java.lang.Throwable th = new java.lang.Throwable();
            android.os.Binder.getTransactionTracker().addTrace(th);
            java.lang.StackTraceElement stackTraceElement = th.getStackTrace()[1];
            android.os.Trace.traceBegin(1L, stackTraceElement.getClassName() + android.media.MediaMetrics.SEPARATOR + stackTraceElement.getMethodName());
        }
        android.os.Binder.ProxyTransactListener proxyTransactListener = sTransactListener;
        if (proxyTransactListener == null) {
            obj = null;
        } else {
            int callingWorkSourceUid = android.os.Binder.getCallingWorkSourceUid();
            obj = proxyTransactListener.onTransactStarted(this, i, i2);
            int callingWorkSourceUid2 = android.os.Binder.getCallingWorkSourceUid();
            if (callingWorkSourceUid != callingWorkSourceUid2) {
                parcel.replaceCallingWorkSourceUid(callingWorkSourceUid2);
            }
        }
        android.app.AppOpsManager.PausedNotedAppOpsCollection pauseNotedAppOpsCollection = android.app.AppOpsManager.pauseNotedAppOpsCollection();
        if ((i2 & 1) == 0 && android.app.AppOpsManager.isListeningForOpNoted()) {
            i2 |= 2;
        }
        try {
            boolean transactNative = transactNative(i, parcel, parcel2, i2);
            if (parcel2 != null && !z) {
                parcel2.addFlags(1);
            }
            return transactNative;
        } finally {
            android.app.AppOpsManager.resumeNotedAppOpsCollection(pauseNotedAppOpsCollection);
            if (proxyTransactListener != null) {
                proxyTransactListener.onTransactEnded(obj);
            }
            if (isStackTrackingEnabled) {
                android.os.Trace.traceEnd(1L);
            }
        }
    }

    @Override // android.os.IBinder
    public void linkToDeath(android.os.IBinder.DeathRecipient deathRecipient, int i) throws android.os.RemoteException {
        linkToDeathNative(deathRecipient, i);
        this.mDeathRecipients.add(deathRecipient);
    }

    @Override // android.os.IBinder
    public boolean unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient, int i) {
        this.mDeathRecipients.remove(deathRecipient);
        return unlinkToDeathNative(deathRecipient, i);
    }

    @Override // android.os.IBinder
    public void dump(java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        obtain.writeFileDescriptor(fileDescriptor);
        obtain.writeStringArray(strArr);
        try {
            transact(android.os.IBinder.DUMP_TRANSACTION, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.os.IBinder
    public void dumpAsync(java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        obtain.writeFileDescriptor(fileDescriptor);
        obtain.writeStringArray(strArr);
        try {
            transact(android.os.IBinder.DUMP_TRANSACTION, obtain, obtain2, 1);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.os.IBinder
    public void shellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        obtain.writeFileDescriptor(fileDescriptor);
        obtain.writeFileDescriptor(fileDescriptor2);
        obtain.writeFileDescriptor(fileDescriptor3);
        obtain.writeStringArray(strArr);
        android.os.ShellCallback.writeToParcel(shellCallback, obtain);
        resultReceiver.writeToParcel(obtain, 0);
        try {
            transact(android.os.IBinder.SHELL_COMMAND_TRANSACTION, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    private static void sendDeathNotice(android.os.IBinder.DeathRecipient deathRecipient, android.os.IBinder iBinder) {
        try {
            deathRecipient.binderDied(iBinder);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.w("BinderNative", "Uncaught exception from death notification", e);
        }
    }
}
