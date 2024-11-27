package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public interface IDvrCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$tv$tuner$IDvrCallback".replace('$', '.');
    public static final java.lang.String HASH = "f8d74c149f04e76b6d622db2bd8e465dae24b08c";
    public static final int VERSION = 2;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void onPlaybackStatus(int i) throws android.os.RemoteException;

    void onRecordStatus(byte b) throws android.os.RemoteException;

    public static class Default implements android.hardware.tv.tuner.IDvrCallback {
        @Override // android.hardware.tv.tuner.IDvrCallback
        public void onPlaybackStatus(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvrCallback
        public void onRecordStatus(byte b) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.tuner.IDvrCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.tuner.IDvrCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.tv.tuner.IDvrCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onPlaybackStatus = 1;
        static final int TRANSACTION_onRecordStatus = 2;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.tv.tuner.IDvrCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.tv.tuner.IDvrCallback)) {
                return (android.hardware.tv.tuner.IDvrCallback) queryLocalInterface;
            }
            return new android.hardware.tv.tuner.IDvrCallback.Stub.Proxy(iBinder);
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
                    onPlaybackStatus(readInt);
                    return true;
                case 2:
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    onRecordStatus(readByte);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.tv.tuner.IDvrCallback {
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

            @Override // android.hardware.tv.tuner.IDvrCallback
            public void onPlaybackStatus(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onPlaybackStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvrCallback
            public void onRecordStatus(byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onRecordStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.tuner.IDvrCallback
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

            @Override // android.hardware.tv.tuner.IDvrCallback
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
