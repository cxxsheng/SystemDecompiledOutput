package android.telephony.data;

/* loaded from: classes3.dex */
public interface IQualifiedNetworksServiceCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.data.IQualifiedNetworksServiceCallback";

    void onNetworkValidationRequested(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void onQualifiedNetworkTypesChanged(int i, int[] iArr) throws android.os.RemoteException;

    void onReconnectQualifedNetworkType(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.telephony.data.IQualifiedNetworksServiceCallback {
        @Override // android.telephony.data.IQualifiedNetworksServiceCallback
        public void onQualifiedNetworkTypesChanged(int i, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksServiceCallback
        public void onNetworkValidationRequested(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IQualifiedNetworksServiceCallback
        public void onReconnectQualifedNetworkType(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.data.IQualifiedNetworksServiceCallback {
        static final int TRANSACTION_onNetworkValidationRequested = 2;
        static final int TRANSACTION_onQualifiedNetworkTypesChanged = 1;
        static final int TRANSACTION_onReconnectQualifedNetworkType = 3;

        public Stub() {
            attachInterface(this, android.telephony.data.IQualifiedNetworksServiceCallback.DESCRIPTOR);
        }

        public static android.telephony.data.IQualifiedNetworksServiceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.data.IQualifiedNetworksServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.data.IQualifiedNetworksServiceCallback)) {
                return (android.telephony.data.IQualifiedNetworksServiceCallback) queryLocalInterface;
            }
            return new android.telephony.data.IQualifiedNetworksServiceCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onQualifiedNetworkTypesChanged";
                case 2:
                    return "onNetworkValidationRequested";
                case 3:
                    return "onReconnectQualifedNetworkType";
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
                parcel.enforceInterface(android.telephony.data.IQualifiedNetworksServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.data.IQualifiedNetworksServiceCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onQualifiedNetworkTypesChanged(readInt, createIntArray);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onNetworkValidationRequested(readInt2, asInterface);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onReconnectQualifedNetworkType(readInt3, readInt4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.data.IQualifiedNetworksServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.data.IQualifiedNetworksServiceCallback.DESCRIPTOR;
            }

            @Override // android.telephony.data.IQualifiedNetworksServiceCallback
            public void onQualifiedNetworkTypesChanged(int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IQualifiedNetworksServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksServiceCallback
            public void onNetworkValidationRequested(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IQualifiedNetworksServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IQualifiedNetworksServiceCallback
            public void onReconnectQualifedNetworkType(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IQualifiedNetworksServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
