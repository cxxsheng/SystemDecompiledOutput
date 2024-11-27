package android.media;

/* loaded from: classes2.dex */
public interface IMediaHTTPConnection extends android.os.IInterface {
    android.os.IBinder connect(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void disconnect() throws android.os.RemoteException;

    java.lang.String getMIMEType() throws android.os.RemoteException;

    long getSize() throws android.os.RemoteException;

    java.lang.String getUri() throws android.os.RemoteException;

    int readAt(long j, int i) throws android.os.RemoteException;

    public static class Default implements android.media.IMediaHTTPConnection {
        @Override // android.media.IMediaHTTPConnection
        public android.os.IBinder connect(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IMediaHTTPConnection
        public void disconnect() throws android.os.RemoteException {
        }

        @Override // android.media.IMediaHTTPConnection
        public int readAt(long j, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IMediaHTTPConnection
        public long getSize() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.media.IMediaHTTPConnection
        public java.lang.String getMIMEType() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IMediaHTTPConnection
        public java.lang.String getUri() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IMediaHTTPConnection {
        public static final java.lang.String DESCRIPTOR = "android.media.IMediaHTTPConnection";
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_getMIMEType = 5;
        static final int TRANSACTION_getSize = 4;
        static final int TRANSACTION_getUri = 6;
        static final int TRANSACTION_readAt = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.IMediaHTTPConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IMediaHTTPConnection)) {
                return (android.media.IMediaHTTPConnection) queryLocalInterface;
            }
            return new android.media.IMediaHTTPConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.media.MediaMetrics.Value.CONNECT;
                case 2:
                    return android.media.MediaMetrics.Value.DISCONNECT;
                case 3:
                    return "readAt";
                case 4:
                    return "getSize";
                case 5:
                    return "getMIMEType";
                case 6:
                    return "getUri";
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
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder connect = connect(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(connect);
                    return true;
                case 2:
                    disconnect();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int readAt = readAt(readLong, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(readAt);
                    return true;
                case 4:
                    long size = getSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(size);
                    return true;
                case 5:
                    java.lang.String mIMEType = getMIMEType();
                    parcel2.writeNoException();
                    parcel2.writeString(mIMEType);
                    return true;
                case 6:
                    java.lang.String uri = getUri();
                    parcel2.writeNoException();
                    parcel2.writeString(uri);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IMediaHTTPConnection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IMediaHTTPConnection.Stub.DESCRIPTOR;
            }

            @Override // android.media.IMediaHTTPConnection
            public android.os.IBinder connect(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaHTTPConnection.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaHTTPConnection
            public void disconnect() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaHTTPConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaHTTPConnection
            public int readAt(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaHTTPConnection.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaHTTPConnection
            public long getSize() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaHTTPConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaHTTPConnection
            public java.lang.String getMIMEType() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaHTTPConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaHTTPConnection
            public java.lang.String getUri() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaHTTPConnection.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
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
