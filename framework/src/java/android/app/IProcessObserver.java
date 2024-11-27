package android.app;

/* loaded from: classes.dex */
public interface IProcessObserver extends android.os.IInterface {
    void onForegroundActivitiesChanged(int i, int i2, boolean z) throws android.os.RemoteException;

    void onForegroundServicesChanged(int i, int i2, int i3) throws android.os.RemoteException;

    void onProcessDied(int i, int i2) throws android.os.RemoteException;

    void onProcessStarted(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    public static class Default implements android.app.IProcessObserver {
        @Override // android.app.IProcessObserver
        public void onProcessStarted(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.IProcessObserver
        public void onForegroundActivitiesChanged(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IProcessObserver
        public void onForegroundServicesChanged(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IProcessObserver
        public void onProcessDied(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IProcessObserver {
        public static final java.lang.String DESCRIPTOR = "android.app.IProcessObserver";
        static final int TRANSACTION_onForegroundActivitiesChanged = 2;
        static final int TRANSACTION_onForegroundServicesChanged = 3;
        static final int TRANSACTION_onProcessDied = 4;
        static final int TRANSACTION_onProcessStarted = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IProcessObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IProcessObserver)) {
                return (android.app.IProcessObserver) queryLocalInterface;
            }
            return new android.app.IProcessObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onProcessStarted";
                case 2:
                    return "onForegroundActivitiesChanged";
                case 3:
                    return "onForegroundServicesChanged";
                case 4:
                    return "onProcessDied";
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onProcessStarted(readInt, readInt2, readInt3, readString, readString2);
                    return true;
                case 2:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onForegroundActivitiesChanged(readInt4, readInt5, readBoolean);
                    return true;
                case 3:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onForegroundServicesChanged(readInt6, readInt7, readInt8);
                    return true;
                case 4:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onProcessDied(readInt9, readInt10);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IProcessObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IProcessObserver.Stub.DESCRIPTOR;
            }

            @Override // android.app.IProcessObserver
            public void onProcessStarted(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IProcessObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IProcessObserver
            public void onForegroundActivitiesChanged(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IProcessObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IProcessObserver
            public void onForegroundServicesChanged(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IProcessObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IProcessObserver
            public void onProcessDied(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IProcessObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
