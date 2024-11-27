package android.hardware.tv.hdmi.earc;

/* loaded from: classes.dex */
public interface IEArcCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$tv$hdmi$earc$IEArcCallback".replace('$', '.');
    public static final java.lang.String HASH = "101230f18c7b8438921e517e80eea4ccc7c1e463";
    public static final int VERSION = 1;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void onCapabilitiesReported(byte[] bArr, int i) throws android.os.RemoteException;

    void onStateChange(byte b, int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.tv.hdmi.earc.IEArcCallback {
        @Override // android.hardware.tv.hdmi.earc.IEArcCallback
        public void onStateChange(byte b, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.earc.IEArcCallback
        public void onCapabilitiesReported(byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.earc.IEArcCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.hdmi.earc.IEArcCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.tv.hdmi.earc.IEArcCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onCapabilitiesReported = 2;
        static final int TRANSACTION_onStateChange = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.tv.hdmi.earc.IEArcCallback.DESCRIPTOR);
        }

        public static android.hardware.tv.hdmi.earc.IEArcCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.tv.hdmi.earc.IEArcCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.tv.hdmi.earc.IEArcCallback)) {
                return (android.hardware.tv.hdmi.earc.IEArcCallback) queryLocalInterface;
            }
            return new android.hardware.tv.hdmi.earc.IEArcCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.tv.hdmi.earc.IEArcCallback.DESCRIPTOR;
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
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    byte readByte = parcel.readByte();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStateChange(readByte, readInt);
                    return true;
                case 2:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCapabilitiesReported(createByteArray, readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.tv.hdmi.earc.IEArcCallback {
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
                return android.hardware.tv.hdmi.earc.IEArcCallback.DESCRIPTOR;
            }

            @Override // android.hardware.tv.hdmi.earc.IEArcCallback
            public void onStateChange(byte b, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.earc.IEArcCallback.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onStateChange is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.earc.IEArcCallback
            public void onCapabilitiesReported(byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.earc.IEArcCallback.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onCapabilitiesReported is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.earc.IEArcCallback
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.tv.hdmi.earc.IEArcCallback.DESCRIPTOR);
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

            @Override // android.hardware.tv.hdmi.earc.IEArcCallback
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.tv.hdmi.earc.IEArcCallback.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.tv.hdmi.earc.IEArcCallback.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}
