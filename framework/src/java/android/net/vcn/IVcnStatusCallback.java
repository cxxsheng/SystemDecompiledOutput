package android.net.vcn;

/* loaded from: classes2.dex */
public interface IVcnStatusCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.vcn.IVcnStatusCallback";

    void onGatewayConnectionError(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void onVcnStatusChanged(int i) throws android.os.RemoteException;

    public static class Default implements android.net.vcn.IVcnStatusCallback {
        @Override // android.net.vcn.IVcnStatusCallback
        public void onVcnStatusChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.net.vcn.IVcnStatusCallback
        public void onGatewayConnectionError(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.vcn.IVcnStatusCallback {
        static final int TRANSACTION_onGatewayConnectionError = 2;
        static final int TRANSACTION_onVcnStatusChanged = 1;

        public Stub() {
            attachInterface(this, android.net.vcn.IVcnStatusCallback.DESCRIPTOR);
        }

        public static android.net.vcn.IVcnStatusCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.vcn.IVcnStatusCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.vcn.IVcnStatusCallback)) {
                return (android.net.vcn.IVcnStatusCallback) queryLocalInterface;
            }
            return new android.net.vcn.IVcnStatusCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onVcnStatusChanged";
                case 2:
                    return "onGatewayConnectionError";
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
                parcel.enforceInterface(android.net.vcn.IVcnStatusCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.vcn.IVcnStatusCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVcnStatusChanged(readInt);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onGatewayConnectionError(readString, readInt2, readString2, readString3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.vcn.IVcnStatusCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.vcn.IVcnStatusCallback.DESCRIPTOR;
            }

            @Override // android.net.vcn.IVcnStatusCallback
            public void onVcnStatusChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.vcn.IVcnStatusCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.vcn.IVcnStatusCallback
            public void onGatewayConnectionError(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.vcn.IVcnStatusCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
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
