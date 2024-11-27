package android.companion;

/* loaded from: classes.dex */
public interface IOnMessageReceivedListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.IOnMessageReceivedListener";

    void onMessageReceived(int i, byte[] bArr) throws android.os.RemoteException;

    public static class Default implements android.companion.IOnMessageReceivedListener {
        @Override // android.companion.IOnMessageReceivedListener
        public void onMessageReceived(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.IOnMessageReceivedListener {
        static final int TRANSACTION_onMessageReceived = 1;

        public Stub() {
            attachInterface(this, android.companion.IOnMessageReceivedListener.DESCRIPTOR);
        }

        public static android.companion.IOnMessageReceivedListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.IOnMessageReceivedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.IOnMessageReceivedListener)) {
                return (android.companion.IOnMessageReceivedListener) queryLocalInterface;
            }
            return new android.companion.IOnMessageReceivedListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onMessageReceived";
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
                parcel.enforceInterface(android.companion.IOnMessageReceivedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.IOnMessageReceivedListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onMessageReceived(readInt, createByteArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.IOnMessageReceivedListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.IOnMessageReceivedListener.DESCRIPTOR;
            }

            @Override // android.companion.IOnMessageReceivedListener
            public void onMessageReceived(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.IOnMessageReceivedListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
