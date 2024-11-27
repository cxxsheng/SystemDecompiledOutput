package android.os;

/* loaded from: classes3.dex */
public class Binder implements android.os.IBinder {
    public static final boolean CHECK_PARCEL_SIZE = false;
    private static final boolean FIND_POTENTIAL_LEAKS = false;
    private static final int NATIVE_ALLOCATION_SIZE = 500;
    static final java.lang.String TAG = "Binder";
    private static final int TRANSACTION_TRACE_NAME_ID_LIMIT = 1024;
    public static final int UNSET_WORKSOURCE = -1;
    private static volatile java.lang.ThreadLocal<com.android.internal.os.SomeArgs> sIdentity$ravenwood;
    private java.lang.String mDescriptor;
    private final long mObject;
    private android.os.IInterface mOwner;
    private volatile java.lang.String mSimpleDescriptor;
    private volatile java.util.concurrent.atomic.AtomicReferenceArray<java.lang.String> mTransactionTraceNames;
    public static boolean LOG_RUNTIME_EXCEPTION = false;
    private static volatile java.lang.String sDumpDisabled = null;
    private static volatile android.os.TransactionTracker sTransactionTracker = null;
    private static com.android.internal.os.BinderInternal.Observer sObserver = null;
    private static volatile com.android.internal.os.BinderCallHeavyHitterWatcher sHeavyHitterWatcher = null;
    private static volatile boolean sStackTrackingEnabled = false;
    static volatile boolean sWarnOnBlocking = false;
    static java.lang.ThreadLocal<java.lang.Boolean> sWarnOnBlockingOnCurrentThread = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: android.os.Binder$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            java.lang.Boolean valueOf;
            valueOf = java.lang.Boolean.valueOf(android.os.Binder.sWarnOnBlocking);
            return valueOf;
        }
    });
    private static boolean sIsHandlingBinderTransaction = false;
    private static android.os.IBinderCallback sBinderCallback = null;
    private static volatile com.android.internal.os.BinderInternal.WorkSourceProvider sWorkSourceProvider = new com.android.internal.os.BinderInternal.WorkSourceProvider() { // from class: android.os.Binder$$ExternalSyntheticLambda1
        @Override // com.android.internal.os.BinderInternal.WorkSourceProvider
        public final int resolveWorkSourceUid(int i) {
            int callingUid;
            callingUid = android.os.Binder.getCallingUid();
            return callingUid;
        }
    };

    public static final native void blockUntilThreadAvailable();

    @dalvik.annotation.optimization.CriticalNative
    public static final native long clearCallingIdentity();

    @dalvik.annotation.optimization.CriticalNative
    public static final native long clearCallingWorkSource();

    public static final native void flushPendingCommands();

    @dalvik.annotation.optimization.CriticalNative
    public static final native int getCallingPid();

    @dalvik.annotation.optimization.CriticalNative
    public static final native int getCallingUid();

    @dalvik.annotation.optimization.CriticalNative
    public static final native int getCallingWorkSourceUid();

    private static native long getNativeBBinderHolder();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getNativeFinalizer();

    @dalvik.annotation.optimization.CriticalNative
    public static final native int getThreadStrictModePolicy();

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean hasExplicitIdentity();

    @dalvik.annotation.optimization.CriticalNative
    public static final native boolean isDirectlyHandlingTransactionNative();

    @dalvik.annotation.optimization.CriticalNative
    public static final native void restoreCallingIdentity(long j);

    @dalvik.annotation.optimization.CriticalNative
    public static final native void restoreCallingWorkSource(long j);

    @dalvik.annotation.optimization.CriticalNative
    public static final native long setCallingWorkSourceUid(int i);

    @dalvik.annotation.optimization.CriticalNative
    public static final native void setThreadStrictModePolicy(int i);

    public final native void forceDowngradeToSystemStability();

    @Override // android.os.IBinder
    public final native android.os.IBinder getExtension();

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public final native void markVintfStability();

    public final native void setExtension(android.os.IBinder iBinder);

    private static class NoImagePreloadHolder {
        public static final libcore.util.NativeAllocationRegistry sRegistry = new libcore.util.NativeAllocationRegistry(android.os.Binder.class.getClassLoader(), android.os.Binder.getNativeFinalizer(), 500);

        private NoImagePreloadHolder() {
        }
    }

    public static void enableStackTracking() {
        sStackTrackingEnabled = true;
    }

    public static void disableStackTracking() {
        sStackTrackingEnabled = false;
    }

    public static boolean isStackTrackingEnabled() {
        return sStackTrackingEnabled;
    }

    public static synchronized android.os.TransactionTracker getTransactionTracker() {
        android.os.TransactionTracker transactionTracker;
        synchronized (android.os.Binder.class) {
            if (sTransactionTracker == null) {
                sTransactionTracker = new android.os.TransactionTracker();
            }
            transactionTracker = sTransactionTracker;
        }
        return transactionTracker;
    }

    public static void setObserver(com.android.internal.os.BinderInternal.Observer observer) {
        sObserver = observer;
    }

    public static void setWarnOnBlocking(boolean z) {
        sWarnOnBlocking = z;
    }

    public static android.os.IBinder allowBlocking(android.os.IBinder iBinder) {
        try {
            if (iBinder instanceof android.os.BinderProxy) {
                ((android.os.BinderProxy) iBinder).mWarnOnBlocking = false;
            } else if (iBinder != null && iBinder.getInterfaceDescriptor() != null && iBinder.queryLocalInterface(iBinder.getInterfaceDescriptor()) == null) {
                android.util.Log.w(TAG, "Unable to allow blocking on interface " + iBinder);
            }
        } catch (android.os.RemoteException e) {
        }
        return iBinder;
    }

    public static android.os.IBinder defaultBlocking(android.os.IBinder iBinder) {
        if (iBinder instanceof android.os.BinderProxy) {
            ((android.os.BinderProxy) iBinder).mWarnOnBlocking = sWarnOnBlocking;
        }
        return iBinder;
    }

    public static void copyAllowBlocking(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        if ((iBinder instanceof android.os.BinderProxy) && (iBinder2 instanceof android.os.BinderProxy)) {
            ((android.os.BinderProxy) iBinder2).mWarnOnBlocking = ((android.os.BinderProxy) iBinder).mWarnOnBlocking;
        }
    }

    public static void allowBlockingForCurrentThread() {
        sWarnOnBlockingOnCurrentThread.set(false);
    }

    public static void defaultBlockingForCurrentThread() {
        sWarnOnBlockingOnCurrentThread.set(java.lang.Boolean.valueOf(sWarnOnBlocking));
    }

    private static class IdentitySupplier implements java.util.function.Supplier<com.android.internal.os.SomeArgs> {
        private IdentitySupplier() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.function.Supplier
        public com.android.internal.os.SomeArgs get() {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = java.lang.Boolean.FALSE;
            obtain.argi1 = android.os.Process.myUid();
            obtain.argi2 = android.os.Process.myPid();
            return obtain;
        }
    }

    public static final boolean isDirectlyHandlingTransactionNative$ravenwood() {
        return false;
    }

    public static final boolean isDirectlyHandlingTransaction() {
        return sIsHandlingBinderTransaction || isDirectlyHandlingTransactionNative();
    }

    public static void setIsDirectlyHandlingTransactionOverride(boolean z) {
        sIsHandlingBinderTransaction = z;
    }

    private static boolean hasExplicitIdentity$ravenwood() {
        return ((com.android.internal.os.SomeArgs) ((java.lang.ThreadLocal) com.android.internal.util.Preconditions.requireNonNullViaRavenwoodRule(sIdentity$ravenwood)).get()).arg1 == java.lang.Boolean.TRUE;
    }

    public static final int getCallingUidOrThrow() {
        if (!isDirectlyHandlingTransaction() && !hasExplicitIdentity()) {
            throw new java.lang.IllegalStateException("Thread is not in a binder transaction, and the calling identity has not been explicitly set with clearCallingIdentity");
        }
        return getCallingUid();
    }

    public static final int getCallingUidOrWtf(java.lang.String str) {
        if (!isDirectlyHandlingTransaction() && !hasExplicitIdentity()) {
            android.util.Slog.wtf(TAG, str + ": Thread is not in a binder transaction, and the calling identity has not been explicitly set with clearCallingIdentity");
        }
        return getCallingUid();
    }

    public static final android.os.UserHandle getCallingUserHandle() {
        return android.os.UserHandle.of(android.os.UserHandle.getUserId(getCallingUid()));
    }

    public static final long clearCallingIdentity$ravenwood() {
        long j;
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) ((java.lang.ThreadLocal) com.android.internal.util.Preconditions.requireNonNullViaRavenwoodRule(sIdentity$ravenwood)).get();
        long j2 = (someArgs.argi1 << 32) | someArgs.argi2;
        if (someArgs.arg1 == java.lang.Boolean.TRUE) {
            j = j2 | 1073741824;
        } else {
            j = j2 & (-1073741825);
        }
        someArgs.arg1 = java.lang.Boolean.TRUE;
        someArgs.argi1 = android.os.Process.myUid();
        someArgs.argi2 = android.os.Process.myPid();
        return j;
    }

    public static final void restoreCallingIdentity$ravenwood(long j) {
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) ((java.lang.ThreadLocal) com.android.internal.util.Preconditions.requireNonNullViaRavenwoodRule(sIdentity$ravenwood)).get();
        someArgs.arg1 = (1073741824 & j) != 0 ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE;
        someArgs.argi1 = (int) (j >> 32);
        someArgs.argi2 = (int) (j & (-1073741825));
    }

    public static final void withCleanCallingIdentity(com.android.internal.util.FunctionalUtils.ThrowingRunnable throwingRunnable) {
        long clearCallingIdentity = clearCallingIdentity();
        try {
            throwingRunnable.runOrThrow();
            restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            restoreCallingIdentity(clearCallingIdentity);
            throw android.util.ExceptionUtils.propagate(th);
        }
    }

    public static final <T> T withCleanCallingIdentity(com.android.internal.util.FunctionalUtils.ThrowingSupplier<T> throwingSupplier) {
        long clearCallingIdentity = clearCallingIdentity();
        try {
            T orThrow = throwingSupplier.getOrThrow();
            restoreCallingIdentity(clearCallingIdentity);
            return orThrow;
        } catch (java.lang.Throwable th) {
            restoreCallingIdentity(clearCallingIdentity);
            throw android.util.ExceptionUtils.propagate(th);
        }
    }

    public static final void flushPendingCommands$ravenwood() {
    }

    public static final void joinThreadPool() {
        com.android.internal.os.BinderInternal.joinThreadPool();
    }

    public static final boolean isProxy(android.os.IInterface iInterface) {
        return iInterface.asBinder() != iInterface;
    }

    public static final void setTransactionCallback(android.os.IBinderCallback iBinderCallback) {
        sBinderCallback = iBinderCallback;
    }

    public static final void transactionCallback(int i, int i2, int i3, int i4) {
        if (sBinderCallback != null) {
            sBinderCallback.onTransactionError(i, i2, i3, i4);
        }
    }

    public Binder() {
        this(null);
    }

    public Binder(java.lang.String str) {
        this.mTransactionTraceNames = null;
        this.mSimpleDescriptor = null;
        this.mObject = getNativeBBinderHolder();
        if (this.mObject != 0) {
            android.os.Binder.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mObject);
        }
        this.mDescriptor = str;
    }

    public void attachInterface(android.os.IInterface iInterface, java.lang.String str) {
        this.mOwner = iInterface;
        this.mDescriptor = str;
    }

    @Override // android.os.IBinder
    public java.lang.String getInterfaceDescriptor() {
        return this.mDescriptor;
    }

    @Override // android.os.IBinder
    public boolean pingBinder() {
        return true;
    }

    @Override // android.os.IBinder
    public boolean isBinderAlive() {
        return true;
    }

    @Override // android.os.IBinder
    public android.os.IInterface queryLocalInterface(java.lang.String str) {
        if (this.mDescriptor != null && this.mDescriptor.equals(str)) {
            return this.mOwner;
        }
        return null;
    }

    public static void setDumpDisabled(java.lang.String str) {
        sDumpDisabled = str;
    }

    @android.annotation.SystemApi
    public interface ProxyTransactListener {
        void onTransactEnded(java.lang.Object obj);

        java.lang.Object onTransactStarted(android.os.IBinder iBinder, int i);

        default java.lang.Object onTransactStarted(android.os.IBinder iBinder, int i, int i2) {
            return onTransactStarted(iBinder, i);
        }
    }

    public static class PropagateWorkSourceTransactListener implements android.os.Binder.ProxyTransactListener {
        @Override // android.os.Binder.ProxyTransactListener
        public java.lang.Object onTransactStarted(android.os.IBinder iBinder, int i) {
            int uid = android.os.ThreadLocalWorkSource.getUid();
            if (uid != -1) {
                return java.lang.Long.valueOf(android.os.Binder.setCallingWorkSourceUid(uid));
            }
            return null;
        }

        @Override // android.os.Binder.ProxyTransactListener
        public void onTransactEnded(java.lang.Object obj) {
            if (obj != null) {
                android.os.Binder.restoreCallingWorkSource(((java.lang.Long) obj).longValue());
            }
        }
    }

    @android.annotation.SystemApi
    public static void setProxyTransactListener(android.os.Binder.ProxyTransactListener proxyTransactListener) {
        android.os.BinderProxy.setTransactListener(proxyTransactListener);
    }

    protected boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        android.os.ParcelFileDescriptor readFileDescriptor;
        java.io.FileDescriptor fileDescriptor;
        if (i == 1598968902) {
            parcel2.writeString(getInterfaceDescriptor());
            return true;
        }
        if (i == 1598311760) {
            readFileDescriptor = parcel.readFileDescriptor();
            java.lang.String[] readStringArray = parcel.readStringArray();
            if (readFileDescriptor != null) {
                try {
                    dump(readFileDescriptor.getFileDescriptor(), readStringArray);
                } finally {
                    libcore.io.IoUtils.closeQuietly(readFileDescriptor);
                }
            }
            if (parcel2 != null) {
                parcel2.writeNoException();
            } else {
                android.os.StrictMode.clearGatheredViolations();
            }
            return true;
        }
        if (i == 1598246212) {
            android.os.ParcelFileDescriptor readFileDescriptor2 = parcel.readFileDescriptor();
            android.os.ParcelFileDescriptor readFileDescriptor3 = parcel.readFileDescriptor();
            readFileDescriptor = parcel.readFileDescriptor();
            java.lang.String[] readStringArray2 = parcel.readStringArray();
            android.os.ShellCallback createFromParcel = android.os.ShellCallback.CREATOR.createFromParcel(parcel);
            android.os.ResultReceiver createFromParcel2 = android.os.ResultReceiver.CREATOR.createFromParcel(parcel);
            if (readFileDescriptor3 != null) {
                if (readFileDescriptor2 == null) {
                    fileDescriptor = null;
                } else {
                    try {
                        fileDescriptor = readFileDescriptor2.getFileDescriptor();
                    } catch (java.lang.Throwable th) {
                        libcore.io.IoUtils.closeQuietly(readFileDescriptor2);
                        libcore.io.IoUtils.closeQuietly(readFileDescriptor3);
                        if (parcel2 != null) {
                            parcel2.writeNoException();
                        } else {
                            android.os.StrictMode.clearGatheredViolations();
                        }
                        throw th;
                    }
                }
                shellCommand(fileDescriptor, readFileDescriptor3.getFileDescriptor(), readFileDescriptor != null ? readFileDescriptor.getFileDescriptor() : readFileDescriptor3.getFileDescriptor(), readStringArray2, createFromParcel, createFromParcel2);
            }
            libcore.io.IoUtils.closeQuietly(readFileDescriptor2);
            libcore.io.IoUtils.closeQuietly(readFileDescriptor3);
            if (parcel2 != null) {
                parcel2.writeNoException();
            } else {
                android.os.StrictMode.clearGatheredViolations();
            }
            return true;
        }
        return false;
    }

    public java.lang.String getTransactionName(int i) {
        return null;
    }

    public final java.lang.String getTransactionTraceName(int i) {
        boolean z = getMaxTransactionId() == 0;
        if (this.mTransactionTraceNames == null) {
            int min = z ? 1024 : java.lang.Math.min(getMaxTransactionId(), 1024);
            this.mSimpleDescriptor = getSimpleDescriptor();
            this.mTransactionTraceNames = new java.util.concurrent.atomic.AtomicReferenceArray<>(min + 1);
        }
        int i2 = z ? i : i - 1;
        if (i2 >= this.mTransactionTraceNames.length() || i2 < 0) {
            return null;
        }
        java.lang.String acquire = this.mTransactionTraceNames.getAcquire(i2);
        if (acquire == null) {
            java.lang.String transactionName = getTransactionName(i);
            java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
            stringBuffer.append("AIDL::java::");
            if (transactionName != null) {
                stringBuffer.append(this.mSimpleDescriptor).append("::").append(transactionName);
            } else {
                stringBuffer.append(this.mSimpleDescriptor).append("::#").append(i);
            }
            stringBuffer.append("::server");
            java.lang.String stringBuffer2 = stringBuffer.toString();
            this.mTransactionTraceNames.setRelease(i2, stringBuffer2);
            return stringBuffer2;
        }
        return acquire;
    }

    private java.lang.String getSimpleDescriptor() {
        java.lang.String str = this.mDescriptor;
        if (str == null) {
            return TAG;
        }
        int lastIndexOf = str.lastIndexOf(android.media.MediaMetrics.SEPARATOR);
        if (lastIndexOf > 0) {
            return str.substring(lastIndexOf + 1);
        }
        return str;
    }

    public int getMaxTransactionId() {
        return 0;
    }

    @Override // android.os.IBinder
    public void dump(java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) {
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(fileDescriptor));
        try {
            doDump(fileDescriptor, fastPrintWriter, strArr);
        } finally {
            fastPrintWriter.flush();
        }
    }

    void doDump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (sDumpDisabled == null) {
            try {
                dump(fileDescriptor, printWriter, strArr);
                return;
            } catch (java.lang.SecurityException e) {
                printWriter.println("Security exception: " + e.getMessage());
                throw e;
            } catch (java.lang.Throwable th) {
                printWriter.println();
                printWriter.println("Exception occurred while dumping:");
                th.printStackTrace(printWriter);
                return;
            }
        }
        printWriter.println(sDumpDisabled);
    }

    @Override // android.os.IBinder
    public void dumpAsync(final java.io.FileDescriptor fileDescriptor, final java.lang.String[] strArr) {
        final com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(fileDescriptor));
        new java.lang.Thread("Binder.dumpAsync") { // from class: android.os.Binder.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    android.os.Binder.this.dump(fileDescriptor, fastPrintWriter, strArr);
                } finally {
                    fastPrintWriter.flush();
                }
            }
        }.start();
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
    }

    @Override // android.os.IBinder
    public void shellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        onShellCommand(fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        int callingUid = getCallingUid();
        int i = -1;
        if (callingUid != 0 && callingUid != 2000) {
            resultReceiver.send(-1, null);
            throw new java.lang.SecurityException("Shell commands are only callable by ADB");
        }
        if (fileDescriptor == null) {
            try {
                fileDescriptor = new java.io.FileInputStream("/dev/null").getFD();
            } catch (java.io.IOException e) {
                if (fileDescriptor3 != null) {
                    fileDescriptor2 = fileDescriptor3;
                }
                com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(fileDescriptor2));
                fastPrintWriter.println("Failed to open /dev/null: " + e.getMessage());
                fastPrintWriter.flush();
                return;
            }
        }
        if (fileDescriptor2 == null) {
            fileDescriptor2 = new java.io.FileOutputStream("/dev/null").getFD();
        }
        if (fileDescriptor3 == null) {
            fileDescriptor3 = fileDescriptor2;
        }
        if (strArr == null) {
            strArr = new java.lang.String[0];
        }
        try {
            try {
                android.os.ParcelFileDescriptor dup = android.os.ParcelFileDescriptor.dup(fileDescriptor);
                try {
                    android.os.ParcelFileDescriptor dup2 = android.os.ParcelFileDescriptor.dup(fileDescriptor2);
                    try {
                        android.os.ParcelFileDescriptor dup3 = android.os.ParcelFileDescriptor.dup(fileDescriptor3);
                        try {
                            i = handleShellCommand(dup, dup2, dup3, strArr);
                            if (dup3 != null) {
                                dup3.close();
                            }
                            if (dup2 != null) {
                                dup2.close();
                            }
                            if (dup != null) {
                                dup.close();
                            }
                        } finally {
                        }
                    } finally {
                    }
                } catch (java.lang.Throwable th) {
                    if (dup != null) {
                        try {
                            dup.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException e2) {
                com.android.internal.util.FastPrintWriter fastPrintWriter2 = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(fileDescriptor3));
                fastPrintWriter2.println("dup() failed: " + e2.getMessage());
                fastPrintWriter2.flush();
            }
        } finally {
            resultReceiver.send(-1, null);
        }
    }

    @android.annotation.SystemApi
    public int handleShellCommand(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3, java.lang.String[] strArr) {
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(parcelFileDescriptor3.getFileDescriptor()));
        fastPrintWriter.println("No shell command implementation.");
        fastPrintWriter.flush();
        return 0;
    }

    @Override // android.os.IBinder
    public final boolean transact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        if (parcel != null) {
            parcel.setDataPosition(0);
        }
        boolean onTransact = onTransact(i, parcel, parcel2, i2);
        if (parcel2 != null) {
            parcel2.setDataPosition(0);
        }
        return onTransact;
    }

    @Override // android.os.IBinder
    public void linkToDeath(android.os.IBinder.DeathRecipient deathRecipient, int i) {
    }

    @Override // android.os.IBinder
    public boolean unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient, int i) {
        return true;
    }

    static void checkParcel(android.os.IBinder iBinder, int i, android.os.Parcel parcel, java.lang.String str) {
    }

    private static long getNativeBBinderHolder$ravenwood() {
        return 0L;
    }

    public static void setWorkSourceProvider(com.android.internal.os.BinderInternal.WorkSourceProvider workSourceProvider) {
        if (workSourceProvider == null) {
            throw new java.lang.IllegalArgumentException("workSourceProvider cannot be null");
        }
        sWorkSourceProvider = workSourceProvider;
    }

    private boolean execTransact(int i, long j, long j2, int i2) {
        android.os.Parcel obtain = android.os.Parcel.obtain(j);
        android.os.Parcel obtain2 = android.os.Parcel.obtain(j2);
        int callingUid = obtain.isForRpc() ? -1 : getCallingUid();
        long uid = callingUid == -1 ? -1L : android.os.ThreadLocalWorkSource.setUid(callingUid);
        try {
            return execTransactInternal(i, obtain, obtain2, i2, callingUid);
        } finally {
            obtain2.recycle();
            obtain.recycle();
            if (callingUid != -1) {
                android.os.ThreadLocalWorkSource.restore(uid);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00b7, code lost:
    
        android.os.StrictMode.clearGatheredViolations();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00ba, code lost:
    
        return r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00b4, code lost:
    
        if (r5 != null) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean execTransactInternal(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2, int i3) {
        com.android.internal.os.BinderInternal.Observer observer = sObserver;
        com.android.internal.os.BinderInternal.CallSession callStarted = observer != null ? observer.callStarted(this, i, -1) : null;
        boolean isTagEnabled = android.os.Trace.isTagEnabled(16777216L);
        getMaxTransactionId();
        java.lang.String transactionTraceName = isTagEnabled ? getTransactionTraceName(i) : null;
        boolean z = true;
        boolean z2 = isTagEnabled && transactionTraceName != null;
        try {
            try {
                com.android.internal.os.BinderCallHeavyHitterWatcher binderCallHeavyHitterWatcher = sHeavyHitterWatcher;
                if (binderCallHeavyHitterWatcher != null && i3 != -1) {
                    binderCallHeavyHitterWatcher.onTransaction(i3, getClass(), i);
                }
                if (z2) {
                    android.os.Trace.traceBegin(16777216L, transactionTraceName);
                }
                if ((i2 & 2) == 0 || i3 == -1) {
                    z = onTransact(i, parcel, parcel2, i2);
                } else {
                    android.app.AppOpsManager.startNotedAppOpsCollection(i3);
                    try {
                        boolean onTransact = onTransact(i, parcel, parcel2, i2);
                        android.app.AppOpsManager.finishNotedAppOpsCollection();
                        z = onTransact;
                    } catch (java.lang.Throwable th) {
                        android.app.AppOpsManager.finishNotedAppOpsCollection();
                        throw th;
                    }
                }
            } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                if (observer != null) {
                    observer.callThrewException(callStarted, e);
                }
                if (LOG_RUNTIME_EXCEPTION) {
                    android.util.Log.w(TAG, "Caught a RuntimeException from the binder stub implementation.", e);
                }
                if ((i2 & 1) == 0) {
                    parcel2.setDataSize(0);
                    parcel2.setDataPosition(0);
                    parcel2.writeException(e);
                } else if (e instanceof android.os.RemoteException) {
                    android.util.Log.w(TAG, "Binder call failed.", e);
                } else {
                    android.util.Log.w(TAG, "Caught a RuntimeException from the binder stub implementation.", e);
                }
                if (z2) {
                    android.os.Trace.traceEnd(16777216L);
                }
            }
        } finally {
            if (z2) {
                android.os.Trace.traceEnd(16777216L);
            }
            if (observer != null) {
                observer.callEnded(callStarted, parcel.dataSize(), parcel2.dataSize(), sWorkSourceProvider.resolveWorkSourceUid(parcel.readCallingWorkSourceUid()));
            }
            checkParcel(this, i, parcel2, "Unreasonably large binder reply buffer");
        }
    }

    public static synchronized void setHeavyHitterWatcherConfig(boolean z, int i, float f, com.android.internal.os.BinderCallHeavyHitterWatcher.BinderCallHeavyHitterListener binderCallHeavyHitterListener) {
        synchronized (android.os.Binder.class) {
            android.util.Slog.i(TAG, "Setting heavy hitter watcher config: " + z + ", " + i + ", " + f);
            com.android.internal.os.BinderCallHeavyHitterWatcher binderCallHeavyHitterWatcher = sHeavyHitterWatcher;
            boolean z2 = false;
            if (z) {
                if (binderCallHeavyHitterListener == null) {
                    throw new java.lang.IllegalArgumentException();
                }
                if (binderCallHeavyHitterWatcher == null) {
                    binderCallHeavyHitterWatcher = com.android.internal.os.BinderCallHeavyHitterWatcher.getInstance();
                    z2 = true;
                }
                binderCallHeavyHitterWatcher.setConfig(true, i, f, binderCallHeavyHitterListener);
                if (z2) {
                    sHeavyHitterWatcher = binderCallHeavyHitterWatcher;
                }
            } else if (binderCallHeavyHitterWatcher != null) {
                binderCallHeavyHitterWatcher.setConfig(false, 0, 0.0f, null);
            }
        }
    }
}
