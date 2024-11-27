package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public interface ITuner extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$tv$tuner$ITuner".replace('$', '.');
    public static final java.lang.String HASH = "f8d74c149f04e76b6d622db2bd8e465dae24b08c";
    public static final int VERSION = 2;

    android.hardware.tv.tuner.DemuxCapabilities getDemuxCaps() throws android.os.RemoteException;

    int[] getDemuxIds() throws android.os.RemoteException;

    android.hardware.tv.tuner.DemuxInfo getDemuxInfo(int i) throws android.os.RemoteException;

    int[] getFrontendIds() throws android.os.RemoteException;

    android.hardware.tv.tuner.FrontendInfo getFrontendInfo(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    int[] getLnbIds() throws android.os.RemoteException;

    int getMaxNumberOfFrontends(int i) throws android.os.RemoteException;

    boolean isLnaSupported() throws android.os.RemoteException;

    android.hardware.tv.tuner.IDemux openDemux(int[] iArr) throws android.os.RemoteException;

    android.hardware.tv.tuner.IDemux openDemuxById(int i) throws android.os.RemoteException;

    android.hardware.tv.tuner.IDescrambler openDescrambler() throws android.os.RemoteException;

    android.hardware.tv.tuner.IFrontend openFrontendById(int i) throws android.os.RemoteException;

    android.hardware.tv.tuner.ILnb openLnbById(int i) throws android.os.RemoteException;

    android.hardware.tv.tuner.ILnb openLnbByName(java.lang.String str, int[] iArr) throws android.os.RemoteException;

    void setLna(boolean z) throws android.os.RemoteException;

    void setMaxNumberOfFrontends(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.hardware.tv.tuner.ITuner {
        @Override // android.hardware.tv.tuner.ITuner
        public int[] getFrontendIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public android.hardware.tv.tuner.IFrontend openFrontendById(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public android.hardware.tv.tuner.IDemux openDemux(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public android.hardware.tv.tuner.DemuxCapabilities getDemuxCaps() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public android.hardware.tv.tuner.IDescrambler openDescrambler() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public android.hardware.tv.tuner.FrontendInfo getFrontendInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public int[] getLnbIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public android.hardware.tv.tuner.ILnb openLnbById(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public android.hardware.tv.tuner.ILnb openLnbByName(java.lang.String str, int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public void setLna(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.ITuner
        public void setMaxNumberOfFrontends(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.ITuner
        public int getMaxNumberOfFrontends(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public boolean isLnaSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public int[] getDemuxIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public android.hardware.tv.tuner.IDemux openDemuxById(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public android.hardware.tv.tuner.DemuxInfo getDemuxInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.ITuner
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.tv.tuner.ITuner {
        static final int TRANSACTION_getDemuxCaps = 4;
        static final int TRANSACTION_getDemuxIds = 14;
        static final int TRANSACTION_getDemuxInfo = 16;
        static final int TRANSACTION_getFrontendIds = 1;
        static final int TRANSACTION_getFrontendInfo = 6;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getLnbIds = 7;
        static final int TRANSACTION_getMaxNumberOfFrontends = 12;
        static final int TRANSACTION_isLnaSupported = 13;
        static final int TRANSACTION_openDemux = 3;
        static final int TRANSACTION_openDemuxById = 15;
        static final int TRANSACTION_openDescrambler = 5;
        static final int TRANSACTION_openFrontendById = 2;
        static final int TRANSACTION_openLnbById = 8;
        static final int TRANSACTION_openLnbByName = 9;
        static final int TRANSACTION_setLna = 10;
        static final int TRANSACTION_setMaxNumberOfFrontends = 11;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.tv.tuner.ITuner asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.tv.tuner.ITuner)) {
                return (android.hardware.tv.tuner.ITuner) queryLocalInterface;
            }
            return new android.hardware.tv.tuner.ITuner.Stub.Proxy(iBinder);
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
            int[] iArr = null;
            switch (i) {
                case 1:
                    int[] frontendIds = getFrontendIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(frontendIds);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.tv.tuner.IFrontend openFrontendById = openFrontendById(readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openFrontendById);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    if (readInt2 >= 0) {
                        iArr = new int[readInt2];
                    }
                    parcel.enforceNoDataAvail();
                    android.hardware.tv.tuner.IDemux openDemux = openDemux(iArr);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openDemux);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 4:
                    android.hardware.tv.tuner.DemuxCapabilities demuxCaps = getDemuxCaps();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(demuxCaps, 1);
                    return true;
                case 5:
                    android.hardware.tv.tuner.IDescrambler openDescrambler = openDescrambler();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openDescrambler);
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.tv.tuner.FrontendInfo frontendInfo = getFrontendInfo(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(frontendInfo, 1);
                    return true;
                case 7:
                    int[] lnbIds = getLnbIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(lnbIds);
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.tv.tuner.ILnb openLnbById = openLnbById(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openLnbById);
                    return true;
                case 9:
                    java.lang.String readString = parcel.readString();
                    int readInt5 = parcel.readInt();
                    if (readInt5 >= 0) {
                        iArr = new int[readInt5];
                    }
                    parcel.enforceNoDataAvail();
                    android.hardware.tv.tuner.ILnb openLnbByName = openLnbByName(readString, iArr);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openLnbByName);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 10:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLna(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMaxNumberOfFrontends(readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int maxNumberOfFrontends = getMaxNumberOfFrontends(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxNumberOfFrontends);
                    return true;
                case 13:
                    boolean isLnaSupported = isLnaSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLnaSupported);
                    return true;
                case 14:
                    int[] demuxIds = getDemuxIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(demuxIds);
                    return true;
                case 15:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.tv.tuner.IDemux openDemuxById = openDemuxById(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openDemuxById);
                    return true;
                case 16:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.tv.tuner.DemuxInfo demuxInfo = getDemuxInfo(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(demuxInfo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.tv.tuner.ITuner {
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

            @Override // android.hardware.tv.tuner.ITuner
            public int[] getFrontendIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getFrontendIds is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public android.hardware.tv.tuner.IFrontend openFrontendById(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openFrontendById is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.tv.tuner.IFrontend.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public android.hardware.tv.tuner.IDemux openDemux(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(iArr.length);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openDemux is unimplemented.");
                    }
                    obtain2.readException();
                    android.hardware.tv.tuner.IDemux asInterface = android.hardware.tv.tuner.IDemux.Stub.asInterface(obtain2.readStrongBinder());
                    obtain2.readIntArray(iArr);
                    return asInterface;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public android.hardware.tv.tuner.DemuxCapabilities getDemuxCaps() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getDemuxCaps is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.tv.tuner.DemuxCapabilities) obtain2.readTypedObject(android.hardware.tv.tuner.DemuxCapabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public android.hardware.tv.tuner.IDescrambler openDescrambler() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openDescrambler is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.tv.tuner.IDescrambler.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public android.hardware.tv.tuner.FrontendInfo getFrontendInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getFrontendInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.tv.tuner.FrontendInfo) obtain2.readTypedObject(android.hardware.tv.tuner.FrontendInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public int[] getLnbIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getLnbIds is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public android.hardware.tv.tuner.ILnb openLnbById(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openLnbById is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.tv.tuner.ILnb.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public android.hardware.tv.tuner.ILnb openLnbByName(java.lang.String str, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(iArr.length);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openLnbByName is unimplemented.");
                    }
                    obtain2.readException();
                    android.hardware.tv.tuner.ILnb asInterface = android.hardware.tv.tuner.ILnb.Stub.asInterface(obtain2.readStrongBinder());
                    obtain2.readIntArray(iArr);
                    return asInterface;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public void setLna(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setLna is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public void setMaxNumberOfFrontends(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setMaxNumberOfFrontends is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public int getMaxNumberOfFrontends(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getMaxNumberOfFrontends is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public boolean isLnaSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isLnaSupported is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public int[] getDemuxIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getDemuxIds is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public android.hardware.tv.tuner.IDemux openDemuxById(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openDemuxById is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.tv.tuner.IDemux.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
            public android.hardware.tv.tuner.DemuxInfo getDemuxInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getDemuxInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.tv.tuner.DemuxInfo) obtain2.readTypedObject(android.hardware.tv.tuner.DemuxInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.ITuner
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

            @Override // android.hardware.tv.tuner.ITuner
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
