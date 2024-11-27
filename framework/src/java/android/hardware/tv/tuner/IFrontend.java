package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public interface IFrontend extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$tv$tuner$IFrontend".replace('$', '.');
    public static final java.lang.String HASH = "f8d74c149f04e76b6d622db2bd8e465dae24b08c";
    public static final int VERSION = 2;

    void close() throws android.os.RemoteException;

    int[] getFrontendStatusReadiness(int[] iArr) throws android.os.RemoteException;

    java.lang.String getHardwareInfo() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.tv.tuner.FrontendStatus[] getStatus(int[] iArr) throws android.os.RemoteException;

    int linkCiCam(int i) throws android.os.RemoteException;

    void removeOutputPid(int i) throws android.os.RemoteException;

    void scan(android.hardware.tv.tuner.FrontendSettings frontendSettings, int i) throws android.os.RemoteException;

    void setCallback(android.hardware.tv.tuner.IFrontendCallback iFrontendCallback) throws android.os.RemoteException;

    void setLnb(int i) throws android.os.RemoteException;

    void stopScan() throws android.os.RemoteException;

    void stopTune() throws android.os.RemoteException;

    void tune(android.hardware.tv.tuner.FrontendSettings frontendSettings) throws android.os.RemoteException;

    void unlinkCiCam(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.tv.tuner.IFrontend {
        @Override // android.hardware.tv.tuner.IFrontend
        public void setCallback(android.hardware.tv.tuner.IFrontendCallback iFrontendCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void tune(android.hardware.tv.tuner.FrontendSettings frontendSettings) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void stopTune() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void close() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void scan(android.hardware.tv.tuner.FrontendSettings frontendSettings, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void stopScan() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public android.hardware.tv.tuner.FrontendStatus[] getStatus(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void setLnb(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public int linkCiCam(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void unlinkCiCam(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public java.lang.String getHardwareInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public void removeOutputPid(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public int[] getFrontendStatusReadiness(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IFrontend
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.tv.tuner.IFrontend {
        static final int TRANSACTION_close = 4;
        static final int TRANSACTION_getFrontendStatusReadiness = 13;
        static final int TRANSACTION_getHardwareInfo = 11;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getStatus = 7;
        static final int TRANSACTION_linkCiCam = 9;
        static final int TRANSACTION_removeOutputPid = 12;
        static final int TRANSACTION_scan = 5;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setLnb = 8;
        static final int TRANSACTION_stopScan = 6;
        static final int TRANSACTION_stopTune = 3;
        static final int TRANSACTION_tune = 2;
        static final int TRANSACTION_unlinkCiCam = 10;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.tv.tuner.IFrontend asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.tv.tuner.IFrontend)) {
                return (android.hardware.tv.tuner.IFrontend) queryLocalInterface;
            }
            return new android.hardware.tv.tuner.IFrontend.Stub.Proxy(iBinder);
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
                    android.hardware.tv.tuner.IFrontendCallback asInterface = android.hardware.tv.tuner.IFrontendCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.hardware.tv.tuner.FrontendSettings frontendSettings = (android.hardware.tv.tuner.FrontendSettings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    tune(frontendSettings);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    stopTune();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.hardware.tv.tuner.FrontendSettings frontendSettings2 = (android.hardware.tv.tuner.FrontendSettings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendSettings.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scan(frontendSettings2, readInt);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    stopScan();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    android.hardware.tv.tuner.FrontendStatus[] status = getStatus(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(status, 1);
                    return true;
                case 8:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLnb(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int linkCiCam = linkCiCam(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(linkCiCam);
                    return true;
                case 10:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unlinkCiCam(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String hardwareInfo = getHardwareInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(hardwareInfo);
                    return true;
                case 12:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOutputPid(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int[] frontendStatusReadiness = getFrontendStatusReadiness(createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(frontendStatusReadiness);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.tv.tuner.IFrontend {
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

            @Override // android.hardware.tv.tuner.IFrontend
            public void setCallback(android.hardware.tv.tuner.IFrontendCallback iFrontendCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iFrontendCallback);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void tune(android.hardware.tv.tuner.FrontendSettings frontendSettings) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(frontendSettings, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tune is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void stopTune() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method stopTune is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void scan(android.hardware.tv.tuner.FrontendSettings frontendSettings, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(frontendSettings, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method scan is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void stopScan() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method stopScan is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public android.hardware.tv.tuner.FrontendStatus[] getStatus(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getStatus is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.tv.tuner.FrontendStatus[]) obtain2.createTypedArray(android.hardware.tv.tuner.FrontendStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void setLnb(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setLnb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public int linkCiCam(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method linkCiCam is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void unlinkCiCam(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method unlinkCiCam is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public java.lang.String getHardwareInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getHardwareInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public void removeOutputPid(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method removeOutputPid is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
            public int[] getFrontendStatusReadiness(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getFrontendStatusReadiness is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFrontend
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

            @Override // android.hardware.tv.tuner.IFrontend
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
