package android.app;

/* compiled from: LoaderManager.java */
/* loaded from: classes.dex */
class LoaderManagerImpl extends android.app.LoaderManager {
    static boolean DEBUG = false;
    static final java.lang.String TAG = "LoaderManager";
    boolean mCreatingLoader;
    private android.app.FragmentHostCallback mHost;
    boolean mRetaining;
    boolean mRetainingStarted;
    boolean mStarted;
    final java.lang.String mWho;
    final android.util.SparseArray<android.app.LoaderManagerImpl.LoaderInfo> mLoaders = new android.util.SparseArray<>(0);
    final android.util.SparseArray<android.app.LoaderManagerImpl.LoaderInfo> mInactiveLoaders = new android.util.SparseArray<>(0);

    /* compiled from: LoaderManager.java */
    final class LoaderInfo implements android.content.Loader.OnLoadCompleteListener<java.lang.Object>, android.content.Loader.OnLoadCanceledListener<java.lang.Object> {
        final android.os.Bundle mArgs;
        android.app.LoaderManager.LoaderCallbacks<java.lang.Object> mCallbacks;
        java.lang.Object mData;
        boolean mDeliveredData;
        boolean mDestroyed;
        boolean mHaveData;
        final int mId;
        boolean mListenerRegistered;
        android.content.Loader<java.lang.Object> mLoader;
        android.app.LoaderManagerImpl.LoaderInfo mPendingLoader;
        boolean mReportNextStart;
        boolean mRetaining;
        boolean mRetainingStarted;
        boolean mStarted;

        public LoaderInfo(int i, android.os.Bundle bundle, android.app.LoaderManager.LoaderCallbacks<java.lang.Object> loaderCallbacks) {
            this.mId = i;
            this.mArgs = bundle;
            this.mCallbacks = loaderCallbacks;
        }

        void start() {
            if (this.mRetaining && this.mRetainingStarted) {
                this.mStarted = true;
                return;
            }
            if (this.mStarted) {
                return;
            }
            this.mStarted = true;
            if (android.app.LoaderManagerImpl.DEBUG) {
                android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Starting: " + this);
            }
            if (this.mLoader == null && this.mCallbacks != null) {
                this.mLoader = this.mCallbacks.onCreateLoader(this.mId, this.mArgs);
            }
            if (this.mLoader != null) {
                if (this.mLoader.getClass().isMemberClass() && !java.lang.reflect.Modifier.isStatic(this.mLoader.getClass().getModifiers())) {
                    throw new java.lang.IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.mLoader);
                }
                if (!this.mListenerRegistered) {
                    this.mLoader.registerListener(this.mId, this);
                    this.mLoader.registerOnLoadCanceledListener(this);
                    this.mListenerRegistered = true;
                }
                this.mLoader.startLoading();
            }
        }

        void retain() {
            if (android.app.LoaderManagerImpl.DEBUG) {
                android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Retaining: " + this);
            }
            this.mRetaining = true;
            this.mRetainingStarted = this.mStarted;
            this.mStarted = false;
            this.mCallbacks = null;
        }

        void finishRetain() {
            if (this.mRetaining) {
                if (android.app.LoaderManagerImpl.DEBUG) {
                    android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Finished Retaining: " + this);
                }
                this.mRetaining = false;
                if (this.mStarted != this.mRetainingStarted && !this.mStarted) {
                    stop();
                }
            }
            if (this.mStarted && this.mHaveData && !this.mReportNextStart) {
                callOnLoadFinished(this.mLoader, this.mData);
            }
        }

        void reportStart() {
            if (this.mStarted && this.mReportNextStart) {
                this.mReportNextStart = false;
                if (this.mHaveData && !this.mRetaining) {
                    callOnLoadFinished(this.mLoader, this.mData);
                }
            }
        }

        void stop() {
            if (android.app.LoaderManagerImpl.DEBUG) {
                android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Stopping: " + this);
            }
            this.mStarted = false;
            if (!this.mRetaining && this.mLoader != null && this.mListenerRegistered) {
                this.mListenerRegistered = false;
                this.mLoader.unregisterListener(this);
                this.mLoader.unregisterOnLoadCanceledListener(this);
                this.mLoader.stopLoading();
            }
        }

