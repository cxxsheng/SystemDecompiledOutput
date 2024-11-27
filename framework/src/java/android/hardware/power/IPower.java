package android.hardware.power;

/* loaded from: classes2.dex */
public interface IPower extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$power$IPower".replace('$', '.');
    public static final java.lang.String HASH = "d111735ed2b89b6c32443aac9b162b1afbbea3f2";
    public static final int VERSION = 5;

    void closeSessionChannel(int i, int i2) throws android.os.RemoteException;

    android.hardware.power.IPowerHintSession createHintSession(int i, int i2, int[] iArr, long j) throws android.os.RemoteException;

    android.hardware.power.IPowerHintSession createHintSessionWithConfig(int i, int i2, int[] iArr, long j, int i3, android.hardware.power.SessionConfig sessionConfig) throws android.os.RemoteException;

    long getHintSessionPreferredRate() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.power.ChannelConfig getSessionChannel(int i, int i2) throws android.os.RemoteException;

    boolean isBoostSupported(int i) throws android.os.RemoteException;

    boolean isModeSupported(int i) throws android.os.RemoteException;

    void setBoost(int i, int i2) throws android.os.RemoteException;

    void setMode(int i, boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.power.IPower {
        @Override // android.hardware.power.IPower
        public void setMode(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.power.IPower
        public boolean isModeSupported(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.power.IPower
        public void setBoost(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.power.IPower
        public boolean isBoostSupported(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.power.IPower
        public android.hardware.power.IPowerHintSession createHintSession(int i, int i2, int[] iArr, long j) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public long getHintSessionPreferredRate() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.power.IPower
        public android.hardware.power.IPowerHintSession createHintSessionWithConfig(int i, int i2, int[] iArr, long j, int i3, android.hardware.power.SessionConfig sessionConfig) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public android.hardware.power.ChannelConfig getSessionChannel(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.power.IPower
        public void closeSessionChannel(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.power.IPower
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.power.IPower
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.power.IPower {
        static final int TRANSACTION_closeSessionChannel = 9;
        static final int TRANSACTION_createHintSession = 5;
        static final int TRANSACTION_createHintSessionWithConfig = 7;
        static final int TRANSACTION_getHintSessionPreferredRate = 6;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSessionChannel = 8;
        static final int TRANSACTION_isBoostSupported = 4;
        static final int TRANSACTION_isModeSupported = 2;
        static final int TRANSACTION_setBoost = 3;
        static final int TRANSACTION_setMode = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.power.IPower asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.power.IPower)) {
                return (android.hardware.power.IPower) queryLocalInterface;
            }
            return new android.hardware.power.IPower.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setMode";
                case 2:
                    return "isModeSupported";
                case 3:
                    return "setBoost";
                case 4:
                    return "isBoostSupported";
                case 5:
                    return "createHintSession";
                case 6:
                    return "getHintSessionPreferredRate";
                case 7:
                    return "createHintSessionWithConfig";
                case 8:
                    return "getSessionChannel";
                case 9:
                    return "closeSessionChannel";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMode(readInt, readBoolean);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isModeSupported = isModeSupported(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isModeSupported);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBoost(readInt3, readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isBoostSupported = isBoostSupported(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBoostSupported);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.hardware.power.IPowerHintSession createHintSession = createHintSession(readInt6, readInt7, createIntArray, readLong);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createHintSession);
                    return true;
                case 6:
                    long hintSessionPreferredRate = getHintSessionPreferredRate();
                    parcel2.writeNoException();
                    parcel2.writeLong(hintSessionPreferredRate);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    long readLong2 = parcel.readLong();
                    int readInt10 = parcel.readInt();
                    android.hardware.power.SessionConfig sessionConfig = new android.hardware.power.SessionConfig();
                    parcel.enforceNoDataAvail();
                    android.hardware.power.IPowerHintSession createHintSessionWithConfig = createHintSessionWithConfig(readInt8, readInt9, createIntArray2, readLong2, readInt10, sessionConfig);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createHintSessionWithConfig);
                    parcel2.writeTypedObject(sessionConfig, 1);
                    return true;
                case 8:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.power.ChannelConfig sessionChannel = getSessionChannel(readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionChannel, 1);
                    return true;
                case 9:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeSessionChannel(readInt13, readInt14);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.power.IPower {
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

            @Override // android.hardware.power.IPower
            public void setMode(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public boolean isModeSupported(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isModeSupported is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void setBoost(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setBoost is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public boolean isBoostSupported(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isBoostSupported is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public android.hardware.power.IPowerHintSession createHintSession(int i, int i2, int[] iArr, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method createHintSession is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.power.IPowerHintSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public long getHintSessionPreferredRate() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getHintSessionPreferredRate is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public android.hardware.power.IPowerHintSession createHintSessionWithConfig(int i, int i2, int[] iArr, long j, int i3, android.hardware.power.SessionConfig sessionConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    obtain.writeLong(j);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method createHintSessionWithConfig is unimplemented.");
                    }
                    obtain2.readException();
                    android.hardware.power.IPowerHintSession asInterface = android.hardware.power.IPowerHintSession.Stub.asInterface(obtain2.readStrongBinder());
                    if (obtain2.readInt() != 0) {
                        sessionConfig.readFromParcel(obtain2);
                    }
                    return asInterface;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public android.hardware.power.ChannelConfig getSessionChannel(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getSessionChannel is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.power.ChannelConfig) obtain2.readTypedObject(android.hardware.power.ChannelConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
            public void closeSessionChannel(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method closeSessionChannel is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.power.IPower
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

            @Override // android.hardware.power.IPower
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

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
