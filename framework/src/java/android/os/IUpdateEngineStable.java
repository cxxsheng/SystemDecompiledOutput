package android.os;

/* loaded from: classes3.dex */
public interface IUpdateEngineStable extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$os$IUpdateEngineStable".replace('$', '.');
    public static final java.lang.String HASH = "ee2e6f0bd51391955f79f4d5eeeafc37c668cd40";
    public static final int VERSION = 2;

    void applyPayloadFd(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2, java.lang.String[] strArr) throws android.os.RemoteException;

    boolean bind(android.os.IUpdateEngineStableCallback iUpdateEngineStableCallback) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    boolean unbind(android.os.IUpdateEngineStableCallback iUpdateEngineStableCallback) throws android.os.RemoteException;

    public static class Default implements android.os.IUpdateEngineStable {
        @Override // android.os.IUpdateEngineStable
        public void applyPayloadFd(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.os.IUpdateEngineStable
        public boolean bind(android.os.IUpdateEngineStableCallback iUpdateEngineStableCallback) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUpdateEngineStable
        public boolean unbind(android.os.IUpdateEngineStableCallback iUpdateEngineStableCallback) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUpdateEngineStable
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.os.IUpdateEngineStable
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IUpdateEngineStable {
        static final int TRANSACTION_applyPayloadFd = 1;
        static final int TRANSACTION_bind = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_unbind = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IUpdateEngineStable asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IUpdateEngineStable)) {
                return (android.os.IUpdateEngineStable) queryLocalInterface;
            }
            return new android.os.IUpdateEngineStable.Stub.Proxy(iBinder);
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
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    applyPayloadFd(parcelFileDescriptor, readLong, readLong2, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.os.IUpdateEngineStableCallback asInterface = android.os.IUpdateEngineStableCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean bind = bind(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bind);
                    return true;
                case 3:
                    android.os.IUpdateEngineStableCallback asInterface2 = android.os.IUpdateEngineStableCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unbind = unbind(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unbind);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IUpdateEngineStable {
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

            @Override // android.os.IUpdateEngineStable
            public void applyPayloadFd(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method applyPayloadFd is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngineStable
            public boolean bind(android.os.IUpdateEngineStableCallback iUpdateEngineStableCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iUpdateEngineStableCallback);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method bind is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngineStable
            public boolean unbind(android.os.IUpdateEngineStableCallback iUpdateEngineStableCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iUpdateEngineStableCallback);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method unbind is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateEngineStable
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

            @Override // android.os.IUpdateEngineStable
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
