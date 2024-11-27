package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public interface IDemux extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$tv$tuner$IDemux".replace('$', '.');
    public static final java.lang.String HASH = "f8d74c149f04e76b6d622db2bd8e465dae24b08c";
    public static final int VERSION = 2;

    void close() throws android.os.RemoteException;

    void connectCiCam(int i) throws android.os.RemoteException;

    void disconnectCiCam() throws android.os.RemoteException;

    int getAvSyncHwId(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException;

    long getAvSyncTime(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.tv.tuner.IDvr openDvr(byte b, int i, android.hardware.tv.tuner.IDvrCallback iDvrCallback) throws android.os.RemoteException;

    android.hardware.tv.tuner.IFilter openFilter(android.hardware.tv.tuner.DemuxFilterType demuxFilterType, int i, android.hardware.tv.tuner.IFilterCallback iFilterCallback) throws android.os.RemoteException;

    android.hardware.tv.tuner.ITimeFilter openTimeFilter() throws android.os.RemoteException;

    void setFrontendDataSource(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.tv.tuner.IDemux {
        @Override // android.hardware.tv.tuner.IDemux
        public void setFrontendDataSource(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public android.hardware.tv.tuner.IFilter openFilter(android.hardware.tv.tuner.DemuxFilterType demuxFilterType, int i, android.hardware.tv.tuner.IFilterCallback iFilterCallback) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public android.hardware.tv.tuner.ITimeFilter openTimeFilter() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public int getAvSyncHwId(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public long getAvSyncTime(int i) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public void close() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public android.hardware.tv.tuner.IDvr openDvr(byte b, int i, android.hardware.tv.tuner.IDvrCallback iDvrCallback) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public void connectCiCam(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public void disconnectCiCam() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDemux
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IDemux
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.tv.tuner.IDemux {
        static final int TRANSACTION_close = 6;
        static final int TRANSACTION_connectCiCam = 8;
        static final int TRANSACTION_disconnectCiCam = 9;
        static final int TRANSACTION_getAvSyncHwId = 4;
        static final int TRANSACTION_getAvSyncTime = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_openDvr = 7;
        static final int TRANSACTION_openFilter = 2;
        static final int TRANSACTION_openTimeFilter = 3;
        static final int TRANSACTION_setFrontendDataSource = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.tv.tuner.IDemux asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.tv.tuner.IDemux)) {
                return (android.hardware.tv.tuner.IDemux) queryLocalInterface;
            }
            return new android.hardware.tv.tuner.IDemux.Stub.Proxy(iBinder);
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFrontendDataSource(readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.hardware.tv.tuner.DemuxFilterType demuxFilterType = (android.hardware.tv.tuner.DemuxFilterType) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterType.CREATOR);
                    int readInt2 = parcel.readInt();
                    android.hardware.tv.tuner.IFilterCallback asInterface = android.hardware.tv.tuner.IFilterCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.hardware.tv.tuner.IFilter openFilter = openFilter(demuxFilterType, readInt2, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openFilter);
                    return true;
                case 3:
                    android.hardware.tv.tuner.ITimeFilter openTimeFilter = openTimeFilter();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openTimeFilter);
                    return true;
                case 4:
                    android.hardware.tv.tuner.IFilter asInterface2 = android.hardware.tv.tuner.IFilter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int avSyncHwId = getAvSyncHwId(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(avSyncHwId);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long avSyncTime = getAvSyncTime(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeLong(avSyncTime);
                    return true;
                case 6:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    byte readByte = parcel.readByte();
                    int readInt4 = parcel.readInt();
                    android.hardware.tv.tuner.IDvrCallback asInterface3 = android.hardware.tv.tuner.IDvrCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.hardware.tv.tuner.IDvr openDvr = openDvr(readByte, readInt4, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openDvr);
                    return true;
                case 8:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    connectCiCam(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    disconnectCiCam();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.tv.tuner.IDemux {
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

            @Override // android.hardware.tv.tuner.IDemux
            public void setFrontendDataSource(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setFrontendDataSource is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public android.hardware.tv.tuner.IFilter openFilter(android.hardware.tv.tuner.DemuxFilterType demuxFilterType, int i, android.hardware.tv.tuner.IFilterCallback iFilterCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(demuxFilterType, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFilterCallback);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openFilter is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.tv.tuner.IFilter.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public android.hardware.tv.tuner.ITimeFilter openTimeFilter() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openTimeFilter is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.tv.tuner.ITimeFilter.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public int getAvSyncHwId(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getAvSyncHwId is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public long getAvSyncTime(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getAvSyncTime is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public android.hardware.tv.tuner.IDvr openDvr(byte b, int i, android.hardware.tv.tuner.IDvrCallback iDvrCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDvrCallback);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openDvr is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.tv.tuner.IDvr.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public void connectCiCam(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method connectCiCam is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
            public void disconnectCiCam() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method disconnectCiCam is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDemux
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

            @Override // android.hardware.tv.tuner.IDemux
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
