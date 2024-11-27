package android.hardware.cas;

/* loaded from: classes.dex */
public interface IDescrambler extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$cas$IDescrambler".replace('$', '.');
    public static final java.lang.String HASH = "bc51d8d70a55ec4723d3f73d0acf7003306bf69f";
    public static final int VERSION = 1;

    int descramble(int i, android.hardware.cas.SubSample[] subSampleArr, android.hardware.cas.SharedBuffer sharedBuffer, long j, android.hardware.cas.DestinationBuffer destinationBuffer, long j2) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void release() throws android.os.RemoteException;

    boolean requiresSecureDecoderComponent(java.lang.String str) throws android.os.RemoteException;

    void setMediaCasSession(byte[] bArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.cas.IDescrambler {
        @Override // android.hardware.cas.IDescrambler
        public int descramble(int i, android.hardware.cas.SubSample[] subSampleArr, android.hardware.cas.SharedBuffer sharedBuffer, long j, android.hardware.cas.DestinationBuffer destinationBuffer, long j2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.cas.IDescrambler
        public void release() throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.IDescrambler
        public boolean requiresSecureDecoderComponent(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.cas.IDescrambler
        public void setMediaCasSession(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.cas.IDescrambler
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.cas.IDescrambler
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.cas.IDescrambler {
        static final int TRANSACTION_descramble = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_release = 2;
        static final int TRANSACTION_requiresSecureDecoderComponent = 3;
        static final int TRANSACTION_setMediaCasSession = 4;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.cas.IDescrambler asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.cas.IDescrambler)) {
                return (android.hardware.cas.IDescrambler) queryLocalInterface;
            }
            return new android.hardware.cas.IDescrambler.Stub.Proxy(iBinder);
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
                    android.hardware.cas.SubSample[] subSampleArr = (android.hardware.cas.SubSample[]) parcel.createTypedArray(android.hardware.cas.SubSample.CREATOR);
                    android.hardware.cas.SharedBuffer sharedBuffer = (android.hardware.cas.SharedBuffer) parcel.readTypedObject(android.hardware.cas.SharedBuffer.CREATOR);
                    long readLong = parcel.readLong();
                    android.hardware.cas.DestinationBuffer destinationBuffer = (android.hardware.cas.DestinationBuffer) parcel.readTypedObject(android.hardware.cas.DestinationBuffer.CREATOR);
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int descramble = descramble(readInt, subSampleArr, sharedBuffer, readLong, destinationBuffer, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeInt(descramble);
                    return true;
                case 2:
                    release();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean requiresSecureDecoderComponent = requiresSecureDecoderComponent(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requiresSecureDecoderComponent);
                    return true;
                case 4:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setMediaCasSession(createByteArray);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.cas.IDescrambler {
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

            @Override // android.hardware.cas.IDescrambler
            public int descramble(int i, android.hardware.cas.SubSample[] subSampleArr, android.hardware.cas.SharedBuffer sharedBuffer, long j, android.hardware.cas.DestinationBuffer destinationBuffer, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(subSampleArr, 0);
                    obtain.writeTypedObject(sharedBuffer, 0);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(destinationBuffer, 0);
                    obtain.writeLong(j2);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method descramble is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
            public void release() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method release is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
            public boolean requiresSecureDecoderComponent(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method requiresSecureDecoderComponent is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
            public void setMediaCasSession(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setMediaCasSession is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.cas.IDescrambler
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

            @Override // android.hardware.cas.IDescrambler
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
