package android.service.quicksettings;

/* loaded from: classes3.dex */
public interface IQSService extends android.os.IInterface {
    android.service.quicksettings.Tile getTile(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean isLocked() throws android.os.RemoteException;

    boolean isSecure() throws android.os.RemoteException;

    void onDialogHidden(android.os.IBinder iBinder) throws android.os.RemoteException;

    void onShowDialog(android.os.IBinder iBinder) throws android.os.RemoteException;

    void onStartActivity(android.os.IBinder iBinder) throws android.os.RemoteException;

    void onStartSuccessful(android.os.IBinder iBinder) throws android.os.RemoteException;

    void startActivity(android.os.IBinder iBinder, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void startUnlockAndRun(android.os.IBinder iBinder) throws android.os.RemoteException;

    void updateQsTile(android.service.quicksettings.Tile tile, android.os.IBinder iBinder) throws android.os.RemoteException;

    void updateStatusIcon(android.os.IBinder iBinder, android.graphics.drawable.Icon icon, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.service.quicksettings.IQSService {
        @Override // android.service.quicksettings.IQSService
        public android.service.quicksettings.Tile getTile(android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.service.quicksettings.IQSService
        public void updateQsTile(android.service.quicksettings.Tile tile, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void updateStatusIcon(android.os.IBinder iBinder, android.graphics.drawable.Icon icon, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void onShowDialog(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void onStartActivity(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void startActivity(android.os.IBinder iBinder, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public boolean isLocked() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.quicksettings.IQSService
        public boolean isSecure() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.quicksettings.IQSService
        public void startUnlockAndRun(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void onDialogHidden(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.service.quicksettings.IQSService
        public void onStartSuccessful(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.quicksettings.IQSService {
        public static final java.lang.String DESCRIPTOR = "android.service.quicksettings.IQSService";
        static final int TRANSACTION_getTile = 1;
        static final int TRANSACTION_isLocked = 7;
        static final int TRANSACTION_isSecure = 8;
        static final int TRANSACTION_onDialogHidden = 10;
        static final int TRANSACTION_onShowDialog = 4;
        static final int TRANSACTION_onStartActivity = 5;
        static final int TRANSACTION_onStartSuccessful = 11;
        static final int TRANSACTION_startActivity = 6;
        static final int TRANSACTION_startUnlockAndRun = 9;
        static final int TRANSACTION_updateQsTile = 2;
        static final int TRANSACTION_updateStatusIcon = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.quicksettings.IQSService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.quicksettings.IQSService)) {
                return (android.service.quicksettings.IQSService) queryLocalInterface;
            }
            return new android.service.quicksettings.IQSService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTile";
                case 2:
                    return "updateQsTile";
                case 3:
                    return "updateStatusIcon";
                case 4:
                    return "onShowDialog";
                case 5:
                    return "onStartActivity";
                case 6:
                    return "startActivity";
                case 7:
                    return "isLocked";
                case 8:
                    return "isSecure";
                case 9:
                    return "startUnlockAndRun";
                case 10:
                    return "onDialogHidden";
                case 11:
                    return "onStartSuccessful";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    android.service.quicksettings.Tile tile = getTile(readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(tile, 1);
                    return true;
                case 2:
                    android.service.quicksettings.Tile tile2 = (android.service.quicksettings.Tile) parcel.readTypedObject(android.service.quicksettings.Tile.CREATOR);
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    updateQsTile(tile2, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    android.graphics.drawable.Icon icon = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateStatusIcon(readStrongBinder3, icon, readString);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onShowDialog(readStrongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onStartActivity(readStrongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    startActivity(readStrongBinder6, pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean isLocked = isLocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLocked);
                    return true;
                case 8:
                    boolean isSecure = isSecure();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSecure);
                    return true;
                case 9:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startUnlockAndRun(readStrongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onDialogHidden(readStrongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onStartSuccessful(readStrongBinder9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.quicksettings.IQSService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.quicksettings.IQSService.Stub.DESCRIPTOR;
            }

            @Override // android.service.quicksettings.IQSService
            public android.service.quicksettings.Tile getTile(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.quicksettings.IQSService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.service.quicksettings.Tile) obtain2.readTypedObject(android.service.quicksettings.Tile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void updateQsTile(android.service.quicksettings.Tile tile, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.quicksettings.IQSService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tile, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void updateStatusIcon(android.os.IBinder iBinder, android.graphics.drawable.Icon icon, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.quicksettings.IQSService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(icon, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void onShowDialog(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.quicksettings.IQSService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void onStartActivity(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.quicksettings.IQSService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void startActivity(android.os.IBinder iBinder, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.quicksettings.IQSService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public boolean isLocked() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.quicksettings.IQSService.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public boolean isSecure() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.quicksettings.IQSService.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void startUnlockAndRun(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.quicksettings.IQSService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void onDialogHidden(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.quicksettings.IQSService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.quicksettings.IQSService
            public void onStartSuccessful(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.quicksettings.IQSService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
