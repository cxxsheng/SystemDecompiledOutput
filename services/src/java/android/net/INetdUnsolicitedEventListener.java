package android.net;

/* loaded from: classes.dex */
public interface INetdUnsolicitedEventListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$net$INetdUnsolicitedEventListener".replace('$', '.');
    public static final java.lang.String HASH = "50bce96bc8d5811ed952950df30ec503f8a561ed";
    public static final int VERSION = 14;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void onInterfaceAdded(java.lang.String str) throws android.os.RemoteException;

    void onInterfaceAddressRemoved(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void onInterfaceAddressUpdated(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void onInterfaceChanged(java.lang.String str, boolean z) throws android.os.RemoteException;

    void onInterfaceClassActivityChanged(boolean z, int i, long j, int i2) throws android.os.RemoteException;

    void onInterfaceDnsServerInfo(java.lang.String str, long j, java.lang.String[] strArr) throws android.os.RemoteException;

    void onInterfaceLinkStateChanged(java.lang.String str, boolean z) throws android.os.RemoteException;

    void onInterfaceRemoved(java.lang.String str) throws android.os.RemoteException;

    void onQuotaLimitReached(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void onRouteChanged(boolean z, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void onStrictCleartextDetected(int i, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.net.INetdUnsolicitedEventListener {
        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceClassActivityChanged(boolean z, int i, long j, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onQuotaLimitReached(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceDnsServerInfo(java.lang.String str, long j, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceAddressUpdated(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceAddressRemoved(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceAdded(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceRemoved(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceLinkStateChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onRouteChanged(boolean z, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onStrictCleartextDetected(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.INetdUnsolicitedEventListener {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onInterfaceAdded = 6;
        static final int TRANSACTION_onInterfaceAddressRemoved = 5;
        static final int TRANSACTION_onInterfaceAddressUpdated = 4;
        static final int TRANSACTION_onInterfaceChanged = 8;
        static final int TRANSACTION_onInterfaceClassActivityChanged = 1;
        static final int TRANSACTION_onInterfaceDnsServerInfo = 3;
        static final int TRANSACTION_onInterfaceLinkStateChanged = 9;
        static final int TRANSACTION_onInterfaceRemoved = 7;
        static final int TRANSACTION_onQuotaLimitReached = 2;
        static final int TRANSACTION_onRouteChanged = 10;
        static final int TRANSACTION_onStrictCleartextDetected = 11;

        public Stub() {
            attachInterface(this, android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
        }

        public static android.net.INetdUnsolicitedEventListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.INetdUnsolicitedEventListener)) {
                return (android.net.INetdUnsolicitedEventListener) queryLocalInterface;
            }
            return new android.net.INetdUnsolicitedEventListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.net.INetdUnsolicitedEventListener.DESCRIPTOR;
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
                    onInterfaceClassActivityChanged(parcel.readBoolean(), parcel.readInt(), parcel.readLong(), parcel.readInt());
                    return true;
                case 2:
                    onQuotaLimitReached(parcel.readString(), parcel.readString());
                    return true;
                case 3:
                    onInterfaceDnsServerInfo(parcel.readString(), parcel.readLong(), parcel.createStringArray());
                    return true;
                case 4:
                    onInterfaceAddressUpdated(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
                    return true;
                case 5:
                    onInterfaceAddressRemoved(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
                    return true;
                case 6:
                    onInterfaceAdded(parcel.readString());
                    return true;
                case 7:
                    onInterfaceRemoved(parcel.readString());
                    return true;
                case 8:
                    onInterfaceChanged(parcel.readString(), parcel.readBoolean());
                    return true;
                case 9:
                    onInterfaceLinkStateChanged(parcel.readString(), parcel.readBoolean());
                    return true;
                case 10:
                    onRouteChanged(parcel.readBoolean(), parcel.readString(), parcel.readString(), parcel.readString());
                    return true;
                case 11:
                    onStrictCleartextDetected(parcel.readInt(), parcel.readString());
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.INetdUnsolicitedEventListener {
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
                return android.net.INetdUnsolicitedEventListener.DESCRIPTOR;
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public void onInterfaceClassActivityChanged(boolean z, int i, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onInterfaceClassActivityChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public void onQuotaLimitReached(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onQuotaLimitReached is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public void onInterfaceDnsServerInfo(java.lang.String str, long j, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onInterfaceDnsServerInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public void onInterfaceAddressUpdated(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onInterfaceAddressUpdated is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public void onInterfaceAddressRemoved(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onInterfaceAddressRemoved is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public void onInterfaceAdded(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onInterfaceAdded is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public void onInterfaceRemoved(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onInterfaceRemoved is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public void onInterfaceChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onInterfaceChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public void onInterfaceLinkStateChanged(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onInterfaceLinkStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public void onRouteChanged(boolean z, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onRouteChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public void onStrictCleartextDetected(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onStrictCleartextDetected is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdUnsolicitedEventListener
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
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

            @Override // android.net.INetdUnsolicitedEventListener
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.net.INetdUnsolicitedEventListener.DESCRIPTOR);
                            this.mRemote.transact(android.net.INetdUnsolicitedEventListener.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
}
