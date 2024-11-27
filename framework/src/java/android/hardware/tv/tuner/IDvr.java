package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public interface IDvr extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$tv$tuner$IDvr".replace('$', '.');
    public static final java.lang.String HASH = "f8d74c149f04e76b6d622db2bd8e465dae24b08c";
    public static final int VERSION = 2;

    void attachFilter(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException;

    void close() throws android.os.RemoteException;

    void configure(android.hardware.tv.tuner.DvrSettings dvrSettings) throws android.os.RemoteException;

    void detachFilter(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException;

    void flush() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getQueueDesc(android.hardware.common.fmq.MQDescriptor<java.lang.Byte, java.lang.Byte> mQDescriptor) throws android.os.RemoteException;

    void setStatusCheckIntervalHint(long j) throws android.os.RemoteException;

    void start() throws android.os.RemoteException;

    void stop() throws android.os.RemoteException;

    public static class Default implements android.hardware.tv.tuner.IDvr {
        @Override // android.hardware.tv.tuner.IDvr
        public void getQueueDesc(android.hardware.common.fmq.MQDescriptor<java.lang.Byte, java.lang.Byte> mQDescriptor) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void configure(android.hardware.tv.tuner.DvrSettings dvrSettings) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void attachFilter(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void detachFilter(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void start() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void stop() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void flush() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void close() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public void setStatusCheckIntervalHint(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvr
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IDvr
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.tv.tuner.IDvr {
        static final int TRANSACTION_attachFilter = 3;
        static final int TRANSACTION_close = 8;
        static final int TRANSACTION_configure = 2;
        static final int TRANSACTION_detachFilter = 4;
        static final int TRANSACTION_flush = 7;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getQueueDesc = 1;
        static final int TRANSACTION_setStatusCheckIntervalHint = 9;
        static final int TRANSACTION_start = 5;
        static final int TRANSACTION_stop = 6;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.tv.tuner.IDvr asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.tv.tuner.IDvr)) {
                return (android.hardware.tv.tuner.IDvr) queryLocalInterface;
            }
            return new android.hardware.tv.tuner.IDvr.Stub.Proxy(iBinder);
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
                    android.hardware.common.fmq.MQDescriptor<java.lang.Byte, java.lang.Byte> mQDescriptor = new android.hardware.common.fmq.MQDescriptor<>();
                    parcel.enforceNoDataAvail();
                    getQueueDesc(mQDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mQDescriptor, 1);
                    return true;
                case 2:
                    android.hardware.tv.tuner.DvrSettings dvrSettings = (android.hardware.tv.tuner.DvrSettings) parcel.readTypedObject(android.hardware.tv.tuner.DvrSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    configure(dvrSettings);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.hardware.tv.tuner.IFilter asInterface = android.hardware.tv.tuner.IFilter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachFilter(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.hardware.tv.tuner.IFilter asInterface2 = android.hardware.tv.tuner.IFilter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    detachFilter(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    start();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    flush();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setStatusCheckIntervalHint(readLong);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.tv.tuner.IDvr {
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

            @Override // android.hardware.tv.tuner.IDvr
            public void getQueueDesc(android.hardware.common.fmq.MQDescriptor<java.lang.Byte, java.lang.Byte> mQDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getQueueDesc is unimplemented.");
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        mQDescriptor.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void configure(android.hardware.tv.tuner.DvrSettings dvrSettings) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(dvrSettings, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method configure is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void attachFilter(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method attachFilter is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void detachFilter(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method detachFilter is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void start() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method start is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void stop() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method stop is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void flush() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method flush is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
            public void setStatusCheckIntervalHint(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setStatusCheckIntervalHint is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvr
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

            @Override // android.hardware.tv.tuner.IDvr
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
