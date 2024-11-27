package android.media;

/* loaded from: classes2.dex */
public interface IRemoteDisplayProvider extends android.os.IInterface {
    void adjustVolume(java.lang.String str, int i) throws android.os.RemoteException;

    void connect(java.lang.String str) throws android.os.RemoteException;

    void disconnect(java.lang.String str) throws android.os.RemoteException;

    void setCallback(android.media.IRemoteDisplayCallback iRemoteDisplayCallback) throws android.os.RemoteException;

    void setDiscoveryMode(int i) throws android.os.RemoteException;

    void setVolume(java.lang.String str, int i) throws android.os.RemoteException;

    public static class Default implements android.media.IRemoteDisplayProvider {
        @Override // android.media.IRemoteDisplayProvider
        public void setCallback(android.media.IRemoteDisplayCallback iRemoteDisplayCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IRemoteDisplayProvider
        public void setDiscoveryMode(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IRemoteDisplayProvider
        public void connect(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IRemoteDisplayProvider
        public void disconnect(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IRemoteDisplayProvider
        public void setVolume(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IRemoteDisplayProvider
        public void adjustVolume(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IRemoteDisplayProvider {
        public static final java.lang.String DESCRIPTOR = "android.media.IRemoteDisplayProvider";
        static final int TRANSACTION_adjustVolume = 6;
        static final int TRANSACTION_connect = 3;
        static final int TRANSACTION_disconnect = 4;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setDiscoveryMode = 2;
        static final int TRANSACTION_setVolume = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.IRemoteDisplayProvider asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IRemoteDisplayProvider)) {
                return (android.media.IRemoteDisplayProvider) queryLocalInterface;
            }
            return new android.media.IRemoteDisplayProvider.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setCallback";
                case 2:
                    return "setDiscoveryMode";
                case 3:
                    return android.media.MediaMetrics.Value.CONNECT;
                case 4:
                    return android.media.MediaMetrics.Value.DISCONNECT;
                case 5:
                    return "setVolume";
                case 6:
                    return "adjustVolume";
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
                    android.media.IRemoteDisplayCallback asInterface = android.media.IRemoteDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDiscoveryMode(readInt);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    connect(readString);
                    return true;
                case 4:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disconnect(readString2);
                    return true;
                case 5:
                    java.lang.String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVolume(readString3, readInt2);
                    return true;
                case 6:
                    java.lang.String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    adjustVolume(readString4, readInt3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IRemoteDisplayProvider {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IRemoteDisplayProvider.Stub.DESCRIPTOR;
            }

            @Override // android.media.IRemoteDisplayProvider
            public void setCallback(android.media.IRemoteDisplayCallback iRemoteDisplayCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRemoteDisplayProvider.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteDisplayCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRemoteDisplayProvider
            public void setDiscoveryMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRemoteDisplayProvider.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRemoteDisplayProvider
            public void connect(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRemoteDisplayProvider.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRemoteDisplayProvider
            public void disconnect(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRemoteDisplayProvider.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRemoteDisplayProvider
            public void setVolume(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRemoteDisplayProvider.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRemoteDisplayProvider
            public void adjustVolume(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRemoteDisplayProvider.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
