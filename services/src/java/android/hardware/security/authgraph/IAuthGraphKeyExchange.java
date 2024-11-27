package android.hardware.security.authgraph;

/* loaded from: classes.dex */
public interface IAuthGraphKeyExchange extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$security$authgraph$IAuthGraphKeyExchange".replace('$', '.');
    public static final java.lang.String HASH = "3758824e7b75acdb1ca66620fb8a8aec0ec6dfcc";
    public static final int VERSION = 1;

    android.hardware.security.authgraph.Arc[] authenticationComplete(android.hardware.security.authgraph.SessionIdSignature sessionIdSignature, android.hardware.security.authgraph.Arc[] arcArr) throws android.os.RemoteException;

    android.hardware.security.authgraph.SessionInitiationInfo create() throws android.os.RemoteException;

    android.hardware.security.authgraph.SessionInfo finish(android.hardware.security.authgraph.PubKey pubKey, android.hardware.security.authgraph.Identity identity, android.hardware.security.authgraph.SessionIdSignature sessionIdSignature, byte[] bArr, int i, android.hardware.security.authgraph.Key key) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.security.authgraph.KeInitResult init(android.hardware.security.authgraph.PubKey pubKey, android.hardware.security.authgraph.Identity identity, byte[] bArr, int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.security.authgraph.IAuthGraphKeyExchange {
        @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
        public android.hardware.security.authgraph.SessionInitiationInfo create() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
        public android.hardware.security.authgraph.KeInitResult init(android.hardware.security.authgraph.PubKey pubKey, android.hardware.security.authgraph.Identity identity, byte[] bArr, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
        public android.hardware.security.authgraph.SessionInfo finish(android.hardware.security.authgraph.PubKey pubKey, android.hardware.security.authgraph.Identity identity, android.hardware.security.authgraph.SessionIdSignature sessionIdSignature, byte[] bArr, int i, android.hardware.security.authgraph.Key key) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
        public android.hardware.security.authgraph.Arc[] authenticationComplete(android.hardware.security.authgraph.SessionIdSignature sessionIdSignature, android.hardware.security.authgraph.Arc[] arcArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.security.authgraph.IAuthGraphKeyExchange {
        static final int TRANSACTION_authenticationComplete = 4;
        static final int TRANSACTION_create = 1;
        static final int TRANSACTION_finish = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_init = 2;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.security.authgraph.IAuthGraphKeyExchange.DESCRIPTOR);
        }

        public static android.hardware.security.authgraph.IAuthGraphKeyExchange asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.security.authgraph.IAuthGraphKeyExchange.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.security.authgraph.IAuthGraphKeyExchange)) {
                return (android.hardware.security.authgraph.IAuthGraphKeyExchange) queryLocalInterface;
            }
            return new android.hardware.security.authgraph.IAuthGraphKeyExchange.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "create";
                case 2:
                    return "init";
                case 3:
                    return "finish";
                case 4:
                    return "authenticationComplete";
                case TRANSACTION_getInterfaceHash /* 16777214 */:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.security.authgraph.IAuthGraphKeyExchange.DESCRIPTOR;
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
                    android.hardware.security.authgraph.SessionInitiationInfo create = create();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(create, 1);
                    return true;
                case 2:
                    android.hardware.security.authgraph.PubKey pubKey = (android.hardware.security.authgraph.PubKey) parcel.readTypedObject(android.hardware.security.authgraph.PubKey.CREATOR);
                    android.hardware.security.authgraph.Identity identity = (android.hardware.security.authgraph.Identity) parcel.readTypedObject(android.hardware.security.authgraph.Identity.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.security.authgraph.KeInitResult init = init(pubKey, identity, createByteArray, readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(init, 1);
                    return true;
                case 3:
                    android.hardware.security.authgraph.PubKey pubKey2 = (android.hardware.security.authgraph.PubKey) parcel.readTypedObject(android.hardware.security.authgraph.PubKey.CREATOR);
                    android.hardware.security.authgraph.Identity identity2 = (android.hardware.security.authgraph.Identity) parcel.readTypedObject(android.hardware.security.authgraph.Identity.CREATOR);
                    android.hardware.security.authgraph.SessionIdSignature sessionIdSignature = (android.hardware.security.authgraph.SessionIdSignature) parcel.readTypedObject(android.hardware.security.authgraph.SessionIdSignature.CREATOR);
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt2 = parcel.readInt();
                    android.hardware.security.authgraph.Key key = (android.hardware.security.authgraph.Key) parcel.readTypedObject(android.hardware.security.authgraph.Key.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.security.authgraph.SessionInfo finish = finish(pubKey2, identity2, sessionIdSignature, createByteArray2, readInt2, key);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(finish, 1);
                    return true;
                case 4:
                    android.hardware.security.authgraph.SessionIdSignature sessionIdSignature2 = (android.hardware.security.authgraph.SessionIdSignature) parcel.readTypedObject(android.hardware.security.authgraph.SessionIdSignature.CREATOR);
                    android.hardware.security.authgraph.Arc[] arcArr = (android.hardware.security.authgraph.Arc[]) parcel.createFixedArray(android.hardware.security.authgraph.Arc[].class, android.hardware.security.authgraph.Arc.CREATOR, 2);
                    parcel.enforceNoDataAvail();
                    android.hardware.security.authgraph.Arc[] authenticationComplete = authenticationComplete(sessionIdSignature2, arcArr);
                    parcel2.writeNoException();
                    parcel2.writeFixedArray(authenticationComplete, 1, 2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.security.authgraph.IAuthGraphKeyExchange {
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
                return android.hardware.security.authgraph.IAuthGraphKeyExchange.DESCRIPTOR;
            }

            @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
            public android.hardware.security.authgraph.SessionInitiationInfo create() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.authgraph.IAuthGraphKeyExchange.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method create is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.security.authgraph.SessionInitiationInfo) obtain2.readTypedObject(android.hardware.security.authgraph.SessionInitiationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
            public android.hardware.security.authgraph.KeInitResult init(android.hardware.security.authgraph.PubKey pubKey, android.hardware.security.authgraph.Identity identity, byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.authgraph.IAuthGraphKeyExchange.DESCRIPTOR);
                    obtain.writeTypedObject(pubKey, 0);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method init is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.security.authgraph.KeInitResult) obtain2.readTypedObject(android.hardware.security.authgraph.KeInitResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
            public android.hardware.security.authgraph.SessionInfo finish(android.hardware.security.authgraph.PubKey pubKey, android.hardware.security.authgraph.Identity identity, android.hardware.security.authgraph.SessionIdSignature sessionIdSignature, byte[] bArr, int i, android.hardware.security.authgraph.Key key) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.authgraph.IAuthGraphKeyExchange.DESCRIPTOR);
                    obtain.writeTypedObject(pubKey, 0);
                    obtain.writeTypedObject(identity, 0);
                    obtain.writeTypedObject(sessionIdSignature, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(key, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method finish is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.security.authgraph.SessionInfo) obtain2.readTypedObject(android.hardware.security.authgraph.SessionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
            public android.hardware.security.authgraph.Arc[] authenticationComplete(android.hardware.security.authgraph.SessionIdSignature sessionIdSignature, android.hardware.security.authgraph.Arc[] arcArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.authgraph.IAuthGraphKeyExchange.DESCRIPTOR);
                    obtain.writeTypedObject(sessionIdSignature, 0);
                    obtain.writeFixedArray(arcArr, 0, 2);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method authenticationComplete is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.security.authgraph.Arc[]) obtain2.createFixedArray(android.hardware.security.authgraph.Arc[].class, android.hardware.security.authgraph.Arc.CREATOR, 2);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.security.authgraph.IAuthGraphKeyExchange.DESCRIPTOR);
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

            @Override // android.hardware.security.authgraph.IAuthGraphKeyExchange
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.security.authgraph.IAuthGraphKeyExchange.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.security.authgraph.IAuthGraphKeyExchange.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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

        public int getMaxTransactionId() {
            return TRANSACTION_getInterfaceHash;
        }
    }
}
