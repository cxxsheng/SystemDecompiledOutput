package android.hardware.tv.hdmi.connection;

/* loaded from: classes.dex */
public interface IHdmiConnection extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$tv$hdmi$connection$IHdmiConnection".replace('$', '.');
    public static final java.lang.String HASH = "85c26fa47f3c3062aa93ffc8bb0897a85c8cb118";
    public static final int VERSION = 1;

    byte getHpdSignal(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.tv.hdmi.connection.HdmiPortInfo[] getPortInfo() throws android.os.RemoteException;

    boolean isConnected(int i) throws android.os.RemoteException;

    void setCallback(android.hardware.tv.hdmi.connection.IHdmiConnectionCallback iHdmiConnectionCallback) throws android.os.RemoteException;

    void setHpdSignal(byte b, int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.tv.hdmi.connection.IHdmiConnection {
        @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
        public android.hardware.tv.hdmi.connection.HdmiPortInfo[] getPortInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
        public boolean isConnected(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
        public void setCallback(android.hardware.tv.hdmi.connection.IHdmiConnectionCallback iHdmiConnectionCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
        public void setHpdSignal(byte b, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
        public byte getHpdSignal(int i) throws android.os.RemoteException {
            return (byte) 0;
        }

        @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.tv.hdmi.connection.IHdmiConnection {
        static final int TRANSACTION_getHpdSignal = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPortInfo = 1;
        static final int TRANSACTION_isConnected = 2;
        static final int TRANSACTION_setCallback = 3;
        static final int TRANSACTION_setHpdSignal = 4;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR);
        }

        public static android.hardware.tv.hdmi.connection.IHdmiConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.tv.hdmi.connection.IHdmiConnection)) {
                return (android.hardware.tv.hdmi.connection.IHdmiConnection) queryLocalInterface;
            }
            return new android.hardware.tv.hdmi.connection.IHdmiConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR;
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
                    android.hardware.tv.hdmi.connection.HdmiPortInfo[] portInfo = getPortInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(portInfo, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isConnected = isConnected(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isConnected);
                    return true;
                case 3:
                    android.hardware.tv.hdmi.connection.IHdmiConnectionCallback asInterface = android.hardware.tv.hdmi.connection.IHdmiConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    byte readByte = parcel.readByte();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHpdSignal(readByte, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte hpdSignal = getHpdSignal(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeByte(hpdSignal);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.tv.hdmi.connection.IHdmiConnection {
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
                return android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR;
            }

            @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
            public android.hardware.tv.hdmi.connection.HdmiPortInfo[] getPortInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getPortInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.tv.hdmi.connection.HdmiPortInfo[]) obtain2.createTypedArray(android.hardware.tv.hdmi.connection.HdmiPortInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
            public boolean isConnected(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isConnected is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
            public void setCallback(android.hardware.tv.hdmi.connection.IHdmiConnectionCallback iHdmiConnectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR);
                    obtain.writeStrongInterface(iHdmiConnectionCallback);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
            public void setHpdSignal(byte b, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setHpdSignal is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
            public byte getHpdSignal(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getHpdSignal is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR);
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

            @Override // android.hardware.tv.hdmi.connection.IHdmiConnection
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.tv.hdmi.connection.IHdmiConnection.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