        boolean cancel() {
            if (android.app.LoaderManagerImpl.DEBUG) {
                android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Canceling: " + this);
            }
            if (this.mStarted && this.mLoader != null && this.mListenerRegistered) {
                boolean cancelLoad = this.mLoader.cancelLoad();
                if (!cancelLoad) {
                    onLoadCanceled(this.mLoader);
                }
                return cancelLoad;
            }
            return false;
        }

        void destroy() {
            java.lang.String str;
            if (android.app.LoaderManagerImpl.DEBUG) {
                android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Destroying: " + this);
            }
            this.mDestroyed = true;
            boolean z = this.mDeliveredData;
            this.mDeliveredData = false;
            if (this.mCallbacks != null && this.mLoader != null && this.mHaveData && z) {
                if (android.app.LoaderManagerImpl.DEBUG) {
                    android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Reseting: " + this);
                }
                if (android.app.LoaderManagerImpl.this.mHost == null) {
                    str = null;
                } else {
                    str = android.app.LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause;
                    android.app.LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = "onLoaderReset";
                }
                try {
                    this.mCallbacks.onLoaderReset(this.mLoader);
                } finally {
                    if (android.app.LoaderManagerImpl.this.mHost != null) {
                        android.app.LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = str;
                    }
                }
            }
            this.mCallbacks = null;
            this.mData = null;
            this.mHaveData = false;
            if (this.mLoader != null) {
                if (this.mListenerRegistered) {
                    this.mListenerRegistered = false;
                    this.mLoader.unregisterListener(this);
                    this.mLoader.unregisterOnLoadCanceledListener(this);
                }
                this.mLoader.reset();
            }
            if (this.mPendingLoader != null) {
                this.mPendingLoader.destroy();
            }
        }

        @Override // android.content.Loader.OnLoadCanceledListener
        public void onLoadCanceled(android.content.Loader<java.lang.Object> loader) {
            if (android.app.LoaderManagerImpl.DEBUG) {
                android.util.Log.v(android.app.LoaderManagerImpl.TAG, "onLoadCanceled: " + this);
            }
            if (this.mDestroyed) {
                if (android.app.LoaderManagerImpl.DEBUG) {
                    android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Ignoring load canceled -- destroyed");
                }
            } else {
                if (android.app.LoaderManagerImpl.this.mLoaders.get(this.mId) != this) {
                    if (android.app.LoaderManagerImpl.DEBUG) {
                        android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Ignoring load canceled -- not active");
                        return;
                    }
                    return;
                }
                android.app.LoaderManagerImpl.LoaderInfo loaderInfo = this.mPendingLoader;
                if (loaderInfo != null) {
                    if (android.app.LoaderManagerImpl.DEBUG) {
                        android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Switching to pending loader: " + loaderInfo);
                    }
                    this.mPendingLoader = null;
                    android.app.LoaderManagerImpl.this.mLoaders.put(this.mId, null);
                    destroy();
                    android.app.LoaderManagerImpl.this.installLoader(loaderInfo);
                }
            }
        }

