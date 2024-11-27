package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public interface IFilter extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$tv$tuner$IFilter".replace('$', '.');
    public static final java.lang.String HASH = "f8d74c149f04e76b6d622db2bd8e465dae24b08c";
    public static final int VERSION = 2;

    void close() throws android.os.RemoteException;

    void configure(android.hardware.tv.tuner.DemuxFilterSettings demuxFilterSettings) throws android.os.RemoteException;

    void configureAvStreamType(android.hardware.tv.tuner.AvStreamType avStreamType) throws android.os.RemoteException;

    void configureIpCid(int i) throws android.os.RemoteException;

    void configureMonitorEvent(int i) throws android.os.RemoteException;

    void flush() throws android.os.RemoteException;

    long getAvSharedHandle(android.hardware.common.NativeHandle nativeHandle) throws android.os.RemoteException;

    int getId() throws android.os.RemoteException;

    long getId64Bit() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getQueueDesc(android.hardware.common.fmq.MQDescriptor<java.lang.Byte, java.lang.Byte> mQDescriptor) throws android.os.RemoteException;

    void releaseAvHandle(android.hardware.common.NativeHandle nativeHandle, long j) throws android.os.RemoteException;

    void setDataSource(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException;

    void setDelayHint(android.hardware.tv.tuner.FilterDelayHint filterDelayHint) throws android.os.RemoteException;

    void start() throws android.os.RemoteException;

    void stop() throws android.os.RemoteException;

    public static class Default implements android.hardware.tv.tuner.IFilter {
        @Override // android.hardware.tv.tuner.IFilter
        public void getQueueDesc(android.hardware.common.fmq.MQDescriptor<java.lang.Byte, java.lang.Byte> mQDescriptor) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void close() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configure(android.hardware.tv.tuner.DemuxFilterSettings demuxFilterSettings) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configureAvStreamType(android.hardware.tv.tuner.AvStreamType avStreamType) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configureIpCid(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void configureMonitorEvent(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void start() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void stop() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void flush() throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public long getAvSharedHandle(android.hardware.common.NativeHandle nativeHandle) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public int getId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public long getId64Bit() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void releaseAvHandle(android.hardware.common.NativeHandle nativeHandle, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void setDataSource(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public void setDelayHint(android.hardware.tv.tuner.FilterDelayHint filterDelayHint) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IFilter
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IFilter
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.tv.tuner.IFilter {
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_configure = 3;
        static final int TRANSACTION_configureAvStreamType = 4;
        static final int TRANSACTION_configureIpCid = 5;
        static final int TRANSACTION_configureMonitorEvent = 6;
        static final int TRANSACTION_flush = 9;
        static final int TRANSACTION_getAvSharedHandle = 10;
        static final int TRANSACTION_getId = 11;
        static final int TRANSACTION_getId64Bit = 12;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getQueueDesc = 1;
        static final int TRANSACTION_releaseAvHandle = 13;
        static final int TRANSACTION_setDataSource = 14;
        static final int TRANSACTION_setDelayHint = 15;
        static final int TRANSACTION_start = 7;
        static final int TRANSACTION_stop = 8;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.tv.tuner.IFilter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.tv.tuner.IFilter)) {
                return (android.hardware.tv.tuner.IFilter) queryLocalInterface;
            }
            return new android.hardware.tv.tuner.IFilter.Stub.Proxy(iBinder);
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
                    close();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.hardware.tv.tuner.DemuxFilterSettings demuxFilterSettings = (android.hardware.tv.tuner.DemuxFilterSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterSettings.CREATOR);
                    parcel.enforceNoDataAvail();
                    configure(demuxFilterSettings);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.hardware.tv.tuner.AvStreamType avStreamType = (android.hardware.tv.tuner.AvStreamType) parcel.readTypedObject(android.hardware.tv.tuner.AvStreamType.CREATOR);
                    parcel.enforceNoDataAvail();
                    configureAvStreamType(avStreamType);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    configureIpCid(readInt);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    configureMonitorEvent(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    start();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    flush();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.hardware.common.NativeHandle nativeHandle = new android.hardware.common.NativeHandle();
                    parcel.enforceNoDataAvail();
                    long avSharedHandle = getAvSharedHandle(nativeHandle);
                    parcel2.writeNoException();
                    parcel2.writeLong(avSharedHandle);
                    parcel2.writeTypedObject(nativeHandle, 1);
                    return true;
                case 11:
                    int id = getId();
                    parcel2.writeNoException();
                    parcel2.writeInt(id);
                    return true;
                case 12:
                    long id64Bit = getId64Bit();
                    parcel2.writeNoException();
                    parcel2.writeLong(id64Bit);
                    return true;
                case 13:
                    android.hardware.common.NativeHandle nativeHandle2 = (android.hardware.common.NativeHandle) parcel.readTypedObject(android.hardware.common.NativeHandle.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    releaseAvHandle(nativeHandle2, readLong);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.hardware.tv.tuner.IFilter asInterface = asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDataSource(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.hardware.tv.tuner.FilterDelayHint filterDelayHint = (android.hardware.tv.tuner.FilterDelayHint) parcel.readTypedObject(android.hardware.tv.tuner.FilterDelayHint.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDelayHint(filterDelayHint);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.tv.tuner.IFilter {
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

            @Override // android.hardware.tv.tuner.IFilter
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

            @Override // android.hardware.tv.tuner.IFilter
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configure(android.hardware.tv.tuner.DemuxFilterSettings demuxFilterSettings) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(demuxFilterSettings, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method configure is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configureAvStreamType(android.hardware.tv.tuner.AvStreamType avStreamType) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(avStreamType, 0);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method configureAvStreamType is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configureIpCid(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method configureIpCid is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void configureMonitorEvent(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method configureMonitorEvent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void start() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method start is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void stop() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method stop is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void flush() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method flush is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public long getAvSharedHandle(android.hardware.common.NativeHandle nativeHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getAvSharedHandle is unimplemented.");
                    }
                    obtain2.readException();
                    long readLong = obtain2.readLong();
                    if (obtain2.readInt() != 0) {
                        nativeHandle.readFromParcel(obtain2);
                    }
                    return readLong;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public int getId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getId is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public long getId64Bit() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getId64Bit is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void releaseAvHandle(android.hardware.common.NativeHandle nativeHandle, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(nativeHandle, 0);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method releaseAvHandle is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void setDataSource(android.hardware.tv.tuner.IFilter iFilter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iFilter);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setDataSource is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
            public void setDelayHint(android.hardware.tv.tuner.FilterDelayHint filterDelayHint) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(filterDelayHint, 0);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setDelayHint is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IFilter
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

            @Override // android.hardware.tv.tuner.IFilter
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
