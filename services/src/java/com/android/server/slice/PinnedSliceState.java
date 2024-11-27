package com.android.server.slice;

/* loaded from: classes2.dex */
public class PinnedSliceState {
    private static final long SLICE_TIMEOUT = 5000;
    private static final java.lang.String TAG = "PinnedSliceState";
    private final java.lang.Object mLock;
    private final java.lang.String mPkg;
    private final com.android.server.slice.SliceManagerService mService;
    private boolean mSlicePinned;
    private final android.net.Uri mUri;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<java.lang.String> mPinnedPkgs = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.slice.PinnedSliceState.ListenerInfo> mListeners = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.app.slice.SliceSpec[] mSupportedSpecs = null;
    private final android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.slice.PinnedSliceState$$ExternalSyntheticLambda0
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            com.android.server.slice.PinnedSliceState.this.handleRecheckListeners();
        }
    };

    public PinnedSliceState(com.android.server.slice.SliceManagerService sliceManagerService, android.net.Uri uri, java.lang.String str) {
        this.mService = sliceManagerService;
        this.mUri = uri;
        this.mPkg = str;
        this.mLock = this.mService.getLock();
    }

    public java.lang.String getPkg() {
        return this.mPkg;
    }

    public android.app.slice.SliceSpec[] getSpecs() {
        return this.mSupportedSpecs;
    }

    public void mergeSpecs(final android.app.slice.SliceSpec[] sliceSpecArr) {
        synchronized (this.mLock) {
            try {
                if (this.mSupportedSpecs == null) {
                    this.mSupportedSpecs = sliceSpecArr;
                } else {
                    this.mSupportedSpecs = (android.app.slice.SliceSpec[]) java.util.Arrays.asList(this.mSupportedSpecs).stream().map(new java.util.function.Function() { // from class: com.android.server.slice.PinnedSliceState$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            android.app.slice.SliceSpec lambda$mergeSpecs$0;
                            lambda$mergeSpecs$0 = com.android.server.slice.PinnedSliceState.this.lambda$mergeSpecs$0(sliceSpecArr, (android.app.slice.SliceSpec) obj);
                            return lambda$mergeSpecs$0;
                        }
                    }).filter(new java.util.function.Predicate() { // from class: com.android.server.slice.PinnedSliceState$$ExternalSyntheticLambda4
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$mergeSpecs$1;
                            lambda$mergeSpecs$1 = com.android.server.slice.PinnedSliceState.lambda$mergeSpecs$1((android.app.slice.SliceSpec) obj);
                            return lambda$mergeSpecs$1;
                        }
                    }).toArray(new java.util.function.IntFunction() { // from class: com.android.server.slice.PinnedSliceState$$ExternalSyntheticLambda5
                        @Override // java.util.function.IntFunction
                        public final java.lang.Object apply(int i) {
                            android.app.slice.SliceSpec[] lambda$mergeSpecs$2;
                            lambda$mergeSpecs$2 = com.android.server.slice.PinnedSliceState.lambda$mergeSpecs$2(i);
                            return lambda$mergeSpecs$2;
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.app.slice.SliceSpec lambda$mergeSpecs$0(android.app.slice.SliceSpec[] sliceSpecArr, android.app.slice.SliceSpec sliceSpec) {
        android.app.slice.SliceSpec findSpec = findSpec(sliceSpecArr, sliceSpec.getType());
        if (findSpec == null) {
            return null;
        }
        if (findSpec.getRevision() < sliceSpec.getRevision()) {
            return findSpec;
        }
        return sliceSpec;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$mergeSpecs$1(android.app.slice.SliceSpec sliceSpec) {
        return sliceSpec != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.app.slice.SliceSpec[] lambda$mergeSpecs$2(int i) {
        return new android.app.slice.SliceSpec[i];
    }

    private android.app.slice.SliceSpec findSpec(android.app.slice.SliceSpec[] sliceSpecArr, java.lang.String str) {
        for (android.app.slice.SliceSpec sliceSpec : sliceSpecArr) {
            if (java.util.Objects.equals(sliceSpec.getType(), str)) {
                return sliceSpec;
            }
        }
        return null;
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    public void destroy() {
        setSlicePinned(false);
    }

    private void setSlicePinned(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mSlicePinned == z) {
                    return;
                }
                this.mSlicePinned = z;
                if (z) {
                    this.mService.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.slice.PinnedSliceState$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.slice.PinnedSliceState.this.handleSendPinned();
                        }
                    });
                } else {
                    this.mService.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.slice.PinnedSliceState$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.slice.PinnedSliceState.this.handleSendUnpinned();
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void pin(java.lang.String str, android.app.slice.SliceSpec[] sliceSpecArr, android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            this.mListeners.put(iBinder, new com.android.server.slice.PinnedSliceState.ListenerInfo(iBinder, str, true, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid()));
            try {
                iBinder.linkToDeath(this.mDeathRecipient, 0);
            } catch (android.os.RemoteException e) {
            }
            mergeSpecs(sliceSpecArr);
            setSlicePinned(true);
        }
    }

    public boolean unpin(java.lang.String str, android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            iBinder.unlinkToDeath(this.mDeathRecipient, 0);
            this.mListeners.remove(iBinder);
        }
        return !hasPinOrListener();
    }

    public boolean isListening() {
        boolean z;
        synchronized (this.mLock) {
            z = !this.mListeners.isEmpty();
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean hasPinOrListener() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = (this.mPinnedPkgs.isEmpty() && this.mListeners.isEmpty()) ? false : true;
            } finally {
            }
        }
        return z;
    }

    android.content.ContentProviderClient getClient() {
        android.content.ContentProviderClient acquireUnstableContentProviderClient = this.mService.getContext().getContentResolver().acquireUnstableContentProviderClient(this.mUri);
        if (acquireUnstableContentProviderClient == null) {
            return null;
        }
        acquireUnstableContentProviderClient.setDetectNotResponding(SLICE_TIMEOUT);
        return acquireUnstableContentProviderClient;
    }

    private void checkSelfRemove() {
        if (!hasPinOrListener()) {
            this.mService.removePinnedSlice(this.mUri);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRecheckListeners() {
        if (hasPinOrListener()) {
            synchronized (this.mLock) {
                try {
                    for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                        if (!this.mListeners.valueAt(size).token.isBinderAlive()) {
                            this.mListeners.removeAt(size);
                        }
                    }
                    checkSelfRemove();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSendPinned() {
        android.content.ContentProviderClient client = getClient();
        if (client != null) {
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putParcelable("slice_uri", this.mUri);
                try {
                    client.call("pin", null, bundle);
                } catch (java.lang.Exception e) {
                    android.util.Log.w(TAG, "Unable to contact " + this.mUri, e);
                }
                client.close();
                return;
            } catch (java.lang.Throwable th) {
                try {
                    client.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (client != null) {
            client.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSendUnpinned() {
        android.content.ContentProviderClient client = getClient();
        if (client != null) {
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putParcelable("slice_uri", this.mUri);
                try {
                    client.call("unpin", null, bundle);
                } catch (java.lang.Exception e) {
                    android.util.Log.w(TAG, "Unable to contact " + this.mUri, e);
                }
                client.close();
                return;
            } catch (java.lang.Throwable th) {
                try {
                    client.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (client != null) {
            client.close();
        }
    }

    private class ListenerInfo {
        private int callingPid;
        private int callingUid;
        private boolean hasPermission;
        private java.lang.String pkg;
        private android.os.IBinder token;

        public ListenerInfo(android.os.IBinder iBinder, java.lang.String str, boolean z, int i, int i2) {
            this.token = iBinder;
            this.pkg = str;
            this.hasPermission = z;
            this.callingUid = i;
            this.callingPid = i2;
        }
    }
}
