package android.database;

/* loaded from: classes.dex */
public final class CursorToBulkCursorAdaptor extends android.database.BulkCursorNative implements android.os.IBinder.DeathRecipient {
    private static final java.lang.String TAG = "Cursor";
    private android.database.CrossProcessCursor mCursor;
    private android.database.CursorWindow mFilledWindow;
    private final java.lang.Object mLock = new java.lang.Object();
    private android.database.CursorToBulkCursorAdaptor.ContentObserverProxy mObserver;
    private final java.lang.String mProviderName;

    private static final class ContentObserverProxy extends android.database.ContentObserver {
        protected android.database.IContentObserver mRemote;

        public ContentObserverProxy(android.database.IContentObserver iContentObserver, android.os.IBinder.DeathRecipient deathRecipient) {
            super(null);
            this.mRemote = iContentObserver;
            try {
                iContentObserver.asBinder().linkToDeath(deathRecipient, 0);
            } catch (android.os.RemoteException e) {
            }
        }

        public boolean unlinkToDeath(android.os.IBinder.DeathRecipient deathRecipient) {
            return this.mRemote.asBinder().unlinkToDeath(deathRecipient, 0);
        }

        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return false;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, java.util.Collection<android.net.Uri> collection, int i, int i2) {
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Objects.requireNonNull(arrayList);
            collection.forEach(new java.util.function.Consumer() { // from class: android.database.CursorToBulkCursorAdaptor$ContentObserverProxy$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    arrayList.add((android.net.Uri) obj);
                }
            });
            try {
                this.mRemote.onChangeEtc(z, (android.net.Uri[]) arrayList.toArray(new android.net.Uri[arrayList.size()]), i, i2);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public CursorToBulkCursorAdaptor(android.database.Cursor cursor, android.database.IContentObserver iContentObserver, java.lang.String str) {
        if (cursor instanceof android.database.CrossProcessCursor) {
            this.mCursor = (android.database.CrossProcessCursor) cursor;
        } else {
            this.mCursor = new android.database.CrossProcessCursorWrapper(cursor);
        }
        this.mProviderName = str;
        synchronized (this.mLock) {
            createAndRegisterObserverProxyLocked(iContentObserver);
        }
    }

    private void closeFilledWindowLocked() {
        if (this.mFilledWindow != null) {
            this.mFilledWindow.close();
            this.mFilledWindow = null;
        }
    }

    private void disposeLocked() {
        if (this.mCursor != null) {
            unregisterObserverProxyLocked();
            this.mCursor.close();
            this.mCursor = null;
        }
        closeFilledWindowLocked();
    }

    private void throwIfCursorIsClosed() {
        if (this.mCursor == null) {
            throw new android.database.StaleDataException("Attempted to access a cursor after it has been closed.");
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        synchronized (this.mLock) {
            disposeLocked();
        }
    }

    public android.database.BulkCursorDescriptor getBulkCursorDescriptor() {
        android.database.BulkCursorDescriptor bulkCursorDescriptor;
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            bulkCursorDescriptor = new android.database.BulkCursorDescriptor();
            bulkCursorDescriptor.cursor = this;
            bulkCursorDescriptor.columnNames = this.mCursor.getColumnNames();
            bulkCursorDescriptor.wantsAllOnMoveCalls = this.mCursor.getWantsAllOnMoveCalls();
            bulkCursorDescriptor.count = this.mCursor.getCount();
            bulkCursorDescriptor.window = this.mCursor.getWindow();
            if (bulkCursorDescriptor.window != null) {
                bulkCursorDescriptor.window.acquireReference();
            }
        }
        return bulkCursorDescriptor;
    }

    @Override // android.database.IBulkCursor
    public android.database.CursorWindow getWindow(int i) {
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            if (!this.mCursor.moveToPosition(i)) {
                closeFilledWindowLocked();
                return null;
            }
            android.database.CursorWindow window = this.mCursor.getWindow();
            if (window != null) {
                closeFilledWindowLocked();
            } else {
                window = this.mFilledWindow;
                if (window == null) {
                    this.mFilledWindow = new android.database.CursorWindow(this.mProviderName);
                    window = this.mFilledWindow;
                } else if (i < window.getStartPosition() || i >= window.getStartPosition() + window.getNumRows()) {
                    window.clear();
                }
                this.mCursor.fillWindow(i, window);
            }
            if (window != null) {
                window.acquireReference();
            }
            return window;
        }
    }

    @Override // android.database.IBulkCursor
    public void onMove(int i) {
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            this.mCursor.onMove(this.mCursor.getPosition(), i);
        }
    }

    @Override // android.database.IBulkCursor
    public void deactivate() {
        synchronized (this.mLock) {
            if (this.mCursor != null) {
                unregisterObserverProxyLocked();
                this.mCursor.deactivate();
            }
            closeFilledWindowLocked();
        }
    }

    @Override // android.database.IBulkCursor
    public void close() {
        synchronized (this.mLock) {
            disposeLocked();
        }
    }

    @Override // android.database.IBulkCursor
    public int requery(android.database.IContentObserver iContentObserver) {
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            closeFilledWindowLocked();
            try {
                if (!this.mCursor.requery()) {
                    return -1;
                }
                unregisterObserverProxyLocked();
                createAndRegisterObserverProxyLocked(iContentObserver);
                return this.mCursor.getCount();
            } catch (java.lang.IllegalStateException e) {
                throw new java.lang.IllegalStateException(this.mProviderName + " Requery misuse db, mCursor isClosed:" + this.mCursor.isClosed(), e);
            }
        }
    }

    private void createAndRegisterObserverProxyLocked(android.database.IContentObserver iContentObserver) {
        if (this.mObserver != null) {
            throw new java.lang.IllegalStateException("an observer is already registered");
        }
        this.mObserver = new android.database.CursorToBulkCursorAdaptor.ContentObserverProxy(iContentObserver, this);
        this.mCursor.registerContentObserver(this.mObserver);
    }

    private void unregisterObserverProxyLocked() {
        if (this.mObserver != null) {
            this.mCursor.unregisterContentObserver(this.mObserver);
            this.mObserver.unlinkToDeath(this);
            this.mObserver = null;
        }
    }

    @Override // android.database.IBulkCursor
    public android.os.Bundle getExtras() {
        android.os.Bundle extras;
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            extras = this.mCursor.getExtras();
        }
        return extras;
    }

    @Override // android.database.IBulkCursor
    public android.os.Bundle respond(android.os.Bundle bundle) {
        android.os.Bundle respond;
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            respond = this.mCursor.respond(bundle);
        }
        return respond;
    }
}
