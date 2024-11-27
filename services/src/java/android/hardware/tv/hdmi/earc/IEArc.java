package android.hardware.tv.hdmi.earc;

/* loaded from: classes.dex */
public interface IEArc extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$tv$hdmi$earc$IEArc".replace('$', '.');
    public static final java.lang.String HASH = "101230f18c7b8438921e517e80eea4ccc7c1e463";
    public static final int VERSION = 1;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    byte[] getLastReportedAudioCapabilities(int i) throws android.os.RemoteException;

    byte getState(int i) throws android.os.RemoteException;

    boolean isEArcEnabled() throws android.os.RemoteException;

    void setCallback(android.hardware.tv.hdmi.earc.IEArcCallback iEArcCallback) throws android.os.RemoteException;

    void setEArcEnabled(boolean z) throws android.os.RemoteException;

    public static class Default implements android.hardware.tv.hdmi.earc.IEArc {
        @Override // android.hardware.tv.hdmi.earc.IEArc
        public void setEArcEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.earc.IEArc
        public boolean isEArcEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.tv.hdmi.earc.IEArc
        public void setCallback(android.hardware.tv.hdmi.earc.IEArcCallback iEArcCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.earc.IEArc
        public byte getState(int i) throws android.os.RemoteException {
            return (byte) 0;
        }

        @Override // android.hardware.tv.hdmi.earc.IEArc
        public byte[] getLastReportedAudioCapabilities(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.hdmi.earc.IEArc
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.hdmi.earc.IEArc
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.tv.hdmi.earc.IEArc {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getLastReportedAudioCapabilities = 5;
        static final int TRANSACTION_getState = 4;
        static final int TRANSACTION_isEArcEnabled = 2;
        static final int TRANSACTION_setCallback = 3;
        static final int TRANSACTION_setEArcEnabled = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR);
        }

        public static android.hardware.tv.hdmi.earc.IEArc asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.tv.hdmi.earc.IEArc)) {
                return (android.hardware.tv.hdmi.earc.IEArc) queryLocalInterface;
            }
            return new android.hardware.tv.hdmi.earc.IEArc.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR;
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEArcEnabled(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean isEArcEnabled = isEArcEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEArcEnabled);
                    return true;
                case 3:
                    android.hardware.tv.hdmi.earc.IEArcCallback asInterface = android.hardware.tv.hdmi.earc.IEArcCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte state = getState(readInt);
                    parcel2.writeNoException();
                    parcel2.writeByte(state);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] lastReportedAudioCapabilities = getLastReportedAudioCapabilities(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(lastReportedAudioCapabilities);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.tv.hdmi.earc.IEArc {
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
                return android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR;
            }

            @Override // android.hardware.tv.hdmi.earc.IEArc
            public void setEArcEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setEArcEnabled is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.earc.IEArc
            public boolean isEArcEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isEArcEnabled is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.earc.IEArc
            public void setCallback(android.hardware.tv.hdmi.earc.IEArcCallback iEArcCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR);
                    obtain.writeStrongInterface(iEArcCallback);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.earc.IEArc
            public byte getState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getState is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.earc.IEArc
            public byte[] getLastReportedAudioCapabilities(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getLastReportedAudioCapabilities is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.earc.IEArc
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR);
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

            @Override // android.hardware.tv.hdmi.earc.IEArc
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.tv.hdmi.earc.IEArc.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
