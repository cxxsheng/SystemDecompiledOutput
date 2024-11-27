package android.hardware.gnss;

/* loaded from: classes2.dex */
public interface IGnssConfiguration extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$gnss$IGnssConfiguration".replace('$', '.');
    public static final int GLONASS_POS_PROTOCOL_LPP_UPLANE = 4;
    public static final int GLONASS_POS_PROTOCOL_RRC_CPLANE = 1;
    public static final int GLONASS_POS_PROTOCOL_RRLP_UPLANE = 2;
    public static final java.lang.String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int LPP_PROFILE_CONTROL_PLANE = 2;
    public static final int LPP_PROFILE_USER_PLANE = 1;
    public static final int SUPL_MODE_MSA = 2;
    public static final int SUPL_MODE_MSB = 1;
    public static final int VERSION = 2;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void setBlocklist(android.hardware.gnss.BlocklistedSource[] blocklistedSourceArr) throws android.os.RemoteException;

    void setEmergencySuplPdn(boolean z) throws android.os.RemoteException;

    void setEsExtensionSec(int i) throws android.os.RemoteException;

    void setGlonassPositioningProtocol(int i) throws android.os.RemoteException;

    void setLppProfile(int i) throws android.os.RemoteException;

    void setSuplMode(int i) throws android.os.RemoteException;

    void setSuplVersion(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.gnss.IGnssConfiguration {
        @Override // android.hardware.gnss.IGnssConfiguration
        public void setSuplVersion(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssConfiguration
        public void setSuplMode(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssConfiguration
        public void setLppProfile(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssConfiguration
        public void setGlonassPositioningProtocol(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssConfiguration
        public void setEmergencySuplPdn(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssConfiguration
        public void setEsExtensionSec(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssConfiguration
        public void setBlocklist(android.hardware.gnss.BlocklistedSource[] blocklistedSourceArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssConfiguration
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssConfiguration
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.gnss.IGnssConfiguration {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_setBlocklist = 7;
        static final int TRANSACTION_setEmergencySuplPdn = 5;
        static final int TRANSACTION_setEsExtensionSec = 6;
        static final int TRANSACTION_setGlonassPositioningProtocol = 4;
        static final int TRANSACTION_setLppProfile = 3;
        static final int TRANSACTION_setSuplMode = 2;
        static final int TRANSACTION_setSuplVersion = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.gnss.IGnssConfiguration asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.IGnssConfiguration)) {
                return (android.hardware.gnss.IGnssConfiguration) queryLocalInterface;
            }
            return new android.hardware.gnss.IGnssConfiguration.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setSuplVersion";
                case 2:
                    return "setSuplMode";
                case 3:
                    return "setLppProfile";
                case 4:
                    return "setGlonassPositioningProtocol";
                case 5:
                    return "setEmergencySuplPdn";
                case 6:
                    return "setEsExtensionSec";
                case 7:
                    return "setBlocklist";
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
                    parcel.enforceNoDataAvail();
                    setSuplVersion(readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSuplMode(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLppProfile(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGlonassPositioningProtocol(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEmergencySuplPdn(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEsExtensionSec(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.hardware.gnss.BlocklistedSource[] blocklistedSourceArr = (android.hardware.gnss.BlocklistedSource[]) parcel.createTypedArray(android.hardware.gnss.BlocklistedSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    setBlocklist(blocklistedSourceArr);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.gnss.IGnssConfiguration {
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

            @Override // android.hardware.gnss.IGnssConfiguration
            public void setSuplVersion(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setSuplVersion is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssConfiguration
            public void setSuplMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setSuplMode is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssConfiguration
            public void setLppProfile(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setLppProfile is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssConfiguration
            public void setGlonassPositioningProtocol(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setGlonassPositioningProtocol is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssConfiguration
            public void setEmergencySuplPdn(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setEmergencySuplPdn is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssConfiguration
            public void setEsExtensionSec(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setEsExtensionSec is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssConfiguration
            public void setBlocklist(android.hardware.gnss.BlocklistedSource[] blocklistedSourceArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(blocklistedSourceArr, 0);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setBlocklist is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssConfiguration
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

            @Override // android.hardware.gnss.IGnssConfiguration
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