        @Override // android.content.Loader.OnLoadCompleteListener
        public void onLoadComplete(android.content.Loader<java.lang.Object> loader, java.lang.Object obj) {
            if (android.app.LoaderManagerImpl.DEBUG) {
                android.util.Log.v(android.app.LoaderManagerImpl.TAG, "onLoadComplete: " + this);
            }
            if (this.mDestroyed) {
                if (android.app.LoaderManagerImpl.DEBUG) {
                    android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Ignoring load complete -- destroyed");
                    return;
                }
                return;
            }
            if (android.app.LoaderManagerImpl.this.mLoaders.get(this.mId) != this) {
                if (android.app.LoaderManagerImpl.DEBUG) {
                    android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Ignoring load complete -- not active");
                    return;
                }
                return;
            }
            android.app.LoaderManagerImpl.LoaderInfo loaderInfo = this.mPendingLoader;
            if (loaderInfo != null) {
                if (android.app.LoaderManagerImpl.DEBUG) {
                    android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  Switching to pending loader: " + loaderInfo);
                }
                this.mPendingLoader = null;
                android.app.LoaderManagerImpl.this.mLoaders.put(this.mId, null);
                destroy();
                android.app.LoaderManagerImpl.this.installLoader(loaderInfo);
                return;
            }
            if (this.mData != obj || !this.mHaveData) {
                this.mData = obj;
                this.mHaveData = true;
                if (this.mStarted) {
                    callOnLoadFinished(loader, obj);
                }
            }
            android.app.LoaderManagerImpl.LoaderInfo loaderInfo2 = android.app.LoaderManagerImpl.this.mInactiveLoaders.get(this.mId);
            if (loaderInfo2 != null && loaderInfo2 != this) {
                loaderInfo2.mDeliveredData = false;
                loaderInfo2.destroy();
                android.app.LoaderManagerImpl.this.mInactiveLoaders.remove(this.mId);
            }
            if (android.app.LoaderManagerImpl.this.mHost != null && !android.app.LoaderManagerImpl.this.hasRunningLoaders()) {
                android.app.LoaderManagerImpl.this.mHost.mFragmentManager.startPendingDeferredFragments();
            }
        }

        void callOnLoadFinished(android.content.Loader<java.lang.Object> loader, java.lang.Object obj) {
            java.lang.String str;
            if (this.mCallbacks != null) {
                if (android.app.LoaderManagerImpl.this.mHost == null) {
                    str = null;
                } else {
                    str = android.app.LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause;
                    android.app.LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = "onLoadFinished";
                }
                try {
                    if (android.app.LoaderManagerImpl.DEBUG) {
                        android.util.Log.v(android.app.LoaderManagerImpl.TAG, "  onLoadFinished in " + loader + ": " + loader.dataToString(obj));
                    }
                    this.mCallbacks.onLoadFinished(loader, obj);
                    this.mDeliveredData = true;
                } finally {
                    if (android.app.LoaderManagerImpl.this.mHost != null) {
                        android.app.LoaderManagerImpl.this.mHost.mFragmentManager.mNoTransactionsBecause = str;
                    }
                }
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
            sb.append("LoaderInfo{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(" #");
            sb.append(this.mId);
            sb.append(" : ");
            android.util.DebugUtils.buildShortClassTag(this.mLoader, sb);
            sb.append("}}");
            return sb.toString();
        }

        public void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            printWriter.print(str);
            printWriter.print("mId=");
            printWriter.print(this.mId);
            printWriter.print(" mArgs=");
            printWriter.println(this.mArgs);
            printWriter.print(str);
            printWriter.print("mCallbacks=");
            printWriter.println(this.mCallbacks);
            printWriter.print(str);
            printWriter.print("mLoader=");
            printWriter.println(this.mLoader);
            if (this.mLoader != null) {
                this.mLoader.dump(str + "  ", fileDescriptor, printWriter, strArr);
            }
            if (this.mHaveData || this.mDeliveredData) {
                printWriter.print(str);
                printWriter.print("mHaveData=");
                printWriter.print(this.mHaveData);
                printWriter.print("  mDeliveredData=");
                printWriter.println(this.mDeliveredData);
                printWriter.print(str);
                printWriter.print("mData=");
                printWriter.println(this.mData);
            }
            printWriter.print(str);
            printWriter.print("mStarted=");
            printWriter.print(this.mStarted);
            printWriter.print(" mReportNextStart=");
            printWriter.print(this.mReportNextStart);
            printWriter.print(" mDestroyed=");
            printWriter.println(this.mDestroyed);
            printWriter.print(str);
            printWriter.print("mRetaining=");
            printWriter.print(this.mRetaining);
            printWriter.print(" mRetainingStarted=");
            printWriter.print(this.mRetainingStarted);
            printWriter.print(" mListenerRegistered=");
            printWriter.println(this.mListenerRegistered);
            if (this.mPendingLoader != null) {
                printWriter.print(str);
                printWriter.println("Pending Loader ");
                printWriter.print(this.mPendingLoader);
                printWriter.println(":");
                this.mPendingLoader.dump(str + "  ", fileDescriptor, printWriter, strArr);
            }
        }
    }

