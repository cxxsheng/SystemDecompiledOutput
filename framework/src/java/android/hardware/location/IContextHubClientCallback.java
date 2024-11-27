package android.hardware.location;

/* loaded from: classes2.dex */
public interface IContextHubClientCallback extends android.os.IInterface {
    void onClientAuthorizationChanged(long j, int i) throws android.os.RemoteException;

    void onHubReset() throws android.os.RemoteException;

    void onMessageFromNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage) throws android.os.RemoteException;

    void onNanoAppAborted(long j, int i) throws android.os.RemoteException;

    void onNanoAppDisabled(long j) throws android.os.RemoteException;

    void onNanoAppEnabled(long j) throws android.os.RemoteException;

    void onNanoAppLoaded(long j) throws android.os.RemoteException;

    void onNanoAppUnloaded(long j) throws android.os.RemoteException;

    public static class Default implements android.hardware.location.IContextHubClientCallback {
        @Override // android.hardware.location.IContextHubClientCallback
        public void onMessageFromNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onHubReset() throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppAborted(long j, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppLoaded(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppUnloaded(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppEnabled(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onNanoAppDisabled(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.location.IContextHubClientCallback
        public void onClientAuthorizationChanged(long j, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.location.IContextHubClientCallback {
        public static final java.lang.String DESCRIPTOR = "android.hardware.location.IContextHubClientCallback";
        static final int TRANSACTION_onClientAuthorizationChanged = 8;
        static final int TRANSACTION_onHubReset = 2;
        static final int TRANSACTION_onMessageFromNanoApp = 1;
        static final int TRANSACTION_onNanoAppAborted = 3;
        static final int TRANSACTION_onNanoAppDisabled = 7;
        static final int TRANSACTION_onNanoAppEnabled = 6;
        static final int TRANSACTION_onNanoAppLoaded = 4;
        static final int TRANSACTION_onNanoAppUnloaded = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.location.IContextHubClientCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.location.IContextHubClientCallback)) {
                return (android.hardware.location.IContextHubClientCallback) queryLocalInterface;
            }
            return new android.hardware.location.IContextHubClientCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onMessageFromNanoApp";
                case 2:
                    return "onHubReset";
                case 3:
                    return "onNanoAppAborted";
                case 4:
                    return "onNanoAppLoaded";
                case 5:
                    return "onNanoAppUnloaded";
                case 6:
                    return "onNanoAppEnabled";
                case 7:
                    return "onNanoAppDisabled";
                case 8:
                    return "onClientAuthorizationChanged";
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
                    android.hardware.location.NanoAppMessage nanoAppMessage = (android.hardware.location.NanoAppMessage) parcel.readTypedObject(android.hardware.location.NanoAppMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMessageFromNanoApp(nanoAppMessage);
                    return true;
                case 2:
                    onHubReset();
                    return true;
                case 3:
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNanoAppAborted(readLong, readInt);
                    return true;
                case 4:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onNanoAppLoaded(readLong2);
                    return true;
                case 5:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onNanoAppUnloaded(readLong3);
                    return true;
                case 6:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onNanoAppEnabled(readLong4);
                    return true;
                case 7:
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onNanoAppDisabled(readLong5);
                    return true;
                case 8:
                    long readLong6 = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onClientAuthorizationChanged(readLong6, readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.location.IContextHubClientCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.location.IContextHubClientCallback.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onMessageFromNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClientCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(nanoAppMessage, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onHubReset() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClientCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onNanoAppAborted(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClientCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onNanoAppLoaded(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClientCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onNanoAppUnloaded(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClientCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onNanoAppEnabled(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClientCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onNanoAppDisabled(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClientCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClientCallback
            public void onClientAuthorizationChanged(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubClientCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
