package android.content;

/* loaded from: classes.dex */
public abstract class AbstractThreadedSyncAdapter {
    private static final boolean ENABLE_LOG;

    @java.lang.Deprecated
    public static final int LOG_SYNC_DETAILS = 2743;
    private static final java.lang.String TAG = "SyncAdapter";
    private boolean mAllowParallelSyncs;
    private final boolean mAutoInitialize;
    private final android.content.Context mContext;
    private final android.content.AbstractThreadedSyncAdapter.ISyncAdapterImpl mISyncAdapterImpl;
    private final java.util.concurrent.atomic.AtomicInteger mNumSyncStarts;
    private final java.lang.Object mSyncThreadLock;
    private final java.util.HashMap<android.accounts.Account, android.content.AbstractThreadedSyncAdapter.SyncThread> mSyncThreads;

    public abstract void onPerformSync(android.accounts.Account account, android.os.Bundle bundle, java.lang.String str, android.content.ContentProviderClient contentProviderClient, android.content.SyncResult syncResult);

    static {
        ENABLE_LOG = android.os.Build.IS_DEBUGGABLE && android.util.Log.isLoggable(TAG, 3);
    }

    public AbstractThreadedSyncAdapter(android.content.Context context, boolean z) {
        this(context, z, false);
    }

    public AbstractThreadedSyncAdapter(android.content.Context context, boolean z, boolean z2) {
        this.mSyncThreads = new java.util.HashMap<>();
        this.mSyncThreadLock = new java.lang.Object();
        this.mContext = context;
        this.mISyncAdapterImpl = new android.content.AbstractThreadedSyncAdapter.ISyncAdapterImpl();
        this.mNumSyncStarts = new java.util.concurrent.atomic.AtomicInteger(0);
        this.mAutoInitialize = z;
        this.mAllowParallelSyncs = z2;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.accounts.Account toSyncKey(android.accounts.Account account) {
        if (this.mAllowParallelSyncs) {
            return account;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ISyncAdapterImpl extends android.content.ISyncAdapter.Stub {
        private ISyncAdapterImpl() {
        }

        private boolean isCallerSystem() {
            if (android.os.Binder.getCallingUid() != 1000) {
                android.util.EventLog.writeEvent(1397638484, "203229608", -1, "");
                return false;
            }
            return true;
        }

        @Override // android.content.ISyncAdapter
        public void onUnsyncableAccount(android.content.ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) {
            if (!isCallerSystem()) {
                return;
            }
            android.os.Handler.getMain().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.content.AbstractThreadedSyncAdapter$ISyncAdapterImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.content.AbstractThreadedSyncAdapter) obj).handleOnUnsyncableAccount((android.content.ISyncAdapterUnsyncableAccountCallback) obj2);
                }
            }, android.content.AbstractThreadedSyncAdapter.this, iSyncAdapterUnsyncableAccountCallback));
        }

        @Override // android.content.ISyncAdapter
        public void startSync(android.content.ISyncContext iSyncContext, java.lang.String str, android.accounts.Account account, android.os.Bundle bundle) {
            boolean z;
            if (isCallerSystem()) {
                if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                    if (bundle != null) {
                        bundle.size();
                    }
                    android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "startSync() start " + str + " " + account + " " + bundle);
                }
                try {
                    try {
                        android.content.SyncContext syncContext = new android.content.SyncContext(iSyncContext);
                        android.accounts.Account syncKey = android.content.AbstractThreadedSyncAdapter.this.toSyncKey(account);
                        synchronized (android.content.AbstractThreadedSyncAdapter.this.mSyncThreadLock) {
                            boolean z2 = true;
                            if (android.content.AbstractThreadedSyncAdapter.this.mSyncThreads.containsKey(syncKey)) {
                                if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                                    android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "  alreadyInProgress");
                                }
                            } else {
                                if (android.content.AbstractThreadedSyncAdapter.this.mAutoInitialize && bundle != null && bundle.getBoolean(android.content.ContentResolver.SYNC_EXTRAS_INITIALIZE, false)) {
                                    try {
                                        if (android.content.ContentResolver.getIsSyncable(account, str) < 0) {
                                            android.content.ContentResolver.setIsSyncable(account, str, 1);
                                        }
                                        if (z) {
                                            return;
                                        } else {
                                            return;
                                        }
                                    } finally {
                                        syncContext.onFinished(new android.content.SyncResult());
                                    }
                                }
                                android.content.AbstractThreadedSyncAdapter.SyncThread syncThread = new android.content.AbstractThreadedSyncAdapter.SyncThread("SyncAdapterThread-" + android.content.AbstractThreadedSyncAdapter.this.mNumSyncStarts.incrementAndGet(), syncContext, str, account, bundle);
                                android.content.AbstractThreadedSyncAdapter.this.mSyncThreads.put(syncKey, syncThread);
                                syncThread.start();
                                z2 = false;
                            }
                            if (z2) {
                                syncContext.onFinished(android.content.SyncResult.ALREADY_IN_PROGRESS);
                            }
                            if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                                android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "startSync() finishing");
                            }
                        }
                    } catch (java.lang.Error | java.lang.RuntimeException e) {
                        if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                            android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "startSync() caught exception", e);
                        }
                        throw e;
                    }
                } finally {
                    if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                        android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "startSync() finishing");
                    }
                }
            }
        }

        @Override // android.content.ISyncAdapter
        public void cancelSync(android.content.ISyncContext iSyncContext) {
            android.content.AbstractThreadedSyncAdapter.SyncThread syncThread;
            try {
                if (!isCallerSystem()) {
                    return;
                }
                try {
                    synchronized (android.content.AbstractThreadedSyncAdapter.this.mSyncThreadLock) {
                        java.util.Iterator it = android.content.AbstractThreadedSyncAdapter.this.mSyncThreads.values().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                syncThread = null;
                                break;
                            } else {
                                syncThread = (android.content.AbstractThreadedSyncAdapter.SyncThread) it.next();
                                if (syncThread.mSyncContext.getSyncContextBinder() == iSyncContext.asBinder()) {
                                    break;
                                }
                            }
                        }
                    }
                    if (syncThread != null) {
                        if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                            android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "cancelSync() " + syncThread.mAuthority + " " + syncThread.mAccount);
                        }
                        if (android.content.AbstractThreadedSyncAdapter.this.mAllowParallelSyncs) {
                            android.content.AbstractThreadedSyncAdapter.this.onSyncCanceled(syncThread);
                        } else {
                            android.content.AbstractThreadedSyncAdapter.this.onSyncCanceled();
                        }
                    } else if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                        android.util.Log.w(android.content.AbstractThreadedSyncAdapter.TAG, "cancelSync() unknown context");
                    }
                } catch (java.lang.Error | java.lang.RuntimeException e) {
                    if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                        android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "cancelSync() caught exception", e);
                    }
                    throw e;
                }
            } finally {
                if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                    android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "cancelSync() finishing");
                }
            }
        }
    }

    private class SyncThread extends java.lang.Thread {
        private final android.accounts.Account mAccount;
        private final java.lang.String mAuthority;
        private final android.os.Bundle mExtras;
        private final android.content.SyncContext mSyncContext;
        private final android.accounts.Account mThreadsKey;

        private SyncThread(java.lang.String str, android.content.SyncContext syncContext, java.lang.String str2, android.accounts.Account account, android.os.Bundle bundle) {
            super(str);
            this.mSyncContext = syncContext;
            this.mAuthority = str2;
            this.mAccount = account;
            this.mExtras = bundle;
            this.mThreadsKey = android.content.AbstractThreadedSyncAdapter.this.toSyncKey(account);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            android.os.Process.setThreadPriority(10);
            if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "Thread started");
            }
            android.os.Trace.traceBegin(128L, this.mAuthority);
            android.content.SyncResult syncResult = new android.content.SyncResult();
            android.content.ContentProviderClient contentProviderClient = null;
            try {
                try {
                    try {
                    } catch (java.lang.SecurityException e) {
                        e = e;
                    }
                    if (isCanceled()) {
                        if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                            android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "Already canceled");
                        }
                        android.os.Trace.traceEnd(128L);
                        if (!isCanceled()) {
                            this.mSyncContext.onFinished(syncResult);
                        }
                        synchronized (android.content.AbstractThreadedSyncAdapter.this.mSyncThreadLock) {
                            android.content.AbstractThreadedSyncAdapter.this.mSyncThreads.remove(this.mThreadsKey);
                        }
                        if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                            android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "Thread finished");
                            return;
                        }
                        return;
                    }
                    if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                        android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "Calling onPerformSync...");
                    }
                    android.content.ContentProviderClient acquireContentProviderClient = android.content.AbstractThreadedSyncAdapter.this.mContext.getContentResolver().acquireContentProviderClient(this.mAuthority);
                    try {
                        if (acquireContentProviderClient != null) {
                            android.content.AbstractThreadedSyncAdapter.this.onPerformSync(this.mAccount, this.mExtras, this.mAuthority, acquireContentProviderClient, syncResult);
                        } else {
                            syncResult.databaseError = true;
                        }
                        if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                            android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "onPerformSync done");
                        }
                        android.os.Trace.traceEnd(128L);
                        if (acquireContentProviderClient != null) {
                            acquireContentProviderClient.release();
                        }
                        if (!isCanceled()) {
                            this.mSyncContext.onFinished(syncResult);
                        }
                        synchronized (android.content.AbstractThreadedSyncAdapter.this.mSyncThreadLock) {
                            android.content.AbstractThreadedSyncAdapter.this.mSyncThreads.remove(this.mThreadsKey);
                        }
                        if (!android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                            return;
                        }
                    } catch (java.lang.Error | java.lang.RuntimeException e2) {
                        e = e2;
                        if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                            android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "caught exception", e);
                        }
                        throw e;
                    } catch (java.lang.SecurityException e3) {
                        e = e3;
                        contentProviderClient = acquireContentProviderClient;
                        if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                            android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "SecurityException", e);
                        }
                        android.content.AbstractThreadedSyncAdapter.this.onSecurityException(this.mAccount, this.mExtras, this.mAuthority, syncResult);
                        syncResult.databaseError = true;
                        android.os.Trace.traceEnd(128L);
                        if (contentProviderClient != null) {
                            contentProviderClient.release();
                        }
                        if (!isCanceled()) {
                            this.mSyncContext.onFinished(syncResult);
                        }
                        synchronized (android.content.AbstractThreadedSyncAdapter.this.mSyncThreadLock) {
                            android.content.AbstractThreadedSyncAdapter.this.mSyncThreads.remove(this.mThreadsKey);
                        }
                        if (!android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                            return;
                        }
                        android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "Thread finished");
                    } catch (java.lang.Throwable th) {
                        th = th;
                        contentProviderClient = acquireContentProviderClient;
                        android.os.Trace.traceEnd(128L);
                        if (contentProviderClient != null) {
                            contentProviderClient.release();
                        }
                        if (!isCanceled()) {
                            this.mSyncContext.onFinished(syncResult);
                        }
                        synchronized (android.content.AbstractThreadedSyncAdapter.this.mSyncThreadLock) {
                            android.content.AbstractThreadedSyncAdapter.this.mSyncThreads.remove(this.mThreadsKey);
                        }
                        if (android.content.AbstractThreadedSyncAdapter.ENABLE_LOG) {
                            android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "Thread finished");
                        }
                        throw th;
                    }
                    android.util.Log.d(android.content.AbstractThreadedSyncAdapter.TAG, "Thread finished");
                } catch (java.lang.Error | java.lang.RuntimeException e4) {
                    e = e4;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        }

        private boolean isCanceled() {
            return java.lang.Thread.currentThread().isInterrupted();
        }
    }

    public final android.os.IBinder getSyncAdapterBinder() {
        return this.mISyncAdapterImpl.asBinder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnUnsyncableAccount(android.content.ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) {
        boolean z;
        try {
            z = onUnsyncableAccount();
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Exception while calling onUnsyncableAccount, assuming 'true'", e);
            z = true;
        }
        try {
            iSyncAdapterUnsyncableAccountCallback.onUnsyncableAccountDone(z);
        } catch (android.os.RemoteException e2) {
            android.util.Log.e(TAG, "Could not report result of onUnsyncableAccount", e2);
        }
    }

    public boolean onUnsyncableAccount() {
        return true;
    }

    public void onSecurityException(android.accounts.Account account, android.os.Bundle bundle, java.lang.String str, android.content.SyncResult syncResult) {
    }

    public void onSyncCanceled() {
        android.content.AbstractThreadedSyncAdapter.SyncThread syncThread;
        synchronized (this.mSyncThreadLock) {
            syncThread = this.mSyncThreads.get(null);
        }
        if (syncThread != null) {
            syncThread.interrupt();
        }
    }

    public void onSyncCanceled(java.lang.Thread thread) {
        thread.interrupt();
    }
}
