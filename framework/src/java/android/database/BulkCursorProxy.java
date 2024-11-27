package android.database;

/* compiled from: BulkCursorNative.java */
/* loaded from: classes.dex */
final class BulkCursorProxy implements android.database.IBulkCursor {
    private android.os.Bundle mExtras = null;
    private android.os.IBinder mRemote;

    public BulkCursorProxy(android.os.IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public android.os.IBinder asBinder() {
        return this.mRemote;
    }

    @Override // android.database.IBulkCursor
    public android.database.CursorWindow getWindow(int i) throws android.os.RemoteException {
        android.database.CursorWindow cursorWindow;
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.database.IBulkCursor.descriptor);
            obtain.writeInt(i);
            this.mRemote.transact(1, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            if (obtain2.readInt() != 1) {
                cursorWindow = null;
            } else {
                cursorWindow = android.database.CursorWindow.newFromParcel(obtain2);
            }
            return cursorWindow;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.database.IBulkCursor
    public void onMove(int i) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.database.IBulkCursor.descriptor);
            obtain.writeInt(i);
            this.mRemote.transact(4, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.database.IBulkCursor
    public void deactivate() throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.database.IBulkCursor.descriptor);
            this.mRemote.transact(2, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.database.IBulkCursor
    public void close() throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.database.IBulkCursor.descriptor);
            this.mRemote.transact(7, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.database.IBulkCursor
    public int requery(android.database.IContentObserver iContentObserver) throws android.os.RemoteException {
        int readInt;
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.database.IBulkCursor.descriptor);
            obtain.writeStrongInterface(iContentObserver);
            boolean transact = this.mRemote.transact(3, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            if (!transact) {
                readInt = -1;
            } else {
                readInt = obtain2.readInt();
                this.mExtras = obtain2.readBundle();
            }
            return readInt;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.database.IBulkCursor
    public android.os.Bundle getExtras() throws android.os.RemoteException {
        if (this.mExtras == null) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            android.os.Parcel obtain2 = android.os.Parcel.obtain();
            try {
                obtain.writeInterfaceToken(android.database.IBulkCursor.descriptor);
                this.mRemote.transact(5, obtain, obtain2, 0);
                android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
                this.mExtras = obtain2.readBundle();
            } finally {
                obtain.recycle();
                obtain2.recycle();
            }
        }
        return this.mExtras;
    }

    @Override // android.database.IBulkCursor
    public android.os.Bundle respond(android.os.Bundle bundle) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.database.IBulkCursor.descriptor);
            obtain.writeBundle(bundle);
            this.mRemote.transact(6, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readBundle();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }
}