    LoaderManagerImpl(java.lang.String str, android.app.FragmentHostCallback fragmentHostCallback, boolean z) {
        this.mWho = str;
        this.mHost = fragmentHostCallback;
        this.mStarted = z;
    }

    void updateHostController(android.app.FragmentHostCallback fragmentHostCallback) {
        this.mHost = fragmentHostCallback;
    }

    @Override // android.app.LoaderManager
    public android.app.FragmentHostCallback getFragmentHostCallback() {
        return this.mHost;
    }

    private android.app.LoaderManagerImpl.LoaderInfo createLoader(int i, android.os.Bundle bundle, android.app.LoaderManager.LoaderCallbacks<java.lang.Object> loaderCallbacks) {
        android.app.LoaderManagerImpl.LoaderInfo loaderInfo = new android.app.LoaderManagerImpl.LoaderInfo(i, bundle, loaderCallbacks);
        loaderInfo.mLoader = loaderCallbacks.onCreateLoader(i, bundle);
        return loaderInfo;
    }

    private android.app.LoaderManagerImpl.LoaderInfo createAndInstallLoader(int i, android.os.Bundle bundle, android.app.LoaderManager.LoaderCallbacks<java.lang.Object> loaderCallbacks) {
        try {
            this.mCreatingLoader = true;
            android.app.LoaderManagerImpl.LoaderInfo createLoader = createLoader(i, bundle, loaderCallbacks);
            installLoader(createLoader);
            return createLoader;
        } finally {
            this.mCreatingLoader = false;
        }
    }

    void installLoader(android.app.LoaderManagerImpl.LoaderInfo loaderInfo) {
        this.mLoaders.put(loaderInfo.mId, loaderInfo);
        if (this.mStarted) {
            loaderInfo.start();
        }
    }

    @Override // android.app.LoaderManager
    public <D> android.content.Loader<D> initLoader(int i, android.os.Bundle bundle, android.app.LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        if (this.mCreatingLoader) {
            throw new java.lang.IllegalStateException("Called while creating a loader");
        }
        android.app.LoaderManagerImpl.LoaderInfo loaderInfo = this.mLoaders.get(i);
        if (DEBUG) {
            android.util.Log.v(TAG, "initLoader in " + this + ": args=" + bundle);
        }
        if (loaderInfo == null) {
            loaderInfo = createAndInstallLoader(i, bundle, loaderCallbacks);
            if (DEBUG) {
                android.util.Log.v(TAG, "  Created new loader " + loaderInfo);
            }
        } else {
            if (DEBUG) {
                android.util.Log.v(TAG, "  Re-using existing loader " + loaderInfo);
            }
            loaderInfo.mCallbacks = loaderCallbacks;
        }
        if (loaderInfo.mHaveData && this.mStarted) {
            loaderInfo.callOnLoadFinished(loaderInfo.mLoader, loaderInfo.mData);
        }
        return (android.content.Loader<D>) loaderInfo.mLoader;
    }

