package android.accessibilityservice;

/* loaded from: classes.dex */
public interface IBrailleDisplayConnection extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.accessibilityservice.IBrailleDisplayConnection";

    void disconnect() throws android.os.RemoteException;

    void write(byte[] bArr) throws android.os.RemoteException;

    public static class Default implements android.accessibilityservice.IBrailleDisplayConnection {
        @Override // android.accessibilityservice.IBrailleDisplayConnection
        public void disconnect() throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IBrailleDisplayConnection
        public void write(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.accessibilityservice.IBrailleDisplayConnection {
        static final int TRANSACTION_disconnect = 1;
        static final int TRANSACTION_write = 2;

        public Stub() {
            attachInterface(this, android.accessibilityservice.IBrailleDisplayConnection.DESCRIPTOR);
        }

        public static android.accessibilityservice.IBrailleDisplayConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.accessibilityservice.IBrailleDisplayConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.accessibilityservice.IBrailleDisplayConnection)) {
                return (android.accessibilityservice.IBrailleDisplayConnection) queryLocalInterface;
            }
            return new android.accessibilityservice.IBrailleDisplayConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.media.MediaMetrics.Value.DISCONNECT;
                case 2:
                    return "write";
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
                parcel.enforceInterface(android.accessibilityservice.IBrailleDisplayConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.accessibilityservice.IBrailleDisplayConnection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    disconnect();
                    return true;
                case 2:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    write(createByteArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.accessibilityservice.IBrailleDisplayConnection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.accessibilityservice.IBrailleDisplayConnection.DESCRIPTOR;
            }

            @Override // android.accessibilityservice.IBrailleDisplayConnection
            public void disconnect() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IBrailleDisplayConnection.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayConnection
            public void write(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IBrailleDisplayConnection.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
