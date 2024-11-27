package android.hardware.cas;

/* loaded from: classes.dex */
public interface ICas extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$cas$ICas".replace('$', '.');
    public static final java.lang.String HASH = "bc51d8d70a55ec4723d3f73d0acf7003306bf69f";
    public static final int VERSION = 1;

    void closeSession(byte[] bArr) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    byte[] openSession(int i, int i2) throws android.os.RemoteException;

    byte[] openSessionDefault() throws android.os.RemoteException;

    void processEcm(byte[] bArr, byte[] bArr2) throws android.os.RemoteException;

    void processEmm(byte[] bArr) throws android.os.RemoteException;

    void provision(java.lang.String str) throws android.os.RemoteException;

    void refreshEntitlements(int i, byte[] bArr) throws android.os.RemoteException;

    void release() throws android.os.RemoteException;

    void sendEvent(int i, int i2, byte[] bArr) throws android.os.RemoteException;

    void sendSessionEvent(byte[] bArr, int i, int i2, byte[] bArr2) throws android.os.RemoteException;

    void setPrivateData(byte[] bArr) throws android.os.RemoteException;

    void setSessionPrivateData(byte[] bArr, byte[] bArr2) throws android.os.RemoteException;

    public static class Default implements android.hardware.cas.ICas {
        @Override // android.hardware.cas.ICas
        public void closeSession(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public byte[] openSessionDefault() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.cas.ICas
        public byte[] openSession(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.cas.ICas
        public void processEcm(byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void processEmm(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void provision(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void refreshEntitlements(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void release() throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void sendEvent(int i, int i2, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void sendSessionEvent(byte[] bArr, int i, int i2, byte[] bArr2) throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void setPrivateData(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public void setSessionPrivateData(byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.ICas
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.cas.ICas
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.cas.ICas {
        static final int TRANSACTION_closeSession = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_openSession = 3;
        static final int TRANSACTION_openSessionDefault = 2;
        static final int TRANSACTION_processEcm = 4;
        static final int TRANSACTION_processEmm = 5;
        static final int TRANSACTION_provision = 6;
        static final int TRANSACTION_refreshEntitlements = 7;
        static final int TRANSACTION_release = 8;
        static final int TRANSACTION_sendEvent = 9;
        static final int TRANSACTION_sendSessionEvent = 10;
        static final int TRANSACTION_setPrivateData = 11;
        static final int TRANSACTION_setSessionPrivateData = 12;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.cas.ICas asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.cas.ICas)) {
                return (android.hardware.cas.ICas) queryLocalInterface;
            }
            return new android.hardware.cas.ICas.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = DESCRIPTOR;
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
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    closeSession(createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    byte[] openSessionDefault = openSessionDefault();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(openSessionDefault);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] openSession = openSession(readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(openSession);
                    return true;
                case 4:
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    processEcm(createByteArray2, createByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    byte[] createByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    processEmm(createByteArray4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    provision(readString);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    byte[] createByteArray5 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    refreshEntitlements(readInt3, createByteArray5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    release();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    byte[] createByteArray6 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendEvent(readInt4, readInt5, createByteArray6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    byte[] createByteArray7 = parcel.createByteArray();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    byte[] createByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendSessionEvent(createByteArray7, readInt6, readInt7, createByteArray8);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    byte[] createByteArray9 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setPrivateData(createByteArray9);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    byte[] createByteArray10 = parcel.createByteArray();
                    byte[] createByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setSessionPrivateData(createByteArray10, createByteArray11);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.cas.ICas {
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
                return DESCRIPTOR;
            }

            @Override // android.hardware.cas.ICas
            public void closeSession(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method closeSession is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public byte[] openSessionDefault() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openSessionDefault is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public byte[] openSession(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openSession is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void processEcm(byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method processEcm is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void processEmm(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method processEmm is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void provision(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method provision is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void refreshEntitlements(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method refreshEntitlements is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void release() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method release is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void sendEvent(int i, int i2, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method sendEvent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void sendSessionEvent(byte[] bArr, int i, int i2, byte[] bArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method sendSessionEvent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void setPrivateData(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setPrivateData is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public void setSessionPrivateData(byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setSessionPrivateData is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.ICas
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
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

            @Override // android.hardware.cas.ICas
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
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
                return this.mCachedHash;
            }
        }
    }
}
