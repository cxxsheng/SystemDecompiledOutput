package android.net.dhcp;

/* loaded from: classes.dex */
public interface IDhcpEventCallbacks extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$net$dhcp$IDhcpEventCallbacks".replace('$', '.');
    public static final java.lang.String HASH = "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
    public static final int VERSION = 21;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void onLeasesChanged(java.util.List<android.net.dhcp.DhcpLeaseParcelable> list) throws android.os.RemoteException;

    void onNewPrefixRequest(android.net.IpPrefix ipPrefix) throws android.os.RemoteException;

    public static class Default implements android.net.dhcp.IDhcpEventCallbacks {
        @Override // android.net.dhcp.IDhcpEventCallbacks
        public void onLeasesChanged(java.util.List<android.net.dhcp.DhcpLeaseParcelable> list) throws android.os.RemoteException {
        }

        @Override // android.net.dhcp.IDhcpEventCallbacks
        public void onNewPrefixRequest(android.net.IpPrefix ipPrefix) throws android.os.RemoteException {
        }

        @Override // android.net.dhcp.IDhcpEventCallbacks
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.dhcp.IDhcpEventCallbacks
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.dhcp.IDhcpEventCallbacks {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onLeasesChanged = 1;
        static final int TRANSACTION_onNewPrefixRequest = 2;

        public Stub() {
            attachInterface(this, android.net.dhcp.IDhcpEventCallbacks.DESCRIPTOR);
        }

        public static android.net.dhcp.IDhcpEventCallbacks asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.dhcp.IDhcpEventCallbacks.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.dhcp.IDhcpEventCallbacks)) {
                return (android.net.dhcp.IDhcpEventCallbacks) queryLocalInterface;
            }
            return new android.net.dhcp.IDhcpEventCallbacks.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.net.dhcp.IDhcpEventCallbacks.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    onLeasesChanged(parcel.createTypedArrayList(android.net.dhcp.DhcpLeaseParcelable.CREATOR));
                    return true;
                case 2:
                    onNewPrefixRequest((android.net.IpPrefix) parcel.readTypedObject(android.net.IpPrefix.CREATOR));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.dhcp.IDhcpEventCallbacks {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.dhcp.IDhcpEventCallbacks.DESCRIPTOR;
            }

            @Override // android.net.dhcp.IDhcpEventCallbacks
            public void onLeasesChanged(java.util.List<android.net.dhcp.DhcpLeaseParcelable> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.dhcp.IDhcpEventCallbacks.DESCRIPTOR);
                    android.net.dhcp.IDhcpEventCallbacks._Parcel.writeTypedList(obtain, list, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onLeasesChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.dhcp.IDhcpEventCallbacks
            public void onNewPrefixRequest(android.net.IpPrefix ipPrefix) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.dhcp.IDhcpEventCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(ipPrefix, 0);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onNewPrefixRequest is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.dhcp.IDhcpEventCallbacks
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.net.dhcp.IDhcpEventCallbacks.DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.net.dhcp.IDhcpEventCallbacks
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.net.dhcp.IDhcpEventCallbacks.DESCRIPTOR);
                            this.mRemote.transact(android.net.dhcp.IDhcpEventCallbacks.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                            obtain2.readException();
                            this.mCachedHash = obtain2.readString();
                            obtain2.recycle();
                            obtain.recycle();
                        } catch (java.lang.Throwable th) {
                            obtain2.recycle();
                            obtain.recycle();
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }

    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends android.os.Parcelable> void writeTypedList(android.os.Parcel parcel, java.util.List<T> list, int i) {
            if (list == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeTypedObject(list.get(i2), i);
            }
        }
    }
}
