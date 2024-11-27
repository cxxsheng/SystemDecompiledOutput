package android.database;

/* loaded from: classes.dex */
public abstract class BulkCursorNative extends android.os.Binder implements android.database.IBulkCursor {
    public BulkCursorNative() {
        attachInterface(this, android.database.IBulkCursor.descriptor);
    }

    public static android.database.IBulkCursor asInterface(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        android.database.IBulkCursor iBulkCursor = (android.database.IBulkCursor) iBinder.queryLocalInterface(android.database.IBulkCursor.descriptor);
        if (iBulkCursor != null) {
            return iBulkCursor;
        }
        return new android.database.BulkCursorProxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        try {
            switch (i) {
                case 1:
                    parcel.enforceInterface(android.database.IBulkCursor.descriptor);
                    android.database.CursorWindow window = getWindow(parcel.readInt());
                    parcel2.writeNoException();
                    if (window == null) {
                        parcel2.writeInt(0);
                        break;
                    } else {
                        parcel2.writeInt(1);
                        window.writeToParcel(parcel2, 1);
                        break;
                    }
                case 2:
                    parcel.enforceInterface(android.database.IBulkCursor.descriptor);
                    deactivate();
                    parcel2.writeNoException();
                    break;
                case 3:
                    parcel.enforceInterface(android.database.IBulkCursor.descriptor);
                    int requery = requery(android.database.IContentObserver.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(requery);
                    parcel2.writeBundle(getExtras());
                    break;
                case 4:
                    parcel.enforceInterface(android.database.IBulkCursor.descriptor);
                    onMove(parcel.readInt());
                    parcel2.writeNoException();
                    break;
                case 5:
                    parcel.enforceInterface(android.database.IBulkCursor.descriptor);
                    android.os.Bundle extras = getExtras();
                    parcel2.writeNoException();
                    parcel2.writeBundle(extras);
                    break;
                case 6:
                    parcel.enforceInterface(android.database.IBulkCursor.descriptor);
                    android.os.Bundle respond = respond(parcel.readBundle());
                    parcel2.writeNoException();
                    parcel2.writeBundle(respond);
                    break;
                case 7:
                    parcel.enforceInterface(android.database.IBulkCursor.descriptor);
                    close();
                    parcel2.writeNoException();
                    break;
            }
            return true;
        } catch (java.lang.Exception e) {
            android.database.DatabaseUtils.writeExceptionToParcel(parcel2, e);
            return true;
        }
    }

    @Override // android.os.IInterface
    public android.os.IBinder asBinder() {
        return this;
    }
}
