package android.net.vcn;

/* loaded from: classes2.dex */
public interface IVcnUnderlyingNetworkPolicyListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.vcn.IVcnUnderlyingNetworkPolicyListener";

    void onPolicyChanged() throws android.os.RemoteException;

    public static class Default implements android.net.vcn.IVcnUnderlyingNetworkPolicyListener {
        @Override // android.net.vcn.IVcnUnderlyingNetworkPolicyListener
        public void onPolicyChanged() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.vcn.IVcnUnderlyingNetworkPolicyListener {
        static final int TRANSACTION_onPolicyChanged = 1;

        public Stub() {
            attachInterface(this, android.net.vcn.IVcnUnderlyingNetworkPolicyListener.DESCRIPTOR);
        }

        public static android.net.vcn.IVcnUnderlyingNetworkPolicyListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.vcn.IVcnUnderlyingNetworkPolicyListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.vcn.IVcnUnderlyingNetworkPolicyListener)) {
                return (android.net.vcn.IVcnUnderlyingNetworkPolicyListener) queryLocalInterface;
            }
            return new android.net.vcn.IVcnUnderlyingNetworkPolicyListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPolicyChanged";
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
                parcel.enforceInterface(android.net.vcn.IVcnUnderlyingNetworkPolicyListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.vcn.IVcnUnderlyingNetworkPolicyListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onPolicyChanged();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.vcn.IVcnUnderlyingNetworkPolicyListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.vcn.IVcnUnderlyingNetworkPolicyListener.DESCRIPTOR;
            }

            @Override // android.net.vcn.IVcnUnderlyingNetworkPolicyListener
            public void onPolicyChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.vcn.IVcnUnderlyingNetworkPolicyListener.DESCRIPTOR);
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
