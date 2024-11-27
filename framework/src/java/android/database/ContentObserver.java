package android.database;

/* loaded from: classes.dex */
public abstract class ContentObserver {
    private static final long ADD_CONTENT_OBSERVER_FLAGS = 150939131;
    private final java.util.concurrent.Executor mExecutor;
    android.os.Handler mHandler;
    private final java.lang.Object mLock;
    private android.database.ContentObserver.Transport mTransport;

    public ContentObserver(android.os.Handler handler) {
        this.mLock = new java.lang.Object();
        this.mHandler = handler;
        this.mExecutor = null;
    }

    public ContentObserver(java.util.concurrent.Executor executor, int i) {
        this.mLock = new java.lang.Object();
        this.mExecutor = executor;
    }

    public android.database.IContentObserver getContentObserver() {
        android.database.ContentObserver.Transport transport;
        synchronized (this.mLock) {
            if (this.mTransport == null) {
                this.mTransport = new android.database.ContentObserver.Transport(this);
            }
            transport = this.mTransport;
        }
        return transport;
    }

    public android.database.IContentObserver releaseContentObserver() {
        android.database.ContentObserver.Transport transport;
        synchronized (this.mLock) {
            transport = this.mTransport;
            if (transport != null) {
                transport.releaseContentObserver();
                this.mTransport = null;
            }
        }
        return transport;
    }

    public boolean deliverSelfNotifications() {
        return false;
    }

    public void onChange(boolean z) {
    }

    public void onChange(boolean z, android.net.Uri uri) {
        onChange(z);
    }

    public void onChange(boolean z, android.net.Uri uri, int i) {
        onChange(z, uri);
    }

    public void onChange(boolean z, java.util.Collection<android.net.Uri> collection, int i) {
        java.util.Iterator<android.net.Uri> it = collection.iterator();
        while (it.hasNext()) {
            onChange(z, it.next(), i);
        }
    }

    @android.annotation.SystemApi
    public void onChange(boolean z, java.util.Collection<android.net.Uri> collection, int i, android.os.UserHandle userHandle) {
        onChange(z, collection, userHandle.getIdentifier());
    }

    public void onChange(boolean z, java.util.Collection<android.net.Uri> collection, int i, int i2) {
        if (!isChangeEnabledAddContentObserverFlags() || android.os.Process.myUid() == 1000) {
            onChange(z, collection, i, android.os.UserHandle.of(i2));
        } else {
            onChange(z, collection, i);
        }
    }

    private static boolean isChangeEnabledAddContentObserverFlags() {
        return android.app.compat.CompatChanges.isChangeEnabled(ADD_CONTENT_OBSERVER_FLAGS);
    }

    private static boolean isChangeEnabledAddContentObserverFlags$ravenwood() {
        return true;
    }

    @java.lang.Deprecated
    public final void dispatchChange(boolean z) {
        dispatchChange(z, null);
    }

    public final void dispatchChange(boolean z, android.net.Uri uri) {
        dispatchChange(z, uri, 0);
    }

    public final void dispatchChange(boolean z, android.net.Uri uri, int i) {
        dispatchChange(z, java.util.Arrays.asList(uri), i);
    }

    public final void dispatchChange(boolean z, java.util.Collection<android.net.Uri> collection, int i) {
        dispatchChange(z, collection, i, android.os.UserHandle.getCallingUserId());
    }

    public final void dispatchChange(final boolean z, final java.util.Collection<android.net.Uri> collection, final int i, final int i2) {
        if (this.mExecutor != null) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.database.ContentObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.database.ContentObserver.this.lambda$dispatchChange$0(z, collection, i, i2);
                }
            });
        } else if (this.mHandler != null) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.database.ContentObserver$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.database.ContentObserver.this.lambda$dispatchChange$1(z, collection, i, i2);
                }
            });
        } else {
            onChange(z, collection, i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchChange$0(boolean z, java.util.Collection collection, int i, int i2) {
        onChange(z, (java.util.Collection<android.net.Uri>) collection, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchChange$1(boolean z, java.util.Collection collection, int i, int i2) {
        onChange(z, (java.util.Collection<android.net.Uri>) collection, i, i2);
    }

    private static final class Transport extends android.database.IContentObserver.Stub {
        private android.database.ContentObserver mContentObserver;

        public Transport(android.database.ContentObserver contentObserver) {
            this.mContentObserver = contentObserver;
        }

        @Override // android.database.IContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            onChangeEtc(z, new android.net.Uri[]{uri}, 0, i);
        }

        @Override // android.database.IContentObserver
        public void onChangeEtc(boolean z, android.net.Uri[] uriArr, int i, int i2) {
            android.database.ContentObserver contentObserver = this.mContentObserver;
            if (contentObserver != null) {
                contentObserver.dispatchChange(z, java.util.Arrays.asList(uriArr), i, i2);
            }
        }

        public void releaseContentObserver() {
            this.mContentObserver = null;
        }
    }
}