    @Override // android.app.LoaderManager
    public <D> android.content.Loader<D> restartLoader(int i, android.os.Bundle bundle, android.app.LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        if (this.mCreatingLoader) {
            throw new java.lang.IllegalStateException("Called while creating a loader");
        }
        android.app.LoaderManagerImpl.LoaderInfo loaderInfo = this.mLoaders.get(i);
        if (DEBUG) {
            android.util.Log.v(TAG, "restartLoader in " + this + ": args=" + bundle);
        }
        if (loaderInfo != null) {
            android.app.LoaderManagerImpl.LoaderInfo loaderInfo2 = this.mInactiveLoaders.get(i);
            if (loaderInfo2 != null) {
                if (loaderInfo.mHaveData) {
                    if (DEBUG) {
                        android.util.Log.v(TAG, "  Removing last inactive loader: " + loaderInfo);
                    }
                    loaderInfo2.mDeliveredData = false;
                    loaderInfo2.destroy();
                    loaderInfo.mLoader.abandon();
                    this.mInactiveLoaders.put(i, loaderInfo);
                } else if (!loaderInfo.cancel()) {
                    if (DEBUG) {
                        android.util.Log.v(TAG, "  Current loader is stopped; replacing");
                    }
                    this.mLoaders.put(i, null);
                    loaderInfo.destroy();
                } else {
                    if (DEBUG) {
                        android.util.Log.v(TAG, "  Current loader is running; configuring pending loader");
                    }
                    if (loaderInfo.mPendingLoader != null) {
                        if (DEBUG) {
                            android.util.Log.v(TAG, "  Removing pending loader: " + loaderInfo.mPendingLoader);
                        }
                        loaderInfo.mPendingLoader.destroy();
                        loaderInfo.mPendingLoader = null;
                    }
                    if (DEBUG) {
                        android.util.Log.v(TAG, "  Enqueuing as new pending loader");
                    }
                    loaderInfo.mPendingLoader = createLoader(i, bundle, loaderCallbacks);
                    return (android.content.Loader<D>) loaderInfo.mPendingLoader.mLoader;
                }
            } else {
                if (DEBUG) {
                    android.util.Log.v(TAG, "  Making last loader inactive: " + loaderInfo);
                }
                loaderInfo.mLoader.abandon();
                this.mInactiveLoaders.put(i, loaderInfo);
            }
        }
        return (android.content.Loader<D>) createAndInstallLoader(i, bundle, loaderCallbacks).mLoader;
    }

    @Override // android.app.LoaderManager
    public void destroyLoader(int i) {
        if (this.mCreatingLoader) {
            throw new java.lang.IllegalStateException("Called while creating a loader");
        }
        if (DEBUG) {
            android.util.Log.v(TAG, "destroyLoader in " + this + " of " + i);
        }
        int indexOfKey = this.mLoaders.indexOfKey(i);
        if (indexOfKey >= 0) {
            android.app.LoaderManagerImpl.LoaderInfo valueAt = this.mLoaders.valueAt(indexOfKey);
            this.mLoaders.removeAt(indexOfKey);
            valueAt.destroy();
        }
        int indexOfKey2 = this.mInactiveLoaders.indexOfKey(i);
        if (indexOfKey2 >= 0) {
            android.app.LoaderManagerImpl.LoaderInfo valueAt2 = this.mInactiveLoaders.valueAt(indexOfKey2);
            this.mInactiveLoaders.removeAt(indexOfKey2);
            valueAt2.destroy();
        }
        if (this.mHost != null && !hasRunningLoaders()) {
            this.mHost.mFragmentManager.startPendingDeferredFragments();
        }
    }

    @Override // android.app.LoaderManager
    public <D> android.content.Loader<D> getLoader(int i) {
        if (this.mCreatingLoader) {
            throw new java.lang.IllegalStateException("Called while creating a loader");
        }
        android.app.LoaderManagerImpl.LoaderInfo loaderInfo = this.mLoaders.get(i);
        if (loaderInfo != null) {
            if (loaderInfo.mPendingLoader != null) {
                return (android.content.Loader<D>) loaderInfo.mPendingLoader.mLoader;
            }
            return (android.content.Loader<D>) loaderInfo.mLoader;
        }
        return null;
    }

    void doStart() {
        if (DEBUG) {
            android.util.Log.v(TAG, "Starting in " + this);
        }
        if (this.mStarted) {
            java.lang.RuntimeException runtimeException = new java.lang.RuntimeException("here");
            runtimeException.fillInStackTrace();
            android.util.Log.w(TAG, "Called doStart when already started: " + this, runtimeException);
        } else {
            this.mStarted = true;
            for (int size = this.mLoaders.size() - 1; size >= 0; size--) {
                this.mLoaders.valueAt(size).start();
            }
        }
    }

