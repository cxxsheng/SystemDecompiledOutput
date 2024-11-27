package com.android.net;

/* loaded from: classes5.dex */
public interface IProxyPortListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.net.IProxyPortListener";

    void setProxyPort(int i) throws android.os.RemoteException;

    public static class Default implements com.android.net.IProxyPortListener {
        @Override // com.android.net.IProxyPortListener
        public void setProxyPort(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.net.IProxyPortListener {
        static final int TRANSACTION_setProxyPort = 1;

        public Stub() {
            attachInterface(this, com.android.net.IProxyPortListener.DESCRIPTOR);
        }

        public static com.android.net.IProxyPortListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.net.IProxyPortListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.net.IProxyPortListener)) {
                return (com.android.net.IProxyPortListener) queryLocalInterface;
            }
            return new com.android.net.IProxyPortListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setProxyPort";
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
                parcel.enforceInterface(com.android.net.IProxyPortListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.net.IProxyPortListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setProxyPort(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.net.IProxyPortListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.net.IProxyPortListener.DESCRIPTOR;
            }

            @Override // com.android.net.IProxyPortListener
            public void setProxyPort(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.net.IProxyPortListener.DESCRIPTOR);
                    obtain.writeInt(i);
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
