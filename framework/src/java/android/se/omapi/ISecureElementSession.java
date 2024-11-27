package android.se.omapi;

/* loaded from: classes3.dex */
public interface ISecureElementSession extends android.os.IInterface {
    public static final java.lang.String HASH = "894069bcfe4f35ceb2088278ddf87c83adee8014";
    public static final int VERSION = 1;

    void close() throws android.os.RemoteException;

    void closeChannels() throws android.os.RemoteException;

    byte[] getAtr() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    boolean isClosed() throws android.os.RemoteException;

    android.se.omapi.ISecureElementChannel openBasicChannel(byte[] bArr, byte b, android.se.omapi.ISecureElementListener iSecureElementListener) throws android.os.RemoteException;

    android.se.omapi.ISecureElementChannel openLogicalChannel(byte[] bArr, byte b, android.se.omapi.ISecureElementListener iSecureElementListener) throws android.os.RemoteException;

    public static class Default implements android.se.omapi.ISecureElementSession {
        @Override // android.se.omapi.ISecureElementSession
        public byte[] getAtr() throws android.os.RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementSession
        public void close() throws android.os.RemoteException {
        }

        @Override // android.se.omapi.ISecureElementSession
        public void closeChannels() throws android.os.RemoteException {
        }

        @Override // android.se.omapi.ISecureElementSession
        public boolean isClosed() throws android.os.RemoteException {
            return false;
        }

        @Override // android.se.omapi.ISecureElementSession
        public android.se.omapi.ISecureElementChannel openBasicChannel(byte[] bArr, byte b, android.se.omapi.ISecureElementListener iSecureElementListener) throws android.os.RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementSession
        public android.se.omapi.ISecureElementChannel openLogicalChannel(byte[] bArr, byte b, android.se.omapi.ISecureElementListener iSecureElementListener) throws android.os.RemoteException {
            return null;
        }

        @Override // android.se.omapi.ISecureElementSession
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.se.omapi.ISecureElementSession
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.se.omapi.ISecureElementSession {
        public static final java.lang.String DESCRIPTOR = "android$se$omapi$ISecureElementSession".replace('$', '.');
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_closeChannels = 3;
        static final int TRANSACTION_getAtr = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_isClosed = 4;
        static final int TRANSACTION_openBasicChannel = 5;
        static final int TRANSACTION_openLogicalChannel = 6;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.se.omapi.ISecureElementSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.se.omapi.ISecureElementSession)) {
                return (android.se.omapi.ISecureElementSession) queryLocalInterface;
            }
            return new android.se.omapi.ISecureElementSession.Stub.Proxy(iBinder);
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
                    byte[] atr = getAtr();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(atr);
                    return true;
                case 2:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    closeChannels();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean isClosed = isClosed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClosed);
                    return true;
                case 5:
                    byte[] createByteArray = parcel.createByteArray();
                    byte readByte = parcel.readByte();
                    android.se.omapi.ISecureElementListener asInterface = android.se.omapi.ISecureElementListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.se.omapi.ISecureElementChannel openBasicChannel = openBasicChannel(createByteArray, readByte, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openBasicChannel);
                    return true;
                case 6:
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte readByte2 = parcel.readByte();
                    android.se.omapi.ISecureElementListener asInterface2 = android.se.omapi.ISecureElementListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.se.omapi.ISecureElementChannel openLogicalChannel = openLogicalChannel(createByteArray2, readByte2, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openLogicalChannel);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.se.omapi.ISecureElementSession {
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
                return android.se.omapi.ISecureElementSession.Stub.DESCRIPTOR;
            }

            @Override // android.se.omapi.ISecureElementSession
            public byte[] getAtr() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.se.omapi.ISecureElementSession.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getAtr is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.se.omapi.ISecureElementSession.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public void closeChannels() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.se.omapi.ISecureElementSession.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method closeChannels is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public boolean isClosed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.se.omapi.ISecureElementSession.Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isClosed is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public android.se.omapi.ISecureElementChannel openBasicChannel(byte[] bArr, byte b, android.se.omapi.ISecureElementListener iSecureElementListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.se.omapi.ISecureElementSession.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByte(b);
                    obtain.writeStrongInterface(iSecureElementListener);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openBasicChannel is unimplemented.");
                    }
                    obtain2.readException();
                    return android.se.omapi.ISecureElementChannel.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public android.se.omapi.ISecureElementChannel openLogicalChannel(byte[] bArr, byte b, android.se.omapi.ISecureElementListener iSecureElementListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.se.omapi.ISecureElementSession.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByte(b);
                    obtain.writeStrongInterface(iSecureElementListener);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method openLogicalChannel is unimplemented.");
                    }
                    obtain2.readException();
                    return android.se.omapi.ISecureElementChannel.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.se.omapi.ISecureElementSession
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.se.omapi.ISecureElementSession.Stub.DESCRIPTOR);
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

            @Override // android.se.omapi.ISecureElementSession
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.se.omapi.ISecureElementSession.Stub.DESCRIPTOR);
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
