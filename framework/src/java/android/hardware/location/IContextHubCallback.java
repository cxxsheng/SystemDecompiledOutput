package android.hardware.location;

/* loaded from: classes2.dex */
public interface IContextHubCallback extends android.os.IInterface {
    void onMessageReceipt(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage) throws android.os.RemoteException;

    public static class Default implements android.hardware.location.IContextHubCallback {
        @Override // android.hardware.location.IContextHubCallback
        public void onMessageReceipt(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.location.IContextHubCallback {
        public static final java.lang.String DESCRIPTOR = "android.hardware.location.IContextHubCallback";
        static final int TRANSACTION_onMessageReceipt = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.location.IContextHubCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.location.IContextHubCallback)) {
                return (android.hardware.location.IContextHubCallback) queryLocalInterface;
            }
            return new android.hardware.location.IContextHubCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onMessageReceipt";
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
                    android.hardware.location.ContextHubMessage contextHubMessage = (android.hardware.location.ContextHubMessage) parcel.readTypedObject(android.hardware.location.ContextHubMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMessageReceipt(readInt, readInt2, contextHubMessage);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.location.IContextHubCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.location.IContextHubCallback.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IContextHubCallback
            public void onMessageReceipt(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IContextHubCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(contextHubMessage, 0);
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