    void doStop() {
        if (DEBUG) {
            android.util.Log.v(TAG, "Stopping in " + this);
        }
        if (!this.mStarted) {
            java.lang.RuntimeException runtimeException = new java.lang.RuntimeException("here");
            runtimeException.fillInStackTrace();
            android.util.Log.w(TAG, "Called doStop when not started: " + this, runtimeException);
        } else {
            for (int size = this.mLoaders.size() - 1; size >= 0; size--) {
                this.mLoaders.valueAt(size).stop();
            }
            this.mStarted = false;
        }
    }

    void doRetain() {
        if (DEBUG) {
            android.util.Log.v(TAG, "Retaining in " + this);
        }
        if (!this.mStarted) {
            java.lang.RuntimeException runtimeException = new java.lang.RuntimeException("here");
            runtimeException.fillInStackTrace();
            android.util.Log.w(TAG, "Called doRetain when not started: " + this, runtimeException);
        } else {
            this.mRetaining = true;
            this.mStarted = false;
            for (int size = this.mLoaders.size() - 1; size >= 0; size--) {
                this.mLoaders.valueAt(size).retain();
            }
        }
    }

    void finishRetain() {
        if (this.mRetaining) {
            if (DEBUG) {
                android.util.Log.v(TAG, "Finished Retaining in " + this);
            }
            this.mRetaining = false;
            for (int size = this.mLoaders.size() - 1; size >= 0; size--) {
                this.mLoaders.valueAt(size).finishRetain();
            }
        }
    }

    void doReportNextStart() {
        for (int size = this.mLoaders.size() - 1; size >= 0; size--) {
            this.mLoaders.valueAt(size).mReportNextStart = true;
        }
    }

    void doReportStart() {
        for (int size = this.mLoaders.size() - 1; size >= 0; size--) {
            this.mLoaders.valueAt(size).reportStart();
        }
    }

    void doDestroy() {
        if (!this.mRetaining) {
            if (DEBUG) {
                android.util.Log.v(TAG, "Destroying Active in " + this);
            }
            for (int size = this.mLoaders.size() - 1; size >= 0; size--) {
                this.mLoaders.valueAt(size).destroy();
            }
            this.mLoaders.clear();
        }
        if (DEBUG) {
            android.util.Log.v(TAG, "Destroying Inactive in " + this);
        }
        for (int size2 = this.mInactiveLoaders.size() - 1; size2 >= 0; size2--) {
            this.mInactiveLoaders.valueAt(size2).destroy();
        }
        this.mInactiveLoaders.clear();
        this.mHost = null;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(" in ");
        android.util.DebugUtils.buildShortClassTag(this.mHost, sb);
        sb.append("}}");
        return sb.toString();
    }

    @Override // android.app.LoaderManager
    public void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (this.mLoaders.size() > 0) {
            printWriter.print(str);
            printWriter.println("Active Loaders:");
            java.lang.String str2 = str + "    ";
            for (int i = 0; i < this.mLoaders.size(); i++) {
                android.app.LoaderManagerImpl.LoaderInfo valueAt = this.mLoaders.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.mLoaders.keyAt(i));
                printWriter.print(": ");
                printWriter.println(valueAt.toString());
                valueAt.dump(str2, fileDescriptor, printWriter, strArr);
            }
        }
        if (this.mInactiveLoaders.size() > 0) {
            printWriter.print(str);
            printWriter.println("Inactive Loaders:");
            java.lang.String str3 = str + "    ";
            for (int i2 = 0; i2 < this.mInactiveLoaders.size(); i2++) {
                android.app.LoaderManagerImpl.LoaderInfo valueAt2 = this.mInactiveLoaders.valueAt(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.mInactiveLoaders.keyAt(i2));
                printWriter.print(": ");
                printWriter.println(valueAt2.toString());
                valueAt2.dump(str3, fileDescriptor, printWriter, strArr);
            }
        }
    }

    public boolean hasRunningLoaders() {
        int size = this.mLoaders.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            android.app.LoaderManagerImpl.LoaderInfo valueAt = this.mLoaders.valueAt(i);
            z |= valueAt.mStarted && !valueAt.mDeliveredData;
        }
        return z;
    }
}
